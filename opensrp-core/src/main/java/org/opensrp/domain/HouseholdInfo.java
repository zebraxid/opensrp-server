package org.opensrp.domain;

import java.util.Map;

public class HouseholdInfo {
	
	private String providerName;
	private String providerId;
	private String ccName;
	private String eventType;
	private String dateCreated;
	private Map<?, ?> household;
	private Map<?, ?> householdHead;
	private Long serverVersion;
	
	
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Map<?, ?> getHousehold() {
		return household;
	}
	public void setHousehold(Map<?, ?> household) {
		this.household = household;
	}
	public Map<?, ?> getHouseholdHead() {
		return householdHead;
	}
	public void setHouseholdHead(Map<?, ?> householdHead) {
		this.householdHead = householdHead;
	}
	public String getCcName() {
		return ccName;
	}
	public void setCcName(String ccName) {
		this.ccName = ccName;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public Long getServerVersion() {
		return serverVersion;
	}
	public void setServerVersion(Long serverVersion) {
		this.serverVersion = serverVersion;
	}
	

}
