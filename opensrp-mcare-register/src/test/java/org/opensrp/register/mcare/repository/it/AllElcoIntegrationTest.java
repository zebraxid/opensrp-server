package org.opensrp.register.mcare.repository.it;

import static org.opensrp.common.AllConstants.ELCORegistrationFields.FWPSRPREGSTS;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.opensrp.common.util.DateTimeUtil;
import org.opensrp.common.util.DateUtil;
import org.opensrp.common.util.WeekBoundariesAndTimestamps;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Ignore@Test
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
    
    @Ignore@Test
    public void PSRFStatusAddedTest(){
    	List<Elco> elcos = allElcos.getAll();
    	System.out.println(elcos.size());
    	for (Elco elco : elcos) {
    		if(elco.details().containsKey(FWPSRPREGSTS)){
        		if(elco.details().get("FWPSRPREGSTS") ==null){
    			
    				elco.withFWPSRPREGSTS("");
	    		}else{
	    			elco.withFWPSRPREGSTS(elco.details().get("FWPSRPREGSTS"));
	    		}
        	}else{
        		elco.withFWPSRPREGSTS("");
        	}
        	try{
        	allElcos.update(elco);
        	System.err.println("Updated with id :"+elco.getId());
        	}catch(Exception e){
        		e.printStackTrace();
        	}
		}
    	
    	/*Elco elco = allElcos.get("ffff869e-cda4-488e-b3ce-2ac478edfdd9");
    	if(elco.details().containsKey(FWPSRPREGSTS)){
    		if(elco.details().get("FWPSRPREGSTS") ==null){
    			System.out.println("okkkk");
    			elco.withFWPSRPREGSTS("");
    		}else{
    			elco.withFWPSRPREGSTS(elco.details().get("FWPSRPREGSTS"));
    		}
    		
    	}else{
    		elco.withFWPSRPREGSTS("");
    	}
    	try{
    	allElcos.update(elco);
    	System.err.println("Updated with id :"+elco.getId());
    	}catch(Exception e){
    		e.printStackTrace();
    	}*/
    }
    
  @Ignore @Test
    public void updateElco(){
 	   List<Elco> elcos = allElcos.getAll();
 	   for (Elco elco : elcos) {
 		  List<Map<String, String>> psrfs = elco.PSRFDETAILS();
 		 
 		
 		 for (int j = 0; j < psrfs.size(); j++) { 			  
 			psrfs.get(j).put("timeStamp", ""+System.currentTimeMillis()); 			 
 			 
 			psrfs.get(j).put("clientVersion", DateTimeUtil.getTimestampOfADate(elco.TODAY()).toString());
 			 
		  }
 		
 		  elco.setTimeStamp(System.currentTimeMillis());
 		allElcos.update(elco);
 	   }
 	   
    }
  
}
