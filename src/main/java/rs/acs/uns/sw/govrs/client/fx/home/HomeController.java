package rs.acs.uns.sw.govrs.client.fx.home;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.laws.LawSearchController;
import rs.acs.uns.sw.govrs.client.fx.manager.StateManager;
import rs.acs.uns.sw.govrs.client.fx.util.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Home stage for every user.
 */
public class HomeController extends AnchorPane implements Initializable {
    // === window controls and drag info ===
    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;
    private Rectangle2D winBounds = null;
    private boolean maximized = false;
    // =====================================

    @FXML
    private Button closeButton;
    @FXML
    private Button minButton;
    @FXML
    private Button maxButton;
    @FXML
    private BorderPane container;
    @FXML
    private Label userLabel;
    @FXML
    private Label userTypeLabel;
    @FXML
    private ImageView userImage;
    @FXML
    private VBox actionContainer;
    @FXML
    private AnchorPane dragPane;

    private MainFXApp app;

    private StateManager stateManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stateManager = new StateManager();
        stateManager.setRootContainer(container);
        stateManager.setApp(app);

        // Replaced standard window buttons and their actions
        closeButton.setOnAction(event -> Platform.exit());
        minButton.setOnAction(event -> app.getStage().setIconified(true));
        maxButton.setOnAction(event -> toggleMax());

        // add window dragging
        dragPane.setOnMousePressed(event -> {
            mouseDragOffsetX = event.getSceneX();
            mouseDragOffsetY = event.getSceneY();
        });
        dragPane.setOnMouseDragged(event -> {
            app.getStage().setX(event.getScreenX() - mouseDragOffsetX);
            app.getStage().setY(event.getScreenY() - mouseDragOffsetY);
        });

    }

    public void setApp(MainFXApp app) {
        this.app = app;
        switch (app.getLoggedUser().getType()) {
            case Constants.PRESIDENT:
                setPresidentActions();
                break;
            case Constants.ALDERMAN:
                setAldermanActions();
                break;
            default:
                actionContainer.getChildren().add(createButton(Constants.HOME, this::homeAction));
                break;
        }

        this.userLabel.setText(app.getLoggedUser().getFirstName() + ' ' + app.getLoggedUser().getLastName());
        this.userTypeLabel.setText(app.getLoggedUser().getType());
        stateManager.switchState(Constants.LAW_SEARCH_FXML);
    }

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

    private Button createButton(String type, Runnable action) {
        Button button = new Button();
        button.setPrefHeight(50);
        button.setPrefWidth(50);
        button.getStyleClass().add(type);
        button.setOnAction(event ->
                action.run()
        );
        return button;
    }

    private void action() {
        Logger.getLogger(HomeController.class.getName()).log(Level.INFO, "Button clicked!");
    }

    private void homeAction() {
        stateManager.switchState(Constants.LAW_SEARCH_FXML);
    }

    private void lawAction() {
        stateManager.switchState(Constants.NEW_LAW_FXML);
    }

    private void setAldermanActions() {
        actionContainer.getChildren().add(createButton(Constants.HOME, this::homeAction));
        actionContainer.getChildren().add(createButton(Constants.LAW, this::lawAction));
        actionContainer.getChildren().add(createButton(Constants.AMENDMENT, this::action));
        actionContainer.getChildren().add(createButton(Constants.ALL, this::action));
    }

    private void setPresidentActions() {
        actionContainer.getChildren().add(createButton(Constants.HOME, this::homeAction));
        actionContainer.getChildren().add(createButton(Constants.LAW, this::lawAction));
        actionContainer.getChildren().add(createButton(Constants.AMENDMENT, this::action));
        actionContainer.getChildren().add(createButton(Constants.ALL, this::action));
        actionContainer.getChildren().add(createButton(Constants.VOTE, this::action));
    }

    @FXML
    private void logout() {
        app.logout();
    }

}
