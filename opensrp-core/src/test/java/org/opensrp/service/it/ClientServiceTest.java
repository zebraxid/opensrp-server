package org.opensrp.service.it;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.BaseIntegrationTest;
import org.opensrp.domain.Client;
import org.opensrp.repository.AllClients;
import org.opensrp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.utils.AssertionUtil.assertTwoListAreSameIgnoringOrder;
import static org.utils.CouchDbAccessUtils.addObjectToRepository;

public class ClientServiceTest extends BaseIntegrationTest {

	@Autowired
	private AllClients allClients;

	@Autowired
	private ClientService clientService;

	@Before
	public void setUp() {
		allClients.removeAll();
	}

	@After
	public void cleanUp() {
		//allClients.removeAll();
	}

	@Test
	public void shouldFindByBaeEntityId() {
		String baseEntityId = "baseEntityId";
		Client expectedClient = new Client(baseEntityId);
		Client invalidClient = new Client("b2");
		Client invalidClientSecond = new Client("b3");
		List<Client> clientList = asList(expectedClient, invalidClient, invalidClientSecond);
		addObjectToRepository(clientList, allClients);

		Client actualClient = clientService.getByBaseEntityId(baseEntityId);

		assertEquals(expectedClient, actualClient);
	}

	@Test
	public void shouldFindAllClient() {
		Client expectedClient = new Client("b1");
		Client expectedClient2 = new Client("b2");
		Client expectedClient3 = new Client("b3");
		List<Client> expectedClientList = asList(expectedClient, expectedClient2, expectedClient3);
		addObjectToRepository(expectedClientList, allClients);

		List<Client> actualClientList = clientService.findAllClients();

		assertTwoListAreSameIgnoringOrder(expectedClientList, actualClientList);

	}

	@Test
	public void shouldFindAllClientsByIdentifierValue() {
		Client expectedClient = new Client("b1");
		expectedClient.addIdentifier("type", "value");
		Client expectedClient2 = new Client("b2");
		expectedClient2.addIdentifier("type", "value");
		Client invalidClient = new Client("b3");
		invalidClient.addIdentifier("type2", "value2");
		addObjectToRepository(asList(expectedClient, expectedClient2, invalidClient), allClients);
		List<Client> expectedClientList = asList(expectedClient, expectedClient2);

		List<Client> actualClientList = clientService.findAllByIdentifier("value");

		assertTwoListAreSameIgnoringOrder(expectedClientList, actualClientList);
	}

	@Test
	public void shouldFindAllClientsByIdentifierTypeAndValue() {
		Client expectedClient = new Client("b1");
		expectedClient.addIdentifier("type", "value");
		Client expectedClient2 = new Client("b2");
		expectedClient2.addIdentifier("type", "value");
		Client invalidClient = new Client("b3");
		invalidClient.addIdentifier("type2", "value2");
		addObjectToRepository(asList(expectedClient, expectedClient2, invalidClient), allClients);
		List<Client> expectedClientList = asList(expectedClient, expectedClient2);

		List<Client> actualClientList = clientService.findAllByIdentifier("type", "value");
		assertTwoListAreSameIgnoringOrder(expectedClientList, actualClientList);
	}

	@Test
	public void shouldFindAllClientsByRelationIdAndDateCreated() {
		Client expectedClient = new Client("b1");
		expectedClient.addRelationship("mother", "id");
		expectedClient.setDateCreated(new DateTime(100L, DateTimeZone.UTC));
		Client expectedClient2 = new Client("b2");
		expectedClient2.addRelationship("mother", "id");
		expectedClient2.setDateCreated(new DateTime(200L, DateTimeZone.UTC));
		Client invalidClient = new Client("b3");
		invalidClient.addRelationship("mother", "id2");
		expectedClient.setDateCreated(new DateTime(300L, DateTimeZone.UTC));
		addObjectToRepository(asList(expectedClient, expectedClient2, invalidClient), allClients);
		List<Client> expectedClientList = asList(expectedClient, expectedClient2);

		List<Client> actualClientList = clientService
				.findByRelationshipIdAndDateCreated("id", new DateTime(100L, DateTimeZone.UTC).toLocalDate().toString(),
						new DateTime(200L, DateTimeZone.UTC).toLocalDate().toString());

		assertTwoListAreSameIgnoringOrder(expectedClientList, actualClientList);
	}

	@Test
	public void shouldFindByRelationShip() {
		Client expectedClient = new Client("b1");
		expectedClient.addRelationship("mother", "id");
		Client expectedClient2 = new Client("b2");
		expectedClient2.addRelationship("mother", "id");
		Client invalidClient = new Client("b3");
		invalidClient.addRelationship("mother", "id2");
		addObjectToRepository(asList(expectedClient, expectedClient2, invalidClient), allClients);
		List<Client> expectedClientList = asList(expectedClient, expectedClient2);

		List<Client> actualClientList = clientService.findByRelationship("id");

		assertTwoListAreSameIgnoringOrder(expectedClientList, actualClientList);
	}

	@Test
	public void shouldFindByAttributeTypeAndValue() {
		Client expectedClient = new Client("b1");
		expectedClient.addAttribute("type", "value");
		Client expectedClient2 = new Client("b2");
		expectedClient2.addAttribute("type", "value");
		Client invalidClient = new Client("b3");
		invalidClient.addAttribute("type2", "value2");
		addObjectToRepository(asList(expectedClient, expectedClient2, invalidClient), allClients);
		List<Client> expectedClientList = asList(expectedClient, expectedClient2);

		List<Client> actualClientList = clientService.findAllByAttribute("type", "value");
		assertTwoListAreSameIgnoringOrder(expectedClientList, actualClientList);
	}

	@Test
	public void shouldFindAllMatchingName() {
		Client expectedClient = new Client("b1");
		expectedClient.setFirstName("first");
		expectedClient.setLastName("last");
		Client expectedClient2 = new Client("b2");
		expectedClient2.setFirstName("first");
		expectedClient2.setLastName("last");
		Client invalidClient = new Client("b3");
		invalidClient.setFirstName("first1");
		invalidClient.setLastName("last1");
		addObjectToRepository(asList(expectedClient, expectedClient2, invalidClient), allClients);
		List<Client> expectedClientList = asList(expectedClient, expectedClient2);

		List<Client> actualClientList = clientService.findAllByMatchingName("first");
		assertTwoListAreSameIgnoringOrder(expectedClientList, actualClientList);
	}
}
