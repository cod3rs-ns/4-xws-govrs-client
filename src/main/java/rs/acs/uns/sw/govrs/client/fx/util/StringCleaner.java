package rs.acs.uns.sw.govrs.client.fx.util;

public class StringCleaner {
    public static boolean checkIsEmpty(String before) {
        String after = before.trim().replaceAll(" +", " ");
        if (after.equals("") || after.equals(" ")) {
            return true;
        }
        return false;
    }

    public static String deleteWhitespace(String before) {
        return before.trim().replaceAll("\t", " ").replaceAll("\n", " ").replaceAll(" +", " ");
    }
}