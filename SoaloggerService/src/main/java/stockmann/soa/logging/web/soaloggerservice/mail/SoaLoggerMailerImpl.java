/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmann.soa.logging.web.soaloggerservice.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import stockmann.com.logging.LogInterface;
import stockmann.com.logging.LogFields;
import java.lang.StringBuilder;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;

/**
 *
 * @author KATVTUO
 */
public class SoaLoggerMailerImpl implements SoaLoggerMailer {

    private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    public Logger logger = Logger.getLogger(SoaLoggerMailerImpl.class.getName());

    public SoaLoggerMailerImpl() {
        this.initLogger();
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

    private void initLogger() {
      Handler hf = new ConsoleHandler();
      logger.addHandler(hf);
   }

    public void sendNotification(LogInterface liParam,String message,String emailTo) {

        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(emailTo);
        Date date = new Date();
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        sb.append(" ");
        sb.append(liParam.getLogInterfaceName());
        sb.append(" "); sb.append("version "); sb.append(liParam.getLogInterfaceVersion());
        sb.append(" "); sb.append(date.toString());
        msg.setText(sb.toString());

        try {
            
            mailSender.send(msg);
            
        } catch (MailException ex) {
            logger.severe("Exception sending the notification message: " + ex.toString());
        } catch (Exception exp) {
            logger.severe("Unknown exception in sending the notification message : " + exp.toString());
        }

    }
}
