package rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Keky on 7.12.2016..
 */
public class StringPropertyAdapter extends XmlAdapter<String, StringProperty> {
    @Override
    public StringProperty unmarshal(String v) throws Exception {
        return new SimpleStringProperty(v);
    }

    @Override
    public String marshal(StringProperty v) throws Exception {
        return v.get();
    }
}
