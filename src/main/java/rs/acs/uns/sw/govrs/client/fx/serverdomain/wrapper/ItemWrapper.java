package rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper;

import javafx.beans.property.StringProperty;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.StringPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Item;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.StringWrapper;
import rs.acs.uns.sw.govrs.client.fx.util.ElementType;
import rs.acs.uns.sw.govrs.client.fx.validation.ErrorMessage;

import java.util.List;


public class ItemWrapper extends Element {

    private Item wrappedItem;

    public ItemWrapper(Item item) {
        wrappedItem = new Item();
        wrappedItem.setValue("");
        wrappedItem.setElementName("Alineja");
        wrappedItem.setId("");
        this.elementContentProperty().set(wrappedItem.getValue());
    }

    @Override
    public String getElementName() {
        return wrappedItem.getElementName();
    }

    @Override
    public void setElementName(String name) {
        wrappedItem.setElementName(name);
    }

    @Override
    public StringProperty elementNameProperty() {
        return wrappedItem.elementNameProperty();
    }

    @Override
    public void setElementContent(String elementContent) {
        wrappedItem.setValue(elementContent);
    }

    @Override
    public String getElementContent() {
       return wrappedItem.getValue();
    }

    public String getId() {
        return wrappedItem.getId();
    }

    public void setId(String value) {
        wrappedItem.setId(value);
    }

    public StringProperty idProperty() {
        return wrappedItem.idProperty();
    }

    public Item getWrappedItem() {
        return wrappedItem;
    }

    public void setWrappedItem(Item wrappedItem) {
        this.wrappedItem = wrappedItem;
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
        // create property list for context
        StringPropertyItem idPropertyItem = new StringPropertyItem(
                idProperty(),
                "Generalno",
                "ID ",
                "Jedinstveni identifikator",
                false);
        StringPropertyItem namePropertyItem = new StringPropertyItem(
                elementNameProperty(),
                "Generalno",
                "Naziv",
                "Naziv elementa",
                true);
        getPropertyItems().add(idPropertyItem);
        getPropertyItems().add(namePropertyItem);
    }

    @Override
    public void preMarshaller() {

    }

    @Override
    public void validate(List<ErrorMessage> errorMessageList) {
        if (getElementContent() == null || "".equals(getElementContent()))
            errorMessageList.add(new ErrorMessage(getId(), getElementName(), ElementType.Item, "Sadržaj alineje je obavezan."));
    }
}
