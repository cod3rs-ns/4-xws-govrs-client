
package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.StringPropertyAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for propis element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="propis">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;choice>
 *           &lt;element name="head">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="datum" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                     &lt;element name="status">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                           &lt;enumeration value="prihvacen"/>
 *                           &lt;enumeration value="predlozen"/>
 *                           &lt;enumeration value="odbijen"/>
 *                           &lt;enumeration value="povucen"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/element>
 *                     &lt;element name="podnosilac" type="{http://www.parlament.gov.rs/schema/korisnici}TKorisnik"/>
 *                     &lt;element name="mjesto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="body">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;choice>
 *                     &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}glava" maxOccurs="unbounded"/>
 *                     &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}dio" maxOccurs="unbounded"/>
 *                   &lt;/choice>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
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
@XmlRootElement(name = "propis", namespace = "http://www.parlament.gov.rs/schema/propis")
public class Law {

    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/propis")
    protected Law.Head head;
    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/propis")
    protected Law.Body body;

    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String id;

    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(StringPropertyAdapter.class)
    protected StringProperty name = new SimpleStringProperty();

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
     *         &lt;element name="datum" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="status">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="prihvacen"/>
     *               &lt;enumeration value="predlozen"/>
     *               &lt;enumeration value="odbijen"/>
     *               &lt;enumeration value="povucen"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="podnosilac" type="{http://www.parlament.gov.rs/schema/korisnici}TKorisnik"/>
     *         &lt;element name="mjesto" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "datum",
        "status",
        "podnosilac",
        "mjesto"
    })
    public static class Head {

        @XmlElement(namespace = "http://www.parlament.gov.rs/schema/propis", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar datum;

        @XmlElement(namespace = "http://www.parlament.gov.rs/schema/propis", required = true)
        protected User podnosilac;


        @XmlElement(namespace = "http://www.parlament.gov.rs/schema/propis", required = true)
        @XmlJavaTypeAdapter(StringPropertyAdapter.class)
        protected StringProperty status;

        @XmlElement(namespace = "http://www.parlament.gov.rs/schema/propis", required = true)
        @XmlJavaTypeAdapter(StringPropertyAdapter.class)
        protected StringProperty mjesto;

        /**
         * Gets the value of the datum property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDatum() {
            return datum;
        }

        /**
         * Sets the value of the datum property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDatum(XMLGregorianCalendar value) {
            this.datum = value;
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

        /**
         * Gets the value of the podnosilac property.
         * 
         * @return
         *     possible object is
         *     {@link User }
         *     
         */
        public User getPodnosilac() {
            return podnosilac;
        }

        /**
         * Sets the value of the podnosilac property.
         * 
         * @param value
         *     allowed object is
         *     {@link User }
         *     
         */
        public void setPodnosilac(User value) {
            this.podnosilac = value;
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

        public StringProperty statusProperty() {
            return status;
        }

        public StringProperty mjestoProperty() {
            return mjesto;
        }
    }

}
