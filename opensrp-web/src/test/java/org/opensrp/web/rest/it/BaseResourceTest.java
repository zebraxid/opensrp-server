package org.opensrp.web.rest.it;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.ResultMatcher;
import org.springframework.test.web.server.result.StatusResultMatchers;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = TestWebContextLoader.class, locations = {
		"classpath:spring/applicationContext-opensrp-web.xml" })
public abstract class BaseResourceTest {

	@Autowired
	protected WebApplicationContext wac;

	protected MockMvc mockMvc;

	protected ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
	}

	protected JsonNode getCallAsJsonNode(String url, String parameter, ResultMatcher expectedStatus) throws Exception {
		String finalUrl = url;
		if (!parameter.isEmpty()) {
			finalUrl = finalUrl + "?" + parameter;
		}

		MvcResult mvcResult = this.mockMvc.perform(get(finalUrl).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(expectedStatus).andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		JsonNode actualObj = mapper.readTree(responseString);
		return actualObj;
	}

	protected byte[] getCallAsByeArray(String url, String parameter, ResultMatcher expectedStatus) throws Exception {
		String finalUrl = url;
		if (!parameter.isEmpty()) {
			finalUrl = finalUrl + "?" + parameter;
		}

		MvcResult mvcResult = this.mockMvc.perform(get(finalUrl).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(expectedStatus).andReturn();

		return mvcResult.getResponse().getContentAsByteArray();
	}
}
