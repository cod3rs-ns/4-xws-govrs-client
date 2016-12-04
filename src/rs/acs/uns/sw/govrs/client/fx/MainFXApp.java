package rs.acs.uns.sw.govrs.client.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rs.acs.uns.sw.govrs.client.fx.domain.User;
import rs.acs.uns.sw.govrs.client.fx.home.HomeController;
import rs.acs.uns.sw.govrs.client.fx.login.LoginController;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main JavaFX application. Everything Starts here.
 */
public class MainFXApp extends Application {
    private User loggedUser;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        this.stage.setTitle("GovRS client");
        this.stage.initStyle(StageStyle.UNDECORATED);
        gotoLogin();
        this.stage.show();
    }

    private void gotoLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent("login/Login.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(MainFXApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void gotoHome() {
        try {
            HomeController home = (HomeController) replaceSceneContent("home/Home.fxml");
            home.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(MainFXApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
            Logger.getLogger(MainFXApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (Initializable) loader.getController();
    }

    public void login(User user) {
        loggedUser = user;
        Logger.getLogger(MainFXApp.class.getName()).log(Level.INFO, loggedUser.toString());
        gotoHome();
    }

    public void logout() {
        this.loggedUser = null;
        gotoLogin();
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public Stage getStage() {
        return stage;
    }
}
