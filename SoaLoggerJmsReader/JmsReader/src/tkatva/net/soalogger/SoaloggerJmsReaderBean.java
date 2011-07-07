package tkatva.net.soalogger;

import java.io.File;

import javax.ejb.MessageDriven;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.ConsoleHandler;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import java.io.FileInputStream;
import javax.jms.ObjectMessage;
import tkatva.net.soalogger.msgutil.MsgUtil;


import org.w3c.dom.*;
import tkatva.net.soalogger.entity.*;
import tkatva.net.soalogger.session.*;
import katva.net.logging.SoaLoggerJmsPojo;

@MessageDriven(mappedName = "jms/soaloggerqueue")
public class SoaloggerJmsReaderBean implements MessageListener {
    
    @EJB private SoaloggerLocal soalogger;
    private Random random = new Random();
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private MsgUtil msgUtil = new MsgUtil();
    private Properties props = new Properties();
    private String env = null;
    private String logMsg = null;
    
    private void setLogger() {
        try {
          Handler hf = new ConsoleHandler();
        logger.addHandler(hf);  
            
        } catch (Exception exx) {
          
        }
    }
    
    private void loadProperties() {
          try {
             
              String path = new java.io.File(".").getCanonicalPath();
             props.load(new FileInputStream(path + "/lib/env.properties"));
            //props.load(this.getClass().getClassLoader().getResourceAsStream("env.properties"));
              
              this.env = props.getProperty("env_name");
              this.logMsg = props.getProperty("default_log_msg");
          } catch(Exception exp) {
            this.logger.severe("PROPS: Exception reading properties: " + exp.toString());
          }
      }
    
    private Timestamp getToday() {
        try {
       
        Date today = new Date();
        Timestamp date2 = new Timestamp(today.getTime());
        return date2;
        } catch (Exception exp) {
        return null;  
        }
    }
      
    
    public void onMessage(Message message) {
        this.setLogger();
        this.loadProperties();
        try {
        message.acknowledge();
        } catch (Exception exp) {
            logger.severe("Error acknowledging...");
        }
        
        
        try {
            ObjectMessage om = null;
            try {
            om = (ObjectMessage)message;
            } catch (Exception exp) {
                logger.severe("Exception casting to ObjectMessage : " + exp.toString());
            }
            
            SoaLoggerJmsPojo jmsPojo = null;
            try {
            jmsPojo = (SoaLoggerJmsPojo)om.getObject();
            } catch (Exception exx) {
                logger.severe("Exception casting ObjectMessage object to SoaLoggerJmsPojo : " + exx.toString());
            }
            
            
            LogInterface intFace = new LogInterface();
            intFace.setLogInterfaceName(jmsPojo.getInterfaceName());
            intFace.setLogInterfaceVersion(jmsPojo.getInterfaceVersion());
            intFace.setLogMessageFlag("N");
            
            if (intFace.getEnvironmentFlag() == null) {
            intFace.setEnvironmentFlag(env);
            } 
            
            ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
            
            LogEntry le = new LogEntry();
            le.setLogLevel(jmsPojo.getLogLevel());
            le.setLogInterface(intFace);
            le.setInstanceVersion(jmsPojo.getInstanceId());
            le.setLogDate(this.getToday());
            le.setLogMsg(logMsg);
            Long eId = new Long(random.nextInt());
            le.setLogEntryId(eId);
            if (jmsPojo.getMsgPayload().length() < 2000) {
                le.setLogPayload(jmsPojo.getMsgPayload());
            }
            le.setLogInterface(intFace);
            entries.add(le);
            
            intFace.setLogEntryList(entries);
            
            intFace = soalogger.addLoginterfaceEntry(intFace);
            
            Document xmlDoc = msgUtil.parseInputString(jmsPojo.getMsgPayload());
            
            List<EntryValues> values = this.getInterfaceEntryValues(intFace.getLogFieldsList(), xmlDoc.getDocumentElement());
            
            le.setEntryValuesList(values);
            
            soalogger.addLogEntryValues(entries);
             
        } catch(Exception exp) {
            logger.severe("Exception occurred while saving LogInterface: " + exp.toString());
        }
        logger.severe("Message received: " + message.getClass().getName());
    }
    
    public static List<String> getStringFromElement(Element e) {
         NodeList childList = e.getChildNodes();
         ArrayList<String> elementValues = new ArrayList<String>();
         for (int i = 0; i < childList.getLength(); i++) {
           Node noodi = childList.item(i);
               if (noodi instanceof CharacterData) {
               CharacterData cd = (CharacterData) noodi;
               elementValues.add(cd.getData());
               }
             }
         return elementValues;
       } 
    
    public List<EntryValues> getInterfaceEntryValues(List<LogFields> fields, Element payloadElement) {
        try {
            
            ArrayList<EntryValues> values = new ArrayList<EntryValues>();
            for (LogFields field:fields) {
                NodeList nl = payloadElement.getElementsByTagNameNS("*", field.getElementName());
                                           
                  for (int i = 0; i < nl.getLength(); i++) {
                        Element valEl = (Element)nl.item(i);
                                               
                        List<String> elementValues = this.getStringFromElement(valEl);  
                                    for (String elementValue:elementValues) {
                                      EntryValues value = new EntryValues();
                                      value.setValueName(field.getLogFieldName());
                                      value.setValueId(random.nextLong()); 
                                      value.setEntryValue(elementValue);  
                                      values.add(value);
                                    }
                                               
                                                                               }
            }
            
            return values;
        } catch (Exception exp) {
            return null;
        }
        
    }
}
