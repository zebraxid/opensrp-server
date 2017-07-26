package org.opensrp.web.rest.it;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.opensrp.domain.Address;
import org.opensrp.domain.Client;
import org.opensrp.repository.AllClients;
import org.opensrp.service.ClientService;
import org.opensrp.web.rest.ClientResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.util.NestedServletException;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.opensrp.common.AllConstants.BaseEntity.SUB_TOWN;
import static org.opensrp.common.AllConstants.BaseEntity.TOWN;
import static org.opensrp.common.AllConstants.Client.BIRTH_DATE;
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

	MockHttpServletRequest req;

	MockMvc mockMvc;

	@Mock
	private MockHttpServletResponse resp = new MockHttpServletResponse();

	@Autowired
	private ClientService cs;

	@Autowired
	private ClientResource cr;

	@Autowired
	private AllClients allClients;

	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() {
		allClients.removeAll();
	}

	@After
	public void tearDown() {
		//allClients.removeAll();
	}

	@Test
	public void testClientSearch() throws Exception {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		//  mockRequest.setContentType(MediaType.APPLICATION_JSON.toString());
		mockRequest.setMethod("GET");
		mockRequest.setRequestURI("/rest/client/search");
		mockRequest.setAttribute(HandlerMapping.class.getName() + ".introspectTypeLevelMapping", true);
		mockRequest.addParameter("name", "firstName100");
		mockRequest.addParameter(GENDER, "MALE");
		mockRequest.addParameter(BIRTH_DATE, "2016-02-01:2016-03-21");
		//mockRequest.addParameter(DEATH_DATE, request);
		//mockRequest.addParameter(ADDRESS_TYPE, request);
		//mockRequest.addParameter(COUNTRY, request);
		//mockRequest.addParameter(STATE_PROVINCE, request);
		//mockRequest.addParameter(CITY_VILLAGE, request);
		//mockRequest.addParameter(COUNTY_DISTRICT, request);
		//mockRequest.addParameter(SUB_DISTRICT, request);
		mockRequest.addParameter(TOWN, "Korangi");
		mockRequest.addParameter(SUB_TOWN, "UC0");

		AnnotationMethodHandlerAdapter handlerAdapter = new AnnotationMethodHandlerAdapter();
		HttpMessageConverter[] messageConverters = { new MappingJacksonHttpMessageConverter() };
		handlerAdapter.setMessageConverters(messageConverters);

		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		handlerAdapter.handle(mockRequest, mockResponse, cr);

		String actual = mockResponse.getContentAsString();
		System.out.println(actual);

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
		String addressType = "addressType";
		String country = "country";
		String stateProvince = "stateProvince";
		String cityVillage = "cityVillage";
		String countryDistrict = "countryDistrict";
		String subDistrict = "subDistrict";
		String town = "town";
		String name = "name";
		String male = "male";
		DateTime birthdate = new DateTime(0l, DateTimeZone.UTC);
		DateTime deathdate = new DateTime(1l, DateTimeZone.UTC);

		Address address = new Address().withAddressType(addressType).withCountry(country).withStateProvince(stateProvince)
				.withCityVillage(cityVillage).withCountyDistrict(countryDistrict).withSubDistrict(subDistrict)
				.withTown(town);

		Client expectedClient = (Client) new Client("1").withFirstName(name).withGender(male).withBirthdate(birthdate, false)
				.withDeathdate(deathdate, true).withAddress(address);
		expectedClient.setDateCreated(new DateTime(0l, DateTimeZone.UTC));

		Client otherClient = new Client("2");
		Client otherClient2 = new Client("3");

		createClient(asList(expectedClient, otherClient, otherClient2));

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc
				.perform(get(BASE_URL + "search?name=name").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		JsonNode actualObj = mapper.readTree(responseString);
		Client actualClient = mapper.treeToValue(actualObj.get(0), Client.class);
		assertEquals(expectedClient, actualClient);

	}

	private void createClient(List<Client> allClient) {
		for (Client client : allClient) {
			allClients.add(client);
		}
	}

}
