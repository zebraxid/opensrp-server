package org.opensrp.register.thrivepk;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.opensrp.common.util.DateUtil;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.thrivepk.AllSmsHistory;
import org.opensrp.register.thrivepk.VaccineRepo.Vaccine;
import org.opensrp.service.ErrorTraceService;
import org.opensrp.service.formSubmission.handler.CustomFormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormSubmissionHandler implements CustomFormSubmissionHandler{
	private AllSmsHistory allSH;
	private ErrorTraceService errorTrace;

	@Autowired
	public FormSubmissionHandler(AllSmsHistory allSH, ErrorTraceService errorTrace) {
		this.allSH = allSH;
		this.errorTrace = errorTrace;
	}
	
	private static String nonEmptyValue(FormSubmission fs, boolean asc, String... fields) {
		List<String> l = Arrays.asList(fields);
		if (!asc) {
			Collections.reverse(l);
		}
		for (String f : l) {
			String v = fs.getField(f);
			if (StringUtils.isNotBlank(v)) {
				return v;
			}
		}
		return "";
	}
	
	private DateTime getDate(FormSubmission fs, String...fields) {
		String f = nonEmptyValue(fs, true, fields);
		if(StringUtils.isNotBlank(f)){
			try {
				return DateUtil.parseDate(f);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void handle(FormSubmission fs) 
	{
		try{
			DateTime birthdate = getDate(fs, "dob", "birthdate", "birth_date", "existing_birthdate", "existing_birth_date");
			DateTime visitDate = getDate(fs, "client_reg_date", "today");
			Boolean reminderApproved = fs.getField("reminders_approval") != null && fs.getField("reminders_approval").matches("yes|Yes|YES|true|TRUE|True");
			String contactNumber = fs.getField("contact_phone_number");
			long serverVersion = fs.serverVersion();
			String name = fs.getField("first_name");
			if(name == null){
				name = fs.getField("existing_first_name");
			}
			if(name == null){
				name = "Baby";
			}
			// check p1 and m1
			if(fs.formName().toLowerCase().contains("enrollment"))
			{
				if(birthdate == null){
					saveWithMissingDoB(Vaccine.penta1, fs.entityId(), serverVersion);
					saveWithMissingDoB(Vaccine.measles1, fs.entityId(), serverVersion);
				}
				else if(visitDate == null){
					saveWithMissingVisitDate(Vaccine.penta1, fs.entityId(), serverVersion);
					saveWithMissingVisitDate(Vaccine.measles1, fs.entityId(), serverVersion);
				}
				else {
					// p1
					if(StringUtils.isNotBlank(nonEmptyValue(fs, true, "penta1", "penta1_retro"))){
						saveWithAlreadyReceived(Vaccine.penta1, fs.entityId(), visitDate, birthdate, serverVersion);
					}
					else if(StringUtils.isNotBlank(nonEmptyValue(fs, true, "penta2", "penta2_retro", "penta3", "penta3_retro"))){
						saveWithNextVaccineReceived(Vaccine.penta1, fs.entityId(), visitDate, birthdate, serverVersion);
					}
					else if(birthdate.plusDays(Vaccine.penta1.milestoneGapDays()-20).isBefore(visitDate)){
						saveWithLateVisit(Vaccine.penta1, fs.entityId(), visitDate, birthdate, birthdate.plusDays(Vaccine.penta1.milestoneGapDays()), serverVersion);
					}
					else {
						saveWithDue(name, Vaccine.penta1, fs.entityId(), reminderApproved, contactNumber, visitDate, birthdate, birthdate.plusDays(Vaccine.penta1.milestoneGapDays()), serverVersion);
					}
					
					// m1
					if(StringUtils.isNotBlank(nonEmptyValue(fs, true, "measles1", "measles1_retro"))){
						saveWithAlreadyReceived(Vaccine.measles1, fs.entityId(), visitDate, birthdate, serverVersion);
					}
					else if(StringUtils.isNotBlank(nonEmptyValue(fs, true, "measles2", "measles2_retro"))){
						saveWithNextVaccineReceived(Vaccine.measles1, fs.entityId(), visitDate, birthdate, serverVersion);
					}
					else if(birthdate.plusDays(Vaccine.measles1.milestoneGapDays()-20).isBefore(visitDate)){
						saveWithLateVisit(Vaccine.measles1, fs.entityId(), visitDate, birthdate, birthdate.plusDays(Vaccine.measles1.milestoneGapDays()), serverVersion);
					}
					else {
						saveWithDue(name, Vaccine.measles1, fs.entityId(), reminderApproved, contactNumber, visitDate, birthdate, birthdate.plusDays(Vaccine.measles1.milestoneGapDays()), serverVersion);
					}
				}
			}
	
			DateTime p1date = getDate(fs, "penta1", "penta1_retro");
			DateTime p2date = getDate(fs, "penta2", "penta2_retro");
			DateTime p3date = getDate(fs, "penta3", "penta3_retro");
			
			// p2
			if(allSH.byEntityIdCause(fs.entityId(), "penta2").size() == 0){
				if(p2date != null){
					saveWithAlreadyReceived(Vaccine.penta2, fs.entityId(), visitDate, birthdate, serverVersion);
				}
				else if(p3date != null){
					saveWithNextVaccineReceived(Vaccine.penta2, fs.entityId(), visitDate, birthdate, serverVersion);
				}
				else if(visitDate != null && p1date != null){
					if(p1date.plusDays(20).isBefore(visitDate)){
						saveWithLateVisit(Vaccine.penta2, fs.entityId(), visitDate, birthdate, p1date.plusDays(Vaccine.penta2.prerequisiteGapDays()), serverVersion);
					}
					else {
						saveWithDue(name, Vaccine.penta2, fs.entityId(), reminderApproved, contactNumber, visitDate, birthdate, p1date.plusDays(Vaccine.penta2.prerequisiteGapDays()), serverVersion);
					}
				}
			}
			
			// p3
			if(allSH.byEntityIdCause(fs.entityId(), "penta3").size() == 0){
				if(p3date != null){
					saveWithAlreadyReceived(Vaccine.penta3, fs.entityId(), visitDate, birthdate, serverVersion);
				}
				else if(visitDate != null && p2date != null){
					if(p2date.plusDays(20).isBefore(visitDate)){
						saveWithLateVisit(Vaccine.penta3, fs.entityId(), visitDate, birthdate, p2date.plusDays(Vaccine.penta3.prerequisiteGapDays()), serverVersion);
					}
					else {
						saveWithDue(name, Vaccine.penta3, fs.entityId(), reminderApproved, contactNumber, visitDate, birthdate, p2date.plusDays(Vaccine.penta3.prerequisiteGapDays()), serverVersion);
					}
				}
			}
			
			DateTime m1date = getDate(fs, "measles1", "measles1_retro");
			DateTime m2date = getDate(fs, "measles2", "measles2_retro");
	
			// m2
			if(allSH.byEntityIdCause(fs.entityId(), "measles2").size() == 0){
				if(m2date != null){
					saveWithAlreadyReceived(Vaccine.measles2, fs.entityId(), visitDate, birthdate, serverVersion);
				}
				else if(birthdate != null && visitDate != null && m1date != null){
					// m1 is given at 13.5 months or onwards
					if(birthdate.plusDays((int) (13.5*365)).isBefore(m1date)){
						if(m1date.plusDays(20).isBefore(visitDate)){
							saveWithLateVisit(Vaccine.measles2, fs.entityId(), visitDate, birthdate, m1date.plusDays(28), serverVersion);
						}
						else {
							saveWithDue(name, Vaccine.measles2, fs.entityId(), reminderApproved, contactNumber, visitDate, birthdate, m1date.plusDays(28), serverVersion);
						}
					}
					else {
						saveWithDue(name, Vaccine.measles2, fs.entityId(), reminderApproved, contactNumber, visitDate, birthdate, birthdate.plusDays(Vaccine.measles2.milestoneGapDays()), serverVersion);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			errorTrace.log("SMS_HISTORY_HANDLER_ERROR", SmsHistory.class.getName(), fs.instanceId(), ExceptionUtils.getStackTrace(e), null);
		}
	}
	
	private void saveWithMissingDoB(Vaccine v, String entityId, long serverVersion) {
		if(allSH.byEntityIdCause(entityId, v.name()).size() == 0){
			allSH.add(addSmsHistory(entityId, "CANCELLED", v.name(), "MISSING_DOB", null, null, null, null, serverVersion));
		}
	}
	
	private void saveWithLateVisit(Vaccine v, String entityId, DateTime visitDate, DateTime birthdate, DateTime duedate, long serverVersion) {
		allSH.add(addSmsHistory(entityId, "CANCELLED", v.name(), "LATE_VISIT", visitDate, birthdate, duedate, null, serverVersion));
	}
	
	private void saveWithNoContactAvailable(Vaccine v, String entityId, DateTime visitDate, DateTime birthdate, DateTime duedate, long serverVersion) {
		allSH.add(addSmsHistory(entityId, "CANCELLED", v.name(), "NO_CONTACT_NUMBER", visitDate, birthdate, duedate, null, serverVersion));
	}
	
	private void saveWithNotApproved(Vaccine v, String entityId, DateTime visitDate, DateTime birthdate, DateTime duedate, long serverVersion) {
		allSH.add(addSmsHistory(entityId, "CANCELLED", v.name(), "NO_REMINDER_APPROVAL", visitDate, birthdate, duedate, null, serverVersion));
	}
	
	private void saveWithAlreadyReceived(Vaccine v, String entityId, DateTime visitDate, DateTime birthdate, long serverVersion) {
		allSH.add(addSmsHistory(entityId, "CANCELLED", v.name(), "ALREADY_RECEIVED", visitDate, birthdate, null, null, serverVersion));
	}
	
	private void saveWithNextVaccineReceived(Vaccine v, String entityId, DateTime visitDate, DateTime birthdate, long serverVersion) {
		allSH.add(addSmsHistory(entityId, "CANCELLED", v.name(), "NEXT_VACCINE_RECEIVED", visitDate, birthdate, null, null, serverVersion));
	}
	
	private void saveWithMissingVisitDate(Vaccine v, String entityId, long serverVersion) {
		if(allSH.byEntityIdCause(entityId, v.name()).size() == 0){
			allSH.add(addSmsHistory(entityId, "CANCELLED", v.name(), "MISSING_VISIT_DATE", null, null, null, null, serverVersion));
		}
	}
	
	private void saveWithDue(String name, Vaccine v, String entityId, Boolean reminderApproved, String contactNumber, 
			DateTime visitDate, DateTime birthdate, DateTime duedate, long serverVersion) {
		if(reminderApproved == null || reminderApproved == false){
			saveWithNotApproved(v, entityId, visitDate, birthdate, duedate, serverVersion);
		}
		else if(StringUtils.isBlank(contactNumber)){
			saveWithNoContactAvailable(v, entityId, visitDate, birthdate, duedate, serverVersion);
		}
		else {
			String text = name+" ko waqt pay hifazati teeka lagwa kar bimarion say mehfooz rakhain. Unhain hifazati tikon kay markaz laen";
			allSH.add(addSmsHistory(entityId, "PENDING", v.name(), null, visitDate, birthdate, duedate, contactNumber, serverVersion, text));
		}
	}
	
	private SmsHistory addSmsHistory(String entityId, String status, String cause, String error, 
			DateTime visitDate, DateTime birthdate, DateTime duedate, String contactNumber, long serverVersion) {
		return addSmsHistory(entityId, status, cause, error, visitDate, birthdate, duedate, contactNumber, serverVersion, null);
	}
	
	private SmsHistory addSmsHistory(String entityId, String status, String cause, String error, 
			DateTime visitDate, DateTime birthdate, DateTime duedate, String contactNumber, long serverVersion, String text) {
		SmsHistory sh = new SmsHistory();
		sh.setBaseEntityId(entityId);
		sh.setCause(cause);
		sh.setErrorDetails(error);
		if(status.equalsIgnoreCase("pending") && duedate != null && duedate.minusDays(3).isBefore(new DateTime(serverVersion))){
			status = "LATE_SYNC";
		}
		sh.setStatus(status);
		sh.setDuedate(duedate);
		
		Map<String, Object> details = new HashMap<>();
		details.put("birthdate", birthdate);
		details.put("visitDate", visitDate);
		details.put("recipient", contactNumber);
		details.put("syncDate", new DateTime(serverVersion));
		details.put("text", text);
		sh.setDetails(details);
		return sh;
	}

}
