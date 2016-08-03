package org.opensrp.repository.it;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.opensrp.dto.AlertStatus.normal;
import static org.opensrp.dto.BeneficiaryType.child;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.dto.ActionData;
import org.opensrp.scheduler.repository.AllReportActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp.xml")
public class AllReportActionsIntegrationTest {
	
	@Autowired
	private AllReportActions allReportActions;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}
	
	@Test
	public void shouldFindAllSchedulesForVisits() throws Exception {
		
		/*ScheduleLog firstReportAction = new ScheduleLog("CASEID-1","INSTANCEID-1", "ANM-1", alert());
		allReportActions.addAlert(firstReportAction);
		
		ScheduleLog secondReportAction = new ScheduleLog("CASEID-1","INSTANCEID-1", "ANM-1", alert());
		allReportActions.addAlert(secondReportAction);
		
		ScheduleLog thirdReportAction = new ScheduleLog("CASEID-1","INSTANCEID-2", "ANM-1", alert());
		allReportActions.addAlert(thirdReportAction);
		
		Map<String, ScheduleLog> schedulesMap = new HashMap<String,ScheduleLog>();
		schedulesMap.put("INSTANCEID-1", firstReportAction);
		schedulesMap.put("INSTANCEID-2", thirdReportAction);*/
		
		// assertThat(allReportActions.findAllSchedulesForVisits(), is(schedulesMap));
		
		//verify(allReportActions).addAlert(firstReportAction);
	}
	
	@Test
	public void shouldFindByInstanceIdByCaseIdByname() {
		assertNotNull(allReportActions.findByInstanceIdByCaseIdByname("f0c5a992-ba9e-4204-a6e2-60aeef60f564",
		    "10c4f8a2-7b5a-44a2-9ca3-bbe77f0e31ac", "ELCO PSRF"));
		
	}
	
	@Test
	public void shouldNotFindByInstanceIdByCaseIdByname() {
		assertNull(allReportActions.findByInstanceIdByCaseIdByname("f0c5a992-ba9f-4204-a6e2-60aeef60f564",
		    "10c4f8a2-7b5a-44a2-9ca3-bbe77f0e31ac", "ELCO PSRF"));
	}
	
	private ActionData alert() {
		return ActionData.createAlert(child, "FWA CENSUS", "FWA CENSUS", normal, DateTime.now(), DateTime.now().plusDays(3));
	}
	
}
