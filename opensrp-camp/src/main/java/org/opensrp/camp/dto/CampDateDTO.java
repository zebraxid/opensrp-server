package org.opensrp.camp.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;


public class CampDateDTO {
	@JsonProperty("date")
	private Date date;
	@JsonProperty("session_id")
	private String session_id;	
	@JsonProperty("status")
	private String status;
	@JsonProperty("username")
	private String username;	
	@JsonProperty("contact")
	private String contact;
	
    public CampDateDTO() {
	    // TODO Auto-generated constructor stub
    }

	public CampDateDTO(Date date, String session_id, String status, String username, String contact) {
	    super();
	    this.date = date;
	    this.session_id = session_id;
	    this.status = status;
	    this.username = username;
	    this.contact = contact;
    }

	
    public Date getDate() {
    	return date;
    }

	
    public void setDate(Date date) {
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

	@Override
    public String toString() {
	    return "CampDateDTO [date=" + date + ", session_id=" + session_id + ", status=" + status + ", username=" + username
	            + ", contact=" + contact + "]";
    }

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((contact == null) ? 0 : contact.hashCode());
	    result = prime * result + ((date == null) ? 0 : date.hashCode());
	    result = prime * result + ((session_id == null) ? 0 : session_id.hashCode());
	    result = prime * result + ((status == null) ? 0 : status.hashCode());
	    result = prime * result + ((username == null) ? 0 : username.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    CampDateDTO other = (CampDateDTO) obj;
	    if (contact == null) {
		    if (other.contact != null)
			    return false;
	    } else if (!contact.equals(other.contact))
		    return false;
	    if (date == null) {
		    if (other.date != null)
			    return false;
	    } else if (!date.equals(other.date))
		    return false;
	    if (session_id == null) {
		    if (other.session_id != null)
			    return false;
	    } else if (!session_id.equals(other.session_id))
		    return false;
	    if (status == null) {
		    if (other.status != null)
			    return false;
	    } else if (!status.equals(other.status))
		    return false;
	    if (username == null) {
		    if (other.username != null)
			    return false;
	    } else if (!username.equals(other.username))
		    return false;
	    return true;
    }
    
	
}
