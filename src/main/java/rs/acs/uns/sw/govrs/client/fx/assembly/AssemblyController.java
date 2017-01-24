package rs.acs.uns.sw.govrs.client.fx.assembly;

import com.gluonhq.connect.GluonObservableObject;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.controlsfx.control.PropertySheet;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.amendments.AmendmentItemController;
import rs.acs.uns.sw.govrs.client.fx.amendments.AmendmentsController;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.manager.StateManager;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.*;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.SearchObject;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.SearchResult;
import rs.acs.uns.sw.govrs.client.fx.util.DateUtils;
import rs.acs.uns.sw.govrs.client.fx.util.IDUtils;

import java.io.IOException;
import java.io.InputStream;
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

    private ObservableList<Amendments> amendmentsList = FXCollections.observableArrayList();

    private StateManager stateManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lawsNameColumn.setCellValueFactory(cellData -> cellData.getValue().elementNameProperty());

        lawsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        amendmentsList.clear();
                        amendmentsContainer.getChildren().clear();

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
                        GluonObservableObject<SearchResult> amenmentsProperty = RestClientProvider.getInstance().getAmendmentsByLaw(selectedLaw.getId());
                        amenmentsProperty.initializedProperty().addListener((observable1, oldValue1, newValue1) -> {
                            System.out.println("amendmentsProperty");
                            SearchResult sr = amenmentsProperty.get();
                            if (sr.getSet() != null && sr.getSet().size() > 0) {
                                for (SearchObject so : sr.getSet()){
                                    GluonObservableObject<Object> aproperty = RestClientProvider.getInstance().getAmendments(IDUtils.extractId(so.getPath()));
                                    aproperty.initializedProperty().addListener((observable2, oldValue2, newValue2) -> {
                                        Amendments amen = (Amendments) aproperty.get();
                                        amendmentsList.add(amen);
                                        updateAmendments();
                                    });
                                }
                            }
                        });
                    }

                });
    }

    private void updateAmendments() {
        amendmentsContainer.getChildren().clear();
        for (Amendments amend: amendmentsList) {
            FXMLLoader loader = new FXMLLoader();
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(MainFXApp.class.getResource("/assembly/SingleAmendment.fxml"));
            try (InputStream in = MainFXApp.class.getResourceAsStream("/assembly/SingleAmendment.fxml")) {
                AnchorPane pane = loader.load(in);
                AmendmentVoteController controller = loader.getController();
                controller.display(amend,selectedLaw, previewContainer);
                amendmentsContainer.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
