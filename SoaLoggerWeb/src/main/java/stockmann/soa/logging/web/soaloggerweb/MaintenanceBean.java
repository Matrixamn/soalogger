/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmann.soa.logging.web.soaloggerweb;

import java.util.logging.Level;
import tkatva.net.soalogger.session.*;
import tkatva.net.soalogger.entity.*;
import java.util.List;
import java.util.HashMap;
import org.richfaces.model.selection.Selection;
import org.richfaces.model.selection.SimpleSelection;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
/**
 *
 * @author KATVTUO
 */
public class MaintenanceBean {

    /** Creates a new instance of MaintenanceBean */
    public MaintenanceBean() {
          Handler hf = new ConsoleHandler();
         logger.addHandler(hf);
         this.loadEnvs();
    }

    private Logger logger = Logger.getLogger(MaintenanceBean.class.getName());
    private Soalogger soaLogger;
    List<LogInterface> interfaces;
    private HashMap interfaceHm = new HashMap();
    private LogInterface interfaceId = new LogInterface();
    private List<LogSched> schedules;
    private Selection scheduleSelection = new SimpleSelection();
    private LogSched selectedSchedule;
    
    private List<LoginterfaceNotification> notifications;
    private Selection notSelection = new SimpleSelection();
    private LoginterfaceNotification selectedNotification;
    private boolean showRemoveBtn = false;
    private HashMap envMap = new HashMap();
    private String selectedEnv;
    private String[] envs = new String[] {"DEV","TST","PROD"};


    public void newSchedule() {
        this.loadEnvs();
        this.loadInterfaces();
        this.selectedSchedule = new LogSched();

    }

    private void loadEnvs() {
        setEnvMap(new HashMap());
        for (String env:envs) {
            getEnvMap().put(env, env);
        }
    }

    public void loadInterfacesWithEnv() {
        try {
            if (this.selectedNotification.getInterfaceEnv() != null) {
                this.selectedEnv = this.selectedNotification.getInterfaceEnv();
            }

            if (this.selectedEnv != null) {
            this.interfaces = this.soaLogger.getAllLogInterfacesWithEnv(this.selectedEnv);
            this.loadHm();
            }
        } catch (Exception exp) {

        }
    }

    public void loadInterfaces() {
        try {
            this.interfaces = soaLogger.getAllLogInterfacesWithEnv(selectedEnv);
            this.loadHm();

        } catch (Exception exp) {

        }
    }

    public void selectNotification() {
        this.loadEnvs();
        Integer notIndx = (Integer)notSelection.getKeys().next();
        this.selectedNotification = notifications.get(notIndx);
        this.showRemoveBtn = true;
    }

    public void removeNotification() {
        try {
            this.soaLogger.removeLogInterfaceNotification(selectedNotification);
            this.selectedNotification = new LoginterfaceNotification();
            this.loadNotifications();
        } catch (Exception exp) {

        }
    }

    public void saveNotification() {
        try {
            //this.selectedNotification.setLogInterfaceId(this.interfaceId);
            this.soaLogger.addLogInterfaceNotification(this.selectedNotification);
            this.selectedNotification = new LoginterfaceNotification();
            this.loadNotifications();
        } catch (Exception exp) {

        }
    }
    
    public void newNotification() {
        this.loadEnvs();
        this.showRemoveBtn = false;
        this.selectedNotification = new LoginterfaceNotification();
    }

    public void loadNotifications() {
        try {
            this.selectedNotification = new LoginterfaceNotification();
            this.notifications = this.soaLogger.getInterfaceNotifications(this.getSelectedInterface());
        } catch (Exception exp) {

        }
    }

    public String navigateToMonitor() {
        return "monitor";
    }

    public void loadSchedules() {
        try {
            this.schedules = soaLogger.getAllLogSchedules();
            for (LogSched sched:schedules) {
                sched.setLogInterface(this.interfaceId);
            }
        } catch (Exception exp) {
                logger.log(Level.SEVERE, "Unable to load schedules: {0}", exp.toString());
        }
    }

