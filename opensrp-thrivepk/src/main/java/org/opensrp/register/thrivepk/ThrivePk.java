package org.opensrp.register.thrivepk;

import java.util.concurrent.TimeUnit;

import org.opensrp.register.thrivepk.TelenorContext.Config;
import org.opensrp.scheduler.RepeatingSchedule;
import org.opensrp.scheduler.TaskSchedulerService;
import org.opensrp.service.formSubmission.handler.HandlerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThrivePk {
	
	@Autowired
	public ThrivePk(HandlerMapper hm, TaskSchedulerService scheduler) {
		System.out.println("STARTING SMS SCHEUDLES");
		
		scheduler.startJob(new RepeatingSchedule("SMS_PROCESSOR_SERVICE", 10, TimeUnit.MINUTES, 6, TimeUnit.HOURS));
		scheduler.startJob(new RepeatingSchedule("SMS_PUSH_SERVICE", 10, TimeUnit.MINUTES, 12, TimeUnit.HOURS));
	}
	
}
