package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.CommonFormFields.ID;
import static org.opensrp.common.AllConstants.HHRegistrationFields.ELCO_REGISTRATION_SUB_FORM_NAME;
import static org.opensrp.common.AllConstants.HHRegistrationFields.REFERENCE_DATE;
import static org.opensrp.common.AllConstants.PSRFFields.*;

import java.util.Map;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.service.scheduling.ELCOScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.opensrp.common.AllConstants.ELCORegistrationFields.END_DATE;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_BIRTHDATE;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_DISPLAY_AGE;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_ELIGIBLE;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_GENDER;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_GOBHHID;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_HUSNAME;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_JiVitAHHID;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_LOCATIONID;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_NHWOMHUSALV;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_NHWOMHUSLIV;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_NHWOMHUSSTR;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_NHWOMSTRMEN;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_PROVIDERID;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_TODAY;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_WOMAGE;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_WOMBID;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_WOMFNAME;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_WOMLNAME;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_WOMNID;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.START_DATE;
import static org.opensrp.common.AllConstants.Form.*;
import static org.opensrp.common.util.EasyMap.create;

@Service
public class ELCOService {
	private static Logger logger = LoggerFactory.getLogger(ELCOService.class
			.toString());

	private AllHouseHolds allHouseHolds;
	private AllElcos allEcos;
	private ELCOScheduleService elcoScheduleService;

	@Autowired
	public ELCOService(AllHouseHolds allHouseHolds, AllElcos allEcos,
			ELCOScheduleService elcoScheduleService) {
		this.allHouseHolds = allHouseHolds;
		this.allEcos = allEcos;
		this.elcoScheduleService = elcoScheduleService;
	}

	public void registerELCO(FormSubmission submission) {
		
		SubFormData subFormData = submission
				.getSubFormByName(ELCO_REGISTRATION_SUB_FORM_NAME);

		for (Map<String, String> elcoFields : subFormData.instances()) {

			Elco elco = allEcos.findByCaseId(elcoFields.get(ID))
					.withPROVIDERID(submission.anmId())
					.withTODAY(submission.getField(REFERENCE_DATE));
			
			allEcos.update(elco);

			elcoScheduleService.enrollIntoMilestoneOfPSRF(elcoFields.get(ID),
					submission.getField(REFERENCE_DATE));
		}

		if (submission.formName().equalsIgnoreCase(ELCO_REGISTRATION)) {

			HouseHold houseHold = allHouseHolds.findByCASEID(submission
					.entityId());

			if (houseHold == null) {
				logger.warn(format(
						"Failed to handle Census form as there is no household registered with ID: {0}",
						submission.entityId()));
				return;
			}
			
			addELCODetailsToHH(submission, subFormData, houseHold);

			houseHold.withPROVIDERID(submission.anmId());
			houseHold.withTODAY(submission.getField(REFERENCE_DATE));
			allHouseHolds.update(houseHold);
		}
	}
	
