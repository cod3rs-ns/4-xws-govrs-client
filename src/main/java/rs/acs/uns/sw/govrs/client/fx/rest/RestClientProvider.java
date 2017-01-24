package rs.acs.uns.sw.govrs.client.fx.rest;


import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.converter.JsonInputConverter;
import com.gluonhq.connect.converter.StringInputConverter;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Amendments;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.AppUser;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.ObjectFactory;
import rs.acs.uns.sw.govrs.client.fx.util.Token;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestClientProvider {

    private static RestClientProvider instance = null;
    private ObjectProperty<AppUser> user = new SimpleObjectProperty<>();

    private String token;
    private JAXBContext amendmentsContext;
    private Marshaller amendmentsMarshaller;
    private JAXBContext lawContext;
    private Marshaller lawMarshaller;

    private RestClientProvider() {
        try {
            amendmentsContext = JAXBContext.newInstance(Amendments.class);
            amendmentsMarshaller = amendmentsContext.createMarshaller();
            lawContext = JAXBContext.newInstance(Law.class);
            lawMarshaller = lawContext.createMarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static RestClientProvider getInstance() {
        if (instance == null) {
            instance = new RestClientProvider();
        }
        return instance;
    }

    public GluonObservableObject<Law> getLaw(String lawId) {
        // create a RestClient to the specific URL
        RestClient restClient = RestClient.create()
                .method("GET")
                .host("http://localhost:9000/api")
                .header("Accept", "application/xml")
                .path("/laws/" + lawId);

        // retrieve an object from the DataProvider
        LawInputConverter converter = new LawInputConverter();
        return DataProvider.retrieveObject(restClient.createObjectDataReader(converter));
    }

    public GluonObservableObject<Object> getAmendments(String amendmentsId) {
        RestClient restClient = RestClient.create()
                .method("GET")
                .host("http://localhost:9000/api")
                .header("Accept", "application/xml")
                .path("/amendments/" + amendmentsId);

        // retrieve an object from the DataProvider
        ResultInputConverter converter = new ResultInputConverter(Amendments.class);
        return DataProvider.retrieveObject(restClient.createObjectDataReader(converter));
    }

    public GluonObservableObject<Object> postAmendments(Amendments amendments) {
        StringWriter writer = new StringWriter();
        try {
            amendmentsMarshaller.marshal(amendments, writer);
        } catch (JAXBException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Unable to marshal Amendments", e);
        }
        String marshalledData = writer.toString();
        // create a RestClient to the specific URL
        RestClient restClient = RestClient.create()
                .method("POST")
                .host("http://localhost:9000/api")
                .contentType("application/xml")
                .path("/amendments/")
                .dataString(marshalledData);

        ResultInputConverter converter = new ResultInputConverter(Amendments.class);
        return DataProvider.retrieveObject(restClient.createObjectDataReader(converter));
    }

    public GluonObservableObject<Law> postLaw(Law law) {
        StringWriter writer = new StringWriter();
        try {
            lawMarshaller.marshal(law, writer);
        } catch (JAXBException e) {
            System.out.println("");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Unable to marshal Amendments", e);
        }
        String marshalledData = writer.toString();
        // create a RestClient to the specific URL
        RestClient restClient = RestClient.create()
                .method("POST")
                .host("http://localhost:9000/api")
                .contentType("application/xml")
                .path("/laws/")
                .dataString(marshalledData);

        // retrieve an object from the DataProvider
        LawInputConverter converter = new LawInputConverter();
        return DataProvider.retrieveObject(restClient.createObjectDataReader(converter));
    }

    public GluonObservableObject<String> getLawHtml(String fullId) {
        // create a RestClient to the specific URL
        RestClient restClientHtml = RestClient.create()
                .method("GET")
                .host("http://localhost:9000")
                .header("Accept", "application/xhtml+xml")
                .path("/api/laws/" + fullId);
        // retrieve a list from the DataProvider
        GluonObservableObject<String> htmlProperty;
        StringInputConverter converterString = new StringInputConverter();
        htmlProperty = DataProvider.retrieveObject(restClientHtml.createObjectDataReader(converterString));
        return htmlProperty;
    }

    public void login(String username, String password) {
        RestClient restClientToken = RestClient.create()
                .method("POST")
                .host("http://localhost:9000")
                .path("/api/users/auth")
                .formParam("username", username)
                .formParam("password", password);
        // retrieve a list from the DataProvider
        GluonObservableObject<Token> tokenProperty;
        JsonInputConverter<Token> tokenConverter = new JsonInputConverter<>(Token.class);
        tokenProperty = DataProvider.retrieveObject(restClientToken.createObjectDataReader(tokenConverter));
        tokenProperty.initializedProperty().addListener((observable, oldValue, newValue) ->
        {
            token = tokenProperty.get().getToken();
            RestClient restClientUser = RestClient.create()
                    .method("GET")
                    .host("http://localhost:9000")
                    .path("/api/users/login")
                    .header("X-AUTH-TOKEN", token);


            ResultInputConverter userConverter = new ResultInputConverter(AppUser.class);
            GluonObservableObject<Object> userProperty = DataProvider.retrieveObject(restClientUser.createObjectDataReader(userConverter));
            userProperty.initializedProperty().addListener((observable1, oldValue1, newValue1) -> {
                AppUser loggedUser = (AppUser) userProperty.get();
                System.out.println(loggedUser.getKorisnickoIme());
                System.out.println(loggedUser.getEmail());
                System.out.println(loggedUser.getUloga());
                user.set(loggedUser);
            });
        });
    }

    public AppUser getUser() {
        return user.get();
    }

    public ObjectProperty<AppUser> userProperty() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user.set(user);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}


