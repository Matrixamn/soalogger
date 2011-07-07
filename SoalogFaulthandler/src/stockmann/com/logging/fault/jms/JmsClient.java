package stockmann.com.logging.fault.jms;

import javax.jms.*;
import javax.naming.*;
import java.io.*;
import java.util.Hashtable;
import java.util.logging.ConsoleHandler;

import katva.net.logging.SoaLoggerJmsPojo;
import java.util.logging.Logger;
import java.util.logging.Handler;


public class JmsClient {
    public JmsClient() {
        super();
    }
    
    private QueueSession session;
    private QueueSender qsndr = null;
    private QueueConnection connection;
    private String username;
    
    public  Logger logger = Logger.getLogger(JmsClient.class.getName());
    
    private void setLogger() {
    try {
    Handler hf = new ConsoleHandler();
    logger.addHandler(hf);
    } catch (Exception exx) {
    
    }
    }
    
    public void sendMessage(SoaLoggerJmsPojo soaMsg) {
            try {
            //Get initialContext 
                Context ctx = getInitialContext();
                
                QueueConnectionFactory queueConnectionFactory = 
                  (QueueConnectionFactory) ctx.lookup("jms/QueueConnectionFactory");
                //Create connection
                connection = queueConnectionFactory.createQueueConnection();
                        
                //Create session
                session = connection.createQueueSession(false, session.AUTO_ACKNOWLEDGE);
                      
                                                      
                // Look up a JMS queue
                Queue queue = (Queue)ctx.lookup("jms/soaloggerqueue");
                
                qsndr = session.createSender(queue);
                
                
                // Start the JMS connection; allows messages to be delivered
                connection.start( );
                //create Object message to transfer invoiceHeader object 
                //which is normal pojo
                
                ObjectMessage om = session.createObjectMessage();
                om.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
                //create invoiceHeader and add some values to it
                
                om.setObject(soaMsg);
                //It seems that persistent messages has to send using send-method
                qsndr.send(om);
                
                
                connection.close( );
                
                logger.severe("JMS-message sended");
            } catch (Exception e) {
                logger.severe("Exception occurred while sending message to JMS-queue : " + e.toString());
            }
        }
        
        private static Context getInitialContext() throws NamingException {
            Hashtable env = new Hashtable();
            // Oracle Application Server 10g connection details
            env.put( Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            //env.put( Context.SECURITY_PRINCIPAL, "oc4jadmin" );
            //env.put( Context.SECURITY_CREDENTIALS, "Stuma113D" );
            env.put(Context.PROVIDER_URL, "iiop://localhost:7001");
            return new InitialContext( env );
        }
    
}
