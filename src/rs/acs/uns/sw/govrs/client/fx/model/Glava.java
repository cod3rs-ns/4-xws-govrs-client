package rs.acs.uns.sw.govrs.client.fx.model;

import javafx.collections.ObservableList;

import java.util.function.Function;

/**
 * Created by St_Keky on 22.11.2016..
 */
public class Glava extends Element {

    public Glava(String name, String image, ObservableList<Element> children, Function<String, Element> childrenSupplier) {
        super(name, image, children, childrenSupplier);
    }

    public Glava(String name, String image, ObservableList<Element> children) {
        super(name, image, children);
    }

    public Glava(String name, String image) {
        super(name, image);
    }

    public Glava(String name) {
        super(name);
    }

    @Override
    public void createAndAddChild(String name) {
        getChildren().add(new Tacka(name));
    }
}
