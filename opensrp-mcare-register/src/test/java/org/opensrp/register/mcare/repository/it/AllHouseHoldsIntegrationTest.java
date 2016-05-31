package org.opensrp.register.mcare.repository.it;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.opensrp.common.util.EasyMap.create;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.ektorp.CouchDbInstance;
import org.ektorp.ViewResult;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.junit.Before;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register-mcare.xml")
public class AllHouseHoldsIntegrationTest {

	@Autowired
    private AllHouseHolds allHouseHolds;
	/*@Autowired
    private AllElcos allElcos;*/
	//private CouchDbInstance dbInstance;
	//private StdCouchDbConnector stdCouchDbConnector;
	
    @Before
    public void setUp() throws Exception {
    	//allHouseHolds.removeAll();
    	//allElcos.removeAll();
       /* HttpClient httpClient = new StdHttpClient.Builder() 
        .host("localhost") 
        .port(5984) 
        .socketTimeout(1000) 
        .build(); 
		dbInstance = new StdCouchDbInstance(httpClient); 
		
		stdCouchDbConnector = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());
		 
		stdCouchDbConnector.createDatabaseIfNotExists(); 
		allHouseHolds = new AllHouseHolds(stdCouchDbConnector);*/
    	initMocks(this);
    }
    
  /*  @Test
    public void shouldRegisterEligibleCouple() throws Exception {
    	HouseHold houseHold = new HouseHold().withFWHOHNAME("HouseHold-1").withPROVIDERID("Provider-I");

    	allHouseHolds.add(houseHold);

        List<HouseHold> allHouseHoldsInDB = allHouseHolds.getAll();
        assertThat(allHouseHoldsInDB, is(asList(houseHold)));
        assertThat(allHouseHoldsInDB.get(0).FWHOHNAME(), is("HouseHold-1"));
    }*/
    
    //@Test
    public void dateUtil_WeekBoundariesAndTimestamps_test(){
    	WeekBoundariesAndTimestamps boundaries = DateUtil.getWeekBoundariesForDashboard();
    	
    	List<String> startAndEndOfWeeks = boundaries.weekBoundariesAsString;
    	List<Long> startAndEndOfWeeksAsTimestamp = boundaries.weekBoundariesAsTimeStamp;    	
    	        
        for(int i = 0; i < startAndEndOfWeeks.size(); i+=2){
        	Date x = new Date(startAndEndOfWeeksAsTimestamp.get(i));
        	System.out.println(startAndEndOfWeeksAsTimestamp.get(i) + " -- " + startAndEndOfWeeks.get(i) + " -- " + x );
        	x = new Date(startAndEndOfWeeksAsTimestamp.get(i+1));
        	System.out.println(startAndEndOfWeeksAsTimestamp.get(i+1) + " -- " + startAndEndOfWeeks.get(i+1) + " -- " + x );
        	System.out.println("Week index- " + i/2);
        }
        
        List<Long> testTimestamps = new ArrayList<Long>();
        for(int i = 0; i < startAndEndOfWeeksAsTimestamp.size(); i+=2){
        	testTimestamps.add(startAndEndOfWeeksAsTimestamp.get(i));
        	testTimestamps.add(startAndEndOfWeeksAsTimestamp.get(i+1));
        	
        	// for a month with 29 days beginning and end of week has the same timestamp
        	//if(startAndEndOfWeeksAsTimestamp.get(i) != startAndEndOfWeeksAsTimestamp.get(i+1)){

        	if(i/2 != 4){
        		int j;
            	for(j = 0; j < (i/2)%5; j++){
            		testTimestamps.add(startAndEndOfWeeksAsTimestamp.get(i) + (j+1)*100);
            	}
        	}
        	
        	if(startAndEndOfWeeksAsTimestamp.get(i) == startAndEndOfWeeksAsTimestamp.get(i+1)){
        		System.out.println("y no work bro- " + i);  // it doesn't get printed
        	}
        	
        }
        //System.out.println("size of testTimestamps- " + testTimestamps.size());
        int[] countsForChart = new int[20];
        for(int i =0; i<countsForChart.length; i++){
        	System.out.println(countsForChart[i] + " -- " + i);
        }
        
        for(int i = 0; i < testTimestamps.size(); i++){
    		countsForChart[DateUtil.dateInsideWhichWeek(testTimestamps.get(i), startAndEndOfWeeksAsTimestamp)]++;
    	}
        
        for(int i =0; i<countsForChart.length; i++){
        	if(countsForChart[i] != 0){
        		System.out.println("count for weekIndex - " + i + " is " + countsForChart[i]);
        	}        	
        }
        
        //
    }
    
