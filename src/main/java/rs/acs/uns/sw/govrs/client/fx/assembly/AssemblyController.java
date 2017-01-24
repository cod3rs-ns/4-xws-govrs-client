package rs.acs.uns.sw.govrs.client.fx.assembly;

import com.gluonhq.connect.GluonObservableObject;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.controlsfx.control.PropertySheet;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.manager.StateManager;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.AppUser;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Parliament;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Ref;
import rs.acs.uns.sw.govrs.client.fx.util.DateUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class AssemblyController implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private VBox amendmentsContainer;
    @FXML
    private TableView<Law> lawsTable;
    @FXML
    private TableColumn<Law, String> lawsNameColumn;
    @FXML
    private BorderPane previewContainer;

    private Law selectedLaw;

    private Parliament parliament;

    private StateManager stateManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lawsNameColumn.setCellValueFactory(cellData -> cellData.getValue().elementNameProperty());

        lawsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedLaw = newValue;
                        nameLabel.setText(newValue.getElementName());
                        idLabel.setText(newValue.idProperty().get());
                        dateLabel.setText(DateUtils.dateToString((selectedLaw.getHead().getDatumPredloga().getValue().toGregorianCalendar().getTime())));
                        GluonObservableObject<Object> userProp = RestClientProvider.getInstance().getUser(selectedLaw.getHead().getPodnosilac().getRef().getId());
                        System.out.println(selectedLaw.getHead().getPodnosilac().getRef().getId());
                        userProp.initializedProperty().addListener((observable1, oldValue1, newValue1) -> {

                            AppUser podnosilac = (AppUser) userProp.get();
                            authorLabel.setText(podnosilac.getIme() + " " + podnosilac.getPrezime());
                        });
                    }

                });
    }
    ObservableList<GluonObservableObject<Law>> observableObjects = FXCollections.observableArrayList();
    public void loadTestData() {
        System.out.println("Loading");
        GluonObservableObject<Object> assemblyProperty = RestClientProvider.getInstance().getParliament();
        assemblyProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Parliament Initialized");
            parliament = (Parliament)assemblyProperty.get();
            for (Ref r : parliament.getBody().getAkti().getRef()) {
                if ("law".equals(r.getType())){
                    System.out.println(r.getId());
                    observableObjects.add(RestClientProvider.getInstance().getLaw(r.getId()));
                }
            }
            for (GluonObservableObject<Law> goo: observableObjects) {
                goo.initializedProperty().addListener((observable1, oldValue1, newValue1) -> {
                    Law l = goo.get();
                    parliament.laws.add(l);
                    lawsTable.setItems(parliament.laws);
                });
            }
        });
    }

    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @FXML
    private void previewLaw() {
        WebView webView = new WebView();
        previewContainer.setCenter(webView);
        GluonObservableObject<String> htmlProperty = RestClientProvider.getInstance().getLawHtml(selectedLaw.getId());
        htmlProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
            webView.getEngine().loadContent(htmlProperty.get());
            webView.requestLayout();
            webView.requestFocus();
        });
    }
}
