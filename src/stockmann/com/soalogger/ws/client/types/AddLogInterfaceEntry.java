
package stockmann.com.soalogger.ws.client.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addLogInterfaceEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addLogInterfaceEntry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="interface" type="{http://soaloggerservice.web.logging.soa.stockmann/}logInterfaceTo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addLogInterfaceEntry", propOrder = {
    "_interface"
})
public class AddLogInterfaceEntry {

    @XmlElement(name = "interface")
    protected LogInterfaceTo _interface;

    /**
     * Gets the value of the interface property.
     * 
     * @return
     *     possible object is
     *     {@link LogInterfaceTo }
     *     
     */
    public LogInterfaceTo getInterface() {
        return _interface;
    }

    /**
     * Sets the value of the interface property.
     * 
     * @param value
     *     allowed object is
     *     {@link LogInterfaceTo }
     *     
     */
    public void setInterface(LogInterfaceTo value) {
        this._interface = value;
    }

}
