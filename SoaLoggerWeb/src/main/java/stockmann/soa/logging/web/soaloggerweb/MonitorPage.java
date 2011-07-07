/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmann.soa.logging.web.soaloggerweb;

import tkatva.net.soalogger.session.*;
import tkatva.net.soalogger.entity.*;
import java.util.List;
import java.util.ArrayList;
import org.richfaces.model.selection.Selection;
import org.richfaces.model.selection.SimpleSelection;
import javax.faces.application.FacesMessage;
import java.util.ResourceBundle;
import java.util.HashMap;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import java.util.Date;
import stockmann.soa.logging.web.soaloggerweb.fileutil.FileSearch;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import stockmann.soa.logging.web.soaloggerweb.app.SoaLoggerAppBean;

/**
 *
 * @author katvtuo
 */
public class MonitorPage {

    /** Creates a new instance of MonitorPage */
    
    private Soalogger logger;
    private List<LogInterface> logInterfaces;
    private List<LogEntry> entries;
    private HashMap logInterfaceDropDownVals = new HashMap();
    private HashMap logLevelHm = new HashMap();
    private Long selectedInterfaceId;
    private boolean showEntries = false;
    private Selection entrySelection = new SimpleSelection();
    private Selection valueSelection = new SimpleSelection();
    private Date fromDate = new Date();
    private Date toDate;
    private LogInterface selectedInterface; 

    private List<LogFields> fields;
    private HashMap fieldMap = new HashMap();
    private boolean showAdvSearch = false;
    private Long selectedFieldId;
    private String searchVal;
    private LogFields selectedLogField;
    private LogEntry selectedEntry;
    private List<EntryValues> values;
    private boolean showEntryValues = false;

    private String[] environments = new String[] {"DEV","TST","PROD"};
    private String selectedEnv;
    private String[] logLevels = new String[] {"ERROR","WARNING","INFO","ALL"};
    private String selectedLogLevel;
    private HashMap envMap = new HashMap();
    private FileSearch fileUtil;
    private String searchString;
    private boolean searchFromMessages = false;
    public Logger serviceLogger = Logger.getLogger(this.getClass().getName());

    private EntryValues selectedValue;

  
    public MonitorPage() {
        this.addHandlerToServiceLogger();
    }

    private void addHandlerToServiceLogger() {
        Handler hf = new ConsoleHandler();
        serviceLogger.addHandler(hf);
    }
    /**
     * @return the logger
     */
    public Soalogger getLogger() {
        return logger;
    }

    /**
     * @param logger the logger to set
     */
    public void setLogger(Soalogger logger) {
        this.logger = logger;
    }

    public void showValueSelection() {
        try {
        Integer valueIndx = (Integer)this.valueSelection.getKeys().next();
        this.selectedValue = this.values.get(valueIndx);
        } catch (Exception exp) {
            System.out.println(exp.toString());
        }
    }

    public void loadLogInterfaces() {
        try {
            this.logInterfaces = this.logger.getAllLogInterfaces();
            this.setInterfaceDropdown();
        } catch (Exception exp) {

        }
    }

    public void cancelSearches() {
        this.searchFromMessages = false;
        this.showAdvSearch = false;
    }

    public void setSearchFromMessages() {
        this.searchFromMessages = true;
        this.showAdvSearch = false;
    }

    public void setEnvs () {
        for (String envString:this.environments) {
                this.envMap.put(envString, envString);
        }
        this.setLevels();
    }

    public void setLevels() {
        for (String levelString:this.logLevels) {
            this.logLevelHm.put(levelString, levelString);
        }
    }

    public void showAdvanced() {
        try {
            this.searchFromMessages = false;
            this.showAdvSearch = true;
            this.selectLogInterface();
            this.fields = logger.getInterfaceFields(selectedInterface);
            this.createFieldMap();
            this.loadLogInterfaces();
        } catch (Exception exp ) {

        }
        
    }

    public void cancelAdvanced() {
        this.showAdvSearch = false;
    }

    private void createFieldMap() {
        this.fieldMap.clear();
        for (LogFields field:this.fields) {
            this.fieldMap.put(field.getLogFieldName(), field.getLogFieldId());

        }
    }

