package org.opensrp.rest.register.DTO;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
public class HouseholdEntryDTO {
	@JsonProperty
	private String caseId;
	@JsonProperty("INSTANCEID")
	private String INSTANCEID;
	@JsonProperty("PROVIDERID")
	private String PROVIDERID;	
	@JsonProperty("TODAY")
	private String TODAY;
	@JsonProperty("START")
	private String START;
	@JsonProperty("END")
	private String END;
	@JsonProperty
	private String Date_Of_Reg;
	@JsonProperty
	private String HoH_HID;
	@JsonProperty("COUNTRY")
	private String COUNTRY;
	@JsonProperty("DIVISION")
	private String DIVISION;
	@JsonProperty("DISTRICT")
	private String DISTRICT;
	@JsonProperty("UPAZILLA")
	private String UPAZILLA;
	@JsonProperty("UNION")
	private String UNION;
	@JsonProperty("WARD")
	private String WARD;
	@JsonProperty
	private String BLOCK;	
	@JsonProperty
	private String HHID;
	@JsonProperty
	private String HoH_Reg_No;
	@JsonProperty
	private String GPS;
	@JsonProperty
	private String HoH_Fname;
	@JsonProperty
	private String HoH_birth_date;	
	@JsonProperty
	private String HoH_Lname;
	@JsonProperty
	private String HoH_Gender;
	@JsonProperty
	private String HoH_NID;
	@JsonProperty
	private String HoH_BRID;
	@JsonProperty
	private String HoH_Mobile_No;
	@JsonProperty
	private String HH_Member_No;
	@JsonProperty
	private String id;
	
	public HouseholdEntryDTO() {
		
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