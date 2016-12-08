package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import rs.acs.uns.sw.govrs.client.fx.domain.Element;


public class StringElement extends Element{

    public StringElement(String content) {
        super();
        setElementContent(content);
    }

    @Override
    public void initChildrenObservableList() {

    }

    @Override
    public void createAndAddChild(String name) {

    }

    @Override
    public String createElementOpening() {
        return null;
    }

    @Override
    public String createElementAttrs() {
        return null;
    }

    @Override
    public String createElementContent() {
        return null;
    }

    @Override
    public String createElementClosing() {
        return null;
    }
}
