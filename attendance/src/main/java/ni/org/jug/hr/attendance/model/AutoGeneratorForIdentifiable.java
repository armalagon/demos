package ni.org.jug.hr.attendance.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@MappedSuperclass
public abstract class AutoGeneratorForIdentifiable<T> extends Identifiable<T> {

    protected abstract T generate();

    @PrePersist
    public void generateBeforePersisted() {
        setId(generate());
    }

}
