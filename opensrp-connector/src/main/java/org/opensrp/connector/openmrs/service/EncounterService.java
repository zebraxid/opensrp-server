package org.opensrp.connector.openmrs.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.common.util.HttpResponse;
import org.opensrp.common.util.HttpUtil;
import org.opensrp.connector.openmrs.schedule.OpenmrsSyncerListener;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.domain.Obs;
import org.opensrp.domain.User;
import org.opensrp.service.ClientService;
import org.opensrp.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;

@Service
public class EncounterService extends OpenmrsService {
	private static Logger logger = LoggerFactory.getLogger(EncounterService.class.toString());
	
	private static final String ENCOUNTER_URL = "ws/rest/v1/encounter";//"ws/rest/emrapi/encounter";
	
	private static final String BAHMNI_ENCOUNTER_URL = "ws/rest/v1/bahmnicore/bahmniencounter";
	
	private static final String OBS_URL = "ws/rest/v1/obs";
	
	private static final String ENCOUNTER__TYPE_URL = "ws/rest/v1/encountertype";
	
	public static final String OPENMRS_UUID_IDENTIFIER_TYPE = "OPENMRS_UUID";
	
	private PatientService patientService;
	
	private OpenmrsUserService userService;
	
	private ClientService clientService;
	
	private EventService eventService;
	
	@Autowired
	private OpenmrsLocationService openmrsLocationService;
	
	@Autowired
	public EncounterService(PatientService patientService, OpenmrsUserService userService, ClientService clientService,
	    EventService eventService) {
		this.patientService = patientService;
		this.userService = userService;
		this.clientService = clientService;
		this.eventService = eventService;
	}
	
	public EncounterService(String openmrsUrl, String user, String password) {
		super(openmrsUrl, user, password);
	}
	
	public PatientService getPatientService() {
		return patientService;
	}
	
	public void setPatientService(PatientService patientService) {
		this.patientService = patientService;
	}
	
	public OpenmrsUserService getUserService() {
		return userService;
	}
	
	public void setUserService(OpenmrsUserService userService) {
		this.userService = userService;
	}
	
	public JSONObject getEncounterByUuid(String uuid, boolean noRepresentationTag) throws JSONException {
		return new JSONObject(HttpUtil.get(getURL() + "/" + ENCOUNTER_URL + "/" + uuid, noRepresentationTag ? "" : "v=full",
		    OPENMRS_USER, OPENMRS_PWD).body());
	}
	
	public JSONObject getBahmniEncounterByUuid(String uuid, boolean noRepresentationTag) throws JSONException {
		return new JSONObject(HttpUtil.get(getURL() + "/" + BAHMNI_ENCOUNTER_URL + "/" + uuid,
		    noRepresentationTag ? "" : "v=full", OPENMRS_USER, OPENMRS_PWD).body());
	}
	
	public JSONObject getObsByEncounterUuid(String encounterUuid) throws JSONException {
		// The data format returned contains the obs uuid and concept uuids
		return new JSONObject(HttpUtil.get(getURL() + "/" + ENCOUNTER_URL + "/" + encounterUuid,
		    "v=custom:(uuid,obs:(uuid,concept:(uuid)))", OPENMRS_USER, OPENMRS_PWD).body());
	}
	
	public JSONObject getObsUuidByParentObsUuid(String obsUuid) throws JSONException {
		//The data format returned contains the children obs uuid and concept uuids
		return new JSONObject(HttpUtil.get(getURL() + "/" + OBS_URL + "/" + obsUuid,
		    "v=custom:(groupMembers:(uuid,concept:(uuid)))", OPENMRS_USER, OPENMRS_PWD).body());
	}
	
	public JSONObject getEncounterType(String encounterType) throws JSONException {
		// we have to use this ugly approach because identifier not found throws exception and 
		// its hard to find whether it was network error or object not found or server error
		JSONArray res = new JSONObject(HttpUtil.get(getURL() + "/" + ENCOUNTER__TYPE_URL, "v=full", OPENMRS_USER,
		    OPENMRS_PWD).body()).getJSONArray("results");
		for (int i = 0; i < res.length(); i++) {
			if (res.getJSONObject(i).getString("display").equalsIgnoreCase(encounterType)) {
				return res.getJSONObject(i);
			}
		}
		return null;
	}
	
	public JSONObject createEncounterType(String name, String description) throws JSONException {
		JSONObject o = convertEncounterToOpenmrsJson(name, description);
		return new JSONObject(HttpUtil.post(getURL() + "/" + ENCOUNTER__TYPE_URL, "", o.toString(), OPENMRS_USER,
		    OPENMRS_PWD).body());
	}
	
	public JSONObject convertEncounterToOpenmrsJson(String name, String description) throws JSONException {
		JSONObject a = new JSONObject();
		a.put("name", name);
		a.put("description", description);
		return a;
	}
	
