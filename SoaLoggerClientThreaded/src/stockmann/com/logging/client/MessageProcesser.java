package stockmann.com.logging.client;

import java.io.StringWriter;

import java.util.List;
import java.util.logging.Logger;
import oracle.tip.mediator.common.api.CalloutMediatorMessage;
import oracle.tip.mediator.infra.exception.MediatorException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Handler;

import com.stockmann.ws.wrapper.SoaloggerService;
import com.stockmann.ws.wrapper.SoaloggerServicePortClient;
import com.stockmann.ws.wrapper.SoaloggerServiceService;
import com.stockmann.ws.wrapper.types.LogInterfaceTo;
import com.stockmann.ws.wrapper.types.LogEntryTo;
import com.stockmann.ws.wrapper.types.LogFieldTo;
import com.stockmann.ws.wrapper.types.LogMsgTo;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.Random;
import java.util.prefs.Preferences;
import javax.xml.xpath.*;

import java.util.logging.ConsoleHandler;
import java.util.Date;

import java.util.GregorianCalendar;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Properties;
import katva.net.logging.SoaLoggerJmsPojo;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceRef;

import com.stockmann.ws.wrapper.types.EntryValueTo;
import stockmann.com.logging.client.jms.JmsClient;
import katva.net.logging.SoaLoggerJmsPojo;

public class MessageProcesser extends Thread {
    
    @WebServiceRef
    private SoaloggerServiceService soaloggerServiceService;  
  private CalloutMediatorMessage calloutMediatorMessage;
  private SoaloggerService soaLogger = null;
  private Preferences prefs;
  MessageUtil msgUtil = new MessageUtil();
  Random random = new Random();
  FileUtil fileutil;
  boolean reentrant;
  String logImsg;
  LogInterfaceTo li;
  LogEntryTo entry;
  Base64 base64 = new Base64();
  Properties envProps = new Properties();
  JmsClient jmsClient = new JmsClient();
  
  public  Logger logger = Logger.getLogger(MessageProcesser.class.getName());
  
  private void getLogger() {
      try {
        //final Context context = getInitialContext();
       // soaLogger = (SoaLogger)context.lookup(envProps.getProperty("soaLogger_lookup"));
       soaloggerServiceService = new SoaloggerServiceService();
       soaLogger = soaloggerServiceService.getSoaloggerServicePort();
      } catch (Exception exp) {
        logger.severe("Exception getting logger: " + exp.toString());
      }
  }
  
  private void loadEnvProperties()  {
      try {
        envProps.load(this.getClass().getClassLoader().getResourceAsStream("env.properties"));
         
      } catch (Exception exp) {
        logger.severe("Exception reading env properties : "  + exp.toString());
        try {  
        String path = new java.io.File(".").getCanonicalPath();
        logger.severe("RUNPATH : " + path);    
        } catch (Exception eee) {}   
      }
  }
  
  private void setLogger() {
      try {
        Handler hf = new ConsoleHandler();
      logger.addHandler(hf);    
      } catch (Exception exx) {
        
      }
  }
  
  private void encodeElements(String encoding, Element element, boolean applyBase64) {
      if (element.hasChildNodes()) {
          
        NodeList childNodes = element.getChildNodes();
        
        for (int i = 0; i < childNodes.getLength(); i++) {
          Node node = childNodes.item(i);
            if (node.getNodeType() == node.ELEMENT_NODE) {
              this.encodeElements(encoding, (Element)node,applyBase64);
            } else if (node.getNodeType() == node.TEXT_NODE) {
                if(applyBase64) {
                        try {
                         byte[] bytes = base64.decode(node.getNodeValue());    
                        String decodedString = new String(bytes,encoding);
                        node.setNodeValue(decodedString);
                        } catch (Exception exp) {}
                } else {
                    try {  
                        byte[] bytes = node.getNodeValue().getBytes(encoding);
                        String decodedString = new String(bytes);
                        node.setNodeValue(decodedString);
                    } catch (Exception epx) {}
                }
              
            }
             
        }
      }
  }
  
  
  
