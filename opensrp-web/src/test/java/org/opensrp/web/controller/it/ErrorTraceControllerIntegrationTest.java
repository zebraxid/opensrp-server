package org.opensrp.web.controller.it;

import org.codehaus.jackson.JsonNode;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.domain.ErrorTrace;
import org.opensrp.domain.ErrorTraceForm;
import org.opensrp.repository.AllErrorTrace;
import org.opensrp.web.rest.it.BaseResourceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.motechproject.delivery.schedule.util.SameItems.hasSameItemsAs;
import static org.opensrp.web.rest.it.ResourceTestUtility.createErrorTraces;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorTraceControllerIntegrationTest extends BaseResourceTest {

	private final static String BASE_URL = "/errorhandler";

	@Autowired
	AllErrorTrace allErrorTrace;

	ErrorTraceForm errorTraceForm;

	@Before
	public void setUP() {
		allErrorTrace.removeAll();
		errorTraceForm = new ErrorTraceForm();
	}

	@After
	public void cleanUp() {
		allErrorTrace.removeAll();
	}

	@Test
	@Ignore
	public void shouldReturnErrorIndex() throws Exception {
		String url = BASE_URL + "/index";
		JsonNode returnedObject = getCallAsJsonNode(url, "", status().isOk());

		System.out.println(returnedObject);

		ModelAndView modelAndView = mapper.treeToValue(returnedObject, ModelAndView.class);
		Map<String, Object> actualModel = modelAndView.getModelMap();
		List<String> actualStatusOptions = mapper
				.treeToValue(mapper.readTree((String) actualModel.get("statusOptions")), List.class);

		assertEquals("home_error", modelAndView.getViewName());
		assertEquals("all", actualModel.get("type"));
		assertEquals(errorTraceForm.getStatusOptions(), hasSameItemsAs(actualStatusOptions));

	}

	@Test
	public void shouldReturnAllError() throws Exception {
		String url = BASE_URL + "/errortrace";
		ErrorTrace expectedErrorTrace = new ErrorTrace(new DateTime(0l, DateTimeZone.UTC), "documentType", "errorType",
				"occuredAt", "stackTrace", "status");

		createErrorTraces(asList(expectedErrorTrace), allErrorTrace);

		JsonNode returnedObject = getCallAsJsonNode(url, "", status().isOk());

		ErrorTrace actualErrorTrace = mapper.treeToValue(returnedObject.get(0), ErrorTrace.class);

		assertEquals(expectedErrorTrace, actualErrorTrace);

	}

}
