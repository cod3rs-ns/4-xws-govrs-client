package rs.acs.uns.sw.govrs.client.fx.model;

import javafx.collections.ObservableList;

import java.util.function.Function;

/**
 * Created by St_Keky on 22.11.2016..
 */
public class Tacka extends Element {

    public Tacka(String name, String image, ObservableList<Element> children, Function<String, Element> childrenSupplier) {
        super(name, image, children, childrenSupplier);
    }

    public Tacka(String name, String image, ObservableList<Element> children) {
        super(name, image, children);
    }

    public Tacka(String name, String image) {
        super(name, image);
    }

    public Tacka(String name) {
        super(name);
    }

    @Override
    public void createAndAddChild(String name) {
        throw new UnsupportedOperationException("Dot can not have children");
    }
}
