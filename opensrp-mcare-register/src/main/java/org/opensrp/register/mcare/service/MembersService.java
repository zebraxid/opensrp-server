/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.CommonFormFields.ID;
import static org.opensrp.common.AllConstants.HHRegistrationFields.*;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MemberScheduleConstants.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.scheduler.service.ActionService;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllMembers;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.service.scheduling.MembersScheduleService;
import org.opensrp.register.mcare.service.scheduling.HHSchedulesService;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
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
	private ActionService actionService;
	private AllActions allActions;
	@Autowired
	public MembersService(AllHouseHolds allHouseHolds, AllMembers allMembers, HHSchedulesService hhSchedulesService, MembersScheduleService membersScheduleService, 
			ScheduleLogService scheduleLogService, AllActions allActions, ActionService actionService) {
		this.allHouseHolds = allHouseHolds;
		this.allMembers = allMembers;
		this.hhSchedulesService = hhSchedulesService;
		this.membersScheduleService = membersScheduleService;
		this.scheduleLogService = scheduleLogService;
		this.allActions = allActions;
		this.actionService = actionService;
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

			//womanVaccineSchedule.immediate_Vaccine(submission, members, membersFields, ELCO_SCHEDULE_PSRF, 
			//		IMD_ELCO_SCHEDULE_PSRF, submission.getField(REFERENCE_DATE), "Eligible");
			
			String fieldName = "Eligible";
			if (!fieldName.equalsIgnoreCase("")) {
				if (membersFields.containsKey(fieldName)) {
					if (membersFields.get(fieldName)!= null || !membersFields.get(fieldName).equalsIgnoreCase("")) {
						if(membersFields.get(fieldName).equalsIgnoreCase("1")){
							membersScheduleService.imediateEnrollIntoMilestoneOfPSRF(
								membersFields.get(ID), submission.getField(REFERENCE_DATE), submission.anmId(), submission.instanceId());
						}
					} 
				}
			}
			
			//womanVaccineSchedule.Vaccine(submission, members, membersFields, child_bcg, submission.getField(REFERENCE_DATE), "Child");

			if(submission.getField("Child") != null && !submission.getField("Child").equalsIgnoreCase(""))
			if(submission.getField("Child").equalsIgnoreCase("1")){
				if(submission.getField(REFERENCE_DATE) != null && !submission.getField(REFERENCE_DATE).equalsIgnoreCase(""))
				if(isValidDate(submission.getField(REFERENCE_DATE))){
					membersScheduleService.imediateEnrollIntoMilestoneOfChild_vaccination(
						members.caseId(),submission.getField(REFERENCE_DATE), submission.anmId(), submission.instanceId());
					
					membersScheduleService.enrollIntoSchedule(submission.entityId(), submission.getField(today), Nutrition);
				}
			}
			
			
			if(submission.getField("Adolescent") != null && !submission.getField("Adolescent").equalsIgnoreCase(""))
			if(submission.getField("Adolescent").equalsIgnoreCase("1")){
				if(submission.getField(REFERENCE_DATE) != null && !submission.getField(REFERENCE_DATE).equalsIgnoreCase(""))
				if(isValidDate(submission.getField(REFERENCE_DATE))){
					membersScheduleService.imediateEnrollIntoMilestoneOfAdolescent(
						members.caseId(),submission.getField(REFERENCE_DATE), submission.anmId(), submission.instanceId());
				}
			}
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
	
	public void Elco_Followup(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle Elco_Followup as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = Calendar.getInstance().getTime();
		Map<String, String> Elco_Followup = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
									.put(START_DATE, submission.getField(START_DATE))
									.put(END_DATE, submission.getField(END_DATE))
									.put(id, submission.getField(id))
									.put(version, submission.getField(version))
									.put(changes, submission.getField(changes))
									.put(existing_Mauzapara, submission.getField(existing_Mauzapara))
									.put(existing_GoB_HHID, submission.getField(existing_GoB_HHID))
									.put(existing_Mem_F_Name, submission.getField(existing_Mem_F_Name))
									.put(existing_Spouse_Name, submission.getField(existing_Spouse_Name))
									.put(existing_ELCO_NID, submission.getField(existing_ELCO_NID))
									.put(existing_Mem_Marital_Status, submission.getField(existing_Mem_Marital_Status))
									.put(existing_ELCO_BRID, submission.getField(existing_ELCO_BRID))
									.put(existing_Calc_Age_Confirm, submission.getField(existing_Calc_Age_Confirm))
									.put(existing_ELCO, submission.getField(existing_ELCO))
									.put(existing_Couple_No, submission.getField(existing_Couple_No))
									.put(existing_ELCO_Mobile_Number, submission.getField(existing_ELCO_Mobile_Number))
									.put(existing_HoH_F_Name, submission.getField(existing_HoH_F_Name))
									.put(existing_Final_Dist, submission.getField(existing_Final_Dist))
									.put(existing_Final_Union, submission.getField(existing_Final_Union))
									.put(existing_Final_Vill, submission.getField(existing_Final_Vill))
									.put(existing_location, submission.getField(existing_location))
									.put(today, submission.getField(today))
									.put(start, submission.getField(start))
									.put(end, submission.getField(end))
									.put(ELCO_Date, submission.getField(ELCO_Date))
									.put(Visit_Status, submission.getField(Visit_Status))
									.put(Wom_Met, submission.getField(Wom_Met))
									.put(Confirm_Info, submission.getField(Confirm_Info))
									.put(Marriage_Date, submission.getField(Marriage_Date))
									.put(LMP, submission.getField(LMP))
									.put(Preg_Status, submission.getField(Preg_Status))
									.put(Using_FP, submission.getField(Using_FP))
									.put(Birth_Control, submission.getField(Birth_Control))
									.put(Type_Oral_Pill, submission.getField(Type_Oral_Pill))
									.put(Pill_Given_No, submission.getField(Pill_Given_No))
									.put(Pill_Given_Date, submission.getField(Pill_Given_Date))
									.put(Format_Pill_Given_Date, submission.getField(Format_Pill_Given_Date))
									.put(Cond_Given_No, submission.getField(Cond_Given_No))
									.put(Cond_Given_Date, submission.getField(Cond_Given_Date))
									.put(Format_Cond_Given_Date, submission.getField(Format_Cond_Given_Date))
									.put(Injetable, submission.getField(Injetable))
									.put(Injection_Date, submission.getField(Injection_Date))
									.put(Format_Injection_Date, submission.getField(Format_Injection_Date))
									.put(Format_Next_Injection_Date, submission.getField(Format_Next_Injection_Date))
									.put(Next_Injection_Date, submission.getField(Next_Injection_Date))
									.put(Type_Implant, submission.getField(Type_Implant))
									.put(Permanent_M_Date, submission.getField(Permanent_M_Date))
									.put(Format_Permanent_M_Date, submission.getField(Format_Permanent_M_Date))
									.put(Source_BC_Product, submission.getField(Source_BC_Product))
									.put(Want_Change, submission.getField(Want_Change))
									.put(Want_To_Use, submission.getField(Want_To_Use))
									.put(Counselling, submission.getField(Counselling))
									.put(Discuss_With_Fam, submission.getField(Discuss_With_Fam))
									.put(Select_FP_Method, submission.getField(Select_FP_Method))
									.put(Has_Changed, submission.getField(Has_Changed))
									.put(Want_FP_Commodities, submission.getField(Want_FP_Commodities))
									.put(Eligible_Injectables, submission.getField(Eligible_Injectables))
									.put(Provide_Pills_Condoms, submission.getField(Provide_Pills_Condoms))
									.put(Refer, submission.getField(Refer))
									.put(TT_Status, submission.getField(TT_Status))
									.put(TT_Dose, submission.getField(TT_Dose))
									.put(Not_Preg_Note, submission.getField(Not_Preg_Note))
									.put(Pregnancy_Reg, submission.getField(Pregnancy_Reg))
									.put(Preg_Note, submission.getField(Preg_Note))
									.put(Height, submission.getField(Height))
									.put(Gestational_Age, submission.getField(Gestational_Age))
									.put(Calc_EDD, submission.getField(Calc_EDD))
									.put(Gravida, submission.getField(Gravida))
									.put(Child_Alive_Boy, submission.getField(Child_Alive_Boy))
									.put(Child_Alive_Girl, submission.getField(Child_Alive_Girl))
									.put(Total_Child_Alive, submission.getField(Total_Child_Alive))
									.put(Live_Birth, submission.getField(Live_Birth))
									.put(Age_Youngest_Child, submission.getField(Age_Youngest_Child))
									.put(Bleeding, submission.getField(Bleeding))
									.put(Last_Pregnancy, submission.getField(Last_Pregnancy))
									.put(yn_dk_label, submission.getField(yn_dk_label))
									.put(Caesarean, submission.getField(Caesarean))
									.put(Heavy_Blood_Flow, submission.getField(Heavy_Blood_Flow))
									.put(Prolong_Delivery, submission.getField(Prolong_Delivery))
									.put(Birth_Outcome, submission.getField(Birth_Outcome))
									.put(Dead_Child, submission.getField(Dead_Child))
									.put(Risky_Preg, submission.getField(Risky_Preg))
									.put(Refer_FWV, submission.getField(Refer_FWV))
									.put(Mother_Mauzapara, submission.getField(Mother_Mauzapara))
									.put(Mother_GoB_HHID, submission.getField(Mother_GoB_HHID))
									.put(Mother_F_Name, submission.getField(Mother_F_Name))
									.put(Mother_Hus_Name, submission.getField(Mother_Hus_Name))
									.put(Mother_NID, submission.getField(Mother_NID))
									.put(Mother_BRID, submission.getField(Mother_BRID))
									.put(Mother_Age, submission.getField(Mother_Age))
									.put(Mother_LMP, submission.getField(Mother_LMP))
									.put(Mother_Valid, submission.getField(Mother_Valid))
									.put(Last_FP_Method, submission.getField(Last_FP_Method))
									.put(Changed_FP_Method, submission.getField(Changed_FP_Method))
									.put(Calc_FP_Given_Date, submission.getField(Calc_FP_Given_Date))
									.put(Is_Eligible_Injectables, submission.getField(Is_Eligible_Injectables))
									.put(Married_Life, submission.getField(Married_Life))
									.put(TT_Count, submission.getField(TT_Count))
									.put(ELCO_Followup_Logic, submission.getField(ELCO_Followup_Logic))
									.put(Not_ELCO, submission.getField(Not_ELCO))
									.put(ELCO, submission.getField(ELCO))
									.put(Eligible, submission.getField(Eligible))
									.put(PW, submission.getField(PW))
									.put(Current_Form_Status, submission.getField(Current_Form_Status))
									.put(Received_Time, format.format(day).toString())
									.map();	
		
		members.Elco_Followup().add(Elco_Followup);
		allMembers.update(members);
		
		logger.info("Value found submission.getField(Visit_Status): " + submission.getField(Visit_Status));
		logger.info("Value found submission.getField(Preg_Status): " + submission.getField(Preg_Status));
  
		if (submission.getField(Visit_Status) != null && submission.getField(Visit_Status).equalsIgnoreCase("2")) 
		{
			//membersScheduleService.enrollAfterimmediateVisit(submission.entityId(),submission.anmId(),submission.getField(today),
			//	submission.instanceId(),ELCO_SCHEDULE_PSRF,IMD_ELCO_SCHEDULE_PSRF);
			
			membersScheduleService.enrollIntoMilestoneOfPSRF(submission.entityId(), submission.getField(today), submission.anmId(),
					submission.instanceId());
		} 
		else if (submission.getField(Preg_Status) != null && 
				(submission.getField(Preg_Status).equalsIgnoreCase("0") || submission.getField(Preg_Status).equalsIgnoreCase("9")))
		{
			//membersScheduleService.enrollAfterimmediateVisit(submission.entityId(),submission.anmId(),submission.getField(today),
			//		submission.instanceId(),ELCO_SCHEDULE_PSRF,IMD_ELCO_SCHEDULE_PSRF);
			
			membersScheduleService.enrollIntoMilestoneOfPSRF(submission.entityId(), submission.getField(today), submission.anmId(),
					submission.instanceId());
		}
		else{
			/*membersScheduleService.unEnrollAndCloseSchedule(members.caseId(),submission.anmId(),
					ELCO_SCHEDULE_PSRF,LocalDate.parse(submission.getField(today)));
			membersScheduleService.unEnrollAndCloseSchedule(members.caseId(),submission.anmId(),
					IMD_ELCO_SCHEDULE_PSRF,LocalDate.parse(submission.getField(today)));
			try {
				List<Action> beforeNewActions = allActions.findAlertByANMIdEntityIdScheduleName(submission.anmId(), submission.entityId(),
						ELCO_SCHEDULE_PSRF);
				if (beforeNewActions.size() > 0) {
					scheduleLogService.closeSchedule(submission.entityId(), submission.instanceId(), beforeNewActions.get(0).timestamp(),
							ELCO_SCHEDULE_PSRF);
				}

			} catch (Exception e) {
				logger.info("From Elco_Followup: " + e.getMessage());
			}*/
			
			membersScheduleService.unEnrollFromScheduleOfPSRF(submission.entityId(), submission.anmId(), "");
			try {
				List<Action> beforeNewActions = allActions.findAlertByANMIdEntityIdScheduleName(submission.anmId(), submission.entityId(),
						ELCO_SCHEDULE_PSRF);
				if (beforeNewActions.size() > 0) {
					scheduleLogService.closeSchedule(submission.entityId(), submission.instanceId(), beforeNewActions.get(0).timestamp(),
							ELCO_SCHEDULE_PSRF);
				}

			} catch (Exception e) {
				logger.info("From Elco_Followup: " + e.getMessage());
			}
			
			membersScheduleService.enrollIntoCorrectMilestoneOfANCRVCare(submission.entityId(), LocalDate.parse(LMP));
		}
		
		//womanVaccineSchedule.immediateVaccine(submission, members, SCHEDULE_Woman_BNF, IMD_SCHEDULE_Woman_BNF, Calc_EDD, Preg_Status);
		
		String fieldName = "Preg_Status";
		if (!fieldName.equalsIgnoreCase("")) {
			if(submission.getField(fieldName) != null && !submission.getField(fieldName).equalsIgnoreCase("")){
				if(submission.getField(fieldName).equalsIgnoreCase("1")){
					membersScheduleService.imediateEnrollIntoMilestoneOfBNF(
							submission.getField(ID), submission.getField(Calc_EDD), submission.anmId(), submission.instanceId());
				}
			} 
		}
		
		if (submission.getField(Is_Eligible_Injectables) != null && !submission.getField(Is_Eligible_Injectables).equalsIgnoreCase(""))
		if(submission.getField(Is_Eligible_Injectables).equalsIgnoreCase("1")){
			membersScheduleService.enrollIntoSchedule(submission.entityId(), submission.getField(Format_Injection_Date), Injectables);
		}
		
		if(submission.getField(fieldName) != null && !submission.getField(fieldName).equalsIgnoreCase("")){
		if(submission.getField(fieldName).equalsIgnoreCase("1") || 
				submission.getField(fieldName).equalsIgnoreCase("2") || submission.getField(fieldName).equalsIgnoreCase("3")){			
				membersScheduleService.unEnrollFromSchedule(submission.entityId(), submission.anmId(), Injectables);
			}
		} 
		
		if(submission.getField(Eligible_Injectables) != null && !submission.getField(Eligible_Injectables).equalsIgnoreCase("")){
			if(submission.getField(Eligible_Injectables).equalsIgnoreCase("0")){			
				membersScheduleService.unEnrollFromSchedule(submission.entityId(), submission.anmId(), Injectables);
			}
		} 
		
		if(submission.getField(Select_FP_Method) != null && !submission.getField(Select_FP_Method).equalsIgnoreCase("")){
			if(!submission.getField(Select_FP_Method).equalsIgnoreCase("3")){			
				membersScheduleService.unEnrollFromSchedule(submission.entityId(), submission.anmId(), Injectables);
			}
		} 
		
		if (submission.getField(Visit_Status) != null && !submission.getField(Visit_Status).equalsIgnoreCase("")){
		if(submission.getField(Visit_Status).equalsIgnoreCase("3") || submission.getField(Visit_Status).equalsIgnoreCase("8"))
			membersScheduleService.unEnrollFromScheduleOfBNF(submission.entityId(), submission.anmId(), "");
			try {
				List<Action> beforeNewActions = allActions.findAlertByANMIdEntityIdScheduleName(submission.anmId(), submission.entityId(),
						SCHEDULE_Woman_BNF);
				if (beforeNewActions.size() > 0) {
					scheduleLogService.closeSchedule(submission.entityId(), submission.instanceId(), beforeNewActions.get(0).timestamp(),
							SCHEDULE_Woman_BNF);
				}

			} catch (Exception e) {
				logger.info("From BNF_Visit: " + e.getMessage());
			}
		}
		
		if (submission.getField(Visit_Status) != null && !submission.getField(Visit_Status).equalsIgnoreCase(""))
			if(submission.getField(Visit_Status).equalsIgnoreCase("10")){
				membersScheduleService.unEnrollFromAllSchedules(submission.entityId());
			}
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
		Date day = Calendar.getInstance().getTime();
		Map<String, String> bnf = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
								.put(START_DATE, submission.getField(START_DATE))
								.put(END_DATE, submission.getField(END_DATE))
								.put(version, submission.getField(version))
								.put(changes, submission.getField(changes))
								.put(existing_Country, submission.getField(existing_Country))
								.put(existing_Division, submission.getField(existing_Division))
								.put(existing_District, submission.getField(existing_District))
								.put(existing_Upazilla, submission.getField(existing_Upazilla))
								.put(existing_Union, submission.getField(existing_Union))
								.put(existing_Ward, submission.getField(existing_Ward))
								.put(existing_Subunit, submission.getField(existing_Subunit))
								.put(existing_Mauzapara, submission.getField(existing_Mauzapara))
								.put(existing_Final_Vill, submission.getField(existing_Final_Vill))
								.put(existing_GPS, submission.getField(existing_GPS))
								.put(existing_location, submission.getField(existing_location))
								.put(existing_Mem_F_Name, submission.getField(existing_Mem_F_Name))
								.put(existing_GoB_HHID, submission.getField(existing_GoB_HHID))
								.put(existing_HoH_F_Name, submission.getField(existing_HoH_F_Name))
								.put(existing_Spouse_Name, submission.getField(existing_Spouse_Name))
								.put(existing_Couple_No, submission.getField(existing_Couple_No))
								.put(existing_TT_Count, submission.getField(existing_TT_Count))
								.put(existing_ELCO_Mobile_Number, submission.getField(existing_ELCO_Mobile_Number))
								.put(existing_EDD, submission.getField(existing_EDD))
								.put(existing_Height, submission.getField(existing_Height))
								.put(Today, submission.getField(Today))
								.put(start, submission.getField(start))
								.put(end , submission.getField(end ))
								.put(Visit_Date, submission.getField(Visit_Date))
								.put(Confirm_Info, submission.getField(Confirm_Info))
								.put(Visit_Status, submission.getField(Visit_Status))
								.put(Mother_Status, submission.getField(Mother_Status))
								.put(note, submission.getField(note))
								.put(Outcome_Occured, submission.getField(Outcome_Occured))
								.put(Where_Delivered, submission.getField(Where_Delivered))
								.put(Who_Delivered, submission.getField(Who_Delivered))
								.put(Delivery_Type, submission.getField(Delivery_Type))
								.put(Misoprostol_Given, submission.getField(Misoprostol_Given))
								.put(Misoprostol_Received, submission.getField(Misoprostol_Received))
								.put(Count_Misorpostol, submission.getField(Count_Misorpostol))
								.put(DOO, submission.getField(DOO))
								.put(Num_Live_Birth, submission.getField(Num_Live_Birth))
								.put(Reg_Newborn, submission.getField(Reg_Newborn))
								.put(Child_Vital_Status, submission.getField(Child_Vital_Status))
								.put(Child_Weight, submission.getField(Child_Weight))
								.put(Premature_Birth, submission.getField(Premature_Birth))
								.put(Member_Gender, submission.getField(Member_Gender))
								.put(Name_Check, submission.getField(Name_Check))
								.put(Child_Name, submission.getField(Child_Name))
								.put(Mem_F_Name, submission.getField(Mem_F_Name))
								.put(Mem_L_Name, submission.getField(Mem_L_Name))
								.put(Member_Birth_Date, submission.getField(Member_Birth_Date))
								.put(Calc_Age, submission.getField(Calc_Age))
								.put(Member_Age, submission.getField(Member_Age))
								.put(Mem_BRID, submission.getField(Mem_BRID))
								.put(Retype_Mem_BRID, submission.getField(Retype_Mem_BRID))
								.put(Mem_BRID_Concept, submission.getField(Mem_BRID_Concept))
								.put(Member_GoB_HHID, submission.getField(Member_GoB_HHID))
								.put(Member_Reg_Date, submission.getField(Member_Reg_Date))
								.put(Mem_Mobile_Number, submission.getField(Mem_Mobile_Number))
								.put(Mem_Country, submission.getField(Mem_Country))
								.put(Mem_Division, submission.getField(Mem_Division))
								.put(Mem_District, submission.getField(Mem_District))
								.put(Mem_Upazilla, submission.getField(Mem_Upazilla))
								.put(Mem_Union, submission.getField(Mem_Union))
								.put(Mem_Ward, submission.getField(Mem_Ward))
								.put(Mem_Subunit, submission.getField(Mem_Subunit))
								.put(Mem_Mauzapara, submission.getField(Mem_Mauzapara))
								.put(Mem_Village_Name, submission.getField(Mem_Village_Name))
								.put(Mem_GPS, submission.getField(Mem_GPS))
								.put(Child_Mother, submission.getField(Child_Mother))
								.put(Child_Father, submission.getField(Child_Father))
								.put(add_child, submission.getField(add_child))
								.put(Is_PNC, submission.getField(Is_PNC))
								.put(relationalid, submission.getField(relationalid))
								.put(bnf_current_formStatus, submission.getField(bnf_current_formStatus))
								.put(Received_Time, format.format(day).toString())
								.map();	
		
		members.BNFVisit().add(bnf);
		allMembers.update(members);
		
		/*if (submission.getField(Visit_status) != null && !submission.getField(Visit_status).equalsIgnoreCase("")){	
		if(submission.getField(Visit_status).equalsIgnoreCase("1")){
		if (submission.getField(Today) != null && !submission.getField(Today).equalsIgnoreCase(""))
		if(isValidDate(submission.getField(Today)))
			membersScheduleService.enrollAfterimmediateVisit(members.caseId(),submission.anmId(),submission.getField(Today),
					submission.instanceId(),SCHEDULE_Woman_BNF,IMD_SCHEDULE_Woman_BNF);	
		}
		}*/
		
		if (submission.getField(Visit_status) != null && !submission.getField(Visit_status).equalsIgnoreCase("")){	
		if(submission.getField(Visit_status).equalsIgnoreCase("1") || submission.getField(Visit_status).equalsIgnoreCase("2")
				 || submission.getField(Visit_status).equalsIgnoreCase("6")){
			membersScheduleService.enrollIntoMilestoneOfBNF(submission.entityId(), submission.getField(Today), submission.anmId(),
				submission.instanceId());
		}	
					
		else if(submission.getField(Visit_status).equalsIgnoreCase("3") || submission.getField(Visit_status).equalsIgnoreCase("4")
				|| submission.getField(Visit_status).equalsIgnoreCase("8")){	
			//membersScheduleService.unEnrollAndCloseSchedule(members.caseId(),submission.anmId(),
			//		SCHEDULE_ANC,LocalDate.parse(submission.getField(Today)));
			
			scheduleLogService.ancScheduleUnEnroll(submission.entityId(), submission.anmId(), SCHEDULE_ANC);
			actionService.markAllAlertsAsInactive(submission.entityId());
			try {
				long timestamp = actionService.getActionTimestamp(submission.anmId(), submission.entityId(), SCHEDULE_ANC);
				membersScheduleService.fullfillSchedule(submission.entityId(), SCHEDULE_ANC, submission.instanceId(), timestamp);
			} catch (Exception e) {
				logger.info("From BNF_Visit:" + e.getMessage());
			}
		}
		
		else if(submission.getField(Visit_status).equalsIgnoreCase("4") || submission.getField(Visit_status).equalsIgnoreCase("8")){
			/*membersScheduleService.unEnrollAndCloseSchedule(members.caseId(),submission.anmId(),
					SCHEDULE_Woman_BNF,LocalDate.parse(submission.getField(Today)));
			membersScheduleService.unEnrollAndCloseSchedule(members.caseId(),submission.anmId(),
					IMD_SCHEDULE_Woman_BNF,LocalDate.parse(submission.getField(Today)));
			try {
				List<Action> beforeNewActions = allActions.findAlertByANMIdEntityIdScheduleName(submission.anmId(), submission.entityId(),
						SCHEDULE_Woman_BNF);
				if (beforeNewActions.size() > 0) {
					scheduleLogService.closeSchedule(submission.entityId(), submission.instanceId(), beforeNewActions.get(0).timestamp(),
							SCHEDULE_Woman_BNF);
				}
	
			} catch (Exception e) {
				logger.info("From BNF_Visit: " + e.getMessage());
			}*/	
			
			membersScheduleService.unEnrollFromScheduleOfBNF(submission.entityId(), submission.anmId(), "");
			try {
				List<Action> beforeNewActions = allActions.findAlertByANMIdEntityIdScheduleName(submission.anmId(), submission.entityId(),
						SCHEDULE_Woman_BNF);
				if (beforeNewActions.size() > 0) {
					scheduleLogService.closeSchedule(submission.entityId(), submission.instanceId(), beforeNewActions.get(0).timestamp(),
							SCHEDULE_Woman_BNF);
				}

			} catch (Exception e) {
				logger.info("From BNF_Visit: " + e.getMessage());
			}
						
			membersScheduleService.unEnrollFromSchedule(submission.entityId(), submission.anmId(), child_reg);
		}
		}
		
		if(submission.getField(Is_PNC).equalsIgnoreCase("1")){
			membersScheduleService.enrollIntoCorrectMilestoneOfPNCRVCare(submission.entityId(), LocalDate.parse(DOO));
		}
		
		if (submission.getField(Visit_status) != null && !submission.getField(Visit_status).equalsIgnoreCase("")){
		if(submission.getField(Visit_status).equalsIgnoreCase("3")){
			/*if(submission.getField(existing_Member_Birth_Date) != null && !submission.getField(existing_Member_Birth_Date).equalsIgnoreCase(""))
			if(isValidDate(submission.getField(existing_Member_Birth_Date))){
				membersScheduleService.enrollimmediateMembersVisit(
					members.caseId(),submission.anmId(),submission.getField(existing_Member_Birth_Date),submission.instanceId(),child_bcg,child_bcg);
			}*/
			
			if(submission.getField(Today) != null && !submission.getField(Today).equalsIgnoreCase("")){
			if(isValidDate(submission.getField(Today)))
					membersScheduleService.imediateEnrollIntoMilestoneOfChild_vaccination(
							submission.entityId(), submission.getField(Today), submission.anmId(), submission.instanceId());
			}
			
			if(submission.getField(Today) != null && !submission.getField(Today).equalsIgnoreCase("")){
			if(isValidDate(submission.getField(Today)))
					membersScheduleService.enrollIntoSchedule(
							submission.entityId(), submission.getField(Today), child_reg);
			}
		}
		}
		
		if (submission.getField("Calc_Age_Newmom") != null && !submission.getField("Calc_Age_Newmom").equalsIgnoreCase("")){	
		if(Integer.parseInt(submission.getField("Calc_Age_Newmom")) <= 3){
			membersScheduleService.enrollIntoSchedule(submission.entityId(), submission.getField(Today), Nutrition);
		}	
		}
		
		if (submission.getField(Visit_Status) != null && !submission.getField(Visit_Status).equalsIgnoreCase(""))
			if(submission.getField(Visit_Status).equalsIgnoreCase("10")){
				membersScheduleService.unEnrollFromAllSchedules(submission.entityId());
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
	
public void childRegistratonHandler(FormSubmission submission) {		
		
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

			//womanVaccineSchedule.Vaccine(submission, members, membersFields, child_bcg, submission.getField(REFERENCE_DATE), "Child");

			if(submission.getField(REFERENCE_DATE) != null && !submission.getField(REFERENCE_DATE).equalsIgnoreCase(""))
			if(isValidDate(submission.getField(REFERENCE_DATE))){
				membersScheduleService.imediateEnrollIntoMilestoneOfChild_vaccination(
					members.caseId(),submission.getField(REFERENCE_DATE), submission.anmId(), submission.instanceId());
			}
		}
		
		HouseHold houseHold = allHouseHolds.findByCaseId(submission
				.entityId());

		if (houseHold == null) {
			logger.warn(format(
					"Failed to handle Child Registraton form as there is no household registered with ID: {0}",
					submission.entityId()));
			return;
		}
		
		addchildRegistratonToHH(submission, subFormData, houseHold);
		
		allHouseHolds.update(houseHold);		
				
		membersScheduleService.unEnrollFromSchedule(submission.entityId(), submission.anmId(), child_reg);
	}
	
	private void addchildRegistratonToHH(FormSubmission submission,
			SubFormData subFormData, HouseHold houseHold) {
		
		SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		for (Map<String, String> membersFields : subFormData.instances()) {

		Map<String, String> birth_Outcome = create(ID, membersFields.get(ID))
				.put(relationalid, membersFields.get(relationalid))
				.put(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
				.put(START_DATE, submission.getField(START_DATE))
				.put(END_DATE, submission.getField(END_DATE))
				.put(add_child, membersFields.get(add_child))
				.put(Calc_Age, membersFields.get(Calc_Age))
				.put(Child_Father, membersFields.get(Child_Father))
				.put(Child_Mother, membersFields.get(Child_Mother))
				.put(Child_Name, membersFields.get(Child_Name))
				.put(Child_Vital_Status, membersFields.get(Child_Vital_Status))
				.put(Child_Weight, membersFields.get(Child_Weight))
				.put(Mem_BRID, membersFields.get(Mem_BRID))
				.put(Mem_BRID_Concept, membersFields.get(Mem_BRID_Concept))
				.put(Mem_Country, membersFields.get(Mem_Country))
				.put(Mem_District, membersFields.get(Mem_District))
				.put(Mem_Division, membersFields.get(Mem_Division))
				.put(Mem_F_Name, membersFields.get(Mem_F_Name))
				.put(Mem_GPS, membersFields.get(Mem_GPS))
				.put(Mem_L_Name, membersFields.get(Mem_L_Name))
				.put(Mem_Mauzapara, membersFields.get(Mem_Mauzapara))
				.put(Mem_Mobile_Number, membersFields.get(Mem_Mobile_Number))
				.put(Mem_Subunit, membersFields.get(Mem_Subunit))
				.put(Mem_Union, membersFields.get(Mem_Union))
				.put(Mem_Upazilla, membersFields.get(Mem_Upazilla))
				.put(Mem_Village_Name, membersFields.get(Mem_Village_Name))
				.put(Mem_Ward, membersFields.get(Mem_Ward))
				.put(Member_Age, membersFields.get(Member_Age))
				.put(Member_Birth_Date, membersFields.get(Member_Birth_Date))
				.put(Member_Gender, membersFields.get(Member_Gender))
				.put(Member_GoB_HHID, membersFields.get(Member_GoB_HHID))
				.put(Member_Reg_Date, membersFields.get(Member_Reg_Date))
				.put(Name_Check, membersFields.get(Name_Check))
				.put(Premature_Birth, membersFields.get(Premature_Birth))
				.put(Reg_Newborn, membersFields.get(Reg_Newborn))
				.put(Retype_Mem_BRID, membersFields.get(Retype_Mem_BRID))
				.put(mother_relational_ID, membersFields.get(mother_relational_ID))
				.put(mother_UUID, membersFields.get(mother_UUID))
				.put(received_time, dateTime.format(today).toString()).map();
			
				if(membersFields.containsKey(Member_GoB_HHID)){
					if(!membersFields.get(Member_GoB_HHID).equalsIgnoreCase("") || membersFields.get(Member_GoB_HHID) != null){
						houseHold.Birth_Outcome().add(birth_Outcome);
				  }
				}						
		}		
	}
	
	public void child_05yrHandler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle child_05yr as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = Calendar.getInstance().getTime();

		Map<String, String> vaccine = create(ID, submission.getField(ID))
								.put(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
								.put(START_DATE, submission.getField(START_DATE))
								.put(END_DATE, submission.getField(END_DATE))
								.put(version, submission.getField(version))
								.put(changes, submission.getField(changes))
								.put(existing_GoB_HHID, submission.getField(existing_GoB_HHID))
								.put(existing_Mem_F_Name, submission.getField(existing_Mem_F_Name))
								.put(existing_Child_Mother, submission.getField(existing_Child_Mother))
								.put(existing_Child_Father, submission.getField(existing_Child_Father))
								.put(existing_Final_Vill, submission.getField(existing_Final_Vill))
								.put(existing_Mem_Mobile_Number, submission.getField(existing_Mem_Mobile_Number))
								.put(existing_Couple_No, submission.getField(existing_Couple_No))
								.put(existing_Member_Birth_Date, submission.getField(existing_Member_Birth_Date))
								.put(existing_Premature_Birth, submission.getField(existing_Premature_Birth))
								.put(existing_HR, submission.getField(existing_HR))
								.put(existing_ELCO_NID, submission.getField(existing_ELCO_NID))
								.put(existing_ELCO_BRID, submission.getField(existing_ELCO_BRID))
								.put(existing_Mem_BRID, submission.getField(existing_Mem_BRID))
								.put(existing_Mauzapara, submission.getField(existing_Mauzapara))
								.put(child_today, submission.getField(child_today))
								.put(start, submission.getField(start))
								.put(end , submission.getField(end ))
								.put(Visit_Date, submission.getField(Visit_Date))
								.put(Visit_Status, submission.getField(Visit_Status))
								.put(Met, submission.getField(Met))
								.put(Confirm_Info, submission.getField(Confirm_Info))
								.put(Child_Vaccination, submission.getField(Child_Vaccination))
								.put(DOO_35, submission.getField(DOO_35))
								.put(DOO_56, submission.getField(DOO_56))
								.put(DOO_266, submission.getField(DOO_266))
								.put(DOO_441, submission.getField(DOO_441))
								.put(Note, submission.getField(Note))
								.put(Vaccines, submission.getField(Vaccines))
								.put(BCG, submission.getField(BCG))
								.put(OPV0, submission.getField(OPV0))
								.put(PCV1, submission.getField(PCV1))
								.put(OPV1, submission.getField(OPV1))
								.put(Penta1, submission.getField(Penta1))
								.put(PCV2, submission.getField(PCV2))
								.put(OPV2, submission.getField(OPV2))
								.put(Penta2, submission.getField(Penta2))
								.put(PCV3, submission.getField(PCV3))
								.put(OPV3, submission.getField(OPV3))
								.put(Penta3, submission.getField(Penta3))
								.put(IPV, submission.getField(IPV))
								.put(Measles1, submission.getField(Measles1))
								.put(Measles2, submission.getField(Measles2))
								.put(Diseases_Prob, submission.getField(Diseases_Prob))
								.put(Detail_Diseases_Prob, submission.getField(Detail_Diseases_Prob))
								.put(Has_Referred, submission.getField(Has_Referred))
								.put(child_current_form_status, submission.getField(child_current_form_status))
								.put(received_time, dateTime.format(day).toString()).map();			

				members.child_vaccine().add(vaccine);
				
				allMembers.update(members);
				
			if (submission.getField(Visit_Status) != null && !submission.getField(Visit_Status).equalsIgnoreCase("")){	
				if(submission.getField(Visit_Status).equalsIgnoreCase("3") || submission.getField(Visit_Status).equalsIgnoreCase("2")){					
				membersScheduleService.enrollIntoMilestoneOfChild_vaccination(submission.entityId(), submission.getField(child_today), submission.anmId(),
						submission.instanceId());				
				}
			}
					
			if (submission.getField(Visit_Status) != null && !submission.getField(Visit_Status).equalsIgnoreCase("")){	
			if(submission.getField(Visit_Status).equalsIgnoreCase("8")){					
				membersScheduleService.unEnrollFromScheduleOfChild_vaccination(submission.entityId(), submission.anmId(), "");
				try {
					List<Action> beforeNewActions = allActions.findAlertByANMIdEntityIdScheduleName(submission.anmId(), submission.entityId(),
							child_bcg);
					if (beforeNewActions.size() > 0) {
						scheduleLogService.closeSchedule(submission.entityId(), submission.instanceId(), beforeNewActions.get(0).timestamp(),
								child_bcg);
					}

				} catch (Exception e) {
					logger.info("From child_05yr Handler: " + e.getMessage());
				}	
			}
			}
			
			if (submission.getField(Visit_Status) != null && !submission.getField(Visit_Status).equalsIgnoreCase(""))
				if(submission.getField(Visit_Status).equalsIgnoreCase("10")){
					membersScheduleService.unEnrollFromAllSchedules(submission.entityId());
				}
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

