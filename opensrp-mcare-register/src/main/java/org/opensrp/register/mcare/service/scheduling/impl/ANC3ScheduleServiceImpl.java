package org.opensrp.register.mcare.service.scheduling.impl;

import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_2;

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
public class ANC3ScheduleServiceImpl {
	
	private static Logger logger = LoggerFactory.getLogger(ANC2ScheduleServiceImpl.class.toString());
	
	public ANC3ScheduleServiceImpl() {
		
	}
	
	@Autowired
	private ScheduleLogService scheduleLogService;
	
	public String saveSchedule(long datediff, DateTime start, String milestone, String entityId,
	                           LocalDate referenceDateForSchedule, String provider, String instanceId, String startDate) {
		DateTime ancStartDate = null;
		DateTime ancExpireDate = null;
		AlertStatus alertStaus = null;
		if (datediff == DateTimeDuration.LASTDAYOFANC2) {
			alertStaus = AlertStatus.urgent;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC2URGENTSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC2URGENTEND);
			scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, SCHEDULE_ANC_2,
			    BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(32).toPeriod()
		        .minusDays(1))) {
			
			alertStaus = AlertStatus.upcoming;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC3UPCOMINGSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC3UPCOMINGEND);
			scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone, BeneficiaryType.mother,
			    alertStaus, ancStartDate, ancExpireDate);
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod()
		        .plusDays(1))) {
			
			alertStaus = AlertStatus.urgent;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC3URGENTSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC3URGENTEND);
			scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone, BeneficiaryType.mother,
			    alertStaus, ancStartDate, ancExpireDate);
		} else {
			logger.info("Form anc3");
		}
		return alertStaus.name();
	}
	
}
