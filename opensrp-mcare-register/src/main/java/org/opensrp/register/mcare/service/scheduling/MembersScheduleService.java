/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MemberScheduleConstants.*;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration.duration;

import org.joda.time.LocalDate;
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
	
	public void enrollimmediateMembersBNFVisit(String entityId,String provider,String date,String instanceId) {       
        logger.info(format("Enrolling Members with Entity id:{0} to Members schedule immediate BNFVisit, milestone: {1}.", entityId, IMD_SCHEDULE_Woman_BNF));
        scheduler.enrollIntoSchedule(entityId, IMD_SCHEDULE_Woman_BNF, date);
        scheduleLogService.createImmediateScheduleAndScheduleLog(entityId, date, provider, instanceId, BeneficiaryType.members, SCHEDULE_Woman_BNF, duration,IMD_SCHEDULE_Woman_BNF);
    }
	
	public void enrollMembersBNFVisit(String caseId,String provider,String date,String instanceId) {
		logger.info(format("Enrolling Elco into PSRF schedule. Id: {0}", caseId));	    
		scheduler.enrollIntoSchedule(caseId, SCHEDULE_Woman_BNF, date);
		scheduleLogService.createNewScheduleLogandUnenrollImmediateSchedule(caseId, date, provider, instanceId, IMD_SCHEDULE_Woman_BNF, SCHEDULE_Woman_BNF, BeneficiaryType.members, duration);
	}
	
	public void enrollMembersMeaslesVisit(String entityId,String provider,String date) {       
        logger.info(format("Enrolling Members with Entity id:{0} to Members schedule Members measles, milestone: {1}.", entityId, SCHEDULE_Woman_Measles));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman_Measles, date);
    }
	
	public void enrollMembersTTVisit(String entityId,String provider,String date) {       
        logger.info(format("Enrolling Members with Entity id:{0} to Members schedule Members TT_1, milestone: {1}.", entityId, SCHEDULE_Woman_1));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman_1, date);
  
    }
	
	public void enrollTT1_Visit(String entityId,String provider,String date) {     
        logger.info(format("Enrolling Members with Entity id:{0} to Members schedule Members TT_2, milestone: {1}.", entityId, SCHEDULE_Woman_2));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman_2, date);
  
    }
	
	public void enrollTT2_Visit(String entityId,String provider,String date) {
        logger.info(format("Enrolling Members with Entity id:{0} to Members schedule Members TT_3, milestone: {1}.", entityId, SCHEDULE_Woman_3));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman_3, date);
  
    }
	
	public void enrollTT3_Visit(String entityId,String provider,String date) {
        logger.info(format("Enrolling Members with Entity id:{0} to Members schedule Members TT_4, milestone: {1}.", entityId, SCHEDULE_Woman_4));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman_4, date);
  
    }
	
	public void enrollTT4_Visit(String entityId,String provider,String date) {      
        logger.info(format("Enrolling Members with Entity id:{0} to Members schedule Members TT_5, milestone: {1}.", entityId, SCHEDULE_Woman_5));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman_5, date);
  
    }
	
	public void enrollChildVisit(String entityId,String provider, String scheduleName, String date) {       
        logger.info(format("Enrolling Child with Entity id:{0} to schedule & milestone: {1}.", entityId, scheduleName));
        scheduler.enrollIntoSchedule(entityId, scheduleName, date);
  
    }
	
	public void enrollimmediateChildBcg(String entityId,String provider,String date,String instanceId) {       
        logger.info(format("Enrolling Members with Entity id:{0} to Members schedule immediate child_bcg, milestone: {1}.", entityId, IMD_child_bcg));
        scheduler.enrollIntoSchedule(entityId, IMD_child_bcg, date);
        scheduleLogService.createImmediateScheduleAndScheduleLog(entityId, date, provider, instanceId, BeneficiaryType.members, child_vaccination_bcg, duration, IMD_child_bcg);
    }
	
	public void enrollChildBcgVisit(String caseId,String provider,String date,String instanceId) {
		logger.info(format("Enrolling Child into Bcg schedule. Id: {0}", caseId));	    
		scheduler.enrollIntoSchedule(caseId, child_vaccination_bcg, date);
		scheduleLogService.createNewScheduleLogandUnenrollImmediateSchedule(caseId, date, provider, instanceId, IMD_child_bcg, child_vaccination_bcg, BeneficiaryType.members, duration);
	}
	
	public void enrollimmediateChildOpv0(String entityId,String provider,String date,String instanceId) {       
        logger.info(format("Enrolling Members with Entity id:{0} to Members schedule immediate child_opv0, milestone: {1}.", entityId, IMD_child_opv0));
        scheduler.enrollIntoSchedule(entityId, IMD_child_opv0, date);
        scheduleLogService.createImmediateScheduleAndScheduleLog(entityId, date, provider, instanceId, BeneficiaryType.members, child_vaccination_opv0, duration, IMD_child_opv0);
    }
	
	public void enrollChildOpv0Visit(String caseId,String provider,String date,String instanceId) {
		logger.info(format("Enrolling child into opv0 schedule. Id: {0}", caseId));	    
		scheduler.enrollIntoSchedule(caseId, child_vaccination_opv0, date);
		scheduleLogService.createNewScheduleLogandUnenrollImmediateSchedule(caseId, date, provider, instanceId, IMD_child_opv0, child_vaccination_opv0, BeneficiaryType.members, duration);
	}
    
    public void unEnrollFromAllSchedules(String entityId) {
        scheduler.unEnrollFromAllSchedules(entityId);
    }

    public void unEnrollFromSchedule(String entityId, String anmId, String scheduleName) {
        logger.info(format("Un-enrolling Members with Entity id:{0} from schedule: {1}", entityId, scheduleName));
        scheduler.unEnrollFromSchedule(entityId, anmId, scheduleName);
    }
    
    public void unEnrollAndCloseSchedule(String entityId, String anmId, String scheduleName, LocalDate completionDate) {
        logger.info(format("Un-enrolling Members with Entity id:{0} from schedule: {1}", entityId, scheduleName));
        scheduler.unEnrollAndCloseSchedule(entityId, anmId, scheduleName, completionDate);
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

