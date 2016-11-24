package rs.acs.uns.sw.govrs.client.fx.editor;


import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyledTextArea;
import org.fxmisc.richtext.model.Paragraph;
import org.fxmisc.richtext.model.StyleSpans;
import org.reactfx.SuspendableNo;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.model.Element;
import rs.acs.uns.sw.govrs.client.fx.model.Glava;
import rs.acs.uns.sw.govrs.client.fx.model.Propis;
import rs.acs.uns.sw.govrs.client.fx.model.Tacka;
import rs.acs.uns.sw.govrs.client.fx.model.tree.TreeModel;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.fxmisc.richtext.model.TwoDimensional.Bias.Backward;
import static org.fxmisc.richtext.model.TwoDimensional.Bias.Forward;

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

    @FXML
    private BorderPane areaContainer;

    private final StyledTextArea<ParStyle, TextStyle> area = new StyledTextArea<>(
            ParStyle.EMPTY, ( paragraph, style) -> paragraph.setStyle(style.toCss()),
            TextStyle.EMPTY.updateFontSize(12).updateFontFamily("Serif").updateTextColor(Color.BLACK),
            ( text, style) -> text.setStyle(style.toCss()));
    {
        area.setWrapText(true);
        area.setStyleCodecs(ParStyle.CODEC, TextStyle.CODEC);
    }

    private final SuspendableNo updatingToolbar = new SuspendableNo();

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
        undoAction.setOnAction(event -> {
            Runnable action = area::undo;
            action.run();
            System.out.println("UNDO");
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
        italicAcition.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.FORMAT_ITALIC));
        italicAcition.setOnAction(event -> {
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
        strikethroughAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.FORMAT_STRIKETHROUGH));
        strikethroughAction.setOnAction(event -> {
            Runnable action = this::toggleStrikethrough;
            action.run();
            area.requestFocus();
        });


        fontSizePicker.setItems(FXCollections.observableArrayList(8, 9, 10, 11, 12, 14, 16, 20, 24, 32, 40));
        fontSizePicker.getSelectionModel().select(0);

        undoAction.disableProperty().bind(Bindings.not(area.undoAvailableProperty()));
        redoAction.disableProperty().bind(Bindings.not(area.redoAvailableProperty()));


        BooleanBinding selectionEmpty = new BooleanBinding() {
            { bind(area.selectionProperty()); }

            @Override
            protected boolean computeValue() {
                return area.getSelection().getLength() == 0;
            }
        };

        cutAction.disableProperty().bind(selectionEmpty);
        copyAction.disableProperty().bind(selectionEmpty);

        area.beingUpdatedProperty().addListener((o, old, beingUpdated) -> {
            if(!beingUpdated) {
                boolean bold, italic, underline, strike;
                Integer fontSize;
                String fontFamily;
                Color textColor;
                Color backgroundColor;

                IndexRange selection = area.getSelection();
                if(selection.getLength() != 0) {
                    StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
                    bold = styles.styleStream().anyMatch(s -> s.bold.orElse(false));
                    italic = styles.styleStream().anyMatch(s -> s.italic.orElse(false));
                    underline = styles.styleStream().anyMatch(s -> s.underline.orElse(false));
                    strike = styles.styleStream().anyMatch(s -> s.strikethrough.orElse(false));
                    int[] sizes = styles.styleStream().mapToInt(s -> s.fontSize.orElse(-1)).distinct().toArray();
                    fontSize = sizes.length == 1 ? sizes[0] : -1;
                    String[] families = styles.styleStream().map(s -> s.fontFamily.orElse(null)).distinct().toArray(String[]::new);
                    fontFamily = families.length == 1 ? families[0] : null;
                    Color[] colors = styles.styleStream().map(s -> s.textColor.orElse(null)).distinct().toArray(Color[]::new);
                    textColor = colors.length == 1 ? colors[0] : null;
                    Color[] backgrounds = styles.styleStream().map(s -> s.backgroundColor.orElse(null)).distinct().toArray(i -> new Color[i]);
                    backgroundColor = backgrounds.length == 1 ? backgrounds[0] : null;
                } else {
                    int p = area.getCurrentParagraph();
                    int col = area.getCaretColumn();
                    TextStyle style = area.getStyleAtPosition(p, col);
                    bold = style.bold.orElse(false);
                    italic = style.italic.orElse(false);
                    underline = style.underline.orElse(false);
                    strike = style.strikethrough.orElse(false);
                    fontSize = style.fontSize.orElse(-1);
                    fontFamily = style.fontFamily.orElse(null);
                    textColor = style.textColor.orElse(null);
                    backgroundColor = style.backgroundColor.orElse(null);
                }

                int startPar = area.offsetToPosition(selection.getStart(), Forward).getMajor();
                int endPar = area.offsetToPosition(selection.getEnd(), Backward).getMajor();
                List<Paragraph<ParStyle, TextStyle>> pars = area.getParagraphs().subList(startPar, endPar + 1);

                @SuppressWarnings("unchecked")
                Optional<TextAlignment>[] alignments = pars.stream().map(p -> p.getParagraphStyle().alignment).distinct().toArray(Optional[]::new);
                Optional<TextAlignment> alignment = alignments.length == 1 ? alignments[0] : Optional.empty();

                @SuppressWarnings("unchecked")
                Optional<Color>[] paragraphBackgrounds = pars.stream().map(p -> p.getParagraphStyle().backgroundColor).distinct().toArray(Optional[]::new);
                Optional<Color> paragraphBackground = paragraphBackgrounds.length == 1 ? paragraphBackgrounds[0] : Optional.empty();

                updatingToolbar.suspendWhile(() -> {
                    if(bold) {
                        if(!boldAction.getStyleClass().contains("pressed")) {
                            boldAction.getStyleClass().add("pressed");
                        }
                    } else {
                        boldAction.getStyleClass().remove("pressed");
                    }

                    if(italic) {
                        if(!italicAcition.getStyleClass().contains("pressed")) {
                            italicAcition.getStyleClass().add("pressed");
                        }
                    } else {
                        italicAcition.getStyleClass().remove("pressed");
                    }

                    if(underline) {
                        if(!underlineAction.getStyleClass().contains("pressed")) {
                            underlineAction.getStyleClass().add("pressed");
                        }
                    } else {
                        underlineAction.getStyleClass().remove("pressed");
                    }

                    if(strike) {
                        if(!strikethroughAction.getStyleClass().contains("pressed")) {
                            strikethroughAction.getStyleClass().add("pressed");
                        }
                    } else {
                        strikethroughAction.getStyleClass().remove("pressed");
                    }


                    if(fontSize != -1) {
                        fontSizePicker.getSelectionModel().select(fontSize);
                    } else {
                        fontSizePicker.getSelectionModel().clearSelection();
                    }
                });
            }
        });


        VirtualizedScrollPane<StyledTextArea<ParStyle, TextStyle>> vsPane = new VirtualizedScrollPane<>(area);

        areaContainer.setCenter(vsPane);
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
    private Button createButton(String styleClass, Runnable action) {
        Button button = new Button();
        button.getStyleClass().add(styleClass);
        button.setOnAction(evt -> {
            action.run();
            area.requestFocus();
        });
        button.setPrefWidth(20);
        button.setPrefHeight(20);
        return button;
    }

    private ToggleButton createToggleButton(ToggleGroup grp, String styleClass, Runnable action) {
        ToggleButton button = new ToggleButton();
        button.setToggleGroup(grp);
        button.getStyleClass().add(styleClass);
        button.setOnAction(evt -> {
            action.run();
            area.requestFocus();
        });
        button.setPrefWidth(20);
        button.setPrefHeight(20);
        return button;
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

    private void alignLeft() {
        updateParagraphStyleInSelection(ParStyle.alignLeft());
    }

    private void alignCenter() {
        updateParagraphStyleInSelection(ParStyle.alignCenter());
    }

    private void alignRight() {
        updateParagraphStyleInSelection(ParStyle.alignRight());
    }

    private void alignJustify() {
        updateParagraphStyleInSelection(ParStyle.alignJustify());
    }

    private void updateStyleInSelection(Function<StyleSpans<TextStyle>, TextStyle> mixinGetter) {
        IndexRange selection = area.getSelection();
        if(selection.getLength() != 0) {
            StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
            TextStyle mixin = mixinGetter.apply(styles);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
            area.setStyleSpans(selection.getStart(), newStyles);
        }
    }

    private void updateStyleInSelection(TextStyle mixin) {
        IndexRange selection = area.getSelection();
        if (selection.getLength() != 0) {
            StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
            area.setStyleSpans(selection.getStart(), newStyles);
        }
    }

    private void updateParagraphStyleInSelection(Function<ParStyle, ParStyle> updater) {
        IndexRange selection = area.getSelection();
        int startPar = area.offsetToPosition(selection.getStart(), Forward).getMajor();
        int endPar = area.offsetToPosition(selection.getEnd(), Backward).getMajor();
        for(int i = startPar; i <= endPar; ++i) {
            Paragraph<ParStyle, TextStyle> paragraph = area.getParagraph(i);
            area.setParagraphStyle(i, updater.apply(paragraph.getParagraphStyle()));
        }
    }

    private void updateParagraphStyleInSelection(ParStyle mixin) {
        updateParagraphStyleInSelection(style -> style.updateWith(mixin));
    }

    private void updateFontSize(Integer size) {
        if(!updatingToolbar.get()) {
            updateStyleInSelection(TextStyle.fontSize(size));
        }
    }

    private void updateFontFamily(String family) {
        if(!updatingToolbar.get()) {
            updateStyleInSelection(TextStyle.fontFamily(family));
        }
    }

    private void updateTextColor(Color color) {
        if(!updatingToolbar.get()) {
            updateStyleInSelection(TextStyle.textColor(color));
        }
    }

    private void updateBackgroundColor(Color color) {
        if(!updatingToolbar.get()) {
            updateStyleInSelection(TextStyle.backgroundColor(color));
        }
    }

    private void updateParagraphBackground(Color color) {
        if(!updatingToolbar.get()) {
            updateParagraphStyleInSelection(ParStyle.backgroundColor(color));
        }
    }
}
