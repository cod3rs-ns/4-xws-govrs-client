package rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.AmendmentType;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AmendmentTypeAdapter extends XmlAdapter<String, ObjectProperty<AmendmentType>> {
    @Override
    public ObjectProperty<AmendmentType> unmarshal(String v) throws Exception {
        if (v.equals("dopuna")) {
            return new SimpleObjectProperty<>(AmendmentType.Dopuna);
        }
        if (v.equals("izmjena")) {
            return new SimpleObjectProperty<>(AmendmentType.Izmena);
        }
        if (v.equals("brisanje")) {
            return new SimpleObjectProperty<>(AmendmentType.Brisanje);
        }
        return null;
    }

    @Override
    public String marshal(ObjectProperty<AmendmentType> v) throws Exception {
        if (v.get() == AmendmentType.Dopuna) {
            return "dopuna";
        }

        if (v.get() == AmendmentType.Izmena) {
            return "izmjena";
        }

        if (v.get() == AmendmentType.Brisanje) {
            return "brisanje";
        }
        return "";
    }
}
