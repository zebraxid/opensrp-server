/**
 * 
 */
package org.opensrp.web.it.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.domain.Address;
import org.opensrp.domain.Client;
import org.opensrp.repository.AllCamp;
import org.opensrp.repository.AllClients;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.service.RapidProServiceImpl;
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
	private RapidproMessageListener rapidProMessageListener;
	
	@Autowired
	private AllCamp allCamp;
	
	@Autowired
	private RapidProServiceImpl rapidproService;
	
	@Before
	public void setup() throws IOException {
		//allClients.removeAll();
		//	allActions.removeAll();
	}
	
	@Ignore
	@Test
	public void testFetchClient() throws JSONException {
		
		/*		Client child = (Client) new Client("127").withFirstName("foomm").withGender("female").withLastName("bae ff")
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
				
				motherAttributes.put("phoneNumber", "01711082537");
				motherAttributes.put("nationalId", "76543222349775");
				motherAttributes.put("spouseName", "Dion");
				mother.setAttributes(motherAttributes);
				allClients.add(mother);
				Action normalAction = new Action("127", "ANM 1", ActionData.createAlert("child", "opv", "opv0", normal,
				    DateTime.now(), DateTime.now().plusDays(3)));
				Action upcominglAction = new Action("127", "ANM 1", ActionData.createAlert("child", "opv", "opv0", upcoming,
				    DateTime.now(), DateTime.now().plusDays(3)));
				allActions.add(normalAction);
				allActions.add(upcominglAction);*/
		//rapidProMessageListener.fetchClient();
		
		rapidProMessageListener.campAnnouncementListener("asma");
		
	}
	
	@Ignore
	@Test
	public void testAddress() {
		
		Client client = allClients.findByBaseEntityId("0001644c-799a-4c5f-b466-99c32025fa61");
		
		System.err.println("subunit" + getClientSubunit(client));
		
	}
	
	@Test
	public void sendTestMessage() {
		List<String> urns = new ArrayList<String>();
		List<String> contacts = new ArrayList<String>();
		List<String> groups = new ArrayList<String>();
		urns.add("tel:" + "+8801711082537");
		//rapidproService.sendMessage(urns, contacts, groups, "test message", "");
		System.err.println("sendDirectToCarrier: " + "+8801711082537");
		rapidproService.sendDirectToCarrier("+8801711082537", "test");
		System.err.println("send message to rapidpro" + "+8801711082537");
		
	}
	
	private String getClientSubunit(Client client) {
		String clientSubUnit = null;
		List<Address> address = client.getAddresses();
		if (address.size() != 0) {
			Address clientAddress = address.get(0);
			if (clientAddress != null) {
				clientSubUnit = clientAddress.getAddressField("address3");
				return clientSubUnit;
			}
			
		}
		
		return clientSubUnit;
	}
	
	@Ignore
	@Test
	public void testgetAgeOfChild() {
		System.err.println("getAgeOfChild: " + getAgeOfChild(new DateTime().minusMonths(23).toDate()));
		
	}
	
	private int getAgeOfChild(Date dateTime) {
		System.err.println("getAgeOfChild: " + dateTime);
		Calendar now = Calendar.getInstance();
		Calendar dob = Calendar.getInstance();
		int age = 0;
		
		dob.setTime(dateTime);
		
		if (dob.after(now)) {
			throw new IllegalArgumentException("Can't be born in the future");
		}
		
		age = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if (now.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
			age--;
		}
		
		return age;
	}
	
}
