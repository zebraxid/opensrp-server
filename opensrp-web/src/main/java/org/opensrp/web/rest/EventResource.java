package org.opensrp.web.rest;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.CLIENTS_FETCH_BATCH_SIZE;
import static org.opensrp.common.AllConstants.BaseEntity.BASE_ENTITY_ID;
import static org.opensrp.common.AllConstants.BaseEntity.LAST_UPDATE;
import static org.opensrp.common.AllConstants.Event.ENTITY_TYPE;
import static org.opensrp.common.AllConstants.Event.EVENT_DATE;
import static org.opensrp.common.AllConstants.Event.EVENT_TYPE;
import static org.opensrp.common.AllConstants.Event.LOCATION_ID;
import static org.opensrp.common.AllConstants.Event.PROVIDER_ID;
import static org.opensrp.common.AllConstants.Event.TEAM;
import static org.opensrp.common.AllConstants.Event.TEAM_ID;
import static org.opensrp.web.rest.RestUtils.getDateRangeFilter;
import static org.opensrp.web.rest.RestUtils.getIntegerFilter;
import static org.opensrp.web.rest.RestUtils.getStringFilter;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.json.JSONObject;
import org.opensrp.common.AllConstants.BaseEntity;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.domain.Obs;
import org.opensrp.search.EventSearchBean;
import org.opensrp.service.ClientService;
import org.opensrp.service.EventService;
import org.opensrp.util.DateTimeTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping(value = "/rest/event")
public class EventResource extends RestResource<Event> {
	
	private static Logger logger = LoggerFactory.getLogger(EventResource.class.toString());
	
	private EventService eventService;
	
	private ClientService clientService;
	
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();
	
	@Value("#{opensrp['opensrp.sync.search.missing.client']}")
	private boolean searchMissingClients;
	
	@Autowired
	public EventResource(ClientService clientService, EventService eventService) {
		this.clientService = clientService;
		this.eventService = eventService;
	}
	
	@Override
	public Event getByUniqueId(String uniqueId) {
		return eventService.find(uniqueId);
	}
	
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	@ResponseBody
	protected List<Event> getAll() {
		return eventService.getAll();
	}
	
