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
	
	public HouseholdTracker() {
		
	}
	
	public HouseholdTracker(String dhis2Url, String user, String password) {
		super(dhis2Url, user, password);
	}
	
	@Override
	public JSONArray getTrackCaptureData(Client client) throws JSONException {
		JSONObject clientData = new JSONObject();
		
		JSONArray generateTrackCaptureData = new JSONArray();
		Map<String, Object> attributes = new HashMap<>();
		attributes = client.getAttributes();
		JSONObject attributesAsJson = new JSONObject(attributes);
		JSONObject clientAsJson = new JSONObject(client);
		
		// firstName
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson, DHIS2Settings.HOUSEHOLDIDMAPPING
		        .get(DHIS2Settings.FIRSTNAME).toString(), DHIS2Settings.FIRSTNAME));
		// LastName 
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson, DHIS2Settings.HOUSEHOLDIDMAPPING
		        .get(DHIS2Settings.LASTNAME).toString(), DHIS2Settings.LASTNAME));
		//Gender
		/*generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get("gender").toString(), "gender"));*/
		//"householdCode/Household ID
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(attributesAsJson,
		    DHIS2Settings.HOUSEHOLDIDMAPPING.get("Household_ID").toString(), "householdCode"));
		//birthdate		
		JSONObject data = new JSONObject();
		data.put(DHIS2Settings.ATTRIBUTEKEY, DHIS2Settings.HOUSEHOLDIDMAPPING.get("birthdate").toString());
		data.put(DHIS2Settings.VALUEKEY, client.getBirthdate());
		generateTrackCaptureData.put(data);
		
		/***** get information form Event ******/
		List<Event> event = eventService.findByBaseEntityAndType(client.getBaseEntityId(), "Household Registration");
		List<Obs> observations = event.get(0).getObs();
		/**** Member_Registration_No /Date_Of_Reg ***/
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureDataFromEventByValues(observations,
		    DHIS2Settings.MOTHERIDMAPPING.get("registration_Date").toString(), "Date_Of_Reg"));
		clientData.put(DHIS2Settings.ATTRIBUTSEKEY, generateTrackCaptureData);
		
		return generateTrackCaptureData;
	}
	
	@Override
	public JSONObject sendTrackCaptureData(JSONArray attributes) throws JSONException {
		String orgUnit = "IDc0HEyjhvL";
		String program = "OprRhyWVIM6";
		
		JSONObject clientData = new JSONObject();
		
		//JSONArray enrollments = new JSONArray();
		//JSONObject enrollmentsObj = new JSONObject();
		//enrollmentsObj.put("orgUnit", orgUnit);
		//enrollmentsObj.put("program", program);
		/*enrollmentsObj.put("enrollmentDate", DateUtil.getTodayAsString());
		enrollmentsObj.put("incidentDate", DateUtil.getTodayAsString());*/
		//enrollments.put(enrollmentsObj);
		//clientData.put("enrollments", enrollments);
		
		clientData.put(DHIS2Settings.ATTRIBUTSEKEY, attributes);
		clientData.put("trackedEntity", "MCPQUTHX1Ze");
		clientData.put(DHIS2Settings.ORGUNITKEY, orgUnit);
		
		JSONObject responseTrackEntityInstance = new JSONObject(Dhis2HttpUtils.post(
		    DHIS2_BASE_URL.replaceAll(DHIS2Settings.REPLACE, "") + "trackedEntityInstances", "", clientData.toString(),
		    DHIS2_USER.replaceAll(DHIS2Settings.REPLACE, ""), DHIS2_PWD.replaceAll(DHIS2Settings.REPLACE, "")).body());
		JSONObject trackEntityReference = (JSONObject) responseTrackEntityInstance.get("response");
		
		JSONObject enroll = new JSONObject();
		enroll.put("trackedEntityInstance", trackEntityReference.get("reference"));
		enroll.put(DHIS2Settings.PROGRAM, program);
		enroll.put(DHIS2Settings.ORGUNITKEY, orgUnit);
		
		JSONObject response = new JSONObject(Dhis2HttpUtils.post(
		    DHIS2_BASE_URL.replaceAll(DHIS2Settings.REPLACE, "") + "enrollments", "", enroll.toString(),
		    DHIS2_USER.replaceAll(DHIS2Settings.REPLACE, ""), DHIS2_PWD.replaceAll(DHIS2Settings.REPLACE, "")).body());
		
		response.put("track", trackEntityReference.get("reference"));
		
		return response;
		
	}
	
}
