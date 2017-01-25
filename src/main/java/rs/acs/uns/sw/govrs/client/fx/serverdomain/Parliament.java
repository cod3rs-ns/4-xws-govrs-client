
package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.DatePropertyAdapter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.IntegerPropertyAdapter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.StringPropertyAdapter;
import rs.acs.uns.sw.govrs.client.fx.validation.ErrorMessage;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * <p>Java class for skupstina element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="skupstina">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="head">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="datum_odrzavanja" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                     &lt;element name="mjesto_odrzavanja" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                     &lt;element name="broj_prisutnih" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                     &lt;element name="status">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                           &lt;enumeration value="sazvana"/>
 *                           &lt;enumeration value="odlo�ena"/>
 *                           &lt;enumeration value="u_toku"/>
 *                           &lt;enumeration value="zavr�ena"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="body">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="akti">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}ref" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
 *         &lt;attGroup ref="{http://www.parlament.gov.rs/schema/elementi}standard_attrs"/>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "head",
    "body"
})
@XmlRootElement(name = "skupstina", namespace = "http://www.parlament.gov.rs/schema/skupstina")
public class Parliament extends Element{

    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/skupstina", required = true)
    protected Parliament.Head head;
    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/skupstina", required = true)
    protected Parliament.Body body;
    @XmlAttribute(name = "id", required = true)

    @XmlSchemaType(name = "anyURI")
    @XmlJavaTypeAdapter(StringPropertyAdapter.class)
    protected StringProperty id =  new SimpleStringProperty();
    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(StringPropertyAdapter.class)
    protected StringProperty name = new SimpleStringProperty();

    @XmlTransient
    public ObservableList<Law> laws = FXCollections.observableArrayList();

    /**
     * Gets the value of the head property.
     *
     * @return
     *     possible object is
     *     {@link Parliament.Head }
     *
     */
    public Parliament.Head getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     *
     * @param value
     *     allowed object is
     *     {@link Parliament.Head }
     *
     */
    public void setHead(Parliament.Head value) {
        this.head = value;
    }

    /**
     * Gets the value of the body property.
     *
     * @return
     *     possible object is
     *     {@link Parliament.Body }
     *
     */
    public Parliament.Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     *
     * @param value
     *     allowed object is
     *     {@link Parliament.Body }
     *
     */
    public void setBody(Parliament.Body value) {
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
        return id.get();
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
        this.id.set(value);
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
        this.name.get();
    }

    @Override
    public String getElementName() {
        return null;
    }

    @Override
    public void setElementName(String name) {

    }

    @Override
    public StringProperty elementNameProperty() {
        return null;
    }

    @Override
    public void initElement() {

    }

    @Override
    public void createAndAddChild(Element element) {

    }

    @Override
    public void removeChild(Element element) {

    }

    @Override
    public void createPropertyAttrs() {

    }

    @Override
    public void preMarshaller() {

    }

    @Override
    public StringProperty idProperty() {
        return null;
    }

    @Override
    public void validate(List<ErrorMessage> errorMessageList) {

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
     *         &lt;element name="akti">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}ref" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "akti"
    })
    public static class Body {

        @XmlElement(namespace = "http://www.parlament.gov.rs/schema/skupstina", required = true)
        protected Parliament.Body.Akti akti;

        /**
         * Gets the value of the akti property.
         *
         * @return
         *     possible object is
         *     {@link Parliament.Body.Akti }
         *
         */
        public Parliament.Body.Akti getAkti() {
            return akti;
        }

        /**
         * Sets the value of the akti property.
         *
         * @param value
         *     allowed object is
         *     {@link Parliament.Body.Akti }
         *
         */
        public void setAkti(Parliament.Body.Akti value) {
            this.akti = value;
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
         *         &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}ref" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
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
        public static class Akti {

            @XmlElement(namespace = "http://www.parlament.gov.rs/schema/elementi")
            protected List<Ref> ref;

            /**
             * Gets the value of the ref property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the ref property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getRef().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ref }
             * 
             * 
             */
            public List<Ref> getRef() {
                if (ref == null) {
                    ref = new ArrayList<Ref>();
                }
                return this.ref;
            }

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
     *         &lt;element name="datum_odrzavanja" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="mjesto_odrzavanja" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="broj_prisutnih" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="status">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="sazvana"/>
     *               &lt;enumeration value="odlo�ena"/>
     *               &lt;enumeration value="u_toku"/>
     *               &lt;enumeration value="zavr�ena"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "datumOdrzavanja",
        "mjestoOdrzavanja",
        "brojPrisutnih",
        "status"
    })
    public static class Head {

        @XmlElement(name = "datum_odrzavanja", namespace = "http://www.parlament.gov.rs/schema/skupstina", required = true)
        @XmlSchemaType(name = "date")
        @XmlJavaTypeAdapter(DatePropertyAdapter.class)
        protected ObjectProperty<LocalDate> datumOdrzavanja = new SimpleObjectProperty<>();

        @XmlElement(name = "mjesto_odrzavanja", namespace = "http://www.parlament.gov.rs/schema/skupstina", required = true)
        @XmlJavaTypeAdapter(StringPropertyAdapter.class)
        protected StringProperty mjestoOdrzavanja = new SimpleStringProperty();

        @XmlElement(name = "broj_prisutnih", namespace = "http://www.parlament.gov.rs/schema/skupstina")
        @XmlJavaTypeAdapter(IntegerPropertyAdapter.class)
        protected IntegerProperty brojPrisutnih = new SimpleIntegerProperty();

        @XmlElement(namespace = "http://www.parlament.gov.rs/schema/skupstina", required = true)
        @XmlJavaTypeAdapter(StringPropertyAdapter.class)
        protected StringProperty status = new SimpleStringProperty();

        /**
         * Gets the value of the datumOdrzavanja property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDatumOdrzavanja() {
            LocalDate localDate = datumOdrzavanja.get();
            GregorianCalendar gregorianCalendar = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            try {
                return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Sets the value of the datumOdrzavanja property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDatumOdrzavanja(XMLGregorianCalendar value) {
            LocalDate localDate = value.toGregorianCalendar().toZonedDateTime().toLocalDate();
            this.datumOdrzavanja.set(localDate);
        }

        /**
         * Gets the value of the mjestoOdrzavanja property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMjestoOdrzavanja() {
            return mjestoOdrzavanja.get();
        }

        /**
         * Sets the value of the mjestoOdrzavanja property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMjestoOdrzavanja(String value) {
            this.mjestoOdrzavanja.set(value);
        }

        /**
         * Gets the value of the brojPrisutnih property.
         * 
         */
        public int getBrojPrisutnih() {
            return brojPrisutnih.get();
        }

        /**
         * Sets the value of the brojPrisutnih property.
         * 
         */
        public void setBrojPrisutnih(int value) {
            this.brojPrisutnih.set(value);
        }

        /**
         * Gets the value of the status property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatus() {
            return status.get();
        }

        /**
         * Sets the value of the status property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatus(String value) {
            this.status.set(value);
        }

    }

}
