package rs.acs.uns.sw.govrs.client.fx.domain.tree;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.editor.XMLEditorController;

import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class TreeModel {
    private final TreeView<Element> treeView;
    private final Function<Element, ObservableList<Element>> children;
    private Function<Element, ObservableValue<String>> text;
    private TreeController treeController;

    public TreeModel(Element rootItem, Function<Element, ObservableList<Element>> children,
                     Function<Element, ObservableValue<String>> text, TreeController controller) {
        this.text = text;
        this.children = children;
        this.treeController = controller;

        treeView = new TreeView<>(createTreeItem(rootItem));
        treeView.setEditable(true);
        treeView.setCellFactory(p -> new CustomTextFieldTreeCell(text, treeController.getPreview()));
        treeView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                TreeItem<Element> item = treeView.getSelectionModel().getSelectedItem();
                if (item != null) {
                    treeController.setSelectedElement(item.getValue());
                }
            }
        });
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
