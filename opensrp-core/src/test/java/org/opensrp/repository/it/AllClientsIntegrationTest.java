package org.opensrp.repository.it;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.common.AllConstants.ActivityLogConstants;
import org.opensrp.common.Gender;
import org.opensrp.domain.Address;
import org.opensrp.domain.Client;
import org.opensrp.repository.AllClients;
import org.opensrp.service.ClientService;
import org.opensrp.util.DateTimeTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-opensrp.xml")
public class AllClientsIntegrationTest {
//TODO detailed testign
	
	@Autowired
	private ClientService clientService;
	@Autowired
	private AllClients ac;

	@Before
	public void setUp() throws Exception {
		System.out.println("Removing all data");
		ac.removeAll();
		System.out.println("Removed");
		initMocks(this);
	}
	
	private void addClients() {
		for (int i = 0; i < 1000; i++) {
			Client c = new Client("eid"+i)
				.withName("fn"+i, "mn"+i, "ln"+i)
				.withGender("MALE")
				.withBirthdate(new DateTime(), false);
			c.withAddress(new Address()
					.withAddressType("usual_residence")
					.withCityVillage(i%5==0?"Karachi":"Shikarpur")
					.withTown(i%5==0?"Korangi":"Garhi Yasin"));
			c.withAttribute("at1", "atval"+i);
			
			clientService.addClient(c, ActivityLogConstants.OpenSRPClientActionCategory);
		}
	}
	
	@Test
	public void shouldMergeSuccessfullyIfClientFound() throws JSONException {//TODO
		Client c = new Client("eid0")
			.withName("fn", "mn", "ln")
			.withGender("MALE")
			.withBirthdate(new DateTime(), false);
		c.withAddress(new Address().withAddressType("usual_residence").withCityVillage("city").withTown("town"));
		c.withAttribute("at1", "atval1");
		
		c = clientService.addClient(c, ActivityLogConstants.OpenSRPClientActionCategory);
		
		Client cu = new Client("eid0")
			.withGender("FEMALE")
			.withBirthdate(new DateTime(), false);
		cu.withAddress(new Address().withAddressType("deathplace").withCityVillage("city").withTown("town"));
		cu.withAttribute("at2", "atval2");

		clientService.mergeClient(cu, ActivityLogConstants.OpenSRPClientActionCategory);
	}
	
	@Test
	public void shouldFetchByAddress() {
		for (int i = 0; i < 20; i++) {
			Client c = new Client(UUID.randomUUID().toString())
					.withFirstName("FN").withLastName("LN").withBirthdate(DateTime.now().minusYears(i), false).withGender("MALE");
			c.withAddress(new Address()
				.withAddressType("usual_residence")
				.withCityVillage("Karachi")
				.withTown("Korangi"));
			clientService.addClient(c, ActivityLogConstants.OpenSRPClientActionCategory);
		}
		List<Client> result = clientService.findAllByAddress("Usual_residence", "cityVillage", "Karachi", DateTime.now().minusDays(1), DateTime.now());
		assertEquals(20, result.size());
		result = clientService.findAllByAddress("usual_residence", "cityVillage", "Shikarpur", DateTime.now().minusDays(1), DateTime.now());
		assertEquals(0, result.size());
		
		result = clientService.findAllByAddress("usual_residence", "town", "Korangi", DateTime.now().minusDays(1), DateTime.now());
		assertEquals(20, result.size());
		result = clientService.findAllByAddress("usual_residence", "town", "Zaman Town", DateTime.now().minusDays(1), DateTime.now());
		assertEquals(0, result.size());
	}
	
	@Test
	public void shouldSearchByLastUpdatedDate() throws JSONException {//TODO

	}
	
	public static void main(String[] args) {
		System.out.println(new DateTime("2016-01-23").toString("MMMM (yyyy)"));
	}
	
	@Ignore @Test 
	public void shouldSearchFullDataClientsIn10Sec() throws MalformedURLException {
		
		 /*org.ektorp.http.HttpClient httpClient = new StdHttpClient.Builder().url("http://202.141.249.106:6808").build();
		    CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);

		    CouchDbConnector db = new StdCouchDbConnector("opensrp", dbInstance);
		    
		Logger.getLogger("FileLogger").info("Starting at "+new DateTime());*/
		
		final long start = System.currentTimeMillis();
		
		for (int i = 0; i < 10000; i++) {
			addClient(i, true, null);
			Logger.getLogger("FileLogger").info("Search while adding");
			List<Client> cl = clientService.findAllByIdentifier("1234556"+"786");
		}
		Logger.getLogger("FileLogger").info("10K entries complete at "+new DateTime()+" in "+((System.currentTimeMillis()-start)/1000)+" sec");
		
		Logger.getLogger("FileLogger").info("Going for First search by Couch");
		clientService.findAllByIdentifier("1234556"+"786");
		Logger.getLogger("FileLogger").info("Completed First search by Couch");
		
		Logger.getLogger("FileLogger").info("Going for 2nd search by Couch");
		clientService.findAllByIdentifier("1234556"+"786");
		Logger.getLogger("FileLogger").info("Completed 2nd search by Couch");
	}
	
