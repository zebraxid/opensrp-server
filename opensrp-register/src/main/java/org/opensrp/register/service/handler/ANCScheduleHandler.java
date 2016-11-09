package org.opensrp.register.service.handler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.opensrp.connector.rapidpro.RapidProService;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.register.service.scheduling.AnteNatalCareSchedulesService;
import org.opensrp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ANCScheduleHandler extends BaseScheduleHandler {
	
	@Autowired
	private AnteNatalCareSchedulesService ancScheduleService;
	
	@Autowired
	RapidProService rapidProService;
	
	@Autowired
	ClientService clientService;
	
	private static final String scheduleName = "Ante Natal Care Reminder Visit";
	
	@Override
	public void handle(Event event, JSONObject scheduleConfigEvent) {
		try {
			
			if (evaluateEvent(event, scheduleConfigEvent)) {
				String action = getAction(scheduleConfigEvent);
				if (action.equalsIgnoreCase(ActionType.enroll.toString())) {
					String refDate = getReferenceDateForSchedule(event, scheduleConfigEvent, action);
					if (!refDate.isEmpty())
						ancScheduleService.enrollMother(event.getBaseEntityId(), scheduleName, LocalDate.parse(refDate),
						    event.getId());
					addMotherContactToRapidPro(event);
				} else if (action.equalsIgnoreCase(ActionType.fulfill.toString())) {
					ancScheduleService.fullfillMilestone(event.getBaseEntityId(), event.getProviderId(), scheduleName,
					    LocalDate.parse(getReferenceDateForSchedule(event, scheduleConfigEvent, action)), event.getId());
				}
			}
			
		}
		
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * https://github.com/OpenSRP/opensrp-server/issues/160
	 * 
	 * @param event This method creates a woman contact in rapidpro if it doesn't exist and adds the
	 *            woman to the pregnant women group. Only event is required since only PSRF event
	 *            enrolls a woman into the ANC schedule
	 */
	private void addMotherContactToRapidPro(Event event) throws Exception {
		String groupName = "Pregnant Women";
		Client client = clientService.find(event.getBaseEntityId());
		if (client.getAttributes().containsKey(RapidProService.RAPIDPRO_GROUPS)
		        && client.getAttributes().get(RapidProService.RAPIDPRO_GROUPS).toString().equalsIgnoreCase(groupName)) {
			return;// this mother is already in the pregnant women 
		}
		
		String phoneNo = getConceptValue(event, "159635AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (phoneNo != null && !phoneNo.isEmpty()) {
			String lmp = getConceptValue(event, "1427AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			Map<String, Object> contact = new HashMap<String, Object>();
			Map<String, Object> fields = new HashMap<String, Object>();
			
			fields.put("lmp", lmp);
			fields.putAll(createAncCard(lmp));
			contact.put("name", client.getFirstName());
			List<String> urns = new ArrayList<String>();
			List<String> groups = new ArrayList<String>();
			groups.add(groupName);
			urns.add("tel:" + phoneNo);
			contact.put("urns", urns);
			contact.put("groups", groups);
			contact.put("fields", fields);
			String rapidProContact = rapidProService.createContact(contact);
			JSONObject jsonObject = new JSONObject(rapidProContact);
			
			if (jsonObject.has("uuid")) {
				
				client.getIdentifiers().put(RapidProService.RAPIDPRO_UUID_IDENTIFIER_TYPE, jsonObject.getString("uuid"));
				JSONArray rapidproGroups = jsonObject.getJSONArray("groups");
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < rapidproGroups.length(); i++) {
					list.add(rapidproGroups.getString(i));
				}
				//			if(client.getAttributes().containsKey(RapidProService.RAPIDPRO_GROUPS)){
				//				list.addAll((Collection<? extends String>) client.getAttributes().get(RapidProService.RAPIDPRO_GROUPS));
				//			}
				//FIXME attributes not accepting list as value
				client.getAttributes().put(RapidProService.RAPIDPRO_GROUPS, list.get(0));
				
				clientService.updateClient(client);
			}
		}
		
	}
	
	/**
	 * Get anc visits dates and due dates for the upcoming visits
	 * 
	 * @param lmp last menustrual date from the psrf event
	 * @param ancEvents
	 * @return
	 */
	private Map<String, Object> createAncCard(String lmp) throws Exception {
		Map<String, Object> ancVisits = new HashMap<String, Object>();
		
		// if size is 4 means all the visits have been done else find out the
		// due dates for the rest of the visits
		if (ancVisits.size() < 4) {
			for (String visitName : ANCVISIT.names()) {
				if (!ancVisits.containsKey(visitName)) {
					// anc visit missing or not done, get due date based on lmp
					int days = ANCVISITDUEDAY.get(visitName);
					Calendar cal = Calendar.getInstance();
					cal.setTime(dateFormat.parse(lmp));
					cal.add(Calendar.DATE, days);
					ancVisits.put(visitName.toLowerCase(), dateFormat.format(cal.getTime()));
					
				}
			}
		}
		
		return ancVisits;
	}
	
	private static enum ANCVISIT {
		ANC1("ANC Reminder Visit 1"), ANC2("ANC Reminder Visit 2"), ANC3("ANC Reminder Visit 3"), ANC4(
		        "ANC Reminder Visit 4");
		
		String value;
		
		private ANCVISIT(String s) {
			value = s;
		}
		
		public String getAncVisitValue() {
			return value;
		}
		
		private static final Map<String, ANCVISIT> lookup = new HashMap<String, ANCVISIT>();
		static {
			// Create reverse lookup hash map
			for (ANCVISIT d : ANCVISIT.values())
				lookup.put(d.getAncVisitValue(), d);
		}
		
		public static List<String> names() {
			
			List<String> list = new ArrayList<String>();
			for (ANCVISIT s : ANCVISIT.values()) {
				list.add(s.name());
			}
			
			return list;
		}
	}
	
	private static enum ANCVISITDUEDAY {
		ANC1(56), ANC2(168), ANC3(224), ANC4(252);
		
		int value;
		
		private ANCVISITDUEDAY(int days) {
			value = days;
		}
		
		public int getAncVisitDueDayValue() {
			return value;
		}
		
		private static final Map<String, Integer> lookup = new HashMap<String, Integer>();
		static {
			// Create reverse lookup hash map
			for (ANCVISITDUEDAY d : ANCVISITDUEDAY.values())
				lookup.put(d.toString(), d.getAncVisitDueDayValue());
		}
		
		public static int get(String key) {
			// the reverse lookup by simply getting
			// the value from the lookup HashMap.
			return lookup.get(key);
		}
	}
}
