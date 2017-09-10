package org.opensrp.connector.openmrs.service.it;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static org.opensrp.dto.AlertStatus.normal;
import static org.opensrp.dto.BeneficiaryType.mother;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.connector.openmrs.constants.OpenmrsHouseHold;
import org.opensrp.connector.openmrs.service.EncounterService;
import org.opensrp.connector.openmrs.service.HouseholdService;
import org.opensrp.connector.openmrs.service.OpenmrsSchedulerService;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.connector.openmrs.service.PatientService;
import org.opensrp.domain.Address;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.dto.ActionData;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.service.formSubmission.FormEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.JsonIOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-connector.xml")
public class HouseHoldServiceTest extends OpenmrsApiService {
	
	public HouseHoldServiceTest() throws IOException {
		super();
	}
	
	@Autowired
	private EncounterService es;
	
	@Autowired
	private FormEntityConverter oc;
	
	@Autowired
	private PatientService ps;
	
	@Autowired
	private OpenmrsUserService us;
	
	@Autowired
	private HouseholdService hhs;
	
	@Autowired
	private OpenmrsSchedulerService ss;
	
	Map<Integer, String[]> datamap = new HashMap<Integer, String[]>() {
		
		private static final long serialVersionUID = 3137471662816047127L;
		{
			put(1, new String[] { "a3f2abf4-2699-4761-819a-cea739224164", "test" });
			put(2, new String[] { "0aac6d81-b51f-4096-b354-5a5786e406c8", "karim mia" });
			put(3, new String[] { "baf59aa4-64e9-46fc-99e6-8cd8f01618ff", "hasan ferox" });
			put(4, new String[] { "f92ee1b5-c3ce-42fb-bbc8-e01f474acc5a", "jashim mia" });
		}
	};
	
	Map<Integer, String[]> childdatamap = new HashMap<Integer, String[]>() {
		
		private static final long serialVersionUID = 3137471662816047127L;
		{
			put(1, new String[] { "babcd9d2-b3e9-4f6d-8a06-2df8f5fbf01f", "74eebb60-a1b9-4691-81a4-5c04ecce7ae9" });
			put(2, new String[] { "b19db74f-6e96-4652-a765-5078beb12434" });
			put(3, new String[] { "409b44c4-262a-40b8-ad7d-748c480c7c13" });
			put(4, new String[] { "0036b7ca-36ec-4242-9885-a0a03a666cda" });
		}
	};
	
	private final String hhRegistrationformName = "new_household_registration";
	
	@Before
	public void setup() throws IOException {
		
	}
	
	@Test
	public void testCreateRelationshipTypeAndGetRelationshipType() throws JSONException {
		String AIsToB = "Mother";
		String BIsToA = "GrandMother";
		JSONObject returnRelationshipType = hhs.createRelationshipType(AIsToB, BIsToA, "test relationship");
		String expectedAIsToB = AIsToB;
		String expectedBIsToA = BIsToA;
		String actualAIsToB = returnRelationshipType.getString("aIsToB");
		String actualBIsToA = returnRelationshipType.getString("bIsToA");
		String uuid = returnRelationshipType.getString("uuid");
		assertEquals(expectedAIsToB, actualAIsToB);
		assertEquals(expectedBIsToA, actualBIsToA);
		assertNotSame("2234frt", actualBIsToA);
		JSONObject findRelationshipType = hhs.findRelationshipTypeMatching(AIsToB);
		
		String actualForFindRelationshipTypeBIsToA = findRelationshipType.getString("bIsToA");
		String actualForFindRelationshipTypeAIsToB = findRelationshipType.getString("aIsToB");
		assertEquals(expectedAIsToB, actualForFindRelationshipTypeAIsToB);
		assertEquals(expectedBIsToA, actualForFindRelationshipTypeBIsToA);
		JSONObject getRelationshipType = hhs.getRelationshipType(AIsToB);
		String actualForGetRelationshipTypeBIsToA = getRelationshipType.getString("bIsToA");
		String actualForGetRelationshipTypeAIsToB = getRelationshipType.getString("aIsToB");
		assertEquals(expectedAIsToB, actualForGetRelationshipTypeAIsToB);
		assertEquals(expectedBIsToA, actualForGetRelationshipTypeBIsToA);
		assertNotSame("2234fr34t", actualForGetRelationshipTypeBIsToA);
		deleteRelationshipType(uuid);
		JSONObject getRelationshipTypeWhichNotExists = hhs.getRelationshipType("2234fr34t");
		if (getRelationshipTypeWhichNotExists.has("error")) {
			System.out.println("Not Found");
		}
		
	}
	
