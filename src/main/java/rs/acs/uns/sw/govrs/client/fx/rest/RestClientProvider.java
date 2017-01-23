package rs.acs.uns.sw.govrs.client.fx.rest;


import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Amendments;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestClientProvider {

    private static RestClientProvider instance = null;

    private JAXBContext amendmentsContext;
    private Marshaller amendmentsMarshaller;
    private JAXBContext lawContext;
    private Marshaller lawMarshaller;

    public String username = "keky";

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
        if(instance == null) {
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


}
