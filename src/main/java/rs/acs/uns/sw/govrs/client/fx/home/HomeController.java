package rs.acs.uns.sw.govrs.client.fx.home;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import org.controlsfx.control.StatusBar;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.manager.StateManager;
import rs.acs.uns.sw.govrs.client.fx.util.Constants;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Home stage for every user.
 */
public class HomeController extends AnchorPane implements Initializable {
    /**
     * App's status bar. Providing feedback to active user.
     */
    @FXML
    public StatusBar statusBar;
    // ===== window controls and drag info =====
    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;
    private Rectangle2D winBounds = null;
    private double resizeDragOffsetX;
    private double resizeDragOffsetY;
    private double minWidth = 1200;
    private double minHeight = 600;
    // =========================================
    private boolean maximized = false;
    // ======== window's action buttons ========
    @FXML
    private Button closeButton;
    @FXML
    private Button minButton;
    // =========================================
    @FXML
    private Button maxButton;
    // ============= user info ui ==============
    @FXML
    private Label userLabel;
    @FXML
    private Label userTypeLabel;
    // =========================================
    @FXML
    private ImageView userImage;
    // ======== resize and movement ui =========
    @FXML
    private AnchorPane dragPane;
    // =========================================
    @FXML
    private Region resizeButton;
    /**
     * Component which contains main active UI element dependent of state.
     */
    @FXML
    private BorderPane mainRootContainer;
    /**
     * Side actions container.
     */
    @FXML
    private VBox actionContainer;
    private MainFXApp app;
    private StateManager stateManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // create and init State Manager
        stateManager = new StateManager(this);
        stateManager.setApp(app);

        // replaced standard window buttons and their actions
        closeButton.setOnAction(event -> Platform.exit());
        minButton.setOnAction(event -> app.getStage().setIconified(true));
        maxButton.setOnAction(event -> toggleMax());

