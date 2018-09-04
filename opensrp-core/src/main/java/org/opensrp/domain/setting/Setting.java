package org.opensrp.domain.setting;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.opensrp.domain.BaseDataObject;

@TypeDiscriminator("doc.type == 'Setting'")
public class Setting extends BaseDataObject {
	
	private static final long serialVersionUID = 1890883609898207738L;
	
	@JsonProperty
	private String key;
	
	@JsonProperty
	private String value;
	
	@JsonProperty
	private Long timestamp;
	
	@JsonProperty
	private String label;
	
	@JsonProperty
	private String description;
	
	@JsonProperty
	private String teamId;
	
	@JsonProperty
	private String providerId;
	
	@JsonProperty
	private String locationId;
	
	@JsonProperty
	private String childLocationId;
	
	@JsonProperty
	private String version;
	
	@JsonProperty
	private Long serverVersion;
	
	@JsonProperty
	private String type;
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTeamId() {
		return teamId;
	}
	
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	
	public String getProviderId() {
		return providerId;
	}
	
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
	public String getLocationId() {
		return locationId;
	}
	
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	
	public String getChildLocationId() {
		return childLocationId;
	}
	
	public void setChildLocationId(String childLocationId) {
		this.childLocationId = childLocationId;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public Long getServerVersion() {
		return serverVersion;
	}
	
	public void setServerVersion(Long serverVersion) {
		this.serverVersion = serverVersion;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}
