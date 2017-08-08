package org.opensrp.connector.openmrs.service.it;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.IOException;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.connector.openmrs.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-connector.xml")
public class OpenmrsUserServiceTest extends OpenmrsApiService {
	
	public OpenmrsUserServiceTest() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private OpenmrsUserService openmrsUserService;
	
	@Autowired
	private PatientService patientService;
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void testGetUser() throws JSONException {
		Assert.assertEquals(openmrsUsername, openmrsUserService.getUser(openmrsUsername).getUsername());
		Assert.assertNotSame("123456gtyyy", openmrsUserService.getUser(openmrsUsername).getUsername());
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullPointerExceptionForGetUser() throws JSONException {
		openmrsUserService.getUser("openmrsUsername786").getUsername();
	}
	
	@Test
	public void testAuthenticateAnDeleteSession() throws JSONException {
		assertTrue(openmrsUserService.authenticate(openmrsUsername, openmrsPassword));
		openmrsUserService.deleteSession(openmrsUsername, openmrsPassword);
	}
	
	@Test
	public void shouldTestProviderAndUser() throws JSONException {
		
		String IdentifierType = "TestIdentifierType";
		JSONObject identifier = patientService.createIdentifierType(IdentifierType, "description");
		String identifierUuid = identifier.getString("uuid");
		String fn = "jack";
		String mn = "bgu";
		String ln = "nil";
		String userName = "Doteli";
		String password = "Dotel@1234";
		JSONObject person = createPerson(fn, mn, ln);
		JSONObject usr = createUser(userName, password, fn, mn, ln);
		System.err.println("usr:" + usr.toString());
		String getUserName = openmrsUserService.getUser(userName).getUsername();
		assertEquals("Should equal User:", userName, getUserName);
		
		openmrsUserService.createProvider(userName, IdentifierType);
		
		JSONObject provider = openmrsUserService.getProvider(IdentifierType);
		JSONObject personObject = provider.getJSONObject("person");
		assertEquals("Should equal IdentifierType:", IdentifierType, provider.get("identifier"));
		
		assertEquals("Should equal Person:", fn + " " + mn + " " + ln, personObject.get("display"));
		
		assertEquals("Should equal Provider:", IdentifierType + " - " + fn + " " + mn + " " + ln, provider.get("display"));
		deletePerson(person.getString("uuid"));
		deleteUser(usr.getString("uuid"));
		deleteIdentifierType(identifierUuid);
		deleteProvider(provider.getString("uuid"));
		deletePerson(personObject.getString("uuid").trim());
		
	}
	
	@Ignore
	@Test
	public void testGetTeamMember() throws JSONException {
		System.err.println("HH;" + openmrsUserService.getTeamMember("5051ee32-8249-4b26-b591-9e1137dce832"));
	}
	
}
