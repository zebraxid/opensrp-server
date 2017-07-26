package org.opensrp.web.rest.it;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.domain.Address;
import org.opensrp.domain.Client;
import org.opensrp.repository.AllClients;
import org.opensrp.service.ClientService;
import org.opensrp.web.rest.ClientResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.opensrp.common.AllConstants.BaseEntity.BASE_ENTITY_ID;
import static org.opensrp.common.AllConstants.Client.BIRTH_DATE;
import static org.opensrp.common.AllConstants.Client.FIRST_NAME;
import static org.opensrp.common.AllConstants.Client.GENDER;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = TestWebContextLoader.class, locations = {
		"classpath:spring/applicationContext-opensrp-web.xml" })
public class ClientResourceTest {

	private final static String BASE_URL = "/rest/client/";

	@Autowired
	private WebApplicationContext wac;

	MockMvc mockMvc;

	@Autowired
	private AllClients allClients;

	@Autowired
	private ClientService clientService;

	@Autowired
	private ClientResource clientResource;

	ObjectMapper mapper = new ObjectMapper();

	String addressType = "addressType";

	String country = "country";

	String stateProvince = "stateProvince";

	String cityVillage = "cityVillage";

	String countryDistrict = "countryDistrict";

	String subDistrict = "subDistrict";

	String town = "town";

	String name = "name";

	String male = "male";

	DateTime birthDate = new DateTime(0l, DateTimeZone.UTC);

	DateTime deathDate = new DateTime(1l, DateTimeZone.UTC);

	Address address = new Address().withAddressType(addressType).withCountry(country).withStateProvince(stateProvince)
			.withCityVillage(cityVillage).withCountyDistrict(countryDistrict).withSubDistrict(subDistrict).withTown(town);

	@Before
	public void setUp() {
		allClients.removeAll();
	}

	@After
	public void tearDown() {
		allClients.removeAll();
	}


	@Test
	public void testRequiredProperties() {
		List<String> requiredProperties = clientResource.requiredProperties();
		assertTrue(requiredProperties.contains(FIRST_NAME));
		assertTrue(requiredProperties.contains(GENDER));
		assertTrue(requiredProperties.contains(BIRTH_DATE));
		assertTrue(requiredProperties.contains(BASE_ENTITY_ID));
	}

	@Test
	public void shouldFindClientById() throws Exception {
		Client expectedClient = new Client("1").withFirstName("first").withGender("male")
				.withBirthdate(new DateTime(0l, DateTimeZone.UTC), false);
		createClient(asList(expectedClient));
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + "1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		String responseString = mvcResult.getResponse().getContentAsString();
		JsonNode actualObj = mapper.readTree(responseString);
		Client actualClient = mapper.treeToValue(actualObj, Client.class);

		assertEquals(expectedClient, actualClient);

	}

	@Test
	public void shouldNotFindClient() throws Exception {
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + "1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		String responseString = mvcResult.getResponse().getContentAsString();
		assertTrue(responseString.isEmpty());
	}

	@Test
	public void shouldCreateClientWithAllRequiredProperties() throws Exception {
		Client expectedClient = new Client("1").withFirstName("first").withGender("male")
				.withBirthdate(new DateTime(0l, DateTimeZone.UTC), false);
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		this.mockMvc.perform(
				post(BASE_URL).contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsBytes(expectedClient))
						.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		List<Client> allClientsInDb = allClients.getAll();
		Client actualClient = allClientsInDb.get(0);
		actualClient.setDateCreated(null); //So We don't need to mock DateTimeUtil.now()
		assertEquals(1, allClientsInDb.size());
		assertEquals(expectedClient, actualClient);
	}

