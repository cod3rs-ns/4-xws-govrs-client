package rs.acs.uns.sw.govrs.client.fx.components;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Vertical box with 3 small buttons for window closing, maximizing and minimizing.
 */
public class WindowButtons extends VBox {
    private Stage stage;
    private Rectangle2D winBounds = null;
    private boolean maximized = false;

    public WindowButtons(final Stage stage) {
        super(4);
        this.stage = stage;

        // close button
        Button closeBtn = new Button();
        closeBtn.setId("win-close-btn");
    }



    public boolean isMaximized() {
        return maximized;
    }
}


