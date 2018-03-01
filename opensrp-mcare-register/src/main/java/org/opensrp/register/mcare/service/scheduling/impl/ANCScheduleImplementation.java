package org.opensrp.register.mcare.service.scheduling.impl;

import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_3;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Weeks;
import org.opensrp.common.util.DateUtil;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ANCScheduleImplementation {
	
	private static Logger logger = LoggerFactory.getLogger(ANCScheduleImplementation.class.toString());
	
	@Autowired
	private ScheduleLogService scheduleLogService;
	
	public String saveANCC1Schedule(DateTime start, String milestone, String entityId, LocalDate referenceDateForSchedule,
	                                String provider, String instanceId, String startDate, boolean isSave) {
		DateTime ancStartDate = null;
		DateTime ancExpireDate = null;
		AlertStatus alertStaus = null;
		if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(8).toPeriod())) {
			alertStaus = AlertStatus.upcoming;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC1UPCOMINGSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC1UPCOMINGEND);
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(23).toPeriod()
		        .plusDays(1))) {
			alertStaus = AlertStatus.urgent;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC1URGENTSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC1URGENTEND);
			System.err.println("from anc1 urgent");
		} else {
			logger.info("Form anc1");
		}
		if (isSave) {
			scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone, BeneficiaryType.mother,
			    alertStaus, ancStartDate, ancExpireDate);
		}
		return alertStaus.name();
	}
	
	public String saveANC2Schedule(long datediff, DateTime start, String milestone, String entityId,
	                               LocalDate referenceDateForSchedule, String provider, String instanceId, String startDate,
	                               boolean isSave) {
		DateTime ancStartDate = null;
		DateTime ancExpireDate = null;
		AlertStatus alertStaus = null;
		if (datediff == DateTimeDuration.LASTDAYOFANC1) {
			alertStaus = AlertStatus.urgent;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC1URGENTSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC1URGENTEND);
			if (isSave) {
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, SCHEDULE_ANC_1,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			}
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(24).toPeriod()
		        .minusDays(1))) {
			alertStaus = AlertStatus.upcoming;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC2UPCOMINGSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC2UPCOMINGEND);
			if (isSave) {
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			}
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(31).toPeriod()
		        .plusDays(1))) {
			alertStaus = AlertStatus.urgent;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC2URGENTSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC2URGENTEND);
			if (isSave) {
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			}
		} else {
			logger.info("Form anc2");
		}
		return alertStaus.name();
	}
	
	public String saveANC3Schedule(long datediff, DateTime start, String milestone, String entityId,
	                               LocalDate referenceDateForSchedule, String provider, String instanceId, String startDate,
	                               boolean isSave) {
		DateTime ancStartDate = null;
		DateTime ancExpireDate = null;
		AlertStatus alertStaus = null;
		if (datediff == DateTimeDuration.LASTDAYOFANC2) {
			alertStaus = AlertStatus.urgent;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC2URGENTSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC2URGENTEND);
			if (isSave) {
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, SCHEDULE_ANC_2,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			}
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(32).toPeriod()
		        .minusDays(1))) {
			
			alertStaus = AlertStatus.upcoming;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC3UPCOMINGSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC3UPCOMINGEND);
			if (isSave) {
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			}
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod()
		        .plusDays(1))) {
			
			alertStaus = AlertStatus.urgent;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC3URGENTSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC3URGENTEND);
			if (isSave) {
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			}
		} else {
			logger.info("Form anc3");
		}
		return alertStaus.name();
	}
	
	public String saveANC4Schedule(long datediff, DateTime start, String milestone, String entityId,
	                               LocalDate referenceDateForSchedule, String provider, String instanceId, String startDate,
	                               boolean isSave) {
		DateTime ancStartDate = null;
		DateTime ancExpireDate = null;
		AlertStatus alertStaus = null;
		if (datediff == DateTimeDuration.LASTDAYOFAN3) {
			alertStaus = AlertStatus.urgent;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC3URGENTSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC3URGENTEND);
			if (isSave) {
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, SCHEDULE_ANC_3,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			}
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(36).toPeriod()
		        .minusDays(1))) {
			
			alertStaus = AlertStatus.upcoming;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC4UPCOMINGSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC4UPCOMINGEND);
			if (isSave) {
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			}
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod()
		        .minusDays(2))) {
			
			alertStaus = AlertStatus.urgent;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC4URGENTSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC4URGENTEND);
			if (isSave) {
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			}
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod()
		        .minusDays(1))) {
			
			alertStaus = AlertStatus.expired;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC4EXPIREDSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC4EXPIREDEND);
			if (isSave) {
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone,
				    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
			}
			
		} else {
			logger.info("Form anc4");
		}
		return alertStaus.name();
	}
	
}
