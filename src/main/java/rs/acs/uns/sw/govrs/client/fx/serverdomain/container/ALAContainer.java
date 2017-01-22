package rs.acs.uns.sw.govrs.client.fx.serverdomain.container;


import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Amendment;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;

public class ALAContainer {
    private Law law;
    private Element currentSubject;
    private Element selectedSubject;

    public String lawId;
    public String currentElementId;

    public ALAContainer(String lawId, String currentElementId, Amendment currentAmendment) {
        this.lawId = lawId; this.currentElementId = currentElementId;
    }
}
