package org.opensrp.dao;


import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.docref.DocumentReferences;
import org.ektorp.docref.FetchType;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;



@TypeDiscriminator("doc.type === 'Camp'")
public class Camp extends MotechBaseDataObject {
	
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("session_name")
	private String session_name;
	
	@JsonProperty("session_date")
	private String session_date;
	
	@JsonProperty("session_location")
	private String session_location;
	
	@JsonProperty("total_hh")
	private String total_hh;
	
	@JsonProperty("total_population")
	private String total_population;
	
	@JsonProperty("total_adolescent")
	private String total_adolescent;
	
	@JsonProperty("total_women")
	private String total_women;
	
	@JsonProperty("total_child0")
	private String total_child0;
	
	@JsonProperty("total_child1")
	private String total_child1;
	
	@JsonProperty("total_child2")
	private String total_child2;
	
	@DocumentReferences(fetch = FetchType.LAZY, descendingSortOrder = true, orderBy = "username", backReference = "session_id")
	private Set<CampDate> news;
	
	public Camp() {
		// TODO Auto-generated constructor stub
	}
	
	public Camp(String session_name, String session_date, String session_location, String total_hh, String total_population,
	    String total_adolescent, String total_women, String total_child0, String total_child1, String total_child2) {
		super();
		this.session_name = session_name;
		this.session_date = session_date;
		this.session_location = session_location;
		this.total_hh = total_hh;
		this.total_population = total_population;
		this.total_adolescent = total_adolescent;
		this.total_women = total_women;
		this.total_child0 = total_child0;
		this.total_child1 = total_child1;
		this.total_child2 = total_child2;
	}
	
	public String getSession_name() {
		return session_name;
	}
	
	public void setSession_name(String session_name) {
		this.session_name = session_name;
	}
	
	public String getSession_date() {
		return session_date;
	}
	
	public void setSession_date(String session_date) {
		this.session_date = session_date;
	}
	
	public String getSession_location() {
		return session_location;
	}
	
	public void setSession_location(String session_location) {
		this.session_location = session_location;
	}
	
	public String getTotal_hh() {
		return total_hh;
	}
	
	public void setTotal_hh(String total_hh) {
		this.total_hh = total_hh;
	}
	
	public String getTotal_population() {
		return total_population;
	}
	
	public void setTotal_population(String total_population) {
		this.total_population = total_population;
	}
	
	public String getTotal_adolescent() {
		return total_adolescent;
	}
	
	public void setTotal_adolescent(String total_adolescent) {
		this.total_adolescent = total_adolescent;
	}
	
	public String getTotal_women() {
		return total_women;
	}
	
	public void setTotal_women(String total_women) {
		this.total_women = total_women;
	}
	
	public String getTotal_child0() {
		return total_child0;
	}
	
	public void setTotal_child0(String total_child0) {
		this.total_child0 = total_child0;
	}
	
	public String getTotal_child1() {
		return total_child1;
	}
	
	public void setTotal_child1(String total_child1) {
		this.total_child1 = total_child1;
	}
	
	public String getTotal_child2() {
		return total_child2;
	}
	
	public void setTotal_child2(String total_child2) {
		this.total_child2 = total_child2;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
