package org.opensrp.web.controller;

import org.motechproject.scheduler.domain.MotechEvent;
import org.opensrp.connector.openmrs.constants.OpenmrsConstants;
import org.opensrp.connector.openmrs.schedule.OpenmrsSyncerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/adminTasks")
public class AdminTaskController {
	private OpenmrsSyncerListener openmrsSync;
	private String token;
	
	@Autowired
	public AdminTaskController(@Value("#{opensrp['admin.task.token']}") String token, OpenmrsSyncerListener openmrsSync) {
		this.token = token;
		this.openmrsSync = openmrsSync;
	}

	@RequestMapping("syncDrugs")
	public @ResponseBody String syncDrugs(@RequestParam("taskToken") String taskToken) {
		if(token.equalsIgnoreCase(taskToken)){
			openmrsSync.pullDataFromOpenMRS(new MotechEvent(OpenmrsConstants.SCHEDULER_OPENMRS_DATA_PULL_SUBJECT));
			return "OK";
		}
		else {
			return "UNAUTHORIZED";
		}
	}
}
