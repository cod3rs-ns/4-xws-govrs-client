package rs.acs.uns.sw.govrs.client.fx.editor.property_sheet;

import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.domain.tree.SelectorTree;
import rs.acs.uns.sw.govrs.client.fx.editor.PopupEditorController;
import rs.acs.uns.sw.govrs.client.fx.editor.help.PopupEditorInit;
import rs.acs.uns.sw.govrs.client.fx.rest.LawInputConverter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;

import java.io.InputStream;

public class PopupPropertyElementEditorEditor implements PropertyEditor<PopupEditorInit> {

    private final Button btnEditor;
    private final PropertySheet.Item item;
    private final ObjectProperty<PopupEditorInit> value = new SimpleObjectProperty<>();

    private PopupEditorController controller;
    private AnchorPane rootPane;

    public PopupPropertyElementEditorEditor(PropertySheet.Item item) {
        this.item = item;
        PopupEditorInit cont = (PopupEditorInit)item.getValue();
        if (cont != null && !cont.isCreateNew() ) {
            btnEditor = new Button(cont.getElement().getElementName());
            value.set((PopupEditorInit) item.getValue());
        } else {
            btnEditor = new Button("<nije izabran>");
        }
        btnEditor.setAlignment(Pos.CENTER_LEFT);
        btnEditor.setOnAction((ActionEvent event) -> {
            displayPopupEditor();
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
            /*
            SelectorTree tree = new SelectorTree(
                    law,
                    Element::getChildren,
                    Element::elementNameProperty,
                    controller
            );

            TreeView<Element> treeView = tree.getTreeView();
            controller.treeContainer.setCenter(treeView);
            });
            */

            Stage stage = new Stage();
            stage.setScene(new Scene(rootPane));
            stage.setTitle("Izaberite predmet amandmana");
            stage.getIcons().add(
                    new Image(MainFXApp.class.getResource("/images/dialog.png").toString()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(btnEditor.getScene().getWindow());

            stage.showAndWait();

        } catch (Exception e) {

        }


    }


    @Override
    public Node getEditor() {
        return btnEditor;
    }

    @Override
    public PopupEditorInit getValue() {
        return value.get();
    }

    @Override
    public void setValue(PopupEditorInit value) {
        System.out.println(value.toString());
    }

}