package org.opensrp.connector.openmrs.service.it;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.scheduler.domain.MotechEvent;
import org.opensrp.connector.openmrs.schedule.OpenmrsSyncerListener;
import org.opensrp.connector.openmrs.service.EncounterService;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.connector.openmrs.service.PatientService;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.repository.AllClients;
import org.opensrp.repository.AllEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-connector.xml")
public class OpenmrsSyncerListenerTest extends OpenmrsApiService {
	
	public OpenmrsSyncerListenerTest() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private AllClients allClients;
	
	@Autowired
	private AllEvents allEvents;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private OpenmrsUserService openmrsUserService;
	
	@Autowired
	private OpenmrsSyncerListener openmrsSyncerListener;
	
	@Autowired
	private EncounterService encounterService;
	
	MotechEvent event = new MotechEvent("subject");
	
	@Before
	public void setup() {
		allEvents.removeAll();
		allClients.removeAll();
		
	}
	
	@After
	public void tearDown() {
		//allEvents.removeAll();
		//allClients.removeAll();
	}
	
	@Test
	public void testPushToOpenMRS() throws JSONException {
		
		Client expectedChildClient = EventClient.getChildClient();
		allClients.add(expectedChildClient);
		Client expectedMotherClient = EventClient.getMotherClient();
		allClients.add(expectedMotherClient);
		
		String identifierType = "OPENMRS_UUID";
		JSONObject expectedIdentifier = patientService.getIdentifierType("OPENMRS_UUID");
		
		if (!identifierType.equals(expectedIdentifier.get("display"))) {
			patientService.createIdentifierType(identifierType, identifierType);
		}
		
		JSONObject createdPatientJsonObject = openmrsSyncerListener.pushClient(0);
		
		JSONObject updatedPatient = openmrsSyncerListener.pushClient(0);
		
		JSONArray createdPatientJsonArray = createdPatientJsonObject.getJSONArray("patient");
		JSONArray createdRelationArray = createdPatientJsonObject.getJSONArray("relation");
		JSONArray updatedRelationArray = updatedPatient.getJSONArray("relation");
		String actualChildNname = "";
		String actualMotherName = "";
		
		JSONObject craetedRelationObject = createdRelationArray.getJSONObject(0);
		String createdRelationId = craetedRelationObject.getString("uuid");
		
		JSONObject updatedRelationObject = updatedRelationArray.getJSONObject(0);
		String updatedRelationId = updatedRelationObject.getString("uuid");
		
		deleteRelation(createdRelationId);
		deleteRelation(updatedRelationId);
		
		for (int i = 0; i < createdPatientJsonArray.length(); i++) {
			JSONObject patient = createdPatientJsonArray.getJSONObject(i);
			JSONObject person = patient.getJSONObject("person");
			deletePerson(person.getString("uuid"));// client person
			if (person.getString("gender").equalsIgnoreCase("male")) {
				actualChildNname = person.getString("display");
			} else if (person.getString("gender").equalsIgnoreCase("Female")) {
				actualMotherName = person.getString("display");
			}
		}
		
		JSONObject childFromRelation = craetedRelationObject.getJSONObject("personB");
		JSONObject motherFromRelation = craetedRelationObject.getJSONObject("personA");
		String actualMotherNameForRelation = motherFromRelation.getString("display");
		String actualChildNameForRelation = childFromRelation.getString("display");
		
		assertEquals(expectedChildClient.fullName() + " -", actualChildNameForRelation);
		assertEquals(expectedMotherClient.fullName() + " -", actualMotherNameForRelation);
		
		assertEquals(expectedChildClient.fullName() + " -", actualChildNname);
		assertEquals(expectedMotherClient.fullName() + " -", actualMotherName);
		
	}
	
	@Test
	public void testPushEvent() throws JSONException {
		
		Event creatingEvent = EventClient.getEvent();
		allEvents.add(creatingEvent);
		String IdentifierType = "TestIdentifierType";
		JSONObject identifier = patientService.createIdentifierType(IdentifierType, "description");
		String identifierUuid = identifier.getString("uuid");
		String fn = "jack";
		String mn = "bgu";
		String ln = "nil";
		String userName = "providerId";
		String password = "Dotel@1234";
		JSONObject person = createPerson(fn, mn, ln);
		JSONObject usr = createUser(userName, password, fn, mn, ln);
		
		openmrsUserService.createProvider(userName, IdentifierType);
		
		JSONObject provider = openmrsUserService.getProvider(IdentifierType);
		JSONObject personObject = provider.getJSONObject("person");
		
		JSONObject returnEncounterType = encounterService.createEncounterType("TestEncounter", "Test desc");
		JSONObject expectedEvent = openmrsSyncerListener.pushEvent(0);
		openmrsSyncerListener.pushEvent(0);
		deletePerson(person.getString("uuid"));
		deleteUser(usr.getString("uuid"));
		deleteIdentifierType(identifierUuid);
		deleteProvider(provider.getString("uuid"));
		deletePerson(personObject.getString("uuid").trim());
		deleteEncounter(expectedEvent.getString("uuid"));
		deleteEncounterType(returnEncounterType.getString("uuid"));
		JSONArray obsArray = expectedEvent.getJSONArray("obs");
		JSONObject obs = obsArray.getJSONObject(0);
		JSONArray encounterProviders = expectedEvent.getJSONArray("encounterProviders");
		JSONObject encounterProvider = encounterProviders.getJSONObject(0);
		JSONObject encounterType = expectedEvent.getJSONObject("encounterType");
		String expectedEncounterProvider = fn + " " + mn + " " + ln + ":" + " Unknown";
		String actualEncounterProvider = encounterProvider.getString("display");
		
		String expectedConceptOfObservation = "WHITE BLOOD CELLS: ";
		String actualConceptOfObservation = obs.getString("display");
		String expectedEncounterType = encounterType.getString("display");
		String actualEncounterType = "TestEncounter";
		assertEquals(expectedConceptOfObservation, actualConceptOfObservation);
		assertEquals(expectedEncounterProvider, actualEncounterProvider);
		assertEquals(expectedEncounterType, actualEncounterType);
	}
	
	@Ignore
	@Test
	public void test() {
		Client expectedChildClient = EventClient.getChild1Client();
		allClients.add(expectedChildClient);
		Client expectedMotherClient = EventClient.getMother1Client();
		allClients.add(expectedMotherClient);
		
		Event creatingEvent = EventClient.getEvent1();
		allEvents.add(creatingEvent);
		//openmrsSyncerListener.pushToOpenMRS(event);
		final TestLoggerAppender appender = new TestLoggerAppender();
		final Logger logger = Logger.getLogger(OpenmrsSyncerListener.class.toString());
		logger.setLevel(Level.ALL);
		logger.addAppender(appender);
		
		final List<LoggingEvent> log = appender.getLog();
		final LoggingEvent firstLogEntry = log.get(0);
		assertEquals(firstLogEntry.getRenderedMessage(), "Unparseable date: \"Not A Valid Date\"");
		logger.removeAllAppenders();
	}
}
