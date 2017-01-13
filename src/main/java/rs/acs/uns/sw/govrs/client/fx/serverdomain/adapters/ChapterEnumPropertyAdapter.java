package rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.ChapterRoles;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ChapterEnumPropertyAdapter extends XmlAdapter<String, ObjectProperty<ChapterRoles>> {
    @Override
    public ObjectProperty<ChapterRoles> unmarshal(String v) throws Exception {
        if (v.equals("uvodni_dio")) {
            return new SimpleObjectProperty<>(ChapterRoles.UvodniDeo);
        }
        if (v.equals("glavni_dio")) {
            return new SimpleObjectProperty<>(ChapterRoles.GlavniDeo);
        }
        if (v.equals("zavrsni_dio")) {
            return new SimpleObjectProperty<>(ChapterRoles.ZavrsniDeo);
        }
        return null;
    }

    @Override
    public String marshal(ObjectProperty<ChapterRoles> v) throws Exception {
        if (v.get() == ChapterRoles.UvodniDeo) {
            return "uvodni_dio";
        }

        if (v.get() == ChapterRoles.GlavniDeo) {
            return "glavni_dio";
        }

        if (v.get() == ChapterRoles.ZavrsniDeo) {
            return "zavrsni_dio";
        }
        return "";
    }
}
