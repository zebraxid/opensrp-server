package org.opensrp.register.mcare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.opensrp.dto.register.ANC_RegisterEntryDTO;

public class ANCRegisterEntry {

	private String caseId;
	
	private String INSTANCEID;
	
	private String PROVIDERID;
	
	private String LOCATIONID;
	
	private String anc1_current_formStatus;

	private String ANC2_current_formStatus;

	private String ANC3_current_formStatus;

	private String ANC4_current_formStatus;
	
	//private String current_formStatus;
	
	private String GOBHHID;
	
	private String JiVitAHHID;
	
	private String FWWOMFNAME;
	
	private String FWHUSNAME;
	
	private String FWWOMNID;
	
	private String FWWOMBID;
	
	private String FWWOMAGE;
	
	private String TODAY;
	
	private String FWPSRLMP;
	
	private String START;
	
	private String END;
	
	private String relationalid;
	
    private String isClosed;
	
	private Map<String, String> details;
	
	private Map<String, String> ancVisitOne;
	
	private Map<String, String> ancVisitTwo;
	
	private Map<String, String> ancVisitThree;
	
	private Map<String, String> ancVisitFour;
	
	private List<Map<String, String>> bnfVisitDetails;
	
	public ANCRegisterEntry() {

		this.details = new HashMap<>();
		this.ancVisitOne = new HashMap<>();
		this.ancVisitTwo = new HashMap<>();
		this.ancVisitThree = new HashMap<>();
		this.ancVisitFour = new HashMap<>();
		this.setIsClosed(false);
	}
	
	public ANCRegisterEntry withCaseId(String caseId) {
		this.caseId = caseId;
		return this;
	}

	public ANCRegisterEntry withINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}
	
	public ANCRegisterEntry withanc1_current_formStatus(String anc1_current_formStatus) {
		this.anc1_current_formStatus = anc1_current_formStatus;
		return this;
	}

	public ANCRegisterEntry withANC2_current_formStatus(String ANC2_current_formStatus) {
		this.ANC2_current_formStatus = ANC2_current_formStatus;
		return this;
	}

	public ANCRegisterEntry withANC3_current_formStatus(String ANC3_current_formStatus) {
		this.ANC3_current_formStatus = ANC3_current_formStatus;
		return this;
	}

	public ANCRegisterEntry withANC4_current_formStatus(String ANC4_current_formStatus) {
		this.ANC4_current_formStatus = ANC4_current_formStatus;
		return this;
	}
	
	/*public ANCRegisterEntry withcurrent_formStatus(String current_formStatus) {
		this.current_formStatus = current_formStatus;
		return this;
	}*/

	public ANCRegisterEntry withFWHUSNAME(String FWHUSNAME) {
		this.FWHUSNAME = FWHUSNAME;
		return this;
	}
	
	public ANCRegisterEntry withGOBHHID(String GOBHHID) {
		this.GOBHHID = GOBHHID;
		return this;
	}
	
	public ANCRegisterEntry withJiVitAHHID(String JiVitAHHID) {
		this.JiVitAHHID = JiVitAHHID;
		return this;
	}
	public ANCRegisterEntry withFWWOMFNAME(String FWWOMFNAME) {
		this.FWWOMFNAME = FWWOMFNAME;
		return this;
	}
	public ANCRegisterEntry withFWWOMNID(String FWWOMNID) {
		this.FWWOMNID = FWWOMNID;
		return this;
	}
	public ANCRegisterEntry withFWWOMBID(String FWWOMBID) {
		this.FWWOMBID = FWWOMBID;
		return this;
	}
	public ANCRegisterEntry withFWWOMAGE(String FWWOMAGE) {
		this.FWWOMAGE = FWWOMAGE;
		return this;
	}
	public ANCRegisterEntry withIsClosed(String isClosed) {
		this.isClosed = isClosed;
		return this;
	}

	public ANCRegisterEntry withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}

	public ANCRegisterEntry withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}

	public ANCRegisterEntry withTODAY(String TODAY) {
		this.TODAY = TODAY;
		return this;
	}

	public ANCRegisterEntry withFWPSRLMP(String FWPSRLMP) {
		this.FWPSRLMP = FWPSRLMP;
		return this;
	}

	public ANCRegisterEntry withSTART(String START) {
		this.START = START;
		return this;
	}

	public ANCRegisterEntry withEND(String END) {
		this.END = END;
		return this;
	}
	public ANCRegisterEntry withRelationalid(String relationalid) {
		this.relationalid = relationalid;
		return this;
	}

	public ANCRegisterEntry withANCVisitOne(Map<String, String> ancVisitOne) {
        this.ancVisitOne = new HashMap<>(ancVisitOne);
        return this;
    }
	public ANCRegisterEntry withANCVisitTwo(Map<String, String> ancVisitTwo) {
        this.ancVisitTwo = new HashMap<>(ancVisitTwo);
        return this;
    }
	public ANCRegisterEntry withANCVisitThree(Map<String, String> ancVisitThree) {
        this.ancVisitThree = new HashMap<>(ancVisitThree);
        return this;
    }
	public ANCRegisterEntry withANCVisitFour(Map<String, String> ancVisitFour) {
        this.ancVisitFour = new HashMap<>(ancVisitFour);
        return this;
    }
	public ANCRegisterEntry withBNFVisitDetails(List<Map<String, String>> bnfVisitDetails) {
        this.bnfVisitDetails = bnfVisitDetails;
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
	
	public String anc1_current_formStatus() {
		return anc1_current_formStatus;
	}

	public String ANC2_current_formStatus() {
		return ANC2_current_formStatus;
	}

	public String ANC3_current_formStatus() {
		return ANC3_current_formStatus;
	}

	public String ANC4_current_formStatus() {
		return ANC4_current_formStatus;
	}
	
	/*public String current_formStatus() {
		return current_formStatus;
	}*/

	public String FWHUSNAME() {
		return FWHUSNAME;
	}
	
	public String GOBHHID() {
		return GOBHHID;
	}
	public String JiVitAHHID() {
		return JiVitAHHID;
	}
	public String FWWOMFNAME() {
		return FWWOMFNAME;
	}
	public String FWWOMNID() {
		return FWWOMNID;
	}
	public String FWWOMBID() {
		return FWWOMBID;
	}
	public String FWWOMAGE() {
		return FWWOMAGE;
	}
	public String isClosed() {
		return isClosed;
	}
	
	public String TODAY() {
		return TODAY;
	}
	public String FWPSRLMP() {
		return FWPSRLMP;
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
		return details;
	}
	public String getDetail(String name) {
		return details.get(name);
	}
	
	public Map<String, String> ancVisitOne() {
		return ancVisitOne;
	}
	public Map<String, String> ancVisitTwo() {
		return ancVisitTwo;
	}
	public Map<String, String> ancVisitThree() {
		return ancVisitThree;
	}
	public Map<String, String> ancVisitFour() {
		return ancVisitFour;
	}
	public List<Map<String, String>> bnfVisitDetails() {
		if (bnfVisitDetails == null) {
			bnfVisitDetails = new ArrayList<>();
		}
		return bnfVisitDetails;
	}
    public ANCRegisterEntry setIsClosed(boolean isClosed) {
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