	public JSONObject createEncounter(Event e) throws JSONException {
		JSONObject enc = createEncounterJson(e);
		logger.info("\n \n \n Final JSON <<>> <<>> <<>>" + enc.toString()+"\n \n \n");
		//JSONObject enc2 = new JSONObject("{\"locationUuid\":\"7d76f621-63ce-4bbb-a715-176286f8de84\",\"patientUuid\":\"b0e47458-1bcb-42d4-88d7-f5aaaa2aa1af\",\"encounterUuid\":null,\"visitUuid\":null,\"providers\":[{\"uuid\":\"313c8507-9821-40e4-8a70-71a5c7693d72\"}],\"encounterDateTime\":null,\"extensions\":{\"mdrtbSpecimen\":[]},\"context\":{},\"visitType\":\"OPD\",\"bahmniDiagnoses\":[],\"orders\":[],\"drugOrders\":[],\"disposition\":null,\"observations\":[{\"concept\":{\"uuid\":\"f87fe2f4-de90-4c48-b07d-4050a5debf8c\",\"name\":\"\u09B8\u09CD\u09AC\u09BE\u09B8\u09CD\u09A5\u09CD\u09AF \u09B8\u09C7\u09AC\u09BE:\"},\"formNamespace\":\"Bahmni\",\"formFieldPath\":\"\u09B8\u09BE\u09A7\u09BE\u09B0\u09A8 \u09B0\u09C7\u09BE\u0997\u09C0\u09B0 \u09B8\u09C7\u09AC\u09BE \u09AB\u09B0\u09CD\u09AE.12/39-0\",\"voided\":false,\"value\":{\"uuid\":\"c2bb6edf-18cb-4c7f-ad91-7c8dd561a437\",\"name\":{\"display\":\"\u0989\u099A\u09CD\u099A \u09B0\u0995\u09CD\u09A4\u099A\u09BE\u09AA\",\"uuid\":\"38b6a8b8-e02f-41c7-813d-63df201562a6\",\"name\":\"\u0989\u099A\u09CD\u099A \u09B0\u0995\u09CD\u09A4\u099A\u09BE\u09AA\",\"locale\":\"en\",\"localePreferred\":true,\"conceptNameType\":null,\"links\":[{\"rel\":\"self\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/c2bb6edf-18cb-4c7f-ad91-7c8dd561a437/name/38b6a8b8-e02f-41c7-813d-63df201562a6\"},{\"rel\":\"full\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/c2bb6edf-18cb-4c7f-ad91-7c8dd561a437/name/38b6a8b8-e02f-41c7-813d-63df201562a6?v=full\"}],\"resourceVersion\":\"1.9\"},\"names\":[{\"display\":\"\u0989\u099A\u09CD\u099A \u09B0\u0995\u09CD\u09A4\u099A\u09BE\u09AA\",\"uuid\":\"38b6a8b8-e02f-41c7-813d-63df201562a6\",\"name\":\"\u0989\u099A\u09CD\u099A \u09B0\u0995\u09CD\u09A4\u099A\u09BE\u09AA\",\"locale\":\"en\",\"localePreferred\":true,\"conceptNameType\":null,\"links\":[{\"rel\":\"self\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/c2bb6edf-18cb-4c7f-ad91-7c8dd561a437/name/38b6a8b8-e02f-41c7-813d-63df201562a6\"},{\"rel\":\"full\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/c2bb6edf-18cb-4c7f-ad91-7c8dd561a437/name/38b6a8b8-e02f-41c7-813d-63df201562a6?v=full\"}],\"resourceVersion\":\"1.9\"},{\"display\":\"High Blood Pressure\",\"uuid\":\"98a2f125-8eac-4680-bd08-33572ff023d0\",\"name\":\"High Blood Pressure\",\"locale\":\"en\",\"localePreferred\":false,\"conceptNameType\":\"FULLY_SPECIFIED\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/c2bb6edf-18cb-4c7f-ad91-7c8dd561a437/name/98a2f125-8eac-4680-bd08-33572ff023d0\"},{\"rel\":\"full\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/c2bb6edf-18cb-4c7f-ad91-7c8dd561a437/name/98a2f125-8eac-4680-bd08-33572ff023d0?v=full\"}],\"resourceVersion\":\"1.9\"}],\"displayString\":\"\u0989\u099A\u09CD\u099A \u09B0\u0995\u09CD\u09A4\u099A\u09BE\u09AA\",\"resourceVersion\":\"2.0\",\"translationKey\":\"\u0989\u099A\u09CD\u099A_\u09B0\u0995\u09CD\u09A4\u099A\u09BE\u09AA_39\"},\"inactive\":false,\"groupMembers\":[]},{\"concept\":{\"uuid\":\"9be72615-b914-4d35-ac80-d5953744a9d0\",\"name\":\"\u09B0\u09C7\u09AB\u09BE\u09B0\u09C7\u09B2:\"},\"formNamespace\":\"Bahmni\",\"formFieldPath\":\"\u09B8\u09BE\u09A7\u09BE\u09B0\u09A8 \u09B0\u09C7\u09BE\u0997\u09C0\u09B0 \u09B8\u09C7\u09AC\u09BE \u09AB\u09B0\u09CD\u09AE.12/40-0\",\"voided\":false,\"value\":{\"uuid\":\"1e3f1870-b252-4808-8edb-f86fad050ebd\",\"name\":{\"display\":\"\u09A1\u09BE\u09AF\u09BC\u09BE\u09AC\u09C7\u099F\u09BF\u09B8\",\"uuid\":\"befce65b-9e80-45ec-b8b7-05234cd5cb9c\",\"name\":\"\u09A1\u09BE\u09AF\u09BC\u09BE\u09AC\u09C7\u099F\u09BF\u09B8\",\"locale\":\"en\",\"localePreferred\":true,\"conceptNameType\":null,\"links\":[{\"rel\":\"self\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/1e3f1870-b252-4808-8edb-f86fad050ebd/name/befce65b-9e80-45ec-b8b7-05234cd5cb9c\"},{\"rel\":\"full\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/1e3f1870-b252-4808-8edb-f86fad050ebd/name/befce65b-9e80-45ec-b8b7-05234cd5cb9c?v=full\"}],\"resourceVersion\":\"1.9\"},\"names\":[{\"display\":\"Diabetes\",\"uuid\":\"fdabcf86-7ac9-4122-96f7-9f84858228fd\",\"name\":\"Diabetes\",\"locale\":\"en\",\"localePreferred\":false,\"conceptNameType\":\"FULLY_SPECIFIED\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/1e3f1870-b252-4808-8edb-f86fad050ebd/name/fdabcf86-7ac9-4122-96f7-9f84858228fd\"},{\"rel\":\"full\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/1e3f1870-b252-4808-8edb-f86fad050ebd/name/fdabcf86-7ac9-4122-96f7-9f84858228fd?v=full\"}],\"resourceVersion\":\"1.9\"},{\"display\":\"\u09A1\u09BE\u09AF\u09BC\u09BE\u09AC\u09C7\u099F\u09BF\u09B8\",\"uuid\":\"befce65b-9e80-45ec-b8b7-05234cd5cb9c\",\"name\":\"\u09A1\u09BE\u09AF\u09BC\u09BE\u09AC\u09C7\u099F\u09BF\u09B8\",\"locale\":\"en\",\"localePreferred\":true,\"conceptNameType\":null,\"links\":[{\"rel\":\"self\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/1e3f1870-b252-4808-8edb-f86fad050ebd/name/befce65b-9e80-45ec-b8b7-05234cd5cb9c\"},{\"rel\":\"full\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/1e3f1870-b252-4808-8edb-f86fad050ebd/name/befce65b-9e80-45ec-b8b7-05234cd5cb9c?v=full\"}],\"resourceVersion\":\"1.9\"}],\"displayString\":\"\u09A1\u09BE\u09AF\u09BC\u09BE\u09AC\u09C7\u099F\u09BF\u09B8\",\"resourceVersion\":\"2.0\",\"translationKey\":\"\u09A1\u09BE\u09AF\u09BC\u09BE\u09AC\u09C7\u099F\u09BF\u09B8_40\"},\"inactive\":false,\"groupMembers\":[]},{\"concept\":{\"uuid\":\"514de0ad-14e3-4fc6-b4a3-a2683317ab53\",\"name\":\"\u09B8\u09CD\u09AC\u09BE\u09B8\u09CD\u09A5\u09CD\u09AF \u09B6\u09BF\u0995\u09CD\u09B7\u09BE:\"},\"formNamespace\":\"Bahmni\",\"formFieldPath\":\"\u09B8\u09BE\u09A7\u09BE\u09B0\u09A8 \u09B0\u09C7\u09BE\u0997\u09C0\u09B0 \u09B8\u09C7\u09AC\u09BE \u09AB\u09B0\u09CD\u09AE.12/41-0\",\"voided\":false,\"value\":{\"uuid\":\"0622f52f-0c95-41c1-ab5d-ee9bc335c839\",\"name\":{\"display\":\"\u09B8\u09AE\u09CD\u09AD\u09BE\u09AC\u09CD\u09AF \u09AF\u0995\u09CD\u09B7\u09CD\u09AE\u09BE\",\"uuid\":\"cc994816-e03d-4674-a455-f1087b88e934\",\"name\":\"\u09B8\u09AE\u09CD\u09AD\u09BE\u09AC\u09CD\u09AF \u09AF\u0995\u09CD\u09B7\u09CD\u09AE\u09BE\",\"locale\":\"en\",\"localePreferred\":true,\"conceptNameType\":null,\"links\":[{\"rel\":\"self\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/0622f52f-0c95-41c1-ab5d-ee9bc335c839/name/cc994816-e03d-4674-a455-f1087b88e934\"},{\"rel\":\"full\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/0622f52f-0c95-41c1-ab5d-ee9bc335c839/name/cc994816-e03d-4674-a455-f1087b88e934?v=full\"}],\"resourceVersion\":\"1.9\"},\"names\":[{\"display\":\"Tuberculosis\",\"uuid\":\"9c02fdc7-0e51-4a1f-a530-586bfed811a9\",\"name\":\"Tuberculosis\",\"locale\":\"en\",\"localePreferred\":false,\"conceptNameType\":\"FULLY_SPECIFIED\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/0622f52f-0c95-41c1-ab5d-ee9bc335c839/name/9c02fdc7-0e51-4a1f-a530-586bfed811a9\"},{\"rel\":\"full\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/0622f52f-0c95-41c1-ab5d-ee9bc335c839/name/9c02fdc7-0e51-4a1f-a530-586bfed811a9?v=full\"}],\"resourceVersion\":\"1.9\"},{\"display\":\"\u09B8\u09AE\u09CD\u09AD\u09BE\u09AC\u09CD\u09AF \u09AF\u0995\u09CD\u09B7\u09CD\u09AE\u09BE\",\"uuid\":\"cc994816-e03d-4674-a455-f1087b88e934\",\"name\":\"\u09B8\u09AE\u09CD\u09AD\u09BE\u09AC\u09CD\u09AF \u09AF\u0995\u09CD\u09B7\u09CD\u09AE\u09BE\",\"locale\":\"en\",\"localePreferred\":true,\"conceptNameType\":null,\"links\":[{\"rel\":\"self\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/0622f52f-0c95-41c1-ab5d-ee9bc335c839/name/cc994816-e03d-4674-a455-f1087b88e934\"},{\"rel\":\"full\",\"uri\":\"http://192.168.19.44/openmrs/ws/rest/v1/concept/0622f52f-0c95-41c1-ab5d-ee9bc335c839/name/cc994816-e03d-4674-a455-f1087b88e934?v=full\"}],\"resourceVersion\":\"1.9\"}],\"displayString\":\"\u09B8\u09AE\u09CD\u09AD\u09BE\u09AC\u09CD\u09AF \u09AF\u0995\u09CD\u09B7\u09CD\u09AE\u09BE\",\"resourceVersion\":\"2.0\",\"translationKey\":\"\u09B8\u09AE\u09CD\u09AD\u09BE\u09AC\u09CD\u09AF_\u09AF\u0995\u09CD\u09B7\u09CD\u09AE\u09BE_41\"},\"inactive\":false,\"groupMembers\":[]}],\"encounterTypeUuid\":\"81852aee-3f10-11e4-adec-0800271c1b75\"}\r\n");
		HttpResponse op = HttpUtil.post(HttpUtil.removeEndingSlash(OPENMRS_BASE_URL) + "/" + BAHMNI_ENCOUNTER_URL, "",
		    enc.toString(), OPENMRS_USER, OPENMRS_PWD);
		logger.info("\n \n \n"+ "Response From Openmrs <<>> <<>> <<>>" + op.body()+"\n \n \n");
		return new JSONObject(op.body());
	}

