/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmann.soa.logging.web.soaloggerservice.transfer;

/**
 *
 * @author KATVTUO
 */
public class LogMsgTo {

    private String instanceId;
    private String env;
    private String interfaceName;
    private String logMsg;
    private String base64Flag;
    private String msgEncoding;
    

    /**
     * @return the instanceId
     */
    public String getInstanceId() {
        return instanceId;
    }

    /**
     * @param instanceId the instanceId to set
     */
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * @return the env
     */
    public String getEnv() {
        return env;
    }

    /**
     * @param env the env to set
     */
    public void setEnv(String env) {
        this.env = env;
    }

    /**
     * @return the interfaceName
     */
    public String getInterfaceName() {
        return interfaceName;
    }

    /**
     * @param interfaceName the interfaceName to set
     */
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    /**
     * @return the logMsg
     */
    public String getLogMsg() {
        return logMsg;
    }

    /**
     * @param logMsg the logMsg to set
     */
    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    /**
     * @return the base64Flag
     */
    public String getBase64Flag() {
        return base64Flag;
    }

    /**
     * @param base64Flag the base64Flag to set
     */
    public void setBase64Flag(String base64Flag) {
        this.base64Flag = base64Flag;
    }

    /**
     * @return the msgEncoding
     */
    public String getMsgEncoding() {
        return msgEncoding;
    }

    /**
     * @param msgEncoding the msgEncoding to set
     */
    public void setMsgEncoding(String msgEncoding) {
        this.msgEncoding = msgEncoding;
    }
    
}
