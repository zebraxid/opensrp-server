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
import org.opensrp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccinationTracker extends DHIS2Service {
	
	@Autowired
	private DHIS2TrackerService dhis2TrackerService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private DHIS2Connector dhis2Connector;
	
	public VaccinationTracker() {
		
	}
	
	public VaccinationTracker(String dhis2Url, String user, String password) {
		super(dhis2Url, user, password);
	}
	
	public JSONArray getTrackCaptureDataAndSend(List<Event> events) throws JSONException {
		for (Event event : events) {
			List<Obs> observations = event.getObs();
			
			for (Obs obs : observations) {
				
				if (DHIS2Settings.VACCINATION.containsKey(obs.getFormSubmissionField())) {
					Client client = clientService.find(event.getBaseEntityId());
					System.err.println("CLLL:" + client);
					if (client != null)
						sendTrackCaptureData(prepareData(obs, client));
				}
			}
			
		}
		return null;
	}
	
	private JSONArray prepareData(Obs obs, Client client) {
		
		JSONArray generateTrackCaptureData = new JSONArray();
		Map<String, Object> attributes = new HashMap<>();
		
		JSONObject clientAsJson = new JSONObject(client);
		try {
			//firstName
			generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson,
			    DHIS2Settings.VACCINATIONMAPPING.get("firstName").toString(), "firstName"));
			// LastName
			generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson,
			    DHIS2Settings.VACCINATIONMAPPING.get("lastName").toString(), "lastName"));
			
			attributes = client.getAttributes();
			if (attributes != null) {
				JSONObject attributesAsJson = new JSONObject(attributes);
				
				generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(attributesAsJson,
				    DHIS2Settings.VACCINATIONMAPPING.get("NID_BRID").toString(), "nationalId"));
				generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(attributesAsJson,
				    DHIS2Settings.VACCINATIONMAPPING.get("Child_Birth_Certificate").toString(), "Child_Birth_Certificate"));
				
			}
			
			//vaccination name
			JSONObject vaccieName = new JSONObject();
			vaccieName.put("attribute", DHIS2Settings.VACCINATIONMAPPING.get("Vaccina_name"));
			vaccieName.put("value", obs.getFormSubmissionField());
			generateTrackCaptureData.put(vaccieName);
			
			//vaccination dose
			JSONObject vaccieDose = new JSONObject();
			vaccieDose.put("attribute", DHIS2Settings.VACCINATIONMAPPING.get("Vaccina_dose"));
			vaccieDose.put("value", DHIS2Settings.VACCINATION.get(obs.getFormSubmissionField()));
			generateTrackCaptureData.put(vaccieDose);
			
			generateTrackCaptureData.put(dhis2TrackerService.getVaccinationDataFromObservation(obs,
			    DHIS2Settings.VACCINATIONMAPPING.get("Vaccina_date").toString()));
			
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("generateTrackCaptureData:" + generateTrackCaptureData);
		return generateTrackCaptureData;
	}
	
	public JSONObject sendTrackCaptureData(JSONArray attributes) throws JSONException {
		String orgUnit = "IDc0HEyjhvL";
		String program = "Bxy7WXRMscX";
		String trackedEntity = "MCPQUTHX1Ze";
		dhis2Connector.setAttributes(attributes);
		dhis2Connector.setOrgUnit(orgUnit);
		dhis2Connector.setProgram(program);
		dhis2Connector.setTrackedEntity(trackedEntity);
		return dhis2Connector.send();
	}
}
