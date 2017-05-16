package org.opensrp.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.MockitoAnnotations.initMocks;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.opensrp.encounter.sync.MakeFormSubmission;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.repository.AllFormSubmissions;


/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
"classpath*:spring/test-applicationContext-opensrp.xml"})*/

public class EncounterSyncTest {
	@Mock
	EventService eventService;
	@Mock
	MakeFormSubmission makeFormSubmission;
	@Mock
	FormSubmission formSubmission;
	@Mock
    private AllFormSubmissions formSubmissions;
	private CouchDbInstance dbInstance;
	private StdCouchDbConnector stdCouchDbConnector;
	
	@Before
	public void setUp() throws Exception
	{
		initMocks(this);
		 HttpClient httpClient = new StdHttpClient.Builder() 
	        .host("localhost") 
	       	.username("Admin").password("mPower@1234")
	        .port(5984) 
	        .socketTimeout(1000) 
	        .build(); 
			dbInstance = new StdCouchDbInstance(httpClient);
			stdCouchDbConnector = new StdCouchDbConnector("opensrp-form", dbInstance, new StdObjectMapperFactory());
			stdCouchDbConnector.createDatabaseIfNotExists();
			formSubmissions = new AllFormSubmissions(stdCouchDbConnector);
		
	}
	
	@Test
	public void shuoldCreateEncounter(){
		MakeFormSubmission makeFormSubmission = new MakeFormSubmission(formSubmissions);
		formSubmission =makeFormSubmission.createFormSumission();
		formSubmissions.remove(formSubmission);
		assertNotNull(formSubmission);
		
	}
	
}
