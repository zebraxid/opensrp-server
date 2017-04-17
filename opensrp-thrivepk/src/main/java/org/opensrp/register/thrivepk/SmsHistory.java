package org.opensrp.register.thrivepk;

import java.util.Map;

import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;
import org.opensrp.domain.BaseDataObject;

@TypeDiscriminator("doc.type === 'SmsHistory'")
public class SmsHistory extends BaseDataObject {
	private String status;
	private String cause;
	private String errorDetails;
	private String baseEntityId;
	private DateTime duedate;
	private DateTime sentDate;
	private Map<String, Object> details;
	
	public SmsHistory(){
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getErrorDetails() {
		return errorDetails;
	}
	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}
	public String getBaseEntityId() {
		return baseEntityId;
	}
	public void setBaseEntityId(String baseEntityId) {
		this.baseEntityId = baseEntityId;
	}

	public DateTime getDuedate() {
		return duedate;
	}

	public void setDuedate(DateTime duedate) {
		this.duedate = duedate;
	}

	public DateTime getSentDate() {
		return sentDate;
	}

	public void setSentDate(DateTime sentDate) {
		this.sentDate = sentDate;
	}

	public Map<String, Object> getDetails() {
		return details;
	}

	public void setDetails(Map<String, Object> details) {
		this.details = details;
	}
	
	
}
