package rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper;

import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Part;

import javax.xml.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({Part.class, Law.class})
public class SearchResult {

    private List<Object> set = new LinkedList<>();

    public SearchResult() {
        super();
    }

    public SearchResult(List<Object> set) {
        this.set = set;
    }

    @XmlMixed
    @XmlAnyElement(lax = true)
    public List<Object> getSet() {
        return set;
    }
}
