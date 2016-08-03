/**
 * @author Asifur
 */
package org.opensrp.register.mcare.service.handler;

import static org.opensrp.common.AllConstants.Form.Child_Registration;

import java.util.Map;

import org.opensrp.common.util.EasyMap;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.opensrp.service.formSubmission.handler.IHandlerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HandlerMapper implements IHandlerMapper {
	
	private final Map<String, FormSubmissionHandler> handlerMap;
	
	@Autowired
	public HandlerMapper(AllFormSubmissions formSubmissionsRepository, ChildRegistrationHandler childRegistrationHandler) {
		handlerMap = EasyMap.create(Child_Registration, (FormSubmissionHandler) childRegistrationHandler).map();
	}
	
	@Override
	public Map<String, FormSubmissionHandler> handlerMapper() {
		return handlerMap;
	}
	
}
