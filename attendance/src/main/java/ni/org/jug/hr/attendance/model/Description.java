package ni.org.jug.hr.attendance.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Embeddable
public class Description {

    @NotEmpty
    @Size(min = 3, max = 100)
    @Column(name = "short_name")
    private String shortName;

    @NotEmpty
    @Size(min = 3, max = 300)
    @Column(name = "full_name")
    private String fullName;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
