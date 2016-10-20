package org.opensrp.web.listener;

import java.util.Date;
import java.util.List;

import org.opensrp.camp.dao.CampDate;
import org.opensrp.camp.service.CampDateService;
import org.opensrp.register.mcare.service.VaccinationService;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.repository.AllActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class VaccinationListener {
	
	private CampDateService campDateService;
	private VaccinationService vaccinationService;
	private AllActions allActions;
	
	
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

	public void vaccinationListener() {		
		List<CampDate> campDates =campDateService.findCampByToday();
		for (CampDate campDate : campDates) {
			List<Action> actions = allActions.listOfEligibleClientForVaccine(campDate.getHealth_assistant(),campDate.getSession_name());
			for (Action action : actions) {
				try{
					vaccinationService.updateVaccineMissedCount(action.anmIdentifier(), action.caseId(), action.data().get("scheduleName"));
				}catch(Exception e){
					e.printStackTrace();
				}
            }
        }
		
	}
	
}
