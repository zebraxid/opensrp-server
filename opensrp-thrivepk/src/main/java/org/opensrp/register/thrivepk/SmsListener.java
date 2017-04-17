package org.opensrp.register.thrivepk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.motechproject.scheduler.domain.MotechEvent;
import org.motechproject.server.event.annotations.MotechListener;
import org.opensrp.common.util.HttpResponse;
import org.opensrp.common.util.HttpUtil;
import org.opensrp.domain.AppStateToken;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.thrivepk.TelenorContext.Config;
import org.opensrp.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsListener {
	public enum SmsConstants{
		SMS_LAST_FORM_SUBMISSION_TOKEN,
	}

	private ConfigService config;
	private AllFormSubmissions allFormSubmission;
	private FormSubmissionHandler formSubmissionHandler;
	private AllSmsHistory allSmsHistory;
	
	@Autowired
	public SmsListener(ConfigService config, AllFormSubmissions allFormSubmission, 
			FormSubmissionHandler formSubmissionHandler, AllSmsHistory allSmsHistory) {
		this.config = config;
		this.allFormSubmission = allFormSubmission;
		this.formSubmissionHandler = formSubmissionHandler;
		this.allSmsHistory = allSmsHistory;
		
		try{
			config.registerAppStateToken(SmsConstants.SMS_LAST_FORM_SUBMISSION_TOKEN, "0", "SMS LAST SYNC TOKEN", true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@MotechListener(subjects = "SMS_PROCESSOR_SERVICE")
	public void runSmsProcessor(MotechEvent event) {
		System.out.println("Running SMS_PROCESSOR_SERVICE");
		
		AppStateToken lastToken = config.getAppStateTokenByName(SmsConstants.SMS_LAST_FORM_SUBMISSION_TOKEN);
		
		List<FormSubmission> formSubmissions = allFormSubmission.findByFormName("child_enrollment", lastToken.longValue());
		System.out.println("Enrollments: "+formSubmissions.size());
		formSubmissions.addAll(allFormSubmission.findByFormName("child_followup", lastToken.longValue()));
		System.out.println("Adding Followups: "+formSubmissions.size());
		
		try{
			for (FormSubmission formSubmission : formSubmissions) {
				System.out.println("Processing form submission "+formSubmission.entityId());
				formSubmissionHandler.handle(formSubmission);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		config.updateAppStateToken(SmsConstants.SMS_LAST_FORM_SUBMISSION_TOKEN, DateTime.now().getMillis());

		System.out.println("Ran successfully SMS_PROCESSOR_SERVICE for FS "+formSubmissions.size());
	}
	
	@MotechListener(subjects = "SMS_PUSH_SERVICE")
	public void runSmsPush(MotechEvent event) {	
		System.out.println("Running SMS_PUSH_SERVICE");
		
		List<SmsHistory> smsl = allSmsHistory.byStatus("PENDING", DateTime.now().minusMonths(4), DateTime.now());
		System.out.println("Fetched sms "+smsl.size());
		
		for (SmsHistory smsHistory : smsl) {
			try{
			String msisdn = TelenorContext.getProperty(Config.MSISDN, null);
			String password = TelenorContext.getProperty(Config.PASSWORD, null);
			HttpResponse sresponse = HttpUtil.post(Config.fullUrl(Config.AUTH_URL) , "msisdn="+msisdn+"&password="+password+"&mask=IRD-EPI", "");
			Map<String, Object> resp = new HashMap<>();
			//System.out.println(response.body());
			Utils.createResponse(sresponse, resp);
			
			String session_id = null;
			if(resp.containsKey("SUCCESS") && (boolean) resp.get("SUCCESS")){
				session_id  = resp.get("data").toString();
			}
			
			String primaryContact = smsHistory.getDetails().get("recipient").toString();
			if(primaryContact .startsWith("+92")){
				primaryContact = primaryContact.substring(1);
			} 
			else if(primaryContact.startsWith("92")){
				// do nothing. its perfect number
			}
			else if(primaryContact.startsWith("03")){
				primaryContact = "92"+primaryContact.substring(1);
			}
			else if(primaryContact.startsWith("3")){
				primaryContact = "92"+primaryContact;
			}
					
			String text = smsHistory.getDetails().get("text").toString();
			String mask = "IRD-EPI";
			HttpResponse response = HttpUtil.post(Config.fullUrl(Config.OUTBOUND_DISPATCH_URL) , 
					outboundPayload(session_id, primaryContact, text, mask, null), "");
			System.out.println(response.body());
			Utils.createResponse(response, resp);
			
			if(resp.containsKey("SUCCESS") && (boolean) resp.get("SUCCESS")){
				Long messageId = new Double(resp.get("data").toString()).longValue();
				
				smsHistory.setStatus("SENT");
				smsHistory.setSentDate(DateTime.now());
				smsHistory.getDetails().put("reference", messageId);
				smsHistory.setErrorDetails(resp.toString());
				
				allSmsHistory.update(smsHistory);
			}
			else {
				smsHistory.setStatus("FAILED");
				smsHistory.setErrorDetails(resp.toString());

				
				allSmsHistory.update(smsHistory);
			}
			}
			catch(Exception e){
				e.printStackTrace();
				
				smsHistory.setStatus("FAILED");
				smsHistory.setErrorDetails(e.getMessage());
				
				allSmsHistory.update(smsHistory);
			}
		}
	}
	
	private String outboundPayload(String session_id, String recipient, String text, String mask, Boolean unicode) {
		String payload = "session_id="+session_id;
		payload += "&to="+recipient;
		payload += "&text="+text;
		payload += "&mask="+mask;
		if(unicode != null){
			payload += "&unicode="+unicode;
		}
		
		return payload;
	}

}
