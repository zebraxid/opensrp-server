package org.opensrp.web.rest;

import static org.opensrp.common.AllConstants.BaseEntity.*;
import static org.opensrp.common.AllConstants.Client.BIRTH_DATE;
import static org.opensrp.common.AllConstants.Client.DEATH_DATE;
import static org.opensrp.common.AllConstants.Client.FIRST_NAME;
import static org.opensrp.common.AllConstants.Client.GENDER;
import static org.opensrp.web.rest.RestUtils.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.search.Query;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.domain.Event;
import org.opensrp.domain.Client;
import org.opensrp.service.ClientService;
import org.opensrp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping(value = "/rest/advanceSearch")
public class AdvanceSearchResource extends RestResource<Map<String, Object>>{
	private ClientService clientService;
	private EventService eventService;
	
	@Autowired
	public AdvanceSearchResource(ClientService clientService,EventService eventService) {
		this.clientService = clientService;
		this.eventService = eventService;
	}
	
	@Override
	public HashMap<String, Object> getByUniqueId(String uniqueId) {
		return null;
	}
	
	@Override
    public Map<String, Object> create(Map<String, Object> o) {
		return null;
	}

	@Override
	public List<String> requiredProperties() {
		List<String> p = new ArrayList<>();
		p.add(FIRST_NAME);
		p.add(BIRTH_DATE);
		p.add(GENDER);
		p.add(BASE_ENTITY_ID);
		return p;
	}

	@Override
	public Map<String, Object> update(Map<String, Object> entity) {//TODO check if send property and id matches
		return null;//TODO update should only be based on baseEntityId
	}
	
	@Override
	public List<Map<String, Object>> search(HttpServletRequest request, String query, String sort, Integer limit, Integer skip, Boolean fts) throws ParseException {
				Map<String, Query> fields = RestUtils.parseClauses(query);
				String firstName = RestUtils.getStringFilter(fields.get("firstName"));
				String participantID =  RestUtils.getStringFilter(fields.get("participantID"));
				String lastName = RestUtils.getStringFilter(fields.get("lastName"));
				String gender = RestUtils.getStringFilter(fields.get(GENDER));
				String status = RestUtils.getStringFilter(fields.get("status"));
				String phoneNumber = RestUtils.getStringFilter(fields.get("phoneNumber"));
				String ageFrom = RestUtils.getStringFilter(fields.get("ageFrom"));
				String ageTo = RestUtils.getStringFilter(fields.get("ageTo"));
				String query1="",query2="";
				
				if(!StringUtils.isEmptyOrWhitespaceOnly(participantID))
				{
					query1=participantID;
					query2=participantID;
				}
				else if(!StringUtils.isEmptyOrWhitespaceOnly(phoneNumber))
				{
					query1=phoneNumber;
					query2=phoneNumber;
				}
				else if(!StringUtils.isEmptyOrWhitespaceOnly(firstName))
				{
					query1=firstName; 
					query2=firstName+"zzz";
				}
				else if(!StringUtils.isEmptyOrWhitespaceOnly(lastName))
				{
					query1=lastName; 
					query2=lastName+"zzz";
				}
				else if(!StringUtils.isEmptyOrWhitespaceOnly(ageFrom) && !StringUtils.isEmptyOrWhitespaceOnly(ageTo))
				{
					query1=ageFrom; 
					query2=ageTo;
				}
				List<Client> clients = clientService.findAllByUserData(gender,query1,query2);
				List<Map<String, Object>> ecList = new ArrayList<Map<String,Object>>();
				ObjectMapper obm = new ObjectMapper();
				for (Client client : clients) {
					Map<String, Object> clientMap = new HashMap<String, Object>();
					clientMap = obm.convertValue(client, Map.class);
					List<Event> listEvents = eventService.findAllByBaseEntityAndType(client.getBaseEntityId(), status);
					List<Map<String,Object>> eventList = new ArrayList<Map<String, Object>>();
					for(Event e : listEvents){
						eventList.add(obm.convertValue(e, Map.class));
					}
					clientMap.put("events", eventList);
					ecList.add(clientMap);
				}	
				return ecList;
	}
	
	@Override
	public List<Map<String, Object>> filter(String query) {
		return null;
	}

}

