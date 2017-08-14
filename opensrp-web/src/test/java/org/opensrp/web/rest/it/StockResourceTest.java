package org.opensrp.web.rest.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.opensrp.common.AllConstants.Stock.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.opensrp.repository.AllStocks;
import org.opensrp.service.StockService;
import org.opensrp.web.rest.StockResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import java.util.List;

public class StockResourceTest extends BaseResourceTest {

	private static final String BASE_URL = "/rest/stockresource/";

	@Autowired
	private AllStocks allStocks;

	@Autowired
	private StockResource stockResource;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		allStocks.removeAll();
	}

	@After
	public void cleanUp() {
		//allStocks.removeAll();
	}

	@Test
	public void testRequiredProperties() {
		List<String> actulaRequiredProperties = stockResource.requiredProperties();

		assertEquals(2, actulaRequiredProperties.size());
		assertTrue(actulaRequiredProperties.contains(PROVIDERID));
		assertTrue(actulaRequiredProperties.contains(TIMESTAMP));
	}


	/*@Test
	@Ignore
	public void testClientSearch() throws Exception {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		//  mockRequest.setContentType(MediaType.APPLICATION_JSON.toString());
		mockRequest.setMethod("GET");
		mockRequest.setRequestURI("/rest/stock/sync/");
		mockRequest.setAttribute(HandlerMapping.class.getName() + ".introspectTypeLevelMapping", true);
		mockRequest.addParameter(IDENTIFIER, "003");
		mockRequest.addParameter(VACCINE_TYPE_ID, "VTID");
		mockRequest.addParameter(PROVIDERID, "4-2");

		AnnotationMethodHandlerAdapter handlerAdapter = new AnnotationMethodHandlerAdapter();
		HttpMessageConverter[] messageConverters = { new MappingJacksonHttpMessageConverter() };
		handlerAdapter.setMessageConverters(messageConverters);

		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		mockResponse.addHeader("chii", "noa");
		handlerAdapter.handle(mockRequest, mockResponse, ss);

		String actual = mockResponse.getContentAsString();
		System.out.println(actual);

	}*/
}
