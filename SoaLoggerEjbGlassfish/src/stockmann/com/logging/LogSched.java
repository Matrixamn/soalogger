package stockmann.com.logging;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "\"soalog\".\"LOG_SCHED\"")
public class LogSched implements Serializable {
    public LogSched() {
    }
    
  @Id
  @Column(name = "\"LOG_SCHED_ID\"", nullable = false)
  private Integer logSchedId;
  
  @Column(name = "\"LOG_INTERFACE_ID\"")
  private Integer logInterfaceId;
  
  @Column(name = "\"DAYS_OLDER_TO_REMOVE\"")
  private Integer daysOlderToRemove;
  
  @Column(name = "\"LOG_DATE\"", nullable = false)
  private Timestamp logDate;

  @Transient
  private LogInterface logInterface;

    public Integer getLogSchedId() {
        return logSchedId;
    }

    public void setLogSchedId(Integer logSchedId) {
        this.logSchedId = logSchedId;
    }

    public Integer getLogInterfaceId() {
        return logInterfaceId;
    }

    public void setLogInterfaceId(Integer logInterfaceId) {
        this.logInterfaceId = logInterfaceId;
    }

    public Integer getDaysOlderToRemove() {
        return daysOlderToRemove;
    }

    public void setDaysOlderToRemove(Integer daysOlderToRemove) {
        this.daysOlderToRemove = daysOlderToRemove;
    }

    public Timestamp getLogDate() {
        return logDate;
    }

    public void setLogDate(Timestamp logDate) {
        this.logDate = logDate;
    }

    public LogInterface getLogInterface() {
        return logInterface;
    }

    public void setLogInterface(LogInterface logInterface) {
        this.logInterface = logInterface;
    }
}
