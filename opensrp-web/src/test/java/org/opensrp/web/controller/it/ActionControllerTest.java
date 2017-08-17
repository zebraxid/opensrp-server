package org.opensrp.web.controller.it;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.domain.Client;
import org.opensrp.dto.ActionData;
import org.opensrp.dto.AlertStatus;
import org.opensrp.repository.AllClients;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.Alert;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.scheduler.repository.AllAlerts;
import org.opensrp.web.controller.ActionConvertor;
import org.opensrp.web.rest.it.BaseResourceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.opensrp.dto.AlertStatus.normal;
import static org.opensrp.dto.BeneficiaryType.mother;
import static org.opensrp.web.rest.it.ResourceTestUtility.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class ActionControllerTest extends BaseResourceTest {

	@Autowired
	private AllActions allActions;

	@Autowired
	private AllClients allClients;

	@Autowired
	private AllAlerts allAlerts;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		allClients.removeAll();
		allActions.removeAll();
		allAlerts.removeAll();
	}

	@After
	public void cleanUp() {
		/*allClients.removeAll();
		allActions.removeAll();
		allAlerts.removeAll();*/
	}

	/*@Test
	public void shouldGiveAlertActionForANMSinceTimeStamp() throws Exception {
		org.opensrp.scheduler.Action alertAction = new org.opensrp.scheduler.Action("Case X", "ANM 1", ActionData
				.createAlert(mother.value(), "Ante Natal Care - Normal", "ANC 1", normal, DateTime.now(),
						DateTime.now().plusDays(3)));
		when(actionService.getNewAlertsForANM("ANM 1", 0L)).thenReturn(asList(alertAction));

		Action expectedAlertActionItem = ActionConvertor.from(alertAction);
		ActionController actionController = new ActionController(actionService, null, null);

		assertThat(asList(expectedAlertActionItem), hasSameItemsAs(actionController.getNewActionForANM("ANM 1", 0L)));
	}*/

	@Test
	public void shouldFetchNewActionsBasedOnAnmIdAndTimestamp() throws Exception {
		String url = "/actions";
		Map<String, String> data = new HashMap<>();
		data.put("key", "value");
		Action expectedAction = new Action("Case X", "ANM 1", ActionData
				.createAlert(mother.value(), "Ante Natal Care - Normal", "ANC 1", normal, DateTime.now(),
						DateTime.now().plusDays(3)));
		createActions(asList(expectedAction), allActions);

		org.opensrp.dto.Action expectedActionDto = ActionConvertor.from(expectedAction);

		MvcResult mvcResult = this.mockMvc.perform(
				get(url + "?anmIdentifier=" + "ANM 1" + "&timeStamp=" + new DateTime().minusDays(1).getMillis())
						.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		JsonNode actualObj = mapper.readTree(responseString);
		org.opensrp.dto.Action actualActionDto = mapper.treeToValue(actualObj.get(0), org.opensrp.dto.Action.class);

		assertEquals(expectedActionDto, actualActionDto);
	}

	@Test
	public void shouldFetchNewActionsBasedOnBaseEntityIdAndTimestamp() throws Exception {
		String url = "/actions";
		Map<String, String> data = new HashMap<>();
		data.put("key", "value");
		Action expectedAction = new Action("Case X", "ANM 1", ActionData
				.createAlert(mother.value(), "Ante Natal Care - Normal", "ANC 1", normal, DateTime.now(),
						DateTime.now().plusDays(3)));
		createActions(asList(expectedAction), allActions);

		org.opensrp.dto.Action expectedActionDto = ActionConvertor.from(expectedAction);

		MvcResult mvcResult = this.mockMvc.perform(
				get(url + "?baseEntityId=" + "Case X" + "&timeStamp=" + new DateTime().minusDays(1).getMillis())
						.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		JsonNode actualObj = mapper.readTree(responseString);
		org.opensrp.dto.Action actualActionDto = mapper.treeToValue(actualObj.get(0), org.opensrp.dto.Action.class);

		assertEquals(expectedActionDto, actualActionDto);
	}

	@Test
	public void shouldRemoveAlertBasedOnKey() throws Exception {
		String url = "/alert_delete";
		Client expectedClient = new Client("1").withFirstName("first").withGender("male")
				.withBirthdate(new DateTime(0l, DateTimeZone.UTC), false);
		createClients(asList(expectedClient), allClients);

		Alert alert = new Alert("providerId", "1", "beneficiaryType", Alert.AlertType.notification, Alert.TriggerType.event,
				"20160727KiSafaiMuhim", "triggerCode", new DateTime(0l, DateTimeZone.UTC),
				new DateTime(1l, DateTimeZone.UTC), AlertStatus.normal, new HashMap<String, String>());
		Alert duplicateAlert = new Alert("providerId", "1", "beneficiaryType", Alert.AlertType.notification,
				Alert.TriggerType.event, "20160727KiSafaiMuhim", "triggerCode", new DateTime(0l, DateTimeZone.UTC),
				new DateTime(1l, DateTimeZone.UTC), AlertStatus.normal, new HashMap<String, String>());

		createAlerts(asList(alert, duplicateAlert), allAlerts);
		assertEquals(2, allAlerts.getAll().size());

		MvcResult mvcResult = this.mockMvc
				.perform(get(url + "?key=" + "20160727KiSafaiMuhim").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andReturn();

		assertEquals(1, allAlerts.getAll().size());
	}

	@Test
	public void shouldFetchNewActionsBasedOnTimestamp() throws Exception {
		String url = "/actions/sync";
		Map<String, String> data = new HashMap<>();
		data.put("key", "value");
		Action expectedAction = new Action("Case X", "ANM 1", ActionData
				.createAlert(mother.value(), "Ante Natal Care - Normal", "ANC 1", normal, DateTime.now(),
						DateTime.now().plusDays(3)));
		createActions(asList(expectedAction), allActions);

		MvcResult mvcResult = this.mockMvc.perform(
				get(url + "?providerId=" + "ANM 1" + "&serverVersion=" + new DateTime().minusDays(1).getMillis())
						.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		JsonNode actualObj = mapper.readTree(responseString);
		int actualActionsSize = Integer.parseInt(actualObj.get("no_of_actions").asText());
		ObjectNode actionObj = (ObjectNode) actualObj.get("actions").get(0);
		actionObj.remove("id");
		actionObj.remove("revision");
		Action actualAction = mapper.treeToValue(actionObj, Action.class);

		assertEquals(1, actualActionsSize);
		assertEquals(expectedAction, actualAction);
	}

	@Test
	public void shouldReturnEmptyResponseForInvalidTimeStampWhileSync() throws Exception {
		String url = "/actions/sync";
		Map<String, String> data = new HashMap<>();
		data.put("key", "value");
		Action expectedAction = new Action("Case X", "ANM 1", ActionData
				.createAlert(mother.value(), "Ante Natal Care - Normal", "ANC 1", normal, DateTime.now(),
						DateTime.now().plusDays(3)));
		createActions(asList(expectedAction), allActions);

		MvcResult mvcResult = this.mockMvc
				.perform(get(url + "?providerId=" + "ANM 1" + "&serverVersion=" + "dsfs").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isInternalServerError()).andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		JsonNode actualObj = mapper.readTree(responseString);
		assertEquals("Error occurred", actualObj.get("msg").asText());
	}

}
