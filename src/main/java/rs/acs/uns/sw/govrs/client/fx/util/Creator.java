package rs.acs.uns.sw.govrs.client.fx.util;

import javafx.beans.property.ObjectProperty;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.io.StringWriter;
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
        Ref podnosilacRef = new Ref();
        podnosilacRef.setContent("");
        podnosilacRef.setId(RestClientProvider.getInstance().getUser().getKorisnickoIme());
        law.getHead().getPodnosilac().getOtherAttributes().put(new QName("rel"), "pred:predlozenOd");
        law.getHead().getPodnosilac().getOtherAttributes().put(new QName("href"), "http://www.ftn.uns.ac.rs/rdf/examples/users/" + RestClientProvider.getInstance().getUser().getKorisnickoIme());
        law.getHead().getPodnosilac().getOtherAttributes().put(new QName("typeOf"), "pred:Odbornik");
        law.getHead().getOtherAttributes().put(new QName("vocab"), "http://www.parlament.gov.rs/rdf_schema/skupstina");
        law.getHead().getOtherAttributes().put(new QName("about"), "http://www.ftn.uns.ac.rs/rdf/examples/laws/" + law.idProperty().get());
        law.getHead().getOtherAttributes().put(new QName("typeOf"), "pred:Odluka");
        law.getHead().getDatumPredloga().getOtherAttributes().put(new QName("datatype"), "xs:date");
        law.getHead().getDatumPredloga().getOtherAttributes().put(new QName("property"), "pred:datumPredloga");
        law.getHead().getDatumIzglasavanja().getOtherAttributes().put(new QName("datatype"), "xs:date");
        law.getHead().getDatumIzglasavanja().getOtherAttributes().put(new QName("property"), "pred:datumIzglasavanja");
        law.getHead().getStatus().getOtherAttributes().put(new QName("datatype"), "xs:string");
        law.getHead().getStatus().getOtherAttributes().put(new QName("property"), "pred:StatusOdluke");
        law.getHead().getGlasovaZa().getOtherAttributes().put(new QName("datatype"), "xs:int");
        law.getHead().getGlasovaZa().getOtherAttributes().put(new QName("property"), "pred:BrojGlasovaZa");
        law.getHead().getGlasovaProtiv().getOtherAttributes().put(new QName("datatype"), "xs:int");
        law.getHead().getGlasovaProtiv().getOtherAttributes().put(new QName("property"), "pred:BrojGlasovaProtiv");
        law.getHead().getGlasovaSuzdrzani().getOtherAttributes().put(new QName("datatype"), "xs:int");
        law.getHead().getGlasovaSuzdrzani().getOtherAttributes().put(new QName("property"), "pred:BrojGlasovaUzdrzanih");


        Ref ref = factory.createRef();
        ref.setId(RestClientProvider.getInstance().getUser().getKorisnickoIme());
        law.getHead().getPodnosilac().setRef(ref);
        law.getHead().setMjesto("New York");

        return law;
    }

    public static Amendments createNewAmendments(String lawId) {
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
        amendments.getBody().setPravniOsnov(createPravniOsnov());
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
        // TODO change to real law ID
        amendments.getHead().setPropis(createPropis(lawId));

        Ref podnosilacRef = new Ref();
        podnosilacRef.setContent("");
        podnosilacRef.setId(RestClientProvider.getInstance().getUser().getKorisnickoIme());
        amendments.getHead().getPodnosilac().setRef(podnosilacRef);
        amendments.getHead().getPodnosilac().getOtherAttributes().put(new QName("rel"), "pred:predlozenOd");
        amendments.getHead().getPodnosilac().getOtherAttributes().put(new QName("href"), "http://www.ftn.uns.ac.rs/rdf/examples/users/" + RestClientProvider.getInstance().getUser().getKorisnickoIme());
        amendments.getHead().getPodnosilac().getOtherAttributes().put(new QName("typeOf"), "pred:Odbornik");

        amendments.getHead().getOtherAttributes().put(new QName("vocab"), "http://www.parlament.gov.rs/rdf_schema/skupstina");
        amendments.getHead().getOtherAttributes().put(new QName("about"), "http://www.ftn.uns.ac.rs/rdf/examples/amendments/" + amendments.idProperty().get());
        amendments.getHead().getOtherAttributes().put(new QName("typeOf"), "pred:Amandman");

        amendments.getHead().getDatumPredloga().getOtherAttributes().put(new QName("datatype"), "xs:date");
        amendments.getHead().getDatumPredloga().getOtherAttributes().put(new QName("property"), "pred:datumPredloga");
        amendments.getHead().getDatumIzglasavanja().getOtherAttributes().put(new QName("datatype"), "xs:date");
        amendments.getHead().getDatumIzglasavanja().getOtherAttributes().put(new QName("property"), "pred:datumIzglasavanja");
        amendments.getHead().getStatus().getOtherAttributes().put(new QName("datatype"), "xs:string");
        amendments.getHead().getStatus().getOtherAttributes().put(new QName("property"), "pred:statusAmandmana");
        amendments.getHead().getGlasovaZa().getOtherAttributes().put(new QName("datatype"), "xs:int");
        amendments.getHead().getGlasovaZa().getOtherAttributes().put(new QName("property"), "pred:brojGlasovaZa");
        amendments.getHead().getGlasovaProtiv().getOtherAttributes().put(new QName("datatype"), "xs:int");
        amendments.getHead().getGlasovaProtiv().getOtherAttributes().put(new QName("property"), "pred:brojGlasovaProtiv");
        amendments.getHead().getGlasovaSuzdrzani().getOtherAttributes().put(new QName("datatype"), "xs:int");
        amendments.getHead().getGlasovaSuzdrzani().getOtherAttributes().put(new QName("property"), "pred:brojGlasovaUzdrzanih");

        return amendments;
    }

    public static Amendment createOneAmendment() {
        ObjectFactory factory = new ObjectFactory();
        Amendment amendment = factory.createAmendment();
        amendment.setName("Novi amandman");
        amendment.setHead(factory.createAmendmentHead());
        amendment.setBody(factory.createAmendmentBody());
        amendment.getBody().setObrazlozenje(factory.createExplanation());
        amendment.getHead().setRjesenje("");
        return amendment;
    }

    public static Amendments.Head.Propis createPropis(String lawId) {
        ObjectFactory factory = new ObjectFactory();
        Amendments.Head.Propis propis = new Amendments.Head.Propis();
        Ref ref = new Ref();
        ref.setContent("");
        ref.setId(lawId);
        propis.setRef(ref);
        propis.getOtherAttributes().put(new QName("rel"), "pred:seOdnosiNa");
        propis.getOtherAttributes().put(new QName("href"), "http://www.ftn.uns.ac.rs/rdf/examples/laws/" + lawId);
        propis.getOtherAttributes().put(new QName("typeof"), "pred:Odluka");
        return propis;
    }

    public static Amendment.Body.Odredba createOdredba() {
        ObjectFactory factory = new ObjectFactory();
        Amendment.Body.Odredba odredba = factory.createAmendmentBodyOdredba();
        return odredba;
    }

    public static Amendments.Body.PravniOsnov createPravniOsnov() {
        ObjectFactory factory = new ObjectFactory();
        Amendments.Body.PravniOsnov pravniOsnov = factory.createAmendmentsBodyPravniOsnov();
        Article a = new Article();
        a.setId("pravni_osnov");
        a.setName("Pravni osnov");
        a.initElement();
        Paragraph p = new Paragraph();
        p.setId("pravni_osnov/pp_id");
        p.setName("Stav 1");
        StringWrapper sw = new StringWrapper("Prema Stavu 1. Člana 7, Zakona o podnošenju amandmana na predlog zakona, donosi se amandman u sledećoj pisanoj formi.");
        a.createAndAddChild(p);
        p.createAndAddChild(sw);
        a.preMarshaller();
        pravniOsnov.setClan(a);
        return pravniOsnov;
    }
}
