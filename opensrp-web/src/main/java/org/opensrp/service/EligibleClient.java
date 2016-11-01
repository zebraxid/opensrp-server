package org.opensrp.service;


public class EligibleClient {
	private String entityId;
	private Integer missedCount;
	
	public EligibleClient(){
		
	}
	
    public EligibleClient(String entityId, Integer missedCount) {	    
	    this.entityId = entityId;
	    this.missedCount = missedCount;
    }

	public String getEntityId() {
    	return entityId;
    }
	
    public Integer getMissedCount() {
    	return missedCount;
    }
	
    public void setEntityId(String entityId) {
    	this.entityId = entityId;
    }
	
    public void setMissedCount(Integer missedCount) {
    	this.missedCount = missedCount;
    }
	
	
}
