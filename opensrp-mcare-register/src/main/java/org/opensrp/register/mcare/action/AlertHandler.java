package org.opensrp.register.mcare.action;

import static org.motechproject.scheduletracking.api.domain.WindowName.due;
import static org.motechproject.scheduletracking.api.domain.WindowName.earliest;
import static org.motechproject.scheduletracking.api.domain.WindowName.late;
import static org.motechproject.scheduletracking.api.domain.WindowName.max;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.HHSchedulesConstants.*;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MemberScheduleConstants.*;
import static org.opensrp.scheduler.Matcher.any;
import static org.opensrp.scheduler.Matcher.anyOf;
import static org.opensrp.scheduler.Matcher.eq;

import org.opensrp.scheduler.HookedEvent;
import org.opensrp.scheduler.Matcher;
import org.opensrp.scheduler.TaskSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AlertHandler {
	@Autowired
	public AlertHandler(TaskSchedulerService scheduler,
			@Qualifier("AlertCreationAction") HookedEvent alertCreation) {
	
		scheduler.addHookedEvent(
				hhSchedules(),
				any(),
				anyOf(earliest.toString(), due.toString(), late.toString(),
						max.toString()), alertCreation).addExtraData(
				"beneficiaryType", "household");

		scheduler.addHookedEvent(
				membersSchedules(),
				any(),
				anyOf(earliest.toString(), due.toString(), late.toString(),
						max.toString()), alertCreation).addExtraData(
				"beneficiaryType", "members");

		scheduler.toReportaAction(alertCreation);		

	}

	private Matcher hhSchedules() {
		return anyOf(HH_SCHEDULE_CENSUS);

	}
	private Matcher membersSchedules() {
		return anyOf(ELCO_SCHEDULE_PSRF, IMD_ELCO_SCHEDULE_PSRF, SCHEDULE_Woman_BNF, IMD_SCHEDULE_Woman_BNF, 
				child_vaccination_bcg, IMD_child_bcg, SCHEDULE_ANC, SCHEDULE_PNC);
	}

}
