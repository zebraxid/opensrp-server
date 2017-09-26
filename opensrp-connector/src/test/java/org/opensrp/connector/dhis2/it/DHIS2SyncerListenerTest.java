package org.opensrp.connector.dhis2.it;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.scheduler.domain.MotechEvent;
import org.opensrp.common.AllConstants.DHIS2Constants;
import org.opensrp.connector.dhis2.DHIS2SyncerListener;
import org.opensrp.connector.dhis2.Dhis2HttpUtils;
import org.opensrp.connector.openmrs.service.TestResourceLoader;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.domain.Obs;
import org.opensrp.repository.AllClients;
import org.opensrp.repository.AllEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-connector.xml")
public class DHIS2SyncerListenerTest extends TestResourceLoader {
	
	@Autowired
	private AllClients allClients;
	
	@Autowired
	private AllEvents allEvents;
	
	@Autowired
	private DHIS2SyncerListener dhis2SyncerListener;
	
	@Autowired
	private Dhis2HttpUtils dhis2HttpUtils;
	
	@Before
	public void setup() {
		allClients.removeAll();
		allEvents.removeAll();
	}
	
	public DHIS2SyncerListenerTest() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void testPushToDHIS2() throws JSONException {
		String baseEntityId = "29";
		String identifierTypeForChild = "ZEIR_ID";
		String identifierTypeValue = "159451-37_child";
		Client client = new Client(baseEntityId).withFirstName("MOOM child").withGender("male").withLastName("Magento")
		        .withBirthdate(new DateTime(), false);
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put(identifierTypeForChild, identifierTypeValue);
		client.setIdentifiers(identifiers);
		
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("Father_NRC_Number", "34");
		attributes.put("Child_Register_Card_Number", "24");
		attributes.put("CHW_Phone_Number", "01284556788");
		attributes.put("CHW_Name", "Jakly");
		attributes.put("Child_Birth_Certificate", "344");
		client.setAttributes(attributes);
		List<String> list = new ArrayList<>();
		list.add("127");
		Map<String, List<String>> relationships = new HashMap<>();
		relationships.put("mother", list);
		client.setRelationships(relationships);
		allClients.add(client);
		
		/**** mother **/
		Client mother = (Client) new Client("127").withFirstName("HOOM Mother").withGender("female")
		        .withLastName("WORD motehr").withBirthdate(new DateTime(), false).withDateCreated(new DateTime());
		
		List<String> motherRelationshipsList = new ArrayList<>();
		motherRelationshipsList.add("130");
		Map<String, List<String>> motherRelationships = new HashMap<>();
		motherRelationships.put("household", motherRelationshipsList);
		mother.setRelationships(motherRelationships);
		Map<String, Object> motherAttributes = new HashMap<>();
		
		motherAttributes.put("phoneNumber", "7654322234");
		motherAttributes.put("nationalId", "76543222349775");
		motherAttributes.put("spouseName", "Dion");
		mother.setAttributes(motherAttributes);
		allClients.add(mother);
		
		Event event = new Event("127", "New Woman Member Registration", new DateTime(0l, DateTimeZone.UTC), "entityType",
		        "provider", "locationId", "formSubmissionId");
		List<Obs> observations = new ArrayList<>();
		observations.add(getObsWithValue("reg_No", "45213687"));
		observations.add(getObsWithValue("epi_card_number", "25096325"));
		observations.add(getObsWithValue("maritial_status", "Married"));
		observations.add(getObsWithValue("couple_no", "1"));
		observations.add(getObsWithValue("pregnant", "Yes"));
		observations.add(getObsWithValue("fp_user", "Yes"));
		
		observations.add(getObsWithHumanReadableValue("fp_methods", "Condoms"));
		observations.add(getObsWithHumanReadableValue("edd_lmp", "EDD"));
		observations.add(getObsWithValue("edd", "24-09-2017"));
		event.setObs(observations);
		allEvents.add(event);
		
		/** Household ***/
		Client household = (Client) new Client("130").withFirstName("humoom").withGender("female").withLastName("la")
		        .withBirthdate(new DateTime(), false).withDateCreated(new DateTime());
		Map<String, Object> householdAttributes = new HashMap<>();
		householdAttributes.put("householdCode", "34Zoomrttt");
		household.setAttributes(householdAttributes);
		allClients.add(household);
		
		Event householdEvent = new Event("130", "Household Registration", new DateTime(0l, DateTimeZone.UTC), "entityType",
		        "provider", "locationId", "formSubmissionId");
		List<Obs> householdOservations = new ArrayList<>();
		householdOservations.add(getObsWithValue("Date_Of_Reg", "21-09-2017"));
		householdEvent.setObs(householdOservations);
		allEvents.add(householdEvent);
		
		MotechEvent motechEvent = new MotechEvent(DHIS2Constants.DHIS2_TRACK_DATA_SYNCER_SUBJECT);
		JSONObject returns = dhis2SyncerListener.pushToDHIS2(motechEvent);
		
		/*JSONObject response = returns.getJSONObject("response");
		String expectedImport = "1";
		String actualImport = response.getString("imported");
		String expectedHttpStatusCode = "200";
		String actualHttpStatusCode = returns.getString("httpStatusCode");
		assertEquals(expectedImport, actualImport);
		assertEquals(expectedHttpStatusCode, actualHttpStatusCode);
		String trackReference = returns.getString("track");
		
		JSONArray importSummariesArray = response.getJSONArray("importSummaries");
		JSONObject importSummariesJsonObject = importSummariesArray.getJSONObject(0);
		String refId = importSummariesJsonObject.getString("reference");
		*/
		/*Clening data*/
		//deleteEnrollment(refId);
		//deleteTrackInstances(trackReference);
	}
	
	public void deleteEnrollment(String id) {
		String url = "enrollments/" + id;
		dhis2HttpUtils.delete(url, "", "");
	}
	
	public Obs getObsWithValue(String fielCode, String value) {
		Obs obs = new Obs();
		obs.setFieldCode(fielCode);
		obs.setFieldDataType("text");
		obs.setFormSubmissionField(fielCode);
		
		List<Object> values = new ArrayList<Object>();
		values.add(value);
		obs.setValues(values);
		
		return obs;
		
	}
	
	public Obs getObsWithHumanReadableValue(String fielCode, String value) {
		Obs obs = new Obs();
		obs.setFieldCode(fielCode);
		obs.setFieldDataType("select one");
		obs.setFormSubmissionField(fielCode);
		List<Object> humanReadableValues = new ArrayList<Object>();
		humanReadableValues.add(value);
		obs.setHumanReadableValues(humanReadableValues);
		return obs;
		
	}
	
	public void deleteTrackInstances(String id) {
		String url = "trackedEntityInstances/" + id;
		dhis2HttpUtils.delete(url, "", "");
	}
}
