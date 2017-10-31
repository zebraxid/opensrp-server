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
	
	public VaccinationTracker() {
		
	}
	
	public VaccinationTracker(String dhis2Url, String user, String password) {
		super(dhis2Url, user, password);
	}
	
	public JSONArray getTrackCaptureDataAndSend(List<Event> events) throws JSONException {
		for (Event event : events) {
			List<Obs> observations = event.getObs();
			for (Obs obs : observations) {
				if (DHIS2Settings.VACCINATIONMAPPING.containsKey(obs.getFormSubmissionField())) {
					Client client = clientService.find(event.getBaseEntityId());
					sendTrackCaptureData(prepareData(obs,
					    DHIS2Settings.VACCINATIONMAPPING.get(obs.getFormSubmissionField()), client));
				}
			}
			
		}
		return null;
	}
	
	private JSONArray prepareData(Obs obs, String attributeId, Client client) {
		
		JSONArray generateTrackCaptureData = new JSONArray();
		Map<String, Object> attributes = new HashMap<>();
		attributes = client.getAttributes();
		JSONObject attributesAsJson = new JSONObject(attributes);
		JSONObject clientAsJson = new JSONObject(client);
		try {
			//firstName
			generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson,
			    DHIS2Settings.VACCINATIONMAPPING.get("firstName").toString(), "firstName"));
			// LastName
			generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(clientAsJson,
			    DHIS2Settings.VACCINATIONMAPPING.get("lastName").toString(), "lastName"));
			
			generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(attributesAsJson,
			    DHIS2Settings.VACCINATIONMAPPING.get("NID_BRID").toString(), "nationalId"));
			generateTrackCaptureData.put(dhis2TrackerService.getTrackCaptureData(attributesAsJson,
			    DHIS2Settings.VACCINATIONMAPPING.get("Child_Birth_Certificate").toString(), "Child_Birth_Certificate"));
			
			//vaccination name
			JSONObject vaccieName = new JSONObject();
			vaccieName.put("attribute", DHIS2Settings.VACCINATIONMAPPING.get("Vaccina_name"));
			vaccieName.put("value", obs.getFormSubmissionField());
			generateTrackCaptureData.put(vaccieName);
			
			//vaccination dose
			JSONObject vaccieDose = new JSONObject();
			vaccieDose.put("attribute", DHIS2Settings.VACCINATIONMAPPING.get("Vaccina_dose"));
			vaccieDose.put("value", obs.getFormSubmissionField());
			generateTrackCaptureData.put(DHIS2Settings.VACCINATION.containsKey(obs.getFormSubmissionField()));
			
			generateTrackCaptureData.put(dhis2TrackerService.getVaccinationDataFromObservation(obs,
			    DHIS2Settings.VACCINATIONMAPPING.get("Vaccina_date").toString()));
			
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return generateTrackCaptureData;
	}
	
	public JSONObject sendTrackCaptureData(JSONArray attributes) throws JSONException {
		String orgUnit = "IDc0HEyjhvL";
		String program = "Bxy7WXRMscX";
		JSONObject clientData = new JSONObject();
		/*JSONArray enrollments = new JSONArray();
		JSONObject enrollmentsObj = new JSONObject();
		enrollmentsObj.put("orgUnit", orgUnit);
		enrollmentsObj.put("program", program);
		enrollmentsObj.put("enrollmentDate", DateUtil.getTodayAsString());
		enrollmentsObj.put("incidentDate", DateUtil.getTodayAsString());
		enrollments.put(enrollmentsObj);*/
		
		clientData.put("attributes", attributes);
		clientData.put("trackedEntity", "MCPQUTHX1Ze");
		clientData.put("orgUnit", orgUnit);
		
		JSONObject responseTrackEntityInstance = new JSONObject(Dhis2HttpUtils.post(
		    DHIS2_BASE_URL.replaceAll("\\s+", "") + "trackedEntityInstances", "", clientData.toString(),
		    DHIS2_USER.replaceAll("\\s+", ""), DHIS2_PWD.replaceAll("\\s+", "")).body());
		JSONObject trackEntityReference = (JSONObject) responseTrackEntityInstance.get("response");
		
		JSONObject enroll = new JSONObject();
		enroll.put("trackedEntityInstance", trackEntityReference.get("reference"));
		enroll.put("program", program);
		enroll.put("orgUnit", orgUnit);
		
		JSONObject response = new JSONObject(Dhis2HttpUtils.post(DHIS2_BASE_URL.replaceAll("\\s+", "") + "enrollments", "",
		    enroll.toString(), DHIS2_USER.replaceAll("\\s+", ""), DHIS2_PWD.replaceAll("\\s+", "")).body());
		
		response.put("track", trackEntityReference.get("reference"));
		
		return response;
	}
	
}
