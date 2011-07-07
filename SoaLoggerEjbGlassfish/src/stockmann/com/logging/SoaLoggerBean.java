package stockmann.com.logging;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.*;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;

@Stateless(name = "SoaLogger", mappedName = "StockmannSoaLogger")
@Remote
@Local
public class SoaLoggerBean implements SoaLogger, SoaLoggerLocal {

    private static Logger logger =
        Logger.getLogger(SoaLoggerBean.class.getName());

    @PersistenceContext(unitName = "SoaLoggerEjb")
    EntityManager em;

    Random keygen = new Random();
    public SoaLoggerBean() {
        this.setLogger();
    }

    private void setLogger() {
        try {
            //Handler fh = new FileHandler("SoaLogger.log");
            Handler hf = new ConsoleHandler();
            logger.addHandler(hf);
            //logger.addHandler(fh);
        } catch (Exception exx) {

        }
    }
    
    
    public int removeLogInterface(LogInterface ifaceParam) {
        try {
           this.removeInterfaceFields(ifaceParam); 
           this.removeEntryFields(ifaceParam);
           this.removeInterfaceSchedules(ifaceParam); 
            
           String query = "SELECT li FROM LogInterface li WHERE li.logInterfaceId = :lid";
           LogInterface li = (LogInterface)em.createQuery(query)
               .setParameter("lid",  ifaceParam.getLogInterfaceId())
               .getSingleResult();
            em.remove(li);
           em.flush(); 
           return 0; 
        } catch (Exception exp)  {
            exp.printStackTrace();
          logger.severe("Exception removing LogInterface: " + exp.toString());
          return -1;
        }
                                     
    }
    
    private void removeInterfaceEntryValues(LogInterface liParam) throws Exception {
      
      String query = "SELECT ev FROM EntryValues ev, LogEntry le, LogInterface li WHERE li.logInterfaceId = :lid "
          + "AND le.logInterface = li AND ev.logEntry = le";
      
      List<EntryValues> values = em.createQuery(query)
          .setParameter("lid", liParam.getLogInterfaceId())
          .getResultList();
      
      for (EntryValues value:values) {
        em.remove(value);
      }
      em.flush();
    }
    
    private void removeInterfaceFields(LogInterface intFaceParam) throws Exception {
        
          String query = "SELECT lf FROM LogFields lf, LogInterface li WHERE li.logInterfaceId = :lid AND lf.logInterface = li";
          List<LogFields> fields = em.createQuery(query)
              .setParameter("lid", intFaceParam.getLogInterfaceId())
              .getResultList();
            for (LogFields field:fields) {
              em.remove(field);
            }
            em.flush();
        
    }
    
