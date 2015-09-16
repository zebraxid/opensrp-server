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

@TypeDiscriminator("doc.type === 'HouseHold'")
public class HouseHold extends MotechBaseDataObject {
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
	private String FWNHREGDATE;
	@JsonProperty
	private String FWGOBHHID; 
	@JsonProperty
	private String FWJIVHHID;
	@JsonProperty
	private String FWNHNEARTO;
	@JsonProperty
	private String FWNHHHGPS;
	@JsonProperty
	private String FWHOHFNAME;
	@JsonProperty
	private String FWHOHLNAME;
	@JsonProperty
	private String FWHOHBIRTHDATE; 
	@JsonProperty
	private String FWHOHGENDER;
	@JsonProperty
	private String FWNHHMBRNUM;
	@JsonProperty
	private String FWNHHMWRA;
	@JsonProperty
	private String MWRA;
	@JsonProperty
	private List<Map<String, String>> ELCODETAILS;
	@JsonProperty
	private Map<String, String> details;

	public HouseHold() {

		this.ELCODETAILS = new ArrayList<>();
	}
	
	public HouseHold withCASEID(String CASEID) {
		this.CASEID = CASEID;
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

	public HouseHold withFWNHREGDATE(String FWNHREGDATE) {
		this.FWNHREGDATE = FWNHREGDATE;
		return this;
	}

	public HouseHold withFWGOBHHID(String FWGOBHHID) {
		this.FWGOBHHID = FWGOBHHID;
		return this;
	}

	public HouseHold withFWJIVHHID(String FWJIVHHID) {
		this.FWJIVHHID = FWJIVHHID;
		return this;
	}

	public HouseHold withFWNHHHGPS(String FWNHHHGPS) {
		this.FWNHHHGPS = FWNHHHGPS;
		return this;
	}

	public HouseHold withFWNHNEARTO(String FWNHNEARTO) {
		this.FWNHNEARTO = FWNHNEARTO;
		return this;
	}

	public HouseHold withFWHOHFNAME(String FWHOHFNAME) {
		this.FWHOHFNAME = FWHOHFNAME;
		return this;
	}

	
	public HouseHold withFWHOHLNAME(String FWHOHLNAME) {
		this.FWHOHLNAME = FWHOHLNAME;
		return this;
	}

	public HouseHold withFWHOHBIRTHDATE(String FWHOHBIRTHDATE) {
		this.FWHOHBIRTHDATE = FWHOHBIRTHDATE;
		return this;
	}

	public HouseHold withFWNHHMBRNUM(String FWNHHMBRNUM) {
		this.FWNHHMBRNUM = FWNHHMBRNUM;
		return this;
	}

	public HouseHold withFWHOHGENDER(String FWHOHGENDER) {
		this.FWHOHGENDER = FWHOHGENDER;
		return this;
	}
	
	public HouseHold withFWNHHMWRA(String FWNHHMWRA) {
		this.FWNHHMWRA = FWNHHMWRA;
		return this;
	}

	public HouseHold withMWRA(String MWRA) {
		this.MWRA = MWRA;
		return this;
	}

	public HouseHold withELCODETAILS(List<Map<String, String>> ELCODETAILS) {
		this.ELCODETAILS = ELCODETAILS;
		return this;
	}
	
	public HouseHold withDetails(Map<String, String> details) {
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

	public String FWNHREGDATE() {
		return FWNHREGDATE;
	}

	public String FWGOBHHID() {
		return FWGOBHHID;
	}

	public String FWJIVHHID() {
		return FWJIVHHID;
	}

	public String FWNHHHGPS() {
		return FWNHHHGPS;
	}

	public String FWNHNEARTO() {
		return FWNHNEARTO;
	}

	public String FWHOHFNAME() {
		return FWHOHFNAME;
	}
	public String FWHOHLNAME() {
		return FWHOHLNAME;
	}

	public String FWHOHBIRTHDATE() {
		return FWHOHBIRTHDATE;
	}

	public String FWNHHMBRNUM() {
		return FWNHHMBRNUM;
	}

	public String FWHOHGENDER() {
		return FWHOHGENDER;
	}

	public String FWNHHMWRA() {
		return FWNHHMWRA;
	}

	public String MWRA() {
		return MWRA;
	}

	public List<Map<String, String>> ELCODETAILS() {
		if (ELCODETAILS == null) {
			ELCODETAILS = new ArrayList<>();
		}
		return ELCODETAILS;
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
	
	public String getELCODetail(String name) {	
		/*int size = ELCODETAILS.size();
		String elems = "";
		for (int i = 0; i < size; i++)
			elems = elems + ELCODETAILS.get(i).get(name) + " " ;
		return elems;	*/	

		return ELCODETAILS.get(0).get(name);
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