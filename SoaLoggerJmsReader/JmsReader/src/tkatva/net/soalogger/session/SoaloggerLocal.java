package tkatva.net.soalogger.session;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import tkatva.net.soalogger.entity.EntryValues;
import tkatva.net.soalogger.entity.IntUsr;
import tkatva.net.soalogger.entity.InterfaceUsrGroups;
import tkatva.net.soalogger.entity.LogEntry;
import tkatva.net.soalogger.entity.LogFields;
import tkatva.net.soalogger.entity.LogInterface;
import tkatva.net.soalogger.entity.LogSched;
import tkatva.net.soalogger.entity.LoginterfaceNotification;
import tkatva.net.soalogger.entity.UserGroup;
import tkatva.net.soalogger.entity.UsrGrpUsr;

@Local
public interface SoaloggerLocal {
    LogInterface addLoginterfaceEntry(LogInterface logIntParam);

        List<LogEntry> getInterfaceEntriesBetween(LogInterface li, Date from,
                                                  Date to);

        LogInterface getLogInterfaceWithNameAndVersion(String logInterfaceName,
                                                       String version);

        List<LogInterface> getAllLogInterfaces();

        int addLogInterface(LogInterface logIntParam);

        int addLogField(LogFields logFieldParam, LogInterface logIntParam,
                        boolean updateFlag);

        List<LogFields> getInterfaceFields(LogInterface liParam);

        int addLogEntryValues(List<LogEntry> entries);

        int updateLogInterface(LogInterface logIntParam);

        int removeLogField(LogFields fieldParam);

        List<LogEntry> getLogEntriesWithEntryValueAndDate(EntryValues value,
                                                          Date fromDate,
                                                          Date toDate);

        List<EntryValues> getEntryValues(LogEntry entry);
        
      List<LogEntry> getLogEntriesWithEntryValueAndDateAndEnv(EntryValues value,
                                                                 Date fromDate,
                                                                 Date toDate,
                                                                 String env);
      
      List<LogInterface> getAllLogInterfacesWithEnv(String env);
      
      List<LogEntry> getLogEntriesWithInstanceid(String instanceId);
      
      List<LogEntry> getInterfaceEntriesBetweenWithEnvLoglevel(LogInterface li,
                                                         String level,
                                                         Date fromParam,
                                                         Date toParam);
      
      List<LogInterface> getInterfaceErrors();
      
      int removeLogInterface(LogInterface ifaceParam);
      
      List<LogSched> getAllLogSchedules();
      
      int removeLogSched(LogSched schedParam);
      
      int updateOrAddLogSchedule(LogSched schedParam);
      
      int removeLogEntriesTo(Date dateTo, LogInterface ifaceParam);
      
      int removeLogSchedule(LogSched lsParam);
      
      List<LoginterfaceNotification> getInterfaceNotifications(LogInterface liParam);
      
      int addLogInterfaceNotification (LoginterfaceNotification notificationParam);
      
      int removeLogInterfaceNotification(LoginterfaceNotification notificationParam);
      
      List<LoginterfaceNotification> getInterfaceNotificationsWithEnv(LogInterface liParam);
      
      Number getLogEntryCountWithDateAndInterface(Date fromParam, Date toParam,LogInterface liParam);
      
      LogInterface getLogInterfaceWithNameEnvAndVersion(String logInterfaceName,
                                                                 String envParam,
                                                              String version);
      
      LogInterface getLogInterfaceWithId(Integer interfaceIdParam);
}
