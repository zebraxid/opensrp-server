package org.opensrp.connector.rapidpro;

import static org.opensrp.dto.AlertStatus.normal;
import static org.opensrp.dto.AlertStatus.upcoming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.opensrp.common.util.DateUtil;
import org.opensrp.domain.Camp;
import org.opensrp.domain.Client;
import org.opensrp.scheduler.Action;
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
	
	public MessageService() {
		
	}
	
	@Autowired
	public MessageService(RapidProServiceImpl rapidproService, ClientService clientService) {
		
		this.rapidproService = rapidproService;
		this.clientService = clientService;
	}
	
	public void sentMessageToClient(MessageFactory messageFactory, List<Action> actions, Camp camp) throws JSONException {
		
		if (actions != null) {
			for (Action action : actions) {
				Map<String, String> data = action.data();
				
				if (data.get("beneficiaryType").equalsIgnoreCase(ClientType.child.name())) {
					//if (isEligible(data)) {
					Client child = clientService.find(action.baseEntityId());
					Map<String, List<String>> relationships = child.getRelationships();
					String motherId = relationships.get("mother").get(0);
					Client mother = clientService.find(motherId);
					generateDataAndsendMessageToRapidpro(mother, data, messageFactory, camp);
					//}
				} else if (data.get("beneficiaryType").equalsIgnoreCase(ClientType.mother.name())) {
					
				} else {
					
				}
			}
		} else {
			logger.info("No vaccine data Found Today");
		}
	}
	
	private void generateDataAndsendMessageToRapidpro(Client client, Map<String, String> data,
	                                                  MessageFactory messageFactory, Camp camp) {
		
		Map<String, Object> attributes = new HashMap<>();
		attributes = client.getAttributes();
		List<String> urns;
		urns = new ArrayList<String>();
		urns.add("tel:" + addExtensionToMobile((String) attributes.get("phoneNumber")));
		List<String> contacts;
		contacts = new ArrayList<String>();
		List<String> groups = new ArrayList<String>();
		rapidproService.sendMessage(urns, contacts, groups,
		    messageFactory.getClientType(ClientType.child).message(client, camp, data), "");
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
			// whatever is appropriate in this case
			throw new IllegalArgumentException("word has less than 10 characters!");
		}
		return mobile;
		
	}
	
}
