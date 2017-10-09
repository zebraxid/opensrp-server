package org.opensrp.web.listener;

import java.util.List;

import org.json.JSONException;
import org.opensrp.connector.rapidpro.MessageFactory;
import org.opensrp.connector.rapidpro.MessageService;
import org.opensrp.connector.rapidpro.MessageType;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class ClientListener {
	
	private ActionService actionService;
	
	private MessageService messageService;
	
	public ClientListener() {
		
	}
	
	@Autowired
	public ClientListener(ActionService actionService, MessageService messageService) {
		this.actionService = actionService;
		this.messageService = messageService;
		
	}
	
	/*public void campAnnouncementListener(String HA) {
		MessageFactory messageFactory = null;
		messageFactory = MessageFactory.getMessageFactory("Ann");
		try {
			List<CampDate> campDates = campDateService.findCampByToday(HA);
			sentMessageToClient(messageFactory, campDates);
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
	}*/
	
	public void fetchClient() throws JSONException {
		
		MessageFactory messageFactory = null;
		messageFactory = MessageFactory.getMessageFactory(MessageType.REMINDER);
		
		try {
			List<Action> actions = actionService.findAllActionNotExpired();
			messageService.sentMessageToClient(messageFactory, actions);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
