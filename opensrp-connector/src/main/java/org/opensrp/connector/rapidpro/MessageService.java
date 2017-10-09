package org.opensrp.connector.rapidpro;

import static org.opensrp.dto.AlertStatus.normal;
import static org.opensrp.dto.AlertStatus.upcoming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.opensrp.common.util.DateUtil;
import org.opensrp.domain.Client;
import org.opensrp.scheduler.Action;
import org.opensrp.service.ClientService;
import org.opensrp.service.RapidProServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	
	private RapidProServiceImpl rapidproService;
	
	private ClientService clientService;
	
	public MessageService() {
		
	}
	
	@Autowired
	public MessageService(RapidProServiceImpl rapidproService, ClientService clientService) {
		
		this.rapidproService = rapidproService;
		this.clientService = clientService;
	}
	
	public void sentMessageToClient(MessageFactory messageFactory, List<Action> actions) throws JSONException {
		
		for (Action action : actions) {
			Map<String, String> data = action.data();
			
			if (data.get("beneficiaryType").equalsIgnoreCase(ClientType.child.name())) {
				if (isEligible(data)) {
					Client child = clientService.find(action.baseEntityId());
					Map<String, List<String>> relationships = child.getRelationships();
					String motherId = relationships.get("mother").get(0);
					Client mother = clientService.find(motherId);
					generateDataAndsendMessageToRapidpro(mother, data, messageFactory);
				}
			} else if (data.get("beneficiaryType").equalsIgnoreCase(ClientType.mother.name())) {
				
			} else {
				
			}
		}
		
	}
	
	private void generateDataAndsendMessageToRapidpro(Client client, Map<String, String> data, MessageFactory messageFactory) {
		
		Map<String, Object> attributes = new HashMap<>();
		attributes = client.getAttributes();
		List<String> urns;
		urns = new ArrayList<String>();
		urns.add("tel:" + addExtensionToMobile((String) attributes.get("phoneNumber")));
		List<String> contacts;
		contacts = new ArrayList<String>();
		List<String> groups = new ArrayList<String>();
		rapidproService.sendMessage(urns, contacts, groups, messageFactory.getClientType(ClientType.child).message(client),
		    "");
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
