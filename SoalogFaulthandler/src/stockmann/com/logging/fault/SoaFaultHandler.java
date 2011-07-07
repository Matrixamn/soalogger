package stockmann.com.logging.fault;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import oracle.integration.platform.faulthandling.recovery.RejectedMsgRecoveryContext;
import oracle.integration.platform.faultpolicy.*;
import katva.net.logging.SoaLoggerJmsPojo;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import com.collaxa.cube.engine.fp.BPELFaultRecoveryContextImpl;

import java.io.StringWriter;

import java.util.Properties;
import java.util.GregorianCalendar;

import java.util.Iterator;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.WebServiceRef;
import oracle.tip.mediator.common.error.recovery.MediatorRecoveryContext;
import stockmann.com.logging.fault.jms.JmsClient;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SoaFaultHandler implements IFaultRecoveryJavaClass {
    public SoaFaultHandler() {
        this.setLogger();
        this.loadProperties();
      
    }
    
    private SoaLoggerJmsPojo jmsMsg = null;
    private Random random = new Random();
    private String env = null;
    private String logLevel = null;
    private Logger logger = Logger.getLogger(SoaFaultHandler.class.getName());
    private Properties props = new Properties();
    private JmsClient jmsClient = null;
    
  public String handleFault(IFaultRecoveryContext ctx) {
      try {   
       jmsClient = new JmsClient();
       jmsMsg = new SoaLoggerJmsPojo();
       this.getJmsMessage(ctx);
       jmsClient.sendMessage(jmsMsg);   
       return "ora-terminate";   
      } catch (Exception exp) {
       exp.printStackTrace();
       logger.severe("Exception in SoaFaultHander: " + exp.toString());
        return "ora-terminate";  
      }
  }
  
  private void setLogger() {
      try {
        Handler hf = new ConsoleHandler();
      logger.addHandler(hf);    
      } catch (Exception exx) {
        
      }
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
  
  public String getEnv() {
    return props.getProperty("env_name");
  }
  
  private void loadProperties() {
      try {
        props.load(this.getClass().getClassLoader().getResourceAsStream("env.properties"));
          this.env = props.getProperty("env_name");
          this.logLevel = props.getProperty("log_level_err");
      } catch(Exception exp) {
        this.logger.severe("PROPS: Exception reading properties: " + exp.toString());
      }
  }
  
 
  private void getJmsMessage(IFaultRecoveryContext ctx) {
      try {
      
      
      if (ctx instanceof BPELFaultRecoveryContextImpl)  {
        BPELFaultRecoveryContextImpl rmrc = (com.collaxa.cube.engine.fp.BPELFaultRecoveryContextImpl)ctx;
        
          jmsMsg.setInterfaceId(new Long(random.nextLong()).toString());
          jmsMsg.setInterfaceName(rmrc.getComponentName());
          jmsMsg.setEnvFlag(env);
          jmsMsg.setInstanceId(rmrc.getCompositeInstanceId());
          jmsMsg.setMsgPayload(rmrc.getFault().getMessage());
          jmsMsg.setLogLevel(logLevel);  
        try {
            if (rmrc.getProcessDN().getRevision() != null) {  
            jmsMsg.setInterfaceVersion(rmrc.getProcessDN().getRevision());
          
            } else {
              jmsMsg.setInterfaceVersion("0");
            }
        } catch (Exception exp) {
            jmsMsg.setInterfaceVersion("0");
        }
        jmsMsg.setMsgPayload(rmrc.getCorrelationId());
        
      } else if (ctx instanceof RejectedMsgRecoveryContext) {
          
        RejectedMsgRecoveryContext rmrc = (RejectedMsgRecoveryContext)ctx;
        
          jmsMsg.setInterfaceId(new Long(random.nextLong()).toString());
          jmsMsg.setInterfaceName(rmrc.getRejectedMessage().getCompositeDn());
          jmsMsg.setEnvFlag(env);
          jmsMsg.setInstanceId(rmrc.getRejectedMessage().getProtocolMessageId());
          jmsMsg.setErrorMsg(rmrc.getRejectedMessage().getErrorMessage());
          jmsMsg.setMsgPayload(rmrc.getRejectedMessage().XML_PAYLOAD);
        //li.setIntegrationType("IT-SOA");
          jmsMsg.setInterfaceVersion(rmrc.getRejectedMessage().getBatchId());
          jmsMsg.setLogLevel(logLevel);
        try {
            if (rmrc.getFault().getCompositeDN().getRevision() != null) { 
            jmsMsg.setInterfaceVersion(rmrc.getFault().getCompositeDN().getRevision());
          } else {
                jmsMsg.setInterfaceVersion("0");
            }
        } catch (Exception exp) {
        jmsMsg.setInterfaceVersion("0");}
      } else if (ctx instanceof MediatorRecoveryContext) {
        MediatorRecoveryContext rmrc = (MediatorRecoveryContext)ctx;
          jmsMsg.setInterfaceId(new Long(random.nextLong()).toString());
          jmsMsg.setInterfaceName(rmrc.getFault().getCompositeDN().getCompositeName());
          jmsMsg.setEnvFlag(env);
          jmsMsg.setInstanceId(rmrc.getFault().getCompositeInstanceId());
          jmsMsg.setLogLevel(logLevel); 
          jmsMsg.setErrorMsg(rmrc.getFault().getMessage().toString());
          try {
              Map payload = rmrc.getMediatorMessage().getPayload();
              //Try to get the message key
              String key = null;
              
              Iterator it = payload.keySet().iterator();
                while (it.hasNext()) {
                    key = (String)it.next();
                    
                }
              Element payloadElement = (Element)payload.get(key);
              jmsMsg.setMsgPayload(this.xmlToString(payloadElement));
          } catch (Exception exp) {
              jmsMsg.setMsgPayload("Error parsing payload...");
          }
          
        try {
            if (rmrc.getFault().getCompositeDN().getRevision() != null) {  
            jmsMsg.setInterfaceVersion(rmrc.getFault().getCompositeDN().getRevision());
            } else {
            jmsMsg.setInterfaceVersion("0");
                }
        } catch (Exception exp) {
        jmsMsg.setInterfaceVersion("0");
        }
      }
    
   
    
    
      } catch (Exception exp) {
        logger.severe("Exception occurred in getLogInterface: " + exp.toString());
        
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
    
  
  /*
   * TKatva, Removed 01.07.2011 because error message is written JMS-queue 
   
  
  private void getLogEntries(IFaultRecoveryContext ctx) {
      try {  
    ArrayList<LogEntryTo> entries = new ArrayList<LogEntryTo>();
    if (ctx instanceof RejectedMsgRecoveryContext) {
    RejectedMsgRecoveryContext rmrc = (RejectedMsgRecoveryContext)ctx;
        LogEntryTo entry = new LogEntryTo();
        if (rmrc.getFault().getComponentInstanceId() != null) {
        entry.setIntegrationInstanceId(rmrc.getFault().getCompositeInstanceId());
        } else {
          entry.setIntegrationInstanceId(rmrc.getFault().getComponentInstanceId());
        }
        entry.setLogEntryId(random.nextInt());
        entry.setInstanceVersion(rmrc.getRejectedMessage().getBatchId());
        entry.setIntegrationInstanceId(rmrc.getRejectedMessage().getProtocolMessageId());
        
        entry.setLogDate(this.getToday());  
        entry.setLogLevel(logLevel);
        entry.setLogMsg(rmrc.getRejectedMessage().getErrorMessage());
        entry.setLogPayload(rmrc.getRejectedMessage().getNativePayload().toString());
        entries.add(entry);  
    } else if (ctx instanceof BPELFaultRecoveryContextImpl) {
        BPELFaultRecoveryContextImpl rmrc = (com.collaxa.cube.engine.fp.BPELFaultRecoveryContextImpl)ctx;
        LogEntryTo entry = new LogEntryTo();
        entry.setLogEntryId(random.nextInt());
        if (rmrc.getCompositeInstanceId() != null) {
          entry.setIntegrationInstanceId(rmrc.getCompositeInstanceId());
        } else  {
          entry.setIntegrationInstanceId(rmrc.getComponentInstanceId());
        }
        //entry.setInstanceVersion();
        
        
        entry.setLogDate(this.getToday());  
        entry.setLogLevel(logLevel);
        entry.setLogMsg(rmrc.getFault().getFaultName().toString());
        entry.setLogPayload(rmrc.getFault().getMessage());
        entries.add(entry); 
    } else if (ctx instanceof MediatorRecoveryContext) {
      MediatorRecoveryContext rmrc = (MediatorRecoveryContext)ctx;
      LogEntryTo entry = new LogEntryTo();
      entry.setLogEntryId(random.nextInt());
      if (rmrc.getFault().getCompositeInstanceId() != null) {
      entry.setIntegrationInstanceId(rmrc.getFault().getCompositeInstanceId());
      } else {
       
        entry.setIntegrationInstanceId(rmrc.getFault().getComponentInstanceId());
      }
     
      entry.setLogDate(this.getToday());  
      entry.setLogLevel(logLevel);
      entry.setLogMsg(rmrc.getFault().toString());
      entry.setLogPayload(rmrc.getFault().getMessage().toString());
      entries.add(entry);
    }
    return entries;
      } catch (Exception exp) {
        logger.severe("Exception getting log entries: " + exp.toString());
        return null;
      }
  } */

    public void handleRetrySuccess(IFaultRecoveryContext iFaultRecoveryContext) {
    }
    
    
    
 
}
