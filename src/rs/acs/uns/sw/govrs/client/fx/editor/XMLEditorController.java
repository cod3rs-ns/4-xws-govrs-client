package rs.acs.uns.sw.govrs.client.fx.editor;


import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyledTextArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.reactfx.SuspendableNo;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.editor.preview.ActPreview;
import rs.acs.uns.sw.govrs.client.fx.editor.style.ParStyle;
import rs.acs.uns.sw.govrs.client.fx.editor.style.TextStyle;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.domain.Glava;
import rs.acs.uns.sw.govrs.client.fx.domain.Propis;
import rs.acs.uns.sw.govrs.client.fx.domain.Tacka;
import rs.acs.uns.sw.govrs.client.fx.domain.tree.TreeModel;

import java.util.function.Function;

public class XMLEditorController {

    private final StyledTextArea<ParStyle, TextStyle> area;
    private final SuspendableNo updatingToolbar = new SuspendableNo();

    private TreeModel tree;
    private ActPreview preview;

    // ------------------ Containers -------------------
    @FXML
    private TitledPane treeContainer;
    @FXML
    private TitledPane previewContainer;
    @FXML
    private BorderPane areaContainer;
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

    // Reference to the main application.
    private MainFXApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public XMLEditorController() {
        area = new StyledTextArea<>(
                ParStyle.EMPTY, (paragraph, style) -> paragraph.setStyle(style.toCss()),
                TextStyle.EMPTY.updateFontSize(12).updateFontFamily("Serif").updateTextColor(Color.BLACK),
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
        Propis propis = createDummyData();

        preview = new ActPreview(propis);
        previewContainer.setContent(preview.getNode());
        preview.update();


        tree = new TreeModel(
                propis,
                Element::getChildren,
                Element::nameProperty,
                preview
        );

        TreeView<Element> treeView = tree.getTreeView();
        treeContainer.setContent(treeView);

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
                boolean bold, italic, underline, strike;
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
                        if (!boldAction.getStyleClass().contains("pressed")) {
                            boldAction.getStyleClass().add("pressed");
                        }
                    } else {
                        boldAction.getStyleClass().remove("pressed");
                    }

                    if (italic) {
                        if (!italicAction.getStyleClass().contains("pressed")) {
                            italicAction.getStyleClass().add("pressed");
                        }
                    } else {
                        italicAction.getStyleClass().remove("pressed");
                    }

                    if (underline) {
                        if (!underlineAction.getStyleClass().contains("pressed")) {
                            underlineAction.getStyleClass().add("pressed");
                        }
                    } else {
                        underlineAction.getStyleClass().remove("pressed");
                    }

                    if (strike) {
                        if (!strikeAction.getStyleClass().contains("pressed")) {
                            strikeAction.getStyleClass().add("pressed");
                        }
                    } else {
                        strikeAction.getStyleClass().remove("pressed");
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

        initActions();

        // Add area to container
        VirtualizedScrollPane<StyledTextArea<ParStyle, TextStyle>> vsPane = new VirtualizedScrollPane<>(area);
        areaContainer.setCenter(vsPane);
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp Main Application instance
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

        fontSizePicker.setOnAction(event -> {
            updateFontSize(fontSizePicker.getValue());
        });

    }

}
