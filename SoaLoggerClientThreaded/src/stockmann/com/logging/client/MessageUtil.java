package stockmann.com.logging.client;

import java.util.ArrayList;
import java.util.StringTokenizer;
import stockmann.com.logging.*;
import java.util.Date;
import java.sql.Timestamp;

import java.util.GregorianCalendar;
import java.util.Random;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.stockmann.ws.wrapper.types.LogEntryTo;
import com.stockmann.ws.wrapper.types.LogInterfaceTo;


public class MessageUtil {
    
    private String logLevel = "INFO";
    
    private Random random = new Random();
    public MessageUtil() {
        super();
    }
    
    public String getSecondToken(String val,String delim) {
      StringTokenizer st = new StringTokenizer(val,delim);
      st.nextToken();
      return st.nextToken();
    }
    
  private XMLGregorianCalendar getToday() {
      try {
      GregorianCalendar c = new GregorianCalendar();
      Date today = new Date();
      c.setTime(today);
      XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
      return date2;
      } catch (Exception exp) {
      return null;  
      }
  }
    
    public LogInterfaceTo getInterfaceName(String iName, String iId , String msg, String payload,String env) {
      Date today = new Date();
      LogInterfaceTo li = new LogInterfaceTo();  
      StringTokenizer st = new StringTokenizer(iName,"!");
      StringTokenizer nameSt = new StringTokenizer(st.nextToken(),"/");
      nameSt.nextToken();
      li.setLogInterfaceName(nameSt.nextToken());
      
      StringTokenizer stt = new StringTokenizer(st.nextToken(),"*");
      String temp = stt.nextToken();
      li.setLogInterfaceVersion(temp);
      li.setLogInterfaceId(random.nextInt());
      li.setEnvironmentFlag(env);
      temp = stt.nextToken();
      
      StringTokenizer ts = new StringTokenizer(temp,"/");
      
      ArrayList<LogEntryTo> entries = new ArrayList<LogEntryTo>();
      
      LogEntryTo entry = new LogEntryTo();
      entry.setInstanceVersion(temp);
      entry.setIntegrationInstanceId(iId);
      entry.setLogLevel(logLevel);
      entry.setLogMsg(msg);
      entry.setLogDate(this.getToday());
      entry.setLogEntryId(random.nextInt());
      entry.setLogPayload(payload);
      //entry.setLogInterface(li);
      
      entries.add(entry);
       li.setLogEntryList(entries);
      
      return li;
    }
}
