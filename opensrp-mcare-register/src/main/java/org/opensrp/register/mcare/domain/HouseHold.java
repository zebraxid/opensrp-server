package org.opensrp.register.mcare.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;
import org.motechproject.model.MotechBaseDataObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@TypeDiscriminator("doc.type === 'HouseHold'")
public class HouseHold extends MotechBaseDataObject {
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
	private String GOB_HHID; 
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
	private String HHID;
	@JsonProperty
	private String HoH_Reg_No;
	@JsonProperty
	private String GPS;
	@JsonProperty
	private String HoH_FName;
	@JsonProperty
	private String HoH_DoB;
	@JsonProperty
	private String HoH_Lname;
	@JsonProperty
	private String HoH_Age; 
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
	private List<Map<String, String>> MEMBERDETAILS;
	@JsonProperty
	private List<Map<String, String>> multimediaAttachments;
	@JsonProperty
	private Map<String, String> details;
	@JsonProperty
	private long SUBMISSIONDATE;
	
	public HouseHold() {
		this.MEMBERDETAILS = new ArrayList<>();
		this.multimediaAttachments = new ArrayList<>();
	}
	
	public HouseHold withCASEID(String caseId) {
		this.caseId = caseId;
		return this;
	}

	public HouseHold withINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}

	public HouseHold withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}

	public HouseHold withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}

	public HouseHold withTODAY(String TODAY) {
		this.TODAY = TODAY;
		return this;
	}

	public HouseHold withSTART(String START) {
		this.START = START;
		return this;
	}

	public HouseHold withEND(String END) {
		this.END = END;
		return this;
	}

	public HouseHold withDate_Of_Reg(String Date_Of_Reg) {
		this.Date_Of_Reg = Date_Of_Reg;
		return this;
	}

	public HouseHold withGOB_HHID(String GOB_HHID) {
		this.GOB_HHID = GOB_HHID;
		return this;
	}

	public HouseHold withHoH_HID(String HoH_HID) {
		this.HoH_HID = HoH_HID;
		return this;
	}

	public HouseHold withGPS(String GPS) {
		this.GPS = GPS;
		return this;
	}
	
	public HouseHold withHoH_FName(String HoH_FName) {
		this.HoH_FName = HoH_FName;
		return this;
	}

	public HouseHold withCOUNTRY(String COUNTRY) {
		this.COUNTRY = COUNTRY;
		return this;
	}

	public HouseHold withDIVISION(String DIVISION) {
		this.DIVISION = DIVISION;
		return this;
	}

	public HouseHold withDISTRICT(String DISTRICT) {
		this.DISTRICT = DISTRICT;
		return this;
	}

	public HouseHold withUPAZILLA(String UPAZILLA) {
		this.UPAZILLA = UPAZILLA;
		return this;
	}

	public HouseHold withUNION(String UNION) {
		this.UNION = UNION;
		return this;
	}

	public HouseHold withWARD(String WARD) {
		this.WARD = WARD;
		return this;
	}

	public HouseHold withHHID(String HHID) {
		this.HHID = HHID;
		return this;
	}
	
	public HouseHold withHoH_Reg_No(String HoH_Reg_No) {
		this.HoH_Reg_No = HoH_Reg_No;
		return this;
	}

	public HouseHold withHoH_DoB(String HoH_DoB) {
		this.HoH_DoB = HoH_DoB;
		return this;
	}

	
	public HouseHold withHoH_Lname(String HoH_Lname) {
		this.HoH_Lname = HoH_Lname;
		return this;
	}

	public HouseHold withHoH_Age(String HoH_Age) {
		this.HoH_Age = HoH_Age;
		return this;
	}

	public HouseHold withHoH_Unique_ID(String HoH_Unique_ID) {
		this.HoH_Unique_ID = HoH_Unique_ID;
		return this;
	}

	public HouseHold withHoH_Gender(String HoH_Gender) {
		this.HoH_Gender = HoH_Gender;
		return this;
	}
	
	public HouseHold withHoH_NID(String HoH_NID) {
		this.HoH_NID = HoH_NID;
		return this;
	}

	public HouseHold withHoH_BRID(String HoH_BRID) {
		this.HoH_BRID = HoH_BRID;
		return this;
	}
	
	public HouseHold withHoH_Mobile_No(String HoH_Mobile_No) {
		this.HoH_Mobile_No = HoH_Mobile_No;
		return this;
	}

	public HouseHold withHoH_Education(String HoH_Education) {
		this.HoH_Education = HoH_Education;
		return this;
	}
	
	public HouseHold withHoH_Occupation(String HoH_Occupation) {
		this.HoH_Occupation = HoH_Occupation;
		return this;
	}
	
	public HouseHold withHH_Member_No(String HH_Member_No) {
		this.HH_Member_No = HH_Member_No;
		return this;
	}
	
	public HouseHold withMEMBERDETAILS(List<Map<String, String>> MEMBERDETAILS) {
		this.MEMBERDETAILS = MEMBERDETAILS;
		return this;
	}
	
	public HouseHold withmultimediaAttachments(List<Map<String, String>> multimediaAttachments) {
		this.multimediaAttachments = multimediaAttachments;
		return this;
	}
	
	public HouseHold withDetails(Map<String, String> details) {
        this.details = new HashMap<>(details);
        return this;
    }

	public HouseHold withSUBMISSIONDATE(long SUBMISSIONDATE){
		this.SUBMISSIONDATE = SUBMISSIONDATE;
		return this;
	}
	public String caseId() {
		return caseId;
	}

	public String INSTANCEID() {
		return INSTANCEID;
	}

	public String PROVIDERID() {
		return PROVIDERID;
	}

	public String LOCATIONID() {
		return LOCATIONID;
	}

	public String TODAY() {
		return TODAY;
	}

	public String START() {
		return START;

	}

	public String END() {
		return END;
	}

	public String Date_Of_Reg() {
		return Date_Of_Reg;
	}

	public String GOB_HHID() {
		return GOB_HHID;
	}

	public String HoH_HID() {
		return HoH_HID;
	}

	public String GPS() {
		return GPS;
	}
	
	public String HoH_FName() {
		return HoH_FName;
	}

	public String COUNTRY() {
		return COUNTRY;
	}
	
	public String DIVISION() {
		return DIVISION;
	}
	
	public String DISTRICT() {
		return DISTRICT;
	}
	
	public String UPAZILLA() {
		return UPAZILLA;
	}
	
	public String UNION() {
		return UNION;
	}
	
	public String WARD() {
		return WARD;
	}
	
	public String HHID() {
		return HHID;
	}
	
	public String HoH_Reg_No() {
		return HoH_Reg_No;
	}

	public String HoH_DoB() {
		return HoH_DoB;
	}
	public String HoH_Lname() {
		return HoH_Lname;
	}

	public String HoH_Age() {
		return HoH_Age;
	}

	public String HoH_Unique_ID() {
		return HoH_Unique_ID;
	}

	public String HoH_Gender() {
		return HoH_Gender;
	}

	public String HoH_NID() {
		return HoH_NID;
	}

	public String HoH_BRID() {
		return HoH_BRID;
	}
	
	public String HoH_Mobile_No() {
		return HoH_Mobile_No;
	}

	public String HoH_Education() {
		return HoH_Education;
	}
	
	public long SUBMISSIONDATE(){
		return SUBMISSIONDATE;
	}
	
	public String HoH_Occupation() {
		return HoH_Occupation;
	}

	public String HH_Member_No() {
		return HH_Member_No;
	}
	
	public List<Map<String, String>> MEMBERDETAILS() {
		if (MEMBERDETAILS == null) {
			MEMBERDETAILS = new ArrayList<>();
		}
		return MEMBERDETAILS;
	}
	
	public List<Map<String, String>> multimediaAttachments() {
		if (multimediaAttachments == null) {
			multimediaAttachments = new ArrayList<>();
		}
		return multimediaAttachments;
	}

	private String getCaseId() {
		return caseId;
	}

	public Map<String, String> details() {
		if (details == null)
			this.details = new HashMap<>();
		return details;
	}

	public String getDetail(String name) {
		return details.get(name);
	}
	
	public String getELCODetail(String name) {	
		/*int size = MEMBERDETAILS.size();
		String elems = "";
		for (int i = 0; i < size; i++)
			elems = elems + MEMBERDETAILS.get(i).get(name) + " " ;
		return elems;	*/	

		return MEMBERDETAILS.get(0).get(name);
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