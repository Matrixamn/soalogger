/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmann.soa.logging.web.soaloggerservice;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import stockmann.soa.logging.web.soaloggerservice.transfer.LogInterfaceTo;
import stockmann.soa.logging.web.soaloggerservice.transfer.LogMsgTo;
import stockmann.soa.logging.web.soaloggerservice.transfer.LogFieldTo;
import stockmann.soa.logging.web.soaloggerservice.transfer.LogEntryTo;
import stockmann.soa.logging.web.soaloggerservice.util.MessageHandler;
import stockmann.soa.logging.web.soaloggerservice.util.LogMsgWriter;
import stockmann.soa.logging.web.soaloggerservice.util.LogMsgUtil;
import stockmann.com.logging.*;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import stockmann.soa.logging.web.soaloggerservice.mail.SoaLoggerMailer;
/**
 *
 * @author katvtuo
 */
@WebService
public class SoaloggerService {

    
    protected SoaLogger soalogger;
    private ToHelper helper;
    public Logger logger = Logger.getLogger(SoaloggerService.class.getName());
    private SoaLoggerMailer mailer;

    public SoaloggerService() {
        try {
            Context  ctx = new InitialContext();

            soalogger = (SoaLogger)ctx.lookup("StockmannSoaLogger#stockmann.com.logging.SoaLogger");
            helper = new ToHelper();
            this.initLogger();
            this.loadMailer();  
        } catch (Exception exp) {
            logger.severe("Exception occurred in SoaloggerService initialization: " + exp.toString());
        }
    }

    private void loadMailer() {
        try {
            Resource resource = new ClassPathResource("/SoaloggerServBeanConf.xml");
            BeanFactory factory = new XmlBeanFactory(resource);
            this.mailer = (SoaLoggerMailer)factory.getBean("defaultMailer");
        } catch (Exception exp) {
            logger.severe("Unable to get the Mailer: " + exp.toString());
        }
    }

    private LogMsgWriter getLogWriter() {
        try {
        Resource resource = new ClassPathResource("/SoaloggerServBeanConf.xml");
        BeanFactory factory = new XmlBeanFactory(resource);
        LogMsgUtil util = (LogMsgUtil)factory.getBean("MsgUtil");
        
        return util.getWriter();
        } catch (Exception exp) {
            logger.severe("Exception getting MsgUtil: " + exp.toString());
            return null;
        }
    }

   private void initLogger() {
      Handler hf = new ConsoleHandler();
      logger.addHandler(hf);
   }


   

   @WebMethod(operationName="addLogMessage")
   public int addLogMessage(@WebParam(name="logMessage") LogMsgTo  msgParam) {
      try {

          if (msgParam.getEnv() != null && msgParam.getInstanceId() != null && msgParam.getInterfaceName() != null) {
          if (msgParam.getBase64Flag()!= null && msgParam.getBase64Flag().trim().equals("Y")) {
              MessageHandler.decodeBase64Msg(msgParam);
          }
           LogMsgWriter msgWriter = this.getLogWriter();
           msgWriter.WriteLogMsg(msgParam);
          
          } else {
              throw new Exception("Message parameters missing");
          }
          return 0;
      } catch (Exception exp) {
          logger.severe("Exception occured in addLogMessage: " + exp.toString());
          return 1;
      }
   }

   @WebMethod(operationName="addLogEntryValues")
   public int addLogEntryValues(List<LogEntryTo> entriesParam) throws Exception {

       List<LogEntry> entries = helper.convertEntries(entriesParam);

       return soalogger.addLogEntryValues(entries);
   }

   @WebMethod(operationName="getInterfaceFields")
   public List<LogFieldTo> getInterfaceFields(LogInterfaceTo intParam) throws Exception {
       LogInterface iface = helper.convertInterfaces(intParam);
       List<LogFields> fields = soalogger.getInterfaceFields(iface);
       return helper.convertFieldTo(fields);
   }

   private synchronized void checkInterface(LogInterface liPara) {
        //TKatva 12.04.2011. This should be refactored... There should be interface defined which would
       //handle LogInterface adding so that you could "register" handlers for specific events.
       //For example send mail in case of error, etc... But I don't have time, so that's why this just
       //checks for errors and sends mail if recipients found.
       String loglevel = null;
       for (LogEntry le:liPara.getLogEntryList()) {
           loglevel = le.getLogLevel();
           
       }
       
       if (loglevel != null  && loglevel.trim().equals("ERROR")) {
           
           List<LogInterfaceNotification> notifications = this.soalogger.getInterfaceNotificationsWithEnv(liPara);
           for (LogInterfaceNotification notification: notifications) {
               this.mailer.sendNotification(liPara, "ERROR IN INTERFACE: " , notification.getRecipientEmail());
           }
       }
   }

   @WebMethod()
   public synchronized LogInterfaceTo addLogInterfaceEntry(@WebParam(name="interface") LogInterfaceTo liParam) throws Exception {

    try {
       LogInterface li = helper.convertInterfaces(liParam);
       
       LogInterface liRet = soalogger.addLoginterfaceEntry(li);
       try {
       this.checkInterface(liRet);
        } catch (Exception exp) {
            logger.severe("Unable to send notification email: " + exp.toString());
        }
       //If notification email sending fails method must continue.
       
       try {
       List<LogFields> fields = soalogger.getInterfaceFields(liRet);
       if (fields != null) {
       liRet.setLogFieldsList(fields);
       }
       } catch (Exception exp) {

       }
       return helper.convertLogInterfaceTo(liRet);
    } catch (Exception exp) {
        logger.severe("Exception occurred in addLogInterfaceEntry: " + exp.toString());
        throw exp;
    }
   }

    /**
     * @return the soalogger
     */
    public SoaLogger getSoalogger() {
        return soalogger;
    }

    /**
     * @param soalogger the soalogger to set
     */
    public void setSoalogger(SoaLogger soalogger) {
        this.soalogger = soalogger;
    }


    
}
