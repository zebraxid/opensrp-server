package org.opensrp.register.mcare.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;
import org.opensrp.dto.PrivilegeDTO;

@TypeDiscriminator("doc.type === 'Location'")
public class Location extends MotechBaseDataObject {
	
	@JsonProperty
	private String name;
	@JsonProperty
	private String status;
	@JsonProperty
	private String location_code;
	@JsonProperty
	private String location_type;
	@JsonProperty
	private SimplifiedLocation parent;
	@JsonProperty
	private List<SimplifiedLocation> children;	
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
	
	public Location(){
		this.created_at = new Date().toString();
		this.updated_at = new Date().toString();
		this.created_by = "Admin";
		this.updated_by = null;
	}
	public Location withName(String name) {
		this.name = name;
		return this;
	}
	public Location withStatus(String status) {
		this.status = status;
		return this;
	}
	public Location withParent(SimplifiedLocation parent){
		this.parent = parent;
		return this;
	}
	public Location withChildren(List<SimplifiedLocation> children){
		this.children = children;
		return this;
	}
	public Location withLocationType(String locationType){
		this.location_type = locationType;
		return this;
	}
	public Location withLoactionCode(String locationCode){
		this.location_code = locationCode;
		return this;
	}
	public String getName() {
		return name;
	}
	public String getStatus() {
		return status;
	}
	public String getLocationType(){
		return location_type;
	}
	public String getLocationCode(){
		return location_code;
	}
	public SimplifiedLocation getParent(){
		return parent;
	}
	public List<SimplifiedLocation> getChildren(){
		return children;
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
