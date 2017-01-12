package rs.acs.uns.sw.govrs.client.fx.domain;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.*;

import javax.xml.bind.annotation.XmlTransient;
import java.util.function.Function;

public abstract class Element {
    private final Function<String, Element> childrenSupplier;
    private final StringProperty elementContent = new SimpleStringProperty();
    private String image = "/images/dot.png";
    private ObservableList<Element> children = FXCollections.observableArrayList();

    public Element(String name, String image, ObservableList<Element> children, Function<String, Element> childrenSupplier) {
        this.image = image;
        this.children = children;
        this.childrenSupplier = childrenSupplier;
        setName(name);
    }

    public Element(){
        this.childrenSupplier = null;
    }

    public Element(String name, String image, ObservableList<Element> children) {
        this(name, image, children, n -> null);
    }

    public Element(String name, String image) {
        this(name, image, FXCollections.observableArrayList(), n -> null);
    }

    public Element(String name) {
        this(name, "/images/dot.png", FXCollections.observableArrayList(), n -> null);
    }

    public abstract String getName();
    public abstract void setName(String name);
    public abstract StringProperty nameProperty();

    public String getElementContent() {
        return elementContent.get();
    }

    public StringProperty elementContentProperty() {
        return elementContent;
    }

    public void setElementContent(String elementContent) {
        this.elementContent.set(elementContent);
    }

    public ObservableList<Element> getChildren() {
        return children;
    }

    public abstract void initChildrenObservableList();

    public abstract void createAndAddChild(String name);

    public String getImage() {
        if (this instanceof Law) {
            return "/images/law.png";
        }
        if (this instanceof Chapter) {
            return "/images/chapter.png";
        }
        if (this instanceof Part) {
            return "/images/part.png";
        }
        if (this instanceof Section) {
            return "/images/section.png";
        }
        if (this instanceof Subsection) {
            return "/images/subsection.png";
        }
        if (this instanceof Article) {
            return "/images/article.png";
        }
        if (this instanceof Paragraph) {
            return "/images/paragraph.png";
        }
        if (this instanceof Clause) {
            return "/images/clause.png";
        }
        if (this instanceof Subclause) {
            return "/images/subclause.png";
        }
        if (this instanceof Item) {
            return "/images/item.png";
        }
        if (this instanceof StringElement) {
            return "/images/text.png";
        }


        return image;
    }

    public abstract String createElementOpening();

    public abstract String createElementAttrs();

    public abstract String createElementContent();

    public abstract String createElementClosing();

    public String getHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append(createElementOpening());
        sb.append(createElementContent());
        for (Element child : getChildren()
                ) {
            sb.append(child.getHtml());
        }
        sb.append(createElementClosing());
        return sb.toString();
    }
}
