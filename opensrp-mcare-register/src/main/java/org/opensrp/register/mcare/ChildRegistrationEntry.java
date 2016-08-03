package org.opensrp.register.mcare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ChildRegistrationEntry {
	
	private String CASEID;
	
	private String INSTANCEID;
	
	private String PROVIDERID;
	
	private String LOCATIONID;
	
	private String TODAY;
	
	private String START;
	
	private String END;
	
	private String FWNHREGDATE;
	
	private String FWGOBHHID;
	
	private String FWJIVHHID;
	
	private String FWCOUNTRY;
	
	private String FWDIVISION;
	
	private String FWDISTRICT;
	
	private String FWUPAZILLA;
	
	private String FWUNION;
	
	private String FWWARD;
	
	private String FWSUBUNIT;
	
	private String FWMAUZA_PARA;
	
	private String FWNHHHGPS;
	
	private String form_name;
	
	private String FWHOHFNAME;
	
	private String FWHOHLNAME;
	
	private String FWHOHBIRTHDATE;
	
	private String FWHOHGENDER;
	
	private String FWNHHMBRNUM;
	
	private String FWNHHMWRA;
	
	private String ELCO;
	
	private String user_type;
	
	private String external_user_ID;
	
	private String current_formStatus;
	
	private List<Map<String, String>> ELCODETAILS;
	
	private List<Map<String, String>> multimediaAttachments;
	
	private Map<String, String> details;
	
	ChildRegistrationEntry() {
		this.ELCODETAILS = new ArrayList<>();
		this.multimediaAttachments = new ArrayList<>();
	}
	
	ChildRegistrationEntry withCASEID(String CASEID) {
		this.CASEID = CASEID;
		return this;
	}
	
	ChildRegistrationEntry withINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}
	
	ChildRegistrationEntry withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}
	
	ChildRegistrationEntry withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}
	
	ChildRegistrationEntry withTODAY(String TODAY) {
		this.TODAY = TODAY;
		return this;
	}
	
	ChildRegistrationEntry withSTART(String START) {
		this.START = START;
		return this;
	}
	
	ChildRegistrationEntry withEND(String END) {
		this.END = END;
		return this;
	}
	
	ChildRegistrationEntry withFWNHREGDATE(String FWNHREGDATE) {
		this.FWNHREGDATE = FWNHREGDATE;
		return this;
	}
	
	ChildRegistrationEntry withFWGOBHHID(String FWGOBHHID) {
		this.FWGOBHHID = FWGOBHHID;
		return this;
	}
	
	ChildRegistrationEntry withFWJIVHHID(String FWJIVHHID) {
		this.FWJIVHHID = FWJIVHHID;
		return this;
	}
	
	ChildRegistrationEntry withFWNHHHGPS(String FWNHHHGPS) {
		this.FWNHHHGPS = FWNHHHGPS;
		return this;
	}
	
	ChildRegistrationEntry withform_name(String form_name) {
		this.form_name = form_name;
		return this;
	}
	
	ChildRegistrationEntry withFWHOHFNAME(String FWHOHFNAME) {
		this.FWHOHFNAME = FWHOHFNAME;
		return this;
	}
	
	ChildRegistrationEntry withFWCOUNTRY(String FWCOUNTRY) {
		this.FWCOUNTRY = FWCOUNTRY;
		return this;
	}
	
	ChildRegistrationEntry withFWDIVISION(String FWDIVISION) {
		this.FWDIVISION = FWDIVISION;
		return this;
	}
	
	ChildRegistrationEntry withFWDISTRICT(String FWDISTRICT) {
		this.FWDISTRICT = FWDISTRICT;
		return this;
	}
	
	ChildRegistrationEntry withFWUPAZILLA(String FWUPAZILLA) {
		this.FWUPAZILLA = FWUPAZILLA;
		return this;
	}
	
	ChildRegistrationEntry withFWUNION(String FWUNION) {
		this.FWUNION = FWUNION;
		return this;
	}
	
	ChildRegistrationEntry withFWWARD(String FWWARD) {
		this.FWWARD = FWWARD;
		return this;
	}
	
	ChildRegistrationEntry withFWSUBUNIT(String FWSUBUNIT) {
		this.FWSUBUNIT = FWSUBUNIT;
		return this;
	}
	
	ChildRegistrationEntry withFWMAUZA_PARA(String FWMAUZA_PARA) {
		this.FWMAUZA_PARA = FWMAUZA_PARA;
		return this;
	}
	
	ChildRegistrationEntry withFWHOHLNAME(String FWHOHLNAME) {
		this.FWHOHLNAME = FWHOHLNAME;
		return this;
	}
	
	ChildRegistrationEntry withFWHOHBIRTHDATE(String FWHOHBIRTHDATE) {
		this.FWHOHBIRTHDATE = FWHOHBIRTHDATE;
		return this;
	}
	
	ChildRegistrationEntry withFWNHHMBRNUM(String FWNHHMBRNUM) {
		this.FWNHHMBRNUM = FWNHHMBRNUM;
		return this;
	}
	
	ChildRegistrationEntry withFWHOHGENDER(String FWHOHGENDER) {
		this.FWHOHGENDER = FWHOHGENDER;
		return this;
	}
	
	ChildRegistrationEntry withFWNHHMWRA(String FWNHHMWRA) {
		this.FWNHHMWRA = FWNHHMWRA;
		return this;
	}
	
	ChildRegistrationEntry withELCO(String ELCO) {
		this.ELCO = ELCO;
		return this;
	}
	
	ChildRegistrationEntry withuser_type(String user_type) {
		this.user_type = user_type;
		return this;
	}
	
	ChildRegistrationEntry withexternal_user_ID(String external_user_ID) {
		this.external_user_ID = external_user_ID;
		return this;
	}
	
	ChildRegistrationEntry withcurrent_formStatus(String current_formStatus) {
		this.current_formStatus = current_formStatus;
		return this;
	}
	
	ChildRegistrationEntry withELCODETAILS(List<Map<String, String>> ELCODETAILS) {
		this.ELCODETAILS = ELCODETAILS;
		return this;
	}
	
	ChildRegistrationEntry withmultimediaAttachments(List<Map<String, String>> multimediaAttachments) {
		this.multimediaAttachments = multimediaAttachments;
		return this;
	}
	
	ChildRegistrationEntry withDetails(Map<String, String> details) {
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
	
	public String form_name() {
		return form_name;
	}
	
	public String FWCOUNTRY() {
		return FWCOUNTRY;
	}
	
	public String FWDIVISION() {
		return FWDIVISION;
	}
	
	public String FWDISTRICT() {
		return FWDISTRICT;
	}
	
	public String FWUPAZILLA() {
		return FWUPAZILLA;
	}
	
	public String FWUNION() {
		return FWUNION;
	}
	
	public String FWWARD() {
		return FWWARD;
	}
	
	public String FWSUBUNIT() {
		return FWSUBUNIT;
	}
	
	public String FWMAUZA_PARA() {
		return FWMAUZA_PARA;
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
	
	public String ELCO() {
		return ELCO;
	}
	
	public String user_type() {
		return user_type;
	}
	
	public String external_user_ID() {
		return external_user_ID;
	}
	
	public String current_formStatus() {
		return current_formStatus;
	}
	
	public List<Map<String, String>> ELCODETAILS() {
		if (ELCODETAILS == null) {
			ELCODETAILS = new ArrayList<>();
		}
		return ELCODETAILS;
	}
	
	public List<Map<String, String>> multimediaAttachments() {
		if (multimediaAttachments == null) {
			multimediaAttachments = new ArrayList<>();
		}
		return multimediaAttachments;
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
