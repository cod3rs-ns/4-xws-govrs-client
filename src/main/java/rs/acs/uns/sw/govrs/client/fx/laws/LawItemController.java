package rs.acs.uns.sw.govrs.client.fx.laws;

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
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;
import rs.acs.uns.sw.govrs.client.fx.util.DateUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class LawItemController implements Initializable {
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
    private Law law;

    @FXML
    private BorderPane previewPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void display(Law law, BorderPane preview) {
        this.law = law;
        previewPane = preview;
        idLabel.setText(law.getId());
        statusLabel.setText(law.getHead().getStatus().getValue());
        forLabel.setText(String.valueOf(law.getHead().getGlasovaZa().getValue()));
        againstLabel.setText(String.valueOf(law.getHead().getGlasovaProtiv().getValue()));
        neutralLabel.setText(String.valueOf(law.getHead().getGlasovaSuzdrzani().getValue()));
        Tooltip.install(withdrawButton, new Tooltip("Povucite propis"));
        proposedLabel.setText(DateUtils.dateToString(law.getHead().getDatumPredloga().getValue().toGregorianCalendar().getTime()));
        votedLabel.setText(DateUtils.dateToString(law.getHead().getDatumPredloga().getValue().toGregorianCalendar().getTime()));
        nameLink.setText(law.getName());
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
    private void withdraw() {

    }

    @FXML
    private void preview() {
        WebView webView = new WebView();
        previewPane.setCenter(webView);
        GluonObservableObject<String> htmlProperty = RestClientProvider.getInstance().getLawHtml(idLabel.getText());
        htmlProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
            webView.getEngine().loadContent(htmlProperty.get());
            webView.requestLayout();
            webView.requestFocus();
        });
    }
}
