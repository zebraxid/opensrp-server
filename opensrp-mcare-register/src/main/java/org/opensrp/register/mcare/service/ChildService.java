/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;

import java.util.List;
import java.util.Map;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.service.scheduling.ChildSchedulesService;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildService {
	
	private static Logger logger = LoggerFactory.getLogger(ChildService.class.toString());
	
	private AllChilds allChilds;
	
	private ChildSchedulesService childSchedulesService;
	
	private ScheduleLogService scheduleLogService;
	
	@Autowired
	public ChildService(AllChilds allChilds, ChildSchedulesService childSchedulesService) {
		this.allChilds = allChilds;
		this.childSchedulesService = childSchedulesService;
	}
	
	public void registerChild(FormSubmission submission) {
		Child child = allChilds.findByCaseId(submission.entityId());
		if (child == null) {
			logger.warn(format("Failed to handle Census form as there is no household registered with ID: {0}",
			    submission.entityId()));
			return;
		}
		
		child.withPROVIDERID(submission.anmId());
		child.withINSTANCEID(submission.instanceId());
		
		allChilds.update(child);
		
		//childSchedulesService.enrollIntoMilestoneOfCensus(submission.entityId(),
		//	submission.getField(Date_Of_Reg),submission.anmId(),submission.instanceId());
	}
	
	public String getEntityIdBybrnId(List<String> brnIdList) {
		List<Child> childs = allChilds.findAllChilds();
		
		if (childs == null || childs.isEmpty()) {
			return null;
		}
		
		for (Child household : childs) {
			for (Map<String, String> members : household.MEMBERDETAILS()) {
				if (brnIdList.contains(members.get("FWWOMBID")))
					return household.caseId();
			}
		}
		return null;
	}
	
}