	@Test(expected = NestedServletException.class)
	public void shouldNotCreateClientWithOutBaseEntityId() throws Exception {
		Client expectedClient = new Client("1").withGender("male").withBirthdate(new DateTime(0l, DateTimeZone.UTC), false);
		expectedClient.setBaseEntityId(null);

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		this.mockMvc.perform(
				post(BASE_URL).contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsBytes(expectedClient))
						.accept(MediaType.APPLICATION_JSON));
		List<Client> allClientsInDb = allClients.getAll();
		assertEquals(0, allClientsInDb.size());
	}

	@Test(expected = NestedServletException.class)
	public void shouldNotCreateClientWithOutFirstName() throws Exception {
		Client expectedClient = new Client("1").withGender("male").withBirthdate(new DateTime(0l, DateTimeZone.UTC), false);
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		this.mockMvc.perform(
				post(BASE_URL).contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsBytes(expectedClient))
						.accept(MediaType.APPLICATION_JSON));
		List<Client> allClientsInDb = allClients.getAll();
		assertEquals(0, allClientsInDb.size());
	}

	@Test(expected = NestedServletException.class)
	public void shouldNotCreateClientWithOutGender() throws Exception {
		Client expectedClient = new Client("1").withFirstName("first").withBirthdate(new DateTime(0l), false);

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		this.mockMvc.perform(
				post(BASE_URL).contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsBytes(expectedClient))
						.accept(MediaType.APPLICATION_JSON));

		List<Client> allClientsInDb = allClients.getAll();
		assertEquals(0, allClientsInDb.size());

	}

	@Test(expected = NestedServletException.class)
	public void shouldNotCreateClientWithOutBirthDate() throws Exception {
		Client expectedClient = new Client("1").withFirstName("first").withGender("male");

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		this.mockMvc.perform(
				post(BASE_URL).contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsBytes(expectedClient))
						.accept(MediaType.APPLICATION_JSON));

		List<Client> allClientsInDb = allClients.getAll();
		assertEquals(0, allClientsInDb.size());

	}

	@Test
	public void shouldUpdateExistingClient() throws Exception {
		Client expectedClient = new Client("1").withFirstName("first").withGender("male")
				.withBirthdate(new DateTime(0l, DateTimeZone.UTC), false);
		createClient(asList(expectedClient));

		expectedClient.setDeathdate(new DateTime(2l, DateTimeZone.UTC));
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		this.mockMvc.perform(
				post(BASE_URL + "1").contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsBytes(expectedClient))
						.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		List<Client> allClientsInDb = allClients.getAll();
		Client actualClient = allClientsInDb.get(0);
		actualClient.setDateEdited(null); //So We don't need to mock DateTimeUtil.now()
		assertEquals(1, allClientsInDb.size());
		assertEquals(expectedClient, actualClient);

	}

	@Test(expected = NestedServletException.class)
	public void shouldThrowExceptionWhileUpdateIfClientNotFound() throws Exception {
		Client expectedClient = new Client("1").withFirstName("first").withGender("male")
				.withBirthdate(new DateTime(0l, DateTimeZone.UTC), false);
		expectedClient.setDeathdate(new DateTime(2l, DateTimeZone.UTC));

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		this.mockMvc.perform(
				post(BASE_URL + "1").contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsBytes(expectedClient))
						.accept(MediaType.APPLICATION_JSON));

		List<Client> allClientsInDb = allClients.getAll();
		assertEquals(0, allClientsInDb.size());

	}

	@Test(expected = NestedServletException.class)
	public void shouldThrowExceptionWhileUpdateIfFistNameNotPresent() throws Exception {
		Client expectedNotUpdatedClient = new Client("1").withGender("male")
				.withBirthdate(new DateTime(0l, DateTimeZone.UTC), false);
		createClient(asList(expectedNotUpdatedClient));
		Client updatedClient = expectedNotUpdatedClient;
		updatedClient.setDeathdate(new DateTime(2l, DateTimeZone.UTC));

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		this.mockMvc.perform(
				post(BASE_URL + "1").contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsBytes(updatedClient))
						.accept(MediaType.APPLICATION_JSON));

		List<Client> allClientsInDb = allClients.getAll();
		Client actualClient = allClientsInDb.get(0);
		assertEquals(1, allClientsInDb.size());
		assertEquals(expectedNotUpdatedClient, actualClient);
		assertNotSame(updatedClient, actualClient);
	}

	@Test(expected = NestedServletException.class)
	public void shouldThrowExceptionWhileUpdateIfGenderNotPresent() throws Exception {
		Client expectedNotUpdatedClient = new Client("1").withFirstName("name")
				.withBirthdate(new DateTime(0l, DateTimeZone.UTC), false);
		createClient(asList(expectedNotUpdatedClient));
		Client updatedClient = expectedNotUpdatedClient;
		updatedClient.setDeathdate(new DateTime(2l, DateTimeZone.UTC));

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		this.mockMvc.perform(
				post(BASE_URL + "1").contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsBytes(updatedClient))
						.accept(MediaType.APPLICATION_JSON));

		List<Client> allClientsInDb = allClients.getAll();
		Client actualClient = allClientsInDb.get(0);
		assertEquals(1, allClientsInDb.size());
		assertEquals(expectedNotUpdatedClient, actualClient);
		assertNotSame(updatedClient, actualClient);
	}

	@Test(expected = NestedServletException.class)
	public void shouldThrowExceptionWhileUpdateIfBirthDateNotPresent() throws Exception {
		Client expectedNotUpdatedClient = new Client("1").withGender("male").withFirstName("name");
		createClient(asList(expectedNotUpdatedClient));
		Client updatedClient = expectedNotUpdatedClient;
		updatedClient.setDeathdate(new DateTime(2l, DateTimeZone.UTC));

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		this.mockMvc.perform(
				post(BASE_URL + "1").contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsBytes(updatedClient))
						.accept(MediaType.APPLICATION_JSON));

		List<Client> allClientsInDb = allClients.getAll();
		Client actualClient = allClientsInDb.get(0);
		assertEquals(1, allClientsInDb.size());
		assertEquals(expectedNotUpdatedClient, actualClient);
		assertNotSame(updatedClient, actualClient);
	}

	@Test(expected = NestedServletException.class)
	public void shouldThrowExceptionWhileUpdateIfBaseEntityIdNotPresent() throws Exception {
		Client expectedNotUpdatedClient = new Client("1").withFirstName("name").withGender("male")
				.withBirthdate(new DateTime(0l, DateTimeZone.UTC), false);
		expectedNotUpdatedClient.setBaseEntityId(null);
		createClient(asList(expectedNotUpdatedClient));
		Client updatedClient = expectedNotUpdatedClient;
		updatedClient.setDeathdate(new DateTime(2l, DateTimeZone.UTC));

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		this.mockMvc.perform(
				post(BASE_URL + "1").contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsBytes(updatedClient))
						.accept(MediaType.APPLICATION_JSON));

		List<Client> allClientsInDb = allClients.getAll();
		Client actualClient = allClientsInDb.get(0);
		assertEquals(1, allClientsInDb.size());
		assertEquals(expectedNotUpdatedClient, actualClient);
		assertNotSame(updatedClient, actualClient);
	}

	@Test
	public void shouldSearchClient() throws Exception {
		Client expectedClient = (Client) new Client("1").withFirstName(name).withGender(male).withBirthdate(birthDate, false)
				.withDeathdate(deathDate, true).withAddress(address);
		expectedClient.setDateCreated(new DateTime(0l, DateTimeZone.UTC));

		Client otherClient = new Client("2");
		Client otherClient2 = new Client("3");

		createClient(asList(expectedClient, otherClient, otherClient2));

		String searchQuery =
				"search?name=" + name + "&gender=" + male + "&addressType=" + addressType + "&birthDate=" + birthDate
						.toLocalDate().toString() + "&deathDate=" + deathDate.toLocalDate().toString() + "&country="
						+ country + "&stateProvince=" + stateProvince + "&countryDistrict=" + countryDistrict
						+ "&cityVillage=" + cityVillage + "&town=" + town + "&subDistrict=" + subDistrict;
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + searchQuery).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		JsonNode actualObj = mapper.readTree(responseString);
		Client actualClient = mapper.treeToValue(actualObj.get(0), Client.class);
		assertEquals(expectedClient, actualClient);
	}

	@Test
	public void shouldFailSearchClientWithInvalidQuery() throws Exception {
		Client expectedClient = (Client) new Client("1").withFirstName(name).withGender(male).withBirthdate(birthDate, false)
				.withDeathdate(deathDate, true).withAddress(address);
		expectedClient.setDateCreated(new DateTime(0l, DateTimeZone.UTC));

		Client otherClient = new Client("2");
		Client otherClient2 = new Client("3");

		createClient(asList(expectedClient, otherClient, otherClient2));

		String searchQuery =
				"search?name=invalid" + name + "&gender=invalid" + male + "&addressType=" + addressType + "&birthDate="
						+ birthDate.toLocalDate().toString() + "&deathDate=" + deathDate.toLocalDate().toString()
						+ "&country=" + country + "&stateProvince=" + stateProvince + "&countryDistrict=" + countryDistrict
						+ "&cityVillage=" + cityVillage + "&town=" + town + "&subDistrict=" + subDistrict;
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + searchQuery).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		assertTrue(responseString.equals("[]"));
		JsonNode actualObj = mapper.readTree(responseString);
		assertNull(actualObj.get(0));
	}

	@Test
	public void shouldFailToSearchIfClientDoesntHaveDateCreateField() throws Exception {
		Client expectedClient = (Client) new Client("1").withFirstName(name).withGender(male).withBirthdate(birthDate, false)
				.withDeathdate(deathDate, true).withAddress(address);

		Client otherClient = new Client("2");
		Client otherClient2 = new Client("3");

		createClient(asList(expectedClient, otherClient, otherClient2));

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc
				.perform(get(BASE_URL + "search?name=" + name).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		assertTrue(responseString.equals("[]"));
		JsonNode actualObj = mapper.readTree(responseString);
		assertNull(actualObj.get(0));
	}

	@Test
	public void shouldFailToSearchIfClientDoesntHaveBirthDate() throws Exception {
		Client expectedClient = (Client) new Client("1").withFirstName(name).withGender(male).withDeathdate(deathDate, true)
				.withAddress(address);
		expectedClient.setDateCreated(new DateTime(0l, DateTimeZone.UTC));

		Client otherClient = new Client("2");
		Client otherClient2 = new Client("3");

		createClient(asList(expectedClient, otherClient, otherClient2));

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc
				.perform(get(BASE_URL + "search?gender=" + male).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		assertTrue(responseString.equals("[]"));
		JsonNode actualObj = mapper.readTree(responseString);
		assertNull(actualObj.get(0));
	}

	@Test
	public void shouldFailToSearchIfClientDoesntHaveAddressField() throws Exception {
		Client expectedClient = (Client) new Client("1").withFirstName(name).withGender(male).withBirthdate(birthDate, false)
				.withDeathdate(deathDate, true);
		expectedClient.setDateCreated(new DateTime(0l, DateTimeZone.UTC));

		Client otherClient = new Client("2");
		Client otherClient2 = new Client("3");

		createClient(asList(expectedClient, otherClient, otherClient2));

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc
				.perform(get(BASE_URL + "search?name=" + name).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		assertTrue(responseString.equals("[]"));
		JsonNode actualObj = mapper.readTree(responseString);
		assertNull(actualObj.get(0));
	}

	@Test
	public void shouldFilterData() throws Exception {
		Client expectedClient = (Client) new Client("1").withFirstName(name).withGender(male).withBirthdate(birthDate, false)
				.withDeathdate(deathDate, true).withAddress(address);
		expectedClient.setDateCreated(new DateTime(0l, DateTimeZone.UTC));

		Client otherClient = new Client("2");
		Client otherClient2 = new Client("3");

		createClient(asList(expectedClient, otherClient, otherClient2));

		String searchQuery =
				"?q=name:" + name + "and gender:" + male + "and addressType:" + addressType + "and birthDate:" + birthDate
						.toLocalDate().toString() + "and deathDate:" + deathDate.toLocalDate().toString() + "and country:"
						+ country + "and stateProvince:" + stateProvince + "and countryDistrict:" + countryDistrict
						+ "and cityVillage:" + cityVillage + "and town:" + town + "and subDistrict:" + subDistrict;
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + searchQuery).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		JsonNode actualObj = mapper.readTree(responseString);
		Client actualClient = mapper.treeToValue(actualObj.get(0), Client.class);
		assertEquals(expectedClient, actualClient);
	}

	@Test
	public void shouldFailToFilterClientWithInvalidQuery() throws Exception {
		Client expectedClient = (Client) new Client("1").withFirstName(name).withGender(male).withBirthdate(birthDate, false)
				.withDeathdate(deathDate, true).withAddress(address);
		expectedClient.setDateCreated(new DateTime(0l, DateTimeZone.UTC));

		Client otherClient = new Client("2");
		Client otherClient2 = new Client("3");

		createClient(asList(expectedClient, otherClient, otherClient2));

		String searchQuery = "?q=firstName:invalid" + name + "and gender:invalid" + male;
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + searchQuery).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		assertTrue(responseString.equals("[]"));
		JsonNode actualObj = mapper.readTree(responseString);
		assertNull(actualObj.get(0));
	}

	@Test
	public void shouldFailToFailIfClientDoesntHaveDateCreateField() throws Exception {
		Client expectedClient = (Client) new Client("1").withFirstName(name).withGender(male).withBirthdate(birthDate, false)
				.withDeathdate(deathDate, true).withAddress(address);

		Client otherClient = new Client("2");
		Client otherClient2 = new Client("3");

		createClient(asList(expectedClient, otherClient, otherClient2));

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + "?q=name:" + name).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		assertTrue(responseString.equals("[]"));
		JsonNode actualObj = mapper.readTree(responseString);
		assertNull(actualObj.get(0));
	}

	@Test
	public void shouldFailToFilterIfClientDoesntHaveBirthDate() throws Exception {
		Client expectedClient = (Client) new Client("1").withFirstName(name).withGender(male).withDeathdate(deathDate, true)
				.withAddress(address);
		expectedClient.setDateCreated(new DateTime(0l, DateTimeZone.UTC));

		Client otherClient = new Client("2");
		Client otherClient2 = new Client("3");

		createClient(asList(expectedClient, otherClient, otherClient2));

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc
				.perform(get(BASE_URL + "?q?gender:" + male).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		assertTrue(responseString.isEmpty());
	}

	@Test
	public void shouldFailToFilterIfClientDoesntHaveAddressField() throws Exception {
		Client expectedClient = (Client) new Client("1").withFirstName(name).withGender(male).withBirthdate(birthDate, false)
				.withDeathdate(deathDate, true);
		expectedClient.setDateCreated(new DateTime(0l, DateTimeZone.UTC));

		Client otherClient = new Client("2");
		Client otherClient2 = new Client("3");

		createClient(asList(expectedClient, otherClient, otherClient2));

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + "?q=name:" + name).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		assertTrue(responseString.equals("[]"));
		JsonNode actualObj = mapper.readTree(responseString);
		assertNull(actualObj.get(0));
	}

	private void createClient(List<Client> allClient) {
		for (Client client : allClient) {
			allClients.add(client);
		}
	}

}
