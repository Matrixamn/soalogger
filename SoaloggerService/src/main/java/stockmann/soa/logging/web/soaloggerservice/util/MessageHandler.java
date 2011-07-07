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
public class MessageHandler {


    

    public static void decodeBase64Msg(LogMsgTo msgParam) throws Exception {
        
        byte[] msgBytes = Base64.decode(msgParam.getLogMsg());

        if (msgParam.getMsgEncoding() != null) {
            msgParam.setLogMsg(new String(msgBytes,msgParam.getMsgEncoding()));
        } else {
            msgParam.setLogMsg(new String(msgBytes));
        }
        
    }
}
