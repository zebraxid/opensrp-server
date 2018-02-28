package org.opensrp.register.mcare.service.scheduling.impl;

import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;

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
public class ANC1ScheduleServiceImpl {
	
	private static Logger logger = LoggerFactory.getLogger(ANC1ScheduleServiceImpl.class.toString());
	
	@Autowired
	private ScheduleLogService scheduleLogService;
	
	public ANC1ScheduleServiceImpl() {
		
	}
	
	public String saveSchedule(DateTime start, String milestone, String entityId, LocalDate referenceDateForSchedule,
	                           String provider, String instanceId, String startDate) {
		DateTime ancStartDate = null;
		DateTime ancExpireDate = null;
		AlertStatus alertStaus = null;
		if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(8).toPeriod().minusDays(6))) {
			alertStaus = AlertStatus.normal;
			ancStartDate = new DateTime(start);
			ancExpireDate = new DateTime(start).plusDays(DateTimeDuration.ANC1NORMALEND);
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(8).toPeriod()
		        .minusDays(1))) {
			alertStaus = AlertStatus.upcoming;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC1UPCOMINGSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC1UPCOMINGEND);
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(23).toPeriod()
		        .plusDays(1))) {
			//160
			alertStaus = AlertStatus.urgent;
			ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC1URGENTSTART);
			ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC1URGENTEND);
			System.err.println("from anc1 urgent");
		} else {
			logger.info("Form anc1");
		}
		scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, milestone, BeneficiaryType.mother,
		    alertStaus, ancStartDate, ancExpireDate);
		return alertStaus.name();
	}
	
}
