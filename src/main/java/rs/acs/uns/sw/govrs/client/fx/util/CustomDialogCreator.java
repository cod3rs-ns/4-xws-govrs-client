package rs.acs.uns.sw.govrs.client.fx.util;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;

import java.util.List;

public class CustomDialogCreator {

    public static final ButtonType YES = new ButtonType("Da", ButtonBar.ButtonData.YES);
    public static final ButtonType NO = new ButtonType("Ne", ButtonBar.ButtonData.NO);

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

    public static Alert createDeleteConfirmationDialog(String elementName) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Uklanjanje elementa");
        alert.setHeaderText("Element <" + elementName + "> će biti uklonjen!" );
        alert.setContentText("Da li ste sigurni?");
        // add custom buttons
        alert.getDialogPane().getButtonTypes().removeAll();
        alert.getDialogPane().getButtonTypes().clear();
        alert.getDialogPane().getButtonTypes().add(YES);
        alert.getDialogPane().getButtonTypes().add(NO);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(
                new Image(MainFXApp.class.getResource("/images/dialog.png").toString()));
        return alert;
    }

    public static Alert createInformationAlert(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(
                new Image(MainFXApp.class.getResource("/images/dialog.png").toString()));
        return alert;
    }
    public static Alert createErrorAlert(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(
                new Image(MainFXApp.class.getResource("/images/dialog.png").toString()));
        return alert;
    }

    public static ChoiceDialog<String> createSelectLawDialog(List<String> choices) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle("Izbor Propisa");
        dialog.setHeaderText("Upravo dodajete novi amandman!");
        dialog.setContentText("Izaberite jedna od propisa:");
        return dialog;
    }
}
