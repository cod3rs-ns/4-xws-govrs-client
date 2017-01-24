package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import javafx.beans.property.*;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.DocumentStatusPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.IntegerPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.LocalDatePropertyItem;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.StringPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.DatePropertyAdapter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.DocumentStatusAdapter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.IntegerPropertyAdapter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.StringPropertyAdapter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.DocumentStatus;
import rs.acs.uns.sw.govrs.client.fx.util.ElementType;
import rs.acs.uns.sw.govrs.client.fx.util.IdentityGenerator;
import rs.acs.uns.sw.govrs.client.fx.validation.ErrorMessage;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "head",
        "body"
})
@XmlRootElement(name = "propis", namespace = "http://www.parlament.gov.rs/schema/propis")
public class Law extends Element {

    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/propis", required = true)
    protected Law.Head head;

    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/propis", required = true)
    protected Law.Body body;

    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "anyURI")
    @XmlJavaTypeAdapter(StringPropertyAdapter.class)
    protected StringProperty id = new SimpleStringProperty();

    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(StringPropertyAdapter.class)
    protected StringProperty name = new SimpleStringProperty("Propis");

    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the head property.
     *
     * @return possible object is
     * {@link Law.Head }
     */
    public Law.Head getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     *
     * @param value allowed object is
     *              {@link Law.Head }
     */
    public void setHead(Law.Head value) {
        this.head = value;
    }

    /**
     * Gets the value of the body property.
     *
     * @return possible object is
     * {@link Law.Body }
     */
    public Law.Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     *
     * @param value allowed object is
     *              {@link Law.Body }
     */
    public void setBody(Law.Body value) {
        this.body = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getId() {
        return id.get();
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setId(String value) {
        this.id.set(value);
    }

    public StringProperty idProperty() {
        return id;
    }


    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        this.name.set(value);
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getElementName() {
        return name.get();
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */

    public void setElementName(String value) {
        this.name.setValue(value);
    }

    public StringProperty elementNameProperty() {
        return name;
    }


    @Override
    public void initElement() {
        // add all chapters
        for (Element e : getBody().getDio()) {
            getChildren().add(e);
        }

        // add all parts
        for (Element e : getBody().getGlava()) {
            getChildren().add(e);
        }

        // init observable list for all children
        for (Element e : getChildren()) {
            e.setElementParent(this);
            e.initElement();
        }

        // create property list for context
        createPropertyAttrs();
    }

    @Override
    public void createAndAddChild(Element element) {
        // create Part
        if (element instanceof Part) {
            Part p = (Part) element;
            p.idProperty().set(IdentityGenerator.get().generate(this, ElementType.Part));
            p.setElementParent(this);
            p.createPropertyAttrs();
            getBody().getGlava().add(p);
            getChildren().add(p);
        }
        // create Chapter
        if (element instanceof Chapter) {
            Chapter c = (Chapter) element;
            c.idProperty().set(IdentityGenerator.get().generate(this, ElementType.Chapter));
            c.setElementParent(this);
            c.createPropertyAttrs();
            getBody().getDio().add(c);
            getChildren().add(c);
        }
    }

    @Override
    public void removeChild(Element element) {
        // create Part
        if (element instanceof Part) {
            Part p = (Part) element;
            getBody().getGlava().remove(p);
            getChildren().remove(p);
        }
        // create Chapter
        if (element instanceof Chapter) {
            Chapter c = (Chapter) element;
            getBody().getDio().remove(c);
            getChildren().remove(c);
        }
    }

    @Override
    public void createPropertyAttrs() {
        StringPropertyItem idPropertyItem = new StringPropertyItem(
                idProperty(),
                "Generalno",
                "ID ",
                "Jedinstveni identifikator",
                false);
        StringPropertyItem namePropertyItem = new StringPropertyItem(
                elementNameProperty(),
                "Generalno",
                "Naziv",
                "Naziv elementa",
                true);
        DocumentStatusPropertyItem statusPropertyItem = new DocumentStatusPropertyItem(
                getHead().getStatus().valueProperty(),
                "Status",
                "Status predloga",
                "Trenutni status Akta na Skupštinskom repertoaru",
                true);
        LocalDatePropertyItem propositionDatePropertyItem = new LocalDatePropertyItem(
                getHead().getDatumPredloga().valueProperty(),
                "Status",
                "Datum predloga",
                "Datum kada je podnet ovaj pravni Akt",
                true);
        LocalDatePropertyItem acceptanceDatePropertyItem = new LocalDatePropertyItem(
                getHead().getDatumIzglasavanja().valueProperty(),
                "Status",
                "Datum izglasavanja",
                "Datum kada se glasalo o ovom Aktu",
                true);
        IntegerPropertyItem yesVotesPropertyItem = new IntegerPropertyItem(
                getHead().getGlasovaZa().valueProperty(),
                "Skupština",
                "Glasova ZA",
                "Broj poslanika koji su glasali ZA usvajanje ovog Akta",
                true);
        IntegerPropertyItem noVotesPropertyItem = new IntegerPropertyItem(
                getHead().getGlasovaProtiv().valueProperty(),
                "Skupština",
                "Glasova PROTIV",
                "Broj poslanika koji su glasali PROTIV usvajanja ovog Akta",
                true);
        IntegerPropertyItem sustainedVotesPropertyItem = new IntegerPropertyItem(
                getHead().getGlasovaSuzdrzani().valueProperty(),
                "Skupština",
                "Suzdržanih",
                "Broj poslanika koji su bili suzdržani za usvajanje ovog Akta",
                true);
        StringPropertyItem placePropertyItem = new StringPropertyItem(
                getHead().mjestoProperty(),
                "Skupština",
                "Mesto",
                "Mesto gde se nalazi ustanova koja odlučuje o Aktu",
                false);

        getPropertyItems().add(idPropertyItem);
        getPropertyItems().add(namePropertyItem);
        getPropertyItems().add(statusPropertyItem);
        getPropertyItems().add(propositionDatePropertyItem);
        getPropertyItems().add(acceptanceDatePropertyItem);
        getPropertyItems().add(yesVotesPropertyItem);
        getPropertyItems().add(noVotesPropertyItem);
        getPropertyItems().add(sustainedVotesPropertyItem);
        getPropertyItems().add(placePropertyItem);
    }

    @Override
    public void preMarshaller() {
        for (Element child : getChildren()) {
            child.preMarshaller();
        }
    }


    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * <p>
     * <p>
     * the map is keyed by the name of the attribute and
     * the value is the string value of the attribute.
     * <p>
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     *
     * @return always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }


    /**
     * <p>Java class for anonymous complex type.
     * <p>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;choice>
     *         &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}glava" maxOccurs="unbounded"/>
     *         &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}dio" maxOccurs="unbounded"/>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "glava",
            "dio"
    })
    public static class Body {

        @XmlElement(namespace = "http://www.parlament.gov.rs/schema/elementi")
        protected List<Part> glava;
        @XmlElement(namespace = "http://www.parlament.gov.rs/schema/elementi")
        protected List<Chapter> dio;

        /**
         * Gets the value of the glava property.
         * <p>
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the glava property.
         * <p>
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGlava().add(newItem);
         * </pre>
         * <p>
         * <p>
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Part }
         */
        public List<Part> getGlava() {
            if (glava == null) {
                glava = new ArrayList<Part>();
            }
            return this.glava;
        }

        /**
         * Gets the value of the dio property.
         * <p>
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dio property.
         * <p>
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDio().add(newItem);
         * </pre>
         * <p>
         * <p>
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Chapter }
         */
        public List<Chapter> getDio() {
            if (dio == null) {
                dio = new ArrayList<Chapter>();
            }
            return this.dio;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * <p>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="datum_predloga">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>date">
     *                 &lt;anyAttribute processContents='lax'/>
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="datum_izglasavanja">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>date">
     *                 &lt;anyAttribute processContents='lax'/>
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="status">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;anyAttribute processContents='lax'/>
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="mjesto" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="glasova_za">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>int">
     *                 &lt;anyAttribute processContents='lax'/>
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="glasova_protiv">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>int">
     *                 &lt;anyAttribute processContents='lax'/>
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="glasova_suzdrzani">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>int">
     *                 &lt;anyAttribute processContents='lax'/>
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="podnosilac">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}ref"/>
     *                 &lt;/sequence>
     *                 &lt;anyAttribute processContents='lax'/>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;anyAttribute processContents='lax'/>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "datumPredloga",
            "datumIzglasavanja",
            "status",
            "mjesto",
            "glasovaZa",
            "glasovaProtiv",
            "glasovaSuzdrzani",
            "podnosilac"
    })
    public static class Head {

        @XmlElement(name = "datum_predloga", namespace = "http://www.parlament.gov.rs/schema/propis", required = true)
        protected Law.Head.DatumPredloga datumPredloga;
        @XmlElement(name = "datum_izglasavanja", namespace = "http://www.parlament.gov.rs/schema/propis", required = true)
        protected Law.Head.DatumIzglasavanja datumIzglasavanja;
        @XmlElement(namespace = "http://www.parlament.gov.rs/schema/propis", required = true)
        protected Law.Head.Status status;
        @XmlElement(name = "glasova_za", namespace = "http://www.parlament.gov.rs/schema/propis", required = true)
        protected Law.Head.GlasovaZa glasovaZa;
        @XmlElement(name = "glasova_protiv", namespace = "http://www.parlament.gov.rs/schema/propis", required = true)
        protected Law.Head.GlasovaProtiv glasovaProtiv;
        @XmlElement(name = "glasova_suzdrzani", namespace = "http://www.parlament.gov.rs/schema/propis", required = true)
        protected Law.Head.GlasovaSuzdrzani glasovaSuzdrzani;
        @XmlElement(namespace = "http://www.parlament.gov.rs/schema/propis", required = true)
        protected Law.Head.Podnosilac podnosilac;

        @XmlElement(namespace = "http://www.parlament.gov.rs/schema/propis", required = true)
        @XmlJavaTypeAdapter(StringPropertyAdapter.class)
        protected StringProperty mjesto = new SimpleStringProperty();

        @XmlAnyAttribute
        private Map<QName, String> otherAttributes = new HashMap<QName, String>();

        /**
         * Gets the value of the datumPredloga property.
         *
         * @return possible object is
         * {@link Law.Head.DatumPredloga }
         */
        public Law.Head.DatumPredloga getDatumPredloga() {
            return datumPredloga;
        }

        /**
         * Sets the value of the datumPredloga property.
         *
         * @param value allowed object is
         *              {@link Law.Head.DatumPredloga }
         */
        public void setDatumPredloga(Law.Head.DatumPredloga value) {
            this.datumPredloga = value;
        }

        /**
         * Gets the value of the datumIzglasavanja property.
         *
         * @return possible object is
         * {@link Law.Head.DatumIzglasavanja }
         */
        public Law.Head.DatumIzglasavanja getDatumIzglasavanja() {
            return datumIzglasavanja;
        }

        /**
         * Sets the value of the datumIzglasavanja property.
         *
         * @param value allowed object is
         *              {@link Law.Head.DatumIzglasavanja }
         */
        public void setDatumIzglasavanja(Law.Head.DatumIzglasavanja value) {
            this.datumIzglasavanja = value;
        }

        /**
         * Gets the value of the status property.
         *
         * @return possible object is
         * {@link Law.Head.Status }
         */
        public Law.Head.Status getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         *
         * @param value allowed object is
         *              {@link Law.Head.Status }
         */
        public void setStatus(Law.Head.Status value) {
            this.status = value;
        }

        /**
         * Gets the value of the mjesto property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getMjesto() {
            return mjesto.get();
        }

        /**
         * Sets the value of the mjesto property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setMjesto(String value) {
            this.mjesto.set(value);
        }

        public StringProperty mjestoProperty() {
            return mjesto;
        }

        /**
         * Gets the value of the glasovaZa property.
         *
         * @return possible object is
         * {@link Law.Head.GlasovaZa }
         */
        public Law.Head.GlasovaZa getGlasovaZa() {
            return glasovaZa;
        }

        /**
         * Sets the value of the glasovaZa property.
         *
         * @param value allowed object is
         *              {@link Law.Head.GlasovaZa }
         */
        public void setGlasovaZa(Law.Head.GlasovaZa value) {
            this.glasovaZa = value;
        }

        /**
         * Gets the value of the glasovaProtiv property.
         *
         * @return possible object is
         * {@link Law.Head.GlasovaProtiv }
         */
        public Law.Head.GlasovaProtiv getGlasovaProtiv() {
            return glasovaProtiv;
        }

        /**
         * Sets the value of the glasovaProtiv property.
         *
         * @param value allowed object is
         *              {@link Law.Head.GlasovaProtiv }
         */
        public void setGlasovaProtiv(Law.Head.GlasovaProtiv value) {
            this.glasovaProtiv = value;
        }

        /**
         * Gets the value of the glasovaSuzdrzani property.
         *
         * @return possible object is
         * {@link Law.Head.GlasovaSuzdrzani }
         */
        public Law.Head.GlasovaSuzdrzani getGlasovaSuzdrzani() {
            return glasovaSuzdrzani;
        }

        /**
         * Sets the value of the glasovaSuzdrzani property.
         *
         * @param value allowed object is
         *              {@link Law.Head.GlasovaSuzdrzani }
         */
        public void setGlasovaSuzdrzani(Law.Head.GlasovaSuzdrzani value) {
            this.glasovaSuzdrzani = value;
        }

        /**
         * Gets the value of the podnosilac property.
         *
         * @return possible object is
         * {@link Law.Head.Podnosilac }
         */
        public Law.Head.Podnosilac getPodnosilac() {
            return podnosilac;
        }

        /**
         * Sets the value of the podnosilac property.
         *
         * @param value allowed object is
         *              {@link Law.Head.Podnosilac }
         */
        public void setPodnosilac(Law.Head.Podnosilac value) {
            this.podnosilac = value;
        }

        /**
         * Gets a map that contains attributes that aren't bound to any typed property on this class.
         * <p>
         * <p>
         * the map is keyed by the name of the attribute and
         * the value is the string value of the attribute.
         * <p>
         * the map returned by this method is live, and you can add new attribute
         * by updating the map directly. Because of this design, there's no setter.
         *
         * @return always non-null
         */
        public Map<QName, String> getOtherAttributes() {
            return otherAttributes;
        }


        /**
         * <p>Java class for anonymous complex type.
         * <p>
         * <p>The following schema fragment specifies the expected content contained within this class.
         * <p>
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>date">
         *       &lt;anyAttribute processContents='lax'/>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        public static class DatumIzglasavanja {

            @XmlValue
            @XmlSchemaType(name = "date")
            @XmlJavaTypeAdapter(DatePropertyAdapter.class)
            protected ObjectProperty<LocalDate> value = new SimpleObjectProperty<>();

            @XmlAnyAttribute
            private Map<QName, String> otherAttributes = new HashMap<QName, String>();

            /**
             * Gets the value of the value property.
             *
             * @return possible object is
             * {@link XMLGregorianCalendar }
             */
            public XMLGregorianCalendar getValue() {
                LocalDate localDate = value.get();
                GregorianCalendar gregorianCalendar = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                try {
                    return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
                } catch (DatatypeConfigurationException e) {
                    e.printStackTrace();
                }
                return null;
            }

            /**
             * Sets the value of the value property.
             *
             * @param value allowed object is
             *              {@link XMLGregorianCalendar }
             */
            public void setValue(XMLGregorianCalendar value) {
                LocalDate localDate = value.toGregorianCalendar().toZonedDateTime().toLocalDate();
                this.value.set(localDate);
            }

            public ObjectProperty<LocalDate> valueProperty() {
                return value;
            }

            /**
             * Gets a map that contains attributes that aren't bound to any typed property on this class.
             * <p>
             * <p>
             * the map is keyed by the name of the attribute and
             * the value is the string value of the attribute.
             * <p>
             * the map returned by this method is live, and you can add new attribute
             * by updating the map directly. Because of this design, there's no setter.
             *
             * @return always non-null
             */
            public Map<QName, String> getOtherAttributes() {
                return otherAttributes;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * <p>
         * <p>The following schema fragment specifies the expected content contained within this class.
         * <p>
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>date">
         *       &lt;anyAttribute processContents='lax'/>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        public static class DatumPredloga {

            @XmlValue
            @XmlSchemaType(name = "date")
            @XmlJavaTypeAdapter(DatePropertyAdapter.class)
            protected ObjectProperty<LocalDate> value = new SimpleObjectProperty<>();

            @XmlAnyAttribute
            private Map<QName, String> otherAttributes = new HashMap<QName, String>();

            /**
             * Gets the value of the value property.
             *
             * @return possible object is
             * {@link XMLGregorianCalendar }
             */
            public XMLGregorianCalendar getValue() {
                LocalDate localDate = value.get();
                GregorianCalendar gregorianCalendar = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                try {
                    return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
                } catch (DatatypeConfigurationException e) {
                    e.printStackTrace();
                }
                return null;
            }

            /**
             * Sets the value of the value property.
             *
             * @param value allowed object is
             *              {@link XMLGregorianCalendar }
             */
            public void setValue(XMLGregorianCalendar value) {
                LocalDate localDate = value.toGregorianCalendar().toZonedDateTime().toLocalDate();
                this.value.set(localDate);
            }

            public ObjectProperty<LocalDate> valueProperty() {
                return value;
            }

            /**
             * Gets a map that contains attributes that aren't bound to any typed property on this class.
             * <p>
             * <p>
             * the map is keyed by the name of the attribute and
             * the value is the string value of the attribute.
             * <p>
             * the map returned by this method is live, and you can add new attribute
             * by updating the map directly. Because of this design, there's no setter.
             *
             * @return always non-null
             */
            public Map<QName, String> getOtherAttributes() {
                return otherAttributes;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * <p>
         * <p>The following schema fragment specifies the expected content contained within this class.
         * <p>
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>int">
         *       &lt;anyAttribute processContents='lax'/>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        public static class GlasovaProtiv {

            @XmlValue
            @XmlJavaTypeAdapter(IntegerPropertyAdapter.class)
            protected IntegerProperty value = new SimpleIntegerProperty(-1);

            @XmlAnyAttribute
            private Map<QName, String> otherAttributes = new HashMap<QName, String>();

            /**
             * Gets the value of the value property.
             */
            public int getValue() {
                return value.get();
            }

            /**
             * Sets the value of the value property.
             */
            public void setValue(int value) {
                this.value.set(value);
            }

            public IntegerProperty valueProperty() {
                return value;
            }

            /**
             * Gets a map that contains attributes that aren't bound to any typed property on this class.
             * <p>
             * <p>
             * the map is keyed by the name of the attribute and
             * the value is the string value of the attribute.
             * <p>
             * the map returned by this method is live, and you can add new attribute
             * by updating the map directly. Because of this design, there's no setter.
             *
             * @return always non-null
             */
            public Map<QName, String> getOtherAttributes() {
                return otherAttributes;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * <p>
         * <p>The following schema fragment specifies the expected content contained within this class.
         * <p>
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>int">
         *       &lt;anyAttribute processContents='lax'/>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        public static class GlasovaSuzdrzani {

            @XmlValue
            @XmlJavaTypeAdapter(IntegerPropertyAdapter.class)
            protected IntegerProperty value = new SimpleIntegerProperty(-1);

            @XmlAnyAttribute
            private Map<QName, String> otherAttributes = new HashMap<QName, String>();

            /**
             * Gets the value of the value property.
             */
            public int getValue() {
                return value.get();
            }

            /**
             * Sets the value of the value property.
             */
            public void setValue(int value) {
                this.value.set(value);
            }

            public IntegerProperty valueProperty() {
                return value;
            }

            /**
             * Gets a map that contains attributes that aren't bound to any typed property on this class.
             * <p>
             * <p>
             * the map is keyed by the name of the attribute and
             * the value is the string value of the attribute.
             * <p>
             * the map returned by this method is live, and you can add new attribute
             * by updating the map directly. Because of this design, there's no setter.
             *
             * @return always non-null
             */
            public Map<QName, String> getOtherAttributes() {
                return otherAttributes;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * <p>
         * <p>The following schema fragment specifies the expected content contained within this class.
         * <p>
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>int">
         *       &lt;anyAttribute processContents='lax'/>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        public static class GlasovaZa {

            @XmlValue
            @XmlJavaTypeAdapter(IntegerPropertyAdapter.class)
            protected IntegerProperty value = new SimpleIntegerProperty(-1);
            @XmlAnyAttribute
            private Map<QName, String> otherAttributes = new HashMap<QName, String>();

            /**
             * Gets the value of the value property.
             */
            public int getValue() {
                return value.get();
            }

            /**
             * Sets the value of the value property.
             */
            public void setValue(int value) {
                this.value.set(value);
            }

            public IntegerProperty valueProperty() {
                return value;
            }

            /**
             * Gets a map that contains attributes that aren't bound to any typed property on this class.
             * <p>
             * <p>
             * the map is keyed by the name of the attribute and
             * the value is the string value of the attribute.
             * <p>
             * the map returned by this method is live, and you can add new attribute
             * by updating the map directly. Because of this design, there's no setter.
             *
             * @return always non-null
             */
            public Map<QName, String> getOtherAttributes() {
                return otherAttributes;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * <p>
         * <p>The following schema fragment specifies the expected content contained within this class.
         * <p>
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}ref"/>
         *       &lt;/sequence>
         *       &lt;anyAttribute processContents='lax'/>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "ref"
        })
        public static class Podnosilac {

            @XmlElement(namespace = "http://www.parlament.gov.rs/schema/elementi", required = true)
            protected Ref ref;
            @XmlAnyAttribute
            private Map<QName, String> otherAttributes = new HashMap<QName, String>();

            /**
             * Gets the value of the ref property.
             *
             * @return possible object is
             * {@link Ref }
             */
            public Ref getRef() {
                return ref;
            }

            /**
             * Sets the value of the ref property.
             *
             * @param value allowed object is
             *              {@link Ref }
             */
            public void setRef(Ref value) {
                this.ref = value;
            }

            /**
             * Gets a map that contains attributes that aren't bound to any typed property on this class.
             * <p>
             * <p>
             * the map is keyed by the name of the attribute and
             * the value is the string value of the attribute.
             * <p>
             * the map returned by this method is live, and you can add new attribute
             * by updating the map directly. Because of this design, there's no setter.
             *
             * @return always non-null
             */
            public Map<QName, String> getOtherAttributes() {
                return otherAttributes;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * <p>
         * <p>The following schema fragment specifies the expected content contained within this class.
         * <p>
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;anyAttribute processContents='lax'/>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        public static class Status {

            @XmlValue
            @XmlJavaTypeAdapter(DocumentStatusAdapter.class)
            protected ObjectProperty<DocumentStatus> value = new SimpleObjectProperty<>(DocumentStatus.Predlozen);

            @XmlAnyAttribute
            private Map<QName, String> otherAttributes = new HashMap<QName, String>();

            /**
             * Gets the value of the value property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getValue() {
                if (value.get() == DocumentStatus.Predlozen) {
                    return "predložen";
                }

                if (value.get() == DocumentStatus.Prihvacen) {
                    return "prihvaćen";
                }

                if (value.get() == DocumentStatus.Odbijen) {
                    return "odbijen";
                }
                return "";
            }

            /**
             * Sets the value of the value property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setValue(String value) {
                if (value.equals("predložen")) {
                    this.value.set(DocumentStatus.Predlozen);
                }
                if (value.equals("prihvaćen")) {
                    this.value.set(DocumentStatus.Prihvacen);
                }
                if (value.equals("odbijen")) {
                    this.value.set(DocumentStatus.Odbijen);
                }
            }

            public ObjectProperty<DocumentStatus> valueProperty() {
                return value;
            }

            /**
             * Gets a map that contains attributes that aren't bound to any typed property on this class.
             * <p>
             * <p>
             * the map is keyed by the name of the attribute and
             * the value is the string value of the attribute.
             * <p>
             * the map returned by this method is live, and you can add new attribute
             * by updating the map directly. Because of this design, there's no setter.
             *
             * @return always non-null
             */
            public Map<QName, String> getOtherAttributes() {
                return otherAttributes;
            }

        }
    }

    @Override
    public void validate(List<ErrorMessage> errorMessageList) {
        if (name.get() == null || "".equals(name.get()))
            errorMessageList.add(new ErrorMessage(id.get(), name.getName(), ElementType.Law, "Ime zakona je obavezno."));
        if (getChildren().size() == 0)
            errorMessageList.add(new ErrorMessage(id.get(), name.getName(), ElementType.Law, "Zakon ne može biti prazan."));
        if (body.getDio().size() > 2)
            errorMessageList.add(new ErrorMessage(id.get(), name.getName(), ElementType.Law, "Zakon ne može da ima više od dva dijela."));
        if (body.getGlava().size() > 2)
            errorMessageList.add(new ErrorMessage(id.get(), name.getName(), ElementType.Law, "Zakon ne može da ima više od dvije glave."));
        if (body.getGlava().size() > 0 && body.getDio().size() > 0) {
            errorMessageList.add(new ErrorMessage(id.get(), name.getName(), ElementType.Law, "Zakon ne može istovremeno da sadrži i \nglave  i dijelove kao korijenski element."));
        }

        // validate children elements
        for (Element child : getChildren()) {
            child.validate(errorMessageList);
        }
    }
}
