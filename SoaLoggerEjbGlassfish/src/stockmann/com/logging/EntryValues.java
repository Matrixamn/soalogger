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
@NamedQueries( { @NamedQuery(name = "EntryValues.findAll",
                             query = "select o from EntryValues o") })
@Table(name = "\"soalog\".\"ENTRY_VALUES\"")
public class EntryValues implements Serializable {
    @Column(name = "\"ENTRY_VALUE\"")
    private String entryValue;
    @Id
    @Column(name = "\"VALUE_ID\"", nullable = false)
    private Integer valueId;
    @Column(name = "\"VALUE_NAME\"")
    private String valueName;
    @ManyToOne
    @JoinColumn(name = "\"LOG_ENTRY_ID\"")
    private LogEntry logEntry;

    private static final long serialVersionUID = 7526472295622776147L;

    public EntryValues() {
    }

    public EntryValues(LogEntry logEntry, String value, Integer valueId,
                       String valueName) {
        this.logEntry = logEntry;
        this.setEntryValue(value);
        this.valueId = valueId;
        this.valueName = valueName;
    }

    public Integer getValueId() {
        return valueId;
    }

    public void setValueId(Integer valueId) {
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

    public String getEntryValue() {
        return entryValue;
    }

    public void setEntryValue(String entryValue) {
        this.entryValue = entryValue;
    }
}
