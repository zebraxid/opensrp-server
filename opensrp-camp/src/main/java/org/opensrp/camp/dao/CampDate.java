package org.opensrp.camp.dao;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@TypeDiscriminator("doc.type === 'CampDate'")
public class CampDate extends MotechBaseDataObject {
	
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("session_date")
	private String session_date;
	
	@JsonProperty("session_id")
	private String session_id;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("health_assistant")
	private String health_assistant;
	
	@JsonProperty("contact")
	private String contact;
	@JsonProperty("timestamp")
	private long timestamp;
	
	public CampDate() {
		// TODO Auto-generated constructor stub
	}

	public CampDate(String session_date, String session_id, String status, String health_assistant, String contact,
        long timestamp) {
	    super();
	    this.session_date = session_date;
	    this.session_id = session_id;
	    this.status = status;
	    this.health_assistant = health_assistant;
	    this.contact = contact;
	    this.timestamp = timestamp;
    }

	
    public String getSession_date() {
    	return session_date;
    }

	
    public String getSession_id() {
    	return session_id;
    }

	
    public String getStatus() {
    	return status;
    }

	
    public String getHealth_assistant() {
    	return health_assistant;
    }

	
    public String getContact() {
    	return contact;
    }

	
    public long getTimestamp() {
    	return timestamp;
    }

	
    public void setSession_date(String session_date) {
    	this.session_date = session_date;
    }

	
    public void setSession_id(String session_id) {
    	this.session_id = session_id;
    }

	
    public void setStatus(String status) {
    	this.status = status;
    }

	
    public void setHealth_assistant(String health_assistant) {
    	this.health_assistant = health_assistant;
    }

	
    public void setContact(String contact) {
    	this.contact = contact;
    }

	
    public void setTimestamp(long timestamp) {
    	this.timestamp = timestamp;
    }

	@Override
    public String toString() {
	    return "CampDate [session_date=" + session_date + ", session_id=" + session_id + ", status=" + status
	            + ", health_assistant=" + health_assistant + ", contact=" + contact + ", timestamp=" + timestamp + "]";
    }

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((contact == null) ? 0 : contact.hashCode());
	    result = prime * result + ((health_assistant == null) ? 0 : health_assistant.hashCode());
	    result = prime * result + ((session_date == null) ? 0 : session_date.hashCode());
	    result = prime * result + ((session_id == null) ? 0 : session_id.hashCode());
	    result = prime * result + ((status == null) ? 0 : status.hashCode());
	    result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
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
	    CampDate other = (CampDate) obj;
	    if (contact == null) {
		    if (other.contact != null)
			    return false;
	    } else if (!contact.equals(other.contact))
		    return false;
	    if (health_assistant == null) {
		    if (other.health_assistant != null)
			    return false;
	    } else if (!health_assistant.equals(other.health_assistant))
		    return false;
	    if (session_date == null) {
		    if (other.session_date != null)
			    return false;
	    } else if (!session_date.equals(other.session_date))
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
	    if (timestamp != other.timestamp)
		    return false;
	    return true;
    }
	
	
    
}
