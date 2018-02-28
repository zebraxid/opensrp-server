package org.opensrp.register.mcare.service.scheduling.impl;

import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;
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
import org.springframework.stereotype.Service;

@Service
public class ANC4ScheduleServiceImpl {
	
	private static Logger logger = LoggerFactory.getLogger(ANC4ScheduleServiceImpl.class.toString());
	
	@Autowired
	private ScheduleLogService scheduleLogService;
	
	public ANC4ScheduleServiceImpl() {
		
	}
	
	public String saveSchedule(long datediff, DateTime start, String milestone, String entityId,
	                           LocalDate referenceDateForSchedule, String provider, String instanceId, String startDate) {
		DateTime ancStartDate = null;
		DateTime ancExpireDate = null;
		AlertStatus alertStaus = null;
		if (datediff == DateTimeDuration.LASTDAYOFAN3) {
			alertStaus = AlertStatus.urgent;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC3URGENTSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC3URGENTEND);
			scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, SCHEDULE_ANC_3,
			    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(36).toPeriod()
		        .minusDays(1))) {
			
			alertStaus = AlertStatus.upcoming;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC4UPCOMINGSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC4UPCOMINGEND);
			scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone, BeneficiaryType.mother,
			    alertStaus, ancStartDate, ancExpireDate);
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod()
		        .minusDays(2))) {
			
			alertStaus = AlertStatus.urgent;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC4URGENTSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC4URGENTEND);
			scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone, BeneficiaryType.mother,
			    alertStaus, ancStartDate, ancExpireDate);
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod()
		        .minusDays(1))) {
			
			alertStaus = AlertStatus.expired;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC4EXPIREDSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC4EXPIREDEND);
			scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone, BeneficiaryType.mother,
			    alertStaus, ancStartDate, ancExpireDate);
			
		} else {
			logger.info("Form anc4");
		}
		return alertStaus.name();
	}
	
}
