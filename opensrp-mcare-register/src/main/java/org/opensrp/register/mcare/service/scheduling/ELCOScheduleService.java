package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ELCOSchedulesConstants.ELCO_SCHEDULE_PSRF;

import org.opensrp.scheduler.HealthSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ELCOScheduleService {
	
	private static Logger logger = LoggerFactory.getLogger(ELCOScheduleService.class.toString());
	
	private HealthSchedulerService scheduler;
	
	@Autowired
	public ELCOScheduleService(HealthSchedulerService scheduler)
	{
		this.scheduler = scheduler;
	}
	
	public void enrollIntoMilestoneOfPSRF(String caseId, String date)
	{
	    logger.info(format("Enrolling Elco into PSRF schedule. Id: {0}", caseId));
	    
		scheduler.enrollIntoSchedule(caseId, ELCO_SCHEDULE_PSRF, date);
	}
	
}
