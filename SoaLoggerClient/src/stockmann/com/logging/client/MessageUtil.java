package stockmann.com.logging.client;

import java.util.ArrayList;
import java.util.StringTokenizer;
import stockmann.com.logging.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Random;


public class MessageUtil {
    
    private String logLevel = "INFO";
    private String env = "DEV";
    private Random random = new Random();
    public MessageUtil() {
        super();
    }
    
    public String getSecondToken(String val,String delim) {
      StringTokenizer st = new StringTokenizer(val,delim);
      st.nextToken();
      return st.nextToken();
    }
    
    public LogInterface getInterfaceName(String iName, String iId , String msg, String payload) {
      Date today = new Date();
      LogInterface li = new LogInterface();  
      StringTokenizer st = new StringTokenizer(iName,"!");
      
      li.setLogInterfaceName(st.nextToken());
      
      StringTokenizer stt = new StringTokenizer(st.nextToken(),"*");
      String temp = stt.nextToken();
      li.setLogInterfaceVersion(temp);
      li.setLogInterfaceId(random.nextInt());
      li.setEnvironmentFlag(env);
      temp = stt.nextToken();
      
      StringTokenizer ts = new StringTokenizer(temp,"/");
      
      ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
      
      LogEntry entry = new LogEntry();
      entry.setInstanceVersion(temp);
      entry.setIntegrationInstanceId(iId);
      entry.setLogLevel(logLevel);
      entry.setLogMsg(msg);
      entry.setLogDate(new Timestamp(today.getTime()));
      entry.setLogEntryId(random.nextInt());
      entry.setLogPayload(payload);
      entry.setLogInterface(li);
      
      entries.add(entry);
       li.setLogEntryList(entries);
      
      return li;
    }
}
