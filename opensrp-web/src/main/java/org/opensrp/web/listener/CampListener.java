package org.opensrp.web.listener;

import java.util.Date;

import org.opensrp.camp.service.CampDateService;
import org.opensrp.scheduler.repository.AllActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class CampListener {
	private CampDateService campDateService;
	private AllActions allActions;
	
	
	@Autowired
    public void setAllActions(AllActions allActions) {
    	this.allActions = allActions;
    }
	@Autowired
    public void setCampDateService(CampDateService campDateService) {
    	this.campDateService = campDateService;
    }
	public void campDeclareListener() {		
		campDateService.findByTimeStamp();
		
	}
	public void campStartListener() {
		
		System.out.println(new Date() + " This runs in a fixed delay with a initial delay");
		
	}
	
}
