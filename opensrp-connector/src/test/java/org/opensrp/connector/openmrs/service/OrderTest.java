package org.opensrp.connector.openmrs.service;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.domain.Drug;

import com.mysql.jdbc.StringUtils;

public class OrderTest extends TestResourceLoader{
	private OpenmrsOrderService orderService;

	public OrderTest() throws IOException {
		super();
	}
	
	@Before
	public void setup() {
		orderService = new OpenmrsOrderService(openmrsOpenmrsUrl, openmrsUsername, openmrsPassword);
	}
	
	@Test
	public void testDrugImport() throws JSONException {
		//pushToOpenmrsForTest = true;
		if(pushToOpenmrsForTest){
			JSONArray drl = orderService.getAllDrugs();
			for (int i = 0; i < drl.length(); i++) {
				JSONObject d = drl.getJSONObject(i);
				System.out.println(d);
				Drug convertedDrug = OpenmrsOrderService.toDrug(d);
				Assert.assertFalse(StringUtils.isEmptyOrWhitespaceOnly(convertedDrug.getDrugName()));
			}
		}
	}
}
