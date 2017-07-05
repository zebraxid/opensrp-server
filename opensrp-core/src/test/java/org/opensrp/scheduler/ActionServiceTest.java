package org.opensrp.scheduler;

import com.google.gson.Gson;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.opensrp.dto.ActionData;
import org.opensrp.dto.MonthSummaryDatum;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.scheduler.repository.AllAlerts;
import org.opensrp.scheduler.service.ActionService;
import org.opensrp.service.BaseEntityService;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.opensrp.dto.AlertStatus.normal;
import static org.powermock.api.mockito.PowerMockito.when;


public class ActionServiceTest {
    @Mock
    private AllActions allActions;
    
    @Mock
    private AllAlerts allAlerts;
    
    @Mock
    private BaseEntityService baseEntityService;

    private ActionService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        allActions.removeAll();
        allAlerts.removeAll();
        service = new ActionService(allActions, allAlerts);
    }

    @Test
    public void shouldSaveAlertActionForEntity() throws Exception {
        DateTime dueDate = DateTime.now().minusDays(1);
        DateTime expiryDate = dueDate.plusWeeks(2);

        service.alertForBeneficiary("mother", "Case X", "ANM ID M", "Ante Natal Care - Normal", "ANC 1", normal, dueDate, expiryDate);
        Action action = new Action("Case X", "ANM ID M", ActionData.createAlert("mother", "Ante Natal Care - Normal", "ANC 1", normal, dueDate, expiryDate));


        verify(allActions).addOrUpdateAlert(action);
        verify(allAlerts).addOrUpdateScheduleNotificationAlert("mother", "Case X", "ANM ID M", "Ante Natal Care - Normal", "ANC 1", normal, dueDate, expiryDate);
    }

    @Test
    @Ignore//TODO: alertForBeneficiary(action) should also call allAlerts.
    public void shouldSaveAlertActionForEntityWithActionObject() throws Exception {
        DateTime dueDate = DateTime.now().minusDays(1);
        DateTime expiryDate = dueDate.plusWeeks(2);

        Action action = new Action("Case X", "ANM ID M", ActionData.createAlert("mother", "Ante Natal Care - Normal", "ANC 1", normal, dueDate, expiryDate));

        service.alertForBeneficiary(action);

        verify(allActions).addOrUpdateAlert(action);
       // verify(allAlerts).addOrUpdateScheduleNotificationAlert("mother", "Case X", "ANM ID M", "Ante Natal Care - Normal", "ANC 1", normal, dueDate, expiryDate);
    }


    @Test
    public void shouldCreateACloseActionForAVisitOfAChild() throws Exception {
        service.markAlertAsClosed("Case X", "ANM 1", "OPV 1", "2012-01-01");

        verify(allActions).add(new Action("Case X", "ANM 1", ActionData.markAlertAsClosed("OPV 1", "2012-01-01")));
    }


    @Test
    public void shouldCreateADeleteAllActionForAMother() throws Exception {
        service.markAllAlertsAsInactive("Case X");

        verify(allActions).markAllAsInActiveFor("Case X");
    }

    @Test
    public void shouldMarkAnAlertAsInactive() throws Exception {
        service.markAlertAsInactive("ANM 1","Case X", "Schedule 1");

        verify(allActions).markAlertAsInactiveFor("ANM 1","Case X", "Schedule 1");
    }


    @Test
    public void shouldReportForIndicator() {
        ActionData summaryActionData = ActionData.reportForIndicator("ANC", "30", new Gson().toJson(asList(new MonthSummaryDatum("3", "2012", "2", "2", asList("CASE 5", "CASE 6")))));

        service.reportForIndicator("ANM X", summaryActionData);

        verify(allActions).add(new Action("", "ANM X", summaryActionData));
    }

    @Test
    public void shouldDeleteAllActionsWithTargetReport() {
        service.deleteReportActions();

        verify(allActions).deleteAllByTarget("report");
    }

    @Test
    public void shouldCreateACloseActionForAVisitOfAMother() throws Exception {
        service.markAlertAsClosed("Case X", "ANM ID 1", "ANC 1", "2012-12-12");

        Action action = new Action("Case X", "ANM ID 1", ActionData.markAlertAsClosed("ANC 1", "2012-12-12"));
        verify(allActions).add(action);
        verify(allAlerts).markAlertAsCompleteFor("ANM ID 1", "Case X", "ANC 1", "2012-12-12");
    }


    @Test
    public void shouldReturnAlertsBasedOnANMIDAndTimeStamp() throws Exception {
        List<Action> alertActions = Arrays.asList(new Action("Case X", "ANM 1", ActionData.createAlert("mother", "Ante Natal Care - Normal", "ANC 1", normal, DateTime.now(), DateTime.now().plusDays(3))));
        when(allActions.findByProviderIdAndTimeStamp("ANM 1", 1010101)).thenReturn(alertActions);

        List<Action> alerts = service.getNewAlertsForANM("ANM 1", 1010101);

        assertEquals(1, alerts.size());
        assertEquals(alertActions, alerts);
    }

    @Test
    public void shouldFindAllByCriteria() {
        List<Action> alerts = service.findByCriteria("team", "ANM 1", 1010101, "sortBy", "sortOrder", 1);
        verify(allActions).findByCriteria("team", "ANM 1", 1010101, "sortBy", "sortOrder", 1);

    }
}
