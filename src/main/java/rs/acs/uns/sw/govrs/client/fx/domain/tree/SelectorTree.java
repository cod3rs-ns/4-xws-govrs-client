package rs.acs.uns.sw.govrs.client.fx.domain.tree;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import rs.acs.uns.sw.govrs.client.fx.amendments.ElementPicker;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.*;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.ItemWrapper;
import rs.acs.uns.sw.govrs.client.fx.util.ElementType;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SelectorTree {
    private final TreeView<Element> treeView;
    private final Function<Element, ObservableList<Element>> children;
    private Function<Element, ObservableValue<String>> text;
    private ElementPicker controller;

    public SelectorTree(Element rootItem, Function<Element, ObservableList<Element>> children,
                     Function<Element, ObservableValue<String>> text, ElementPicker controller) {
        this.text = text;
        this.children = children;
        this.controller = controller;

        treeView = new TreeView<>(createTreeItem(rootItem));
        treeView.setEditable(false);
        treeView.setCellFactory(p ->  new ElementSelectorTreeCell(text));
        treeView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                TreeItem<Element> item = treeView.getSelectionModel().getSelectedItem();
                if (item != null) {
                    Element element = item.getValue();
                    if (element instanceof Article || element instanceof Paragraph || element instanceof Clause || element instanceof Subclause || element instanceof ItemWrapper) {
                        controller.selectedLabel.setText(element.idProperty().get());
                        controller.setSelectedId(element.idProperty().get());
                        controller.setSelectedElement(element);
                        Logger.getLogger(getClass().getName()).log(Level.INFO, element.toString());
                        if (element instanceof Article) {
                            controller.setSelectedType(ElementType.Article);
                        } else if (element instanceof Paragraph) {
                            controller.setSelectedType(ElementType.Paragraph);
                        } else if (element instanceof Clause) {
                            controller.setSelectedType(ElementType.Clause);
                        } else if (element instanceof Subclause) {
                            controller.setSelectedType(ElementType.Subclause);
                        } else if (element instanceof ItemWrapper) {
                            controller.setSelectedType(ElementType.Item);
                        } else {
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Invalid selection type!");
                        }
                    }
                }
            }
        });
    }

    public TreeView<Element> getTreeView() {
        return treeView;
    }

    private TreeItem<Element> createTreeItem(Element t) {
        TreeItem<Element> item = new TreeItem<>(t, new ImageView(new Image(t.getImage())));
        children.apply(t).stream().map(this::createTreeItem).forEach(item.getChildren()::add);
        return item;
    }
}
