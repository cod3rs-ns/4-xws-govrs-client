package rs.acs.uns.sw.govrs.client.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import rs.acs.uns.sw.govrs.client.fx.client.RESTClient;
import rs.acs.uns.sw.govrs.client.fx.domain.User;
import rs.acs.uns.sw.govrs.client.fx.home.HomeController;
import rs.acs.uns.sw.govrs.client.fx.login.LoginController;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

    /**
     * Main method.
     * @param args arguments
     */
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

    private void testRest() throws ParserConfigurationException, IOException, SAXException {
        String url = "http://localhost:9011/api/laws/name0";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(url);
        System.out.println(doc.getTextContent());
    }

    private void gotoLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent("/login/Login.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(MainFXApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void gotoHome() {
        try {
            HomeController home = (HomeController) replaceSceneContent("/home/Home.fxml");
            home.setApp(this);

            String a = RESTClient.testRest("name0");
            System.out.println(a);
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

    /**
     * Log-in provided user and redirect to Home stage.
     * @param user User to be logged-in.
     */
    public void login(User user) {
        loggedUser = user;
        Logger.getLogger(MainFXApp.class.getName()).log(Level.INFO, loggedUser.toString());
        gotoHome();
    }

    /**
     *  Log-out current user and redirect to Login stage.
     */
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
