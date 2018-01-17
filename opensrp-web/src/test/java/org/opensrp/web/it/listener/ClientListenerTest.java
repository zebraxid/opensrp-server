/**
 * 
 */
package org.opensrp.web.it.listener;

import static org.opensrp.dto.AlertStatus.normal;
import static org.opensrp.dto.AlertStatus.upcoming;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.domain.Client;
import org.opensrp.dto.ActionData;
import org.opensrp.repository.AllClients;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.web.listener.RapidproMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author proshanto
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-opensrp-web.xml" })
public class ClientListenerTest {
	
	@Autowired
	private AllClients allClients;
	
	@Autowired
	private AllActions allActions;
	
	@Autowired
	private RapidproMessageListener clientListener;
	
	@Before
	public void setup() throws IOException {
		//allClients.removeAll();
		//	allActions.removeAll();
	}
	
	@Test
	public void testFetchClient() throws JSONException {
		
		Client child = (Client) new Client("127").withFirstName("foomm").withGender("female").withLastName("bae ff")
		        .withBirthdate(new DateTime(), false).withDateCreated(new DateTime());
		
		List<String> motherRelationshipsList = new ArrayList<>();
		motherRelationshipsList.add("130");
		Map<String, List<String>> motherRelationships = new HashMap<>();
		motherRelationships.put("mother", motherRelationshipsList);
		child.setRelationships(motherRelationships);
		
		allClients.add(child);
		
		Client mother = (Client) new Client("130").withFirstName("foorrr").withGender("female").withLastName("bae ff")
		        .withBirthdate(new DateTime(), false).withDateCreated(new DateTime());
		
		Map<String, Object> motherAttributes = new HashMap<>();
		
		motherAttributes.put("phoneNumber", "01912773007");
		motherAttributes.put("nationalId", "76543222349775");
		motherAttributes.put("spouseName", "Dion");
		mother.setAttributes(motherAttributes);
		allClients.add(mother);
		Action normalAction = new Action("127", "ANM 1", ActionData.createAlert("child", "opv", "opv0", normal,
		    DateTime.now(), DateTime.now().plusDays(3)));
		Action upcominglAction = new Action("127", "ANM 1", ActionData.createAlert("child", "opv", "opv0", upcoming,
		    DateTime.now(), DateTime.now().plusDays(3)));
		allActions.add(normalAction);
		allActions.add(upcominglAction);
		clientListener.fetchClient();
		
	}
	
	/*@Test
	public void mesageTest() throws JSONException {
		clientListener.fetchClient();
	}*/
}
