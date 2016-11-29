package rs.acs.uns.sw.govrs.client.fx.editor.preview;

import javafx.scene.web.WebView;
import rs.acs.uns.sw.govrs.client.fx.model.Element;

/**
 * WebView preview.
 */
public class ActPreview {
    private final WebView webView = new WebView();
    private Element rootElement;
    private int lastScrollX;
    private int lastScrollY;

    public ActPreview(Element root) {
        webView.setFocusTraversable(false);
        webView.setPrefSize(300, 800);
        rootElement = root;
    }

    public javafx.scene.Node getNode() {
        return webView;
    }

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

        webView.getEngine().loadContent(
                "<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "<head>\n"
                        + "<link rel=\"stylesheet\" href=\"" + getClass().getResource("preview_style.css") + "\">\n"
                        + "</head>\n"
                        + "<body" + scrollScript + ">\n"
                        + rootElement.getHtml()
                        + "</body>\n"
                        + "</html>");
    }

    public void scrollY(double value) {
        webView.getEngine().executeScript(
                "window.scrollTo(0, (document.body.scrollHeight - window.innerHeight) * " + value + ");");
    }
}
