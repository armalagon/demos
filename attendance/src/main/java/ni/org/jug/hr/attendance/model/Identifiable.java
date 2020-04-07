package ni.org.jug.hr.attendance.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class Identifiable<T> {

    @Id
    private T id;

    public Identifiable() {
    }

    protected Identifiable(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Identifiable)) {
            return false;
        }
        Identifiable<?> that = (Identifiable<?>) other;
        return Objects.equals(id, that.id);
    }
}
