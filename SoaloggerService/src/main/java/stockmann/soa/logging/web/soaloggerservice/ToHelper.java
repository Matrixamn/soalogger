/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmann.soa.logging.web.soaloggerservice;

import stockmann.com.logging.*;
import stockmann.soa.logging.web.soaloggerservice.transfer.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Random;
/**
 *
 * @author katvtuo
 */
public class ToHelper {


    Random keygen = new Random();
    public synchronized LogInterface convertInterfaces(LogInterfaceTo li) {
           
            
                LogInterface iface = new LogInterface();
                if (li.getLogInterfaceId() != null) {
                    iface.setLogInterfaceId(li.getLogInterfaceId());
                } else {
                    iface.setLogInterfaceId(new Integer(keygen.nextInt()));
                }
                iface.setEnvironmentFlag(li.getEnvironmentFlag());
                iface.setIntegrationType(li.getIntegrationType());
                iface.setLogInterfaceName(li.getLogInterfaceName());
                iface.setLogInterfaceVersion(li.getLogInterfaceVersion());
                iface.setLogMessageFlag(li.getLogMessageFlag());
                iface.setMsgMaxLenght(li.getMsgMaxLenght());
                iface.setPreferredLoggingLevel(li.getPreferredLoggingLevel());
                if (li.getLogEntryList() != null) {
                    iface.setLogEntryList(this.convertEntries(li.getLogEntryList()));
                }

                if (li.getLogFieldsList() != null) {
                    iface.setLogFieldsList(this.convertFields(li.getLogFieldsList()));
                }

                if (li.getApplyBase64() != null) {
                    iface.setApplyBase64(li.getApplyBase64());
                }

                if (li.getMsgEncoding() != null) {
                    iface.setMsg_encoding(li.getMsgEncoding());
                }
            
            return iface;
    }

    public synchronized List<LogEntry> convertEntries(List<LogEntryTo> entriesParam) {
        ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
        for (LogEntryTo to:entriesParam) {
            LogEntry entry = new LogEntry();
            entry.setInstanceVersion(to.getInstanceVersion());
            entry.setIntegrationInstanceId(to.getIntegrationInstanceId());
            entry.setLogDate(new Timestamp(to.getLogDate().getTime()));
           
                if (to.getLogEntryId() != null) {
                entry.setLogEntryId(to.getLogEntryId());
                } else {
                    entry.setLogEntryId(new Integer(keygen.nextInt()));
                }
            
            entry.setLogLevel(to.getLogLevel());
            entry.setLogMsg(to.getLogMsg());
            entry.setLogPayload(to.getLogPayload());
            if (to.getEntryValuesList() != null) {
               List<EntryValues> values = this.getEntryValues(to.getEntryValuesList(), entry);
               entry.setEntryValuesList(values);
            }
            entries.add(entry);
        }
        return entries;
    }

    public synchronized List<EntryValues> getEntryValues(List<EntryValueTo> valuesParam, LogEntry entryParam) {
        ArrayList<EntryValues> values = new ArrayList<EntryValues>();
        for (EntryValueTo to:valuesParam) {
            EntryValues value = new EntryValues();
            value.setEntryValue(to.getEntryValue());
           
               if (to.getValueId() != null) {
                   value.setValueId(to.getValueId());
               }  else {
                   value.setValueId(new Integer(keygen.nextInt()));
               }
           
            
            value.setValueName(to.getValueName());
            value.setLogEntry(entryParam);
            values.add(value);
        }
        return values;
    }

    public synchronized List<LogInterfaceTo> convertInterfacesTo(List<LogInterface> interfacesParam) {
        ArrayList<LogInterfaceTo> interfacesTo = new ArrayList<LogInterfaceTo>();
        for (LogInterface li:interfacesParam) {
            LogInterfaceTo liTo = new LogInterfaceTo();
            liTo.setLogInterfaceId(li.getLogInterfaceId());
            liTo.setEnvironmentFlag(li.getEnvironmentFlag());
            liTo.setIntegrationType(li.getIntegrationType());
            liTo.setLogInterfaceName(li.getLogInterfaceName());
            liTo.setLogInterfaceVersion(li.getLogInterfaceVersion());
            liTo.setLogMessageFlag(li.getLogMessageFlag());
            liTo.setMsgMaxLenght(li.getMsgMaxLenght());
            liTo.setPreferredLoggingLevel(li.getPreferredLoggingLevel());
            interfacesTo.add(liTo);
        }

        return interfacesTo;
    }

    public synchronized LogInterfaceTo convertLogInterfaceTo(LogInterface li) {
            LogInterfaceTo liTo = new LogInterfaceTo();
            liTo.setLogInterfaceId(li.getLogInterfaceId());
            liTo.setEnvironmentFlag(li.getEnvironmentFlag());
            liTo.setIntegrationType(li.getIntegrationType());
            liTo.setLogInterfaceName(li.getLogInterfaceName());
            liTo.setLogInterfaceVersion(li.getLogInterfaceVersion());
            liTo.setLogMessageFlag(li.getLogMessageFlag());
            liTo.setMsgMaxLenght(li.getMsgMaxLenght());
            liTo.setPreferredLoggingLevel(li.getPreferredLoggingLevel());
            if (li.getLogFieldsList() != null) {
                List<LogFieldTo> fields = this.convertFieldTo(li.getLogFieldsList());
                liTo.setLogFieldsList(fields);
            }

            if (li.getApplyBase64() != null) {
                liTo.setApplyBase64(li.getApplyBase64());
            }

            if (li.getMsg_encoding() != null) {
                liTo.setMsgEncoding(li.getMsg_encoding());
            }
            return liTo;

    }

    public synchronized List<LogFieldTo> convertFieldTo(List<LogFields> fieldsParam) {
        ArrayList<LogFieldTo> fields = new ArrayList<LogFieldTo>();
        for (LogFields lf:fieldsParam) {
            LogFieldTo toField = new LogFieldTo();
            toField.setLogFieldId(lf.getLogFieldId());
            toField.setElementName(lf.getElementName());
            toField.setLogFieldName(lf.getLogFieldName());
            toField.setXmlXpath(lf.getXmlXpath());
            fields.add(toField);
        }
        return fields;
    }

    public synchronized List<LogFields> convertFields(List<LogFieldTo> fieldsParam) {
        ArrayList<LogFields> fields = new ArrayList<LogFields>();
        for (LogFieldTo to:fieldsParam) {
            LogFields field = new LogFields();
            field.setElementName(to.getElementName());
            field.setXmlXpath(to.getXmlXpath());
                if (to.getLogFieldId() != null) {
                    field.setLogFieldId(to.getLogFieldId());
                } else {
                    field.setLogFieldId(new Integer(keygen.nextInt()));
                }
           
            
            fields.add(field);
        }
        return fields;
    }


}
