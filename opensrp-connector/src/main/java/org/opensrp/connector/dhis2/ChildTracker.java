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
import org.opensrp.service.EventService;
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
	
	@Autowired
	private DHIS2Connector dhis2Connector;
	
	public ChildTracker() {
		
	}
	
	public ChildTracker(String dhis2Url, String user, String password) {
		super(dhis2Url, user, password);
	}
	
	@Override
	public JSONArray getTrackCaptureData(Client client) throws JSONException {
		JSONObject clientData = new JSONObject();
		String firstName ="firstName";
		String lastName ="lastName";
		String attributeKey = "attribute";
		String valueKey = "value";
		String gender = "gender";
		String Child_Birth_Certificate = "Child_Birth_Certificate";
		JSONArray generateTrackCaptureData = new JSONArray();
		Map<String, Object> attributes = new HashMap<>();
		attributes = client.getAttributes();
		JSONObject attributesAsJson = new JSONObject(attributes);
		JSONObject clientAsJson = new JSONObject(client);
		
		// firstName
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get(firstName).toString(), firstName));
		// LastName
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get(lastName).toString(), lastName));
		//Gender
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get(gender).toString(), gender));
		//Child_Birth_Certificate
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(attributesAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get(Child_Birth_Certificate).toString(), Child_Birth_Certificate));
		//birthdate		
		JSONObject data = new JSONObject();
		data.put(attributeKey, DHIS2Settings.CLIENTIDMAPPING.get("birthdate").toString());
		data.put(valueKey, client.getBirthdate());
		generateTrackCaptureData.put(data);
		
		/**** getting mother info from Client of Mother *******/
		Map<String, List<String>> relationships = client.getRelationships();
		String motherbaseEntityId = relationships.get("mother").get(0);
		
		Client mother = clientService.find(motherbaseEntityId);
		JSONObject motherAsJson = new JSONObject(mother);
		//Mother/Guardian First Name
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(motherAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get("Mother_guardian_First_Name").toString(), "firstName"));
		
		// Mother_Guardian_Last_Name
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(motherAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get("Mother_Guardian_Last_Name").toString(), "lastName"));
		
		// Mother_Guardian birthdate		
		JSONObject motherbirthDate = new JSONObject();
		motherbirthDate.put("attribute", DHIS2Settings.CLIENTIDMAPPING.get("Mother_Guardian_DOB").toString());
		motherbirthDate.put("value", mother.getBirthdate());
		generateTrackCaptureData.put(motherbirthDate);
		
		/****** getting information from Event *****/
		
		clientData.put("attributes", generateTrackCaptureData);
		
		return generateTrackCaptureData;
	}
	
	@Override
	public JSONObject sendTrackCaptureData(JSONArray attributes) throws JSONException {
		String orgUnit = "IDc0HEyjhvL";
		String program = "OprRhyWVIM6";
		String trackedEntity = "MCPQUTHX1Ze";
		dhis2Connector.setAttributes(attributes);
		dhis2Connector.setOrgUnit(orgUnit);
		dhis2Connector.setProgram(program);
		dhis2Connector.setTrackedEntity(trackedEntity);
		return dhis2Connector.send();
	}
}
