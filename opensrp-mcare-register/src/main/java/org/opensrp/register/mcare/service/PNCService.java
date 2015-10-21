/**
 * @author julkar nain
 */

package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.register.mcare.service.scheduling.PNCSchedulesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PNCService {

	private static Logger logger = LoggerFactory.getLogger(ANCService.class
			.toString());
	private AllMothers allMothers;
	private PNCSchedulesService pncSchedulesService;
	
	public PNCService(AllMothers allMothers,PNCSchedulesService pncSchedulesService)
	{
		this.allMothers = allMothers;
		this.pncSchedulesService = pncSchedulesService; 
	}
	public void pncVisitOne(FormSubmission submission) {
		Mother mother = allMothers.findByCASEID(submission.entityId());

		if (mother == null) {
			logger.warn(format(
					"Failed to handle PNC-1 as there is no Mother enroll with ID: {0}",
					submission.entityId()));
			return;
		}
		allMothers.update(mother);
	}

	public void pncVisitTwo(FormSubmission submission) {
		Mother mother = allMothers.findByCASEID(submission.entityId());

		if (mother == null) {
			logger.warn(format(
					"Failed to handle PNC-2 as there is no Mother enroll with ID: {0}",
					submission.entityId()));
			return;
		}
		
		allMothers.update(mother);

	}

	public void pncVisitThree(FormSubmission submission) {
		Mother mother = allMothers.findByCASEID(submission.entityId());

		if (mother == null) {
			logger.warn(format(
					"Failed to handle PNC-3 as there is no Mother enroll with ID: {0}",
					submission.entityId()));
			return;
		}

		allMothers.update(mother);
	}
}
