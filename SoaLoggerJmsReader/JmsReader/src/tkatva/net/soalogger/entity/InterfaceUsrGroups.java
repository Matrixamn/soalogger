package tkatva.net.soalogger.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "InterfaceUsrGroups.findAll", query = "select o from InterfaceUsrGroups o")
})
@Table(name = "INTERFACE_USR_GROUPS")
@IdClass(InterfaceUsrGroupsPK.class)
public class InterfaceUsrGroups implements Serializable {
    @Id
    @Column(name="LOG_INTERFACE_ID", nullable = false, insertable = false,
            updatable = false)
    private Long logInterfaceId;
    @Id
    @Column(name="USER_GROUP_ID", nullable = false, insertable = false,
            updatable = false)
    private Long userGroupId;
    @ManyToOne
    @JoinColumn(name = "LOG_INTERFACE_ID")
    private LogInterface logInterface;
    @ManyToOne
    @JoinColumn(name = "USER_GROUP_ID")
    private UserGroup userGroup;

    public InterfaceUsrGroups() {
    }

    public InterfaceUsrGroups(LogInterface logInterface, UserGroup userGroup) {
        this.logInterface = logInterface;
        this.userGroup = userGroup;
    }

    public Long getLogInterfaceId() {
        return logInterfaceId;
    }

    public void setLogInterfaceId(Long logInterfaceId) {
        this.logInterfaceId = logInterfaceId;
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public LogInterface getLogInterface() {
        return logInterface;
    }

    public void setLogInterface(LogInterface logInterface) {
        this.logInterface = logInterface;
        if (logInterface != null) {
            this.logInterfaceId = logInterface.getLogInterfaceId();
        }
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
        if (userGroup != null) {
            this.userGroupId = userGroup.getUserGroupId();
        }
    }
}
