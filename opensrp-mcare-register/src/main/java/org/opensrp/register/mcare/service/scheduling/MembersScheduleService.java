/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MemberScheduleConstants.*;

import org.joda.time.LocalDate;

import org.opensrp.scheduler.HealthSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembersScheduleService {

	private static Logger logger = LoggerFactory.getLogger(MembersScheduleService.class.toString());
	private HealthSchedulerService scheduler;
	
	@Autowired
	public MembersScheduleService(HealthSchedulerService scheduler)
	{
		this.scheduler = scheduler;	
	}
	
	public void enrollWomanMeaslesVisit(String entityId,String provider,String date) {       
        logger.info(format("Enrolling Woman with Entity id:{0} to Woman schedule Woman measles, milestone: {1}.", entityId, SCHEDULE_Woman_Measles));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman_Measles, date);
    }
	
	public void enrollWomanTTVisit(String entityId,String provider,String date) {       
        logger.info(format("Enrolling Woman with Entity id:{0} to Woman schedule Woman TT_1, milestone: {1}.", entityId, SCHEDULE_Woman_1));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman_1, date);
  
    }
	
	public void enrollTT1_Visit(String entityId,String provider,String date) {     
        logger.info(format("Enrolling Woman with Entity id:{0} to Woman schedule Woman TT_2, milestone: {1}.", entityId, SCHEDULE_Woman_2));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman_2, date);
  
    }
	
	public void enrollTT2_Visit(String entityId,String provider,String date) {
        logger.info(format("Enrolling Woman with Entity id:{0} to Woman schedule Woman TT_3, milestone: {1}.", entityId, SCHEDULE_Woman_3));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman_3, date);
  
    }
	
	public void enrollTT3_Visit(String entityId,String provider,String date) {
        logger.info(format("Enrolling Woman with Entity id:{0} to Woman schedule Woman TT_4, milestone: {1}.", entityId, SCHEDULE_Woman_4));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman_4, date);
  
    }
	
	public void enrollTT4_Visit(String entityId,String provider,String date) {      
        logger.info(format("Enrolling Woman with Entity id:{0} to Woman schedule Woman TT_5, milestone: {1}.", entityId, SCHEDULE_Woman_5));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman_5, date);
  
    }
	
	public void enrollChildVisit(String entityId,String provider, String scheduleName, String date) {       
        logger.info(format("Enrolling Child with Entity id:{0} to schedule & milestone: {1}.", entityId, scheduleName));
        scheduler.enrollIntoSchedule(entityId, scheduleName, date);
  
    }
    
    public void unEnrollFromAllSchedules(String entityId) {
        scheduler.unEnrollFromAllSchedules(entityId);
    }

    public void unEnrollFromSchedule(String entityId, String anmId, String scheduleName) {
        logger.info(format("Un-enrolling Woman with Entity id:{0} from schedule: {1}", entityId, scheduleName));
        scheduler.unEnrollFromSchedule(entityId, anmId, scheduleName);
    }
    
    public void fullfillMilestone(String entityId, String providerId, String scheduleName,LocalDate completionDate ){
    	try{
    		scheduler.fullfillMilestone(entityId, providerId, scheduleName, completionDate);
    		logger.info("fullfillMilestone with id: :"+entityId);
    	}catch(Exception e){
    		logger.info("Does not a fullfillMilestone :"+e.getMessage());
    	}
    }

}

