package tkatva.net.soalogger.msgutil;

import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import org.w3c.dom.*;
import java.io.*;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class MsgUtil {
    public MsgUtil() {
        this.setLogger();
    }
    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    public Document parseInputString(String inputString) {
        try {
            DocumentBuilderFactory dbf =
                        DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(inputString));
            Document doc = db.parse(is);
            
            return doc;
        } catch (Exception exp) {
            logger.severe("Unable to convert String to XML-document: " + exp.toString());
            return null;
        }
    }
    
    private void setLogger() {
        try {
          Handler hf = new ConsoleHandler();
        logger.addHandler(hf);    
        } catch (Exception exx) {
          
        }
    }
    
    
}
