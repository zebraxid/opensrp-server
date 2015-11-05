/**
 * The ELCOService class implements ELCO registry, Census Enrollment and PSRF schedule. 
 * @author julkar nain 
 */
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
import org.opensrp.register.mcare.service.scheduling.HHSchedulesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.opensrp.common.AllConstants.ELCORegistrationFields.*;
import static org.opensrp.common.AllConstants.Form.*;
import static org.opensrp.common.util.EasyMap.create;

@Service
public class ELCOService {
	private static Logger logger = LoggerFactory.getLogger(ELCOService.class
			.toString());

	private AllHouseHolds allHouseHolds;
	private AllElcos allEcos;
	private HHSchedulesService hhSchedulesService;
	private ELCOScheduleService elcoScheduleService;
	private ANCService ancService;

	@Autowired
	public ELCOService(AllHouseHolds allHouseHolds, AllElcos allEcos, HHSchedulesService hhSchedulesService,
			ELCOScheduleService elcoScheduleService,ANCService ancService) {
		this.allHouseHolds = allHouseHolds;
		this.allEcos = allEcos;
		this.hhSchedulesService = hhSchedulesService;
		this.elcoScheduleService = elcoScheduleService;
		this.ancService = ancService;
	}

	public void registerELCO(FormSubmission submission) {
		
		SubFormData subFormData = submission
				.getSubFormByName(ELCO_REGISTRATION_SUB_FORM_NAME);

		for (Map<String, String> elcoFields : subFormData.instances()) {

			Elco elco = allEcos.findByCaseId(elcoFields.get(ID))
					.withINSTANCEID(submission.instanceId())
					.withPROVIDERID(submission.anmId())
					.withTODAY(submission.getField(REFERENCE_DATE));
			
			allEcos.update(elco);

			elcoScheduleService.enrollIntoMilestoneOfPSRF(elcoFields.get(ID),
					submission.getField(REFERENCE_DATE));
		}

		if (submission.formName().equalsIgnoreCase(ELCO_REGISTRATION)) {

			HouseHold houseHold = allHouseHolds.findByCaseId(submission
					.entityId());

			if (houseHold == null) {
				logger.warn(format(
						"Failed to handle Census form as there is no household registered with ID: {0}",
						submission.entityId()));
				return;
			}
			
			addELCODetailsToHH(submission, subFormData, houseHold);

			houseHold.withPROVIDERID(submission.anmId());
			houseHold.withINSTANCEID(submission.instanceId());
			houseHold.withTODAY(submission.getField(REFERENCE_DATE));
			allHouseHolds.update(houseHold);
			
			hhSchedulesService.enrollIntoMilestoneOfCensus(submission.entityId(),
					submission.getField(REFERENCE_DATE));

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
					.put(FW_WOMANYID, elcoFields.get(FW_WOMANYID))
					.put(FW_WOMNID, elcoFields.get(FW_WOMNID))
					.put(FW_WOMRETYPENID, elcoFields.get(FW_WOMRETYPENID))
					.put(FW_WOMBID, elcoFields.get(FW_WOMBID))
					.put(FW_WOMRETYPEBID, elcoFields.get(FW_WOMRETYPEBID))
					.put(FW_HUSNAME, elcoFields.get(FW_HUSNAME))
					.put(FW_GENDER, elcoFields.get(FW_GENDER))
					.put(FW_BIRTHDATE, elcoFields.get(FW_BIRTHDATE))
					.put(FW_WOMAGE, elcoFields.get(FW_WOMAGE))
					.put(FW_DISPLAY_AGE, elcoFields.get(FW_DISPLAY_AGE))
					.put(FW_NHWOMSTRMEN, elcoFields.get(FW_NHWOMSTRMEN))
					.put(FW_NHWOMHUSALV, elcoFields.get(FW_NHWOMHUSALV))
					.put(FW_NHWOMHUSSTR, elcoFields.get(FW_NHWOMHUSSTR))
					.put(FW_NHWOMHUSLIV, elcoFields.get(FW_NHWOMHUSLIV))
					.put(FW_ELIGIBLE, elcoFields.get(FW_ELIGIBLE))			
					.put(FW_WOMCOUNTRY, elcoFields.get(FW_WOMCOUNTRY))
					.put(FW_WOMDIVISION, elcoFields.get(FW_WOMDIVISION))
					.put(FW_WOMDISTRICT, elcoFields.get(FW_WOMDISTRICT))
					.put(FW_WOMUPAZILLA, elcoFields.get(FW_WOMUPAZILLA))
					.put(FW_WOMUNION, elcoFields.get(FW_WOMUNION))
					.put(FW_WOMWARD, elcoFields.get(FW_WOMWARD))
					.put(FW_WOMSUBUNIT, elcoFields.get(FW_WOMSUBUNIT))
					.put(FW_WOMMAUZA_PARA, elcoFields.get(FW_WOMMAUZA_PARA))
					.put(FW_WOMGOBHHID, elcoFields.get(FW_WOMGOBHHID))
					.put(FW_WOMGPS, elcoFields.get(FW_WOMGPS)).map();
			
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
					.put(FW_PSRMUAC, submission.getField(FW_PSRMUAC))
					.put(FW_PSRPHONE, submission.getField(FW_PSRPHONE))
					.put(FW_PSRPHONENUM, submission.getField(FW_PSRPHONENUM)).map();
			
			elco.PSRFDETAILS().add(psrf);	
			
			elco.withTODAY(submission.getField(REFERENCE_DATE));
			
			allEcos.update(elco);
			
			elcoScheduleService.enrollIntoMilestoneOfPSRF(submission.entityId(),
					submission.getField(REFERENCE_DATE));
			
			if(submission.getField(FW_PSRPREGSTS).equals("1"))
			{
				ancService.registerANC(submission);
			}
	}
}
