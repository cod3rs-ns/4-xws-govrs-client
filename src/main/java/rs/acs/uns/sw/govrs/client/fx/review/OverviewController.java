package rs.acs.uns.sw.govrs.client.fx.review;

import com.gluonhq.connect.GluonObservableObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.amendments.AmendmentItemController;
import rs.acs.uns.sw.govrs.client.fx.laws.LawItemController;
import rs.acs.uns.sw.govrs.client.fx.manager.StateManager;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Amendment;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Amendments;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.SearchObject;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.SearchResult;
import rs.acs.uns.sw.govrs.client.fx.util.IDUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class OverviewController implements Initializable {
    @FXML
    private VBox lawsContainer;
    @FXML
    private VBox amendmentsContainer;
    @FXML
    private BorderPane previewContainer;

    private ObservableList<Law> laws = FXCollections.observableArrayList();
    private ObservableList<Amendments> amendments = FXCollections.observableArrayList();

    private StateManager stateManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lawsContainer.setSpacing(5);
        amendmentsContainer.setSpacing(5);
        GluonObservableObject<SearchResult> userLaws = RestClientProvider.getInstance().getLawsByUser(RestClientProvider.getInstance().getUser().getId());
        userLaws.initializedProperty().addListener((observable, oldValue, newValue) -> {
            SearchResult sr = userLaws.get();
            System.out.println(sr);
            if (sr.getSet() != null) {
                if (sr.getSet().size() > 0) {
                    for (SearchObject so : sr.getSet()) {
                        String currentId = IDUtils.extractId(so.getPath());
                        GluonObservableObject<Law> lawProperty = RestClientProvider.getInstance().getLaw(currentId);
                        lawProperty.initializedProperty().addListener((observable1, oldValue1, newValue1) -> {
                            Law law = lawProperty.get();
                            System.out.println("AHAHA");
                            laws.add(law);
                            updateLaws();
                        });
                    }
                }
            }
        });

        GluonObservableObject<SearchResult> userAmendments = RestClientProvider.getInstance().getAmendmentsByUser(RestClientProvider.getInstance().getUser().getId());
        userAmendments.initializedProperty().addListener((observable, oldValue, newValue) -> {
            SearchResult sr = userAmendments.get();
            if (sr.getSet() != null && sr.getSet().size() > 0) {
                for (SearchObject so: sr.getSet() ) {
                    String currentId = IDUtils.extractId(so.getPath());
                    GluonObservableObject<Object> amendmentsProperty = RestClientProvider.getInstance().getAmendments(currentId);
                    amendmentsProperty.initializedProperty().addListener((observable1, oldValue1, newValue1) -> {
                        Amendments amendment = (Amendments) amendmentsProperty.get();
                        amendments.add(amendment);
                        updateAmendments();
                    });
                }
            }
        });
    }

    private void updateLaws() {
        lawsContainer.getChildren().clear();
        for (Law l: laws) {
            FXMLLoader loader = new FXMLLoader();
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(MainFXApp.class.getResource("/laws/LawItem.fxml"));
            try (InputStream in = MainFXApp.class.getResourceAsStream("/laws/LawItem.fxml")) {
                AnchorPane pane = loader.load(in);
                LawItemController controller = loader.getController();
                controller.display(l, previewContainer);
                lawsContainer.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateAmendments() {
        amendmentsContainer.getChildren().clear();
        for (Amendments amend: amendments) {
            FXMLLoader loader = new FXMLLoader();
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(MainFXApp.class.getResource("/amendments/AmendmentItem.fxml"));
            try (InputStream in = MainFXApp.class.getResourceAsStream("/amendments/AmendmentItem.fxml")) {
                AnchorPane pane = loader.load(in);
                AmendmentItemController controller = loader.getController();
                controller.display(amend, previewContainer);
                amendmentsContainer.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }
}
