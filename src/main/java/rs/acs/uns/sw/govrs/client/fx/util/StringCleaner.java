package rs.acs.uns.sw.govrs.client.fx.util;

public class StringCleaner {
    /**
     * Check if current string is empty (ignoring whitespace)
     *
     * @param stringToCheck String value to be checked
     * @return true if variable is 'empty'
     */
    public static boolean checkIsEmpty(String stringToCheck) {
        String after = stringToCheck.trim().replaceAll(" +", " ");
        return after.equals("") || after.equals(" ");
    }

    /**
     * Removes unnecessary whitespaces including \n \t etc.
     *
     * @param before String variable before cleaning
     * @return cleaned, whitespace free variable
     */
    public static String deleteWhitespace(String before) {
        return before.trim().replaceAll("\t", " ").replaceAll("\n", " ").replaceAll(" +", " ");
    }
}
