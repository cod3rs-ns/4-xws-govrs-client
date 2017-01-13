package rs.acs.uns.sw.govrs.client.fx.editor.preview;

import javafx.scene.web.WebView;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.render.Transformers;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.StringWriter;

/**
 * WebView preview.
 */
public class ActPreview {
    private final WebView webView = new WebView();
    private Element rootElement;
    private Transformers renderer;
    private int lastScrollX;
    private int lastScrollY;

    public ActPreview(Element root, String elementName) {
        try {
            renderer = new Transformers(elementName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        webView.setFocusTraversable(false);
        webView.setPrefSize(300, 800);
        rootElement = root;
    }

    public WebView getNode() {
        return webView;
    }

    public void update() {
        /*
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
            JAXBContext context = JAXBContext.newInstance(Law.class);
            Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(rootElement, writer);
            String xml = writer.toString();
            xhtml = renderer.toHtml(xml);
            System.out.println(xhtml);
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
        */
    }

}
