
package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for TFizicko_lice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TFizicko_lice">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.parlament.gov.rs/schema/korisnici}TLice">
 *       &lt;sequence>
 *         &lt;element name="ime" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="prezime" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TFizicko_lice", namespace = "http://www.parlament.gov.rs/schema/korisnici", propOrder = {
    "ime",
    "prezime"
})
@XmlSeeAlso({
    User.class
})
public class Individual
    extends Person
{

    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/korisnici", required = true)
    protected Object ime;
    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/korisnici", required = true)
    protected Object prezime;

    /**
     * Gets the value of the ime property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getIme() {
        return ime;
    }

    /**
     * Sets the value of the ime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setIme(Object value) {
        this.ime = value;
    }

    /**
     * Gets the value of the prezime property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getPrezime() {
        return prezime;
    }

    /**
     * Sets the value of the prezime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setPrezime(Object value) {
        this.prezime = value;
    }

}
