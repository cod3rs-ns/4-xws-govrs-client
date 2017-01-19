package rs.acs.uns.sw.govrs.client.fx.util;

import rs.acs.uns.sw.govrs.client.fx.serverdomain.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

public class ObjectCreator {

    // TODO change when IDGenerator is implemented
    public static Law createNewLaw() {
        ObjectFactory factory = new ObjectFactory();

        GregorianCalendar gregorianCalendar = GregorianCalendar.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()));
        XMLGregorianCalendar dateTest = null;
        try {
            dateTest = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        Law law = factory.createLaw();
        law.setId("law00");
        law.setHead(factory.createLawHead());
        law.setBody(factory.createLawBody());

        law.getHead().setDatumIzglasavanja(factory.createLawHeadDatumIzglasavanja());
        law.getHead().getDatumIzglasavanja().setValue(dateTest);
        law.getHead().setDatumPredloga(factory.createLawHeadDatumPredloga());
        law.getHead().getDatumPredloga().setValue(dateTest);
        law.getHead().setGlasovaProtiv(factory.createLawHeadGlasovaProtiv());
        law.getHead().setGlasovaSuzdrzani(factory.createLawHeadGlasovaSuzdrzani());
        law.getHead().setGlasovaZa(factory.createLawHeadGlasovaZa());
        law.getHead().setStatus(factory.createLawHeadStatus());
        law.getHead().setPodnosilac(factory.createLawHeadPodnosilac());
        Ref ref = factory.createRef();
        ref.setId("autor");
        law.getHead().getPodnosilac().setRef(ref);
        law.getHead().setMjesto("New York");

        return law;
    }
}
