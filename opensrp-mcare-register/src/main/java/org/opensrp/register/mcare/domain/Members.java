/**
 * @author Asifur
 */

package org.opensrp.register.mcare.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@TypeDiscriminator("doc.type === 'Members'")
public class Members extends MotechBaseDataObject {
	@JsonProperty
	private String 
	caseId;
	@JsonProperty
	private String 
	INSTANCEID
	;
	@JsonProperty
	private String 
	PROVIDERID
	;
	@JsonProperty
	private String 
	LOCATIONID
	; 
	@JsonProperty 
	private String Today
	; 
	@JsonProperty 
	private String Start
	; 
	@JsonProperty 
	private String End
	;
	@JsonProperty 
	private String 
	relationalid
	; 
	@JsonProperty 
	private String 
	Member_GoB_HHID
	; 
	@JsonProperty 
	private String 
	Member_Reg_Date
	; 
	@JsonProperty 
	private String 
	Mem_F_Name
	; 
	@JsonProperty 
	private String 
	Mem_L_Name
	; 
	@JsonProperty 
	private String 
	Member_Birth_Date_Known
	; 
	@JsonProperty 
	private String 
	Member_Birth_Date
	; 
	@JsonProperty 
	private String 
	Member_Age
	; 
	@JsonProperty 
	private String 
	Calc_Age
	; 
	@JsonProperty 
	private String 
	Calc_Dob
	; 
	@JsonProperty 
	private String 
	Calc_Dob_Confirm
	; 
	@JsonProperty 
	private String 
	Calc_Age_Confirm
	; 
	@JsonProperty 
	private String 
	Birth_Date_Note
	; 
	@JsonProperty 
	private String 
	Note_age
	; 
	@JsonProperty 
	private String 
	Member_Gender
	; 
	@JsonProperty 
	private String 
	Mem_ID_Type
	; 
	@JsonProperty 
	private String 
	Mem_NID
	; 
	@JsonProperty 
	private String 
	Retype_Mem_NID
	; 
	@JsonProperty 
	private String 
	Mem_NID_Concept
	; 
	@JsonProperty 
	private String 
	Mem_BRID
	; 
	@JsonProperty 
	private String 
	Retype_Mem_BRID
	; 
	@JsonProperty 
	private String 
	Mem_BRID_Concept
	; 
	@JsonProperty 
	private String 
	Mem_Mobile_Number
	; 
	@JsonProperty 
	private String 
	Mem_Marital_Status
	; 
	@JsonProperty 
	private String 
	Couple_No
	; 
	@JsonProperty 
	private String 
	Spouse_Name
	; 
	@JsonProperty 
	private String 
	Wom_Menstruating
	; 
	@JsonProperty 
	private String 
	Wom_Sterilized
	; 
	@JsonProperty 
	private String 
	Wom_Hus_Live
	; 
	@JsonProperty 
	private String 
	Wom_Hus_Alive
	; 
	@JsonProperty 
	private String 
	Wom_Hus_Sterilized
	; 
	@JsonProperty 
	private String 
	Eligible
	; 
	@JsonProperty 
	private String 
	Eligible2
	; 
	@JsonProperty 
	private String 
	ELCO
	; 
	@JsonProperty 
	private String 
	ELCO_Note
	; 
	@JsonProperty 
	private String 
	Mem_Country
	; 
	@JsonProperty 
	private String 
	Mem_Division
	; 
	@JsonProperty 
	private String 
	Mem_District
	; 
	@JsonProperty 
	private String 
	Mem_Upazilla
	; 
	@JsonProperty 
	private String 
	Mem_Union
	; 
	@JsonProperty 
	private String 
	Mem_Ward
	; 
	@JsonProperty 
	private String 
	Mem_Subunit
	; 
	@JsonProperty 
	private String 
	Mem_Mauzapara
	; 
	@JsonProperty 
	private String 
	Mem_Village_Name
	; 
	@JsonProperty 
	private String 
	Mem_GPS
	; 
	@JsonProperty 
	private String 
	ELCO_ID_Type
	; 
	@JsonProperty 
	private String 
	ELCO_NID
	; 
	@JsonProperty 
	private String 
	ELCO_NID_Concept
	; 
	@JsonProperty 
	private String 
	ELCO_BRID
	; 
	@JsonProperty 
	private String 
	ELCO_BRID_Concept
	; 
	@JsonProperty 
	private String 
	ELCO_Mobile_Number
	; 
	@JsonProperty 
	private String 
	Member_Detail
	; 
	@JsonProperty 
	private String 
	Permanent_Address
	; 
	@JsonProperty 
	private String 
	Updated_Dist
	; 
	@JsonProperty 
	private String 
	Updated_Union
	; 
	@JsonProperty 
	private String 
	Updated_Vill
	; 
	@JsonProperty 
	private String 
	Final_Dist
	; 
	@JsonProperty 
	private String 
	Final_Union
	; 
	@JsonProperty 
	private String 
	Final_Vill
	; 
	@JsonProperty 
	private String 
	Relation_HoH
	; 
	@JsonProperty 
	private String 
	Place_Of_Birth
	; 
	@JsonProperty 
	private String 
	Education
	; 
	@JsonProperty 
	private String 
	Religion
	; 
	@JsonProperty 
	private String 
	BD_Citizen
	; 
	@JsonProperty 
	private String 
	Occupation
	; 
	@JsonProperty 
	private String 
	add_member
	; 
	@JsonProperty
	private Map<String, String> details;
	@JsonProperty
	private Map<String, String> TTVisit;
	@JsonProperty
	private Map<String, String> generalVisit;
	@JsonProperty
	private List<Map<String, String>> child_vaccine;
	@JsonProperty
	private Map<String, String> BNFVisit;
	@JsonProperty
	private String isClosed;
	public Members() {
				this.details = new HashMap<>();
				this.TTVisit = new HashMap<>();
				this.generalVisit = new HashMap<>();
				this.BNFVisit = new HashMap<>();
				this.child_vaccine = new ArrayList<>();
				this.setIsClosed(false);
	}
	
