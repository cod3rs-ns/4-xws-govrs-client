package rs.acs.uns.sw.govrs.client.fx.util;

import rs.acs.uns.sw.govrs.client.fx.serverdomain.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

public class Creator {
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
        law.setId(IdentityGenerator.get().generate(null, ElementType.Law));
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

    public static Amendments createNewAmendments() {
        ObjectFactory factory = new ObjectFactory();
        GregorianCalendar gregorianCalendar = GregorianCalendar.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()));
        XMLGregorianCalendar dateTest = null;
        try {
            dateTest = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        Amendments amendments = factory.createAmendments();
        amendments.setHead(factory.createAmendmentsHead());
        amendments.setBody(factory.createAmendmentsBody());
        amendments.setId(IdentityGenerator.get().generate(null, ElementType.Amendments));
        amendments.setName("Neki novi amandmani");
        amendments.getHead().setDatumIzglasavanja(factory.createAmendmentsHeadDatumIzglasavanja());
        amendments.getHead().getDatumIzglasavanja().setValue(dateTest);
        amendments.getHead().setDatumPredloga(factory.createAmendmentsHeadDatumPredloga());
        amendments.getHead().getDatumPredloga().setValue(dateTest);
        amendments.getHead().setGlasovaProtiv(factory.createAmendmentsHeadGlasovaProtiv());
        amendments.getHead().setGlasovaSuzdrzani(factory.createAmendmentsHeadGlasovaSuzdrzani());
        amendments.getHead().setGlasovaZa(factory.createAmendmentsHeadGlasovaZa());
        amendments.getHead().setStatus(factory.createAmendmentsHeadStatus());
        amendments.getHead().setPodnosilac(factory.createAmendmentsHeadPodnosilac());
        amendments.getHead().setPropis(factory.createAmendmentsHeadPropis());
        amendments.getHead().getPropis().setRef(factory.createRef());
        // TODO
        amendments.getHead().getPropis().getRef().setId("law01");
        return amendments;
    }

    public static Amendment createOneAmendment() {
        ObjectFactory factory = new ObjectFactory();
        Amendment amendment = factory.createAmendment();
        amendment.setId("aa_id");
        amendment.setName("novi amandman");
        amendment.setHead(factory.createAmendmentHead());
        amendment.setBody(factory.createAmendmentBody());
        amendment.getHead().setPredmet(factory.createAmendmentHeadPredmet());
        amendment.getHead().getPredmet().setRef(factory.createRef());
        amendment.getHead().getPredmet().getRef().setId("article01");
        amendment.getBody().setObrazlozenje(factory.createExplanation());
        amendment.getHead().setRjesenje("");
        return amendment;
    }

    public static Amendment.Body.Odredba createOdredba() {
        ObjectFactory factory = new ObjectFactory();
        Amendment.Body.Odredba odredba = factory.createAmendmentBodyOdredba();
        return odredba;
    }
}
