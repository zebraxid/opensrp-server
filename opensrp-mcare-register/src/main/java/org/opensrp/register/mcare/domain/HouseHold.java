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
	private String Version; 
	@JsonProperty 
	private String Changes; 
	@JsonProperty 
	private String existing_location; 
	@JsonProperty 
	private String existing_Country; 
	@JsonProperty 
	private String existing_Division; 
	@JsonProperty 
	private String existing_District; 
	@JsonProperty 
	private String existing_Upazilla; 
	@JsonProperty 
	private String existing_Union; 
	@JsonProperty 
	private String existing_Ward; 
	@JsonProperty 
	private String existing_Subunit; 
	@JsonProperty 
	private String existing_Mauzapara; 
	@JsonProperty 
	private String Form_Name; 
	@JsonProperty 
	private String Today; 
	@JsonProperty 
	private String Start; 
	@JsonProperty 
	private String End; 
	@JsonProperty 
	private String Reg_Date; 
	@JsonProperty 
	private String Village_Name; 
	@JsonProperty 
	private String GoB_HHID; 
	@JsonProperty 
	private String No_Of_Couples; 
	@JsonProperty 
	private String Country; 
	@JsonProperty 
	private String Division; 
	@JsonProperty 
	private String District; 
	@JsonProperty 
	private String Upazilla; 
	@JsonProperty 
	private String Union; 
	@JsonProperty 
	private String Ward; 
	@JsonProperty 
	private String Subunit; 
	@JsonProperty 
	private String Mauzapara; 
	@JsonProperty 
	private String GPS; 
	@JsonProperty 
	private String HoH_F_Name; 
	@JsonProperty 
	private String HoH_L_Name; 
	@JsonProperty 
	private String HoH_Birth_Date; 
	@JsonProperty 
	private String HoH_Gender; 
	@JsonProperty 
	private String HoH_Birth_Date_Known; 
	@JsonProperty 
	private String HoH_Age; 
	@JsonProperty 
	private String Calc_HoH_Age; 
	@JsonProperty 
	private String Calc_HoH_Dob; 
	@JsonProperty 
	private String Calc_HoH_Dob_Confirm; 
	@JsonProperty 
	private String Calc_HoH_Age_Confirm; 
	@JsonProperty 
	private String HoH_Birth_Date_Note; 
	@JsonProperty 
	private String HoH_Note_age; 
	@JsonProperty 
	private String HoH_ID_Type; 
	@JsonProperty 
	private String HoH_NID; 
	@JsonProperty 
	private String Retype_HoH_NID; 
	@JsonProperty 
	private String HoH_NID_Concept; 
	@JsonProperty 
	private String HoH_BRID; 
	@JsonProperty 
	private String Retype_HoH_BRID; 
	@JsonProperty 
	private String HoH_BRID_Concept; 
	@JsonProperty 
	private String HoH_Mobile_number; 
	@JsonProperty 
	private String Member_Number; 
	@JsonProperty 
	private String MWRA; 
	@JsonProperty 
	private String Final_ELCO; 
	@JsonProperty 
	private String Child; 
	@JsonProperty 
	private String Adolescent; 
	@JsonProperty 
	private String HH_Status;
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
	@JsonProperty
	private long serverVersion;
	@JsonProperty
    private long clientVersion;
	@JsonProperty
    private long timeStamp;


	public HouseHold() {
		this.MEMBERDETAILS = new ArrayList<>();
		this.multimediaAttachments = new ArrayList<>();
		this.Birth_Outcome = new ArrayList<>();
	}

    public long getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(long serverVersion) {
        this.serverVersion = serverVersion;
    }

    public long getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(long clientVersion) {
        this.clientVersion = clientVersion;
    }

    public long getUpdatedTimeStamp() {
        return timeStamp;
    }

    public void setUpdatedTimeStamp(long updatedTimeStamp) {
        this.timeStamp = updatedTimeStamp;
    }

    public HouseHold setCASEID(String caseId) {
		this.caseId = caseId;
		return this;
	}

	public HouseHold setBAHMNIID(String BAHMNI_ID){
		this.BAHMNI_ID = BAHMNI_ID;
		return this;
	}
	public HouseHold setINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}

	public HouseHold setPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}

	public HouseHold setLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}

	public HouseHold setVersion(String version) {
		this.Version = version;
	return this;
	}

	public HouseHold setChanges(String changes) {
		this.Changes = changes;
	return this;
	}

	public HouseHold setExisting_location(String existing_location) {
		this.existing_location = existing_location;
	return this;
	}

	public HouseHold setExisting_Country(String existing_Country) {
		this.existing_Country = existing_Country;
	return this;
	}

	public HouseHold setExisting_Division(String existing_Division) {
		this.existing_Division = existing_Division;
	return this;
	}

	public HouseHold setExisting_District(String existing_District) {
		this.existing_District = existing_District;
	return this;
	}

	public HouseHold setExisting_Upazilla(String existing_Upazilla) {
		this.existing_Upazilla = existing_Upazilla;
	return this;
	}

	public HouseHold setExisting_Union(String existing_Union) {
		this.existing_Union = existing_Union;
	return this;
	}

	public HouseHold setExisting_Ward(String existing_Ward) {
		this.existing_Ward = existing_Ward;
	return this;
	}

	public HouseHold setExisting_Subunit(String existing_Subunit) {
		this.existing_Subunit = existing_Subunit;
	return this;
	}

	public HouseHold setExisting_Mauzapara(String existing_Mauzapara) {
		this.existing_Mauzapara = existing_Mauzapara;
	return this;
	}

	public HouseHold setForm_Name(String form_Name) {
		this.Form_Name = form_Name;
	return this;
	}

	public HouseHold setToday(String today) {
		this.Today = today;
	return this;
	}

	public HouseHold setStart(String start) {
		this.Start = start;
	return this;
	}

	public HouseHold setEnd(String end) {
		this.End = end;
	return this;
	}

	public HouseHold setReg_Date(String reg_Date) {
		this.Reg_Date = reg_Date;
	return this;
	}

	public HouseHold setVillage_Name(String village_Name) {
		this.Village_Name = village_Name;
	return this;
	}

	public HouseHold setGoB_HHID(String goB_HHID) {
		this.GoB_HHID = goB_HHID;
	return this;
	}

	public HouseHold setNo_Of_Couples(String no_Of_Couples) {
		this.No_Of_Couples = no_Of_Couples;
	return this;
	}

	public HouseHold setCountry(String country) {
		this.Country = country;
	return this;
	}

	public HouseHold setDivision(String division) {
		this.Division = division;
	return this;
	}

	public HouseHold setDistrict(String district) {
		this.District = district;
	return this;
	}

	public HouseHold setUpazilla(String upazilla) {
		this.Upazilla = upazilla;
	return this;
	}

	public HouseHold setUnion(String union) {
		this.Union = union;
	return this;
	}

	public HouseHold setWard(String ward) {
		this.Ward = ward;
	return this;
	}

	public HouseHold setSubunit(String subunit) {
		this.Subunit = subunit;
	return this;
	}

	public HouseHold setMauzapara(String mauzapara) {
		this.Mauzapara = mauzapara;
	return this;
	}

	public HouseHold setGPS(String gPS) {
		this.GPS = gPS;
	return this;
	}

	public HouseHold setHoH_F_Name(String hoH_F_Name) {
		this.HoH_F_Name = hoH_F_Name;
	return this;
	}

	public HouseHold setHoH_L_Name(String hoH_L_Name) {
		this.HoH_L_Name = hoH_L_Name;
	return this;
	}

	public HouseHold setHoH_Birth_Date(String hoH_Birth_Date) {
		this.HoH_Birth_Date = hoH_Birth_Date;
	return this;
	}

	public HouseHold setHoH_Gender(String hoH_Gender) {
		this.HoH_Gender = hoH_Gender;
	return this;
	}

	public HouseHold setHoH_Birth_Date_Known(String hoH_Birth_Date_Known) {
		this.HoH_Birth_Date_Known = hoH_Birth_Date_Known;
	return this;
	}

	public HouseHold setHoH_Age(String hoH_Age) {
		this.HoH_Age = hoH_Age;
	return this;
	}

	public HouseHold setCalc_HoH_Age(String calc_HoH_Age) {
		this.Calc_HoH_Age = calc_HoH_Age;
	return this;
	}

	public HouseHold setCalc_HoH_Dob(String calc_HoH_Dob) {
		this.Calc_HoH_Dob = calc_HoH_Dob;
	return this;
	}

	public HouseHold setCalc_HoH_Dob_Confirm(String calc_HoH_Dob_Confirm) {
		this.Calc_HoH_Dob_Confirm = calc_HoH_Dob_Confirm;
	return this;
	}

	public HouseHold setCalc_HoH_Age_Confirm(String calc_HoH_Age_Confirm) {
		this.Calc_HoH_Age_Confirm = calc_HoH_Age_Confirm;
	return this;
	}

	public HouseHold setHoH_Birth_Date_Note(String hoH_Birth_Date_Note) {
		this.HoH_Birth_Date_Note = hoH_Birth_Date_Note;
	return this;
	}

	public HouseHold setHoH_Note_age(String hoH_Note_age) {
		this.HoH_Note_age = hoH_Note_age;
	return this;
	}

	public HouseHold setHoH_ID_Type(String hoH_ID_Type) {
		this.HoH_ID_Type = hoH_ID_Type;
	return this;
	}

	public HouseHold setHoH_NID(String hoH_NID) {
		this.HoH_NID = hoH_NID;
	return this;
	}

	public HouseHold setRetype_HoH_NID(String retype_HoH_NID) {
		this.Retype_HoH_NID = retype_HoH_NID;
	return this;
	}

	public HouseHold setHoH_NID_Concept(String hoH_NID_Concept) {
		this.HoH_NID_Concept = hoH_NID_Concept;
	return this;
	}

	public HouseHold setHoH_BRID(String hoH_BRID) {
		this.HoH_BRID = hoH_BRID;
	return this;
	}

	public HouseHold setRetype_HoH_BRID(String retype_HoH_BRID) {
		this.Retype_HoH_BRID = retype_HoH_BRID;
	return this;
	}

	public HouseHold setHoH_BRID_Concept(String hoH_BRID_Concept) {
		this.HoH_BRID_Concept = hoH_BRID_Concept;
	return this;
	}

	public HouseHold setHoH_Mobile_number(String hoH_Mobile_number) {
		this.HoH_Mobile_number = hoH_Mobile_number;
	return this;
	}

	public HouseHold setMember_Number(String member_Number) {
		this.Member_Number = member_Number;
	return this;
	}

	public HouseHold setMWRA(String mWRA) {
		this.MWRA = mWRA;
	return this;
	}

	public HouseHold setFinal_ELCO(String final_ELCO) {
		this.Final_ELCO = final_ELCO;
	return this;
	}

	public HouseHold setChild(String child) {
		this.Child = child;
	return this;
	}

	public HouseHold setAdolescent(String adolescent) {
		this.Adolescent = adolescent;
	return this;
	}

	public HouseHold setHH_Status(String hH_Status) {
		this.HH_Status = hH_Status;
	return this;
	}	
	
	public HouseHold setMEMBERDETAILS(List<Map<String, String>> MEMBERDETAILS) {
		this.MEMBERDETAILS = MEMBERDETAILS;
		return this;
	}
	
	public HouseHold setmultimediaAttachments(List<Map<String, String>> multimediaAttachments) {
		this.multimediaAttachments = multimediaAttachments;
		return this;
	}
	
	public HouseHold setDetails(Map<String, String> details) {
        this.details = new HashMap<>(details);
        return this;
    }
	
    public HouseHold setBirth_Outcome(List<Map<String, String>> Birth_Outcome) {
        this.Birth_Outcome = Birth_Outcome;
        return this;
    }

    public String caseId() {
		return caseId;
	}
	
	private String getCaseId() {
		return caseId;
	}
	
	public String BAHMNIID(){
		return BAHMNI_ID;
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

	public String getVersion() {
		return Version;
	}

	public String getChanges() {
		return Changes;
	}

	public String getExisting_location() {
		return existing_location;
	}

	public String getExisting_Country() {
		return existing_Country;
	}

	public String getExisting_Division() {
		return existing_Division;
	}

	public String getExisting_District() {
		return existing_District;
	}

	public String getExisting_Upazilla() {
		return existing_Upazilla;
	}

	public String getExisting_Union() {
		return existing_Union;
	}

	public String getExisting_Ward() {
		return existing_Ward;
	}

	public String getExisting_Subunit() {
		return existing_Subunit;
	}

	public String getExisting_Mauzapara() {
		return existing_Mauzapara;
	}

	public String getForm_Name() {
		return Form_Name;
	}

	public String getToday() {
		return Today;
	}

	public String getStart() {
		return Start;
	}

	public String getEnd() {
		return End;
	}

	public String getReg_Date() {
		return Reg_Date;
	}

	public String getVillage_Name() {
		return Village_Name;
	}

	public String getGoB_HHID() {
		return GoB_HHID;
	}

	public String getNo_Of_Couples() {
		return No_Of_Couples;
	}

	public String getCountry() {
		return Country;
	}

	public String getDivision() {
		return Division;
	}

	public String getDistrict() {
		return District;
	}

	public String getUpazilla() {
		return Upazilla;
	}

	public String getUnion() {
		return Union;
	}

	public String getWard() {
		return Ward;
	}

	public String getSubunit() {
		return Subunit;
	}

	public String getMauzapara() {
		return Mauzapara;
	}

	public String getGPS() {
		return GPS;
	}

	public String getHoH_F_Name() {
		return HoH_F_Name;
	}

	public String getHoH_L_Name() {
		return HoH_L_Name;
	}

	public String getHoH_Birth_Date() {
		return HoH_Birth_Date;
	}

	public String getHoH_Gender() {
		return HoH_Gender;
	}

	public String getHoH_Birth_Date_Known() {
		return HoH_Birth_Date_Known;
	}

	public String getHoH_Age() {
		return HoH_Age;
	}

	public String getCalc_HoH_Age() {
		return Calc_HoH_Age;
	}

	public String getCalc_HoH_Dob() {
		return Calc_HoH_Dob;
	}

	public String getCalc_HoH_Dob_Confirm() {
		return Calc_HoH_Dob_Confirm;
	}

	public String getCalc_HoH_Age_Confirm() {
		return Calc_HoH_Age_Confirm;
	}

	public String getHoH_Birth_Date_Note() {
		return HoH_Birth_Date_Note;
	}

	public String getHoH_Note_age() {
		return HoH_Note_age;
	}

	public String getHoH_ID_Type() {
		return HoH_ID_Type;
	}

	public String getHoH_NID() {
		return HoH_NID;
	}

	public String getRetype_HoH_NID() {
		return Retype_HoH_NID;
	}

	public String getHoH_NID_Concept() {
		return HoH_NID_Concept;
	}

	public String getHoH_BRID() {
		return HoH_BRID;
	}

	public String getRetype_HoH_BRID() {
		return Retype_HoH_BRID;
	}

	public String getHoH_BRID_Concept() {
		return HoH_BRID_Concept;
	}

	public String getHoH_Mobile_number() {
		return HoH_Mobile_number;
	}

	public String getMember_Number() {
		return Member_Number;
	}

	public String getMWRA() {
		return MWRA;
	}

	public String getFinal_ELCO() {
		return Final_ELCO;
	}

	public String getChild() {
		return Child;
	}

	public String getAdolescent() {
		return Adolescent;
	}

	public String getHH_Status() {
		return HH_Status;
	}	
	
	public List<Map<String, String>> Birth_Outcome() {
		if (Birth_Outcome == null)
			this.Birth_Outcome = new ArrayList<>();
		return Birth_Outcome;
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

	public Map<String, String> details() {
		if (details == null)
			this.details = new HashMap<>();
		return details;
	}

	public String Detail(String name) {
		return details.get(name);
	}
	
	public String getMEMBERDetail(String name) {	
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