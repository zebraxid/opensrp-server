package org.opensrp.domain.viewconfiguration;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;


public class TestResultsConfiguration extends BaseConfiguration{
	
	@JsonProperty
	private Object resultsConfig;
	
	private Integer followupOverduePeriod;
	
	private Integer smearOverduePeriod;

	public Object getResultsConfig() {
		return resultsConfig;
	}

	public void setResultsConfig(Object resultsConfig) {
		this.resultsConfig = resultsConfig;
	}

	public Integer getFollowupOverduePeriod() {
		return followupOverduePeriod;
	}

	public void setFollowupOverduePeriod(Integer followupOverduePeriod) {
		this.followupOverduePeriod = followupOverduePeriod;
	}

	public Integer getSmearOverduePeriod() {
		return smearOverduePeriod;
	}

	public void setSmearOverduePeriod(Integer smearOverduePeriod) {
		this.smearOverduePeriod = smearOverduePeriod;
	}
}
