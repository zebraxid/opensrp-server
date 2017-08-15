package org.opensrp.web.rest.it;

import org.codehaus.jackson.JsonNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public abstract class RestResourceTest<D extends MotechBaseRepository, T> extends BaseResourceTest {

	String BASE_URL;

	protected D databaseAccesObject;

	private Class<T> classTypeOfT;

	protected RestResourceTest(Class<T> classTypeOfT, String baseUrl) {
		this.BASE_URL = baseUrl;
		this.classTypeOfT = classTypeOfT;
	}

	/**
	 * Client should implement this function to set @param databaseAccessObject.
	 * This function should be called in @setUp method.
	 */
	public abstract void setDatabaseAccessObjectOfParent();

	public void assertAddByUniqueId(T expectedObject, String uniqueId) throws Exception {

		databaseAccesObject.add(expectedObject);
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + uniqueId).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andReturn();

		String responseString = mvcResult.getResponse().getContentAsString();
		JsonNode actualObj = mapper.readTree(responseString);
		T actualObject = mapper.treeToValue(actualObj, classTypeOfT);

		assertEquals(expectedObject, actualObject);
	}

}
