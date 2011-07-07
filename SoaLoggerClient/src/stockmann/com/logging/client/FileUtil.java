package stockmann.com.logging.client;

import java.io.*;


public class FileUtil {
    
    private String pathSeparator = null;
    private FileOutputStream out; // declare a file output object
    private PrintStream p; // declare a print stream object
    private String baseDirectory = "SoaLogger";
    
    
    public FileUtil() {
        super();
        this.pathSeparator = System.getProperty("file.separator");
    }
    
    public int createLoggingMsg(String directory,String env, String instanceId,String msg) {
        try {
           String fullDirectory = baseDirectory + pathSeparator +env + pathSeparator + directory;
           File logDir = new File(fullDirectory);
            if (!logDir.exists()) {
              boolean mkdirOk = logDir.mkdirs();
              if (!mkdirOk) {
                return -2;
              }
            }
           File logMsgFile = new File (logDir.getPath() + pathSeparator + instanceId + ".log");
           boolean fileCreateOk = logMsgFile.createNewFile();
            if (!fileCreateOk) {
             
              return -3;
            }
            out = new FileOutputStream(logMsgFile);

          
            p = new PrintStream( out );
            
            p.print(msg);
            
            p.flush();
            
           return 0;
        } catch (Exception exp) {
          SoaMessageHandler.logger.severe("createLoggingMsg : " + exp.toString()); 
          return -1;
          } finally {
            try {
              p.close();
              out.close();
            } catch (Exception e) {}
          }
    }
    
}
