/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmann.soa.logging.web.soaloggerservice.mail;

import stockmann.com.logging.LogInterface;
/**
 *
 * @author KATVTUO
 */
public interface SoaLoggerMailer {

    void sendNotification(LogInterface liParam,String message,String emailTo);
}
