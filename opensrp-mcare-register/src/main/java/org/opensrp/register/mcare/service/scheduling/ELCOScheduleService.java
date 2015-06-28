package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.HHSchedulesConstants.ELCO_SCHEDULE_PSRF;

import org.opensrp.scheduler.HealthSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ELCOScheduleService {
	
	private static Logger logger = LoggerFactory.getLogger(ELCOScheduleService.class.toString());
	
	private HealthSchedulerService scheduler;
	
	@Autowired
	public ELCOScheduleService(HealthSchedulerService scheduler)
	{
		this.scheduler = scheduler;
	}
	
	public void enrollIntoMilestoneOfPSRF(String entityId, String date)
	{
	    logger.info(format("Enrolling household into Census schedule. Id: {0}", entityId));
	    
		scheduler.enrollIntoSchedule(entityId, ELCO_SCHEDULE_PSRF, date);
	}
	
}
