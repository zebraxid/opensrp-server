package org.opensrp.web.rest.rapid;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.Weeks;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.service.ClientService;
import org.opensrp.service.EventService;
import org.opensrp.util.Utils;
import org.opensrp.web.controller.ANMLocationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest/rapid/client")
public class ANCClientResource {
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	EventService eventService;
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final String ANC_CONCEPT = "162942AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	
	private static final String ANC_CONCEPT_VALUE = "1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	
	private static final String ANC_LMP_CONCEPT = "1427AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	
	private static final int PREGNANCY_PERIOD=280;
	
	private static Logger logger = LoggerFactory.getLogger(ANMLocationController.class.toString());
	
	@RequestMapping("/anc")
	@ResponseBody
	public Map<String, Object> getAncClient(HttpServletRequest req) {
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			String id = req.getParameter("id");
			logger.debug("ANC client id " + id);
			Client client = clientService.find(id);
			if (client == null) {
				m.put("found", false);
				return m;
			}
			
			Date now = new Date();
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			cal.add(Calendar.DATE, 1);
			Date dateTo = cal.getTime();
			String strDateTo = dateFormat.format(dateTo);
			cal = Calendar.getInstance();
			cal.setTime(now);
			cal.add(Calendar.DATE, -315);
			Date dateFrom = cal.getTime();
			String strDateFrom = dateFormat.format(dateFrom);
			
			List<Event> events = eventService.findByClientAndConceptAndDate(client.getBaseEntityId(), ANC_CONCEPT,
			    ANC_CONCEPT_VALUE, strDateFrom, strDateTo);
			if (events != null && !events.isEmpty()) {
				Date lmp = getLmp(events.get(0));
				List<Event> ancEvents = eventService.findByEventTypeAndDate(client.getBaseEntityId(), "ANC Reminder Visit",
				    new DateTime(dateFrom), new DateTime(dateTo));
				// sort the events in descending order to only get the latest
				Collections.sort(ancEvents, new Comparator<Event>() {
					
					@Override
					public int compare(Event lhs, Event rhs) {
						// -1 - less than, 1 - greater than, 0 - equal, all
						// inversed for descending
						return lhs.getVersion() > rhs.getVersion() ? -1 : (lhs.getVersion() < rhs.getVersion()) ? 1 : 0;
					}
				});
				
				m.put("ancCard", createAncCard(lmp, ancEvents));
				int age = Weeks.weeksBetween(new DateTime(lmp), DateTime.now()).getWeeks();
				m.put("ga", age);
				cal.clear();
				cal.setTime(lmp);
				cal.add(Calendar.DATE, PREGNANCY_PERIOD);
				m.put("edd", dateFormat.format(cal.getTime()));
				
			}
			
			m.put("found", true);
			m.put("client", client);
			int age = Weeks.weeksBetween(client.getBirthdate(), DateTime.now()).getWeeks();
			m.put("age", age);
			
		}
		catch (Exception e) {
			logger.error("", e);
		}
		return m;
	}
	
	/**
	 * Get anc visits dates and due dates for the upcoming visits
	 * 
	 * @param lmp last menustrual date from the psrf event
	 * @param ancEvents
	 * @return
	 */
	private Map<String, String> createAncCard(Date lmp, List<Event> ancEvents) {
		Map<String, String> ancVisits = new HashMap<String, String>();
		if (ancEvents != null && !ancEvents.isEmpty()) {
			for (Event event : ancEvents) {
				String visit = ANCVISIT.get(event.getEventType()).toString();
				if (!ancVisits.containsKey(visit)) {
					ancVisits.put(visit, dateFormat.format(event.getEventDate().toDate()));
				}
			}
		}
		// if size is 4 means all the visits have been done else find out the
		// due dates
		if (ancVisits.size() < 4) {
			for (String visitName : ANCVISIT.names()) {
				if (!ancVisits.containsKey(visitName)) {
					// anc visit missing or not done, get due date based on lmp
					int days = ANCVISITDUEDAY.get(visitName);
					Calendar cal = Calendar.getInstance();
					cal.setTime(lmp);
					cal.add(Calendar.DATE, days);
					if (cal.getTime().before(new Date())) {
						ancVisits.put(visitName, "due");
					} else {
						ancVisits.put(visitName, dateFormat.format(cal.getTime()));
					}
					
				}
			}
		}
		
		return ancVisits;
	}
	
	enum ANCVISIT {
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
		
		public static ANCVISIT get(String value) {
			// the reverse lookup by simply getting
			// the value from the lookup HashMap.
			return lookup.get(value);
		}
		
		public static List<String> names() {
			
			List<String> list = new ArrayList<String>();
			for (ANCVISIT s : ANCVISIT.values()) {
				list.add(s.name());
			}
			
			return list;
		}
	}
	
	enum ANCVISITDUEDAY {
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
	
	private Date getLmp(Event event) throws Exception {
		Map<String, Object> obs = Utils.getEventObs(Utils.eventToJson(event));
		if (obs == null || (obs != null && !obs.containsKey(ANC_LMP_CONCEPT))) {
			return null;
		}
		String lmp = obs.get(ANC_LMP_CONCEPT).toString();
		return dateFormat.parse(lmp);
		
	}
}
