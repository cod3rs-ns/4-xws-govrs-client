
package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TKorisnik complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TKorisnik">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.parlament.gov.rs/schema/korisnici}TFizicko_lice">
 *       &lt;sequence>
 *         &lt;element name="uloga">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="gradjanin"/>
 *               &lt;enumeration value="narodni_poslanik"/>
 *               &lt;enumeration value="predsednik_skupstine"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TKorisnik", namespace = "http://www.parlament.gov.rs/schema/korisnici", propOrder = {
    "uloga"
})
public class User
    extends Individual
{

    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/korisnici", required = true)
    protected String uloga;

    /**
     * Gets the value of the uloga property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUloga() {
        return uloga;
    }

    /**
     * Sets the value of the uloga property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUloga(String value) {
        this.uloga = value;
    }

}
