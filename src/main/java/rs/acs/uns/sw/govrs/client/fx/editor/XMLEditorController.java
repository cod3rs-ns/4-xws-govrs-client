package rs.acs.uns.sw.govrs.client.fx.editor;


import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.converter.StringInputConverter;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.controlsfx.control.PropertySheet;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyledTextArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.reactfx.SuspendableNo;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.domain.tree.TreeModel;
import rs.acs.uns.sw.govrs.client.fx.editor.preview.HtmlPreview;
import rs.acs.uns.sw.govrs.client.fx.editor.style.ParStyle;
import rs.acs.uns.sw.govrs.client.fx.editor.style.TextStyle;
import rs.acs.uns.sw.govrs.client.fx.manager.StateManager;
import rs.acs.uns.sw.govrs.client.fx.rest.LawInputConverter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.StringWrapper;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.ItemWrapper;

import java.util.function.Function;

/**
 * Used for creating Laws.
 */
public class XMLEditorController {

    private static final String PRESSED = "pressed";
    public final StyledTextArea<ParStyle, TextStyle> area;
    private final SuspendableNo updatingToolbar = new SuspendableNo();
    public HtmlPreview preview;
    private TreeModel tree;
    // ------------------ Containers -------------------
    @FXML
    private TitledPane treeContainer;
    @FXML
    private TitledPane previewContainer;
    @FXML
    private BorderPane areaContainer;
    @FXML
    private BorderPane attributesContainer;
    // -------------------------------------------------

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
    private Button italicAction;
    @FXML
    private Button underlineAction;
    @FXML
    private Button strikeAction;
    @FXML
    private Button linkAction;
    @FXML
    private ComboBox<Integer> fontSizePicker;
    // -------------------------------------------------
    private Law propis;
    // ----------- save load buttons --------------
    @FXML
    private ImageView openButton;
    @FXML
    private ImageView saveButton;
    @FXML
    private ImageView saveAsButton;

    // Reference to the main application.
    private MainFXApp mainApp;

    public PropertySheet propertySheet;

    private Element activeElement;

    private StateManager stateManager;

    public StateManager getStateManager() {
        return stateManager;
    }

    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public XMLEditorController() {
        // setup text area
        area = new StyledTextArea<>(
                ParStyle.EMPTY, (paragraph, style) -> paragraph.setStyle(style.toCss()),
                TextStyle.EMPTY.updateFontSize(12).updateFontFamily("Verdana").updateTextColor(Color.BLACK),
                (text, style) -> text.setStyle(style.toCss()));
        area.setWrapText(true);
        area.setStyleCodecs(ParStyle.CODEC, TextStyle.CODEC);
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // setup additional parameters of area and init actions
        setupAdditionalAreaParams();
        initActions();

        // add area to container
        VirtualizedScrollPane<StyledTextArea<ParStyle, TextStyle>> vsPane = new VirtualizedScrollPane<>(area);
        areaContainer.setCenter(vsPane);

        // create tooltips for file control buttons
        Tooltip.install(openButton, new Tooltip("Otvorite novi dokument"));
        Tooltip.install(saveButton, new Tooltip("Sačuvajte dokument"));
        Tooltip.install(saveAsButton, new Tooltip("Sačuvajte dokument kao..."));
    }

    public void loadData() {

        // create a RestClient to the specific URL
        RestClient restClient = RestClient.create()
                .method("GET")
                .host("http://localhost:9000/api")
                .path("/laws/law01");

        // retrieve a list from the DataProvider
        GluonObservableObject<Law> lawProperty;
        LawInputConverter converter = new LawInputConverter();
        lawProperty = DataProvider.retrieveObject(restClient.createObjectDataReader(converter));
        ProgressBar pb = new ProgressBar();
        pb.setPrefWidth(150);
        stateManager.homeController.getStatusBar().getLeftItems().clear();
        stateManager.homeController.getStatusBar().getLeftItems().add(new Text("Učitavanje podataka..."));
        stateManager.homeController.getStatusBar().getLeftItems().add(pb);
        propertySheet = new PropertySheet();
        attributesContainer.setCenter(propertySheet);

        lawProperty.initializedProperty().addListener(((observable, oldValue, newValue) -> {
            propis = lawProperty.get();
            propis.initElement();
            preview = new HtmlPreview(propis, "propis", Law.class);
            previewContainer.setContent(preview.getNode());
            area.replaceText(0, 0, "");
            tree = new TreeModel(
                    propis,
                    Element::getChildren,
                    Element::elementNameProperty,
                    this
            );

            stateManager.homeController.getStatusBar().getLeftItems().clear();
            stateManager.homeController.getStatusBar().getLeftItems().add(new Text("Podaci uspešno učitani."));

            TreeView<Element> treeView = tree.getTreeView();
            treeContainer.setContent(treeView);

            // create a RestClient to the specific URL
            RestClient restClientHtml = RestClient.create()
                    .method("GET")
                    .host("http://localhost:9000/api")
                    .path("/laws/html/law01");

            // retrieve a list from the DataProvider
            GluonObservableObject<String> htmlProperty;
            StringInputConverter converterString = new StringInputConverter();
            htmlProperty = DataProvider.retrieveObject(restClientHtml.createObjectDataReader(converterString));
            htmlProperty.initializedProperty().addListener(((a, ov, nv) -> {
                //preview.getNode().getEngine().loadContent(htmlProperty.get());
                //System.out.println(htmlProperty.get());
            }));
        }));

    }

