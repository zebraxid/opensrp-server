/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.CommonFormFields.ID;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.*;
import static org.opensrp.common.AllConstants.HHRegistrationFields.MEMBERS_REGISTRATION_SUB_FORM_NAME;
import static org.opensrp.common.AllConstants.HHRegistrationFields.REFERENCE_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.START_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.END_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.Date_Of_Reg;
import static org.opensrp.common.AllConstants.HHRegistrationFields.existing_location;
import static org.opensrp.common.AllConstants.HHRegistrationFields.existing_Country;
import static org.opensrp.common.AllConstants.HHRegistrationFields.existing_Division;
import static org.opensrp.common.AllConstants.HHRegistrationFields.existing_District;
import static org.opensrp.common.AllConstants.HHRegistrationFields.existing_Upazilla;
import static org.opensrp.common.AllConstants.HHRegistrationFields.existing_Union;
import static org.opensrp.common.AllConstants.HHRegistrationFields.existing_Ward;
import static org.opensrp.common.AllConstants.HHRegistrationFields.received_time;

import static org.opensrp.common.util.EasyMap.create;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.opensrp.register.mcare.domain.HouseHold;
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
			HHSchedulesService hhSchedulesService,ScheduleLogService scheduleLogService) {
		this.allHouseHolds = allHouseHolds;
		this.membersService = membersService;
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
		subFormData = submission.getSubFormByName(MEMBERS_REGISTRATION_SUB_FORM_NAME);		
		//addDetailsToHH(submission, subFormData, houseHold);
		
		addMEMBERDETAILSToHH(submission, subFormData, houseHold);
		
		houseHold.withPROVIDERID(submission.anmId());
		houseHold.withINSTANCEID(submission.instanceId());
		houseHold.withSUBMISSIONDATE(scheduleLogService.getTimeStampMills());
		allHouseHolds.update(houseHold);
			
		hhSchedulesService.enrollIntoMilestoneOfCensus(submission.entityId(),
			submission.getField(Date_Of_Reg),submission.anmId(),submission.instanceId());
		
		membersService.registerMembers(submission);
	}
	
	private void addDetailsToHH(FormSubmission submission,
			SubFormData subFormData, HouseHold houseHold) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date today = Calendar.getInstance().getTime();
						houseHold.details().put(existing_location, submission.getField(existing_location));
						houseHold.details().put(existing_Country, submission.getField(existing_Country));		
						houseHold.details().put(existing_Division, submission.getField(existing_Division));
						houseHold.details().put(existing_District, submission.getField(existing_District));
						houseHold.details().put(existing_Upazilla, submission.getField(existing_Upazilla));
						houseHold.details().put(existing_Union, submission.getField(existing_Union));		
						houseHold.details().put(existing_Ward, submission.getField(existing_Ward));
						houseHold.details().put(received_time,format.format(today).toString());
				    	houseHold.details().put(REFERENCE_DATE, submission.getField(REFERENCE_DATE));
						houseHold.details().put(START_DATE, submission.getField(START_DATE));		
						houseHold.details().put(END_DATE, submission.getField(END_DATE));
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
					.put(HH_Member, membersFields.get(HH_Member))
					.put(Reg_No, membersFields.get(Reg_No))
					.put(relationalid, membersFields.get(relationalid))
					.put(BDH, membersFields.get(BDH))
					.put(Member_Fname, membersFields.get(Member_Fname))
					.put(Member_LName, membersFields.get(Member_LName))
					.put(Gender, membersFields.get(Gender))
					.put(DoB, membersFields.get(DoB))
					.put(Type_DoB, membersFields.get(Type_DoB))
					.put(Age, membersFields.get(Age))
					.put(Display_Age, membersFields.get(Display_Age))
					.put(Child_Vital_Status, membersFields.get(Child_Vital_Status))
					.put(MOTHER_Vaccine_Dates, membersFields.get(MOTHER_Vaccine_Dates))
					.put(FW_CWOMHUSSTR, membersFields.get(FW_CWOMHUSSTR))
					.put(Date_of_BCG_OPV_0, membersFields.get(Date_of_BCG_OPV_0))
					.put(Date_of_OPV_Penta_PCV_1, membersFields.get(Date_of_OPV_Penta_PCV_1))
					.put(Date_of_OPV_Penta_PCV_2, membersFields.get(Date_of_OPV_Penta_PCV_2))
					.put(Date_of_OPV_Penta_3_IPV, membersFields.get(Date_of_OPV_Penta_3_IPV))
					.put(Date_of_PCV_3, membersFields.get(Date_of_PCV_3))
					.put(Date_of_MR, membersFields.get(Date_of_MR))
					.put(Date_of_Measles, membersFields.get(Date_of_Measles))
					.put(Date_BRID, membersFields.get(Date_BRID))
					.put(Child_BRID, membersFields.get(Child_BRID))
					.put(Date_Child_Death, membersFields.get(Date_Child_Death))
					.put(C_Guardian_Type, membersFields.get(C_Guardian_Type))
					.put(C_Guardian_Name_Father, membersFields.get(C_Guardian_Name_Father))					
					.put(C_Guardian_Name_Mother, membersFields.get(C_Guardian_Name_Mother))
					.put(C_Guardian_Name_Hus, membersFields.get(C_Guardian_Name_Hus))
					.put(Marital_Status, membersFields.get(Marital_Status))
					.put(Couple_No, membersFields.get(Couple_No))
					.put(LMP, membersFields.get(LMP))
					.put(EDD, membersFields.get(EDD))
					.put(GA, membersFields.get(GA))
					.put(Pregnancy_Status, membersFields.get(Pregnancy_Status))
					.put(Date_of_MR_wom, membersFields.get(Date_of_MR_wom))
					.put(Date_of_TT1, membersFields.get(Date_of_TT1))
					.put(Date_of_TT2, membersFields.get(Date_of_TT2))
					.put(Date_of_TT3, membersFields.get(Date_of_TT3))					
					.put(Date_of_TT4, membersFields.get(Date_of_TT4))
					.put(Date_of_TT5, membersFields.get(Date_of_TT5))
					.put(Unique_ID, membersFields.get(Unique_ID))
					.put(NID, membersFields.get(NID))
					.put(BRID, membersFields.get(BRID))
					.put(HID, membersFields.get(HID))					
					.put(Guardian_Type, membersFields.get(Guardian_Type))
					.put(Guardian_Name_Father, membersFields.get(Guardian_Name_Father))
					.put(Guardian_Name_Mother, membersFields.get(Guardian_Name_Mother))
					.put(Guardian_Name_Hus, membersFields.get(Guardian_Name_Hus))
					.put(Mobile_No, membersFields.get(Mobile_No))
					.put(Education, membersFields.get(Education))
					.put(Occupation, membersFields.get(Occupation))
					.put(Is_TT, membersFields.get(Is_TT))
					.put(Is_Measles, membersFields.get(Is_Measles))
					.put(Is_FP, membersFields.get(Is_FP))
					.put(Is_NewBorn, membersFields.get(Is_NewBorn))
					.put(Member_COUNTRY, membersFields.get(Member_COUNTRY))
					.put(Member_DIVISION, membersFields.get(Member_DIVISION))
					.put(Member_DISTRICT, membersFields.get(Member_DISTRICT))
					.put(Member_UPAZILLA, membersFields.get(Member_UPAZILLA))
					.put(Member_UNION, membersFields.get(Member_UNION))
					.put(Member_WARD, membersFields.get(Member_WARD))
					.put(Member_GOB_HHID, membersFields.get(Member_GOB_HHID))
					.put(Member_GPS, membersFields.get(Member_GPS))
					.put(received_time, dateTime.format(today).toString()).map();
			
				if(membersFields.containsKey(Reg_No)){
					if(!membersFields.get(Reg_No).equalsIgnoreCase("") || membersFields.get(Reg_No) != null){
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
