package rs.acs.uns.sw.govrs.client.fx.home;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import rs.acs.uns.sw.govrs.client.fx.editor.XMLEditorController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends AnchorPane implements Initializable {
    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;
    private Rectangle2D winBounds = null;
    private boolean maximized = false;

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

    public MainFXApp getApp() {
        return app;
    }

    private MainFXApp app;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApp.class.getResource("editor/XMLEditor.fxml"));
        AnchorPane xmleditor = null;
        try {
            xmleditor = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        container.setCenter(xmleditor);

        // Give the controller access to the main app.
        XMLEditorController controller = loader.getController();
        controller.setMainApp(app);

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
            case "predsednik":
                actionContainer.getChildren().add(createButton("a-home", this::action));
                actionContainer.getChildren().add(createButton("a-law", this::action));
                actionContainer.getChildren().add(createButton("a-amendment", this::action));
                actionContainer.getChildren().add(createButton("a-all", this::action));
                actionContainer.getChildren().add(createButton("a-vote", this::action));
                break;
            case "odbornik":
                actionContainer.getChildren().add(createButton("a-home", this::action));
                actionContainer.getChildren().add(createButton("a-law", this::action));
                actionContainer.getChildren().add(createButton("a-amendment", this::action));
                actionContainer.getChildren().add(createButton("a-all", this::action));
                break;
            default:
                actionContainer.getChildren().add(createButton("a-home", this::action));
                break;
        }

        this.userLabel.setText(app.getLoggedUser().getFirstName() + ' ' + app.getLoggedUser().getLastName());
        this.userTypeLabel.setText(app.getLoggedUser().getType());
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
        userImage.setImage(new Image(this.getClass().getResourceAsStream("../images/user-info-hover.png")));
    }

    @FXML
    private void userInfoExit() {
        userImage.setImage(new Image(this.getClass().getResourceAsStream("../images/user-info.png")));
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
        System.out.println("Button clicked");
    }

    @FXML
    private void logout() {
        app.logout();
    }
}
