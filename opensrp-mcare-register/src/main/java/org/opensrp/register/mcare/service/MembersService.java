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

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
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
	@Autowired
	public MembersService(AllHouseHolds allHouseHolds, AllMembers allMembers, HHSchedulesService hhSchedulesService,
			MembersScheduleService membersScheduleService, ScheduleLogService scheduleLogService) {
		this.allHouseHolds = allHouseHolds;
		this.allMembers = allMembers;
		this.hhSchedulesService = hhSchedulesService;
		this.membersScheduleService = membersScheduleService;
		this.scheduleLogService = scheduleLogService;
		
	}
	
	public void registerMembers(FormSubmission submission) {
		
		SubFormData subFormData = submission
				.getSubFormByName(MEMBERS_REGISTRATION_SUB_FORM_NAME);	
		  
		for (Map<String, String> membersFields : subFormData.instances()) {
			
			Members members = allMembers.findByCaseId(membersFields.get(ID))
					.withINSTANCEID(submission.instanceId())
					.withPROVIDERID(submission.anmId());					
			
			if(membersFields.containsKey(Reg_No)){
				allMembers.update(members);
				logger.info("members updated");
			}else{
				allMembers.remove(members);
				logger.info("members removed");
			}
			
			if(membersFields.containsKey(Is_woman)){
				if(!membersFields.get(Is_woman).equalsIgnoreCase("") || membersFields.get(Is_woman) != null){	
					if(membersFields.get(Is_woman).equalsIgnoreCase("1")){
						TT_Visit(submission, members, membersFields);
					}					
				}
			}
			
			if(membersFields.containsKey(Is_child))
				if(!membersFields.get(Is_child).equalsIgnoreCase("") && membersFields.get(Is_child) != null)
					if(membersFields.get(Is_child).equalsIgnoreCase("1")){
						Child_Visit(submission, members, membersFields);
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

			houseHold.withPROVIDERID(submission.anmId());
			houseHold.withINSTANCEID(submission.instanceId());
			
			allHouseHolds.update(houseHold);

			hhSchedulesService.enrollIntoMilestoneOfCensus(submission.entityId(),
					submission.getField(REFERENCE_DATE),submission.anmId(),submission.instanceId());
		}	 
	}
	
	public void Child_Visit(FormSubmission submission, Members members, Map<String, String> membersFields) {
		
		if (!membersFields.get(Date_of_BCG_OPV_0).equalsIgnoreCase("") && membersFields.get(Date_of_BCG_OPV_0) != null)
			if(isValidDate(membersFields.get(Date_of_BCG_OPV_0))){
				membersScheduleService.enrollChildVisit(members.caseId(),submission.anmId(),child_vaccination_bcg,membersFields.get(Date_of_BCG_OPV_0));
				membersScheduleService.enrollChildVisit(members.caseId(),submission.anmId(),child_vaccination_opv0,membersFields.get(Date_of_BCG_OPV_0));
			}
		
		if (!membersFields.get(Date_of_OPV_Penta_PCV_1).equalsIgnoreCase("") && membersFields.get(Date_of_OPV_Penta_PCV_1) != null)
			if(isValidDate(membersFields.get(Date_of_OPV_Penta_PCV_1))){
				membersScheduleService.enrollChildVisit(members.caseId(),submission.anmId(),child_vaccination_opv1,membersFields.get(Date_of_OPV_Penta_PCV_1));
				membersScheduleService.enrollChildVisit(members.caseId(),submission.anmId(),child_vaccination_penta1,membersFields.get(Date_of_OPV_Penta_PCV_1));
				membersScheduleService.enrollChildVisit(members.caseId(),submission.anmId(),child_vaccination_pcv1,membersFields.get(Date_of_OPV_Penta_PCV_1));
			}
		
		if (!membersFields.get(Date_of_MR).equalsIgnoreCase("") && membersFields.get(Date_of_MR) != null)
			if(isValidDate(membersFields.get(Date_of_MR)))
				membersScheduleService.enrollChildVisit(members.caseId(),submission.anmId(),child_vaccination_mr,membersFields.get(Date_of_MR));
		
		if (!membersFields.get(Date_of_Measles).equalsIgnoreCase("") && membersFields.get(Date_of_Measles) != null)
			if(isValidDate(membersFields.get(Date_of_Measles)))
				membersScheduleService.enrollChildVisit(members.caseId(),submission.anmId(),child_vaccination_measles,membersFields.get(Date_of_Measles));
			
	}
	
	public void TT_Visit(FormSubmission submission, Members members, Map<String, String> membersFields) {
		
		if(membersFields.containsKey(PVF)){
			if(!membersFields.get(PVF).equalsIgnoreCase("") && membersFields.get(PVF) != null){	
				if(membersFields.get(PVF).equalsIgnoreCase("1"))
				{						
						membersScheduleService.enrollMembersBNFVisit(members.caseId(),submission.anmId(),
								membersFields.get(lmp));
				}
			}
		}
		
		if (!membersFields.get(tt1).equalsIgnoreCase("") && membersFields.get(tt1) != null){
			if(isValidDate(membersFields.get(final_lmp)))
				membersScheduleService.enrollMembersTTVisit(members.caseId(),submission.anmId(),membersFields.get(final_lmp));
		}
		
		else{
			if (!membersFields.get(tt1_retro).equalsIgnoreCase("") && membersFields.get(tt1_retro) != null){
				if(isValidDate(membersFields.get(final_lmp)))
					membersScheduleService.enrollMembersTTVisit(members.caseId(),submission.anmId(),membersFields.get(final_lmp));
			}
		}
		
		if (!membersFields.get(Date_of_TT2).equalsIgnoreCase("") && membersFields.get(Date_of_TT2) != null)
			if(isValidDate(membersFields.get(Date_of_TT2)))
				membersScheduleService.enrollTT1_Visit(members.caseId(),submission.anmId(),membersFields.get(Date_of_TT2));
		
		if (!membersFields.get(Date_of_TT3).equalsIgnoreCase("") && membersFields.get(Date_of_TT3) != null)
			if(isValidDate(membersFields.get(Date_of_TT3)))
				membersScheduleService.enrollTT2_Visit(members.caseId(),submission.anmId(),membersFields.get(Date_of_TT3));
		
		if (!membersFields.get(Date_of_TT4).equalsIgnoreCase("") && membersFields.get(Date_of_TT4) != null)
			if(isValidDate(membersFields.get(Date_of_TT4)))
				membersScheduleService.enrollTT3_Visit(members.caseId(),submission.anmId(),membersFields.get(Date_of_TT4));
		
		if (!membersFields.get(Date_of_TT5).equalsIgnoreCase("") && membersFields.get(Date_of_TT5) != null)
			if(isValidDate(membersFields.get(Date_of_TT5)))
				membersScheduleService.enrollTT4_Visit(members.caseId(),submission.anmId(),membersFields.get(Date_of_TT5));
	
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
		Map<String, String> general = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(general_Date_Of_Reg, submission.getField(general_Date_Of_Reg))
											.put(Patient_Diagnosis, submission.getField(Patient_Diagnosis))
											.put(Treatment, submission.getField(Treatment))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withgeneral(general);
		allMembers.update(members);
		
		if (!submission.getField(TT1_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(TT1_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(TT1_Date_of_Vaccination)))
				membersScheduleService.enrollMembersBNFVisit(members.caseId(),submission.anmId(),
						submission.getField(TT1_Date_of_Vaccination));
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
					.put(Member_Fname, membersFields.get(Member_Fname))
					.put(Member_LName, membersFields.get(Member_LName))
					.put(Gender, membersFields.get(Gender))
					.put(Marital_Status, membersFields.get(Marital_Status))
					.put(Couple_No, membersFields.get(Couple_No))
					.put(lmp, membersFields.get(lmp))
					.put(edd, membersFields.get(edd))
					.put(Member_COUNTRY, membersFields.get(Member_COUNTRY))
					.put(Member_DIVISION, membersFields.get(Member_DIVISION))
					.put(Member_DISTRICT, membersFields.get(Member_DISTRICT))
					.put(Member_UPAZILLA, membersFields.get(Member_UPAZILLA))
					.put(Member_Paurasava, membersFields.get(Member_Paurasava))
					.put(Member_UNION, membersFields.get(Member_UNION))
					.put(Member_WARD, membersFields.get(Member_WARD))
					.put(Member_Address_line, membersFields.get(Member_Address_line))
					.put(Member_HIE_facilities, membersFields.get(Member_HIE_facilities))
					.put(HH_Address, membersFields.get(HH_Address))
					.put(Member_type, membersFields.get(Member_type))
					.put(Member_Unique_ID, membersFields.get(Member_Unique_ID))
					.put(Member_GPS, membersFields.get(Member_GPS))
					.put(Member_NID, membersFields.get(Member_NID))
					.put(Member_BRID, membersFields.get(Member_BRID))
					.put(Member_HID, membersFields.get(Member_HID))
					.put(member_birth_date_known, membersFields.get(member_birth_date_known))
					.put(member_birth_date, membersFields.get(member_birth_date))
					.put(age, membersFields.get(age))
					.put(calc_age, membersFields.get(calc_age))
					.put(calc_dob, membersFields.get(calc_dob))
					.put(calc_dob_confirm, membersFields.get(calc_dob_confirm))
					.put(calc_dob_estimated, membersFields.get(calc_dob_estimated))
					.put(calc_age_confirm, membersFields.get(calc_age_confirm))
					.put(birth_date_note, membersFields.get(birth_date_note))
					.put(note_age, membersFields.get(note_age))
					.put(Father_name, membersFields.get(Father_name))
					.put(Husband_name, membersFields.get(Husband_name))
					.put(WomanInfo, membersFields.get(WomanInfo))
					.put(pregnant, membersFields.get(pregnant))
					.put(FP_USER, membersFields.get(FP_USER))
					.put(FP_Methods, membersFields.get(FP_Methods))
					.put(edd_lmp, membersFields.get(edd_lmp))
					.put(ultrasound_date, membersFields.get(ultrasound_date))
					.put(ultrasound_weeks, membersFields.get(ultrasound_weeks))
					.put(edd_calc_lmp, membersFields.get(edd_calc_lmp))
					.put(edd_calc_ultrasound, membersFields.get(edd_calc_ultrasound))
					.put(edd_calc_lmp_formatted, membersFields.get(edd_calc_lmp_formatted))
					.put(edd_calc_ultrasound_formatted, membersFields.get(edd_calc_ultrasound_formatted))
					.put(lmp_calc_edd, membersFields.get(lmp_calc_edd))
					.put(lmp_calc_ultrasound, membersFields.get(lmp_calc_ultrasound))
					.put(lmp_calc_edd_formatted, membersFields.get(lmp_calc_edd_formatted))
					.put(lmp_calc_ultrasound_formatted, membersFields.get(lmp_calc_ultrasound_formatted))
					.put(final_edd, membersFields.get(final_edd))
					.put(final_lmp, membersFields.get(final_lmp))
					.put(ga_edd, membersFields.get(ga_edd))
					.put(ga_lmp, membersFields.get(ga_lmp))
					.put(ga_ult, membersFields.get(ga_ult))
					.put(final_edd_note, membersFields.get(final_edd_note))
					.put(final_lmp_note, membersFields.get(final_lmp_note))
					.put(final_ga, membersFields.get(final_ga))
					.put(final_ga_note, membersFields.get(final_ga_note))
					.put(vaccines, membersFields.get(vaccines))
					.put(tt1_retro, membersFields.get(tt1_retro))
					.put(tt_1_dose, membersFields.get(tt_1_dose))
					.put(tt2_retro, membersFields.get(tt2_retro))
					.put(tt_2_dose, membersFields.get(tt_2_dose))
					.put(tt3_retro, membersFields.get(tt3_retro))
					.put(tt_3_dose, membersFields.get(tt_3_dose))
					.put(tt4_retro, membersFields.get(tt4_retro))
					.put(tt_4_dose, membersFields.get(tt_4_dose))
					.put(vaccines_2, membersFields.get(vaccines_2))
					.put(tt1, membersFields.get(tt1))
					.put(tt_1_dose_today, membersFields.get(tt_1_dose_today))
					.put(tt2, membersFields.get(tt2))
					.put(tt_2_dose_today, membersFields.get(tt_2_dose_today))
					.put(tt3, membersFields.get(tt3))
					.put(tt_3_dose_today, membersFields.get(tt_3_dose_today))
					.put(tt4, membersFields.get(tt4))
					.put(tt_4_dose_today, membersFields.get(tt_4_dose_today))
					.put(tt5, membersFields.get(tt5))
					.put(tt_5_dose_today, membersFields.get(tt_5_dose_today))
					.put(Child_birth_date_known, membersFields.get(Child_birth_date_known))
					.put(Child_birth_date, membersFields.get(Child_birth_date))
					.put(Child_age, membersFields.get(Child_age))
					.put(Child_calc_age, membersFields.get(Child_calc_age))
					.put(Child_calc_dob, membersFields.get(Child_calc_dob))
					.put(Child_dob, membersFields.get(Child_dob))
					.put(Child_dob_estimated, membersFields.get(Child_dob_estimated))
					.put(Child_age_days, membersFields.get(Child_age_days))
					.put(Child_birth_date_note, membersFields.get(Child_birth_date_note))
					.put(Birth_Weigtht, membersFields.get(Birth_Weigtht))
					.put(Newborn_Care_Received, membersFields.get(Newborn_Care_Received))
					.put(Child_gender, membersFields.get(Child_gender))
					.put(Child_mother_name, membersFields.get(Child_mother_name))
					.put(Child_father_name, membersFields.get(Child_father_name))
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
					.put(Is_woman, membersFields.get(Is_woman))
					.put(Is_child, membersFields.get(Is_child))
					.put(PVF, membersFields.get(PVF))
					.put(received_time, dateTime.format(today).toString()).map();
			
				if(membersFields.containsKey(Reg_No)){
					if(!membersFields.get(Reg_No).equalsIgnoreCase("") || membersFields.get(Reg_No) != null){
						houseHold.MEMBERDETAILS().add(members);
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
		
		members.withgeneral(general);
		allMembers.update(members);
	}
	
	public void TT1_Visit(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle TT1_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> TT1_visit = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(Has_Vaccinated, submission.getField(Has_Vaccinated))
											.put(TT1_Date_of_Vaccination, submission.getField(TT1_Date_of_Vaccination))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withTTVisitOne(TT1_visit);
		allMembers.update(members);
		
		if (!submission.getField(TT1_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(TT1_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(TT1_Date_of_Vaccination)))
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),SCHEDULE_Woman_1);
	
	}
	
	public void TT2_Visit(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());	
		if (members == null) {
			logger.warn(format(
					"Failed to handle TT2_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> TT2_visit = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(Has_Vaccinated, submission.getField(Has_Vaccinated))
											.put(TT2_Date_of_Vaccination, submission.getField(TT2_Date_of_Vaccination))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withTTVisitTwo(TT2_visit);
		allMembers.update(members);		
		
		if (!submission.getField(TT2_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(TT2_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(TT2_Date_of_Vaccination)))
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),SCHEDULE_Woman_2);
	}
	
	public void TT3_Visit(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle TT3_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> TT3_visit = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(Has_Vaccinated, submission.getField(Has_Vaccinated))
											.put(TT3_Date_of_Vaccination, submission.getField(TT3_Date_of_Vaccination))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withTTVisitOne(TT3_visit);
		allMembers.update(members);
		
		if (!submission.getField(TT3_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(TT3_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(TT3_Date_of_Vaccination)))
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),SCHEDULE_Woman_3);
	}
	
	public void TT4_Visit(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle TT4_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> TT4_visit = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(Has_Vaccinated, submission.getField(Has_Vaccinated))
											.put(TT4_Date_of_Vaccination, submission.getField(TT4_Date_of_Vaccination))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withTTVisitOne(TT4_visit);
		allMembers.update(members);
		
		if (!submission.getField(TT4_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(TT4_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(TT4_Date_of_Vaccination)))
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),SCHEDULE_Woman_4);	
	}
	
	public void TT5_Visit(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle TT5_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> TT5_visit = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(Has_Vaccinated, submission.getField(Has_Vaccinated))
											.put(TT5_Date_of_Vaccination, submission.getField(TT5_Date_of_Vaccination))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withTTVisitFive(TT5_visit);
		allMembers.update(members);
		
		if (!submission.getField(TT5_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(TT5_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(TT5_Date_of_Vaccination)))
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),SCHEDULE_Woman_5);
	}

	public void PCV1Handler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle PCV1_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> PCV1 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(ChildVaccination_PCV1_Date_of_Vaccination, submission.getField(ChildVaccination_PCV1_Date_of_Vaccination))
											.put(Has_Vaccinated, submission.getField(Has_Vaccinated))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withPCV1(PCV1);
		allMembers.update(members);
		
		if (!submission.getField(ChildVaccination_PCV1_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(ChildVaccination_PCV1_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(ChildVaccination_PCV1_Date_of_Vaccination))){
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),child_vaccination_pcv1);
				membersScheduleService.enrollChildVisit(members.caseId(),submission.anmId(),child_vaccination_pcv2,submission.getField(ChildVaccination_PCV1_Date_of_Vaccination));
			}		
	}

	public void PCV2Handler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle PCV2_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> PCV2 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(ChildVaccination_PCV2_Date_of_Vaccination, submission.getField(ChildVaccination_PCV2_Date_of_Vaccination))
											.put(Has_Vaccinated, submission.getField(Has_Vaccinated))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withPCV2(PCV2);
		allMembers.update(members);	
		
		if (!submission.getField(ChildVaccination_PCV2_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(ChildVaccination_PCV2_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(ChildVaccination_PCV2_Date_of_Vaccination))){
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),child_vaccination_pcv2);
				membersScheduleService.enrollChildVisit(members.caseId(),submission.anmId(),child_vaccination_ipv,submission.getField(ChildVaccination_PCV2_Date_of_Vaccination));
			}		
	}

	public void PCV3Handler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle PCV3_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> PCV3 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(ChildVaccination_PCV3_Date_of_Vaccination, submission.getField(ChildVaccination_PCV3_Date_of_Vaccination))
											.put(Has_Vaccinated, submission.getField(Has_Vaccinated))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withPCV3(PCV3);
		allMembers.update(members);
		
		if (!submission.getField(ChildVaccination_PCV3_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(ChildVaccination_PCV3_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(ChildVaccination_PCV3_Date_of_Vaccination))){
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),child_vaccination_pcv3);				
			}
	}

	public void PENTA3Handler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle PENTA3_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> PENTA3 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withPENTA3(PENTA3);
		allMembers.update(members);
		
		if (!submission.getField(ChildVaccination_PENTA3_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(ChildVaccination_PENTA3_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(ChildVaccination_PENTA3_Date_of_Vaccination)))
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),child_vaccination_penta3);
	}

	public void PENTA2Handler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle PENTA2_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> PENTA2 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withPENTA2(PENTA2);
		allMembers.update(members);
		
		if (!submission.getField(ChildVaccination_PENTA2_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(ChildVaccination_PENTA2_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(ChildVaccination_PENTA2_Date_of_Vaccination))){
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),child_vaccination_penta2);
				membersScheduleService.enrollChildVisit(members.caseId(),submission.anmId(),child_vaccination_penta3,submission.getField(ChildVaccination_PENTA2_Date_of_Vaccination));
			}
	}

	public void PENTA1Handler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle PENTA1_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> PENTA1 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withPENTA1(PENTA1);
		allMembers.update(members);
		
		if (!submission.getField(ChildVaccination_PENTA1_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(ChildVaccination_PENTA1_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(ChildVaccination_PENTA1_Date_of_Vaccination))){
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),child_vaccination_penta1);
				membersScheduleService.enrollChildVisit(members.caseId(),submission.anmId(),child_vaccination_penta2,submission.getField(ChildVaccination_PENTA1_Date_of_Vaccination));
			}
	}

	public void OPV3Handler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle OPV3_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> OPV3 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(ChildVaccination_OPV3_Date_of_Vaccination, submission.getField(ChildVaccination_OPV3_Date_of_Vaccination))
											.put(Has_Vaccinated, submission.getField(Has_Vaccinated))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withOPV3(OPV3);
		allMembers.update(members);
		
		if (!submission.getField(ChildVaccination_OPV3_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(ChildVaccination_OPV3_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(ChildVaccination_OPV3_Date_of_Vaccination))){
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),child_vaccination_opv3);
				membersScheduleService.enrollChildVisit(members.caseId(),submission.anmId(),child_vaccination_pcv3,submission.getField(ChildVaccination_OPV3_Date_of_Vaccination));
			}
	}

	public void OPV2Handler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle OPV2_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> OPV2 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(ChildVaccination_OPV2_Date_of_Vaccination, submission.getField(ChildVaccination_OPV2_Date_of_Vaccination))
											.put(Has_Vaccinated, submission.getField(Has_Vaccinated))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withOPV2(OPV2);
		allMembers.update(members);
		
		if (!submission.getField(ChildVaccination_OPV2_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(ChildVaccination_OPV2_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(ChildVaccination_OPV2_Date_of_Vaccination))){
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),child_vaccination_opv2);
				membersScheduleService.enrollChildVisit(members.caseId(),submission.anmId(),child_vaccination_opv3,submission.getField(ChildVaccination_OPV2_Date_of_Vaccination));
			}
	}

	public void OPV1Handler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle OPV1_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> OPV1 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(ChildVaccination_OPV1_Date_of_Vaccination, submission.getField(ChildVaccination_OPV1_Date_of_Vaccination))
											.put(Has_Vaccinated, submission.getField(Has_Vaccinated))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withOPV1(OPV1);
		
		allMembers.update(members);
		
		if (!submission.getField(ChildVaccination_OPV1_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(ChildVaccination_OPV1_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(ChildVaccination_OPV1_Date_of_Vaccination))){
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),child_vaccination_opv1);
				membersScheduleService.enrollChildVisit(members.caseId(),submission.anmId(),child_vaccination_opv2,submission.getField(ChildVaccination_OPV1_Date_of_Vaccination));
			}
	}

	public void OPV0Handler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle OPV0_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> OPV0 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(ChildVaccination_OPV0_Date_of_Vaccination, submission.getField(ChildVaccination_OPV0_Date_of_Vaccination))
											.put(Has_Vaccinated, submission.getField(Has_Vaccinated))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withOPV0(OPV0);
		allMembers.update(members);

		if (!submission.getField(ChildVaccination_OPV0_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(ChildVaccination_OPV0_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(ChildVaccination_OPV0_Date_of_Vaccination)))
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),child_vaccination_opv0);
	}

	public void Measles1_Visit(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle Measles1_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> measlesVisit = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(Has_Vaccinated, submission.getField(Has_Vaccinated))
											.put(measles_Date_of_Vaccination, submission.getField(measles_Date_of_Vaccination))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withMeaslesVisit(measlesVisit);
		allMembers.update(members);
		
		if (!submission.getField(measles_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(measles_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(measles_Date_of_Vaccination)))
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),SCHEDULE_Woman_Measles);
	}

	public void Measles2Handler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle Measles2_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> Measles2 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(ChildVaccination_Measles_Date_of_Vaccination, submission.getField(ChildVaccination_Measles_Date_of_Vaccination))
											.put(Has_Vaccinated, submission.getField(Has_Vaccinated))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withMeasles2(Measles2);
		allMembers.update(members);

		if (!submission.getField(ChildVaccination_Measles_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(ChildVaccination_Measles_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(ChildVaccination_Measles_Date_of_Vaccination)))
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),child_vaccination_measles);
	}

	public void IPVHandler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle IPV_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> IPV = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(ChildVaccination_IPV_Date_of_Vaccination, submission.getField(ChildVaccination_IPV_Date_of_Vaccination))
											.put(Has_Vaccinated, submission.getField(Has_Vaccinated))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withIPV(IPV);
		allMembers.update(members);
		
		if (!submission.getField(ChildVaccination_IPV_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(ChildVaccination_IPV_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(ChildVaccination_IPV_Date_of_Vaccination))){
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),child_vaccination_ipv);				
			}
	}

	public void BCGHandler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle BCG_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> BCG = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(ChildVaccination_BCG_Date_of_Vaccination, submission.getField(ChildVaccination_BCG_Date_of_Vaccination))
											.put(Has_Vaccinated, submission.getField(Has_Vaccinated))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withBCG(BCG);
		allMembers.update(members);

		if (!submission.getField(ChildVaccination_BCG_Date_of_Vaccination).equalsIgnoreCase("") && submission.getField(ChildVaccination_BCG_Date_of_Vaccination) != null)
			if(isValidDate(submission.getField(ChildVaccination_BCG_Date_of_Vaccination)))
				membersScheduleService.unEnrollFromSchedule(members.caseId(),submission.anmId(),child_vaccination_bcg);
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