    public String createRawStartKey(String provider, String district, String upazilla, String union){
		String key = "";
		if(provider.equals("")){
			if(union.equals("")){
				if(upazilla.equals("")){
					key = "[\"" + district + "\"]";
				}
				else{
					key = "[\"" + district + "\",\"" + upazilla + "\"]";
				}
			}
			else{
				key = "[\"" + district + "\",\"" + upazilla + "\",\"" + union + "\"]";
			}
		}
		else{
			key = "[\"" + provider + "\",\"" + district + "\",\"" + upazilla + "\",\"" + union + "\"]";
		}
		return key;
	}
    
    @Test 
    public void testRawkey() throws Exception {
    	/*List<HouseHold> fetchedHH = allHouseHolds.allHHsCreatedLastFourMonthsByLocation("[\"Gaibandha\"]", "[\"Gaibandha\",{}]");
    	System.out.println("Number of fetched rows- " + fetchedHH.size());*/
    	
    	/*List<HouseHold> fetchedHHTempNew = allHouseHolds.allHHsCreatedLastFourMonthsByLocationAnother("Gaibandha","GAIBANDHA SADAR", "");
    	System.out.println("Number of fetched rows- " + fetchedHHTempNew.size());
    	
    	List<HouseHold> fetchedHHTemp = allHouseHolds.allHHsCreatedLastFourMonthsByLocation("[\"Gaibandha\",\"GAIBANDHA SADAR\"]", "[\"Gaibandha\",\"GAIBANDHA SADAR\",{}]");
    	System.out.println("Number of fetched rows- " + fetchedHHTemp.size());*/
    	
    	/*WeekBoundariesAndTimestamps boundaries = DateUtil.getWeekBoundariesForDashboard();
    	
    	List<String> startAndEndOfWeeks = boundaries.weekBoundariesAsString;//DateUtil.getWeekBoundariesForDashboard();
    	List<Long> startAndEndOfWeeksAsTimestamp = boundaries.weekBoundariesAsTimeStamp;//DateUtil.getWeekBoundariesForDashboardAsTimestamp();
    	
    	int[] countsForChart = new int[20];
        int count = 0;
        
        //this segment below does the rawkey thing
        ViewResult result = allHouseHolds.allHHsCreatedLastFourMonthsByLocationViewResult("[\"Gaibandha\",\"GAIBANDHA SADAR\"]", "[\"Gaibandha\",\"GAIBANDHA SADAR\",{}]");
    	List<Long> timestamps = new ArrayList<Long>();
    	for (ViewResult.Row row : result.getRows()) {
    		String stringValue = row.getValue();
    		String stringKey = row.getKey();
    		System.out.println(stringValue + " -- " + stringKey);
    		count++;
    		timestamps.add(Long.parseLong(stringValue));
    	}
    	System.out.println("number of rows found - " + count);   	
    	
    	//this segment will do the counting
    	for(int i = 0; i < timestamps.size(); i++){
    		countsForChart[DateUtil.dateInsideWhichWeek(timestamps.get(i), startAndEndOfWeeksAsTimestamp)]++;
    	}        
    	int foundCount = 0;
    	for(int i =0; i<countsForChart.length; i++){
        	if(countsForChart[i] != 0){
        		System.out.println("count for weekIndex - " + i + " is " + countsForChart[i]);
        		foundCount += countsForChart[i]; 
        	}        	
        }
    	System.out.println("foundCount - " + foundCount);*/
    	System.out.println(createRawStartKey("aklima","Gaibandha","GAIBANDHA SADAR","LAXMIPUR"));
    	System.out.println(createRawStartKey("","Gaibandha","GAIBANDHA SADAR","LAXMIPUR"));
    	System.out.println(createRawStartKey("","Gaibandha","GAIBANDHA SADAR",""));
    	System.out.println(createRawStartKey("","Gaibandha","",""));
    	String oka = createRawStartKey("aklima","Gaibandha","GAIBANDHA SADAR","LAXMIPUR");
    	System.out.println(oka.substring(0, oka.length()-1) + ",{}]");
    	System.out.println("startkey=" + oka + "&endkey=" + oka.substring(0, oka.length()-1) + ",{}]");
    }

}
