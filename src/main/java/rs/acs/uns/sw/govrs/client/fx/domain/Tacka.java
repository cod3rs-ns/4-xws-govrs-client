package rs.acs.uns.sw.govrs.client.fx.domain;

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

    @Override
    public String createElementOpening() {
        return "<p>";
    }

    @Override
    public String createElementAttrs() {
        return null;
    }

    @Override
    public String createElementContent() {
        return getName();
    }

    @Override
    public String createElementClosing() {
        return "</p>";
    }

    public Tacka(String name) {
        super(name);
    }

    @Override
    public void createAndAddChild(String name) {
        throw new UnsupportedOperationException("Dot can not have children");
    }
}
