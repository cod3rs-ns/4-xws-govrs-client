package rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DocumentStatusAdapter extends XmlAdapter<String, ObjectProperty<rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.DocumentStatus>> {
    @Override
    public ObjectProperty<rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.DocumentStatus> unmarshal(String v) throws Exception {
        if (v.equals("predložen")) {
            return new SimpleObjectProperty<>(rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.DocumentStatus.Predlozen);
        }
        if (v.equals("prihvaćen")) {
            return new SimpleObjectProperty<>(rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.DocumentStatus.Prihvacen);
        }
        if (v.equals("odbijen")) {
            return new SimpleObjectProperty<>(rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.DocumentStatus.Odbijen);
        }
        return null;
    }

    @Override
    public String marshal(ObjectProperty<rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.DocumentStatus> v) throws Exception {
        if (v.get() == rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.DocumentStatus.Predlozen) {
            return "predložen";
        }

        if (v.get() == rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.DocumentStatus.Prihvacen) {
            return "prihvaćen";
        }

        if (v.get() == rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.DocumentStatus.Odbijen) {
            return "odbijen";
        }
        return "";
    }
}
