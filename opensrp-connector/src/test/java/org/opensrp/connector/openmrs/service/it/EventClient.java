package org.opensrp.connector.openmrs.service.it;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.connector.openmrs.service.PatientService;
import org.opensrp.domain.Address;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.domain.Obs;

public class EventClient extends OpenmrsApiService {
	
	final static String text = "text";
	
	final static String formSubmissionId = "formSubmissionId";
	
	final static String baseEntityId = "2";
	
	final static String M_ZEIR_ID = "M_ZEIR_ID";
	
	final static String ZEIR_ID = "ZEIR_ID";
	
	final static String mother = "mother";
	
	final static String female = "female";
	
	final static String male = "male";
	
	final static String description = "description";
	
	static String motherBaseEntityId = "53";
	
	static PatientService patientService = new PatientService(openmrsOpenmrsUrl, openmrsUsername, openmrsPassword);
	
	public EventClient() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static Event getEvent() {
		Event expectedEvent = new Event(baseEntityId, "TestEncounter", new DateTime(0l, DateTimeZone.UTC), "entityType",
		        "providerId", "locationId", formSubmissionId);
		expectedEvent.addIdentifier("key", "value");
		Obs obs = new Obs();
		obs.setFieldCode("163260AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		obs.setFieldDataType(text);
		obs.setFieldType("concept");
		obs.setParentCode("678AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		List<Object> values = new ArrayList<Object>();
		values.add("09-03-2017");
		obs.setValues(values);
		List<Obs> observations = new ArrayList<>();
		observations.add(obs);
		expectedEvent.setObs(observations);
		
		return expectedEvent;
	}
	
	public static Client getMotherClient() {
		String baseEntityId = "2455";
		Client expectedClient = new Client(baseEntityId).withFirstName("motherName").withGender(female)
		        .withBirthdate(new DateTime(), false);
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put(M_ZEIR_ID, "159451-r7_mothers");
		expectedClient.setIdentifiers(identifiers);
		return expectedClient;
	}
	
	public static Client getChildClient() {
		String baseEntityId = "errrr29";
		Client expectedClient = new Client(baseEntityId).withFirstName("childName").withGender(male)
		        .withBirthdate(new DateTime(), false);
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put(ZEIR_ID, "159451-r7_child");
		expectedClient.setIdentifiers(identifiers);
		List<String> list = new ArrayList<>();
		list.add("27");
		Map<String, List<String>> relationships = new HashMap<>();
		relationships.put(mother, list);
		expectedClient.setRelationships(relationships);
		return expectedClient;
	}
	
	public static Client getMother1Client() {
		Client expectedClient = new Client(motherBaseEntityId).withFirstName("testmotherName").withGender(female)
		        .withBirthdate(new DateTime(), false);
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put(M_ZEIR_ID, "159451-53_mothers");
		expectedClient.setIdentifiers(identifiers);
		return expectedClient;
	}
	
	public static Client getChild1Client() {
		String baseEntityId = "52";
		Client expectedClient = new Client(baseEntityId).withFirstName("testchildName").withGender(female)
		        .withBirthdate(new DateTime(), false);
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put(ZEIR_ID, "159451-52_child");
		expectedClient.setIdentifiers(identifiers);
		List<String> list = new ArrayList<>();
		list.add(motherBaseEntityId);
		Map<String, List<String>> relationships = new HashMap<>();
		relationships.put(mother, list);
		expectedClient.setRelationships(relationships);
		return expectedClient;
	}
	
	public static JSONObject getCreatedPatientData(String fn, String mn, String ln, String OpenSRPThriveUID,
	                                               String attributeName, String baseEntityId) throws JSONException {
		
		Map<String, String> addressFields = new HashMap<>();
		addressFields.put("ADDRESS1", "testAdress1");
		addressFields.put("ADDRESS2", "testAddress2");
		addressFields.put("ADDRESS3", "testAddress3");
		addressFields.put("ADDRESS4", "testAddress4");
		addressFields.put("ADDRESS4", "testAddress5");
		
		Map<String, Object> attributes = new HashMap<>();
		
		attributes.put(attributeName, "test value");
		List<Address> addresses = new ArrayList<>();
		addresses.add(new Address("BIRTH", DateTime.now(), DateTime.now(), addressFields, "LAT", "LON", "PCODE", "SINDH",
		        "PK"));
		addresses.add(new Address("DEATH", DateTime.now(), DateTime.now(), addressFields, "LATd", "LONd", "dPCODE", "KPK",
		        "PKA"));
		Map<String, Object> attribs = new HashMap<>();
		
		Client c = new Client(baseEntityId).withFirstName(fn).withMiddleName(mn).withLastName(ln)
		        .withBirthdate(new DateTime(), true).withDeathdate(new DateTime(), false).withGender("MALE");
		
		c.withAddresses(addresses).withAttributes(attributes);
		c.withIdentifier("OpenSRP Thrive UID", OpenSRPThriveUID);
		JSONObject patient = patientService.createPatient(c);
		return patient;
		
	}
}
