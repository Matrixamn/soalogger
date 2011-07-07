package stockmann.com.logging;

import java.util.Hashtable;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SoaLoggerClient {
    public static void main(String [] args) {
        try {
            Context context = getInitialContext();
            SoaLogger soaLogger = (SoaLogger)context.lookup("StockmannSoaLogger#stockmann.com.logging.SoaLogger");
            List<LogInterface> interfaces = soaLogger.getAllLogInterfaces();
                      for (LogInterface log:interfaces) {
                        System.out.println("Log : " + log.getLogInterfaceName());
                      }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x connection details
        env.put( Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory" );
        env.put(Context.PROVIDER_URL, "t3://olympix:7003");
        return new InitialContext( env );
    }
}
