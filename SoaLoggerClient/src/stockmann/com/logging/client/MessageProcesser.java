package stockmann.com.logging.client;

import java.io.StringWriter;

import java.util.List;
import java.util.logging.Logger;
import oracle.tip.mediator.common.api.CalloutMediatorMessage;
import oracle.tip.mediator.common.api.IJavaCallout;
import oracle.tip.mediator.common.api.MediatorCalloutException;
import oracle.tip.mediator.infra.exception.MediatorException;
import oracle.tip.mediator.metadata.CaseType;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Handler;

import stockmann.com.logging.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.Random;

import java.util.logging.ConsoleHandler;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class MessageProcesser extends Thread {
    
    
  private CalloutMediatorMessage calloutMediatorMessage;
  private SoaLogger soaLogger = null;
  MessageUtil msgUtil = new MessageUtil();
  Random random = new Random();
  FileUtil fileutil;
  boolean reentrant;
  String logImsg;
  LogInterface li;
  LogEntry entry;
  
  public  Logger logger = Logger.getLogger(MessageProcesser.class.getName());
  
  private void getLogger() {
      try {
        final Context context = getInitialContext();
        soaLogger = (SoaLogger)context.lookup("StockmannSoaLogger#stockmann.com.logging.SoaLogger");
          
      } catch (Exception exp) {
        logger.severe("Exception getting logger: " + exp.toString());
      }
  }
  
  private void setLogger() {
      try {
        Handler hf = new ConsoleHandler();
      logger.addHandler(hf);    
      } catch (Exception exx) {
        
      }
  }
  
  public void initializeThread() {
    fileutil = new FileUtil();
    this.setLogger();
    this.getLogger();
  }
  
  public void addLogEntry() {
    String msgId = null;
    
    try {
    msgId = getCalloutMediatorMessage().getId();
    } catch (MediatorException mexp) {}
    
    if (!reentrant) {
      logImsg = "Started";
    } else {
      logImsg = "Stopped";
    }
    
    LogInterface tempLi = msgUtil.getInterfaceName(getCalloutMediatorMessage().getComponentDN(),msgId, logImsg, "");
    
    entry = new LogEntry();
    entry.setLogEntryId(tempLi.getLogEntryList().get(0).getLogEntryId());
    entry.setLogDate(tempLi.getLogEntryList().get(0).getLogDate());
    entry.setInstanceVersion(tempLi.getLogEntryList().get(0).getInstanceVersion());
    entry.setLogLevel(tempLi.getLogEntryList().get(0).getLogLevel());
    entry.setIntegrationInstanceId(tempLi.getLogEntryList().get(0).getIntegrationInstanceId());
    entry.setLogMsg(tempLi.getLogEntryList().get(0).getLogMsg());
    entry.setLogInterface(tempLi.getLogEntryList().get(0).getLogInterface());
    entry.setLogPayload(tempLi.getLogEntryList().get(0).getLogPayload());
    li = soaLogger.addLoginterfaceEntry(tempLi);
  }
    
  public void run() {
      
      
      if (!reentrant) {
      
      try {
        Map payload = getCalloutMediatorMessage().getPayload();
        String key = null;
        Iterator it = payload.keySet().iterator();
          while (it.hasNext()) {
              key = (String)it.next();
          }
        Element payloadElement = (Element)payload.get(key);
        
        
        
        
          try {
              if (li.getLogMessageFlag() != null && li.getLogMessageFlag().trim().equals("Y")) {  
            String msg = this.xmlToString(payloadElement);
            int retval = fileutil.createLoggingMsg(msgUtil.getSecondToken(li.getLogInterfaceName(), "/"), li.getEnvironmentFlag(), entry.getIntegrationInstanceId(), msg);
            if (retval != 0) {
              logger.severe("Writing message to disk did not succeed: " + retval);
            }
              }
          } catch (Exception expp) {
              logger.severe("Exception writing message to disk : " + expp.toString());
          }
      List<LogFields> lfs = null;
          try {    
        lfs = soaLogger.getInterfaceFields(li);
        if (lfs != null) {
            
            ArrayList<EntryValues> values = new ArrayList<EntryValues>();
            for (LogFields field:lfs) {
              EntryValues value = new EntryValues();
                              value.setLogEntry(entry);
                              value.setValueName(field.getLogFieldName());
                              value.setValueId(random.nextInt());
                              NodeList nl = payloadElement.getElementsByTagName(field.getElementName());
                                for (int i = 0; i < nl.getLength(); i++) {
                                  Element valEl = (Element)nl.item(i);
                                  value.setEntryValue(MessageProcesser.getStringFromElement(valEl));
                                }
                               values.add(value);  
            }
            entry.setEntryValuesList(values);
            ArrayList<LogEntry> logEntries = new ArrayList<LogEntry>();
            logEntries.add(entry);
            
            soaLogger.addLogEntryValues(logEntries);
          
      }
          } catch (Exception ex) {
              logger.severe("Exception occurred adding logfield value: " + ex.toString());
              }
          
          
          
          
      } catch (Exception eexp) {
        eexp.printStackTrace();
        logger.severe("Exception occurred in preRouting : " + eexp.toString());
        
        
      }
      
      }
    }
  
  public String xmlToString(Node node) {
          try {
              Source source = new DOMSource(node);
              StringWriter stringWriter = new StringWriter();
              Result result = new StreamResult(stringWriter);
              TransformerFactory factory = TransformerFactory.newInstance();
              Transformer transformer = factory.newTransformer();
              transformer.transform(source, result);
              return stringWriter.getBuffer().toString();
          } catch (TransformerConfigurationException e) {
              e.printStackTrace();
          } catch (TransformerException e) {
              e.printStackTrace();
          }
          return null;
      }
  
  public static String getStringFromElement(Element e) {
      Node child = e.getFirstChild();
      if (child instanceof CharacterData) {
         CharacterData cd = (CharacterData) child;
         return cd.getData();
      }
      return "?";
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

    public CalloutMediatorMessage getCalloutMediatorMessage() {
        return calloutMediatorMessage;
    }

    public void setCalloutMediatorMessage(CalloutMediatorMessage calloutMediatorMessage) {
        this.calloutMediatorMessage = calloutMediatorMessage;
    }

    public boolean isReentrant() {
        return reentrant;
    }

    public void setReentrant(boolean reentrant) {
        this.reentrant = reentrant;
    }
}
