package org.opensrp.connector.openmrs.service.it;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.common.util.HttpResponse;
import org.opensrp.common.util.HttpUtil;
import org.opensrp.connector.openmrs.service.TestResourceLoader;

public abstract class OpenmrsApiService extends TestResourceLoader {
	
	public OpenmrsApiService() throws IOException {
		// TODO Auto-generated constructor stub
	}
	
	String OPENMRS_URL = openmrsOpenmrsUrl;
	
	String PERSON_URL = "ws/rest/v1/person";
	
	String USER_URL = "ws/rest/v1/user";
	
	String PERSON_ATTRIBUTE_TYPE = "ws/rest/v1/personattributetype";
	
	JSONObject person = new JSONObject();
	
	JSONObject personAttributeType = new JSONObject();
	
	public JSONObject createPerson(String fn, String mn, String ln) throws JSONException {
		
		person.put("gender", "F");
		person.put("birthdate", "2017-01-01");
		person.put("age", "32");
		person.put("names", new JSONArray("[{\"givenName\":\"" + fn + "\",\"middleName\":\"" + mn + "\", \"familyName\":\""
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
		
		person.put("gender", "F");
		person.put("birthdate", "2017-01-01");
		person.put("age", "32");
		person.put("names", new JSONArray("[{\"givenName\":\"" + fn + "\",\"middleName\":\"" + mn + "\", \"familyName\":\""
		        + ln + "\"}]"));
		JSONObject user = new JSONObject();
		user.put("username", userName);
		user.put("password", password);
		user.put("person", person);
		String response = HttpUtil.post(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/" + USER_URL, "", user.toString(),
		    openmrsUsername, openmrsPassword).body();
		
		return new JSONObject(response);
		
	}
	
	public JSONObject createPersonAttributeType(String desc, String name) throws JSONException {
		
		personAttributeType.put("description", desc);
		personAttributeType.put("name", name);
		personAttributeType.put("format", "java.lang.String");
		String response = HttpUtil.post(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/" + PERSON_ATTRIBUTE_TYPE, "",
		    personAttributeType.toString(), openmrsUsername, openmrsPassword).body();
		
		return new JSONObject(response);
		
	}
	
	public void deletePersonAttributeType(String uuid) {
		HttpResponse od = HttpUtil.delete(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/ws/rest/v1/personattributetype/"
		        + uuid + "?purge=true", "", openmrsUsername, openmrsPassword);
	}
	
	public void deleteUser(String uuid) {
		HttpResponse od = HttpUtil.delete(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/ws/rest/v1/user/" + uuid
		        + "?purge=true", "", openmrsUsername, openmrsPassword);
	}
	
	public void deleteIdentifierType(String uuid) {
		HttpUtil.delete(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/ws/rest/v1/patientidentifiertype/" + uuid
		        + "?purge=true", "", openmrsUsername, openmrsPassword);
	}
	
	public void deleteProvider(String uuid) {
		
		HttpUtil.delete(HttpUtil.removeEndingSlash(OPENMRS_URL) + "/ws/rest/v1/provider/" + uuid + "?purge=true", "",
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