        // add window dragging
        dragPane.setOnMousePressed(event -> {
            if (!maximized) {
                mouseDragOffsetX = event.getSceneX();
                mouseDragOffsetY = event.getSceneY();
            }
        });
        dragPane.setOnMouseDragged(event -> {
            if (!maximized) {
                app.getStage().setX(event.getScreenX() - mouseDragOffsetX);
                app.getStage().setY(event.getScreenY() - mouseDragOffsetY);
            }
        });
        connectResizeButton();
    }

    /**
     * Toggle Maximized window mode.
     */
    private void toggleMax() {
        final Screen screen = Screen.getScreensForRectangle(app.getStage().getX(), app.getStage().getY(), 1, 1).get(0);
        if (maximized) {
            maximized = false;
            if (winBounds != null) {
                app.getStage().setX(winBounds.getMinX());
                app.getStage().setY(winBounds.getMinY());
                app.getStage().setWidth(winBounds.getWidth());
                app.getStage().setHeight(winBounds.getHeight());
            }
        } else {
            maximized = true;
            winBounds = new Rectangle2D(app.getStage().getX(), app.getStage().getY(), app.getStage().getWidth(), app.getStage().getHeight());
            app.getStage().setX(screen.getVisualBounds().getMinX());
            app.getStage().setY(screen.getVisualBounds().getMinY());
            app.getStage().setWidth(screen.getVisualBounds().getWidth());
            app.getStage().setHeight(screen.getVisualBounds().getHeight());
        }
    }

    @FXML
    private void userInfoEnter() {
        userImage.setImage(new Image(MainFXApp.class.getResourceAsStream("/images/user-info-hover.png")));
    }

    @FXML
    private void userInfoExit() {
        userImage.setImage(new Image(MainFXApp.class.getResourceAsStream("/images/user-info.png")));
    }

    /**
     * Utility function for creating Button object.
     *
     * @param type   type of button
     * @param action action which will be executed on mouse click
     * @return created Button
     */
    private Button createButton(String type, Runnable action, String tooltipText) {
        Button button = new Button();
        button.setPrefHeight(50);
        button.setPrefWidth(50);
        button.getStyleClass().add(type);
        Tooltip t = new Tooltip(tooltipText);
        t.setFont(new Font("System", 12));
        Tooltip.install(button, t);
        button.setOnAction(event ->
                action.run()
        );
        return button;
    }

    /**
     * Test action
     * TODO this is for test purpose only.
     */
    private void action() {
        Logger.getLogger(HomeController.class.getName()).log(Level.INFO, "Button clicked!");
    }

    /**
     * Action that switches to Search state.
     */
    private void searchAction() {
        stateManager.switchState(Constants.LAW_SEARCH_FXML);
    }

    /**
     * Action that switches to Editor state.
     */
    private void lawAction() {
        stateManager.switchState(Constants.LAW_EDITOR_FXML);
    }

    /**
     * Initializes actions for user - Alderman.
     */
    private void setAldermanActions() {
        actionContainer.getChildren().add(createButton(Constants.SEARCH, this::searchAction, "Početna"));
        actionContainer.getChildren().add(createButton(Constants.LAW, this::lawAction, "Propis"));
        actionContainer.getChildren().add(createButton(Constants.AMENDMENT, this::action, "Amandman"));
        actionContainer.getChildren().add(createButton(Constants.ALL, this::action, "Moji predlozi"));
    }

    /**
     * Initializes actions for user - President.
     */
    private void setPresidentActions() {
        actionContainer.getChildren().add(createButton(Constants.SEARCH, this::searchAction, "Početna"));
        actionContainer.getChildren().add(createButton(Constants.LAW, this::lawAction, "Propis"));
        actionContainer.getChildren().add(createButton(Constants.AMENDMENT, this::action, "Amandman"));
        actionContainer.getChildren().add(createButton(Constants.ALL, this::action, "Moji predlozi"));
        actionContainer.getChildren().add(createButton(Constants.VOTE, this::action, "Skupština"));
    }

    /**
     * Method that <strong>MUST</strong> be called after initialize();
     *
     * @param app main application reference
     */
    public void setAppAndInitializeActions(MainFXApp app) {
        this.app = app;
        switch (app.getLoggedUser().getType()) {
            case Constants.PRESIDENT:
                setPresidentActions();
                break;
            case Constants.ALDERMAN:
                setAldermanActions();
                break;
            default:
                actionContainer.getChildren().add(createButton(Constants.SEARCH, this::searchAction, "Početna"));
                break;
        }
        // user info
        this.userLabel.setText(app.getLoggedUser().getFirstName() + ' ' + app.getLoggedUser().getLastName());
        this.userTypeLabel.setText(app.getLoggedUser().getType());

        // first state
        stateManager.switchState(Constants.LAW_SEARCH_FXML);
    }

    /**
     * Initializes listeners for resize functionality.
     */
    private void connectResizeButton() {
        resizeButton.setId("window-resize-button");
        resizeButton.setPrefSize(11, 11);
        resizeButton.setOnMousePressed(e -> {
            maximized = false;
            resizeDragOffsetX = (app.getStage().getX() + app.getStage().getWidth()) - e.getScreenX();
            resizeDragOffsetY = (app.getStage().getY() + app.getStage().getHeight()) - e.getScreenY();
            e.consume();
        });
        resizeButton.setOnMouseDragged(e -> {
            ObservableList<Screen> screens = Screen.getScreensForRectangle(app.getStage().getX(), app.getStage().getY(), 1, 1);
            final Screen screen;
            if (screens.size() > 0) {
                screen = Screen.getScreensForRectangle(app.getStage().getX(), app.getStage().getY(), 1, 1).get(0);
            } else {
                screen = Screen.getScreensForRectangle(0, 0, 1, 1).get(0);
            }
            Rectangle2D visualBounds = screen.getVisualBounds();
            double maxX = Math.min(visualBounds.getMaxX(), e.getScreenX() + resizeDragOffsetX);
            double maxY = Math.min(visualBounds.getMaxY(), e.getScreenY() - resizeDragOffsetY);
            app.getStage().setWidth(Math.max(minWidth, maxX - app.getStage().getX()));
            app.getStage().setHeight(Math.max(minHeight, maxY - app.getStage().getY()));
            e.consume();
        });
    }

    /**
     * Perform User's logout functionality.
     */
    @FXML
    private void logout() {
        app.logout();
    }

    public BorderPane getMainRootContainer() {
        return mainRootContainer;
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

}
