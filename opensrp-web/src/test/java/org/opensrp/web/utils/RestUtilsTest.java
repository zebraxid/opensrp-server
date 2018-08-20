package org.opensrp.web.utils;

import java.text.ParseException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.opensrp.common.AllConstants.Client;
import org.opensrp.web.rest.RestUtils;
import org.springframework.mock.web.MockHttpServletRequest;

public class RestUtilsTest {
	@Test
	public void test() throws ParseException {
		MockHttpServletRequest req = new MockHttpServletRequest();
		req.addParameter(Client.BIRTH_DATE, "2015-02-01:2016-03-01");
		
		System.out.println(Arrays.toString(RestUtils.getDateRangeFilter(Client.BIRTH_DATE, req)));
	}
	
	@Test
	public void testGetStringArrayFilter() throws ParseException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		String attributes = "anc_id:09876543,other:12345678";
		request.addParameter(Client.ATTRIBUTES, attributes);
		
		Assert.assertNotNull(request);
		
		System.out.println(Arrays.toString(RestUtils.getStringArrayFilter(Client.ATTRIBUTES, request)));
	}
}
