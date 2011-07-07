/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmann.soa.logging.web.soaloggerweb;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import tkatva.net.soalogger.session.*;
import tkatva.net.soalogger.entity.*;
import java.util.List;
import org.richfaces.model.selection.Selection;
import org.richfaces.model.selection.SimpleSelection;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;
import java.util.HashMap;
import java.util.Random;
import javax.el.ELContext;
import stockmann.soa.logging.web.soaloggerweb.fileutil.FileSearch;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
/**
 *
 * @author KATVTUO
 */
public class SessionController {

    //@EJB(mappedName="SoaloggerBean#tkatva.net.soalogger.session.Soalogger")
    private Soalogger logger;
    private List<LogInterface> interfaces;
    private LogInterface selectedInterface;
    private Selection interfaceSelection = new SimpleSelection();
    private Selection fieldSelection = new SimpleSelection();
    public Logger serviceLogger = Logger.getLogger(SessionController.class.getName());

    private boolean showInterfaceEdit = false;
    private String[] environments = new String[] {"DEV","TST","PROD"};
    private String[] envs = new String[] {"ALL","DEV","TST","PROD"};
    private HashMap envsHm = new HashMap();
    private String selectedEnvironment = null;
    private boolean logWholeMsg = false;
    private HashMap envDropDown = new HashMap();
    private String selectedEnv = " ";

    private List<LogFields> logFields;
    private LogFields selectedLogField;
    private boolean showLogFieldTable = false;

    private boolean showLogFieldEdit = false;
    private boolean isFieldNew = false;
    private Random random = new Random();

    private boolean showRemoveBtn = false;
    private List<LogInterface> errorInterfaces;
    private boolean showErrors = false;
    /** Creates a new instance of SessionController */
    public SessionController() {
        try {
        
        this.loadSoaLoggerSessionBean();
        
        this.loadInterfaces();
        this.loadEnvs();
        this.loadErrorInterfaces();
        this.initLogger();
        serviceLogger.severe("SessionController initialized");
        } catch (Exception exp) {
           serviceLogger.severe("Exception initializing SessionController : " + exp.toString());
        }
    }

    private void initLogger() {
        Handler hf = new ConsoleHandler();
        serviceLogger.addHandler(hf);
    }

    private void loadSoaLoggerSessionBean() {
        try {
            InitialContext ctx = new InitialContext();
            this.logger = (Soalogger)ctx.lookup("SoaloggerBean#tkatva.net.soalogger.session.Soalogger");
        } catch (Exception exp) {
            serviceLogger.severe("Exception looking up SoaLogger session bean: " + exp.toString());
        }
    }

    private void loadErrorInterfaces() {
        try {
            this.errorInterfaces = logger.getInterfaceErrors();
            if (errorInterfaces.size() > 1) {
                this.showErrors = true;
            }
        } catch(Exception exp) {
            serviceLogger.severe("Error loading error interfaces: " + exp.toString());
        }
    }

    private void loadEnvs() {
        for (String env:envs) {
            envsHm.put(env, env);
        }
    }

    public void loadInterfaces() {
        try {
        if (this.selectedEnvironment == null) {
            this.selectedEnvironment = "ALL";
        }
        if (this.selectedEnvironment.trim().equals("ALL")) {
        this.interfaces = logger.getAllLogInterfaces();
        } else {
            this.interfaces = logger.getAllLogInterfacesWithEnv(this.selectedEnvironment);

        }
        } catch (Exception exp) {
            exp.printStackTrace();
            serviceLogger.severe("Exception in loading interfaces : " + exp.toString());
        }
    }

    private Object getManagedBean(String name) {
        try  {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        return FacesContext.getCurrentInstance().getApplication()
        .getELResolver().getValue(elContext, null, name);
        } catch (Exception exp) {
            serviceLogger.severe("Unable to find managed bean : " + name + ", exception : " + exp.toString());
            return null;
        }

    }

    public void refreshTable() {
        this.loadInterfaces();
        this.loadErrorInterfaces();
    }

    public void removeLogInterface() {
       int retval = this.logger.removeLogInterface(selectedInterface);
       FileSearch fileUtil = this.getInitFileSearch();
       fileUtil.setInterfaceName(selectedInterface.getLogInterfaceName());
       fileUtil.setEnv(selectedInterface.getEnvironmentFlag());
       int returnVal = fileUtil.removeAllInterfaceMessages();
       if (returnVal < 0) {
           serviceLogger.severe("Exception occurred removing interface messages, error code: "  + returnVal);
       }
       this.loadInterfaces();
       this.loadErrorInterfaces();
       this.selectedInterface = null;
       this.showInterfaceEdit = false;
       this.showLogFieldTable = false;
    }


