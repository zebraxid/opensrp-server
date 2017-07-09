package org.opensrp.register.service.handler;

import static org.mockito.Matchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.domain.Obs;
import org.opensrp.repository.AllEvents;
import org.opensrp.scheduler.HealthSchedulerService;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register.xml")*/
@RunWith(PowerMockRunner.class)
//@PrepareForTest(VaccinesScheduleHandler.class)
public class VaccinesScheduleHandlerTest extends TestResourceLoader {	
    @Mock
	private HealthSchedulerService scheduler;	
    @Mock
    private AllEvents allEvents;
    private VaccinesScheduleHandler  vaccinesScheduleHandler;
    private static final String JSON_KEY_HANDLER = "handler";	
    private static final String JSON_KEY_TYPES = "types";	
    private static final String JSON_KEY_SCHEDULE_NAME = "name";	
    private static final String JSON_KEY_EVENTS = "events";	
	
    @Before
    public void setUp() throws Exception {
        initMocks(this);        
        vaccinesScheduleHandler = new VaccinesScheduleHandler(scheduler, allEvents);
        
    }

    @Test
    public void shouldVaccinesScheduleHandlerForVIT() throws Exception{
        String baseEntityId = "ooo-yyy-yyy";
        String eventType="Vaccination";
       // String eventType = "Birth Registration";
        DateTime eventDate = new DateTime();
        String entityType = "";
        String providerId ="anm";
        String locationId ="";
        String formSubmissionId ="";
        Event event = new Event(baseEntityId, eventType, eventDate, entityType, providerId, locationId, formSubmissionId);
        event.setId("23456");
        String scheduleName = "VIT A 1";
        String schedulesStr = getFile();
        List<Object> values = new ArrayList<>();
        values.add("2016-07-10");
        Obs observation1  = new Obs("concept", "date", "1410AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", values, "", "");
        List<Obs> obs = new ArrayList<>();
        obs.add(observation1);		
        Client client = new Client("ooo-yyy-yyy", "hmmm", "hummm", "lssssss", new DateTime("1995-12-28T00:00:00.000Z"), new DateTime(), true, true, "Female", "", "");
        List<Object> values1 = new ArrayList<>();
        values1.add("2017-06-08 09:33:39");		
        Obs observation2  = new Obs("concept", "birthdate", "163137AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", values1, "", "birthdate");
        event.addObs(observation1);
        event.addObs(observation2);
        JSONArray schedulesJsonObject = new JSONArray("[" + schedulesStr + "]");        
        for (int i = 0; i < schedulesJsonObject.length(); i++) {
            JSONObject scheduleJsonObject = schedulesJsonObject.getJSONObject(i);
            String handler = scheduleJsonObject.has(JSON_KEY_HANDLER)?scheduleJsonObject.getString(JSON_KEY_HANDLER):"VaccinesScheduleHandler";
            JSONArray eventsJsonArray = scheduleJsonObject.getJSONArray(JSON_KEY_EVENTS);
            scheduleName = scheduleJsonObject.getString(JSON_KEY_SCHEDULE_NAME);
            System.err.println("scheduleName:"+scheduleName);
            for (int j = 0; j < eventsJsonArray.length(); j++) {
                JSONObject scheduleConfigEvent = eventsJsonArray.getJSONObject(j);
                JSONArray eventTypesJsonArray = scheduleConfigEvent.getJSONArray(JSON_KEY_TYPES);
                List<String> eventsList = jsonArrayToList(eventTypesJsonArray);
                System.err.println("eventsList:"+eventsList);
                if (eventsList.contains(event.getEventType())) {					
                	vaccinesScheduleHandler.handle(event,scheduleConfigEvent, "VIT A 1");
                    //InOrder inOrder = inOrder(anteNatalCareSchedulesService);
                    /*inOrder.verify(anteNatalCareSchedulesService).enrollMother(event.getBaseEntityId(),scheduleName, LocalDate.parse("2017-02-03"),
					   event.getId());*/					
                }				
            }			
        }		
    }
    @Test
    public void shouldVaccinesScheduleHandlerForMEASLES() throws Exception{
        String baseEntityId = "ooo-yyy-yyy";
        String eventType="Vaccination";
       // String eventType = "Birth Registration";
        DateTime eventDate = new DateTime();
        String entityType = "";
        String providerId ="anm";
        String locationId ="";
        String formSubmissionId ="";
        Event event = new Event(baseEntityId, eventType, eventDate, entityType, providerId, locationId, formSubmissionId);
        event.setId("23456");
        String scheduleName = "VIT A 1";
        String schedulesStr = getFile();
        List<Object> values = new ArrayList<>();
        values.add("2016-07-10");
        Obs observation1  = new Obs("concept", "date", "1410AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", values, "", "");
        List<Obs> obs = new ArrayList<>();
        obs.add(observation1);		
        Client client = new Client("ooo-yyy-yyy", "hmmm", "hummm", "lssssss", new DateTime("1995-12-28T00:00:00.000Z"), new DateTime(), true, true, "Female", "", "");
        List<Object> values1 = new ArrayList<>();
        values1.add("2017-06-08 09:33:39");		
        Obs observation2  = new Obs("concept", "birthdate", "163137AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", values1, "", "birthdate");
        event.addObs(observation1);
        event.addObs(observation2);
        JSONArray schedulesJsonObject = new JSONArray("[" + schedulesStr + "]");        
        for (int i = 0; i < schedulesJsonObject.length(); i++) {
            JSONObject scheduleJsonObject = schedulesJsonObject.getJSONObject(i);
            String handler = scheduleJsonObject.has(JSON_KEY_HANDLER)?scheduleJsonObject.getString(JSON_KEY_HANDLER):"VaccinesScheduleHandler";
            JSONArray eventsJsonArray = scheduleJsonObject.getJSONArray(JSON_KEY_EVENTS);
            scheduleName = scheduleJsonObject.getString(JSON_KEY_SCHEDULE_NAME);
            System.err.println("scheduleName:"+scheduleName);
            for (int j = 0; j < eventsJsonArray.length(); j++) {
                JSONObject scheduleConfigEvent = eventsJsonArray.getJSONObject(j);
                JSONArray eventTypesJsonArray = scheduleConfigEvent.getJSONArray(JSON_KEY_TYPES);
                List<String> eventsList = jsonArrayToList(eventTypesJsonArray);
                System.err.println("eventsList:"+eventsList);
                if (eventsList.contains(event.getEventType())) {					
                	vaccinesScheduleHandler.handle(event,scheduleConfigEvent, "MEASLES 1");
                    //InOrder inOrder = inOrder(anteNatalCareSchedulesService);
                    /*inOrder.verify(anteNatalCareSchedulesService).enrollMother(event.getBaseEntityId(),scheduleName, LocalDate.parse("2017-02-03"),
					   event.getId());*/					
                }				
            }			
        }		
    }
    @Test
    public void shouldVaccinesScheduleHandlerForMR() throws Exception{
        String baseEntityId = "ooo-yyy-yyy";
        String eventType="Vaccination";
       // String eventType = "Birth Registration";
        DateTime eventDate = new DateTime();
        String entityType = "";
        String providerId ="anm";
        String locationId ="";
        String formSubmissionId ="";
        Event event = new Event(baseEntityId, eventType, eventDate, entityType, providerId, locationId, formSubmissionId);
        event.setId("23456");
        String scheduleName = "VIT A 1";
        String schedulesStr = getFile();
        List<Object> values = new ArrayList<>();
        values.add("2016-07-10");
        Obs observation1  = new Obs("concept", "date", "1410AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", values, "", "");
        List<Obs> obs = new ArrayList<>();
        obs.add(observation1);		
        Client client = new Client("ooo-yyy-yyy", "hmmm", "hummm", "lssssss", new DateTime("1995-12-28T00:00:00.000Z"), new DateTime(), true, true, "Female", "", "");
        List<Object> values1 = new ArrayList<>();
        values1.add("2017-06-08 09:33:39");		
        Obs observation2  = new Obs("concept", "birthdate", "163137AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", values1, "", "birthdate");
        event.addObs(observation1);
        event.addObs(observation2);
        JSONArray schedulesJsonObject = new JSONArray("[" + schedulesStr + "]");        
        for (int i = 0; i < schedulesJsonObject.length(); i++) {
            JSONObject scheduleJsonObject = schedulesJsonObject.getJSONObject(i);
            String handler = scheduleJsonObject.has(JSON_KEY_HANDLER)?scheduleJsonObject.getString(JSON_KEY_HANDLER):"VaccinesScheduleHandler";
            JSONArray eventsJsonArray = scheduleJsonObject.getJSONArray(JSON_KEY_EVENTS);
            scheduleName = scheduleJsonObject.getString(JSON_KEY_SCHEDULE_NAME);
            System.err.println("scheduleName:"+scheduleName);
            for (int j = 0; j < eventsJsonArray.length(); j++) {
                JSONObject scheduleConfigEvent = eventsJsonArray.getJSONObject(j);
                JSONArray eventTypesJsonArray = scheduleConfigEvent.getJSONArray(JSON_KEY_TYPES);
                List<String> eventsList = jsonArrayToList(eventTypesJsonArray);
                System.err.println("eventsList:"+eventsList);
                if (eventsList.contains(event.getEventType())) {					
                	vaccinesScheduleHandler.handle(event,scheduleConfigEvent, "MR 1");
                    //InOrder inOrder = inOrder(anteNatalCareSchedulesService);
                    /*inOrder.verify(anteNatalCareSchedulesService).enrollMother(event.getBaseEntityId(),scheduleName, LocalDate.parse("2017-02-03"),
					   event.getId());*/					
                }				
            }			
        }		
    }
    @Test
    public void shouldVaccinesScheduleHandlerForOPV() throws Exception{
        String baseEntityId = "ooo-yyy-yyy";
        String eventType="Vaccination";
       // String eventType = "Birth Registration";
        DateTime eventDate = new DateTime();
        String entityType = "";
        String providerId ="anm";
        String locationId ="";
        String formSubmissionId ="";
        Event event = new Event(baseEntityId, eventType, eventDate, entityType, providerId, locationId, formSubmissionId);
        event.setId("23456");
        String scheduleName = "VIT A 1";
        String schedulesStr = getFile();
        List<Object> values = new ArrayList<>();
        values.add("2016-07-10");
        Obs observation1  = new Obs("concept", "date", "1410AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", values, "", "");
        List<Obs> obs = new ArrayList<>();
        obs.add(observation1);		
        Client client = new Client("ooo-yyy-yyy", "hmmm", "hummm", "lssssss", new DateTime("1995-12-28T00:00:00.000Z"), new DateTime(), true, true, "Female", "", "");
        List<Object> values1 = new ArrayList<>();
        values1.add("2017-06-08 09:33:39");		
        Obs observation2  = new Obs("concept", "birthdate", "163137AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", values1, "", "birthdate");
        event.addObs(observation1);
        event.addObs(observation2);
        List<Event> events = new ArrayList<>();
        AllEvents spy = PowerMockito.spy(allEvents);
        when(spy, method(AllEvents.class, "findByBaseEntityIdAndConceptParentCode", String.class, String.class,String.class))
        .withArguments(anyString(), anyString(),anyString())
        .thenReturn(events);
        JSONArray schedulesJsonObject = new JSONArray("[" + schedulesStr + "]");        
        for (int i = 0; i < schedulesJsonObject.length(); i++) {
            JSONObject scheduleJsonObject = schedulesJsonObject.getJSONObject(i);
            String handler = scheduleJsonObject.has(JSON_KEY_HANDLER)?scheduleJsonObject.getString(JSON_KEY_HANDLER):"VaccinesScheduleHandler";
            JSONArray eventsJsonArray = scheduleJsonObject.getJSONArray(JSON_KEY_EVENTS);
            scheduleName = scheduleJsonObject.getString(JSON_KEY_SCHEDULE_NAME);
            System.err.println("scheduleName:"+scheduleName);
            for (int j = 0; j < eventsJsonArray.length(); j++) {
                JSONObject scheduleConfigEvent = eventsJsonArray.getJSONObject(j);
                JSONArray eventTypesJsonArray = scheduleConfigEvent.getJSONArray(JSON_KEY_TYPES);
                List<String> eventsList = jsonArrayToList(eventTypesJsonArray);
                System.err.println("eventsList:"+eventsList);
                if (eventsList.contains(event.getEventType())) {
                	
                	vaccinesScheduleHandler.handle(event,scheduleConfigEvent, "OPV 4");
                    //InOrder inOrder = inOrder(anteNatalCareSchedulesService);
                    /*inOrder.verify(anteNatalCareSchedulesService).enrollMother(event.getBaseEntityId(),scheduleName, LocalDate.parse("2017-02-03"),
					   event.getId());*/					
                }				
            }			
        }		
    }

}
