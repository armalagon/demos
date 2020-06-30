package ni.org.jug.hr.attendance.boundary;

import ni.org.jug.hr.attendance.model.Attendance;
import ni.org.jug.hr.attendance.model.CountDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttendanceRepository extends CrudRepository<Attendance, Integer> {

    @Query("SELECT DISTINCT a FROM Attendance a JOIN FETCH a.details d WHERE a.year >= 2019")
    List<Attendance> findAllAttendance();

    @Query("SELECT new ni.org.jug.hr.attendance.model.CountDTO(a.date, a.dayname, COUNT(d)) " +
            "FROM Attendance a JOIN a.details d " +
            "WHERE a.year >= 2019 " +
            "GROUP BY a.date, a.dayname")
    List<CountDTO> findAttendanceCount();
}
