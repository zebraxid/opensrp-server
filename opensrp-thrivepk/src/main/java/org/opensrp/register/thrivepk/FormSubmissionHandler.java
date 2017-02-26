package org.opensrp.register.thrivepk;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.thrivepk.AllSmsHistory;
import org.opensrp.service.formSubmission.handler.CustomFormSubmissionHandler;
import org.springframework.stereotype.Component;

@Component
public class FormSubmissionHandler implements CustomFormSubmissionHandler{
	AllSmsHistory allSH;
	
	public FormSubmissionHandler(AllSmsHistory allSH) {
		this.allSH = allSH;
	}
	
	@Override
	public void handle(FormSubmission submission) {
		// TODO Auto-generated method stub
		
	}

}
