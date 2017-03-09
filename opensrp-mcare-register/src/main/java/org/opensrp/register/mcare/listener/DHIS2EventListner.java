package org.opensrp.register.mcare.listener;

import org.motechproject.scheduler.domain.MotechEvent;
import org.motechproject.server.event.annotations.MotechListener;
import org.opensrp.register.mcare.OpenSRPScheduleConstants;
import org.opensrp.register.mcare.repository.AllMembers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DHIS2EventListner {
	private static Logger logger = LoggerFactory.getLogger(DHIS2EventListner.class.toString());
	private AllMembers allMembers;
	
	@Autowired
	 public DHIS2EventListner(AllMembers allMembers) {
		super();
		this.allMembers = allMembers;
	}

	@MotechListener(subjects = OpenSRPScheduleConstants.DHIS2_DATA_SEND_SCHEDULE_SUBJECT)
	 public void sendDataToDHIS2(MotechEvent event) {
		 System.out.println("In DHIS2 subject");
	 }
}
