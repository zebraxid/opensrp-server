package org.opensrp.web.listener;

import java.util.List;

import org.json.JSONException;
import org.opensrp.common.util.DateUtil;
import org.opensrp.connector.rapidpro.MessageFactory;
import org.opensrp.connector.rapidpro.MessageService;
import org.opensrp.connector.rapidpro.MessageType;
import org.opensrp.domain.Camp;
import org.opensrp.repository.AllCamp;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.service.ActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class RapidproMessageListener {
	
	private ActionService actionService;
	
	private MessageService messageService;
	
	private static Logger logger = LoggerFactory.getLogger(RapidproMessageListener.class.toString());
	
	@Autowired
	private AllCamp allCamp;
	
	public RapidproMessageListener() {
		
	}
	
	@Autowired
	public RapidproMessageListener(ActionService actionService, MessageService messageService) {
		this.actionService = actionService;
		this.messageService = messageService;
		
	}
	
	public void campAnnouncementListener(String provider) {
		MessageFactory messageFactory = null;
		messageFactory = MessageFactory.getMessageFactory(MessageType.ANNOUNCEMENT);
		try {
			List<Camp> camps = allCamp.findAllActiveByProvider(provider);
			if (camps != null) {
				for (Camp camp : camps) {
					if (DateUtil.dateDiff(camp.getDate()) == 0) {
						List<Action> actions = actionService.findAllActionByProviderNotExpired(camp.getProviderName());
						messageService.sentMessageToClient(messageFactory, actions, camp);
						allCamp.updateCamp(camp);
					} else {
						logger.info("No Camp Found Today");
					}
				}
			} else {
				logger.info("No Camp Found");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fetchClient() throws JSONException {
		MessageFactory messageFactory = null;
		messageFactory = MessageFactory.getMessageFactory(MessageType.REMINDER);
		try {
			List<Camp> camps = allCamp.findAllActive();
			List<Action> actions = null;
			if (camps != null) {
				for (Camp camp : camps) {
					if (DateUtil.dateDiff(camp.getDate()) == -1) {
						if (camp.getProviderName() == null || camp.getProviderName().isEmpty()
						        || camp.getProviderName().equalsIgnoreCase("")) {
							actions = actionService.findAllActionNotExpired();
						} else {
							actions = actionService.findAllActionByProviderNotExpired(camp.getProviderName());
						}
						
						messageService.sentMessageToClient(messageFactory, actions, camp);
						allCamp.updateCamp(camp);
					} else {
						logger.info("No Camp Found Today");
					}
				}
				
			} else {
				logger.info("No Camp Found");
			}
			
		}
		catch (Exception e) {
			logger.info("Fetch client:" + e.getMessage());
		}
	}
}
