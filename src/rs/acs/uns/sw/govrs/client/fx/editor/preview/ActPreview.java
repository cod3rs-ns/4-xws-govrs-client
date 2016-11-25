package rs.acs.uns.sw.govrs.client.fx.editor.preview;


import javafx.scene.web.WebView;
import rs.acs.uns.sw.govrs.client.fx.model.Element;

import java.nio.file.Path;


/**
 * WebView preview.
 *
 * @author Karl Tauber
 */
public class ActPreview {
    private final WebView webView = new WebView();
    private int lastScrollX;
    private int lastScrollY;

    public ActPreview() {
        webView.setFocusTraversable(false);
        webView.setPrefSize(300, 800);
    }

    public javafx.scene.Node getNode() {
        return webView;
    }

    public void update(Path path, Element root) {
        if (!webView.getEngine().getLoadWorker().isRunning()) {
            // get window.scrollX and window.scrollY from web engine,
            // but only no worker is running (in this case the result would be zero)
            Object scrollXobj = webView.getEngine().executeScript("window.scrollX");
            Object scrollYobj = webView.getEngine().executeScript("window.scrollY");
            lastScrollX = (scrollXobj instanceof Number) ? ((Number) scrollXobj).intValue() : 0;
            lastScrollY = (scrollYobj instanceof Number) ? ((Number) scrollYobj).intValue() : 0;
        }

        String base = (path != null)
                ? ("<base href=\"" + path.getParent().toUri().toString() + "\">\n")
                : "";
        String scrollScript = (lastScrollX > 0 || lastScrollY > 0)
                ? ("  onload='window.scrollTo(" + lastScrollX + ", " + lastScrollY + ");'")
                : "";

        webView.getEngine().loadContent(
                "<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "<head>\n"
                        + "<link rel=\"stylesheet\" href=\"" + getClass().getResource("markdownpad-github.css") + "\">\n"
                        + base
                        + "</head>\n"
                        + "<body" + scrollScript + ">\n"
                        + root.getHtml()
                        + "</body>\n"
                        + "</html>");
    }

    public void scrollY(double value) {
        webView.getEngine().executeScript(
                "window.scrollTo(0, (document.body.scrollHeight - window.innerHeight) * " + value + ");");
    }
}
