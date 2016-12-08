
package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import javafx.beans.property.StringProperty;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.StringPropertyAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for alineja element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="alineja">
 *   &lt;complexType>
 *     &lt;simpleContent>
 *       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *         &lt;attGroup ref="{http://www.parlament.gov.rs/schema/elementi}standard_attrs"/>
 *       &lt;/extension>
 *     &lt;/simpleContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "value"
})
@XmlRootElement(name = "alineja", namespace = "http://www.parlament.gov.rs/schema/elementi")
public class Item {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String id;

    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(StringPropertyAdapter.class)
    protected StringProperty name;

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
        this.name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }
}
