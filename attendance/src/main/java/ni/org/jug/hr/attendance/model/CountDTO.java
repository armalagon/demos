package ni.org.jug.hr.attendance.model;

import java.time.LocalDate;

public class CountDTO {
    public final LocalDate date;
    public final String dayName;
    public final Long count;

    public CountDTO(LocalDate date, String dayName, Long count) {
        this.date = date;
        this.dayName = dayName;
        this.count = count;
    }
}
