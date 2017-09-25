package org.opensrp.web.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.opensrp.common.util.DateUtil;
import org.opensrp.connector.rapidpro.MessageFactory;
import org.opensrp.domain.Client;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.service.ActionService;
import org.opensrp.service.ClientService;
import org.opensrp.service.RapidProServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class MessageListener {
	
	private ActionService actionService;
	
	private RapidProServiceImpl rapidproService;
	
	private ClientService clientService;
	
	public MessageListener() {
		
	}
	
	@Autowired
	public MessageListener(ActionService actionService, RapidProServiceImpl rapidproService, ClientService clientService) {
		this.actionService = actionService;
		this.rapidproService = rapidproService;
		this.clientService = clientService;
	}
	
	/*public void campAnnouncementListener(String HA) {
		MessageFactory messageFactory = null;
		messageFactory = MessageFactory.getMessageFactory("Ann");
		try {
			List<CampDate> campDates = campDateService.findCampByToday(HA);
			sentMessageToClient(messageFactory, campDates);
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
	}*/
	
	public void remainderMessageListener() throws JSONException {
		System.err.println("come here");
		MessageFactory messageFactory = null;
		messageFactory = MessageFactory.getMessageFactory("Remainder");
		
		try {
			List<Action> actions = actionService.findAllActionNotExpired();
			sentMessageToClient(messageFactory, actions);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sentMessageToClient(MessageFactory messageFactory, List<Action> actions) throws JSONException {
		String message;
		System.err.println("messageFactory:" + messageFactory);
		
		for (Action action : actions) {
			Map<String, String> data = action.data();
			
			if (data.get("beneficiaryType").equalsIgnoreCase("child")) {
				if (isEligible(data)) {
					/*Client client = clientService.find(action.baseEntityId());
					Map<String, Object> attributes = new HashMap<>();
					attributes = client.getAttributes();
					List<String> urns;
					urns = new ArrayList<String>();
					urns.add("tel:" + getMobileNumber((String) attributes.get("phoneNumber")));
					List<String> contacts;
					contacts = new ArrayList<String>();
					List<String> groups = new ArrayList<String>();
					message = messageFactory.getMessageType("Child").message();
					rapidproService.sendMessage(urns, contacts, groups, message, "");*/
					sendMessage(action, data, messageFactory);
				}
			} else if (data.get("beneficiaryType").equalsIgnoreCase("mother")) {
				
			} else {
				
			}
			
		}
		
	}
	
	private void sendMessage(Action action, Map<String, String> data, MessageFactory messageFactory) {
		Client client = clientService.find(action.baseEntityId());
		Map<String, Object> attributes = new HashMap<>();
		attributes = client.getAttributes();
		List<String> urns;
		urns = new ArrayList<String>();
		urns.add("tel:" + getMobileNumber((String) attributes.get("phoneNumber")));
		List<String> contacts;
		contacts = new ArrayList<String>();
		List<String> groups = new ArrayList<String>();
		rapidproService.sendMessage(urns, contacts, groups, messageFactory.getMessageType("Child").message(), "");
	}
	
	private boolean isEligible(Map<String, String> data) {
		boolean status = false;
		if (data.get("alertStatus").equalsIgnoreCase("normal")) {
			if (DateUtil.dateDiff(data.get("expiryDate")) == 0) {
				status = true;
			}
			
		} else {
			if (DateUtil.dateDiff(data.get("startDate")) == 0) {
				status = true;
			}
		}
		return status;
	}
	
	private String getMobileNumber(String mobile) {
		if (mobile.length() == 10) {
			mobile = "+880" + mobile;
			
		} else if (mobile.length() > 10) {
			mobile = mobile.substring(mobile.length() - 10);
			mobile = "+880" + mobile;
		} else {
			// whatever is appropriate in this case
			throw new IllegalArgumentException("word has less than 10 characters!");
		}
		return mobile;
		
	}
}
