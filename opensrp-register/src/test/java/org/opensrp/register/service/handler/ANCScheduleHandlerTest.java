package org.opensrp.register.service.handler;

import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.motechproject.scheduletracking.api.domain.json.ScheduleRecord;
import org.motechproject.scheduletracking.api.repository.AllSchedules;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.domain.Obs;
import org.opensrp.register.service.scheduling.AnteNatalCareSchedulesService;
import org.opensrp.repository.AllClients;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register.xml")
/*@RunWith(PowerMockRunner.class)
@PrepareForTest({ANCScheduleHandler.class})
@PowerMockIgnore({ "org.apache.log4j.*", "org.apache.commons.logging.*" })*/
public class ANCScheduleHandlerTest extends TestResourceLoader {	
    @Mock
    private AnteNatalCareSchedulesService anteNatalCareSchedulesService;
    @Autowired
    private ANCScheduleHandler aNCScheduleHandler;
    @Autowired
    private AllClients allClients;
    @Autowired
    private AllSchedules allSchedules;
    private static final String JSON_KEY_HANDLER = "handler";	
    private static final String JSON_KEY_TYPES = "types";	
    private static final String JSON_KEY_SCHEDULE_NAME = "name";	
    private static final String JSON_KEY_EVENTS = "events";	
	
    @Before
    public void setUp() throws Exception {
        initMocks(this);		
    }

    @Test
    public void shouldTestANCScheduleHandler() throws Exception{
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
        String scheduleName = "";
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
        //iterate through concatenated schedule-configs files to retrieve the events and compare with the current event from the db
        System.out.println("aNCScheduleHandler:"+aNCScheduleHandler);		
        allClients.add(client);
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
                    aNCScheduleHandler.handle(event,scheduleConfigEvent, scheduleName);
                    // InOrder inOrder = inOrder(anteNatalCareSchedulesService);
                    /*inOrder.verify(anteNatalCareSchedulesService).enrollMother(event.getBaseEntityId(),scheduleName, LocalDate.parse(refDate),
					   event.getId());*/					
                }				
            }			
        }		
    }	
	
    
}
