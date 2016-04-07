package org.opensrp.register.mcare.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@TypeDiscriminator("doc.type === 'Privilege'")
public class Privilege extends MotechBaseDataObject {
	
	@JsonProperty
	private String name;
	@JsonProperty
	private String status;
	@JsonProperty
	private Map<String, String> meta;
	@JsonProperty
	private String created_at;
	@JsonProperty
	private String updated_at;
	@JsonProperty
	private String created_by;
	@JsonProperty
	private String updated_by;

	public Privilege()
	{
		//accessTokens = new HashMap<String, String>();
		this.created_at = new Date().toString();
		this.updated_at = new Date().toString();
		this.created_by = "Admin";
		this.updated_by = null;
	}
	public Privilege withName(String privilegeName) {
		this.name = privilegeName;
		return this;
	}
	public Privilege withStatus(String status) {
		this.status = status;
		return this;
	}
	
	public Privilege withAccessTokens(Map<String, String> accessTokens) {
        //this.accessTokens = new HashMap<>(accessTokens);
        return this;
    }
	
	public String getName() {
		return name;
	}
	public String getStatus() {
		return status;
	}

	/*public Map<String, String> getAccessTokens() {
		return accessTokens;
	}*/
	
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
