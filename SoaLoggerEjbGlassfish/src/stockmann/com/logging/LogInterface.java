package stockmann.com.logging;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "LogInterface.findAll",
                             query = "select o from LogInterface o") })
@Table(name = "\"soalog\".\"LOG_INTERFACE\"")
public class LogInterface implements Serializable {
    @Column(name = "\"ENVIRONMENT_FLAG\"")
    private String environmentFlag;
    @Column(name = "\"INTEGRATION_TYPE\"")
    private String integrationType;
    @Id
    @Column(name = "\"LOG_INTERFACE_ID\"", nullable = false)
    private Integer logInterfaceId;
    @Column(name = "\"LOG_INTERFACE_NAME\"")
    private String logInterfaceName;
    @Column(name = "\"LOG_INTERFACE_VERSION\"")
    private String logInterfaceVersion;
    @Column(name = "\"LOG_MESSAGE_FLAG\"")
    private String logMessageFlag;
    @Column(name = "\"MSG_MAX_LENGHT\"")
    private Integer msgMaxLenght;
    @Column(name = "\"PREFERRED_LOGGING_LEVEL\"")
    private String preferredLoggingLevel;
    @Column(name="\"MSG_ENCODING\"")
    private String msg_encoding;
    @Column(name="\"APPLY_BASE64\"")
    private String applyBase64;
    @OneToMany(mappedBy = "logInterface")
    private List<LogFields> logFieldsList;
    @OneToMany(mappedBy = "logInterface")
    private List<LogEntry> logEntryList;
    @Transient
    private String addField;

    public LogInterface() {
    }

    public LogInterface(String environmentFlag, String integrationType,
                        Integer logInterfaceId, String logInterfaceName,
                        String logInterfaceVersion, String logMessageFlag,
                        Integer msgMaxLenght, String preferredLoggingLevel) {
        this.environmentFlag = environmentFlag;
        this.integrationType = integrationType;
        this.logInterfaceId = logInterfaceId;
        this.logInterfaceName = logInterfaceName;
        this.logInterfaceVersion = logInterfaceVersion;
        this.logMessageFlag = logMessageFlag;
        this.msgMaxLenght = msgMaxLenght;
        this.preferredLoggingLevel = preferredLoggingLevel;
    }

    public String getEnvironmentFlag() {
        return environmentFlag;
    }

    public void setEnvironmentFlag(String environmentFlag) {
        this.environmentFlag = environmentFlag;
    }

    public String getIntegrationType() {
        return integrationType;
    }

    public void setIntegrationType(String integrationType) {
        this.integrationType = integrationType;
    }

    public Integer getLogInterfaceId() {
        return logInterfaceId;
    }

    public void setLogInterfaceId(Integer logInterfaceId) {
        this.logInterfaceId = logInterfaceId;
    }

    public String getLogInterfaceName() {
        return logInterfaceName;
    }

    public void setLogInterfaceName(String logInterfaceName) {
        this.logInterfaceName = logInterfaceName;
    }

    public String getLogInterfaceVersion() {
        return logInterfaceVersion;
    }

    public void setLogInterfaceVersion(String logInterfaceVersion) {
        this.logInterfaceVersion = logInterfaceVersion;
    }

    public String getLogMessageFlag() {
        return logMessageFlag;
    }

    public void setLogMessageFlag(String logMessageFlag) {
        this.logMessageFlag = logMessageFlag;
    }

    public Integer getMsgMaxLenght() {
        return msgMaxLenght;
    }

    public void setMsgMaxLenght(Integer msgMaxLenght) {
        this.msgMaxLenght = msgMaxLenght;
    }

    public String getPreferredLoggingLevel() {
        return preferredLoggingLevel;
    }

    public void setPreferredLoggingLevel(String preferredLoggingLevel) {
        this.preferredLoggingLevel = preferredLoggingLevel;
    }

    public List<LogFields> getLogFieldsList() {
        return logFieldsList;
    }

    public void setLogFieldsList(List<LogFields> logFieldsList) {
        this.logFieldsList = logFieldsList;
    }

    public LogFields addLogFields(LogFields logFields) {
        getLogFieldsList().add(logFields);
        logFields.setLogInterface(this);
        return logFields;
    }

    public LogFields removeLogFields(LogFields logFields) {
        getLogFieldsList().remove(logFields);
        logFields.setLogInterface(null);
        return logFields;
    }

    public List<LogEntry> getLogEntryList() {
        return logEntryList;
    }

    public void setLogEntryList(List<LogEntry> logEntryList) {
        this.logEntryList = logEntryList;
    }

    public LogEntry addLogEntry(LogEntry logEntry) {
        getLogEntryList().add(logEntry);
        logEntry.setLogInterface(this);
        return logEntry;
    }

    public LogEntry removeLogEntry(LogEntry logEntry) {
        getLogEntryList().remove(logEntry);
        logEntry.setLogInterface(null);
        return logEntry;
    }

  public String getAddField() {
    return addField;
  }

  public void setAddField(String addField) {
    this.addField = addField;
  }

    public String getMsg_encoding() {
        return msg_encoding;
    }

    public void setMsg_encoding(String msg_encoding) {
        this.msg_encoding = msg_encoding;
    }

    public String getApplyBase64() {
        return applyBase64;
    }

    public void setApplyBase64(String applyBase64) {
        this.applyBase64 = applyBase64;
    }
}
