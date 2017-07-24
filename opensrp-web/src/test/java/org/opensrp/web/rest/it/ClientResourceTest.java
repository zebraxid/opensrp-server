package org.opensrp.web.rest.it;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import static org.opensrp.common.AllConstants.BaseEntity.SUB_TOWN;
import static org.opensrp.common.AllConstants.BaseEntity.TOWN;
import static org.opensrp.common.AllConstants.Client.BIRTH_DATE;
import static org.opensrp.common.AllConstants.Client.GENDER;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = TestWebContextLoader.class, locations = {
		"classpath:spring/applicationContext-opensrp-web.xml" })
public class ClientResourceTest {

	@Autowired
	private WebApplicationContext wac;

	private static final String BASE_URL = "http://localhost:8080/opensrp-web/client";

	private RestTemplate restTemplate;

	MockHttpServletRequest req;

	MockMvc mockMvc;

	@Mock
	private MockHttpServletResponse resp = new MockHttpServletResponse();

	@Autowired
	private ClientService cs;

	@Autowired
	private ClientResource cr;

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
		MvcResult mvcResult = this.mockMvc.perform(get("/").accept(MediaType.TEXT_HTML)).andDo(print()).andReturn();
		System.out.println("***************************************************************");
		System.out.println(this.mockMvc.perform(get("/")).andReturn().getRequest().getRequestURI());
		System.out.println(mvcResult.getResponse().getContentAsString());
		//	.andExpect(status().isOk());
	}

}
