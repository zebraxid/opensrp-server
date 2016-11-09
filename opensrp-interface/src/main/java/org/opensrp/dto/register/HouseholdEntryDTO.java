package org.opensrp.dto.register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;
public class HouseholdEntryDTO {
	@JsonProperty
	private String caseId;
	@JsonProperty
	private String INSTANCEID;
	@JsonProperty
	private String PROVIDERID;
	@JsonProperty
	private String LOCATIONID;
	@JsonProperty
	private String TODAY;
	@JsonProperty
	private String START;
	@JsonProperty
	private String END;
	@JsonProperty
	private String Date_Of_Reg;
	@JsonProperty
	private String HoH_HID;
	@JsonProperty
	private String COUNTRY;
	@JsonProperty
	private String DIVISION;
	@JsonProperty
	private String DISTRICT;
	@JsonProperty
	private String UPAZILLA;
	@JsonProperty
	private String UNION;
	@JsonProperty
	private String WARD;
	@JsonProperty
	private String BLOCK;
	@JsonProperty
	private String Block_no;
	@JsonProperty
	private String HHID;
	@JsonProperty
	private String HoH_Reg_No;
	@JsonProperty
	private String GPS;
	@JsonProperty
	private String HoH_Fname;
	@JsonProperty
	private String HoH_birth_date;
	@JsonProperty
	private String HoH_birth_date_known;
	@JsonProperty
	private String HoH_Lname;
	@JsonProperty
	private String HoH_age; 
	@JsonProperty
	private String HoH_Gender;
	@JsonProperty
	private String HoH_Unique_ID;
	@JsonProperty
	private String HoH_NID;
	@JsonProperty
	private String HoH_BRID;
	@JsonProperty
	private String HoH_Mobile_No;
	@JsonProperty
	private String HoH_Education;
	@JsonProperty
	private String HoH_Occupation;
	@JsonProperty
	private String HH_Member_No;
	@JsonProperty
	private String deviceid;
	@JsonProperty
	private String subscriberid;
	@JsonProperty
	private String simserial;
	@JsonProperty
	private String phonenumber;
	@JsonProperty
	private String PAURASAVA;
	@JsonProperty
	private String ADDRESS_LINE;
	@JsonProperty
	private String HIE_FACILITIES; 
	@JsonProperty
	private String calc_HoH_age;
	@JsonProperty
	private String calc_HoH_dob;
	@JsonProperty
	private String calc_HoH_dob_confirm;
	@JsonProperty
	private String calc_dob_estimated;
	@JsonProperty
	private String calc_HoH_age_confirm;
	@JsonProperty
	private String birth_date_note;
	@JsonProperty
	private String note_age;
	@JsonProperty
	private String HH_current_form_status;
	@JsonProperty
	private List<Map<String, String>> Birth_Outcome;
	@JsonProperty
	private List<Map<String, String>> MEMBERDETAILS;
	@JsonProperty
	private List<Map<String, String>> multimediaAttachments;
	@JsonProperty
	private Map<String, String> details;
	@JsonProperty
	private String BAHMNI_ID;

	public HouseholdEntryDTO() {
		this.MEMBERDETAILS = new ArrayList<>();
		this.multimediaAttachments = new ArrayList<>();
	}
	
	
	