    public void removeSchedule() {
        try {
            this.soaLogger.removeLogSched(selectedSchedule);
            this.selectedSchedule = new LogSched();
            this.loadSchedules();
        } catch (Exception exp) {
            logger.log(Level.SEVERE, "Unable to remove schedule: {0}", exp.toString());
        }
    }

    public void takeScheduleSelection() {
        Integer schedIndx = (Integer)this.scheduleSelection.getKeys().next();
        this.selectedSchedule = this.schedules.get(schedIndx);
        this.loadInterfaces();
        this.loadEnvs();
        this.interfaceId = this.selectedSchedule.getLogInterface();


    }
    
    private LogInterface getSelectedInterface() {
        LogInterface foundIface = null;
        for (LogInterface iface:this.interfaces) {
            if (iface.getLogInterfaceId().equals(this.interfaceId)) {
                foundIface = iface;
            }
        }
        return foundIface;
    }

    public void saveSchedule() {
        try {
            this.selectedSchedule.setLogInterface(interfaceId);
            this.selectedSchedule.setLogInterface(this.getSelectedInterface());
            this.soaLogger.updateOrAddLogSchedule(selectedSchedule);
            this.loadSchedules();
        } catch (Exception exp) {
            logger.log(Level.SEVERE, "Unable to save schedule: {0}", exp.toString());
        }
    }

    private void loadHm() {
        this.interfaceHm = new HashMap();
        for (LogInterface iFace:this.interfaces) {
            getInterfaceHm().put(iFace.getLogInterfaceName(), iFace.getLogInterfaceId());
        }
    }

    /**
     * @return the soaLogger
     */
    public Soalogger getSoaLogger() {
        return soaLogger;
    }

    /**
     * @param soaLogger the soaLogger to set
     */
    public void setSoaLogger(Soalogger soaLogger) {
        this.soaLogger = soaLogger;
    }

    /**
     * @return the interfaceId
     */
    public LogInterface getInterfaceId() {
        return interfaceId;
    }

    /**
     * @param interfaceId the interfaceId to set
     */
    public void setInterfaceId(LogInterface interfaceId) {
        this.interfaceId = interfaceId;
    }

    /**
     * @return the scheduleSelection
     */
    public Selection getScheduleSelection() {
        return scheduleSelection;
    }

    /**
     * @param scheduleSelection the scheduleSelection to set
     */
    public void setScheduleSelection(Selection scheduleSelection) {
        this.scheduleSelection = scheduleSelection;
    }

    /**
     * @return the schedules
     */
    public List<LogSched> getSchedules() {
        return schedules;
    }

    /**
     * @param schedules the schedules to set
     */
    public void setSchedules(List<LogSched> schedules) {
        this.schedules = schedules;
    }

    /**
     * @return the selectedSchedule
     */
    public LogSched getSelectedSchedule() {
        return selectedSchedule;
    }

    /**
     * @param selectedSchedule the selectedSchedule to set
     */
    public void setSelectedSchedule(LogSched selectedSchedule) {
        this.selectedSchedule = selectedSchedule;
    }

    /**
     * @return the interfaceHm
     */
    public HashMap getInterfaceHm() {
        return interfaceHm;
    }

    /**
     * @param interfaceHm the interfaceHm to set
     */
    public void setInterfaceHm(HashMap interfaceHm) {
        this.interfaceHm = interfaceHm;
    }

    /**
     * @return the notifications
     */
    public List<LoginterfaceNotification> getNotifications() {
        return notifications;
    }

    /**
     * @param notifications the notifications to set
     */
    public void setNotifications(List<LoginterfaceNotification> notifications) {
        this.notifications = notifications;
    }

    /**
     * @return the notSelection
     */
    public Selection getNotSelection() {
        return notSelection;
    }

    /**
     * @param notSelection the notSelection to set
     */
    public void setNotSelection(Selection notSelection) {
        this.notSelection = notSelection;
    }

    /**
     * @return the selectedNotification
     */
    public LoginterfaceNotification getSelectedNotification() {
        return selectedNotification;
    }

    /**
     * @param selectedNotification the selectedNotification to set
     */
    public void setSelectedNotification(LoginterfaceNotification selectedNotification) {
        this.selectedNotification = selectedNotification;
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
    

}
