package rs.acs.uns.sw.govrs.client.fx.editor.help;

import javafx.beans.property.*;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.util.ElementType;

public class PopupEditorOptions {
    private StringProperty parentIdBase = new SimpleStringProperty();
    private BooleanProperty createNew = new SimpleBooleanProperty(true);
    private ObjectProperty<Element> element = new SimpleObjectProperty<>();
    private BooleanProperty saved = new SimpleBooleanProperty(false);
    private ObjectProperty<ElementType> typeOfElement = new SimpleObjectProperty<>(ElementType.None);


    public PopupEditorOptions(String parentIdBase, boolean createNew, Element element, ElementType typeOfElement) {
        this.parentIdBase = new SimpleStringProperty(parentIdBase);
        this.createNew = new SimpleBooleanProperty(createNew);
        this.element = new SimpleObjectProperty<>(element);
        this.typeOfElement = new SimpleObjectProperty<>(typeOfElement);
    }

    public String getParentIdBase() {
        return parentIdBase.get();
    }

    public StringProperty parentIdBaseProperty() {
        return parentIdBase;
    }

    public void setParentIdBase(String parentIdBase) {
        this.parentIdBase.set(parentIdBase);
    }

    public boolean isCreateNew() {
        return createNew.get();
    }

    public BooleanProperty createNewProperty() {
        return createNew;
    }

    public void setCreateNew(boolean createNew) {
        this.createNew.set(createNew);
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

    public boolean isSaved() {
        return saved.get();
    }

    public BooleanProperty savedProperty() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved.set(saved);
    }

    public ElementType getTypeOfElement() {
        return typeOfElement.get();
    }

    public ObjectProperty<ElementType> typeOfElementProperty() {
        return typeOfElement;
    }

    public void setTypeOfElement(ElementType typeOfElement) {
        this.typeOfElement.set(typeOfElement);
    }

    @Override
    public String toString() {
        return "PopupEditorOptions{" +
                "parentIdBase=" + parentIdBase.get() +
                ", createNew=" + createNew.get() +
                ", element=" + element.get() +
                ", saved=" + saved.get() +
                ", typeOfElement=" + typeOfElement.get() +
                '}';
    }
}
