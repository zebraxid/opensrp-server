/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service.scheduling;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.service.MembersService;
import org.opensrp.register.mcare.service.VaccinationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildVaccineSchedule {
	
	private static Logger logger = LoggerFactory.getLogger(MembersService.class.toString());
	private MembersScheduleService membersScheduleService;
	private VaccinationService vaccinationService;
	
	@Autowired
	public ChildVaccineSchedule(MembersScheduleService membersScheduleService) {
		this.membersScheduleService = membersScheduleService;		
	}
	
	
	@Autowired
    public void setVaccinationService(VaccinationService vaccinationService) {
    	this.vaccinationService = vaccinationService;
    }


	public void immediateChildVaccine(FormSubmission submission, Members members, Map<String, String> membersFields, String scheduleName, String immediateScheduleName, String refDate, String age, String age_days, String cond, int agenum, int days) {
			if ( membersFields.containsKey(refDate) && membersFields.containsKey(age_days)){
				if ((membersFields.get(cond) == null || membersFields.get(cond).equalsIgnoreCase("")) && (membersFields.get(age_days) != null && !membersFields.get(age_days).equalsIgnoreCase(""))){			
					if((Integer.parseInt(membersFields.get(age_days))<=days) && (membersFields.get(refDate) != null && !membersFields.get(refDate).equalsIgnoreCase("")) ){								
						if(isValidDate(membersFields.get(refDate))){
							membersScheduleService.enrollimmediateMembersVisit(
													members.caseId(),submission.anmId(),membersFields.get(refDate),submission.instanceId(),scheduleName,immediateScheduleName);
							
						}	
					}else{
						logger.info("Child age greater than  365 dyas:"+days);
					}
					
				}
			}
		
			
	}
	
	public void AfterimmediateChildVisit(FormSubmission submission, Members members, String scheduleName, String immediateScheduleName, String refDate, String age, String age_days, String cond, int agenum, int days){
		
		if (submission.getField(cond) == null ||submission.getField(cond).equalsIgnoreCase("") && (submission.getField(age_days) != null && !submission.getField(age_days).equalsIgnoreCase("")) ){
			if((Integer.parseInt(submission.getField(age_days))<=days) && ((submission.getField(refDate) != null && !submission.getField(refDate).equalsIgnoreCase(""))) ){
				if(isValidDate(submission.getField(refDate))){					
					membersScheduleService.enrollAfterimmediateVisit(
							members.caseId(),submission.anmId(),submission.getField(refDate),submission.instanceId(),scheduleName,immediateScheduleName);							
				}else{
					logger.info("reference date is not valid.."+submission.getField(refDate));
				}
			}else{
				logger.info("Age is out of range"+days);
			}
		}
		
		if (submission.getField(cond) != null && !submission.getField(cond).equalsIgnoreCase("")){
			membersScheduleService.unEnrollFromImmediateSchedule(
					members.caseId(),submission.anmId(),scheduleName,immediateScheduleName);	
			vaccinationService.updateVaccineStatus(members.caseId(), scheduleName);
		}
	}
	
	public void ChildVaccine(FormSubmission submission, Members members, Map<String, String> membersFields, String scheduleName, String refDate, String age, String age_days, String cond, int agenum, int days) {
		
		if ((membersFields.containsKey(refDate)) && membersFields.containsKey(cond) && (membersFields.get(cond) == null || membersFields.get(cond).equalsIgnoreCase("")) && (membersFields.containsKey(age_days)) )
			if((membersFields.get(age_days) != null && !membersFields.get(age_days).equalsIgnoreCase("")) && (membersFields.get(refDate) != null && !membersFields.get(refDate).equalsIgnoreCase("")) )
			if((Integer.parseInt(membersFields.get(age_days))<=days) && (isValidDate(membersFields.get(refDate)))){		
				membersScheduleService.enrollChildVisit(
					members.caseId(),submission.anmId(),scheduleName,membersFields.get(refDate));							
			}else{
				logger.info("Age is out of range:"+days);
			}
		
		
	}
	
	public void ChildFollowupVaccine(FormSubmission submission, Members members, String scheduleName, String refDate, String age, String age_days, String cond, int agenum, int days) {
		if ((submission.getField(cond) == null || submission.getField(cond).equalsIgnoreCase(""))  && ((submission.getField(age_days) != null && !submission.getField(age_days).equalsIgnoreCase("")) )){
			if((Integer.parseInt(submission.getField(age_days))<=days) && ((submission.getField(refDate) != null && !submission.getField(refDate).equalsIgnoreCase("")) ))
			if(isValidDate(submission.getField(refDate))){				
				membersScheduleService.enrollChildVisit(members.caseId(),submission.anmId(),scheduleName,submission.getField(refDate));
			}				
		}
		
		if (submission.getField(cond) != null && !submission.getField(cond).equalsIgnoreCase("")){
			membersScheduleService.unEnrollFromSchedule(
					members.caseId(),submission.anmId(),scheduleName);
			vaccinationService.updateVaccineStatus(members.caseId(), scheduleName);
		}
		
		
	}
	
	public boolean isValidDate(String dateString) {
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    if(dateString == null || dateString.isEmpty()){
	    	return false;
	    }
	    try {    	
	        df.parse(dateString);
	        return true;
	    } catch (ParseException e) {
	        return false;
	    }
	}

}
