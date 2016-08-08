/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service.handler;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.service.WomanService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BCGHandler implements FormSubmissionHandler {

	private WomanService womanService;

	@Autowired
	public BCGHandler(WomanService womanService) {
		this.womanService = womanService;
	}
	
	@Override
	public void handle(FormSubmission submission) {
		womanService.BCGHandler(submission);	
	}

}