	void addClient(int i, boolean direct, CouchDbConnector db){
		int ageInWeeks = new Random().nextInt(2860);// assuming average age of people is 55 years
		DateTime birthdate = new DateTime().minusWeeks(ageInWeeks);
		DateTime deathdate = i%7==0?new DateTime():null;// every 7th person died today
		Client c = new Client("entityId"+i, "firstName"+i, "middleName"+i, "lastName"+i, birthdate, deathdate, false, false, i%2==0?"FEMALE":"MALE");
		
		Map<String, String> am = new HashMap<>();
		Address ab = new Address("birthplace", null, null, am , null, null, null, "Sindh", "Pakistan");
		ab.setCityVillage("Karachi");
		ab.setTown("Korangi");
		ab.setSubTown("UC"+i%11);
		c.addAddress(ab);
		
		Address ur = new Address("usual_residence", null, null, am , null, null, "752"+new Random().nextInt(5), "Sindh", "Pakistan");
		ur.setCityVillage("Karachi");
		ur.setTown(i%3==0?"Korangi":"Baldia");
		ur.setSubTown("UC"+i%11);
		c.addAddress(ur);
		
		c.addAttribute("ethnicity", "eth"+i%7);
		c.addAttribute("health area", "healtha"+i%7);
		
		c.addIdentifier("CNIC", "1234556"+i);
		c.addIdentifier("NTN", "564300"+i);
		
		if(db != null){
			db.create(c);
		}
		else if(direct){
			ac.add(c);
		}
		else {
			clientService.addClient(c, ActivityLogConstants.OpenSRPClientActionCategory);
		}
		System.out.println("Client # "+i);
	}
	
	@Test
	public void shouldFetchClientByIdentifier()
	{
		String baseEntityId = "testclient2";
		Client c = new Client(baseEntityId)
			.withBirthdate(new DateTime(), false)
			.withFirstName("C first n")
			.withLastName("C last n")
			.withMiddleName("C middle n")
			.withGender(Gender.MALE);
		c.withAddress(new Address("birthplace", new DateTime(System.currentTimeMillis()-1000*60*60*24*2), DateTime.now(), null, "lat", "lon", "75210", "Sindh", "Pakistan"));
		c.withAttribute("ETHNICITY", "Mughal");
		c.withIdentifier("Program ID", "01001222");
		
		clientService.addClient(c, ActivityLogConstants.OpenSRPClientActionCategory);
		
		Client ce = clientService.getByBaseEntityId("testclient2");
		assertEquals("testclient2", ce.getBaseEntityId());
		assertTrue(Client.class.getSimpleName().equals(ce.type()));
		assertEquals("birthplace", ce.getAddresses().get(0).getAddressType());
		assertEquals("Mughal", ce.getAttribute("ethnicity"));
		assertEquals("01001222", ce.getIdentifier("program id"));
		
		List<Client> ce2 = clientService.findAllByIdentifier("01001222");
		assertTrue(ce2.size() == 1);
		assertEquals("testclient2", ce2.get(0).getBaseEntityId());
		
		List<Client> ce3 = clientService.findAllByIdentifier("01001222");
		assertTrue(ce3.size() == 1);
		assertEquals("testclient2", ce3.get(0).getBaseEntityId());
	}
	
	@Test
	public void shouldFetchClientByAttribute()
	{
		String baseEntityId = "testclient2";
		Client c = new Client(baseEntityId)
			.withBirthdate(new DateTime(), false)
			.withFirstName("C first n")
			.withLastName("C last n")
			.withMiddleName("C middle n")
			.withGender(Gender.MALE);
		c.withAddress(new Address("birthplace", new DateTime(System.currentTimeMillis()-1000*60*60*24*2), DateTime.now(), null, "lat", "lon", "75210", "Sindh", "Pakistan"));
		c.withAttribute("ETHNICITY", "Mughal");
		c.withIdentifier("Program ID", "01001222");
		
		clientService.addClient(c, ActivityLogConstants.OpenSRPClientActionCategory);
		
		c = new Client("testclient3")
		.withBirthdate(new DateTime(), false)
		.withFirstName("C first n")
		.withLastName("C last n")
		.withMiddleName("C middle n")
		.withGender(Gender.MALE);
		c.withAttribute("ETHNICITY", "Mughal");
		c.addIdentifier("Program ID", "01001223");
		
		clientService.addClient(c, ActivityLogConstants.OpenSRPClientActionCategory);
		
		List<Client> ce = clientService.findAllByAttribute("ETHNICITY", "Mughal", DateTime.now().minusYears(11), DateTime.now());
		assertTrue(ce.size() == 2);
		assertThat(ce, Matchers.<Client>hasItem(Matchers.<Client>hasProperty("baseEntityId",equalTo("testclient2"))));
		assertThat(ce, Matchers.<Client>hasItem(Matchers.<Client>hasProperty("baseEntityId",equalTo("testclient3"))));
	}
	
}
