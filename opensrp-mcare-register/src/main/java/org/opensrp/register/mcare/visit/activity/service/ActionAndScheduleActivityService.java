package org.opensrp.register.mcare.visit.activity.service;

import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ELCOSchedulesConstants.ELCO_SCHEDULE_PSRF;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_BNF;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC;

import java.util.List;

import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.repository.AllActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ActionAndScheduleActivityService {
	
	private static Logger logger = LoggerFactory.getLogger(ActionAndScheduleActivityService.class.toString());
	
	@Autowired
	private AllActions allActions;
	
	@Autowired
	private AllChilds allChilds;
	
	@Autowired
	private HealthSchedulerService scheduler;
	
	protected void reactivePSRFByCaseId(String provider, String caseId) {
		List<Action> psrfs = allActions.findAlertByANMIdEntityIdScheduleName(provider, caseId, ELCO_SCHEDULE_PSRF);
		for (Action action : psrfs) {
			if (action != null) {
				logger.info("psrf action found at case id: " + caseId);
				action.timestamp(System.currentTimeMillis());
				allActions.update(action);
			} else {
				logger.error("no action found at case id: " + caseId);
			}
		}
		scheduler.unEnrollFromSchedule(caseId, "", SCHEDULE_ANC);
	}
	
	protected void deleteANCActionAndUnenrollScheduleByCaseId(String provider, String caseId) {
		List<Action> ancs = allActions.findAlertByANMIdEntityIdScheduleName(provider, caseId, SCHEDULE_ANC);
		for (Action action : ancs) {
			if (action != null) {
				logger.info("anc action found at case id: " + caseId);
				allActions.remove(action);
			} else {
				logger.error("no action found at case id: " + caseId);
			}
		}
		scheduler.unEnrollFromSchedule(caseId, "", SCHEDULE_ANC);
	}
	
	protected void deleteBNFActionAndUnenrollScheduleByCaseId(String provider, String caseId) {
		List<Action> bnfs = allActions.findAlertByANMIdEntityIdScheduleName(provider, caseId, SCHEDULE_BNF);
		for (Action action : bnfs) {
			if (action != null) {
				logger.info("bnf action found at case id: " + caseId);
				allActions.remove(action);
			} else {
				logger.error("no action found at case id: " + caseId);
			}
		}
		scheduler.unEnrollFromSchedule(caseId, "", SCHEDULE_BNF);
	}
	
	protected void deletePNCActionAndUnenrollScheduleByCaseId(String provider, String caseId) {
		List<Action> pncs = allActions.findAlertByANMIdEntityIdScheduleName(provider, caseId, SCHEDULE_PNC);
		for (Action action : pncs) {
			if (action != null) {
				logger.info("pnc action found at case id: " + caseId);
				allActions.remove(action);
			} else {
				logger.error("no pnc action found at case id: " + caseId);
			}
		}
		scheduler.unEnrollFromSchedule(caseId, "", SCHEDULE_PNC);
	}
	
	protected void deleteAllActionAndUnenrollScheduleByCaseId(String caseId) {
		List<Action> actions = allActions.findByCaseID(caseId);
		for (Action action : actions) {
			if (action != null) {
				logger.info("action found at case id: " + caseId);
				allActions.remove(action);
			} else {
				logger.error("no action found at case id: " + caseId);
			}
		}
		scheduler.unEnrollFromAllSchedules(caseId);
	}
}
