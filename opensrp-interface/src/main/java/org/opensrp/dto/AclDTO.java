package org.opensrp.dto;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class AclDTO {

	@JsonProperty
	private String roleName;
	@JsonProperty
	private Map<String, String> accessTokens;

	public AclDTO(String roleName, Map<String, String> accessTokens) {
		this.roleName = roleName;
		this.accessTokens = accessTokens;
	}
	public AclDTO(){
		
	}
	public AclDTO withRoleName(String roleName) {
		this.roleName = roleName;
		return this;
	}
	
	public AclDTO withAccessTokens(Map<String, String> accessTokens) {
        this.accessTokens = new HashMap<>(accessTokens);
        return this;
    }
	public String getRoleName() {
		return roleName;
	}

	public Map<String, String> getAccessTokens() {
		return accessTokens;
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
