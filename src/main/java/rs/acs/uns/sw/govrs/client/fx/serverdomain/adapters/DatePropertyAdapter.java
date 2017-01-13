package rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

public class DatePropertyAdapter extends XmlAdapter<XMLGregorianCalendar, ObjectProperty<LocalDate>> {
    @Override
    public ObjectProperty<LocalDate> unmarshal(XMLGregorianCalendar v) throws Exception {
        LocalDate localDate = v.toGregorianCalendar().toZonedDateTime().toLocalDate();
        return new SimpleObjectProperty<>(localDate);
    }

    @Override
    public XMLGregorianCalendar marshal(ObjectProperty<LocalDate> v) throws Exception {
        LocalDate localDate = v.get();
        GregorianCalendar gregorianCalendar = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }
}