    public String getCaseId() {
    	return caseId;
    }


	
    public String getINSTANCEID() {
    	return INSTANCEID;
    }


	
    public String getPROVIDERID() {
    	return PROVIDERID;
    }


	
    public String getLOCATIONID() {
    	return LOCATIONID;
    }


	
    public String getTODAY() {
    	return TODAY;
    }


	
    public String getSTART() {
    	return START;
    }


	
    public String getEND() {
    	return END;
    }


	
    public String getDate_Of_Reg() {
    	return Date_Of_Reg;
    }


	
    public String getHoH_HID() {
    	return HoH_HID;
    }


	
    public String getCOUNTRY() {
    	return COUNTRY;
    }


	
    public String getDIVISION() {
    	return DIVISION;
    }


	
    public String getDISTRICT() {
    	return DISTRICT;
    }


	
    public String getUPAZILLA() {
    	return UPAZILLA;
    }


	
    public String getUNION() {
    	return UNION;
    }


	
    public String getWARD() {
    	return WARD;
    }


	
    public String getBLOCK() {
    	return BLOCK;
    }


	
    public String getBlock_no() {
    	return Block_no;
    }


	
    public String getHHID() {
    	return HHID;
    }


	
    public String getHoH_Reg_No() {
    	return HoH_Reg_No;
    }


	
    public String getGPS() {
    	return GPS;
    }


	
    public String getHoH_Fname() {
    	return HoH_Fname;
    }


	
    public String getHoH_birth_date() {
    	return HoH_birth_date;
    }


	
    public String getHoH_birth_date_known() {
    	return HoH_birth_date_known;
    }


	
    public String getHoH_Lname() {
    	return HoH_Lname;
    }


	
    public String getHoH_age() {
    	return HoH_age;
    }


	
    public String getHoH_Gender() {
    	return HoH_Gender;
    }


	
    public String getHoH_Unique_ID() {
    	return HoH_Unique_ID;
    }


	
    public String getHoH_NID() {
    	return HoH_NID;
    }


	
    public String getHoH_BRID() {
    	return HoH_BRID;
    }


	
    public String getHoH_Mobile_No() {
    	return HoH_Mobile_No;
    }


	
    public String getHoH_Education() {
    	return HoH_Education;
    }


	
    public String getHoH_Occupation() {
    	return HoH_Occupation;
    }


	
    public String getHH_Member_No() {
    	return HH_Member_No;
    }


	
    public String getDeviceid() {
    	return deviceid;
    }


	
    public String getSubscriberid() {
    	return subscriberid;
    }


	
    public String getSimserial() {
    	return simserial;
    }


	
    public String getPhonenumber() {
    	return phonenumber;
    }


	
    public String getPAURASAVA() {
    	return PAURASAVA;
    }


	
    public String getADDRESS_LINE() {
    	return ADDRESS_LINE;
    }


	
    public String getHIE_FACILITIES() {
    	return HIE_FACILITIES;
    }


	
    public String getCalc_HoH_age() {
    	return calc_HoH_age;
    }


	
    public String getCalc_HoH_dob() {
    	return calc_HoH_dob;
    }


	
    public String getCalc_HoH_dob_confirm() {
    	return calc_HoH_dob_confirm;
    }


	
    public String getCalc_dob_estimated() {
    	return calc_dob_estimated;
    }


	
    public String getCalc_HoH_age_confirm() {
    	return calc_HoH_age_confirm;
    }


	
    public String getBirth_date_note() {
    	return birth_date_note;
    }


	
    public String getNote_age() {
    	return note_age;
    }


	
    public String getHH_current_form_status() {
    	return HH_current_form_status;
    }


	
    public List<Map<String, String>> getBirth_Outcome() {
    	return Birth_Outcome;
    }


	
    public List<Map<String, String>> getMEMBERDETAILS() {
    	return MEMBERDETAILS;
    }


	
    public List<Map<String, String>> getMultimediaAttachments() {
    	return multimediaAttachments;
    }


	
    public Map<String, String> getDetails() {
    	return details;
    }


	
    public String getBAHMNI_ID() {
    	return BAHMNI_ID;
    }


	
    public void setCaseId(String caseId) {
    	this.caseId = caseId;
    }


	
    public void setINSTANCEID(String iNSTANCEID) {
    	INSTANCEID = iNSTANCEID;
    }


	
    public void setPROVIDERID(String pROVIDERID) {
    	PROVIDERID = pROVIDERID;
    }


	
    public void setLOCATIONID(String lOCATIONID) {
    	LOCATIONID = lOCATIONID;
    }


	
    public void setTODAY(String tODAY) {
    	TODAY = tODAY;
    }


	
    public void setSTART(String sTART) {
    	START = sTART;
    }


	
    public void setEND(String eND) {
    	END = eND;
    }


	
    public void setDate_Of_Reg(String date_Of_Reg) {
    	Date_Of_Reg = date_Of_Reg;
    }


	
    public void setHoH_HID(String hoH_HID) {
    	HoH_HID = hoH_HID;
    }


	
    public void setCOUNTRY(String cOUNTRY) {
    	COUNTRY = cOUNTRY;
    }


	
    public void setDIVISION(String dIVISION) {
    	DIVISION = dIVISION;
    }


	
    public void setDISTRICT(String dISTRICT) {
    	DISTRICT = dISTRICT;
    }


	
    public void setUPAZILLA(String uPAZILLA) {
    	UPAZILLA = uPAZILLA;
    }


	
    public void setUNION(String uNION) {
    	UNION = uNION;
    }


	
    public void setWARD(String wARD) {
    	WARD = wARD;
    }


	
    public void setBLOCK(String bLOCK) {
    	BLOCK = bLOCK;
    }


	
    public void setBlock_no(String block_no) {
    	Block_no = block_no;
    }


	
    public void setHHID(String hHID) {
    	HHID = hHID;
    }


	
    public void setHoH_Reg_No(String hoH_Reg_No) {
    	HoH_Reg_No = hoH_Reg_No;
    }


	
    public void setGPS(String gPS) {
    	GPS = gPS;
    }


	
    public void setHoH_Fname(String hoH_Fname) {
    	HoH_Fname = hoH_Fname;
    }


	
    public void setHoH_birth_date(String hoH_birth_date) {
    	HoH_birth_date = hoH_birth_date;
    }


	
    public void setHoH_birth_date_known(String hoH_birth_date_known) {
    	HoH_birth_date_known = hoH_birth_date_known;
    }


	
    public void setHoH_Lname(String hoH_Lname) {
    	HoH_Lname = hoH_Lname;
    }


	
    public void setHoH_age(String hoH_age) {
    	HoH_age = hoH_age;
    }


	
    public void setHoH_Gender(String hoH_Gender) {
    	HoH_Gender = hoH_Gender;
    }


	
    public void setHoH_Unique_ID(String hoH_Unique_ID) {
    	HoH_Unique_ID = hoH_Unique_ID;
    }


	
    public void setHoH_NID(String hoH_NID) {
    	HoH_NID = hoH_NID;
    }


	
    public void setHoH_BRID(String hoH_BRID) {
    	HoH_BRID = hoH_BRID;
    }


	
    public void setHoH_Mobile_No(String hoH_Mobile_No) {
    	HoH_Mobile_No = hoH_Mobile_No;
    }


	
    public void setHoH_Education(String hoH_Education) {
    	HoH_Education = hoH_Education;
    }


	
    public void setHoH_Occupation(String hoH_Occupation) {
    	HoH_Occupation = hoH_Occupation;
    }


	
    public void setHH_Member_No(String hH_Member_No) {
    	HH_Member_No = hH_Member_No;
    }


	
    public void setDeviceid(String deviceid) {
    	this.deviceid = deviceid;
    }


	
    public void setSubscriberid(String subscriberid) {
    	this.subscriberid = subscriberid;
    }


	
    public void setSimserial(String simserial) {
    	this.simserial = simserial;
    }


	
    public void setPhonenumber(String phonenumber) {
    	this.phonenumber = phonenumber;
    }


	
    public void setPAURASAVA(String pAURASAVA) {
    	PAURASAVA = pAURASAVA;
    }


	
    public void setADDRESS_LINE(String aDDRESS_LINE) {
    	ADDRESS_LINE = aDDRESS_LINE;
    }


	
    public void setHIE_FACILITIES(String hIE_FACILITIES) {
    	HIE_FACILITIES = hIE_FACILITIES;
    }


	
    public void setCalc_HoH_age(String calc_HoH_age) {
    	this.calc_HoH_age = calc_HoH_age;
    }


	
    public void setCalc_HoH_dob(String calc_HoH_dob) {
    	this.calc_HoH_dob = calc_HoH_dob;
    }


	
    public void setCalc_HoH_dob_confirm(String calc_HoH_dob_confirm) {
    	this.calc_HoH_dob_confirm = calc_HoH_dob_confirm;
    }


	
    public void setCalc_dob_estimated(String calc_dob_estimated) {
    	this.calc_dob_estimated = calc_dob_estimated;
    }


	
    public void setCalc_HoH_age_confirm(String calc_HoH_age_confirm) {
    	this.calc_HoH_age_confirm = calc_HoH_age_confirm;
    }


	
    public void setBirth_date_note(String birth_date_note) {
    	this.birth_date_note = birth_date_note;
    }


	
    public void setNote_age(String note_age) {
    	this.note_age = note_age;
    }


	
    public void setHH_current_form_status(String hH_current_form_status) {
    	HH_current_form_status = hH_current_form_status;
    }


	
    public void setBirth_Outcome(List<Map<String, String>> birth_Outcome) {
    	Birth_Outcome = birth_Outcome;
    }


	
    public void setMEMBERDETAILS(List<Map<String, String>> mEMBERDETAILS) {
    	MEMBERDETAILS = mEMBERDETAILS;
    }


	
    public void setMultimediaAttachments(List<Map<String, String>> multimediaAttachments) {
    	this.multimediaAttachments = multimediaAttachments;
    }


	
    public void setDetails(Map<String, String> details) {
    	this.details = details;
    }


	
    public void setBAHMNI_ID(String bAHMNI_ID) {
    	BAHMNI_ID = bAHMNI_ID;
    }


	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o, "id", "revision");
	}
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "id", "revision");
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}