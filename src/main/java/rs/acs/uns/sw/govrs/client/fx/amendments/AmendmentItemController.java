package rs.acs.uns.sw.govrs.client.fx.amendments;

import com.gluonhq.connect.GluonObservableObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Amendments;
import rs.acs.uns.sw.govrs.client.fx.util.DateUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class AmendmentItemController implements Initializable {
    @FXML
    private Label idLabel;
    @FXML
    private Label proposedLabel;
    @FXML
    private Label votedLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label forLabel;
    @FXML
    private Label againstLabel;
    @FXML
    private Label neutralLabel;
    @FXML
    private Hyperlink nameLink;
    @FXML
    private ImageView withdrawButton;

    @FXML
    private Amendments amendment;

    private BorderPane previewPane;

    public AmendmentItemController(Amendments amendment, BorderPane preview) {
        this.amendment = amendment;
        previewPane = preview;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idLabel.setText(amendment.getId());
        statusLabel.setText(amendment.getHead().getStatus().getValue());
        forLabel.setText(String.valueOf(amendment.getHead().getGlasovaZa().getValue()));
        againstLabel.setText(String.valueOf(amendment.getHead().getGlasovaProtiv().getValue()));
        neutralLabel.setText(String.valueOf(amendment.getHead().getGlasovaSuzdrzani().getValue()));
        Tooltip.install(withdrawButton, new Tooltip("Povucite amandman"));
        proposedLabel.setText(DateUtils.dateToString(amendment.getHead().getDatumPredloga().getValue().toGregorianCalendar().getTime()));
        votedLabel.setText(DateUtils.dateToString(amendment.getHead().getDatumPredloga().getValue().toGregorianCalendar().getTime()));
        votedLabel.setText(amendment.getName());
    }

    @FXML
    private void downloadRdf() {

    }

    @FXML
    private void downloadPdf() {

    }

    @FXML
    private void downloadHtml() {

    }

    @FXML
    private void downloadJson() {

    }

    @FXML
    private void preview () {
        WebView webView = new WebView();
        previewPane.setCenter(webView);
        GluonObservableObject<String> htmlProperty = RestClientProvider.getInstance().getLawHtml(idLabel.getText());
        htmlProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
            webView.getEngine().loadContent(htmlProperty.get());
            webView.requestLayout();
            webView.requestFocus();
        });
    }

    @FXML
    private void withdraw() {

    }

}