    public String navigateToSettings() {
        this.showAdvSearch = false;
        this.showEntries = false;
        this.showEntryValues = false;
        return "toMain";
    }

    public String navigateToMaintenance() {
        this.showAdvSearch = false;
        this.showEntries = false;
        this.showEntryValues = false;
        return "maintenance";
    }

    public void setInterfaceDropdown() {
        this.logInterfaceDropDownVals.clear();
        for (LogInterface logInter:logInterfaces) {
            logInterfaceDropDownVals.put(logInter.getLogInterfaceName(), logInter.getLogInterfaceId());
        }
    }

    public void showEntrySelection() {
        try {
            Integer indx = (Integer)this.entrySelection.getKeys().next();
            this.setSelectedEntry(this.entries.get(indx));
            this.values = this.logger.getEntryValues(getSelectedEntry());
            this.showEntryValues = true;
        } catch (Exception exp) {

        }
    }

    private Object getManagedBean(String name) {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        return FacesContext.getCurrentInstance().getApplication()
        .getELResolver().getValue(elContext, null, name);

    }

    /**
     * @return the logInterfaceDropDownVals
     */
    public HashMap getLogInterfaceDropDownVals() {
        return logInterfaceDropDownVals;
    }

    /**
     * @param logInterfaceDropDownVals the logInterfaceDropDownVals to set
     */
    public void setLogInterfaceDropDownVals(HashMap logInterfaceDropDownVals) {
        this.logInterfaceDropDownVals = logInterfaceDropDownVals;
    }

    public void findInterfaceWithEnv() {
        try {
            this.showEntries = false;
            this.entries = new ArrayList<LogEntry>();
            this.logInterfaces = this.logger.getAllLogInterfacesWithEnv(this.selectedEnv);
            this.setInterfaceDropdown();
        } catch (Exception exp) {
           
        }
    }

    public void findWithDates() {
        try {
            //If this is checked, then value will be searched from message residing in docroot-folder
            if (this.searchFromMessages) {
                this.fileUtil = this.getInitFileSearch();
                this.fileUtil.setEnv(this.selectedEnv);
                this.selectLogInterface();
                this.fileUtil.setInterfaceName(this.selectedInterface.getLogInterfaceName());
                ArrayList<String> foundFiles = this.fileUtil.searchFolder(this.searchString);
                if (foundFiles != null && foundFiles.size() > 0) {

                    this.entries = new ArrayList<LogEntry>();
                    for (String searchStr:foundFiles) {

                    List<LogEntry> logEntries = this.logger.getLogEntriesWithInstanceid(searchStr);
                        for (LogEntry logEntry:logEntries) {

                        entries.add(logEntry);
                        }
                        this.checkFiles();
                    }
                    this.showEntries = true;
                }
                
            } else {
            //If this is checked then entries containing specified search variable will be searched
            if (showAdvSearch) {
               if (this.fromDate != null && this.toDate != null && this.selectedInterfaceId != null) {
                this.selectLogInterface();
                this.selectLogField();
                if (this.checkQueryCount(selectedInterface, fromDate, toDate)) {
                EntryValues ev = new EntryValues();
                ev.setValueName(this.selectedLogField.getLogFieldName());
                ev.setEntryValue(this.searchVal);
                this.entries = this.logger.getLogEntriesWithEntryValueAndDate(ev, fromDate, toDate);
                this.checkFiles();
                this.showEntries = true;
                } else {
                    this.addUserErrorMessage("Search_too_many");
                }
            }
            } else {
            //This is "default seach...
            if (this.fromDate != null && this.toDate != null && this.selectedInterfaceId != null) {
                
                this.selectLogInterface();
                if (this.checkQueryCount(selectedInterface, fromDate, toDate)) {
                if (this.selectedLogLevel.trim().equals("ALL")) {
                   this.entries = this.logger.getInterfaceEntriesBetween(getSelectedInterface(),fromDate, toDate);
                } else {
                this.entries = this.logger.getInterfaceEntriesBetweenWithEnvLoglevel(selectedInterface, this.selectedLogLevel ,fromDate, toDate);
                }
                this.checkFiles();
                this.showEntries = true;
                } else {
                    this.addUserErrorMessage("Search_too_many");
                }
            }
            }

            }
            
        } catch (Exception exp) {
            exp.printStackTrace();
            serviceLogger.severe("Exception finding log entries : " + exp.toString());
        }
    }


