package rs.acs.uns.sw.govrs.client.fx.laws;


import com.gluonhq.connect.GluonObservableObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.controlsfx.control.Notifications;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.SearchObject;
import rs.acs.uns.sw.govrs.client.fx.util.DateUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleLaw extends AnchorPane implements Initializable {
    @FXML
    private Hyperlink lawName;
    @FXML
    private Label lawStatus;
    @FXML
    private Label proposedDate;
    @FXML
    private Label votingDate;
    @FXML
    private Label forLabel;
    @FXML
    private Label againstLabel;
    @FXML
    private Label neutralLabel;
    @FXML
    private TextArea lawPreview;
    @FXML
    private LawSearchController parent;
    @FXML
    private ImageView pdfButton;
    @FXML
    private ImageView htmlButton;
    @FXML
    private ImageView rdfButton;
    @FXML
    private ImageView jsonButton;

    private String id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(pdfButton, new Tooltip("Preuzmite PDF"));
        Tooltip.install(htmlButton, new Tooltip("Preuzmite HTML"));
        Tooltip.install(rdfButton, new Tooltip("Preuzmite RDF"));
        Tooltip.install(jsonButton, new Tooltip("Preuzmite JSON"));
    }

    public void setInfo(LawSearchController controller, SearchObject searchObject) {
        this.lawName.setText(searchObject.getPath());
        // parse id
        String link = lawName.getText();
        String [] links = link.split("/");
        id = links[links.length-1];

        if (!"".equals(searchObject.getPreview())) {
            this.lawPreview.setText(searchObject.getPreview());
        }
        if (searchObject.getMetadata() != null) {

            if (!searchObject.getMetadata().getStatus().equals("")) {
                this.lawStatus.setText(searchObject.getMetadata().getStatus());
            }

            if (searchObject.getMetadata().getDateOfVoting() != null) {
                votingDate.setText(DateUtils.dateToString(searchObject.getMetadata().getDateOfVoting()));
            }
            if (searchObject.getMetadata().getDateOfProposal() != null) {
                proposedDate.setText(DateUtils.dateToString(searchObject.getMetadata().getDateOfProposal()));
            }

            if (searchObject.getMetadata().getVotesAgainst() != -1) {
                againstLabel.setText(searchObject.getMetadata().getVotesAgainst().toString());
            }
            if (searchObject.getMetadata().getVotesFor() != -1) {
                forLabel.setText(searchObject.getMetadata().getVotesFor().toString());
            }
            if (searchObject.getMetadata().getVotesNeutral() != -1) {
                neutralLabel.setText(searchObject.getMetadata().getVotesNeutral().toString());
            }
        }
        this.parent = controller;
    }

    @FXML
    public void previewHtml() {
        WebView webView = new WebView();
        parent.previewHtml.setCenter(webView);
        GluonObservableObject<String> htmlProperty = RestClientProvider.getInstance().getLawHtml(id);
        htmlProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
            webView.getEngine().loadContent(htmlProperty.get());
            webView.requestLayout();
            webView.requestFocus();
        });
    }

    @FXML
    private void downloadRdf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Preuzmite RDF...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML files", "*.xml")
        );

        Stage stage = (Stage) pdfButton.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            GluonObservableObject<Object> pdfProperty = RestClientProvider.getInstance().downloadPDFlaw(file.getPath(), id, "laws", "metadata/xml/", "application/octet-stream");
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

        Stage stage = (Stage) pdfButton.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            GluonObservableObject<Object> pdfProperty = RestClientProvider.getInstance().downloadPDFlaw(file.getPath(), id, "laws", "", "application/octet-stream");
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

        Stage stage = (Stage) pdfButton.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            GluonObservableObject<Object> pdfProperty = RestClientProvider.getInstance().downloadPDFlaw(file.getPath(), id, "laws", "", "application/xhtml+xml");
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

        Stage stage = (Stage) pdfButton.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            GluonObservableObject<Object> pdfProperty = RestClientProvider.getInstance().downloadPDFlaw(file.getPath(), id, "laws", "metadata/json/", "application/octet-stream");
            pdfProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
                Notifications.create().owner(stage).title("JSON").text("JSON fajl je uspešno preuzet.").showConfirm();
            });
        }
    }
}
