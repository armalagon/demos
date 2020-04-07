package ni.org.jug.hr.attendance.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Embeddable
public class AuditTrail {

    @NotNull
    @PastOrPresent
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @PastOrPresent
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    @PrePersist
    public void onBeforeInsert() {
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    public void onBeforeUpdate() {
        updatedOn = LocalDateTime.now();
    }
}
