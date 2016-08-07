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
public class MEMBERSHandler implements FormSubmissionHandler {

	private WomanService womanService;

	@Autowired
	public MEMBERSHandler(WomanService womanService) {
		this.womanService = womanService;
	}
	
	@Override
	public void handle(FormSubmission submission) {
		womanService.registerWoman(submission);	
	}

}
