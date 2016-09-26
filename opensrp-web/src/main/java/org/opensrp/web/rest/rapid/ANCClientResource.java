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
import org.opensrp.domain.Obs;
import org.opensrp.service.ClientService;
import org.opensrp.service.EventService;
import org.opensrp.util.Utils;
import org.opensrp.web.controller.ANMLocationController;
import org.opensrp.web.rest.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.StringUtils;

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
	
	private static final int PREGNANCY_PERIOD = 259;//37 weeks
	
	private static final String DEFAULT_FIELDTYPE = "concept";
	
	private static final String DEFAULT_FIELD_DATA_TYPE = "text";
	
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
	
	@RequestMapping(value = "/ancvisit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateAncVisit(HttpServletRequest req) {
		Map<String, String> resp = new HashMap<>();
		String id = req.getParameter("clientId");
		String location = req.getParameter("location");
		String date = req.getParameter("date");
		String ancvisit = req.getParameter("anc");
		String systolicbp = req.getParameter("sbp");
		String diastolicbp = req.getParameter("dbp");
		String temperature = req.getParameter("temp");
		String pulserate = req.getParameter("pulse");
		String weight = req.getParameter("weight");
		String pallor = req.getParameter("pallor");
		String swelling = req.getParameter("swelling");
		String bleeding = req.getParameter("bleeding");
		String jaundice = req.getParameter("jaundice");
		String fits = req.getParameter("fits");
		String eventType = "";
		
		try {
			if (StringUtils.isEmptyOrWhitespaceOnly(ancvisit)) {
				resp.put("ERROR", "ANC visit MUST be specified.");
				return resp;
			}
			if (StringUtils.isEmptyOrWhitespaceOnly(date)) {
				resp.put("ERROR", "date MUST be specified.");
				return resp;
			}
			if (StringUtils.isEmptyOrWhitespaceOnly(id)) {
				resp.put("ERROR", "clientId MUST be specified.");
				return resp;
			}
			Client c = clientService.find(id);
			if (c == null) {
				resp.put("ERROR", "ID Not found");
				return resp;
			}
			eventType = RestUtils.ANCVISIT.getValue(ancvisit);
			String entityType = RestUtils.ENTITYTYPES.MCAREMOTHER.toString().toLowerCase();
			Event e = new Event(c.getBaseEntityId(), eventType, new DateTime(), entityType, "demo1", location,
			        System.currentTimeMillis() + "");
			List<Object> values = new ArrayList<>();
			
			if (StringUtils.isEmptyOrWhitespaceOnly(systolicbp)) {
				values.clear();
				values.add(systolicbp);
				e.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.SYSTOLIC.toString()).toString(), null, values, "",
				        null));
			}
			if (StringUtils.isEmptyOrWhitespaceOnly(diastolicbp)) {
				values.clear();
				values.add(diastolicbp);
				e.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.DIASTOLIC.toString()).toString(), null, values, "",
				        null));
			}
			if (StringUtils.isEmptyOrWhitespaceOnly(temperature)) {
				values.clear();
				values.add(temperature);
				e.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.TEMPERATURE.toString()).toString(), null, values, "",
				        null));
			}
			if (StringUtils.isEmptyOrWhitespaceOnly(pulserate)) {
				values.clear();
				values.add(pulserate);
				e.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.PULSE.toString()).toString(), null, values, "", null));
			}
			if (StringUtils.isEmptyOrWhitespaceOnly(weight)) {
				values.clear();
				values.add(weight);
				e.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.WEIGHT.toString()).toString(), null, values, "",
				        null));
			}
			//TODO QUESTION/ANSWER CONCEPT
			if (StringUtils.isEmptyOrWhitespaceOnly(pallor)) {
				values.clear();
				values.add(pallor);
				e.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.PALLOR.toString()).toString(), null, values, "",
				        null));
			}
			//TODO QUESTION/ANSWER CONCEPT
			if (StringUtils.isEmptyOrWhitespaceOnly(swelling)) {
				values.clear();
				values.add(swelling);
				e.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.SWELLING.toString()).toString(), null, values, "",
				        null));
			}
			//TODO QUESTION/ANSWER CONCEPT
			if (StringUtils.isEmptyOrWhitespaceOnly(bleeding)) {
				values.clear();
				values.add(bleeding);
				e.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.BATHCANALBLEEDING.toString()).toString(), null, values,
				        "", null));
			}
			//TODO QUESTION/ANSWER CONCEPT
			if (StringUtils.isEmptyOrWhitespaceOnly(jaundice)) {
				values.clear();
				values.add(jaundice);
				e.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.JAUNDICE.toString()).toString(), null, values, "",
				        null));
			}
			//TODO QUESTION/ANSWER CONCEPT
			if (StringUtils.isEmptyOrWhitespaceOnly(fits)) {
				values.clear();
				values.add(fits);
				e.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.CONVULSIONS.toString()).toString(), null, values, "",
				        null));
			}
			
			eventService.addEvent(e);
			resp.put("success", Boolean.toString(true));
			return resp;
		}
		catch (Exception e) {
			logger.error("", e);
			resp.put("ERROR", "Unable to complete request");
			return resp;
		}
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
				String visit = RestUtils.ANCVISIT.get(event.getEventType()).toString();
				if (!ancVisits.containsKey(visit)) {
					ancVisits.put(visit, dateFormat.format(event.getEventDate().toDate()));
				}
			}
		}
		// if size is 4 means all the visits have been done else find out the
		// due dates
		if (ancVisits.size() < 4) {
			for (String visitName : RestUtils.ANCVISIT.names()) {
				if (!ancVisits.containsKey(visitName)) {
					// anc visit missing or not done, get due date based on lmp
					int days = RestUtils.ANCVISITDUEDAY.get(visitName);
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
	
	private Date getLmp(Event event) throws Exception {
		Map<String, Object> obs = Utils.getEventObs(Utils.eventToJson(event));
		if (obs == null || (obs != null && !obs.containsKey(ANC_LMP_CONCEPT))) {
			return null;
		}
		String lmp = obs.get(ANC_LMP_CONCEPT).toString();
		return dateFormat.parse(lmp);
		
	}
}
