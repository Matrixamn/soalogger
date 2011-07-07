/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmann.soa.logging.web.soaloggerweb.app;

import javax.ejb.EJB;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.util.List;
import org.quartz.Job;
import javax.naming.Context;
import javax.naming.InitialContext;
import tkatva.net.soalogger.session.*;
import tkatva.net.soalogger.entity.*;
import stockmann.soa.logging.web.soaloggerweb.fileutil.FileSearch;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import java.util.Calendar;

/**
 *
 * @author katvtuo
 */
public class LoggerCleaningJob implements Job {
    @EJB(mappedName="SoaloggerBean")
    private Soalogger soaLogger;
    public Logger logger = Logger.getLogger(LoggerCleaningJob.class.getName());
    public void execute(JobExecutionContext context)
      throws JobExecutionException
    {
        this.getLogger();
        logger.info("Soa log cleaning job started");
        try {

            this.getSoaLogger();
            
            List<LogSched> schedules = soaLogger.getAllLogSchedules();
            for (LogSched schedule:schedules) {
                

                FileSearch fileUtil = this.getInitFileSearch();
                fileUtil.setInterfaceName(schedule.getLogInterface().getLogInterfaceName());
                fileUtil.setEnv(schedule.getLogInterface().getEnvironmentFlag());
                Calendar cal = Calendar.getInstance();
                //cal.add(Calendar.DATE, this.getReverseInt(schedule.getDaysOlderToRemove()));
                soaLogger.removeLogEntriesTo(cal.getTime(), schedule.getLogInterface());
                fileUtil.removeInterfaceMessageTo(cal.getTime());
            }
            logger.info("Soa log cleaning job finished");
        } catch (Exception exp) {
            exp.printStackTrace();
            logger.severe("Exception occurred in LoggerCleaningJob: " + exp.toString());
        }

    }

    private int getReverseInt(Integer yx) {
        int yxVal = yx.intValue();
        int yx2x = yxVal + yxVal;
        yxVal = yxVal - yx2x;
        return yxVal;
    }

    private void getLogger() {
         Handler hf = new ConsoleHandler();
         logger.addHandler(hf);
    }

    private void getSoaLogger() throws Exception {
        //Context ctx = new InitialContext();

        //soaLogger = (Soalogger)ctx.lookup("StockmannSoaLogger#stockmann.com.logging.SoaLogger");
    }

    private FileSearch getInitFileSearch() {
        try {
        Resource resource = new ClassPathResource("/SoaloggerWebConf.xml");
        BeanFactory factory = new XmlBeanFactory(resource);
        return (FileSearch)factory.getBean("FileSearch");
        } catch (Exception exp) {
            return null;
        }
    }


}
