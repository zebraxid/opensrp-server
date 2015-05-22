package org.opensrp.register.mcare.service.scheduling;

import static org.opensrp.register.mcare.OpenSRPScheduleConstants.HHSchedulesConstants.HH_SCHEDULE_CENSUS;

import org.opensrp.scheduler.HealthSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HHSchedulesService {
	
	private HealthSchedulerService scheduler;
	@Autowired
	public HHSchedulesService(HealthSchedulerService scheduler)
	{
		this.scheduler = scheduler;
	}

	public void enrollIntoMilestoneOfCensus(String entityId, String date)
	{
		scheduler.enrollIntoSchedule(entityId, HH_SCHEDULE_CENSUS, date);
		
	}
}