  public void initializeThread() {
    fileutil = new FileUtil();
    this.loadEnvProperties();
   // prefs = Preferences.userNodeForPackage(this.getClass());
    this.setLogger();
    this.getLogger();
    
    
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
  
  private void checkFailedLogEntries(LogInterfaceTo intFace) {
    int failedCount = prefs.getInt(intFace.getLogInterfaceName(), 0);
    if (failedCount > 0) {
      
      LogEntryTo entry = new LogEntryTo();
      entry.setInstanceVersion("0");
      entry.setLogEntryId(random.nextInt());
      entry.setLogLevel((String)SoaMessageHandler.logLevels.get("ERROR"));
      entry.setIntegrationInstanceId("0");
      entry.setLogMsg( failedCount + " instances not logged");
      
      entry.setLogDate(this.getToday());
      intFace.getLogEntryList().add(entry);
    } 
  }
  
  private void failedCountToZero(LogInterfaceTo liParam) {
    prefs.putInt(liParam.getLogInterfaceName(), 0);
  }
  
  private void increaseFailedCounter(LogInterfaceTo liParam) {
    int counter = prefs.getInt(liParam.getLogInterfaceName(), 0);
    counter++;
    prefs.putInt(liParam.getLogInterfaceName(), counter);
  }
  
  public void addLogEntry() {
    String msgId = null;
    
    try {
    msgId = getCalloutMediatorMessage().getId();
    logger.info("Mediator message id : " + msgId);    
    } catch (MediatorException mexp) {}
    
    if (!reentrant) {
      logImsg = "Instance processed";
    } else {
      logImsg = "Stopped";
    }
    
    SoaLoggerJmsPojo pojo = new SoaLoggerJmsPojo();
    
    LogInterfaceTo tempLi = null;
    tempLi = msgUtil.getInterfaceName(getCalloutMediatorMessage().getComponentDN(),msgId, logImsg, "",envProps.getProperty("env_flag"));
    
    pojo.setInterfaceName(tempLi.getLogInterfaceName());
    pojo.setEnvFlag(tempLi.getEnvironmentFlag());
    pojo.setInterfaceId(tempLi.getLogInterfaceId().toString());
    pojo.setLogLevel(tempLi.getLogEntryList().get(0).getLogLevel());
    pojo.setInterfaceVersion(tempLi.getLogInterfaceVersion());
    pojo.setInstanceId(tempLi.getLogEntryList().get(0).getIntegrationInstanceId());
    
    //try {
    //tempLi = msgUtil.getInterfaceName(getCalloutMediatorMessage().getComponentDN(),msgId, logImsg, "",envProps.getProperty("env_flag"));
    //Failure counter is removed because of the exceptions regarding backing store    
    //this.checkFailedLogEntries(tempLi);
    //15.06.2011, TKatva, removed because JMS-queue listener parses the message.
    /*    
    entry = new LogEntryTo();
    entry.setLogEntryId(tempLi.getLogEntryList().get(0).getLogEntryId());
    entry.setLogDate(tempLi.getLogEntryList().get(0).getLogDate());
    entry.setInstanceVersion(tempLi.getLogEntryList().get(0).getInstanceVersion());
    entry.setLogLevel(tempLi.getLogEntryList().get(0).getLogLevel());
    entry.setIntegrationInstanceId(tempLi.getLogEntryList().get(0).getIntegrationInstanceId());
    entry.setLogMsg(tempLi.getLogEntryList().get(0).getLogMsg());
    //entry.setLogInterface(tempLi.getLogEntryList().get(0).getLogInterface());
    entry.setLogPayload(tempLi.getLogEntryList().get(0).getLogPayload());
        
    li = soaLogger.addLogInterfaceEntry(tempLi);
    } catch (Exception exp) {
      logger.severe("EXCEPTION : " + exp.toString());
    }*/
    /* Failure counter is removed because of the exceptions regarding backing store
    if (li == null) {
      this.increaseFailedCounter(tempLi);
    } else {
      this.failedCountToZero(tempLi);
    }
    /*
    if (!reentrant) {
      this.checkValues();
    } */
    //Get payload of the message  
    Map payload = getCalloutMediatorMessage().getPayload();
    //Try to get the message key
    String key = null;
    
    Iterator it = payload.keySet().iterator();
      while (it.hasNext()) {
          key = (String)it.next();
          
      }
    Element payloadElement = (Element)payload.get(key);
    //this.logger.severe("ELEMENT : " + this.xmlToString(payloadElement));
    pojo.setMsgPayload(this.xmlToString(payloadElement));
    jmsClient.sendMessage(pojo);
  }
  
  private void checkValues() {
    
    try {
      //Get payload of the message  
      Map payload = getCalloutMediatorMessage().getPayload();
      //Try to get the message key
      String key = null;
      
      Iterator it = payload.keySet().iterator();
        while (it.hasNext()) {
            key = (String)it.next();
            
        }
      Element payloadElement = (Element)payload.get(key);
      
      
      
      
        try {
          
            if(li.getLogMessageFlag() != null && li.getLogMessageFlag().trim().equals("Y")) { 
            MsgWriter writer = new MsgWriter();
            
             writer.setPayload(payloadElement);
             
             writer.setLi(li);
             writer.setEntry(entry);
             writer.setSoaLogger(soaLogger);
             writer.run();
             /* This code is moved to another thread
          LogMsgTo msg = new LogMsgTo();
          msg.setBase64Flag(li.getApplyBase64());
          msg.setEnv(li.getEnvironmentFlag());
          msg.setInstanceId(entry.getIntegrationInstanceId());
          msg.setInterfaceName(li.getLogInterfaceName());
          msg.setLogMsg(msgString);  
          int retval = soaLogger.addLogMessage(msg);  
          if (retval != 0) {
            logger.severe("Writing message to web service did not succeed: " + retval);
          }
            */
            }
        } catch (Exception expp) {
            expp.printStackTrace();
            logger.severe("Exception writing message to web service : " + expp.toString());
        }
    List<LogFieldTo> lfs = null;
        try {    
      lfs = soaLogger.getInterfaceFields(li);
      if (lfs != null) {
          
          ArrayList<EntryValueTo> values = new ArrayList<EntryValueTo>();
          for (LogFieldTo field:lfs) {
                         
              if (field.getXmlXpath() != null) {
                  try {
                    
                XPathFactory factory = XPathFactory.newInstance();
                XPath xpath = factory.newXPath();
                XPathExpression expr = xpath.compile(field.getXmlXpath());
                String xpathResult = (String)expr.evaluate(payloadElement, XPathConstants.STRING); 
                EntryValueTo value = new EntryValueTo();
                value.setValueName(field.getLogFieldName());
                value.setValueId(random.nextInt());   
                value.setEntryValue(xpathResult);   
               
                values.add(value);      
                  } catch (Exception exp) {
                    logger.severe("Exception parsing Xpath : " + exp.toString());
                  }
              } else {
                            
                            NodeList nl = payloadElement.getElementsByTagNameNS("*", field.getElementName());
                            
                              for (int i = 0; i < nl.getLength(); i++) {
                                Element valEl = (Element)nl.item(i);
                                
                                List<String> elementValues = MessageProcesser.getStringFromElement(valEl);  
                                  for (String elementValue:elementValues) {
                                    EntryValueTo value = new EntryValueTo();
                                    value.setValueName(field.getLogFieldName());
                                    value.setValueId(random.nextInt()); 
                                    value.setEntryValue(elementValue);  
                                    values.add(value);
                                  }
                                
                              }
              }
          }
          entry.setEntryValuesList(values);
          ArrayList<LogEntryTo> logEntries = new ArrayList<LogEntryTo>();
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
    
  public void run() {
      try {
      if (!reentrant) {
        this.addLogEntry();
      }
      } catch (Exception exp) {
        logger.severe("Exception in MessageProcesser thread: " + exp.toString());
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
