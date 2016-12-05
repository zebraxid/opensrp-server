/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MemberScheduleConstants.*;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration.duration;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.opensrp.common.util.DateUtil;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.scheduler.HealthSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembersScheduleService {

	private static Logger logger = LoggerFactory.getLogger(MembersScheduleService.class.toString());
	private HealthSchedulerService scheduler;
	private ScheduleLogService scheduleLogService;
	
	@Autowired
	public MembersScheduleService(HealthSchedulerService scheduler,ScheduleLogService scheduleLogService)
	{
		this.scheduler = scheduler;	
		this.scheduleLogService = scheduleLogService;
	}
	
	public void enrollIntoCorrectMilestoneOfANCRVCare(String entityId, LocalDate referenceDateForSchedule) {
        String milestone=null;

        if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ZERO.toPeriod().plusDays(168))) {
            milestone = SCHEDULE_ANC_1;
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ZERO.toPeriod().plusDays(224))) {
            milestone = SCHEDULE_ANC_2;
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ZERO.toPeriod().plusDays(252))) {
            milestone = SCHEDULE_ANC_3;
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ZERO.toPeriod().plusDays(960))) {
            milestone = SCHEDULE_ANC_4;
        } else{
        	
        }

        logger.info(format("Enrolling ANC with Entity id:{0} to ANC schedule, milestone: {1}.", entityId, milestone));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_ANC, milestone, referenceDateForSchedule.toString());
    }
	
	public void enrollANCForMother(String entityId, String sch_name, LocalDate referenceDateForSchedule) {
        logger.info(format("Enrolling ANC with Entity id:{0} to ANC schedule, milestone: {1}.", entityId, sch_name));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_ANC, sch_name, referenceDateForSchedule.toString());
    }
	
	public void enrollimmediateMembersVisit(String entityId,String provider,String date,String instanceId,String scheduleName,String ImmediateScheduleName) {       
        logger.info(format("Enrolling Members with Entity id:{0} to Members schedule immediate BNFVisit, milestone: {1}.", entityId, ImmediateScheduleName));
        scheduler.enrollIntoSchedule(entityId, ImmediateScheduleName, date);
        scheduleLogService.createImmediateScheduleAndScheduleLog(entityId, date, provider, instanceId, BeneficiaryType.members, scheduleName, duration, ImmediateScheduleName);
    }
	
	public void enrollAfterimmediateVisit(String caseId,String provider,String date,String instanceId,String scheduleName,String ImmediateScheduleName) {
		logger.info(format("Enrolling Members into schedule. Id: {0}", caseId));	    
		scheduler.enrollIntoSchedule(caseId, scheduleName, date);
		scheduleLogService.createNewScheduleLogandUnenrollImmediateSchedule(caseId, date, provider, instanceId, ImmediateScheduleName, scheduleName, BeneficiaryType.members, duration);
	}
	
	public void enrollWomanTTVisit(String entityId,String provider,String date,String scheduleName) {       
        logger.info(format("Enrolling Members with Entity id:{0} to Members schedule , milestone: {1}.", entityId, scheduleName));
        scheduler.enrollIntoSchedule(entityId, scheduleName, date);
  
    }
	
	public void enrollChildVisit(String entityId,String provider, String scheduleName, String date) {       
        logger.info(format("Enrolling Child with Entity id:{0} to schedule & milestone: {1}.", entityId, scheduleName));
        scheduler.enrollIntoSchedule(entityId, scheduleName, date);
  
    }
    
    public void unEnrollFromAllSchedules(String entityId) {
        scheduler.unEnrollFromAllSchedules(entityId);
    }

    public void unEnrollFromSchedule(String entityId, String anmId, String scheduleName) {
        logger.info(format("Un-enrolling Members with Entity id:{0} from schedule: {1}", entityId, scheduleName));
        scheduler.unEnrollFromSchedule(entityId, anmId, scheduleName);
    }
    
    public void unEnrollFromImmediateSchedule(String entityId, String anmId, String scheduleName, String ImmediateScheduleName) {
        logger.info(format("Un-enrolling Members with Entity id:{0} from schedule: {1}", entityId, scheduleName));
        scheduler.unEnrollFromImmediateSchedule(entityId, anmId, scheduleName, ImmediateScheduleName);
    }
    
    public void unEnrollAndCloseSchedule(String entityId, String anmId, String scheduleName, LocalDate completionDate) {
        logger.info(format("Un-enrolling Members with Entity id:{0} from schedule: {1}", entityId, scheduleName));
        scheduler.unEnrollAndCloseSchedule(entityId, anmId, scheduleName, completionDate);
    }
    
    public void unEnrollAndCloseImmediateSchedule(String entityId, String anmId, String scheduleName,String ImmediateScheduleName, LocalDate completionDate) {
        logger.info(format("Un-enrolling Members with Entity id:{0} from schedule: {1}", entityId, scheduleName));
        scheduler.unEnrollAndCloseImmediateSchedule(entityId, anmId, scheduleName, ImmediateScheduleName, completionDate);
    }
    
    public void fullfillMilestone(String entityId, String providerId, String scheduleName,LocalDate completionDate ){
    	try{
    		scheduler.fullfillMilestone(entityId, providerId, scheduleName, completionDate);
    		logger.info("fullfillMilestone with id: :"+entityId);
    	}catch(Exception e){
    		logger.info("Does not a fullfillMilestone :"+e.getMessage());
    	}
    }
    
    public void fullfillSchedule(String caseID, String scheduleName, String instanceId, long timestamp){
    	try{
	    	scheduleLogService.fullfillSchedule(caseID, scheduleName, instanceId, timestamp);
	    	logger.info("fullfillSchedule a Schedule with id : "+caseID);
    	}catch(Exception e){
    		logger.info("Does not fullfill a schedule:"+e.getMessage());
    	}
    }

}

