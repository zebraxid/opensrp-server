package org.opensrp.web.rest.rapid;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
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
public class BNFClientResource {
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	EventService eventService;
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final String PREGNANCY_OUTCOME_EVENT = "Birth Notification Followup form";
	
	private static final String CHILD_VITAL_STATUS_EVENT = "Child Vital Status";
	
	private static final String DEFAULT_FIELDTYPE = "concept";
	
	private static final String DEFAULT_FIELD_DATA_TYPE = "text";
	
	private static Logger logger = LoggerFactory.getLogger(ANMLocationController.class.toString());
	
	@RequestMapping(value = "/pregnancyoutcome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateAncVisit(HttpServletRequest req) {
		Map<String, String> resp = new HashMap<>();
		String id = req.getParameter("clientId");
		String location = req.getParameter("location");
		String doo = req.getParameter("date-of-outcome");
		String outcome = req.getParameter("outcome");
		
		try {
			if (StringUtils.isEmptyOrWhitespaceOnly(outcome)) {
				resp.put("ERROR", "ANC visit MUST be specified.");
				return resp;
			}
			if (StringUtils.isEmptyOrWhitespaceOnly(doo)) {
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
			String entityType = RestUtils.ENTITYTYPES.MCAREMOTHER.toString().toLowerCase();
			Event e = new Event(c.getBaseEntityId(), PREGNANCY_OUTCOME_EVENT, new DateTime(), entityType, "demo1", location,
			        System.currentTimeMillis() + "");
			List<Object> values = new ArrayList<>();
			
			if (StringUtils.isEmptyOrWhitespaceOnly(doo)) {
				values.clear();
				values.add(doo);
				e.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.DATEOFCONFINEMENT.toString()).toString(), null, values, "",
				        null));
			}
			if (StringUtils.isEmptyOrWhitespaceOnly(outcome)) {
				values.clear();
				//TODO values.add(diastolicbp);
				e.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.DIASTOLIC.toString()).toString(), null, values, "",
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
	
	@RequestMapping(value = "/birthregistration", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> birthRegistration(HttpServletRequest req) {
		Map<String, String> resp = new HashMap<>();
		String motherId = req.getParameter("clientId");
		String location = req.getParameter("location");
		String childname = req.getParameter("childname");
		String gender = req.getParameter("gender");
		String doo = req.getParameter("doo");
		
		try {
			if (StringUtils.isEmptyOrWhitespaceOnly(childname)) {
				resp.put("ERROR", "Child Name MUST be specified.");
				return resp;
			}
			if (StringUtils.isEmptyOrWhitespaceOnly(gender)) {
				resp.put("ERROR", "date MUST be specified.");
				return resp;
			}
			if (StringUtils.isEmptyOrWhitespaceOnly(motherId)) {
				resp.put("ERROR", "Mother ID MUST be specified.");
				return resp;
			}
			Client mother = clientService.find(motherId);
			if (mother == null) {
				resp.put("ERROR", "Mother ID Not found");
				return resp;
			}
			String entityType = RestUtils.ENTITYTYPES.MCAREMOTHER.toString().toLowerCase();
			
			//Create child client doc
			Client child = new Client(UUID.randomUUID().toString());
			child.setDateCreated(new DateTime());
			Map<String, List<String>> relationships= new HashMap<String, List<String>>();
			List<String> relationalIds= new ArrayList<String>();
			relationalIds.add(mother.getBaseEntityId());
			relationships.put("moter", relationalIds);
			child.setRelationships(relationships);
			if (doo != null) {
				child.setBirthdate(new DateTime(doo));
			}
			child.setGender(gender);
			child=clientService.addClient(child);
			
			Event e = new Event(child.getBaseEntityId(), CHILD_VITAL_STATUS_EVENT, new DateTime(), entityType, "demo1", location,
			        System.currentTimeMillis() + "");
			List<Object> values = new ArrayList<>();
			
			if (StringUtils.isEmptyOrWhitespaceOnly(childname)) {
				values.clear();
				values.add(childname);
				e.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.CHILDNAME.toString()).toString(), null, values, "",
				        null));
			}
			if (StringUtils.isEmptyOrWhitespaceOnly(gender)) {
				
				if (gender.equalsIgnoreCase("1") || gender.equalsIgnoreCase("female")) {
					gender = RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.FEMALEGENDER.toString()).toString();
				} else if (gender.equalsIgnoreCase("2") || gender.equalsIgnoreCase("male")) {
					gender = RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.MALEGENDER.toString()).toString();
					
				} else {
					gender = RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.UNKNOWNGENDER.toString()).toString();
					
				}
				
				values.clear();
				values.add(gender);
				e.addObs(new Obs(DEFAULT_FIELDTYPE, DEFAULT_FIELD_DATA_TYPE,
				        RestUtils.CONCEPTS.valueOf(RestUtils.CONCEPTS.CHILDGENDER.toString()).toString(), null, values, "",
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
	
}
