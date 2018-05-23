package org.opensrp.web.listener;

import java.util.List;

import org.opensrp.common.util.DateUtil;
import org.opensrp.connector.rapidpro.MessageFactory;
import org.opensrp.connector.rapidpro.MessageService;
import org.opensrp.connector.rapidpro.MessageType;
import org.opensrp.domain.Camp;
import org.opensrp.domain.Event;
import org.opensrp.dto.rapidpro.AnnouncedClient;
import org.opensrp.repository.AllCamp;
import org.opensrp.scheduler.service.ActionService;
import org.opensrp.service.EventService;
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
	
	@Autowired
	private EventService eventService;
	
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
		logger.info("request receive for camp announchment message provider: " + provider);
		
		List<Camp> camps = allCamp.findAllActiveByProvider(provider);
		if (camps != null) {
			for (Camp camp : camps) {
				try {
					if (DateUtil.dateDiff(camp.getDate()) == 0) {
						allCamp.updateCamp(camp);
						List<Event> events = eventService.findByProviderAndEntityType(camp.getProviderName());
						logger.info("total events found for announcement eventSize: " + events.size() + " ,provider:"
						        + camp.getProviderName());
						messageService.sentMessageToClient(messageFactory, events, camp);
						
					} else {
						logger.info("no camp found for camp announchment message provider:" + camp.getProviderName());
					}
				}
				catch (Exception e) {
					logger.warn("could not complete announchment for provider: " + camp.getProviderName() + " ,campdate: "
					        + camp.getDate());
					logger.error("camp announcement error: " + e.getMessage() + " ,cause:" + e.getCause());
				}
			}
		} else {
			logger.info("no camp found for camp announchment message provider:" + provider);
		}
		
	}
	
	public int campAnnouncementListener(String provider, List<AnnouncedClient> announcedClients) {
		MessageFactory messageFactory = null;
		messageFactory = MessageFactory.getMessageFactory(MessageType.ANNOUNCEMENT);
		
		List<Camp> camps = allCamp.findAllActiveByProvider(provider);
		if (camps == null) {
			logger.info("no active camp found for announchment provider:" + provider);
			return 0;
		} else {
			for (Camp camp : camps) {
				try {
					if (DateUtil.dateDiff(camp.getDate()) != 0) {
						logger.info("no active camp found today for announchment  provider:" + camp.getProviderName());
						return 0;
						
					} else {
						allCamp.updateCamp(camp);
						messageService.sentMessageToClientNew(messageFactory, announcedClients, camp);
					}
				}
				catch (Exception e) {
					logger.warn("could not complete announchment for provider: " + camp.getProviderName() + " ,campdate: "
					        + camp.getDate());
					logger.error("camp announcement error: " + e.getMessage() + " ,cause:" + e.getCause());
					return -1;
				}
			}
		}
		return 1;
	}
	
	public void fetchClient() {
		MessageFactory messageFactory = null;
		messageFactory = MessageFactory.getMessageFactory(MessageType.REMINDER);
		logger.info("started processing camp reminder messages");
		List<Camp> camps = allCamp.findAllActive();
		logger.info("total active camp found campsize: " + camps.size());
		if (camps.size() != 0) {
			for (Camp camp : camps) {
				try {
					if (DateUtil.dateDiff(camp.getDate()) == -1) {
						logger.info("active camp found for camp reminder message campDate: " + camp.getDate()
						        + " ,provider:" + camp.getProviderName());
						if (camp.getProviderName() == null || camp.getProviderName().isEmpty()
						        || camp.getProviderName().equalsIgnoreCase("")) {
							logger.info("problem with camp definition " + camp.getDate() + " ,_id:" + camp.getId());
							
						} else {
							List<Event> events = eventService.findByProviderAndEntityType(camp.getProviderName());
							if (events.size() != 0) {
								logger.info("total events found for reminder eventSize: " + events.size() + " ,provider:"
								        + camp.getProviderName());
								messageService.sentMessageToClient(messageFactory, events, camp);
							} else {
								logger.info("no events found for reminder eventSize: " + events.size() + " ,provider:"
								        + camp.getProviderName());
							}
						}
						//allCamp.updateCamp(camp);
					} else {
						logger.info("no camp found for camp reminder message campDate: " + camp.getDate() + " ,provider:"
						        + camp.getProviderName());
					}
				}
				catch (Exception e) {
					logger.error("fetchClient error: " + e.getMessage() + " ,cause:" + e.getCause());
				}
			}
			
		} else {
			logger.info("no camp found for camp reminder message");
		}
		
	}
}
