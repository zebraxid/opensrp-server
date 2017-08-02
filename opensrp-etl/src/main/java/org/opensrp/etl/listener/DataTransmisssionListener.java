package org.opensrp.etl.listener;

import org.motechproject.scheduler.domain.MotechEvent;
import org.motechproject.server.event.annotations.MotechListener;
import org.opensrp.etl.constant.ETLConstant;
import org.opensrp.etl.document.HouseholdDocument;
import org.opensrp.etl.entity.HousoholdEntity;
import org.opensrp.etl.service.HouseholdServices;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataTransmisssionListener {
	
	private HouseholdServices householdServices;	
	private HousoholdEntity housoholdEntity;	
    private AllHouseHolds allHouseHolds;
    @Autowired
	public DataTransmisssionListener(HouseholdServices householdServices,HousoholdEntity housoholdEntity,
			AllHouseHolds allHouseHolds) {
		this.householdServices = householdServices;
		this.housoholdEntity = housoholdEntity;
		this.allHouseHolds = allHouseHolds;
	}
	
	@MotechListener(subjects = ETLConstant.DATA_TRANSMISSION_SUBJECT)	
	public void sentDataToPostgres(MotechEvent event){
		System.err.println("Data transmission listener");
		HouseHold houseHold = allHouseHolds.findByCaseId("0001af07-b96a-48aa-a4c2-aa5e5eebbcbd");   	
    	HouseholdDocument doc = HouseholdDocument.getInstance();   	
    	doc.sendPreparedData(houseHold,householdServices);
    	
	}

}
