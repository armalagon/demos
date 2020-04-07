package ni.org.jug.hr.attendance.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@Table(name = "client")
@SequenceGenerator(name = "seq", sequenceName = "client_id_seq", allocationSize = 1)
public class Client extends SerialIdentifiable<Integer> implements Activeable {

    @NotNull
    @Valid
    @Embedded
    private Description description = new Description();

    @NotNull
    @Column(name = "is_active")
    private Boolean active = Boolean.TRUE;

    @NotNull
    @PastOrPresent
    @Column(name = "client_since")
    private LocalDate clientSince;

    @Column(name = "contract_cancellation_date")
    private LocalDate contractCancellation;

    @NotNull
    @Valid
    @Embedded
    private Country country = new Country();

    @NotNull
    @Valid
    @Embedded
    private AuditTrail auditTrail = new AuditTrail();

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public Boolean getActive() {
        return active;
    }

    @Override
    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getClientSince() {
        return clientSince;
    }

    public void setClientSince(LocalDate clientSince) {
        this.clientSince = clientSince;
    }

    public LocalDate getContractCancellation() {
        return contractCancellation;
    }

    public void setContractCancellation(LocalDate contractCancellation) {
        this.contractCancellation = contractCancellation;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public AuditTrail getAuditTrail() {
        return auditTrail;
    }

    public void setAuditTrail(AuditTrail auditTrail) {
        this.auditTrail = auditTrail;
    }

    @Override
    public int hashCode() {
        return 7;
    }
}
