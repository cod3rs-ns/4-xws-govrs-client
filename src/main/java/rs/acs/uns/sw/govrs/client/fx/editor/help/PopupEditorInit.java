package rs.acs.uns.sw.govrs.client.fx.editor.help;

import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.util.ElementTypes;

public class PopupEditorInit {
    private String parentIdBase;
    private boolean createNew = true;
    private Element element;
    private boolean saved;
    private ElementTypes typeOfElement;


    public PopupEditorInit(String parentIdBase, boolean createNew, Element element, ElementTypes typeOfElement) {
        this.parentIdBase = parentIdBase;
        this.createNew = createNew;
        this.element = element;
        this.saved = false;
        this.typeOfElement = typeOfElement;
    }
    public String getParentIdBase() {
        return parentIdBase;
    }

    public void setParentIdBase(String parentIdBase) {
        this.parentIdBase = parentIdBase;
    }

    public boolean isCreateNew() {
        return createNew;
    }

    public void setCreateNew(boolean createNew) {
        this.createNew = createNew;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public ElementTypes getTypeOfElement() {
        return typeOfElement;
    }

    public void setTypeOfElement(ElementTypes typeOfElement) {
        this.typeOfElement = typeOfElement;
    }
}
