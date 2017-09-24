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
public class ChildTracker implements DHIS2Tracker {
	
	@Autowired
	private DHIS2TrackerService dhis2TrackerService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private EventService eventService;
	
	@Override
	public JSONObject getTrackCaptureData(Client client) throws JSONException {
		JSONObject clientData = new JSONObject();
		
		JSONArray generateTrackCaptureData = new JSONArray();
		Map<String, Object> attributes = new HashMap<>();
		attributes = client.getAttributes();
		JSONObject attributesAsJson = new JSONObject(attributes);
		JSONObject clientAsJson = new JSONObject(client);
		
		// firstName
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get("firstName").toString(), "firstName"));
		// LastName
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get("lastName").toString(), "lastName"));
		//Gender
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get("gender").toString(), "gender"));
		//Child_Birth_Certificate
		generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(attributesAsJson, DHIS2Settings.CLIENTIDMAPPING
		        .get("Child_Birth_Certificate").toString(), "Child_Birth_Certificate"));
		//birthdate		
		JSONObject data = new JSONObject();
		data.put("attribute", DHIS2Settings.CLIENTIDMAPPING.get("birthdate").toString());
		data.put("value", client.getBirthdate());
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
		System.err.println("clientData:" + clientData.toString());
		return null;
	}
}
