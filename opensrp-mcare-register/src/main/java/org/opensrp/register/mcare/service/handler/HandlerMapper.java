/**
 * @author Asifur
 */
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
			NewBornHandler newBornHandler,
			GeneralHandler generalHandler,
			FamilyPlanningHandler familyPlanningHandler,
			MeaslesHandler measlesHandler,
			TT1_Handler TT1_Handler,
			TT2_Handler TT2_Handler,
			TT3_Handler TT3_Handler,
			TT4_Handler TT4_Handler,
			TT5_Handler TT5_Handler
			)
	{
		handlerMap = EasyMap.create(HH_REGISTRATION, (FormSubmissionHandler) hhRegistrationHandler)
				     .put(NewBornHandler, newBornHandler)
				     .put(GeneralHandler, generalHandler)
				     .put(FamilyPlanningHandler, familyPlanningHandler)
				     .put(MeaslesHandler, measlesHandler)
				     .put(TT1_Visit_Handler, TT1_Handler)
				     .put(TT2_Visit_Handler, TT2_Handler)
				     .put(TT3_Visit_Handler, TT3_Handler)
				     .put(TT4_Visit_Handler, TT4_Handler)
				     .put(TT5_Visit_Handler, TT5_Handler)
                     .map();
	}

	@Override
	public Map<String, FormSubmissionHandler> handlerMapper() {
		return handlerMap;
	}

}