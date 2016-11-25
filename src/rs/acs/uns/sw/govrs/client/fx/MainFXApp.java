package rs.acs.uns.sw.govrs.client.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.StatusBar;
import rs.acs.uns.sw.govrs.client.fx.components.WindowButtons;
import rs.acs.uns.sw.govrs.client.fx.editor.XMLEditorController;

import java.io.IOException;

public class MainFXApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ToolBar toolBar;
    private StatusBar statusBar;

    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;

    public MainFXApp() {

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setTitle("GovRS client");
        initRootLayout();
        showXMLEditor();
    }

    private void initRootLayout() throws IOException {
        rootLayout = new BorderPane();
        rootLayout.setPrefSize(1000, 800);

        // initial end set up components
        toolBar = createMainToolbar();
        statusBar = createStatusBar();
        rootLayout.setTop(toolBar);
        rootLayout.setBottom(statusBar);

        Scene scene = new Scene(rootLayout);
        scene.getStylesheets().add(MainFXApp.class.getResource("app-theme.css").toExternalForm());
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

    }



    public void showXMLEditor() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApp.class.getResource("editor/XMLEditor.fxml"));
        BorderPane xmleditor = (BorderPane) loader.load();
        rootLayout.setCenter(xmleditor);

        // Give the controller access to the main app.
        XMLEditorController controller = loader.getController();
        controller.setMainApp(this);
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

    }

    private ToolBar createMainToolbar() {
        // create main toolbar
        ToolBar toolbar = new ToolBar();
        toolbar.setId("main-tool-bar");
        ImageView logo = new ImageView(new Image(MainFXApp.class.getResourceAsStream("images/logo.png")));
        HBox.setMargin(logo, new Insets(0,0,0,5));
        toolbar.getItems().add(logo);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        toolbar.getItems().add(spacer);
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        toolbar.getItems().add(spacer2);

        toolbar.setPrefHeight(66);
        toolbar.setMinHeight(66);
        toolbar.setMaxHeight(66);
        GridPane.setConstraints(toolbar, 0, 0);

        // add close min max
        final WindowButtons windowButtons = new WindowButtons(primaryStage);
        toolbar.getItems().add(windowButtons);

        // add window header double clicking
        toolbar.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                windowButtons.toggleMax();
            }
        });

        // add window dragging
        toolbar.setOnMousePressed(event -> {
            mouseDragOffsetX = event.getSceneX();
            mouseDragOffsetY = event.getSceneY();
        });
        toolbar.setOnMouseDragged(event -> {
            if(!windowButtons.isMaximized()) {
                primaryStage.setX(event.getScreenX()-mouseDragOffsetX);
                primaryStage.setY(event.getScreenY()-mouseDragOffsetY);
            }
        });

        return toolbar;
    }

    private StatusBar createStatusBar() {
        StatusBar status = new StatusBar();
        status.setText("GovRS client initial app.");
        return status;
    }
}
