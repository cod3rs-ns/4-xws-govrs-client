package rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.NONE)
public class SearchResult {

    @XmlElement
    private List<SearchObject> set;

    @SuppressWarnings("unused")
    public SearchResult() {
        super();
    }

    public SearchResult(List<SearchObject> set) {
        this.set = set;
    }

    public List<SearchObject> getSet() {
        return set;
    }
}
