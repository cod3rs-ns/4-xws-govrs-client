package rs.acs.uns.sw.govrs.client.fx.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ActSample {

    public ObservableList<ActSample> children = FXCollections.observableArrayList();
    private StringProperty id;
    private StringProperty name;
    private ObjectProperty<ActType> type;
    private StringProperty textValue;

    public ActSample(String id, String name, ActType type, String text) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleObjectProperty<>(type);
        this.textValue = new SimpleStringProperty(text);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ActType getType() {
        return type.get();
    }

    public void setType(ActType type) {
        this.type.set(type);
    }

    public ObjectProperty<ActType> typeProperty() {
        return type;
    }

    public String getTextValue() {
        return textValue.get();
    }

    public void setTextValue(String textValue) {
        this.textValue.set(textValue);
    }

    public StringProperty textValueProperty() {
        return textValue;
    }

    @Override
    public String toString() {
        return "ActSample{" +
                "id=" + id +
                ", name=" + name +
                ", type=" + type +
                ", textValue=" + textValue +
                ", children=" + children +
                '}';
    }
}
