package rs.acs.uns.sw.govrs.client.fx.model.tree;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.editor.preview.ActPreview;
import rs.acs.uns.sw.govrs.client.fx.model.Element;

import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class TreeModel {

    private final TreeView<Element> treeView;
    private Function<Element, ObservableValue<String>> text;
    private final Function<Element, ObservableList<Element>> children;
    private final ActPreview preview;

    public TreeModel(Element rootItem, Function<Element, ObservableList<Element>> children,
                     Function<Element, ObservableValue<String>> text, ActPreview preview) {
        this.text = text;
        this.children = children;
        this.preview = preview;

        treeView = new TreeView<>(createTreeItem(rootItem));
        treeView.setEditable(true);
        treeView.setCellFactory(p -> new CustomTextFieldTreeCell(text, preview));
    }

    public TreeView<Element> getTreeView() {
        return treeView;
    }

    private TreeItem<Element> createTreeItem(Element t) {
        TreeItem<Element> item = new TreeItem<>(t);
        children.apply(t).stream().map(this::createTreeItem).forEach(item.getChildren()::add);

        children.apply(t).addListener((ListChangeListener.Change<? extends Element> change) -> {
            while (change.next()) {

                if (change.wasAdded()) {
                    item.getChildren().addAll(change.getAddedSubList().stream()
                            .map(this::createTreeItem).collect(toList()));
                }
                if (change.wasRemoved()) {
                    item.getChildren().removeIf(treeItem -> change.getRemoved()
                            .contains(treeItem.getValue()));
                }
            }
        });

        return item;
    }
}
