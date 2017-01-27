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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Amendments;
import rs.acs.uns.sw.govrs.client.fx.util.DateUtils;

import java.io.File;
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

    public AmendmentItemController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void display(Amendments amendment, BorderPane preview) {
        this.amendment = amendment;
        previewPane = preview;
        idLabel.setText(amendment.getId());
        statusLabel.setText(amendment.getHead().getStatus().getValue());
        forLabel.setText(String.valueOf(amendment.getHead().getGlasovaZa().getValue()));
        againstLabel.setText(String.valueOf(amendment.getHead().getGlasovaProtiv().getValue()));
        neutralLabel.setText(String.valueOf(amendment.getHead().getGlasovaSuzdrzani().getValue()));
        Tooltip.install(withdrawButton, new Tooltip("Povucite amandman"));
        proposedLabel.setText(DateUtils.dateToString(amendment.getHead().getDatumPredloga().getValue().toGregorianCalendar().getTime()));
        votedLabel.setText(DateUtils.dateToString(amendment.getHead().getDatumPredloga().getValue().toGregorianCalendar().getTime()));
        nameLink.setText(amendment.getName());
        if (!"predložen".equals(amendment.getHead().getStatus().getValue())) {
            withdrawButton.setVisible(false);
        }
        if (!"sazvana".equals(RestClientProvider.getInstance().parliamentState.get())) {
            withdrawButton.setVisible(false);
        }
    }

    @FXML
    private void downloadRdf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Preuzmite RDF...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML files", "*.xml")
        );

        Stage stage = (Stage) previewPane.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            GluonObservableObject<Object> pdfProperty = RestClientProvider.getInstance().downloadPDFlaw(file.getPath(), idLabel.getText(), "amendments", "metadata/xml/", "application/octet-stream");
            pdfProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
                Notifications.create().owner(stage).title("XML").text("XML fajl je uspešno preuzet.").showConfirm();
            });
        }
    }

    @FXML
    private void downloadPdf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Preuzmite PDF...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF files", "*.pdf")
        );

        Stage stage = (Stage) previewPane.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            GluonObservableObject<Object> pdfProperty = RestClientProvider.getInstance().downloadPDFlaw(file.getPath(), idLabel.getText(), "amendments", "", "application/octet-stream");
            pdfProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
                Notifications.create().owner(stage).title("PDF").text("PDF fajl je uspešno preuzet.").showConfirm();
            });
        }
    }

    @FXML
    private void downloadHtml() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Preuzmite HTML...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("HTML files", "*.html")
        );

        Stage stage = (Stage) previewPane.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            GluonObservableObject<Object> pdfProperty = RestClientProvider.getInstance().downloadPDFlaw(file.getPath(), idLabel.getText(), "amendments", "", "application/xhtml+xml");
            pdfProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
                Notifications.create().owner(stage).title("HTML").text("HTML fajl je uspešno preuzet.").showConfirm();
            });
        }
    }

    @FXML
    private void downloadJson() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Preuzmite JSON...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON files", "*.json")
        );

        Stage stage = (Stage) previewPane.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            GluonObservableObject<Object> pdfProperty = RestClientProvider.getInstance().downloadPDFlaw(file.getPath(), idLabel.getText(), "amendments", "metadata/json/", "application/octet-stream");
            pdfProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
                Notifications.create().owner(stage).title("JSON").text("JSON fajl je uspešno preuzet.").showConfirm();
            });
        }
    }

    @FXML
    private void preview() {
        WebView webView = new WebView();
        previewPane.setCenter(webView);
        GluonObservableObject<String> htmlProperty = RestClientProvider.getInstance().getAmendmentHtml(idLabel.getText());
        htmlProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
            webView.getEngine().loadContent(htmlProperty.get());
            webView.requestLayout();
            webView.requestFocus();
        });
    }


    @FXML
    private void withdraw() {
        GluonObservableObject<Object> lawProperty = RestClientProvider.getInstance().withdrawAmendment(amendment.getId());
        lawProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
            Amendments l = (Amendments) lawProperty.get();
            Notifications.create().owner(withdrawButton.getScene().getWindow()).title("Amandman").text("Uspešno ste povukli amandman").showConfirm();
            withdrawButton.setVisible(false);
        });
    }

}
