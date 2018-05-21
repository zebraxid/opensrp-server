package org.opensrp.web.rest;

import static org.opensrp.common.AllConstants.BaseEntity.*;
import static org.opensrp.common.AllConstants.Client.BIRTH_DATE;
import static org.opensrp.common.AllConstants.Client.DEATH_DATE;
import static org.opensrp.common.AllConstants.Client.FIRST_NAME;
import static org.opensrp.common.AllConstants.Client.GENDER;
import static org.opensrp.web.rest.RestUtils.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
import com.mysql.fabric.xmlrpc.base.Array;
import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping(value = "/rest/advanceSearch")
public class AdvanceSearchResource extends RestResource<Map<String, Object>>{
	private ClientService clientService;
	private EventService eventService;
	private String lastName;
	
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
	public Map<String, Object> advanceSearch(HttpServletRequest request, String query, String sort, Integer limit, Integer skip, Boolean fts) throws ParseException {
				Map<String, Query> fields = RestUtils.parseClauses(query);
				String firstName = RestUtils.getStringFilter(fields.get("firstName"));
				String participantID =  RestUtils.getStringFilter(fields.get("participantID"));
				lastName = RestUtils.getStringFilter(fields.get("lastName"));
				String gender = RestUtils.getStringFilter(fields.get(GENDER));
				String status = RestUtils.getStringFilter(fields.get("status"));
				String phoneNumber = RestUtils.getStringFilter(fields.get("phoneNumber"));
				String ageGroup = RestUtils.getStringFilter(fields.get("ageGroup"));
				String presumptive = RestUtils.getStringFilter(fields.get("presumptive"));
				String positive = RestUtils.getStringFilter(fields.get("positive"));
				String intreatment = RestUtils.getStringFilter(fields.get("intreatment"));

				Map<String,Object> patMap = new HashMap<>();
				
				if(!StringUtils.isEmptyOrWhitespaceOnly(participantID)){
					populateByParticipantId(patMap,participantID);
				}
				else if(!StringUtils.isEmptyOrWhitespaceOnly(phoneNumber)){
					populateByPhoneLastTenDigits(patMap, phoneNumber.substring(phoneNumber.length() - 10, phoneNumber.length()));
				}
				else{
					populateByOtherAttributes(patMap,gender,ageGroup,firstName,presumptive,positive,intreatment);
				}
				populateAllEventsForClients(patMap);
				return patMap;
	}
	
	@Override
	public List<Map<String, Object>> filter(String query) {
		return null;
	}

	@Override
	public List<Map<String, Object>> search(HttpServletRequest request,
			String query, String sort, Integer limit, Integer skip, Boolean fts)
			throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void populateByParticipantId(Map<String,Object> patMap, String id){
		List<Client> clients = clientService.findAllByIdentifier("TBREACH ID", id);
		if(clients != null && clients.size() > 0)	
			checkClientStatus(patMap, clients);
	}
	private void populateByPhoneLastTenDigits(Map<String,Object> patMap, String phoneNumber){
		List<Client> clients = clientService.findAllByLastTenPhoneDigits(phoneNumber);
		if(clients != null && clients.size() > 0)
			checkClientStatus(patMap, clients);
	}
	
