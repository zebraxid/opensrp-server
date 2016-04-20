/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.CommonFormFields.ID;
import static org.opensrp.common.AllConstants.TT_VisitFields.*;
import static org.opensrp.common.AllConstants.HHRegistrationFields.*;

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
			
			//addDetailsToMembers(submission, subFormData, members);
			
			if(membersFields.containsKey(Reg_No)){
				allMembers.update(members);
				logger.info("members updated");
			}else{
				allMembers.remove(members);
				logger.info("members removed");
			}
			
			if(membersFields.containsKey(Is_TT)){
				if(!membersFields.get(Is_TT).equalsIgnoreCase("") || membersFields.get(Is_TT) != null){	
					if(membersFields.get(Is_TT).equalsIgnoreCase("1"))
					//membersScheduleService.enrollWomanTTVisit(members.caseId(),submission.anmId(),submission.instanceId(),membersFields)
						;
				}
			}
			
			if(membersFields.containsKey(Is_Measles)){
				if(!membersFields.get(Is_Measles).equalsIgnoreCase("") || membersFields.get(Is_Measles) != null){	
					if(membersFields.get(Is_Measles).equalsIgnoreCase("1"))
					//membersScheduleService.enrollWomanMeaslesVisit(members.caseId(),submission.anmId(), submission.instanceId(),LocalDate.parse(submission.getField(Date_of_Measles)))
						;
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

			houseHold.withPROVIDERID(submission.anmId());
			houseHold.withINSTANCEID(submission.instanceId());
			
			allHouseHolds.update(houseHold);

			hhSchedulesService.enrollIntoMilestoneOfCensus(submission.entityId(),
					submission.getField(REFERENCE_DATE),submission.anmId(),submission.instanceId());
			}	 
	}
	
	private void addDetailsToMembers(FormSubmission submission,
			SubFormData subFormData, Members members) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		members.details().put(relationalid, subFormData.instances().get(0).get(relationalid));
		members.details().put(REFERENCE_DATE, submission.getField(REFERENCE_DATE));
		members.details().put(START_DATE, submission.getField(START_DATE));		
		members.details().put(END_DATE, submission.getField(END_DATE));
		members.details().put(Received_Time,format.format(today).toString());
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
					.put(Received_Time, dateTime.format(today).toString()).map();
			
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
	
	public void familyPlanning(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle familyPlanning as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> familyPlanning = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(FP_DATE_OF_REG, submission.getField(FP_DATE_OF_REG))
											.put(FP_USER, submission.getField(FP_USER))
											.put(FP_Methods, submission.getField(FP_Methods))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withfamilyPlanning(familyPlanning);
		allMembers.update(members);
	}
	
	public void newBorn(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle familyPlanning as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> newBorn = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(new_born_DATE_OF_REG, submission.getField(new_born_DATE_OF_REG))
											.put(Birth_Weigtht, submission.getField(Birth_Weigtht))
											.put(Newborn_Care_Received, submission.getField(Newborn_Care_Received))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withnewBorn(newBorn);
		allMembers.update(members);
	}
	
	public void Measles_Visit(FormSubmission submission) {
		Members members = allMembers.findByCaseId(submission.entityId());
		if (members == null) {
			logger.warn(format(
					"Failed to handle Measles_Visit as there is no Member enrolled with ID: {0}",
					submission.entityId()));
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> measlesVisit = create(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
											.put(START_DATE, submission.getField(START_DATE))
											.put(END_DATE, submission.getField(END_DATE))
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withMeaslesVisit(measlesVisit);
		allMembers.update(members);
		membersScheduleService.enrollTTVisit(members.caseId(),submission.anmId(),submission.instanceId(),members.TODAY());
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
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withTTVisitOne(TT1_visit);
		allMembers.update(members);
		membersScheduleService.enrollTT1_Visit(members.caseId(),submission.anmId(),submission.instanceId(),members.TODAY());
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
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withTTVisitTwo(TT2_visit);
		allMembers.update(members);
		membersScheduleService.enrollTT2_Visit(members.caseId(),submission.anmId(),submission.instanceId(),members.TODAY());
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
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withTTVisitOne(TT3_visit);
		allMembers.update(members);
		membersScheduleService.enrollTT3_Visit(members.caseId(),submission.anmId(),submission.instanceId(),members.TODAY());
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
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withTTVisitOne(TT4_visit);
		allMembers.update(members);
		membersScheduleService.enrollTT4_Visit(members.caseId(),submission.anmId(),submission.instanceId(),members.TODAY());
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
											.put(Received_Time, format.format(today).toString())
											.map();	
		
		members.withTTVisitFive(TT5_visit);
		allMembers.update(members);
		membersScheduleService.enrollTT5_Visit(members.caseId(),submission.anmId());
	}

	public void PCV1Handler(FormSubmission submission) {
		// TODO Auto-generated method stub
		
	}

	public void PCV2Handler(FormSubmission submission) {
		// TODO Auto-generated method stub
		
	}

	public void PENTA3Handler(FormSubmission submission) {
		// TODO Auto-generated method stub
		
	}

	public void PENTA2Handler(FormSubmission submission) {
		// TODO Auto-generated method stub
		
	}

	public void PENTA1Handler(FormSubmission submission) {
		// TODO Auto-generated method stub
		
	}

	public void PCV3Handler(FormSubmission submission) {
		// TODO Auto-generated method stub
		
	}

	public void OPV3Handler(FormSubmission submission) {
		// TODO Auto-generated method stub
		
	}

	public void OPV2Handler(FormSubmission submission) {
		// TODO Auto-generated method stub
		
	}

	public void OPV1Handler(FormSubmission submission) {
		// TODO Auto-generated method stub
		
	}

	public void OPV0Handler(FormSubmission submission) {
		// TODO Auto-generated method stub
		
	}

	public void MRHandler(FormSubmission submission) {
		// TODO Auto-generated method stub
		
	}

	public void Measles2Handler(FormSubmission submission) {
		// TODO Auto-generated method stub
		
	}

	public void IPVHandler(FormSubmission submission) {
		// TODO Auto-generated method stub
		
	}

	public void BCGHandler(FormSubmission submission) {
		// TODO Auto-generated method stub
		
	}

}

