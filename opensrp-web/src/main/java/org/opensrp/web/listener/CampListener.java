package org.opensrp.web.listener;

import java.util.List;

import org.opensrp.camp.dao.CampDate;
import org.opensrp.camp.service.CampDateService;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMembers;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.service.MessageFactory;
import org.opensrp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class CampListener {
	private CampDateService campDateService;
	private AllActions allActions;
	private AllMembers allMembers;
	private AllHouseHolds allHouseHolds;
	private MessageService messageService;	
	
	@Autowired
    public void setMessageService(MessageService messageService) {
    	this.messageService = messageService;
    }

	@Autowired
    public void setAllMembers(AllMembers allMembers) {
    	this.allMembers = allMembers;
    }
	
	@Autowired
    public void setAllHouseHolds(AllHouseHolds allHouseHolds) {
    	this.allHouseHolds = allHouseHolds;
    }
	@Autowired
    public void setAllActions(AllActions allActions) {
    	this.allActions = allActions;
    }
	@Autowired
    public void setCampDateService(CampDateService campDateService) {
    	this.campDateService = campDateService;
    }
	
	public void campAnnouncementListener() {
		MessageFactory messageFactory = null;
		messageFactory = MessageFactory.getMessageFactory("Ann");
		try{
			List<CampDate> campDates =campDateService.findCampByToday();			
			sentMessageToClient(messageFactory,campDates);
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	public void campRemainderListener() {	
		MessageFactory messageFactory = null;
		messageFactory = MessageFactory.getMessageFactory("Rem");
		try{
			List<CampDate> campDates =campDateService.findByTimeStamp();			
			sentMessageToClient(messageFactory,campDates);		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	 private void sentMessageToClient(MessageFactory messageFactory,List<CampDate> campDates){
		String message ;
		System.err.println("messageFactory:"+messageFactory);
		if(campDates != null){
			for (CampDate campDate : campDates) {
				List<Action> actions = allActions.listOfEligibleClientForVaccine(campDate.getHealth_assistant(),campDate.getSession_name());
				
				for (Action action : actions) {					
					Members member = allMembers.findByCaseId(action.caseId());					
					HouseHold houseHold = allHouseHolds.findByCaseId(member.details().get("relationalid"));										
					if(member.Is_child().equalsIgnoreCase("0")){
						message = messageFactory.getMessageType("Child").message(member, campDate);
					}else{
						message =messageFactory.getMessageType("Woman").message(member, campDate);
					}					
					messageService.sentMessage(message, member.Member_Fname(), houseHold.HoH_Mobile_No(),campDate.getSession_location());
	            }
	        }
		}else{
			
		}
	}
	
   
	
}
