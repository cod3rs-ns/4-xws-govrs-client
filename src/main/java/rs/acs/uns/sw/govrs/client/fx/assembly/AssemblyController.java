package rs.acs.uns.sw.govrs.client.fx.assembly;

import com.gluonhq.connect.GluonObservableObject;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PropertySheet;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.amendments.AmendmentItemController;
import rs.acs.uns.sw.govrs.client.fx.amendments.AmendmentsController;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.manager.StateManager;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.*;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.DocumentStatus;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.SearchObject;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.SearchResult;
import rs.acs.uns.sw.govrs.client.fx.util.DateUtils;
import rs.acs.uns.sw.govrs.client.fx.util.IDUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;
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
    @FXML
    private Slider forSlider;
    @FXML
    private Slider againstSlider;
    @FXML
    private Slider neutralSlider;
    @FXML
    private Label votesForLabel;
    @FXML
    private Label votesAgainstLabel;
    @FXML
    private Label votesNeutralLabel;

    @FXML
    private Button voteButton;
    @FXML
    private TitledPane lawVotePane;
    @FXML
    private Label stateLabel;
    @FXML
    private Label placeLabel;
    @FXML
    private Label parliamentDateLabel;
    @FXML
    private HBox buttonContainer;
    private Button finish = new Button("Zatvori");

    @FXML
    private TitledPane lawTable;
    @FXML
    private TitledPane amendmentsContainerPane;
    @FXML
    private TitledPane lawInfoPane;
    @FXML
    private TitledPane prevTitle;
    @FXML
    private Label lawStatusLabel;

    private Law selectedLaw;

    private Parliament parliament;

    private ObservableList<Amendments> amendmentsList = FXCollections.observableArrayList();

    private StateManager stateManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lawVotePane.setDisable(true);
        RestClientProvider.getInstance().parliamentState.addListener((observable, oldValue, newValue) -> {
            setParliamentState();
        });

        finish.setOnAction(event -> {
            finishParliament();
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

        stateLabel.setText(RestClientProvider.getInstance().getActiveParliament().getHead().getStatus());
        placeLabel.setText(RestClientProvider.getInstance().getActiveParliament().getHead().getMjestoOdrzavanja());
        parliamentDateLabel.setText(DateUtils.dateToString(RestClientProvider.getInstance().getActiveParliament().getHead().getDatumOdrzavanja().toGregorianCalendar().getTime()));
        lawsNameColumn.setCellValueFactory(cellData -> cellData.getValue().elementNameProperty());
        lawVotePane.setDisable(true);
        lawsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        amendmentsList.clear();
                        amendmentsContainer.getChildren().clear();

                        selectedLaw = newValue;
                        nameLabel.setText(newValue.getElementName());
                        idLabel.setText(newValue.idProperty().get());
                        lawStatusLabel.setText(newValue.getHead().getStatus().getValue());
                        dateLabel.setText(DateUtils.dateToString((selectedLaw.getHead().getDatumPredloga().getValue().toGregorianCalendar().getTime())));
                        if (!"predložen".equals(selectedLaw.getHead().getStatus().getValue())) {
                            lawVotePane.setDisable(true);
                            forSlider.setValue((int)selectedLaw.getHead().getGlasovaZa().getValue());
                            againstSlider.setValue((int)selectedLaw.getHead().getGlasovaProtiv().getValue());
                            neutralSlider.setValue((int)selectedLaw.getHead().getGlasovaSuzdrzani().getValue());

                        }
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
                            amendmentsList.clear();
                            amendmentsContainer.getChildren().clear();
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
        amendmentsList.addListener(new ListChangeListener<Amendments>() {
            @Override
            public void onChanged(Change<? extends Amendments> c) {
                System.out.println("Change");
                lawVotePane.setDisable(false);
                for (Amendments a: amendmentsList) {
                    if (a.getHead().getStatus().getValue().equals(DocumentStatus.Predlozen.toString())) {
                        System.out.println("True");
                        lawVotePane.setDisable(true);
                    }
                }
                if (!"predložen".equals(selectedLaw.getHead().getStatus().getValue())) {
                    lawVotePane.setDisable(true);
                    forSlider.setValue((int)selectedLaw.getHead().getGlasovaZa().getValue());
                    againstSlider.setValue((int)selectedLaw.getHead().getGlasovaProtiv().getValue());
                    neutralSlider.setValue((int)selectedLaw.getHead().getGlasovaSuzdrzani().getValue());

                }
            }
        });
        RestClientProvider.getInstance().parliamentState.addListener((observable, oldValue, newValue) -> {
            setParliamentState();
        });
        setParliamentState();
        lawVotePane.setDisable(true);
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

    private void finishParliament() {
        boolean invalid = false;
        for (Law law : laws) {
            if ("predložen".equals(law.getHead().getStatus().getValue())){
                invalid = true;
            }
        }
        if (invalid) {
            Notifications.create().owner(amendmentsContainer.getScene().getWindow()).title("Upozorenje!").text("Postoje Propisi čiji status nije razrešen!").showWarning();
        } else {
            RestClientProvider.getInstance().changeParliamentStatus("završena");
        }
    }

    ObservableList<GluonObservableObject<Law>> observableObjects = FXCollections.observableArrayList();
    ObservableList<Law> laws = FXCollections.observableArrayList();
    public void loadTestData() {
        observableObjects.clear();
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
            laws.clear();
            for (GluonObservableObject<Law> goo: observableObjects) {
                goo.initializedProperty().addListener((observable1, oldValue1, newValue1) -> {
                    Law l = goo.get();
                    laws.add(l);
                    lawsTable.setItems(laws);
                });
            }
        });
    }

    @FXML
    private void vote() {
        VotingObject vo = new VotingObject();
        vo.setVotesAgainst((int)againstSlider.getValue());
        vo.setVotesFor((int)forSlider.getValue());
        vo.setVotesNeutral((int)neutralSlider.getValue());
        GluonObservableObject<Object> updateProperty = RestClientProvider.getInstance().updateLawVotes(vo, selectedLaw.getId());
        updateProperty.initializedProperty().addListener((observable, oldValue, newValue) -> {
            Law law = (Law) updateProperty.get();
            law.getHead().setStatus(law.getHead().getStatus());
            if ("prihvaćen".equals(law.getHead().getStatus().getValue())) {
                System.out.println("Huehuehuhehuehueh");
                for (Amendments a: amendmentsList) {
                    GluonObservableObject patchProperty = RestClientProvider.getInstance().patchLaw(a.getId());
                }
                loadTestData();
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

    private void setParliamentState() {
        if ("sazvana".equals(RestClientProvider.getInstance().parliamentState.get())) {
            amendmentsContainerPane.setDisable(true);
            lawVotePane.setDisable(true);
            lawTable.setDisable(true);
            lawInfoPane.setDisable(true);
            prevTitle.setDisable(true);
            Button utokuButton = new Button("Pokreni");
            buttonContainer.getChildren().clear();
            buttonContainer.getChildren().add(utokuButton);
            stateLabel.setText(RestClientProvider.getInstance().parliamentState.get());
            utokuButton.setOnAction(event -> {
                RestClientProvider.getInstance().changeParliamentStatus("u_toku");
            });
        } else if ("u_toku".equals(RestClientProvider.getInstance().parliamentState.get())) {
            amendmentsContainerPane.setDisable(false);
            lawVotePane.setDisable(false);
            lawTable.setDisable(false);
            lawInfoPane.setDisable(false);
            prevTitle.setDisable(false);
            buttonContainer.getChildren().clear();
            buttonContainer.getChildren().add(finish);
            stateLabel.setText(RestClientProvider.getInstance().parliamentState.get());
        } else if ("završena".equals(RestClientProvider.getInstance().parliamentState.get())) {
            amendmentsContainerPane.setDisable(false);
            lawVotePane.setDisable(true);
            lawTable.setDisable(true);
            lawInfoPane.setDisable(true);
            prevTitle.setDisable(true);
            buttonContainer.getChildren().clear();
            Button button = new Button("Zakaži");
            button.setOnAction(event -> {
                FXMLLoader loader = new FXMLLoader();
                loader.setBuilderFactory(new JavaFXBuilderFactory());
                AnchorPane pickerPane = null;
                NewAssemblyController picker = null;
                loader.setLocation(MainFXApp.class.getResource("/assembly/NewAssembly.fxml"));
                try (InputStream in = MainFXApp.class.getResourceAsStream("/assembly/NewAssembly.fxml")) {
                    pickerPane = loader.load(in);
                    picker = loader.getController();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setScene(new Scene(pickerPane));
                stage.setTitle("Unesite podatke o zasedanju skupštine");
                stage.getIcons().add(
                        new Image(MainFXApp.class.getResource("/images/dialog.png").toString()));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(buttonContainer.getScene().getWindow());
                stage.setResizable(false);
                LocalDate localDate = picker.datePicker.getValue();
                String mesto = picker.place.getText();
                picker.saveButton.setOnAction(event1 -> {
                    Parliament parliament = new Parliament();
                    parliament.setId("active_parliament");
                    parliament.setHead(new Parliament.Head());
                    parliament.setBody(new Parliament.Body());
                    parliament.getHead().setStatus("sazvana");
                    parliament.getHead().setBrojPrisutnih(100);
                    // TODO
                    GregorianCalendar gregorianCalendar = GregorianCalendar.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()));
                    parliament.getHead().setMjestoOdrzavanja(mesto);
                    try {
                        parliament.getHead().setDatumOdrzavanja(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                    } catch (DatatypeConfigurationException e) {
                        e.printStackTrace();
                    }
                    parliament.getBody().setAkti(new Parliament.Body.Akti());
                    RestClientProvider.getInstance().createParliament(parliament);
                });
                picker.cancelButton.setOnAction(event1 -> {
                    stage.close();
                });
                stage.showAndWait();
            });
            buttonContainer.getChildren().add(button);
        }
    }
}
