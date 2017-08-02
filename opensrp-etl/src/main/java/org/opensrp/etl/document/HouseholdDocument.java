package org.opensrp.etl.document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.opensrp.etl.entity.HousoholdEntity;
import org.opensrp.etl.interfaces.DocumentType;
import org.opensrp.etl.service.HouseholdServices;
import org.opensrp.register.mcare.domain.HouseHold;
import org.springframework.stereotype.Component;

@Component
public class HouseholdDocument extends CommonInformation implements Serializable,DocumentType<HouseholdDocument, HouseHold,HouseholdServices> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	private String firstName;	
	private String lastName;	
	private String birthDate; 	
	private String gender;	
	private String FWNHHMBRNUM;	
	private String FWNHHMWRA;	
	private String ELCO;
	private List<Map<String, String>> Elcodetails;
	
	
	
	private HouseholdDocument() {
		// TODO Auto-generated constructor stub
	}
	
	private static final HouseholdDocument INSTANCE = new HouseholdDocument();
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getFWNHHMBRNUM() {
		return FWNHHMBRNUM;
	}
	public void setFWNHHMBRNUM(String fWNHHMBRNUM) {
		FWNHHMBRNUM = fWNHHMBRNUM;
	}
	public String getFWNHHMWRA() {
		return FWNHHMWRA;
	}
	public void setFWNHHMWRA(String fWNHHMWRA) {
		FWNHHMWRA = fWNHHMWRA;
	}
	public String getElco() {
		return ELCO;
	}
	public void setElco(String ELCO) {
		this.ELCO = ELCO;
	}
	public List<Map<String, String>> getElcodetails() {
		return Elcodetails;
	}
	public void setElcodetails(List<Map<String, String>> elcodetails) {
		Elcodetails = elcodetails;
	}
	
	public static HouseholdDocument getInstance() {
		return INSTANCE;
	}
	
	@Override
	public HouseholdDocument getPreparedData(HouseHold houseHold) {
		getInstance().setBirthDate(houseHold.FWHOHBIRTHDATE());
		getInstance().setCaseId(houseHold.caseId());
		getInstance().setClientVersion(houseHold.get());
	    getInstance().setCountry(houseHold.FWCOUNTRY());
	    getInstance().setCurrentFormStatus(houseHold.current_formStatus());
	    getInstance().setDetails(houseHold.details());
	    getInstance().setDistrict(houseHold.FWDISTRICT());
	    getInstance().setDivision(houseHold.FWDIVISION());
	    getInstance().setElco(houseHold.ELCO());
	    getInstance().setElcodetails(houseHold.ELCODETAILS());
	    getInstance().setEnd(houseHold.END());
	    getInstance().setExternalUserId(houseHold.external_user_ID());
	    getInstance().setFirstName(houseHold.FWHOHFNAME());
	    getInstance().setFormName(houseHold.form_name());
	    getInstance().setFWNHHMBRNUM(houseHold.FWNHHMBRNUM());
	    getInstance().setFWNHHMWRA(houseHold.FWNHHMWRA());
	    getInstance().setGender(houseHold.FWHOHGENDER());
	    getInstance().setGOBHHID(houseHold.FWGOBHHID());
	    getInstance().setGps(houseHold.FWNHHHGPS());
	    getInstance().setInstantId(houseHold.INSTANCEID());
	    getInstance().setJivitahouseholdId(houseHold.FWJIVHHID());
	    getInstance().setLastName(houseHold.FWHOHLNAME());
	    getInstance().setLocationId(houseHold.LOCATIONID());
	    getInstance().setMauzaPara(houseHold.FWMAUZA_PARA());
	    getInstance().setMultimediaAttachments(houseHold.multimediaAttachments());
	    getInstance().setProvider(houseHold.PROVIDERID());
	    getInstance().setRegistrationDate(houseHold.FWNHREGDATE());
	    getInstance().setStart(houseHold.START());
	    getInstance().setSubmissionTime(houseHold.SUBMISSIONDATE());
	    getInstance().setSubunit(houseHold.FWSUBUNIT());
	    getInstance().setToday(houseHold.TODAY());
	    getInstance().setUnion(houseHold.FWUNION());
	    getInstance().setUpazila(houseHold.FWUPAZILLA());
	    getInstance().setUserType(houseHold.user_type());
	    getInstance().setWard(houseHold.FWWARD());
	   
	    
	    
		return getInstance();
	}
	@Override
	public void sendPreparedData(HouseHold houseHold,HouseholdServices householdServices) {
		HousoholdEntity housoholdEntity = new HousoholdEntity();
		
		housoholdEntity.setCaseId(houseHold.caseId());
    	housoholdEntity.setCreated();
    	housoholdEntity.setTimeStamp();
    	housoholdEntity.setUpdated();
    	housoholdEntity.setDocumentId(houseHold.getId());
    	housoholdEntity.setTimeStamp();
    	housoholdEntity.setRevId(houseHold.getRevision());
    	housoholdEntity.setStatus(true);
    	HouseholdDocument doc = HouseholdDocument.getInstance();
    	doc.getPreparedData(houseHold);
    	housoholdEntity.setDoc(doc);
    	
    	householdServices.addHousehold(housoholdEntity);
		
	}

}
