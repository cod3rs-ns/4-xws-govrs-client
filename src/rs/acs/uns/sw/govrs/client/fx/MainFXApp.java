package rs.acs.uns.sw.govrs.client.fx;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rs.acs.uns.sw.govrs.client.fx.model.ActSample;
import rs.acs.uns.sw.govrs.client.fx.model.ActType;
import rs.acs.uns.sw.govrs.client.fx.components.WindowButtons;
import rs.acs.uns.sw.govrs.client.fx.view.XMLEditorController;

import java.io.IOException;

public class MainFXApp extends Application {

    public ActSample rootAct;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ToolBar toolBar;

    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;

    public MainFXApp() {
        rootAct = new ActSample("id1", "Saiyan Act Root", ActType.TYPE_1, "Super Saiyan God Rose act");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("govRS");

        initRootLayout();

        showXMLEditor();

    }

    private void initRootLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApp.class.getResource("view/RootLayout.fxml"));

        rootLayout = (BorderPane) loader.load();
        Scene scene = new Scene(rootLayout);

        scene.getStylesheets().add(MainFXApp.class.getResource("app-theme.css").toExternalForm());
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

        // create main toolbar
        toolBar = new ToolBar();
        toolBar.setId("main-tool-bar");
        ImageView logo = new ImageView(new Image(MainFXApp.class.getResourceAsStream("images/logo.png")));
        HBox.setMargin(logo, new Insets(0,0,0,5));
        toolBar.getItems().add(logo);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        toolBar.getItems().add(spacer);

        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        toolBar.getItems().add(spacer2);
        toolBar.setPrefHeight(66);
        toolBar.setMinHeight(66);
        toolBar.setMaxHeight(66);
        GridPane.setConstraints(toolBar, 0, 0);

            // add close min max
            final WindowButtons windowButtons = new WindowButtons(primaryStage);
            toolBar.getItems().add(windowButtons);
            // add window header double clicking
            toolBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2) {
                        windowButtons.toggleMax();
                    }
                }
            });
            // add window dragging
            toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent event) {
                    mouseDragOffsetX = event.getSceneX();
                    mouseDragOffsetY = event.getSceneY();
                }
            });
            toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent event) {
                    if(!windowButtons.isMaximized()) {
                        primaryStage.setX(event.getScreenX()-mouseDragOffsetX);
                        primaryStage.setY(event.getScreenY()-mouseDragOffsetY);
                    }
                }
            });

        rootLayout.setTop(toolBar);
    }

    public void showXMLEditor() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApp.class.getResource("view/XMLEditor.fxml"));
        AnchorPane xmleditor = (AnchorPane) loader.load();
        rootLayout.setCenter(xmleditor);

        // Give the controller access to the main app.
        XMLEditorController controller = loader.getController();
        controller.setMainApp(this);
    }
}
