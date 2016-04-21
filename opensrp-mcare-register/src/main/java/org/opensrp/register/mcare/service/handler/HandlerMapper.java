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
			BCGHandler BCGHandler,
			IPVHandler IPVHandler,
			Measles2Handler Measles2Handler,
			MRHandler MRHandler,
			OPV0Handler OPV0Handler,
			OPV1Handler OPV1Handler,
			OPV2Handler OPV2Handler,
			OPV3Handler OPV3Handler,
			PCV1Handler PCV1Handler,
			PCV2Handler PCV2Handler,
			PCV3Handler PCV3Handler,
			PENTA1Handler PENTA1Handler,
			PENTA2Handler PENTA2Handler,
			PENTA3Handler PENTA3Handler,
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
				     .put(BCGHandler_, BCGHandler)
				     .put(IPVHandler_ , IPVHandler)
				     .put(Measles2Handler_ , Measles2Handler)
				     .put(MRHandler_ , MRHandler)
				     .put(OPV0Handler_ , OPV0Handler)
				     .put(OPV1Handler_ , OPV1Handler)
				     .put(OPV2Handler_ , OPV2Handler)
				     .put(OPV3Handler_ , OPV3Handler)
				     .put(PCV1Handler_ , PCV1Handler)
				     .put(PCV2Handler_ , PCV2Handler)
				     .put(PCV3Handler_ , PCV3Handler)
				     .put(PENTA1Handler_ , PENTA1Handler)
				     .put(PENTA2Handler_ , PENTA2Handler)
				     .put(PENTA3Handler_ , PENTA3Handler)
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