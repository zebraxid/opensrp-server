package org.opensrp.register.mcare.repository.it;

import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ChildScheduleConstants.SCHEDULE_ENCC_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ChildScheduleConstants.SCHEDULE_ENCC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ChildScheduleConstants.SCHEDULE_ENCC_3;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_3;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_4;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_3;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Weeks;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.opensrp.common.AllConstants.ScheduleNames;
import org.opensrp.common.util.DateUtil;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
import org.opensrp.scheduler.Action;
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
		
		.host("localhost").port(5984).socketTimeout(1000000).username("Admin").password("mPower@1234").build();
		dbInstance = new StdCouchDbInstance(httpClient);
		
		stdCouchDbConnector = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());
		stdCouchDbConnectorMothech = new StdCouchDbConnector("motech-scheduletracking-api", dbInstance,
		        new StdObjectMapperFactory());
		//stdCouchDbConnector.createDatabaseIfNotExists(); 
		allHouseHolds = new AllHouseHolds(2, stdCouchDbConnector);
		allElcos = new AllElcos(2, stdCouchDbConnector);
		allMothers = new AllMothers(2, stdCouchDbConnector);
		allChilds = new AllChilds(2, stdCouchDbConnector);
		allActions = new AllActions(stdCouchDbConnector);
		allEnrollmentWrapper = new AllEnrollmentWrapper(stdCouchDbConnectorMothech);
		
	}
	
	// for data cleaning
	@Ignore
	@Test
	public void shouldUpdateLocation() {
		List<Mother> mothers = allMothers.getAll();
		int i = 0;
		int cnt = 0;
		String FWWOMDISTRICT = "";
		String FWWOMUPAZILLA = "";
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
		System.out.println("CNT:" + i + "FormCNT:" + cnt);
		
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
	
	@Ignore
	@Test
	public void actionFalse() throws ParseException {
		
		DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
		List<Action> actions = allActions.getAll();
		System.err.println("-------------------------------");
		int i = 0;
		for (Action action : actions) {
			String status = "";
			//List<Enrollment> enrollments = allEnrollmentWrapper.getByEid(action.caseId());
			
			/*if (enrollments != null) {
				for (Enrollment enrollment : enrollments) {
					if ("Post Natal Care Reminder Visit".equalsIgnoreCase(enrollment.getScheduleName())) {
						//enrollment.setStatus(EnrollmentStatus.ACTIVE);
						//allEnrollmentWrapper.update(enrollment);
						status = enrollment.getStatus().name();
					}
				}
			}*/
			i++;
			/*String date = action.data().get("expiryDate");
			long checkingDate = 1510751863000l;
			long timestamp = yyyyMMdd.parse(date).getTime();
			//if(timestamp<checkingDate && "pncrv_3".equalsIgnoreCase(action.data().get("visitCode"))){
			if ("DEFAULTED".equalsIgnoreCase(status)) {
				System.err.println("" + status + "visitCode:  " + action.caseId() + "     " + action.data().get("visitCode")
				        + "    alertStatus:" + action.data().get("alertStatus")
				        + "                                   Data:  " + action.data().get("expiryDate"));
				
				action.data().put("alertStatus", "expired");
				action.timestamp(System.currentTimeMillis() + 2000);
				//allActions.update(action);
				//System.err.println("action:"+action);
			}*/
			
			System.err.println(action.caseId() + ",");
			
		}
		
		System.err.println("CNT:" + i);
	}
	
	@Ignore
	@Test
	public void anc() {
		System.err.println("" + allEnrollmentWrapper);
		List<Enrollment> enrollments = allEnrollmentWrapper.all();
		System.err.println("" + enrollments.size());
		int i = 0;
		int j = 0;
		for (Enrollment enrollment : enrollments) {
			if ("DEFAULTED".equalsIgnoreCase(enrollment.getStatus().name())) {
				List<Action> actions = allActions.findByCaseID(enrollment.getExternalId());
				if (actions.size() != 0) {
					if ("ancrv_1".equalsIgnoreCase(actions.get(0).data().get("visitCode"))) {
						j++;
						//System.err.println("" + actions.get(0).data().get("visitCode"));
					}
				}
				i++;
				System.err.println(enrollment.getFulfillments().toString() + "  " + enrollment.getStatus() + "  ");
			}
		}
		System.err.println("CNT:" + i + "   " + j);
	}
	
	@Ignore
	@Test
	public void ancScheduleTest() {
		int i = 0;
		String visitCode = "";
		List<Action> actions = allActions.getAll();
		System.err.println("" + actions.size());
		String lmp = "";
		String currentVisitiCode = "";
		int m = 0;
		int acn4 = 0;
		int unenroll = 0;
		int currentVisitiCodec = 0;
		int notCurrentVisitiCodec = 0;
		int expired = 0;
		int enroll = 0;
		int notsubSame = 0;
		int notsubNotSame = 0;
		boolean isEnrolled = false;
		for (Action action : actions) {
			visitCode = action.data().get("visitCode");
			Mother mother = allMothers.findByCaseId(action.caseId());
			//System.err.println("Mother;" + mother);
			
			if (mother != null) {
				lmp = mother.details().get("LMP");
				currentVisitiCode = checkANC(LocalDate.parse(lmp), lmp);
				boolean ancStatus = isANCSubmited(mother, visitCode);
				if (ancStatus) {// if submitted //780
				
					/*System.err.println("ANC CaseId:" + action.caseId() + "visitCode:" + visitCode + " status:"
					        + action.getIsActionActive());*/
					if (SCHEDULE_ANC_4.equalsIgnoreCase(visitCode)) {
						//should false all scedule; // problem in tab synced schedule
						// unenroll all schedule
						acn4++;//196
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode)) {
							
							notCurrentVisitiCodec++; //168
							/*System.err.println(currentVisitiCode + " | " + visitCode + "  |  " + action.getIsActionActive()
							        + " | " + action.caseId());*/
							// refresh schedule
						} else {
							currentVisitiCodec++; //416
							System.err.println(currentVisitiCode + " | " + visitCode + "  |  " + action.getIsActionActive()
							        + " | " + action.caseId() + " | " + action.getIsActionActive());
							
							// false all schedule
						}
					}
					
				} else { // not submitted
					i++; //1875
					if (action.data().get("alertStatus").equalsIgnoreCase("expired")) {
						List<Enrollment> enrollments = allEnrollmentWrapper.getByEid(action.caseId());
						
						for (Enrollment enrollment : enrollments) {
							if (!"ACTIVE".equalsIgnoreCase(enrollment.getStatus().name())
							        && ScheduleNames.ANC.equalsIgnoreCase(enrollment.getScheduleName())) {
								//System.err.println("Sataus:" + enrollment.getStatus());
								isEnrolled = true;
							}
						}
						if (isEnrolled) {
							unenroll++;//311
							// nothing to do OR refresh all unenroled/completed
						} else {
							enroll++;
						}
						expired++;//466
						/*System.err.println(currentVisitiCode + " | " + visitCode + "  |  " + action.getIsActionActive()
						        + " | " + action.caseId());*/
						// refresh schedule
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode)) {
							notsubNotSame++; //203
							// must need to refresh
							
						} else {
							notsubSame++;//1038/ may be nothing to do
							
							/*System.err.println(currentVisitiCode + " | " + visitCode + "  |  " + action.getIsActionActive()
							        + " | " + action.caseId());*/
							
						}
					}
					
					/*System.err.println("ANC CaseId:" + action.caseId() + "visitCode:" + visitCode + " status:"
					        + action.getIsActionActive());*/
				}
			} else {
				m++;
			}
			
		}
		System.err.println("CNT:" + i + "Not Mother:" + m + "acn4:" + acn4 + "currentVisitiCodec:" + currentVisitiCodec
		        + "   notCurrentVisitiCodec:" + notCurrentVisitiCodec + "  expired:" + expired + "unenroll: " + unenroll
		        + "  enroll:" + enroll + " notsubSame  :" + notsubSame + " notsubNotSame;" + notsubNotSame);
	}
	
	@Ignore
	@Test
	public void pncScheduleTest() {
		String pattern = "yyyy-MM-dd";
		int i = 0;
		String visitCode = "";
		List<Action> actions = allActions.getAll();
		System.err.println("" + actions.size());
		String doo = "";
		String currentVisitiCode = "";
		int m = 0;
		int acn4 = 0;
		int unenroll = 0;
		int currentVisitiCodec = 0;
		int notCurrentVisitiCodec = 0;
		int expired = 0;
		int enroll = 0;
		int notsubSame = 0;
		int notsubNotSame = 0;
		boolean isEnrolled = false;
		for (Action action : actions) {
			visitCode = action.data().get("visitCode");
			Mother mother = allMothers.findByCaseId(action.caseId());
			//System.err.println("Mother;" + mother);
			
			if (mother != null) {
				
				List<Map<String, String>> bnfVisitDetails = mother.bnfVisitDetails();
				doo = getBnfDate(bnfVisitDetails);
				DateTime dateTime = DateTime.parse(doo);
				DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
				String referenceDate = fmt.print(dateTime);
				currentVisitiCode = checkPNC(LocalDate.parse(referenceDate), referenceDate);
				boolean pncStatus = isPNCSubmited(mother, visitCode);
				if (pncStatus) {// if submitted //780
				
					/*System.err.println("ANC CaseId:" + action.caseId() + "visitCode:" + visitCode + " status:"
					        + action.getIsActionActive());*/
					if (SCHEDULE_PNC_3.equalsIgnoreCase(visitCode)) {
						//should false all scedule; // problem in tab synced schedule
						// unenroll all schedule
						acn4++;//14
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode)) {
							
							notCurrentVisitiCodec++; //7
							/*System.err.println(currentVisitiCode + " | " + visitCode + "  |  " + action.getIsActionActive()
							        + " | " + action.caseId());*/
							// refresh schedule
						} else {
							currentVisitiCodec++; //0
							System.err.println(currentVisitiCode + " | " + visitCode + "  |  " + action.getIsActionActive()
							        + " | " + action.caseId() + " | " + action.getIsActionActive());
							
							// // false all schedule
						}
					}
					
				} else { // not submitted
				
					if (action.data().get("alertStatus").equalsIgnoreCase("expired")) {
						List<Enrollment> enrollments = allEnrollmentWrapper.getByEid(action.caseId());
						
						for (Enrollment enrollment : enrollments) {
							if (!"ACTIVE".equalsIgnoreCase(enrollment.getStatus().name())
							        && ScheduleNames.PNC.equalsIgnoreCase(enrollment.getScheduleName())) {
								//System.err.println("Sataus:" + enrollment.getStatus());
								isEnrolled = true;
							}
						}
						if (isEnrolled) {
							unenroll++;//69
							// nothing to do OR refresh all unenroled/completed
						} else {
							enroll++;
						}
						expired++;//466
						/*System.err.println(currentVisitiCode + " | " + visitCode + "  |  " + action.getIsActionActive()
						        + " | " + action.caseId());*/
						// refresh schedule
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode)) {
							notsubNotSame++; //0
							// must need to refresh
							
						} else {
							notsubSame++;//6/ may be nothing to do
							
							/*System.err.println(currentVisitiCode + " | " + visitCode + "  |  " + action.getIsActionActive()
							        + " | " + action.caseId());*/
							
						}
					}
					
					/*System.err.println("ANC CaseId:" + action.caseId() + "visitCode:" + visitCode + " status:"
					        + action.getIsActionActive());*/
				}
			} else {
				m++;
			}
			
		}
		System.err.println("Not Mother:" + m + "acn4:" + acn4 + "currentVisitiCodec:" + currentVisitiCodec
		        + "   notCurrentVisitiCodec:" + notCurrentVisitiCodec + "  expired:" + expired + "unenroll: " + unenroll
		        + "  enroll:" + enroll + " notsubSame  :" + notsubSame + " notsubNotSame;" + notsubNotSame);
	}
	
	@Test
	public void enncScheduleTest() {
		String pattern = "yyyy-MM-dd";
		int i = 0;
		String visitCode = "";
		List<Action> actions = allActions.getAll();
		System.err.println("" + actions.size());
		String doo = "";
		String currentVisitiCode = "";
		int m = 0;
		int acn4 = 0;
		int unenroll = 0;
		int currentVisitiCodec = 0;
		int notCurrentVisitiCodec = 0;
		int expired = 0;
		int enroll = 0;
		int notsubSame = 0;
		int notsubNotSame = 0;
		boolean isEnrolled = false;
		for (Action action : actions) {
			visitCode = action.data().get("visitCode");
			Child child = allChilds.findByCaseId(action.caseId());
			//System.err.println("Mother;" + mother);
			
			if (child != null) {
				doo = child.details().get("FWBNFDOB");
				DateTime dateTime = DateTime.parse(doo);
				DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
				String referenceDate = fmt.print(dateTime);
				currentVisitiCode = checkENCC(LocalDate.parse(referenceDate), referenceDate);
				boolean enccStatus = isENNCSubmited(child, visitCode);
				if (enccStatus) {// if submitted //780
				
					/*System.err.println("ANC CaseId:" + action.caseId() + "visitCode:" + visitCode + " status:"
					        + action.getIsActionActive());*/
					if (SCHEDULE_ENCC_3.equalsIgnoreCase(visitCode)) {
						//should false all scedule; // problem in tab synced schedule
						// unenroll all schedule
						acn4++;//4
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode)) {
							
							notCurrentVisitiCodec++; //5
							/*System.err.println(currentVisitiCode + " | " + visitCode + "  |  " + action.getIsActionActive()
							        + " | " + action.caseId());*/
							// refresh schedule
						} else {
							currentVisitiCodec++; //0
							/*System.err.println(currentVisitiCode + " | " + visitCode + "  |  " + action.getIsActionActive()
							        + " | " + action.caseId() + "  " + action.getIsActionActive());
							*/
							//// false all schedule
						}
					}
					
				} else { // not submitted
				
					if (action.data().get("alertStatus").equalsIgnoreCase("expired")) {
						List<Enrollment> enrollments = allEnrollmentWrapper.getByEid(action.caseId());
						
						for (Enrollment enrollment : enrollments) {
							if (!"ACTIVE".equalsIgnoreCase(enrollment.getStatus().name())
							        && ScheduleNames.CHILD.equalsIgnoreCase(enrollment.getScheduleName())) {
								
								isEnrolled = true;
							} else {
								System.err.println("Sataus:" + enrollment.getStatus() + " | " + action.getIsActionActive()
								        + " |" + action.caseId());
							}
						}
						if (isEnrolled) {
							unenroll++;//0
							// nothing to do OR refresh all unenroled/completed
						} else {
							enroll++;//0
							
						}
						expired++;//466
						/*System.err.println(currentVisitiCode + " | " + visitCode + "  |  " + action.getIsActionActive()
						        + " | " + action.caseId());*/
						// refresh schedule
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode)) {
							notsubNotSame++; //29
							// must need to refresh
							
						} else {
							notsubSame++;//6/ may be nothing to do
							
							/*System.err.println(currentVisitiCode + " | " + visitCode + "  |  " + action.getIsActionActive()
							        + " | " + action.caseId());*/
							
						}
					}
					
					/*System.err.println("ANC CaseId:" + action.caseId() + "visitCode:" + visitCode + " status:"
					        + action.getIsActionActive());*/
				}
			} else {
				m++;//1
			}
			
		}
		System.err.println("Not Mother:" + m + "acn4:" + acn4 + "currentVisitiCodec:" + currentVisitiCodec
		        + "   notCurrentVisitiCodec:" + notCurrentVisitiCodec + "  expired:" + expired + "unenroll: " + unenroll
		        + "  enroll:" + enroll + " notsubSame  :" + notsubSame + " notsubNotSame;" + notsubNotSame);
	}
	
	private String checkENCC(LocalDate referenceDateForSchedule, String referenceDate) {
		
		String milestone = null;
		
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(referenceDate);
			
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateTime FWBNFDTOO = new DateTime(date);
		long datediff = ScheduleLogService.getDaysDifference(FWBNFDTOO);
		//System.err.println("datediff:" + datediff);
		if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ONE.toPeriod())) {
			milestone = SCHEDULE_ENCC_1;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.FIVE.toPeriod())) {
			
			milestone = SCHEDULE_ENCC_2;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.SEVEN.plus(2).toPeriod())) {
			
			milestone = SCHEDULE_ENCC_3;
			
		} else {
			milestone = "expired";
			
		}
		return milestone;
		
	}
	
	private boolean isENNCSubmited(Child child, String visitCode) {
		Map<String, String> pnc = null;
		
		if (SCHEDULE_ENCC_1.equalsIgnoreCase(visitCode)) {
			pnc = child.enccVisitOne();
		} else if (SCHEDULE_ENCC_2.equalsIgnoreCase(visitCode)) {
			pnc = child.enccVisitTwo();
		} else if (SCHEDULE_ENCC_3.equalsIgnoreCase(visitCode)) {
			pnc = child.enccVisitThree();
		}
		if (!pnc.isEmpty()) {
			return true;//submitted
		}
		return false;
		
	}
	
	private String checkANC(LocalDate referenceDateForSchedule, String startDate) {
		String milestone = null;
		
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(startDate);
		}
		catch (ParseException e) {
			System.err.println(e.getMessage());
		}
		//System.err.println("startDate:" + startDate);
		DateTime start = new DateTime(date);
		
		long datediff = ScheduleLogService.getDaysDifference(start);
		//System.err.println("start:" + start + " datediff:" + datediff);
		if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(23).toPeriod())) {
			//161
			milestone = SCHEDULE_ANC_1;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(31).toPeriod())) {
			//217
			milestone = SCHEDULE_ANC_2;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod())) {
			//245
			milestone = SCHEDULE_ANC_3;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod()
		        .minusDays(1))) {
			// 307
			milestone = SCHEDULE_ANC_4;
			
		} else {
			milestone = "expired";
			
		}
		return milestone;
		
	}
	
	private boolean isANCSubmited(Mother mother, String visitCode) {
		Map<String, String> anc = null;
		
		if (SCHEDULE_ANC_1.equalsIgnoreCase(visitCode)) {
			anc = mother.ancVisitOne();
		} else if (SCHEDULE_ANC_2.equalsIgnoreCase(visitCode)) {
			anc = mother.ancVisitTwo();
		} else if (SCHEDULE_ANC_3.equalsIgnoreCase(visitCode)) {
			anc = mother.ancVisitThree();
		} else if (SCHEDULE_ANC_4.equalsIgnoreCase(visitCode)) {
			anc = mother.ancVisitFour();
		}
		if (!anc.isEmpty()) {
			return true;//submitted
		}
		return false;
		
	}
	
	private boolean isPNCSubmited(Mother mother, String visitCode) {
		Map<String, String> pnc = null;
		
		if (SCHEDULE_PNC_1.equalsIgnoreCase(visitCode)) {
			pnc = mother.pncVisitOne();
		} else if (SCHEDULE_PNC_2.equalsIgnoreCase(visitCode)) {
			pnc = mother.pncVisitTwo();
		} else if (SCHEDULE_PNC_3.equalsIgnoreCase(visitCode)) {
			pnc = mother.pncVisitThree();
		}
		if (!pnc.isEmpty()) {
			return true;//submitted
		}
		return false;
		
	}
	
	private String checkPNC(LocalDate referenceDateForSchedule, String referenceDate) {
		String milestone = null;
		
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(referenceDate);
			
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateTime FWBNFDTOO = new DateTime(date);
		long datediff = ScheduleLogService.getDaysDifference(FWBNFDTOO);
		
		if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ONE.toPeriod())) {
			milestone = SCHEDULE_PNC_1;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.FIVE.toPeriod())) {
			
			milestone = SCHEDULE_PNC_2;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.SEVEN.plus(2).toPeriod())) {
			
			milestone = SCHEDULE_PNC_3;
			
		} else {
			milestone = "expired";
			
		}
		return milestone;
		
	}
	
	private String getBnfDate(List<Map<String, String>> bnfVisitDetails) {
		String user_type = "";
		String date = "";
		for (Map<String, String> map : bnfVisitDetails) {
			user_type = map.get("user_type");
			date = map.get("FWBNFDTOO");
			if ((!"".equalsIgnoreCase(date) || !date.isEmpty()) && "FD".equalsIgnoreCase(user_type)) {
				//System.err.println("Date:" + date);
				return date;
			}
		}
		
		return null;
		
	}
}
