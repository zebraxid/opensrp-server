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

import org.opensrp.common.util.DateUtil;
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
		houseHold.setTimestamp(DateUtil.getTimestampToday());

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
					.put(FORM_NAME, submission.getField(FORM_NAME))
					.put(START_DATE, submission.getField(START_DATE))
					.put(END_DATE, submission.getField(END_DATE))
					.put(INSTANCEID	, submission.getField(INSTANCEID))
					.put(PROVIDERID	, submission.getField(PROVIDERID))
					.put(LOCATIONID	, submission.getField(LOCATIONID))
					.put(relationalid	, membersFields.get(relationalid))
					.put(Member_GoB_HHID	, membersFields.get(Member_GoB_HHID))
					.put(Member_Reg_Date	, membersFields.get(Member_Reg_Date))
					.put(Mem_F_Name	, membersFields.get(Mem_F_Name))
					.put(Mem_L_Name	, membersFields.get(Mem_L_Name))
					.put(Member_Birth_Date_Known	, membersFields.get(Member_Birth_Date_Known))
					.put(Member_Birth_Date	, membersFields.get(Member_Birth_Date))
					.put(Member_Age	, membersFields.get(Member_Age))
					.put(Calc_Age	, membersFields.get(Calc_Age))
					.put(Calc_Dob	, membersFields.get(Calc_Dob))
					.put(Calc_Dob_Confirm	, membersFields.get(Calc_Dob_Confirm))
					.put(Calc_Age_Confirm	, membersFields.get(Calc_Age_Confirm))
					.put(Birth_Date_Note	, membersFields.get(Birth_Date_Note))
					.put(Note_age	, membersFields.get(Note_age))
					.put(Member_Gender	, membersFields.get(Member_Gender))
					.put(Mem_ID_Type	, membersFields.get(Mem_ID_Type))
					.put(Mem_NID	, membersFields.get(Mem_NID))
					.put(Retype_Mem_NID	, membersFields.get(Retype_Mem_NID))
					.put(Mem_NID_Concept	, membersFields.get(Mem_NID_Concept))
					.put(Mem_BRID	, membersFields.get(Mem_BRID))
					.put(Retype_Mem_BRID	, membersFields.get(Retype_Mem_BRID))
					.put(Mem_BRID_Concept	, membersFields.get(Mem_BRID_Concept))
					.put(Mem_Mobile_Number	, membersFields.get(Mem_Mobile_Number))
					.put(Mem_Marital_Status	, membersFields.get(Mem_Marital_Status))
					.put(Couple_No	, membersFields.get(Couple_No))
					.put(Spouse_Name	, membersFields.get(Spouse_Name))
					.put(Wom_Menstruating	, membersFields.get(Wom_Menstruating))
					.put(Wom_Sterilized	, membersFields.get(Wom_Sterilized))
					.put(Wom_Hus_Live	, membersFields.get(Wom_Hus_Live))
					.put(Wom_Hus_Alive	, membersFields.get(Wom_Hus_Alive))
					.put(Wom_Hus_Sterilized	, membersFields.get(Wom_Hus_Sterilized))
					.put(Eligible	, membersFields.get(Eligible))
					.put(Eligible2	, membersFields.get(Eligible2))
					.put(ELCO	, membersFields.get(ELCO))
					.put(ELCO_Note	, membersFields.get(ELCO_Note))
					.put(Mem_Country	, membersFields.get(Mem_Country))
					.put(Mem_Division	, membersFields.get(Mem_Division))
					.put(Mem_District	, membersFields.get(Mem_District))
					.put(Mem_Upazilla	, membersFields.get(Mem_Upazilla))
					.put(Mem_Union	, membersFields.get(Mem_Union))
					.put(Mem_Ward	, membersFields.get(Mem_Ward))
					.put(Mem_Subunit	, membersFields.get(Mem_Subunit))
					.put(Mem_Mauzapara	, membersFields.get(Mem_Mauzapara))
					.put(Mem_Village_Name	, membersFields.get(Mem_Village_Name))
					.put(Mem_GPS	, membersFields.get(Mem_GPS))
					.put(ELCO_ID_Type	, membersFields.get(ELCO_ID_Type))
					.put(ELCO_NID	, membersFields.get(ELCO_NID))
					.put(ELCO_NID_Concept	, membersFields.get(ELCO_NID_Concept))
					.put(ELCO_BRID	, membersFields.get(ELCO_BRID))
					.put(ELCO_BRID_Concept	, membersFields.get(ELCO_BRID_Concept))
					.put(ELCO_Mobile_Number	, membersFields.get(ELCO_Mobile_Number))
					.put(Member_Detail	, membersFields.get(Member_Detail))
					.put(Permanent_Address	, membersFields.get(Permanent_Address))
					.put(Updated_Dist	, membersFields.get(Updated_Dist))
					.put(Updated_Union	, membersFields.get(Updated_Union))
					.put(Updated_Vill	, membersFields.get(Updated_Vill))
					.put(Final_Dist	, membersFields.get(Final_Dist))
					.put(Final_Union	, membersFields.get(Final_Union))
					.put(Final_Vill	, membersFields.get(Final_Vill))
					.put(Relation_HoH	, membersFields.get(Relation_HoH))
					.put(Place_Of_Birth	, membersFields.get(Place_Of_Birth))
					.put(Education	, membersFields.get(Education))
					.put(Religion	, membersFields.get(Religion))
					.put(BD_Citizen	, membersFields.get(BD_Citizen))
					.put(Occupation	, membersFields.get(Occupation))
					.put(add_member	, membersFields.get(add_member))
					.put(isClosed	, membersFields.get(isClosed))
					.put(received_time, dateTime.format(today).toString()).map();
			
				if(membersFields.containsKey(Mem_F_Name)){
					if(!membersFields.get(Mem_F_Name).equalsIgnoreCase("") || membersFields.get(Mem_F_Name) != null){
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