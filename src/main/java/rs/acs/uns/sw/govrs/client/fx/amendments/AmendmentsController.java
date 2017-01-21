package rs.acs.uns.sw.govrs.client.fx.amendments;

import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.editor.preview.HtmlPreview;
import rs.acs.uns.sw.govrs.client.fx.manager.StateManager;
import rs.acs.uns.sw.govrs.client.fx.rest.ResultInputConverter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Amendments;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;
import rs.acs.uns.sw.govrs.client.fx.util.CustomDialogCreator;
import rs.acs.uns.sw.govrs.client.fx.util.ObjectCreator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AmendmentsController {
    // ------------------ components -------------------
    public HtmlPreview preview;
    // ---------------- ui-containers ------------------
    @FXML
    private TableView<Element> amendmentsTable;
    @FXML
    private TableColumn<Element, String> amendmentNameColumn;
    @FXML
    private TitledPane previewContainer;
    @FXML
    private PropertySheet amendmentProperties;
    @FXML
    private PropertySheet generalProperties;
    // -------------------------------------------------

    // ------------ file control buttons ---------------
    @FXML
    private ImageView openButton;
    @FXML
    private ImageView saveButton;
    @FXML
    private ImageView saveAsButton;
    // -------------------------------------------------
    @FXML
    private ImageView newLawButton;
    // -------------------------------------------------
    /**
     * Injected StateManger - parent component
     **/
    private StateManager stateManager;

    /**
     * Active Law element
     **/
    private Amendments amendments;

    /**
     * Represents file on disk, if opened, currently initialized to some value for testing
     **/
    private File activeFile = new File(System.getProperty("user.home") + File.separator + "test_save_xml");

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public AmendmentsController() {

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        amendmentNameColumn.setCellValueFactory(cellData -> cellData.getValue().elementNameProperty());

        // create tooltips for file control buttons
        Tooltip.install(openButton, new Tooltip("Otvorite novi dokument"));
        Tooltip.install(saveButton, new Tooltip("Sačuvajte dokument"));
        Tooltip.install(saveAsButton, new Tooltip("Sačuvajte dokument kao..."));

        amendmentsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null){
                        amendmentProperties.getItems().clear();
                        amendmentProperties.getItems().addAll(newValue.getPropertyItems());
                        preview.update();
                    }
                });

    }

    public void loadTestData() {
        // create a RestClient to the specific URL
        RestClient restClient = RestClient.create()
                .method("GET")
                .host("http://localhost:9000/api")
                .header("Accept", "application/xml")
                .path("/amendments/amendments02");

        // retrieve a list from the DataProvider
        GluonObservableObject<Object> amendmentsProperty;
        ResultInputConverter converter = new ResultInputConverter(Amendments.class);
        amendmentsProperty = DataProvider.retrieveObject(restClient.createObjectDataReader(converter));

        ProgressBar pb = new ProgressBar();
        pb.setPrefWidth(150);

        stateManager.homeController.getStatusBar().getLeftItems().clear();
        stateManager.homeController.getStatusBar().getLeftItems().add(new Text("Učitavanje podataka..."));
        stateManager.homeController.getStatusBar().getLeftItems().add(pb);


        amendmentsProperty.initializedProperty().addListener(((observable, oldValue, newValue) -> {
            amendments = (Amendments) amendmentsProperty.get();
            amendments.initElement();
            generalProperties.getItems().clear();
            generalProperties.getItems().addAll(amendments.getPropertyItems());
            //amendments.initElement();
            preview = new HtmlPreview(amendments, "amandman", Amendments.class);
            previewContainer.setContent(preview.getNode());
            preview.update();
            for (Element a : amendments.getChildren()
                    ) {
                System.out.println(a.getElementName());
            }

            amendmentsTable.setItems(amendments.getChildren());
            amendmentsTable.refresh();

            stateManager.homeController.getStatusBar().getLeftItems().clear();
            stateManager.homeController.getStatusBar().getLeftItems().add(new Text("Podaci uspešno učitani."));

        }));

    }

    /**
     * Opens XML file from disk.
     */
    @FXML
    private void openAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otvori XML propis");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML files", "*.xml")
        );
        Stage stage = (Stage) amendmentsTable.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                JAXBContext context = JAXBContext.newInstance(Amendments.class);
                Unmarshaller unMarshaller = context.createUnmarshaller();
                Amendments openNew = (Amendments) unMarshaller.unmarshal(file);
                switchViewToNewAmendment(openNew);
                CustomDialogCreator.createInformationAlert(
                        "GovRS",
                        "Otvaranje XML datoteke",
                        "Fajl je uspešno otvoren."
                ).showAndWait();
            } catch (Exception e) {
                CustomDialogCreator.createErrorAlert(
                        "GovRS",
                        "GREŠKA!",
                        "Fajl nije moguće otvoriti."
                ).showAndWait();
                Logger.getLogger(getClass().getName()).log(Level.WARNING, "Unable to load file.", e);
            }
        }
    }

    /**
     * Saves active file to disk.
     */
    @FXML
    private void saveAction() {
        try {
            if (activeFile != null) {
                JAXBContext context = JAXBContext.newInstance(Amendments.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(amendments, activeFile);
            }
            CustomDialogCreator.createInformationAlert(
                    "GovRS",
                    "Čuvanje XML datoteke",
                    "Fajl je uspešno sačuvan."
            ).showAndWait();
        } catch (Exception e) {
            CustomDialogCreator.createErrorAlert(
                    "GovRS",
                    "Čucanje XML datoteke",
                    "Fajl nije uspešno sačuvan."
            ).showAndWait();
            Logger.getLogger(getClass().getName()).log(Level.WARNING, "Unable to save file.");
        }
    }

    /**
     * Selects and saves active Law to file.
     */
    @FXML
    private void saveAsAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sačuvaj amandman kao...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML files", "*.xml")
        );

        Stage stage = (Stage) amendmentsTable.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                JAXBContext context = JAXBContext.newInstance(Amendments.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(amendments, file);
                CustomDialogCreator.createInformationAlert(
                        "GovRS",
                        "Čuvanje XML datoteke",
                        "Fajl je uspešno sačuvan."
                ).showAndWait();
            } catch (Exception e) {
                CustomDialogCreator.createErrorAlert(
                        "GovRS",
                        "Čuvanje XML datoteke",
                        "Fajl nije uspešno sačuvan."
                ).showAndWait();
                Logger.getLogger(getClass().getName()).log(Level.WARNING, "Unable to save file.");
            }
        }
    }

    @FXML
    private void createNewLaw() {
        TextInputDialog dialog = CustomDialogCreator.createNewEntryDialog("Propis");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            Law newl = ObjectCreator.createNewLaw();
            //switchViewToNewAmendment(newl);
        });
    }

    /**
     * Updates context when new Law is opened.
     *
     * @param newAmendnents newly opened Law
     */
    private void switchViewToNewAmendment(Amendments newAmendnents) {
        amendments = newAmendnents;
        amendments.initElement();
        preview.setRootElement(amendments);
        preview.update();
        amendmentsTable.getItems().clear();
        amendmentsTable.setItems(amendments.getChildren());
        generalProperties.getItems().clear();
        generalProperties.getItems().addAll(amendments.getPropertyItems());
        /**
         tree = new TreeModel(
         amendments,
         Element::getChildren,
         Element::elementNameProperty,
         this
         );*/

        //TreeView<Element> treeView = tree.getTreeView();
        //treeContainer.setContent(treeView);
    }


    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }

}
