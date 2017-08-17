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
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllChilds;
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
	private AllChilds allChilds;
	
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
		allChilds = new AllChilds(2, stdCouchDbConnector);
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
    
    @Ignore@Test
    public void updateChild(){
    	List<Child> childs = allChilds.getAll();
    	for (Child child : childs) {
			child.setTimeStamp(System.currentTimeMillis());
			allChilds.update(child);
		}
    	
    }
    
  @Ignore@Test
    public void updateMother(){
 	   List<Mother> mothers = allMothers.getAll();
 	   for (Mother mother : mothers) {
 		  List<Map<String, String>> psrfs =mother.bnfVisitDetails();
 		// mother.bnfVisitDetails().clear();
 		  for (int i = 0; i < psrfs.size(); i++) {
			psrfs.get(i).put("timeStamp", ""+System.currentTimeMillis());
		}
 		
 		  mother.setTimeStamp(System.currentTimeMillis());
 		 allMothers.update(mother);
 	   }
 	   
    }
 
 
  
  
 @Ignore @Test
  public void womanUpdate(){
		List<Mother> mothers = allMothers.getAll();
		int i =0;
		for (Mother mother : mothers) {
			try{
			
			if(mother.TODAY()!=null){
				mother.withClientVersion(DateTimeUtil.getTimestampOfADate(mother.TODAY()));
			}else{
				mother.withClientVersion(0);
			}
			if(!mother.ancVisitOne().isEmpty()){
				mother.ancVisitOne().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.ancVisitOne().get("today")).toString());
				mother.ancVisitOne().put("timeStamp", ""+System.currentTimeMillis());
				
				
			}
			
			if(!mother.ancVisitTwo().isEmpty()){
				mother.ancVisitTwo().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.ancVisitTwo().get("today")).toString());
				mother.ancVisitTwo().put("timeStamp", ""+System.currentTimeMillis());
				
				
			}
			
			if(!mother.ancVisitThree().isEmpty()){
				mother.ancVisitThree().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.ancVisitThree().get("today")).toString());
				mother.ancVisitThree().put("timeStamp", ""+System.currentTimeMillis());
				
				
			}

			if(!mother.ancVisitFour().isEmpty()){
				mother.ancVisitFour().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.ancVisitFour().get("today")).toString());
				mother.ancVisitFour().put("timeStamp", ""+System.currentTimeMillis());
				
				
			}
			
			if(!mother.pncVisitOne().isEmpty()){
				mother.pncVisitOne().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.pncVisitOne().get("today")).toString());
				mother.pncVisitOne().put("timeStamp", ""+System.currentTimeMillis());
				
				
			}
			
			if(!mother.pncVisitTwo().isEmpty()){
				mother.pncVisitTwo().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.pncVisitTwo().get("today")).toString());
				mother.pncVisitTwo().put("timeStamp", ""+System.currentTimeMillis());
				
				
			}
			if(!mother.pncVisitThree().isEmpty()){
				mother.pncVisitThree().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.pncVisitThree().get("today")).toString());
				mother.pncVisitTwo().put("timeStamp", ""+System.currentTimeMillis());
				
				
			}
			 List<Map<String, String>> bnfs =mother.bnfVisitDetails();
			 for (int j = 0; j < bnfs.size(); j++) {
				 bnfs.get(j).put("timeStamp", ""+System.currentTimeMillis());
				 bnfs.get(j).put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.TODAY()).toString());
				}
			mother.setTimeStamp(System.currentTimeMillis());
			//mother.withClientVersion(DateTimeUtil.getTimestampOfADate(mother.TODAY()));
			allMothers.update(mother);
			System.err.println("I:"+i+1);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
  
  @Ignore @Test
  public void childUpdate(){
		List<Child> clilds = allChilds.getAll();
		int i =0;
		for (Child child : clilds) {
			try{
			Mother mother = allMothers.findByCaseId(child.details().get("relationalid"));
			if(child.TODAY()!=null){
			child.withClientVersion(DateTimeUtil.getTimestampOfADate(child.TODAY()));
			}else{
				child.withClientVersion(0);
			}
			try{
				child.withDistrict(mother.FWWOMDISTRICT());
			}catch(Exception e){
				child.withDistrict("");
			}
			try{
				child.withUpazilla(mother.FWWOMUPAZILLA());
			}catch(Exception e){
				child.withUpazilla("");
			}
			
			try{
				child.withUnion(mother.getFWWOMUNION());
			}catch(Exception e){
				child.withUnion("");
			}
			try{
				child.withUnit(mother.getFWWOMSUBUNIT());
			}catch(Exception e){
				child.withUnit("");
			}
			
			try{
				child.withMouzaPara(mother.getMother_mauza());
			}catch(Exception e){
				child.withMouzaPara("");
			}
			
			allChilds.update(child);
			System.err.println("I:"+i+1);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
 @Ignore @Test
  public void childENCCUpdate(){
		List<Child> childs = allChilds.getAll();
		int i =0;
		for (Child child : childs) {
			try{
			
			if(child.TODAY()!=null){
				child.withClientVersion(DateTimeUtil.getTimestampOfADate(child.TODAY()));
			}else{
				child.withClientVersion(0);
			}
			if(!child.enccVisitOne().isEmpty()){
				child.enccVisitOne().put("clientVersion", DateTimeUtil.getTimestampOfADate(child.enccVisitOne().get("today")).toString());
				child.enccVisitOne().put("timeStamp", ""+System.currentTimeMillis());
				
				
			}
			
			if(!child.enccVisitTwo().isEmpty()){
				child.enccVisitTwo().put("clientVersion", DateTimeUtil.getTimestampOfADate(child.enccVisitTwo().get("today")).toString());
				child.enccVisitTwo().put("timeStamp", ""+System.currentTimeMillis());
				
				
			}
			
			if(!child.enccVisitThree().isEmpty()){
				child.enccVisitThree().put("clientVersion", DateTimeUtil.getTimestampOfADate(child.enccVisitThree().get("today")).toString());
				child.enccVisitThree().put("timeStamp", ""+System.currentTimeMillis());
				
				
			}

			
			
			child.setTimeStamp(System.currentTimeMillis());
			
			allChilds.update(child);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
  
}
