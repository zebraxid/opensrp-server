/**
 * @author julkar nain 
 */
package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_3;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_4;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_DELIVERY_PLAN;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_EDD;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_HB_TEST_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_IFA_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_LAB;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_TT_1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Weeks;
import org.opensrp.common.util.DateUtil;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.repository.AllActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ANCSchedulesService {
	
	private static Logger logger = LoggerFactory.getLogger(ANCSchedulesService.class.toString());
	
	private static final String[] NON_ANC_SCHEDULES = { SCHEDULE_EDD, SCHEDULE_LAB, SCHEDULE_TT_1, SCHEDULE_IFA_1,
	        SCHEDULE_HB_TEST_1, SCHEDULE_DELIVERY_PLAN };
	
	private HealthSchedulerService scheduler;
	
	private AllActions allActions;
	
	private ScheduleLogService scheduleLogService;
	
	@Autowired
	public ANCSchedulesService(HealthSchedulerService scheduler, AllActions allActions, ScheduleLogService scheduleLogService) {
		this.scheduler = scheduler;
		this.allActions = allActions;
		this.scheduleLogService = scheduleLogService;
	}
	
	public void enrollMother(String caseId, LocalDate referenceDateForSchedule, String provider, String instanceId,
	                         String startDate) {
		/*for (String schedule : NON_ANC_SCHEDULES) {
			scheduler.enrollIntoSchedule(caseId, schedule, referenceDateForSchedule);
		}*/
		enrollIntoCorrectMilestoneOfANCCare(caseId, referenceDateForSchedule, provider, instanceId, startDate);
	}
	
	/**
	 * Create ANC Schedule depends on LMP Date
	 * 
	 * @param entityId form entity Id
	 * @param referenceDateForSchedule LMP Date convert to Local Date
	 * @param provider FW user name
	 * @param instanceId form instance id
	 * @param startDate LMP Date as String format
	 */
	private void enrollIntoCorrectMilestoneOfANCCare(String entityId, LocalDate referenceDateForSchedule, String provider,
	                                                 String instanceId, String startDate) {
		String milestone = null;
		DateTime ancStartDate = null;
		DateTime ancExpireDate = null;
		AlertStatus alertStaus = null;
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(startDate);
		}
		catch (ParseException e) {
			logger.info("Date parse exception:" + e.getMessage());
		}
		System.err.println("startDate:" + startDate);
		DateTime start = new DateTime(date);
		
		long datediff = ScheduleLogService.getDaysDifference(start);
		System.err.println("start:" + start + " datediff:" + datediff);
		if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(23).toPeriod())) {
			//161
			milestone = SCHEDULE_ANC_1;
			if (DateUtil
			        .isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(8).toPeriod().minusDays(6))) {
				alertStaus = AlertStatus.normal;
				ancStartDate = new DateTime(start);
				ancExpireDate = new DateTime(start).plusDays(DateTimeDuration.ANC1NORMALEND);
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(8).toPeriod()
			        .minusDays(1))) {
				alertStaus = AlertStatus.upcoming;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC1UPCOMINGSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC1UPCOMINGEND);
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(23).toPeriod()
			        .minusDays(1))) {
				//160
				alertStaus = AlertStatus.urgent;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC1URGENTSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC1URGENTEND);
				System.err.println("from anc1 urgent");
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(23).toPeriod())) {
				//162
				alertStaus = AlertStatus.expired;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC1EXPIREDSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC1EXPIREDEND);
				System.err.println("from anc1 expi=red");
			} else {
				logger.info("Form anc1");
			}
			scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone, BeneficiaryType.mother,
			    alertStaus, ancStartDate, ancExpireDate);
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(31).toPeriod())) {
			//217
			milestone = SCHEDULE_ANC_2;
			if (datediff == -162) {
				System.err.println("from 2 to 1");
				alertStaus = AlertStatus.expired;
				//ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC1EXPIREDEND);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC1EXPIREDEND);
				ancStartDate = ancExpireDate;
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, SCHEDULE_ANC_1,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(24).toPeriod()
			        .minusDays(1))) {//167
				alertStaus = AlertStatus.upcoming;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC2UPCOMINGSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC2UPCOMINGEND);
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(31).toPeriod()
			        .minusDays(1))) {//216
				alertStaus = AlertStatus.urgent;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC2URGENTSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC2URGENTEND);
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(31).toPeriod())) {
				//217
				alertStaus = AlertStatus.expired;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC2EXPIREDSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC2EXPIREDEND);
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			} else {
				logger.info("Form anc2");
			}
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod())) {
			//245
			milestone = SCHEDULE_ANC_3;
			if (datediff == -218) {
				alertStaus = AlertStatus.expired;
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC2EXPIREDEND);
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, SCHEDULE_ANC_2,
				    BeneficiaryType.mother, alertStaus, ancExpireDate, ancExpireDate);
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(32).toPeriod()
			        .minusDays(1))) {
				//223
				alertStaus = AlertStatus.upcoming;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC3UPCOMINGSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC3UPCOMINGEND);
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod()
			        .minusDays(1))) {
				//244
				alertStaus = AlertStatus.urgent;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC3URGENTSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC3URGENTEND);
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod())) {
				//245
				alertStaus = AlertStatus.expired;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC3EXPIREDSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC3EXPIREDEND);
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			} else {
				logger.info("Form anc3");
			}
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod())) {
			// 307
			milestone = SCHEDULE_ANC_4;
			if (datediff == -246) {
				alertStaus = AlertStatus.expired;
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC3EXPIREDEND);
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, SCHEDULE_ANC_3,
				    BeneficiaryType.mother, alertStaus, ancExpireDate, ancExpireDate);
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(36).toPeriod()
			        .minusDays(1))) {
				//251
				alertStaus = AlertStatus.upcoming;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC4UPCOMINGSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC4UPCOMINGEND);
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod()
			        .minusDays(2))) {
				//306
				alertStaus = AlertStatus.urgent;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC4URGENTSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC4URGENTEND);
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod()
			        .minusDays(1))) {
				//307
				alertStaus = AlertStatus.expired;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC4EXPIREDSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC4EXPIREDEND);
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
				
			} else {
				logger.info("Form anc4");
			}
			
		} else {
			logger.info("ANC Schedule out of Date of case id" + entityId);
			scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, SCHEDULE_ANC_4,
			    BeneficiaryType.mother, alertStaus, new DateTime(start).plusDays(308), new DateTime(start).plusDays(308));
		}
		
		logger.info(format("Enrolling ANC with Entity id:{0} to ANC schedule, milestone: {1}.", entityId, milestone));
		scheduler.enrollIntoSchedule(entityId, SCHEDULE_ANC, milestone, referenceDateForSchedule.toString());
		
	}
	
	public void fullfillSchedule(String caseID, String scheduleName, String instanceId, long timestamp) {
		try {
			scheduleLogService.fullfillSchedule(caseID, scheduleName, instanceId, timestamp);
			logger.info("fullfillSchedule a Schedule with id : " + caseID);
		}
		catch (Exception e) {
			logger.info("Does not fullfill a schedule:" + e.getMessage());
		}
	}
	
	public void unEnrollFromAllSchedules(String entityId) {
		scheduler.unEnrollFromAllSchedules(entityId);
	}
	
	private void unEnrollFromSchedule(String entityId, String anmId, String scheduleName) {
		logger.info(format("Un-enrolling ANC with Entity id:{0} from schedule: {1}", entityId, scheduleName));
		scheduler.unEnrollFromSchedule(entityId, anmId, scheduleName);
	}
	
	public void fullfillMilestone(String entityId, String providerId, String scheduleName, LocalDate completionDate) {
		try {
			scheduler.fullfillMilestone(entityId, providerId, scheduleName, completionDate);
			logger.info("fullfillMilestone with id: :" + entityId);
		}
		catch (Exception e) {
			logger.info("Does not a fullfillMilestone :" + e.getMessage());
		}
	}
	
	public void enrollANCSchedule() {
		
	}
	
}
