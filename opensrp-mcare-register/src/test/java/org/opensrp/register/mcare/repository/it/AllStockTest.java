package org.opensrp.register.mcare.repository.it;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.api.domain.User;
import org.opensrp.connector.HttpUtil;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.register.mcare.domain.Stock;
import org.opensrp.register.mcare.repository.AllStocks;
import org.powermock.api.mockito.PowerMockito;

/**
 * Created by proshanto on 4/3/17.
 */
public class AllStockTest {

    private AllStocks allStocks;

    private CouchDbInstance dbInstance;
    private StdCouchDbConnector stdCouchDbConnector;
    

	
    @Before
    public void setUp() throws Exception {
        HttpClient httpClient = new StdHttpClient.Builder()
                .host("localhost")
                //.host("192.168.19.55")
                .port(5984)
                .username("Admin").password("mPower@1234")
                .socketTimeout(1000)
                .build();
        dbInstance = new StdCouchDbInstance(httpClient);
        stdCouchDbConnector = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());
        stdCouchDbConnector.createDatabaseIfNotExists();
        allStocks = new AllStocks(2, stdCouchDbConnector);

       
    }
    
    @Test
    public void addStockTest(){
        Stock stock =new Stock();
        stock.setCaseId("aaaa-eee-rrree");
        allStocks.add(stock);
        System.out.print("33t");
        Assert.assertNotNull("ddsd");
        allStocks.remove(stock);
        
    }
}
