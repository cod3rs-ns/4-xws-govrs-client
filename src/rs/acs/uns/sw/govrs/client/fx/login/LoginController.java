package rs.acs.uns.sw.govrs.client.fx.login;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController extends AnchorPane implements Initializable {
    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button closeButton;
    @FXML
    private Label errorLabel;
    @FXML
    private AnchorPane mainContainer;

    private MainFXApp app;

    public LoginController() {

    }

    public void setApp(MainFXApp app) {
        this.app = app;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorLabel.setText("");
        // add window dragging
        mainContainer.setOnMousePressed(event -> {
            mouseDragOffsetX = event.getSceneX();
            mouseDragOffsetY = event.getSceneY();
        });
        mainContainer.setOnMouseDragged(event -> {
            app.getStage().setX(event.getScreenX() - mouseDragOffsetX);
            app.getStage().setY(event.getScreenY() - mouseDragOffsetY);
        });

        // close app
        closeButton.setOnAction(event -> Platform.exit());

        loginButton.setOnAction(event -> {
            if (usernameField.getText().equals("admin") && passwordField.getText().equals("admin")){
                app.login();
            } else {
                usernameField.setText("");
                passwordField.setText("");
                errorLabel.setText("Neuspešna autorizacija!");
            }
        });
    }
}
