package rs.acs.uns.sw.govrs.client.fx;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rs.acs.uns.sw.govrs.client.fx.model.*;
import rs.acs.uns.sw.govrs.client.fx.components.WindowButtons;
import rs.acs.uns.sw.govrs.client.fx.view.XMLEditorController;

import java.io.IOException;

public class MainFXApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ToolBar toolBar;

    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;

    public MainFXApp() {

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
            toolBar.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    windowButtons.toggleMax();
                }
            });
            // add window dragging
            toolBar.setOnMousePressed(event -> {
                mouseDragOffsetX = event.getSceneX();
                mouseDragOffsetY = event.getSceneY();
            });
            toolBar.setOnMouseDragged(event -> {
                if(!windowButtons.isMaximized()) {
                    primaryStage.setX(event.getScreenX()-mouseDragOffsetX);
                    primaryStage.setY(event.getScreenY()-mouseDragOffsetY);
                }
            });

        rootLayout.setTop(toolBar);
    }

    public void showXMLEditor() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApp.class.getResource("view/XMLEditor.fxml"));
        AnchorPane xmleditor = (AnchorPane) loader.load();
        rootLayout.setCenter(xmleditor);
        /**
        TextField add = new TextField();

        Button addButton = new Button("Add");
        addButton.disableProperty().bind(Bindings.createBooleanBinding(() ->
                        treeView.getSelectionModel().getSelectedItem() == null ||
                                treeView.getSelectionModel().getSelectedItem().getValue() instanceof Text,
                treeView.getSelectionModel().selectedItemProperty()));

        EventHandler<ActionEvent> addHandler = e -> {
            if (treeView.getSelectionModel().getSelectedItem() == null
                    || treeView.getSelectionModel().getSelectedItem().getValue() instanceof Text) {
                return ;
            }
            treeView.getSelectionModel().getSelectedItem().getValue().createAndAddChild(add.getText());
            add.clear();
        };

        add.setOnAction(event -> {

        });
        add.setOnAction(addHandler);
        addButton.setOnAction(addHandler);

        Button delete = new Button("Delete");
        delete.disableProperty().bind(Bindings.createBooleanBinding(() ->
                        treeView.getSelectionModel().getSelectedItem() == null ||
                                treeView.getSelectionModel().getSelectedItem().getValue() == company,
                treeView.getSelectionModel().selectedItemProperty()));

        delete.setOnAction(e -> {
            TreeItem<Element> selected = treeView.getSelectionModel().getSelectedItem() ;
            selected.getParent().getValue().getChildren().remove(selected.getValue());
        });

        HBox controls = new HBox(5, add, addButton, delete);
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.CENTER);

        rootLayout.setCenter(treeView);
        rootLayout.setBottom(controls);
        */

        // Give the controller access to the main app.
        XMLEditorController controller = loader.getController();
        controller.setMainApp(this);
    }
}
