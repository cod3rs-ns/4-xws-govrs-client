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
import rs.acs.uns.sw.govrs.client.fx.domain.User;

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
    @FXML
    private Hyperlink citizenHyperlink;

    private MainFXApp app;

    public LoginController() {

    }

    public void setApp(MainFXApp app) {
        this.app = app;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: delete this
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

        loginButton.setOnAction(event -> {
            if (usernameField.getText().equals("predsednik") && passwordField.getText().equals("pass")){
                User u = new User("predsednik", "pass", "Petar", "Petrović", "predsednik");
                app.setLoggedUser(u);
                app.login();
            }
            else if(usernameField.getText().equals("odbornik") && passwordField.getText().equals("pass")){
                User u = new User("odbornik", "pass", "Nikola", "Nikolić", "odbornik");
                app.setLoggedUser(u);
                app.login();
            }
            else {
                usernameField.setText("");
                passwordField.setText("");
                errorLabel.setText("Neuspešna autorizacija!");
            }
        });

        citizenHyperlink.setOnAction(event -> {
            User u = new User("gradjanin", "pass", "Marko", "Marković", "gradjanin");
            app.setLoggedUser(u);
            app.login();
        });
    }
}