	private void addELCODetailsToHH(FormSubmission submission,
			SubFormData subFormData, HouseHold houseHold) {

		for (Map<String, String> elcoFields : subFormData.instances()) {

			Map<String, String> elco = create(ID, elcoFields.get(ID))
					.put(FW_PROVIDERID, elcoFields.get(FW_PROVIDERID))
					.put(FW_LOCATIONID, elcoFields.get(FW_LOCATIONID))
					.put(FW_TODAY, elcoFields.get(FW_TODAY))
					.put(START_DATE, elcoFields.get(START_DATE))
					.put(END_DATE, elcoFields.get(END_DATE))
					.put(FW_GOBHHID, elcoFields.get(FW_GOBHHID))
					.put(FW_JiVitAHHID, elcoFields.get(FW_JiVitAHHID))
					.put(FW_WOMFNAME, elcoFields.get(FW_WOMFNAME))
					.put(FW_WOMLNAME, elcoFields.get(FW_WOMLNAME))
					.put(FW_WOMNID, elcoFields.get(FW_WOMNID))
					.put(FW_WOMBID, elcoFields.get(FW_WOMBID))
					.put(FW_HUSNAME, elcoFields.get(FW_HUSNAME))
					.put(FW_GENDER, elcoFields.get(FW_GENDER))
					.put(FW_BIRTHDATE, elcoFields.get(FW_BIRTHDATE))
					.put(FW_WOMAGE, elcoFields.get(FW_WOMAGE))
					.put(FW_DISPLAY_AGE, elcoFields.get(FW_DISPLAY_AGE))
					.put(FW_NHWOMSTRMEN, elcoFields.get(FW_NHWOMSTRMEN))
					.put(FW_NHWOMHUSALV, elcoFields.get(FW_NHWOMHUSALV))
					.put(FW_NHWOMHUSSTR, elcoFields.get(FW_NHWOMHUSSTR))
					.put(FW_NHWOMHUSLIV, elcoFields.get(FW_NHWOMHUSLIV))
					.put(FW_ELIGIBLE, elcoFields.get(FW_ELIGIBLE)).map();
			
			houseHold.ELCODETAILS().add(elco);

		}
		
	}
	
	public void addPSRFDetailsToELCO(FormSubmission submission) {

		    Elco elco = allEcos.findByCaseId(submission.entityId());
		    
		    if (elco == null) {
				logger.warn(format(
						"Failed to handle PSRF form as there is no ELCO registered with ID: {0}",
						submission.entityId()));
				return;
			}
		 
		    
		   
			Map<String, String> psrf = create(FW_PSRDATE, submission.getField(FW_PSRDATE))
					.put(FW_CONFIRMATION, submission.getField(FW_CONFIRMATION))
					.put(FW_PSRSTS, submission.getField(FW_PSRSTS))
					.put(FW_PSRLMP, submission.getField(FW_PSRLMP))
					.put(FW_PSRPREGSTS, submission.getField(FW_PSRPREGSTS))
					.put(FW_PSRWOMPREGWTD, submission.getField(FW_PSRWOMPREGWTD))
					.put(FW_PSRHUSPREGWTD, submission.getField(FW_PSRHUSPREGWTD))
					.put(FW_PSREVRPREG, submission.getField(FW_PSREVRPREG))
					.put(FW_PSRTOTBIRTH, submission.getField(FW_PSRTOTBIRTH))
					.put(FW_PSRNBDTH, submission.getField(FW_PSRNBDTH))
					.put(FW_PSRPRSB, submission.getField(FW_PSRPRSB))
					.put(FW_PSRPRMC, submission.getField(FW_PSRPRMC))
					.put(FW_PSRPREGTWYRS, submission.getField(FW_PSRPREGTWYRS))
					.put(FW_PSRPRVPREGCOMP, submission.getField(FW_PSRPRVPREGCOMP))
					.put(FW_PSRPRCHECKS, submission.getField(FW_PSRPRCHECKS))
					.put(FW_PSRVDGMEM, submission.getField(FW_PSRVDGMEM))
					.put(FW_PSRWOMEDU, submission.getField(FW_PSRWOMEDU))
					.put(FW_PSRHHLAT, submission.getField(FW_PSRHHLAT))
					.put(FW_PSRHHRICE, submission.getField(FW_PSRHHRICE))
					.put(FW_PSRANM, submission.getField(FW_PSRANM))
					.put(FW_PSRHBP, submission.getField(FW_PSRHBP))
					.put(FW_PSRDBT, submission.getField(FW_PSRDBT))
					.put(FW_PSRTHY, submission.getField(FW_PSRTHY))
					.put(FW_PSRHGT, submission.getField(FW_PSRHGT))
					.put(FW_PSRMUAC, submission.getField(FW_PSRMUAC)).map();
			
			elco.PSRFDETAILS().add(psrf);	
			
			elco.withTODAY(submission.getField(REFERENCE_DATE));
			
			allEcos.update(elco);
	}
}
