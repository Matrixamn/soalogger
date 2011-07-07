
package stockmann.com.soalogger.ws.client.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for logEntryTo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="logEntryTo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entryValuesList" type="{http://soaloggerservice.web.logging.soa.stockmann/}entryValueTo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="instanceVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="integrationInstanceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="logEntryId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="logLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logPayload" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "logEntryTo", propOrder = {
    "entryValuesList",
    "instanceVersion",
    "integrationInstanceId",
    "logDate",
    "logEntryId",
    "logLevel",
    "logMsg",
    "logPayload"
})
public class LogEntryTo {

    @XmlElement(nillable = true)
    private List<EntryValueTo> entryValuesList;
    protected String instanceVersion;
    protected String integrationInstanceId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar logDate;
    protected Integer logEntryId;
    protected String logLevel;
    protected String logMsg;
    protected String logPayload;

    /**
     * Gets the value of the entryValuesList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the entryValuesList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntryValuesList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EntryValueTo }
     * 
     * 
     */
    public List<EntryValueTo> getEntryValuesList() {
        if (entryValuesList == null) {
            entryValuesList = new ArrayList<EntryValueTo>();
        }
        return this.entryValuesList;
    }

    /**
     * Gets the value of the instanceVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstanceVersion() {
        return instanceVersion;
    }

    /**
     * Sets the value of the instanceVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstanceVersion(String value) {
        this.instanceVersion = value;
    }

    /**
     * Gets the value of the integrationInstanceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntegrationInstanceId() {
        return integrationInstanceId;
    }

    /**
     * Sets the value of the integrationInstanceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntegrationInstanceId(String value) {
        this.integrationInstanceId = value;
    }

    /**
     * Gets the value of the logDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLogDate() {
        return logDate;
    }

    /**
     * Sets the value of the logDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLogDate(XMLGregorianCalendar value) {
        this.logDate = value;
    }

    /**
     * Gets the value of the logEntryId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLogEntryId() {
        return logEntryId;
    }

    /**
     * Sets the value of the logEntryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLogEntryId(Integer value) {
        this.logEntryId = value;
    }

    /**
     * Gets the value of the logLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogLevel() {
        return logLevel;
    }

    /**
     * Sets the value of the logLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogLevel(String value) {
        this.logLevel = value;
    }

    /**
     * Gets the value of the logMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogMsg() {
        return logMsg;
    }

    /**
     * Sets the value of the logMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogMsg(String value) {
        this.logMsg = value;
    }

    /**
     * Gets the value of the logPayload property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogPayload() {
        return logPayload;
    }

    /**
     * Sets the value of the logPayload property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogPayload(String value) {
        this.logPayload = value;
    }

    public void setEntryValuesList(List<EntryValueTo> entryValuesList) {
        this.entryValuesList = entryValuesList;
    }
}