	@Test
	public void testConvertRelationshipToOpenmrsJson() throws JSONException {
		String expectedPersonA = "personA";
		String expectedPersonB = "personB";
		
		JSONObject convertRelationshipToOpenmrsJson = hhs.convertRelationshipToOpenmrsJson(expectedPersonA,
		    "isARelationship", expectedPersonB);
		String actualPersonA = convertRelationshipToOpenmrsJson.getString("personA");
		String actualPersonB = convertRelationshipToOpenmrsJson.getString("personB");
		assertEquals(expectedPersonA, actualPersonA);
		assertEquals(expectedPersonB, actualPersonB);
		assertNotSame("actualllllllll", actualPersonA);
		assertNotSame("actualllllllll", expectedPersonB);
		
	}
	
	@Test
	public void testConvertRelationshipTypeToOpenmrsJson() throws JSONException {
		String AIsToB = "Mother";
		String BIsToA = "GrandMother";
		JSONObject convertRelationshipTypeToOpenmrsJson = hhs.convertRelationshipTypeToOpenmrsJson(AIsToB, BIsToA,
		    "description");
		String expectedAIsToB = AIsToB;
		String expectedBIsToA = BIsToA;
		
		String actualAIsToB = convertRelationshipTypeToOpenmrsJson.getString("aIsToB");
		String actualBIsToA = convertRelationshipTypeToOpenmrsJson.getString("bIsToA");
		assertEquals(expectedAIsToB, actualAIsToB);
		assertEquals(expectedBIsToA, actualBIsToA);
		assertNotSame("2234frt", actualBIsToA);
	}
	
