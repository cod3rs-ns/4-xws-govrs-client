package rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import rs.acs.uns.sw.govrs.client.fx.util.StringCleaner;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class StringPropertyCleanerAdapter extends XmlAdapter<String, StringProperty> {
    @Override
    public StringProperty unmarshal(String v) throws Exception {
        return new SimpleStringProperty(StringCleaner.deleteWhitespace(v));
    }

    @Override
    public String marshal(StringProperty v) throws Exception {
        return StringCleaner.deleteWhitespace(v.get());
    }
}
