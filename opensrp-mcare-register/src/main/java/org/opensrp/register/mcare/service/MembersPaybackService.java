/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.HHRegistrationFields.*;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.*;
import static org.opensrp.common.AllConstants.TT_VisitFields.Received_Time;
import static org.opensrp.common.util.EasyMap.create;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MemberScheduleConstants.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.opensrp.common.util.DateUtil;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;
import org.opensrp.register.mcare.service.scheduling.MembersScheduleService;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.repository.AllActions;
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
	private AllActions allActions;
	
	@Autowired
	public MembersPaybackService(AllMembers allMembers, MembersScheduleService membersScheduleService, 
			ScheduleLogService scheduleLogService, AllActions allActions) {
		this.allMembers = allMembers;
		this.allActions = allActions;
		this.membersScheduleService = membersScheduleService;
	}
	
	public void InjectablesHandler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle Injectables as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = Calendar.getInstance().getTime();
		Map<String, String> Injectable = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
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
								.put(existing_Mem_NID, submission.getField(existing_Mem_NID))
								.put(existing_Mem_BRID, submission.getField(existing_Mem_BRID))
								.put(existing_HoH_F_Name, submission.getField(existing_HoH_F_Name))
								.put(existing_Spouse_Name, submission.getField(existing_Spouse_Name))
								.put(existing_Couple_No, submission.getField(existing_Couple_No))
								.put(existing_TT_Count, submission.getField(existing_TT_Count))
								.put(existing_ELCO_Mobile_Number, submission.getField(existing_ELCO_Mobile_Number))
								.put(existing_Injection_Date, submission.getField(existing_Injection_Date))
								.put(existing_Dose_No, submission.getField(existing_Dose_No))
								.put(injectable_Today, submission.getField(injectable_Today))
								.put(start, submission.getField(start))
								.put(end , submission.getField(end ))
								.put(Visit_Date, submission.getField(Visit_Date))
								.put(Visit_Status, submission.getField(Visit_Status))
								.put(Woman_Met, submission.getField(Woman_Met))
								.put(Confirm_Info, submission.getField(Confirm_Info))
								.put(Previoud_Dose_Inject, submission.getField(Previoud_Dose_Inject))
								.put(Side_Effects, submission.getField(Side_Effects))
								.put(Note_SE1, submission.getField(Note_SE1))
								.put(Note_SE2, submission.getField(Note_SE2))
								.put(Note_SE3, submission.getField(Note_SE3))
								.put(Note_SE4, submission.getField(Note_SE4))
								.put(Injection_Date, submission.getField(Injection_Date))
								.put(Todays_Dose_No, submission.getField(Todays_Dose_No))
								.put(Dose_No, submission.getField(Dose_No))
								.put(Is_Due, submission.getField(Is_Due))
								.put(Is_Post_Due, submission.getField(Is_Post_Due))
								.put(Format_Next_Injection_Date, submission.getField(Format_Next_Injection_Date))
								.put(Next_Injection_Date, submission.getField(Next_Injection_Date))
								.put(relationalid, submission.getField(relationalid))
								.put(injectable_current_formStatus, submission.getField(injectable_current_formStatus))
								.put(Received_Time, format.format(day).toString())
								.map();

        members.setUpdatedTimeStamp(DateUtil.getTimestampToday());
		members.injecTables().add(Injectable);
		allMembers.update(members);
		
		if (submission.getField(Visit_Status) != null && !submission.getField(Visit_Status).equalsIgnoreCase(""))
		if(submission.getField(Visit_Status).equalsIgnoreCase("2") || submission.getField(Visit_Status).equalsIgnoreCase("6")){
			membersScheduleService.enrollIntoSchedule(submission.entityId(), submission.getField(injectable_Today), Injectables);
		}
		
		if (submission.getField(Visit_Status) != null && !submission.getField(Visit_Status).equalsIgnoreCase(""))
		if(submission.getField(Visit_Status).equalsIgnoreCase("8")){
			membersScheduleService.unEnrollFromSchedule(submission.entityId(), submission.anmId(), Injectables);
		}
		
		if (submission.getField(Visit_Status) != null && !submission.getField(Visit_Status).equalsIgnoreCase(""))
			if(submission.getField(Visit_Status).equalsIgnoreCase("10")){
				membersScheduleService.unEnrollFromAllSchedules(submission.entityId());
			}
	}
	
	public void AdolescentHealthHandler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle Adolescent Health as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = Calendar.getInstance().getTime();
		Map<String, String> Adolescent = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
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
									.put(adolescent_today, submission.getField(adolescent_today))
									.put(start, submission.getField(start))
									.put(end , submission.getField(end))
									.put(Visit_Date, submission.getField(Visit_Date))
									.put(Visit_Status, submission.getField(Visit_Status))
									.put(Met, submission.getField(Met))
									.put(Confirm_Info, submission.getField(Confirm_Info))
									.put(Councelling, submission.getField(Councelling))
									.put(Comment, submission.getField(Comment))
									.put(Received_Time, format.format(day).toString())
									.map();

        members.setUpdatedTimeStamp(DateUtil.getTimestampToday());
		members.adolescent().add(Adolescent);
		allMembers.update(members);
		
		if (submission.getField(Visit_Status) != null && (submission.getField(Visit_Status).equalsIgnoreCase("8")))
		{			
			membersScheduleService.unEnrollFromScheduleOfAdolescent(submission.entityId(), submission.anmId(), "");
			try {
				List<Action> beforeNewActions = allActions.findAlertByANMIdEntityIdScheduleName(submission.anmId(), submission.entityId(),
						Adolescent_Health);
				if (beforeNewActions.size() > 0) {
					scheduleLogService.closeSchedule(submission.entityId(), submission.instanceId(), beforeNewActions.get(0).timestamp(),
							Adolescent_Health);
				}

			} catch (Exception e) {
				logger.info("From Adolescent_Health: " + e.getMessage());
			}			
		}
		System.out.println("submission.getField(Visit_Status)::  "+submission.getField(Visit_Status));
		if (submission.getField(Visit_Status) != null && 
				(submission.getField(Visit_Status).equalsIgnoreCase("2") || submission.getField(Visit_Status).equalsIgnoreCase("3")))
		{		
			membersScheduleService.enrollIntoMilestoneOfAdolescent(submission.entityId(), submission.getField(adolescent_today), submission.anmId(),
					submission.instanceId());			
		}
		
		if (submission.getField(Visit_Status) != null && !submission.getField(Visit_Status).equalsIgnoreCase(""))
			if(submission.getField(Visit_Status).equalsIgnoreCase("10")){
				membersScheduleService.unEnrollFromAllSchedules(submission.entityId());
			}
	}
	
	public void DeathRegHandler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle DeathReg as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = Calendar.getInstance().getTime();
		Map<String, String> DeathReg = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
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
									.put(existing_Spouse_Name, submission.getField(existing_Spouse_Name))
									.put(existing_Member_Gender, submission.getField(existing_Member_Gender))
									.put(existing_ELCO_NID, submission.getField(existing_ELCO_NID))
									.put(existing_ELCO_BRID, submission.getField(existing_ELCO_BRID))
									.put(existing_Mem_BRID, submission.getField(existing_Mem_BRID))
									.put(existing_Mauzapara, submission.getField(existing_Mauzapara))
									.put(death_today, submission.getField(death_today))
									.put(start, submission.getField(start))
									.put(end , submission.getField(end))
									.put(Visit_Date, submission.getField(Visit_Date))
									.put(Confirm_Info, submission.getField(Confirm_Info))
									.put(Date_Death, submission.getField(Date_Death))
									.put(Gender_Deceased, submission.getField(Gender_Deceased))
									.put(Deceased_Age_Group, submission.getField(Deceased_Age_Group))
									.put(Deceased_Age, submission.getField(Deceased_Age))
									.put(Reason_Death, submission.getField(Reason_Death))
									.put(Received_Time, format.format(day).toString())
									.map();

        members.setUpdatedTimeStamp(DateUtil.getTimestampToday());
		members.setDeathReg(DeathReg);
		allMembers.update(members);
		
		membersScheduleService.unEnrollFromAllSchedules(submission.entityId());
	}
	
	public void NutritionHandler(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle Nutrition as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = Calendar.getInstance().getTime();
		Map<String, String> Nutritions = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
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
									.put(existing_Injection_Date, submission.getField(existing_Injection_Date))
									.put(existing_Calc_Dob_Confirm, submission.getField(existing_Calc_Dob_Confirm))
									.put(existing_Child_Mother, submission.getField(existing_Child_Mother))
									.put(existing_Child_Father, submission.getField(existing_Child_Father))
									.put(Today, submission.getField(Today))
									.put(start, submission.getField(start))
									.put(end , submission.getField(end))
									.put(Visit_Date, submission.getField(Visit_Date))
									.put(Visit_Status, submission.getField(Visit_Status))
									.put(Calc_Dob_Confirm, submission.getField(Calc_Dob_Confirm))
									.put(Met, submission.getField(Met))
									.put(Confirm_Info, submission.getField(Confirm_Info))
									.put(Mother_Nutrition, submission.getField(Mother_Nutrition))
									.put(Distrinuted_Nutrition, submission.getField(Distrinuted_Nutrition))
									.put(IFA_Tablets, submission.getField(IFA_Tablets))
									.put(VitA_Minarals, submission.getField(VitA_Minarals))
									.put(Child_Nutrition, submission.getField(Child_Nutrition))
									.put(Supplementary_Food, submission.getField(Supplementary_Food))
									.put(Refer, submission.getField(Refer))
									.put(relationalid, submission.getField(relationalid))
									.put(Received_Time, format.format(day).toString())
									.map();

        members.setUpdatedTimeStamp(DateUtil.getTimestampToday());
		members.nutrition().add(Nutritions);
		allMembers.update(members);
		
		membersScheduleService.unEnrollFromSchedule(submission.entityId(), submission.anmId(), Nutrition);
		
		if (submission.getField(Visit_Status) != null && !submission.getField(Visit_Status).equalsIgnoreCase(""))
			if(submission.getField(Visit_Status).equalsIgnoreCase("10") || submission.getField(Visit_Status).equalsIgnoreCase("11")){
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
