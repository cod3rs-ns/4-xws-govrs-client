package rs.acs.uns.sw.govrs.client.fx.assembly;


import com.gluonhq.connect.GluonObservableObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import org.controlsfx.control.Notifications;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.*;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.DocumentStatus;
import rs.acs.uns.sw.govrs.client.fx.util.DateUtils;

public class AmendmentVoteController {
    @FXML
    private Label dateLabel;
    @FXML
    private Label authorNameLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label votesForLabel;
    @FXML
    private Label votesAgainstLabel;
    @FXML
    private Label votesNeutralLabel;
    @FXML
    private Slider forSlider;
    @FXML
    private Slider neutralSlider;
    @FXML
    private Slider againstSlider;
    @FXML
    private Button voteButton;
    @FXML
    private Label statusLabel;

    private AssemblyController assemblyController;

    private Amendments amendment;
    private Law law;
    private BorderPane previewContainer;

    public void display(Amendments amen, Law parent, BorderPane preview) {
        amendment = amen;
        law = parent;
        previewContainer = preview;
        idLabel.setText(amendment.getId());
        votesForLabel.setText(String.valueOf(amendment.getHead().getGlasovaZa().getValue()));
        votesAgainstLabel.setText(String.valueOf(amendment.getHead().getGlasovaProtiv().getValue()));
        votesNeutralLabel.setText(String.valueOf(amendment.getHead().getGlasovaSuzdrzani().getValue()));
        neutralSlider.setValue(amendment.getHead().getGlasovaSuzdrzani().getValue());
        forSlider.setValue(amendment.getHead().getGlasovaZa().getValue());
        againstSlider.setValue(amendment.getHead().getGlasovaProtiv().getValue());
        statusLabel.setText(amendment.getHead().getStatus().getValue());
        dateLabel.setText(DateUtils.dateToString(amendment.getHead().getDatumPredloga().getValue().toGregorianCalendar().getTime()));
        nameLabel.setText(amendment.getName());
        GluonObservableObject<Object> userProp = RestClientProvider.getInstance().getUser(amendment.getHead().getPodnosilac().getRef().getId());
        System.out.println(amendment.getHead().getPodnosilac().getRef().getId());
        userProp.initializedProperty().addListener((observable1, oldValue1, newValue1) -> {
            AppUser podnosilac = (AppUser) userProp.get();
            authorNameLabel.setText(podnosilac.getIme() + " " + podnosilac.getPrezime());
        });

        forSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            votesForLabel.setText(String.valueOf(Integer.valueOf(newValue.intValue())));
        });
        againstSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            votesAgainstLabel.setText(String.valueOf(Integer.valueOf(newValue.intValue())));
        });
        neutralSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            votesNeutralLabel.setText(String.valueOf(Integer.valueOf(newValue.intValue())));
        });

        if (!DocumentStatus.Predlozen.toString().equals(amendment.getHead().getStatus().getValue())) {
            voteButton.setDisable(true);
            forSlider.setDisable(true);
            againstSlider.setDisable(true);
            neutralSlider.setDisable(true);
        }

        amendment.getHead().getStatus().valueProperty().addListener((observable, oldValue, newValue) -> {
            if(!DocumentStatus.Predlozen.toString().equals(amendment.getHead().getStatus().getValue())) {
                voteButton.setDisable(true);
                forSlider.setDisable(true);
                againstSlider.setDisable(true);
                neutralSlider.setDisable(true);
            }
        });

    }

    @FXML
    private void voteForAmendment() {
        VotingObject vo = new VotingObject();
        vo.setVotesAgainst((int)againstSlider.getValue());
        vo.setVotesFor((int)forSlider.getValue());
        vo.setVotesNeutral((int)neutralSlider.getValue());
        GluonObservableObject<Object> updateProperty = RestClientProvider.getInstance().updateAmandmentsVotes(vo, idLabel.getText());
        updateProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
            Amendments a = (Amendments)updateProperty.get();
            amendment.getHead().getStatus().valueProperty().set(a.getHead().getStatus().valueProperty().get());
            Notifications.create().owner(forSlider.getScene().getWindow()).title("Glasanje").text("Uspešno ste glasali o Amandmanu.").showInformation();
        });
        assemblyController.loadTestData();
        assemblyController.refresh();
    }

    @FXML
    private void preview() {
        WebView webView = new WebView();
        previewContainer.setCenter(webView);
        GluonObservableObject<String> htmlProperty = RestClientProvider.getInstance().getAmendmentHtml(idLabel.getText());
        htmlProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
            webView.getEngine().loadContent(htmlProperty.get());
            webView.requestLayout();
            webView.requestFocus();
        });
    }

    public AssemblyController getAssemblyController() {
        return assemblyController;
    }

    public void setAssemblyController(AssemblyController assemblyController) {
        this.assemblyController = assemblyController;
    }
}