    private void setupAdditionalAreaParams() {
        // Populate possible font sizes
        fontSizePicker.setItems(FXCollections.observableArrayList(8, 9, 10, 11, 12, 14, 16, 20, 24, 32, 40));
        fontSizePicker.getSelectionModel().select(4);

        // Disable Undo&Redo actions when there is nothing to undo/redo
        undoAction.disableProperty().bind(Bindings.not(area.undoAvailableProperty()));
        redoAction.disableProperty().bind(Bindings.not(area.redoAvailableProperty()));

        // Make boolean binding on empty selection, for disabling adequate buttons
        BooleanBinding selectionEmpty = new BooleanBinding() {
            {
                bind(area.selectionProperty());
            }

            @Override
            protected boolean computeValue() {
                return area.getSelection().getLength() == 0;
            }
        };

        // Disable cut/copy when there is no selection
        cutAction.disableProperty().bind(selectionEmpty);
        copyAction.disableProperty().bind(selectionEmpty);

        // Area style update logic
        area.beingUpdatedProperty().addListener((o, old, beingUpdated) -> {
            if (!beingUpdated) {
                boolean bold;
                boolean italic;
                boolean underline;
                boolean strike;
                Integer fontSize;

                IndexRange selection = area.getSelection();
                if (selection.getLength() != 0) {
                    StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
                    bold = styles.styleStream().anyMatch(s -> s.bold.orElse(false));
                    italic = styles.styleStream().anyMatch(s -> s.italic.orElse(false));
                    underline = styles.styleStream().anyMatch(s -> s.underline.orElse(false));
                    strike = styles.styleStream().anyMatch(s -> s.strikethrough.orElse(false));
                    int[] sizes = styles.styleStream().mapToInt(s -> s.fontSize.orElse(-1)).distinct().toArray();
                    fontSize = sizes.length == 1 ? sizes[0] : -1;
                } else {
                    int p = area.getCurrentParagraph();
                    int col = area.getCaretColumn();
                    TextStyle style = area.getStyleAtPosition(p, col);
                    bold = style.bold.orElse(false);
                    italic = style.italic.orElse(false);
                    underline = style.underline.orElse(false);
                    strike = style.strikethrough.orElse(false);
                    fontSize = style.fontSize.orElse(-1);
                }

                updatingToolbar.suspendWhile(() -> {
                    if (bold) {
                        if (!boldAction.getStyleClass().contains(PRESSED)) {
                            boldAction.getStyleClass().add(PRESSED);
                        }
                    } else {
                        boldAction.getStyleClass().remove(PRESSED);
                    }

                    if (italic) {
                        if (!italicAction.getStyleClass().contains(PRESSED)) {
                            italicAction.getStyleClass().add(PRESSED);
                        }
                    } else {
                        italicAction.getStyleClass().remove(PRESSED);
                    }

                    if (underline) {
                        if (!underlineAction.getStyleClass().contains(PRESSED)) {
                            underlineAction.getStyleClass().add(PRESSED);
                        }
                    } else {
                        underlineAction.getStyleClass().remove(PRESSED);
                    }

                    if (strike) {
                        if (!strikeAction.getStyleClass().contains(PRESSED)) {
                            strikeAction.getStyleClass().add(PRESSED);
                        }
                    } else {
                        strikeAction.getStyleClass().remove(PRESSED);
                    }

                    if (fontSize != -1) {
                        fontSizePicker.getSelectionModel().select(fontSize);
                    } else {
                        fontSizePicker.getSelectionModel().clearSelection();
                    }

                    // Update preview on finished style changing
                    preview.update();
                });
            }
        });
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp Main Application instance
     */
    public void setMainApp(MainFXApp mainApp) {
        this.mainApp = mainApp;
    }

    private void toggleBold() {
        updateStyleInSelection(spans -> TextStyle.bold(!spans.styleStream().allMatch(style -> style.bold.orElse(false))));
    }

    private void toggleItalic() {
        updateStyleInSelection(spans -> TextStyle.italic(!spans.styleStream().allMatch(style -> style.italic.orElse(false))));
    }

    private void toggleUnderline() {
        updateStyleInSelection(spans -> TextStyle.underline(!spans.styleStream().allMatch(style -> style.underline.orElse(false))));
    }

    private void toggleStrikethrough() {
        updateStyleInSelection(spans -> TextStyle.strikethrough(!spans.styleStream().allMatch(style -> style.strikethrough.orElse(false))));
    }

    private void updateFontSize(Integer size) {
        if (!updatingToolbar.get()) {
            updateWholeStyle(TextStyle.fontSize(size));
        }
    }

    private void updateStyleInSelection(Function<StyleSpans<TextStyle>, TextStyle> mixinGetter) {
        IndexRange selection = area.getSelection();
        if (selection.getLength() != 0) {
            StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
            TextStyle mixin = mixinGetter.apply(styles);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
            area.setStyleSpans(selection.getStart(), newStyles);
        }
    }

    private void updateWholeStyle(TextStyle ts) {
        area.selectAll();
        IndexRange selection = area.getSelection();
        if (selection.getLength() != 0) {
            StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(ts));
            area.setStyleSpans(selection.getStart(), newStyles);
        }
        area.deselect();
    }

