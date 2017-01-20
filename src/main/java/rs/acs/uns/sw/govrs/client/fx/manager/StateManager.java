package rs.acs.uns.sw.govrs.client.fx.manager;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.editor.XMLEditorController;
import rs.acs.uns.sw.govrs.client.fx.home.HomeController;
import rs.acs.uns.sw.govrs.client.fx.laws.LawSearchController;
import rs.acs.uns.sw.govrs.client.fx.util.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StateManager {
    private AnchorPane searchPane;
    private LawSearchController searchController;

    private AnchorPane newLawPane;
    private XMLEditorController newLawController;
    private BorderPane rootContainer;
    private MainFXApp app;

    public HomeController homeController;

    public StateManager(HomeController hc) {
        homeController = hc;
        rootContainer = hc.getMainRootContainer();
    }

    public void switchState(String fxml) {
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MainFXApp.class.getResource(fxml));
        try (InputStream in = MainFXApp.class.getResourceAsStream(fxml)) {
            if (fxml.equals(Constants.LAW_SEARCH_FXML)) {
                if (searchPane == null) {
                    searchPane = loader.load(in);
                    searchController = loader.getController();
                    // TODO probably set main app
                }
                if (rootContainer.getChildren().size() > 0) {
                    rootContainer.getChildren().remove(0);
                }
                rootContainer.setCenter(searchPane);
            }
            if (fxml.equals(Constants.LAW_EDITOR_FXML)) {

                if (newLawPane == null) {
                    newLawPane = loader.load(in);
                    newLawController = loader.getController();
                    newLawController.setStateManager(this);
                    // TODO this should not be called outside controller
                    newLawController.loadTestData();
                }

                if (rootContainer.getChildren().size() > 0) {
                    rootContainer.getChildren().remove(0);
                }
                rootContainer.setCenter(newLawPane);
            }

        } catch (IOException e) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public BorderPane getRootContainer() {
        return rootContainer;
    }

    public void setRootContainer(BorderPane rootContainer) {
        this.rootContainer = rootContainer;
    }

    public MainFXApp getApp() {
        return app;
    }

    public void setApp(MainFXApp app) {
        this.app = app;
    }
}
