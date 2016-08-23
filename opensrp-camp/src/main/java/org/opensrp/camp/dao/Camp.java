package org.opensrp.camp.dao;


import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.docref.CascadeType;
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
	@JsonProperty("username")
	private String username;	
	@JsonProperty("contact")
	private String contact;
	@JsonProperty("created")
	private String created;
	@JsonProperty("user")
	private String user;
	
		
	@DocumentReferences(fetch = FetchType.LAZY, descendingSortOrder = true, orderBy = "date", backReference = "session_id")
	private Set<CampDate> camp_dates;
	
    public Set<CampDate> getCampDates() {
    	return camp_dates;
    }
	
    public void setCampDates(Set<CampDate> campDates) {
    	this.camp_dates = campDates;
    }

	public Camp() {
		// TODO Auto-generated constructor stub
	}

	public Camp(String session_name, String session_location, String total_hh, String total_population,
        String total_adolescent, String total_women, String total_child0, String total_child1, String total_child2,
        String username, String contact, String created, String user, Set<CampDate> camp_dates) {
	    super();
	    this.session_name = session_name;
	    this.session_location = session_location;
	    this.total_hh = total_hh;
	    this.total_population = total_population;
	    this.total_adolescent = total_adolescent;
	    this.total_women = total_women;
	    this.total_child0 = total_child0;
	    this.total_child1 = total_child1;
	    this.total_child2 = total_child2;
	    this.username = username;
	    this.contact = contact;
	    this.created = created;
	    this.user = user;
	    this.camp_dates = camp_dates;
    }

	
    public String getSession_name() {
    	return session_name;
    }

	
    public void setSession_name(String session_name) {
    	this.session_name = session_name;
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

	
    public String getUsername() {
    	return username;
    }

	
    public void setUsername(String username) {
    	this.username = username;
    }

	
    public String getContact() {
    	return contact;
    }

	
    public void setContact(String contact) {
    	this.contact = contact;
    }

	
    public String getCreated() {
    	return created;
    }

	
    public void setCreated(String created) {
    	this.created = created;
    }

	
    public String getUser() {
    	return user;
    }

	
    public void setUser(String user) {
    	this.user = user;
    }

	
    public Set<CampDate> getCamp_dates() {
    	return camp_dates;
    }

	
    public void setCamp_dates(Set<CampDate> camp_dates) {
    	this.camp_dates = camp_dates;
    }

	@Override
    public String toString() {
	    return "Camp [session_name=" + session_name + ", session_location=" + session_location + ", total_hh=" + total_hh
	            + ", total_population=" + total_population + ", total_adolescent=" + total_adolescent + ", total_women="
	            + total_women + ", total_child0=" + total_child0 + ", total_child1=" + total_child1 + ", total_child2="
	            + total_child2 + ", username=" + username + ", contact=" + contact + ", created=" + created + ", user="
	            + user + ", camp_dates=" + camp_dates + "]";
    }
	
	
	
}
