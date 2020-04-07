package ni.org.jug.hr.attendance.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Entity
@Table(name = "attendance")
public class Attendance extends Identifiable<Integer> {

    @NotNull
    @PastOrPresent
    @Column(name = "attd_date")
    private LocalDate date;

    @NotNull
    @Min(2015)
    @Max(2100)
    @Column(name = "attd_year")
    private Short year;

    @NotNull
    @Min(1)
    @Max(12)
    @Column(name = "attd_month")
    private Short month;

    @NotNull
    @Min(1)
    @Max(31)
    @Column(name = "attd_day")
    private Short day;

    @NotNull
    @Min(1)
    @Max(7)
    @Column(name = "attd_week_day")
    private Short weekDay;

    @NotEmpty
    @Size(min = 3)
    @Column(name = "attd_dayname")
    private String dayname;

    @NotNull
    @Min(1)
    @Max(53)
    @Column(name = "attd_week_year")
    private Short weekYear;

    @NotNull
    @Valid
    @Embedded
    private AuditTrail auditTrail = new AuditTrail();

    @OneToMany(mappedBy = "attendance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttendanceDetail> details = new ArrayList<>();

    public Attendance() {
    }

    public Attendance(LocalDate date) {
        super(Dates.toInt(date));
        this.date = date;
        initDependant();
    }

    public LocalDate getDate() {
        return date;
    }

    public Short getYear() {
        return year;
    }

    public Short getMonth() {
        return month;
    }

    public Short getDay() {
        return day;
    }

    public Short getWeekDay() {
        return weekDay;
    }

    public String getDayname() {
        return dayname;
    }

    public Short getWeekYear() {
        return weekYear;
    }

    public AuditTrail getAuditTrail() {
        return auditTrail;
    }

    public List<AttendanceDetail> getDetails() {
        return details;
    }

    private void initDependant() {
        Objects.requireNonNull(date, "[date] required to initialize dependant properties");
        year = (short) date.get(IsoFields.WEEK_BASED_YEAR);
        month = (short) date.getMonthValue();
        day = (short) date.getDayOfMonth();
        weekDay = (short) date.getDayOfWeek().getValue();
        dayname = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        weekYear = (short) date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }

    @Override
    public int hashCode() {
        return 43;
    }
}
