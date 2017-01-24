package rs.acs.uns.sw.govrs.client.fx.laws;

import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.PropertySheet;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.DocumentStatusPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.IntegerPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.LocalDatePropertyItem;
import rs.acs.uns.sw.govrs.client.fx.home.HomeController;
import rs.acs.uns.sw.govrs.client.fx.rest.SearchResultInputConverter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.DocumentStatus;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.SearchObject;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.SearchResult;
import rs.acs.uns.sw.govrs.client.fx.util.Loader;
import rs.acs.uns.sw.govrs.client.fx.util.StringCleaner;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LawSearchController extends AnchorPane implements Initializable {
    @FXML
    public BorderPane previewHtml;
    IntegerProperty startVotesFor = new SimpleIntegerProperty();
    IntegerProperty endVotesFor = new SimpleIntegerProperty();
    IntegerProperty startVotesAgainst = new SimpleIntegerProperty();
    IntegerProperty endVotesAgainst = new SimpleIntegerProperty();
    IntegerProperty startVotesNeutral = new SimpleIntegerProperty();
    IntegerProperty endVotesNeutral = new SimpleIntegerProperty();
    ObjectProperty<LocalDate> startDateOfProposal = new SimpleObjectProperty<>();
    ObjectProperty<LocalDate> endDateOfProposal = new SimpleObjectProperty<>();
    ObjectProperty<LocalDate> startDateOfVoting = new SimpleObjectProperty<>();
    ObjectProperty<LocalDate> endDateOfVoting = new SimpleObjectProperty<>();
    ObjectProperty<DocumentStatus> status = new SimpleObjectProperty<>();
    StringProperty queryStringProperty = new SimpleStringProperty("?");
    GluonObservableObject<SearchResult> searchResultProperty;
    ObservableList<PropertySheet.Item> items = FXCollections.observableArrayList();
    @FXML
    private VBox lawsContainer;
    @FXML
    private Button searchButton;
    @FXML
    private PropertySheet propertySheet;
    @FXML
    private TextField searchField;
    @FXML
    private Pagination pagination;
    @FXML
    private ScrollPane scrolledContainer;
    private int totalPages;
    private ObservableList<SearchObject> retrievedObjects = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lawsContainer.setSpacing(5);
        createItems();
        propertySheet.getItems().addAll(items);

        pagination.setCurrentPageIndex(0);
        pagination.setPageCount(1);

    }
    private Stage loadingStage;
    ChangeListener<Boolean> listener;

    @FXML
    private void clearData() {
        startVotesFor = new SimpleIntegerProperty();
        endVotesFor = new SimpleIntegerProperty();
        startVotesAgainst = new SimpleIntegerProperty();
        endVotesAgainst = new SimpleIntegerProperty();
        startVotesNeutral = new SimpleIntegerProperty();
        endVotesNeutral = new SimpleIntegerProperty();
        startDateOfProposal = new SimpleObjectProperty<>();
        endDateOfProposal = new SimpleObjectProperty<>();
        startDateOfVoting = new SimpleObjectProperty<>();
        endDateOfVoting = new SimpleObjectProperty<>();
        status = new SimpleObjectProperty<>();
        items.clear();
        propertySheet.getItems().clear();
        createItems();
        propertySheet.getItems().addAll(items);
        queryStringProperty = new SimpleStringProperty("?");
        retrievedObjects.clear();
        scrolledContainer.setContent(null);
        searchField.setText("");
        pagination.setCurrentPageIndex(0);
        pagination.setPageCount(0);
    }
    @FXML
    private void performSearch() {
        try{
            searchResultProperty.initializedProperty().removeListener(listener);
        }
        catch (Exception e){

        }
        if (!searchField.getText().trim().equals("")) {
            queryStringProperty.setValue(queryStringProperty.get() + "query=" + StringCleaner.deleteWhitespace(searchField.getText()));
        }
        if (startVotesFor.get() != 0) {
            queryStringProperty.set(queryStringProperty.get() + "&startVotesFor=" + startVotesFor.get());
        }
        if (endVotesFor.get() != 0) {
            queryStringProperty.set(queryStringProperty.get() + "&endVotesFor=" + endVotesFor.get());
        }
        if (startVotesNeutral.get() != 0) {
            queryStringProperty.set(queryStringProperty.get() + "&startVotesNeutral=" + startVotesNeutral.get());
        }
        if (startVotesNeutral.get() != 0) {
            queryStringProperty.set(queryStringProperty.get() + "&endVotesNeutral=" + endVotesNeutral.get());
        }
        if (startVotesAgainst.get() != 0) {
            queryStringProperty.set(queryStringProperty.get() + "&startVotesAgainst=" + startVotesAgainst.get());
        }
        if (endVotesAgainst.get() != 0) {
            queryStringProperty.set(queryStringProperty.get() + "&endVotesAgainst=" + endVotesAgainst.get());
        }
        if (status.get() != null) {
            queryStringProperty.set(queryStringProperty.get() + "&status=" + status.get().toString());
        }

        if (startDateOfProposal.get() != null) {
            queryStringProperty.set(queryStringProperty.get() + "&startDateOfProposal=" + convertLocalDate(startDateOfProposal.get()));
        }
        if (endDateOfProposal.get() != null) {
            queryStringProperty.set(queryStringProperty.get() + "&endDateOfProposal=" + convertLocalDate(endDateOfProposal.get()));
        }
        if (startDateOfVoting.get() != null) {
            queryStringProperty.set(queryStringProperty.get() + "&startDateOfVoting=" + convertLocalDate(startDateOfVoting.get()));
        }
        if (endDateOfVoting.get() != null) {
            queryStringProperty.set(queryStringProperty.get() + "&endDateOfVoting=" + convertLocalDate(endDateOfVoting.get()));
        }

        System.out.println(queryStringProperty.get());

        // create a RestClient to the specific URL
        RestClient restClient = RestClient.create()
                .method("GET")
                .host("http://localhost:9000/api")
                .path("/laws/search" + queryStringProperty.get());
        queryStringProperty.set("?");

        SearchResultInputConverter converter = new SearchResultInputConverter();
        searchResultProperty = DataProvider.retrieveObject(restClient.createObjectDataReader(converter));
        loadingStage = Loader.createLoader(searchField.getScene());
        loadingStage.show();
        lawsContainer.getChildren().clear();
        retrievedObjects.clear();
        listener = createListener();
        searchResultProperty.initializedProperty().addListener(listener);

    }
    
    private ChangeListener<Boolean> createListener() {
        return new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                retrievedObjects.clear();
                scrolledContainer.setContent(null);
                if (searchResultProperty.get() != null && searchResultProperty.get().getSet() != null) {
                    if (searchResultProperty.get().getSet().size() > 0) {
                        for (Object o : searchResultProperty.get().getSet()) {
                            SearchObject so = (SearchObject) o;
                            retrievedObjects.add(so);
                        }

                        totalPages = retrievedObjects.size() / itemsPerPage() + 1;
                        pagination.setPageCount(totalPages);
                        System.out.println(retrievedObjects.size());
                        pagination.setPageFactory(new Callback<Integer, Node>() {
                            @Override
                            public Node call(Integer pageIndex) {
                                return createPage(pageIndex);
                            }
                        });
                    } else {
                        retrievedObjects.clear();
                        totalPages = 0;
                        pagination.setPageCount(totalPages);

                    }
                    pagination.setCurrentPageIndex(0);
                }
                loadingStage.close();
            }
        };
    }


    public void createItems() {
        IntegerPropertyItem startVotesForPropertyItem = new IntegerPropertyItem(
                startVotesFor,
                "Glasovi -za-",
                "za <od>",
                "Minimalan broj glasova ZA",
                true);
        IntegerPropertyItem endVotesForPropertyItem = new IntegerPropertyItem(
                endVotesFor,
                "Glasovi -za-",
                "za <do>",
                "Maksimalan broj glasova ZA",
                true);
        IntegerPropertyItem startVotesAgainstPropertyItem = new IntegerPropertyItem(
                startVotesAgainst,
                "Glasovi -protiv-",
                "protiv <od>",
                "Minimalan broj glasova PROTIV",
                true);
        IntegerPropertyItem endVotesAgainstPropertyItem = new IntegerPropertyItem(
                endVotesAgainst,
                "Glasovi -protiv-",
                "protiv <do>",
                "Maksimalan broj glasova PROTIV",
                true);

        IntegerPropertyItem startVotesNeutralPropertyItem = new IntegerPropertyItem(
                startVotesNeutral,
                "Glasovi -suzdržani-",
                "suzdržani <od>",
                "Minimalan broj glasova SUZDRŽANI",
                true);
        IntegerPropertyItem endVotesNeutralPropertyItem = new IntegerPropertyItem(
                endVotesNeutral,
                "Glasovi -suzdržani-",
                "suzdržani <do>",
                "Maksimalan broj glasova SUZDRŽANI",
                true);

        DocumentStatusPropertyItem statusPropertyItem = new DocumentStatusPropertyItem(
                status,
                "Status",
                "status predloga",
                "Trenutni status Akta na Skupštinskom repertoaru",
                true);

        LocalDatePropertyItem startDateOfProposalPropertyItem = new LocalDatePropertyItem(
                startDateOfProposal,
                "Predložen",
                "predložen <od>",
                "Minimalna vrednost datum kada je podnet ovaj pravni Akt",
                true);
        LocalDatePropertyItem endDateOfProposalPropertyItem = new LocalDatePropertyItem(
                endDateOfProposal,
                "Predložen",
                "predložen <do>",
                "Maksimalna vrednost datuma kada je podnet ovaj pravni Akt",
                true);
        LocalDatePropertyItem startDateOfVotingPropertyItem = new LocalDatePropertyItem(
                startDateOfVoting,
                "Izglasan",
                "izglasan <od>",
                "Maksimalna vrednost datuma kada se glasalo o ovom pravnom aktu",
                true);
        LocalDatePropertyItem endDateOfVotingPropertyItem = new LocalDatePropertyItem(
                endDateOfVoting,
                "Izglasan",
                "izglasan <do>",
                "Maksimalna vrednost datuma kada se glasalo o ovom pravnom Aktu",
                true);

        items.add(startVotesForPropertyItem);
        items.add(endVotesForPropertyItem);
        items.add(startVotesNeutralPropertyItem);
        items.add(endVotesNeutralPropertyItem);
        items.add(startVotesAgainstPropertyItem);
        items.add(endVotesAgainstPropertyItem);
        items.add(startDateOfProposalPropertyItem);
        items.add(endDateOfProposalPropertyItem);
        items.add(startDateOfVotingPropertyItem);
        items.add(endDateOfVotingPropertyItem);
        items.add(statusPropertyItem);
    }

    public String convertLocalDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return localDate.format(formatter);
    }

    public int itemsPerPage() {
        return 5;
    }

    public VBox createPage(int pageIndex) {

        VBox box = new VBox(5);
        scrolledContainer.setContent(box);
        int page = pageIndex * itemsPerPage();

        for (int i = page; i < page + itemsPerPage(); i++) {
            if (i >= retrievedObjects.size()) {
                VBox v = new VBox();
                v.setMaxSize(0, 0);
                v.setVisible(false);
                return v;
            }
            SearchObject so = retrievedObjects.get(i);
            FXMLLoader loader = new FXMLLoader();
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(MainFXApp.class.getResource("/laws/SingleLaw.fxml"));
            try (InputStream in = MainFXApp.class.getResourceAsStream("/laws/SingleLaw.fxml")) {
                AnchorPane singlePrev = loader.load(in);
                box.getChildren().add(singlePrev);
                // Give the controller access to the main app.
                SingleLaw controller = loader.getController();
                controller.setInfo(this, so);
            } catch (IOException e) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        VBox v = new VBox();
        v.setMaxSize(0, 0);
        v.setVisible(false);
        return v;
    }


}
