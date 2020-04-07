package ni.org.jug.hr.attendance.model;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employee")
@SequenceGenerator(name = "seq", sequenceName = "employee_id_seq", allocationSize = 1)
public class Employee extends SerialIdentifiable<Integer> implements Activeable {

    @NotNull
    @Valid
    @Embedded
    private Name names = new Name();

    @NotNull
    @Valid
    @Embedded
    @AttributeOverride(name = "names", column = @Column(name = "lastnames"))
    private Name lastNames = new Name();

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @PastOrPresent
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @NotNull
    @Valid
    @Embedded
    @AttributeOverride(name = "twoLetterCode", column = @Column(name = "country_of_birth"))
    private Country countryOfBirth = new Country();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "empl_role")
    private EmployeeRole role;

    @NotNull
    @Column(name = "is_active")
    private Boolean active = Boolean.TRUE;

    @NotNull
    @PastOrPresent
    @Column(name = "start_working_date")
    private LocalDate startWorkingDate;

    @PastOrPresent
    @Column(name = "end_working_date")
    private LocalDate endWorkingDate;

    @NotNull
    @Valid
    @Embedded
    private AuditTrail auditTrail = new AuditTrail();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeeAssignment> assignments;

    public Name getNames() {
        return names;
    }

    public void setNames(Name names) {
        this.names = names;
    }

    public Name getLastNames() {
        return lastNames;
    }

    public void setLastNames(Name lastNames) {
        this.lastNames = lastNames;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Country getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(Country countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    @Override
    public Boolean getActive() {
        return active;
    }

    @Override
    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getStartWorkingDate() {
        return startWorkingDate;
    }

    public void setStartWorkingDate(LocalDate startWorkingDate) {
        this.startWorkingDate = startWorkingDate;
    }

    public LocalDate getEndWorkingDate() {
        return endWorkingDate;
    }

    public void setEndWorkingDate(LocalDate endWorkingDate) {
        this.endWorkingDate = endWorkingDate;
    }

    public AuditTrail getAuditTrail() {
        return auditTrail;
    }

    public void setAuditTrail(AuditTrail auditTrail) {
        this.auditTrail = auditTrail;
    }

    public List<EmployeeAssignment> getAssignments() {
        return assignments;
    }

    public String getFullname() {
        StringBuilder fullname = new StringBuilder();
        if (names != null && names.getNames() != null) {
            fullname.append(names.getNames());
        }
        if (lastNames != null && lastNames.getNames() != null) {
            if (fullname.length() > 0) {
                fullname.append(" ");
            }
            fullname.append(lastNames.getNames());
        }
        return fullname.toString();
    }

    @Override
    public int hashCode() {
        return 13;
    }
}
