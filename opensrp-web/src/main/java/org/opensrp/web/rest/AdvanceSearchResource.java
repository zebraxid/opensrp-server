package org.opensrp.web.rest;

import static org.opensrp.common.AllConstants.BaseEntity.*;
import static org.opensrp.common.AllConstants.Client.BIRTH_DATE;
import static org.opensrp.common.AllConstants.Client.DEATH_DATE;
import static org.opensrp.common.AllConstants.Client.FIRST_NAME;
import static org.opensrp.common.AllConstants.Client.GENDER;
import static org.opensrp.web.rest.RestUtils.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.search.Query;
import org.joda.time.DateTime;
import org.opensrp.domain.Event;
import org.opensrp.domain.Client;
import org.opensrp.service.ClientService;
import org.opensrp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping(value = "/rest/advanceSearch")
public class AdvanceSearchResource extends RestResource<HashMap<JsonObject, List<Event>>>{
	private ClientService clientService;
	private EventService eventService;
	
	@Autowired
	public AdvanceSearchResource(ClientService clientService,EventService eventService) {
		this.clientService = clientService;
		this.eventService = eventService;
	}
	
	@Override
	public HashMap<JsonObject, List<Event>> getByUniqueId(String uniqueId) {
		return null;
	}
	
	@Override
    public HashMap<JsonObject, List<Event>> create(HashMap<JsonObject, List<Event>> o) {
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
	public HashMap<JsonObject, List<Event>> update(HashMap<JsonObject, List<Event>> entity) {//TODO check if send property and id matches
		return null;//TODO update should only be based on baseEntityId
	}
	
	@Override
	public List<HashMap<JsonObject, List<Event>>> search(HttpServletRequest request, String query, String sort, Integer limit, Integer skip, Boolean fts) throws ParseException {
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
				HashMap<JsonObject, List<Event>> map = new HashMap<JsonObject, List<Event>>();
				for (Client client : clients) {
					JsonObject ob=new JsonObject();
					ob.addProperty("baseEntityId", client.getBaseEntityId());
					ob.addProperty("firstName", client.getFirstName());
					ob.addProperty("lastName", client.getLastName());
					ob.addProperty("gender", client.getGender());
					ob.addProperty("dob", String.valueOf(client.getBirthdate()));
					map.put(ob,eventService.findByBaseEntityAndType(client.getBaseEntityId(), status));
				}
				List<HashMap<JsonObject, List<Event>>> list=new ArrayList<HashMap<JsonObject, List<Event>>>();
				list.add(map);				
				return list;
	}
	
	@Override
	public List<HashMap<JsonObject, List<Event>>> filter(String query) {
		return null;
	}

}

