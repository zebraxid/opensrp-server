package org.opensrp.register.mcare.encounter.sync;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.MockitoAnnotations.initMocks;

import java.text.ParseException;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.opensrp.register.encounter.sync.*;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.service.EventService;


/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
"classpath*:spring/test-applicationContext-opensrp.xml"})*/

public class EncounterSyncTest {
	@Mock
	EventService eventService;
	@Mock
	FeedHandler makeFormSubmission;
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
	
	@Ignore@Test
	public void shuoldCreateEncounter(){
		/*FormHandler makeFormSubmission = new FormHandler(formSubmissions);
		formSubmission =makeFormSubmission.createFormSumission();
		formSubmissions.remove(formSubmission);
		assertNotNull(formSubmission);*/
		
	}
	
	@Ignore@Test
	public void ShouldRemoveSubstringFromString() throws Exception{
		FeedHandler makeFormSubmission = new FeedHandler();
		String StringAfterFilter = makeFormSubmission.stringFilter("Immunization Incident Template: BCG (Tuberculosis, live attenuated), 2017-01-25, true, 0.0");
		assertEquals(makeFormSubmission.getTTFromString(StringAfterFilter, "BCG"),true);
		
		Assert.assertNotSame(makeFormSubmission.getTTFromString(StringAfterFilter, "TT"), true);
		String getDateOfVaccine = makeFormSubmission.getDateFromString(StringAfterFilter);
		Assert.assertNotSame(getDateOfVaccine,null);
		double getDoseOfVaccine = makeFormSubmission.getDoseFromString(StringAfterFilter);
		Assert.assertNotSame(getDoseOfVaccine,null);
		int i =(int) getDoseOfVaccine;
		System.out.println(i);
	}
	
	
	
}
