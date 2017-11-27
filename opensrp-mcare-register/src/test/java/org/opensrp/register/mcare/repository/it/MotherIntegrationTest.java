package org.opensrp.register.mcare.repository.it;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.motechproject.scheduletracking.api.domain.EnrollmentStatus;
import org.opensrp.common.util.DateTimeUtil;
import org.opensrp.scheduler.Action;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.scheduler.service.AllEnrollmentWrapper;
import org.springframework.beans.factory.annotation.Autowired;


public class MotherIntegrationTest {

	@Autowired
    private AllHouseHolds allHouseHolds;
	@Autowired
	private AllElcos allElcos;
	private AllMothers allMothers;
	private CouchDbInstance dbInstance;
	private StdCouchDbConnector stdCouchDbConnector;
	private StdCouchDbConnector stdCouchDbConnectorMothech;
	private AllChilds allChilds;
	
	private AllActions allActions;
	private AllEnrollmentWrapper allEnrollmentWrapper;
	
    @Before
    public void setUp() throws Exception {
    	
       HttpClient httpClient = new StdHttpClient.Builder() 
        
       	.host("192.168.19.97")
        .port(5984) 
        .socketTimeout(1000000) 
        .username("Admin").password("mPower@1234")
        .build(); 
		dbInstance = new StdCouchDbInstance(httpClient); 
		
		stdCouchDbConnector = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());
		stdCouchDbConnectorMothech = new StdCouchDbConnector("motech-scheduletracking-api", dbInstance, new StdObjectMapperFactory());
		//stdCouchDbConnector.createDatabaseIfNotExists(); 
		allHouseHolds = new AllHouseHolds(2, stdCouchDbConnector);
		allElcos = new AllElcos(2, stdCouchDbConnector);
		allMothers = new AllMothers(2, stdCouchDbConnector);
		allChilds = new AllChilds(2, stdCouchDbConnector);
		allActions = new AllActions(stdCouchDbConnector);
		allEnrollmentWrapper = new AllEnrollmentWrapper(stdCouchDbConnectorMothech);
    	
    }
    
  
    // for data cleaning
  @Ignore @Test
    public void shouldUpdateLocation(){
    	List<Mother> mothers = allMothers.getAll();
    	int i=0;
    	int cnt = 0;
    	String FWWOMDISTRICT = "";
    	String FWWOMUPAZILLA ="";
    	for (Mother mother : mothers) {
    		//allMothers.remove(mother);
    		/*i++;
    			
    			try{
            		Elco elco = allElcos.findByCaseId(mother.getRelationalid());
            		
            		
            		if(elco !=null){
            			
            			mother.details().put("birthDate", elco.FWBIRTHDATE());
               		 List<Map<String, String>> psrfs =elco.PSRFDETAILS();
               		 int psrfsCount = psrfs.size()-1;
               		 Map<String, String> psrf = psrfs.get(psrfsCount);
               		 mother.details().put("LMP", psrf.get("FWPSRLMP"));
               		 mother.details().put("division", elco.FWWOMDIVISION());
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
            			
            			List<Map<String, String>> bnfs =mother.bnfVisitDetails();
            	 		
              		  for (int j = 0; j < bnfs.size(); j++) {
              			  System.err.println(""+i);
              			  bnfs.get(j).put("timeStamp", ""+System.currentTimeMillis());
              			  bnfs.get(j).put("clientVersion",""+System.currentTimeMillis());
              		  }
              		
          			mother.withFWWOMDISTRICT(FWWOMDISTRICT);
                  	mother.withFWWOMUPAZILLA(FWWOMUPAZILLA);
            			
            		}
            		
            		
        			
            		 
                	mother.setTimeStamp(System.currentTimeMillis());
            		allMothers.update(mother);
            		System.err.println("CNT:::"+i);
            	}catch(Exception e){
            		
            		e.printStackTrace();
            		System.out.println("mother:"+e.getMessage());
            		System.out.println("caseId:"+mother.caseId());
            	}
    		}*/
			
    	}
    	System.out.println("CNT:"+i+"FormCNT:"+cnt);
    	
    }
    
    
   
    
   /*@Test
    public void updateMother(){
	   
 	   List<Mother> mothers = allMothers.getAll();
 	  System.err.println("kk"+mothers.size());
 	   for (Mother mother : mothers) {
 		  List<Map<String, String>> psrfs =mother.bnfVisitDetails();
 		try{
 		for (int i = 0; i < psrfs.size(); i++) {
			psrfs.get(i).put("timeStamp", ""+System.currentTimeMillis());
			psrfs.get(i).put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.TODAY()).toString());
		}
 		 
 		
 		
 		
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
 		 allMothers.update(mother);
 		 System.err.println("okkkk");
 		}catch(Exception e){
 			System.err.println(""+e.getMessage());
 			System.err.println(""+mother.caseId());
 		}
 	   }
 	   
    }*/
 
 
  
  
/*
  @Ignore
@Test
 public void deleteUpdateChild(){
 	List<Child> childs = allChilds.getAll();
 	for (Child child : childs) {
 		
 		
 			child.setTimeStamp(System.currentTimeMillis());
			allChilds.update(child);
 			
	}
 	
 }*/

 /* @Ignore@Test
  public void childUpdate(){
		List<Child> clilds = allChilds.getAll();
		int i =0;
		for (Child child : clilds) {
			i++;
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
			try{
			child.details().put("ward", mother.getFWWOMWARD());
			}catch(Exception e){
				child.details().put("ward", "");
			}
			
			
			try{
				child.details().put("division", mother.details().get("division"));
				}catch(Exception e){
					child.details().put("division", "");
				}
			
			
			allChilds.update(child);
			System.err.println("I:"+i);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}*/

  /*@Test
  public void childENCCUpdate(){
		List<Child> childs = allChilds.getAll();
		int i =0;
		for (Child child : childs) {
			try{
			
			
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
			allChilds.update(child);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
  */
  
  //Essential Newborn Care Checklist
  //Post Natal Care Reminder Visit
 @Test
    public void actionFalse() throws ParseException{
    	
    	DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    	List<Action> actions = allActions.getAll();
    	System.err.println("-------------------------------");
    	int i = 0;
    	for (Action action : actions) {
    		String status = "";
    		List<Enrollment> enrollments = allEnrollmentWrapper.getByEid(action.caseId());
    		
    		if(enrollments!=null){
    		for (Enrollment enrollment : enrollments) {
				if("Post Natal Care Reminder Visit".equalsIgnoreCase(enrollment.getScheduleName())){
					//enrollment.setStatus(EnrollmentStatus.ACTIVE);
					//allEnrollmentWrapper.update(enrollment);
					status = enrollment.getStatus().name();
				}
			}
    		}
    		i++;
    		String date =action.data().get("expiryDate");
    		long  checkingDate = 1510751863000l;
    		long timestamp = yyyyMMdd.parse(date).getTime();
    		//if(timestamp<checkingDate && "pncrv_3".equalsIgnoreCase(action.data().get("visitCode"))){
    		if("DEFAULTED".equalsIgnoreCase(status)){
    		System.err.println(""+status+"visitCode:  "+action.caseId() +"     "+action.data().get("visitCode")+"    alertStatus:"+action.data().get("alertStatus")+"                                   Data:  "+action.data().get("expiryDate"));
    		
    		action.data().put("alertStatus", "expired");
    		action.timestamp(System.currentTimeMillis()+2000);
    		//allActions.update(action);
    		//System.err.println("action:"+action);
    		}
    		
    		
		}
    	
    	System.err.println("CNT:"+i);
    }
   
}
