package org.opensrp.register.mcare.domain;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

public class BaseRegistry<T> {

	@JsonProperty
	private String caseId;
	@JsonProperty
	private String instanceId;
	@JsonProperty
	private String provider;
	@JsonProperty
	private String locationId;
	@JsonProperty
	private String today;
	@JsonProperty
	private String start;
	@JsonProperty
	private String end;
	@JsonProperty	
	private String country;
	@JsonProperty
	private String division;
	@JsonProperty
	private String district;
	@JsonProperty
	private String upazila;
	@JsonProperty
	private String union;
	@JsonProperty
	private String ward;
	@JsonProperty
	private String block;
	@JsonProperty
	private String blockNo;
	@JsonProperty
	private Map<String, String> details;
	
	public BaseRegistry(){
		
	}
	
	public BaseRegistry<T> setCASEID(String caseId) {
		this.caseId = caseId;
		return this;
	}
	
	public BaseRegistry<T> setDetails(Map<String, String> details) {
        this.details = new HashMap<>(details);
        return this;
    }
	public Map<String, String> details() {
		if (details == null)
			this.details = new HashMap<>();
		return details;
	}
}
