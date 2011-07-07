package tkatva.net.soalogger.entity;

import java.io.Serializable;

import java.sql.Timestamp;

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
  @NamedQuery(name = "LogSched.findAll", query = "select o from LogSched o")
})
@Table(name = "LOG_SCHED")
public class LogSched implements Serializable {
    @Column(name="DAYS_OLDER_TO_REMOVE")
    private Long daysOlderToRemove;
    @Column(name="LOG_DATE")
    private Timestamp logDate;
    @Id
    @Column(name="LOG_SCHED_ID", nullable = false)
    private Long logSchedId;
    @ManyToOne
    @JoinColumn(name = "LOG_INTERFACE_ID")
    private LogInterface logInterface;

    public LogSched() {
    }

    public LogSched(Long daysOlderToRemove, Timestamp logDate,
                    LogInterface logInterface, Long logSchedId) {
        this.daysOlderToRemove = daysOlderToRemove;
        this.logDate = logDate;
        this.logInterface = logInterface;
        this.logSchedId = logSchedId;
    }

    public Long getDaysOlderToRemove() {
        return daysOlderToRemove;
    }

    public void setDaysOlderToRemove(Long daysOlderToRemove) {
        this.daysOlderToRemove = daysOlderToRemove;
    }

    public Timestamp getLogDate() {
        return logDate;
    }

    public void setLogDate(Timestamp logDate) {
        this.logDate = logDate;
    }


    public Long getLogSchedId() {
        return logSchedId;
    }

    public void setLogSchedId(Long logSchedId) {
        this.logSchedId = logSchedId;
    }

    public LogInterface getLogInterface() {
        return logInterface;
    }

    public void setLogInterface(LogInterface logInterface) {
        this.logInterface = logInterface;
    }
}
