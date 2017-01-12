package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.StringPropertyAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "head",
    "body"
})
@XmlRootElement(name = "propis", namespace = "http://www.parlament.gov.rs/schema/propis")
public class Law  extends Element{

    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/propis", required = true)
    protected Law.Head head;
    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/propis", required = true)
    protected Law.Body body;

    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String id;

    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(StringPropertyAdapter.class)
    protected StringProperty name = new SimpleStringProperty("Propis");

    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the head property.
     *
     * @return
     *     possible object is
     *     {@link Law.Head }
     *
     */
    public Law.Head getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     *
     * @param value
     *     allowed object is
     *     {@link Law.Head }
     *
     */
    public void setHead(Law.Head value) {
        this.head = value;
    }

    /**
     * Gets the value of the body property.
     *
     * @return
     *     possible object is
     *     {@link Law.Body }
     *
     */
    public Law.Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     *
     * @param value
     *     allowed object is
     *     {@link Law.Body }
     *
     */
    public void setBody(Law.Body value) {
        this.body = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name.get();
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */

    public void setName(String value) {
        this.name.setValue(value);
    }

    public StringProperty nameProperty() {
        return name;
    }


    @Override
    public void initChildrenObservableList() {
        // add all chapters
        for (Element e: getBody().getDio()){
            getChildren().add(e);
        }

        // add all parts
        for (Element e: getBody().getGlava()){
            getChildren().add(e);
        }

        // init observable list for all children
        for (Element e: getChildren()) {
            e.initChildrenObservableList();
        }
    }

    @Override
    public void createAndAddChild(Element element) {

    }

    @Override
    public String createElementOpening() {
        return null;
    }

    @Override
    public String createElementAttrs() {
        return null;
    }

    @Override
    public String createElementContent() {
        return null;
    }

    @Override
    public String createElementClosing() {
        return null;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     *
     * <p>
     * the map is keyed by the name of the attribute and
     * the value is the string value of the attribute.
     *
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     *
     *
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
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
     *
     *
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
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the glava property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGlava().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Part }
         *
         *
         */
        public List<Part> getGlava() {
            if (glava == null) {
                glava = new ArrayList<Part>();
            }
            return this.glava;
        }

        /**
         * Gets the value of the dio property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dio property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDio().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Chapter }
         *
         *
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
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
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
     *
     *
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
         * @return
         *     possible object is
         *     {@link Law.Head.DatumPredloga }
         *
         */
        public Law.Head.DatumPredloga getDatumPredloga() {
            return datumPredloga;
        }

        /**
         * Sets the value of the datumPredloga property.
         *
         * @param value
         *     allowed object is
         *     {@link Law.Head.DatumPredloga }
         *
         */
        public void setDatumPredloga(Law.Head.DatumPredloga value) {
            this.datumPredloga = value;
        }

        /**
         * Gets the value of the datumIzglasavanja property.
         *
         * @return
         *     possible object is
         *     {@link Law.Head.DatumIzglasavanja }
         *
         */
        public Law.Head.DatumIzglasavanja getDatumIzglasavanja() {
            return datumIzglasavanja;
        }

        /**
         * Sets the value of the datumIzglasavanja property.
         *
         * @param value
         *     allowed object is
         *     {@link Law.Head.DatumIzglasavanja }
         *
         */
        public void setDatumIzglasavanja(Law.Head.DatumIzglasavanja value) {
            this.datumIzglasavanja = value;
        }

        /**
         * Gets the value of the status property.
         *
         * @return
         *     possible object is
         *     {@link Law.Head.Status }
         *
         */
        public Law.Head.Status getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         *
         * @param value
         *     allowed object is
         *     {@link Law.Head.Status }
         *
         */
        public void setStatus(Law.Head.Status value) {
            this.status = value;
        }

        /**
         * Gets the value of the mjesto property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getMjesto() {
            return mjesto.get();
        }

        /**
         * Sets the value of the mjesto property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setMjesto(String value) {
            this.mjesto.setValue(value);
        }

        /**
         * Gets the value of the glasovaZa property.
         *
         * @return
         *     possible object is
         *     {@link Law.Head.GlasovaZa }
         *
         */
        public Law.Head.GlasovaZa getGlasovaZa() {
            return glasovaZa;
        }

        /**
         * Sets the value of the glasovaZa property.
         *
         * @param value
         *     allowed object is
         *     {@link Law.Head.GlasovaZa }
         *
         */
        public void setGlasovaZa(Law.Head.GlasovaZa value) {
            this.glasovaZa = value;
        }

        /**
         * Gets the value of the glasovaProtiv property.
         *
         * @return
         *     possible object is
         *     {@link Law.Head.GlasovaProtiv }
         *
         */
        public Law.Head.GlasovaProtiv getGlasovaProtiv() {
            return glasovaProtiv;
        }

        /**
         * Sets the value of the glasovaProtiv property.
         *
         * @param value
         *     allowed object is
         *     {@link Law.Head.GlasovaProtiv }
         *
         */
        public void setGlasovaProtiv(Law.Head.GlasovaProtiv value) {
            this.glasovaProtiv = value;
        }

        /**
         * Gets the value of the glasovaSuzdrzani property.
         *
         * @return
         *     possible object is
         *     {@link Law.Head.GlasovaSuzdrzani }
         *
         */
        public Law.Head.GlasovaSuzdrzani getGlasovaSuzdrzani() {
            return glasovaSuzdrzani;
        }

        /**
         * Sets the value of the glasovaSuzdrzani property.
         *
         * @param value
         *     allowed object is
         *     {@link Law.Head.GlasovaSuzdrzani }
         *
         */
        public void setGlasovaSuzdrzani(Law.Head.GlasovaSuzdrzani value) {
            this.glasovaSuzdrzani = value;
        }

        /**
         * Gets the value of the podnosilac property.
         *
         * @return
         *     possible object is
         *     {@link Law.Head.Podnosilac }
         *
         */
        public Law.Head.Podnosilac getPodnosilac() {
            return podnosilac;
        }

        /**
         * Sets the value of the podnosilac property.
         *
         * @param value
         *     allowed object is
         *     {@link Law.Head.Podnosilac }
         *
         */
        public void setPodnosilac(Law.Head.Podnosilac value) {
            this.podnosilac = value;
        }

        /**
         * Gets a map that contains attributes that aren't bound to any typed property on this class.
         * 
         * <p>
         * the map is keyed by the name of the attribute and 
         * the value is the string value of the attribute.
         * 
         * the map returned by this method is live, and you can add new attribute
         * by updating the map directly. Because of this design, there's no setter.
         * 
         * 
         * @return
         *     always non-null
         */
        public Map<QName, String> getOtherAttributes() {
            return otherAttributes;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>date">
         *       &lt;anyAttribute processContents='lax'/>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class DatumIzglasavanja {

            @XmlValue
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar value;
            @XmlAnyAttribute
            private Map<QName, String> otherAttributes = new HashMap<QName, String>();

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setValue(XMLGregorianCalendar value) {
                this.value = value;
            }

            /**
             * Gets a map that contains attributes that aren't bound to any typed property on this class.
             * 
             * <p>
             * the map is keyed by the name of the attribute and 
             * the value is the string value of the attribute.
             * 
             * the map returned by this method is live, and you can add new attribute
             * by updating the map directly. Because of this design, there's no setter.
             * 
             * 
             * @return
             *     always non-null
             */
            public Map<QName, String> getOtherAttributes() {
                return otherAttributes;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>date">
         *       &lt;anyAttribute processContents='lax'/>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class DatumPredloga {

            @XmlValue
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar value;
            @XmlAnyAttribute
            private Map<QName, String> otherAttributes = new HashMap<QName, String>();

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setValue(XMLGregorianCalendar value) {
                this.value = value;
            }

            /**
             * Gets a map that contains attributes that aren't bound to any typed property on this class.
             * 
             * <p>
             * the map is keyed by the name of the attribute and 
             * the value is the string value of the attribute.
             * 
             * the map returned by this method is live, and you can add new attribute
             * by updating the map directly. Because of this design, there's no setter.
             * 
             * 
             * @return
             *     always non-null
             */
            public Map<QName, String> getOtherAttributes() {
                return otherAttributes;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>int">
         *       &lt;anyAttribute processContents='lax'/>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class GlasovaProtiv {

            @XmlValue
            protected int value;
            @XmlAnyAttribute
            private Map<QName, String> otherAttributes = new HashMap<QName, String>();

            /**
             * Gets the value of the value property.
             * 
             */
            public int getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             */
            public void setValue(int value) {
                this.value = value;
            }

            /**
             * Gets a map that contains attributes that aren't bound to any typed property on this class.
             * 
             * <p>
             * the map is keyed by the name of the attribute and 
             * the value is the string value of the attribute.
             * 
             * the map returned by this method is live, and you can add new attribute
             * by updating the map directly. Because of this design, there's no setter.
             * 
             * 
             * @return
             *     always non-null
             */
            public Map<QName, String> getOtherAttributes() {
                return otherAttributes;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>int">
         *       &lt;anyAttribute processContents='lax'/>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class GlasovaSuzdrzani {

            @XmlValue
            protected int value;
            @XmlAnyAttribute
            private Map<QName, String> otherAttributes = new HashMap<QName, String>();

            /**
             * Gets the value of the value property.
             * 
             */
            public int getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             */
            public void setValue(int value) {
                this.value = value;
            }

            /**
             * Gets a map that contains attributes that aren't bound to any typed property on this class.
             * 
             * <p>
             * the map is keyed by the name of the attribute and 
             * the value is the string value of the attribute.
             * 
             * the map returned by this method is live, and you can add new attribute
             * by updating the map directly. Because of this design, there's no setter.
             * 
             * 
             * @return
             *     always non-null
             */
            public Map<QName, String> getOtherAttributes() {
                return otherAttributes;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>int">
         *       &lt;anyAttribute processContents='lax'/>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class GlasovaZa {

            @XmlValue
            protected int value;
            @XmlAnyAttribute
            private Map<QName, String> otherAttributes = new HashMap<QName, String>();

            /**
             * Gets the value of the value property.
             * 
             */
            public int getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             */
            public void setValue(int value) {
                this.value = value;
            }

            /**
             * Gets a map that contains attributes that aren't bound to any typed property on this class.
             * 
             * <p>
             * the map is keyed by the name of the attribute and 
             * the value is the string value of the attribute.
             * 
             * the map returned by this method is live, and you can add new attribute
             * by updating the map directly. Because of this design, there's no setter.
             * 
             * 
             * @return
             *     always non-null
             */
            public Map<QName, String> getOtherAttributes() {
                return otherAttributes;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
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
         * 
         * 
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
             * @return
             *     possible object is
             *     {@link Ref }
             *     
             */
            public Ref getRef() {
                return ref;
            }

            /**
             * Sets the value of the ref property.
             * 
             * @param value
             *     allowed object is
             *     {@link Ref }
             *     
             */
            public void setRef(Ref value) {
                this.ref = value;
            }

            /**
             * Gets a map that contains attributes that aren't bound to any typed property on this class.
             * 
             * <p>
             * the map is keyed by the name of the attribute and 
             * the value is the string value of the attribute.
             * 
             * the map returned by this method is live, and you can add new attribute
             * by updating the map directly. Because of this design, there's no setter.
             * 
             * 
             * @return
             *     always non-null
             */
            public Map<QName, String> getOtherAttributes() {
                return otherAttributes;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;anyAttribute processContents='lax'/>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Status {

            @XmlValue
            protected String value;
            @XmlAnyAttribute
            private Map<QName, String> otherAttributes = new HashMap<QName, String>();

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets a map that contains attributes that aren't bound to any typed property on this class.
             * 
             * <p>
             * the map is keyed by the name of the attribute and 
             * the value is the string value of the attribute.
             * 
             * the map returned by this method is live, and you can add new attribute
             * by updating the map directly. Because of this design, there's no setter.
             * 
             * 
             * @return
             *     always non-null
             */
            public Map<QName, String> getOtherAttributes() {
                return otherAttributes;
            }

        }

    }

}
