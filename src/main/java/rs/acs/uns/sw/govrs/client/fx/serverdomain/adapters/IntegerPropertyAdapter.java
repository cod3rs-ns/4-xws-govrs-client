package rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javax.xml.bind.annotation.adapters.XmlAdapter;


public class IntegerPropertyAdapter extends XmlAdapter<Integer, IntegerProperty> {

    @Override
    public IntegerProperty unmarshal(Integer v) throws Exception {
        return new SimpleIntegerProperty(v);
    }

    @Override
    public Integer marshal(IntegerProperty v) throws Exception {
        return v.get();
    }
}
