package org.opensrp.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class CountServiceDTO {
	@JsonProperty
	public int householdTotalCount;
	@JsonProperty
	private int householdTodayCount;
	@JsonProperty
	private int householdThisWeekCount;
	@JsonProperty
	private int householdThisMonthCount;
	@JsonProperty
	private int elcoTotalCount;
	@JsonProperty
	private int elcoTodayCount;
	@JsonProperty
	private int elcoThisWeekCount;
	@JsonProperty
	private int elcoThisMonthCount;
	@JsonProperty
	private int pwTotalCount;
	@JsonProperty
	private int pwTodayCount;
	@JsonProperty
	private int pwThisWeekCount;
	@JsonProperty
	private int pwThisMonthCount;
	
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
