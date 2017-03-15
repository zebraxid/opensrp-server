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
public class AllHouseHoldsIntegrationTest {

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
       	.host("192.168.22.55")
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
		if(provider.equalsIgnoreCase("")){
			if(union.equalsIgnoreCase("")){
				if(upazilla.equalsIgnoreCase("")){
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
			if(union.equalsIgnoreCase("")){
				if(upazilla.equalsIgnoreCase("")){
					if(district.equalsIgnoreCase("")){
						key = "[\"" + provider + "\"]";
					}
					else{
						key = "[\"" + provider + "\",\"" + district + "\"]";
					}
					
				}
				else{
					key = "[\"" + provider + "\",\"" + district + "\",\"" + upazilla + "\"]";
				}
			}
			else{
				key = "[\"" + provider + "\",\"" + district + "\",\"" + upazilla + "\",\"" + union + "\"]";
			}
		
		}
		
		return key;
	}
    
    @Ignore@Test 
    public void testRawkey() throws Exception {
    	String oka = createRawStartKey("aklima","","","");//,"Gaibandha","GAIBANDHA SADAR","LAXMIPUR");
    	System.out.println(oka.substring(0, oka.length()-1) + ",{}]");
    	System.out.println("startkey=" + oka + "&endkey=" + oka.substring(0, oka.length()-1) + ",{}]");
    	
    	System.out.println(DateUtil.getTimestampToday() + " -- " + new Date(DateUtil.getTimestampToday()).toString());
    	
    	ViewResult hhViewResult;		
		//hhViewResult = allHouseHolds.allHHsCreatedLastFourMonthsByLocationViewResult("[\"Gaibandha\"]", "[\"Gaibandha\",{}]");
    	hhViewResult = allHouseHolds.allHHsCreatedLastFourMonthsByProviderAndLocationViewResult(oka, oka.substring(0, oka.length()-1) + ",{}]");
		System.out.println("number of hh with keys " + oka + oka.substring(0, oka.length()-1) + ",{}]" + " " + hhViewResult.getRows().size());
		
		for (ViewResult.Row row : hhViewResult.getRows()) {
			if(DateUtil.ifDateInsideAWeek(Long.parseLong(row.getValue()), DateUtil.getTimestampToday(), DateUtil.getTimestampToday())){
				System.out.println(row.getId());				
			}
		}
		
		ViewResult elcoViewResult;		
		String key = createRawStartKey("", "Gaibandha", "", "");
		System.out.println(key + " -the startKey");
		elcoViewResult = allElcos.allMothersCreatedLastFourMonthsByLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");			
		System.out.println(elcoViewResult.getRows().size() + " count of mothers from gaibandha" );
		//return this.coverViewResultToCount(elcoViewResult);
    }
    @Ignore@Test
    public void hhTest(){
    	ViewResult vr = allHouseHolds.allHHsCreatedLastFourMonthsViewResult();
    	
    	System.out.println("data:"+vr.getRows().size());
    	
    }
    @Test
    public void DataCount(){
    	WeekBoundariesAndTimestamps boundaries = DateUtil.getWeekBoundariesForDashboard();
    	
    	List<Long> timestamps = new ArrayList<Long>(); 
    	/*timestamps.add(1480615200000L);
    	timestamps.add(1480701600000L);
    	timestamps.add(1480788000000L);
    	timestamps.add(1481997600000L);
    	timestamps.add(1482084000000L);
    	timestamps.add(1482256800000L);
    	timestamps.add(1482343200000L);
    	timestamps.add(1482429600000L);
    	timestamps.add(1482516000000L);
    	timestamps.add(1482602400000L);
    	timestamps.add(1482688800000L);
    	timestamps.add(1482775200000L);
    	timestamps.add(1482861600000L);  
    	timestamps.add(1482948000000L);
    	timestamps.add(1483034400000L);
    	timestamps.add(1483120800000L);
    	timestamps.add(1483207200000L);
    	timestamps.add(1483293600000L);
    	timestamps.add(1483898400000L);
    	timestamps.add(1484071200000L);
    	timestamps.add(1484157600000L);
    	timestamps.add(1484244000000L);
    	timestamps.add(1484589600000L);
    	timestamps.add(1486144800000L);
    	timestamps.add(1486231200000L);
    	timestamps.add(1486317600000L);
    	timestamps.add(1486404000000L);
    	timestamps.add(1487008800000L);*/
    	timestamps.add(1487786400000L);
    	List<Long> startAndEndOfWeeksAsTimestamp = boundaries.weekBoundariesAsTimeStamp; 
    	int[] countsForChart = new int[23];
    	for(int i = 0; i < timestamps.size(); i++){
    		
    		countsForChart[dateInsideWhichWeek(timestamps.get(i), startAndEndOfWeeksAsTimestamp)]++;
    	}
    	System.out.println("data:"+countsForChart.toString());
    }
    
    
    
	 public static boolean ifDateInsideAWeek(long timestamp, long lowerLimit, long upperLimit){
	    	
	    	if(timestamp >= lowerLimit && timestamp <= upperLimit){
	    		return true;
	    	}
	    	return false;
	    }
    
    // weekBoundaries should have a size of even number
    public static int dateInsideWhichWeek(long timestamp, List<Long> weekBoundaries){
    	int max = weekBoundaries.size() - 1, min = 0, mid = (max + min)/2;
    	
    	while(max >= min){
    		
    		if(ifDateInsideAWeek(timestamp, weekBoundaries.get(mid), weekBoundaries.get(mid + 1))){
    			System.out.println(" find index for - " + timestamp);
    			return mid;
        	}
    		else if(timestamp > weekBoundaries.get(mid + 1)){
    			min = mid + 1;
    			//max = mid - 1;
    		}
    		else if(timestamp < weekBoundaries.get(mid )){
    			max = mid;
    			//min = mid + 1;
    		}
    		mid = (max + min) / 2;
    	}
    	
    	/*for(int i = 0; i < weekBoundaries.size(); i+=2){
    		if(timestamp >= weekBoundaries.get(i) && timestamp <= weekBoundaries.get(i+1)){
    			return 1;
    		}
    	}*/
    	//System.out.println("could not find index for - " + timestamp);
    	return -1;
    }

}