	/**
	 * Fetch events ordered by serverVersion ascending order and return the clients associated with
	 * the events
	 * 
	 * @param request
	 * @return a map response with events, clients and optionally msg when an error occurs
	 */
	/*@RequestMapping(headers = { "Accept=application/json;charset=UTF-8" }, value = "/sync", method = RequestMethod.GET)
	@ResponseBody
	protected ResponseEntity<String> sync(HttpServletRequest request) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			String providerId = getStringFilter(PROVIDER_ID, request);
			String requestProviderName = request.getRemoteUser();
			String locationId = getStringFilter(LOCATION_ID, request);
			String baseEntityId = getStringFilter(BASE_ENTITY_ID, request);
			String serverVersion = getStringFilter(BaseEntity.SERVER_VERSIOIN, request);
			String team = getStringFilter(TEAM, request);
			String teamId = getStringFilter(TEAM_ID, request);
			logger.info("synced user " + providerId + locationId + teamId + ", timestamp : " + serverVersion);
			Long lastSyncedServerVersion = null;
			if (serverVersion != null) {
				lastSyncedServerVersion = Long.valueOf(serverVersion) + 1;
			}
			Integer limit = getIntegerFilter("limit", request);
			if (limit == null || limit.intValue() == 0) {
				limit = 25;
			}
			String getProviderName = "";
			List<Event> eventList = new ArrayList<Event>();
			List<Event> events = new ArrayList<Event>();
			List<Event> allEvent = new ArrayList<Event>();
			
			List<String> clientIds = new ArrayList<String>();
			List<Client> clients = new ArrayList<Client>();
			long startTime = System.currentTimeMillis();
			if (teamId != null || team != null || providerId != null || locationId != null || baseEntityId != null) {
				EventSearchBean eventSearchBean = new EventSearchBean();
				eventSearchBean.setTeam(team);
				eventSearchBean.setTeamId(teamId);
				eventSearchBean.setProviderId(providerId);
				eventSearchBean.setLocationId(locationId);
				eventSearchBean.setBaseEntityId(baseEntityId);
				eventSearchBean.setServerVersion(lastSyncedServerVersion);
				eventList = eventService.findEvents(eventSearchBean, BaseEntity.SERVER_VERSIOIN, "asc", limit);
				allEvent.addAll(eventList);
				logger.info("fetching events took: " + (System.currentTimeMillis() - startTime));
				logger.info("Initial Size:" + eventList.size());
				if (!eventList.isEmpty()) {
					for (Event event : eventList) {
						getProviderName = event.getProviderId();
						logger.info("getProviderName:" + getProviderName + ": request provider name" + requestProviderName);
						if (getProviderName.isEmpty()) {
							events.add(event);
						} else if (!getProviderName.equalsIgnoreCase(requestProviderName)) {} else {
							events.add(event);
						}
					}
					
					logger.info("After cleaning Size:" + events.size());
					for (Event event : events) {
						if (event.getBaseEntityId() != null && !event.getBaseEntityId().isEmpty()
						        && !clientIds.contains(event.getBaseEntityId())) {
							clientIds.add(event.getBaseEntityId());
						}
					}
					for (int i = 0; i < clientIds.size(); i = i + CLIENTS_FETCH_BATCH_SIZE) {
						int end = i + CLIENTS_FETCH_BATCH_SIZE < clientIds.size() ? i + CLIENTS_FETCH_BATCH_SIZE : clientIds
						        .size();
						clients.addAll(clientService.findByFieldValue(BASE_ENTITY_ID, clientIds.subList(i, end)));
					}
					logger.info("fetching clients took: " + (System.currentTimeMillis() - startTime));
				}
			}
			
			if (searchMissingClients) {
				
				List<String> foundClientIds = new ArrayList<>();
				for (Client client : clients) {
					foundClientIds.add(client.getBaseEntityId());
				}
				
				boolean removed = clientIds.removeAll(foundClientIds);
				if (removed) {
					for (String clientId : clientIds) {
						Client client = clientService.getByBaseEntityId(clientId);
						if (client != null) {
							clients.add(client);
						}
					}
				}
				logger.info("fetching missing clients took: " + (System.currentTimeMillis() - startTime));
			}
			
			JsonArray eventsArray = (JsonArray) gson.toJsonTree(events, new TypeToken<List<Event>>() {}.getType());
			
			JsonArray clientsArray = (JsonArray) gson.toJsonTree(clients, new TypeToken<List<Client>>() {}.getType());
			
			response.put("events", eventsArray);
			response.put("clients", clientsArray);
			response.put("no_of_events", events.size());
			
			return new ResponseEntity<>(gson.toJson(response), HttpStatus.OK);
			
		}
		catch (Exception e) {
			response.put("msg", "Error occurred");
			logger.error("", e);
			return new ResponseEntity<>(new Gson().toJson(response), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
	
	/**
	 * Fetch events ordered by serverVersion ascending order and return the clients associated with
	 * the events
	 * 
	 * @param request
	 * @return a map response with events, clients and optionally msg when an error occurs
	 */
	@RequestMapping(headers = { "Accept=application/json;charset=UTF-8" }, value = "/sync", method = RequestMethod.GET)
	@ResponseBody
	protected ResponseEntity<String> sync(HttpServletRequest request) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			String providerId = getStringFilter(PROVIDER_ID, request);
			String requestProviderName = request.getRemoteUser();
			String locationId = getStringFilter(LOCATION_ID, request);
			String baseEntityId = getStringFilter(BASE_ENTITY_ID, request);
			String serverVersion = getStringFilter(BaseEntity.SERVER_VERSIOIN, request);
			String team = getStringFilter(TEAM, request);
			String teamId = getStringFilter(TEAM_ID, request);
			logger.info("synced user " + providerId + locationId + teamId + ", timestamp : " + serverVersion);
			Long lastSyncedServerVersion = null;
			if (serverVersion != null) {
				lastSyncedServerVersion = Long.valueOf(serverVersion) + 1;
			}
			Integer limit = getIntegerFilter("limit", request);
			if (limit == null || limit.intValue() == 0) {
				limit = 25;
			}
			String getProviderName = "";
			List<Event> eventList = new ArrayList<Event>();
			List<Event> events = new ArrayList<Event>();
			List<Event> allEvent = new ArrayList<Event>();
			
