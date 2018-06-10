package org.opensrp.register.mcare.visit.activity.service;

import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_BNF;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC;

import java.util.List;

import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.repository.AllActions;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ActionAndScheduleActivityService {
	
	@Autowired
	private AllActions allActions;
	
	@Autowired
	private AllChilds allChilds;
	
	@Autowired
	private HealthSchedulerService scheduler;
	
	protected void deleteANCActionAndUnenrollScheduleByCaseId(String provider, String caseId) {
		List<Action> ancs = allActions.findAlertByANMIdEntityIdScheduleName(provider, caseId, SCHEDULE_ANC);
		for (Action anc : ancs) {
			allActions.remove(anc);
		}
		scheduler.unEnrollFromSchedule(caseId, "", SCHEDULE_ANC);
	}
	
	protected void deleteBNFActionAndUnenrollScheduleByCaseId(String provider, String caseId) {
		List<Action> bnfs = allActions.findAlertByANMIdEntityIdScheduleName(provider, caseId, SCHEDULE_BNF);
		for (Action bnf : bnfs) {
			allActions.remove(bnf);
		}
		scheduler.unEnrollFromSchedule(caseId, "", SCHEDULE_BNF);
	}
	
	protected void deletePNCActionAndUnenrollScheduleByCaseId(String provider, String caseId) {
		List<Action> pncs = allActions.findAlertByANMIdEntityIdScheduleName(provider, caseId, SCHEDULE_PNC);
		for (Action pnc : pncs) {
			allActions.remove(pnc);
		}
		scheduler.unEnrollFromSchedule(caseId, "", SCHEDULE_PNC);
	}
	
	protected void deleteAllActionAndUnenrollScheduleByCaseId(String caseId) {
		List<Action> actions = allActions.findByCaseID(caseId);
		for (Action action : actions) {
			allActions.remove(action);
		}
		scheduler.unEnrollFromAllSchedules(caseId);
	}
}
