package rs.acs.uns.sw.govrs.client.fx.assembly;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import rs.acs.uns.sw.govrs.client.fx.manager.StateManager;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Parliament;

import java.net.URL;
import java.util.ResourceBundle;

public class AssemblyController implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private VBox amendmentsContainer;
    @FXML
    private TableView<Law> lawsTable;
    @FXML
    private TableColumn<String, Law> lawsNameColumn;

    private Parliament parliament;

    private StateManager stateManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void loadTestData() {

    }

    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }
}
