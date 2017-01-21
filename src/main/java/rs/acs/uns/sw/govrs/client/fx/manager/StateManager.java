package rs.acs.uns.sw.govrs.client.fx.manager;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.amendments.AmendmentsController;
import rs.acs.uns.sw.govrs.client.fx.editor.XMLEditorController;
import rs.acs.uns.sw.govrs.client.fx.home.HomeController;
import rs.acs.uns.sw.govrs.client.fx.laws.LawSearchController;
import rs.acs.uns.sw.govrs.client.fx.util.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StateManager {
    /**
     * Main controller of rootContainer;
     */
    public HomeController homeController;
    /**
     * Search State components
     **/
    private AnchorPane searchPane;
    private LawSearchController searchController;
    /**
     * Law Editor State components
     **/
    private AnchorPane newLawPane;
    private XMLEditorController newLawController;

    /** Amendments editor state components */
    private AnchorPane amendmentsPane;
    private AmendmentsController amendmentsController;

    /**
     * Root container.
     */
    private BorderPane rootContainer;
    /**
     * Main Application ref.
     */
    private MainFXApp app;

    public StateManager(HomeController homeCtrl) {
        homeController = homeCtrl;
        rootContainer = homeCtrl.getMainRootContainer();
        app = homeCtrl.getApp();
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
            } else if (fxml.equals(Constants.LAW_EDITOR_FXML)) {

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
            } else if (fxml.equals(Constants.AMENDMENTS_EDITOR_FXML)) {
                if (amendmentsPane == null) {
                    amendmentsPane = loader.load(in);
                    amendmentsController = loader.getController();
                    amendmentsController.setStateManager(this);
                    amendmentsController.loadTestData();
                }
                if (rootContainer.getChildren().size() > 0) {
                    rootContainer.getChildren().remove(0);
                }
                rootContainer.setCenter(amendmentsPane);
            } else {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Unsupported State!");
            }

        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Invalid FXML specs.", e);
        }
    }

    public MainFXApp getApp() {
        return app;
    }

}
