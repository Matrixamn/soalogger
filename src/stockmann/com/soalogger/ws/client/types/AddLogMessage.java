
package stockmann.com.soalogger.ws.client.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addLogMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addLogMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="logMessage" type="{http://soaloggerservice.web.logging.soa.stockmann/}logMsgTo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addLogMessage", propOrder = {
    "logMessage"
})
public class AddLogMessage {

    protected LogMsgTo logMessage;

    /**
     * Gets the value of the logMessage property.
     * 
     * @return
     *     possible object is
     *     {@link LogMsgTo }
     *     
     */
    public LogMsgTo getLogMessage() {
        return logMessage;
    }

    /**
     * Sets the value of the logMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link LogMsgTo }
     *     
     */
    public void setLogMessage(LogMsgTo value) {
        this.logMessage = value;
    }

}
