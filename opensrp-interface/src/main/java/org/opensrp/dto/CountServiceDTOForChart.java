package org.opensrp.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class CountServiceDTOForChart {
	@JsonProperty
	private int currentMonthWeek1;
	@JsonProperty
	private int currentMonthWeek2;
	@JsonProperty
	private int currentMonthWeek3;
	@JsonProperty
	private int currentMonthWeek4;
	@JsonProperty
	private int currentMonthWeek5;
	@JsonProperty
	private int currentMonth_1Week1;
	@JsonProperty
	private int currentMonth_1Week2;
	@JsonProperty
	private int currentMonth_1Week3;
	@JsonProperty
	private int currentMonth_1Week4;
	@JsonProperty
	private int currentMonth_1Week5;
	@JsonProperty
	private int currentMonth_2Week1;
	@JsonProperty
	private int currentMonth_2Week2;
	@JsonProperty
	private int currentMonth_2Week3;
	@JsonProperty
	private int currentMonth_2Week4;
	@JsonProperty
	private int currentMonth_2Week5;
	@JsonProperty
	private int currentMonth_3Week1;
	@JsonProperty
	private int currentMonth_3Week2;
	@JsonProperty
	private int currentMonth_3Week3;
	@JsonProperty
	private int currentMonth_3Week4;
	@JsonProperty
	private int currentMonth_3Week5;

	public int getCurrentMonthWeek1() {
		return currentMonthWeek1;
	}

	public void setCurrentMonthWeek1(int currentMonthWeek1) {
		this.currentMonthWeek1 = currentMonthWeek1;
	}

	public int getCurrentMonthWeek2() {
		return currentMonthWeek2;
	}

	public void setCurrentMonthWeek2(int currentMonthWeek2) {
		this.currentMonthWeek2 = currentMonthWeek2;
	}

	public int getCurrentMonthWeek3() {
		return currentMonthWeek3;
	}

	public void setCurrentMonthWeek3(int currentMonthWeek3) {
		this.currentMonthWeek3 = currentMonthWeek3;
	}

	public int getCurrentMonthWeek4() {
		return currentMonthWeek4;
	}

	public void setCurrentMonthWeek4(int currentMonthWeek4) {
		this.currentMonthWeek4 = currentMonthWeek4;
	}

	public int getCurrentMonthWeek5() {
		return currentMonthWeek5;
	}

	public void setCurrentMonthWeek5(int currentMonthWeek5) {
		this.currentMonthWeek5 = currentMonthWeek5;
	}

	public int getCurrentMonth_1Week1() {
		return currentMonth_1Week1;
	}

	public void setCurrentMonth_1Week1(int currentMonth_1Week1) {
		this.currentMonth_1Week1 = currentMonth_1Week1;
	}

	public int getCurrentMonth_1Week2() {
		return currentMonth_1Week2;
	}

	public void setCurrentMonth_1Week2(int currentMonth_1Week2) {
		this.currentMonth_1Week2 = currentMonth_1Week2;
	}

	public int getCurrentMonth_1Week3() {
		return currentMonth_1Week3;
	}

	public void setCurrentMonth_1Week3(int currentMonth_1Week3) {
		this.currentMonth_1Week3 = currentMonth_1Week3;
	}

	public int getCurrentMonth_1Week4() {
		return currentMonth_1Week4;
	}

	public void setCurrentMonth_1Week4(int currentMonth_1Week4) {
		this.currentMonth_1Week4 = currentMonth_1Week4;
	}

	public int getCurrentMonth_1Week5() {
		return currentMonth_1Week5;
	}

	public void setCurrentMonth_1Week5(int currentMonth_1Week5) {
		this.currentMonth_1Week5 = currentMonth_1Week5;
	}

	public int getCurrentMonth_2Week1() {
		return currentMonth_2Week1;
	}

	public void setCurrentMonth_2Week1(int currentMonth_2Week1) {
		this.currentMonth_2Week1 = currentMonth_2Week1;
	}

	public int getCurrentMonth_2Week2() {
		return currentMonth_2Week2;
	}

	public void setCurrentMonth_2Week2(int currentMonth_2Week2) {
		this.currentMonth_2Week2 = currentMonth_2Week2;
	}

	public int getCurrentMonth_2Week3() {
		return currentMonth_2Week3;
	}

	public void setCurrentMonth_2Week3(int currentMonth_2Week3) {
		this.currentMonth_2Week3 = currentMonth_2Week3;
	}

	public int getCurrentMonth_2Week4() {
		return currentMonth_2Week4;
	}

	public void setCurrentMonth_2Week4(int currentMonth_2Week4) {
		this.currentMonth_2Week4 = currentMonth_2Week4;
	}

	public int getCurrentMonth_2Week5() {
		return currentMonth_2Week5;
	}

	public void setCurrentMonth_2Week5(int currentMonth_2Week5) {
		this.currentMonth_2Week5 = currentMonth_2Week5;
	}

	public int getCurrentMonth_3Week1() {
		return currentMonth_3Week1;
	}

	public void setCurrentMonth_3Week1(int currentMonth_3Week1) {
		this.currentMonth_3Week1 = currentMonth_3Week1;
	}

	public int getCurrentMonth_3Week2() {
		return currentMonth_3Week2;
	}

	public void setCurrentMonth_3Week2(int currentMonth_3Week2) {
		this.currentMonth_3Week2 = currentMonth_3Week2;
	}

	public int getCurrentMonth_3Week3() {
		return currentMonth_3Week3;
	}

	public void setCurrentMonth_3Week3(int currentMonth_3Week3) {
		this.currentMonth_3Week3 = currentMonth_3Week3;
	}

	public int getCurrentMonth_3Week4() {
		return currentMonth_3Week4;
	}

	public void setCurrentMonth_3Week4(int currentMonth_3Week4) {
		this.currentMonth_3Week4 = currentMonth_3Week4;
	}

	public int getCurrentMonth_3Week5() {
		return currentMonth_3Week5;
	}

	public void setCurrentMonth_3Week5(int currentMonth_3Week5) {
		this.currentMonth_3Week5 = currentMonth_3Week5;
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
