package rs.acs.uns.sw.govrs.client.fx.laws;


import com.gluonhq.connect.GluonObservableObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.SearchObject;
import rs.acs.uns.sw.govrs.client.fx.util.DateUtils;

import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setInfo(LawSearchController controller, SearchObject searchObject) {
        System.out.println(searchObject);
        this.lawName.setText(searchObject.getPath());
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
        String link = lawName.getText();
        String [] links = link.split("/");
        String id = links[links.length-1];
        WebView webView = new WebView();
        parent.previewHtml.setCenter(webView);
        GluonObservableObject<String> htmlProperty = RestClientProvider.getInstance().getLawHtml(id);
        htmlProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
            webView.getEngine().loadContent(htmlProperty.get());
            webView.requestLayout();
            webView.requestFocus();
        });
    }
}
