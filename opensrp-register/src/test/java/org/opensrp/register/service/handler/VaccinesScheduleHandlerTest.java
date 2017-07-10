package org.opensrp.register.service.handler;

import static org.mockito.Matchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.motechproject.model.Time;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.motechproject.scheduletracking.api.domain.EnrollmentStatus;
import org.motechproject.scheduletracking.api.domain.Schedule;
import org.opensrp.common.util.TestLoggerAppender;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.domain.Obs;
import org.opensrp.register.service.handler.BaseScheduleHandler.ActionType;
import org.opensrp.repository.AllEvents;
import org.opensrp.scheduler.HealthSchedulerService;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.Mockito.inOrder;

import com.google.gson.Gson;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register.xml")*/
@RunWith(PowerMockRunner.class)
//@PrepareForTest(AllEvents.class)
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
        Event event = getevent(); 
        String scheduleName = "";
        Schedule schedule = new Schedule("VIT A 1");
        Enrollment enrollment = new Enrollment("ooo-yyy-yyy", schedule, "", new DateTime(), new DateTime(), new Time(), EnrollmentStatus.ACTIVE, null);
        JSONArray schedulesJsonObject = new JSONArray("[" + getFile() + "]");
        when(scheduler, method(HealthSchedulerService.class, "getActiveEnrollment", String.class,String.class))
            .withArguments("ooo-yyy-yyy", "VIT A 1")
            .thenReturn(enrollment);
        for (int i = 0; i < schedulesJsonObject.length(); i++) {
            JSONObject scheduleJsonObject = schedulesJsonObject.getJSONObject(i);
            String handler = scheduleJsonObject.has(JSON_KEY_HANDLER)?scheduleJsonObject.getString(JSON_KEY_HANDLER):"VaccinesScheduleHandler";
            JSONArray eventsJsonArray = scheduleJsonObject.getJSONArray(JSON_KEY_EVENTS);
            scheduleName = scheduleJsonObject.getString(JSON_KEY_SCHEDULE_NAME);            
            for (int j = 0; j < eventsJsonArray.length(); j++) {
                JSONObject scheduleConfigEvent = eventsJsonArray.getJSONObject(j);
                JSONArray eventTypesJsonArray = scheduleConfigEvent.getJSONArray(JSON_KEY_TYPES);
                List<String> eventsList = jsonArrayToList(eventTypesJsonArray);                
                if (eventsList.contains(event.getEventType())) {
                	String milestone=vaccinesScheduleHandler.getMilestone(scheduleConfigEvent);
                    if(milestone.equalsIgnoreCase("opv2")){
                        vaccinesScheduleHandler.handle(event,scheduleConfigEvent, "VIT A 1");
                        scheduleName = "VIT A 1";
                        String action = vaccinesScheduleHandler.getAction(scheduleConfigEvent);
                        InOrder inOrder = inOrder(scheduler);
                        inOrder.verify(scheduler).getActiveEnrollment("ooo-yyy-yyy", "VIT A 1");
                        inOrder.verify(scheduler).unEnrollFromSchedule("ooo-yyy-yyy", "anm", "VIT A 1", event.getId());
                        LocalDate  date = LocalDate.parse(vaccinesScheduleHandler.getReferenceDateForSchedule(event, scheduleConfigEvent, action));
                        inOrder.verify(scheduler).enrollIntoSchedule(event.getBaseEntityId(), scheduleName, milestone,
                        		vaccinesScheduleHandler.getReferenceDateForSchedule(event, scheduleConfigEvent, action), event.getId());                        
                    }
                }				
            }			
        }		
    }
    @Test
    public void shouldVaccinesScheduleHandlerForMEASLES() throws Exception{
        Event event = getevent(); 
        String scheduleName = "";
        JSONArray schedulesJsonObject = new JSONArray("[" + getFile() + "]");         
        for (int i = 0; i < schedulesJsonObject.length(); i++) {
            JSONObject scheduleJsonObject = schedulesJsonObject.getJSONObject(i);
            String handler = scheduleJsonObject.has(JSON_KEY_HANDLER)?scheduleJsonObject.getString(JSON_KEY_HANDLER):"VaccinesScheduleHandler";
            JSONArray eventsJsonArray = scheduleJsonObject.getJSONArray(JSON_KEY_EVENTS);
            scheduleName = scheduleJsonObject.getString(JSON_KEY_SCHEDULE_NAME);            
            for (int j = 0; j < eventsJsonArray.length(); j++) {
                JSONObject scheduleConfigEvent = eventsJsonArray.getJSONObject(j);
                JSONArray eventTypesJsonArray = scheduleConfigEvent.getJSONArray(JSON_KEY_TYPES);
                List<String> eventsList = jsonArrayToList(eventTypesJsonArray);                
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
        Event event = getevent(); 
        String scheduleName = "";
        JSONArray schedulesJsonObject = new JSONArray("[" + getFile() + "]");        
        for (int i = 0; i < schedulesJsonObject.length(); i++) {
            JSONObject scheduleJsonObject = schedulesJsonObject.getJSONObject(i);
            String handler = scheduleJsonObject.has(JSON_KEY_HANDLER)?scheduleJsonObject.getString(JSON_KEY_HANDLER):"VaccinesScheduleHandler";
            JSONArray eventsJsonArray = scheduleJsonObject.getJSONArray(JSON_KEY_EVENTS);
            scheduleName = scheduleJsonObject.getString(JSON_KEY_SCHEDULE_NAME);            
            for (int j = 0; j < eventsJsonArray.length(); j++) {
                JSONObject scheduleConfigEvent = eventsJsonArray.getJSONObject(j);
                JSONArray eventTypesJsonArray = scheduleConfigEvent.getJSONArray(JSON_KEY_TYPES);
                List<String> eventsList = jsonArrayToList(eventTypesJsonArray);                
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
        Event event = getevent();      
        List<Event> events = getEvents("2016-02-03");
        when(allEvents, method(AllEvents.class, "findByBaseEntityIdAndConceptParentCode", String.class, String.class,String.class))
            .withArguments(anyString(), anyString(),anyString())
            .thenReturn(events);
        JSONArray schedulesJsonObject = new JSONArray("[" + getFile() + "]");        
        String scheduleName = "";
        for (int i = 0; i < schedulesJsonObject.length(); i++) {
            JSONObject scheduleJsonObject = schedulesJsonObject.getJSONObject(i);            
            JSONArray eventsJsonArray = scheduleJsonObject.getJSONArray(JSON_KEY_EVENTS);
            scheduleName = scheduleJsonObject.getString(JSON_KEY_SCHEDULE_NAME);            
            for (int j = 0; j < eventsJsonArray.length(); j++) {
                JSONObject scheduleConfigEvent = eventsJsonArray.getJSONObject(j);
                JSONArray eventTypesJsonArray = scheduleConfigEvent.getJSONArray(JSON_KEY_TYPES);
                List<String> eventsList = jsonArrayToList(eventTypesJsonArray);                
                if (eventsList.contains(event.getEventType())) {                	
                	vaccinesScheduleHandler.handle(event,scheduleConfigEvent, "OPV 4");
                    //InOrder inOrder = inOrder(anteNatalCareSchedulesService);
                    /*inOrder.verify(anteNatalCareSchedulesService).enrollMother(event.getBaseEntityId(),scheduleName, LocalDate.parse("2017-02-03"),
					   event.getId());*/					
                }				
            }			
        }        
        
    }
    
    @Test
    public void shouldTestExceptionForVaccinesScheduleHandlerForOPV() throws Exception{
        Event event = getevent();      
        List<Event> events = getEvents("Not A Valid Date");
        when(allEvents, method(AllEvents.class, "findByBaseEntityIdAndConceptParentCode", String.class, String.class,String.class))
        .withArguments(anyString(), anyString(),anyString())
        .thenReturn(events);
        JSONArray schedulesJsonObject = new JSONArray("[" + getFile() + "]");
        final TestLoggerAppender appender = new TestLoggerAppender();       
        final Logger logger = Logger.getLogger(BaseScheduleHandler.class.toString());
        logger.setLevel(Level.ALL);
        logger.addAppender(appender);
        String scheduleName = "";
        for (int i = 0; i < schedulesJsonObject.length(); i++) {
            JSONObject scheduleJsonObject = schedulesJsonObject.getJSONObject(i);            
            JSONArray eventsJsonArray = scheduleJsonObject.getJSONArray(JSON_KEY_EVENTS);
            scheduleName = scheduleJsonObject.getString(JSON_KEY_SCHEDULE_NAME);            
            for (int j = 0; j < eventsJsonArray.length(); j++) {
                JSONObject scheduleConfigEvent = eventsJsonArray.getJSONObject(j);
                JSONArray eventTypesJsonArray = scheduleConfigEvent.getJSONArray(JSON_KEY_TYPES);
                List<String> eventsList = jsonArrayToList(eventTypesJsonArray);                
                if (eventsList.contains(event.getEventType())) {                	
                	vaccinesScheduleHandler.handle(event,scheduleConfigEvent, "OPV 4");
                    //InOrder inOrder = inOrder(anteNatalCareSchedulesService);
                    /*inOrder.verify(anteNatalCareSchedulesService).enrollMother(event.getBaseEntityId(),scheduleName, LocalDate.parse("2017-02-03"),
					   event.getId());*/					
                }				
            }			
        }
        final List<LoggingEvent> log = appender.getLog();
        final LoggingEvent firstLogEntry = log.get(0);
        Assert.assertEquals(firstLogEntry.getRenderedMessage(), "Unparseable date: \"Not A Valid Date\"");
        logger.removeAllAppenders();
        
    }

}
