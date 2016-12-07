
package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for pododjeljak element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="pododjeljak">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}odjeljak"/>
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
    "odjeljak"
})
@XmlRootElement(name = "pododjeljak", namespace = "http://www.parlament.gov.rs/schema/elementi")
public class Subsection {

    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/elementi", required = true)
    protected Section odjeljak;

    /**
     * Gets the value of the odjeljak property.
     * 
     * @return
     *     possible object is
     *     {@link Section }
     *     
     */
    public Section getOdjeljak() {
        return odjeljak;
    }

    /**
     * Sets the value of the odjeljak property.
     * 
     * @param value
     *     allowed object is
     *     {@link Section }
     *     
     */
    public void setOdjeljak(Section value) {
        this.odjeljak = value;
    }

}
