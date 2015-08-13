package org.opensrp.dto.register;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class ELCORegisterEntryDTO {

	@JsonProperty
	private String CASEID;
	@JsonProperty
	private String LOCATIONID;
	@JsonProperty
	private String PROVIDERID;
	@JsonProperty
	private String TODAY;
	@JsonProperty
	private String START;
	@JsonProperty
	private String END;
	@JsonProperty
	private String GOBHHID;
	@JsonProperty
	private String JiVitAHHID;
	@JsonProperty
	private String FWCENDATE;
	@JsonProperty
	private String FWCENSTAT;
	@JsonProperty
	private String FWWOMFNAME;
	@JsonProperty
	private String FWWOMLNAME;
	@JsonProperty
	private String FWWOMNID;
	@JsonProperty
	private String FWWOMBID;
	@JsonProperty
	private String FWHUSNAME;
	@JsonProperty
	private String FWBIRTHDATE;
	@JsonProperty
	private String FWGENDER;
	@JsonProperty
	private String FWWOMAGE;
	@JsonProperty
	private String FWDISPLAYAGE;
	@JsonProperty
	private String FWWOMSTRMEN;
	@JsonProperty
	private String FWWOMHUSALV;
	@JsonProperty
	private String FWWOMHUSSTR;
	@JsonProperty
	private String FWWOMHUSLIV;
	@JsonProperty
	private String FWELIGIBLE;
	@JsonProperty
	private Map<String, String> details;
	
	@JsonProperty
	private List<Map<String, String>> PSRFDETAILS;

	public ELCORegisterEntryDTO() {

	}

	public ELCORegisterEntryDTO withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}
	public ELCORegisterEntryDTO withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}
	public ELCORegisterEntryDTO withTODAY(String TODAY) {
		this.TODAY = TODAY;
		return this;
	}
	public ELCORegisterEntryDTO withSTART(String START) {
		this.START = START;
		return this;
	}
	public ELCORegisterEntryDTO withEND(String END) {
		this.END = END;
		return this;
	}
	public ELCORegisterEntryDTO withGOBHHID(String GOBHHID) {
		this.GOBHHID = GOBHHID;
		return this;
	}

	public ELCORegisterEntryDTO withJiVitAHHID(String JiVitAHHID) {
		this.JiVitAHHID = JiVitAHHID;
		return this;
	}

	public ELCORegisterEntryDTO withFWCENDATE(String FWCENDATE) {
		this.FWCENDATE = FWCENDATE;
		return this;
	}

	public ELCORegisterEntryDTO withFWCENSTAT(String FWCENSTAT) {
		this.FWCENSTAT = FWCENSTAT;
		return this;
	}
	public ELCORegisterEntryDTO withFWWOMFNAME(String FWWOMFNAME) {
		this.FWWOMFNAME = FWWOMFNAME;
		return this;
	}
	public ELCORegisterEntryDTO withFWWOMLNAME(String FWWOMLNAME) {
		this.FWWOMLNAME = FWWOMLNAME;
		return this;
	}
	public ELCORegisterEntryDTO withFWWOMNID(String FWWOMNID) {
		this.FWWOMNID = FWWOMNID;
		return this;
	}
	public ELCORegisterEntryDTO withFWWOMBID(String FWWOMBID) {
		this.FWWOMBID = FWWOMBID;
		return this;
	}
	public ELCORegisterEntryDTO withFWHUSNAME(String FWHUSNAME) {
		this.FWHUSNAME = FWHUSNAME;
		return this;
	}
	public ELCORegisterEntryDTO withFWBIRTHDATE(String FWBIRTHDATE) {
		this.FWBIRTHDATE = FWBIRTHDATE;
		return this;
	}
	public ELCORegisterEntryDTO withFWGENDER(String FWGENDER) {
		this.FWGENDER = FWGENDER;
		return this;
	}
	public ELCORegisterEntryDTO withFWWOMAGE(String FWWOMAGE) {
		this.FWWOMAGE = FWWOMAGE;
		return this;
	}
	public ELCORegisterEntryDTO withFWDISPLAYAGE(String FWDISPLAYAGE) {
		this.FWDISPLAYAGE = FWDISPLAYAGE;
		return this;
	}
	public ELCORegisterEntryDTO withFWWOMSTRMEN(String FWWOMSTRMEN) {
		this.FWWOMSTRMEN = FWWOMSTRMEN;
		return this;
	}
	public ELCORegisterEntryDTO withFWWOMHUSSTR(String FWWOMHUSSTR) {
		this.FWWOMHUSSTR = FWWOMHUSSTR;
		return this;
	}
	public ELCORegisterEntryDTO withFWWOMHUSALV(String FWWOMHUSALV) {
		this.FWWOMHUSALV = FWWOMHUSALV;
		return this;
	}
	public ELCORegisterEntryDTO withFWWOMHUSLIV(String FWWOMHUSLIV) {
		this.FWWOMHUSLIV = FWWOMHUSLIV;
		return this;
	}
	public ELCORegisterEntryDTO withFWELIGIBLE(String FWELIGIBLE) {
		this.FWELIGIBLE = FWELIGIBLE;
		return this;
	}
	public ELCORegisterEntryDTO withDetails(Map<String, String> details) {
        this.details = new HashMap<>(details);
        return this;
    }
	public ELCORegisterEntryDTO withPSRFDETAILS(List<Map<String, String>> PSRFDETAILS) {
		this.PSRFDETAILS = PSRFDETAILS;
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