    private boolean checkQueryCount(LogInterface liParam,Date fromParam,Date toParam) {
        try {
            
            Number countNumber = this.logger.getLogEntryCountWithDateAndInterface(fromParam, toParam, liParam);
            int count = countNumber.intValue();
            SoaLoggerAppBean soaApp = (SoaLoggerAppBean)this.getManagedBean("soaLoggerAppBean");
            if (count > soaApp.getMaxQueryResults()) {
                return false;
            } else {
                return true;
            }


        } catch (Exception exp) {
            exp.printStackTrace();
            serviceLogger.severe("Exception getting queryCount: " + exp.toString());
            return false;
        }
    }

    private void selectLogField() {
        for (LogFields field:this.fields) {
            if (field.getLogFieldId().equals(this.selectedFieldId)) {
                this.selectedLogField = field;

            }
        }
    }

    private FileSearch getInitFileSearch() {
        try {
        Resource resource = new ClassPathResource("/SoaloggerWebConf.xml");
        BeanFactory factory = new XmlBeanFactory(resource);
        return (FileSearch)factory.getBean("FileSearch");
        } catch (Exception exp) {
            return null;
        }
    }

    private void checkFiles() {
        
        /*
        try {
        for (LogEntry entry:entries) {
            FileSearch fs = this.getInitFileSearch();
            
            fs.setEnv(this.selectedEnv);
            fs.setInterfaceName(this.selectedInterface.getLogInterfaceName());
            fs.setInstanceId(entry.getIntegrationInstanceId());
            if (fs.checkFile()) {
                entry.setUrlSet(true);
                entry.setUrlPath(fs.getUrlPath());
            } else {
                entry.setUrlSet(false);
            }
        }
        } catch (Exception exp) {
            System.out.println("exception : " + exp.toString());
        }
        */
    }

    private void selectLogInterface() {
        
                for (LogInterface inter:this.logInterfaces) {
                    if (inter.getLogInterfaceId().equals(this.selectedInterfaceId)) {
                        selectedInterface = inter;
                    }
                }
    }