	private void populateByOtherAttributes(Map<String, Object> patMap,
			String gender, String ageGroup, String firstName, String presumptive, String positive, String intreatment) {
		
		List<Client> presClients = new ArrayList<Client>();
		List<Client> posClients = new ArrayList<Client>();
		List<Client> intreatmentClients = new ArrayList<Client>();
		if(presumptive != null)	{
			presClients = clientService.findClientsByGAGFN(gender,"presumptive", ageGroup, firstName);
			if(presClients.size() > 0){
				if(!StringUtils.isEmptyOrWhitespaceOnly(this.lastName))
					filterByLastName(patMap, presClients);
				if(presClients.size() > 0)
					patMap.put("presumptive", presClients);
			}
		}
		if(positive != null){
			posClients = clientService.findClientsByGAGFN(gender,"positive", ageGroup, firstName);
			if(posClients.size() > 0){
				if(!StringUtils.isEmptyOrWhitespaceOnly(this.lastName))
					filterByLastName(patMap, posClients);
				if(posClients.size() > 0)
					patMap.put("positive", posClients);
				ListIterator<Client> iterator = posClients.listIterator();
				while(iterator.hasNext()){
					List<Event> listPosEvents = eventService.findBaseEntityIdsByPositiveEvent(iterator.next().getBaseEntityId());
					if(listPosEvents ==null || listPosEvents.size() <= 0)
						iterator.remove();
				}
			}					
		}
		if(intreatment != null)	{
			intreatmentClients = clientService.findClientsByGAGFN(gender,"in-treatment", ageGroup, firstName);
			if(intreatmentClients.size() > 0){
				if(!StringUtils.isEmptyOrWhitespaceOnly(this.lastName))
					filterByLastName(patMap, intreatmentClients);
				if(intreatmentClients.size() > 0)
					patMap.put("intreatment", intreatmentClients);
			}
		}

	}

	private void populateAllEventsForClients(Map<String,Object> patMap){
		ObjectMapper obm = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
		obm.setDateFormat(df);
		
		List<Map<String, Object>> ecList = null;
		if(patMap.size() > 0){
			Iterator it = patMap.entrySet().iterator();
			while(it.hasNext()){
				ecList = new ArrayList<Map<String,Object>>();
				Map.Entry pair = (Map.Entry) it.next();
				for(Client client : (List<Client>)pair.getValue()){
					Map<String, Object> clientMap = new HashMap<String, Object>();
					clientMap = obm.convertValue(client, Map.class);
					List<Event> listEvents = eventService.findAllEventsByBaseEntityId(client.getBaseEntityId());
//					List<Event> listEvents = eventService.findAllByBaseEntityAndType(client.getBaseEntityId(), status);
					List<Map<String,Object>> eventList = new ArrayList<Map<String, Object>>();
					if(listEvents != null && listEvents.size() > 0){
						for(Event e : listEvents){
							Map<String,Object> eMap = obm.convertValue(e, Map.class);
							eMap.put("id", (String) eMap.get("_id"));
							eMap.remove("_id");
							eventList.add(eMap);
						}
						clientMap.put("events", eventList);
					}
					ecList.add(clientMap);
				}
				patMap.put((String)pair.getKey(), ecList);
				
			}
		}
	}
	
	private void checkClientStatus(Map<String,Object> patMap, List<Client> clients){
		Map<String,Object> attributes = clients.get(0).getAttributes();
		if(!attributes.containsKey("date_removed")){
			if(attributes.containsKey("baseline")){
				patMap.put("intreatment", clients);
			}
			else if(attributes.containsKey("diagnosis_date")){
				List<Event> listPosEvents = eventService.findBaseEntityIdsByPositiveEvent(clients.get(0).getBaseEntityId());
				if(listPosEvents !=null && listPosEvents.size() > 0){
					List<Event> list = eventService.findBaseEntityIdsByPositiveEvent(clients.get(0).getBaseEntityId());
					if(list !=null && list.size() > 0)
						patMap.put("positive", clients);
				}
			}
			else if(attributes.containsKey("Primary Contact Number")){
				patMap.put("presumptive", clients);
			}
		}
		
	}
	
	private void filterByLastName(Map<String,Object> patMap, List<Client> presClients){
		ListIterator<Client> it = presClients.listIterator();
		while(it.hasNext()){
			try{
				Client c = it.next();
				if(!c.getLastName().equalsIgnoreCase(this.lastName)){
					it.remove();
				}
			}catch(NullPointerException e){
				e.printStackTrace();
			}
		}
	}
}

