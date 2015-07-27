package org.opensrp.connector.openmrs.service;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;


public class OpenmrsUserServiceTest extends TestResourceLoader{

	public OpenmrsUserServiceTest() throws IOException {
		super();
	}

	OpenmrsUserService ls;

	@Before
	public void setup(){
		ls = new OpenmrsUserService(openmrsOpenmrsUrl, openmrsUsername, openmrsPassword);
		//this.ls = new OpenmrsUserService("http://27.147.138.50:8080/openmrs/", "admin", "Admin123");
	}
	
	@Test
	public void testAuthentication() throws JSONException {
	//	assertTrue(ls.authenticate(openmrsUsername, openmrsPassword));
	}
	
	@Test
	public void testUser() throws JSONException {
		//assertTrue(ls.getUser("admin").getUsername().equalsIgnoreCase("admin"));
	}
}
