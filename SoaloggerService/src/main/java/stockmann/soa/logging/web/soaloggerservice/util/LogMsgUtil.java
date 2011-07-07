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
public class LogMsgUtil {


    
    private LogMsgWriter writer;
    

    public void logMsg(LogMsgTo msgParam) throws Exception {
        writer.WriteLogMsg(msgParam);
    }

    /**
     * @return the writer
     */
    public LogMsgWriter getWriter() {
        return writer;
    }

    /**
     * @param writer the writer to set
     */
    public void setWriter(LogMsgWriter writer) {
        this.writer = writer;
    }
}
