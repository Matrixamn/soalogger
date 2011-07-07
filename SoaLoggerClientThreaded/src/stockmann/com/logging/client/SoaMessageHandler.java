package stockmann.com.logging.client;


import java.util.logging.Logger;
import oracle.tip.mediator.common.api.CalloutMediatorMessage;
import oracle.tip.mediator.common.api.IJavaCallout;
import oracle.tip.mediator.common.api.MediatorCalloutException;
import oracle.tip.mediator.infra.exception.MediatorException;
import oracle.tip.mediator.metadata.CaseType;
import java.util.HashMap;


/* Change history 
 * 
 * TKatva, 1.6.2011 : Removed use of thread because excessive amount of 
 * threads were created on peak loads
 * 
 * TKatva, 15.06.2011 Added class which writes message to Jms-queue, Message-Driven Bean reads payload from queue
 * and sends the message to Web Service. Depencies to SoaloggerWsWrapper must be removed. Message-driven bean will handle
 * all logic, including sending and parsing the message. 
 * 
 */

public class SoaMessageHandler implements IJavaCallout {
    

  
  public static Logger logger = Logger.getLogger(SoaMessageHandler.class.getName());
  public static HashMap logLevels = new HashMap();
    public SoaMessageHandler() {
        super();
        logLevels.put("INFO", "INFO");
        logLevels.put("WARNING", "WARNING");
        logLevels.put("ERROR", "ERROR");
        
    }
   
  public void initialize(Logger logger) throws MediatorCalloutException {
  
  }
  

    
  public boolean preRouting(CalloutMediatorMessage calloutMediatorMessage) throws MediatorCalloutException {
      
      
      //this.addLoggingEntry(calloutMediatorMessage);
      MessageProcesser mp = new MessageProcesser();
      mp.setCalloutMediatorMessage(calloutMediatorMessage);
      mp.setReentrant(false);
      mp.initializeThread();
      mp.addLogEntry();
      
      return false;
  }

  public boolean preRoutingRule(CaseType caseType,
                                CalloutMediatorMessage calloutMediatorMessage) throws MediatorCalloutException {
      
    
     
      return false;
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
      
      
      //Not implemented because of racing conditions
      /*MessageProcesser mp = new MessageProcesser();
      mp.setCalloutMediatorMessage(calloutMediatorMessage);
      mp.setReentrant(true);
      mp.initializeThread();
      
      mp.start();*/
      return false;
  }

  public boolean preCallbackRouting(CalloutMediatorMessage calloutMediatorMessage) throws MediatorCalloutException {
      return false;
  }

  public boolean postCallbackRouting(CalloutMediatorMessage calloutMediatorMessage,
                                     Throwable throwable) throws MediatorCalloutException {
      return false;
  }
  

}
