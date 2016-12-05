package rs.acs.uns.sw.govrs.client.fx.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.UriBuilder;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;


/**
 * @author Gus Garsaky
 */
public class RESTClient {
    private static final String WS_URI = "http://localhost:9011/api/laws/";


    public static String testRest(String dni) throws RuntimeException {
        Client client = null;
        try {
            client = ClientBuilder.newClient();
            WebTarget target = client.target(getBaseUri());
            String found = target.path(dni).request().get(new GenericType<String>() {
            });
            return found;
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (client != null) client.close();
        }
    }

    /* *************************************
     * 			 UTIL METHODS
     ***************************************/
    // Check the status of RESTful WebService
    public static boolean checkWS() {
        Boolean stateOfWS = false;
        try {
            URL siteURL = new URL(getBaseUri().toString());
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            if (code == 200) stateOfWS = true;
        } catch (Exception e) {
            // do nothing
        }
        return stateOfWS;
    }

    private static URI getBaseUri() {
        return UriBuilder.fromUri(WS_URI).build();
    }
}