/**
 * 
 */
package org.opensrp.connector.dhis2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.domain.Client;
import org.opensrp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author proshanto
 */
@Service
public class ChildTracker extends DHIS2Service implements DHIS2Tracker {
	
	@Autowired
	private DHIS2TrackerService dhis2TrackerService;
	
	@Autowired
	private ClientService clientService;
	
	public ChildTracker() {
		
	}
	
	public ChildTracker(String dhis2Url, String user, String password) {
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
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get(DHIS2Settings.FIRSTNAME).toString(), DHIS2Settings.FIRSTNAME));
		// LastName
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get(DHIS2Settings.LASTNAME).toString(), DHIS2Settings.LASTNAME));
		//Gender
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get(DHIS2Settings.GENDER).toString(), DHIS2Settings.GENDER));
		//Child_Birth_Certificate
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(attributesAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get("Child_Birth_Certificate").toString(), "Child_Birth_Certificate"));
		//birthdate		
		JSONObject data = new JSONObject();
		data.put(DHIS2Settings.ATTRIBUTEKEY, DHIS2Settings.CLIENTIDMAPPING.get("birthdate").toString());
		data.put(DHIS2Settings.VALUEKEY, client.getBirthdate());
		generateTrackCaptureData.put(data);
		
		/**** getting mother info from Client of Mother *******/
		Map<String, List<String>> relationships = client.getRelationships();
		String motherbaseEntityId = relationships.get("mother").get(0);
		
		Client mother = clientService.find(motherbaseEntityId);
		JSONObject motherAsJson = new JSONObject(mother);
		//Mother/Guardian First Name
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(motherAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get("Mother_guardian_First_Name").toString(), DHIS2Settings.FIRSTNAME));
		
		// Mother_Guardian_Last_Name
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(motherAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get("Mother_Guardian_Last_Name").toString(), DHIS2Settings.LASTNAME));
		
		// Mother_Guardian birthdate		
		JSONObject motherbirthDate = new JSONObject();
		motherbirthDate.put(DHIS2Settings.ATTRIBUTEKEY, DHIS2Settings.CLIENTIDMAPPING.get("Mother_Guardian_DOB").toString());
		motherbirthDate.put(DHIS2Settings.VALUEKEY, mother.getBirthdate());
		generateTrackCaptureData.put(motherbirthDate);
		
		/****** getting information from Event *****/
		
		clientData.put(DHIS2Settings.ATTRIBUTSEKEY, generateTrackCaptureData);
		
		return generateTrackCaptureData;
	}
	
	@Override
	public JSONObject sendTrackCaptureData(JSONArray attributes) throws JSONException {
		String orgUnit = "IDc0HEyjhvL";
		String program = "OprRhyWVIM6";
		JSONObject clientData = new JSONObject();
		/*JSONArray enrollments = new JSONArray();
		JSONObject enrollmentsObj = new JSONObject();
		enrollmentsObj.put(DHIS2Settings.ORGUNITKEY, orgUnit);
		enrollmentsObj.put(DHIS2Settings.PROGRAM, program);
		enrollmentsObj.put("enrollmentDate", DateUtil.getTodayAsString());
		enrollmentsObj.put("incidentDate", DateUtil.getTodayAsString());
		enrollments.put(enrollmentsObj);*/
		
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
