package rs.acs.uns.sw.govrs.client.fx.editor.property_sheet;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;
import rs.acs.uns.sw.govrs.client.fx.editor.help.PopupEditorInit;

import java.util.Optional;

public class PopupButtonPropertyItem implements PropertySheet.Item {
    private String category;
    private String name;
    private String description;
    private boolean editable;
    public ObjectProperty<PopupEditorInit> property;

    public PopupButtonPropertyItem(ObjectProperty<PopupEditorInit> property, String category, String name, String description, boolean editable) {
        this.property = property;
        this.category = category;
        this.description = description;
        this.name = name;
        this.editable = editable;
    }

    @Override
    public Class<?> getType() {
        return Integer.class;
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
        System.out.println("property");
        property.set((PopupEditorInit) value);
    }

    @Override
    public Optional<ObservableValue<? extends Object>> getObservableValue() {
        return Optional.of(property);
    }

    @Override
    public Optional<Class<? extends PropertyEditor<?>>> getPropertyEditorClass() {
        return Optional.of(PopupPropertyElementEditorEditor.class);
    }

    @Override
    public boolean isEditable() {
        return editable;
    }
}
