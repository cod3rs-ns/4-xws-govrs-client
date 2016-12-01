package rs.acs.uns.sw.govrs.client.fx.home;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.editor.XMLEditorController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends AnchorPane implements Initializable {

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


        closeButton.setOnAction(actionEvent -> Platform.exit());
        minButton.setOnAction(actionEvent -> app.getStage().setIconified(true));
        maxButton.setOnAction(actionEvent -> toggleMax());

    }

    public void setApp(MainFXApp app) {
        this.app = app;
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
}
