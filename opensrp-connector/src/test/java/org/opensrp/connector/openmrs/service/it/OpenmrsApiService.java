package org.opensrp.connector.openmrs.service.it;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.common.util.HttpResponse;
import org.opensrp.common.util.HttpUtil;
import org.opensrp.connector.openmrs.service.OpenmrsLocationService;
import org.opensrp.connector.openmrs.service.TestResourceLoader;

public abstract class OpenmrsApiService extends TestResourceLoader {
	
	OpenmrsLocationService ls;
	
	public OpenmrsApiService() throws IOException {
		ls = new OpenmrsLocationService(openmrsOpenmrsUrl, openmrsUsername, openmrsPassword);
	}
	
	final String OPENMRS_URL = openmrsOpenmrsUrl;
	
	final String PERSON_URL = "ws/rest/v1/person";
	
	final String USER_URL = "ws/rest/v1/user";
	
	final String PERSON_ATTRIBUTE_TYPE = "ws/rest/v1/personattributetype";
	
	final String ENCOUTER_TYPE_URL = "ws/rest/v1/encountertype";
	
	final String RELATIONSHIP_TYPE = "/ws/rest/v1/relationshiptype/";
	
	final String LOCATION = "ws/rest/v1/location";
	
	JSONObject person = new JSONObject();
	
	JSONObject personAttributeType = new JSONObject();
	
	final static String ageKey = "age";
	
	final static String genderKey = "gender";
	
	final static String birthdateKey = "birthdate";
	
	final static String namesKey = "names";
	
	final static String purgePartUrl = "?purge=true";
	
	final static String nameKey = "name";
	
	final static String descriptionkey = "description";
	
	final static String usernameKey = "username";
	
	final static String passwordKey = "password";
	
	final static String personKey = "person";
	
	final static String formatKey = "format";
	
	String aIsToBKey = "aIsToB";
	
	String bIsToAKey = "bIsToA";
	
	String description = "description";
	
	String uuidKey = "uuid";
	
	String displayKey = "display";
	
	String encountersKey = "encounters";
	
	String relationshipsKey = "relationships";
	
	String encounterTypeKey = "encounterType";
	
	String identifierKey = "identifier";
	
	String relationKey = "relation";
	
	String childrenKey = "children";
	
	String labelKey = "label";
	
	String nodeKey = "node";
	
	String parentLocationKey = "parentLocation";
	
	String locationsHierarchyKey = "locationsHierarchy";
	
	int indexKey = 0;
	
	public JSONObject createPerson(String fn, String mn, String ln) throws JSONException {
		
		person.put(genderKey, "F");
		person.put(birthdateKey, "2017-01-01");
		person.put(ageKey, "32");
		person.put(namesKey, new JSONArray("[{\"givenName\":\"" + fn + "\",\"middleName\":\"" + mn + "\", \"familyName\":\""
		        + ln + "\"}]"));
		String response = HttpUtil.post(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/" + PERSON_URL, "", person.toString(),
		    openmrsUsername, openmrsPassword).body();
		
		return new JSONObject(response);
		
	}
	
	public void deletePerson(String uuid) {
		HttpUtil.delete(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/ws/rest/v1/person/" + uuid + "?purge=true", "",
		    openmrsUsername, openmrsPassword);
	}
	
	public JSONObject createUser(String userName, String password, String fn, String mn, String ln) throws JSONException {
		
		person.put(genderKey, "F");
		person.put(birthdateKey, "2017-01-01");
		person.put(ageKey, "32");
		person.put(namesKey, new JSONArray("[{\"givenName\":\"" + fn + "\",\"middleName\":\"" + mn + "\", \"familyName\":\""
		        + ln + "\"}]"));
		JSONObject user = new JSONObject();
		user.put(usernameKey, userName);
		user.put(passwordKey, password);
		user.put(personKey, person);
		String response = HttpUtil.post(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/" + USER_URL, "", user.toString(),
		    openmrsUsername, openmrsPassword).body();
		
		return new JSONObject(response);
		
	}
	
