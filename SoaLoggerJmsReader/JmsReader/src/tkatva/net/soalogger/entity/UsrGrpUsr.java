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
  @NamedQuery(name = "UsrGrpUsr.findAll", query = "select o from UsrGrpUsr o")
})
@Table(name = "USR_GRP_USR")
public class UsrGrpUsr implements Serializable {
    @Column(name="EXT_USRNAME", length = 100)
    private String extUsrname;
    @Id
    @Column(name="USR_GRP_USR_ID", nullable = false)
    private Long usrGrpUsrId;
    @ManyToOne
    @JoinColumn(name = "USER_GROUP_ID")
    private UserGroup userGroup;
    @ManyToOne
    @JoinColumn(name = "INT_USR_ID")
    private IntUsr intUsr;

    public UsrGrpUsr() {
    }

    public UsrGrpUsr(String extUsrname, IntUsr intUsr, UserGroup userGroup,
                     Long usrGrpUsrId) {
        this.extUsrname = extUsrname;
        this.intUsr = intUsr;
        this.userGroup = userGroup;
        this.usrGrpUsrId = usrGrpUsrId;
    }

    public String getExtUsrname() {
        return extUsrname;
    }

    public void setExtUsrname(String extUsrname) {
        this.extUsrname = extUsrname;
    }


    public Long getUsrGrpUsrId() {
        return usrGrpUsrId;
    }

    public void setUsrGrpUsrId(Long usrGrpUsrId) {
        this.usrGrpUsrId = usrGrpUsrId;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public IntUsr getIntUsr() {
        return intUsr;
    }

    public void setIntUsr(IntUsr intUsr) {
        this.intUsr = intUsr;
    }
}