	private JSONObject createEncounterJson(Event e) throws JSONException {
		JSONObject pt = patientService.getPatientByIdentifier(e.getBaseEntityId());
		Client client = clientService.getByBaseEntityId(e.getBaseEntityId());
		JSONObject enc = getStaticJsonObject("normalDisease");
		JSONObject pr = userService.getPersonByUser(e.getProviderId());
		JSONArray dummnyArray = new JSONArray();
		
/*		//Main JSON Structure
		//enc.put("encounterDatetime", OPENMRS_DATE.format(e.getEventDate().toDate()));
		enc.put("encounterDatetime", JSONObject.NULL);
		// patient must be existing in OpenMRS before it submits an encounter. if it doesnot it would throw NPE
		enc.put("patientUuid", pt.getString("uuid"));
		enc.put("locationUuid", e.getLocationId());
		//enc.put("provider", pr.getString("uuid"));
		//TODO enc.put("patientUuid", pt.getString("uuid"));
		//enc.put("encounterType", e.getEventType());
		//TODO enc.put("encounterTypeUuid", e.getEventType());
		JSONObject dummbyObject = new JSONObject();
		//enc.put("context", dummbyObject);
		enc.put("context", JSONObject.NULL);
		enc.put("encounterUuid", JSONObject.NULL);
		enc.put("visitUuid", JSONObject.NULL);
		enc.put("disposition", JSONObject.NULL);
		enc.put("orders", dummnyArray);
		enc.put("drugOrders", dummnyArray);
		enc.put("bahmniDiagnoses", dummnyArray);
		JSONArray prov = new JSONArray();
		JSONObject providerUUID = new JSONObject();
		providerUUID.put("uuid", "313c8507-9821-40e4-8a70-71a5c7693d72");
		prov.put(providerUUID);
		enc.put("providers", prov);
		enc.put("visitType", "Community clinic service");
		enc.put("encounterTypeUuid", "81852aee-3f10-11e4-adec-0800271c1b75");
		dummbyObject.put("mdrtbSpecimen", dummnyArray);
		enc.put("extensions", dummbyObject);*/

		//observations for Followup Disease Female and Male
		JSONArray obar = null;
		if (e.getEventType().equalsIgnoreCase("Followup Disease Female")) {
			//pass list of obs instead of dummyArray
			//createDiseaseJSON needs massive change
			obar = createDiseaseJSON(e, dummnyArray);
		}
		enc.put("observations", obar);
		return enc;
	}

