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
			Child_FollowupHandler child_FollowupHandler,
			Child_05yrHandler child_05yrHandler,
			ANCVisit1Handler aNCVisit1Handler,
			ANCVisit2Handler aNCVisit2Handler,
			ANCVisit3Handler aNCVisit3Handler,
			ANCVisit4Handler aNCVisit4Handler,
			Elco_FollowupHandler elco_FollowupHandler,
			Elco_RegisterHandler elco_RegisterHandler,
			PNCVisit1Handler pNCVisit1Handler,
			PNCVisit2Handler pNCVisit2Handler,
			PNCVisit3Handler pNCVisit3Handler,
			PNCVisit4Handler pNCVisit4Handler,
			NutritionHandler nutritionHandler,
			InjectablesHandler injectablesHandler,
			AdolescentHealthHandler adolescentHealthHandler,
			DeathRegHandler deathRegHandler
			)
	{
		handlerMap = EasyMap.create(HH_REGISTRATION, (FormSubmissionHandler) hhRegistrationHandler)
					 .put(MEMBERS_REGISTRATION, membersHandler)	
					 .put(BNF_Handler, BNFHandler)
				     .put(Child_FollowupHandler, child_FollowupHandler)
				     .put(Child_05yrHandler, child_05yrHandler)
				     .put(ANCVisit1Handler, aNCVisit1Handler)
				     .put(ANCVisit2Handler, aNCVisit2Handler)
				     .put(ANCVisit3Handler, aNCVisit3Handler)
				     .put(ANCVisit4Handler, aNCVisit4Handler)
				     .put(Elco_FollowupHandler, elco_FollowupHandler)
				     .put(Elco_RegisterHandler, elco_RegisterHandler)
				     .put(PNCVisit1Handler, pNCVisit1Handler)
				     .put(PNCVisit2Handler, pNCVisit2Handler)
				     .put(PNCVisit3Handler, pNCVisit3Handler)
				     .put(PNCVisit4Handler, pNCVisit4Handler)
				     .put(NutritionHandler, nutritionHandler)
				     .put(InjectablesHandler, injectablesHandler)
				     .put(AdolescentHealthHandler, adolescentHealthHandler)
				     .put(DeathRegHandler, deathRegHandler)
                     .map();
	}

	@Override
	public Map<String, FormSubmissionHandler> handlerMapper() {
		return handlerMap;
	}

}