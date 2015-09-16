package org.opensrp.repository.it;

import static org.junit.Assert.*;
import static org.opensrp.dto.AlertStatus.normal;
import static org.opensrp.dto.BeneficiaryType.mother;
import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.is;

import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import org.opensrp.dto.ActionData;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.ReportAction;
import org.opensrp.scheduler.repository.AllReportActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.opensrp.dto.AlertStatus.normal;
import static org.opensrp.dto.BeneficiaryType.household;

public class AllReportActionsIntegrationTest {

	@Mock
	private AllReportActions allReportActions;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void shouldFindAllSchedulesForVisits()  throws Exception{

		 ReportAction firstReportAction = new ReportAction("CASEID-1","INSTANCEID-1", "ANM-1", alert());
		 allReportActions.addAlert(firstReportAction);
		 
		 ReportAction secondReportAction = new ReportAction("CASEID-1","INSTANCEID-1", "ANM-1", alert());
		 allReportActions.addAlert(secondReportAction);
	
		 ReportAction thirdReportAction = new ReportAction("CASEID-1","INSTANCEID-2", "ANM-1", alert());
		 allReportActions.addAlert(thirdReportAction);
		 
		 Map<String, ReportAction> schedulesMap = new HashMap<String,ReportAction>();
		 schedulesMap.put("INSTANCEID-1", firstReportAction);
		 schedulesMap.put("INSTANCEID-2", thirdReportAction);
	 
		 
		 assertThat(allReportActions.findAllSchedulesForVisits(), is(schedulesMap));
		 
		 verify(allReportActions).addAlert(firstReportAction);
	}

	private ActionData alert() {
		return ActionData.createAlert(household, "FWA CENSUS", "FWA CENSUS", normal, DateTime.now(), DateTime.now().plusDays(3));
	}

}
