package rs.acs.uns.sw.govrs.client.fx.review;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import rs.acs.uns.sw.govrs.client.fx.manager.StateManager;

import java.net.URL;
import java.util.ResourceBundle;

public class OverviewController implements Initializable {
    @FXML
    private VBox lawsContainer;
    @FXML
    private VBox amendmentsContainer;
    @FXML
    private BorderPane previewContainer;

    private StateManager stateManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lawsContainer.setSpacing(5);
        amendmentsContainer.setSpacing(5);
        // TODO retrieve objects
    }

    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }
}
