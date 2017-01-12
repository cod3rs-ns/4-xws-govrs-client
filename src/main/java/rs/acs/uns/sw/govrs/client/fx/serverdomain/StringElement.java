package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;


public class StringElement extends Element{
    protected StringProperty name = new SimpleStringProperty("Tekst");

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
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
    public void setName(String value) {
        this.name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringElement(String content) {
        super();
        setElementContent(content);
    }

    @Override
    public void initChildrenObservableList() {

    }


    @Override
    public void createAndAddChild(Element element) {

    }

    @Override
    public String createElementOpening() {
        return null;
    }

    @Override
    public String createElementAttrs() {
        return null;
    }

    @Override
    public String createElementContent() {
        return null;
    }

    @Override
    public String createElementClosing() {
        return null;
    }
}
