package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.Form.HH_REGISTRATION;
import static org.opensrp.common.AllConstants.Form.ELCO_REGISTRATION;
import static org.opensrp.common.AllConstants.CommonFormFields.ID;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.*;
import static org.opensrp.common.AllConstants.HHRegistrationFields.ELCO_REGISTRATION_SUB_FORM_NAME;
import static org.opensrp.common.AllConstants.HHRegistrationFields.ELCO_REGISTRATION_SUB_FORM_NAME_CENSUS;
import static org.opensrp.common.AllConstants.HHRegistrationFields.REFERENCE_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.FW_UPAZILLA;
import static org.opensrp.common.AllConstants.PSRFFields.FW_CONFIRMATION;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRDATE;
import static org.opensrp.common.util.EasyMap.create;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.service.scheduling.HHSchedulesService;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
import org.opensrp.scheduler.ScheduleRules;
import org.opensrp.scheduler.service.ScheduleRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HHService {

	private static Logger logger = LoggerFactory.getLogger(HHService.class
			.toString());
	private AllHouseHolds allHouseHolds;
	private ELCOService elcoService;
	private HHSchedulesService hhSchedulesService;
	private ScheduleLogService scheduleLogService;
	@Autowired
	public HHService(AllHouseHolds allHouseHolds, ELCOService elcoService,
			HHSchedulesService hhSchedulesService,ScheduleLogService scheduleLogService) {
		this.allHouseHolds = allHouseHolds;
		this.elcoService = elcoService;
		this.hhSchedulesService = hhSchedulesService;	
		this.scheduleLogService = scheduleLogService;
	}

	public void registerHouseHold(FormSubmission submission) {

		HouseHold houseHold = allHouseHolds.findByCaseId(submission.entityId());

		if (houseHold == null) {
			logger.warn(format(
					"Failed to handle Census form as there is no household registered with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SubFormData subFormData =null;
		
		
		subFormData = submission.getSubFormByName(ELCO_REGISTRATION_SUB_FORM_NAME);

		addELCODetailsToHH(submission, subFormData, houseHold);

		houseHold.withPROVIDERID(submission.anmId());
		houseHold.withINSTANCEID(submission.instanceId());
		houseHold.withTODAY(submission.getField(REFERENCE_DATE));
		houseHold.withFWUPAZILLA(submission.getField(FW_UPAZILLA).replace("+", " "));
	
		/*Map<String, String> hh = new HashMap<String, String>();
		hh.put("Attachments", submission.getField(REFERENCE_DATE));*/
		
		Map<String, String> hh = create("Attachments", "newHH")
									.put("REFERENCE_DATE", submission.getField(REFERENCE_DATE))
									.map();
		
		houseHold.attachments().add(hh);
		
		allHouseHolds.update(houseHold);
		
		/*String cencusCondition =  scheduleLogService.getScheduleRuleForCensus("HouseHold Form");
		logger.info("Cencus Condition :"+cencusCondition);
		if(!cencusCondition.equalsIgnoreCase("") && cencusCondition.equalsIgnoreCase("1")){
			
			hhSchedulesService.enrollIntoMilestoneOfCensus(submission.entityId(),
				submission.getField(REFERENCE_DATE),submission.anmId(),submission.instanceId());
		}else{
			logger.info("Rule Defination Not Found for Cencus");
		}*/
		
		elcoService.registerELCO(submission);
	}

	private void addELCODetailsToHH(FormSubmission submission,
			SubFormData subFormData, HouseHold houseHold) {

		for (Map<String, String> elcoFields : subFormData.instances()) {

			Map<String, String> elco = create(ID, elcoFields.get(ID))
					.put(FW_TODAY, submission.getField(REFERENCE_DATE))
					.put(START_DATE, submission.getField(START_DATE))
					.put(END_DATE, submission.getField(END_DATE))
					.put(FW_GOBHHID, elcoFields.get(FW_GOBHHID))
					.put(FW_JiVitAHHID, elcoFields.get(FW_JiVitAHHID))
					.put(FW_CENDATE, submission.getField(FW_CENDATE))
					.put(FW_CENSTAT, submission.getField(FW_CENSTAT))
					.put(ELCO, submission.getField(ELCO))
					.put(WomanREGDATE, elcoFields.get(WomanREGDATE))
					.put(FW_CWOMSTRMEN, elcoFields.get(FW_CWOMSTRMEN))
					.put(FW_CWOMHUSALV, elcoFields.get(FW_CWOMHUSALV))
					.put(FW_CWOMHUSSTR, elcoFields.get(FW_CWOMHUSSTR))
					.put(FW_CWOMHUSLIV, elcoFields.get(FW_CWOMHUSLIV))
					.put(form_name, submission.getField(form_name))
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
					.put(FW_CWOMSTRMEN, elcoFields.get(FW_CWOMSTRMEN))
					.put(FW_CWOMHUSALV, elcoFields.get(FW_CWOMHUSALV))
					.put(FW_CWOMHUSSTR, elcoFields.get(FW_CWOMHUSSTR))
					.put(FW_CWOMHUSLIV, elcoFields.get(FW_CWOMHUSLIV))
					.put(FW_ELIGIBLE, elcoFields.get(FW_ELIGIBLE))
					.put(FW_ELIGIBLE2, elcoFields.get(FW_ELIGIBLE2))
					.put(FW_WOMCOUNTRY, elcoFields.get(FW_WOMCOUNTRY))
					.put(FW_WOMDIVISION, elcoFields.get(FW_WOMDIVISION))
					.put(FW_WOMDISTRICT, elcoFields.get(FW_WOMDISTRICT))
					.put(FW_WOMUPAZILLA, elcoFields.get(FW_WOMUPAZILLA).replace("+", " "))
					.put(FW_WOMUNION, elcoFields.get(FW_WOMUNION))
					.put(FW_WOMWARD, elcoFields.get(FW_WOMWARD))
					.put(FW_WOMSUBUNIT, elcoFields.get(FW_WOMSUBUNIT))
					.put(FW_WOMMAUZA_PARA, elcoFields.get(FW_WOMMAUZA_PARA))
					.put(FW_WOMGOBHHID, elcoFields.get(FW_WOMGOBHHID))
					.put(FW_WOMGPS, elcoFields.get(FW_WOMGPS)).map();
 
			
				if(elcoFields.containsKey(FW_WOMFNAME)){
					if(!elcoFields.get(FW_WOMFNAME).equalsIgnoreCase("") || elcoFields.get(FW_WOMFNAME) != null){
						houseHold.ELCODETAILS().add(elco);
					}
				}else{
					logger.info("Variable not found");
				}
			
			
			
			/*
			 * Elco elcoRegistry = allEcos.findByCaseId(elcoFields.get(ID))
			 * .withPROVIDERID(submission.anmId());
			 * 
			 * allEcos.update(elcoRegistry);
			 */

		}
		
	}
	
	public String getEntityIdBybrnId(List<String> brnIdList)
	{
	   List<HouseHold> houseHolds =	allHouseHolds.findAllHouseHolds();
	   
	   if (houseHolds == null || houseHolds.isEmpty()) {
           return null;
       }
	   
	   for (HouseHold household : houseHolds)
	   {
		   for ( Map<String, String> elco : household.ELCODETAILS()) 
		   {
			   if(brnIdList.contains(elco.get("FWWOMBID")))
				   return household.caseId();
		   }
	   }
	   return null;
	}
	
}