	private JSONArray createDiseaseJSON(Event e, JSONArray dummnyArray)
			throws JSONException {
		JSONArray obar= new JSONArray();
		//JSONObject observationObject1 = new JSONObject();

		Client client = clientService.getByBaseEntityId(e.getBaseEntityId());
		boolean hasDisease =false;
		if(client.getAttributes().containsKey("Disease_status")){
			hasDisease = true;
		}

		if(hasDisease){
			obar.put(getStaticJsonObject("healthCareGivenYes"));
			obar.put(getStaticJsonObject("highBloodPressure"));
			obar.put(getStaticJsonObject("diabetes"));
			obar.put(getStaticJsonObject("tuberculosis"));
			obar.put(getStaticJsonObject("otherSymptoms"));
		}else{
			obar.put(getStaticJsonObject("healthCareGivenNo"));
		}
		
		return obar;
	}
	
// not needed if we put static json object in observation : February 4, 2019
/*	public JSONObject setCommonObservationInfo(HashMap<String, Object> commonValuesMap) throws JSONException {
		JSONObject observationObject = new JSONObject();
		JSONObject conceptObject = new JSONObject();
		String uuid = (String) commonValuesMap.get("concept_uuid");
		String name = (String) commonValuesMap.get("concept_name");
		conceptObject.put("uuid", uuid);
		conceptObject.put("name", name);
		observationObject.put("concept", conceptObject);

		String formNamespace = (String) commonValuesMap.get("formNamespace");
		observationObject.put("formNamespace", formNamespace);
		String formFieldPath = (String) commonValuesMap.get("formFieldPath");
		observationObject.put("formFieldPath", formFieldPath);
		boolean voided = (boolean) commonValuesMap.get("voided");
		observationObject.put("voided", voided);
		Object interpretation = commonValuesMap.get("interpretation");
		observationObject.put("interpretation", interpretation);
		JSONArray groupMembers = (JSONArray)commonValuesMap.get("groupMembers");
		observationObject.put("groupMembers", groupMembers);
		boolean inactive = (boolean) commonValuesMap.get("inactive");
		observationObject.put("inactive", inactive);
		return observationObject;
	}*/
	
