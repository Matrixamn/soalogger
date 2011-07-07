/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmann.soa.logging.web.soaloggerweb.app;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.JobDetail;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;

/**
 *
 * @author katvtuo
 */
public class SoaLoggerAppBean {

    private Scheduler scheduler;
    private Logger logger = Logger.getLogger(SoaLoggerAppBean.class.getName());
    private boolean scheduled = false;
    private long triggerSeconds = 0;
    private int triggerMinutes = 0;
    private int maxQueryResults = 1500;
    /** Creates a new instance of SoaLoggerAppBean */
    public SoaLoggerAppBean() {
        Handler hf = new ConsoleHandler();
         logger.addHandler(hf);
    }


    public void scheduleCleaning() {
        try {

            SimpleTrigger trigger = new SimpleTrigger("minuteTrigger",
                                            null,
                                            new Date(),
                                            null,
                                            SimpleTrigger.REPEAT_INDEFINITELY, getTriggerSeconds() * 1000L);

            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            scheduler = schedFact.getScheduler();

            JobDetail cleaningJob = new JobDetail("Cleaning job", null, LoggerCleaningJob.class);

            scheduler.start();

            scheduler.scheduleJob(cleaningJob, trigger);
            scheduled = true;
            logger.info("Job scheduled");
        } catch (Exception exp) {
            scheduled = false;
            exp.printStackTrace();
            logger.severe("Unable to schedule cleaning job : " + exp.toString());
        }
    }
    /**
     * @return the scheduler
     */
    public Scheduler getScheduler() {
        return scheduler;
    }


    public void stopSchedule() {
        try {
            this.scheduler.shutdown();
            this.scheduled = false;
            logger.info("Job finished");
        } catch (Exception exp) {
            exp.printStackTrace();
            logger.severe("Unable to shutdown scheduler : " + exp.toString());
        }
    }
    /**
     * @param scheduler the scheduler to set
     */
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * @return the scheduled
     */
    public boolean isScheduled() {
        return scheduled;
    }

    /**
     * @param scheduled the scheduled to set
     */
    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    /**
     * @return the triggerSeconds
     */
    public long getTriggerSeconds() {
        return this.triggerMinutes * 60;
    }

    /**
     * @param triggerSeconds the triggerSeconds to set
     */
    public void setTriggerSeconds(long triggerSeconds) {
        this.triggerSeconds = triggerSeconds;
    }

    /**
     * @return the triggerMinutes
     */
    public int getTriggerMinutes() {
        return triggerMinutes;
    }

    /**
     * @param triggerMinutes the triggerMinutes to set
     */
    public void setTriggerMinutes(int triggerMinutes) {
        this.triggerMinutes = triggerMinutes;
    }

    /**
     * @return the maxQueryResults
     */
    public int getMaxQueryResults() {
        return maxQueryResults;
    }

    /**
     * @param maxQueryResults the maxQueryResults to set
     */
    public void setMaxQueryResults(int maxQueryResults) {
        this.maxQueryResults = maxQueryResults;
    }

}
