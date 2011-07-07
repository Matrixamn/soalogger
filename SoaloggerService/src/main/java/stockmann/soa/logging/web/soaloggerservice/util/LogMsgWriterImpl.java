/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmann.soa.logging.web.soaloggerservice.util;

import stockmann.soa.logging.web.soaloggerservice.transfer.LogMsgTo;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.File;
/**
 *
 * @author KATVTUO
 */
public class LogMsgWriterImpl implements LogMsgWriter {
    
    private String basePath;
    private String fullPath;
    private FileOutputStream out; // declare a file output object
    private PrintStream p; // declare a print stream object
    private LogMsgTo msg;
    private String pathSeparator;

    public void WriteLogMsg(LogMsgTo msgParam) throws Exception {
        try {
        msg = msgParam;
        this.createFullPath();
        File logDir = new File(fullPath);
        if (!logDir.exists()) {
        boolean mkdirOk = logDir.mkdirs();
            if (!mkdirOk) {
                throw new Exception("Cannot create directory : " + fullPath);
            }
        }
        File logMsgFile = new File (logDir.getPath() + pathSeparator + msgParam.getInstanceId() + ".log");

        if (!logMsgFile.createNewFile()) {

              throw new Exception("Cannot create file : " + logDir.getPath() + pathSeparator + msgParam.getInstanceId() + ".log");
        }
            out = new FileOutputStream(logMsgFile);


            p = new PrintStream( out );

            p.print(msg.getLogMsg());

            p.flush();

        } catch (Exception exp) {

        } finally {
             try {
              p.close();

            } catch (Exception e) {}
             try {
                out.close();
             } catch (Exception exx) {}
        }
    }

    private void createFullPath() {
        pathSeparator = System.getProperty("file.separator");
        this.fullPath = basePath + pathSeparator + msg.getEnv() + pathSeparator + msg.getInterfaceName();
    }

    /**
     * @return the basePath
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * @param basePath the basePath to set
     */
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
