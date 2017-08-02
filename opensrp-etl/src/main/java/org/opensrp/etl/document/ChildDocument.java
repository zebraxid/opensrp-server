package org.opensrp.etl.document;

import java.io.Serializable;
import java.util.Map;

public class ChildDocument extends CommonInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String encc1_current_form_status;	
	private String encc2_current_form_status;	
	private String encc3_current_form_status;	
	private String isClosed;
	private Map<String, String> enccVisitOne;	
	private Map<String, String> enccVisitTwo;	
	private Map<String, String> enccVisitThree;
	private ChildDocument() {
		// TODO Auto-generated constructor stub
	}
	
	private static final ChildDocument INSTANCE = new ChildDocument();
	public static ChildDocument getInstance() {
		return INSTANCE;
	}
	public String getEncc1_current_form_status() {
		return encc1_current_form_status;
	}
	public void setEncc1_current_form_status(String encc1_current_form_status) {
		this.encc1_current_form_status = encc1_current_form_status;
	}
	public String getEncc2_current_form_status() {
		return encc2_current_form_status;
	}
	public void setEncc2_current_form_status(String encc2_current_form_status) {
		this.encc2_current_form_status = encc2_current_form_status;
	}
	public String getEncc3_current_form_status() {
		return encc3_current_form_status;
	}
	public void setEncc3_current_form_status(String encc3_current_form_status) {
		this.encc3_current_form_status = encc3_current_form_status;
	}
	public String getIsClosed() {
		return isClosed;
	}
	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}
	public Map<String, String> getEnccVisitOne() {
		return enccVisitOne;
	}
	public void setEnccVisitOne(Map<String, String> enccVisitOne) {
		this.enccVisitOne = enccVisitOne;
	}
	public Map<String, String> getEnccVisitTwo() {
		return enccVisitTwo;
	}
	public void setEnccVisitTwo(Map<String, String> enccVisitTwo) {
		this.enccVisitTwo = enccVisitTwo;
	}
	public Map<String, String> getEnccVisitThree() {
		return enccVisitThree;
	}
	public void setEnccVisitThree(Map<String, String> enccVisitThree) {
		this.enccVisitThree = enccVisitThree;
	}
	
	

}
