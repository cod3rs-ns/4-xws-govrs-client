package rs.acs.uns.sw.govrs.client.fx.editor.property_sheet;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.editor.PopupEditorController;
import rs.acs.uns.sw.govrs.client.fx.editor.help.PopupEditorOptions;

import java.io.InputStream;

public class PopupPropertyElementEditorEditor implements PropertyEditor<PopupEditorOptions> {

    private final Button btnEditor;
    private final PropertySheet.Item item;
    private final ObjectProperty<PopupEditorOptions> value = new SimpleObjectProperty<>();

    private PopupEditorController controller;
    private AnchorPane rootPane;

    public PopupPropertyElementEditorEditor(PropertySheet.Item item) {
        this.item = item;
        PopupEditorOptions cont = (PopupEditorOptions)item.getValue();
        if (!cont.isCreateNew() ) {
            btnEditor = new Button(cont.getElement().getElementName());
            value.set((PopupEditorOptions) item.getValue());
        } else {
            btnEditor = new Button("<nije izabran>");
        }
        btnEditor.setAlignment(Pos.CENTER_LEFT);
        btnEditor.setOnAction((ActionEvent event) -> {
            displayPopupEditor();
        });

        PopupButtonPropertyItem pbpi = (PopupButtonPropertyItem) item;
        pbpi.property.addListener(observable -> {
            value.set(pbpi.property.get());
            if (!value.get().isCreateNew() ) {
                btnEditor.setText(value.get().getElement().getElementName());
            } else {
                btnEditor.setText("<nije izabran>");
            }
        });
    }

    private void displayPopupEditor() {
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MainFXApp.class.getResource("/editor/EditorPopup.fxml"));
        try (InputStream in = MainFXApp.class.getResourceAsStream("/editor/EditorPopup.fxml")) {
            rootPane = loader.load(in);
            controller = loader.getController();
            controller.initElements(getValue());
            Stage stage = new Stage();
            stage.setScene(new Scene(rootPane));
            stage.setTitle("Izaberite predmet amandmana");
            stage.getIcons().add(
                    new Image(MainFXApp.class.getResource("/images/dialog.png").toString()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(btnEditor.getScene().getWindow());
            stage.showAndWait();

            //getValue().setElement(controller.getRootElement());
            if (getValue().getElement() != null) {
                getValue().setCreateNew(false);
            }
            btnEditor.setText("<" + getValue().getTypeOfElement() + "> " + getValue().getElement().getElementName() + " (" + getValue().getElement().idProperty().get() + ")" );
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public Node getEditor() {
        return btnEditor;
    }

    @Override
    public PopupEditorOptions getValue() {
        return value.get();
    }

    @Override
    public void setValue(PopupEditorOptions value) {
        this.value.set(value);
        if (!value.isCreateNew() ) {
            btnEditor.setText(value.getElement().getElementName());
        } else {
            btnEditor.setText("<nije izabran>");
        }
    }

}