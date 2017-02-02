package rs.acs.uns.sw.govrs.client.fx.amendments;

import com.gluonhq.connect.GluonObservableObject;
import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PropertySheet;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.domain.tree.ContextMenuHandler;
import rs.acs.uns.sw.govrs.client.fx.editor.preview.HtmlPreview;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.StringPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.manager.StateManager;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Amendment;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Amendments;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Parliament;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Ref;
import rs.acs.uns.sw.govrs.client.fx.util.Creator;
import rs.acs.uns.sw.govrs.client.fx.util.CustomDialogCreator;
import rs.acs.uns.sw.govrs.client.fx.util.Loader;
import rs.acs.uns.sw.govrs.client.fx.validation.ErrorMessage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
                        for(PropertySheet.Item i : amendmentProperties.getItems()) {
                            if (i instanceof StringPropertyItem) {
                                ((StringPropertyItem) i).property.addListener((o, ol, oll) ->
                                {
                                    if(preview != null) preview.update();
                                });
                            }
                        }
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
                Notifications.create().owner(amendmentsTable.getScene().getWindow()).title("Otvaranje XML fajl").text("XML fajl je uspešno otvoren").showConfirm();
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
        if (amendments != null) {
            try {
                List<ErrorMessage> errors = new ArrayList<>();
                amendments.validate(errors);
                if (errors.size() > 0) {
                    for (ErrorMessage em : errors) {
                        Notifications.create().owner(previewContainer.getScene().getWindow())
                                .title("Greška <" + em.getElementType().toString() + ">")
                                .text(em.getMessage())
                                .showError();
                    }
                } else {
                    amendments.preMarshaller();
                    if (activeFile != null) {
                        JAXBContext context = JAXBContext.newInstance(Amendments.class);
                        Marshaller marshaller = context.createMarshaller();
                        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                        marshaller.marshal(amendments, activeFile);
                        Notifications.create().owner(previewContainer.getScene().getWindow()).title("Skladištenje XML datoteke").text("Fajl je uspešno sačuvan").showConfirm();
                    }
                }
            } catch (Exception e) {
                CustomDialogCreator.createErrorAlert(
                        "GovRS",
                        "Skladištenje XML datoteke",
                        "Fajl nije uspešno sačuvan."
                ).showAndWait();
                Logger.getLogger(getClass().getName()).log(Level.WARNING, "Unable to save file.");
            }
        } else {
            Notifications.create().owner(amendmentsTable.getScene().getWindow()).title("Neispravna akcija").text("Morate prvo otvoriti/napraviti novi amandman").showWarning();
        }
    }

    /**
     * Selects and saves active Law to file.
     */
    @FXML
    private void saveAsAction() {
        if (amendments != null) {
            List<ErrorMessage> errors = new ArrayList<>();
            amendments.validate(errors);
            if (errors.size() > 0) {
                for (ErrorMessage em : errors) {
                    Notifications.create().owner(previewContainer.getScene().getWindow())
                            .title("Greška <" + em.getElementType().toString() + ">")
                            .text(em.getMessage())
                            .showError();
                }
            } else {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Sačuvaj amandman kao...");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("XML files", "*.xml")
                );

                Stage stage = (Stage) amendmentsTable.getScene().getWindow();
                File file = fileChooser.showSaveDialog(stage);
                if (file != null) {
                    try {
                        amendments.preMarshaller();
                        JAXBContext context = JAXBContext.newInstance(Amendments.class);
                        Marshaller marshaller = context.createMarshaller();
                        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                        marshaller.marshal(amendments, file);
                        Notifications.create().owner(amendmentsTable.getScene().getWindow()).title("Skladištenje XML datoteke").text("Fajl je uspešno sačuvan").showConfirm();
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
        } else {
            Notifications.create().owner(amendmentsTable.getScene().getWindow()).title("Neispravna akcija").text("Morate prvo otvoriti/napraviti novi amandman").showWarning();
        }
    }

    @FXML
    private void createNewAmendments() {
        String lawId = "";
        Parliament parliament = RestClientProvider.getInstance().getActiveParliament();
        if ("sazvana".equals(RestClientProvider.getInstance().parliamentState.get())) {
            List<String> possibleLaws = new ArrayList<>();
            for (Ref r : parliament.getBody().getAkti().getRef()) {
                if ("law".equals(r.getType())) {
                    possibleLaws.add(r.getId());
                }
            }
            ChoiceDialog<String> choice = CustomDialogCreator.createSelectLawDialog(possibleLaws);
            Optional<String> selected = choice.showAndWait();
            selected.ifPresent(id -> {
                if (!id.equals("")) {
                    TextInputDialog dialog = CustomDialogCreator.createNewEntryDialog("Neki novi amandmani");
                    Optional<String> result = dialog.showAndWait();
                    result.ifPresent(name -> {
                        Amendments newl = Creator.createNewAmendments(id);
                        newl.setName(name);
                        switchViewToNewAmendment(newl);

                    });
                } else {
                    Notifications.create().owner(previewContainer.getScene().getWindow()).title("Neuspešna akcija").text("Morate izabrati Propis za koji pišete Amandman").showWarning();
                }
            });

        } else {
            Notifications.create().owner(previewContainer.getScene().getWindow()).title("Neuspela operacija").text("Trenutno ne postoji sazvana sednica Skupštine").showWarning();
        }

    }

    @FXML
    private void uploadAmendment() {
        if (amendments != null) {
            if ("sazvana".equals(RestClientProvider.getInstance().parliamentState.get())) {
                try {
                    List<ErrorMessage> errors = new ArrayList<>();
                    amendments.validate(errors);
                    if (errors.size() > 0) {
                        for (ErrorMessage em : errors) {
                            Notifications.create().owner(previewContainer.getScene().getWindow())
                                    .title("Greška <" + em.getElementType().toString() + ">")
                                    .text(em.getMessage())
                                    .showError();
                        }
                    } else {
                        amendments.preMarshaller();
                        GluonObservableObject<Object> amendmentsProperty =
                                RestClientProvider.getInstance().postAmendments(amendments);
                        Stage stage = Loader.createLoader(amendmentsTable.getScene());
                        stage.show();

                        amendmentsProperty.initializedProperty().addListener(((observable, oldValue, newValue) -> {
                            stage.close();
                            if (amendmentsProperty.get() == null) {
                                Notifications.create().owner(amendmentsTable.getScene().getWindow()).title("GREŠKA!").text("Amandman sa ovim identifikatorom već postoji!").showError();
                            }
                            Notifications.create().owner(amendmentsTable.getScene().getWindow()).title("Amandman").text("Uspešno ste predložili Amandman.").showInformation();
                        }));
                    }
                } catch (Exception e) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Unable to upload Amendments!");
                }
            } else {
                Notifications.create().owner(previewContainer.getScene().getWindow()).title("Neuspela operacija").text("Trenutno ne postoji sazvana sednica Skupštine").showWarning();

            }

        } else {
            Notifications.create().owner(previewContainer.getScene().getWindow()).title("Neuspela operacija").text("Morate prvo kreirati amandman.").showWarning();
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
        generalProperties.getItems().clear();
        generalProperties.getItems().addAll(amendments.getPropertyItems());
        //amendments.initElement();
        preview = new HtmlPreview(amendments, "amandman", Amendments.class);
        previewContainer.setContent(preview.getNode());
        amendmentsTable.getItems().clear();
        amendmentsTable.setItems(amendments.getChildren());
        amendmentsTable.refresh();
        preview.setRootElement(amendments);
        preview.update();
        generalProperties.getItems().clear();
        generalProperties.getItems().addAll(amendments.getPropertyItems());
        for(PropertySheet.Item i : generalProperties.getItems()) {
           if (i instanceof StringPropertyItem) {
               ((StringPropertyItem) i).property.addListener((observable, oldValue, newValue) -> preview.update());
           }
        }

    }

    @FXML
    private void addAmendment() {
        if (amendments != null) {
            amendments.createAndAddChild(Creator.createOneAmendment());
        } else {
            Notifications.create().owner(previewContainer.getScene().getWindow()).title("Neuspela operacija").text("Morate prvo napraviti novi Amandman.").showWarning();
        }
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
