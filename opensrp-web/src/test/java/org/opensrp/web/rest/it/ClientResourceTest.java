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

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
	public void test() throws Exception {
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc.perform(get("/test")).andDo(print()).andReturn();
		System.out.println("***************************************************************");
		System.out.println(mvcResult.getRequest().getRequestURI());
		System.out.println(mvcResult.getResponse().getContentAsString());
		//	.andExpect(status().isOk());
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
		MvcResult mvcResult = this.mockMvc.perform(
				post(BASE_URL).contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsBytes(expectedClient))
						.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print()).andReturn();
		String responseString = mvcResult.getResponse().getContentAsString();
		System.out.println(responseString);
		List<Client> allClientsInDb = allClients.getAll();
		Client actualClient = allClientsInDb.get(0);
		actualClient.setDateCreated(null); //So We don't need to mock DateTimeUtil.now()
		assertEquals(1, allClientsInDb.size());
		assertEquals(expectedClient, actualClient);
	}

	private void createClient(List<Client> allClient) {
		for (Client client : allClient) {
			allClients.add(client);
		}
	}

}