    private void addUserErrorMessage(String bundleKeyName) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages",FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String text = bundle.getString(bundleKeyName);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,text,null));
    }
    /**
     * @return the selectedInterfaceId
     */
    public Long getSelectedInterfaceId() {
        return selectedInterfaceId;
    }

    /**
     * @param selectedInterfaceId the selectedInterfaceId to set
     */
    public void setSelectedInterfaceId(Long selectedInterfaceId) {
        this.selectedInterfaceId = selectedInterfaceId;
    }

    /**
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the entries
     */
    public List<LogEntry> getEntries() {
        return entries;
    }

    /**
     * @param entries the entries to set
     */
    public void setEntries(List<LogEntry> entries) {
        this.entries = entries;
    }

    /**
     * @return the showEntries
     */
    public boolean isShowEntries() {
        return showEntries;
    }

    /**
     * @param showEntries the showEntries to set
     */
    public void setShowEntries(boolean showEntries) {
        this.showEntries = showEntries;
    }

    /**
     * @return the entrySelection
     */
    public Selection getEntrySelection() {
        return entrySelection;
    }

    /**
     * @param entrySelection the entrySelection to set
     */
    public void setEntrySelection(Selection entrySelection) {
        this.entrySelection = entrySelection;
    }

    /**
     * @return the fields
     */
    public List<LogFields> getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(List<LogFields> fields) {
        this.fields = fields;
    }

    /**
     * @return the fieldMap
     */
    public HashMap getFieldMap() {
        return fieldMap;
    }

    /**
     * @param fieldMap the fieldMap to set
     */
    public void setFieldMap(HashMap fieldMap) {
        this.fieldMap = fieldMap;
    }

    /**
     * @return the showAdvSearch
     */
    public boolean isShowAdvSearch() {
        return showAdvSearch;
    }

    /**
     * @param showAdvSearch the showAdvSearch to set
     */
    public void setShowAdvSearch(boolean showAdvSearch) {
        this.showAdvSearch = showAdvSearch;
    }

    /**
     * @return the selectedInterface
     */
    public LogInterface getSelectedInterface() {
        return selectedInterface;
    }

    /**
     * @param selectedInterface the selectedInterface to set
     */
    public void setSelectedInterface(LogInterface selectedInterface) {
        this.selectedInterface = selectedInterface;
    }

    /**
     * @return the selectedFieldId
     */
    public Long getSelectedFieldId() {
        return selectedFieldId;
    }

    /**
     * @param selectedFieldId the selectedFieldId to set
     */
    public void setSelectedFieldId(Long selectedFieldId) {
        this.selectedFieldId = selectedFieldId;
    }

    /**
     * @return the searchVal
     */
    public String getSearchVal() {
        return searchVal;
    }

    /**
     * @param searchVal the searchVal to set
     */
    public void setSearchVal(String searchVal) {
        this.searchVal = searchVal;
    }

    /**
     * @return the selectedLogField
     */
    public LogFields getSelectedLogField() {
        return selectedLogField;
    }

    /**
     * @param selectedLogField the selectedLogField to set
     */
    public void setSelectedLogField(LogFields selectedLogField) {
        this.selectedLogField = selectedLogField;
    }

    /**
     * @return the values
     */
    public List<EntryValues> getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(List<EntryValues> values) {
        this.values = values;
    }

    /**
     * @return the showEntryValues
     */
    public boolean isShowEntryValues() {
        return showEntryValues;
    }

    /**
     * @param showEntryValues the showEntryValues to set
     */
    public void setShowEntryValues(boolean showEntryValues) {
        this.showEntryValues = showEntryValues;
    }

    /**
     * @return the selectedEnv
     */
    public String getSelectedEnv() {
        return selectedEnv;
    }

    /**
     * @param selectedEnv the selectedEnv to set
     */
    public void setSelectedEnv(String selectedEnv) {
        this.selectedEnv = selectedEnv;
    }

    /**
     * @return the envMap
     */
    public HashMap getEnvMap() {
        return envMap;
    }

    /**
     * @param envMap the envMap to set
     */
    public void setEnvMap(HashMap envMap) {
        this.envMap = envMap;
    }

    /**
     * @return the selectedEntry
     */
    public LogEntry getSelectedEntry() {
        return selectedEntry;
    }

    /**
     * @param selectedEntry the selectedEntry to set
     */
    public void setSelectedEntry(LogEntry selectedEntry) {
        this.selectedEntry = selectedEntry;
    }

    /**
     * @return the searchString
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * @param searchString the searchString to set
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    /**
     * @return the searchFromMessages
     */
    public boolean isSearchFromMessages() {
        return searchFromMessages;
    }

    /**
     * @param searchFromMessages the searchFromMessages to set
     */
    public void setSearchFromMessages(boolean searchFromMessages) {
        this.searchFromMessages = searchFromMessages;
    }

    /**
     * @return the logLevels
     */
    public String[] getLogLevels() {
        return logLevels;
    }

    /**
     * @param logLevels the logLevels to set
     */
    public void setLogLevels(String[] logLevels) {
        this.logLevels = logLevels;
    }

    /**
     * @return the selectedLogLevel
     */
    public String getSelectedLogLevel() {
        return selectedLogLevel;
    }

    /**
     * @param selectedLogLevel the selectedLogLevel to set
     */
    public void setSelectedLogLevel(String selectedLogLevel) {
        this.selectedLogLevel = selectedLogLevel;
    }

    /**
     * @return the logLevelHm
     */
    public HashMap getLogLevelHm() {
        return logLevelHm;
    }

    /**
     * @param logLevelHm the logLevelHm to set
     */
    public void setLogLevelHm(HashMap logLevelHm) {
        this.logLevelHm = logLevelHm;
    }

    /**
     * @return the selectedValue
     */
    public EntryValues getSelectedValue() {
        return selectedValue;
    }

    /**
     * @param selectedValue the selectedValue to set
     */
    public void setSelectedValue(EntryValues selectedValue) {
        this.selectedValue = selectedValue;
    }

    /**
     * @return the valueSelection
     */
    public Selection getValueSelection() {
        return valueSelection;
    }

    /**
     * @param valueSelection the valueSelection to set
     */
    public void setValueSelection(Selection valueSelection) {
        this.valueSelection = valueSelection;
    }

    
}
