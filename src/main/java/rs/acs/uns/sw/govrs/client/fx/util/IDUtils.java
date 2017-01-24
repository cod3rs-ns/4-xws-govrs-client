package rs.acs.uns.sw.govrs.client.fx.util;

public class IDUtils {
    public static String extractId(String path){
        String [] links = path.split("/");
        String id = links[links.length-1];
        return id;
    }
}
