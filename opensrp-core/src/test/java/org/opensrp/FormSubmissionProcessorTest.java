package org.opensrp;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.opensrp.form.service.FormAttributeParser;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.Schedule;
import org.opensrp.scheduler.Schedule.ActionType;
import org.opensrp.service.ClientService;
import org.opensrp.service.EventService;
import org.opensrp.service.formSubmission.FormEntityConverter;
import org.opensrp.service.formSubmission.FormSubmissionProcessor;
import org.opensrp.service.formSubmission.handler.FormSubmissionRouter;
import org.opensrp.service.formSubmission.ziggy.ZiggyService;
import org.opensrp.util.ScheduleBuilder;
import org.opensrp.util.TestResourceLoader;

import com.google.gson.JsonIOException;

public class FormSubmissionProcessorTest extends TestResourceLoader{

	public FormSubmissionProcessorTest() throws IOException {
		super();
	}

	@Mock
	private FormSubmissionProcessor fsp;
	@Mock
	private ZiggyService ziggyService;
	@Mock
	private FormSubmissionRouter formSubmissionRouter;
	@Mock
	private FormEntityConverter formEntityConverter;
	@Mock
	private HealthSchedulerService scheduleService;
	@Mock
	private ClientService clientService;
	@Mock
	private EventService eventService;
	
	@Before
	public void setup() throws IOException{
		initMocks(this);
		FormEntityConverter fec = new FormEntityConverter(new FormAttributeParser("/form"));
		fsp = new FormSubmissionProcessor(ziggyService, formSubmissionRouter, 
				fec, scheduleService, clientService, eventService);
	}
	
	@Test
	public void testFormSubmission() throws Exception{
		FormSubmission submission = getFormSubmissionFor("pnc_reg_form");

		List<Schedule> schl = new ArrayList<Schedule>();
		schl.add(new Schedule(ActionType.enroll, new String[]{submission.formName()}, "Boosters", "REMINDER", new String[]{"birthdate"}, "child", ""));
		when(scheduleService.findAutomatedSchedules(submission.formName())).thenReturn(schl);
		when(clientService.getByBaseEntityId(any(String.class))).thenReturn(new Client("testid"));

		fsp.processFormSubmission(submission);
		
		
		int totalEntities = 1;
		for (SubFormData e : submission.subForms()) {
			totalEntities += e.instances().size();
		}
		verify(clientService, times(totalEntities-1)).addClient(any(Client.class), any(String.class));
		verify(eventService, times(totalEntities)).addEvent(any(Event.class), any(String.class));
		verify(scheduleService, times(totalEntities-1)).enrollIntoSchedule(any(String.class), 
				eq("Boosters"), eq("REMINDER"), any(String.class), eq(submission.getInstanceId()));
	}
	
	@Test
	public void shouldSkipClientCreationAndCreateEventIfNoClientDataUpdated() throws Exception {
		FormSubmission submission = getFormSubmissionFor("pnc_reg_form", 2);
		when(clientService.getByBaseEntityId(submission.entityId())).thenReturn(new Client(submission.entityId()));
		
		fsp.processFormSubmission(submission);
		
		verify(clientService, times(0)).addClient(any(Client.class), any(String.class));
		verify(clientService, times(0)).mergeClient(any(Client.class), any(String.class));
		verify(clientService, times(1)).getByBaseEntityId(any(String.class));
		verify(eventService, times(1)).addEvent(any(Event.class), any(String.class));
	}
	
	@Test(expected=IllegalStateException.class)
	public void shouldFailIfClientDataUpdatedAndNoClientExists() throws Exception {
		FormSubmission submission = getFormSubmissionFor("pnc_reg_form", 3);
		
		fsp.processFormSubmission(submission);
		
		verify(clientService, times(0)).addClient(any(Client.class), any(String.class));
		verify(clientService, times(1)).mergeClient(any(Client.class), any(String.class));
		verify(clientService, times(1)).getByBaseEntityId(any(String.class));
	}
	
	@Test
	public void shouldPassIfClientDataUpdatedAndClientExists() throws Exception {
		FormSubmission submission = getFormSubmissionFor("pnc_reg_form", 3);
		when(clientService.getByBaseEntityId(submission.entityId())).thenReturn(new Client(submission.entityId()));
		when(clientService.findClient(any(Client.class))).thenReturn(new Client(submission.entityId()));
		when(formEntityConverter.hasUpdatedClientProperties(submission)).thenReturn(true);
		when(formEntityConverter.getClientFromFormSubmission(submission)).thenReturn(new Client(submission.entityId()));
		
		fsp.processFormSubmission(submission);
		
		verify(clientService, times(0)).addClient(any(Client.class), any(String.class));
		verify(clientService, times(1)).mergeClient(any(Client.class), any(String.class));
		verify(clientService, times(1)).getByBaseEntityId(any(String.class));
		verify(eventService, times(1)).addEvent(any(Event.class), any(String.class));
	}
	
	@Test
	public void shouldCreateEventFromRepeatGroupForSameEntity() throws Exception {
		FormSubmission submission = getFormSubmissionFor("delivery_form");
		when(clientService.getByBaseEntityId(submission.entityId())).thenReturn(new Client(submission.entityId()));
		when(clientService.findClient(any(Client.class))).thenReturn(new Client(submission.entityId()));
		when(formEntityConverter.hasUpdatedClientProperties(submission)).thenReturn(true);
		when(formEntityConverter.getClientFromFormSubmission(submission)).thenReturn(new Client(submission.entityId()));
		
		fsp.processFormSubmission(submission);
		// only main client would be saved
		verify(clientService, times(1)).mergeClient(any(Client.class), any(String.class));
		verify(clientService, times(1)).getByBaseEntityId(any(String.class));
		verify(eventService, times(1)).addEvent(any(Event.class), any(String.class));
		
		verify(clientService, times(1)).getByBaseEntityId(any(String.class));
		verify(eventService, times(1)).addEvent(any(Event.class), any(String.class));
		
		verify(clientService, times(1)).getByBaseEntityId(any(String.class));
		verify(eventService, times(1)).addEvent(any(Event.class), any(String.class));
		
		verify(clientService, times(1)).getByBaseEntityId(any(String.class));
		verify(eventService, times(1)).addEvent(any(Event.class), any(String.class));
	}
	
}
