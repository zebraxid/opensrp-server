/**
 * 
 */
package org.opensrp.register.mcare.service;

import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_BNF;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC;

import java.util.List;

import org.opensrp.connector.etl.service.RegisterApiService;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.repository.AllActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author proshanto
 */
@Service
public class RegisterService {
	
	@Autowired
	private AllActions allActions;
	
	@Autowired
	private AllMothers allMothers;
	
	@Autowired
	private AllChilds allChilds;
	
	@Autowired
	private HealthSchedulerService scheduler;
	
	@Autowired
	private RegisterApiService registerApiService;
	
	void deleteMotherAndActionAndUnenrollSchedule(String provider, String caseId) {
		Mother mother = allMothers.findByCaseId(caseId);
		allMothers.remove(mother);
		List<Action> ancs = allActions.findAlertByANMIdEntityIdScheduleName(provider, caseId, SCHEDULE_ANC);
		for (Action anc : ancs) {
			allActions.remove(anc);
		}
		scheduler.unEnrollFromSchedule(caseId, "", SCHEDULE_ANC);
		
		List<Action> bnfs = allActions.findAlertByANMIdEntityIdScheduleName(provider, caseId, SCHEDULE_BNF);
		for (Action bnf : bnfs) {
			allActions.remove(bnf);
		}
		scheduler.unEnrollFromSchedule(caseId, "", SCHEDULE_BNF);
		
		List<Action> pncs = allActions.findAlertByANMIdEntityIdScheduleName(provider, caseId, SCHEDULE_PNC);
		for (Action pnc : pncs) {
			allActions.remove(pnc);
		}
		scheduler.unEnrollFromSchedule(caseId, "", SCHEDULE_PNC);
		registerApiService.deleteMotherAndRelatedInformation(caseId);
	}
	
	void deleteChildAndActionAndUnenrollSchedule(String caseId) {
		Child child = allChilds.findByCaseId(caseId);
		allChilds.remove(child);
		List<Action> actions = allActions.findByCaseID(caseId);
		for (Action action : actions) {
			allActions.remove(action);
		}
		scheduler.unEnrollFromAllSchedules(caseId);
		registerApiService.deleteChildAndRelatedInformation(caseId);
	}
}
