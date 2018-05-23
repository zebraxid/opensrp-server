package org.opensrp.dto.rapidpro;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class AnnouncedClient {
	
	public AnnouncedClient(String baseEntityId, String mobileNo, String clientType, String vaccineDue, String vaccinationDate) {
		super();
		this.baseEntityId = baseEntityId;
		this.mobileNo = mobileNo;
		this.clientType = clientType;
		this.vaccineDue = vaccineDue;
		this.vaccinationDate = vaccinationDate;
	}
	
	@JsonProperty
	private String baseEntityId;
	
	@JsonProperty
	private String mobileNo;
	
	@JsonProperty
	private String clientType;
	
	@JsonProperty
	private String vaccineDue;
	
	@JsonProperty
	private String vaccinationDate;
	
	public String getBaseEntityId() {
		return baseEntityId;
	}
	
	public void setBaseEntityId(String baseEntityId) {
		this.baseEntityId = baseEntityId;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}
	
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public String getClientType() {
		return clientType;
	}
	
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	
	public String getVaccineDue() {
		return vaccineDue;
	}
	
	public void setVaccineDue(String vaccineDue) {
		this.vaccineDue = vaccineDue;
	}
	
	public String getVaccinationDate() {
		return vaccinationDate;
	}
	
	public void setVaccinationDate(String vaccinationDate) {
		this.vaccinationDate = vaccinationDate;
	}
	
	@Override
	public final boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}
	
	@Override
	public final int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