    public void loadLogFields() {
        try {
            this.logFields = this.logger.getInterfaceFields(selectedInterface);
            
            this.showLogFieldTable = true;
        } catch (Exception exp) {
            serviceLogger.severe("Exception loading log fields: " + exp.toString());
        }
    }

    private String getResourceString(String resName) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages",FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String text = bundle.getString(resName);
        return text;
    }

    private void addUserMessage(String bundleKeyName) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages",FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String text = bundle.getString(bundleKeyName);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,text,null));
    }

    private void addUserWarnMessage(String bundleKeyName) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages",FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String text = bundle.getString(bundleKeyName);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,text,null));
    }

    public void takeInterfaceSelection() {
        this.showInterfaceEdit = true;
        
        Integer indx = (Integer)interfaceSelection.getKeys().next();
        this.selectedInterface = interfaces.get(indx);
        //Try to get interface environment
        try {
        if (this.selectedInterface.getEnvironmentFlag() != null)
        this.selectedEnv = this.selectedInterface.getEnvironmentFlag();
        } catch (Exception exx) {

        }
        //Try to get log flag
        try {
          if (this.selectedInterface.getLogMessageFlag().trim().equals("Y")) {
              this.logWholeMsg = true;
          }  else {
              this.logWholeMsg = false;
          }
        } catch (Exception exp) {
            this.logWholeMsg = false;
        }
        this.getEnvs();
        this.loadLogFields();
    }

    public void takeFieldSelection() {
        this.setShowRemoveBtn(true);
        this.showLogFieldEdit = true;
        this.isFieldNew = false;
        Integer indx = (Integer)this.fieldSelection.getKeys().next();
        this.selectedLogField = this.logFields.get(indx);
    }

    public void cancelFieldEdit() {
        this.showLogFieldEdit = false;
    }

    public void newLogField() {
        this.setShowRemoveBtn(false);
        this.showLogFieldEdit = true;
        this.isFieldNew = true;
        this.selectedLogField = new LogFields();
    }

    public String navigateToMonitor() {
        
        MonitorPage monitor = null;
        
        monitor = (MonitorPage)this.getManagedBean("monitorPage");
        
        monitor.setLogger(this.logger);
        
        
        //monitor.loadLogInterfaces();
        monitor.setEnvs();
        return "monitor";
    }

    public String navigateMaintenance() {
        MaintenanceBean maintenance = (MaintenanceBean)this.getManagedBean("maintenanceBean");
        maintenance.setSoaLogger(this.logger);
        maintenance.loadSchedules();
        maintenance.loadNotifications();
        maintenance.loadInterfaces();
        return "maintenance";
    }

    public void saveField() {
        boolean updateFlag;

        if (this.selectedLogField.getXmlXpath().length() > 0 && this.selectedLogField.getElementName().length() > 0) {
            this.addUserWarnMessage("LogField_selectOne");
            return;
        }

        if (this.selectedLogField.getXmlXpath().length() < 1 && this.selectedLogField.getElementName().length() < 1) {
            this.addUserWarnMessage("LogField_oneNeeded");
            return;
        }

        if (this.selectedLogField.getXmlXpath().length() < 1) {
            this.selectedLogField.setXmlXpath(null);
        }

        if (isFieldNew) {
            updateFlag = false;
            this.selectedLogField.setLogFieldId(random.nextLong());
        } else {
            updateFlag = true;
        }
        this.logger.addLogField(selectedLogField, selectedInterface, updateFlag);
        this.showLogFieldEdit = false;
        this.loadLogFields();
    }

    public void removeFiled() {
        this.logger.removeLogField(selectedLogField);
        this.showLogFieldEdit = false;
        this.loadLogFields();
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
    
    public void saveInterface() {
        if (logWholeMsg) {
            this.selectedInterface.setLogMessageFlag("Y");
        } else {
            this.selectedInterface.setLogMessageFlag("N");
        }
        this.selectedInterface.setEnvironmentFlag(selectedEnv);
        int retval = this.logger.updateLogInterface(selectedInterface);
        if (retval == 0) {
        this.addUserMessage("Main_interfaceEditOkMsg");
        } else {
        this.addUserMessage("Main_interfaceEditNotOkMsg");
        }
        this.showInterfaceEdit = false;
        this.showLogFieldTable = false;
        this.showLogFieldEdit = false;
    }

    public void cancelInterfaceEdit() {
        this.showInterfaceEdit = false;
        this.showLogFieldTable = false;
        this.showLogFieldEdit = false;
        this.selectedInterface = new LogInterface();
    }

    private void getEnvs() {
        for (String env:this.environments) {
            this.envDropDown.put(env, env);
        }
    }
 
    public Soalogger getLogger() {
        return logger;
    }

   
    public void setLogger(Soalogger logger) {
        this.logger = logger;
    }

   
    public List<LogInterface> getInterfaces() {
        return interfaces;
    }

   
    public void setInterfaces(List<LogInterface> interfaces) {
        this.interfaces = interfaces;
    }

   
    public LogInterface getSelectedInterface() {
        return selectedInterface;
    }

   
    public void setSelectedInterface(LogInterface selectedInterface) {
        this.selectedInterface = selectedInterface;
    }

    /**
     * @return the interfaceSelection
     */
    public Selection getInterfaceSelection() {
        return interfaceSelection;
    }

    /**
     * @param interfaceSelection the interfaceSelection to set
     */
    public void setInterfaceSelection(Selection interfaceSelection) {
        this.interfaceSelection = interfaceSelection;
    }

    /**
     * @return the showInterfaceEdit
     */
    public boolean isShowInterfaceEdit() {
        return showInterfaceEdit;
    }

    /**
     * @param showInterfaceEdit the showInterfaceEdit to set
     */
    public void setShowInterfaceEdit(boolean showInterfaceEdit) {
        this.showInterfaceEdit = showInterfaceEdit;
    }

    /**
     * @return the envDropDown
     */
    public HashMap getEnvDropDown() {
        return envDropDown;
    }

    /**
     * @param envDropDown the envDropDown to set
     */
    public void setEnvDropDown(HashMap envDropDown) {
        this.envDropDown = envDropDown;
    }

    /**
     * @return the logWholeMsg
     */
    public boolean isLogWholeMsg() {
        return logWholeMsg;
    }

    /**
     * @param logWholeMsg the logWholeMsg to set
     */
    public void setLogWholeMsg(boolean logWholeMsg) {
        this.logWholeMsg = logWholeMsg;
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
     * @return the logFields
     */
    public List<LogFields> getLogFields() {
        return logFields;
    }

    /**
     * @param logFields the logFields to set
     */
    public void setLogFields(List<LogFields> logFields) {
        this.logFields = logFields;
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
     * @return the showLogFieldTable
     */
    public boolean isShowLogFieldTable() {
        return showLogFieldTable;
    }

    /**
     * @param showLogFieldTable the showLogFieldTable to set
     */
    public void setShowLogFieldTable(boolean showLogFieldTable) {
        this.showLogFieldTable = showLogFieldTable;
    }

    /**
     * @return the fieldSelection
     */
    public Selection getFieldSelection() {
        return fieldSelection;
    }

    /**
     * @param fieldSelection the fieldSelection to set
     */
    public void setFieldSelection(Selection fieldSelection) {
        this.fieldSelection = fieldSelection;
    }

    /**
     * @return the showLogFieldEdit
     */
    public boolean isShowLogFieldEdit() {
        return showLogFieldEdit;
    }

    /**
     * @param showLogFieldEdit the showLogFieldEdit to set
     */
    public void setShowLogFieldEdit(boolean showLogFieldEdit) {
        this.showLogFieldEdit = showLogFieldEdit;
    }

    /**
     * @return the isFieldNew
     */
    public boolean isIsFieldNew() {
        return isFieldNew;
    }

    /**
     * @param isFieldNew the isFieldNew to set
     */
    public void setIsFieldNew(boolean isFieldNew) {
        this.isFieldNew = isFieldNew;
    }

    /**
     * @return the showRemoveBtn
     */
    public boolean isShowRemoveBtn() {
        return showRemoveBtn;
    }

    /**
     * @param showRemoveBtn the showRemoveBtn to set
     */
    public void setShowRemoveBtn(boolean showRemoveBtn) {
        this.showRemoveBtn = showRemoveBtn;
    }

    /**
     * @param envs the envs to set
     */
    public void setEnvs(String[] envs) {
        this.envs = envs;
    }

    /**
     * @return the envsHm
     */
    public HashMap getEnvsHm() {
        return envsHm;
    }

    /**
     * @param envsHm the envsHm to set
     */
    public void setEnvsHm(HashMap envsHm) {
        this.envsHm = envsHm;
    }

    /**
     * @return the selectedEnvironment
     */
    public String getSelectedEnvironment() {
        return selectedEnvironment;
    }

    /**
     * @param selectedEnvironment the selectedEnvironment to set
     */
    public void setSelectedEnvironment(String selectedEnvironment) {
        this.selectedEnvironment = selectedEnvironment;
    }

    /**
     * @return the errorInterfaces
     */
    public List<LogInterface> getErrorInterfaces() {
        return errorInterfaces;
    }

    /**
     * @param errorInterfaces the errorInterfaces to set
     */
    public void setErrorInterfaces(List<LogInterface> errorInterfaces) {
        this.errorInterfaces = errorInterfaces;
    }

    /**
     * @return the showErrors
     */
    public boolean isShowErrors() {
        return showErrors;
    }

    /**
     * @param showErrors the showErrors to set
     */
    public void setShowErrors(boolean showErrors) {
        this.showErrors = showErrors;
    }
    
}
