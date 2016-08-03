package org.opensrp.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class CountServiceDTO {
	
	@JsonProperty
	private int childTotalCount;
	
	@JsonProperty
	private int childTodayCount;
	
	@JsonProperty
	private int childThisWeekCount;
	
	@JsonProperty
	private int childThisMonthCount;
	
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
	
	public CountServiceDTO() {
		
	}
	
	public CountServiceDTO(int childTotalCount, int childTodayCount, int childThisWeekCount, int childThisMonthCount,
	    int elcoTotalCount, int elcoTodayCount, int elcoThisWeekCount, int elcoThisMonthCount, int pwTotalCount,
	    int pwTodayCount, int pwThisWeekCount, int pwThisMonthCount) {
		super();
		this.childTotalCount = childTotalCount;
		this.childTodayCount = childTodayCount;
		this.childThisWeekCount = childThisWeekCount;
		this.childThisMonthCount = childThisMonthCount;
		this.elcoTotalCount = elcoTotalCount;
		this.elcoTodayCount = elcoTodayCount;
		this.elcoThisWeekCount = elcoThisWeekCount;
		this.elcoThisMonthCount = elcoThisMonthCount;
		this.pwTotalCount = pwTotalCount;
		this.pwTodayCount = pwTodayCount;
		this.pwThisWeekCount = pwThisWeekCount;
		this.pwThisMonthCount = pwThisMonthCount;
		
	}
	
	public int getChildTotalCount() {
		return childTotalCount;
	}
	
	public void setChildTotalCount(int childTotalCount) {
		this.childTotalCount = childTotalCount;
	}
	
	public int getChildTodayCount() {
		return childTodayCount;
	}
	
	public void setChildTodayCount(int childTodayCount) {
		this.childTodayCount = childTodayCount;
	}
	
	public int getChildThisWeekCount() {
		return childThisWeekCount;
	}
	
	public void setChildThisWeekCount(int childThisWeekCount) {
		this.childThisWeekCount = childThisWeekCount;
	}
	
	public int getChildThisMonthCount() {
		return childThisMonthCount;
	}
	
	public void setChildThisMonthCount(int childThisMonthCount) {
		this.childThisMonthCount = childThisMonthCount;
	}
	
	public int getElcoTotalCount() {
		return elcoTotalCount;
	}
	
	public void setElcoTotalCount(int elcoTotalCount) {
		this.elcoTotalCount = elcoTotalCount;
	}
	
	public int getElcoTodayCount() {
		return elcoTodayCount;
	}
	
	public void setElcoTodayCount(int elcoTodayCount) {
		this.elcoTodayCount = elcoTodayCount;
	}
	
	public int getElcoThisWeekCount() {
		return elcoThisWeekCount;
	}
	
	public void setElcoThisWeekCount(int elcoThisWeekCount) {
		this.elcoThisWeekCount = elcoThisWeekCount;
	}
	
	public int getElcoThisMonthCount() {
		return elcoThisMonthCount;
	}
	
	public void setElcoThisMonthCount(int elcoThisMonthCount) {
		this.elcoThisMonthCount = elcoThisMonthCount;
	}
	
	public int getPwTotalCount() {
		return pwTotalCount;
	}
	
	public void setPwTotalCount(int pwTotalCount) {
		this.pwTotalCount = pwTotalCount;
	}
	
	public int getPwTodayCount() {
		return pwTodayCount;
	}
	
	public void setPwTodayCount(int pwTodayCount) {
		this.pwTodayCount = pwTodayCount;
	}
	
	public int getPwThisWeekCount() {
		return pwThisWeekCount;
	}
	
	public void setPwThisWeekCount(int pwThisWeekCount) {
		this.pwThisWeekCount = pwThisWeekCount;
	}
	
	public int getPwThisMonthCount() {
		return pwThisMonthCount;
	}
	
	public void setPwThisMonthCount(int pwThisMonthCount) {
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
