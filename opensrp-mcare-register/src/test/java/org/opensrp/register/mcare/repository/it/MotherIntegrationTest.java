package org.opensrp.register.mcare.repository.it;

import static org.opensrp.common.AllConstants.ELCORegistrationFields.FWPSRPREGSTS;

import java.text.ParseException;
import java.util.ArrayList;
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
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.springframework.beans.factory.annotation.Autowired;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register-mcare.xml")*/
public class MotherIntegrationTest {

	@Autowired
    private AllHouseHolds allHouseHolds;
	@Autowired
	private AllElcos allElcos;
	private AllMothers allMothers;
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
        .username("Admin").password("mPower@1234")
        .build(); 
		dbInstance = new StdCouchDbInstance(httpClient); 
		
		stdCouchDbConnector = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());
		 
		stdCouchDbConnector.createDatabaseIfNotExists(); 
		allHouseHolds = new AllHouseHolds(2, stdCouchDbConnector);
		allElcos = new AllElcos(2, stdCouchDbConnector);
		allMothers = new AllMothers(2, stdCouchDbConnector);
    	//initMocks(this);
    }
    @Ignore@Test
    public void motherAddressUpdateTest() throws ParseException{
    	
    	List<Mother> mothers = allMothers.getAll();
    	String FWWOMDISTRICT = "";
    	String FWWOMUPAZILLA ="";
    	for (Mother mother : mothers) { 
        	try{
        		Elco elco = allElcos.findByCaseId(mother.getRelationalid());
        		if(elco !=null){
        			if(elco.FWWOMDISTRICT()!=null){
        				FWWOMDISTRICT = elco.FWWOMDISTRICT();
        			}else{
        				FWWOMDISTRICT = "";
        			}
        			if(elco.FWWOMUPAZILLA()!=null ){
        				FWWOMUPAZILLA =elco.FWWOMUPAZILLA();
        			}else{
        				FWWOMUPAZILLA = "";
        			}
        			
        		}/*else{
        			//allMothers.remove(mother);
        		}*/
        		
        		if(mother.getFWWOMUNION() ==null){
    				mother.setFWWOMUNION("");
    			}
    			
    			mother.withFWWOMDISTRICT(FWWOMDISTRICT);
            	mother.withFWWOMUPAZILLA(FWWOMUPAZILLA);
        		System.out.println("mother:"+mother);
        		allMothers.update(mother);
        		
        	}catch(Exception e){
        		e.printStackTrace();
        	}
		}
    	
    	
    }
    
    
  
}
