package rs.acs.uns.sw.govrs.client.fx.editor.property_sheet;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;

import java.util.Optional;

public class StringPropertyItem implements PropertySheet.Item {
    private String category;
    private String name;
    private String description;
    private boolean editable;
    private StringProperty property;

    public StringPropertyItem(StringProperty property, String category, String name, String description, boolean editable) {
        this.property = property;
        this.category = category;
        this.description = description;
        this.name = name;
        this.editable = editable;
    }

    @Override
    public Class<?> getType() {
        return String.class;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Object getValue() {
        return property.get();
    }

    @Override
    public void setValue(Object value) {
        property.set(value.toString());
    }

    @Override
    public Optional<ObservableValue<? extends Object>> getObservableValue() {
        return Optional.empty();
    }

    @Override
    public boolean isEditable() {
        return editable;
    }
}
