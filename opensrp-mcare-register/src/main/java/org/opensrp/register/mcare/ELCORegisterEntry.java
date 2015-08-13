package org.opensrp.register.mcare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class ELCORegisterEntry {
	
	private String CASEID;
	
	private String LOCATIONID;
	
	private String PROVIDERID;
	
	private String TODAY;
	
	private String START;
	
	private String END;
	
	private String GOBHHID;
	
	private String JiVitAHHID;
	
	private String FWCENDATE;
	
	private String FWCENSTAT;
	
	private String FWWOMFNAME;
	
	private String FWWOMLNAME;
	
	private String FWWOMNID;
	
	private String FWWOMBID;
	
	private String FWHUSNAME;
	
	private String FWBIRTHDATE;
	
	private String FWGENDER;
	
	private String FWWOMAGE;
	
	private String FWDISPLAYAGE;
	
	private String FWWOMSTRMEN;
	
	private String FWWOMHUSALV;
	
	private String FWWOMHUSSTR;
	
	private String FWWOMHUSLIV;
	
	private String FWELIGIBLE;
	
	private Map<String, String> details;
	
	private List<Map<String, String>> PSRFDETAILS;

	public ELCORegisterEntry() {

	}

	public ELCORegisterEntry withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}
	public ELCORegisterEntry withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}
	public ELCORegisterEntry withTODAY(String TODAY) {
		this.TODAY = TODAY;
		return this;
	}
	public ELCORegisterEntry withSTART(String START) {
		this.START = START;
		return this;
	}
	public ELCORegisterEntry withEND(String END) {
		this.END = END;
		return this;
	}
	public ELCORegisterEntry withGOBHHID(String GOBHHID) {
		this.GOBHHID = GOBHHID;
		return this;
	}

	public ELCORegisterEntry withJiVitAHHID(String JiVitAHHID) {
		this.JiVitAHHID = JiVitAHHID;
		return this;
	}

	public ELCORegisterEntry withFWCENDATE(String FWCENDATE) {
		this.FWCENDATE = FWCENDATE;
		return this;
	}

	public ELCORegisterEntry withFWCENSTAT(String FWCENSTAT) {
		this.FWCENSTAT = FWCENSTAT;
		return this;
	}
	public ELCORegisterEntry withFWWOMFNAME(String FWWOMFNAME) {
		this.FWWOMFNAME = FWWOMFNAME;
		return this;
	}
	public ELCORegisterEntry withFWWOMLNAME(String FWWOMLNAME) {
		this.FWWOMLNAME = FWWOMLNAME;
		return this;
	}
	public ELCORegisterEntry withFWWOMNID(String FWWOMNID) {
		this.FWWOMNID = FWWOMNID;
		return this;
	}
	public ELCORegisterEntry withFWWOMBID(String FWWOMBID) {
		this.FWWOMBID = FWWOMBID;
		return this;
	}
	public ELCORegisterEntry withFWHUSNAME(String FWHUSNAME) {
		this.FWHUSNAME = FWHUSNAME;
		return this;
	}
	public ELCORegisterEntry withFWBIRTHDATE(String FWBIRTHDATE) {
		this.FWBIRTHDATE = FWBIRTHDATE;
		return this;
	}
	public ELCORegisterEntry withFWGENDER(String FWGENDER) {
		this.FWGENDER = FWGENDER;
		return this;
	}
	public ELCORegisterEntry withFWWOMAGE(String FWWOMAGE) {
		this.FWWOMAGE = FWWOMAGE;
		return this;
	}
	public ELCORegisterEntry withFWDISPLAYAGE(String FWDISPLAYAGE) {
		this.FWDISPLAYAGE = FWDISPLAYAGE;
		return this;
	}
	public ELCORegisterEntry withFWWOMSTRMEN(String FWWOMSTRMEN) {
		this.FWWOMSTRMEN = FWWOMSTRMEN;
		return this;
	}
	public ELCORegisterEntry withFWWOMHUSSTR(String FWWOMHUSSTR) {
		this.FWWOMHUSSTR = FWWOMHUSSTR;
		return this;
	}
	public ELCORegisterEntry withFWWOMHUSALV(String FWWOMHUSALV) {
		this.FWWOMHUSALV = FWWOMHUSALV;
		return this;
	}
	public ELCORegisterEntry withFWWOMHUSLIV(String FWWOMHUSLIV) {
		this.FWWOMHUSLIV = FWWOMHUSLIV;
		return this;
	}
	public ELCORegisterEntry withFWELIGIBLE(String FWELIGIBLE) {
		this.FWELIGIBLE = FWELIGIBLE;
		return this;
	}
	public ELCORegisterEntry withDetails(Map<String, String> details) {
        this.details = new HashMap<>(details);
        return this;
    }
	public ELCORegisterEntry withPSRFDETAILS(List<Map<String, String>> PSRFDETAILS) {
		this.PSRFDETAILS = PSRFDETAILS;
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
		return  END;
	}
	public String GOBHHID() {
		return GOBHHID;
	}
	public String JiVitAHHID() {
		return JiVitAHHID;
	}
	public String FWCENDATE() {
		return FWCENDATE;
	}
	public String FWCENSTAT() {
		return FWCENSTAT;
	}
	public String FWWOMFNAME() {
		return FWWOMFNAME;
	}
	public String FWWOMLNAME() {
		return FWWOMLNAME;
	}
	public String FWWOMNID() {
		return FWWOMNID;
	}
	public String FWWOMBID() {
		return FWWOMBID;
	}
	public String FWHUSNAME() {
		return FWHUSNAME;
	}
	public String FWBIRTHDATE() {
		return FWBIRTHDATE;
	}
	public String FWGENDER() {
		return FWGENDER;
	}
	public String FWWOMAGE() {
		return FWWOMAGE;
	}
	public String FWDISPLAYAGE() {
		return FWDISPLAYAGE;
	}
	public String FWWOMSTRMEN() {
		return FWWOMSTRMEN;
	}
	public String FWWOMHUSSTR() {
		return FWWOMHUSSTR;
	}
	public String FWWOMHUSALV() {
		return FWWOMHUSALV;
	}
	public String FWWOMHUSLIV() {
		return FWWOMHUSLIV;
	}
	public String FWELIGIBLE() {
		return FWELIGIBLE;
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

	public List<Map<String, String>> PSRFDETAILS() {
		if (PSRFDETAILS == null) {
			PSRFDETAILS = new ArrayList<>();
		}
		return PSRFDETAILS;
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
