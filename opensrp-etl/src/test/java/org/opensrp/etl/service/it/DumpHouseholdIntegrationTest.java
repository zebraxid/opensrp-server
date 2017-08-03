package org.opensrp.etl.service.it;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.etl.document.HouseholdDocument;
import org.opensrp.etl.document.processor.HouseholdDocumentProcesor;
import org.opensrp.etl.entity.HousoholdEntity;
import org.opensrp.etl.service.HouseholdServices;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-etl.xml")*/

public class DumpHouseholdIntegrationTest {

	
    private AllHouseHolds allHouseHolds;
    
	private CouchDbInstance dbInstance;
	private StdCouchDbConnector stdCouchDbConnector;
    @Before
    public void setUp() throws Exception {
    	HttpClient httpClient = new StdHttpClient.Builder() 
        //.host("localhost") 
       	.host("localhost")
        .port(5984)
        .username("Admin").password("mPower@1234")
        .socketTimeout(1000) 
        .build(); 
		dbInstance = new StdCouchDbInstance(httpClient);
		stdCouchDbConnector = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());		 
		stdCouchDbConnector.createDatabaseIfNotExists(); 
		allHouseHolds = new AllHouseHolds(2, stdCouchDbConnector);
    }
    
    @Test
    public void testAddHousehold(){
    	HouseHold houseHold = allHouseHolds.findByCaseId("0004c559-fcda-4542-a2d4-a33a51369484");
    	
    	
    	HouseholdDocument doc = HouseholdDocument.getInstance();
    	System.err.println("doc:"+doc);
    	//doc.sendPreparedData(houseHold,householdServices);
    	//householdDocumentProcesor.savePreparedData(houseHold);
    	System.err.println(allHouseHolds.findByTypeAndTimeStamp("HouseHold", 0).size());
    	
    }
   
  
}
