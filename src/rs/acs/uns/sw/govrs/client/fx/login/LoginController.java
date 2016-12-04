package rs.acs.uns.sw.govrs.client.fx.login;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.domain.User;
import rs.acs.uns.sw.govrs.client.fx.util.Constants;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls user log-in.
 */
public class LoginController extends AnchorPane implements Initializable {
    // mouse drag offset for window moving
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
    @FXML
    private Hyperlink citizenHyperlink;

    private MainFXApp app;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: delete this
        // This is only for test purpose, because it speeds up log-in process
        usernameField.setText("predsednik");
        passwordField.setText("pass");

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

        // TODO: implement real login functionality
        loginButton.setOnAction(event -> {
            if (usernameField.getText().equals(Constants.PRESIDENT) && "pass".equals(passwordField.getText())) {
                User u = new User("predsednik", "pass", "Petar", "Petrović", Constants.PRESIDENT);
                app.login(u);
            } else if (usernameField.getText().equals(Constants.ALDERMAN) && "pass".equals(passwordField.getText())) {
                User u = new User("odbornik", "pass", "Nikola", "Nikolić", Constants.ALDERMAN);
                app.login(u);
            } else {
                usernameField.setText("");
                passwordField.setText("");
                errorLabel.setText("Neuspešna autorizacija!");
            }
        });

        // citizen login
        citizenHyperlink.setOnAction(event -> {
            User u = new User("gradjanin", "pass", "Marko", "Marković", Constants.CITIZEN);
            app.login(u);
        });
    }

    public void setApp(MainFXApp app) {
        this.app = app;
    }
}
