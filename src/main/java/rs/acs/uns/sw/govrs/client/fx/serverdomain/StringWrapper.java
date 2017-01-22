package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.StringPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.util.StringCleaner;


public class StringWrapper extends Element{
    protected StringProperty name = new SimpleStringProperty("Tekst");

    /**
     * Reference to original Object from parent list List<Object> content.
     * Used for switching object in-place when content of this changes.
     */
    private Object wrappedObject;

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getElementName() {
        return name.get();
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setElementName(String value) {
        this.name.set(value);
    }

    public StringProperty elementNameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        this.name.set(value);
    }

    public StringWrapper(Object obj) {
        super();
        // remove unnecessary whitespace
        String cleaned = StringCleaner.deleteWhitespace(obj.toString());
        setElementContent(cleaned);
        wrappedObject = obj;
    }

    public Object getWrappedObject() {
        return wrappedObject;
    }

    public void setWrappedObject(Object wrappedObject) {
        this.wrappedObject = wrappedObject;
    }

    @Override
    public void initElement() {
        createPropertyAttrs();
    }


    @Override
    public void createAndAddChild(Element element) {

    }

    @Override
    public void removeChild(Element element) {

    }

    @Override
    public void createPropertyAttrs() {
        StringPropertyItem namePropertyItem = new StringPropertyItem(
                elementNameProperty(),
                "Generalno",
                "Naziv",
                "Naziv elementa",
                true);
        getPropertyItems().add(namePropertyItem);
    }

    @Override
    public void preMarshaller() {

    }

    @Override
    public StringProperty idProperty() {
        return new SimpleStringProperty("text");
    }

}
