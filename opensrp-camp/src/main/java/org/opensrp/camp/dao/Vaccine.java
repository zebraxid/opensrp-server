package org.opensrp.camp.dao;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;


@TypeDiscriminator("doc.type === 'Vaccine'")
public class Vaccine extends MotechBaseDataObject{
	
	 /**
     * 
     */
    private static final long serialVersionUID = 1L;
		@JsonProperty
	    private String health_assistant;
	    @JsonProperty
	    private String clientId;
	    @JsonProperty
	    private String actionId;	    
	    @JsonProperty
	    private String beneficiaryType;
	    @JsonProperty
	    private String vaccineName;
	    @JsonProperty
	    private String scheduleDate;
	    @JsonProperty
	    private String expiredDate;
	    @JsonProperty
	    private String status;
	    @JsonProperty
	    private String missedCount;
	    @JsonProperty
	    private String createdDate;
	    @JsonProperty
	    private String executionDate;
		public Vaccine(String health_assistant, String clientId, String actionId, String beneficiaryType,
            String vaccineName, String scheduleDate, String expiredDate, String status, String missedCount,
            String createdDate, String executionDate) {
	        super();
	        this.health_assistant = health_assistant;
	        this.clientId = clientId;
	        this.actionId = actionId;
	        this.beneficiaryType = beneficiaryType;
	        this.vaccineName = vaccineName;
	        this.scheduleDate = scheduleDate;
	        this.expiredDate = expiredDate;
	        this.status = status;
	        this.missedCount = missedCount;
	        this.createdDate = createdDate;
	        this.executionDate = executionDate;
        }
		
        public String getHealth_assistant() {
        	return health_assistant;
        }
		
        public String getClientId() {
        	return clientId;
        }
		
        public String getActionId() {
        	return actionId;
        }
		
        public String getBeneficiaryType() {
        	return beneficiaryType;
        }
		
        public String getVaccineName() {
        	return vaccineName;
        }
		
        public String getScheduleDate() {
        	return scheduleDate;
        }
		
        public String getExpiredDate() {
        	return expiredDate;
        }
		
        public String getStatus() {
        	return status;
        }
		
        public String getMissedCount() {
        	return missedCount;
        }
		
        public String getCreatedDate() {
        	return createdDate;
        }
		
        public String getExecutionDate() {
        	return executionDate;
        }
		
        public void setHealth_assistant(String health_assistant) {
        	this.health_assistant = health_assistant;
        }
		
        public void setClientId(String clientId) {
        	this.clientId = clientId;
        }
		
        public void setActionId(String actionId) {
        	this.actionId = actionId;
        }
		
        public void setBeneficiaryType(String beneficiaryType) {
        	this.beneficiaryType = beneficiaryType;
        }
		
        public void setVaccineName(String vaccineName) {
        	this.vaccineName = vaccineName;
        }
		
        public void setScheduleDate(String scheduleDate) {
        	this.scheduleDate = scheduleDate;
        }
		
        public void setExpiredDate(String expiredDate) {
        	this.expiredDate = expiredDate;
        }
		
        public void setStatus(String status) {
        	this.status = status;
        }
		
        public void setMissedCount(String missedCount) {
        	this.missedCount = missedCount;
        }
		
        public void setCreatedDate(String createdDate) {
        	this.createdDate = createdDate;
        }
		
        public void setExecutionDate(String executionDate) {
        	this.executionDate = executionDate;
        }

		@Override
        public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ((actionId == null) ? 0 : actionId.hashCode());
	        result = prime * result + ((beneficiaryType == null) ? 0 : beneficiaryType.hashCode());
	        result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
	        result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
	        result = prime * result + ((executionDate == null) ? 0 : executionDate.hashCode());
	        result = prime * result + ((expiredDate == null) ? 0 : expiredDate.hashCode());
	        result = prime * result + ((health_assistant == null) ? 0 : health_assistant.hashCode());
	        result = prime * result + ((missedCount == null) ? 0 : missedCount.hashCode());
	        result = prime * result + ((scheduleDate == null) ? 0 : scheduleDate.hashCode());
	        result = prime * result + ((status == null) ? 0 : status.hashCode());
	        result = prime * result + ((vaccineName == null) ? 0 : vaccineName.hashCode());
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
	        Vaccine other = (Vaccine) obj;
	        if (actionId == null) {
		        if (other.actionId != null)
			        return false;
	        } else if (!actionId.equals(other.actionId))
		        return false;
	        if (beneficiaryType == null) {
		        if (other.beneficiaryType != null)
			        return false;
	        } else if (!beneficiaryType.equals(other.beneficiaryType))
		        return false;
	        if (clientId == null) {
		        if (other.clientId != null)
			        return false;
	        } else if (!clientId.equals(other.clientId))
		        return false;
	        if (createdDate == null) {
		        if (other.createdDate != null)
			        return false;
	        } else if (!createdDate.equals(other.createdDate))
		        return false;
	        if (executionDate == null) {
		        if (other.executionDate != null)
			        return false;
	        } else if (!executionDate.equals(other.executionDate))
		        return false;
	        if (expiredDate == null) {
		        if (other.expiredDate != null)
			        return false;
	        } else if (!expiredDate.equals(other.expiredDate))
		        return false;
	        if (health_assistant == null) {
		        if (other.health_assistant != null)
			        return false;
	        } else if (!health_assistant.equals(other.health_assistant))
		        return false;
	        if (missedCount == null) {
		        if (other.missedCount != null)
			        return false;
	        } else if (!missedCount.equals(other.missedCount))
		        return false;
	        if (scheduleDate == null) {
		        if (other.scheduleDate != null)
			        return false;
	        } else if (!scheduleDate.equals(other.scheduleDate))
		        return false;
	        if (status == null) {
		        if (other.status != null)
			        return false;
	        } else if (!status.equals(other.status))
		        return false;
	        if (vaccineName == null) {
		        if (other.vaccineName != null)
			        return false;
	        } else if (!vaccineName.equals(other.vaccineName))
		        return false;
	        return true;
        }

		@Override
        public String toString() {
	        return "Vaccine [health_assistant=" + health_assistant + ", clientId=" + clientId + ", actionId=" + actionId
	                + ", beneficiaryType=" + beneficiaryType + ", vaccineName=" + vaccineName + ", scheduleDate="
	                + scheduleDate + ", expiredDate=" + expiredDate + ", status=" + status + ", missedCount=" + missedCount
	                + ", createdDate=" + createdDate + ", executionDate=" + executionDate + "]";
        }
	    
	
}
