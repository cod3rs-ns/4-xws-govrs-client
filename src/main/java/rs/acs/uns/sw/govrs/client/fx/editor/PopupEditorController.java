package rs.acs.uns.sw.govrs.client.fx.editor;


import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyledTextArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.reactfx.SuspendableNo;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.domain.tree.TreeController;
import rs.acs.uns.sw.govrs.client.fx.domain.tree.TreeModel;
import rs.acs.uns.sw.govrs.client.fx.editor.help.PopupEditorOptions;
import rs.acs.uns.sw.govrs.client.fx.editor.preview.HtmlPreview;
import rs.acs.uns.sw.govrs.client.fx.editor.style.ParStyle;
import rs.acs.uns.sw.govrs.client.fx.editor.style.TextStyle;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.*;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.ItemWrapper;
import rs.acs.uns.sw.govrs.client.fx.util.ElementType;
import rs.acs.uns.sw.govrs.client.fx.util.IdentityGenerator;

import java.util.function.Function;

public class PopupEditorController implements TreeController {
    /**
     * Attribute for CSS change of buttons
     */
    private static final String PRESSED = "pressed";

    /**
     * Styled Text Area attributes
     **/
    private final StyledTextArea<ParStyle, TextStyle> area;
    private final SuspendableNo updatingToolbar = new SuspendableNo();

    // ---------------- ui-containers ------------------
    @FXML
    private TitledPane treeContainer;
    @FXML
    private BorderPane areaContainer;
    @FXML
    private BorderPane attributesContainer;
    // -------------------------------------------------

    // ----------- text area commands ------------------
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
    private ComboBox<Integer> fontSizePicker;

    @FXML
    private PropertySheet propertySheet;

    private PopupEditorOptions initObject;

    private TreeModel tree;
    // -------------------------------------------------

    /**
     * Selected element on Tree
     **/
    private Element selectedElement;

    /**
     * Change Listener for Text Area
     **/
    private ChangeListener textAreaChangeListener;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PopupEditorController() {
        // setup text area
        area = new StyledTextArea<>(
                ParStyle.EMPTY, (paragraph, style) -> paragraph.setStyle(style.toCss()),
                TextStyle.EMPTY.updateFontSize(10).updateFontFamily("Verdana").updateTextColor(Color.BLACK),
                (text, style) -> text.setStyle(style.toCss()));
        area.setWrapText(true);
        area.setStyleCodecs(ParStyle.CODEC, TextStyle.CODEC);
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        // setup additional parameters of area and init actions
        setupAdditionalAreaParams();
        connectTextAreaActions();

        // add area to container
        VirtualizedScrollPane<StyledTextArea<ParStyle, TextStyle>> vsPane = new VirtualizedScrollPane<>(area);
        areaContainer.setCenter(vsPane);

    }

    public void initElements(PopupEditorOptions init) {
        initObject = init;
        Element element = null;
        if (initObject.isCreateNew()) {
            if (init.getTypeOfElement() == ElementType.Article) {
                element = new Article();
                element.idProperty().set(IdentityGenerator.get().generate(initObject.getParentElement(), ElementType.Article));
            } else if (init.getTypeOfElement() == ElementType.Paragraph) {
                element = new Paragraph();
                element.idProperty().set(IdentityGenerator.get().generate(initObject.getParentElement(), ElementType.Paragraph));
            } else if (init.getTypeOfElement() == ElementType.Clause) {
                element = new Clause();
                element.idProperty().set(IdentityGenerator.get().generate(init.getParentElement(), ElementType.Clause));
            } else if (init.getTypeOfElement() == ElementType.Subclause) {
                element = new Subclause();
                element.idProperty().set(IdentityGenerator.get().generate(init.getParentElement(), ElementType.Subclause));
            } else if (init.getTypeOfElement() == ElementType.Item) {
                Item i = new Item();
                i.setValue("");
                i.idProperty().set(IdentityGenerator.get().generate(initObject.getParentElement(), ElementType.Item));
                element = new ItemWrapper(i);
            } else {
                System.out.println("Something went wrong in initElements");
            }
            init.setElement(element);
            init.setCreateNew(false);
            init.getElement().initElement();
            init.getElement().createPropertyAttrs();
        }
        tree = new TreeModel(
                init.getElement(),
                Element::getChildren,
                Element::elementNameProperty,
                this
        );

        TreeView<Element> treeView = tree.getTreeView();
        treeContainer.setContent(treeView);
    }

    @FXML
    public void saveAction() {
        initObject.setSaved(true);
        ((Stage) treeContainer.getScene().getWindow()).close();
    }

    @FXML
    public void cancelAction() {
        initObject.setSaved(false);
        ((Stage) treeContainer.getScene().getWindow()).close();
    }


    /**
     * Creates all actions of <strong>TextArea</strong> and initializes all remaining properties.
     * Shouldn't be changed further.
     */
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
                });
            }
        });
    }

    // ------------------------------------------ TextArea actions ---------------------------------------------------//
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
    // END--------------------------------------- TextArea actions ---------------------------------------------------//

    /**
     * Connects TextArea actions.
     */
    private void connectTextAreaActions() {
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
        fontSizePicker.setOnAction(event -> updateFontSize(fontSizePicker.getValue()));
    }


    public Element getSelectedElement() {
        return selectedElement;
    }

    @Override
    public HtmlPreview getPreview() {
        return null;
    }

    /**
     * This method should be called when on Tree Element change, to trigger context change.
     *
     * @param selectedElement newly selected Element
     */
    public void setSelectedElement(Element selectedElement) {
        this.selectedElement = selectedElement;
        // remove old listener if exist
        if (textAreaChangeListener != null) {
            area.textProperty().removeListener(textAreaChangeListener);
        }

        // displays content of text elements
        if (selectedElement instanceof StringWrapper || selectedElement instanceof ItemWrapper) {
            area.setDisable(false);

            area.clear();
            // safe :)
            if (selectedElement.getElementContent() == null) {
                selectedElement.setElementContent("");
            }
            area.replaceText(0, -1, selectedElement.getElementContent());

            textAreaChangeListener = createChangeAreaListener(selectedElement);
            area.textProperty().addListener(textAreaChangeListener);
        } else {
            area.clear();
            area.setDisable(true);
        }
        this.propertySheet.getItems().clear();
        this.propertySheet.getItems().addAll(selectedElement.getPropertyItems());
    }

    /**
     * Creates new Listener for newly selected Element
     *
     * @param selElement newly selected Element
     * @return ChangeListener instance
     */
    private ChangeListener createChangeAreaListener(Element selElement) {
        return (ChangeListener<String>) (observable, oldValue, newValue) -> {
            selElement.setElementContent(newValue);
        };
    }

}
