package rs.acs.uns.sw.govrs.client.fx.amendments;

import com.gluonhq.connect.GluonObservableObject;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.domain.tree.ContextMenuHandler;
import rs.acs.uns.sw.govrs.client.fx.editor.preview.HtmlPreview;
import rs.acs.uns.sw.govrs.client.fx.manager.StateManager;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Amendment;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Amendments;
import rs.acs.uns.sw.govrs.client.fx.util.CustomDialogCreator;
import rs.acs.uns.sw.govrs.client.fx.util.Loader;
import rs.acs.uns.sw.govrs.client.fx.util.Creator;

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
    @FXML
    private ImageView newAmendmentsButton;
    @FXML
    private ImageView uploadButton;
    @FXML
    private ImageView addButton;
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
        Tooltip.install(newAmendmentsButton, new Tooltip("Kreirajte novi amandman"));
        Tooltip.install(uploadButton, new Tooltip("Postavite amandmand"));
        Tooltip.install(addButton, new Tooltip("Dodaj novi amandman"));

        amendmentsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        amendmentProperties.getItems().clear();
                        amendmentProperties.getItems().addAll(newValue.getPropertyItems());
                        preview.update();
                        newValue.getPropertyItems().addListener(new ListChangeListener<PropertySheet.Item>() {
                            @Override
                            public void onChanged(Change<? extends PropertySheet.Item> c) {
                                amendmentProperties.getItems().clear();
                                amendmentProperties.getItems().addAll(newValue.getPropertyItems());
                            }
                        });
                    }

                });

    }

    public void loadTestData() {
        // send request
        GluonObservableObject<Object> amendmentsProperty =
                RestClientProvider.getInstance().getAmendments("amendments02");

        ProgressBar pb = new ProgressBar();
        pb.setPrefWidth(150);

        stateManager.homeController.getStatusBar().getLeftItems().clear();
        stateManager.homeController.getStatusBar().getLeftItems().add(new Text("Učitavanje podataka..."));
        stateManager.homeController.getStatusBar().getRightItems().add(pb);


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
            stateManager.homeController.getStatusBar().getRightItems().clear();
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
    private void createNewAmendments() {
        TextInputDialog dialog = CustomDialogCreator.createNewEntryDialog("Neki novi amandmani");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            Amendments newl = Creator.createNewAmendments();
            switchViewToNewAmendment(newl);
        });
    }

    @FXML
    private void uploadAmendment() {
        try {
            GluonObservableObject<Object> amendmentsProperty =
                    RestClientProvider.getInstance().postAmendments(amendments);
            Stage stage = Loader.createLoader(amendmentsTable.getScene());
            stage.show();

            amendmentsProperty.initializedProperty().addListener(((observable, oldValue, newValue) -> {
                stage.close();
            }));
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Unable to upload Amendments!");
        }

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
    }

    @FXML
    private void addAmendment() {
        amendments.createAndAddChild(Creator.createOneAmendment());
    }

    @FXML
    private void createTableContextMenu() {
        Element element = amendmentsTable.getSelectionModel().selectedItemProperty().get();
        if (element != null) {
            Amendment a = (Amendment) element;
            ContextMenuHandler cmh = new ContextMenuHandler();
            ContextMenu contextMenu = cmh.createContextMenu(a);
            amendmentsTable.setContextMenu(contextMenu);
        }
    }


    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }

}
