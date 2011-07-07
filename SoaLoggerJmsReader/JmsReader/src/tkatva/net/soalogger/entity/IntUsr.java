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
  @NamedQuery(name = "IntUsr.findAll", query = "select o from IntUsr o")
})
@Table(name = "INT_USR")
public class IntUsr implements Serializable {
    @Id
    @Column(name="INT_USR_ID", nullable = false)
    private Long intUsrId;
    @Column(name="USR_DESC", length = 20)
    private String usrDesc;
    @Column(name="USR_NAME", length = 50)
    private String usrName;
    @Column(name="USR_PWORD", length = 100)
    private String usrPword;
    @OneToMany(mappedBy = "intUsr")
    private List<UsrGrpUsr> usrGrpUsrList;

    public IntUsr() {
    }

    public IntUsr(Long intUsrId, String usrDesc, String usrName,
                  String usrPword) {
        this.intUsrId = intUsrId;
        this.usrDesc = usrDesc;
        this.usrName = usrName;
        this.usrPword = usrPword;
    }

    public Long getIntUsrId() {
        return intUsrId;
    }

    public void setIntUsrId(Long intUsrId) {
        this.intUsrId = intUsrId;
    }

    public String getUsrDesc() {
        return usrDesc;
    }

    public void setUsrDesc(String usrDesc) {
        this.usrDesc = usrDesc;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getUsrPword() {
        return usrPword;
    }

    public void setUsrPword(String usrPword) {
        this.usrPword = usrPword;
    }

    public List<UsrGrpUsr> getUsrGrpUsrList() {
        return usrGrpUsrList;
    }

    public void setUsrGrpUsrList(List<UsrGrpUsr> usrGrpUsrList) {
        this.usrGrpUsrList = usrGrpUsrList;
    }

    public UsrGrpUsr addUsrGrpUsr(UsrGrpUsr usrGrpUsr) {
        getUsrGrpUsrList().add(usrGrpUsr);
        usrGrpUsr.setIntUsr(this);
        return usrGrpUsr;
    }

    public UsrGrpUsr removeUsrGrpUsr(UsrGrpUsr usrGrpUsr) {
        getUsrGrpUsrList().remove(usrGrpUsr);
        usrGrpUsr.setIntUsr(null);
        return usrGrpUsr;
    }
}
