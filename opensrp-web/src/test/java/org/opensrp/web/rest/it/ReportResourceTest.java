package org.opensrp.web.rest.it;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.repository.AllReports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class ReportResourceTest extends BaseResourceTest {

	public static final String ADD_URL = "add";

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
