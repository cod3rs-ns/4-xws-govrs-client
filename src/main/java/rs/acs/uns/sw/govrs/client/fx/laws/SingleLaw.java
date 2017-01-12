package rs.acs.uns.sw.govrs.client.fx.laws;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleLaw extends AnchorPane implements Initializable{
    @FXML
    private Hyperlink lawName;
    @FXML
    private Label lawStatus;
    @FXML
    private Label authorName;
    @FXML
    private Label lawDate;
    @FXML
    private TextArea lawPreview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setInfo(String lawName, String lawStatus, String authorName, String lawDate, String lawPreview) {
        this.lawName.setText(lawName);
        this.lawDate.setText(lawDate);
        this.authorName.setText(authorName);
        this.lawStatus.setText(lawStatus);
        this.lawPreview.setText(lawPreview);
    }
}
