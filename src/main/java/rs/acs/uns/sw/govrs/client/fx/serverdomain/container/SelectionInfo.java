package rs.acs.uns.sw.govrs.client.fx.serverdomain.container;


import rs.acs.uns.sw.govrs.client.fx.util.ElementTypes;

public class SelectionInfo {
    public String lawId;
    public String elementId;
    public ElementTypes elementType;

    public SelectionInfo(String lawId, String elementId, ElementTypes elementType) {
        this.lawId = lawId;
        this.elementId = elementId;
        this.elementType = elementType;
    }
}
