package org.opensrp.etl.document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MotherDocument extends CommonInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final MotherDocument INSTANCE = new MotherDocument();
	private MotherDocument() {
		// TODO Auto-generated constructor stub
	}
	public static MotherDocument getInstance(){
		return INSTANCE;
	}
	private String anc1_current_form_status;	
	private String anc2_current_form_status;	
	private String anc3_current_form_status;	
	private String anc4_current_form_status;	
	private String pnc1_current_form_status;	
	private String pnc2_current_form_status;	
	private String pnc3_current_form_status;	
	private String bnf_current_form_status;
	private String mother_husname;	
	private String mother_wom_nid;	
	private String mother_wom_bid;	
	private String mother_wom_age;
	private String mother_valid;
	private String FWVG;	
	private String FWHRP;	
	private String FWHR_PSR;	
	private String FWFLAGVALUE;	
	private String FWSORTVALUE;	
	private String mother_wom_lmp;	
	private String relationalid;	
	private String isClosed;	
	private Map<String, String> ancVisitOne;	
	private Map<String, String> ancVisitTwo;	
	private Map<String, String> ancVisitThree;	
	private Map<String, String> ancVisitFour;	
	private List<Map<String, String>> bnfVisitDetails;	
	private Map<String, String> pncVisitOne;	
	private Map<String, String> pncVisitTwo;	
	private Map<String, String> pncVisitThree;
	
	public String getAnc1_current_form_status() {
		return anc1_current_form_status;
	}
	public void setAnc1_current_form_status(String anc1_current_form_status) {
		this.anc1_current_form_status = anc1_current_form_status;
	}
	public String getAnc2_current_form_status() {
		return anc2_current_form_status;
	}
	public void setAnc2_current_form_status(String anc2_current_form_status) {
		this.anc2_current_form_status = anc2_current_form_status;
	}
	public String getAnc3_current_form_status() {
		return anc3_current_form_status;
	}
	public void setAnc3_current_form_status(String anc3_current_form_status) {
		this.anc3_current_form_status = anc3_current_form_status;
	}
	public String getAnc4_current_form_status() {
		return anc4_current_form_status;
	}
	public void setAnc4_current_form_status(String anc4_current_form_status) {
		this.anc4_current_form_status = anc4_current_form_status;
	}
	public String getPnc1_current_form_status() {
		return pnc1_current_form_status;
	}
	public void setPnc1_current_form_status(String pnc1_current_form_status) {
		this.pnc1_current_form_status = pnc1_current_form_status;
	}
	public String getPnc2_current_form_status() {
		return pnc2_current_form_status;
	}
	public void setPnc2_current_form_status(String pnc2_current_form_status) {
		this.pnc2_current_form_status = pnc2_current_form_status;
	}
	public String getPnc3_current_form_status() {
		return pnc3_current_form_status;
	}
	public void setPnc3_current_form_status(String pnc3_current_form_status) {
		this.pnc3_current_form_status = pnc3_current_form_status;
	}
	public String getBnf_current_form_status() {
		return bnf_current_form_status;
	}
	public void setBnf_current_form_status(String bnf_current_form_status) {
		this.bnf_current_form_status = bnf_current_form_status;
	}
	public String getMother_husname() {
		return mother_husname;
	}
	public void setMother_husname(String mother_husname) {
		this.mother_husname = mother_husname;
	}
	public String getMother_wom_nid() {
		return mother_wom_nid;
	}
	public void setMother_wom_nid(String mother_wom_nid) {
		this.mother_wom_nid = mother_wom_nid;
	}
	public String getMother_wom_bid() {
		return mother_wom_bid;
	}
	public void setMother_wom_bid(String mother_wom_bid) {
		this.mother_wom_bid = mother_wom_bid;
	}
	public String getMother_wom_age() {
		return mother_wom_age;
	}
	public void setMother_wom_age(String mother_wom_age) {
		this.mother_wom_age = mother_wom_age;
	}
	public String getMother_valid() {
		return mother_valid;
	}
	public void setMother_valid(String mother_valid) {
		this.mother_valid = mother_valid;
	}
	public String getFWVG() {
		return FWVG;
	}
	public void setFWVG(String fWVG) {
		FWVG = fWVG;
	}
	public String getFWHRP() {
		return FWHRP;
	}
	public void setFWHRP(String fWHRP) {
		FWHRP = fWHRP;
	}
	public String getFWHR_PSR() {
		return FWHR_PSR;
	}
	public void setFWHR_PSR(String fWHR_PSR) {
		FWHR_PSR = fWHR_PSR;
	}
	public String getFWFLAGVALUE() {
		return FWFLAGVALUE;
	}
	public void setFWFLAGVALUE(String fWFLAGVALUE) {
		FWFLAGVALUE = fWFLAGVALUE;
	}
	public String getFWSORTVALUE() {
		return FWSORTVALUE;
	}
	public void setFWSORTVALUE(String fWSORTVALUE) {
		FWSORTVALUE = fWSORTVALUE;
	}
	public String getMother_wom_lmp() {
		return mother_wom_lmp;
	}
	public void setMother_wom_lmp(String mother_wom_lmp) {
		this.mother_wom_lmp = mother_wom_lmp;
	}
	public String getRelationalid() {
		return relationalid;
	}
	public void setRelationalid(String relationalid) {
		this.relationalid = relationalid;
	}
	public String getIsClosed() {
		return isClosed;
	}
	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}
	public Map<String, String> getAncVisitOne() {
		return ancVisitOne;
	}
	public void setAncVisitOne(Map<String, String> ancVisitOne) {
		this.ancVisitOne = ancVisitOne;
	}
	public Map<String, String> getAncVisitTwo() {
		return ancVisitTwo;
	}
	public void setAncVisitTwo(Map<String, String> ancVisitTwo) {
		this.ancVisitTwo = ancVisitTwo;
	}
	public Map<String, String> getAncVisitThree() {
		return ancVisitThree;
	}
	public void setAncVisitThree(Map<String, String> ancVisitThree) {
		this.ancVisitThree = ancVisitThree;
	}
	public Map<String, String> getAncVisitFour() {
		return ancVisitFour;
	}
	public void setAncVisitFour(Map<String, String> ancVisitFour) {
		this.ancVisitFour = ancVisitFour;
	}
	public List<Map<String, String>> getBnfVisitDetails() {
		return bnfVisitDetails;
	}
	public void setBnfVisitDetails(List<Map<String, String>> bnfVisitDetails) {
		this.bnfVisitDetails = bnfVisitDetails;
	}
	public Map<String, String> getPncVisitOne() {
		return pncVisitOne;
	}
	public void setPncVisitOne(Map<String, String> pncVisitOne) {
		this.pncVisitOne = pncVisitOne;
	}
	public Map<String, String> getPncVisitTwo() {
		return pncVisitTwo;
	}
	public void setPncVisitTwo(Map<String, String> pncVisitTwo) {
		this.pncVisitTwo = pncVisitTwo;
	}
	public Map<String, String> getPncVisitThree() {
		return pncVisitThree;
	}
	public void setPncVisitThree(Map<String, String> pncVisitThree) {
		this.pncVisitThree = pncVisitThree;
	}
	

}
