/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmann.soa.logging.web.soaloggerservice.transfer;

import java.util.Date;
import java.util.List;
/**
 *
 * @author katvtuo
 */
public class LogEntryTo {

    private String instanceVersion;

    private String integrationInstanceId;

    private Date logDate;

    private Integer logEntryId;

    private String logLevel;

    private String logMsg;

    private String logPayload;

    private List<EntryValueTo> entryValuesList;

    /**
     * @return the instanceVersion
     */
    public String getInstanceVersion() {
        return instanceVersion;
    }

    /**
     * @param instanceVersion the instanceVersion to set
     */
    public void setInstanceVersion(String instanceVersion) {
        this.instanceVersion = instanceVersion;
    }

    /**
     * @return the integrationInstanceId
     */
    public String getIntegrationInstanceId() {
        return integrationInstanceId;
    }

    /**
     * @param integrationInstanceId the integrationInstanceId to set
     */
    public void setIntegrationInstanceId(String integrationInstanceId) {
        this.integrationInstanceId = integrationInstanceId;
    }

    /**
     * @return the logDate
     */
    public Date getLogDate() {
        return logDate;
    }

    /**
     * @param logDate the logDate to set
     */
    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    /**
     * @return the logEntryId
     */
    public Integer getLogEntryId() {
        return logEntryId;
    }

    /**
     * @param logEntryId the logEntryId to set
     */
    public void setLogEntryId(Integer logEntryId) {
        this.logEntryId = logEntryId;
    }

    /**
     * @return the logLevel
     */
    public String getLogLevel() {
        return logLevel;
    }

    /**
     * @param logLevel the logLevel to set
     */
    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * @return the logMsg
     */
    public String getLogMsg() {
        return logMsg;
    }

    /**
     * @param logMsg the logMsg to set
     */
    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    /**
     * @return the logPayload
     */
    public String getLogPayload() {
        return logPayload;
    }

    /**
     * @param logPayload the logPayload to set
     */
    public void setLogPayload(String logPayload) {
        this.logPayload = logPayload;
    }

    /**
     * @return the entryValuesList
     */
    public List<EntryValueTo> getEntryValuesList() {
        return entryValuesList;
    }

    /**
     * @param entryValuesList the entryValuesList to set
     */
    public void setEntryValuesList(List<EntryValueTo> entryValuesList) {
        this.entryValuesList = entryValuesList;
    }
}