	public Members setCaseId(String caseId) {
				this.caseId = caseId;
		return this;
	}

	public Members setINSTANCEID(String INSTANCEID) {
				this.INSTANCEID = INSTANCEID;
		return this;
	}

	public Members setPROVIDERID(String PROVIDERID) {
				this.PROVIDERID = PROVIDERID;
		return this;
	}

	public Members setLOCATIONID(String LOCATIONID) {
				this.LOCATIONID = LOCATIONID;
		return this;
	}
	
	public Members setDetails(Map<String, String> details) {
        this.details = new HashMap<>(details);
        return this;
    }
	public Members setgeneralVisit(Map<String, String> generalVisit) {
        this.generalVisit = new HashMap<>(generalVisit);
        return this;
    }
    public Members setTTVisit(Map<String, String> TTVisit) {
        this.TTVisit = new HashMap<>(TTVisit);
        return this;
    }
    public Members setBNFVisit(Map<String, String> BNFVisit) {
        this.BNFVisit = new HashMap<>(BNFVisit);
        return this;
    }
    public Members setchild_vaccine(List<Map<String, String>> child_vaccine) {
				this.child_vaccine = child_vaccine;
		return this;
	}
    
    public Members setIsClosed(boolean isClosed) {
        this.isClosed = Boolean.toString(isClosed);
        return this;
    }
    
    public Members setRelationalid(String relationalid) {
			this.relationalid = relationalid;
			return this;
	}

	public Members setMember_GoB_HHID(String member_GoB_HHID) {
		this.Member_GoB_HHID = member_GoB_HHID;
		return this;
	}

	public Members setMember_Reg_Date(String member_Reg_Date) {
		this.Member_Reg_Date = member_Reg_Date;
		return this;
	}

	public Members setMem_F_Name(String mem_F_Name) {
		this.Mem_F_Name = mem_F_Name;
		return this;
	}

	public Members setMem_L_Name(String mem_L_Name) {
		this.Mem_L_Name = mem_L_Name;
		return this;
	}

	public Members setMember_Birth_Date_Known(String member_Birth_Date_Known) {
		this.Member_Birth_Date_Known = member_Birth_Date_Known;
		return this;
	}

	public Members setMember_Birth_Date(String member_Birth_Date) {
		this.Member_Birth_Date = member_Birth_Date;
		return this;
	}

	public Members setMember_Age(String member_Age) {
		this.Member_Age = member_Age;
		return this;
	}

	public Members setCalc_Age(String calc_Age) {
		this.Calc_Age = calc_Age;
		return this;
	}

	public Members setCalc_Dob(String calc_Dob) {
		this.Calc_Dob = calc_Dob;
		return this;
	}

