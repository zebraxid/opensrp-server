package org.opensrp.web.rest;

import static org.opensrp.common.AllConstants.BaseEntity.BASE_ENTITY_ID;
import static org.opensrp.common.AllConstants.BaseEntity.LAST_UPDATE;
import static org.opensrp.common.AllConstants.Event.*;
import static org.opensrp.common.AllConstants.Event.PROVIDER_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.search.Query;
import org.joda.time.DateTime;
import org.opensrp.domain.Event;
import org.opensrp.service.ClientService;
import org.opensrp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/rest/event")
public class EventResource extends RestResource<Event>{
	private EventService eventService;
	private ClientService clientService;
	
	@Autowired
	public EventResource(ClientService clientService, EventService eventService) {
		this.clientService = clientService;
		this.eventService = eventService;
	}

	@Override
	public Event getByUniqueId(String uniqueId) {
		return eventService.find(uniqueId);
	}
	
/*	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public Event getByBaseEntityAndFormSubmissionId(@RequestParam String baseEntityId, @RequestParam String formSubmissionId) {
		return eventService.getByBaseEntityAndFormSubmissionId(baseEntityId, formSubmissionId);
	}*/
	
	@Override
    public Event create(Event o) {
		return eventService.addEvent(o);
	}

	@Override
	public List<String> requiredProperties() {
		List<String> p = new ArrayList<>();
		p.add(BASE_ENTITY_ID);
		//p.add(FORM_SUBMISSION_ID);
		p.add(EVENT_TYPE);
		//p.add(LOCATION_ID);
		//p.add(EVENT_DATE);
		p.add(PROVIDER_ID);
		//p.add(ENTITY_TYPE);
		return p;
	}
	
	@Override
	public Event update(Event entity) {
		return eventService.mergeEvent(entity);
	}
	
	public static void main(String[] args) {

	}
	
	@Override
	public List<Event> search(HttpServletRequest request, String query, String sort, Integer limit, Integer skip, Boolean fts) {
		if(fts == null || fts == false){
			Map<String, Query> fields = RestUtils.parseClauses(query);
			if(fields.containsKey(LAST_UPDATE) == false){
				throw new IllegalArgumentException("A valid date/long range for field "+LAST_UPDATE+" must be specified");
			}
			if(fields.containsKey("type") == false){
				throw new IllegalArgumentException("A valid value for field type must be specified");
			}
			
			DateTime from = RestUtils.getLowerDateFilter(fields.get(LAST_UPDATE));
			DateTime to = RestUtils.getUpperDateFilter(fields.get(LAST_UPDATE));
			String searchType = RestUtils.getStringFilter(fields.get("type"));
			
			if(searchType.equalsIgnoreCase("full")){
				return eventService.findAllByTimestamp(from, to);
			}
			if(searchType.toLowerCase().contains("event")){
				String entityOrEventType = RestUtils.getStringFilter(fields.get(EVENT_TYPE));
				if(entityOrEventType == null){
					entityOrEventType = RestUtils.getStringFilter(fields.get(ENTITY_TYPE));
				}
				if(entityOrEventType != null){
					return eventService.findAllByEntityOrEventType(entityOrEventType, from, to);
				}

				String locationOrProvider = RestUtils.getStringFilter(fields.get(PROVIDER_ID));
				if(locationOrProvider == null){
					locationOrProvider = RestUtils.getStringFilter(fields.get(LOCATION_ID));
				}
				if(locationOrProvider != null){
					return eventService.findAllByLocationOrProvider(locationOrProvider, from, to);
				}
			}
		}
		else {
			return eventService.findEventsByDynamicQuery(query, sort, limit, skip);
		}
		
		return new ArrayList<>();
	}
}
