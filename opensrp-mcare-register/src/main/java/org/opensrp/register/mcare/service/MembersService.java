/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.CommonFormFields.ID;
import static org.opensrp.common.AllConstants.TT_VisitFields.*;
import static org.opensrp.common.AllConstants.HHRegistrationFields.*;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MemberScheduleConstants.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.joda.time.LocalDate;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllMembers;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.service.scheduling.MembersScheduleService;
import org.opensrp.register.mcare.service.scheduling.HHSchedulesService;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
import org.opensrp.register.mcare.service.scheduling.ChildVaccineSchedule;
import org.opensrp.register.mcare.service.scheduling.WomanVaccineSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.*;
import static org.opensrp.common.AllConstants.Form.*;
import static org.opensrp.common.util.EasyMap.create;

@Service
public class MembersService {
	private static Logger logger = LoggerFactory.getLogger(MembersService.class
			.toString());

	private AllHouseHolds allHouseHolds;
	private AllMembers allMembers;
	private HHSchedulesService hhSchedulesService;
	private MembersScheduleService membersScheduleService;
	private ScheduleLogService scheduleLogService;
	private ChildVaccineSchedule childVaccineSchedule;
	private WomanVaccineSchedule womanVaccineSchedule;
	@Autowired
	public MembersService(AllHouseHolds allHouseHolds, AllMembers allMembers, HHSchedulesService hhSchedulesService, MembersScheduleService membersScheduleService, 
			ScheduleLogService scheduleLogService, ChildVaccineSchedule childVaccineSchedule, WomanVaccineSchedule womanVaccineSchedule) {
		this.allHouseHolds = allHouseHolds;
		this.allMembers = allMembers;
		this.hhSchedulesService = hhSchedulesService;
		this.membersScheduleService = membersScheduleService;
		this.scheduleLogService = scheduleLogService;
		this.childVaccineSchedule = childVaccineSchedule;
		this.womanVaccineSchedule = womanVaccineSchedule;
	}
	
	public void registerMembers(FormSubmission submission) {
		
		SubFormData subFormData = submission
				.getSubFormByName(MEMBERS_REGISTRATION_SUB_FORM_NAME);	
		  
		for (Map<String, String> membersFields : subFormData.instances()) {
			
			Members members = allMembers.findByCaseId(membersFields.get(ID))
					.setINSTANCEID(submission.instanceId())
					.setPROVIDERID(submission.anmId())
					.setToday(submission.getField(REFERENCE_DATE))
					.setRelationalid(submission.getField(relationalid));					
			
			if(membersFields.containsKey(Mem_F_Name)){
				allMembers.update(members);
				logger.info("members updated");
			}else{
				allMembers.remove(members);
				logger.info("members removed");
			}
			
			if(membersFields.containsKey(Is_woman))
			if(!membersFields.get(Is_woman).equalsIgnoreCase("") || membersFields.get(Is_woman) != null)	
				if(membersFields.get(Is_woman).equalsIgnoreCase("1")){
					TT_Vaccine(submission, members, membersFields);
				}					
				
			
			if(membersFields.containsKey(Is_child))
			if(!membersFields.get(Is_child).equalsIgnoreCase("") && membersFields.get(Is_child) != null)
				if(membersFields.get(Is_child).equalsIgnoreCase("1"))
					Child_Vaccine(submission, members, membersFields);
		}	
			

		if (submission.formName().equalsIgnoreCase(MEMBERS_REGISTRATION)) {

			HouseHold houseHold = allHouseHolds.findByCaseId(submission
					.entityId());

			if (houseHold == null) {
				logger.warn(format(
						"Failed to handle Census form as there is no household registered with ID: {0}",
						submission.entityId()));
				return;
			}
			
			addMEMBERDETAILSToHH(submission, subFormData, houseHold);

			houseHold.setPROVIDERID(submission.anmId());
			houseHold.setINSTANCEID(submission.instanceId());
			houseHold.setToday(submission.getField(REFERENCE_DATE));	
			
			allHouseHolds.update(houseHold);

			hhSchedulesService.enrollIntoMilestoneOfCensus(submission.entityId(),
					submission.getField(REFERENCE_DATE),submission.anmId(),submission.instanceId());
		}	 
	}
	
