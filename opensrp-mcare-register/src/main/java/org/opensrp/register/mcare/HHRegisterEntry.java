package org.opensrp.register.mcare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class HHRegisterEntry {

	private String CASEID;
	
	private String PROVIDERID;
	
	private String LOCATIONID;
	
	private String TODAY;
	
	private String START;
	
	private String END;
	
	private String FWNHREGDATE;
	
	private String FWGOBHHID; 
	
	private String FWJIVHHID;
	
	private String FWNHNEARTO;
	
	private String FWNHHHGPS;
	
	private String FWHOHFNAME;
	
	private String FWHOHLNAME;
	
	private String FWHOHBIRTHDATE; 
	
	private String FWHOHGENDER;
	
	private String FWNHHMBRNUM;
	
	private String FWNHHMWRA;
	
	private String MWRA;
	
	private List<Map<String, String>> ELCODETAILS;
	
	private Map<String, String> details;

	public HHRegisterEntry() {

		this.ELCODETAILS = new ArrayList<>();
	}

	public HHRegisterEntry withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}

	public HHRegisterEntry withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}

	public HHRegisterEntry withTODAY(String TODAY) {
		this.TODAY = TODAY;
		return this;
	}

	public HHRegisterEntry withSTART(String START) {
		this.START = START;
		return this;
	}

	public HHRegisterEntry withEND(String END) {
		this.END = END;
		return this;
	}

	public HHRegisterEntry withFWNHREGDATE(String FWNHREGDATE) {
		this.FWNHREGDATE = FWNHREGDATE;
		return this;
	}

	public HHRegisterEntry withFWGOBHHID(String FWGOBHHID) {
		this.FWGOBHHID = FWGOBHHID;
		return this;
	}

	public HHRegisterEntry withFWJIVHHID(String FWJIVHHID) {
		this.FWJIVHHID = FWJIVHHID;
		return this;
	}

	public HHRegisterEntry withFWNHHHGPS(String FWNHHHGPS) {
		this.FWNHHHGPS = FWNHHHGPS;
		return this;
	}

	public HHRegisterEntry withFWNHNEARTO(String FWNHNEARTO) {
		this.FWNHNEARTO = FWNHNEARTO;
		return this;
	}

	public HHRegisterEntry withFWHOHFNAME(String FWHOHFNAME) {
		this.FWHOHFNAME = FWHOHFNAME;
		return this;
	}

	
	public HHRegisterEntry withFWHOHLNAME(String FWHOHLNAME) {
		this.FWHOHLNAME = FWHOHLNAME;
		return this;
	}

	public HHRegisterEntry withFWHOHBIRTHDATE(String FWHOHBIRTHDATE) {
		this.FWHOHBIRTHDATE = FWHOHBIRTHDATE;
		return this;
	}

	public HHRegisterEntry withFWNHHMBRNUM(String FWNHHMBRNUM) {
		this.FWNHHMBRNUM = FWNHHMBRNUM;
		return this;
	}

	public HHRegisterEntry withFWHOHGENDER(String FWHOHGENDER) {
		this.FWHOHGENDER = FWHOHGENDER;
		return this;
	}
	
	public HHRegisterEntry withFWNHHMWRA(String FWNHHMWRA) {
		this.FWNHHMWRA = FWNHHMWRA;
		return this;
	}

	public HHRegisterEntry withMWRA(String MWRA) {
		this.MWRA = MWRA;
		return this;
	}

	public HHRegisterEntry withELCODETAILS(List<Map<String, String>> ELCODETAILS) {
		this.ELCODETAILS = ELCODETAILS;
		return this;
	}
	public HHRegisterEntry withDetails(Map<String, String> details) {
        this.details = new HashMap<>(details);
        return this;
    }
	public String CASEID() {
		return CASEID;
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

	public String FWNHHMWRA(String FWNHHMWRA) {
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