	public Members setCalc_Dob_Confirm(String calc_Dob_Confirm) {
		this.Calc_Dob_Confirm = calc_Dob_Confirm;
		return this;
	}

	public Members setCalc_Age_Confirm(String calc_Age_Confirm) {
		this.Calc_Age_Confirm = calc_Age_Confirm;
		return this;
	}

	public Members setBirth_Date_Note(String birth_Date_Note) {
		this.Birth_Date_Note = birth_Date_Note;
		return this;
	}

	public Members setNote_age(String note_age) {
		this.Note_age = note_age;
		return this;
	}

	public Members setMember_Gender(String member_Gender) {
		this.Member_Gender = member_Gender;
		return this;
	}

	public Members setMem_ID_Type(String mem_ID_Type) {
		this.Mem_ID_Type = mem_ID_Type;
		return this;
	}

	public Members setMem_NID(String mem_NID) {
		this.Mem_NID = mem_NID;
		return this;
	}

	public Members setRetype_Mem_NID(String retype_Mem_NID) {
		this.Retype_Mem_NID = retype_Mem_NID;
		return this;
	}

	public Members setMem_NID_Concept(String mem_NID_Concept) {
		this.Mem_NID_Concept = mem_NID_Concept;
		return this;
	}

	public Members setMem_BRID(String mem_BRID) {
		this.Mem_BRID = mem_BRID;
		return this;
	}

	public Members setRetype_Mem_BRID(String retype_Mem_BRID) {
		this.Retype_Mem_BRID = retype_Mem_BRID;
		return this;
	}

	public Members setMem_BRID_Concept(String mem_BRID_Concept) {
		this.Mem_BRID_Concept = mem_BRID_Concept;
		return this;
	}

	public Members setMem_Mobile_Number(String mem_Mobile_Number) {
		this.Mem_Mobile_Number = mem_Mobile_Number;
		return this;
	}

	public Members setMem_Marital_Status(String mem_Marital_Status) {
		this.Mem_Marital_Status = mem_Marital_Status;
		return this;
	}

	public Members setCouple_No(String couple_No) {
		this.Couple_No = couple_No;
		return this;
	}

	public Members setSpouse_Name(String spouse_Name) {
		this.Spouse_Name = spouse_Name;
		return this;
	}

	public Members setWom_Menstruating(String wom_Menstruating) {
		this.Wom_Menstruating = wom_Menstruating;
		return this;
	}

	public Members setWom_Sterilized(String wom_Sterilized) {
		this.Wom_Sterilized = wom_Sterilized;
		return this;
	}

	public Members setWom_Hus_Live(String wom_Hus_Live) {
		this.Wom_Hus_Live = wom_Hus_Live;
		return this;
	}

	public Members setWom_Hus_Alive(String wom_Hus_Alive) {
		this.Wom_Hus_Alive = wom_Hus_Alive;
		return this;
	}

	public Members setWom_Hus_Sterilized(String wom_Hus_Sterilized) {
		this.Wom_Hus_Sterilized = wom_Hus_Sterilized;
		return this;
	}

	public Members setEligible(String eligible) {
		this.Eligible = eligible;
		return this;
	}

	public Members setEligible2(String eligible2) {
		this.Eligible2 = eligible2;
		return this;
	}

	public Members setELCO(String eLCO) {
		this.ELCO = eLCO;
		return this;
	}

	public Members setELCO_Note(String eLCO_Note) {
		this.ELCO_Note = eLCO_Note;
		return this;
	}

	public Members setMem_Country(String mem_Country) {
		this.Mem_Country = mem_Country;
		return this;
	}

	public Members setMem_Division(String mem_Division) {
		this.Mem_Division = mem_Division;
		return this;
	}

	public Members setMem_District(String mem_District) {
		this.Mem_District = mem_District;
		return this;
	}

	public Members setMem_Upazilla(String mem_Upazilla) {
		this.Mem_Upazilla = mem_Upazilla;
		return this;
	}

	public Members setMem_Union(String mem_Union) {
		this.Mem_Union = mem_Union;
		return this;
	}

	public Members setMem_Ward(String mem_Ward) {
		this.Mem_Ward = mem_Ward;
		return this;
	}

	public Members setMem_Subunit(String mem_Subunit) {
		this.Mem_Subunit = mem_Subunit;
		return this;
	}

	public Members setMem_Mauzapara(String mem_Mauzapara) {
		this.Mem_Mauzapara = mem_Mauzapara;
		return this;
	}

