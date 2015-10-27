/**
 * @author julkar nain
 */
package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.repository.AllChilds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ChildService {

	private static Logger logger = LoggerFactory.getLogger(ChildService.class
			.toString());
	
	private AllChilds allChilds;
	
	public ChildService(AllChilds allChilds)
	{
		this.allChilds = allChilds;
	}
	
	public void enccOne(FormSubmission submission) {
		Child child = allChilds.findByCaseId(submission.entityId());

		if (child == null) {
			logger.warn(format(
					"Failed to handle ENCC-1 as there is no Child enroll with ID: {0}",
					submission.entityId()));
			return;
		}
		allChilds.update(child);
	}

	public void enccTwo(FormSubmission submission) {
		Child child = allChilds.findByCaseId(submission.entityId());

		if (child == null) {
			logger.warn(format(
					"Failed to handle ENCC-2 as there is no Child enroll with ID: {0}",
					submission.entityId()));
			return;
		}
		allChilds.update(child);
	}

	public void enccThree(FormSubmission submission) {
		Child child = allChilds.findByCaseId(submission.entityId());

		if (child == null) {
			logger.warn(format(
					"Failed to handle ENCC-3 as there is no Child enroll with ID: {0}",
					submission.entityId()));
			return;
		}
		allChilds.update(child);
	}

}
