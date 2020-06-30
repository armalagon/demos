package ni.org.jug.hr.attendance;

import ni.org.jug.hr.attendance.boundary.AttendanceRepository;
import ni.org.jug.hr.attendance.model.Attendance;
import ni.org.jug.hr.attendance.model.CountDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SpringBootTest
class AttendanceApplicationTests {

    private static final ConcurrentMap<String, Long> STATS = new ConcurrentHashMap<>();

    @PersistenceContext
    EntityManager em;

    @Autowired
    AttendanceRepository repository;

    @Test
    void fetchOnlyHeader() {
        TypedQuery<Attendance> query = em.createQuery("SELECT a FROM Attendance a WHERE a.year >= 2019", Attendance.class);

        long start = System.currentTimeMillis();
        List<Attendance> data = query.getResultList();
        System.out.println("Days: " + data.size());
        for (Attendance att : data) {
            System.out.println("Date: " + att.getDate() + ", dayname: " + att.getDayname());
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("Time elapsed: " + duration);
        STATS.put(getTestMethodName(), duration);
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
        long duration = System.currentTimeMillis() - start;
        System.out.println("Time elapsed: " + duration);
        STATS.put(getTestMethodName(), duration);
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
        long duration = System.currentTimeMillis() - start;
        System.out.println("Time elapsed: " + duration);
        STATS.put(getTestMethodName(), duration);
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
        long duration = System.currentTimeMillis() - start;
        System.out.println("Time elapsed: " + duration);
        STATS.put(getTestMethodName(), duration);
    }

    @Test
    void fetchProjectionUsingTuple_v1() {
        TypedQuery<Tuple> query = em.createQuery("SELECT a.date, a.dayname, COUNT(d) " +
                "FROM Attendance a JOIN a.details d " +
                "WHERE a.year >= 2019 " +
                "GROUP BY a.date, a.dayname", Tuple.class);

        long start = System.currentTimeMillis();
        List<Tuple> data = query.getResultList();
        System.out.println("Days: " + data.size());
        for (Tuple tpl : data) {
            System.out.println("Date: " + tpl.get(0) + ", dayname: " + tpl.get(1) + ", employee count: " + tpl.get(2));
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("Time elapsed: " + duration);
        STATS.put(getTestMethodName(), duration);
    }

    @Test
    void fetchProjectionUsingTuple_v2() {
        TypedQuery<Tuple> query = em.createQuery("SELECT a.date as date, a.dayname as dayname, COUNT(d) as count " +
                "FROM Attendance a JOIN a.details d " +
                "WHERE a.year >= 2019 " +
                "GROUP BY a.date, a.dayname", Tuple.class);

        long start = System.currentTimeMillis();
        List<Tuple> data = query.getResultList();
        System.out.println("Days: " + data.size());
        for (Tuple tpl : data) {
            System.out.println("Date: " + tpl.get("date") + ", dayname: " + tpl.get("dayname") + ", employee count: " + tpl.get("count"));
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("Time elapsed: " + duration);
        STATS.put(getTestMethodName(), duration);
    }

    @Test
    void fetchProjectionUsingMap() {
        TypedQuery<Map> query = em.createQuery("SELECT new map(a.date as date, a.dayname as dayname, COUNT(d) as count) " +
                "FROM Attendance a JOIN a.details d " +
                "WHERE a.year >= 2019 " +
                "GROUP BY a.date, a.dayname", Map.class);

        long start = System.currentTimeMillis();
        List<Map> data = query.getResultList();
        System.out.println("Days: " + data.size());
        for (Map map : data) {
            System.out.println("Date: " + map.get("date") + ", dayname: " + map.get("dayname") + ", employee count: " + map.get("count"));
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("Time elapsed: " + duration);
        STATS.put(getTestMethodName(), duration);
    }

    @Test
    void fetchProjectionUsingList() {
        TypedQuery<List> query = em.createQuery("SELECT new list(a.date, a.dayname, COUNT(d)) " +
                "FROM Attendance a JOIN a.details d " +
                "WHERE a.year >= 2019 " +
                "GROUP BY a.date, a.dayname", List.class);

        long start = System.currentTimeMillis();
        List<List> data = query.getResultList();
        System.out.println("Days: " + data.size());
        for (List list : data) {
            System.out.println("Date: " + list.get(0) + ", dayname: " + list.get(1) + ", employee count: " + list.get(2));
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("Time elapsed: " + duration);
        STATS.put(getTestMethodName(), duration);
    }

    @Test
    void fetchHeaderUsingRepository() {
        long start = System.currentTimeMillis();
        List<Attendance> data = repository.findAllAttendance();
        System.out.println("Days: " + data.size());
        for (Attendance att : data) {
            int count = att.getDetails().size();
            System.out.println("Date: " + att.getDate() + ", dayname: " + att.getDayname() + ", employee count: " + count);
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("Time elapsed: " + duration);
        STATS.put(getTestMethodName(), duration);
    }

    @Test
    void fetchProjectionUsingRepository() {
        long start = System.currentTimeMillis();
        List<CountDTO> data = repository.findAttendanceCount();
        System.out.println("Days: " + data.size());
        for (CountDTO att : data) {
            System.out.println("Date: " + att.date + ", dayname: " + att.dayName + ", employee count: " + att.count);
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("Time elapsed: " + duration);
        STATS.put(getTestMethodName(), duration);
    }

    String getTestMethodName() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        return stackTrace[1].getMethodName();
    }

    @AfterAll
    static void printStats() {
        System.out.println("--------------------------------------------------------");
        System.out.println("Fetching strategy duration");
        System.out.println("--------------------------------------------------------");
        STATS.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .forEachOrdered((entry) -> {
                    System.out.println("Method: " + entry.getKey() + ", Duration: " + entry.getValue());
                });
        System.out.println("--------------------------------------------------------");
    }
}
