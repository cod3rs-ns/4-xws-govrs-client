package rs.acs.uns.sw.govrs.client.fx.domain.tree;

import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.editor.preview.HtmlPreview;

public interface TreeController {
    public HtmlPreview getPreview();
    public void setSelectedElement(Element element);
}
