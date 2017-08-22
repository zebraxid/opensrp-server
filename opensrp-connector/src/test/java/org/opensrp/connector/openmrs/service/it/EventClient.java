package org.opensrp.connector.openmrs.service.it;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.domain.Obs;

public class EventClient {
	
	public EventClient() {
		// TODO Auto-generated constructor stub
	}
	
	public static Event getEvent() {
		Event expectedEvent = new Event("2", "TestEncounter", new DateTime(0l, DateTimeZone.UTC), "entityType",
		        "providerId", "locationId", "formSubmissionId");
		expectedEvent.addIdentifier("key", "value");
		Obs obs = new Obs();
		obs.setFieldCode("163260AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		obs.setFieldDataType("text");
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
		Client expectedClient = new Client("27").withFirstName("motherName").withGender("male")
		        .withBirthdate(new DateTime(), false);
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put("M_ZEIR_ID", "159451-37_mothers");
		expectedClient.setIdentifiers(identifiers);
		return expectedClient;
	}
	
	public static Client getChildClient() {
		Client expectedClient = new Client("29").withFirstName("childName").withGender("male")
		        .withBirthdate(new DateTime(), false);
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put("ZEIR_ID", "159451-37_child");
		expectedClient.setIdentifiers(identifiers);
		List<String> list = new ArrayList<>();
		list.add("27");
		Map<String, List<String>> relationships = new HashMap<>();
		relationships.put("mother", list);
		expectedClient.setRelationships(relationships);
		return expectedClient;
	}
	
	public static Client getMother1Client() {
		Client expectedClient = new Client("53").withFirstName("testmotherName").withGender("male")
		        .withBirthdate(new DateTime(), false);
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put("M_ZEIR_ID", "159451-53_mothers");
		expectedClient.setIdentifiers(identifiers);
		return expectedClient;
	}
	
	public static Client getChild1Client() {
		Client expectedClient = new Client("52").withFirstName("testchildName").withGender("male")
		        .withBirthdate(new DateTime(), false);
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put("ZEIR_ID", "159451-52_child");
		expectedClient.setIdentifiers(identifiers);
		List<String> list = new ArrayList<>();
		list.add("53");
		Map<String, List<String>> relationships = new HashMap<>();
		relationships.put("mother", list);
		expectedClient.setRelationships(relationships);
		return expectedClient;
	}
	
	public static Event getEvent1() {
		Event expectedEvent = new Event("2", "Test", new DateTime(0l, DateTimeZone.UTC), "entityTypess", "sumon",
		        "locationIds", "formSubmissionId");
		expectedEvent.addIdentifier("key", "values");
		Obs obs = new Obs();
		obs.setFieldCode("163260AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		obs.setFieldDataType("text");
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
}
