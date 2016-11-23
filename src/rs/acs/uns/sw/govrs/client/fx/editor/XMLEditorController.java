package rs.acs.uns.sw.govrs.client.fx.editor;


import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.glyphfont.FontAwesome;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.model.Element;
import rs.acs.uns.sw.govrs.client.fx.model.Glava;
import rs.acs.uns.sw.govrs.client.fx.model.Propis;
import rs.acs.uns.sw.govrs.client.fx.model.Tacka;
import rs.acs.uns.sw.govrs.client.fx.model.tree.TreeModel;

public class XMLEditorController {

    private TreeModel tree;

    @FXML
    private TitledPane treeContainer;

    @FXML
    private TextArea textArea;


    // -------------------- Buttons --------------------
    @FXML
    private Button undoAction;

    @FXML
    private Button redoAction;

    @FXML
    private Button cutAction;

    @FXML
    private Button copyAction;

    @FXML
    private Button pasteAction;

    @FXML
    private Button boldAction;

    @FXML
    private Button italicAcition;

    @FXML
    private Button underlineAction;

    @FXML
    private Button strikethroughAction;

    @FXML
    private ComboBox<Integer> fontSizePicker;



    // Reference to the main application.
    private MainFXApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public XMLEditorController() {

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        Propis propis = createDummyData();

        tree = new TreeModel(
                propis,
                Element::getChildren,
                Element::nameProperty
        );

        TreeView<Element> treeView = tree.getTreeView();
        treeContainer.setContent(treeView);

        boldAction.setText(null);
        undoAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.UNDO));
        redoAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.REDO));
        cutAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.CONTENT_CUT));
        copyAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.CONTENT_COPY));
        pasteAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.CONTENT_PASTE));
        boldAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.FORMAT_BOLD));
        italicAcition.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.FORMAT_ITALIC));
        underlineAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.FORMAT_UNDERLINE));
        strikethroughAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.FORMAT_STRIKETHROUGH));

        fontSizePicker.setItems(FXCollections.observableArrayList(8, 9, 10, 11, 12, 14, 16, 20, 24, 32, 40));
        fontSizePicker.getSelectionModel().select(0);
    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainFXApp mainApp) {
        this.mainApp = mainApp;
    }

    private Propis createDummyData() {
        Propis propis = new Propis("Propis o destabilizaciji mozga", "images/law.png");
        Glava glava1 = new Glava("Glava prva", null);
        Glava glava2 = new Glava("Glava druga", null);
        Glava glava3 = new Glava("Glava treća", null);
        Tacka t1 = new Tacka("Prva tačka");
        Tacka t2 = new Tacka("Druga tačka");
        Tacka t3 = new Tacka("Treća tačka");
        Tacka t4 = new Tacka("Četvrta tačka");
        Tacka t5 = new Tacka("Peta tačka");
        Tacka t6 = new Tacka("Šesta tačka");

        propis.getChildren().add(glava1);
        propis.getChildren().add(glava2);
        propis.getChildren().add(glava3);

        glava1.getChildren().add(t1);
        glava2.getChildren().add(t2);
        glava2.getChildren().add(t3);
        glava3.getChildren().add(t4);
        glava3.getChildren().add(t5);
        glava3.getChildren().add(t6);
        return propis;
    }

}
