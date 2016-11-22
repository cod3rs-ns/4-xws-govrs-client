package rs.acs.uns.sw.govrs.client.fx.model;

import javafx.collections.ObservableList;

import java.util.function.Function;

public class Propis extends Element {

    public Propis(String name, String image, ObservableList<Element> children, Function<String, Element> childrenSupplier) {
        super(name, image, children, childrenSupplier);
    }

    public Propis(String name, String image, ObservableList<Element> children) {
        super(name, image, children);
    }

    public Propis(String name, String image) {
        super(name, image);
    }

    public Propis(String name) {
        super(name);
    }

    @Override
    public void createAndAddChild(String name) {
        getChildren().add(new Glava(name));
    }
}
