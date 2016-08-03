package org.opensrp.register.mcare.service.reporting;

import java.util.ArrayList;
import java.util.List;

import org.opensrp.register.mcare.ChildRegistration;
import org.opensrp.register.mcare.ChildRegistrationEntry;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.repository.AllChilds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildReportService {
	
	private AllChilds allChilds;
	
	@Autowired
	public ChildReportService(AllChilds allChilds) {
		this.allChilds = allChilds;
	}
	
	public ChildRegistration getChildDataVisitedPrev7Days(String providerId) {
		ArrayList<ChildRegistrationEntry> childRegisterEntries = new ArrayList<>();
		List<Child> childs = allChilds.allChildsVisited7Days(providerId);
		
		for (Child child : childs) {/*
		                                ChildRegisterEntry childRegisterEntry = new ChildRegisterEntry()
		                                .withCASEID(child.caseId())  
		                                .withPROVIDERID(child.PROVIDERID())
		                                //.withLOCATIONID(child.LOCATIONID())
		                                .withTODAY(child.TODAY())
		                                .withFWNHREGDATE(child.FWNHREGDATE())
		                                .withFWGOBChildID(child.FWGOBChildID())
		                                .withFWJIVChildID(child.FWJIVChildID())
		                                .withFWNChildHGPS(child.FWNChildHGPS())
		                                .withFWHOHFNAME(child.FWHOHFNAME())
		                                .withFWHOHLNAME(child.FWHOHLNAME())
		                                .withFWHOHBIRTHDATE(child.FWHOHBIRTHDATE())
		                                .withFWHOHGENDER(child.FWHOHGENDER())
		                                .withFWNChildMBRNUM(child.FWNChildMBRNUM())
		                                .withFWNChildMWRA(child.FWNChildMWRA())
		                                .withELCO(child.ELCO())
		                                .withuser_type(child.user_type())
		                                .withexternal_user_ID(child.external_user_ID())
		                                .withELCODETAILS(child.ELCODETAILS())
		                                .withDetails(child.details())
		                                .withLOCATIONID(child.getDetail(LOCATION_NAME))
		                                .withTODAY(child.getDetail(REFERENCE_DATE))
		                                .withSTART(child.getDetail(START_DATE))
		                                .withEND(child.getDetail(END_DATE))
		                                .withFWWOMAGE(child.getELCODetail(FW_WOMAGE))
		                                .withFWBIRTHDATE(child.getELCODetail(FW_BIRTHDATE))
		                                .withid(child.getELCODetail(id))
		                                .withFWWOMLNAME(child.getELCODetail(FW_WOMLNAME))
		                                .withFWWOMFNAME(child.getELCODetail(FW_WOMFNAME))
		                                .withJiVitAChildID(child.getELCODetail(FW_JiVitAChildID))
		                                .withFWGENDER(child.getELCODetail(FW_GENDER))
		                                .withFWWOMBID(child.getELCODetail(FW_WOMBID))
		                                .withFWWOMNID(child.getELCODetail(FW_WOMNID))
		                                .withFWHUSNAME(child.getELCODetail(FW_HUSNAME))
		                                .withFWELIGIBLE(child.getELCODetail(FW_ELIGIBLE))
		                                .withFWDISPLAYAGE(child.getELCODetail(FW_DISPLAY_AGE));
		                                
		                                childRegisterEntries.add(childRegisterEntry);
		                                */}
		return new ChildRegistration(childRegisterEntries);
	}
}
