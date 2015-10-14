package org.opensrp.register.mcare.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;
import org.opensrp.dto.register.HHRegisterEntryDTO;

@TypeDiscriminator("doc.type === 'Mother'")
public class Mother extends MotechBaseDataObject {
	@JsonProperty
	private String CASEID;
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
    private List<Map<String, String>> ancVisits;
	@JsonProperty
	private Map<String, String> details;

	public Mother() {

		this.details = new HashMap<>();
		this.ancVisits = new ArrayList<>();
	}
	
	public Mother withCASEID(String CASEID) {
		this.CASEID = CASEID;
		return this;
	}

	public Mother withINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}

	public Mother withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}

	public Mother withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}

	public Mother withTODAY(String TODAY) {
		this.TODAY = TODAY;
		return this;
	}

	public Mother withSTART(String START) {
		this.START = START;
		return this;
	}

	public Mother withEND(String END) {
		this.END = END;
		return this;
	}

	public Mother withANCVisits(List<Map<String, String>> ancVisits) {
		this.ancVisits = ancVisits;
		return this;
	}
	public void updateANCVisitInformation(Map<String, String> ancVisits) {
        if (this.ancVisits == null) {
            this.ancVisits = new ArrayList<>();
        }
        this.ancVisits.add(ancVisits);
    }

	public Mother withDetails(Map<String, String> details) {
        this.details = new HashMap<>(details);
        return this;
    }

	public String CASEID() {
		return CASEID;
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

	public List<Map<String, String>> ancVisits() {
		if (ancVisits == null) {
			ancVisits = new ArrayList<>();
		}
		return ancVisits;
	}

	private String getCASEID() {
		return CASEID;
	}

	public Map<String, String> details() {
		return details;
	}

	public String getDetail(String name) {
		return details.get(name);
	}
	
	public String getancVisit(String name) {	
		/*int size = ELCODETAILS.size();
		String elems = "";
		for (int i = 0; i < size; i++)
			elems = elems + ELCODETAILS.get(i).get(name) + " " ;
		return elems;	*/	

		return ancVisits.get(0).get(name);
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