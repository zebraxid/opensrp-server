package org.opensrp.dto.register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

public class ChildRegisterEntryDTO {
	
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
	private String FWGOBChildID;
	
	@JsonProperty
	private String FWJIVChildID;
	
	@JsonProperty
	private String FWCOUNTRY;
	
	@JsonProperty
	private String FWDIVISION;
	
	@JsonProperty
	private String FWDISTRICT;
	
	@JsonProperty
	private String FWUPAZILLA;
	
	@JsonProperty
	private String FWUNION;
	
	@JsonProperty
	private String FWWARD;
	
	@JsonProperty
	private String FWSUBUNIT;
	
	@JsonProperty
	private String FWMAUZA_PARA;
	
	@JsonProperty
	private String FWNChildHGPS;
	
	@JsonProperty
	private String form_name;
	
	@JsonProperty
	private String FWHOHFNAME;
	
	@JsonProperty
	private String FWHOHLNAME;
	
	@JsonProperty
	private String FWHOHBIRTHDATE;
	
	@JsonProperty
	private String FWHOHGENDER;
	
	@JsonProperty
	private String FWNChildMBRNUM;
	
	@JsonProperty
	private String FWNChildMWRA;
	
	@JsonProperty
	private String ELCO;
	
	@JsonProperty
	private String user_type;
	
	@JsonProperty
	private String external_user_ID;
	
	@JsonProperty
	private String current_formStatus;
	
	@JsonProperty
	private List<Map<String, String>> ELCODETAILS;
	
	@JsonProperty
	private List<Map<String, String>> multimediaAttachments;
	
	@JsonProperty
	private Map<String, String> details;
	
	private DateTime received_time;
	
	public ChildRegisterEntryDTO() {
		this.ELCODETAILS = new ArrayList<>();
		this.multimediaAttachments = new ArrayList<>();
	}
	
	public ChildRegisterEntryDTO withCASEID(String CASEID) {
		this.CASEID = CASEID;
		return this;
	}
	
	public ChildRegisterEntryDTO withINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}
	
	public ChildRegisterEntryDTO withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}
	
	public ChildRegisterEntryDTO withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}
	
	public ChildRegisterEntryDTO withTODAY(String TODAY) {
		this.TODAY = TODAY;
		return this;
	}
	
	public ChildRegisterEntryDTO withSTART(String START) {
		this.START = START;
		return this;
	}
	
	public ChildRegisterEntryDTO withEND(String END) {
		this.END = END;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWNHREGDATE(String FWNHREGDATE) {
		this.FWNHREGDATE = FWNHREGDATE;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWGOBChildID(String FWGOBChildID) {
		this.FWGOBChildID = FWGOBChildID;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWJIVChildID(String FWJIVChildID) {
		this.FWJIVChildID = FWJIVChildID;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWNChildHGPS(String FWNChildHGPS) {
		this.FWNChildHGPS = FWNChildHGPS;
		return this;
	}
	
	public ChildRegisterEntryDTO withform_name(String form_name) {
		this.form_name = form_name;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWCOUNTRY(String FWCOUNTRY) {
		this.FWCOUNTRY = FWCOUNTRY;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWDIVISION(String FWDIVISION) {
		this.FWDIVISION = FWDIVISION;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWDISTRICT(String FWDISTRICT) {
		this.FWDISTRICT = FWDISTRICT;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWUPAZILLA(String FWUPAZILLA) {
		this.FWUPAZILLA = FWUPAZILLA;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWUNION(String FWUNION) {
		this.FWUNION = FWUNION;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWWARD(String FWWARD) {
		this.FWWARD = FWWARD;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWSUBUNIT(String FWSUBUNIT) {
		this.FWSUBUNIT = FWSUBUNIT;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWMAUZA_PARA(String FWMAUZA_PARA) {
		this.FWMAUZA_PARA = FWMAUZA_PARA;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWHOHFNAME(String FWHOHFNAME) {
		this.FWHOHFNAME = FWHOHFNAME;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWHOHLNAME(String FWHOHLNAME) {
		this.FWHOHLNAME = FWHOHLNAME;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWHOHBIRTHDATE(String FWHOHBIRTHDATE) {
		this.FWHOHBIRTHDATE = FWHOHBIRTHDATE;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWNChildMBRNUM(String FWNChildMBRNUM) {
		this.FWNChildMBRNUM = FWNChildMBRNUM;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWHOHGENDER(String FWHOHGENDER) {
		this.FWHOHGENDER = FWHOHGENDER;
		return this;
	}
	
	public ChildRegisterEntryDTO withFWNChildMWRA(String FWNChildMWRA) {
		this.FWNChildMWRA = FWNChildMWRA;
		return this;
	}
	
	public ChildRegisterEntryDTO withELCO(String ELCO) {
		this.ELCO = ELCO;
		return this;
	}
	
	public ChildRegisterEntryDTO withuser_type(String user_type) {
		this.user_type = user_type;
		return this;
	}
	
	public ChildRegisterEntryDTO withexternal_user_ID(String external_user_ID) {
		this.external_user_ID = external_user_ID;
		return this;
	}
	
	public ChildRegisterEntryDTO withcurrent_formStatus(String current_formStatus) {
		this.current_formStatus = current_formStatus;
		return this;
	}
	
	public ChildRegisterEntryDTO withELCODETAILS(List<Map<String, String>> ELCODETAILS) {
		this.ELCODETAILS = ELCODETAILS;
		return this;
	}
	
	public ChildRegisterEntryDTO withmultimediaAttachments(List<Map<String, String>> multimediaAttachments) {
		this.multimediaAttachments = multimediaAttachments;
		return this;
	}
	
	public ChildRegisterEntryDTO withDetails(Map<String, String> details) {
		this.details = new HashMap<>(details);
		return this;
	}
	
	public ChildRegisterEntryDTO withReceivedTime(DateTime received_time) {
		this.received_time = received_time;
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
