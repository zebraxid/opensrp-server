package org.opensrp.service.it;

import org.junit.Before;
import org.junit.Test;
import org.opensrp.BaseIntegrationTest;
import org.opensrp.domain.Event;
import org.opensrp.repository.AllEvents;
import org.opensrp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.opensrp.util.SampleFullDomainObject.*;
import static org.utils.AssertionUtil.assertNewObjectCreation;
import static org.utils.AssertionUtil.assertObjectUpdate;
import static org.utils.AssertionUtil.assertTwoListAreSameIgnoringOrder;
import static org.utils.CouchDbAccessUtils.addObjectToRepository;
import static org.utils.CouchDbAccessUtils.getCouchDbConnector;

public class EventServiceTest extends BaseIntegrationTest {

	@Autowired
	private AllEvents allEvents;

	@Autowired
	private EventService eventService;

	@Before
	public void setUp() {
		allEvents.removeAll();
	}

	@Before
	public void cleanUp() {
		//allEvents.removeAll();
	}

	@Test
	public void shouldFindAllByIdentifier() {
		Event expectedEvent = getEvent();
		Event expectedEvent2 = getEvent();
		expectedEvent2.setBaseEntityId(DIFFERENT_BASE_ENTITY_ID);
		Event invalidEvent = getEvent();
		Map<String, String> identifiers = new HashMap<>(identifier);
		identifiers.put(IDENTIFIER_TYPE, "invalidValue");
		invalidEvent.setIdentifiers(identifiers);
		List<Event> expectedEventList = asList(expectedEvent, expectedEvent2);

		addObjectToRepository(asList(expectedEvent, expectedEvent2, invalidEvent), allEvents);

		List<Event> actualEventList = eventService.findAllByIdentifier(IDENTIFIER_VALUE);

		assertTwoListAreSameIgnoringOrder(expectedEventList, actualEventList);
	}

	@Test
	public void shouldFindAllByIdentifierTypeAndValue() {
		Event expectedEvent = getEvent();
		Event expectedEvent2 = getEvent();
		expectedEvent2.setBaseEntityId(DIFFERENT_BASE_ENTITY_ID);
		Event invalidEvent = getEvent();
		Map<String, String> identifiers = new HashMap<>(identifier);
		identifiers.put(IDENTIFIER_TYPE, "invalidValue");
		invalidEvent.setIdentifiers(identifiers);
		List<Event> expectedEventList = asList(expectedEvent, expectedEvent2);

		addObjectToRepository(asList(expectedEvent, expectedEvent2, invalidEvent), allEvents);

		List<Event> actualEventList = eventService.findAllByIdentifier(IDENTIFIER_TYPE, IDENTIFIER_VALUE);

		assertTwoListAreSameIgnoringOrder(expectedEventList, actualEventList);
	}

	@Test
	public void shouldFindByDocumentId() {
		addObjectToRepository(asList(getEvent()), allEvents);
		Event expectedEvent = allEvents.getAll().get(0);

		Event actualEvent = eventService.getById(expectedEvent.getId());

		assertEquals(expectedEvent, actualEvent);
	}

