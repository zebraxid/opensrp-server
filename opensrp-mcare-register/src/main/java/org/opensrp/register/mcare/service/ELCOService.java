package org.opensrp.register.mcare.service;

import static org.opensrp.common.AllConstants.CommonFormFields.ID;
import static org.opensrp.common.AllConstants.HHRegistrationFields.ELCO_REGISTRATION_SUB_FORM_NAME;

import java.util.Map;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_BIRTHDATE;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_WOMLNAME;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_WOMNID;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_WOMFNAME;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_HUSNAME;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_GENDER;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_ELIGIBLE;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_DISPLAY_AGE;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_WOMBID;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_WOMAGE;


@Service
public class ELCOService {
	private static Logger logger = LoggerFactory.getLogger(ELCOService.class.toString());
	
	private AllHouseHolds allHouseHolds;
	private AllElcos allEcos;

	@Autowired
	public ELCOService(AllHouseHolds allHouseHolds, AllElcos allEcos)
	{
		this.allEcos = allEcos;
		this.allHouseHolds = allHouseHolds;
	}
	
	public void registerELCO(FormSubmission submission)
	{
		HouseHold houseHold = allHouseHolds.findByCASEID(submission.entityId());
		SubFormData subFormData = submission.getSubFormByName(ELCO_REGISTRATION_SUB_FORM_NAME);
		
		for (Map<String, String> elcoFields : subFormData.instances()) {
			
			Elco elco = allEcos.findByCaseId(elcoFields.get(ID));
			
			elco.withFWBIRTHDATE(FW_BIRTHDATE)
			.withFWGENDER(FW_GENDER)
			.withFWWOMFNAME(FW_WOMFNAME)
			.withFWHUSNAME(FW_HUSNAME)
			.withFWWOMBID(FW_WOMBID)
			.withFWWOMFNAME(FW_WOMFNAME)
			.withFWDISPLAYAGE(FW_DISPLAY_AGE)
			.withFWELIGIBLE(FW_ELIGIBLE);
			
			allEcos.update(elco);
		}
		
	}
	
	
}
