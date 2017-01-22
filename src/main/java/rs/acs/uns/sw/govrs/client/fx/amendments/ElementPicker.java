package rs.acs.uns.sw.govrs.client.fx.amendments;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.util.ElementTypes;

import java.net.URL;
import java.util.ResourceBundle;


public class ElementPicker extends AnchorPane implements Initializable {
    @FXML
    public Label selectedLabel;
    @FXML
    public BorderPane treeContainer;
    @FXML
    public Button chooseButton;

    private String selectedId;
    private ElementTypes selectedType;
    private Element selectedElement;

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public String getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(String selectedId) {
        this.selectedId = selectedId;
    }

    public ElementTypes getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(ElementTypes selectedType) {
        this.selectedType = selectedType;
    }

    public Element getSelectedElement() {
        return selectedElement;
    }

    public void setSelectedElement(Element selectedElement) {
        this.selectedElement = selectedElement;
    }
}
