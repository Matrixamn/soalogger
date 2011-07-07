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

@Entity
@NamedQueries({
  @NamedQuery(name = "UserGroup.findAll", query = "select o from UserGroup o")
})
@Table(name = "USER_GROUP")
public class UserGroup implements Serializable {
    @Column(name="USER_GROUP_DETAILS", length = 200)
    private String userGroupDetails;
    @Id
    @Column(name="USER_GROUP_ID", nullable = false)
    private Long userGroupId;
    @Column(name="USER_GROUP_NAME", nullable = false, length = 50)
    private String userGroupName;
    @OneToMany(mappedBy = "userGroup")
    private List<UsrGrpUsr> usrGrpUsrList;
    @OneToMany(mappedBy = "userGroup")
    private List<InterfaceUsrGroups> interfaceUsrGroupsList;

    public UserGroup() {
    }

    public UserGroup(String userGroupDetails, Long userGroupId,
                     String userGroupName) {
        this.userGroupDetails = userGroupDetails;
        this.userGroupId = userGroupId;
        this.userGroupName = userGroupName;
    }

    public String getUserGroupDetails() {
        return userGroupDetails;
    }

    public void setUserGroupDetails(String userGroupDetails) {
        this.userGroupDetails = userGroupDetails;
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public List<UsrGrpUsr> getUsrGrpUsrList() {
        return usrGrpUsrList;
    }

    public void setUsrGrpUsrList(List<UsrGrpUsr> usrGrpUsrList) {
        this.usrGrpUsrList = usrGrpUsrList;
    }

    public UsrGrpUsr addUsrGrpUsr(UsrGrpUsr usrGrpUsr) {
        getUsrGrpUsrList().add(usrGrpUsr);
        usrGrpUsr.setUserGroup(this);
        return usrGrpUsr;
    }

    public UsrGrpUsr removeUsrGrpUsr(UsrGrpUsr usrGrpUsr) {
        getUsrGrpUsrList().remove(usrGrpUsr);
        usrGrpUsr.setUserGroup(null);
        return usrGrpUsr;
    }

    public List<InterfaceUsrGroups> getInterfaceUsrGroupsList() {
        return interfaceUsrGroupsList;
    }

    public void setInterfaceUsrGroupsList(List<InterfaceUsrGroups> interfaceUsrGroupsList) {
        this.interfaceUsrGroupsList = interfaceUsrGroupsList;
    }

    public InterfaceUsrGroups addInterfaceUsrGroups(InterfaceUsrGroups interfaceUsrGroups) {
        getInterfaceUsrGroupsList().add(interfaceUsrGroups);
        interfaceUsrGroups.setUserGroup(this);
        return interfaceUsrGroups;
    }

    public InterfaceUsrGroups removeInterfaceUsrGroups(InterfaceUsrGroups interfaceUsrGroups) {
        getInterfaceUsrGroupsList().remove(interfaceUsrGroups);
        interfaceUsrGroups.setUserGroup(null);
        return interfaceUsrGroups;
    }
}
