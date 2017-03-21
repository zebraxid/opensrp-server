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
    	
    	System.err.println(DateUtil.getCurrentMonthCurrentweek());
	 
    	/*Long startTime =DateUtil.getStartTimeStampOfAMonth(3);
    	ViewResult vr = allElcos.pregnantElcoBetweenTwoDatesAsViewResult(startTime);
    	System.err.println(allElcos.totalElco());*/
    	//System.out.println("Start"+allElcos.pregnantElcoBetweenTwoDatesAsViewResult(startTime).size());
    	//this.convertViewResultToWeekWiseCount(vr);
    	/*for (ViewResult.Row row : vr.getRows()) {
    		System.err.println("Row:"+Long.parseLong(row.getValue()));
    	}*/
    	//this.convertViewResultToWeekWiseCount(vr);
    	/*System.err.println("startTime:"+startTime+ " : "+ vr.getRows().size());
    	Calendar c = Calendar.getInstance();
		int dayOfTheMonth = c.get(Calendar.DAY_OF_MONTH);
		System.out.println("dayOfTheMonth:"+dayOfTheMonth);*/
    }
    
    private List<Integer> convertViewResultToWeekWiseCount( ViewResult vr){		
		List<Integer> seperateWeeklyCountDataForRegisterFromViewResult = new ArrayList<>();
		for (int index = 0; index < 23; index++) {
			seperateWeeklyCountDataForRegisterFromViewResult.add(index,0);
		}
		WeekBoundariesAndTimestamps boundaries = DateUtil.getWeekBoundariesForDashboard();
    	List<Long> startAndEndOfWeeksAsTimestamp = boundaries.weekBoundariesAsTimeStamp; 
    	System.err.println("startAndEndOfWeeksAsTimestamp:"+startAndEndOfWeeksAsTimestamp);
    	int todaysCount=0;
    	long todayTimeStamp = DateUtil.getTimestampToday() ; 
    	long oldTimeStamp=0;
    	int oldPosition=0;
    	int position = 0;
    	for (ViewResult.Row row : vr.getRows()) {
    		String stringValue = row.getValue(); 
    		long value = Long.parseLong(stringValue);			
			if(todayTimeStamp==value){
				todaysCount++;
			}			
    		try{ 
    			if(Long.parseLong(stringValue) == oldTimeStamp){
    				Integer existingCount = seperateWeeklyCountDataForRegisterFromViewResult.get(oldPosition);
    				seperateWeeklyCountDataForRegisterFromViewResult.set(oldPosition, existingCount+1);
    			}else{
	    			position = DateUtil.binarySearch(Long.parseLong(stringValue), startAndEndOfWeeksAsTimestamp);
	    			Integer existingCount = seperateWeeklyCountDataForRegisterFromViewResult.get(position);
	    			seperateWeeklyCountDataForRegisterFromViewResult.set(position, existingCount+1);
    			}    			
    			oldTimeStamp = Long.parseLong(stringValue);
    			oldPosition = position;
    			   			
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}    	
    	
    	for(int monthIndex = 3; monthIndex >= 0; monthIndex--){
			int month = DateUtil.getMontNumber(monthIndex);    		
    		if(monthIndex ==3 && month==1 ){
    			seperateWeeklyCountDataForRegisterFromViewResult.add(4, 0);    			
    		}else if(monthIndex ==2 && month==1){
    			seperateWeeklyCountDataForRegisterFromViewResult.add(9, 0);    			
    		}else if(monthIndex ==1 && month==1){
    			seperateWeeklyCountDataForRegisterFromViewResult.add(14, 0);    			
    		}else if(monthIndex ==0 && month==1){
    			seperateWeeklyCountDataForRegisterFromViewResult.add(19, 0);    			
    		}else{
    			
    		}
    	}
    	seperateWeeklyCountDataForRegisterFromViewResult.set(20, todaysCount);
    	System.out.println(seperateWeeklyCountDataForRegisterFromViewResult.toString());
		return seperateWeeklyCountDataForRegisterFromViewResult;
	}
  
}
