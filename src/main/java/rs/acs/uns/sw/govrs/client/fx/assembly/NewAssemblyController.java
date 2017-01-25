package rs.acs.uns.sw.govrs.client.fx.assembly;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class NewAssemblyController extends AnchorPane implements Initializable{
    @FXML
    public DatePicker datePicker;
    @FXML
    public TextField place;
    @FXML
    public Button saveButton;
    @FXML
    public Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