	public void Child_Vaccine(FormSubmission submission, Members members, Map<String, String> membersFields) {			
		childVaccineSchedule.immediateChildVaccine(submission, members, membersFields, 
				child_vaccination_bcg, IMD_child_bcg, Child_dob, Child_age, Child_age_days, final_bcg, 1, 365);
		
		childVaccineSchedule.immediateChildVaccine(submission, members, membersFields, 
				child_vaccination_opv0, IMD_child_opv0, Child_dob, Child_age, Child_age_days, final_opv0, 5, 1825);
				
		childVaccineSchedule.ChildVaccine(submission, members, membersFields, 
				child_vaccination_opv1, final_opv0, Child_age, Child_age_days, final_opv1, 5, 1825);
		
		childVaccineSchedule.ChildVaccine(submission, members, membersFields, 
				child_vaccination_opv2, final_opv1, Child_age, Child_age_days, final_opv2, 5, 1825);
		
		childVaccineSchedule.ChildVaccine(submission, members, membersFields, 
				child_vaccination_opv3, final_opv2, Child_age, Child_age_days, final_opv3, 5, 1825);
		
		childVaccineSchedule.ChildVaccine(submission, members, membersFields, 
				child_vaccination_penta1, Child_dob, Child_age, Child_age_days, final_penta1, 1, 365);
		
		childVaccineSchedule.ChildVaccine(submission, members, membersFields, 
				child_vaccination_penta2, final_penta1, Child_age, Child_age_days, final_penta2, 1, 365);
	
		childVaccineSchedule.ChildVaccine(submission, members, membersFields, 
				child_vaccination_penta3, final_penta2, Child_age, Child_age_days, final_penta3, 1, 365);
		
		childVaccineSchedule.ChildVaccine(submission, members, membersFields, 
				child_vaccination_pcv1, Child_dob, Child_age, Child_age_days, final_pcv1, 5, 1825);
		
		childVaccineSchedule.ChildVaccine(submission, members, membersFields, 
				child_vaccination_pcv2, final_pcv1, Child_age, Child_age_days, final_pcv2, 5, 1825);
		
		childVaccineSchedule.ChildVaccine(submission, members, membersFields, 
				child_vaccination_pcv3, final_pcv2, Child_age, Child_age_days, final_pcv3, 5, 1825);
		
		childVaccineSchedule.ChildVaccine(submission, members, membersFields, 
				child_vaccination_measles1, Child_dob, Child_age, Child_age_days, final_measles1, 5, 1825);
		
		childVaccineSchedule.ChildVaccine(submission, members, membersFields, 
				child_vaccination_measles2, final_measles1, Child_age, Child_age_days, final_measles2, 5, 1825);
		
		childVaccineSchedule.ChildVaccine(submission, members, membersFields, 
				child_vaccination_ipv, final_opv2, Child_age, Child_age_days, final_ipv, 15, 5475);			
	}
	
	
	public void TT_Vaccine(FormSubmission submission, Members members, Map<String, String> membersFields) {		
		womanVaccineSchedule.immediateWomanVaccine(submission,members, membersFields, SCHEDULE_Woman_BNF, IMD_SCHEDULE_Woman_BNF, final_edd, Is_preg_outcome, Marital_status);
		
		womanVaccineSchedule.WomanVaccine(submission, members, membersFields, SCHEDULE_Woman_1, final_lmp, tt1_final);
		
		womanVaccineSchedule.WomanVaccine(submission, members, membersFields, SCHEDULE_Woman_2, tt1_final, tt2_final);
		
		womanVaccineSchedule.WomanVaccine(submission, members, membersFields, SCHEDULE_Woman_3, tt2_final, tt3_final);
		
		womanVaccineSchedule.WomanVaccine(submission, members, membersFields, SCHEDULE_Woman_4, tt3_final, tt4_final);
		
		womanVaccineSchedule.WomanVaccine(submission, members, membersFields, SCHEDULE_Woman_5, tt4_final, tt5_final);
	}
	
	
	public void BNF_Visit(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle BNF_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> bnf = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(Member_Fname, submission.getField(Member_Fname))
											.put(Husband_name, submission.getField(Husband_name))
											.put(Member_BLOCK, submission.getField(Member_BLOCK))											
											.put(lmp, submission.getField(lmp))
											.put(existing_location, submission.getField(existing_location))
											.put(Date_of_interview, submission.getField(Date_of_interview))
											.put(Visit_status, submission.getField(Visit_status))
											.put(Display_text, submission.getField(Display_text))
											.put(Confirm_info, submission.getField(Confirm_info))											
											.put(Gestational_age, submission.getField(Gestational_age))
											.put(EDD, submission.getField(EDD))
											.put(Woman_vital_status, submission.getField(Woman_vital_status))
											.put(DOO, submission.getField(DOO))
											.put(Number_live_birth, submission.getField(Number_live_birth))
											.put(pregsts_bnf_current_formStatus, submission.getField(pregsts_bnf_current_formStatus))
											.put(outcome_active, submission.getField(outcome_active))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.setBNFVisit(bnf);
		allMembers.update(members);
		
		if (!submission.getField(Visit_status).equalsIgnoreCase("") && submission.getField(Visit_status) != null){	
			if(submission.getField(Visit_status).equalsIgnoreCase("1")){
				if (!submission.getField(EDD).equalsIgnoreCase("") && submission.getField(EDD) != null)
					if(isValidDate(submission.getField(EDD)))
						membersScheduleService.enrollAfterimmediateVisit(members.caseId(),submission.anmId(),submission.getField(EDD),submission.instanceId(),SCHEDULE_Woman_BNF,IMD_SCHEDULE_Woman_BNF);
			}
				
			else if(submission.getField(Visit_status).equalsIgnoreCase("3")){
				membersScheduleService.unEnrollAndCloseSchedule(members.caseId(),submission.anmId(),SCHEDULE_Woman_BNF,LocalDate.parse(submission.getField(REFERENCE_DATE)));
			}
		}
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
	
	public void Birth_OutcomeHandler(FormSubmission submission) {	
		
		SubFormData subFormData = submission
				.getSubFormByName(MEMBERS_REGISTRATION_SUB_FORM_NAME);	
		  
		for (Map<String, String> membersFields : subFormData.instances()) {
			
			Members members = allMembers.findByCaseId(membersFields.get(ID))
					.setINSTANCEID(submission.instanceId())
					.setPROVIDERID(submission.anmId())
					.setToday(submission.getField(REFERENCE_DATE))
					.setRelationalid(submission.getField(relationalid));					
			
			if(membersFields.containsKey(REG_NO)){
				allMembers.update(members);
				logger.info("members updated");
			}else{
				allMembers.remove(members);
				logger.info("members removed");
			}
			
			if(membersFields.containsKey(Is_woman)){
				if(!membersFields.get(Is_woman).equalsIgnoreCase("") || membersFields.get(Is_woman) != null){	
					if(membersFields.get(Is_woman).equalsIgnoreCase("1")){
						TT_Vaccine(submission, members, membersFields);
					}					
				}
			}
			
			if(membersFields.containsKey(Is_child))
				if(!membersFields.get(Is_child).equalsIgnoreCase("") && membersFields.get(Is_child) != null)
					if(membersFields.get(Is_child).equalsIgnoreCase("1")){
						Child_Vaccine(submission, members, membersFields);
					}
			
		}

		HouseHold houseHold = allHouseHolds.findByCaseId(submission
				.entityId());

		if (houseHold == null) {
			logger.warn(format(
					"Failed to handle Census form as there is no household registered with ID: {0}",
					submission.entityId()));
			return;
		}
		
		addBirth_OutcomeToHH(submission, subFormData, houseHold);

		houseHold.setPROVIDERID(submission.anmId());
		houseHold.setINSTANCEID(submission.instanceId());
		
		allHouseHolds.update(houseHold);
 
	}
	
	private void addBirth_OutcomeToHH(FormSubmission submission,
			SubFormData subFormData, HouseHold houseHold) {
		
		SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		for (Map<String, String> membersFields : subFormData.instances()) {

			Map<String, String> birth_Outcome = create(ID, membersFields.get(ID))
					.put(relationalid, submission.getField(relationalid))
					.put(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
					.put(START_DATE, submission.getField(START_DATE))
					.put(END_DATE, submission.getField(END_DATE))
					.put(REG_NO, membersFields.get(REG_NO))
					.put(MEMBER_COUNTRY, membersFields.get(MEMBER_COUNTRY))
					.put(MEMBER_DIVISION, membersFields.get(MEMBER_DIVISION))
					.put(MEMBER_DISTRICT, membersFields.get(MEMBER_DISTRICT))
					.put(MEMBER_UPAZILLA, membersFields.get(MEMBER_UPAZILLA))
					.put(MEMBER_PAURASAVA, membersFields.get(MEMBER_PAURASAVA))
					.put(MEMBER_UNION, membersFields.get(MEMBER_UNION))
					.put(MEMBER_WARD, membersFields.get(MEMBER_WARD))
					.put(MEMBER_ADDRESS_LINE, membersFields.get(MEMBER_ADDRESS_LINE))
					.put(MEMBER_HIE_FACILITIES, membersFields.get(MEMBER_HIE_FACILITIES))
					.put(Member_Reg_Date, membersFields.get(Member_Reg_Date))
					.put(Member_BLOCK, submission.getField(Member_BLOCK))
					.put(HH_ADDRESS, membersFields.get(HH_ADDRESS))					
					.put(Child_vital_status, membersFields.get(Child_vital_status))
					.put(Child_name_check, membersFields.get(Child_name_check))
					.put(Child_name, membersFields.get(Child_name))
					.put(Child_last_name, membersFields.get(Child_last_name))
					.put(Member_Fname, membersFields.get(Member_Fname))
					.put(child_vaccines_2, membersFields.get(child_vaccines_2))
					.put(add_child, membersFields.get(add_child))					
					.put(Child_dob, membersFields.get(Child_dob))
					.put(Child_dob_estimated, membersFields.get(Child_dob_estimated))
					.put(Child_age_days, membersFields.get(Child_age_days))
					.put(Child_birth_date_note, membersFields.get(Child_birth_date_note))
					.put(Birth_Weigtht, membersFields.get(Birth_Weigtht))
					.put(Newborn_Care_Received, membersFields.get(Newborn_Care_Received))
					.put(Child_gender, membersFields.get(Child_gender))
					.put(Child_mother_name, membersFields.get(Child_mother_name))
					.put(Child_father_name, membersFields.get(Child_father_name))
					.put(Child_guardian_id, membersFields.get(Child_guardian_id))
					.put(Child_Mother_NID, membersFields.get(Child_Mother_NID))
					.put(Child_Mother_BRID, membersFields.get(Child_Mother_BRID))
					.put(Child_Father_NID, membersFields.get(Child_Father_NID))
					.put(Child_Father_BRID, membersFields.get(Child_Father_BRID))
					.put(Child_Other_Guardian_NID, membersFields.get(Child_Other_Guardian_NID))
					.put(Child_Other_Guardian_BRID, membersFields.get(Child_Other_Guardian_BRID))	
					.put(epi_card_number, membersFields.get(epi_card_number))
					.put(child_was_suffering_from_a_disease_at_birth, membersFields.get(child_was_suffering_from_a_disease_at_birth))
					.put(reminders_approval, membersFields.get(reminders_approval))
					.put(contact_phone_number, membersFields.get(contact_phone_number))
					.put(child_vaccines, membersFields.get(child_vaccines))
					.put(bcg_retro, membersFields.get(bcg_retro))
					.put(opv0_retro, membersFields.get(opv0_retro))
					.put(opv0_dose, membersFields.get(opv0_dose))
					.put(pcv1_retro, membersFields.get(pcv1_retro))
					.put(pcv1_dose, membersFields.get(pcv1_dose))
					.put(opv1_retro, membersFields.get(opv1_retro))
					.put(opv1_dose, membersFields.get(opv1_dose))
					.put(penta1_retro, membersFields.get(penta1_retro))
					.put(penta1_dose, membersFields.get(penta1_dose))
					.put(pcv2_retro, membersFields.get(pcv2_retro))
					.put(pcv2_dose, membersFields.get(pcv2_dose))
					.put(opv2_retro, membersFields.get(opv2_retro))
					.put(opv2_dose, membersFields.get(opv2_dose))
					.put(penta2_retro, membersFields.get(penta2_retro))
					.put(penta2_dose, membersFields.get(penta2_dose))
					.put(pcv3_retro, membersFields.get(pcv3_retro))
					.put(pcv3_dose, membersFields.get(pcv3_dose))
					.put(opv3_retro, membersFields.get(opv3_retro))
					.put(opv3_dose, membersFields.get(opv3_dose))
					.put(penta3_retro, membersFields.get(penta3_retro))
					.put(penta3_dose, membersFields.get(penta3_dose))
					.put(ipv_retro, membersFields.get(ipv_retro))
					.put(measles1_retro, membersFields.get(measles1_retro))
					.put(measles1_dose, membersFields.get(measles1_dose))
					.put(measles2_retro, membersFields.get(measles2_retro))
					.put(measles2_dose, membersFields.get(measles2_dose))
					.put(bcg, membersFields.get(bcg))
					.put(opv0, membersFields.get(opv0))
					.put(opv0_dose_today, membersFields.get(opv0_dose_today))
					.put(pcv1, membersFields.get(pcv1))
					.put(pcv1_dose_today, membersFields.get(pcv1_dose_today))
					.put(opv1, membersFields.get(opv1))
					.put(opv1_dose_today, membersFields.get(opv1_dose_today))
					.put(penta1, membersFields.get(penta1))
					.put(penta1_dose_today, membersFields.get(penta1_dose_today))
					.put(pcv2, membersFields.get(pcv2))
					.put(pcv2_dose_today, membersFields.get(pcv2_dose_today))
					.put(opv2, membersFields.get(opv2))
					.put(opv2_dose_today, membersFields.get(opv2_dose_today))
					.put(penta2, membersFields.get(penta2))
					.put(penta2_dose_today, membersFields.get(penta2_dose_today))
					.put(pcv3, membersFields.get(pcv3))
					.put(pcv3_dose_today, membersFields.get(pcv3_dose_today))
					.put(opv3, membersFields.get(opv3))
					.put(opv3_dose_today, membersFields.get(opv3_dose_today))
					.put(penta3, membersFields.get(penta3))
					.put(penta3_dose_today, membersFields.get(penta3_dose_today))
					.put(ipv, membersFields.get(ipv))
					.put(measles1, membersFields.get(measles1))
					.put(measles1_dose_today, membersFields.get(measles1_dose_today))
					.put(measles2, membersFields.get(measles2))
					.put(measles2_dose_today, membersFields.get(measles2_dose_today))
					.put(final_bcg, membersFields.get(final_bcg))
					.put(final_opv0, membersFields.get(final_opv0))
					.put(final_pcv1, membersFields.get(final_pcv1))
					.put(final_opv1, membersFields.get(final_opv1))
					.put(final_penta1, membersFields.get(final_penta1))
					.put(final_pcv2, membersFields.get(final_pcv2))
					.put(final_opv2, membersFields.get(final_opv2))
					.put(final_penta2, membersFields.get(final_penta2))
					.put(final_pcv3, membersFields.get(final_pcv3))
					.put(final_opv3, membersFields.get(final_opv3))
					.put(final_penta3, membersFields.get(final_penta3))
					.put(final_ipv, membersFields.get(final_ipv))
					.put(final_measles1, membersFields.get(final_measles1))
					.put(final_measles2, membersFields.get(final_measles2))
					.put(Is_child, membersFields.get(Is_child))
					.put(received_time, dateTime.format(today).toString()).map();
			
				if(membersFields.containsKey(REG_NO)){
					if(!membersFields.get(REG_NO).equalsIgnoreCase("") || membersFields.get(REG_NO) != null){
						houseHold.Birth_Outcome().add(birth_Outcome);
				  }
				}						
		}		
	}
	
	public void general_Visit(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle general_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> general = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(general_Date_Of_Reg, submission.getField(general_Date_Of_Reg))
											.put(Patient_Diagnosis, submission.getField(Patient_Diagnosis))
											.put(Treatment, submission.getField(Treatment))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.setgeneralVisit(general);
		allMembers.update(members);
	}
	
	public void TTform_Visit(FormSubmission submission) {	
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle TT_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		
		Map<String, String> TT_visit = create(ID, submission.getField(ID))
.put(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
.put(START_DATE, submission.getField(START_DATE))
.put(END_DATE, submission.getField(END_DATE))											
.put(address_change	,submission.getField(address_change	))
.put(address_note	,submission.getField(address_note	))
.put(address1	,submission.getField(address1	))
.put(birth_date_note	,submission.getField(birth_date_note	))
.put(calc_age_confirm	,submission.getField(calc_age_confirm	))
.put(calc_dob_confirm	,submission.getField(calc_dob_confirm	))
.put(center_gps	,submission.getField(center_gps	))
.put(contact_phone_number	,submission.getField(contact_phone_number	))
.put(e_tt1	,submission.getField(e_tt1	))
.put(e_tt2	,submission.getField(e_tt2	))
.put(e_tt3	,submission.getField(e_tt3	))
.put(e_tt4	,submission.getField(e_tt4	))
.put(e_tt5	,submission.getField(e_tt5	))
.put(edd	,submission.getField(edd	))
.put(edd_calc_lmp	,submission.getField(edd_calc_lmp	))
.put(edd_calc_lmp_formatted	,submission.getField(edd_calc_lmp_formatted	))
.put(edd_calc_ultrasound	,submission.getField(edd_calc_ultrasound	))
.put(edd_calc_ultrasound_formatted	,submission.getField(edd_calc_ultrasound_formatted	))
.put(edd_lmp	,submission.getField(edd_lmp	))
.put(epi_card_number	,submission.getField(epi_card_number	))
.put(epi_card_number_note	,submission.getField(epi_card_number_note	))
.put(existing_contact_phone_number	,submission.getField(existing_contact_phone_number	))
.put(existing_location	,submission.getField(existing_location	))
.put(Father_name	,submission.getField(Father_name	))
.put(father_name_note	,submission.getField(father_name_note	))
.put(final_edd	,submission.getField(final_edd	))
.put(final_edd_note	,submission.getField(final_edd_note	))
.put(final_ga	,submission.getField(final_ga	))
.put(final_ga_note	,submission.getField(final_ga_note	))
.put(final_lmp	,submission.getField(final_lmp	))
.put(final_lmp_note	,submission.getField(final_lmp_note	))
.put(first_name_note	,submission.getField(first_name_note	))
.put(ga_edd	,submission.getField(ga_edd	))
.put(ga_lmp	,submission.getField(ga_lmp	))
.put(ga_ult	,submission.getField(ga_ult	))
.put(Husband_name	,submission.getField(Husband_name	))
.put(husband_name	,submission.getField(husband_name	))
.put(husband_name_note	,submission.getField(husband_name_note	))
.put(landmark	,submission.getField(landmark	))
.put(lmp	,submission.getField(lmp	))
.put(lmp_calc_edd	,submission.getField(lmp_calc_edd	))
.put(lmp_calc_edd_formatted	,submission.getField(lmp_calc_edd_formatted	))
.put(lmp_calc_ultrasound	,submission.getField(lmp_calc_ultrasound	))
.put(lmp_calc_ultrasound_formatted	,submission.getField(lmp_calc_ultrasound_formatted	))
.put(Marital_Status	,submission.getField(Marital_Status	))
.put(marriage	,submission.getField(marriage	))
.put(Member_Address_line	,submission.getField(Member_Address_line	))
.put(Member_BLOCK	,submission.getField(Member_BLOCK	))
.put(Member_COUNTRY	,submission.getField(Member_COUNTRY	))
.put(Member_DISTRICT	,submission.getField(Member_DISTRICT	))
.put(Member_DIVISION	,submission.getField(Member_DIVISION	))
.put(Member_Fname	,submission.getField(Member_Fname	))
.put(Member_GPS	,submission.getField(Member_GPS	))
.put(Member_HIE_facilities	,submission.getField(Member_HIE_facilities	))
.put(Member_Paurasava	,submission.getField(Member_Paurasava	))
.put(Member_UNION	,submission.getField(Member_UNION	))
.put(Member_UPAZILLA	,submission.getField(Member_UPAZILLA	))
.put(Member_WARD	,submission.getField(Member_WARD	))
.put(pregnant	,submission.getField(pregnant	))
.put(pregnant	,submission.getField(pregnant	))
.put(provider_id	,submission.getField(provider_id	))
.put(provider_location_id	,submission.getField(provider_location_id	))
.put(provider_location_name	,submission.getField(provider_location_name	))
.put(provider_location_note	,submission.getField(provider_location_note	))
.put(tt_1_dose	,submission.getField(tt_1_dose	))
.put(tt_1_dose_today	,submission.getField(tt_1_dose_today	))
.put(tt_2_dose	,submission.getField(tt_2_dose	))
.put(tt_2_dose_today	,submission.getField(tt_2_dose_today	))
.put(tt_3_dose	,submission.getField(tt_3_dose	))
.put(tt_3_dose_today	,submission.getField(tt_3_dose_today	))
.put(tt_4_dose	,submission.getField(tt_4_dose	))
.put(tt_4_dose_today	,submission.getField(tt_4_dose_today	))
.put(tt_5_dose_today	,submission.getField(tt_5_dose_today	))
.put(tt1	,submission.getField(tt1	))
.put(tt1_final	,submission.getField(tt1_final	))
.put(tt1_note	,submission.getField(tt1_note	))
.put(tt1_retro	,submission.getField(tt1_retro	))
.put(tt2	,submission.getField(tt2	))
.put(tt2_final	,submission.getField(tt2_final	))
.put(tt2_note	,submission.getField(tt2_note	))
.put(tt2_retro	,submission.getField(tt2_retro	))
.put(tt3	,submission.getField(tt3	))
.put(tt3_final	,submission.getField(tt3_final	))
.put(tt3_note	,submission.getField(tt3_note	))
.put(tt3_retro	,submission.getField(tt3_retro	))
.put(tt4	,submission.getField(tt4	))
.put(tt4_final	,submission.getField(tt4_final	))
.put(tt4_note	,submission.getField(tt4_note	))
.put(tt4_retro	,submission.getField(tt4_retro	))
.put(tt5	,submission.getField(tt5	))
.put(tt5_final	,submission.getField(tt5_final	))
.put(ultrasound_date	,submission.getField(ultrasound_date	))
.put(ultrasound_weeks	,submission.getField(ultrasound_weeks	))
.put(vaccines	,submission.getField(vaccines	))
.put(vaccines_2	,submission.getField(vaccines_2	))
.put(Received_Time, format.format(today).toString())
.map();	
		
		members.setTTVisit(TT_visit);		
		
		allMembers.update(members);	
		
		womanVaccineSchedule.WomanFollowupVaccine(submission, members,  SCHEDULE_Woman_1, final_lmp, tt1_final);

		womanVaccineSchedule.WomanFollowupVaccine(submission, members,  SCHEDULE_Woman_2, tt1_final, tt2_final);
		
		womanVaccineSchedule.WomanFollowupVaccine(submission, members,  SCHEDULE_Woman_3, tt2_final, tt3_final);
	
		womanVaccineSchedule.WomanFollowupVaccine(submission, members,  SCHEDULE_Woman_4, tt3_final, tt4_final);
		
		womanVaccineSchedule.WomanFollowupVaccine(submission, members,  SCHEDULE_Woman_5, tt4_final, tt5_final);
	}
	
	
	public void child_vaccineHandler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle child_vaccine as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();

		Map<String, String> vaccine = create(ID, submission.getField(ID))
.put(REFERENCE_DATE, 
		submission.getField(REFERENCE_DATE))
.put(START_DATE, 
		submission.getField(START_DATE))
.put(END_DATE, 
		submission.getField(END_DATE))
.put(Member_Fname
	    	, submission.getField(Member_Fname))
.put(Child_mother_name
	    	, submission.getField(Child_mother_name))
.put(Child_gender
	    	, submission.getField(Child_gender))
.put(Child_dob
	    	, submission.getField(Child_dob))
.put(Member_Reg_Date
	    	, submission.getField(Member_Reg_Date))
.put(MEMBER_COUNTRY   
			, submission.getField(MEMBER_COUNTRY))
.put(MEMBER_DIVISION
	    	, submission.getField(MEMBER_DIVISION))
.put(MEMBER_DISTRICT
	    	, submission.getField(MEMBER_DISTRICT))
.put(MEMBER_UPAZILLA
	    	, submission.getField(MEMBER_UPAZILLA))
.put(MEMBER_PAURASAVA
	    	, submission.getField(MEMBER_PAURASAVA))
.put(MEMBER_UNION
	    	, submission.getField(MEMBER_UNION))
.put(MEMBER_WARD
	    	, submission.getField(MEMBER_WARD))
.put(Member_BLOCK
	    	, submission.getField(Member_BLOCK))
.put(MEMBER_HIE_FACILITIES
	    	, submission.getField(MEMBER_HIE_FACILITIES))
.put(MEMBER_ADDRESS_LINE
	    	, submission.getField(MEMBER_ADDRESS_LINE))
.put(MEMBER_GPS
	    	, submission.getField(MEMBER_GPS))
.put(existing_epi_card_number
	    	, submission.getField(existing_epi_card_number))
.put(child_was_suffering_from_a_disease_at_birth
	    	, submission.getField(child_was_suffering_from_a_disease_at_birth))
.put(contact_phone_number
	    	, submission.getField(contact_phone_number))
.put(e_bcg
	    	, submission.getField(e_bcg))
.put(e_opv0
	    	, submission.getField(e_opv0))
.put(e_penta2
	    	, submission.getField(e_penta2))
.put(e_penta1
	    	, submission.getField(e_penta1))
.put(e_penta3
	    	, submission.getField(e_penta3))
.put(e_opv1
	    	, submission.getField(e_opv1))
.put(e_opv2
	    	, submission.getField(e_opv2))
.put(e_opv3
	    	, submission.getField(e_opv3))
.put(e_pcv1
	    	, submission.getField(e_pcv1))
.put(e_pcv2
	    	, submission.getField(e_pcv2)) 
.put(e_pcv3
	    	, submission.getField(e_pcv3))
.put(e_ipv
	    	, submission.getField(e_ipv))
.put(e_measles1
	    	, submission.getField(e_measles1))
.put(e_measles2
	    	, submission.getField(e_measles2))
.put(provider_id
	    	, submission.getField(provider_id))
.put(provider_location_id
	    	, submission.getField(provider_location_id))
.put(provider_location_name
	    	, submission.getField(provider_location_name))
.put(provider_location_note
	    	, submission.getField(provider_location_note))
.put(existing_client_reg_date_note
	    	, submission.getField(existing_client_reg_date_note))
.put(epi_card_number_note
	    	, submission.getField(epi_card_number_note))
.put(first_name_note
	    	, submission.getField(first_name_note))
.put(child_age
	    	, submission.getField(child_age))
.put(child_age_days
			, submission.getField(child_age_days))
.put(calc_dob_note
	    	, submission.getField(calc_dob_note))
.put(gender_note
	    	, submission.getField(gender_note))
.put(mother_name_note
	    	, submission.getField(mother_name_note))
.put(address
	    	, submission.getField(address))
.put(address_change
	    	, submission.getField(address_change))
.put(address1
	    	, submission.getField(address1))
.put(landmark
	    	, submission.getField(landmark))
.put(center_gps
	    	, submission.getField(center_gps))
.put(child_was_suffering_from_a_disease_at_birth_note
	    	, submission.getField(child_was_suffering_from_a_disease_at_birth_note))
.put(side_effects
	    	, submission.getField(side_effects))
.put(six_weeks
	    	, submission.getField(six_weeks))
.put(ten_weeks
	    	, submission.getField(ten_weeks))
.put(forteen_weeks
	    	, submission.getField(forteen_weeks))
.put(nine_months
	    	, submission.getField(nine_months))
.put(fifteen_months
	    	, submission.getField(fifteen_months))
.put(bcg_note
	    	, submission.getField(bcg_note))
.put(opv0_note
	    	, submission.getField(opv0_note))
.put(opv1_note
	    	, submission.getField(opv1_note))
.put(pcv1_note
	    	, submission.getField(pcv1_note))
.put(penta1_note
	    	, submission.getField(penta1_note))
.put(opv2_note
	    	, submission.getField(opv2_note))
.put(pcv2_note
	    	, submission.getField(pcv2_note))
.put(penta2_note
	    	, submission.getField(penta2_note))
.put(opv3_note
	    	, submission.getField(opv3_note))
.put(pcv3_note
	    	, submission.getField(pcv3_note))
.put(penta3_note
	    	, submission.getField(penta3_note))
.put(ipv_note
	    	, submission.getField(ipv_note))
.put(measles1_note
	    	, submission.getField(ipv_note))
.put(measles2_note
	    	, submission.getField(measles2_note))
.put(vaccination_date
	    	, submission.getField(vaccination_date))
.put(final_bcg
	    	, submission.getField(final_bcg))
.put(final_opv0
	    	, submission.getField(final_opv0))
.put(final_pcv1
	    	, submission.getField(final_pcv1))
.put(final_opv1
	    	, submission.getField(final_opv1))
.put(final_penta1
	    	, submission.getField(final_penta1))
.put(final_pcv2
	    	, submission.getField(final_pcv2))
.put(final_opv2
	    	, submission.getField(final_opv2))
.put(final_penta2
	    	, submission.getField(final_penta2))
.put(final_pcv3
	    	, submission.getField(final_pcv3))
.put(final_opv3
	    	, submission.getField(final_opv3))
.put(final_penta3
	    	, submission.getField(final_penta3))
.put(final_ipv
	    	, submission.getField(final_ipv))
.put(final_measles1
	    	, submission.getField(final_measles1))
.put(final_measles2
	    	, submission.getField(final_measles2))
.put(received_time, 
		dateTime.format(today).toString()).map();			

				members.child_vaccine().add(vaccine);
				
				allMembers.update(members);
				
				childVaccineSchedule.AfterimmediateChildVisit(submission, members,  
						child_vaccination_bcg, IMD_child_bcg, Child_dob, child_age, child_age_days, final_bcg, 1, 365);
				
				childVaccineSchedule.AfterimmediateChildVisit(submission, members,  
						child_vaccination_opv0, IMD_child_opv0, Child_dob, child_age, child_age_days, final_opv0, 5, 1825);
				
				childVaccineSchedule.ChildFollowupVaccine(submission, members, 
						child_vaccination_opv1, final_opv0, child_age, child_age_days, final_opv1, 5, 1825);
				
				childVaccineSchedule.ChildFollowupVaccine(submission, members, 
						child_vaccination_opv2, final_opv1, child_age, child_age_days, final_opv2, 5, 1825);
				
				childVaccineSchedule.ChildFollowupVaccine(submission, members, 
						child_vaccination_opv3, final_opv2, child_age, child_age_days, final_opv3, 5, 1825);
				
				childVaccineSchedule.ChildFollowupVaccine(submission, members, 
						child_vaccination_penta1, Child_dob, child_age, child_age_days, final_penta1, 1, 365);
				
				childVaccineSchedule.ChildFollowupVaccine(submission, members, 
						child_vaccination_penta2, final_penta1, child_age, child_age_days, final_penta2, 1, 365);
				
				childVaccineSchedule.ChildFollowupVaccine(submission, members, 
						child_vaccination_penta3, final_penta2, child_age, child_age_days, final_penta3, 1, 365);
				
				childVaccineSchedule.ChildFollowupVaccine(submission, members, 
						child_vaccination_pcv1, Child_dob, child_age, child_age_days, final_pcv1, 5, 1825);
				
				childVaccineSchedule.ChildFollowupVaccine(submission, members, 
						child_vaccination_pcv2, final_pcv1, child_age, child_age_days, final_pcv2, 5, 1825);
				
				childVaccineSchedule.ChildFollowupVaccine(submission, members, 
						child_vaccination_pcv3, final_pcv2, child_age, child_age_days, final_pcv3, 5, 1825);
				
				childVaccineSchedule.ChildFollowupVaccine(submission, members, 
						child_vaccination_measles1, Child_dob, child_age, child_age_days, final_measles1, 5, 1825);
				
				childVaccineSchedule.ChildFollowupVaccine(submission, members, 
						child_vaccination_measles2, final_measles1, child_age, child_age_days, final_measles2, 5, 1825);
				
				childVaccineSchedule.ChildFollowupVaccine(submission, members, 
						child_vaccination_ipv, final_opv2, child_age, child_age_days, final_ipv, 15, 5475);				
	}
	
	public boolean isValidDate(String dateString) {
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    try {
	        df.parse(dateString);
	        return true;
	    } catch (ParseException e) {
	        return false;
	    }
	}
}