	public Members setMem_Village_Name(String mem_Village_Name) {
		this.Mem_Village_Name = mem_Village_Name;
		return this;
	}

	public Members setMem_GPS(String mem_GPS) {
		this.Mem_GPS = mem_GPS;
		return this;
	}

	public Members setELCO_ID_Type(String eLCO_ID_Type) {
		this.ELCO_ID_Type = eLCO_ID_Type;
		return this;
	}

	public Members setELCO_NID(String eLCO_NID) {
		this.ELCO_NID = eLCO_NID;
		return this;
	}

	public Members setELCO_NID_Concept(String eLCO_NID_Concept) {
		this.ELCO_NID_Concept = eLCO_NID_Concept;
		return this;
	}

	public Members setELCO_BRID(String eLCO_BRID) {
		this.ELCO_BRID = eLCO_BRID;
		return this;
	}

	public Members setELCO_BRID_Concept(String eLCO_BRID_Concept) {
		this.ELCO_BRID_Concept = eLCO_BRID_Concept;
		return this;
	}

	public Members setELCO_Mobile_Number(String eLCO_Mobile_Number) {
		this.ELCO_Mobile_Number = eLCO_Mobile_Number;
		return this;
	}

	public Members setMember_Detail(String member_Detail) {
		this.Member_Detail = member_Detail;
		return this;
	}

	public Members setPermanent_Address(String permanent_Address) {
		this.Permanent_Address = permanent_Address;
		return this;
	}

	public Members setUpdated_Dist(String updated_Dist) {
		this.Updated_Dist = updated_Dist;
		return this;
	}

	public Members setUpdated_Union(String updated_Union) {
		this.Updated_Union = updated_Union;
		return this;
	}

	public Members setUpdated_Vill(String updated_Vill) {
		this.Updated_Vill = updated_Vill;
		return this;
	}

	public Members setFinal_Dist(String final_Dist) {
		this.Final_Dist = final_Dist;
		return this;
	}

	public Members setFinal_Union(String final_Union) {
		this.Final_Union = final_Union;
		return this;
	}

	public Members setFinal_Vill(String final_Vill) {
		this.Final_Vill = final_Vill;
		return this;
	}

	public Members setRelation_HoH(String relation_HoH) {
		this.Relation_HoH = relation_HoH;
		return this;
	}

	public Members setPlace_Of_Birth(String place_Of_Birth) {
		this.Place_Of_Birth = place_Of_Birth;
		return this;
	}

	public Members setEducation(String education) {
		this.Education = education;
		return this;
	}

	public Members setReligion(String religion) {
		this.Religion = religion;
		return this;
	}

	public Members setBD_Citizen(String bD_Citizen) {
		this.BD_Citizen = bD_Citizen;
		return this;
	}

	public Members setOccupation(String occupation) {
		this.Occupation = occupation;
		return this;
	}

	public Members setAdd_member(String add_member) {
		this.add_member = add_member;
		return this;
	}
	
	public Members setToday(String today) {
		this.Today = today;
		return this;
	}

	public Members setStart(String start) {
		this.Start = start;
		return this;
	}

