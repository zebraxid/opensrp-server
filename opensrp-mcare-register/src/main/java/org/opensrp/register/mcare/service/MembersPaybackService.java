/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.HHRegistrationFields.*;
import static org.opensrp.common.AllConstants.TT_VisitFields.Received_Time;
import static org.opensrp.common.util.EasyMap.create;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MemberScheduleConstants.*;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembersPaybackService {
	private static Logger logger = LoggerFactory.getLogger(MembersService.class
			.toString());

	private AllMembers allMembers;
	private MembersScheduleService membersScheduleService;
	private ScheduleLogService scheduleLogService;
	@Autowired
	public MembersPaybackService(AllMembers allMembers, MembersScheduleService membersScheduleService, 
			ScheduleLogService scheduleLogService) {
		this.allMembers = allMembers;
		this.membersScheduleService = membersScheduleService;
	}
	
	public void InjectablesHandler(FormSubmission submission) {
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
									.put(name, submission.getField(name))
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
	
	public void AdolescentHealthHandler(FormSubmission submission) {
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
									.put(name, submission.getField(name))
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
	
	public void DeathRegHandler(FormSubmission submission) {
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
									.put(name, submission.getField(name))
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
	
	public void NutritionHandler(FormSubmission submission) {
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
									.put(name, submission.getField(name))
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
