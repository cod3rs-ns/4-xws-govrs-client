package rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper;

import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;
import rs.acs.uns.sw.govrs.client.fx.util.StringCleaner;

import javax.xml.bind.annotation.*;
import java.util.List;

@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class SearchObject {

    @XmlElement
    private Double confidence;

    @XmlElement
    private String path;

    @XmlElement
    private String preview;

    @XmlElement
    private Metadata metadata;

    public SearchObject() {
        super();
    }

    public SearchObject(Double confidence, String path, String preview, Metadata metadata) {
        this.setConfidence(confidence);
        this.setPath(path);
        this.setPreviews(preview);
        this.setMetadata(metadata);

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {

        try {
            this.path = "/api" + (path.split("\"")[1]).split("\\.")[0];
        } catch (Exception e) {
            this.path = path;
        }
    }

    public String getPreview() {
        return preview;
    }

    public void setPreviews(String preview) {
        this.preview = preview;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "SearchObject{" +
                "confidence=" + confidence +
                ", path='" + path + '\'' +
                ", preview=" + preview +
                ", metadata=" + metadata +
                '}';
    }
}
