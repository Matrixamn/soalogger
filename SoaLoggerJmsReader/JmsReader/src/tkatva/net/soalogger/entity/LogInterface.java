package tkatva.net.soalogger.entity;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@NamedQueries({
  @NamedQuery(name = "LogInterface.findAll", query = "select o from LogInterface o")
})
@Table(name = "LOG_INTERFACE")
public class LogInterface implements Serializable {
    @Column(name="APPLY_BASE64", length = 1)
    private String applyBase64;
    @Column(name="ENVIRONMENT_FLAG", nullable = false, length = 20)
    private String environmentFlag;
    @Column(name="INTEGRATION_TYPE", length = 20)
    private String integrationType;
    @Id
    @Column(name="LOG_INTERFACE_ID", nullable = false)
    private Long logInterfaceId;
    @Column(name="LOG_INTERFACE_NAME", nullable = false, length = 1000)
    private String logInterfaceName;
    @Column(name="LOG_INTERFACE_VERSION", nullable = false, length = 20)
    private String logInterfaceVersion;
    @Column(name="LOG_MESSAGE_FLAG", nullable = false, length = 1)
    private String logMessageFlag;
    @Column(name="MSG_ENCODING", length = 20)
    private String msgEncoding;
    @Column(name="MSG_MAX_LENGHT")
    private Long msgMaxLenght;
    @Column(name="PREFERRED_LOGGING_LEVEL", length = 20)
    private String preferredLoggingLevel;
    @OneToMany(mappedBy = "logInterface")
    private List<LogSched> logSchedList;
    @OneToMany(mappedBy = "logInterface")
    private List<InterfaceUsrGroups> interfaceUsrGroupsList;
    @OneToMany(mappedBy = "logInterface")
    private List<LoginterfaceNotification> loginterfaceNotificationList;
    @OneToMany(mappedBy = "logInterface")
    private List<LogFields> logFieldsList;
    @OneToMany(mappedBy = "logInterface")
    private List<LogEntry> logEntryList;
    @Transient
    private String addField;

    public LogInterface() {
    }

    public LogInterface(String applyBase64, String environmentFlag,
                        String integrationType, Long logInterfaceId,
                        String logInterfaceName, String logInterfaceVersion,
                        String logMessageFlag, String msgEncoding,
                        Long msgMaxLenght, String preferredLoggingLevel) {
        this.applyBase64 = applyBase64;
        this.environmentFlag = environmentFlag;
        this.integrationType = integrationType;
        this.logInterfaceId = logInterfaceId;
        this.logInterfaceName = logInterfaceName;
        this.logInterfaceVersion = logInterfaceVersion;
        this.logMessageFlag = logMessageFlag;
        this.msgEncoding = msgEncoding;
        this.msgMaxLenght = msgMaxLenght;
        this.preferredLoggingLevel = preferredLoggingLevel;
    }

    public String getApplyBase64() {
        return applyBase64;
    }

    public void setApplyBase64(String applyBase64) {
        this.applyBase64 = applyBase64;
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

    public Long getLogInterfaceId() {
        return logInterfaceId;
    }

    public void setLogInterfaceId(Long logInterfaceId) {
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

    public String getMsgEncoding() {
        return msgEncoding;
    }

    public void setMsgEncoding(String msgEncoding) {
        this.msgEncoding = msgEncoding;
    }

    public Long getMsgMaxLenght() {
        return msgMaxLenght;
    }

    public void setMsgMaxLenght(Long msgMaxLenght) {
        this.msgMaxLenght = msgMaxLenght;
    }

    public String getPreferredLoggingLevel() {
        return preferredLoggingLevel;
    }

    public void setPreferredLoggingLevel(String preferredLoggingLevel) {
        this.preferredLoggingLevel = preferredLoggingLevel;
    }

    public List<LogSched> getLogSchedList() {
        return logSchedList;
    }

    public void setLogSchedList(List<LogSched> logSchedList) {
        this.logSchedList = logSchedList;
    }

    public LogSched addLogSched(LogSched logSched) {
        getLogSchedList().add(logSched);
        logSched.setLogInterface(this);
        return logSched;
    }

    public LogSched removeLogSched(LogSched logSched) {
        getLogSchedList().remove(logSched);
        logSched.setLogInterface(null);
        return logSched;
    }

    public List<InterfaceUsrGroups> getInterfaceUsrGroupsList() {
        return interfaceUsrGroupsList;
    }

    public void setInterfaceUsrGroupsList(List<InterfaceUsrGroups> interfaceUsrGroupsList) {
        this.interfaceUsrGroupsList = interfaceUsrGroupsList;
    }

    public InterfaceUsrGroups addInterfaceUsrGroups(InterfaceUsrGroups interfaceUsrGroups) {
        getInterfaceUsrGroupsList().add(interfaceUsrGroups);
        interfaceUsrGroups.setLogInterface(this);
        return interfaceUsrGroups;
    }

    public InterfaceUsrGroups removeInterfaceUsrGroups(InterfaceUsrGroups interfaceUsrGroups) {
        getInterfaceUsrGroupsList().remove(interfaceUsrGroups);
        interfaceUsrGroups.setLogInterface(null);
        return interfaceUsrGroups;
    }

    public List<LoginterfaceNotification> getLoginterfaceNotificationList() {
        return loginterfaceNotificationList;
    }

    public void setLoginterfaceNotificationList(List<LoginterfaceNotification> loginterfaceNotificationList) {
        this.loginterfaceNotificationList = loginterfaceNotificationList;
    }

    public LoginterfaceNotification addLoginterfaceNotification(LoginterfaceNotification loginterfaceNotification) {
        getLoginterfaceNotificationList().add(loginterfaceNotification);
        loginterfaceNotification.setLogInterface(this);
        return loginterfaceNotification;
    }

    public LoginterfaceNotification removeLoginterfaceNotification(LoginterfaceNotification loginterfaceNotification) {
        getLoginterfaceNotificationList().remove(loginterfaceNotification);
        loginterfaceNotification.setLogInterface(null);
        return loginterfaceNotification;
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
}
