package org.opensrp.register.mcare.service.handler;

import java.util.Map;

import org.opensrp.common.util.EasyMap;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.opensrp.service.formSubmission.handler.IHandlerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static org.opensrp.common.AllConstants.Form.*;

@Service
public class HandlerMapper implements IHandlerMapper {

	private final Map<String, FormSubmissionHandler> handlerMap;
	
	@Autowired
	public HandlerMapper(  
			AllFormSubmissions formSubmissionsRepository,
			HHRegistrationHandler hhRegistrationHandler,
			ELCOHandler elcoHandler,
			PSRFHandler psrfHandler)
	{
		handlerMap = EasyMap.create(HH_REGISTRATION, (FormSubmissionHandler) hhRegistrationHandler)
				     .put(ELCO_REGISTRATION, elcoHandler)
				     .put(PSRF_FORM, psrfHandler)
                     .map();
	}

	@Override
	public Map<String, FormSubmissionHandler> handlerMapper() {
		return handlerMap;
	}

}
