package org.opensrp.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class CountServiceDTO {
	@JsonProperty
	public int householdTotalCount;
	@JsonProperty
	public int householdTodayCount;
	@JsonProperty
	public int householdThisWeekCount;
	@JsonProperty
	public int householdThisMonthCount;
	@JsonProperty
	public int elcoTotalCount;
	@JsonProperty
	public int elcoTodayCount;
	@JsonProperty
	public int elcoThisWeekCount;
	@JsonProperty
	public int elcoThisMonthCount;
	@JsonProperty
	public int pwTotalCount;
	@JsonProperty
	public int pwTodayCount;
	@JsonProperty
	public int pwThisWeekCount;
	@JsonProperty
	public int pwThisMonthCount;
	
	public CountServiceDTO(){
		
	}
	
	public CountServiceDTO(int householdTotalCount, int householdTodayCount,
			int householdThisWeekCount, int householdThisMonthCount,
			int elcoTotalCount, int elcoTodayCount, int elcoThisWeekCount,
			int elcoThisMonthCount, int pwTotalCount, int pwTodayCount,
			int pwThisWeekCount, int pwThisMonthCount) {
		super();
		this.householdTotalCount = householdTotalCount;
		this.householdTodayCount = householdTodayCount;
		this.householdThisWeekCount = householdThisWeekCount;
		this.householdThisMonthCount = householdThisMonthCount;
		this.elcoTotalCount = elcoTotalCount;
		this.elcoTodayCount = elcoTodayCount;
		this.elcoThisWeekCount = elcoThisWeekCount;
		this.elcoThisMonthCount = elcoThisMonthCount;
		this.pwTotalCount = pwTotalCount;
		this.pwTodayCount = pwTodayCount;
		this.pwThisWeekCount = pwThisWeekCount;
		this.pwThisMonthCount = pwThisMonthCount;
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
