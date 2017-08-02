package org.opensrp.etl.document;

import java.util.List;
import java.util.Map;

public abstract class CommonInformation {
	
	private String caseId;	
	private String instantId;	
	private String provider;	
	private String locationId;	
	private String today;
	private String start;	
	private String end;	
	private String registrationDate;	
	private String GOBHHID; 	
	private String jivitaHouseholdId;	
	private String country;	
	private String division;	
	private String district;	
	private String upazila;	
	private String union;	
	private String ward;	
	private String subunit;	
	private String mauzaPara;	
	private String gps;	
	private String formName;	
	private String userType;	
	private String externalUserId;	
	private String currentFormStatus;	
	private List<Map<String, String>> multimediaAttachments;	
	private Map<String, String> details;	
	private long submissionTime;	
	private long clientVersion;
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getInstantId() {
		return instantId;
	}
	public void setInstantId(String instantId) {
		this.instantId = instantId;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getGOBHHID() {
		return GOBHHID;
	}
	public void setGOBHHID(String gOBHHID) {
		GOBHHID = gOBHHID;
	}
	public String getJivitahouseholdId() {
		return jivitaHouseholdId;
	}
	public void setJivitahouseholdId(String jivitaHouseholdId) {
		this.jivitaHouseholdId = jivitaHouseholdId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getUpazila() {
		return upazila;
	}
	public void setUpazila(String upazila) {
		this.upazila = upazila;
	}
	public String getUnion() {
		return union;
	}
	public void setUnion(String union) {
		this.union = union;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getSubunit() {
		return subunit;
	}
	public void setSubunit(String subunit) {
		this.subunit = subunit;
	}
	public String getMauzaPara() {
		return mauzaPara;
	}
	public void setMauzaPara(String mauzaPara) {
		this.mauzaPara = mauzaPara;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getExternalUserId() {
		return externalUserId;
	}
	public void setExternalUserId(String externalUserId) {
		this.externalUserId = externalUserId;
	}
	public String getCurrentFormStatus() {
		return currentFormStatus;
	}
	public void setCurrentFormStatus(String currentFormStatus) {
		this.currentFormStatus = currentFormStatus;
	}
	public List<Map<String, String>> getMultimediaAttachments() {
		return multimediaAttachments;
	}
	public void setMultimediaAttachments(
			List<Map<String, String>> multimediaAttachments) {
		this.multimediaAttachments = multimediaAttachments;
	}
	public Map<String, String> getDetails() {
		return details;
	}
	public void setDetails(Map<String, String> details) {
		this.details = details;
	}
	public long getSubmissionTime() {
		return submissionTime;
	}
	public void setSubmissionTime(long submissionTime) {
		this.submissionTime = submissionTime;
	}
	public long getClientVersion() {
		return clientVersion;
	}
	public void setClientVersion(long clientVersion) {
		this.clientVersion = clientVersion;
	}
	
	

}
