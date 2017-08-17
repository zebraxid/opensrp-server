package org.opensrp.web.rest.it;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.domain.Stock;
import org.opensrp.repository.AllStocks;
import org.opensrp.web.rest.StockResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.opensrp.common.AllConstants.Stock.PROVIDERID;
import static org.opensrp.common.AllConstants.Stock.TIMESTAMP;

public class StockResourceTest extends RestResourceTest<AllStocks, Stock> {

	private static final String BASE_URL = "/rest/stockresource/";

	@Autowired
	private AllStocks allStocks;

	@Autowired
	private StockResource stockResource;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		allStocks.removeAll();
		setDatabaseAccessObjectOfParent();
	}

	@After
	public void cleanUp() {
		//allStocks.removeAll();
	}


	@Autowired
	public StockResourceTest() {
		super(Stock.class, BASE_URL);
	}

	@Override
	public void setDatabaseAccessObjectOfParent() {
		this.databaseAccesObject = allStocks;
	}

	@Test
	public void testRequiredProperties() {
		List<String> actulaRequiredProperties = stockResource.requiredProperties();

		assertEquals(2, actulaRequiredProperties.size());
		assertTrue(actulaRequiredProperties.contains(PROVIDERID));
		assertTrue(actulaRequiredProperties.contains(TIMESTAMP));
	}

	@Test
	@Ignore
	public void shouldFindByProviderId() throws Exception {
		Stock expectedStock = new Stock(200l, "vaccineTypeId", "transactionType", "providerId", 3,
				new DateTime(0l, DateTimeZone.UTC).getMillis(), "toFrom", new DateTime(0l, DateTimeZone.UTC).getMillis(),
				223l);
		assertAddByUniqueId(expectedStock, "providerId");
	}

}
