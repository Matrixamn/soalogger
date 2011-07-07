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
  @NamedQuery(name = "EntryValues.findAll", query = "select o from EntryValues o")
})
@Table(name = "ENTRY_VALUES")
public class EntryValues implements Serializable {
    @Column(name="ENTRY_VALUE", length = 2000)
    private String entryValue;
    @Id
    @Column(name="VALUE_ID", nullable = false)
    private Long valueId;
    @Column(name="VALUE_NAME", length = 200)
    private String valueName;
    @ManyToOne
    @JoinColumn(name = "LOG_ENTRY_ID")
    private LogEntry logEntry;

    public EntryValues() {
    }

    public EntryValues(String entryValue, LogEntry logEntry, Long valueId,
                       String valueName) {
        this.entryValue = entryValue;
        this.logEntry = logEntry;
        this.valueId = valueId;
        this.valueName = valueName;
    }

    public String getEntryValue() {
        return entryValue;
    }

    public void setEntryValue(String entryValue) {
        this.entryValue = entryValue;
    }


    public Long getValueId() {
        return valueId;
    }

    public void setValueId(Long valueId) {
        this.valueId = valueId;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public LogEntry getLogEntry() {
        return logEntry;
    }

    public void setLogEntry(LogEntry logEntry) {
        this.logEntry = logEntry;
    }
}
