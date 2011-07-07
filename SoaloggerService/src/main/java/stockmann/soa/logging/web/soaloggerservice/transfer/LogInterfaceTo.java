/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmann.soa.logging.web.soaloggerservice.transfer;

import java.util.List;
/**
 *
 * @author katvtuo
 */
public class LogInterfaceTo {


    private String environmentFlag;

    private String integrationType;

    private Integer logInterfaceId;

    private String logInterfaceName;

    private String logInterfaceVersion;

    private String logMessageFlag;

    private Integer msgMaxLenght;

    private String preferredLoggingLevel;

    private String msgEncoding;

    private String applyBase64;

    private List<LogFieldTo> logFieldsList;

    private List<LogEntryTo> logEntryList;

    /**
     * @return the environmentFlag
     */
    public String getEnvironmentFlag() {
        return environmentFlag;
    }

    /**
     * @param environmentFlag the environmentFlag to set
     */
    public void setEnvironmentFlag(String environmentFlag) {
        this.environmentFlag = environmentFlag;
    }

    /**
     * @return the integrationType
     */
    public String getIntegrationType() {
        return integrationType;
    }

    /**
     * @param integrationType the integrationType to set
     */
    public void setIntegrationType(String integrationType) {
        this.integrationType = integrationType;
    }

    /**
     * @return the logInterfaceId
     */
    public Integer getLogInterfaceId() {
        return logInterfaceId;
    }

    /**
     * @param logInterfaceId the logInterfaceId to set
     */
    public void setLogInterfaceId(Integer logInterfaceId) {
        this.logInterfaceId = logInterfaceId;
    }

    /**
     * @return the logInterfaceName
     */
    public String getLogInterfaceName() {
        return logInterfaceName;
    }

    /**
     * @param logInterfaceName the logInterfaceName to set
     */
    public void setLogInterfaceName(String logInterfaceName) {
        this.logInterfaceName = logInterfaceName;
    }

    /**
     * @return the logInterfaceVersion
     */
    public String getLogInterfaceVersion() {
        return logInterfaceVersion;
    }

    /**
     * @param logInterfaceVersion the logInterfaceVersion to set
     */
    public void setLogInterfaceVersion(String logInterfaceVersion) {
        this.logInterfaceVersion = logInterfaceVersion;
    }

    /**
     * @return the logMessageFlag
     */
    public String getLogMessageFlag() {
        return logMessageFlag;
    }

    /**
     * @param logMessageFlag the logMessageFlag to set
     */
    public void setLogMessageFlag(String logMessageFlag) {
        this.logMessageFlag = logMessageFlag;
    }

    /**
     * @return the msgMaxLenght
     */
    public Integer getMsgMaxLenght() {
        return msgMaxLenght;
    }

    /**
     * @param msgMaxLenght the msgMaxLenght to set
     */
    public void setMsgMaxLenght(Integer msgMaxLenght) {
        this.msgMaxLenght = msgMaxLenght;
    }

    /**
     * @return the preferredLoggingLevel
     */
    public String getPreferredLoggingLevel() {
        return preferredLoggingLevel;
    }

    /**
     * @param preferredLoggingLevel the preferredLoggingLevel to set
     */
    public void setPreferredLoggingLevel(String preferredLoggingLevel) {
        this.preferredLoggingLevel = preferredLoggingLevel;
    }

    /**
     * @return the logFieldsList
     */
    public List<LogFieldTo> getLogFieldsList() {
        return logFieldsList;
    }

    /**
     * @param logFieldsList the logFieldsList to set
     */
    public void setLogFieldsList(List<LogFieldTo> logFieldsList) {
        this.logFieldsList = logFieldsList;
    }

    /**
     * @return the logEntryList
     */
    public List<LogEntryTo> getLogEntryList() {
        return logEntryList;
    }

    /**
     * @param logEntryList the logEntryList to set
     */
    public void setLogEntryList(List<LogEntryTo> logEntryList) {
        this.logEntryList = logEntryList;
    }

    /**
     * @return the msgEncoding
     */
    public String getMsgEncoding() {
        return msgEncoding;
    }

    /**
     * @param msgEncoding the msgEncoding to set
     */
    public void setMsgEncoding(String msgEncoding) {
        this.msgEncoding = msgEncoding;
    }

    /**
     * @return the applyBase64
     */
    public String getApplyBase64() {
        return applyBase64;
    }

    /**
     * @param applyBase64 the applyBase64 to set
     */
    public void setApplyBase64(String applyBase64) {
        this.applyBase64 = applyBase64;
    }

}
