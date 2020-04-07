package ni.org.jug.hr.attendance.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class SerialIdentifiable<T extends Number> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private T id;

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
        if (!(other instanceof SerialIdentifiable)) {
            return false;
        }
        SerialIdentifiable<?> that = (SerialIdentifiable<?>) other;
        return Objects.equals(id, that.id);
    }
}
