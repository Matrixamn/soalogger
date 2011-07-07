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

public class SoaMessageHandler implements IJavaCallout {
    
  SoaLogger soaLogger = null;
  MessageUtil msgUtil = new MessageUtil();
  Random random = new Random();
  FileUtil fileutil;
  
  public static Logger logger = Logger.getLogger(SoaMessageHandler.class.getName());
    public SoaMessageHandler() {
        super();
        fileutil = new FileUtil();
        this.setLogger();
        this.getLogger();
        
    }
    
    
  private void setLogger() {
      try {
        Handler hf = new ConsoleHandler();
      logger.addHandler(hf);    
      } catch (Exception exx) {
        
      }
  }
    
    private void getLogger() {
        try {
          final Context context = getInitialContext();
          soaLogger = (SoaLogger)context.lookup("StockmannSoaLogger#stockmann.com.logging.SoaLogger");
            
        } catch (Exception exp) {
          logger.severe("Exception getting logger: " + exp.toString());
        }
    }
    
 
    
  public void initialize(Logger logger) throws MediatorCalloutException {
  
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
   
  private String getElementStringValue(String nodeName,Element nodeElement) {
         try {
             NodeList node = nodeElement.getElementsByTagName(nodeName);
             return getStringFromElement((Element)node.item(0));
         } catch (Exception exp) {
             return null;
         }
     }

  public static String getStringFromElement(Element e) {
      Node child = e.getFirstChild();
      if (child instanceof CharacterData) {
         CharacterData cd = (CharacterData) child;
         return cd.getData();
      }
      return "?";
    } 
    
  public boolean preRouting(CalloutMediatorMessage calloutMediatorMessage) throws MediatorCalloutException {
      
      
      this.addLoggingEntry(calloutMediatorMessage);
      
      return false;
  }

  public boolean preRoutingRule(CaseType caseType,
                                CalloutMediatorMessage calloutMediatorMessage) throws MediatorCalloutException {
      
    
     
      return false;
  }
  
  private void addLoggingEntry(CalloutMediatorMessage calloutMediatorMessage) {
    String msgId = null;
    try {
    msgId = calloutMediatorMessage.getId();
    } catch (MediatorException mexp) {}
    LogInterface tempLi = msgUtil.getInterfaceName(calloutMediatorMessage.getComponentDN(),msgId, "Started", "");
    
    LogEntry entry = new LogEntry();
    entry.setLogEntryId(tempLi.getLogEntryList().get(0).getLogEntryId());
    entry.setLogDate(tempLi.getLogEntryList().get(0).getLogDate());
    entry.setInstanceVersion(tempLi.getLogEntryList().get(0).getInstanceVersion());
    entry.setLogLevel(tempLi.getLogEntryList().get(0).getLogLevel());
    entry.setIntegrationInstanceId(tempLi.getLogEntryList().get(0).getIntegrationInstanceId());
    entry.setLogMsg(tempLi.getLogEntryList().get(0).getLogMsg());
    entry.setLogInterface(tempLi.getLogEntryList().get(0).getLogInterface());
    entry.setLogPayload(tempLi.getLogEntryList().get(0).getLogPayload());
    LogInterface li = soaLogger.addLoginterfaceEntry(tempLi);
    
    
    
    try {
      Map payload = calloutMediatorMessage.getPayload();
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
                                value.setEntryValue(SoaMessageHandler.getStringFromElement(valEl));
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

  public boolean postRoutingRule(CaseType caseType,
                                 CalloutMediatorMessage calloutMediatorMessage,
                                 CalloutMediatorMessage calloutMediatorMessage2,
                                 Throwable throwable) throws MediatorCalloutException {
      
    
      return false;
  }

  public boolean postRouting(CalloutMediatorMessage calloutMediatorMessage,
                             CalloutMediatorMessage calloutMediatorMessage2,
                             Throwable throwable) throws MediatorCalloutException {
      
        String msgId = null;
        try {
        msgId = calloutMediatorMessage.getId();
        } catch (MediatorException mexp) {}
      
      soaLogger.addLoginterfaceEntry(msgUtil.getInterfaceName(calloutMediatorMessage.getComponentDN(),msgId, "Stopped", ""));
      return false;
  }

  public boolean preCallbackRouting(CalloutMediatorMessage calloutMediatorMessage) throws MediatorCalloutException {
      return false;
  }

  public boolean postCallbackRouting(CalloutMediatorMessage calloutMediatorMessage,
                                     Throwable throwable) throws MediatorCalloutException {
      return false;
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
