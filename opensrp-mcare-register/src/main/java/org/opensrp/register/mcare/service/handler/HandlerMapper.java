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
			MEMBERSHandler membersHandler,
			BNFHandler BNFHandler,
			Birth_OutcomeHandler Birth_OutcomeHandler,
			GeneralHandler generalHandler,
			Measles1Handler measles1Handler,
			BCGHandler BCGHandler,
			IPVHandler IPVHandler,
			Measles2Handler Measles2Handler,
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
			TT1_Handler TT1_Handler
			)
	{
		handlerMap = EasyMap.create(HH_REGISTRATION, (FormSubmissionHandler) hhRegistrationHandler)
					 .put(MEMBERS_REGISTRATION, membersHandler)	
					 .put(BNF_Handler, BNFHandler)
					 .put(BirthOutcome_Handler, Birth_OutcomeHandler)
				     .put(GeneralHandler, generalHandler)
				     .put(Measles1Handler, measles1Handler)
				     .put(BCGHandler_, BCGHandler)
				     .put(IPVHandler_ , IPVHandler)
				     .put(Measles2Handler_ , Measles2Handler)
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
				     .put(TT_Visit_Handler, TT1_Handler)
                     .map();
	}

	@Override
	public Map<String, FormSubmissionHandler> handlerMapper() {
		return handlerMap;
	}

}