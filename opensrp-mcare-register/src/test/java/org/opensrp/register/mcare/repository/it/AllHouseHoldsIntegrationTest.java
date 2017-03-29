package org.opensrp.register.mcare.repository.it;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.common.util.DateUtil;
import org.opensrp.common.util.WeekBoundariesAndTimestamps;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.springframework.beans.factory.annotation.Autowired;

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
       	.host("localhost")
        .port(5984)
        .username("Admin").password("mPower@1234")
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
    @Ignore@Test
    public void deleteEmptyProviderHouseHoldTest(){
    	List<HouseHold> households = allHouseHolds.getAll();
    	System.err.println(households.size());
    	for (HouseHold household : households) {
    		
    		if(household.user_type().equalsIgnoreCase("FD") || household.PROVIDERID()==null || household.PROVIDERID().equalsIgnoreCase("") ){
        		try{
        			System.out.println(household.getId());
        		allHouseHolds.remove(household);
        		}catch(Exception e){
        			e.printStackTrace();
        		}
        	}
    		
    		
    		
		}
    	
    	/*HouseHold household = allHouseHolds.get("fc2966f0-8e9a-4648-9e09-e07b9dc39aef");
    	if(household.PROVIDERID()==null){
    		try{
    			System.out.println(household.getId());
    		//allHouseHolds.remove(household);
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}*/
    	
    }
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
    		countsForChart[DateUtil.binarySearch(testTimestamps.get(i), startAndEndOfWeeksAsTimestamp)]++;
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
    public void hhTest() throws ParseException{
    	/*ViewResult vr = allHouseHolds.allHHsCreatedLastFourMonthsViewResult();
    	System.out.println("data Total:"+vr.getTotalRows());
    	System.out.println("data:"+vr.getRows().size());*/
	 
	   //System.out.println("Total by All :"+allHouseHolds.getAll().size());
	   System.err.println(DateUtil.getCurrentMonthCurrentweek());
    	Long startTime =DateUtil.getStartTimeStampOfAMonth(3);
    	ViewResult vr = allHouseHolds.HouseholdBetweenTwoDatesAsViewResult(startTime);
    	System.err.println("startTime:"+startTime+ " : "+ vr.getRows().size());
    	Calendar c = Calendar.getInstance();
		int dayOfTheMonth = c.get(Calendar.DAY_OF_MONTH);
		System.out.println("dayOfTheMonth:"+dayOfTheMonth);
		
		this.convertViewResultToWeekWiseCount(vr);
    }
   
   private List<Integer> convertViewResultToWeekWiseCount( ViewResult vr){		
		List<Integer> seperateWeeklyCountDataForRegisterFromViewResult = new ArrayList<>();
		for (int index = 0; index < 23; index++) {
			seperateWeeklyCountDataForRegisterFromViewResult.add(index,0);
		}
		WeekBoundariesAndTimestamps boundaries = DateUtil.getWeekBoundariesForDashboard();
   	List<Long> startAndEndOfWeeksAsTimestamp = boundaries.weekBoundariesAsTimeStamp;
   	System.err.println(startAndEndOfWeeksAsTimestamp.toString());
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
   				System.out.println("oldTimeStamp:"+oldTimeStamp +" : " +oldPosition);
   				Integer existingCount = seperateWeeklyCountDataForRegisterFromViewResult.get(oldPosition);
   				seperateWeeklyCountDataForRegisterFromViewResult.set(oldPosition, existingCount+1);
   			}else{
	    			position = DateUtil.binarySearch(Long.parseLong(stringValue), startAndEndOfWeeksAsTimestamp);
	    			Integer existingCount = seperateWeeklyCountDataForRegisterFromViewResult.get(position);
	    			seperateWeeklyCountDataForRegisterFromViewResult.set(position, existingCount+1);
	    			System.out.println("TimeStamp:"+Long.parseLong(stringValue) +" : " +position);
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
		return seperateWeeklyCountDataForRegisterFromViewResult;
	}
   
   @Ignore@Test
   public void  getWeekBoundariesForDashboard(){   	
   	Calendar now = GregorianCalendar.getInstance();   	
   	
   	List<String> dates = new ArrayList<String>();
   	List<Long> dateTimestamps = new ArrayList<Long>();
   	List<String> tempDates;
   	List<Long> tempDateTimestamps;
   	String tempDateStr;
   	for(int monthIndex = 0; monthIndex < 4; monthIndex++){
   		now = GregorianCalendar.getInstance();
   		now.add(GregorianCalendar.MONTH, -monthIndex);
       	now.set(GregorianCalendar.DAY_OF_MONTH, 1);
       	now.set(Calendar.HOUR_OF_DAY, -6);
       	now.set(Calendar.MINUTE, 0);
       	now.set(Calendar.SECOND, 0);
       	int numOfDaysInMonth = now.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);        	
       	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
       	int numberOfWeeks = (int)Math.ceil( (double)numOfDaysInMonth/7);
       	System.out.println("current date- " + now.getTime() + " in a month with days- " + numOfDaysInMonth + " number of week- " + numberOfWeeks);
       	tempDates = new ArrayList<String>();
       	tempDateTimestamps = new ArrayList<Long>();
       	for(int i = 0; i< numberOfWeeks; i++){
       		int firstDay = i * 7 + 1;
       		int lastDay;
       		if(firstDay + 6 <= numOfDaysInMonth){
       			lastDay = firstDay + 6;
       		}
       		else{
       			lastDay = firstDay + numOfDaysInMonth % 7 -1;
       		}
       		
       		

       		now.set(GregorianCalendar.DAY_OF_MONTH, firstDay);
       		tempDates.add(dateFormatter.format(now.getTime()));
       		now.set(GregorianCalendar.DAY_OF_MONTH, lastDay);
       		tempDates.add(dateFormatter.format(now.getTime()));   
       		try {
       			System.err.println("Date : "+firstDay+" : "+ lastDay);
       			now.set(GregorianCalendar.DAY_OF_MONTH, firstDay+1);
       			
           		tempDateStr = dateFormatter.format(now.getTime());	
           		tempDates.add(tempDateStr);
           		tempDateTimestamps.add(dateFormatter.parse(tempDateStr).getTime());
					now.set(GregorianCalendar.DAY_OF_MONTH, lastDay);
					tempDateStr = dateFormatter.format(now.getTime());	
           		tempDates.add(tempDateStr);
           		tempDateTimestamps.add(dateFormatter.parse(tempDateStr).getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
       	}
       	/*if(numberOfWeeks == 4){
       		tempDates.add("");
       		tempDates.add("");
       		tempDateTimestamps.add(0l);
       		tempDateTimestamps.add(0l);
       	}*/
       	Collections.reverse(tempDates);        	
       	dates.addAll(tempDates);
       	Collections.reverse(tempDateTimestamps);
       	dateTimestamps.addAll(tempDateTimestamps);
   	}
   	Collections.reverse(dates);
   	Collections.reverse(dateTimestamps);
   	System.out.println(dateTimestamps.toString());
   	WeekBoundariesAndTimestamps boundaries = new WeekBoundariesAndTimestamps(dates, dateTimestamps);
   	
   	System.out.println(boundaries.toString());
   }
    @Ignore@Test
    public void MonthTest(){
    	List<Integer> CountForWeaks = new ArrayList<>();
    	for(int monthIndex = 3; monthIndex >=0; monthIndex--){
    		Calendar cal = Calendar.getInstance();    		
    		cal.set(Calendar.HOUR_OF_DAY, 0);
    		cal.set(Calendar.MINUTE, 0);
    		cal.set(Calendar.SECOND, 0);
    		cal.add(Calendar.MONTH, -monthIndex);
    		cal.set(Calendar.DAY_OF_MONTH, 1);
    		int month = cal.get(cal.MONTH);
    		System.out.println(monthIndex+":"+month);
    		int[] countsForChart = new int[23];
    		if(monthIndex ==0 && month==1){
    			countsForChart[5] = 0;
    		}
    		
    	}
    	for (int i = 0; i < 23; i++) {
    		CountForWeaks.add(i,0);
		}
    	System.err.println(CountForWeaks.toString());
    	//Integer existingCount = CountForWeaks.get(position);
    	try{
    	System.err.println( CountForWeaks.get(0));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    @Ignore@Test
    public void DataCount(){
    	WeekBoundariesAndTimestamps boundaries = DateUtil.getWeekBoundariesForDashboard();
    	
    	List<Long> timestamps = new ArrayList<Long>(); 
    	timestamps.add(1480615200000L);
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
    	timestamps.add(1487008800000L);
    	timestamps.add(1488218400000L);
    	try{
    	List<Long> startAndEndOfWeeksAsTimestamp = boundaries.weekBoundariesAsTimeStamp; 
    System.err.println(startAndEndOfWeeksAsTimestamp.toString());
    	long todayTimeStamp = startAndEndOfWeeksAsTimestamp.get(startAndEndOfWeeksAsTimestamp.size()-1) ;
    	System.out.println("pkkkkkkkkk...");
    	System.err.println("TimeStamp:Today"+todayTimeStamp);
    	int todaysCount=0;
    	for(int i = 0; i < timestamps.size(); i++){
    		try{
    			long data = timestamps.get(i);
    			System.out.println(todayTimeStamp+" : "+data);
    			if(todayTimeStamp==data){
    				todaysCount++;
    			} 
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		
    	}
    	System.out.println("CNT:"+todaysCount);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	int[] countsForChart = new int[23];
    	List<Integer> CountForWeaks = new ArrayList<>();
    	for (int index = 0; index < 23; index++) {
    		CountForWeaks.add(index,0);
		}
    	
    	System.out.println("data:"+CountForWeaks.toString());
    }
    
    
    
	 public static boolean ifDateInsideAWeek(long timestamp, long lowerLimit, long upperLimit){
	    	
	    	if(timestamp >= lowerLimit && timestamp <= upperLimit){
	    		return true;
	    	}
	    	return false;
	    }
    
    // weekBoundaries should have a size of even number
    public static int dateInsideWhichWeek(long timestamp, List<Long> weekBoundaries){
    	int max = weekBoundaries.size()/2 - 1, min = 0, mid = (max + min)/2;
    	while(max >= min){
    		
    		if(ifDateInsideAWeek(timestamp, weekBoundaries.get(2*mid), weekBoundaries.get(2*mid + 1))){
        		return mid;
        	}
    		else if(timestamp > weekBoundaries.get(2*mid + 1)){
    			min = mid + 1;
    			//max = mid - 1;
    		}
    		else if(timestamp < weekBoundaries.get(2*mid )){
    			max = mid -1;
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
