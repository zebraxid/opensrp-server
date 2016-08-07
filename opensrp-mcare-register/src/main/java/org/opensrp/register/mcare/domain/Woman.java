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
@TypeDiscriminator("doc.type === 'Woman'")
public class Woman extends MotechBaseDataObject {
	@JsonProperty
	private String caseId;
	@JsonProperty
	private String INSTANCEID;
	@JsonProperty
	private String PROVIDERID;
	@JsonProperty
	private String LOCATIONID;
	@JsonProperty
	private String HH_Member;
	@JsonProperty
	private String Reg_No;
	@JsonProperty
	private String BDH;
	@JsonProperty
	private String Member_Fname;
	@JsonProperty
	private String Member_LName;
	@JsonProperty
	private String Gender;
	@JsonProperty
	private String DoB;	
	@JsonProperty
	private String Type_DoB;
	@JsonProperty
	private String Age; 
	@JsonProperty
	private String Display_Age;
	@JsonProperty
	private String Child_Vital_Status;
	@JsonProperty
	private String MOTHER_Vaccine_Dates;
	@JsonProperty
	private String FWCWOMHUSSTR;
	@JsonProperty
	private String Date_of_BCG_OPV_0;
	@JsonProperty
	private String Date_of_OPV_Penta_PCV_1;
	@JsonProperty
	private String Date_of_OPV_Penta_PCV_2;
	@JsonProperty
	private String Date_of_OPV_Penta_3_IPV;
	@JsonProperty
	private String Date_of_PCV_3;
	@JsonProperty
	private String Date_of_MR;
	@JsonProperty
	private String Date_of_Measles;
	@JsonProperty
	private String Date_BRID;
	@JsonProperty
	private String Child_BRID;
	@JsonProperty
	private String Date_Child_Death; 
	@JsonProperty
	private String C_Guardian_Type;
	@JsonProperty
	private String C_Guardian_Name_Father;
	@JsonProperty
	private String C_Guardian_Name_Mother;
	@JsonProperty
	private String C_Guardian_Name_Hus;
	@JsonProperty
	private String Marital_Status;
	@JsonProperty
	private String Couple_No;
	@JsonProperty
	private String LMP;
	@JsonProperty
	private String EDD;
	@JsonProperty
	private String GA;
	@JsonProperty
	private String Pregnancy_Status; 
	@JsonProperty
	private String Date_of_MR_wom;
	@JsonProperty
	private String Date_of_TT1;
	@JsonProperty
	private String Date_of_TT2;
	@JsonProperty
	private String Date_of_TT3;
	@JsonProperty
	private String Date_of_TT4;
	@JsonProperty
	private String Date_of_TT5;
	@JsonProperty
	private String Unique_ID;
	@JsonProperty
	private String NID;
	@JsonProperty
	private String BRID;
	@JsonProperty
	private String HID;
	@JsonProperty
	private String Guardian_Type;
	@JsonProperty
	private String Guardian_Name_Father;
	@JsonProperty
	private String Guardian_Name_Mother;
	@JsonProperty
	private String Guardian_Name_Hus; 
	@JsonProperty
	private String Mobile_No;
	@JsonProperty
	private String Education;
	@JsonProperty
	private String Occupation;
	@JsonProperty
	private String Is_TT;
	@JsonProperty
	private String Is_Measles;
	@JsonProperty
	private String Is_FP;
	@JsonProperty
	private String Is_NewBorn;
	@JsonProperty
	private String Member_COUNTRY;
	@JsonProperty
	private String Member_DIVISION;
	@JsonProperty
	private String Member_DISTRICT;
	@JsonProperty
	private String Member_UPAZILLA;
	@JsonProperty
	private String Member_UNION;
	@JsonProperty
	private String Member_WARD;
	@JsonProperty
	private String Member_GOB_HHID;
	@JsonProperty
	private String Member_GPS;	
	@JsonProperty
	private String TODAY;
	@JsonProperty
	private String START;
	@JsonProperty
	private String END;
	@JsonProperty
	private String relationalid;
	@JsonProperty
	private Map<String, String> details;
	@JsonProperty
	private Map<String, String> TTVisitOne;
	@JsonProperty
	private Map<String, String> TTVisitTwo;
	@JsonProperty
	private Map<String, String> TTVisitThree;
	@JsonProperty
	private Map<String, String> TTVisitFour;
	@JsonProperty
	private Map<String, String> TTVisitFive;
	@JsonProperty
	private Map<String, String> MeaslesVisit;
	@JsonProperty
	private Map<String, String> familyPlanning;
	@JsonProperty
	private Map<String, String> general;
	@JsonProperty
	private Map<String, String> newBorn;
	@JsonProperty
	private Map<String, String> PCV1;
	@JsonProperty
	private Map<String, String> PCV2;
	@JsonProperty
	private Map<String, String> PCV3;
	@JsonProperty
	private Map<String, String> PENTA1;
	@JsonProperty
	private Map<String, String> PENTA2;
	@JsonProperty
	private Map<String, String> PENTA3;
	@JsonProperty
	private Map<String, String> OPV0;
	@JsonProperty
	private Map<String, String> OPV1;
	@JsonProperty
	private Map<String, String> OPV2;
	@JsonProperty
	private Map<String, String> OPV3;
	@JsonProperty
	private Map<String, String> MR;
	@JsonProperty
	private Map<String, String> Measles2;
	@JsonProperty
	private Map<String, String> IPV;
	@JsonProperty
	private Map<String, String> BCG;
	@JsonProperty
	private String isClosed;
	public Woman() {
		this.details = new HashMap<>();
		this.TTVisitOne = new HashMap<>(); 
		this.TTVisitTwo = new HashMap<>();
		this.TTVisitThree = new HashMap<>();
		this.TTVisitFour = new HashMap<>();
		this.TTVisitFive = new HashMap<>();
		this.familyPlanning = new HashMap<>();
		this.general = new HashMap<>();
		this.newBorn = new HashMap<>();
		this.general = new HashMap<>();
		this.general = new HashMap<>();
		this.general = new HashMap<>();
		this.general = new HashMap<>();
		this.general = new HashMap<>();
		this.general = new HashMap<>();
		this.general = new HashMap<>();
		this.general = new HashMap<>();
		this.general = new HashMap<>();
		this.general = new HashMap<>();
		this.general = new HashMap<>();
		this.general = new HashMap<>();
		this.general = new HashMap<>();
		this.general = new HashMap<>();
		this.setIsClosed(false);
	}
	
