package org.opensrp.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class RoleDTO {

	@JsonProperty
	private String userName;
	@JsonProperty
	private String roleName;

	public RoleDTO(String userName, String roleName) {
		this.userName = userName;
		this.roleName = roleName;
	}

	public RoleDTO() {

	}
	public String getUserName() {
		return userName;
	}
	public String getRoleName() {
		return roleName;
	}
	
	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
