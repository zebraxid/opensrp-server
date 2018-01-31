package org.opensrp.domain;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@TypeDiscriminator("doc.type == 'SMSLog'")
public class SMSLog extends MotechBaseDataObject {
	
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty
	private String mobileNo;
	
	@JsonProperty
	private String smsText;
	
	@JsonProperty
	private Date sentTime;
	
	@JsonProperty
	private String providerName;
	
	@JsonProperty
	private String campDate;
	
	@JsonProperty
	private String campName;
	
	@JsonProperty
	private String centerName;
	
	public String getMobileNo() {
		return mobileNo;
	}
	
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public String getSmsText() {
		return smsText;
	}
	
	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}
	
	public Date getSentTime() {
		return sentTime;
	}
	
	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}
	
	public String getProviderName() {
		return providerName;
	}
	
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	
	public String getCampDate() {
		return campDate;
	}
	
	public void setCampDate(String campDate) {
		this.campDate = campDate;
	}
	
	public String getCampName() {
		return campName;
	}
	
	public void setCampName(String campName) {
		this.campName = campName;
	}
	
	public String getCenterName() {
		return centerName;
	}
	
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}
	
}
