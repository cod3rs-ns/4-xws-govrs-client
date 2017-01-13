package rs.acs.uns.sw.govrs.client.fx.editor.preview;

import javafx.scene.web.WebView;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.render.Renderer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.StringWriter;

/**
 * WebView preview.
 */
public class HtmlPreview {
    private final WebView webView = new WebView();
    private Element rootElement;
    private Renderer renderer;
    private Class<?> rootClass;
    private int lastScrollX;
    private int lastScrollY;

    public HtmlPreview(Element root, String elementName, Class<?> rootClassType) {
        try {
            renderer = new Renderer(elementName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        webView.setFocusTraversable(false);
        webView.setPrefSize(300, 800);
        rootElement = root;
        rootClass = rootClassType;
    }

    public WebView getNode() {
        return webView;
    }

    /**
     * Refreshes view.
     */
    public void update() {
        if (!webView.getEngine().getLoadWorker().isRunning()) {
            Object scrollX = webView.getEngine().executeScript("window.scrollX");
            Object scrollY = webView.getEngine().executeScript("window.scrollY");
            lastScrollX = (scrollX instanceof Number) ? ((Number) scrollX).intValue() : 0;
            lastScrollY = (scrollY instanceof Number) ? ((Number) scrollY).intValue() : 0;
        }

        String scrollScript = (lastScrollX > 0 || lastScrollY > 0)
                ? ("  onload='window.scrollTo(" + lastScrollX + ", " + lastScrollY + ");'")
                : "";

        String xhtml = "";
        try {
            JAXBContext context = JAXBContext.newInstance(rootClass);
            Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(rootElement, writer);
            String xml = writer.toString();
            xhtml = renderer.toHtml(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // split over opening body tag
        String[] parts = xhtml.split("<body");

        // insert script into document
        StringBuilder sb = new StringBuilder();
        sb.append(parts[0]);
        sb.append("<body");
        sb.append(scrollScript);
        sb.append(parts[1]);

        webView.getEngine().loadContent(sb.toString());
    }
}
