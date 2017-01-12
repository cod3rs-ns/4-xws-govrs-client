package rs.acs.uns.sw.govrs.client.fx.editor;

import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;

public class CustomDialogCreator {

    public static TextInputDialog createNewEntryDialog(String prompt) {
        TextInputDialog dialog = new TextInputDialog(prompt);
        dialog.setTitle("Novi element");
        dialog.setHeaderText("Unos novog elementa tipa - " + prompt);
        dialog.setContentText("Unesite naziv elementa:");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(
                new Image(MainFXApp.class.getResource("/images/dialog.png").toString()));
        return dialog;
    }
}