    private void updateStyleInSelection(TextStyle mixin) {
        IndexRange selection = area.getSelection();
        if (selection.getLength() != 0) {
            StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
            area.setStyleSpans(selection.getStart(), newStyles);
        }
    }

    private void initActions() {

        undoAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.UNDO));
        undoAction.setOnAction(event -> {
            Runnable action = area::undo;
            action.run();
            area.requestFocus();
        });
        redoAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.REDO));
        redoAction.setOnAction(event -> {
            Runnable action = area::redo;
            action.run();
            area.requestFocus();
        });
        cutAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.CONTENT_CUT));
        cutAction.setOnAction(event -> {
            Runnable action = area::cut;
            action.run();
            area.requestFocus();
        });
        copyAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.CONTENT_COPY));
        copyAction.setOnAction(event -> {
            Runnable action = area::copy;
            action.run();
            area.requestFocus();
        });
        pasteAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.CONTENT_PASTE));
        pasteAction.setOnAction(event -> {
            Runnable action = area::paste;
            action.run();
            area.requestFocus();
        });

        boldAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.FORMAT_BOLD));
        boldAction.setOnAction(event -> {
            Runnable action = this::toggleBold;
            action.run();
            area.requestFocus();
        });
        italicAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.FORMAT_ITALIC));
        italicAction.setOnAction(event -> {
            Runnable action = this::toggleItalic;
            action.run();
            area.requestFocus();
        });
        underlineAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.FORMAT_UNDERLINE));
        underlineAction.setOnAction(event -> {
            Runnable action = this::toggleUnderline;
            action.run();
            area.requestFocus();
        });
        strikeAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.FORMAT_STRIKETHROUGH));
        strikeAction.setOnAction(event -> {
            Runnable action = this::toggleStrikethrough;
            action.run();
            area.requestFocus();
        });

        linkAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.LINK));

        fontSizePicker.setOnAction(event -> updateFontSize(fontSizePicker.getValue()));

    }

    @FXML
    private void openAction() {

    }

    @FXML
    private void saveAction() {

    }

    @FXML
    private void saveAsAction() {

    }

    public Element getActiveElement() {
        return activeElement;
    }

    public void setActiveElement(Element activeElement) {
        this.activeElement = activeElement;
        // remove old listener if exist
        if (changeListener != null){
            area.textProperty().removeListener(changeListener);
        }

        if (activeElement instanceof StringWrapper || activeElement instanceof ItemWrapper) {
            area.setDisable(false);

            area.clear();
            area.replaceText(0, -1, activeElement.getElementContent());

            changeListener = createChangeAreaListener(activeElement);
            area.textProperty().addListener(changeListener);

        }
        else{
            area.clear();
            area.setDisable(true);
        }
        this.propertySheet.getItems().clear();
        this.propertySheet.getItems().addAll(activeElement.getPropertyItems());
    }
    public ChangeListener changeListener;

    public ChangeListener createChangeAreaListener(Element s){
        return new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                s.setElementContent(newValue);
                preview.update();
            }
        };
    }

}
