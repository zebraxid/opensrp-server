package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ELCOSchedulesConstants.ELCO_SCHEDULE_PSRF;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.HHSchedulesConstants.HH_SCHEDULE_CENSUS;

import java.util.List;

import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.repository.AllActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HHSchedulesService {
	
	private static Logger logger = LoggerFactory.getLogger(HHSchedulesService.class.toString());
	
	private HealthSchedulerService scheduler;
	private ScheduleLogService scheduleLogService;
	private AllActions allActions;
	
	@Autowired
	public HHSchedulesService(HealthSchedulerService scheduler,AllActions allActions,ScheduleLogService scheduleLogService)
	{
		this.scheduler = scheduler;
		this.allActions = allActions;
		this.scheduleLogService = scheduleLogService;
	}

	public void enrollIntoMilestoneOfCensus(String entityId, String date,String provider,String instanceId)
	{
	    logger.info(format("Enrolling household into Census schedule. Id: {0}", entityId));
	    
		scheduler.enrollIntoSchedule(entityId, HH_SCHEDULE_CENSUS, date);
		List<Enrollment> e = scheduleLogService.findEnrollmentByCaseIdAndScheduleName(entityId, ELCO_SCHEDULE_PSRF);
		scheduleLogService.saveScheduleLog(BeneficiaryType.elco, entityId, instanceId, provider, ELCO_SCHEDULE_PSRF, ELCO_SCHEDULE_PSRF, AlertStatus.upcoming, e.get(0).getStartOfSchedule(), e.get(0).getEnrolledOn(),ELCO_SCHEDULE_PSRF);
	}
}
