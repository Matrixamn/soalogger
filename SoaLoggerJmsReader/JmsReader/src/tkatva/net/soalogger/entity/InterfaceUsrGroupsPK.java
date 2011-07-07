package tkatva.net.soalogger.entity;

import java.io.Serializable;

public class InterfaceUsrGroupsPK implements Serializable {
    private Long logInterfaceId;
    private Long userGroupId;

    public InterfaceUsrGroupsPK() {
    }

    public InterfaceUsrGroupsPK(Long logInterfaceId, Long userGroupId) {
        this.logInterfaceId = logInterfaceId;
        this.userGroupId = userGroupId;
    }

    public boolean equals(Object other) {
        if (other instanceof InterfaceUsrGroupsPK) {
            final InterfaceUsrGroupsPK otherInterfaceUsrGroupsPK = (InterfaceUsrGroupsPK) other;
            final boolean areEqual =
                (otherInterfaceUsrGroupsPK.logInterfaceId.equals(logInterfaceId) && otherInterfaceUsrGroupsPK.userGroupId.equals(userGroupId));
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    Long getLogInterfaceId() {
        return logInterfaceId;
    }

    void setLogInterfaceId(Long logInterfaceId) {
        this.logInterfaceId = logInterfaceId;
    }

    Long getUserGroupId() {
        return userGroupId;
    }

    void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }
}
