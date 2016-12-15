/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.HHRegistrationFields.*;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.*;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MemberScheduleConstants.*;
import static org.opensrp.common.AllConstants.TT_VisitFields.Received_Time;
import static org.opensrp.common.util.EasyMap.create;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;
import org.opensrp.register.mcare.service.scheduling.MembersScheduleService;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
import org.opensrp.scheduler.service.ActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembersFollowupService {
	private static Logger logger = LoggerFactory.getLogger(MembersService.class
			.toString());

	private AllMembers allMembers;
	private ActionService actionService;
	private MembersScheduleService membersScheduleService;
	private ScheduleLogService scheduleLogService;
	@Autowired
	public MembersFollowupService(AllMembers allMembers, ActionService actionService, 
			MembersScheduleService membersScheduleService, ScheduleLogService scheduleLogService) {
		this.allMembers = allMembers;
		this.actionService = actionService;
		this.membersScheduleService = membersScheduleService;
	}
	
	public void PNCVisit1(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle PNCVisit1 as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = Calendar.getInstance().getTime();
		Map<String, String> PNCVisit1 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
									.put(START_DATE, submission.getField(START_DATE))
									.put(END_DATE, submission.getField(END_DATE))
									.put(version, submission.getField(version))
									.put(changes, submission.getField(changes))
									.put(existing_GoB_HHID, submission.getField(existing_GoB_HHID))
									.put(existing_ELCO_NID, submission.getField(existing_ELCO_NID))
									.put(existing_ELCO_BRID, submission.getField(existing_ELCO_BRID))
									.put(existing_Mem_F_Name, submission.getField(existing_Mem_F_Name))
									.put(existing_Spouse_Name, submission.getField(existing_Spouse_Name))
									.put(existing_LMP, submission.getField(existing_LMP))
									.put(existing_ELCO, submission.getField(existing_ELCO))
									.put(existing_HoH_F_Name, submission.getField(existing_HoH_F_Name))
									.put(existing_Couple_No, submission.getField(existing_Couple_No))
									.put(existing_Final_Vill, submission.getField(existing_Final_Vill))
									.put(exising_Union, submission.getField(exising_Union))
									.put(exising_Upazilla, submission.getField(exising_Upazilla))
									.put(existing_TT_Count, submission.getField(existing_TT_Count))
									.put(existing_ELCO_Mobile_Number, submission.getField(existing_ELCO_Mobile_Number))
									.put(existing_DOO, submission.getField(existing_DOO))
									.put(existing_Total_Child_Alive, submission.getField(existing_Total_Child_Alive))
									.put(existing_Num_Live_Birth, submission.getField(existing_Num_Live_Birth))
									.put(existing_Premature_Birth, submission.getField(existing_Premature_Birth))
									.put(existing_Delivery_Type, submission.getField(existing_Delivery_Type))
									.put(existing_Visit_Status, submission.getField(existing_Visit_Status))
									.put(existing_Risky_Preg, submission.getField(existing_Risky_Preg))
									.put(existing_Where_Delivered, submission.getField(existing_Where_Delivered))
									.put(existing_Mauzapara, submission.getField(existing_Mauzapara))
									.put(today, submission.getField(today))
									.put(start, submission.getField(start))
									.put(end , submission.getField(end ))
									.put(Visit_Status, submission.getField(Visit_Status))
									.put(Met, submission.getField(Met))
									.put(Confirm_Info, submission.getField(Confirm_Info))
									.put(Visit_Date, submission.getField(Visit_Date))
									.put(PNC1_Due_Date, submission.getField(PNC1_Due_Date))
									.put(Is_On_Time, submission.getField(Is_On_Time))
									.put(PNC1_Post_Due_Date, submission.getField(PNC1_Post_Due_Date))
									.put(Is_Post_Due, submission.getField(Is_Post_Due))
									.put(PNC1_Expired_Date, submission.getField(PNC1_Expired_Date))
									.put(Is_Expired, submission.getField(Is_Expired))
									.put(Calc_On_Time, submission.getField(Calc_On_Time))
									.put(Has_PNC_Given_On_Time, submission.getField(Has_PNC_Given_On_Time))
									.put(Symptoms, submission.getField(Symptoms))
									.put(yn_dk_label, submission.getField(yn_dk_label))
									.put(Menstruation, submission.getField(Menstruation))
									.put(Headache_Blur_Vision, submission.getField(Headache_Blur_Vision))
									.put(High_Fever, submission.getField(High_Fever))
									.put(Convulsions, submission.getField(Convulsions))
									.put(Is_Critical, submission.getField(Is_Critical))
									.put(Is_Reffered, submission.getField(Is_Reffered))
									.put(Newborn, submission.getField(Newborn))
									.put(yn_dk_label, submission.getField(yn_dk_label))
									.put(Is_Cleaned, submission.getField(Is_Cleaned))
									.put(Chlorhexidin, submission.getField(Chlorhexidin))
									.put(Breasmilk_Fed, submission.getField(Breasmilk_Fed))
									.put(Not_Bathed, submission.getField(Not_Bathed))
									.put(Comment, submission.getField(Comment))
									.put(Visit_No, submission.getField(Visit_No))
									.put(HR, submission.getField(HR))
									.put(pnc1_current_formStatus, submission.getField(pnc1_current_formStatus))
									.put(Received_Time, format.format(day).toString())
									.map();	
		
		members.setPNCVisit1(PNCVisit1);
		allMembers.update(members);
		
		membersScheduleService.fullfillMilestone(submission.entityId(), submission.anmId(), SCHEDULE_PNC, new LocalDate());

		String pattern = "yyyy-MM-dd";
		// DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);

		DateTime dateTime = DateTime.parse(submission.getField(today));
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		String referenceDate = fmt.print(dateTime);
		membersScheduleService.enrollPNCForMother(submission.entityId(), SCHEDULE_PNC_2, LocalDate.parse(referenceDate));
	}
	
	public void PNCVisit2(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle PNCVisit2 as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = Calendar.getInstance().getTime();
		Map<String, String> PNCVisit2 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
									.put(START_DATE, submission.getField(START_DATE))
									.put(END_DATE, submission.getField(END_DATE))									
									.put(version, submission.getField(version))
									.put(changes, submission.getField(changes))
									.put(existing_GoB_HHID, submission.getField(existing_GoB_HHID))
									.put(existing_ELCO_NID, submission.getField(existing_ELCO_NID))
									.put(existing_ELCO_BRID, submission.getField(existing_ELCO_BRID))
									.put(existing_Mem_F_Name, submission.getField(existing_Mem_F_Name))
									.put(existing_Spouse_Name, submission.getField(existing_Spouse_Name))
									.put(existing_LMP, submission.getField(existing_LMP))
									.put(existing_ELCO, submission.getField(existing_ELCO))
									.put(existing_HoH_F_Name, submission.getField(existing_HoH_F_Name))
									.put(existing_Couple_No, submission.getField(existing_Couple_No))
									.put(existing_Final_Vill, submission.getField(existing_Final_Vill))
									.put(exising_Union, submission.getField(exising_Union))
									.put(exising_Upazilla, submission.getField(exising_Upazilla))
									.put(existing_TT_Count, submission.getField(existing_TT_Count))
									.put(existing_ELCO_Mobile_Number, submission.getField(existing_ELCO_Mobile_Number))
									.put(existing_DOO, submission.getField(existing_DOO))
									.put(existing_Total_Child_Alive, submission.getField(existing_Total_Child_Alive))
									.put(existing_Num_Live_Birth, submission.getField(existing_Num_Live_Birth))
									.put(existing_Premature_Birth, submission.getField(existing_Premature_Birth))
									.put(existing_Delivery_Type, submission.getField(existing_Delivery_Type))
									.put(existing_Visit_Status, submission.getField(existing_Visit_Status))
									.put(existing_Risky_Preg, submission.getField(existing_Risky_Preg))
									.put(existing_Where_Delivered, submission.getField(existing_Where_Delivered))
									.put(existing_Mauzapara, submission.getField(existing_Mauzapara))
									.put(today, submission.getField(today))
									.put(start, submission.getField(start))
									.put(end , submission.getField(end ))
									.put(Visit_Status, submission.getField(Visit_Status))
									.put(Met, submission.getField(Met))
									.put(Confirm_Info, submission.getField(Confirm_Info))
									.put(Visit_Date, submission.getField(Visit_Date))
									.put(PNC2_Due_Date, submission.getField(PNC2_Due_Date))
									.put(Is_On_Time, submission.getField(Is_On_Time))
									.put(PNC2_Post_Due_Date, submission.getField(PNC2_Post_Due_Date))
									.put(Is_Post_Due, submission.getField(Is_Post_Due))
									.put(PNC2_Expired_Date, submission.getField(PNC2_Expired_Date))
									.put(Is_Expired, submission.getField(Is_Expired))
									.put(Calc_On_Time, submission.getField(Calc_On_Time))
									.put(Has_PNC_Given_On_Time, submission.getField(Has_PNC_Given_On_Time))
									.put(Symptoms, submission.getField(Symptoms))
									.put(yn_dk_label, submission.getField(yn_dk_label))
									.put(Menstruation, submission.getField(Menstruation))
									.put(Headache_Blur_Vision, submission.getField(Headache_Blur_Vision))
									.put(High_Fever, submission.getField(High_Fever))
									.put(Convulsions, submission.getField(Convulsions))
									.put(Newborn, submission.getField(Newborn))
									.put(yn_dk_label, submission.getField(yn_dk_label))
									.put(Is_Cleaned, submission.getField(Is_Cleaned))
									.put(Chlorhexidin, submission.getField(Chlorhexidin))
									.put(Breasmilk_Fed, submission.getField(Breasmilk_Fed))
									.put(Not_Bathed, submission.getField(Not_Bathed))
									.put(Comment, submission.getField(Comment))
									.put(Visit_No, submission.getField(Visit_No))
									.put(HR, submission.getField(HR))
									.put(PNC2_current_formStatus, submission.getField(PNC2_current_formStatus))
									.put(Received_Time, format.format(day).toString())
									.map();
		
		members.setPNCVisit2(PNCVisit2);
		allMembers.update(members);
		
		membersScheduleService.fullfillMilestone(submission.entityId(), submission.anmId(), SCHEDULE_PNC, new LocalDate());

		String pattern = "yyyy-MM-dd";
		// DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);

		DateTime dateTime = DateTime.parse(submission.getField(today));
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		String referenceDate = fmt.print(dateTime);
		membersScheduleService.enrollPNCForMother(submission.entityId(), SCHEDULE_PNC_3, LocalDate.parse(referenceDate));
	}
	
	public void PNCVisit3(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle PNCVisit3 as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = Calendar.getInstance().getTime();
		Map<String, String> PNCVisit3 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
									.put(START_DATE, submission.getField(START_DATE))
									.put(END_DATE, submission.getField(END_DATE))									
									.put(version, submission.getField(version))
									.put(changes, submission.getField(changes))
									.put(existing_GoB_HHID, submission.getField(existing_GoB_HHID))
									.put(existing_ELCO_NID, submission.getField(existing_ELCO_NID))
									.put(existing_ELCO_BRID, submission.getField(existing_ELCO_BRID))
									.put(existing_Mem_F_Name, submission.getField(existing_Mem_F_Name))
									.put(existing_Spouse_Name, submission.getField(existing_Spouse_Name))
									.put(existing_LMP, submission.getField(existing_LMP))
									.put(existing_ELCO, submission.getField(existing_ELCO))
									.put(existing_HoH_F_Name, submission.getField(existing_HoH_F_Name))
									.put(existing_Couple_No, submission.getField(existing_Couple_No))
									.put(existing_Final_Vill, submission.getField(existing_Final_Vill))
									.put(exising_Union, submission.getField(exising_Union))
									.put(exising_Upazilla, submission.getField(exising_Upazilla))
									.put(existing_TT_Count, submission.getField(existing_TT_Count))
									.put(existing_ELCO_Mobile_Number, submission.getField(existing_ELCO_Mobile_Number))
									.put(existing_DOO, submission.getField(existing_DOO))
									.put(existing_Total_Child_Alive, submission.getField(existing_Total_Child_Alive))
									.put(existing_Num_Live_Birth, submission.getField(existing_Num_Live_Birth))
									.put(existing_Premature_Birth, submission.getField(existing_Premature_Birth))
									.put(existing_Delivery_Type, submission.getField(existing_Delivery_Type))
									.put(existing_Visit_Status, submission.getField(existing_Visit_Status))
									.put(existing_Risky_Preg, submission.getField(existing_Risky_Preg))
									.put(existing_Where_Delivered, submission.getField(existing_Where_Delivered))
									.put(existing_Mauzapara, submission.getField(existing_Mauzapara))
									.put(today, submission.getField(today))
									.put(start, submission.getField(start))
									.put(end , submission.getField(end ))
									.put(Visit_Status, submission.getField(Visit_Status))
									.put(Met, submission.getField(Met))
									.put(Confirm_Info, submission.getField(Confirm_Info))
									.put(Visit_Date, submission.getField(Visit_Date))
									.put(PNC3_Due_Date, submission.getField(PNC3_Due_Date))
									.put(Is_On_Time, submission.getField(Is_On_Time))
									.put(PNC3_Post_Due_Date, submission.getField(PNC3_Post_Due_Date))
									.put(Is_Post_Due, submission.getField(Is_Post_Due))
									.put(PNC3_Expired_Date, submission.getField(PNC3_Expired_Date))
									.put(Is_Expired, submission.getField(Is_Expired))
									.put(Calc_On_Time, submission.getField(Calc_On_Time))
									.put(Has_PNC_Given_On_Time, submission.getField(Has_PNC_Given_On_Time))
									.put(Symptoms, submission.getField(Symptoms))
									.put(yn_dk_label, submission.getField(yn_dk_label))
									.put(Menstruation, submission.getField(Menstruation))
									.put(Headache_Blur_Vision, submission.getField(Headache_Blur_Vision))
									.put(High_Fever, submission.getField(High_Fever))
									.put(Convulsions, submission.getField(Convulsions))
									.put(Newborn, submission.getField(Newborn))
									.put(yn_dk_label, submission.getField(yn_dk_label))
									.put(Is_Cleaned, submission.getField(Is_Cleaned))
									.put(Chlorhexidin, submission.getField(Chlorhexidin))
									.put(Breasmilk_Fed, submission.getField(Breasmilk_Fed))
									.put(Not_Bathed, submission.getField(Not_Bathed))
									.put(Comment, submission.getField(Comment))
									.put(Visit_No, submission.getField(Visit_No))
									.put(HR, submission.getField(HR))
									.put(PNC3_current_formStatus, submission.getField(PNC3_current_formStatus))
									.put(Received_Time, format.format(day).toString())
									.map();	
		
		members.setPNCVisit3(PNCVisit3);
		allMembers.update(members);
		
		membersScheduleService.fullfillMilestone(submission.entityId(), submission.anmId(), SCHEDULE_PNC, new LocalDate());

		String pattern = "yyyy-MM-dd";
		// DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);

		DateTime dateTime = DateTime.parse(submission.getField(today));
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		String referenceDate = fmt.print(dateTime);
		membersScheduleService.enrollPNCForMother(submission.entityId(), SCHEDULE_PNC_4, LocalDate.parse(referenceDate));
	}
	
	public void PNCVisit4(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle PNCVisit4 as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = Calendar.getInstance().getTime();
		Map<String, String> PNCVisit4 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
									.put(START_DATE, submission.getField(START_DATE))
									.put(END_DATE, submission.getField(END_DATE))									
									.put(version, submission.getField(version))
									.put(changes, submission.getField(changes))
									.put(existing_GoB_HHID, submission.getField(existing_GoB_HHID))
									.put(existing_ELCO_NID, submission.getField(existing_ELCO_NID))
									.put(existing_ELCO_BRID, submission.getField(existing_ELCO_BRID))
									.put(existing_Mem_F_Name, submission.getField(existing_Mem_F_Name))
									.put(existing_Spouse_Name, submission.getField(existing_Spouse_Name))
									.put(existing_LMP, submission.getField(existing_LMP))
									.put(existing_ELCO, submission.getField(existing_ELCO))
									.put(existing_HoH_F_Name, submission.getField(existing_HoH_F_Name))
									.put(existing_Couple_No, submission.getField(existing_Couple_No))
									.put(existing_Final_Vill, submission.getField(existing_Final_Vill))
									.put(exising_Union, submission.getField(exising_Union))
									.put(exising_Upazilla, submission.getField(exising_Upazilla))
									.put(existing_TT_Count, submission.getField(existing_TT_Count))
									.put(existing_ELCO_Mobile_Number, submission.getField(existing_ELCO_Mobile_Number))
									.put(existing_DOO, submission.getField(existing_DOO))
									.put(existing_Total_Child_Alive, submission.getField(existing_Total_Child_Alive))
									.put(existing_Num_Live_Birth, submission.getField(existing_Num_Live_Birth))
									.put(existing_Premature_Birth, submission.getField(existing_Premature_Birth))
									.put(existing_Delivery_Type, submission.getField(existing_Delivery_Type))
									.put(existing_Visit_Status, submission.getField(existing_Visit_Status))
									.put(existing_Risky_Preg, submission.getField(existing_Risky_Preg))
									.put(existing_Where_Delivered, submission.getField(existing_Where_Delivered))
									.put(existing_Mauzapara, submission.getField(existing_Mauzapara))
									.put(today, submission.getField(today))
									.put(start, submission.getField(start))
									.put(end , submission.getField(end ))
									.put(Visit_Status, submission.getField(Visit_Status))
									.put(Met, submission.getField(Met))
									.put(Confirm_Info, submission.getField(Confirm_Info))
									.put(Visit_Date, submission.getField(Visit_Date))
									.put(PNC4_Due_Date, submission.getField(PNC4_Due_Date))
									.put(Is_On_Time, submission.getField(Is_On_Time))
									.put(PNC4_Post_Due_Date, submission.getField(PNC4_Post_Due_Date))
									.put(Is_Post_Due, submission.getField(Is_Post_Due))
									.put(PNC4_Expired_Date, submission.getField(PNC4_Expired_Date))
									.put(Is_Expired, submission.getField(Is_Expired))
									.put(Calc_On_Time, submission.getField(Calc_On_Time))
									.put(Has_PNC_Given_On_Time, submission.getField(Has_PNC_Given_On_Time))
									.put(Symptoms, submission.getField(Symptoms))
									.put(yn_dk_label, submission.getField(yn_dk_label))
									.put(Menstruation, submission.getField(Menstruation))
									.put(Headache_Blur_Vision, submission.getField(Headache_Blur_Vision))
									.put(High_Fever, submission.getField(High_Fever))
									.put(Convulsions, submission.getField(Convulsions))
									.put(Newborn, submission.getField(Newborn))
									.put(yn_dk_label, submission.getField(yn_dk_label))
									.put(Is_Cleaned, submission.getField(Is_Cleaned))
									.put(Chlorhexidin, submission.getField(Chlorhexidin))
									.put(Breasmilk_Fed, submission.getField(Breasmilk_Fed))
									.put(Not_Bathed, submission.getField(Not_Bathed))
									.put(Comment, submission.getField(Comment))
									.put(Visit_No, submission.getField(Visit_No))
									.put(HR, submission.getField(HR))
									.put(PNC4_current_formStatus, submission.getField(PNC4_current_formStatus))
									.put(Received_Time, format.format(day).toString())
									.map();	
		
		members.setPNCVisit4(PNCVisit4);
		allMembers.update(members);
		
		membersScheduleService.unEnrollFromSchedule(submission.entityId(), submission.anmId(), SCHEDULE_PNC);
	}
	
	
	public void ANCVisit1(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle ANCVisit1 as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = Calendar.getInstance().getTime();
		Map<String, String> ANCVisit1 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
									.put(START_DATE, submission.getField(START_DATE))
									.put(END_DATE, submission.getField(END_DATE))									
									.put(version, submission.getField(version))
									.put(changes, submission.getField(changes))
									.put(existing_GoB_HHID, submission.getField(existing_GoB_HHID))
									.put(existing_ELCO_NID, submission.getField(existing_ELCO_NID))
									.put(existing_ELCO_BRID, submission.getField(existing_ELCO_BRID))
									.put(existing_Mem_F_Name, submission.getField(existing_Mem_F_Name))
									.put(existing_Mem_Marital_Status, submission.getField(existing_Mem_Marital_Status))
									.put(existing_Marriage_Life, submission.getField(existing_Marriage_Life))
									.put(existing_Spouse_Name, submission.getField(existing_Spouse_Name))
									.put(existing_Gestational_Age, submission.getField(existing_Gestational_Age))
									.put(existing_EDD, submission.getField(existing_EDD))
									.put(existing_ELCO, submission.getField(existing_ELCO))
									.put(existing_HoH_F_Name, submission.getField(existing_HoH_F_Name))
									.put(existing_Couple_No, submission.getField(existing_Couple_No))
									.put(existing_Final_Vill, submission.getField(existing_Final_Vill))
									.put(existing_TT_Count, submission.getField(existing_TT_Count))
									.put(existing_ELCO_Mobile_Number, submission.getField(existing_ELCO_Mobile_Number))
									.put(existing_Mauzapara, submission.getField(existing_Mauzapara))
									.put(existing_Total_Child_Alive, submission.getField(existing_Total_Child_Alive))
									.put(existing_Age_Youngest_Child, submission.getField(existing_Age_Youngest_Child))
									.put(existing_Gravida, submission.getField(existing_Gravida))
									.put(existing_Bleeding, submission.getField(existing_Bleeding))
									.put(existing_Caesarean, submission.getField(existing_Caesarean))
									.put(existing_Heavy_Blood_Flow, submission.getField(existing_Heavy_Blood_Flow))
									.put(existing_Prolong_Delivery, submission.getField(existing_Prolong_Delivery))
									.put(existing_Birth_Outcome, submission.getField(existing_Birth_Outcome))
									.put(existing_Dead_Child, submission.getField(existing_Dead_Child))
									.put(existing_Risky_Preg, submission.getField(existing_Risky_Preg))
									.put(existing_Height, submission.getField(existing_Height))
									.put(existing_LMP, submission.getField(existing_LMP))
									.put(today, submission.getField(today))
									.put(start, submission.getField(start))
									.put(end , submission.getField(end ))
									.put(Visit_Status, submission.getField(Visit_Status))
									.put(Met, submission.getField(Met))
									.put(Confirm_Info, submission.getField(Confirm_Info))
									.put(Visit_Date, submission.getField(Visit_Date))
									.put(ANC1_Due_Date, submission.getField(ANC1_Due_Date))
									.put(Is_On_Time, submission.getField(Is_On_Time))
									.put(ANC1_Post_Due_Date, submission.getField(ANC1_Post_Due_Date))
									.put(Is_Post_Due, submission.getField(Is_Post_Due))
									.put(ANC1_Expired_Date, submission.getField(ANC1_Expired_Date))
									.put(Is_Expired, submission.getField(Is_Expired))
									.put(Preg_Status, submission.getField(Preg_Status))
									.put(Symptoms, submission.getField(Symptoms))
									.put(yn_dk_label, submission.getField(yn_dk_label))
									.put(Menstruation, submission.getField(Menstruation))
									.put(Headache_Blur_Vision, submission.getField(Headache_Blur_Vision))
									.put(High_Fever, submission.getField(High_Fever))
									.put(Prolonged_Delivery, submission.getField(Prolonged_Delivery))
									.put(Position_Child_During_Delivery, submission.getField(Position_Child_During_Delivery))
									.put(Convulsions, submission.getField(Convulsions))
									.put(Is_Critical, submission.getField(Is_Critical))
									.put(Is_Reffered, submission.getField(Is_Reffered))
									.put(IFA_Received, submission.getField(IFA_Received))
									.put(Not_Eligible, submission.getField(Not_Eligible))
									.put(ELCO, submission.getField(ELCO))
									.put(anc1_current_formStatus, submission.getField(anc1_current_formStatus))
									.put(relationalid, submission.getField(relationalid))
									.put(Received_Time, format.format(day).toString())
									.map();	
		
		members.setANCVisit1(ANCVisit1);
		allMembers.update(members);
		
		membersScheduleService.fullfillMilestone(submission.entityId(), submission.anmId(), SCHEDULE_ANC, new LocalDate());
		//actionService.markAllAlertsAsInactive(submission.entityId());
		try {
			long timestamp = actionService.getActionTimestamp(submission.anmId(), submission.entityId(), SCHEDULE_ANC);
			membersScheduleService.fullfillSchedule(submission.entityId(), SCHEDULE_ANC, submission.instanceId(), timestamp);
		} catch (Exception e) {
			logger.info("From ancVisitTwo:" + e.getMessage());
		}
	}
	
	public void ANCVisit2(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle ANCVisit2 as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = Calendar.getInstance().getTime();
		Map<String, String> ANCVisit2 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
									.put(START_DATE, submission.getField(START_DATE))
									.put(END_DATE, submission.getField(END_DATE))								
									.put(version, submission.getField(version))
									.put(changes, submission.getField(changes))
									.put(existing_GoB_HHID, submission.getField(existing_GoB_HHID))
									.put(existing_ELCO_NID, submission.getField(existing_ELCO_NID))
									.put(existing_ELCO_BRID, submission.getField(existing_ELCO_BRID))
									.put(existing_Mem_F_Name, submission.getField(existing_Mem_F_Name))
									.put(existing_Mem_Marital_Status, submission.getField(existing_Mem_Marital_Status))
									.put(existing_Marriage_Life, submission.getField(existing_Marriage_Life))
									.put(existing_Spouse_Name, submission.getField(existing_Spouse_Name))
									.put(existing_Gestational_Age, submission.getField(existing_Gestational_Age))
									.put(existing_EDD, submission.getField(existing_EDD))
									.put(existing_ELCO, submission.getField(existing_ELCO))
									.put(existing_HoH_F_Name, submission.getField(existing_HoH_F_Name))
									.put(existing_Couple_No, submission.getField(existing_Couple_No))
									.put(existing_Final_Vill, submission.getField(existing_Final_Vill))
									.put(existing_TT_Count, submission.getField(existing_TT_Count))
									.put(existing_ELCO_Mobile_Number, submission.getField(existing_ELCO_Mobile_Number))
									.put(existing_Mauzapara, submission.getField(existing_Mauzapara))
									.put(existing_Total_Child_Alive, submission.getField(existing_Total_Child_Alive))
									.put(existing_Age_Youngest_Child, submission.getField(existing_Age_Youngest_Child))
									.put(existing_Gravida, submission.getField(existing_Gravida))
									.put(existing_Bleeding, submission.getField(existing_Bleeding))
									.put(existing_Caesarean, submission.getField(existing_Caesarean))
									.put(existing_Heavy_Blood_Flow, submission.getField(existing_Heavy_Blood_Flow))
									.put(existing_Prolong_Delivery, submission.getField(existing_Prolong_Delivery))
									.put(existing_Birth_Outcome, submission.getField(existing_Birth_Outcome))
									.put(existing_Dead_Child, submission.getField(existing_Dead_Child))
									.put(existing_Risky_Preg, submission.getField(existing_Risky_Preg))
									.put(existing_Height, submission.getField(existing_Height))
									.put(existing_LMP, submission.getField(existing_LMP))
									.put(today, submission.getField(today))
									.put(start, submission.getField(start))
									.put(end , submission.getField(end ))
									.put(Visit_Status, submission.getField(Visit_Status))
									.put(Met, submission.getField(Met))
									.put(Confirm_Info, submission.getField(Confirm_Info))
									.put(Visit_Date, submission.getField(Visit_Date))
									.put(ANC2_Due_Date, submission.getField(ANC2_Due_Date))
									.put(Is_On_Time, submission.getField(Is_On_Time))
									.put(ANC2_Post_Due_Date, submission.getField(ANC2_Post_Due_Date))
									.put(Is_Post_Due, submission.getField(Is_Post_Due))
									.put(ANC2_Expired_Date, submission.getField(ANC2_Expired_Date))
									.put(Is_Expired, submission.getField(Is_Expired))
									.put(Preg_Status, submission.getField(Preg_Status))
									.put(Symptoms, submission.getField(Symptoms))
									.put(yn_dk_label, submission.getField(yn_dk_label))
									.put(Menstruation, submission.getField(Menstruation))
									.put(Headache_Blur_Vision, submission.getField(Headache_Blur_Vision))
									.put(High_Fever, submission.getField(High_Fever))
									.put(Prolonged_Delivery, submission.getField(Prolonged_Delivery))
									.put(Position_Child_During_Delivery, submission.getField(Position_Child_During_Delivery))
									.put(Convulsions, submission.getField(Convulsions))
									.put(Is_Critical, submission.getField(Is_Critical))
									.put(Is_Reffered, submission.getField(Is_Reffered))
									.put(IFA_Received, submission.getField(IFA_Received))
									.put(Not_Eligible, submission.getField(Not_Eligible))
									.put(ELCO, submission.getField(ELCO))
									.put(anc2_current_formStatus, submission.getField(anc2_current_formStatus))
									.put(relationalid, submission.getField(relationalid))
									.put(Received_Time, format.format(day).toString())
									.map();		
		
		members.setANCVisit2(ANCVisit2);
		allMembers.update(members);
		
		membersScheduleService.fullfillMilestone(submission.entityId(), submission.anmId(), SCHEDULE_ANC, new LocalDate());
		//actionService.markAllAlertsAsInactive(submission.entityId());
		try {
			long timestamp = actionService.getActionTimestamp(submission.anmId(), submission.entityId(), SCHEDULE_ANC);
			membersScheduleService.fullfillSchedule(submission.entityId(), SCHEDULE_ANC, submission.instanceId(), timestamp);
		} catch (Exception e) {
			logger.info("From ancVisitTwo:" + e.getMessage());
		}
	}
	
	public void ANCVisit3(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle ANCVisit3 as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = Calendar.getInstance().getTime();
		Map<String, String> ANCVisit3 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
									.put(START_DATE, submission.getField(START_DATE))
									.put(END_DATE, submission.getField(END_DATE))									
									.put(version, submission.getField(version))
									.put(changes, submission.getField(changes))
									.put(existing_GoB_HHID, submission.getField(existing_GoB_HHID))
									.put(existing_ELCO_NID, submission.getField(existing_ELCO_NID))
									.put(existing_ELCO_BRID, submission.getField(existing_ELCO_BRID))
									.put(existing_Mem_F_Name, submission.getField(existing_Mem_F_Name))
									.put(existing_Mem_Marital_Status, submission.getField(existing_Mem_Marital_Status))
									.put(existing_Marriage_Life, submission.getField(existing_Marriage_Life))
									.put(existing_Spouse_Name, submission.getField(existing_Spouse_Name))
									.put(existing_Gestational_Age, submission.getField(existing_Gestational_Age))
									.put(existing_EDD, submission.getField(existing_EDD))
									.put(existing_ELCO, submission.getField(existing_ELCO))
									.put(existing_HoH_F_Name, submission.getField(existing_HoH_F_Name))
									.put(existing_Couple_No, submission.getField(existing_Couple_No))
									.put(existing_Final_Vill, submission.getField(existing_Final_Vill))
									.put(existing_TT_Count, submission.getField(existing_TT_Count))
									.put(existing_ELCO_Mobile_Number, submission.getField(existing_ELCO_Mobile_Number))
									.put(existing_Mauzapara, submission.getField(existing_Mauzapara))
									.put(existing_Total_Child_Alive, submission.getField(existing_Total_Child_Alive))
									.put(existing_Age_Youngest_Child, submission.getField(existing_Age_Youngest_Child))
									.put(existing_Gravida, submission.getField(existing_Gravida))
									.put(existing_Bleeding, submission.getField(existing_Bleeding))
									.put(existing_Caesarean, submission.getField(existing_Caesarean))
									.put(existing_Heavy_Blood_Flow, submission.getField(existing_Heavy_Blood_Flow))
									.put(existing_Prolong_Delivery, submission.getField(existing_Prolong_Delivery))
									.put(existing_Birth_Outcome, submission.getField(existing_Birth_Outcome))
									.put(existing_Dead_Child, submission.getField(existing_Dead_Child))
									.put(existing_Risky_Preg, submission.getField(existing_Risky_Preg))
									.put(existing_Height, submission.getField(existing_Height))
									.put(existing_LMP, submission.getField(existing_LMP))
									.put(today, submission.getField(today))
									.put(start, submission.getField(start))
									.put(end , submission.getField(end ))
									.put(Visit_Status, submission.getField(Visit_Status))
									.put(Met, submission.getField(Met))
									.put(Confirm_Info, submission.getField(Confirm_Info))
									.put(Visit_Date, submission.getField(Visit_Date))
									.put(ANC3_Due_Date, submission.getField(ANC3_Due_Date))
									.put(Is_On_Time, submission.getField(Is_On_Time))
									.put(ANC3_Post_Due_Date, submission.getField(ANC3_Post_Due_Date))
									.put(Is_Post_Due, submission.getField(Is_Post_Due))
									.put(ANC3_Expired_Date, submission.getField(ANC3_Expired_Date))
									.put(Is_Expired, submission.getField(Is_Expired))
									.put(Preg_Status, submission.getField(Preg_Status))
									.put(Symptoms, submission.getField(Symptoms))
									.put(yn_dk_label, submission.getField(yn_dk_label))
									.put(Menstruation, submission.getField(Menstruation))
									.put(Headache_Blur_Vision, submission.getField(Headache_Blur_Vision))
									.put(High_Fever, submission.getField(High_Fever))
									.put(Prolonged_Delivery, submission.getField(Prolonged_Delivery))
									.put(Position_Child_During_Delivery, submission.getField(Position_Child_During_Delivery))
									.put(Convulsions, submission.getField(Convulsions))
									.put(Is_Critical, submission.getField(Is_Critical))
									.put(Is_Reffered, submission.getField(Is_Reffered))
									.put(IFA_Received, submission.getField(IFA_Received))									
									.put(Misoprostol_Given, submission.getField(Misoprostol_Given))
									.put(Misoprostol_Received, submission.getField(Misoprostol_Received))
									.put(Count_Misorpostol, submission.getField(Count_Misorpostol))									
									.put(Not_Eligible, submission.getField(Not_Eligible))
									.put(ELCO, submission.getField(ELCO))
									.put(anc3_current_formStatus, submission.getField(anc3_current_formStatus))
									.put(relationalid, submission.getField(relationalid))
									.put(Received_Time, format.format(day).toString())
									.map();
		
		members.setANCVisit3(ANCVisit3);
		allMembers.update(members);
		
		membersScheduleService.fullfillMilestone(submission.entityId(), submission.anmId(), SCHEDULE_ANC, new LocalDate());
		//actionService.markAllAlertsAsInactive(submission.entityId());
		try {
			long timestamp = actionService.getActionTimestamp(submission.anmId(), submission.entityId(), SCHEDULE_ANC);
			membersScheduleService.fullfillSchedule(submission.entityId(), SCHEDULE_ANC, submission.instanceId(), timestamp);
		} catch (Exception e) {
			logger.info("From ancVisitTwo:" + e.getMessage());
		}
	}
	
	public void ANCVisit4(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle ANCVisit4 as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = Calendar.getInstance().getTime();
		Map<String, String> ANCVisit4 = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
									.put(START_DATE, submission.getField(START_DATE))
									.put(END_DATE, submission.getField(END_DATE))									
									.put(version, submission.getField(version))
									.put(changes, submission.getField(changes))
									.put(existing_GoB_HHID, submission.getField(existing_GoB_HHID))
									.put(existing_ELCO_NID, submission.getField(existing_ELCO_NID))
									.put(existing_ELCO_BRID, submission.getField(existing_ELCO_BRID))
									.put(existing_Mem_F_Name, submission.getField(existing_Mem_F_Name))
									.put(existing_Mem_Marital_Status, submission.getField(existing_Mem_Marital_Status))
									.put(existing_Marriage_Life, submission.getField(existing_Marriage_Life))
									.put(existing_Spouse_Name, submission.getField(existing_Spouse_Name))
									.put(existing_Gestational_Age, submission.getField(existing_Gestational_Age))
									.put(existing_EDD, submission.getField(existing_EDD))
									.put(existing_ELCO, submission.getField(existing_ELCO))
									.put(existing_HoH_F_Name, submission.getField(existing_HoH_F_Name))
									.put(existing_Couple_No, submission.getField(existing_Couple_No))
									.put(existing_Final_Vill, submission.getField(existing_Final_Vill))
									.put(existing_TT_Count, submission.getField(existing_TT_Count))
									.put(existing_ELCO_Mobile_Number, submission.getField(existing_ELCO_Mobile_Number))
									.put(existing_Mauzapara, submission.getField(existing_Mauzapara))
									.put(existing_Total_Child_Alive, submission.getField(existing_Total_Child_Alive))
									.put(existing_Age_Youngest_Child, submission.getField(existing_Age_Youngest_Child))
									.put(existing_Gravida, submission.getField(existing_Gravida))
									.put(existing_Bleeding, submission.getField(existing_Bleeding))
									.put(existing_Caesarean, submission.getField(existing_Caesarean))
									.put(existing_Heavy_Blood_Flow, submission.getField(existing_Heavy_Blood_Flow))
									.put(existing_Prolong_Delivery, submission.getField(existing_Prolong_Delivery))
									.put(existing_Birth_Outcome, submission.getField(existing_Birth_Outcome))
									.put(existing_Dead_Child, submission.getField(existing_Dead_Child))
									.put(existing_Risky_Preg, submission.getField(existing_Risky_Preg))
									.put(existing_Height, submission.getField(existing_Height))
									.put(existing_LMP, submission.getField(existing_LMP))
									.put(today, submission.getField(today))
									.put(start, submission.getField(start))
									.put(end , submission.getField(end ))
									.put(Visit_Status, submission.getField(Visit_Status))
									.put(Met, submission.getField(Met))
									.put(Confirm_Info, submission.getField(Confirm_Info))
									.put(Visit_Date, submission.getField(Visit_Date))
									.put(ANC4_Due_Date, submission.getField(ANC4_Due_Date))
									.put(Is_On_Time, submission.getField(Is_On_Time))
									.put(ANC4_Post_Due_Date, submission.getField(ANC4_Post_Due_Date))
									.put(Is_Post_Due, submission.getField(Is_Post_Due))
									.put(Preg_Status, submission.getField(Preg_Status))
									.put(Symptoms, submission.getField(Symptoms))
									.put(yn_dk_label, submission.getField(yn_dk_label))
									.put(Menstruation, submission.getField(Menstruation))
									.put(Headache_Blur_Vision, submission.getField(Headache_Blur_Vision))
									.put(High_Fever, submission.getField(High_Fever))
									.put(Prolonged_Delivery, submission.getField(Prolonged_Delivery))
									.put(Position_Child_During_Delivery, submission.getField(Position_Child_During_Delivery))
									.put(Convulsions, submission.getField(Convulsions))
									.put(Is_Critical, submission.getField(Is_Critical))
									.put(Is_Reffered, submission.getField(Is_Reffered))
									.put(IFA_Received, submission.getField(IFA_Received))									
									.put(Misoprostol_Given, submission.getField(Misoprostol_Given))
									.put(Misoprostol_Received, submission.getField(Misoprostol_Received))
									.put(Count_Misorpostol, submission.getField(Count_Misorpostol))									
									.put(Not_Eligible, submission.getField(Not_Eligible))
									.put(ELCO, submission.getField(ELCO))
									.put(anc4_current_formStatus, submission.getField(anc4_current_formStatus))
									.put(relationalid, submission.getField(relationalid))
									.put(Received_Time, format.format(day).toString())
									.map();	
		
		members.setANCVisit4(ANCVisit4);
		allMembers.update(members);
		
		membersScheduleService.fullfillMilestone(submission.entityId(), submission.anmId(), SCHEDULE_ANC, new LocalDate());
		//actionService.markAllAlertsAsInactive(submission.entityId());
		try {
			long timestamp = actionService.getActionTimestamp(submission.anmId(), submission.entityId(), SCHEDULE_ANC);
			membersScheduleService.fullfillSchedule(submission.entityId(), SCHEDULE_ANC, submission.instanceId(), timestamp);
		} catch (Exception e) {
			logger.info("From ancVisitTwo:" + e.getMessage());
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
