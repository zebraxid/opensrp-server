package org.opensrp.web.rest.rapid;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.joda.time.Years;
import org.opensrp.common.FormEntityConstants;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.domain.Obs;
import org.opensrp.dto.AlertStatus;
import org.opensrp.scheduler.service.ActionService;
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
	
	@Autowired
	ActionService actionService;
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final String SELF_REPORTED_PREGNANCY_CONCEPT = "162942AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	
	private static final String SELF_REPORTED_PREGNANCY_CONCEPT_VALUE = "1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	
	private static final String ANC_LMP_CONCEPT = "1427AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	
	private static final int PREGNANCY_PERIOD = 259;//37 weeks
	
	private static final String DEFAULT_FIELDTYPE = "concept";
	
	private static final String DEFAULT_FIELD_DATA_TYPE = "text";
	private static final String DUE = "due";
	
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
			
			List<Event> events = eventService.findByClientAndConceptAndDate(client.getBaseEntityId(),
			    SELF_REPORTED_PREGNANCY_CONCEPT, SELF_REPORTED_PREGNANCY_CONCEPT_VALUE, strDateFrom, strDateTo);
			if (events != null && !events.isEmpty()) {
				Date lmp = getLmp(events.get(0));
				List<Event> ancEvents = eventService.findByEventTypeAndEventDate(client.getBaseEntityId(),
				    "ANC Reminder Visit", new DateTime(dateFrom), new DateTime(dateTo));
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
				m.put("ga", age + " Wks");
				cal.clear();
				cal.setTime(lmp);
				cal.add(Calendar.DATE, PREGNANCY_PERIOD);
				m.put("edd", dateFormat.format(cal.getTime()));
				
			} else {
				// Woman not pregnant
				m.put("found", false);
				m.put("ERROR", "Woman found but not pregnant :)");
				return m;
			}
			
			m.put("found", true);
			m.put("client", client);
			int age = Years.yearsBetween(client.getBirthdate(), DateTime.now()).getYears();
			m.put("age", age + " yrs");
			
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
		String ancVisit = req.getParameter("anc");
		String systolicbp = req.getParameter("sbp");
		String diastolicbp = req.getParameter("dbp");
		String temperature = req.getParameter("temp");
		String pulserate = req.getParameter("pulse");
		String weight = req.getParameter("weight");
		String rsymptoms = req.getParameter("rsymptoms");
		//		String pallor = req.getParameter("pallor");
		//		String swelling = req.getParameter("swelling");
		//		String bleeding = req.getParameter("bleeding");
		//		String jaundice = req.getParameter("jaundice");
		//		String fits = req.getParameter("fits");
		String eventType = "";
		
		try {
			if (StringUtils.isEmptyOrWhitespaceOnly(ancVisit)) {
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
			Client client = clientService.find(id);
			if (client == null) {
				resp.put("ERROR", "ID Not found");
				return resp;
			}
			
			String entityType = RestUtils.ENTITYTYPES.MCAREMOTHER.toString().toLowerCase();
			Event event = new Event(client.getBaseEntityId(), null, new DateTime(), entityType, "demo1", location,
			        "Aleena" + FormEntityConstants.FORM_DATE.format(new Date()));
			event.setDateCreated(new DateTime());
			List<Object> values = new ArrayList<>();
			
			ancVisit = addAncVisit(event, ancVisit);
			eventType = RestUtils.ANCVISIT.getValue(ancVisit);
			event.setEventType(eventType);
			
			List<String> riskSymptoms = new ArrayList<String>();
			if (!StringUtils.isEmptyOrWhitespaceOnly(rsymptoms)) {
				rsymptoms=rsymptoms.startsWith("/")?rsymptoms.replaceAll("/", ""):rsymptoms.trim();
				String[] risksymptomsArray = rsymptoms.split(" ");
				riskSymptoms.addAll(Arrays.asList(risksymptomsArray));
				addRiskSymptoms(event, riskSymptoms);
			}
			if (!StringUtils.isEmptyOrWhitespaceOnly(systolicbp)) {
				values.clear();
				values.add(systolicbp);
				event.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.SYSTOLIC.toString()).toString(), null, values, "", null));
			}
			if (!StringUtils.isEmptyOrWhitespaceOnly(diastolicbp)) {
				values.clear();
				values.add(diastolicbp);
				event.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.DIASTOLIC.toString()).toString(), null, values, "", null));
			}
			if (!StringUtils.isEmptyOrWhitespaceOnly(temperature)) {
				values.clear();
				values.add(temperature);
				event.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.TEMPERATURE.toString()).toString(), null, values, "",
				        null));
			}
			if (!StringUtils.isEmptyOrWhitespaceOnly(pulserate)) {
				values.clear();
				values.add(pulserate);
				event.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.PULSE.toString()).toString(), null, values, "", null));
			}
			if (!StringUtils.isEmptyOrWhitespaceOnly(weight)) {
				values.clear();
				values.add(weight);
				event.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.WEIGHT.toString()).toString(), null, values, "", null));
			}
			
			eventService.addEvent(event);
			//create an action for the visit to reflect in the client android app since the app relies on actions
			//status has been set to upcoming for simplicity purposes since client for now checks for the fwanc*date and the status to display the alerts colors
			actionService.alertForBeneficiary("elco", client.getBaseEntityId(), "demo1", "Ante Natal Care Reminder Visit",
			    RestUtils.ANCMILESTONE.get(ancVisit), AlertStatus.complete, new DateTime(), new DateTime());
			
			resp.put("success", Boolean.toString(true));
			return resp;
		}
		catch (
		
		Exception e) {
			logger.error("", e);
			resp.put("ERROR", "Unable to complete request");
			return resp;
		}
	}
	
	private String addAncVisit(Event event, String ancVisit) {
		//this should be the way to go for all rapidpro variables
		if (ancVisit.startsWith("/")) {
			ancVisit = ancVisit.replace("/", "");
		}
		List<String> values = new ArrayList<String>();
		values.add(FormEntityConstants.FORM_DATE.format(new Date()));
		//FWANC1DATE
		if (ancVisit.equalsIgnoreCase("1") || ancVisit.equalsIgnoreCase("anc1")) {
			
			event.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
			        RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.ANCDATE.toString()).toString(), null, values, "",
			        "FWANC1DATE"));
			ancVisit = "anc1";
		} else if (ancVisit.equalsIgnoreCase("2") || ancVisit.equalsIgnoreCase("anc2")
		        || ancVisit.equalsIgnoreCase("/anc2")) {
			event.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
			        RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.ANCDATE.toString()).toString(), null, values, "",
			        "FWANC2DATE"));
			ancVisit = "anc2";
		} else if (ancVisit.equalsIgnoreCase("3") || ancVisit.equalsIgnoreCase("anc3")
		        || ancVisit.equalsIgnoreCase("/anc3")) {
			event.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
			        RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.ANCDATE.toString()).toString(), null, values, "",
			        "FWANC3DATE"));
			ancVisit = "anc3";
		} else if (ancVisit.equalsIgnoreCase("4") || ancVisit.equalsIgnoreCase("anc4")
		        || ancVisit.equalsIgnoreCase("/anc4")) {
			event.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
			        RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.ANCDATE.toString()).toString(), null, values, "",
			        "FWANC4DATE"));
			ancVisit = "anc4";
		}
		// return a normalized value in a way other methods would process without having to involve so many ifs
		return ancVisit;
	}
	
	private void addRiskSymptoms(Event event, List<String> riskSymptoms) {
		List<String> values = new ArrayList<String>();
		String pallor;
		if (riskSymptoms.contains(RestUtils.ANCRISKSYMPTOMS.get(RestUtils.ANCRISKSYMPTOMS.PALLOR.toString()).toString())) {
			pallor = RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.YES.toString()).toString();
		} else {
			pallor = RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.NO.toString()).toString();
		}
		
		values.clear();
		values.add(pallor);
		event.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
		        RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.PALLOR.toString()).toString(), null, values, "", null));
		
		String swelling;
		
		if (riskSymptoms.contains(RestUtils.ANCRISKSYMPTOMS.get(RestUtils.ANCRISKSYMPTOMS.SWELLING.toString()).toString())) {
			swelling = RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.YES.toString()).toString();
		} else {
			swelling = RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.NO.toString()).toString();
		}
		values.clear();
		values.add(swelling);
		event.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
		        RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.SWELLING.toString()).toString(), null, values, "", null));
		
		String bleeding;
		if (riskSymptoms.contains(RestUtils.ANCRISKSYMPTOMS.get(RestUtils.ANCRISKSYMPTOMS.BLEEDING.toString()).toString())) {
			bleeding = RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.YES.toString()).toString();
		} else {
			bleeding = RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.NO.toString()).toString();
		}
		values.clear();
		values.add(bleeding);
		event.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
		        RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.BATHCANALBLEEDING.toString()).toString(), null, values, "", null));
		
		String jaundice;
		if (riskSymptoms.contains(RestUtils.ANCRISKSYMPTOMS.get(RestUtils.ANCRISKSYMPTOMS.JAUNDICE.toString()).toString())) {
			jaundice = RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.YES.toString()).toString();
		} else {
			jaundice = RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.NO.toString()).toString();
		}
		values.clear();
		values.add(jaundice);
		event.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
		        RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.JAUNDICE.toString()).toString(), null, values, "", null));
		
		String fits;
		if (riskSymptoms.contains(RestUtils.ANCRISKSYMPTOMS.get(RestUtils.ANCRISKSYMPTOMS.FITS.toString()).toString())) {
			fits = RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.YES.toString()).toString();
		} else {
			fits = RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.NO.toString()).toString();
		}
		
		values.clear();
		values.add(fits);
		event.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
		        RestUtils.CONCEPTS.get(RestUtils.CONCEPTS.CONVULSIONS.toString()).toString(), null, values, "", null));
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
		// due dates for the rest of the visits
		if (ancVisits.size() < 4) {
			for (String visitName : RestUtils.ANCVISIT.names()) {
				if (!ancVisits.containsKey(visitName)) {
					// anc visit missing or not done, get due date based on lmp
					int days = RestUtils.ANCVISITDUEDAY.get(visitName);
					Calendar cal = Calendar.getInstance();
					cal.setTime(lmp);
					cal.add(Calendar.DATE, days);
					if (cal.getTime().before(new Date())) {
						ancVisits.put(visitName, DUE);
					} else {
						// if none is due so far make the current one due
						if(!ancVisits.containsValue(DUE)){
							ancVisits.put(visitName,DUE);
						}else{
							//dateFormat.format(cal.getTime())
							ancVisits.put(visitName,"NA" );
						}
						
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
