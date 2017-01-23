package rs.acs.uns.sw.govrs.client.fx.domain.tree;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;

import java.util.function.Function;

public class ElementSelectorTreeCell extends TreeCell<Element>{
    private Function<Element, ObservableValue<String>> text;

    public ElementSelectorTreeCell(Function<Element, ObservableValue<String>> text) {
        this.text = text;
    }

    @Override
    protected void updateItem(Element item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty) {
            textProperty().bind(text.apply(item));
            setGraphic(new ImageView(new Image(MainFXApp.class.getResourceAsStream(item.getImage()))));
        }
    }
}
