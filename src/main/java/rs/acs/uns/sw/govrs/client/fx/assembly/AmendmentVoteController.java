package rs.acs.uns.sw.govrs.client.fx.assembly;


import com.gluonhq.connect.GluonObservableObject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Amendments;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.AppUser;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;
import rs.acs.uns.sw.govrs.client.fx.util.DateUtils;
import rs.acs.uns.sw.govrs.client.fx.util.StringCleaner;

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

    }

    @FXML
    private void voteForAmendment() {

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
}