	public Woman withCaseId(String caseId) {
		this.caseId = caseId;
		return this;
	}

	public Woman withINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}

	public Woman withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}

	public Woman withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}

	public Woman withMember_Fname(String Member_Fname) {
		this.Member_Fname = Member_Fname;
		return this;
	}

	public Woman withHH_Member(String HH_Member) {
		this.HH_Member = HH_Member;
		return this;
	}
	public Woman withReg_No(String Reg_No) {
		this.Reg_No = Reg_No;
		return this;
	}
	public Woman withBDH(String BDH) {
		this.BDH = BDH;
		return this;
	}
	public Woman withMember_LName(String Member_LName) {
		this.Member_LName = Member_LName;
		return this;
	}
	public Woman withGender(String Gender) {
		this.Gender = Gender;
		return this;
	}
	public Woman withDoB(String DoB) {
		this.DoB = DoB;
		return this;
	}
	
	public Woman withType_DoB(String Type_DoB) {
		this.Type_DoB = Type_DoB;
		return this;
	}

	public Woman withAge(String Age) {
		this.Age = Age;
		return this;
	}

	public Woman withDisplay_Age(String Display_Age) {
		this.Display_Age = Display_Age;
		return this;
	}

	public Woman withDate_of_MR(String Date_of_MR) {
		this.Date_of_MR = Date_of_MR;
		return this;
	}
	
	public Woman withDate_of_Measles(String Date_of_Measles) {
		this.Date_of_Measles = Date_of_Measles;
		return this;
	}

	public Woman withChild_Vital_Status(String Child_Vital_Status) {
		this.Child_Vital_Status = Child_Vital_Status;
		return this;
	}

	public Woman withMOTHER_Vaccine_Dates(String MOTHER_Vaccine_Dates) {
		this.MOTHER_Vaccine_Dates = MOTHER_Vaccine_Dates;
		return this;
	}

	public Woman withFWCWOMHUSSTR(String FWCWOMHUSSTR) {
		this.FWCWOMHUSSTR = FWCWOMHUSSTR;
		return this;
	}

	public Woman withDate_of_BCG_OPV_0(String Date_of_BCG_OPV_0) {
		this.Date_of_BCG_OPV_0 = Date_of_BCG_OPV_0;
		return this;
	}

	public Woman withDate_of_OPV_Penta_PCV_1(String Date_of_OPV_Penta_PCV_1) {
		this.Date_of_OPV_Penta_PCV_1 = Date_of_OPV_Penta_PCV_1;
		return this;
	}

	public Woman withDate_of_OPV_Penta_PCV_2(String Date_of_OPV_Penta_PCV_2) {
		this.Date_of_OPV_Penta_PCV_2 = Date_of_OPV_Penta_PCV_2;
		return this;
	}

	public Woman withDate_of_OPV_Penta_3_IPV(String Date_of_OPV_Penta_3_IPV) {
		this.Date_of_OPV_Penta_3_IPV = Date_of_OPV_Penta_3_IPV;
		return this;
	}
	
	public Woman withDate_of_PCV_3(String Date_of_PCV_3) {
		this.Date_of_PCV_3 = Date_of_PCV_3;
		return this;
	}

	public Woman withDate_BRID(String Date_BRID) {
		this.Date_BRID = Date_BRID;
		return this;
	}
	
	public Woman withChild_BRID(String Child_BRID) {
		this.Child_BRID = Child_BRID;
		return this;
	}

	public Woman withDate_Child_Death(String Date_Child_Death) {
		this.Date_Child_Death = Date_Child_Death;
		return this;
	}

	public Woman withC_Guardian_Name_Father(String C_Guardian_Name_Father) {
		this.C_Guardian_Name_Father = C_Guardian_Name_Father;
		return this;
	}

	public Woman withC_Guardian_Type(String C_Guardian_Type) {
		this.C_Guardian_Type = C_Guardian_Type;
		return this;
	}
	
	public Woman withC_Guardian_Name_Mother(String C_Guardian_Name_Mother) {
		this.C_Guardian_Name_Mother = C_Guardian_Name_Mother;
		return this;
	}

	public Woman withC_Guardian_Name_Hus(String C_Guardian_Name_Hus) {
		this.C_Guardian_Name_Hus = C_Guardian_Name_Hus;
		return this;
	}
	
	public Woman withMarital_Status(String Marital_Status) {
		this.Marital_Status = Marital_Status;
		return this;
	}

	public Woman withCouple_No(String Couple_No) {
		this.Couple_No = Couple_No;
		return this;
	}
	
	public Woman withLMP(String LMP) {
		this.LMP = LMP;
		return this;
	}
	
	public Woman withEDD(String EDD) {
		this.EDD = EDD;
		return this;
	}
	
	public Woman withGA(String GA) {
		this.GA = GA;
		return this;
	}
	
	public Woman withPregnancy_Status(String Pregnancy_Status) {
		this.Pregnancy_Status = Pregnancy_Status;
		return this;
	}

	public Woman withDate_of_MR_wom(String Date_of_MR_wom) {
		this.Date_of_MR_wom = Date_of_MR_wom;
		return this;
	}

	public Woman withHID(String HID) {
		this.HID = HID;
		return this;
	}
	
	public Woman withGuardian_Type(String Guardian_Type) {
		this.Guardian_Type = Guardian_Type;
		return this;
	}

	public Woman withDate_of_TT1(String Date_of_TT1) {
		this.Date_of_TT1 = Date_of_TT1;
		return this;
	}

	public Woman withDate_of_TT2(String Date_of_TT2) {
		this.Date_of_TT2 = Date_of_TT2;
		return this;
	}

	public Woman withDate_of_TT3(String Date_of_TT3) {
		this.Date_of_TT3 = Date_of_TT3;
		return this;
	}

	public Woman withDate_of_TT4(String Date_of_TT4) {
		this.Date_of_TT4 = Date_of_TT4;
		return this;
	}

	public Woman withDate_of_TT5(String Date_of_TT5) {
		this.Date_of_TT5 = Date_of_TT5;
		return this;
	}

	public Woman withUnique_ID(String Unique_ID) {
		this.Unique_ID = Unique_ID;
		return this;
	}

	public Woman withNID(String NID) {
		this.NID = NID;
		return this;
	}
	
	public Woman withBRID(String BRID) {
		this.BRID = BRID;
		return this;
	}

	public Woman withGuardian_Name_Father(String Guardian_Name_Father) {
		this.Guardian_Name_Father = Guardian_Name_Father;
		return this;
	}

	
	public Woman withGuardian_Name_Mother(String Guardian_Name_Mother) {
		this.Guardian_Name_Mother = Guardian_Name_Mother;
		return this;
	}

	public Woman withGuardian_Name_Hus(String Guardian_Name_Hus) {
		this.Guardian_Name_Hus = Guardian_Name_Hus;
		return this;
	}

	public Woman withEducation(String Education) {
		this.Education = Education;
		return this;
	}

	public Woman withMobile_No(String Mobile_No) {
		this.Mobile_No = Mobile_No;
		return this;
	}
	
	public Woman withOccupation(String Occupation) {
		this.Occupation = Occupation;
		return this;
	}

	public Woman withIs_TT(String Is_TT) {
		this.Is_TT = Is_TT;
		return this;
	}
	
	public Woman withIs_Measles(String Is_Measles) {
		this.Is_Measles = Is_Measles;
		return this;
	}

	public Woman withIs_FP(String Is_FP) {
		this.Is_FP = Is_FP;
		return this;
	}
	
	public Woman withIs_NewBorn(String Is_NewBorn) {
		this.Is_NewBorn = Is_NewBorn;
		return this;
	}
	
	public Woman withMember_COUNTRY(String Member_COUNTRY) {
		this.Member_COUNTRY = Member_COUNTRY;
		return this;
	}
	
	public Woman withMember_DIVISION(String Member_DIVISION) {
		this.Member_DIVISION = Member_DIVISION;
		return this;
	}
	
	public Woman withMember_DISTRICT(String Member_DISTRICT) {
		this.Member_DISTRICT = Member_DISTRICT;
		return this;
	}
	
	public Woman withMember_UPAZILLA(String Member_UPAZILLA) {
		this.Member_UPAZILLA = Member_UPAZILLA;
		return this;
	}
	
	public Woman withMember_UNION(String Member_UNION) {
		this.Member_UNION = Member_UNION;
		return this;
	}
	
	public Woman withMember_WARD(String Member_WARD) {
		this.Member_WARD = Member_WARD;
		return this;
	}
	
	public Woman withMember_GOB_HHID(String Member_GOB_HHID) {
		this.Member_GOB_HHID = Member_GOB_HHID;
		return this;
	}
	
	public Woman withMember_GPS(String Member_GPS) {
		this.Member_GPS = Member_GPS;
		return this;
	}
	
	public Woman withTODAY(String TODAY) {
		this.TODAY = TODAY;
		return this;
	}

	public Woman withSTART(String START) {
		this.START = START;
		return this;
	}

	public Woman withEND(String END) {
		this.END = END;
		return this;
	}
	public Woman withRelationalid(String relationalid) {
		this.relationalid = relationalid;
		return this;
	}
	public Woman withDetails(Map<String, String> details) {
        this.details = new HashMap<>(details);
        return this;
    }
	public Woman withTTVisitOne(Map<String, String> TTVisitOne) {
        this.TTVisitOne = new HashMap<>(TTVisitOne);
        return this;
    }
	public Woman withTTVisitTwo(Map<String, String> TTVisitTwo) {
        this.TTVisitTwo = new HashMap<>(TTVisitTwo);
        return this;
    }
	public Woman withTTVisitThree(Map<String, String> TTVisitThree) {
        this.TTVisitThree = new HashMap<>(TTVisitThree);
        return this;
    }
	public Woman withTTVisitFour(Map<String, String> TTVisitFour) {
        this.TTVisitFour = new HashMap<>(TTVisitFour);
        return this;
    }
	public Woman withTTVisitFive(Map<String, String> TTVisitFive) {
        this.TTVisitFive = new HashMap<>(TTVisitFive);
        return this;
    }
	public Woman withMeaslesVisit(Map<String, String> MeaslesVisit) {
        this.MeaslesVisit = new HashMap<>(MeaslesVisit);
        return this;
    }
	public Woman withfamilyPlanning(Map<String, String> familyPlanning) {
        this.familyPlanning = new HashMap<>(familyPlanning);
        return this;
    }
	public Woman withgeneral(Map<String, String> general) {
        this.general = new HashMap<>(general);
        return this;
    }
	public Woman withnewBorn(Map<String, String> newBorn) {
        this.newBorn = new HashMap<>(newBorn);
        return this;
    }
	public Woman withPCV1(Map<String, String> PCV1) {
        this.PCV1 = new HashMap<>(PCV1);
        return this;
    }
	public Woman withPCV2(Map<String, String> PCV2) {
        this.PCV2 = new HashMap<>(PCV2);
        return this;
    }
	public Woman withPCV3(Map<String, String> PCV3) {
        this.PCV3 = new HashMap<>(PCV3);
        return this;
    }
	public Woman withPENTA1(Map<String, String> PENTA1) {
        this.PENTA1 = new HashMap<>(PENTA1);
        return this;
    }
	public Woman withPENTA2(Map<String, String> PENTA2) {
        this.PENTA2 = new HashMap<>(PENTA2);
        return this;
    }
	public Woman withPENTA3(Map<String, String> PENTA3) {
        this.PENTA3 = new HashMap<>(PENTA3);
        return this;
    }
	public Woman withOPV0(Map<String, String> OPV0) {
        this.OPV0 = new HashMap<>(OPV0);
        return this;
    }
	public Woman withOPV1(Map<String, String> OPV1) {
        this.OPV1 = new HashMap<>(OPV1);
        return this;
    }
	public Woman withOPV2(Map<String, String> OPV2) {
        this.OPV2 = new HashMap<>(OPV2);
        return this;
    }
	public Woman withOPV3(Map<String, String> OPV3) {
        this.OPV3 = new HashMap<>(OPV3);
        return this;
    }
	public Woman withMR(Map<String, String> MR) {
        this.MR = new HashMap<>(MR);
        return this;
    }
	public Woman withMeasles2(Map<String, String> Measles2) {
        this.Measles2 = new HashMap<>(Measles2);
        return this;
    }
	public Woman withIPV(Map<String, String> IPV) {
        this.IPV = new HashMap<>(IPV);
        return this;
    }
	public Woman withBCG(Map<String, String> BCG) {
        this.BCG = new HashMap<>(BCG);
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

	public String Member_Fname() {
		return Member_Fname;
	}
	public String HH_Member() {
		return HH_Member;
	}
	public String Reg_No() {
		return Reg_No;
	}
	public String BDH() {
		return BDH;
	}
	public String Member_LName() {
		return Member_LName;
	}
	public String Gender() {
		return Gender;
	}
	public String DoB() {
		return DoB;
	}

	public String Type_DoB() {
		return Type_DoB;
	}

	public String Age() {
		return Age;
	}

	public String Display_Age() {
		return Display_Age;
	}

	public String Date_of_MR() {
		return Date_of_MR;
	}
	
	public String Date_of_Measles() {
		return Date_of_Measles;
	}

	public String Child_Vital_Status() {
		return Child_Vital_Status;
	}
	
	public String MOTHER_Vaccine_Dates() {
		return MOTHER_Vaccine_Dates;
	}
	
	public String FWCWOMHUSSTR() {
		return FWCWOMHUSSTR;
	}
	
	public String Date_of_BCG_OPV_0() {
		return Date_of_BCG_OPV_0;
	}
	
	public String Date_of_OPV_Penta_PCV_1() {
		return Date_of_OPV_Penta_PCV_1;
	}
	
	public String Date_of_OPV_Penta_PCV_2() {
		return Date_of_OPV_Penta_PCV_2;
	}
	
	public String Date_of_OPV_Penta_3_IPV() {
		return Date_of_OPV_Penta_3_IPV;
	}
	
	public String Date_of_PCV_3() {
		return Date_of_PCV_3;
	}

	public String Date_BRID() {
		return Date_BRID;
	}
	public String Child_BRID() {
		return Child_BRID;
	}

	public String Date_Child_Death() {
		return Date_Child_Death;
	}

	public String C_Guardian_Name_Father() {
		return C_Guardian_Name_Father;
	}

	public String C_Guardian_Type() {
		return C_Guardian_Type;
	}

	public String C_Guardian_Name_Mother() {
		return C_Guardian_Name_Mother;
	}

	public String C_Guardian_Name_Hus() {
		return C_Guardian_Name_Hus;
	}
	
	public String Marital_Status() {
		return Marital_Status;
	}

	public String Couple_No() {
		return Couple_No;
	}
	
	public String LMP() {
		return LMP;
	}

	public String EDD() {
		return EDD;
	}
	
	public String GA() {
		return GA;
	}
	
	public String Pregnancy_Status() {
		return Pregnancy_Status;
	}

	public String Date_of_MR_wom() {
		return Date_of_MR_wom;
	}

	public String HID() {
		return HID;
	}
	
	public String Guardian_Type() {
		return Guardian_Type;
	}

	public String Date_of_TT1() {
		return Date_of_TT1;
	}
	
	public String Date_of_TT2() {
		return Date_of_TT2;
	}
	
	public String Date_of_TT3() {
		return Date_of_TT3;
	}
	
	public String Date_of_TT4() {
		return Date_of_TT4;
	}
	
	public String Date_of_TT5() {
		return Date_of_TT5;
	}
	
	public String Unique_ID() {
		return Unique_ID;
	}
	
	public String NID() {
		return NID;
	}
	
	public String BRID() {
		return BRID;
	}

	public String Guardian_Name_Father() {
		return Guardian_Name_Father;
	}
	
	public String Guardian_Name_Mother() {
		return Guardian_Name_Mother;
	}

	public String Guardian_Name_Hus() {
		return Guardian_Name_Hus;
	}

	public String Education() {
		return Education;
	}

	public String Mobile_No() {
		return Mobile_No;
	}

	public String Occupation() {
		return Occupation;
	}

	public String Is_TT() {
		return Is_TT;
	}
	
	public String Is_Measles() {
		return Is_Measles;
	}

	public String Is_FP() {
		return Is_FP;
	}
	
	public String Is_NewBorn() {
		return Is_NewBorn;
	}

	public String Member_COUNTRY() {
		return Member_COUNTRY;
	}
	
	public String Member_DIVISION() {
		return Member_DIVISION;
	}
	
	public String Member_DISTRICT() {
		return Member_DISTRICT;
	}
	
	public String Member_UPAZILLA() {
		return Member_UPAZILLA;
	}
	
	public String Member_UNION() {
		return Member_UNION;
	}
	
	public String Member_WARD() {
		return Member_WARD;
	}
	
	public String Member_GOB_HHID() {
		return Member_GOB_HHID;
	}
	
	public String Member_GPS() {
		return Member_GPS;
	}

	public String isClosed() {
		return isClosed;
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
	
	public String relationalid() {
		return relationalid;
	}

	private String getCaseId() {
		return caseId;
	}

	public String getRelationalid() {
		return relationalid;
	}

	public Map<String, String> details() {
		if (details == null)
			this.details = new HashMap<>();
		return details;
	}
	
	public String getDetail(String name) {
		return details.get(name);
	}
	
	public Map<String, String> TTVisitOne() {
		if (TTVisitOne == null)
			this.TTVisitOne = new HashMap<>();
		return TTVisitOne;
	}
	public Map<String, String> TTVisitTwo() {
		if (TTVisitTwo == null)
			this.TTVisitTwo = new HashMap<>();
		return TTVisitTwo;
	}
	public Map<String, String> TTVisitThree() {
		if (TTVisitThree == null)
			this.TTVisitThree = new HashMap<>();
		return TTVisitThree;
	}
	public Map<String, String> TTVisitFour() {
		if (TTVisitFour == null)
			this.TTVisitFour = new HashMap<>();
		return TTVisitFour;
	}
	public Map<String, String> TTVisitFive() {
		if (TTVisitFive == null)
			this.TTVisitFive = new HashMap<>();
		return TTVisitFive;
	}
	public Map<String, String> MeaslesVisit() {
		if (MeaslesVisit == null)
			this.MeaslesVisit = new HashMap<>();
		return MeaslesVisit;
	}
	public Map<String, String> familyPlanning() {
		if (familyPlanning == null)
			this.familyPlanning = new HashMap<>();
		return familyPlanning;
	}
	public Map<String, String> general() {
		if (general == null)
			this.general = new HashMap<>();
		return general;
	}
	public Map<String, String> newBorn() {
		if (newBorn == null)
			this.newBorn = new HashMap<>();
		return newBorn;
	}
	public Map<String, String> PCV1() {
		if (PCV1 == null)
			this.PCV1 = new HashMap<>();
		return PCV1;
	}
	public Map<String, String> PCV2() {
		if (PCV2 == null)
			this.PCV2 = new HashMap<>();
		return PCV2;
	}
	public Map<String, String> PCV3() {
		if (PCV3 == null)
			this.PCV3 = new HashMap<>();
		return PCV3;
	}
	public Map<String, String> PENTA1() {
		if (PENTA1 == null)
			this.PENTA1 = new HashMap<>();
		return PENTA1;
	}
	public Map<String, String> PENTA2() {
		if (PENTA2 == null)
			this.PENTA2 = new HashMap<>();
		return PENTA2;
	}
	public Map<String, String> PENTA3() {
		if (PENTA3 == null)
			this.PENTA3 = new HashMap<>();
		return PENTA3;
	}
	public Map<String, String> OPV0() {
		if (OPV0 == null)
			this.OPV0 = new HashMap<>();
		return OPV0;
	}
	public Map<String, String> OPV1() {
		if (OPV1 == null)
			this.OPV1 = new HashMap<>();
		return OPV1;
	}
	public Map<String, String> OPV2() {
		if (OPV2 == null)
			this.OPV2 = new HashMap<>();
		return OPV2;
	}
	public Map<String, String> OPV3() {
		if (OPV3 == null)
			this.OPV3 = new HashMap<>();
		return OPV3;
	}
	public Map<String, String> MR() {
		if (MR == null)
			this.MR = new HashMap<>();
		return MR;
	}
	public Map<String, String> Measles2() {
		if (Measles2 == null)
			this.Measles2 = new HashMap<>();
		return Measles2;
	}
	public Map<String, String> IPV() {
		if (IPV == null)
			this.IPV = new HashMap<>();
		return IPV;
	}
	public Map<String, String> BCG() {
		if (BCG == null)
			this.BCG = new HashMap<>();
		return BCG;
	}
    public Woman setIsClosed(boolean isClosed) {
        this.isClosed = Boolean.toString(isClosed);
        return this;
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
