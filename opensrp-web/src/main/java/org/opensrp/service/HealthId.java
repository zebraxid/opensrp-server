package org.opensrp.service;


public class HealthId {
	private String entityId;
	private String healthId;
	private long timeStamp;;
	
	public HealthId(){
		
	}
	
    public String getEntityId() {
    	return entityId;
    }
	
    public String getHealthId() {
    	return healthId;
    }
	
    public void setEntityId(String entityId) {
    	this.entityId = entityId;
    }
	
    public void setHealthId(String healthId) {
    	this.healthId = healthId;
    }

	
    public long getTimeStamp() {
    	return timeStamp;
    }

	
    public void setTimeStamp(long timeStamp) {
    	this.timeStamp = timeStamp;
    }
	
}
