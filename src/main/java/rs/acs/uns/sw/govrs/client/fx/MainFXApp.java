package rs.acs.uns.sw.govrs.client.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rs.acs.uns.sw.govrs.client.fx.home.HomeController;
import rs.acs.uns.sw.govrs.client.fx.login.LoginController;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main JavaFX application. Everything Starts here.
 */
public class MainFXApp extends Application {
    private Stage stage;

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        stage.setTitle("GovRS client");
        stage.setX(200);
        stage.setY(200);
        stage.initStyle(StageStyle.UNDECORATED);
        gotoLogin();
        stage.show();
    }

    /**
     * Switch scene to Login.
     */
    private void gotoLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent("/login/Login.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(MainFXApp.class.getName()).log(Level.SEVERE, "Login FXML problem", ex);
        }
    }

    /**
     * Switch scene to Home.
     */
    private void gotoHome() {
        try {
            HomeController home = (HomeController) replaceSceneContent("/home/Home.fxml");
            home.setAppAndInitializeActions(this);
        } catch (Exception ex) {
            Logger.getLogger(MainFXApp.class.getName()).log(Level.SEVERE, "Home FXML problem", ex);
        }
    }

    /**
     * Replaces current scene content.
     *
     * @param fxml FXML file specifying scene content
     * @return scene controller
     */
    private Initializable replaceSceneContent(String fxml) {
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MainFXApp.class.getResource(fxml));
        try (InputStream in = MainFXApp.class.getResourceAsStream(fxml)) {
            AnchorPane page = loader.load(in);
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.sizeToScene();
        } catch (IOException ex) {
            Logger.getLogger(MainFXApp.class.getName()).log(Level.SEVERE, "Problem with scene changing", ex);
        }
        return (Initializable) loader.getController();
    }

    /**
     * Log-in provided user and redirect to Home stage.
     */
    public void login() {
        Logger.getLogger(MainFXApp.class.getName()).log(Level.INFO, RestClientProvider.getInstance().getUser().toString());
        gotoHome();
    }

    /**
     * Log-out current user and redirect to Login stage.
     */
    public void logout() {
        RestClientProvider.getInstance().setUser(null);
        RestClientProvider.getInstance().setToken(null);
        gotoLogin();
    }

    public Stage getStage() {
        return stage;
    }
}
