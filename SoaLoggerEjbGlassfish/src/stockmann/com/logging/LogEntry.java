package stockmann.com.logging;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.List;
import javax.persistence.Transient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "LogEntry.findAll",
                             query = "select o from LogEntry o") })
@Table(name = "\"soalog\".\"LOG_ENTRY\"")
public class LogEntry implements Serializable {
    @Column(name = "\"INSTANCE_VERSION\"")
    private String instanceVersion;
    @Column(name = "\"INTEGRATION_INSTANCE_ID\"", nullable = false)
    private String integrationInstanceId;
    @Column(name = "\"LOG_DATE\"", nullable = false)
    private Timestamp logDate;
    @Id
    @Column(name = "\"LOG_ENTRY_ID\"", nullable = false)
    private Integer logEntryId;
    @Column(name = "\"LOG_LEVEL\"", nullable = false)
    private String logLevel;
    @Column(name = "\"LOG_MSG\"", nullable = false)
    private String logMsg;
    @Column(name = "\"LOG_PAYLOAD\"")
    private String logPayload;
    @OneToMany(mappedBy = "logEntry")
    private List<EntryValues> entryValuesList;
    @ManyToOne
    @JoinColumn(name = "\"LOG_INTERFACE_ID\"")
    private LogInterface logInterface;
    @Transient
    private String urlPath;
    @Transient
    private boolean urlSet;
    
    public LogEntry() {
    }

    public LogEntry(String instanceVersion, String integrationInstanceId,
                    Timestamp logDate, Integer logEntryId,
                    LogInterface logInterface, String logLevel, String logMsg,
                    String logPayload) {
        this.instanceVersion = instanceVersion;
        this.integrationInstanceId = integrationInstanceId;
        this.logDate = logDate;
        this.logEntryId = logEntryId;
        this.logInterface = logInterface;
        this.logLevel = logLevel;
        this.logMsg = logMsg;
        this.logPayload = logPayload;
    }

    public String getInstanceVersion() {
        return instanceVersion;
    }

    public void setInstanceVersion(String instanceVersion) {
        this.instanceVersion = instanceVersion;
    }

    public String getIntegrationInstanceId() {
        return integrationInstanceId;
    }

    public void setIntegrationInstanceId(String integrationInstanceId) {
        this.integrationInstanceId = integrationInstanceId;
    }

    public Timestamp getLogDate() {
        return logDate;
    }

    public void setLogDate(Timestamp logDate) {
        this.logDate = logDate;
    }

    public Integer getLogEntryId() {
        return logEntryId;
    }

    public void setLogEntryId(Integer logEntryId) {
        this.logEntryId = logEntryId;
    }


    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    public String getLogPayload() {
        return logPayload;
    }

    public void setLogPayload(String logPayload) {
        this.logPayload = logPayload;
    }

    public List<EntryValues> getEntryValuesList() {
        return entryValuesList;
    }

    public void setEntryValuesList(List<EntryValues> entryValuesList) {
        this.entryValuesList = entryValuesList;
    }

    public EntryValues addEntryValues(EntryValues entryValues) {
        getEntryValuesList().add(entryValues);
        entryValues.setLogEntry(this);
        return entryValues;
    }

    public EntryValues removeEntryValues(EntryValues entryValues) {
        getEntryValuesList().remove(entryValues);
        entryValues.setLogEntry(null);
        return entryValues;
    }

    public LogInterface getLogInterface() {
        return logInterface;
    }

    public void setLogInterface(LogInterface logInterface) {
        this.logInterface = logInterface;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public boolean isUrlSet() {
        return urlSet;
    }

    public void setUrlSet(boolean urlSet) {
        this.urlSet = urlSet;
    }

}
