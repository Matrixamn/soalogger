package stockmann.com.logging.client;

import java.io.StringWriter;

import javax.xml.ws.WebServiceRef;
import com.stockmann.ws.wrapper.SoaloggerService;
import com.stockmann.ws.wrapper.SoaloggerServiceService;
import com.stockmann.ws.wrapper.types.LogInterfaceTo;
import com.stockmann.ws.wrapper.types.LogEntryTo;
import com.stockmann.ws.wrapper.types.LogFieldTo;
import com.stockmann.ws.wrapper.types.LogMsgTo;
import java.util.logging.Handler;
import java.util.logging.Logger;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class MsgWriter extends Thread {
    
    
  private  Logger logger = Logger.getLogger(MsgWriter.class.getName());
  
  private SoaloggerService soaLogger = null;
  
  private String msgParam;
  private LogInterfaceTo li;
  private LogEntryTo entry;
  private Element payload;
  
  public void run() {
      try {
        String msgString = this.xmlToString(payload);
          if (li.getMsgMaxLenght() != null && msgString.length() < li.getMsgMaxLenght()) {  
        LogMsgTo msg = new LogMsgTo();
        msg.setBase64Flag(getLi().getApplyBase64());
        msg.setEnv(getLi().getEnvironmentFlag());
        msg.setInstanceId(getEntry().getIntegrationInstanceId());
        msg.setInterfaceName(getLi().getLogInterfaceName());
        msg.setLogMsg(msgString);
        msg.setMsgEncoding(li.getMsgEncoding());
        int retval = getSoaLogger().addLogMessage(msg);  
        if (retval != 0) {
          logger.severe("Writing message to web service did not succeed: " + retval);
        }
        
          } else {
            logger.severe("Message too long... Message not sent to Web Service");
          }
      } catch (Exception exp) {
        exp.printStackTrace();
        logger.severe("Exception in MsgWriter-thread: " + exp.toString());
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

    public SoaloggerService getSoaLogger() {
        return soaLogger;
    }

    public void setSoaLogger(SoaloggerService soaLogger) {
        this.soaLogger = soaLogger;
    }

    public String getMsgParam() {
        return msgParam;
    }

    public void setMsgParam(String msgParam) {
        this.msgParam = msgParam;
    }

    public LogInterfaceTo getLi() {
        return li;
    }

    public void setLi(LogInterfaceTo li) {
        this.li = li;
    }

    public LogEntryTo getEntry() {
        return entry;
    }

    public void setEntry(LogEntryTo entry) {
        this.entry = entry;
    }

    public Element getPayload() {
        return payload;
    }

    public void setPayload(Element payload) {
        this.payload = payload;
    }
}
