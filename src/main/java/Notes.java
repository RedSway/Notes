import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;
import java.sql.Timestamp;

public class Notes {

    private StringProperty value;
    private ObjectProperty<Timestamp> date;

    public Notes() {
        this(null, null);
    }

    public Notes(String value, Timestamp date) {
        this.value = new SimpleStringProperty(value);
        this.date = new SimpleObjectProperty<>(date);
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public Timestamp getDate() {
        return date.get();
    }

    public ObjectProperty<Timestamp> dateProperty() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date.set(date);
    }
}
