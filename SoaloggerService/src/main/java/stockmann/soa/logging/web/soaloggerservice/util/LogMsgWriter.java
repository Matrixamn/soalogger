/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmann.soa.logging.web.soaloggerservice.util;

import stockmann.soa.logging.web.soaloggerservice.transfer.LogMsgTo;
/**
 *
 * @author KATVTUO
 */
public interface LogMsgWriter {


    void WriteLogMsg(LogMsgTo msgParam) throws Exception;
}
