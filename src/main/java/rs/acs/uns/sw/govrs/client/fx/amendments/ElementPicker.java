package rs.acs.uns.sw.govrs.client.fx.amendments;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

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
}
