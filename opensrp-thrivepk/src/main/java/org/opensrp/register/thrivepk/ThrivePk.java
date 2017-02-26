package org.opensrp.register.thrivepk;

import org.opensrp.service.formSubmission.handler.HandlerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThrivePk {
	
	@Autowired
	public ThrivePk(HandlerMapper hm, FormSubmissionHandler fsH) {
		hm.addCustomFormSubmissionHandler("child_enrollment", fsH);
		hm.addCustomFormSubmissionHandler("child_followup", fsH);
	}
	
}
