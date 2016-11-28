package org.opensrp.rest.register.DTO;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

public class VaccineEntryDTO {
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
    private boolean status;
    @JsonProperty
    private int missedCount;
    @JsonProperty
    private Date createdDate;
    @JsonProperty
    private DateTime executionDate;
    @JsonProperty
    private long timeStamp; 
	
	public VaccineEntryDTO() {
		
	}	  

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o, "id", "revision");
	}
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "id", "revision");
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
