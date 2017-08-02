package org.opensrp.etl.document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.opensrp.etl.interfaces.DocumentType;
import org.opensrp.etl.service.HouseholdServices;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.HouseHold;


public class ElcoDocument extends CommonInformation implements Serializable ,DocumentType<ElcoDocument, Elco,HouseholdServices> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String FWCWOMSTRMEN;	
	private String FWCWOMSTER;	
	private String FWCWOMHUSALV;	
	private String FWCWOMHUSLIV;	
	private String FWCWOMHUSSTR;	
	private String isClosed;	
	private String existingElco;	
	private String newElco;	
	private String ELCO;	
	private String FWCENDATE;	
	private String FWCENSTAT;	
	private String FWWOMFNAME;	
	private String FWWOMLNAME;	
	private String FWWOMANYID;	
	private String FWWOMNID;	
	private String FWWOMRETYPENID;	
	private String FWWOMRETYPENID_CONCEPT;	
	private String FWWOMBID;	
	private String FWWOMRETYPEBID;	
	private String FWWOMRETYPEBID_CONCEPT;	
	private String FWHUSNAME;	
	private String FWBIRTHDATE;
	private String FWWOMAGE;	
	private String FWDISPLAYAGE;	
	private String FWWOMSTRMEN;	
	private String FWWOMHUSALV;	
	private String FWWOMHUSSTR;	
	private String FWWOMHUSLIV;	
	private String FWELIGIBLE;	
	private String FWELIGIBLE2;	
	private String FWPSRPREGSTS;	
	private List<Map<String, String>> PSRFDETAILS;	
	private List<Map<String, String>> MISDETAILS;
	private static final ElcoDocument INSTANCE = new ElcoDocument();
	private ElcoDocument() {
		// TODO Auto-generated constructor stub
	}
	
	public static ElcoDocument getInstance() {
		return INSTANCE;
	}

	public String getFWCWOMSTRMEN() {
		return FWCWOMSTRMEN;
	}

	public void setFWCWOMSTRMEN(String fWCWOMSTRMEN) {
		FWCWOMSTRMEN = fWCWOMSTRMEN;
	}

	public String getFWCWOMSTER() {
		return FWCWOMSTER;
	}

	public void setFWCWOMSTER(String fWCWOMSTER) {
		FWCWOMSTER = fWCWOMSTER;
	}

	public String getFWCWOMHUSALV() {
		return FWCWOMHUSALV;
	}

	public void setFWCWOMHUSALV(String fWCWOMHUSALV) {
		FWCWOMHUSALV = fWCWOMHUSALV;
	}

	public String getFWCWOMHUSLIV() {
		return FWCWOMHUSLIV;
	}

	public void setFWCWOMHUSLIV(String fWCWOMHUSLIV) {
		FWCWOMHUSLIV = fWCWOMHUSLIV;
	}

	public String getFWCWOMHUSSTR() {
		return FWCWOMHUSSTR;
	}

	public void setFWCWOMHUSSTR(String fWCWOMHUSSTR) {
		FWCWOMHUSSTR = fWCWOMHUSSTR;
	}

	public String getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}

	public String getExistingElco() {
		return existingElco;
	}

	public void setExistingElco(String existingElco) {
		this.existingElco = existingElco;
	}

	public String getNewElco() {
		return newElco;
	}

	public void setNewElco(String newElco) {
		this.newElco = newElco;
	}

	public String getELCO() {
		return ELCO;
	}

	public void setELCO(String eLCO) {
		ELCO = eLCO;
	}

	public String getFWCENDATE() {
		return FWCENDATE;
	}

	public void setFWCENDATE(String fWCENDATE) {
		FWCENDATE = fWCENDATE;
	}

	public String getFWCENSTAT() {
		return FWCENSTAT;
	}

	public void setFWCENSTAT(String fWCENSTAT) {
		FWCENSTAT = fWCENSTAT;
	}

	public String getFWWOMFNAME() {
		return FWWOMFNAME;
	}

	public void setFWWOMFNAME(String fWWOMFNAME) {
		FWWOMFNAME = fWWOMFNAME;
	}

	public String getFWWOMLNAME() {
		return FWWOMLNAME;
	}

	public void setFWWOMLNAME(String fWWOMLNAME) {
		FWWOMLNAME = fWWOMLNAME;
	}

	public String getFWWOMANYID() {
		return FWWOMANYID;
	}

	public void setFWWOMANYID(String fWWOMANYID) {
		FWWOMANYID = fWWOMANYID;
	}

	public String getFWWOMNID() {
		return FWWOMNID;
	}

	public void setFWWOMNID(String fWWOMNID) {
		FWWOMNID = fWWOMNID;
	}

	public String getFWWOMRETYPENID() {
		return FWWOMRETYPENID;
	}

	public void setFWWOMRETYPENID(String fWWOMRETYPENID) {
		FWWOMRETYPENID = fWWOMRETYPENID;
	}

	public String getFWWOMRETYPENID_CONCEPT() {
		return FWWOMRETYPENID_CONCEPT;
	}

	public void setFWWOMRETYPENID_CONCEPT(String fWWOMRETYPENID_CONCEPT) {
		FWWOMRETYPENID_CONCEPT = fWWOMRETYPENID_CONCEPT;
	}

	public String getFWWOMBID() {
		return FWWOMBID;
	}

	public void setFWWOMBID(String fWWOMBID) {
		FWWOMBID = fWWOMBID;
	}

	public String getFWWOMRETYPEBID() {
		return FWWOMRETYPEBID;
	}

	public void setFWWOMRETYPEBID(String fWWOMRETYPEBID) {
		FWWOMRETYPEBID = fWWOMRETYPEBID;
	}

	public String getFWWOMRETYPEBID_CONCEPT() {
		return FWWOMRETYPEBID_CONCEPT;
	}

	public void setFWWOMRETYPEBID_CONCEPT(String fWWOMRETYPEBID_CONCEPT) {
		FWWOMRETYPEBID_CONCEPT = fWWOMRETYPEBID_CONCEPT;
	}

	public String getFWHUSNAME() {
		return FWHUSNAME;
	}

	public void setFWHUSNAME(String fWHUSNAME) {
		FWHUSNAME = fWHUSNAME;
	}

	public String getFWBIRTHDATE() {
		return FWBIRTHDATE;
	}

	public void setFWBIRTHDATE(String fWBIRTHDATE) {
		FWBIRTHDATE = fWBIRTHDATE;
	}

	public String getFWWOMAGE() {
		return FWWOMAGE;
	}

	public void setFWWOMAGE(String fWWOMAGE) {
		FWWOMAGE = fWWOMAGE;
	}

	public String getFWDISPLAYAGE() {
		return FWDISPLAYAGE;
	}

	public void setFWDISPLAYAGE(String fWDISPLAYAGE) {
		FWDISPLAYAGE = fWDISPLAYAGE;
	}

	public String getFWWOMSTRMEN() {
		return FWWOMSTRMEN;
	}

	public void setFWWOMSTRMEN(String fWWOMSTRMEN) {
		FWWOMSTRMEN = fWWOMSTRMEN;
	}

	public String getFWWOMHUSALV() {
		return FWWOMHUSALV;
	}

	public void setFWWOMHUSALV(String fWWOMHUSALV) {
		FWWOMHUSALV = fWWOMHUSALV;
	}

	public String getFWWOMHUSSTR() {
		return FWWOMHUSSTR;
	}

	public void setFWWOMHUSSTR(String fWWOMHUSSTR) {
		FWWOMHUSSTR = fWWOMHUSSTR;
	}

	public String getFWWOMHUSLIV() {
		return FWWOMHUSLIV;
	}

	public void setFWWOMHUSLIV(String fWWOMHUSLIV) {
		FWWOMHUSLIV = fWWOMHUSLIV;
	}

	public String getFWELIGIBLE() {
		return FWELIGIBLE;
	}

	public void setFWELIGIBLE(String fWELIGIBLE) {
		FWELIGIBLE = fWELIGIBLE;
	}

	public String getFWELIGIBLE2() {
		return FWELIGIBLE2;
	}

	public void setFWELIGIBLE2(String fWELIGIBLE2) {
		FWELIGIBLE2 = fWELIGIBLE2;
	}

	public String getFWPSRPREGSTS() {
		return FWPSRPREGSTS;
	}

	public void setFWPSRPREGSTS(String fWPSRPREGSTS) {
		FWPSRPREGSTS = fWPSRPREGSTS;
	}

	public List<Map<String, String>> getPSRFDETAILS() {
		return PSRFDETAILS;
	}

	public void setPSRFDETAILS(List<Map<String, String>> pSRFDETAILS) {
		PSRFDETAILS = pSRFDETAILS;
	}

	public List<Map<String, String>> getMISDETAILS() {
		return MISDETAILS;
	}

	public void setMISDETAILS(List<Map<String, String>> mISDETAILS) {
		MISDETAILS = mISDETAILS;
	}

	@Override
	public ElcoDocument getPreparedData(Elco x) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendPreparedData(Elco t, HouseholdServices y) {
		// TODO Auto-generated method stub
		
	}

	
	
	

}
