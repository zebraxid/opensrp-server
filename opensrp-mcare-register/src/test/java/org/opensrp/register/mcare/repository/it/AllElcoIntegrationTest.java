package org.opensrp.register.mcare.repository.it;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.opensrp.common.util.EasyMap.create;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.ektorp.CouchDbInstance;
import org.ektorp.ViewResult;
import org.ektorp.ViewResult.Row;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.common.util.DateUtil;
import org.opensrp.common.util.WeekBoundariesAndTimestamps;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register-mcare.xml")*/
public class AllElcoIntegrationTest {

	@Autowired
    private AllHouseHolds allHouseHolds;
	@Autowired
	private AllElcos allElcos;
	private CouchDbInstance dbInstance;
	private StdCouchDbConnector stdCouchDbConnector;
	
    @Before
    public void setUp() throws Exception {
    	//allHouseHolds.removeAll();
    	//allElcos.removeAll();
       HttpClient httpClient = new StdHttpClient.Builder() 
        //.host("localhost") 
       	.host("localhost")
        .port(5984) 
        .socketTimeout(1000) 
        .build(); 
		dbInstance = new StdCouchDbInstance(httpClient); 
		
		stdCouchDbConnector = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());
		 
		stdCouchDbConnector.createDatabaseIfNotExists(); 
		allHouseHolds = new AllHouseHolds(2, stdCouchDbConnector);
		allElcos = new AllElcos(2, stdCouchDbConnector);
    	//initMocks(this);
    }
    @Test
    public void hhTest() throws ParseException{
    	
	 
	 
    	Long startTime =DateUtil.getStartTimeStampOfAMonth(0);
    	ViewResult vr = allElcos.ElcoBetweenTwoDatesAsViewResult(startTime);
    	for (ViewResult.Row row : vr.getRows()) {
    		System.err.println("Row:"+Long.parseLong(row.getValue()));
    	}
    	System.err.println("startTime:"+startTime+ " : "+ vr.getRows().size());
    	Calendar c = Calendar.getInstance();
		int dayOfTheMonth = c.get(Calendar.DAY_OF_MONTH);
		System.out.println("dayOfTheMonth:"+dayOfTheMonth);
    }
  
}
