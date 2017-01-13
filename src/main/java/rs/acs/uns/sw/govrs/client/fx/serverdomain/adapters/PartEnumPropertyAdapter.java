package rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.PartRoles;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PartEnumPropertyAdapter extends XmlAdapter<String, ObjectProperty<PartRoles>> {

    @Override
    public ObjectProperty<PartRoles> unmarshal(String v) throws Exception {
        if (v.equals("uvodne_odredbe")) {
            return new SimpleObjectProperty<>(PartRoles.UvodneOdredbe);
        }
        if (v.equals("opste_odredbe")) {
            return new SimpleObjectProperty<>(PartRoles.OpsteOdredbe);
        }
        if (v.equals("zavrsne_odredbe")) {
            return new SimpleObjectProperty<>(PartRoles.ZavrsneOdredbe);
        }
        return null;
    }

    @Override
    public String marshal(ObjectProperty<PartRoles> v) throws Exception {
        if (v.get() == PartRoles.UvodneOdredbe) {
            return "uvodne_odredbe";
        }

        if (v.get() == PartRoles.OpsteOdredbe) {
            return "opste_odredbe";
        }

        if (v.get() == PartRoles.ZavrsneOdredbe) {
            return "zavrsne_odredbe";
        }
        return "";
    }
}
