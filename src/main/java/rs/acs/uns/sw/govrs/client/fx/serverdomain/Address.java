
package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;


/**
 * <p>Java class for Adresa element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="Adresa">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="ulica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="postanski_broj">
 *             &lt;simpleType>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                 &lt;minInclusive value="11000"/>
 *                 &lt;maxInclusive value="40000"/>
 *               &lt;/restriction>
 *             &lt;/simpleType>
 *           &lt;/element>
 *           &lt;element name="mjesto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="broj" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;/sequence>
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
    "ulica",
    "postanskiBroj",
    "mjesto",
    "broj"
})
@XmlRootElement(name = "Adresa", namespace = "http://www.parlament.gov.rs/schema/korisnici")
public class Address {

    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/korisnici", required = true)
    protected String ulica;
    @XmlElement(name = "postanski_broj", namespace = "http://www.parlament.gov.rs/schema/korisnici", defaultValue = "21000")
    protected int postanskiBroj;
    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/korisnici", required = true, defaultValue = "Novi Sad")
    protected String mjesto;
    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/korisnici", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger broj;

    /**
     * Gets the value of the ulica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUlica() {
        return ulica;
    }

    /**
     * Sets the value of the ulica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUlica(String value) {
        this.ulica = value;
    }

    /**
     * Gets the value of the postanskiBroj property.
     * 
     */
    public int getPostanskiBroj() {
        return postanskiBroj;
    }

    /**
     * Sets the value of the postanskiBroj property.
     * 
     */
    public void setPostanskiBroj(int value) {
        this.postanskiBroj = value;
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
        return mjesto;
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
        this.mjesto = value;
    }

    /**
     * Gets the value of the broj property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBroj() {
        return broj;
    }

    /**
     * Sets the value of the broj property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBroj(BigInteger value) {
        this.broj = value;
    }

}
