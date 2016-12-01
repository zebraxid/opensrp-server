/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.CommonFormFields.ID;
import static org.opensrp.common.AllConstants.HHRegistrationFields.*;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.*;
import static org.opensrp.common.util.EasyMap.create;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.service.scheduling.HHSchedulesService;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HHService {

	private static Logger logger = LoggerFactory.getLogger(HHService.class
			.toString());
	private AllHouseHolds allHouseHolds;
	private MembersService membersService;
	private HHSchedulesService hhSchedulesService;
	private ScheduleLogService scheduleLogService;	 
	@Autowired
	public HHService(AllHouseHolds allHouseHolds, MembersService membersService,
			HHSchedulesService hhSchedulesService) {
		this.allHouseHolds = allHouseHolds;
		this.membersService = membersService;
		this.hhSchedulesService = hhSchedulesService;	
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
		subFormData = submission.getSubFormByName(MEMBERS_REGISTRATION_SUB_FORM_NAME);		
		
		addMEMBERDETAILSToHH(submission, subFormData, houseHold);
		
		houseHold.setPROVIDERID(submission.anmId());
		houseHold.setINSTANCEID(submission.instanceId());
		houseHold.setToday(submission.getField(REFERENCE_DATE));	

		allHouseHolds.update(houseHold);
			
		hhSchedulesService.enrollIntoMilestoneOfCensus(submission.entityId(),
			submission.getField(REFERENCE_DATE),submission.anmId(),submission.instanceId());
		
		membersService.registerMembers(submission);
	}

	private void addMEMBERDETAILSToHH(FormSubmission submission,
			SubFormData subFormData, HouseHold houseHold) {
		
		SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		for (Map<String, String> membersFields : subFormData.instances()) {

			Map<String, String> members = create(ID, membersFields.get(ID))
					.put(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
					.put(START_DATE, submission.getField(START_DATE))
					.put(END_DATE, submission.getField(END_DATE))
					.put(caseId , submission.getField(caseId))
					.put(INSTANCEID	, submission.getField(INSTANCEID))
					.put(PROVIDERID	, submission.getField(PROVIDERID))
					.put(LOCATIONID	, submission.getField(LOCATIONID))
					.put(Today	, submission.getField(Today))
					.put(Start	, submission.getField(Start))
					.put(End	, submission.getField(End))
					.put(relationalid	, submission.getField(relationalid))
					.put(Member_GoB_HHID	, submission.getField(Member_GoB_HHID))
					.put(Member_Reg_Date	, submission.getField(Member_Reg_Date))
					.put(Mem_F_Name	, submission.getField(Mem_F_Name))
					.put(Mem_L_Name	, submission.getField(Mem_L_Name))
					.put(Member_Birth_Date_Known	, submission.getField(Member_Birth_Date_Known))
					.put(Member_Birth_Date	, submission.getField(Member_Birth_Date))
					.put(Member_Age	, submission.getField(Member_Age))
					.put(Calc_Age	, submission.getField(Calc_Age))
					.put(Calc_Dob	, submission.getField(Calc_Dob))
					.put(Calc_Dob_Confirm	, submission.getField(Calc_Dob_Confirm))
					.put(Calc_Age_Confirm	, submission.getField(Calc_Age_Confirm))
					.put(Birth_Date_Note	, submission.getField(Birth_Date_Note))
					.put(Note_age	, submission.getField(Note_age))
					.put(Member_Gender	, submission.getField(Member_Gender))
					.put(Mem_ID_Type	, submission.getField(Mem_ID_Type))
					.put(Mem_NID	, submission.getField(Mem_NID))
					.put(Retype_Mem_NID	, submission.getField(Retype_Mem_NID))
					.put(Mem_NID_Concept	, submission.getField(Mem_NID_Concept))
					.put(Mem_BRID	, submission.getField(Mem_BRID))
					.put(Retype_Mem_BRID	, submission.getField(Retype_Mem_BRID))
					.put(Mem_BRID_Concept	, submission.getField(Mem_BRID_Concept))
					.put(Mem_Mobile_Number	, submission.getField(Mem_Mobile_Number))
					.put(Mem_Marital_Status	, submission.getField(Mem_Marital_Status))
					.put(Couple_No	, submission.getField(Couple_No))
					.put(Spouse_Name	, submission.getField(Spouse_Name))
					.put(Wom_Menstruating	, submission.getField(Wom_Menstruating))
					.put(Wom_Sterilized	, submission.getField(Wom_Sterilized))
					.put(Wom_Hus_Live	, submission.getField(Wom_Hus_Live))
					.put(Wom_Hus_Alive	, submission.getField(Wom_Hus_Alive))
					.put(Wom_Hus_Sterilized	, submission.getField(Wom_Hus_Sterilized))
					.put(Eligible	, submission.getField(Eligible))
					.put(Eligible2	, submission.getField(Eligible2))
					.put(ELCO	, submission.getField(ELCO))
					.put(ELCO_Note	, submission.getField(ELCO_Note))
					.put(Mem_Country	, submission.getField(Mem_Country))
					.put(Mem_Division	, submission.getField(Mem_Division))
					.put(Mem_District	, submission.getField(Mem_District))
					.put(Mem_Upazilla	, submission.getField(Mem_Upazilla))
					.put(Mem_Union	, submission.getField(Mem_Union))
					.put(Mem_Ward	, submission.getField(Mem_Ward))
					.put(Mem_Subunit	, submission.getField(Mem_Subunit))
					.put(Mem_Mauzapara	, submission.getField(Mem_Mauzapara))
					.put(Mem_Village_Name	, submission.getField(Mem_Village_Name))
					.put(Mem_GPS	, submission.getField(Mem_GPS))
					.put(ELCO_ID_Type	, submission.getField(ELCO_ID_Type))
					.put(ELCO_NID	, submission.getField(ELCO_NID))
					.put(ELCO_NID_Concept	, submission.getField(ELCO_NID_Concept))
					.put(ELCO_BRID	, submission.getField(ELCO_BRID))
					.put(ELCO_BRID_Concept	, submission.getField(ELCO_BRID_Concept))
					.put(ELCO_Mobile_Number	, submission.getField(ELCO_Mobile_Number))
					.put(Member_Detail	, submission.getField(Member_Detail))
					.put(Permanent_Address	, submission.getField(Permanent_Address))
					.put(Updated_Dist	, submission.getField(Updated_Dist))
					.put(Updated_Union	, submission.getField(Updated_Union))
					.put(Updated_Vill	, submission.getField(Updated_Vill))
					.put(Final_Dist	, submission.getField(Final_Dist))
					.put(Final_Union	, submission.getField(Final_Union))
					.put(Final_Vill	, submission.getField(Final_Vill))
					.put(Relation_HoH	, submission.getField(Relation_HoH))
					.put(Place_Of_Birth	, submission.getField(Place_Of_Birth))
					.put(Education	, submission.getField(Education))
					.put(Religion	, submission.getField(Religion))
					.put(BD_Citizen	, submission.getField(BD_Citizen))
					.put(Occupation	, submission.getField(Occupation))
					.put(add_member	, submission.getField(add_member))
					.put(isClosed	, submission.getField(isClosed))
					.put(received_time, dateTime.format(today).toString()).map();
			
				if(membersFields.containsKey(REG_NO)){
					if(!membersFields.get(REG_NO).equalsIgnoreCase("") || membersFields.get(REG_NO) != null){
						houseHold.MEMBERDETAILS().add(members);
				  }
				}						
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
		   for ( Map<String, String> members : household.MEMBERDETAILS()) 
		   {
			   if(brnIdList.contains(members.get("FWWOMBID")))
				   return household.caseId();
		   }
	   }
	   return null;
	}
	
}
