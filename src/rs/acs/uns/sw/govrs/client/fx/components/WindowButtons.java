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
        closeBtn.setOnAction(actionEvent -> Platform.exit());

        // minimize button
        Button minBtn = new Button();
        minBtn.setId("win-min-btn");
        minBtn.setOnAction(actionEvent -> stage.setIconified(true));

        // maximize button
        Button maxBtn = new Button();
        maxBtn.setId("win-max-btn");
        maxBtn.setOnAction(actionEvent -> toggleMax());

        getChildren().addAll(closeBtn, maxBtn, minBtn);
    }

    public void toggleMax() {
        final Screen screen = Screen.getScreensForRectangle(stage.getX(), stage.getY(), 1, 1).get(0);
        if (maximized) {
            maximized = false;
            if (winBounds != null) {
                stage.setX(winBounds.getMinX());
                stage.setY(winBounds.getMinY());
                stage.setWidth(winBounds.getWidth());
                stage.setHeight(winBounds.getHeight());
            }
        } else {
            maximized = true;
            winBounds = new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
            stage.setX(screen.getVisualBounds().getMinX());
            stage.setY(screen.getVisualBounds().getMinY());
            stage.setWidth(screen.getVisualBounds().getWidth());
            stage.setHeight(screen.getVisualBounds().getHeight());
        }
    }

    public boolean isMaximized() {
        return maximized;
    }
}