	public JSONObject buildUpdateEncounter(Event e) throws JSONException {
		String openmrsuuid = e.getIdentifier(OPENMRS_UUID_IDENTIFIER_TYPE);
		JSONObject encounterObsUuids = getObsByEncounterUuid(openmrsuuid);
		JSONArray obsUuids = encounterObsUuids.getJSONArray("obs");
		
		System.out.print("[OBS-UUIDS]" + obsUuids);
		
		JSONObject pt = patientService.getPatientByIdentifier(e.getBaseEntityId());//TODO find by any identifier
		JSONObject enc = new JSONObject();
		
		JSONObject pr = userService.getPersonByUser(e.getProviderId());
		
		enc.put("encounterDatetime", OPENMRS_DATE.format(e.getEventDate().toDate()));
		// patient must be existing in OpenMRS before it submits an encounter. if it doesnot it would throw NPE
		enc.put("patient", pt.getString("uuid"));
		//TODO	enc.put("patientUuid", pt.getString("uuid"));
		enc.put("encounterType", e.getEventType());
		enc.put("location", e.getLocationId());
		//enc.put("provider", pr.has("uuid") ? pr.getString("uuid") : "");
		
		List<Obs> ol = e.getObs();
		Map<String, JSONArray> p = new HashMap<>();
		Map<String, JSONArray> pc = new HashMap<>();
		
		if (ol != null)
			for (Obs obs : ol) {
				if (!StringUtils.isEmptyOrWhitespaceOnly(obs.getFieldCode())
				        && (obs.getFieldType() == null || obs.getFieldType().equalsIgnoreCase("concept"))) {//skipping empty obs
					//if no parent simply make it root obs
					if (StringUtils.isEmptyOrWhitespaceOnly(obs.getParentCode())) {
						p.put(obs.getFieldCode(), convertObsToJson(obs));
					} else {
						//find parent obs if not found search and fill or create one
						JSONArray parentObs = p.get(obs.getParentCode());
						if (parentObs == null) {
							p.put(obs.getParentCode(), convertObsToJson(getOrCreateParent(ol, obs)));
						}
						// find if any other exists with same parent if so add to the list otherwise create new list
						JSONArray obl = pc.get(obs.getParentCode());
						if (obl == null) {
							obl = new JSONArray();
						}
						JSONArray addobs = convertObsToJson(obs);
						for (int i = 0; i < addobs.length(); i++) {
							obl.put(addobs.getJSONObject(i));
						}
						pc.put(obs.getParentCode(), obl);
					}
				}
			}
		
		JSONArray obar = new JSONArray();
		for (String ok : p.keySet()) {
			for (int i = 0; i < p.get(ok).length(); i++) {
				JSONObject obo = p.get(ok).getJSONObject(i);
				obo.put("uuid", getObsUuid(obo, obsUuids));
				
				JSONArray cob = pc.get(ok);
				if (cob != null && cob.length() > 0) {
					// Fetch children obs uuids
					JSONObject obsGroupUuids = getObsUuidByParentObsUuid(obo.getString("uuid"));
					JSONArray groupUuids = obsGroupUuids.getJSONArray("groupMembers");
					// Add uuids to group members
					for (int j = 0; j < cob.length(); j++) {
						JSONObject cobObj = cob.getJSONObject(j);
						cobObj.put("uuid", getObsUuid(cobObj, groupUuids));
					}
					
					obo.put("groupMembers", cob);
				}
				
				obar.put(obo);
			}
		}
		//enc.put("obs", obar);
		
		return enc;
	}
	
