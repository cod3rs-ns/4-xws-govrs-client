
package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TPravno_lice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPravno_lice">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.parlament.gov.rs/schema/korisnici}TLice">
 *       &lt;sequence>
 *         &lt;element name="naziv" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPravno_lice", namespace = "http://www.parlament.gov.rs/schema/korisnici", propOrder = {
    "naziv"
})
public class LegalEntity
    extends Person
{

    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/korisnici", required = true)
    protected Object naziv;

    /**
     * Gets the value of the naziv property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getNaziv() {
        return naziv;
    }

    /**
     * Sets the value of the naziv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setNaziv(Object value) {
        this.naziv = value;
    }

}
