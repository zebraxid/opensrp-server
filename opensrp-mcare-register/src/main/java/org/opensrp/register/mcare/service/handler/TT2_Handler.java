/**
 * @author Asifur
 */
package org.opensrp.register.mcare.service.handler;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.service.MembersService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TT2_Handler implements FormSubmissionHandler {

	private MembersService membersService;

	@Autowired
	public TT2_Handler(MembersService membersService) {
		this.membersService = membersService;
	}

	@Override
	public void handle(FormSubmission submission) {
		membersService.TT2_Visit(submission);
	}
}