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
@NamedQueries( { @NamedQuery(name = "LogFields.findAll",
                             query = "select o from LogFields o") })
@Table(name = "\"soalog\".\"LOG_FIELDS\"")
public class LogFields implements Serializable {
    @Column(name = "\"ELEMENT_NAME\"")
    private String elementName;
    @Id
    @Column(name = "\"LOG_FIELD_ID\"", nullable = false)
    private Integer logFieldId;
    @Column(name = "\"LOG_FIELD_NAME\"", nullable = false)
    private String logFieldName;
    @Column(name = "\"XML_XPATH\"")
    private String xmlXpath;
    @ManyToOne
    @JoinColumn(name = "\"LOG_INTERFACE_ID\"")
    private LogInterface logInterface;

    public LogFields() {
    }

    public LogFields(String elementName, Integer logFieldId,
                     String logFieldName, LogInterface logInterface) {
        this.elementName = elementName;
        this.logFieldId = logFieldId;
        this.logFieldName = logFieldName;
        this.logInterface = logInterface;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public Integer getLogFieldId() {
        return logFieldId;
    }

    public void setLogFieldId(Integer logFieldId) {
        this.logFieldId = logFieldId;
    }

    public String getLogFieldName() {
        return logFieldName;
    }

    public void setLogFieldName(String logFieldName) {
        this.logFieldName = logFieldName;
    }


    public LogInterface getLogInterface() {
        return logInterface;
    }

    public void setLogInterface(LogInterface logInterface) {
        this.logInterface = logInterface;
    }

    public String getXmlXpath() {
        return xmlXpath;
    }

    public void setXmlXpath(String xmlXpath) {
        this.xmlXpath = xmlXpath;
    }
}
