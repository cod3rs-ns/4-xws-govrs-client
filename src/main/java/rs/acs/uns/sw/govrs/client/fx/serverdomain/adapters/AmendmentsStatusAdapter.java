package rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.AmendmentsStatus;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AmendmentsStatusAdapter extends XmlAdapter<String, ObjectProperty<AmendmentsStatus>> {
    @Override
    public ObjectProperty<AmendmentsStatus> unmarshal(String v) throws Exception {
        if (v.equals("predložen")) {
            return new SimpleObjectProperty<>(AmendmentsStatus.Predlozen);
        }
        if (v.equals("prihvaćen")) {
            return new SimpleObjectProperty<>(AmendmentsStatus.Prihvacen);
        }
        if (v.equals("odbijen")) {
            return new SimpleObjectProperty<>(AmendmentsStatus.Odbijen);
        }
        return null;
    }

    @Override
    public String marshal(ObjectProperty<AmendmentsStatus> v) throws Exception {
        if (v.get() == AmendmentsStatus.Predlozen) {
            return "predložen";
        }

        if (v.get() == AmendmentsStatus.Prihvacen) {
            return "prihvaćen";
        }

        if (v.get() == AmendmentsStatus.Odbijen) {
            return "odbijen";
        }
        return "";
    }
}
