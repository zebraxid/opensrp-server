package org.opensrp.web.rest.it;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.domain.Event;
import org.opensrp.repository.AllEvents;
import org.opensrp.service.EventService;
import org.opensrp.web.rest.EventResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.opensrp.common.AllConstants.BaseEntity.BASE_ENTITY_ID;
import static org.opensrp.common.AllConstants.Event.EVENT_TYPE;
import static org.opensrp.common.AllConstants.Event.PROVIDER_ID;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = TestWebContextLoader.class, locations = {
		"classpath:spring/applicationContext-opensrp-web.xml" })
public class EventResourceTest {

	private final static String BASE_URL = "/rest/event/";

	@Autowired
	private WebApplicationContext wac;

	MockMvc mockMvc;

	@Autowired
	private AllEvents allEvents;

	@Autowired
	private EventService eventService;

	@Autowired
	private EventResource eventResource;

	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() {
		allEvents.removeAll();
	}

	@After
	public void tearDown() {
		allEvents.removeAll();
	}

	@Test
	public void testRequiredProperties() {
		List<String> requiredProperties = eventResource.requiredProperties();
		assertTrue(requiredProperties.contains(PROVIDER_ID));
		assertTrue(requiredProperties.contains(EVENT_TYPE));
		assertTrue(requiredProperties.contains(BASE_ENTITY_ID));
	}

	@Test
	public void shouldFindEventById() throws Exception {
		Event expectedEvent = new Event("1", "eventType", new DateTime(0l, DateTimeZone.UTC), "entityType", "providerId",
				"locationId", "formSubmissionId");
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put("key", "value");
		expectedEvent.setIdentifiers(identifiers);
		createEvent(asList(expectedEvent));

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + "value").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		JsonNode actualObj = mapper.readTree(responseString);
		Event actualEvent = mapper.treeToValue(actualObj, Event.class);

		assertEquals(expectedEvent, actualEvent);

	}

	@Test
	public void shouldNotFindEvnet() throws Exception {
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + "value").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		String responseString = mvcResult.getResponse().getContentAsString();
		assertTrue(responseString.isEmpty());
	}

	@Test
	public void shouldCreateEventWithAllRequiredProperties() throws Exception {
		Event expectedEvent = new Event("1", "eventType", new DateTime(0l, DateTimeZone.UTC), "entityType", "providerId",
				"locationId", "formSubmissionId");
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put("key", "value");
		expectedEvent.setIdentifiers(identifiers);

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		this.mockMvc.perform(
				post(BASE_URL).contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsBytes(expectedEvent))
						.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		List<Event> allEventsInDB = allEvents.getAll();
		Event actualEvent = allEventsInDB.get(0);
		actualEvent.setDateCreated(null); //So We don't need to mock DateTimeUtil.now()
		assertEquals(1, allEventsInDB.size());
		assertEquals(expectedEvent, actualEvent);
	}

	@Test(expected = NestedServletException.class)
	public void shouldNotCreateEventWithOutProviderId() throws Exception {
		Event expectedEvent = new Event("1", "eventType", new DateTime(0l, DateTimeZone.UTC), "entityType", "providerId",
				"locationId", "formSubmissionId");
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put("key", "value");
		expectedEvent.setIdentifiers(identifiers);
		expectedEvent.setProviderId(null);

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		this.mockMvc.perform(
				post(BASE_URL).contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsBytes(expectedEvent))
						.accept(MediaType.APPLICATION_JSON));

		List<Event> allEventsInDb = allEvents.getAll();
		assertEquals(0, allEventsInDb.size());
	}

	@Test(expected = NestedServletException.class)
	public void shouldNotCreateEventWithOutEventType() throws Exception {
		Event expectedEvent = new Event("1", "eventType", new DateTime(0l, DateTimeZone.UTC), "entityType", "providerId",
				"locationId", "formSubmissionId");
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put("key", "value");
		expectedEvent.setIdentifiers(identifiers);
		expectedEvent.setEventType(null);

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		this.mockMvc.perform(
				post(BASE_URL).contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsBytes(expectedEvent))
						.accept(MediaType.APPLICATION_JSON));

		List<Event> allEventsInDb = allEvents.getAll();
		assertEquals(0, allEventsInDb.size());
	}

	@Test(expected = NestedServletException.class)
	public void shouldNotCreateEventWithOutBaseEntityId() throws Exception {
		Event expectedEvent = new Event("1", "eventType", new DateTime(0l, DateTimeZone.UTC), "entityType", "providerId",
				"locationId", "formSubmissionId");
		Map<String, String> identifiers = new HashMap<>();
		identifiers.put("key", "value");
		expectedEvent.setIdentifiers(identifiers);
		expectedEvent.setBaseEntityId(null);

		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		this.mockMvc.perform(
				post(BASE_URL).contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsBytes(expectedEvent))
						.accept(MediaType.APPLICATION_JSON));

		List<Event> allEventsInDb = allEvents.getAll();
		assertEquals(0, allEventsInDb.size());
	}

	private void createEvent(List<Event> events) {
		for (Event event : events) {
			allEvents.add(event);
		}
	}

}