			List<String> clientIds = new ArrayList<String>();
			List<Client> clients = new ArrayList<Client>();
			long startTime = System.currentTimeMillis();
			if (teamId != null || team != null || providerId != null || locationId != null || baseEntityId != null) {
				EventSearchBean eventSearchBean = new EventSearchBean();
				eventSearchBean.setTeam(team);
				eventSearchBean.setTeamId(teamId);
				eventSearchBean.setProviderId(providerId);
				eventSearchBean.setLocationId(locationId);
				eventSearchBean.setBaseEntityId(baseEntityId);
				eventSearchBean.setServerVersion(lastSyncedServerVersion);
				eventList = eventService.findEvents(eventSearchBean, BaseEntity.SERVER_VERSIOIN, "asc", limit);
				allEvent.addAll(eventList);
				logger.info("fetching events took: " + (System.currentTimeMillis() - startTime));
				logger.info("Initial Size:" + eventList.size());
				if (!eventList.isEmpty()) {
					for (Event event : eventList) {
						getProviderName = event.getProviderId();
						logger.info("getProviderName:" + getProviderName + ": request provider name" + requestProviderName);
						if (getProviderName.isEmpty()) {
							events.add(event);
						} else if (!getProviderName.equalsIgnoreCase(requestProviderName)) {} else {
							events.add(event);
						}
					}
					
					logger.info("After cleaning Size:" + events.size());
					for (Event event : events) {
						if (event.getBaseEntityId() != null && !event.getBaseEntityId().isEmpty()
						        && !clientIds.contains(event.getBaseEntityId())) {
							clientIds.add(event.getBaseEntityId());
						}
					}
					for (int i = 0; i < clientIds.size(); i = i + CLIENTS_FETCH_BATCH_SIZE) {
						int end = i + CLIENTS_FETCH_BATCH_SIZE < clientIds.size() ? i + CLIENTS_FETCH_BATCH_SIZE : clientIds
						        .size();
						clients.addAll(clientService.findByFieldValue(BASE_ENTITY_ID, clientIds.subList(i, end)));
					}
					logger.info("fetching clients took: " + (System.currentTimeMillis() - startTime));
				}
			}
			
			if (searchMissingClients) {
				
				List<String> foundClientIds = new ArrayList<>();
				for (Client client : clients) {
					foundClientIds.add(client.getBaseEntityId());
				}
				
				boolean removed = clientIds.removeAll(foundClientIds);
				if (removed) {
					for (String clientId : clientIds) {
						Client client = clientService.getByBaseEntityId(clientId);
						if (client != null) {
							clients.add(client);
						}
					}
				}
				logger.info("fetching missing clients took: " + (System.currentTimeMillis() - startTime));
			}
			
			JsonArray eventsArray = (JsonArray) gson.toJsonTree(events, new TypeToken<List<Event>>() {}.getType());
			
			JsonArray clientsArray = (JsonArray) gson.toJsonTree(clients, new TypeToken<List<Client>>() {}.getType());
			
			response.put("events", eventsArray);
			response.put("clients", clientsArray);
			response.put("no_of_events", events.size());
			
