package org.opensrp.web.rest.it;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.opensrp.common.AllConstants;
import org.opensrp.service.ClientService;
import org.opensrp.service.EventService;
import org.opensrp.service.SearchService;
import org.opensrp.web.rest.RestUtils;
import org.opensrp.web.rest.SearchResource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;

import java.text.ParseException;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-opensrp-web.xml")
public class SearchResourceTest {
	
	@Mock
	private SearchService searchService;
	
	@Mock
	private ClientService clientService;
	
	@Mock
	private EventService eventService;
	
	@InjectMocks
	private SearchResource searchResource = new SearchResource(searchService,clientService,eventService);
	
	@Test
	public void testGenerateItemsMap() throws ParseException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		String attributes = "anc_id:09876543,other:12345678";
		request.addParameter(AllConstants.Client.ATTRIBUTES, attributes);
		
		Assert.assertNotNull(request);
		
		String[] myAttributesArray = RestUtils.getStringArrayFilter(AllConstants.Client.ATTRIBUTES, request);
		Map<String, String> attributeMap = searchResource.generateItemsHashMap(myAttributesArray);
		
		Assert.assertTrue(attributeMap != null);
		System.out.println(attributeMap);
	}
}
