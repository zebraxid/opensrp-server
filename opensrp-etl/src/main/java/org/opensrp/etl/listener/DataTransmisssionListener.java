package org.opensrp.etl.listener;

import static java.util.Collections.sort;

import java.util.Comparator;
import java.util.List;

import org.motechproject.scheduler.domain.MotechEvent;
import org.motechproject.server.event.annotations.MotechListener;
import org.opensrp.etl.constant.ETLConstant;
import org.opensrp.etl.document.processor.HouseholdDocumentProcesor;
import org.opensrp.etl.entity.HousoholdEntity;
import org.opensrp.etl.entity.MarkerEntity;
import org.opensrp.etl.service.HouseholdServices;
import org.opensrp.etl.service.MarkerService;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataTransmisssionListener {
	
	private HouseholdServices householdServices;
	private HousoholdEntity housoholdEntity;
    private AllHouseHolds allHouseHolds;
    private MarkerService markerService;
    
    private HouseholdDocumentProcesor householdDocumentProcesor = HouseholdDocumentProcesor.getInstance();
    
	public DataTransmisssionListener() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public DataTransmisssionListener(HouseholdServices householdServices,HousoholdEntity housoholdEntity,
			AllHouseHolds allHouseHolds,MarkerService markerService) {
		this.householdServices = householdServices;
		this.housoholdEntity = housoholdEntity;
		this.allHouseHolds = allHouseHolds;
		this.markerService = markerService;
	}


	@MotechListener(subjects = ETLConstant.DATA_TRANSMISSION_SUBJECT)	
	public void transferHouseholdDocument(MotechEvent event){
		System.err.println("called");
		MarkerEntity markerEntity = markerService.getMarkerByNameAndType("household","load");
		List<HouseHold> houseHolds = allHouseHolds.findByTypeAndTimeStamp("HouseHold", markerEntity.getTimeStamp());
		System.err.println("houseHolds:"+houseHolds.size());
		sort(houseHolds, serverVersionComparator());
		for (HouseHold houseHold : houseHolds) {
			try {
                householdDocumentProcesor.savePreparedData(houseHold,householdServices,housoholdEntity);
                markerEntity.setTimeStamp(houseHold.get());
                markerService.update(markerEntity);
                System.err.println("houseHold.get():"+houseHold.get());
			}
			catch (Exception e) {
				System.err.println("Message:"+e.getMessage());
			} 
			finally {
				
			}
		}    	
	}
	
	private Comparator<HouseHold> serverVersionComparator() {
        return new Comparator<HouseHold>() {
            public int compare(HouseHold firstHousehold, HouseHold secondHouseHold) {
                long firstTimestamp = firstHousehold.get();
                long secondTimestamp = secondHouseHold.get();
                return firstTimestamp == secondTimestamp ? 0 : firstTimestamp < secondTimestamp ? -1 : 1;
            }
        };
    }

}
