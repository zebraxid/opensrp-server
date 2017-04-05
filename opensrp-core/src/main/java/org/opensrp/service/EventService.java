package org.opensrp.service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.support.View;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.domain.ActivityLog;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.domain.Obs;
import org.opensrp.repository.AllActivityLogs;
import org.opensrp.repository.AllEvents;
import org.opensrp.util.DateTimeTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class EventService {

	private final AllEvents allEvents;
	private final AllActivityLogs allLogs;
	
	@Autowired
	public EventService(AllEvents allEvents, AllActivityLogs allLogs)
	{
		this.allEvents = allEvents;
		this.allLogs = allLogs;
	}
	
	public List<Event> findAllByIdentifier(String identifier) {
		return allEvents.findAllByIdentifier(identifier);
	}
	
	public List<Event> findByBaseEntityId(String baseEntityId) {
		return allEvents.findByBaseEntityId(baseEntityId);
	}
	
	public List<Event> findByFormSubmissionId(String formSubmissionId){
		return allEvents.findByFormSubmissionId(formSubmissionId);
	}
	
	public List<Event> findEventsByDynamicQuery(String query, String sort, Integer limit, Integer skip) {
		return allEvents.findEventsByDynamicQuery(query, sort, limit, skip);
	}
	
	public Event find(String uniqueId){
		if(allEvents.contains(uniqueId)){
			return allEvents.get(uniqueId);
		}
		
		List<Event> el = allEvents.findAllByIdentifier(uniqueId);
		if(el.size() > 1){
			throw new IllegalArgumentException("Multiple events with identifier "+uniqueId+" exist.");
		}
		else if(el.size() != 0){
			return el.get(0);
		}
		return null;
	}
	
	public List<Event> findAllByLocationOrProvider(String locationOrProvider, DateTime from, DateTime to) {
		return allEvents.findAllByLocationOrProvider(locationOrProvider, from, to);
	}
	
	public List<Event> findAllByEntityOrEventType(String entityOrEventType, DateTime from, DateTime to) {
		return allEvents.findAllByEntityOrEventType(entityOrEventType, from, to);
	}
	
	public List<Event> findAllByTimestamp(DateTime from, DateTime to) {
		return allEvents.findByTimestamp(from, to);
	}
	
	public Event find(Event event){		
		for (String idt : event.getIdentifiers().keySet()) {
			List<Event> el = allEvents.findAllByIdentifier(event.getIdentifier(idt));
			if(el.size() > 1){
				throw new IllegalArgumentException("Multiple events with identifier type "+idt+" and ID "+event.getIdentifier(idt)+" exist.");
			}
			else if(el.size() != 0){
				return el.get(0); 
			}
		}
		return null;
	}
	
	public synchronized Event addEvent(Event event, String activityCategory)
	{
		Event e = find(event);
		if(e != null){
			throw new IllegalArgumentException("An event already exists with given list of identifiers. Consider updating data.["+e+"]");
		}

		event.setDateCreated(DateTime.now());
		allEvents.add(event);
		
		allLogs.add(new ActivityLog("Event Added from Service", event.getId(), Event.class.getName(), event.getId(), "EVENT_ADD", activityCategory, null, DateTime.now(), null));

		return event;
	}
	
	public void updateEvent(Event updatedEvent, String activityCategory)
	{
		// If update is on original entity
		if(updatedEvent.isNew()){
			throw new IllegalArgumentException("Event to be updated is not an existing and persisting domain object. Update database object instead of new pojo");
		}
		
		updatedEvent.setDateEdited(DateTime.now());
				
		allLogs.add(new ActivityLog("Event Updated from Service", updatedEvent.getId(), Event.class.getName(), updatedEvent.getId(), "EVENT_EDITED", activityCategory, null, DateTime.now(), null));

		allEvents.update(updatedEvent);					
	}
	
	//TODO Review and add test cases as well
	public Event mergeEvent(Event updatedEvent, String activityCategory) 
	{
		try{
			Event original = find(updatedEvent);
			if(original == null){
				throw new IllegalArgumentException("No event found with given list of identifiers. Consider adding new!");
			}
			
			Gson gs = new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();
			JSONObject originalJo = new JSONObject(gs.toJson(original));
	
			JSONObject updatedJo = new JSONObject(gs.toJson(updatedEvent));
			List<Field> fn = Arrays.asList(Event.class.getDeclaredFields());
	
			JSONObject mergedJson = new JSONObject();
			if (originalJo.length() > 0) {
				mergedJson = new JSONObject(originalJo, JSONObject.getNames(originalJo));
			}
			if (updatedJo.length() > 0) {
				for (Field key : fn) {
					String jokey = key.getName();
					if(updatedJo.has(jokey)) mergedJson.put(jokey, updatedJo.get(jokey));
				}
			
				original = gs.fromJson(mergedJson.toString(), Event.class);
				
				for (Obs o : updatedEvent.getObs()) {
					// TODO handle parent
					if(original.getObs(null, o.getFieldCode()) == null) {
						original.addObs(o);
					}
					else {
						original.getObs(null, o.getFieldCode()).setComments(o.getComments());
						original.getObs(null, o.getFieldCode()).setEffectiveDatetime(o.getEffectiveDatetime());
						original.getObs(null, o.getFieldCode()).setValues(o.getValues(false), o.getValues(true));
					}
				}
				for (String k : updatedEvent.getIdentifiers().keySet()) {
					original.addIdentifier(k, updatedEvent.getIdentifier(k));
				}
			}
	
			original.setDateEdited(DateTime.now());
			allEvents.update(original);

			allLogs.add(new ActivityLog("Event Merged from Service", original.getId(), Event.class.getName(), original.getId(), "EVENT_MERGED", activityCategory, null, DateTime.now(), null));

			return original;
		}
		catch(JSONException e){
			throw new RuntimeException(e);
		}
	}

}
