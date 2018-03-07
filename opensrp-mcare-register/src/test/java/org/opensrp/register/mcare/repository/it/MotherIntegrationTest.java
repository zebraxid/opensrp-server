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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import org.motechproject.scheduletracking.api.domain.EnrollmentStatus;
import org.opensrp.common.util.DateUtil;
import org.opensrp.dto.AlertStatus;
import org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration;
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
		
		.host("192.168.19.79").port(5984).socketTimeout(1000000).username("Admin").password("mPower@1234").build();
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
	
	///http://localhost:5984/_utils/database.html?motech-scheduletracking-api/_design/Enrollment/_view/by_external_id
	//function(doc) { if(doc.type === 'Enrollment' && doc.scheduleName=='Ante Natal Care Reminder Visit') emit(doc.externalId); }
	//http://localhost:5984/_utils/database.html?opensrp/_design/Action/_view/all#
	//function(doc) { if(doc.type === 'Action' && doc.data.scheduleName=='Ante Natal Care Reminder Visit' && doc.data.alertStatus!='expired') {emit(null, doc._id)} }
	@Ignore
	@Test
	public void ancScheduleTest() {
		int i = 0;
		String visitCode = "";
		List<Action> actions = allActions.findActionByScheduleName("Ante Natal Care Reminder Visit");
		System.err.println("" + actions.size());
		String lmp = "";
		
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
			
			if (mother != null && mother.PROVIDERID() != null) {
				lmp = mother.details().get("LMP");
				List<Enrollment> enrollments = allEnrollmentWrapper.findByExternalIdAndScheduleName(action.caseId(),
				    "Ante Natal Care Reminder Visit");
				
				Map<String, String> ancParam = checkANC(LocalDate.parse(lmp), lmp);
				
				boolean ancStatus = isANCSubmited(mother, visitCode);
				if (ancStatus) {// if submitted //780
				
					/*System.err.println("ANC CaseId:" + action.caseId() + "visitCode:" + visitCode + " status:"
					        + action.getIsActionActive());*/
					if (SCHEDULE_ANC_4.equalsIgnoreCase(visitCode)) {
						//should false all scedule; // problem in tab synced schedule
						// unenroll all schedule
						
						acn4++;//196
					} else {
						if (!ancParam.get("milestone").equalsIgnoreCase(visitCode) && isActive(enrollments)) {
							
							notCurrentVisitiCodec++; //168
							
							// refresh schedule
						} else if (!ancParam.get("alert").equalsIgnoreCase(action.data().get("alertStatus"))
						        && ancParam.get("milestone").equalsIgnoreCase(visitCode) && isActive(enrollments)) {
							notCurrentVisitiCodec++; //168
							
						} else {
							currentVisitiCodec++; //416
							
						}
					}
					
				} else { // not submitted
					i++; //1875
					if (action.data().get("alertStatus").equalsIgnoreCase("expired")) {
						
					} else {
						if (!ancParam.get("milestone").equalsIgnoreCase(visitCode) && isActive(enrollments)) {
							
							notsubNotSame++; //168
							
							// refresh schedule
						} else if (!ancParam.get("alert").equalsIgnoreCase(action.data().get("alertStatus"))
						        && ancParam.get("milestone").equalsIgnoreCase(visitCode) && isActive(enrollments)) {
							notsubNotSame++; //168
							
						} else {
							notsubSame++;//1038/ may be nothing to do
							
						}
					}
					
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
		List<Action> actions = allActions.findActionByScheduleName("Post Natal Care Reminder Visit");
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
				List<Enrollment> enrollments = allEnrollmentWrapper.findByExternalIdAndScheduleName(action.caseId(),
				    "Post Natal Care Reminder Visit");
				if (pncStatus) {// if submitted //780
				
					/*System.err.println("ANC CaseId:" + action.caseId() + "visitCode:" + visitCode + " status:"
					        + action.getIsActionActive());*/
					if (SCHEDULE_PNC_3.equalsIgnoreCase(visitCode)) {
						//should false all scedule; // problem in tab synced schedule
						// unenroll all schedule
						acn4++;//14
						
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode) && isActive(enrollments)) {
							
							notCurrentVisitiCodec++; //7
							
							// refresh schedule
						} else {
							currentVisitiCodec++; //0
							
							// // false all schedule
						}
					}
					
				} else { // not submitted
				
					if (action.data().get("alertStatus").equalsIgnoreCase("expired")) {
						
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode) && isActive(enrollments)) {
							notsubNotSame++; //0
							System.err.println("" + action.caseId());
							// must need to refresh
							
						} else {
							notsubSame++;//6/ may be nothing to do
							
						}
					}
					
				}
			} else {
				m++;
			}
			
		}
		System.err.println("Not Mother:" + m + "acn4:" + acn4 + "currentVisitiCodec:" + currentVisitiCodec
		        + "   notCurrentVisitiCodec:" + notCurrentVisitiCodec + "  expired:" + expired + "unenroll: " + unenroll
		        + "  enroll:" + enroll + " notsubSame  :" + notsubSame + " notsubNotSame;" + notsubNotSame);
	}
	
	@Ignore
	@Test
	public void enncScheduleTest() {
		String pattern = "yyyy-MM-dd";
		int i = 0;
		String visitCode = "";
		List<Action> actions = allActions.findActionByScheduleName("Essential Newborn Care Checklist");
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
				List<Enrollment> enrollments = allEnrollmentWrapper.findByExternalIdAndScheduleName(action.caseId(),
				    "Essential Newborn Care Checklist");
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
						if (!currentVisitiCode.equalsIgnoreCase(visitCode) && isActive(enrollments)) {
							notCurrentVisitiCodec++; //5
							
							// refresh schedule // schedule interupted due to server off 21 december
						} else {
							currentVisitiCodec++; //0 no data found
							
							//// false all schedule
						}
					}
					
				} else { // not submitted
				
					if (action.data().get("alertStatus").equalsIgnoreCase("expired")) {
						
						// refresh schedule
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode) && isActive(enrollments)) {
							notsubNotSame++; //29
							System.err.println("" + action.caseId());
							// must need to refresh
							
						} else {
							
							notsubSame++;//6/ may be nothing to do
							
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
	
	private Map<String, String> checkANC(LocalDate referenceDateForSchedule, String startDate) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		String milestone = null;
		DateTime ancStartDate = null;
		DateTime ancExpireDate = null;
		AlertStatus alertStaus = null;
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(startDate);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		
		DateTime start = new DateTime(date);
		
		long datediff = ScheduleLogService.getDaysDifference(start);
		
		if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(23).toPeriod())) {
			//161
			milestone = SCHEDULE_ANC_1;
			if (DateUtil
			        .isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(8).toPeriod().minusDays(6))) {
				alertStaus = AlertStatus.normal;
				ancStartDate = new DateTime(start);
				ancExpireDate = new DateTime(start).plusDays(DateTimeDuration.ANC1NORMALEND);
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(8).toPeriod()
			        .minusDays(1))) {
				alertStaus = AlertStatus.upcoming;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC1UPCOMINGSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC1UPCOMINGEND);
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(23).toPeriod()
			        .minusDays(1))) {
				//160
				alertStaus = AlertStatus.urgent;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC1URGENTSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC1URGENTEND);
				System.err.println("from anc1 urgent");
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(23).toPeriod())) {
				//162
				alertStaus = AlertStatus.expired;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC1EXPIREDSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC1EXPIREDEND);
				
			} else {
				alertStaus = AlertStatus.expired;
			}
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(31).toPeriod())) {
			//217
			milestone = SCHEDULE_ANC_2;
			if (datediff == -162) {
				System.err.println("from 2 to 1");
				alertStaus = AlertStatus.expired;
				//ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC1EXPIREDEND);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC1EXPIREDEND);
				ancStartDate = ancExpireDate;
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(24).toPeriod()
			        .minusDays(1))) {//167
				alertStaus = AlertStatus.upcoming;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC2UPCOMINGSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC2UPCOMINGEND);
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(31).toPeriod()
			        .minusDays(1))) {//216
				alertStaus = AlertStatus.urgent;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC2URGENTSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC2URGENTEND);
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(31).toPeriod())) {
				//217
				alertStaus = AlertStatus.expired;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC2EXPIREDSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC2EXPIREDEND);
				
			} else {
				alertStaus = AlertStatus.expired;
			}
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod())) {
			//245
			milestone = SCHEDULE_ANC_3;
			if (datediff == -218) {
				alertStaus = AlertStatus.expired;
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC2EXPIREDEND);
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(32).toPeriod()
			        .minusDays(1))) {
				//223
				alertStaus = AlertStatus.upcoming;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC3UPCOMINGSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC3UPCOMINGEND);
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod()
			        .minusDays(1))) {
				//244
				alertStaus = AlertStatus.urgent;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC3URGENTSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC3URGENTEND);
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod())) {
				//245
				alertStaus = AlertStatus.expired;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC3EXPIREDSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC3EXPIREDEND);
				
			} else {
				alertStaus = AlertStatus.expired;
			}
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod()
		        .minusDays(1))) {
			// 307
			System.err.println("ojjj");
			milestone = SCHEDULE_ANC_4;
			if (datediff == -246) {
				alertStaus = AlertStatus.expired;
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC3EXPIREDEND);
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(36).toPeriod()
			        .minusDays(1))) {
				//251
				alertStaus = AlertStatus.upcoming;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC4UPCOMINGSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC4UPCOMINGEND);
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod()
			        .minusDays(2))) {
				//306
				alertStaus = AlertStatus.urgent;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC4URGENTSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC4URGENTEND);
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod()
			        .minusDays(1))) {
				//307
				alertStaus = AlertStatus.expired;
				ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC4EXPIREDSTART);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC4EXPIREDEND);
				
			} else {
				
			}
			
		} else {
			milestone = SCHEDULE_ANC_4;
			alertStaus = AlertStatus.expired;
			
		}
		
		map.put("alert", alertStaus.name());
		map.put("milestone", milestone);
		return map;
		
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
	
	private boolean isActive(List<Enrollment> enrollments) {
		boolean completed = false;
		System.err.println("Size:" + enrollments.size());
		for (Enrollment enrollment : enrollments) {
			System.err.println("enrollment:" + enrollment.getStatus());
			if (EnrollmentStatus.COMPLETED.name().equalsIgnoreCase(enrollment.getStatus().name())
			        || EnrollmentStatus.UNENROLLED.name().equalsIgnoreCase(enrollment.getStatus().name())) {
				completed = true;
				
			} else if (EnrollmentStatus.ACTIVE.name().equalsIgnoreCase(enrollment.getStatus().name())
			        || EnrollmentStatus.DEFAULTED.name().equalsIgnoreCase(enrollment.getStatus().name())) {
				
			}
			
		}
		
		if (completed) {
			System.err.println("completed;" + completed);
			return false;
		} else {
			return true;
		}
		
	}
	
	@Ignore
	@Test
	public void expiredScheduleCorrection() {
		List<Action> actions = allActions.getAll(); //all view should change as only get expired schedule
		for (Action action : actions) {
			action.markAsInActive();
			action.timestamp(System.currentTimeMillis());
			action.setRevision(action.getRevision());
			allActions.update(action);
		}
	}
	
	//function(doc) { if(doc.type === 'Action' && doc.data.alertStatus=='expired' && doc.data.scheduleName=='Ante Natal Care Reminder Visit') {emit([doc.data.scheduleName], null)} }
	@Test
	public void updateANCForExpiredSchedule() {
		List<Action> actions = allActions.findActionByScheduleName("Ante Natal Care Reminder Visit");
		System.err.println("actions:" + actions.size());
		int anc1 = 0;
		int anc2 = 0;
		int anc3 = 0;
		int anc4 = 0;
		int e = 0;
		for (Action action : actions) {
			
			if (action.data().get("visitCode").equalsIgnoreCase("ancrv_1")) {
				anc1++;
				
				action.data().put("alertStatus", "urgent");
				action.setRevision(action.getRevision());
				
				allActions.update(action);
			} else if (action.data().get("visitCode").equalsIgnoreCase("ancrv_2")) {
				anc2++;
				action.data().put("alertStatus", "urgent");
				action.setRevision(action.getRevision());
				allActions.update(action);
			} else if (action.data().get("visitCode").equalsIgnoreCase("ancrv_3")) {
				anc3++;
				action.data().put("alertStatus", "urgent");
				action.setRevision(action.getRevision());
				allActions.update(action);
			} else if (action.data().get("visitCode").equalsIgnoreCase("ancrv_4")) {
				anc4++;
				
			} else {
				e++;
			}
			
		}
		System.err.println("anc1:" + anc1 + " anc2:" + anc2 + " anc3:" + anc3 + " anc4:" + anc4 + " e:" + e);
	}
}
