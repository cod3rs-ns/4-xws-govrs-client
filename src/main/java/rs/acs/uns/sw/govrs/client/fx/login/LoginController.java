package rs.acs.uns.sw.govrs.client.fx.login;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.AppUser;
import rs.acs.uns.sw.govrs.client.fx.util.Constants;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    @FXML
    private ProgressIndicator indicator;

    private MainFXApp app;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: delete this
        // This is only for test purpose, because it speeds up log-in process
        indicator.setVisible(false);
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
            try {
                indicator.setVisible(true);
                RestClientProvider.getInstance().userProperty().addListener((observable, oldValue, newValue) -> {
                    indicator.setVisible(false);
                    if(newValue!=null) app.login();
                });
                RestClientProvider.getInstance().login(usernameField.getText(), passwordField.getText());
            } catch (Exception e) {
                indicator.setVisible(false);
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Invalid user information.");
                usernameField.setText("");
                passwordField.setText("");
                errorLabel.setText("Neuspešna autorizacija!");
            }
        });

        // citizen login
        citizenHyperlink.setOnAction(event -> {
            AppUser user = new AppUser();
            user.setId("gradjanin");
            user.setKorisnickoIme("gradjanin");
            user.setIme("ulogovan");
            user.setPrezime("kao");
            user.setUloga(Constants.CITIZEN);
            RestClientProvider.getInstance().setUser(user);
            app.login();
        });
    }

    public void setApp(MainFXApp app) {
        this.app = app;
    }
}
