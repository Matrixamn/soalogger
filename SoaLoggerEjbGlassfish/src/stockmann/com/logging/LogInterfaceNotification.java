package stockmann.com.logging;

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
@Table(name = "\"soalog\".\"LOGINTERFACE_NOTIFICATION\"")
public class LogInterfaceNotification implements Serializable {
    public LogInterfaceNotification() {
        
    }
    
    @Id
    @Column(name = "\"LOG_INTERFACE_NOTIFICATION_ID\"", nullable = false)
    private Integer logInterfaceNotificationId;
    
    @Column(name = "\"RECIPIENT_EMAIL\"")
    private String recipientEmail;
    
    @Column(name = "\"LOG_INTERFACE_ID\"", nullable = false)
    private Integer logInterfaceId;
    
    @Column(name = "\"NOTIFICATION_DESC\"")
    private String notificationDesc;
    
  @Column(name = "\"INTERFACE_ENV\"")
    private String interfaceEnv;

    public Integer getLogInterfaceNotificationId() {
        return logInterfaceNotificationId;
    }

    public void setLogInterfaceNotificationId(Integer logInterfaceNotificationId) {
        this.logInterfaceNotificationId = logInterfaceNotificationId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public Integer getLogInterfaceId() {
        return logInterfaceId;
    }

    public void setLogInterfaceId(Integer logInterfaceId) {
        this.logInterfaceId = logInterfaceId;
    }

    public String getNotificationDesc() {
        return notificationDesc;
    }

    public void setNotificationDesc(String notificationDesc) {
        this.notificationDesc = notificationDesc;
    }

    public String getInterfaceEnv() {
        return interfaceEnv;
    }

    public void setInterfaceEnv(String interfaceEnv) {
        this.interfaceEnv = interfaceEnv;
    }
}
