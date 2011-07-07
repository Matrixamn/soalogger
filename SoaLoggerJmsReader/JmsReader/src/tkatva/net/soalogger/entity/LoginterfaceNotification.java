package tkatva.net.soalogger.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "LoginterfaceNotification.findAll", query = "select o from LoginterfaceNotification o")
})
@Table(name = "LOGINTERFACE_NOTIFICATION")
public class LoginterfaceNotification implements Serializable {
    @Column(name="INTERFACE_ENV", length = 20)
    private String interfaceEnv;
    @Id
    @Column(name="LOG_INTERFACE_NOTIFICATION_ID", nullable = false)
    private Long logInterfaceNotificationId;
    @Column(name="NOTIFICATION_DESC", length = 1000)
    private String notificationDesc;
    @Column(name="RECIPIENT_EMAIL", length = 200)
    private String recipientEmail;
    @ManyToOne
    @JoinColumn(name = "LOG_INTERFACE_ID")
    private LogInterface logInterface;

    public LoginterfaceNotification() {
    }

    public LoginterfaceNotification(String interfaceEnv,
                                    LogInterface logInterface,
                                    Long logInterfaceNotificationId,
                                    String notificationDesc,
                                    String recipientEmail) {
        this.interfaceEnv = interfaceEnv;
        this.logInterface = logInterface;
        this.logInterfaceNotificationId = logInterfaceNotificationId;
        this.notificationDesc = notificationDesc;
        this.recipientEmail = recipientEmail;
    }

    public String getInterfaceEnv() {
        return interfaceEnv;
    }

    public void setInterfaceEnv(String interfaceEnv) {
        this.interfaceEnv = interfaceEnv;
    }


    public Long getLogInterfaceNotificationId() {
        return logInterfaceNotificationId;
    }

    public void setLogInterfaceNotificationId(Long logInterfaceNotificationId) {
        this.logInterfaceNotificationId = logInterfaceNotificationId;
    }

    public String getNotificationDesc() {
        return notificationDesc;
    }

    public void setNotificationDesc(String notificationDesc) {
        this.notificationDesc = notificationDesc;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public LogInterface getLogInterface() {
        return logInterface;
    }

    public void setLogInterface(LogInterface logInterface) {
        this.logInterface = logInterface;
    }
}