	@Test
	public void shouldFindByBaseEntityAndFormSubmissionId() {
		Event expectedEvent = getEvent();
		Event invalidEvent = getEvent();
		invalidEvent.setBaseEntityId(DIFFERENT_BASE_ENTITY_ID);

		addObjectToRepository(asList(expectedEvent, invalidEvent), allEvents);

		Event actualEvent = eventService.getByBaseEntityAndFormSubmissionId(BASE_ENTITY_ID, FORM_SUBMISSION_ID);

		assertEquals(expectedEvent, actualEvent);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldThrowExceptionIfMultipleFound() {
		addObjectToRepository(asList(getEvent(), getEvent()), allEvents);

		eventService.getByBaseEntityAndFormSubmissionId(BASE_ENTITY_ID, FORM_SUBMISSION_ID);
	}

	@Test
	public void shouldReturnNullIfNoEventFound() {
		Event actualEvent = eventService.getByBaseEntityAndFormSubmissionId(BASE_ENTITY_ID, FORM_SUBMISSION_ID);

		assertNull(actualEvent);
	}

	@Test
	public void shouldFindByBaseEntityIdAndFormSubmissionIdUsingCouchDbConnector() throws IOException {
		Event expectedEvent = getEvent();
		Event invalidEvent = getEvent();
		invalidEvent.setBaseEntityId(DIFFERENT_BASE_ENTITY_ID);

		addObjectToRepository(asList(expectedEvent, invalidEvent), allEvents);

		Event actualEvent = eventService
				.getByBaseEntityAndFormSubmissionId(getCouchDbConnector("opensrp"), BASE_ENTITY_ID, FORM_SUBMISSION_ID);

		assertEquals(expectedEvent, actualEvent);
	}

	@Test
	public void shouldFindByBaseEntityId() {
		Event expectedEvent = getEvent();
		Event expectedEvent2 = getEvent();
		Event invalidEvent = getEvent();
		invalidEvent.setBaseEntityId(DIFFERENT_BASE_ENTITY_ID);
		List<Event> expectedEvents = asList(expectedEvent, expectedEvent2);

		addObjectToRepository(asList(expectedEvent, expectedEvent2, invalidEvent), allEvents);

		List<Event> actualEvents = eventService.findByBaseEntityId(BASE_ENTITY_ID);

		assertTwoListAreSameIgnoringOrder(expectedEvents, actualEvents);
	}

	@Test
	public void shouldFindByFormSubmissionId() {
		Event expectedEvent = getEvent();
		Event expectedEvent2 = getEvent();
		Event invalidEvent = getEvent();
		invalidEvent.setFormSubmissionId(DIFFERENT_BASE_ENTITY_ID);
		List<Event> expectedEvents = asList(expectedEvent, expectedEvent2);

		addObjectToRepository(asList(expectedEvent, expectedEvent2, invalidEvent), allEvents);

		List<Event> actualEvents = eventService.findByFormSubmissionId(FORM_SUBMISSION_ID);

		assertTwoListAreSameIgnoringOrder(expectedEvents, actualEvents);
	}

	@Test
	public void shouldFindByUniqueIdIdentifier() {
		Event expectedEvent = getEvent();
		Event invalidEvent = getEvent();
		Map<String, String> identifiers = new HashMap<>(identifier);
		identifiers.put(IDENTIFIER_TYPE, "invalidValue");
		invalidEvent.setIdentifiers(identifiers);

		addObjectToRepository(asList(expectedEvent, invalidEvent), allEvents);

		Event actualEvent = eventService.find(IDENTIFIER_VALUE);

		assertEquals(expectedEvent, actualEvent);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfMultipleFoundWithSameIdentifierValue() {
		Event expectedEvent = getEvent();
		Event expectedEvent2 = getEvent();
		expectedEvent2.setBaseEntityId(DIFFERENT_BASE_ENTITY_ID);
		Event invalidEvent = getEvent();
		Map<String, String> identifiers = new HashMap<>(identifier);
		identifiers.put(IDENTIFIER_TYPE, "invalidValue");
		invalidEvent.setIdentifiers(identifiers);

		addObjectToRepository(asList(expectedEvent, expectedEvent2, invalidEvent), allEvents);

		eventService.find(IDENTIFIER_VALUE);
	}

	@Test
	public void shouldReturnNullIfNothingFoundWithIdentifier() {
		assertNull(eventService.find(IDENTIFIER_VALUE));
	}

	@Test
	public void shouldFindByEventObject() {
		Event expectedEvent = getEvent();

		Event invalidEvent = getEvent();
		Map<String, String> identifiers = new HashMap<>(identifier);
		identifiers.put(IDENTIFIER_TYPE, "invalidValue");
		invalidEvent.setIdentifiers(identifiers);

		addObjectToRepository(asList(expectedEvent, invalidEvent), allEvents);

		Event actualEvent = eventService.find(expectedEvent);

		assertEquals(expectedEvent, actualEvent);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfMultipleFoundWithSameEvent() {
		Event expectedEvent = getEvent();
		Event expectedEvent2 = getEvent();
		expectedEvent2.setBaseEntityId(DIFFERENT_BASE_ENTITY_ID);
		Event invalidEvent = getEvent();
		Map<String, String> identifiers = new HashMap<>(identifier);
		identifiers.put(IDENTIFIER_TYPE, "invalidValue");
		invalidEvent.setIdentifiers(identifiers);

		addObjectToRepository(asList(expectedEvent, expectedEvent2, invalidEvent), allEvents);

		eventService.find(expectedEvent);
	}

	@Test
	public void shouldReturnNullIfNothingFoundWithSameEvent() {
		Event expectedEvent = getEvent();
		assertNull(eventService.find(expectedEvent));
	}

	@Test
	public void shouldFindByEventOrDocumentId() {
		addObjectToRepository(asList(getEvent()), allEvents);
		Event expectedEvent = allEvents.getAll().get(0);

		Event actualEvent = eventService.findById(expectedEvent.getId());

		assertEquals(expectedEvent, actualEvent);
	}

	@Test
	public void shouldReturnNullForNullOrEmptyIdInFindById() {
		Event actualEvent = eventService.findById("");
		assertNull(actualEvent);
		actualEvent = eventService.findById(null);
		assertNull(actualEvent);
	}

	@Test
	public void shouldReturnNullIfEventNotFound() {
		addObjectToRepository(asList(getEvent()), allEvents);

		Event actualEvent = eventService.findById(DIFFERENT_BASE_ENTITY_ID);

		assertNull(actualEvent);
	}

	@Test
	public void shouldAddEvent() {
		Event expectedEvent = getEvent();

		Event actualEvent = eventService.addEvent(expectedEvent);

		List<Event> dbEvents = allEvents.getAll();
		assertEquals(1, dbEvents.size());
		assertEquals(expectedEvent, actualEvent);
		assertNewObjectCreation(expectedEvent, dbEvents.get(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfAnEventAlreadyExistWithSameIdentifier() {
		Event expectedEvent = getEvent();
		Event expectedEvent2 = getEvent();
		expectedEvent2.setBaseEntityId(DIFFERENT_BASE_ENTITY_ID);
		Event invalidEvent = getEvent();
		Map<String, String> identifiers = new HashMap<>(identifier);
		identifiers.put(IDENTIFIER_TYPE, "invalidValue");
		invalidEvent.setIdentifiers(identifiers);

		addObjectToRepository(asList(expectedEvent, expectedEvent2, invalidEvent), allEvents);

		eventService.addEvent(expectedEvent);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfAnEventAlreadyExistWithSameBaseEntityIdAndFormSumbissionId() {
		Event expectedEvent = getEvent();
		Event invalidEvent = getEvent();
		Map<String, String> identifiers = new HashMap<>(identifier);
		identifiers.put(IDENTIFIER_TYPE, "invalidValue");
		invalidEvent.setIdentifiers(identifiers);

		addObjectToRepository(asList(expectedEvent, invalidEvent), allEvents);

		eventService.addEvent(expectedEvent);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfNullFormSubmissionId() {
		Event expectedEvent = getEvent();
		Event invalidEvent = getEvent();
		Map<String, String> identifiers = new HashMap<>(identifier);
		identifiers.put(IDENTIFIER_TYPE, "invalidValue");
		invalidEvent.setIdentifiers(identifiers);

		addObjectToRepository(asList(expectedEvent, invalidEvent), allEvents);

		expectedEvent.setFormSubmissionId(null);
		eventService.addEvent(expectedEvent);
	}

	@Test
	public void shouldAddEventWithCouchDbConnector() throws IOException {
		Event expectedEvent = getEvent();

		Event actualEvent = eventService.addEvent(getCouchDbConnector("opensrp"), expectedEvent);

		List<Event> dbEvents = allEvents.getAll();
		assertEquals(1, dbEvents.size());
		assertEquals(expectedEvent, actualEvent);
		assertNewObjectCreation(expectedEvent, dbEvents.get(0));
	}

	@Test(expected = IllegalStateException.class)
	public void shouldThrowExceptionIfAnEventAlreadyExistWithSameBaseEntityIdAndFormSumbissionIdWithCouchDbConector()
			throws IOException {
		Event expectedEvent = getEvent();
		Event invalidEvent = getEvent();
		Map<String, String> identifiers = new HashMap<>(identifier);
		identifiers.put(IDENTIFIER_TYPE, "invalidValue");
		invalidEvent.setIdentifiers(identifiers);

		addObjectToRepository(asList(expectedEvent, invalidEvent), allEvents);

		eventService.addEvent(getCouchDbConnector("opensrp"), expectedEvent);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfNullFormSubmissionIdWithCouchDbConnector() throws IOException {
		Event expectedEvent = getEvent();
		Event invalidEvent = getEvent();
		Map<String, String> identifiers = new HashMap<>(identifier);
		identifiers.put(IDENTIFIER_TYPE, "invalidValue");
		invalidEvent.setIdentifiers(identifiers);

		addObjectToRepository(asList(expectedEvent, invalidEvent), allEvents);

		expectedEvent.setFormSubmissionId(null);
		eventService.addEvent(getCouchDbConnector("opensrrp"), expectedEvent);
	}

	@Test
	public void shouldAddIfNewEntityInAddOrUpdate() {
		Event expectedEvent = getEvent();

		Event actualEvent = eventService.addorUpdateEvent(expectedEvent);

		List<Event> dbEvents = eventService.getAll();
		assertEquals(1, dbEvents.size());
		assertEquals(expectedEvent, actualEvent);

		assertNewObjectCreation(expectedEvent, dbEvents.get(0));
	}

	@Test
	public void shouldUpdateIfExistingEntityInAddOrUpdate() {
		addObjectToRepository(Collections.singletonList(getEvent()), allEvents);
		Event expectedEvent = allEvents.getAll().get(0);

		Event actualEvent = eventService.addorUpdateEvent(expectedEvent);

		List<Event> dbEvents = eventService.getAll();
		assertEquals(1, dbEvents.size());
		assertEquals(expectedEvent, actualEvent);

		assertObjectUpdate(expectedEvent, dbEvents.get(0));
	}

	@Test
	public void shouldUpdateEvent() {
		addObjectToRepository(Collections.singletonList(getEvent()), allEvents);
		Event expectedEvent = allEvents.getAll().get(0);

		eventService.updateEvent(expectedEvent);

		List<Event> dbEvents = eventService.getAll();
		assertEquals(1, dbEvents.size());
		assertObjectUpdate(expectedEvent, dbEvents.get(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfNewEventInUpdate() {
		Event event = getEvent();

		eventService.updateEvent(event);
	}

	@Test
	public void shouldFindBySeverVersion() {
		addObjectToRepository(Collections.singletonList(getEvent()), allEvents);

		Event expectedEvent = allEvents.getAll().get(0);

		List<Event> actualEvents = allEvents.findByServerVersion(expectedEvent.getServerVersion() - 1);

		assertEquals(1, actualEvents.size());
		assertEquals(expectedEvent, actualEvents.get(0));
	}

	@Test
	public void shouldGeAllEvents() {
		Event expectedEvent = getEvent();
		Event expectedEvent2 = getEvent();
		expectedEvent2.setBaseEntityId(DIFFERENT_BASE_ENTITY_ID);
		Map<String, String> identifiers = new HashMap<>(identifier);
		identifiers.put(IDENTIFIER_TYPE, "invalidValue");
		expectedEvent2.setIdentifiers(identifiers);
		List<Event> expectedEvents = asList(expectedEvent, expectedEvent2);

		addObjectToRepository(expectedEvents, allEvents);

		List<Event> actualEvents = eventService.getAll();

		assertTwoListAreSameIgnoringOrder(expectedEvents, actualEvents);
	}

	@Test
	public void shouldFindByObsConceptAndValue() {

	}

}
