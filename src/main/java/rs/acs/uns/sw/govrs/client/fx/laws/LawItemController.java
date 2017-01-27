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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;
import rs.acs.uns.sw.govrs.client.fx.util.DateUtils;

import java.io.File;
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Preuzmite RDF...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML files", "*.xml")
        );

        Stage stage = (Stage) previewPane.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            GluonObservableObject<Object> pdfProperty = RestClientProvider.getInstance().downloadPDFlaw(file.getPath(), idLabel.getText(), "laws", "metadata/xml/", "application/octet-stream");
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
            GluonObservableObject<Object> pdfProperty = RestClientProvider.getInstance().downloadPDFlaw(file.getPath(), idLabel.getText(), "laws", "", "application/octet-stream");
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
            GluonObservableObject<Object> pdfProperty = RestClientProvider.getInstance().downloadPDFlaw(file.getPath(), idLabel.getText(), "laws", "", "application/xhtml+xml");
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
            GluonObservableObject<Object> pdfProperty = RestClientProvider.getInstance().downloadPDFlaw(file.getPath(), idLabel.getText(), "laws", "metadata/json/", "application/octet-stream");
            pdfProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
                Notifications.create().owner(stage).title("JSON").text("JSON fajl je uspešno preuzet.").showConfirm();
            });
        }
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
