package org.opensrp.register.mcare.encounter.sync;

import static org.mockito.MockitoAnnotations.initMocks;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;

public class TestConfig {
	
	private CouchDbInstance dbInstance;
	private StdCouchDbConnector stdCouchDbConnectorOpensrpForm;
	private StdCouchDbConnector stdCouchDbConnectorOpensrp;
	private StdCouchDbConnector stdCouchDbConnectorForSchedule;
	
	public TestConfig(){
		initMocks(this);
		HttpClient httpClient = new StdHttpClient.Builder() 
	        .host("localhost") 
	       	.username("Admin").password("mPower@1234")
	        .port(5984) 
	        .socketTimeout(1000) 
	        .build(); 
			dbInstance = new StdCouchDbInstance(httpClient);
			stdCouchDbConnectorOpensrpForm = new StdCouchDbConnector("opensrp-form", dbInstance, new StdObjectMapperFactory());
			stdCouchDbConnectorOpensrpForm.createDatabaseIfNotExists();
			stdCouchDbConnectorOpensrp = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());
			stdCouchDbConnectorOpensrp.createDatabaseIfNotExists();
			
			stdCouchDbConnectorForSchedule = new StdCouchDbConnector("motech-scheduletracking-api", dbInstance, new StdObjectMapperFactory());
			stdCouchDbConnectorForSchedule.createDatabaseIfNotExists();
			
	}
	

	public StdCouchDbConnector getStdCouchDbConnectorForOpensrpForm(){
		return stdCouchDbConnectorOpensrpForm;
	}
	public StdCouchDbConnector getStdCouchDbConnectorForOpensrp(){
		return stdCouchDbConnectorOpensrp;
	}
	
	public StdCouchDbConnector getStdCouchDbConnectorForSchedule(){
		return stdCouchDbConnectorForSchedule;
	}
	public CouchDbInstance getDbInstance() {
		return dbInstance;
	}

	public void setDbInstance(CouchDbInstance dbInstance) {
		this.dbInstance = dbInstance;
	}
	
	

}
