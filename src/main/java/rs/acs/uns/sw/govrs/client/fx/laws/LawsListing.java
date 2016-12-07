package rs.acs.uns.sw.govrs.client.fx.laws;

import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.home.HomeController;
import rs.acs.uns.sw.govrs.client.fx.rest.SearchResultInputConverter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.SearchResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LawsListing extends AnchorPane implements Initializable {
    @FXML
    private VBox lawsContainer;
    @FXML
    private Button searchButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lawsContainer.setSpacing(5);
        searchButton.setOnAction(event -> {
            // create a RestClient to the specific URL
            RestClient restClient = RestClient.create()
                    .method("GET")
                    .host("http://localhost:9011/api")
                    .path("/laws/");

            // retrieve a list from the DataProvider
            GluonObservableObject<SearchResult> searchResultProperty;
            SearchResultInputConverter converter = new SearchResultInputConverter();
            searchResultProperty = DataProvider.retrieveObject(restClient.createObjectDataReader(converter));

            searchResultProperty.initializedProperty().addListener(((observable, oldValue, newValue) -> {
                for (Object o : searchResultProperty.getValue().getSet()) {
                    Law l = (Law) o;
                    FXMLLoader loader = new FXMLLoader();
                    loader.setBuilderFactory(new JavaFXBuilderFactory());
                    loader.setLocation(MainFXApp.class.getResource("/laws/SingleLaw.fxml"));
                    try (InputStream in = MainFXApp.class.getResourceAsStream("/laws/SingleLaw.fxml")) {
                        AnchorPane singlePrev = loader.load(in);
                        lawsContainer.getChildren().add(singlePrev);
                        // Give the controller access to the main app.
                        SingleLaw controller = loader.getController();
                        controller.setInfo(l.getName(), l.getId(), "Miloš Milošević", "11.1.2016", "Ovo je isečak iz zakona o tome i tome i tome lkjdsalkčsdaflčsadlkklasdflkasdjfčasdkjflčajflajflajčlafjlksadjflkjaflaf");
                    } catch (IOException e) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            }));
        });

    }

}
