package rs.acs.uns.sw.govrs.client.fx.util;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Loader {
    public static Stage createLoader(Scene owner) {
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MainFXApp.class.getResource("/other/Loading.fxml"));
        try (InputStream in = MainFXApp.class.getResourceAsStream("/other/Loading.fxml")) {
            AnchorPane pane = loader.load(in);
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Loading...");
            stage.getIcons().add(
                    new Image(MainFXApp.class.getResource("/images/dialog.png").toString()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(owner.getWindow());
            stage.setX(owner.getWindow().getX() + owner.getWindow().getWidth()/2 - pane.getWidth());
            stage.setY(owner.getWindow().getY() + owner.getWindow().getHeight()/2 - pane.getHeight());
            stage.setAlwaysOnTop(true);
            return stage;
        } catch (Exception e) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, "Something went wrong.", e);
        }
        return null;
    }
}
