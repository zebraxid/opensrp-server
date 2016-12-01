/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service.handler;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.service.MembersFollowupService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PNCVisit3Handler implements FormSubmissionHandler {

	private MembersFollowupService womanService;

	@Autowired
	public PNCVisit3Handler(MembersFollowupService womanService) {
		this.womanService = womanService;
	}

	@Override
	public void handle(FormSubmission submission) {
		womanService.PNCVisit3(submission);
	}
}
