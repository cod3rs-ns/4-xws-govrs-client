package rs.acs.uns.sw.govrs.client.fx.serverdomain;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TLice complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TLice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.parlament.gov.rs/schema/korisnici}Adresa"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TLice", namespace = "http://www.parlament.gov.rs/schema/korisnici", propOrder = {
        "adresa"
})
@XmlSeeAlso({
        LegalEntity.class,
        Individual.class
})
public abstract class Person {

    @XmlElement(name = "Adresa", namespace = "http://www.parlament.gov.rs/schema/korisnici", required = true)
    protected Address adresa;
    @XmlAttribute(name = "id")
    protected String id;

    /**
     * Gets the value of the adresa property.
     *
     * @return
     *     possible object is
     *     {@link Address }
     *
     */
    public Address getAdresa() {
        return adresa;
    }

    /**
     * Sets the value of the adresa property.
     *
     * @param value
     *     allowed object is
     *     {@link Address }
     *
     */
    public void setAdresa(Address value) {
        this.adresa = value;
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

}
