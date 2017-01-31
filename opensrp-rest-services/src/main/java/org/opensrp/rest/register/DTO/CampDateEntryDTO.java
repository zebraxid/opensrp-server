package org.opensrp.rest.register.DTO;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class CampDateEntryDTO {
	@JsonProperty
    private String id;
	@JsonProperty
    private String session_date;
    @JsonProperty
    private String session_id;
    @JsonProperty
    private String session_name;	    
    @JsonProperty
    private String status;
    @JsonProperty
    private String health_assistant;
    @JsonProperty
    private String contact;
    @JsonProperty
    private long timestamp;
    @JsonProperty("thana")
	private String thana;
	@JsonProperty("union")
	private String union;
	@JsonProperty("ward")
	private String ward;
	@JsonProperty("unit")
	private String unit;
    
	
	public CampDateEntryDTO() {
		
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
