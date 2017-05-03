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
		System.out.println("immediate Age:"+age);
		System.err.println("immediate refDate:"+refDate);
		System.err.println("immediate cond:"+cond);
		System.err.println("immediate age_days:"+age_days);
		
		System.out.println("immediate Age:"+membersFields.get(age));
		System.err.println("immediate refDate:"+membersFields.containsKey(refDate));
		System.err.println("immediate cond:"+membersFields.get(cond));
		System.err.println("immediate age_days:"+membersFields.containsKey(age_days));
		System.err.println("membersFields:"+membersFields.toString());
		/*try{
			System.out.println("First try");
			if (membersFields.containsKey(cond))
			if (membersFields.get(cond) == null || membersFields.get(cond).equalsIgnoreCase("")){
			if(membersFields.containsKey(age))
			if(membersFields.get(age) != null && !membersFields.get(age).equalsIgnoreCase(""))
			if(Integer.parseInt(membersFields.get(age))<agenum){
				if(membersFields.containsKey(refDate))
				if (membersFields.get(refDate) != null && !membersFields.get(refDate).equalsIgnoreCase(""))
				if(isValidDate(membersFields.get(refDate)))
					membersScheduleService.enrollimmediateMembersVisit(
						members.caseId(),submission.anmId(),membersFields.get(refDate),submission.instanceId(),scheduleName,immediateScheduleName);							
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}*/
		
		try{
			System.out.println("Second try");
			if ( membersFields.containsKey(refDate) && membersFields.containsKey(age_days)){
				System.err.println("ok1");
				if ((membersFields.get(cond) == null || membersFields.get(cond).equalsIgnoreCase("")) && (membersFields.get(age_days) != null && !membersFields.get(age_days).equalsIgnoreCase(""))){			
					System.err.println("ok2");
					if((Integer.parseInt(membersFields.get(age_days))<=days) && (membersFields.get(refDate) != null && !membersFields.get(refDate).equalsIgnoreCase("")) ){								
						System.err.println("ok3");
						if(isValidDate(membersFields.get(refDate))){
							System.err.println("ok4");
							membersScheduleService.enrollimmediateMembersVisit(
													members.caseId(),submission.anmId(),membersFields.get(refDate),submission.instanceId(),scheduleName,immediateScheduleName);
						}	
					}
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	public void AfterimmediateChildVisit(FormSubmission submission, Members members, String scheduleName, String immediateScheduleName, String refDate, String age, String age_days, String cond, int agenum, int days){
		System.out.println("Afterimmediate Age:"+age);
		System.err.println("AfterimmediaterefDate:"+refDate);
		System.err.println("Afterimmediatecond:"+cond);
		System.err.println("Afterimmediateage_days:"+age_days);
		/*if (submission.getField(cond) == null || submission.getField(cond).equalsIgnoreCase("")){
		if(submission.getField(age) != null && !submission.getField(age).equalsIgnoreCase(""))
		if(Integer.parseInt(submission.getField(age))<1)
			if (submission.getField(refDate) != null && !submission.getField(refDate).equalsIgnoreCase(""))
			if(isValidDate(submission.getField(refDate))){
				membersScheduleService.enrollAfterimmediateVisit(
						members.caseId(),submission.anmId(),submission.getField(refDate),submission.instanceId(),scheduleName,immediateScheduleName);							
			}
		}*/
		System.out.println("members:"+members.toString());
		if (submission.getField(cond).equalsIgnoreCase("") && (submission.getField(age_days) != null && !submission.getField(age_days).equalsIgnoreCase("")) ){
			System.out.println("AfterimmediateChildVisit1");
			if((Integer.parseInt(submission.getField(age_days))<=agenum) && ((submission.getField(refDate) != null && !submission.getField(refDate).equalsIgnoreCase(""))) ){
				System.out.println("AfterimmediateChildVisit2");
				if(isValidDate(submission.getField(refDate))){
					System.out.println("AfterimmediateChildVisit3");
					membersScheduleService.enrollAfterimmediateVisit(
							members.caseId(),submission.anmId(),submission.getField(refDate),submission.instanceId(),scheduleName,immediateScheduleName);							
				}
			}
		}
		
		if (submission.getField(cond) != null && !submission.getField(cond).equalsIgnoreCase("")){
			membersScheduleService.unEnrollFromImmediateSchedule(
					members.caseId(),submission.anmId(),scheduleName,immediateScheduleName);	
			vaccinationService.updateVaccineStatus(members.caseId(), scheduleName);
		}
	}
	
	public void ChildVaccine(FormSubmission submission, Members members, Map<String, String> membersFields, String scheduleName, String refDate, String age, String age_days, String cond, int agenum, int days) {
		
		/*if (membersFields.containsKey(cond))
		if (membersFields.get(cond) == null || membersFields.get(cond).equalsIgnoreCase("")){
		if(membersFields.containsKey(age))
		if(membersFields.get(age) != null && !membersFields.get(age).equalsIgnoreCase(""))
		if(Integer.parseInt(membersFields.get(age))<agenum){
			if(membersFields.containsKey(refDate))
			if (membersFields.get(refDate) != null && !membersFields.get(refDate).equalsIgnoreCase(""))
			if(isValidDate(membersFields.get(refDate)))
				membersScheduleService.enrollChildVisit(
					members.caseId(),submission.anmId(),scheduleName,membersFields.get(refDate));							
			}
		}*/
		System.out.println("ChildVaccine Age:"+age);
		System.err.println("ChildVaccine refDate:"+refDate);
		System.err.println("ChildVaccine cond :"+cond);
		System.err.println("ChildVaccine age_days:"+age_days);
		if ((membersFields.containsKey(refDate)) && membersFields.containsKey(cond) && (membersFields.get(cond) == null || membersFields.get(cond).equalsIgnoreCase("")) && (membersFields.containsKey(age_days)) )
			System.out.println("ChildVaccine OK1");		
			if((membersFields.get(age_days) != null && !membersFields.get(age_days).equalsIgnoreCase("")) && (membersFields.get(refDate) != null && !membersFields.get(refDate).equalsIgnoreCase("")) )
				System.out.println("ChildVaccine OK2");
			if((Integer.parseInt(membersFields.get(age_days))<=days) && (isValidDate(membersFields.get(refDate)))){		
				System.out.println("ChildVaccine OK3");
				membersScheduleService.enrollChildVisit(
					members.caseId(),submission.anmId(),scheduleName,membersFields.get(refDate));							
			}
		
		
	}
	
	public void ChildFollowupVaccine(FormSubmission submission, Members members, String scheduleName, String refDate, String age, String age_days, String cond, int agenum, int days) {
	
		/*if (submission.getField(cond) == null || submission.getField(cond).equalsIgnoreCase("")){
		if(submission.getField(age) != null && !submission.getField(age).equalsIgnoreCase(""))
		if(Integer.parseInt(submission.getField(age))<5)
			if (submission.getField(refDate) != null && !submission.getField(refDate).equalsIgnoreCase(""))
			if(isValidDate(submission.getField(refDate))){
				membersScheduleService.enrollChildVisit(members.caseId(),submission.anmId(),scheduleName,submission.getField(refDate));
			}				
		}*/
		System.out.println("ChildFollowupVaccine Age:"+age);
		System.err.println("ChildFollowupVaccine refDate:"+refDate);
		System.err.println("ChildFollowupVaccine cond :"+cond);
		System.err.println("ChildFollowupVaccine age_days:"+age_days);
		System.out.println("submission.getField(age_days):"+submission.getField(age_days));
		System.out.println("submission.getField(cond):"+submission.getField(cond));
		if ((submission.getField(cond) == null || submission.getField(cond).equalsIgnoreCase(""))  && ((submission.getField(age_days) != null && !submission.getField(age_days).equalsIgnoreCase("")) )){
			System.out.println("ChildFollowupVaccine OK1");
			if((Integer.parseInt(submission.getField(age_days))<=days) && ((submission.getField(refDate) != null && !submission.getField(refDate).equalsIgnoreCase("")) ))
				System.out.println("ChildFollowupVaccine OK2");
			if(isValidDate(submission.getField(refDate))){
				System.out.println("ChildFollowupVaccine OK3");
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