	public Members setEnd(String end) {
		this.End = end;
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
	
	public Map<String, String> details() {
		if (details == null)
			this.details = new HashMap<>();
		return details;
	}
	
	public String Detail(String name) {
		return details.get(name);
	}

	public Map<String, String> generalVisit() {
		if (generalVisit == null)
			this.generalVisit = new HashMap<>();
		return generalVisit;
	}
	public Map<String, String> TTVisit() {
		if (TTVisit == null)
			this.TTVisit = new HashMap<>();
		return TTVisit;
	}
	public Map<String, String> BNFVisit() {
		if (BNFVisit == null)
			this.BNFVisit = new HashMap<>();
		return BNFVisit;
	}
	public List<Map<String, String>> child_vaccine() {
		if (child_vaccine == null) {
			child_vaccine = new ArrayList<>();
		}
		return child_vaccine;
	}

	public String getRelationalid() {
		return relationalid;
	}

	public String getMember_GoB_HHID() {
		return Member_GoB_HHID;
	}

	public String getMember_Reg_Date() {
		return Member_Reg_Date;
	}

	public String getMem_F_Name() {
		return Mem_F_Name;
	}

	public String getMem_L_Name() {
		return Mem_L_Name;
	}

	public String getMember_Birth_Date_Known() {
		return Member_Birth_Date_Known;
	}

	public String getMember_Birth_Date() {
		return Member_Birth_Date;
	}

	public String getMember_Age() {
		return Member_Age;
	}

	public String getCalc_Age() {
		return Calc_Age;
	}

	public String getCalc_Dob() {
		return Calc_Dob;
	}

	public String getCalc_Dob_Confirm() {
		return Calc_Dob_Confirm;
	}

	public String getCalc_Age_Confirm() {
		return Calc_Age_Confirm;
	}

	public String getBirth_Date_Note() {
		return Birth_Date_Note;
	}

	public String getNote_age() {
		return Note_age;
	}

	public String getMember_Gender() {
		return Member_Gender;
	}

	public String getMem_ID_Type() {
		return Mem_ID_Type;
	}

	public String getMem_NID() {
		return Mem_NID;
	}

	public String getRetype_Mem_NID() {
		return Retype_Mem_NID;
	}

	public String getMem_NID_Concept() {
		return Mem_NID_Concept;
	}

	public String getMem_BRID() {
		return Mem_BRID;
	}

	public String getRetype_Mem_BRID() {
		return Retype_Mem_BRID;
	}

	public String getMem_BRID_Concept() {
		return Mem_BRID_Concept;
	}

	public String getMem_Mobile_Number() {
		return Mem_Mobile_Number;
	}

	public String getMem_Marital_Status() {
		return Mem_Marital_Status;
	}

	public String getCouple_No() {
		return Couple_No;
	}

	public String getSpouse_Name() {
		return Spouse_Name;
	}

	public String getWom_Menstruating() {
		return Wom_Menstruating;
	}

	public String getWom_Sterilized() {
		return Wom_Sterilized;
	}

	public String getWom_Hus_Live() {
		return Wom_Hus_Live;
	}

	public String getWom_Hus_Alive() {
		return Wom_Hus_Alive;
	}

	public String getWom_Hus_Sterilized() {
		return Wom_Hus_Sterilized;
	}

	public String getEligible() {
		return Eligible;
	}

	public String getEligible2() {
		return Eligible2;
	}

	public String getELCO() {
		return ELCO;
	}

	public String getELCO_Note() {
		return ELCO_Note;
	}

	public String getMem_Country() {
		return Mem_Country;
	}

	public String getMem_Division() {
		return Mem_Division;
	}

	public String getMem_District() {
		return Mem_District;
	}

	public String getMem_Upazilla() {
		return Mem_Upazilla;
	}

	public String getMem_Union() {
		return Mem_Union;
	}

	public String getMem_Ward() {
		return Mem_Ward;
	}

	public String getMem_Subunit() {
		return Mem_Subunit;
	}

	public String getMem_Mauzapara() {
		return Mem_Mauzapara;
	}

	public String getMem_Village_Name() {
		return Mem_Village_Name;
	}

	public String getMem_GPS() {
		return Mem_GPS;
	}

	public String getELCO_ID_Type() {
		return ELCO_ID_Type;
	}

	public String getELCO_NID() {
		return ELCO_NID;
	}

	public String getELCO_NID_Concept() {
		return ELCO_NID_Concept;
	}

	public String getELCO_BRID() {
		return ELCO_BRID;
	}

	public String getELCO_BRID_Concept() {
		return ELCO_BRID_Concept;
	}

	public String getELCO_Mobile_Number() {
		return ELCO_Mobile_Number;
	}

	public String getMember_Detail() {
		return Member_Detail;
	}

	public String getPermanent_Address() {
		return Permanent_Address;
	}

	public String getUpdated_Dist() {
		return Updated_Dist;
	}

	public String getUpdated_Union() {
		return Updated_Union;
	}

	public String getUpdated_Vill() {
		return Updated_Vill;
	}

	public String getFinal_Dist() {
		return Final_Dist;
	}

	public String getFinal_Union() {
		return Final_Union;
	}

	public String getFinal_Vill() {
		return Final_Vill;
	}

	public String getRelation_HoH() {
		return Relation_HoH;
	}

	public String getPlace_Of_Birth() {
		return Place_Of_Birth;
	}

	public String getEducation() {
		return Education;
	}

	public String getReligion() {
		return Religion;
	}

	public String getBD_Citizen() {
		return BD_Citizen;
	}

	public String getOccupation() {
		return Occupation;
	}

	public String getAdd_member() {
		return add_member;
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
