package rs.acs.uns.sw.govrs.client.fx.domain.tree;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.editor.preview.HtmlPreview;

import java.util.function.Function;

/**
 * Custom Tree Cell based on Element. Also handles creation of ContextMenu on items.
 */
public class CustomTextFieldTreeCell extends TreeCell<Element> {
    private TextField textField;
    private ContextMenu contextMenu;
    private Function<Element, ObservableValue<String>> text;
    private HtmlPreview preview;
    private ContextMenuHandler contextMenuHandler;

    public CustomTextFieldTreeCell(Function<Element, ObservableValue<String>> text, HtmlPreview preview) {
        this.text = text;
        this.preview = preview;
        this.contextMenuHandler = new ContextMenuHandler();
        this.contextMenu = new ContextMenu();
    }

    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }
        textProperty().unbind();
        setText("");
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().getElementName());
        setGraphic(new ImageView(new Image(MainFXApp.class.getResourceAsStream(getItem().getImage()))));
    }

    @Override
    public void updateItem(Element item, boolean empty) {
        super.updateItem(item, empty);
        textProperty().unbind();
        graphicProperty().unbind();
        if (empty) {
            setText("");
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(item.getElementName());
                }
                setText("");
                setGraphic(textField);
            } else {
                textProperty().bind(text.apply(item));
                if (item.getImage() != null) {
                    setGraphic(new ImageView(new Image(MainFXApp.class.getResourceAsStream(item.getImage()))));
                }
                contextMenu = contextMenuHandler.createContextMenu(getItem());
                setContextMenu(contextMenu);
                preview.update();
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getItem().getElementName());
        textField.setOnKeyReleased(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                getItem().setElementName(textField.getText());
                commitEdit(getItem());
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