	public JSONObject createPersonAttributeType(String desc, String name) throws JSONException {
		
		personAttributeType.put(descriptionkey, desc);
		personAttributeType.put(nameKey, name);
		personAttributeType.put(formatKey, "java.lang.String");
		String response = HttpUtil.post(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/" + PERSON_ATTRIBUTE_TYPE, "",
		    personAttributeType.toString(), openmrsUsername, openmrsPassword).body();
		
		return new JSONObject(response);
		
	}
	
	public void deletePersonAttributeType(String uuid) {
		HttpResponse od = HttpUtil.delete(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/ws/rest/v1/personattributetype/"
		        + uuid + purgePartUrl, "", openmrsUsername, openmrsPassword);
	}
	
	public void deleteUser(String uuid) {
		HttpResponse od = HttpUtil.delete(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/ws/rest/v1/user/" + uuid
		        + purgePartUrl, "", openmrsUsername, openmrsPassword);
	}
	
	public void deleteIdentifierType(String uuid) {
		HttpUtil.delete(
		    HttpUtil.removeEndingSlash(OPENMRS_URL) + "/ws/rest/v1/patientidentifiertype/" + uuid + purgePartUrl, "",
		    openmrsUsername, openmrsPassword);
	}
	
	public void deleteProvider(String uuid) {
		
		HttpUtil.delete(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/ws/rest/v1/provider/" + uuid + purgePartUrl, "",
		    openmrsUsername, openmrsPassword);
		
	}
	
	public void deleteRelation(String uuid) {
		HttpUtil.delete(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/ws/rest/v1/relationship/" + uuid + purgePartUrl, "",
		    openmrsUsername, openmrsPassword);
	}
	
	public JSONObject createEncounterType(String name, String desc) throws JSONException {
		
		JSONObject encounterType = new JSONObject();
		encounterType.put(nameKey, name);
		encounterType.put(descriptionkey, desc);
		String response = HttpUtil.post(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/" + ENCOUTER_TYPE_URL, "",
		    encounterType.toString(), openmrsUsername, openmrsPassword).body();
		
		return new JSONObject(response);
		
	}
	
	public void deleteEncounterType(String uuid) {
		
		HttpUtil.delete(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/ws/rest/v1/encountertype/" + uuid + purgePartUrl, "",
		    openmrsUsername, openmrsPassword);
		
	}
	
	public void deleteEncounter(String uuid) {
		
		HttpUtil.delete(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/ws/rest/v1/encounter/" + uuid + purgePartUrl, "",
		    openmrsUsername, openmrsPassword);
		
	}
	
	public void deleteRelationshipType(String uuid) {
		
		HttpUtil.delete(HttpUtil.removeEndingSlash(OPENMRS_URL) + RELATIONSHIP_TYPE + uuid + purgePartUrl, "",
		    openmrsUsername, openmrsPassword);
		
	}
	
	public JSONObject createLocation(String name, String parentLocation) throws JSONException {
		JSONObject location = new JSONObject();
		String response = "";
		location.put(nameKey, name);
		if (parentLocation != null || !parentLocation.isEmpty()) {
			location.put("parentLocation", parentLocation);
		}
		response = HttpUtil.post(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/" + LOCATION, "", location.toString(),
		    openmrsUsername, openmrsPassword).body();
		return new JSONObject(response);
		
	}
	
	public void deleteLocation(String uuid) {
		
		HttpUtil.delete(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/" + LOCATION + "/" + uuid + "?purge=true", "",
		    openmrsUsername, openmrsPassword);
		
	}
	
	public void sample() throws IOException, JSONException {
		
		String fn = "jack";
		String mn = "mil";
		String ln = "nil";
		String userName = "joaki";
		String password = "sd@@fggWW1";
		JSONObject person = createPerson(fn, mn, ln);
		JSONObject user = createUser(userName, password, fn, mn, ln);
		JSONObject pr = user.getJSONObject("person");
		String uuid = pr.getString("uuid");
		
		deletePerson(uuid);
		
		//openmrsApiService.deleteUser(user.getString("uuid"));
	}
}
