package org.opensrp.register.mcare.service;

import static org.opensrp.common.AllConstants.CommonFormFields.ID;
import static org.opensrp.common.AllConstants.HHRegistrationFields.ELCO_REGISTRATION_SUB_FORM_NAME_CENSUS;

import java.util.Map;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ELCOService {
	private static Logger logger = LoggerFactory.getLogger(ELCOService.class.toString());
	
	private HHService hhService;
	private AllElcos allEcos;

	@Autowired
	public ELCOService(AllHouseHolds allHouseHolds, AllElcos allEcos, HHService hhService)
	{
		this.allEcos = allEcos;
		this.hhService = hhService;
	}
	
	public void registerELCO(FormSubmission submission)
	{
		SubFormData subFormData = submission.getSubFormByName(ELCO_REGISTRATION_SUB_FORM_NAME_CENSUS);
		
		for (Map<String, String> elcoFields : subFormData.instances()) {
			
			Elco elco = allEcos.findByCaseId(elcoFields.get(ID))
					.withPROVIDERID(submission.anmId());
					/*.withGOBHHID(FW_GOBHHID)
					.withJiVitAHHID(FW_JiVitAHHID)
					.withFWWOMFNAME(FW_WOMFNAME)
					.withFWWOMFNAME(FW_WOMLNAME)
					.withFWWOMBID(FW_WOMNID)
					.withFWWOMBID(FW_WOMBID)
					.withFWHUSNAME(FW_HUSNAME)
					.withFWGENDER(FW_GENDER)
					.withFWBIRTHDATE(FW_BIRTHDATE)
					.withFWWOMAGE(FW_WOMAGE)
					.withFWELIGIBLE(FW_ELIGIBLE);*/
			
			allEcos.update(elco);
		}
		
		hhService.registerHouseHold(submission);
		
	}
	
	
}
