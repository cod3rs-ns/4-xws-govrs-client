package rs.acs.uns.sw.govrs.client.fx.view;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.model.*;
import rs.acs.uns.sw.govrs.client.fx.model.tree.TreeModel;

public class XMLEditorController {

    private final Image rootIcon =
            new Image(getClass().getResourceAsStream("../images/law.png"));

    @FXML
    private TitledPane treeContainer;

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
        Propis propis = new Propis("Propis o destabilizaciji mozga", "images/law.png");
        Glava glava1 = new Glava("Glava prva", null);
        Glava glava2 = new Glava("Glava druga", null);
        Glava glava3 = new Glava("Glava treća", null);
        Tacka t1 = new Tacka("Prva tačka");
        Tacka t2= new Tacka("Prva tačka");
        Tacka t3 = new Tacka("Prva tačka");
        Tacka t4 = new Tacka("Prva tačka");
        Tacka t5 = new Tacka("Prva tačka");
        Tacka t6 = new Tacka("Prva tačka");

        propis.getChildren().add(glava1);
        propis.getChildren().add(glava2);
        propis.getChildren().add(glava3);

        glava1.getChildren().add(t1);
        glava2.getChildren().add(t2);
        glava2.getChildren().add(t3);
        glava3.getChildren().add(t4);
        glava3.getChildren().add(t5);
        glava3.getChildren().add(t6);


        TreeModel tree = new TreeModel(
                propis,
                Element::getChildren,
                Element::nameProperty
        );

        TreeView<Element> treeView = tree.getTreeView();
        treeContainer.setContent(treeView);
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
