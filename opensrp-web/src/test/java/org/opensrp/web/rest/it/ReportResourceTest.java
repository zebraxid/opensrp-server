package org.opensrp.web.rest.it;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.domain.Hia2Indicator;
import org.opensrp.domain.Report;
import org.opensrp.repository.AllReports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class ReportResourceTest extends BaseResourceTest {

	public static final String ADD_URL = "add";

	public static final int DURATION = 200;

	public String BASE_URL = "/rest/report/";

	@Autowired
	public AllReports allReports;

	@Before
	public void setUp() {
		allReports.removeAll();
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
	}

	@After
	public void cleanUp() {
		//allReports.removeAll();
	}

	@Test
	public void shouldCreateReportFromSyncData() throws Exception {
		Hia2Indicator hia2Indicator = new Hia2Indicator("indicatorCode", "label", "dhisId", "description", "category",
				"value", "providerId", "updatedAt");
		Report expectedReport = new Report("22", "locationId", null, "reportType", "formSubmissionId", "providerId",
				"status", 300l, 200, asList(hia2Indicator));

		String syncData = "{\"reports\" : [" + mapper.writeValueAsString(expectedReport) + "]}";

		this.mockMvc.perform(post(BASE_URL + ADD_URL).contentType(MediaType.APPLICATION_JSON).body(syncData.getBytes())
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated());

		List<Report> actualReports = allReports.getAll();
		Report actualReport = actualReports.get(0);

		assertEquals(1, actualReports.size());
		assertEquals(expectedReport, actualReport);

	}

	@Test
	public void shouldReturnBadRequestIfSyncDataDoesntHaveReport() throws Exception {
		String emptySyncData = "{}";

		this.mockMvc.perform(post(BASE_URL + ADD_URL).contentType(MediaType.APPLICATION_JSON).body(emptySyncData.getBytes())
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isBadRequest());

	}

	@Test
	public void shouldThrowErrorIfReportJsonCanotBeParsed() throws Exception {
		String invalidSyncData = "{\"reports\" : \"dsf\"}";

		this.mockMvc.perform(
				post(BASE_URL + ADD_URL).contentType(MediaType.APPLICATION_JSON).body(invalidSyncData.getBytes())
						.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isInternalServerError());

	}

}
