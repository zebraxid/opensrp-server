package org.opensrp.register.thrivepk;

import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;
import org.opensrp.domain.BaseDataObject;

@TypeDiscriminator("doc.type === 'SmsHistory'")
public class SmsHistory extends BaseDataObject {
	private Boolean isSent;
	private String cause;
	private String errorDetails;
	private String baseEntityId;
	
	public SmsHistory(){
		
	}
	
	public Boolean getIsSent() {
		return isSent;
	}
	public void setIsSent(Boolean isSent) {
		this.isSent = isSent;
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
	
	
}
