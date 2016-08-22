package org.opensrp.camp.dao;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@TypeDiscriminator("doc.type === 'CampDate'")
public class CampDate extends MotechBaseDataObject {
	
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("date")
	private String date;
	
	@JsonProperty("session_id")
	private String session_id;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("username")
	private String username;
	
	@JsonProperty("contact")
	private String contact;
	
	public CampDate() {
		// TODO Auto-generated constructor stub
	}

	public CampDate(String date, String session_id, String status, String username, String contact) {
	    super();
	    this.date = date;
	    this.session_id = session_id;
	    this.status = status;
	    this.username = username;
	    this.contact = contact;
    }

	
    public String getDate() {
    	return date;
    }

	
    public void setDate(String date) {
    	this.date = date;
    }

	
    public String getSession_id() {
    	return session_id;
    }

	
    public void setSession_id(String session_id) {
    	this.session_id = session_id;
    }

	
    public String getStatus() {
    	return status;
    }

	
    public void setStatus(String status) {
    	this.status = status;
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

	
    public static long getSerialversionuid() {
    	return serialVersionUID;
    }

	@Override
    public String toString() {
	    return "CampDate [date=" + date + ", session_id=" + session_id + ", status=" + status + ", username=" + username
	            + ", contact=" + contact + "]";
    }
	
}
