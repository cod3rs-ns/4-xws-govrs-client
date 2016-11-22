package rs.acs.uns.sw.govrs.client.fx.view;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;

public class XMLEditorController {

    private final Node rootIcon = new ImageView(
            new Image(getClass().getResourceAsStream("../images/law.png"))
    );

    @FXML
    private TreeView<String> actTree;

    @FXML
    private TextArea textArea;

    // Reference to the main application.
    private MainFXApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public XMLEditorController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        TreeItem<String> rootItem = new TreeItem<String>("Pravilnik o zabrani XML-a", rootIcon);
        rootItem.setExpanded(true);
        TreeItem<String> item1 = new TreeItem<String>("Deo I");
        TreeItem<String> item2 = new TreeItem<String>("Deo II");
        TreeItem<String> item3 = new TreeItem<String>("Glava I");
        rootItem.getChildren().add(item1);
        rootItem.getChildren().add(item2);
        item1.getChildren().add(item3);
        item2.getChildren().add(item3);

        actTree.setRoot(rootItem);
        System.out.println("XMLCTRL INIT");
        actTree.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            System.out.println(newValue.getValue());
        });
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainFXApp mainApp) {
        this.mainApp = mainApp;
    }
}
