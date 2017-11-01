package org.opensrp.connector.dhis2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.domain.Obs;
import org.opensrp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseholdTracker extends DHIS2Service implements DHIS2Tracker {
	
	@Autowired
	private DHIS2TrackerService dhis2TrackerService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private DHIS2Connector dhis2Connector;
	
	public HouseholdTracker() {
		
	}
	
	public HouseholdTracker(String dhis2Url, String user, String password) {
		super(dhis2Url, user, password);
	}
	
	@Override
	public JSONArray getTrackCaptureData(Client client) throws JSONException {
		JSONObject clientData = new JSONObject();
		String firstName = "firstName";
		String lastName = "lastName";
		String attributeKey = "attribute";
		String valueKey = "value";
		JSONArray generateTrackCaptureData = new JSONArray();
		Map<String, Object> attributes = new HashMap<>();
		attributes = client.getAttributes();
		JSONObject attributesAsJson = new JSONObject(attributes);
		JSONObject clientAsJson = new JSONObject(client);
		
		// firstName
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson, DHIS2Settings.HOUSEHOLDIDMAPPING
		        .get(firstName).toString(), firstName));
		// LastName 
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson, DHIS2Settings.HOUSEHOLDIDMAPPING
		        .get(lastName).toString(), lastName));
		//Gender
		/*generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get("gender").toString(), "gender"));*/
		//"householdCode/Household ID
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(attributesAsJson,
		    DHIS2Settings.HOUSEHOLDIDMAPPING.get("Household_ID").toString(), "householdCode"));
		//birthdate		
		JSONObject data = new JSONObject();
		data.put(attributeKey, DHIS2Settings.HOUSEHOLDIDMAPPING.get("birthdate").toString());
		data.put(valueKey, client.getBirthdate());
		generateTrackCaptureData.put(data);
		
		/***** get information form Event ******/
		List<Event> event = eventService.findByBaseEntityAndType(client.getBaseEntityId(), "Household Registration");
		List<Obs> observations = event.get(0).getObs();
		/**** Member_Registration_No /Date_Of_Reg ***/
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureDataFromEventByValues(observations,
		    DHIS2Settings.MOTHERIDMAPPING.get("registration_Date").toString(), "Date_Of_Reg"));
		clientData.put("attributes", generateTrackCaptureData);
		System.err.println("HHData:" + clientData.toString());
		return generateTrackCaptureData;
	}
	
	@Override
	public JSONObject sendTrackCaptureData(JSONArray attributes) throws JSONException {
		String orgUnit = "IDc0HEyjhvL";
		String program = "OprRhyWVIM6";
		String trackedEntity = "MCPQUTHX1Ze";
		JSONObject clientData = new JSONObject();
		
		//JSONArray enrollments = new JSONArray();
		//JSONObject enrollmentsObj = new JSONObject();
		//enrollmentsObj.put("orgUnit", orgUnit);
		//enrollmentsObj.put("program", program);
		/*enrollmentsObj.put("enrollmentDate", DateUtil.getTodayAsString());
		enrollmentsObj.put("incidentDate", DateUtil.getTodayAsString());*/
		//enrollments.put(enrollmentsObj);
		//clientData.put("enrollments", enrollments);		
		dhis2Connector.setAttributes(attributes);
		dhis2Connector.setOrgUnit(orgUnit);
		dhis2Connector.setProgram(program);
		dhis2Connector.setTrackedEntity(trackedEntity);
		return dhis2Connector.send();
		
	}
	
}
