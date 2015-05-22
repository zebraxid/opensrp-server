package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.CommonFormFields.ID;
import static org.opensrp.common.AllConstants.HHRegistrationFields.REFERENCE_DATE;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_BIRTHDATE;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_DISPLAY_AGE;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_ELIGIBLE;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_GENDER;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_HUSNAME;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_WOMAGE;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_WOMBID;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_WOMFNAME;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_WOMLNAME;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_WOMNID;
import static org.opensrp.common.AllConstants.FamilyPlanningFormFields.CURRENT_FP_METHOD_FIELD_NAME;
import static org.opensrp.common.AllConstants.HHRegistrationFields.ELCO_REGISTRATION_SUB_FORM_NAME;
import static org.opensrp.common.util.EasyMap.create;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.service.scheduling.HHSchedulesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HHService {
	private static Logger logger = LoggerFactory.getLogger(HHService.class
			.toString());
	private AllHouseHolds allHouseHolds;
	private HHSchedulesService hhSchedulesService;

	@Autowired
	public HHService(AllHouseHolds allHouseHolds, HHSchedulesService hhSchedulesService) {
		this.allHouseHolds = allHouseHolds;
		this.hhSchedulesService = hhSchedulesService;
	}

	public void registerHouseHold(FormSubmission submission) {
		
		HouseHold houseHold = allHouseHolds.findByCASEID(submission.entityId());
		
		 if (houseHold == null) {
	            logger.warn(format("Failed to handle census form as there is no household registered with ID: {0}", submission.entityId()));
	            return;
	        }
		SubFormData subFormData = submission
				.getSubFormByName(ELCO_REGISTRATION_SUB_FORM_NAME);

		addELCODetailsToHH(submission, subFormData, houseHold);

		houseHold.withPROVIDERID(submission.anmId());
		houseHold.withTODAY(submission.getField(REFERENCE_DATE));
		allHouseHolds.update(houseHold);
		
		hhSchedulesService.enrollIntoMilestoneOfCensus(submission.entityId(), submission.getField(REFERENCE_DATE));
	}

	private void addELCODetailsToHH(FormSubmission submission,
			SubFormData subFormData, HouseHold houseHold) {
		List<Map<String, String>> elcoDetails = new ArrayList<>(
				houseHold.ELCODETAILS());

		for (Map<String, String> elcoFields : subFormData.instances()) {

			Map<String, String> elco = create(ID, elcoFields.get(ID))
					.put(FW_WOMFNAME, elcoFields.get(FW_WOMFNAME))
					.put(FW_WOMLNAME, elcoFields.get(FW_WOMLNAME))
					.put(FW_WOMNID, elcoFields.get(FW_WOMNID))
					.put(FW_WOMBID, elcoFields.get(FW_WOMBID))
					.put(FW_HUSNAME, elcoFields.get(FW_HUSNAME))
					.put(FW_GENDER, elcoFields.get(FW_GENDER))
					.put(FW_BIRTHDATE, elcoFields.get(FW_BIRTHDATE))
					.put(FW_WOMAGE, elcoFields.get(FW_WOMAGE))
					.put(FW_DISPLAY_AGE, elcoFields.get(FW_DISPLAY_AGE))
					.put(FW_ELIGIBLE, elcoFields.get(FW_ELIGIBLE))
					.map();
			
			elcoDetails.add(elco);
		}
		
		houseHold.withELCODETAILS(elcoDetails);

	}
}