	public JSONObject updateEncounter(Event e) throws JSONException {
		if (StringUtils.isEmptyOrWhitespaceOnly(e.getIdentifier(OPENMRS_UUID_IDENTIFIER_TYPE))) {
			throw new IllegalArgumentException("Encounter was never pushed to OpenMRS as " + OPENMRS_UUID_IDENTIFIER_TYPE
			        + " is empty. Consider creating a new one");
		}
		
		String openmrsuuid = e.getIdentifier(OPENMRS_UUID_IDENTIFIER_TYPE);
		
		JSONObject enc = buildUpdateEncounter(e);
		
		HttpResponse op = HttpUtil.post(HttpUtil.removeEndingSlash(OPENMRS_BASE_URL) + "/" + ENCOUNTER_URL + "/"
		        + openmrsuuid, "", enc.toString(), OPENMRS_USER, OPENMRS_PWD);
		return new JSONObject(op.body());
	}
	
	private String getObsUuid(JSONObject obs, JSONArray obsUuids) throws JSONException {
		String uuid = "";
		// obs = {"concept":"163137AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"}
		// obsUuids = [{"concept":{"uuid":"163137AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"},"uuid":"b267b2f5-94be-43e8-85c4-4e36f2eb8471"}, {}]
		
		for (int i = 0; i < obsUuids.length(); i++) {
			JSONObject obsUuid = obsUuids.getJSONObject(i);
			JSONObject conceptObj = obsUuid.getJSONObject("concept");
			
			if (conceptObj.get("uuid").equals(obs.get("concept"))) {
				return obsUuid.getString("uuid");
			}
		}
		
		return uuid;
	}
	
	private JSONArray convertObsToJson(Obs o) throws JSONException {
		JSONArray arr = new JSONArray();
		if (o.getValues() == null || o.getValues().size() == 0) {//must be parent of some obs
			JSONObject obo = new JSONObject();
			obo.put("concept", o.getFieldCode());
			
			arr.put(obo);
		} else {
			//OpenMRS can not handle multivalued obs so add obs with multiple values as two different obs
			for (Object v : o.getValues()) {
				JSONObject obo = new JSONObject();
				obo.put("concept", o.getFieldCode());
				obo.put("value", v);
				
				arr.put(obo);
			}
		}
		return arr;
	}
	
	private Obs getOrCreateParent(List<Obs> obl, Obs o) {
		for (Obs obs : obl) {
			if (o.getParentCode().equalsIgnoreCase(obs.getFieldCode())) {
				return obs;
			}
		}
		return new Obs("concept", "parent", o.getParentCode(), null, null, null, null);
	}
	
