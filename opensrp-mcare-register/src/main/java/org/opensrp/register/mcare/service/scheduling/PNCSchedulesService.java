/**
 * @author julkar nain 
 */
package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_DELIVERY_PLAN;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_EDD;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_HB_TEST_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_IFA_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_LAB;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_3;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_TT_1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.opensrp.common.util.DateUtil;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration;
import org.opensrp.scheduler.HealthSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PNCSchedulesService {
	
	private static Logger logger = LoggerFactory.getLogger(PNCSchedulesService.class.toString());
	private static final String[] NON_ANC_SCHEDULES = {SCHEDULE_EDD, SCHEDULE_LAB, SCHEDULE_TT_1, SCHEDULE_IFA_1,
        SCHEDULE_HB_TEST_1, SCHEDULE_DELIVERY_PLAN};
	private HealthSchedulerService scheduler;
	private ScheduleLogService scheduleLogService;
	@Autowired
	public PNCSchedulesService(HealthSchedulerService scheduler,ScheduleLogService scheduleLogService){
		this.scheduler = scheduler;
		this.scheduleLogService = scheduleLogService;
	}

    public void enrollPNCRVForMother(String caseId,String instanceId,String provider, LocalDate referenceDateForSchedule,String referenceDate) {
       
        enrollIntoCorrectMilestoneOfPNCRVCare(caseId,instanceId,provider, referenceDateForSchedule,referenceDate);
    }
    private void enrollIntoCorrectMilestoneOfPNCRVCare(String entityId,String instanceId,String provider, LocalDate referenceDateForSchedule,String referenceDate) {
        String milestone=null;
        DateTime startDate = null;
        DateTime expireDate = null;
        
        Date date = null;        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
			date = format.parse(referenceDate);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        DateTime FWBNFDTOO = new DateTime(date);
        
        if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ONE.toPeriod())) {
            milestone = SCHEDULE_PNC_1;
            startDate = new DateTime(FWBNFDTOO);
            expireDate = new DateTime(FWBNFDTOO).plusDays(DateTimeDuration.pnc1);
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.FOUR.toPeriod())) {
            milestone = SCHEDULE_PNC_2;  
            startDate = new DateTime(FWBNFDTOO).plusDays(DateTimeDuration.pnc1);
            expireDate = new DateTime(startDate).plusDays(DateTimeDuration.pnc2);
           
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.SEVEN.plus(1).toPeriod())) {
            milestone = SCHEDULE_PNC_3;
            startDate = new DateTime(FWBNFDTOO).plusDays(DateTimeDuration.pnc2);
            expireDate = new DateTime(startDate).plusDays(DateTimeDuration.pnc3);
        } else{
        	
        }
        scheduleLogService.scheduleCloseAndSave(entityId, instanceId, provider, SCHEDULE_PNC, milestone, BeneficiaryType.mother, AlertStatus.upcoming, startDate, expireDate);

        logger.info(format("Enrolling PNC with Entity id:{0} to PNC schedule, milestone: {1}.", entityId, milestone));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_PNC, milestone, referenceDateForSchedule.toString());
    }
    
    public void enrollPNCForMother(String entityId, String sch_name, LocalDate referenceDateForSchedule) {
        logger.info(format("Enrolling PNC with Entity id:{0} to PNC schedule, milestone: {1}.", entityId, sch_name));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_PNC, sch_name, referenceDateForSchedule.toString());
    }
    
    public void fullfillMilestone(String entityId, String providerId, String scheduleName,LocalDate completionDate ){
    	try{
    		scheduler.fullfillMilestone(entityId, providerId, scheduleName, completionDate);
    		logger.info("Fullfill Milestone with id: :"+entityId);
    	}catch(Exception e){
    		logger.info("Not a fullfillMilestone :"+e.getMessage());
    	}
    }
    
    public void unEnrollFromSchedule(String entityId, String anmId, String scheduleName) {
        logger.info(format("Un-enrolling PNC with Entity id:{0} from schedule: {1}", entityId, scheduleName));
        scheduler.unEnrollFromSchedule(entityId, anmId, scheduleName);
    }
    
    public void unEnrollFromAllSchedules(String entityId) {
    	scheduler.unEnrollFromAllSchedules(entityId);
    }
}
