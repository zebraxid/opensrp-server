package org.opensrp.etl.document.processor;

import org.opensrp.etl.document.HouseholdDocument;
import org.opensrp.etl.entity.HousoholdEntity;
import org.opensrp.etl.service.HouseholdServices;
import org.opensrp.register.mcare.domain.HouseHold;


public class HouseholdDocumentProcesor {
	
	private HouseholdDocument householdDocument = HouseholdDocument.getInstance();
	private static final HouseholdDocumentProcesor INSTANCE = new HouseholdDocumentProcesor();
	private HouseholdDocumentProcesor() {
		
	}	
	
	private HouseholdDocument getPreparedData(HouseHold houseHold) {
		
		householdDocument.setBirthDate(houseHold.FWHOHBIRTHDATE());
		householdDocument.setCaseId(houseHold.caseId());
		householdDocument.setClientVersion(houseHold.get());
		householdDocument.setCountry(houseHold.FWCOUNTRY());
		householdDocument.setCurrentFormStatus(houseHold.current_formStatus());
		householdDocument.setDetails(houseHold.details());
		householdDocument.setDistrict(houseHold.FWDISTRICT());
		householdDocument.setDivision(houseHold.FWDIVISION());
		householdDocument.setElco(houseHold.ELCO());
		householdDocument.setElcodetails(houseHold.ELCODETAILS());
		householdDocument.setEnd(houseHold.END());
		householdDocument.setExternalUserId(houseHold.external_user_ID());
		householdDocument.setFirstName(houseHold.FWHOHFNAME());
		householdDocument.setFormName(houseHold.form_name());
		householdDocument.setFWNHHMBRNUM(houseHold.FWNHHMBRNUM());
		householdDocument.setFWNHHMWRA(houseHold.FWNHHMWRA());
		householdDocument.setGender(houseHold.FWHOHGENDER());
		householdDocument.setGOBHHID(houseHold.FWGOBHHID());
		householdDocument.setGps(houseHold.FWNHHHGPS());
		householdDocument.setInstantId(houseHold.INSTANCEID());
		householdDocument.setJivitahouseholdId(houseHold.FWJIVHHID());
		householdDocument.setLastName(houseHold.FWHOHLNAME());
		householdDocument.setLocationId(houseHold.LOCATIONID());
		householdDocument.setMauzaPara(houseHold.FWMAUZA_PARA());
		householdDocument.setMultimediaAttachments(houseHold.multimediaAttachments());
		householdDocument.setProvider(houseHold.PROVIDERID());
		householdDocument.setRegistrationDate(houseHold.FWNHREGDATE());
		householdDocument.setStart(houseHold.START());
		householdDocument.setSubmissionTime(houseHold.SUBMISSIONDATE());
		householdDocument.setSubunit(houseHold.FWSUBUNIT());
		householdDocument.setToday(houseHold.TODAY());
		householdDocument.setUnion(houseHold.FWUNION());
		householdDocument.setUpazila(houseHold.FWUPAZILLA());
		householdDocument.setUserType(houseHold.user_type());
		householdDocument.setWard(houseHold.FWWARD());	    
		return householdDocument;
	}
    
	
	public void savePreparedData(HouseHold houseHold,HouseholdServices householdServices,HousoholdEntity housoholdEntity) {
		
		housoholdEntity.setCaseId(houseHold.caseId());
    	housoholdEntity.setCreated();
    	housoholdEntity.setTimeStamp();
    	housoholdEntity.setUpdated();
    	housoholdEntity.setDocumentId(houseHold.getId());
    	housoholdEntity.setTimeStamp();
    	housoholdEntity.setRevId(houseHold.getRevision());
    	housoholdEntity.setStatus(true);    	
    	housoholdEntity.setDoc(getPreparedData(houseHold));    	
    	householdServices.save(housoholdEntity);		
	}

	public static HouseholdDocumentProcesor getInstance() {
		return INSTANCE;
	}

}
