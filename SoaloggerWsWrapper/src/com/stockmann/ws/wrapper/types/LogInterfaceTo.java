
package com.stockmann.ws.wrapper.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for logInterfaceTo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="logInterfaceTo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applyBase64" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="environmentFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="integrationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logEntryList" type="{http://soaloggerservice.web.logging.soa.stockmann/}logEntryTo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="logFieldsList" type="{http://soaloggerservice.web.logging.soa.stockmann/}logFieldTo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="logInterfaceId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="logInterfaceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logInterfaceVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logMessageFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="msgEncoding" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="msgMaxLenght" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="preferredLoggingLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "logInterfaceTo", propOrder = {
    "applyBase64",
    "environmentFlag",
    "integrationType",
    "logEntryList",
    "logFieldsList",
    "logInterfaceId",
    "logInterfaceName",
    "logInterfaceVersion",
    "logMessageFlag",
    "msgEncoding",
    "msgMaxLenght",
    "preferredLoggingLevel"
})
public class LogInterfaceTo {

    protected String applyBase64;
    protected String environmentFlag;
    protected String integrationType;
    @XmlElement(nillable = true)
    private List<LogEntryTo> logEntryList;
    @XmlElement(nillable = true)
    private List<LogFieldTo> logFieldsList;
    protected Integer logInterfaceId;
    protected String logInterfaceName;
    protected String logInterfaceVersion;
    protected String logMessageFlag;
    protected String msgEncoding;
    protected Integer msgMaxLenght;
    protected String preferredLoggingLevel;

    /**
     * Gets the value of the applyBase64 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplyBase64() {
        return applyBase64;
    }

    /**
     * Sets the value of the applyBase64 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplyBase64(String value) {
        this.applyBase64 = value;
    }

    /**
     * Gets the value of the environmentFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnvironmentFlag() {
        return environmentFlag;
    }

    /**
     * Sets the value of the environmentFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnvironmentFlag(String value) {
        this.environmentFlag = value;
    }

    /**
     * Gets the value of the integrationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntegrationType() {
        return integrationType;
    }

    /**
     * Sets the value of the integrationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntegrationType(String value) {
        this.integrationType = value;
    }

    /**
     * Gets the value of the logEntryList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the logEntryList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLogEntryList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LogEntryTo }
     * 
     * 
     */
    public List<LogEntryTo> getLogEntryList() {
        if (logEntryList == null) {
            logEntryList = new ArrayList<LogEntryTo>();
        }
        return this.logEntryList;
    }

    /**
     * Gets the value of the logFieldsList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the logFieldsList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLogFieldsList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LogFieldTo }
     * 
     * 
     */
    public List<LogFieldTo> getLogFieldsList() {
        if (logFieldsList == null) {
            logFieldsList = new ArrayList<LogFieldTo>();
        }
        return this.logFieldsList;
    }

    /**
     * Gets the value of the logInterfaceId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLogInterfaceId() {
        return logInterfaceId;
    }

    /**
     * Sets the value of the logInterfaceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLogInterfaceId(Integer value) {
        this.logInterfaceId = value;
    }

    /**
     * Gets the value of the logInterfaceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogInterfaceName() {
        return logInterfaceName;
    }

    /**
     * Sets the value of the logInterfaceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogInterfaceName(String value) {
        this.logInterfaceName = value;
    }

    /**
     * Gets the value of the logInterfaceVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogInterfaceVersion() {
        return logInterfaceVersion;
    }

    /**
     * Sets the value of the logInterfaceVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogInterfaceVersion(String value) {
        this.logInterfaceVersion = value;
    }

    /**
     * Gets the value of the logMessageFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogMessageFlag() {
        return logMessageFlag;
    }

    /**
     * Sets the value of the logMessageFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogMessageFlag(String value) {
        this.logMessageFlag = value;
    }

    /**
     * Gets the value of the msgEncoding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgEncoding() {
        return msgEncoding;
    }

    /**
     * Sets the value of the msgEncoding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgEncoding(String value) {
        this.msgEncoding = value;
    }

    /**
     * Gets the value of the msgMaxLenght property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMsgMaxLenght() {
        return msgMaxLenght;
    }

    /**
     * Sets the value of the msgMaxLenght property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMsgMaxLenght(Integer value) {
        this.msgMaxLenght = value;
    }

    /**
     * Gets the value of the preferredLoggingLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferredLoggingLevel() {
        return preferredLoggingLevel;
    }

    /**
     * Sets the value of the preferredLoggingLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferredLoggingLevel(String value) {
        this.preferredLoggingLevel = value;
    }

    public void setLogEntryList(List<LogEntryTo> logEntryList) {
        this.logEntryList = logEntryList;
    }

    public void setLogFieldsList(List<LogFieldTo> logFieldsList) {
        this.logFieldsList = logFieldsList;
    }
}