			return new ResponseEntity<>(gson.toJson(response), HttpStatus.OK);
			
		}
		catch (Exception e) {
			response.put("msg", "Error occurred");
			logger.error("", e);
			return new ResponseEntity<>(new Gson().toJson(response), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(headers = { "Accept=application/json;charset=UTF-8" }, value = "/client-list-to-delete", method = RequestMethod.GET)
	@ResponseBody
	protected ResponseEntity<String> clientListToDelete(HttpServletRequest request) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			String providerId = getStringFilter(PROVIDER_ID, request);
			String requestProviderName = request.getRemoteUser();
			String locationId = getStringFilter(LOCATION_ID, request);
			String serverVersion = getStringFilter(BaseEntity.SERVER_VERSIOIN, request);
			logger.info("synced user " + providerId + locationId + ", timestamp : " + serverVersion);
			Long lastSyncedServerVersion = null;
			if (serverVersion != null) {
				lastSyncedServerVersion = Long.valueOf(serverVersion) + 1;
			}
			Integer limit = getIntegerFilter("limit", request);
			if (limit == null || limit.intValue() == 0) {
				limit = 25;
			}
			String getProviderName = "";
			List<Event> eventList = new ArrayList<Event>();
			Set<String> clientIds = new HashSet<String>();
			long startTime = System.currentTimeMillis();
			if (locationId != null) {
				EventSearchBean eventSearchBean = new EventSearchBean();
				eventSearchBean.setLocationId(locationId);
				eventSearchBean.setServerVersion(lastSyncedServerVersion);
				eventList = eventService.findEvents(eventSearchBean, BaseEntity.SERVER_VERSIOIN, "asc", limit);
				logger.info("fetching events took: " + (System.currentTimeMillis() - startTime));
				logger.info("Initial Size:" + eventList.size());
				if (!eventList.isEmpty()) {
					for (Event event : eventList) {
						getProviderName = event.getProviderId();
						logger.info("getProviderName:" + getProviderName + ": request provider name" + requestProviderName);
						if (getProviderName.isEmpty()) {} else if (!getProviderName.equalsIgnoreCase(requestProviderName)) {
							clientIds.add(event.getBaseEntityId());
						} else {}
					}
				}
			}
			return new ResponseEntity<>(gson.toJson(clientIds), HttpStatus.OK);
		}
		catch (Exception e) {
			response.put("msg", "Error occurred");
			logger.error("", e);
			return new ResponseEntity<>(new Gson().toJson(response), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(headers = { "Accept=application/json;charset=UTF-8" }, method = POST, value = "/add")
	public ResponseEntity<HttpStatus> save(@RequestBody String data, HttpServletRequest request) {
		try {
			JSONObject syncData = new JSONObject(data);
			if (!syncData.has("clients") && !syncData.has("events")) {
				return new ResponseEntity<>(BAD_REQUEST);
			}
			String getProvider = "";
			String dataProvider = request.getRemoteUser();
			logger.info("dataProvider:" + dataProvider);
			if (syncData.has("clients")) {
				
				ArrayList<Client> clients = (ArrayList<Client>) gson.fromJson(syncData.getString("clients"),
				    new TypeToken<ArrayList<Client>>() {}.getType());
				logger.info("received client size:" + clients.size());
				for (Client client : clients) {
					try {
						List<Event> events = eventService.findByBaseEntityAndEventTypeContaining(client.getBaseEntityId(),
						    "Registration");
						if (events.size() != 0) {
							Event event = events.get(0);
							getProvider = event.getProviderId();
							logger.info("getProvider:" + getProvider);
						} else {
							getProvider = "";
						}
						if (getProvider.isEmpty() || (dataProvider.equalsIgnoreCase(getProvider) && !getProvider.isEmpty())) {
							client.withIsSendToOpenMRS("yes");
							clientService.addorUpdate(client);
						} else {
							logger.info("already updated by another");
						}
					}
					catch (Exception e) {
						logger.error("Client" + client.getBaseEntityId() == null ? "" : client.getBaseEntityId()
						        + " failed to sync", e);
					}
				}
				
			}
			if (syncData.has("events")) {
				ArrayList<Event> events = (ArrayList<Event>) gson.fromJson(syncData.getString("events"),
				    new TypeToken<ArrayList<Event>>() {}.getType());
				logger.info("received event size:" + events.size());
				for (Event event : events) {
					try {
						List<Event> getEvents = eventService.findByBaseEntityAndEventTypeContaining(event.getBaseEntityId(),
						    "Registration");
						if (getEvents.size() != 0) {
							Event getEvent = getEvents.get(0);
							getProvider = getEvent.getProviderId();
						} else {
							getProvider = "";
						}
						if (getProvider.isEmpty() || (dataProvider.equalsIgnoreCase(getProvider) && !getProvider.isEmpty())) {
							event = eventService.processOutOfArea(event);
							event.withIsSendToOpenMRS("yes");
							eventService.addorUpdateEvent(event);
							Client client = clientService.find(event.getBaseEntityId());
							String eventType = event.getEventType();
							Obs obs = new Obs();
							/*if (eventType.equalsIgnoreCase("Followup Pregnant Status")) {
								obs = event.getObs("", "pregnant_status");								
								String value = (String) obs.getValue();
								System.err.println("value>>>>>>>>>>>"+value);
								if (value.equalsIgnoreCase("প্রসব পূর্ব")) {
									client.addAttribute("PregnancyStatus", "Antenatal Period");
									clientService.addorUpdate(client);
								} else if (value.equalsIgnoreCase("প্রসবোত্তর")) {
									obs = event.getObs("", "Delivery_date");
									Object deliveryDate = obs.getValue();									
									client.addAttribute("delivery_date", deliveryDate);
									client.addAttribute("PregnancyStatus", "Postnatal");
									clientService.addorUpdate(client);
								}
								
							} else if (eventType.equalsIgnoreCase("Followup Marital Status")) {
								
							} */
						} else {
							logger.info("already updated by another");
						}
						
					}
					catch (Exception e) {
						logger.error(
						    "Event of type " + event.getEventType() + " for client " + event.getBaseEntityId() == null ? ""
						            : event.getBaseEntityId() + " failed to sync", e);
					}
				}
			}
		}
		catch (Exception e) {
			logger.error(format("Sync data processing failed with exception {0}.- ", e));
			return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(CREATED);
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
	public List<Event> search(HttpServletRequest request) throws ParseException {
		String clientId = getStringFilter("identifier", request);
		DateTime[] eventDate = getDateRangeFilter(EVENT_DATE, request);//TODO
		String eventType = getStringFilter(EVENT_TYPE, request);
		String location = getStringFilter(LOCATION_ID, request);
		String provider = getStringFilter(PROVIDER_ID, request);
		String entityType = getStringFilter(ENTITY_TYPE, request);
		DateTime[] lastEdit = getDateRangeFilter(LAST_UPDATE, request);
		String team = getStringFilter(TEAM, request);
		String teamId = getStringFilter(TEAM_ID, request);
		
		if (!StringUtils.isEmptyOrWhitespaceOnly(clientId)) {
			Client c = clientService.find(clientId);
			if (c == null) {
				return new ArrayList<>();
			}
			
			clientId = c.getBaseEntityId();
		}
		EventSearchBean eventSearchBean = new EventSearchBean();
		eventSearchBean.setBaseEntityId(clientId);
		eventSearchBean.setEventDateFrom(eventDate == null ? null : eventDate[0]);
		eventSearchBean.setEventDateTo(eventDate == null ? null : eventDate[1]);
		eventSearchBean.setEventType(eventType);
		eventSearchBean.setEntityType(entityType);
		eventSearchBean.setProviderId(provider);
		eventSearchBean.setLocationId(location);
		eventSearchBean.setLastEditFrom(lastEdit == null ? null : lastEdit[0]);
		eventSearchBean.setLastEditTo(lastEdit == null ? null : lastEdit[1]);
		eventSearchBean.setTeam(team);
		eventSearchBean.setTeamId(teamId);
		
		return eventService.findEventsBy(eventSearchBean);
	}
	
	@Override
	public List<Event> filter(String query) {
		return eventService.findEventsByDynamicQuery(query);
	}
	
}
