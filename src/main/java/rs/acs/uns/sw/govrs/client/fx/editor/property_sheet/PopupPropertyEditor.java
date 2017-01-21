package rs.acs.uns.sw.govrs.client.fx.editor.property_sheet;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;

public class PopupPropertyEditor implements PropertyEditor<Element> {

    private final Button btnEditor;
    private final PropertySheet.Item item;
    private final ObjectProperty<Element> value = new SimpleObjectProperty<>();

    public PopupPropertyEditor(PropertySheet.Item item) {
        this.item = item;
        if (item.getValue() != null) {
            btnEditor = new Button(item.getValue().toString());
            value.set((Element) item.getValue());
        } else {
            btnEditor = new Button("<empty>");
        }
        btnEditor.setAlignment(Pos.CENTER_LEFT);
        btnEditor.setOnAction((ActionEvent event) -> {
            displayPopupEditor();
        });
    }

    private void displayPopupEditor() {

        ElementPicker ep = new ElementPicker();
        Stage stage = new Stage();
        stage.setScene(new Scene(ep));
        stage.setTitle("My modal window");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(btnEditor.getScene().getWindow());
        stage.showAndWait();

        btnEditor.setText(ep.returnValue);

    }


    @Override
    public Node getEditor() {
        return btnEditor;
    }

    @Override
    public Element getValue() {
        return value.get();
    }

    @Override
    public void setValue(Element value) {
        System.out.println(value.toString());
    }

    private class ElementPicker extends BorderPane {
        public String returnValue = "HAHAHA";

        public ElementPicker() {
            Label l = new Label("AAAA");
            setCenter(l);
        }
    }

}