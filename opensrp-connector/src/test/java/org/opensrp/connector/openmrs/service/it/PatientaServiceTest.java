package org.opensrp.connector.openmrs.service.it;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.connector.openmrs.service.PatientService;
import org.opensrp.domain.Address;
import org.opensrp.domain.Client;
import org.opensrp.domain.Multimedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-connector.xml")
public class PatientaServiceTest extends OpenmrsApiService {
	
	public PatientaServiceTest() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private PatientService patientService;
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void shouldCreatePatient() throws JSONException {
		String fn = "jack";
		String mn = "bgu";
		String ln = "nil";
		Map<String, String> addressFields = new HashMap<>();
		addressFields.put("ADDRESS1", "ADDRESS1");
		addressFields.put("ADDRESS2", "ADDRESS2");
		addressFields.put("ADDRESS3", "ADDRESS3");
		addressFields.put("ADDRESS4", "ADDRESS4");
		addressFields.put("ADDRESS4", "ADDRESS4");
		
		Map<String, Object> attributes = new HashMap<>();
		String attributeName = "HHAttribute";
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
		c.withIdentifier("Birth Reg Num", "b-8912819" + new Random().nextInt(99));
		
		if (patientService.getPatientByIdentifier(c.getBaseEntityId()) == null) {
			
			JSONObject patient = patientService.createPatient(c);
			JSONObject person = patient.getJSONObject("person");
			String personName = person.getString("display");
			String uuid = patient.getString("uuid");
			deletePerson(uuid);
			assertEquals("Should equal Person:", fn + " " + mn + " " + ln, personName);
			deletePersonAttributeType(attribute.getString("uuid"));
		}
		
	}
	
	@Test
	public void testPatientImageUpload() throws IOException, JSONException {
		
		String fn = "moushumi";
		String mn = "sumaita";
		String ln = "khan";
		Map<String, String> addressFields = new HashMap<>();
		addressFields.put("ADDRESS1", "ADDRESS1");
		addressFields.put("ADDRESS2", "ADDRESS2");
		addressFields.put("ADDRESS3", "ADDRESS3");
		addressFields.put("ADDRESS4", "ADDRESS4");
		addressFields.put("ADDRESS4", "ADDRESS4");
		Map<String, Object> attributes = new HashMap<>();
		String attributeName = "PatientAttributeName";
		JSONObject attribute = createPersonAttributeType("Description", attributeName);
		attributes.put(attributeName, "test value");
		List<Address> addresses = new ArrayList<>();
		Client c1 = new Client(UUID.randomUUID().toString()).withFirstName(fn).withMiddleName(mn).withLastName(ln)
		        .withBirthdate(new DateTime(), true).withDeathdate(new DateTime(), false).withGender("MALE");
		
		c1.withAddresses(addresses).withAttributes(attributes);
		c1.withIdentifier("OpenSRP Thrive UID", "yuucd9d2-b3e9-4fud-8a06-udf8f5fbf018");
		JSONObject patient = patientService.createPatient(c1);
		
		Multimedia multimedia = new Multimedia();
		multimedia.setFilePath("/multimedia/sumon/images/1.jpg");
		multimedia.setCaseId("74cc2645-8202-4f12-a7b0-e73f7c49eea9");
		multimedia.setFileCategory("dp");
		multimedia.setProviderId("sumon");
		multimedia.setContentType("Image");
		String expectedResponse = "Patient Image is  successfully uploaded !";
		List<String> resposne = patientService.patientImageUpload(multimedia);
		String uuids = patient.getString("uuid");
		deletePerson(uuids);
		
		deletePersonAttributeType(attribute.getString("uuid"));
		assertEquals(expectedResponse, resposne.get(0));
	}
}
