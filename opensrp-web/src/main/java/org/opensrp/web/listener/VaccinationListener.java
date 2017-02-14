package org.opensrp.web.listener;

import java.util.List;

import org.opensrp.camp.dao.CampDate;
import org.opensrp.camp.service.CampDateService;
import org.opensrp.register.mcare.service.VaccinationService;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.service.DataCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class VaccinationListener {
	private static Logger logger = LoggerFactory.getLogger(VaccinationListener.class.toString());
	private CampDateService campDateService;
	private VaccinationService vaccinationService;
	private AllActions allActions;
	@Autowired
	private DataCountService dataCountService;
	
	
	@Autowired
    public void setAllActions(AllActions allActions) {
    	this.allActions = allActions;
    }

	@Autowired
    public void setVaccinationService(VaccinationService vaccinationService) {
    	this.vaccinationService = vaccinationService;
    }

	@Autowired
    public void setCampDateService(CampDateService campDateService) {
    	this.campDateService = campDateService;
    }
	/**
	 * This listener method called in a given time such as 6PM in everyday.
	 * Update status as Completed all today's camp.
	 * Update missed count for a vaccination, who is not pesent in the camp.
	 * 
	 * 
	 * */

	public void vaccinationListenerForCampDateUpdateAndVaccineMissedCount() {	
		logger.info("vaccinationListener called");
		List<CampDate> campDates =campDateService.findCampByTodayForVaccinationListener();
		if(campDates !=null){
			for (CampDate campDate : campDates) {
				List<Action> actions = allActions.listOfEligibleClientForVaccine(campDate.getHealth_assistant(),campDate.getSession_name());
				for (Action action : actions) {
					try{
						vaccinationService.updateVaccineMissedCount(action.anmIdentifier(), action.caseId(), action.data().get("scheduleName"));
						campDate.setStatus("Completed");
						campDate.setId(campDate.getId());
						campDate.setRevision(campDate.getRevision());
						campDateService.edit(campDate);
					}catch(Exception e){
						e.printStackTrace();
					}
	            }
	        }
		}
		
	}
	
	public void vaccinationCounterForSendingToDHIS2() {	
		logger.info("vaccinationCounter called for SendingToDHIS2");
		
		try{
			dataCountService.getVaccineCountForSendingToDHIS2("all","2016-10-01","2016-10-31");
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
}
