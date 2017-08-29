package org.opensrp.connector.dhis2.it;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.connector.dhis2.DHIS2SyncerListener;
import org.opensrp.connector.openmrs.service.TestResourceLoader;
import org.opensrp.domain.Client;
import org.opensrp.repository.AllClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-connector.xml")
public class DHIS2SyncerListenerTest extends TestResourceLoader {
	
	public DHIS2SyncerListenerTest() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private AllClients allClients;
	
	@Autowired
	private DHIS2SyncerListener dhis2SyncerListener;
	
	@Before
	public void setup() {
		allClients.removeAll();
	}
	
	@Test
	public void testsentTrackCaptureDataToDHIS2() throws JSONException {
		Client client = new Client("29").withFirstName("Jared").withGender("male").withLastName("Omwenga")
		        .withBirthdate(new DateTime(), false);
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put("ZEIR_ID", "159451-37_child");
		client.setIdentifiers(identifiers);
		List<String> list = new ArrayList<>();
		list.add("27");
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("Father_NRC_Number", "34");
		attributes.put("Child_Register_Card_Number", "24");
		attributes.put("CHW_Phone_Number", "01284556788");
		attributes.put("CHW_Name", "Jakly");
		attributes.put("Child_Birth_Certificate", "344");
		client.setAttributes(attributes);
		allClients.add(client);
		JSONObject returns = dhis2SyncerListener.sentTrackCaptureDataToDHIS2(client);
		JSONObject response = returns.getJSONObject("response");
		String expectedImport = "1";
		String actualImport = response.getString("imported");
		assertEquals(expectedImport, actualImport);
		String expectedHttpStatusCode = "200";
		String actualHttpStatusCode = returns.getString("httpStatusCode");
		assertEquals(expectedHttpStatusCode, actualHttpStatusCode);
		
	}
	
}
