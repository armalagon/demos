package ni.org.jug.hr.attendance.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Embeddable
public class Country {

    @NotEmpty
    @Size(min = 2, max = 2)
    @Column(name = "country")
    private String twoLetterCode;

    public String getTwoLetterCode() {
        return twoLetterCode;
    }

    public void setTwoLetterCode(String twoLetterCode) {
        this.twoLetterCode = twoLetterCode;
    }
}
