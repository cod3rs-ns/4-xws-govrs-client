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
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.amendments.ElementPicker;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.domain.tree.SelectorTree;
import rs.acs.uns.sw.govrs.client.fx.domain.tree.TreeModel;
import rs.acs.uns.sw.govrs.client.fx.editor.preview.HtmlPreview;
import rs.acs.uns.sw.govrs.client.fx.rest.LawInputConverter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.container.ALAContainer;

import java.io.InputStream;

public class PopupPropertyElementPickerEditor implements PropertyEditor<ALAContainer> {

    private final Button btnEditor;
    private final PropertySheet.Item item;
    private final ObjectProperty<ALAContainer> value = new SimpleObjectProperty<>();

    private ElementPicker picker;
    private AnchorPane pickerPane;

    public PopupPropertyElementPickerEditor(PropertySheet.Item item) {
        this.item = item;
        ALAContainer cont = (ALAContainer)item.getValue();
        if (cont != null && !cont.currentElementId.equals("")) {
            btnEditor = new Button(cont.currentElementId);
            value.set((ALAContainer) item.getValue());
        } else {
            btnEditor = new Button("<nije_izabran>");
        }
        btnEditor.setAlignment(Pos.CENTER_LEFT);
        btnEditor.setOnAction((ActionEvent event) -> {
            displayPopupEditor();
        });

        ButtonPropertyItem bpi = (ButtonPropertyItem) item;
        bpi.property.addListener(observable -> {
            value.setValue(bpi.property.get());
            value.setValue(bpi.property.get());
            if (value.get().currentElementId != null) {
                btnEditor.setText(value.get().currentElementId);
            } else {
                btnEditor.setText("<nije izabran>");
            }
        });
    }

    private void displayPopupEditor() {

        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MainFXApp.class.getResource("/amendments/ElementPicker.fxml"));
        try (InputStream in = MainFXApp.class.getResourceAsStream("/amendments/ElementPicker.fxml")) {
            pickerPane = loader.load(in);
            picker = loader.getController();
            // create a RestClient to the specific URL
            RestClient restClient = RestClient.create()
                    .method("GET")
                    .host("http://localhost:9000/api")
                    .header("Accept", "application/xml")
                    .path("/laws/" + value.get().lawId);

            // retrieve a list from the DataProvider
            GluonObservableObject<Law> lawProperty;
            LawInputConverter converter = new LawInputConverter();
            lawProperty = DataProvider.retrieveObject(restClient.createObjectDataReader(converter));
            lawProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
                Law law = lawProperty.get();
                law.initElement();

                SelectorTree tree = new SelectorTree(
                        law,
                        Element::getChildren,
                        Element::elementNameProperty,
                        picker
                );

                TreeView<Element> treeView = tree.getTreeView();
                picker.treeContainer.setCenter(treeView);
            });

            Stage stage = new Stage();
            stage.setScene(new Scene(pickerPane));
            stage.setTitle("Izaberite predmet amandmana");
            stage.getIcons().add(
                    new Image(MainFXApp.class.getResource("/images/dialog.png").toString()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnEditor.getScene().getWindow());
            picker.chooseButton.setOnAction(event -> {
                stage.close();
                if(picker.getSelectedId() != null) {
                    btnEditor.setText(picker.getSelectedId());
                    value.get().currentElementId = picker.getSelectedId();
                }
            });
            stage.showAndWait();



        } catch (Exception e) {

        }


    }


    @Override
    public Node getEditor() {
        return btnEditor;
    }

    @Override
    public ALAContainer getValue() {
        return value.get();
    }

    @Override
    public void setValue(ALAContainer value) {
        this.value.set(value);
    }

}