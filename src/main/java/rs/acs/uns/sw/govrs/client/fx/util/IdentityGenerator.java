package rs.acs.uns.sw.govrs.client.fx.util;


import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;

public class IdentityGenerator {

    private static IdentityGenerator instance = null;
    private RandomGenerator random;
    private RestClientProvider client;

    private IdentityGenerator() {
        random = new RandomGenerator();
        client = RestClientProvider.getInstance();
    }

    public static IdentityGenerator get() {
        if (instance == null) {
            instance = new IdentityGenerator();
        }
        return instance;
    }

    public String generate(Element parent, ElementType type) {
        StringBuilder sb = new StringBuilder();
        // generate for root element
        if (parent == null) {
            sb.append(client.getUser().getKorisnickoIme());
            sb.append("_");
            sb.append(type.toString());
            sb.append("_");
            sb.append(random.nextIdLarge());
        } else {
            sb.append(parent.idProperty().get());
            sb.append("_");
            sb.append(type.toString());
            sb.append("_");
            sb.append(random.nextIdSmall());
        }
        String id = sb.toString();
        return id;
    }
}
