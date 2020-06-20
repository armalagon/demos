package ni.org.jug.hr.attendance;

import ni.org.jug.hr.attendance.model.Attendance;
import ni.org.jug.hr.attendance.model.CountDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

@SpringBootTest
class AttendanceApplicationTests {

    @PersistenceContext
    EntityManager em;

    @Test
    void fetchOnlyHeader() {
        TypedQuery<Attendance> query = em.createQuery("SELECT a FROM Attendance a WHERE a.year >= 2019", Attendance.class);

        long start = System.currentTimeMillis();
        List<Attendance> data = query.getResultList();
        System.out.println("Days: " + data.size());
        for (Attendance att : data) {
            System.out.println("Date: " + att.getDate() + ", dayname: " + att.getDayname());
        }
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - start));
    }

    @Test
    @Transactional
    void fetchHeaderAndLazyLoadDetail() {
        TypedQuery<Attendance> query = em.createQuery("SELECT a FROM Attendance a WHERE a.year >= 2019", Attendance.class);

        long start = System.currentTimeMillis();
        List<Attendance> data = query.getResultList();
        System.out.println("Days: " + data.size());
        for (Attendance att : data) {
            int count = att.getDetails().size();
            System.out.println("Date: " + att.getDate() + ", dayname: " + att.getDayname() + ", employee count: " + count);
        }
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - start));
    }

    @Test
    void fetchHeaderAndDetail() {
        TypedQuery<Attendance> query = em.createQuery("SELECT DISTINCT a FROM Attendance a JOIN FETCH a.details d " +
                "WHERE a.year >= 2019", Attendance.class);

        long start = System.currentTimeMillis();
        List<Attendance> data = query.getResultList();
        System.out.println("Days: " + data.size());
        for (Attendance att : data) {
            int count = att.getDetails().size();
            System.out.println("Date: " + att.getDate() + ", dayname: " + att.getDayname() + ", employee count: " + count);
        }
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - start));
    }

    @Test
    void fetchProjectionUsingDTO() {
        TypedQuery<CountDTO> query = em.createQuery("SELECT new ni.org.jug.hr.attendance.model.CountDTO(a.date, a.dayname, COUNT(d)) " +
                "FROM Attendance a JOIN a.details d " +
                "WHERE a.year >= 2019 " +
                "GROUP BY a.date, a.dayname", CountDTO.class);

        long start = System.currentTimeMillis();
        List<CountDTO> data = query.getResultList();
        System.out.println("Days: " + data.size());
        for (CountDTO att : data) {
            System.out.println("Date: " + att.date + ", dayname: " + att.dayName + ", employee count: " + att.count);
        }
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - start));
    }
}
