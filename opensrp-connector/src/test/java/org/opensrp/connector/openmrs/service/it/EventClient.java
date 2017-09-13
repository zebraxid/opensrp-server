package org.opensrp.connector.openmrs.service.it;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
	
	final static String description = "description";
	
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
		Client expectedClient = new Client("2455").withFirstName("motherName").withGender("male")
		        .withBirthdate(new DateTime(), false);
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put(M_ZEIR_ID, "159451-r7_mothers");
		expectedClient.setIdentifiers(identifiers);
		return expectedClient;
	}
	
	public static Client getChildClient() {
		Client expectedClient = new Client("errrr29").withFirstName("childName").withGender("male")
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
		Client expectedClient = new Client("53").withFirstName("testmotherName").withGender("male")
		        .withBirthdate(new DateTime(), false);
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put(M_ZEIR_ID, "159451-53_mothers");
		expectedClient.setIdentifiers(identifiers);
		return expectedClient;
	}
	
	public static Client getChild1Client() {
		Client expectedClient = new Client("52").withFirstName("testchildName").withGender("male")
		        .withBirthdate(new DateTime(), false);
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put(ZEIR_ID, "159451-52_child");
		expectedClient.setIdentifiers(identifiers);
		List<String> list = new ArrayList<>();
		list.add("53");
		Map<String, List<String>> relationships = new HashMap<>();
		relationships.put(mother, list);
		expectedClient.setRelationships(relationships);
		return expectedClient;
	}
	
	public static JSONObject getCreatedPatientData(String fn, String mn, String ln, String OpenSRPThriveUID,
	                                               String attributeName) throws JSONException {
		
		Map<String, String> addressFields = new HashMap<>();
		addressFields.put("ADDRESS1", "ADDRESS1");
		addressFields.put("ADDRESS2", "ADDRESS2");
		addressFields.put("ADDRESS3", "ADDRESS3");
		addressFields.put("ADDRESS4", "ADDRESS4");
		addressFields.put("ADDRESS4", "ADDRESS4");
		
		Map<String, Object> attributes = new HashMap<>();
		
		attributes.put(attributeName, "test value");
		List<Address> addresses = new ArrayList<>();
		addresses.add(new Address("BIRTH", DateTime.now(), DateTime.now(), addressFields, "LAT", "LON", "PCODE", "SINDH",
		        "PK"));
		addresses.add(new Address("DEATH", DateTime.now(), DateTime.now(), addressFields, "LATd", "LONd", "dPCODE", "KPK",
		        "PKA"));
		Map<String, Object> attribs = new HashMap<>();
		
		Client c = new Client(UUID.randomUUID().toString()).withFirstName(fn).withMiddleName(mn).withLastName(ln)
		        .withBirthdate(new DateTime(), true).withDeathdate(new DateTime(), false).withGender("MALE");
		
		c.withAddresses(addresses).withAttributes(attributes);
		c.withIdentifier("OpenSRP Thrive UID", OpenSRPThriveUID);
		JSONObject patient = patientService.createPatient(c);
		return patient;
		
	}
}
