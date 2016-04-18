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
	private String caseId;
	@JsonProperty
	private String INSTANCEID;
	@JsonProperty
	private String PROVIDERID;
	@JsonProperty
	private String LOCATIONID;
	@JsonProperty
	private String GOBHHID;
	@JsonProperty
	private String JiVitAHHID;
	@JsonProperty
	private String FWWOMFNAME;
	@JsonProperty
	private String FWHUSNAME;
	@JsonProperty
	private String FWWOMNID;
	@JsonProperty
	private String FWWOMBID;
	@JsonProperty
	private String FWWOMAGE;
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
	private Map<String, String> withMeaslesVisit;
	@JsonProperty
	private Map<String, String> familyPlanning;
	@JsonProperty
	private Map<String, String> general;
	@JsonProperty
	private Map<String, String> newBorn;
	@JsonProperty
	private String isClosed;
	public Members() {
		this.details = new HashMap<>();
		this.TTVisitOne = new HashMap<>(); 
		this.TTVisitTwo = new HashMap<>();
		this.TTVisitThree = new HashMap<>();
		this.TTVisitFour = new HashMap<>();
		this.TTVisitFive = new HashMap<>();
		this.familyPlanning = new HashMap<>();
		this.general = new HashMap<>();
		this.newBorn = new HashMap<>();
		this.setIsClosed(false);
	}
	
	public Members withCaseId(String caseId) {
		this.caseId = caseId;
		return this;
	}

	public Members withINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}

	public Members withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}

	public Members withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}

	public Members withFWHUSNAME(String FWHUSNAME) {
		this.FWHUSNAME = FWHUSNAME;
		return this;
	}

	public Members withGOBHHID(String GOBHHID) {
		this.GOBHHID = GOBHHID;
		return this;
	}
	public Members withJiVitAHHID(String JiVitAHHID) {
		this.JiVitAHHID = JiVitAHHID;
		return this;
	}
	public Members withJFWWOMFNAME(String FWWOMFNAME) {
		this.FWWOMFNAME = FWWOMFNAME;
		return this;
	}
	public Members withFWWOMNID(String FWWOMNID) {
		this.FWWOMNID = FWWOMNID;
		return this;
	}
	public Members withFWWOMBID(String FWWOMBID) {
		this.FWWOMBID = FWWOMBID;
		return this;
	}
	public Members withFWWOMAGE(String FWWOMAGE) {
		this.FWWOMAGE = FWWOMAGE;
		return this;
	}
	public Members withTODAY(String TODAY) {
		this.TODAY = TODAY;
		return this;
	}

	public Members withSTART(String START) {
		this.START = START;
		return this;
	}

	public Members withEND(String END) {
		this.END = END;
		return this;
	}
	public Members withRelationalid(String relationalid) {
		this.relationalid = relationalid;
		return this;
	}
	public Members withDetails(Map<String, String> details) {
        this.details = new HashMap<>(details);
        return this;
    }
	public Members withTTVisitOne(Map<String, String> TTVisitOne) {
        this.TTVisitOne = new HashMap<>(TTVisitOne);
        return this;
    }
	public Members withTTVisitTwo(Map<String, String> TTVisitTwo) {
        this.TTVisitTwo = new HashMap<>(TTVisitTwo);
        return this;
    }
	public Members withTTVisitThree(Map<String, String> TTVisitThree) {
        this.TTVisitThree = new HashMap<>(TTVisitThree);
        return this;
    }
	public Members withTTVisitFour(Map<String, String> TTVisitFour) {
        this.TTVisitFour = new HashMap<>(TTVisitFour);
        return this;
    }
	public Members withTTVisitFive(Map<String, String> TTVisitFive) {
        this.TTVisitFive = new HashMap<>(TTVisitFive);
        return this;
    }
	public Members withMeaslesVisit(Map<String, String> withMeaslesVisit) {
        this.withMeaslesVisit = new HashMap<>(withMeaslesVisit);
        return this;
    }
	public Members withfamilyPlanning(Map<String, String> familyPlanning) {
        this.familyPlanning = new HashMap<>(familyPlanning);
        return this;
    }
	public Members withgeneral(Map<String, String> general) {
        this.general = new HashMap<>(general);
        return this;
    }
	public Members withnewBorn(Map<String, String> newBorn) {
        this.newBorn = new HashMap<>(newBorn);
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
	
	public Map<String, String> TTVisitOne() {
		return TTVisitOne;
	}
	public Map<String, String> TTVisitTwo() {
		return TTVisitTwo;
	}
	public Map<String, String> TTVisitThree() {
		return TTVisitThree;
	}
	public Map<String, String> TTVisitFour() {
		return TTVisitFour;
	}
	public Map<String, String> TTVisitFive() {
		return TTVisitFive;
	}
	public Map<String, String> withMeaslesVisit() {
		return withMeaslesVisit;
	}
	public Map<String, String> familyPlanning() {
		return familyPlanning;
	}
	public Map<String, String> general() {
		return general;
	}
	public Map<String, String> newBorn() {
		return newBorn;
	}
    public Members setIsClosed(boolean isClosed) {
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
