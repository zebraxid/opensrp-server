/**
 * @author julkar nain 
 */
package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_BNF;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.opensrp.dto.ActionData;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.repository.AllActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BNFSchedulesService {
	private static Logger logger = LoggerFactory
			.getLogger(BNFSchedulesService.class.toString());
	private HealthSchedulerService scheduler;
	private AllActions allActions;
	private ScheduleLogService scheduleLogService;

	@Autowired
	public BNFSchedulesService(HealthSchedulerService scheduler,AllActions allActions,ScheduleLogService scheduleLogService) {
		this.scheduler = scheduler;
		this.allActions = allActions;
		this.scheduleLogService = scheduleLogService;
	}

	public void enrollBNF(String caseId, LocalDate referenceDateForSchedule,String provider,String instanceId,String startDate) {
		logger.info(format("Enrolling Mother into BNF schedule. Id: {0}",
				caseId));

		scheduler.enrollIntoSchedule(caseId, SCHEDULE_BNF,
				referenceDateForSchedule);	
        
		/*List<Action> beforeNewActions = allActions.findAlertByANMIdEntityIdScheduleName(provider, caseId, SCHEDULE_BNF);
		if(beforeNewActions.size() > 0){ 
		scheduleLogService.closeSchedule(caseId,instanceId,beforeNewActions.get(0).timestamp(),SCHEDULE_BNF);
		}
		allActions.addOrUpdateAlert(new Action(caseId, provider, ActionData.createAlert(BeneficiaryType.mother, SCHEDULE_BNF, SCHEDULE_BNF, AlertStatus.upcoming, new DateTime(),  new DateTime().plusDays(DateTimeDuration.bnf))));
		logger.info(format("create psrf from psrf to psrf..", caseId));
		List<Action> afterNewActions = allActions.findAlertByANMIdEntityIdScheduleName(provider, caseId, SCHEDULE_BNF);
		if(afterNewActions.size() > 0){ 
			scheduleLogService.saveScheduleLog(BeneficiaryType.mother, caseId, instanceId, provider, SCHEDULE_BNF, SCHEDULE_BNF, AlertStatus.upcoming, new DateTime(), new DateTime().plusDays(DateTimeDuration.bnf),SCHEDULE_BNF,afterNewActions.get(0).timestamp());
	
		}*/
	}

}