    private void removeEntryFields(LogInterface ifaceParam) throws Exception {
        
          String query = "SELECT le FROM LogEntry le, LogInterface li WHERE li.logInterfaceId = :lid AND le.logInterface = li";
          List<LogEntry> entries = em.createQuery(query)
              .setParameter("lid", ifaceParam.getLogInterfaceId())
              .getResultList();
            
            for (LogEntry entry:entries) {
              List<EntryValues> values = entry.getEntryValuesList();
                for (EntryValues value : values) {
                  em.remove(value);
                }
                em.remove(entry);
            }
            em.flush();
        
    }
    
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) 
    public int addLogInterfaceNotification (LogInterfaceNotification notificationParam) {
        try {
          
            if (notificationParam.getLogInterfaceNotificationId() != null) {
              em.merge(notificationParam);
            } else {
              notificationParam.setLogInterfaceNotificationId(keygen.nextInt());
              em.persist(notificationParam);
            }
          
          return 0;
        } catch (Exception exp) {
          logger.severe("Exception saving LogInterfaceNotification : " + exp.toString());  
          return -1;
        }
    }
    
    
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) 
    public int addLogEntryValues(List<LogEntry> entries) {
        try {
          
            for(LogEntry entry:entries) {
                //em.merge(entry);
                for(EntryValues value:entry.getEntryValuesList()) {
                  int retval = this.addLogEntryValue(value, entry);
                    if (retval != 0) {
                      throw new Exception ("");
                    }
                }
            }
          
          return 0;
        } catch (Exception exp) {
          logger.severe("Exception in addLogEntryValues : "  + exp.toString());
          return -1;
        }
    }
  
  public List<LogInterfaceNotification> getInterfaceNotifications(LogInterface liParam) {
      try {
        
        String query = "SELECT lin FROM LogInterfaceNotification lin WHERE lin.logInterfaceId = :lid";
        List<LogInterfaceNotification> notifications = em.createQuery(query)
            .setParameter("lid", liParam.getLogInterfaceId())
            .getResultList();
          
        return notifications;  
        
      } catch (Exception exp) {
        logger.severe("Exception getting interface notifications : " + exp.toString());
        return null;
      }
  }
  
  public List<LogInterfaceNotification> getInterfaceNotificationsWithEnv(LogInterface liParam) {
      try {
        
        String query = "SELECT lin FROM LogInterfaceNotification lin WHERE lin.logInterfaceId = :lid "
            + "AND lin.interfaceEnv = :env";
        List<LogInterfaceNotification> notifications = em.createQuery(query)
            .setParameter("lid", liParam.getLogInterfaceId())
            .setParameter("env", liParam.getEnvironmentFlag().trim())
            .getResultList();
          
        return notifications;  
        
      } catch (Exception exp) {
        logger.severe("Exception getting interface notifications : " + exp.toString());
        return null;
      }
  }
  

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public LogInterface addLoginterfaceEntry(LogInterface logIntParam) {
        try {
            List<LogEntry> logEntries = logIntParam.getLogEntryList();
            boolean checkFields = false;
            LogInterface li = null;
            //Check if LogInterface exists, if it exists do nothing to that LogInterface
            try {
                li = this.getLogInterfaceWithNameEnvAndVersion(logIntParam.getLogInterfaceName(), logIntParam.getEnvironmentFlag(), logIntParam.getLogInterfaceVersion());
 
            } catch (Exception exw) {
            }
            if (li == null) {

                logIntParam.setLogEntryList(null);
                this.addLogInterface(logIntParam);

                li = logIntParam;
                checkFields = false;


            } else {
                checkFields = true;
            }


            //Check if interface has fields defined
            if (checkFields) {

                try {
                    for (LogFields field : li.getLogFieldsList()) {
                        field.getLogFieldId();
                    }
                } catch (Exception exxx) {


                }
            }
            //Check if incoming object has child LogEntry objects
            //loop those through and insert to db.
            if (logEntries != null) {
                for (LogEntry entry : logEntries) {


                    this.addLogEntry(entry, li);

                    li.addLogEntry(entry);
                }
            }


            return li;
        } catch (Exception exp) {
            logger.severe("Exception in addLoginterfaceEntry : " +
                          exp.toString());

            return null;
        }
    }


    public LogEntry getLogEventWithValue(String valueNameParam,
                                         String valueParam) {
        try {
            String query =
                "SELECT ev FROM EntryValues ev WHERE ev.valueName = :valName AND ev.value = :val";
            EntryValues evv =
                (EntryValues)em.createQuery(query).setParameter("valName",
                                                                valueNameParam).setParameter("val",
                                                                                             valueParam).getSingleResult();
            LogEntry le = evv.getLogEntry();
            ArrayList<EntryValues> vals = new ArrayList<EntryValues>();
            vals.add(evv);
            le.setEntryValuesList(vals);
            return le;
        } catch (Exception exp) {

            return null;
        }
    }
    
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<LogSched> getAllLogSchedules() {
        try {
          String query  = "SELECT ls FROM LogSched ls";
            
          List<LogSched> schedules = em.createQuery(query)
              .getResultList();
          
            for (LogSched ls:schedules) {
              LogInterface li = this.getLogInterfaceWithId(ls.getLogInterfaceId());
              ls.setLogInterface(li);  
            }
          
          return schedules;
        } catch (Exception exp) {
          logger.severe("Exception getting log schedules : " + exp.toString());
          return null;
        }
    }
    
    public int removeLogSched(LogSched schedParam) {
        try {
          LogSched sched = em.merge(schedParam);
          em.remove(sched);  
          em.flush();  
          return 0;
        } catch (Exception exp) {
          logger.severe("Exception removing LogSched : " + exp.toString());
          return -1;
        }
    }
    
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int updateOrAddLogSchedule(LogSched schedParam) {
        try {
            Date today = new Date();
            if (schedParam.getLogSchedId() == null ) {
              schedParam.setLogSchedId(new Integer(keygen.nextInt()));
              schedParam.setLogDate(new Timestamp(today.getTime()));
              em.persist(schedParam);
            } else {
              LogSched sched = em.merge(schedParam);
              sched.setLogDate(new Timestamp(today.getTime()));  
            }
            
          em.flush();
          
          return 0;
        } catch (Exception exp) {
          logger.severe("Exception persisting LogSched: "  + exp.toString());
          return -1;
        }
    }
    
    public List<LogEntry> getLogEntriesWithInstanceid(String instanceId) {
        try {
          String query = "SELECT le FROM LogEntry le WHERE le.integrationInstanceId = :iid";
          List<LogEntry> entries = em.createQuery(query)
              .setParameter("iid", instanceId)
              .getResultList();
          
            for (LogEntry entry:entries) {
              List<EntryValues> values = entry.getEntryValuesList();
                for (EntryValues value:values) {
                  value.getValueId();
                }
            }
            
          return entries;  
        } catch (Exception exp) {
          return null;
        }
    }
    
    public int removeLogEntriesTo(Date dateTo, LogInterface ifaceParam) {
        try {
          
          String query = "SELECT le FROM LogEntry le, LogInterface li WHERE li.logInterfaceId = :lid AND le.logInterface = li "
              + "AND le.logDate < :dateto";
            Timestamp timeTo = new Timestamp(dateTo.getTime());
            List<LogEntry> entries = em.createQuery(query)
                .setParameter("lid", ifaceParam.getLogInterfaceId())
                .setParameter("dateto", timeTo, TemporalType.TIMESTAMP)
                .getResultList();
            
            for (LogEntry entry:entries) {
              em.remove(entry);
            }
           em.flush();
          return 0;
        } catch (Exception exp) {
          return 1;
        }
    }
    
    
  public List<LogEntry> getLogEntriesWithEntryValueAndDateAndEnv(EntryValues value,
                                                           Date fromDate,
                                                           Date toDate,
                                                           String env) {
      try {
          String query =
              "SELECT le FROM LogEntry le, EntryValues ev, LogInterface li WHERE ev.valueName = :valName AND ev.entryValue = :entryVal " +
              "AND ev.logEntry = le AND li.environmentFlag = :envflag AND le.logInterface = li " +
              "AND le.logDate BETWEEN :from AND :to";
          Timestamp from = new Timestamp(fromDate.getTime());
          Timestamp to = new Timestamp(toDate.getTime());
          List<LogEntry> entries =
              em.createQuery(query).setParameter("valName",value.getValueName().trim())
              .setParameter("entryVal",value.getEntryValue().trim())
              .setParameter("envflag", env.trim())
              .setParameter("from",from,TemporalType.TIMESTAMP)
              .setParameter("to",to,TemporalType.TIMESTAMP).getResultList();

          for (LogEntry entry : entries) {
              List<EntryValues> evv = entry.getEntryValuesList();
              for (EntryValues ev : evv) {
                  ev.getValueId();
              }
          }
          return entries;
      } catch (Exception exp) {

          return null;
      }
  }

    public List<LogEntry> getLogEntriesWithEntryValueAndDate(EntryValues value,
                                                             Date fromDate,
                                                             Date toDate) {
        try {
            String query =
                "SELECT le FROM LogEntry le, EntryValues ev WHERE ev.valueName = :valName AND ev.entryValue = :entryVal " +
                "AND ev.logEntry = le AND le.logDate BETWEEN :from AND :to";
            Timestamp from = new Timestamp(fromDate.getTime());
            Timestamp to = new Timestamp(toDate.getTime());
            List<LogEntry> entries =
                em.createQuery(query).setParameter("valName",
                                                   value.getValueName().trim()).setParameter("entryVal",
                                                                                             value.getEntryValue().trim()).setParameter("from",
                                                                                                                                        from,
                                                                                                                                        TemporalType.TIMESTAMP).setParameter("to",
                                                                                                                                                                             to,
                                                                                                                                                                             TemporalType.TIMESTAMP).getResultList();

            for (LogEntry entry : entries) {
                List<EntryValues> evv = entry.getEntryValuesList();
                for (EntryValues ev : evv) {
                    ev.getValueId();
                }
            }
            return entries;
        } catch (Exception exp) {

            return null;
        }
    }

    public List<EntryValues> getEntryValues(LogEntry entry) {
        try {
            String query =
                "SELECT val FROM EntryValues val, LogEntry log WHERE log.logEntryId = :leid AND val.logEntry = log";
            List<EntryValues> values =
                em.createQuery(query).setParameter("leid",
                                                   entry.getLogEntryId()).getResultList();

            return values;
        } catch (Exception exp) {
            return null;
        }
    }
    
    
  public List<LogEntry> getInterfaceEntriesBetweenWithEnvLoglevel(LogInterface li,
                                                   String level,
                                                   Date fromParam,
                                                   Date toParam) {
      try {

          String query =
              "SELECT le FROM LogEntry le, LogInterface li WHERE li.logInterfaceId = :liid " +
              "AND le.logInterface = li AND le.logLevel = :level AND le.logDate BETWEEN :from AND :to";
          Timestamp from = new Timestamp(fromParam.getTime());
          Timestamp to = new Timestamp(toParam.getTime());
          List<LogEntry> entries =
              em.createQuery(query).setParameter("liid", li.getLogInterfaceId())
              .setParameter("level", level)
              .setParameter("from",from,TemporalType.TIMESTAMP)
              .setParameter("to",to,TemporalType.TIMESTAMP).getResultList();

          for (LogEntry entry : entries) {
              List<EntryValues> evv = entry.getEntryValuesList();
              for (EntryValues ev : evv) {
                  ev.getValueId();
              }
          }

          return entries;


      } catch (Exception exp) {

          return null;
      }
  }  
    
    
  public List<LogEntry> getInterfaceEntriesBetweenWithEnv(LogInterface li,
                                                   Date fromParam,
                                                   Date toParam) {
      try {

          String query =
              "SELECT le FROM LogEntry le, LogInterface li WHERE li.logInterfaceId = :liid " +
              "AND le.logInterface = li AND le.logDate BETWEEN :from AND :to";
          Timestamp from = new Timestamp(fromParam.getTime());
          Timestamp to = new Timestamp(toParam.getTime());
          List<LogEntry> entries =
              em.createQuery(query).setParameter("liid", li.getLogInterfaceId())
              .setParameter("from",from,TemporalType.TIMESTAMP)
              .setParameter("to",to,TemporalType.TIMESTAMP).getResultList();

          for (LogEntry entry : entries) {
              List<EntryValues> evv = entry.getEntryValuesList();
              for (EntryValues ev : evv) {
                  ev.getValueId();
              }
          }

          return entries;


      } catch (Exception exp) {

          return null;
      }
  }

    public List<LogEntry> getInterfaceEntriesBetween(LogInterface li,
                                                     Date fromParam,
                                                     Date toParam) {
        try {

            String query =
                "SELECT le FROM LogEntry le, LogInterface li WHERE li.logInterfaceId = :liid " +
                "AND le.logInterface = li AND le.logDate BETWEEN :from AND :to";
            Timestamp from = new Timestamp(fromParam.getTime());
            Timestamp to = new Timestamp(toParam.getTime());
            List<LogEntry> entries =
                em.createQuery(query).setParameter("liid", li.getLogInterfaceId()).setParameter("from",
                                                                                                from,
                                                                                                TemporalType.TIMESTAMP).setParameter("to",
                                                                                                                                     to,
                                                                                                                                     TemporalType.TIMESTAMP).getResultList();

            for (LogEntry entry : entries) {
                List<EntryValues> evv = entry.getEntryValuesList();
                for (EntryValues ev : evv) {
                    ev.getValueId();
                }
            }

            return entries;


        } catch (Exception exp) {

            return null;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<LogFields> getInterfaceFields(LogInterface liParam) {
        try {
            String query =
                "SELECT lf FROM LogFields lf, LogInterface li WHERE li.logInterfaceId = :liId AND lf.logInterface = li";
            return em.createQuery(query).setParameter("liId",
                                                      liParam.getLogInterfaceId()).getResultList();
        } catch (Exception exp) {

            return null;
        }
    }

    private LogEntry getLogEntryWithId(LogEntry logEntryParam) {
        try {
            String query =
                "SELECT le FROM LogEntry le WHERE le.logEntryId = :leId";
            return (LogEntry)em.createQuery(query).setParameter("leId",
                                                                logEntryParam.getLogEntryId()).getSingleResult();
        } catch (Exception exp) {

            return null;
        }
    }
    
  public LogInterface getLogInterfaceWithNameEnvAndVersion(String logInterfaceName,
                                                           String envParam,
                                                        String version) {
      try {


          String query =
              "SELECT li FROM LogInterface li WHERE li.logInterfaceName = :liname AND li.logInterfaceVersion = :liversion " +
              "AND li.environmentFlag = :env";
          LogInterface li =
              (LogInterface)em.createQuery(query).setParameter("liname",logInterfaceName)
              .setParameter("liversion",version)
              .setParameter("env", envParam)
              .getSingleResult();

          return li;

      } catch (Exception exp) {


          return null;
      }

  }
  
    public LogInterface getLogInterfaceWithNameAndVersion(String logInterfaceName,
                                                          String version) {
        try {


            String query =
                "SELECT li FROM LogInterface li WHERE li.logInterfaceName = :liname AND li.logInterfaceVersion = :liversion";
            LogInterface li =
                (LogInterface)em.createQuery(query).setParameter("liname",
                                                                 logInterfaceName).setParameter("liversion",
                                                                                                version).getSingleResult();

            return li;

        } catch (Exception exp) {


            return null;
        }

    }

    public int addLogField(LogFields logFieldParam, LogInterface logIntParam,
                           boolean updateFlag) {
        try {
            //em.merge(logIntParam);

            if (updateFlag) {
                em.merge(logFieldParam);
            } else {
                em.persist(logFieldParam);
            }
            logFieldParam.setLogInterface(logIntParam);
            return 0;
        } catch (Exception exp) {

            logger.severe("Exception in addLogField : " + exp.toString());
            return -1;
        }
    }


    private int addLogEntry(LogEntry logEntryParam, LogInterface liParam) {
        try {

            //em.merge(liParam);
            logEntryParam.setLogInterface(liParam);

            em.persist(logEntryParam);

            return 0;
        } catch (Exception exp) {

            logger.severe("Exception in addLogEntry : " + exp.toString());
            return -1;
        }
    }

    private int addLogEntryValue(EntryValues evParam, LogEntry leParam) {
        try {
            //em.merge(leParam);

            evParam.setLogEntry(leParam);

            em.persist(evParam);

            return 0;
        } catch (Exception exp) {
            logger.severe("Exception in addLogEntryValue : " + exp.toString());
            return -1;
        }
    }
    
  public LogInterface getLogInterfaceWithId(Integer interfaceIdParam) {
      try {

          String query = "SELECT li FROM LogInterface li WHERE li.logInterfaceId = :lid";
          return (LogInterface)em.createQuery(query)
              .setParameter("lid", interfaceIdParam)
              .getSingleResult();

      } catch (Exception exxxx) {
          return null;
      }

  }

    public List<LogInterface> getAllLogInterfaces() {
        try {

            String query = "SELECT li FROM LogInterface li";
            return em.createQuery(query).getResultList();

        } catch (Exception exxxx) {
            return null;
        }

    }
    
    public List<LogInterface> getInterfaceErrors() {
      try {
        List<LogInterface> ints = this.getAllLogInterfaces();
        ArrayList<LogInterface> interfaces = new ArrayList<LogInterface>();
        Date today = new Date();
        for (LogInterface li: ints) {
          int errorCounter = 0;
          List<LogEntry> entries = this.getLogEntriesWithDateAndInterface(today, li);
          for (LogEntry le: entries) {
            if (le.getLogLevel().trim().equals("ERROR")) {
              errorCounter++;
            }
          }
          if (errorCounter > 0 ) {
             li.setAddField(Integer.toString(errorCounter));
             interfaces.add(li);
          }
        }
        return interfaces;
      } catch (Exception exp) {
        return null;
      }
    }
    
  public Number getLogEntryCountWithDateAndInterface(Date fromParam, Date toParam,LogInterface liParam) {
      try {
        
        Timestamp from = new Timestamp(fromParam.getTime());
        
        Timestamp to = new Timestamp(toParam.getTime());
        String query = "SELECT COUNT(le.logEntryId) FROM LogEntry le, LogInterface li WHERE li.logInterfaceId = :lid AND le.logInterface = li AND le.logDate BETWEEN :from AND :to";
        Number num = (Number)em.createQuery(query)
          .setParameter("lid", liParam.getLogInterfaceId())
          .setParameter("from", from, TemporalType.TIMESTAMP)
          .setParameter("to", to, TemporalType.TIMESTAMP)
          .getSingleResult();
        
        return num;
      } catch (Exception exp) {
        logger.severe("Exception in getLogEntriesWithDateAndInterface : " + exp.toString());
        return null;
      }
  }
    
    
    private List<LogEntry> getLogEntriesWithDateAndInterface(Date date,LogInterface liParam) {
        try {
          Calendar curcal = Calendar.getInstance();
          curcal.setTime(date);
          curcal.add(Calendar.DATE, -1);
          Timestamp from = new Timestamp(curcal.getTime().getTime());
          curcal.setTime(date);
          curcal.add(Calendar.DATE, 1);
          Timestamp to = new Timestamp(curcal.getTime().getTime());
          String query = "SELECT le FROM LogEntry le, LogInterface li WHERE li.logInterfaceId = :lid AND le.logInterface = li AND le.logDate BETWEEN :from AND :to";
          List<LogEntry> entries = em.createQuery(query)
            .setParameter("lid", liParam.getLogInterfaceId())
            .setParameter("from", from, TemporalType.TIMESTAMP)
            .setParameter("to", to, TemporalType.TIMESTAMP)
            .getResultList();
          
          return entries;
        } catch (Exception exp) {
          logger.severe("Exception in getLogEntriesWithDateAndInterface : " + exp.toString());
          return null;
        }
    }
    
    
  public List<LogInterface> getAllLogInterfacesWithEnv(String env) {
      try {

          String query = "SELECT li FROM LogInterface li WHERE li.environmentFlag = :envFlag";
          return em.createQuery(query)
              .setParameter("envFlag", env.trim())
              .getResultList();

      } catch (Exception exxxx) {
          return null;
      }

  }

    public int updateLogInterface(LogInterface logIntParam) {
        try {
            em.merge(logIntParam);
            return 0;
        } catch (Exception exp) {
            logger.severe("Exception in updateLogInterface : " +
                          exp.toString());
            return -1;
        }
    }
    
    private void removeEntryValues(LogEntry leParam) {
        try {
          String query = "SELECT ev FROM EntryValues ev, LogEntry le WHERE le.logEntryId = :leid AND ev.logEntry = le";
          List<EntryValues> values = em.createQuery(query)
              .setParameter("leid", leParam.getLogEntryId())
              .getResultList();
            
            for (EntryValues value:values) {
              em.remove(value);
            }
            em.flush();
        } catch (Exception exp) {
          
        }
    }
    
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int removeLogSchedule(LogSched lsParam) {
        try {
          LogSched ls = em.merge(lsParam);
          em.remove(ls);
          em.flush();
          
          return 0;
        } catch (Exception exp) {
          logger.severe("Exception in removeLogSchedule : " + exp.toString());
          return -1;
        }
          
    }
    
    private int removeInterfaceSchedules(LogInterface li) {
        try {
          String query = "SELECT ls FROM LogSched ls WHERE ls.logInterfaceId = :lid";
          List<LogSched> schedules = em.createQuery(query)
              .setParameter("lid", li.getLogInterfaceId())
              .getResultList();
            
            for (LogSched ls: schedules) {
              em.remove(ls);
            }
            em.flush();
            return 0;
        } catch (Exception exp) {
          logger.severe("Exception in removeInterfaceSchedules : " + exp.toString());
          return -1;
        }
    }
    
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int removeLogInterfaceNotification(LogInterfaceNotification notificationParam) {
        try {
          LogInterfaceNotification lin = em.merge(notificationParam);
          em.remove(lin);  
          return 0;
        } catch (Exception exp) {
          logger.severe("Exception removing log interface notification : " + exp.toString());
          return -1;
        }
    }
    
    public int removeLogField(LogFields fieldParam) {
        try {
            String query = "SELECT lf FROM LogFields lf WHERE lf.logFieldId = :lfid";
            LogFields field = (LogFields)em.createQuery(query)
                .setParameter("lfid", fieldParam.getLogFieldId())
                .getSingleResult();
            em.remove(field);
            return 0;
        } catch (Exception exp) {
            logger.severe("Exception in removeLogField : " + exp.toString());
            return -1;
        }
    }
  
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int addLogInterface(LogInterface logIntParam) {
        try {

            em.persist(logIntParam);
            em.flush();
            return 0;
        } catch (Exception exp) {
            logger.severe("Exception in addLogInterface : " + exp.toString());

            return -1;
        }
    }
}