	@Test
	public void testHHScheduleData() throws JSONException, ParseException, JsonIOException, IOException {
		FormSubmission fs = getFormSubmissionFor("new_household_registration", 6);
		
		/*** create patient *******/
		
		String fn = "shumi";
		String mn = "sumaita";
		String ln = "khan";
		Map<String, String> addressFields = new HashMap<>();
		addressFields.put("ADDRESS1", "ADDRESS1");
		addressFields.put("ADDRESS2", "ADDRESS2");
		addressFields.put("ADDRESS3", "ADDRESS3");
		addressFields.put("ADDRESS4", "ADDRESS4");
		addressFields.put("ADDRESS4", "ADDRESS4");
		
		Map<String, Object> attributes = new HashMap<>();
		String attributeName = "HouseholdAttributeName";
		JSONObject attribute = createPersonAttributeType("Description", attributeName);
		attributes.put(attributeName, "test value");
		List<Address> addresses = new ArrayList<>();
		addresses.add(new Address("BIRTH", DateTime.now(), DateTime.now(), addressFields, "LAT", "LON", "PCODE", "SINDH",
		        "PK"));
		addresses.add(new Address("DEATH", DateTime.now(), DateTime.now(), addressFields, "LATd", "LONd", "dPCODE", "KPK",
		        "PK"));
		Map<String, Object> attribs = new HashMap<>();
		
		Client c = new Client(UUID.randomUUID().toString()).withFirstName(fn).withMiddleName(mn).withLastName(ln)
		        .withBirthdate(new DateTime(), true).withDeathdate(new DateTime(), false).withGender("MALE");
		
		c.withAddresses(addresses).withAttributes(attributes);
		c.withIdentifier("OpenSRP Thrive UID", "a3f2abf4-2699-4761-819a-cea739224164");
		JSONObject patient = patientService.createPatient(c);
		System.err.println("Patient:" + patient);
		Client c1 = new Client(UUID.randomUUID().toString()).withFirstName(fn).withMiddleName(mn).withLastName(ln)
		        .withBirthdate(new DateTime(), true).withDeathdate(new DateTime(), false).withGender("MALE");
		
		c1.withAddresses(addresses).withAttributes(attributes);
		c1.withIdentifier("OpenSRP Thrive UID", "babcd9d2-b3e9-4f6d-8a06-2df8f5fbf01f");
		JSONObject patients = patientService.createPatient(c1);
		System.err.println("Patient:" + patients);
		/**** create provider *********/
		String IdentifierType = "TestIdentifierType";
		JSONObject identifier = patientService.createIdentifierType(IdentifierType, "description");
		String identifierUuid = identifier.getString("uuid");
		
		String userName = "adminadmin";
		String password = "Dotel@1234";
		JSONObject person = createPerson(fn, mn, ln);
		JSONObject usr = createUser(userName, password, fn, mn, ln);
		System.err.println("usr:" + usr.toString());
		String getUserName = us.getUser(userName).getUsername();
		us.createProvider(userName, IdentifierType);
		
		JSONObject provider = us.getProvider(IdentifierType);
		JSONObject personObject = provider.getJSONObject("person");
		
		/*********************/
		Client hhhead = oc.getClientFromFormSubmission(fs);
		Event ev = oc.getEventFromFormSubmission(fs);
		Map<String, Map<String, Object>> dep = oc.getDependentClientsFromFormSubmission(fs);
		
		OpenmrsHouseHold household = new OpenmrsHouseHold(hhhead, ev);
		for (String hhmid : dep.keySet()) {
			household.addHHMember((Client) dep.get(hhmid).get("client"), (Event) dep.get(hhmid).get("event"));
		}
		
		JSONObject pr = us.getProvider(fs.anmId());
		if (pr == null) {
			us.createProvider(fs.anmId(), fs.anmId());
		}
		
		JSONObject enct = es.getEncounterType(ev.getEventType());
		if (enct == null) {
			es.createEncounterType(ev.getEventType(), "Encounter type created to fullfill scheduling test pre-reqs");
		}
		
		for (String hhmid : dep.keySet()) {
			Event ein = (Event) dep.get(hhmid).get("event");
			JSONObject hmenct = es.getEncounterType(ein.getEventType());
			if (hmenct == null) {
				es.createEncounterType(ein.getEventType(), "Encounter type created to fullfill scheduling test pre-reqs");
			}
			
		}
		
		JSONObject response = hhs.saveHH(household, true);
		
		JSONArray encounters = response.getJSONArray("encounters");
		JSONArray relationships = response.getJSONArray("relationships");
		JSONObject hhEncounter = encounters.getJSONObject(0);
		String hhEncounterUUID = hhEncounter.getString("uuid");
		JSONObject hhEncounterType = hhEncounter.getJSONObject("encounterType");
		String actualHHEncounterTypeName = hhEncounterType.getString("display");
		
		JSONObject memberEncounter = encounters.getJSONObject(1);
		String memberEncounterUUID = memberEncounter.getString("uuid");
		
		JSONObject meEncounterType = memberEncounter.getJSONObject("encounterType");
		String actualMEEncounterTypeName = meEncounterType.getString("display");
		
		JSONObject relationship = relationships.getJSONObject(0);
		String relationshipUUID = relationship.getString("uuid");
		JSONObject personB = relationship.getJSONObject("personB");
		JSONObject personA = relationship.getJSONObject("personA");
		String actualPersonBName = personB.getString("display");
		String actualPersonAName = personA.getString("display");
		
		/* cleaning openmrs data */
		deleteEncounter(hhEncounterUUID);
		deleteEncounter(memberEncounterUUID);
		
		deleteRelation(relationshipUUID);
		deletePerson(person.getString("uuid"));
		deleteUser(usr.getString("uuid"));
		deleteIdentifierType(identifierUuid);
		deleteProvider(provider.getString("uuid"));
		deletePerson(personObject.getString("uuid").trim());
		String uuid = patient.getString("uuid");
		deletePerson(uuid);
		String uuids = patients.getString("uuid");
		deletePerson(uuids);
		deletePersonAttributeType(attribute.getString("uuid"));
		assertEquals("New Household Registration", actualHHEncounterTypeName);
		assertEquals("Census and New Woman Registration", actualMEEncounterTypeName);
		assertEquals(fn + " " + mn + " " + ln, actualPersonBName);
		assertEquals(fn + " " + mn + " " + ln, actualPersonAName);
		
	}
	
	private ActionData alert(String schedule, String milestone) {
		return ActionData.createAlert(mother.value(), schedule, milestone, normal, DateTime.now(), DateTime.now()
		        .plusDays(3));
	}
}
