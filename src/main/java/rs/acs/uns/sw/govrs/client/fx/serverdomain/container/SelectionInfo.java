package rs.acs.uns.sw.govrs.client.fx.serverdomain.container;


import javafx.beans.property.*;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.util.ElementTypes;

public class SelectionInfo {
    private StringProperty lawId = new SimpleStringProperty();
    private StringProperty elementId = new SimpleStringProperty();
    private ObjectProperty<ElementTypes> elementType = new SimpleObjectProperty<>();
    private ObjectProperty<Element> element = new SimpleObjectProperty<>();
    private BooleanProperty saved = new SimpleBooleanProperty(false);

    public SelectionInfo(String lawId, String elementId, ElementTypes elementType) {
        this.lawId = new SimpleStringProperty(lawId);
        this.elementId = new SimpleStringProperty(elementId);
        this.elementType = new SimpleObjectProperty<>(elementType);
    }

    public String getLawId() {
        return lawId.get();
    }

    public StringProperty lawIdProperty() {
        return lawId;
    }

    public void setLawId(String lawId) {
        this.lawId.set(lawId);
    }

    public String getElementId() {
        return elementId.get();
    }

    public StringProperty elementIdProperty() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId.set(elementId);
    }

    public ElementTypes getElementType() {
        return elementType.get();
    }

    public ObjectProperty<ElementTypes> elementTypeProperty() {
        return elementType;
    }

    public void setElementType(ElementTypes elementType) {
        this.elementType.set(elementType);
    }

    public Element getElement() {
        return element.get();
    }

    public ObjectProperty<Element> elementProperty() {
        return element;
    }

    public void setElement(Element element) {
        this.element.set(element);
    }

    @Override
    public String toString() {
        return "SelectionInfo{" +
                "lawId=" + lawId.get() +
                ", elementId=" + elementId.get() +
                ", elementType=" + elementType.get() +
                ", element=" + element.get() +
                '}';
    }

    public boolean isSaved() {
        return saved.get();
    }

    public BooleanProperty savedProperty() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved.set(saved);
    }
}