	// TODO needs review and refactor
	public Event makeNewEventForNewClient(Client c, String eventType, String entityType) {
		Event event = new Event();
		try {
			String locationId = "";
			String ward = c.getAddresses().get(0).getAddressField("address2");
			org.opensrp.api.domain.Location location = null;
			location = openmrsLocationService.getLocation(ward);
			locationId = location.getLocationId();
			
			event.setServerVersion(System.currentTimeMillis());
			event.setTeam("");
			event.setTeamId("");
			event.setBaseEntityId(c.getBaseEntityId());
			event.setDateCreated(new DateTime());
			event.setEventDate(new DateTime());
			event.withProviderId("");
			event.setVersion(System.currentTimeMillis());
			event.setLocationId(locationId);
			event.setFormSubmissionId(UUID.randomUUID().toString().trim());
			event.withIsSendToOpenMRS("no").withEventType(eventType).withEntityType(entityType);
			List<String> eventAddress = new ArrayList<String>();
			eventAddress.add("BANGLADESH");
			eventAddress.add(c.getAddresses().get(0).getAddressField("stateProvince"));
			eventAddress.add(c.getAddresses().get(0).getAddressField("countyDistrict"));
			eventAddress.add(c.getAddresses().get(0).getAddressField("cityVillage"));
			eventAddress.add(c.getAddresses().get(0).getAddressField("address1"));
			eventAddress.add(c.getAddresses().get(0).getAddressField("address2"));
			JSONArray addressFieldValue = new JSONArray(eventAddress);
			event.addObs(new Obs("formsubmissionField", "text", "HIE_FACILITIES", "" /*//TODO handle parent*/,
			        addressFieldValue.toString(), ""/*comments*/, "HIE_FACILITIES"/*formSubmissionField*/));
			
			eventService.addorUpdateEvent(event);
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return event;
		
	}
	
	public Event convertToEvent(JSONObject encounter) throws JSONException {
		if (encounter.has("patientUuid") == false) {
			throw new IllegalStateException("No 'patient' object found in given encounter");
		}
		Event e = new Event();
		String patientId = encounter.getString("patientId");
		String patientUuid = encounter.getString("patientUuid");
		Client c = clientService.find(patientId);
		if (c == null || c.getBaseEntityId() == null) {
			//try to get the client from openmrs based on the uuid
			JSONObject openmrsPatient = patientService.getPatientByUuid(patientUuid, false);
			c = patientService.convertToClient(openmrsPatient);
			if (c == null || c.getBaseEntityId() == null) {
				throw new IllegalStateException(
				        "Client was not found registered while converting Encounter to an Event in OpenSRP");
			} else {
				//clientService.addClient(c);// currently not valid
			}
		}
		List<Event> events = eventService.findByBaseEntityId(c.getBaseEntityId());
		String providerId = "";
		if (events.size() != 0) {
			providerId = events.get(0).getProviderId();
		}
		//JSONObject creator = encounter.getJSONObject("auditInfo").getJSONObject("creator");
		e.withBaseEntityId(c.getBaseEntityId())
		//.withCreator(new User(creator.getString("uuid"), creator.getString("display"), null, null))
		        .withDateCreated(DateTime.now());
		
		e.withEventDate(new DateTime(encounter.getString("encounterDateTime")))
		        //.withEntityType(entityType) //TODO
		        .withEventType(encounter.getString("encounterType"))
		        .withFormSubmissionId(encounter.getString("encounterUuid"))//TODO
		        .withLocationId(encounter.getString("locationUuid"))
		        //TODO manage providers and uuid in couch
		        .withProviderId(providerId);
		
		e.addIdentifier(OPENMRS_UUID_IDENTIFIER_TYPE, encounter.getString("encounterUuid"));
		
		JSONArray ol = encounter.getJSONArray("observations");
		for (int i = 0; i < ol.length(); i++) {
			JSONObject o = ol.getJSONObject(i);
			List<Object> values = new ArrayList<Object>();
			List<Object> humanReadableValues = new ArrayList<Object>();
			if (o.optJSONObject("value") != null) {
				values.add(o.getString("valueAsString"));
				humanReadableValues.add(o.getJSONObject("value").getString("name"));
			} else if (o.has("value")) {
				values.add(o.getString("value"));
				humanReadableValues.add(o.getString("value"));
			}
			String fieldDataType = o.getJSONObject("concept").getString("dataType");
			if ("N/A".equalsIgnoreCase(fieldDataType)) {
				fieldDataType = "text";
			}
			
			e.addObs(new Obs("concept", fieldDataType, o.getJSONObject("concept").getString("uuid"),
			        "" /*//TODO handle parent*/, values, humanReadableValues, ""/*comments*/, o.getJSONObject("concept")
			                .getString("shortName")/*formSubmissionField*/));
		}
		
		return e;
	}
	
	
	public JSONObject getStaticJsonObject(String nameOfJSONObject) {
		JSONObject normalDisease = null;
		JSONObject objectToReturn = null;
		JSONObject diabetes = null;
		JSONObject highBloodPressure = null;
		JSONObject healthCareGivenYes = null;
		JSONObject healthCareGivenNo = null;
		JSONObject tuberculosis = null;
		JSONObject otherSymptoms = null;
		try {
			normalDisease = new JSONObject("{\"encounterTypeUuid\":\"81852aee-3f10-11e4-adec-0800271c1b75\",\"visitType\":\"Community clinic service\",\"patientUuid\":\"391ec594-5381-4075-9b1d-7608ed19332d\",\"locationUuid\":\"ec9bfa0e-14f2-440d-bf22-606605d021b2\",\"providers\":[{\"uuid\":\"313c8507-9821-40e4-8a70-71a5c7693d72\"}]}");
			diabetes = new JSONObject("{\"concept\":{\"uuid\":\"a725f0d7-067b-492d-a450-4ce7e535c371\",\"name\":\"Possible_Disease\"},\"formNamespace\":\"Bahmni\",\"formFieldPath\":\"সাধারন রোগীর সেবা.19/31-0\",\"voided\":false,\"value\":{\"uuid\":\"1e3f1870-b252-4808-8edb-f86fad050ebd\",\"name\":{\"display\":\"Diabetes\",\"uuid\":\"befce65b-9e80-45ec-b8b7-05234cd5cb9c\",\"name\":\"Diabetes\",\"locale\":\"en\",\"localePreferred\":true,\"conceptNameType\":null,\"resourceVersion\":\"1.9\"},\"displayString\":\"Diabetes\",\"resourceVersion\":\"2.0\",\"translationKey\":\"ডায়াবেটিস_31\"},\"inactive\":false,\"groupMembers\":[]}");
			healthCareGivenYes = new JSONObject("{\"concept\":{\"uuid\":\"f2671938-ffc5-4547-91c0-fcd28b6e29b4\",\"name\":\"Provide_Health_Service\"},\"formNamespace\":\"Bahmni\",\"formFieldPath\":\"সাধারন রোগীর সেবা.19/43-0\",\"voided\":false,\"value\":{\"uuid\":\"a2065636-5326-40f5-aed6-0cc2cca81ccc\",\"name\":{\"display\":\"Yes\",\"uuid\":\"b5a4d83a-7158-4477-b81c-71144f5a7232\",\"name\":\"Yes\",\"locale\":\"en\",\"localePreferred\":true,\"conceptNameType\":null,\"resourceVersion\":\"1.9\"},\"displayString\":\"Yes\",\"resourceVersion\":\"2.0\",\"translationKey\":\"হ্যাঁ_43\"},\"interpretation\":null,\"inactive\":false,\"groupMembers\":[]}");
			highBloodPressure = new JSONObject("{\"concept\":{\"uuid\":\"a725f0d7-067b-492d-a450-4ce7e535c371\",\"name\":\"Possible_Disease\"},\"formNamespace\":\"Bahmni\",\"formFieldPath\":\"সাধারন রোগীর সেবা.19/31-0\",\"voided\":false,\"value\":{\"uuid\":\"c2bb6edf-18cb-4c7f-ad91-7c8dd561a437\",\"name\":{\"display\":\"High Blood Pressure\",\"uuid\":\"c2bb6edf-18cb-4c7f-ad91-7c8dd561a437\",\"name\":\"High Blood Pressure\",\"locale\":\"en\",\"localePreferred\":true,\"conceptNameType\":null,\"resourceVersion\":\"1.9\"},\"displayString\":\"High Blood Pressure\",\"resourceVersion\":\"2.0\",\"translationKey\":\"উচ্চ_রক্তচাপ_31\"},\"inactive\":false,\"groupMembers\":[]}");
			healthCareGivenNo = new JSONObject("{\"groupMembers\":[],\"inactive\":false,\"interpretation\":null,\"concept\":{\"name\":\"Provide_Health_Service\",\"uuid\":\"f2671938-ffc5-4547-91c0-fcd28b6e29b4\"},\"formNamespace\":\"Bahmni\",\"formFieldPath\":\"সাধারন রোগীর সেবা.19/43-0\",\"voided\":false,\"value\":{\"translationKey\":\"না_43\",\"displayString\":\"No\",\"resourceVersion\":\"2.0\",\"name\":{\"display\":\"No\",\"resourceVersion\":\"1.9\",\"name\":\"No\",\"localePreferred\":true,\"locale\":\"en\",\"uuid\":\"17432139-eeca-4cf5-b0fd-00a6a4f83395\",\"conceptNameType\":null},\"uuid\":\"b497171e-0410-4d8d-bbd4-7e1a8f8b504e\"}}");
			tuberculosis = new JSONObject("{\"groupMembers\":[],\"inactive\":false,\"concept\":{\"name\":\"Possible_Disease\",\"uuid\":\"a725f0d7-067b-492d-a450-4ce7e535c371\"},\"formNamespace\":\"Bahmni\",\"formFieldPath\":\"সাধারন রোগীর সেবা.19/31-0\",\"voided\":false,\"value\":{\"translationKey\":\"যক্ষ্মা_31\",\"displayString\":\"Tuberculosis\",\"resourceVersion\":\"2.0\",\"name\":{\"display\":\"Tuberculosis\",\"resourceVersion\":\"1.9\",\"name\":\"Tuberculosis\",\"localePreferred\":true,\"locale\":\"en\",\"uuid\":\"d1183ae6-825f-478b-abd7-225d2a234da5\",\"conceptNameType\":null},\"uuid\":\"0622f52f-0c95-41c1-ab5d-ee9bc335c839\"}}");
			otherSymptoms = new JSONObject("{\"groupMembers\":[],\"inactive\":false,\"concept\":{\"name\":\"রোগের লক্ষণ\",\"uuid\":\"2cee2850-1a33-4a22-b933-35fbe6409c0e\"},\"formNamespace\":\"Bahmni\",\"formFieldPath\":\"সাধারন রোগীর সেবা.19/29-0\",\"voided\":false,\"value\":{\"translationKey\":\"অন্যান্য_লক্ষন_29\",\"displayString\":\"Other_Symptoms\",\"resourceVersion\":\"2.0\",\"name\":{\"display\":\"Other_Symptoms\",\"resourceVersion\":\"1.9\",\"name\":\"Other_Symptoms\",\"localePreferred\":true,\"locale\":\"en\",\"uuid\":\"ea20fe61-50f9-4c75-aa1f-9d4490d16513\",\"conceptNameType\":null},\"uuid\":\"37e04caf-e1cb-4438-bc55-3dbfa19cc14a\"}}");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(nameOfJSONObject.equals("normalDisease")){
			objectToReturn = normalDisease;
		}else if(nameOfJSONObject.equals("healthCareGivenYes")){
			objectToReturn = healthCareGivenYes;
		}else if(nameOfJSONObject.equals("healthCareGivenNo")){
			objectToReturn = healthCareGivenNo;
		}else if(nameOfJSONObject.equals("highBloodPressure")){
			objectToReturn = highBloodPressure;
		}else if(nameOfJSONObject.equals("diabetes")){
			objectToReturn = diabetes;
		}else if(nameOfJSONObject.equals("tuberculosis")){
			objectToReturn = tuberculosis;
		}else if(nameOfJSONObject.equals("otherSymptoms")){
			objectToReturn = otherSymptoms;
		}
		return objectToReturn;
	}
}
