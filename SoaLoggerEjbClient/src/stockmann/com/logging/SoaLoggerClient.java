package stockmann.com.logging;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import stockmann.com.logging.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Random;
import java.sql.Timestamp;

import java.util.List;

import javax.naming.NamingEnumeration;

import javax.naming.NameClassPair;
import java.util.StringTokenizer;

public class SoaLoggerClient {
    public static void main(String [] args) {
        try {
            /*String charAr = "default/DummyTest!1.0*soa_a66b1bb4-2ff8-40b3-bf17-0952a03a45be/DummyMediator";
            StringTokenizer st = new StringTokenizer(charAr,"!");
            System.out.println(st.nextToken());
            
            StringTokenizer stt = new StringTokenizer(st.nextToken(),"*");
            String temp = stt.nextToken();
            System.out.println(temp);
            temp = stt.nextToken();
            System.out.println(temp);
            StringTokenizer ts = new StringTokenizer(temp,"/");
            System.out.println(ts.nextToken());
            */
            
            
            Random random = new Random();
            final Context context = getInitialContext();
            
            /*NamingEnumeration ne = context.list("");
            while (ne.hasMore()) {
              NameClassPair nvp = (NameClassPair)ne.next();
                System.out.println("ID : " + nvp.getClassName() + " name " + nvp.getName());
            } */
            
            SoaLogger soaLogger = (SoaLogger)context.lookup("StockmannSoaLogger#stockmann.com.logging.SoaLogger");
            LogInterface li = new LogInterface();
            li.setPreferredLoggingLevel("INFO");
            li.setEnvironmentFlag("DEV");
            li.setLogInterfaceId(random.nextInt());
            li.setMsgMaxLenght(10000);
            li.setLogInterfaceVersion("1.0");
            li.setLogInterfaceName("ProductAswDigtator");
            li.setLogMessageFlag("1");
            li.setIntegrationType("IT-SOA");
            
            ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
            LogEntry le = new LogEntry();
            Date d = new Date();
            Timestamp ts = new Timestamp(d.getTime());
            le.setLogDate(ts);
            le.setInstanceVersion("1.0");
            le.setIntegrationInstanceId(new Integer(random.nextInt()).toString());
            le.setLogEntryId(random.nextInt());
            le.setLogLevel("INFO");
            le.setLogMsg("TESTIA");
            le.setLogPayload("YTRYOKROYJREHOJERHO");
            entries.add(le);
            li.setLogEntryList(entries);
            LogInterface lInt = soaLogger.addLoginterfaceEntry(li);
            System.out.println("Return value : " + lInt.getLogInterfaceName());
            
            String fieldName = null;
            List<LogFields> fields = lInt.getLogFieldsList();
            for (LogFields field: fields) {
              System.out.println("Field : " + field.getLogFieldName());
              fieldName = field.getLogFieldName();
            }
            System.out.println("Lint name: " + lInt.getLogInterfaceName());
            System.out.println("Log entry size: " + lInt.getLogEntryList().size());
            System.out.println("Field size : " + lInt.getLogFieldsList().size());
            List<LogEntry> logEntries = entries;
            for (LogEntry logEntry: logEntries){
              System.out.println("Log entry: " + logEntry.getIntegrationInstanceId());
              ArrayList<EntryValues> values = new ArrayList<EntryValues>();
              EntryValues value = new EntryValues();
              value.setValueId(new Integer(23542));
              value.setEntryValue("Testi arvo");
              
              value.setValueName(fieldName);  
              value.setLogEntry(logEntry);
              values.add(value); 
              logEntry.setEntryValuesList(values);
            }
            int returnVal = soaLogger.addLogEntryValues(logEntries);
            System.out.println("Return value: " + returnVal);
            
            
            /*
            SoaLogger soaLogger = (SoaLogger)context.lookup("StockmannSoaLogger#stockmann.com.logging.SoaLogger");
            
            List<LogInterface> interfaces = soaLogger.getAllLogInterfaces();
            for (LogInterface intrFace:interfaces) {
              System.out.println(intrFace.getLogInterfaceName());
            }
            */
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception : " + ex.toString());
        }
    }

    private static Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x connection details
        env.put( Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory" );
        env.put(Context.SECURITY_CREDENTIALS, "Weblogic10");
        env.put(Context.SECURITY_PRINCIPAL, "weblogic");
        env.put(Context.PROVIDER_URL, "t3://olympix:7003");
        return new InitialContext( env );
    }
}
