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

@TypeDiscriminator("doc.type === 'Elco'")
public class Elco extends MotechBaseDataObject {

	@JsonProperty
	private String CASEID;
	@JsonProperty
	private String INSTANCEID;
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

	public Elco() {

	}

	public Elco withCASEID(String CASEID) {
		this.CASEID = CASEID;
		return this;
	}
	
	public Elco withINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}

	public Elco withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}
	public Elco withLOCATIONID(String LOCAoTIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}
	public Elco withTODAY(String TODAY) {
		this.TODAY = TODAY;
		return this;
	}
	public Elco withSTART(String START) {
		this.START = START;
		return this;
	}
	public Elco withEND(String END) {
		this.END = END;
		return this;
	}
	public Elco withGOBHHID(String GOBHHID) {
		this.GOBHHID = GOBHHID;
		return this;
	}

	public Elco withJiVitAHHID(String JiVitAHHID) {
		this.JiVitAHHID = JiVitAHHID;
		return this;
	}

	public Elco withFWCENDATE(String FWCENDATE) {
		this.FWCENDATE = FWCENDATE;
		return this;
	}

	public Elco withFWCENSTAT(String FWCENSTAT) {
		this.FWCENSTAT = FWCENSTAT;
		return this;
	}
	public Elco withFWWOMFNAME(String FWWOMFNAME) {
		this.FWWOMFNAME = FWWOMFNAME;
		return this;
	}
	public Elco withFWWOMLNAME(String FWWOMLNAME) {
		this.FWWOMLNAME = FWWOMLNAME;
		return this;
	}
	public Elco withFWWOMNID(String FWWOMNID) {
		this.FWWOMNID = FWWOMNID;
		return this;
	}
	public Elco withFWWOMBID(String FWWOMBID) {
		this.FWWOMBID = FWWOMBID;
		return this;
	}
	public Elco withFWHUSNAME(String FWHUSNAME) {
		this.FWHUSNAME = FWHUSNAME;
		return this;
	}
	public Elco withFWBIRTHDATE(String FWBIRTHDATE) {
		this.FWBIRTHDATE = FWBIRTHDATE;
		return this;
	}
	public Elco withFWGENDER(String FWGENDER) {
		this.FWGENDER = FWGENDER;
		return this;
	}
	public Elco withFWWOMAGE(String FWWOMAGE) {
		this.FWWOMAGE = FWWOMAGE;
		return this;
	}
	public Elco withFWDISPLAYAGE(String FWDISPLAYAGE) {
		this.FWDISPLAYAGE = FWDISPLAYAGE;
		return this;
	}
	public Elco withFWWOMSTRMEN(String FWWOMSTRMEN) {
		this.FWWOMSTRMEN = FWWOMSTRMEN;
		return this;
	}
	public Elco withFWWOMHUSSTR(String FWWOMHUSSTR) {
		this.FWWOMHUSSTR = FWWOMHUSSTR;
		return this;
	}
	public Elco withFWWOMHUSALV(String FWWOMHUSALV) {
		this.FWWOMHUSALV = FWWOMHUSALV;
		return this;
	}
	public Elco withFWWOMHUSLIV(String FWWOMHUSLIV) {
		this.FWWOMHUSLIV = FWWOMHUSLIV;
		return this;
	}
	public Elco withFWELIGIBLE(String FWELIGIBLE) {
		this.FWELIGIBLE = FWELIGIBLE;
		return this;
	}
	public Elco withDetails(Map<String, String> details) {
        this.details = new HashMap<>(details);
        return this;
    }
	public Elco withPSRFDETAILS(List<Map<String, String>> PSRFDETAILS) {
		this.PSRFDETAILS = PSRFDETAILS;
		return this;
	}
	public String CASEID() {
		return CASEID;
	}
	public String INSTANCEID()
	{
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
