package rs.acs.uns.sw.govrs.client.fx.domain.tree;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import rs.acs.uns.sw.govrs.client.fx.editor.preview.ActPreview;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;

import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class TreeModel {
    private final TreeView<Element> treeView;
    private final Function<Element, ObservableList<Element>> children;
    private final ActPreview preview;
    private Function<Element, ObservableValue<String>> text;

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