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
import org.opensrp.common.util.DateTimeUtil;
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
    
  
    
    @Ignore@Test
    public void updateHouseHold(){
 	   List<HouseHold> houseHolds = allHouseHolds.getAll();
 	   int i=0;
 	   for (HouseHold houseHold : houseHolds) {
 		houseHold.setTimeStamp(System.currentTimeMillis());
 		allHouseHolds.update(houseHold);
 		i++;
 		System.err.println("I::"+i);
 	   }
 	   
    }
   // remove hh which has no provider and update setTimeStamp
   // Data cleaning
  @Test
   public void shouldRemoveAndUpdateTimeStampHHWithNoProvider(){
   	int i=0; // _count need to remove from view
   	List<HouseHold> households = allHouseHolds.getAll();
   	for (HouseHold houehold : households) {
			if(houehold.PROVIDERID()==null){
				i++;
				allHouseHolds.remove(houehold);
				
			}else{
				try{
				houehold.setTimeStamp(DateTimeUtil.getTimestampOfADate(houehold.START()));
				i++;
		 		allHouseHolds.update(houehold);
		 		System.err.println("I::"+i);
				}catch(Exception e){
					System.err.println("MSG:"+e.getMessage());
					System.err.println("houehold:"+houehold.caseId());
				}
			}
		}
   	System.err.println("CNT:"+i);
   }
   
    
}
