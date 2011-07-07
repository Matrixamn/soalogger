package stockmann.com.logging;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

@Local
public interface SoaLoggerLocal {

    LogInterface addLoginterfaceEntry(LogInterface logIntParam);

    List<LogEntry> getInterfaceEntriesBetween(LogInterface li, Date from,
                                              Date to);

    LogInterface getLogInterfaceWithNameAndVersion(String logInterfaceName,
                                                   String version);

    List<LogInterface> getAllLogInterfaces();

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
  
  List<LogInterfaceNotification> getInterfaceNotifications(LogInterface liParam);
  
  int addLogInterfaceNotification (LogInterfaceNotification notificationParam);
  
  int removeLogInterfaceNotification(LogInterfaceNotification notificationParam);
  
  List<LogInterfaceNotification> getInterfaceNotificationsWithEnv(LogInterface liParam);
  
  Number getLogEntryCountWithDateAndInterface(Date fromParam, Date toParam,LogInterface liParam);
  
  LogInterface getLogInterfaceWithNameEnvAndVersion(String logInterfaceName,
                                                             String envParam,
                                                          String version);
  
  LogInterface getLogInterfaceWithId(Integer interfaceIdParam);
}
