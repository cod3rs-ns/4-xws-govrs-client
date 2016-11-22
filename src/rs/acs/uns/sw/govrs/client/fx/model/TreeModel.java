package rs.acs.uns.sw.govrs.client.fx.model;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;

import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class TreeModel {

    private final TreeView<Element> treeView;

    private final Function<Element, ObservableList<Element>> children;

    public TreeModel(Element rootItem, Function<Element, ObservableList<Element>> children,
                     Function<Element, ObservableValue<String>> text) {

        this.children = children;

        treeView = new TreeView<>(createTreeItem(rootItem));

        treeView.setCellFactory(tv -> new TreeCell<Element>() {
            @Override
            protected void updateItem(Element item, boolean empty) {
                super.updateItem(item, empty);
                textProperty().unbind();
                if (empty) {
                    setText("");
                } else {
                    textProperty().bind(text.apply(item));
                    if (item.getImage() != null)
                        setGraphic(new ImageView(new Image(MainFXApp.class.getResourceAsStream(item.getImage()))));
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
