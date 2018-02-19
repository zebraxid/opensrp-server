package org.opensrp.connector.rapidpro;

import static org.opensrp.dto.AlertStatus.normal;
import static org.opensrp.dto.AlertStatus.upcoming;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.opensrp.common.util.DateUtil;
import org.opensrp.domain.Camp;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.domain.SMSLog;
import org.opensrp.repository.AllSMSLog;
import org.opensrp.service.ClientService;
import org.opensrp.service.RapidProServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	
	private static Logger logger = LoggerFactory.getLogger(MessageService.class.toString());
	
	private RapidProServiceImpl rapidproService;
	
	private ClientService clientService;
	
	@Autowired
	private AllSMSLog allSMSLog;
	
	public MessageService() {
		
	}
	
	@Autowired
	public MessageService(RapidProServiceImpl rapidproService, ClientService clientService) {
		
		this.rapidproService = rapidproService;
		this.clientService = clientService;
	}
	
	public void sentMessageToClient(MessageFactory messageFactory, List<Event> events, Camp camp) throws JSONException {
		
		if (events != null) {
			for (Event event : events) {
				/*				Map<String, String> data = action.data();
								logger.info("sentMessageToClient actiondata:" +  data.toString());*/
				if (event.getEntityType().equalsIgnoreCase(ClientType.child.name())) {
					Client child = clientService.find(event.getBaseEntityId());
					int age = getAgeOfChild(child.getBirthdate().toDate());
					if (child != null && (age >= 0 && age < 2)) {
						logger.info("sending message to child childBaseEntityId:" + child.getBaseEntityId() + " ,age:" + age);
						Map<String, List<String>> relationships = child.getRelationships();
						String motherId = relationships.get("mother").get(0);
						Client mother = clientService.find(motherId);
						logger.info("sending message to mother moterBaseEntityId:" + mother.getBaseEntityId());
						generateDataAndsendMessageToRapidpro(mother, ClientType.child, messageFactory, camp);
					}
				} else if (event.getEntityType().equalsIgnoreCase(ClientType.mother.name())) {
					Client mother = clientService.find(event.getBaseEntityId());
					if (mother != null) {
						logger.info("sending message to mother moterBaseEntityId:" + mother.getBaseEntityId());
						generateDataAndsendMessageToRapidpro(mother, ClientType.mother, messageFactory, camp);
					}
					
				} else {
					
				}
			}
		} else {
			logger.info("No vaccine data Found Today");
		}
	}
	
	private int getAgeOfChild(Date dateTime) {
		Calendar now = Calendar.getInstance();
		Calendar dob = Calendar.getInstance();
		int age = 0;
		
		dob.setTime(dateTime);
		
		if (dob.after(now)) {
			throw new IllegalArgumentException("Can't be born in the future");
		}
		
		age = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if (now.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
			age--;
		}
		
		return age;
	}
	
	private void generateDataAndsendMessageToRapidpro(Client client, ClientType clientType, MessageFactory messageFactory,
	                                                  Camp camp) {
		
		Map<String, Object> attributes = new HashMap<>();
		attributes = client.getAttributes();
		List<String> urns = new ArrayList<String>();
		List<String> contacts = new ArrayList<String>();
		List<String> groups = new ArrayList<String>();
		String mobileNo = null;
		String smsText = null;
		if (attributes.containsKey("phoneNumber")) {
			mobileNo = addExtensionToMobile((String) attributes.get("phoneNumber"));
			smsText = messageFactory.getClientType(clientType).message(client, camp, null);
			logger.info("sending mesage to mobileno:" + mobileNo);
			urns.add("tel:" + mobileNo);
			rapidproService.sendMessage(urns, contacts, groups, smsText, "");
			SMSLog smsLog = new SMSLog();
			smsLog.setMobileNo(mobileNo);
			smsLog.setSmsText(smsText);
			smsLog.setSentTime(new Date());
			smsLog.setProviderName(camp.getProviderName());
			smsLog.setCampDate(camp.getDate());
			smsLog.setCampName(camp.getCampName());
			smsLog.setCenterName(camp.getCenterName());
			allSMSLog.add(smsLog);
		}
	}
	
	private boolean isEligible(Map<String, String> data) {
		boolean status = false;
		if (data.get("alertStatus").equalsIgnoreCase(normal.name())) {
			if (DateUtil.dateDiff(data.get("expiryDate")) == 0) {
				status = true;
			}
			
		} else if (data.get("alertStatus").equalsIgnoreCase(upcoming.name())) {
			if (DateUtil.dateDiff(data.get("startDate")) == 0) {
				status = true;
			}
		}
		return status;
	}
	
	private String addExtensionToMobile(String mobile) {
		if (mobile.length() == 10) {
			mobile = "+880" + mobile;
			
		} else if (mobile.length() > 10) {
			mobile = mobile.substring(mobile.length() - 10);
			mobile = "+880" + mobile;
		} else {
			
			throw new IllegalArgumentException("invalid mobile no!!");
		}
		return mobile;
		
	}
	
}
