package org.opensrp.rest.register.DTO;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;


public class Member {
	@JsonProperty
	private String caseId;
	@JsonProperty
	private String INSTANCEID;
	@JsonProperty
	private String PROVIDERID;	
	@JsonProperty
	private String Member_DIVISION;
	@JsonProperty
	private String Member_DISTRICT;
	@JsonProperty
	private String Member_UPAZILLA;	
	@JsonProperty
	private String Member_UNION;
	@JsonProperty
	private String Member_WARD;
	@JsonProperty
	private String Member_BLOCK;	
	@JsonProperty
	private String Member_GPS;
	@JsonProperty
	private String Is_woman;
	@JsonProperty
	private String Is_child;
	@JsonProperty
	private String Member_Fname;
	@JsonProperty
	private String Member_LName;
	@JsonProperty
	private String Husband_name;
	@JsonProperty
	private String WomanInfo;
	@JsonProperty
	private String Reg_No;
	@JsonProperty
	private String calc_dob;
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
