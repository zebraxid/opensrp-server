package org.opensrp.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class VaccineCountDTO {
	@JsonProperty
	private int Woman_measlesCount;
	@JsonProperty
	private int Woman_TT1Count;
	@JsonProperty
	private int Woman_TT2Count;
	@JsonProperty
	private int Woman_TT3Count;
	@JsonProperty
	private int Woman_TT4Count;
	@JsonProperty
	private int Woman_TT5Count;
	@JsonProperty
	private int child_bcgCount;
	@JsonProperty
	private int child_ipvCount;
	@JsonProperty
	private int child_measles1Count;
	@JsonProperty
	private int child_measles2Count;
	@JsonProperty
	private int child_opv0Count;
	@JsonProperty
	private int child_opv1Count;
	@JsonProperty
	private int child_opv2Count;
	@JsonProperty
	private int child_opv3Count;
	@JsonProperty
	private int child_pcv1Count;
	@JsonProperty
	private int child_pcv2Count;
	@JsonProperty
	private int child_pcv3Count;
	@JsonProperty
	private int child_penta1Count;
	@JsonProperty
	private int child_penta2Count;
	@JsonProperty
	private int child_penta3Count;
	
	public VaccineCountDTO(){
		
	}
	
	public VaccineCountDTO(int Woman_measlesCount, int Woman_TT1Count,
			int Woman_TT2Count, int Woman_TT3Count,
			int Woman_TT4Count, int Woman_TT5Count, 
			int child_bcgCount, int child_ipvCount, 
			int child_measles1Count, int child_measles2Count,
			int child_opv0Count, int child_opv1Count, 
			int child_opv2Count, int child_opv3Count, 
			int child_pcv1Count, int child_pcv2Count, 
			int child_pcv3Count, int child_penta1Count,
			int child_penta2Count, int child_penta3Count) {
		super();
		this.Woman_measlesCount = Woman_measlesCount;
		this.Woman_TT1Count = Woman_TT1Count;
		this.Woman_TT2Count = Woman_TT2Count;
		this.Woman_TT3Count = Woman_TT3Count;
		this.Woman_TT4Count = Woman_TT4Count;
		this.Woman_TT5Count = Woman_TT5Count;
		this.child_bcgCount = child_bcgCount;
		this.child_ipvCount = child_ipvCount;
		this.child_measles1Count = child_measles1Count;
		this.child_measles2Count = child_measles2Count;
		this.child_opv0Count = child_opv0Count;
		this.child_opv1Count = child_opv1Count;
		this.child_opv2Count = child_opv2Count;
		this.child_opv3Count = child_opv3Count;
		this.child_pcv1Count = child_pcv1Count;
		this.child_pcv2Count = child_pcv2Count;
		this.child_pcv3Count = child_pcv3Count;
		this.child_penta1Count = child_penta1Count;
		this.child_penta2Count = child_penta2Count;
		this.child_penta3Count = child_penta3Count;
	}
	
	public int getWoman_measlesCount() {
		return Woman_measlesCount;
	}

	public int getWoman_TT1Count() {
		return Woman_TT1Count;
	}

	public int getWoman_TT2Count() {
		return Woman_TT2Count;
	}

	public int getWoman_TT3Count() {
		return Woman_TT3Count;
	}

	public int getWoman_TT4Count() {
		return Woman_TT4Count;
	}

	public int getWoman_TT5Count() {
		return Woman_TT5Count;
	}

	public int getChild_bcgCount() {
		return child_bcgCount;
	}

	public int getChild_ipvCount() {
		return child_ipvCount;
	}

	public int getChild_measles1Count() {
		return child_measles1Count;
	}

	public int getChild_measles2Count() {
		return child_measles2Count;
	}

	public int getChild_opv0Count() {
		return child_opv0Count;
	}

	public int getChild_opv1Count() {
		return child_opv1Count;
	}

	public int getChild_opv2Count() {
		return child_opv2Count;
	}

	public int getChild_opv3Count() {
		return child_opv3Count;
	}

	public int getChild_pcv1Count() {
		return child_pcv1Count;
	}

	public int getChild_pcv2Count() {
		return child_pcv2Count;
	}

	public int getChild_pcv3Count() {
		return child_pcv3Count;
	}

	public int getChild_penta1Count() {
		return child_penta1Count;
	}

	public int getChild_penta2Count() {
		return child_penta2Count;
	}

	public int getChild_penta3Count() {
		return child_penta3Count;
	}
	@JsonProperty("Woman_measlesCount")
	public void setWoman_measlesCount(int woman_measlesCount) {
		this.Woman_measlesCount = woman_measlesCount;
	}
	@JsonProperty("Woman_TT1Count")
	public void setWoman_TT1Count(int woman_TT1Count) {
		this.Woman_TT1Count = woman_TT1Count;
	}
	@JsonProperty("Woman_TT2Count")
	public void setWoman_TT2Count(int woman_TT2Count) {
		this.Woman_TT2Count = woman_TT2Count;
	}
	@JsonProperty("Woman_TT3Count")
	public void setWoman_TT3Count(int woman_TT3Count) {
		this.Woman_TT3Count = woman_TT3Count;
	}
	@JsonProperty("Woman_TT4Count")
	public void setWoman_TT4Count(int woman_TT4Count) {
		this.Woman_TT4Count = woman_TT4Count;
	}
	@JsonProperty("Woman_TT5Count")
	public void setWoman_TT5Count(int woman_TT5Count) {
		this.Woman_TT5Count = woman_TT5Count;
	}

	public void setChild_bcgCount(int child_bcgCount) {
		this.child_bcgCount = child_bcgCount;
	}

	public void setChild_ipvCount(int child_ipvCount) {
		this.child_ipvCount = child_ipvCount;
	}

	public void setChild_measles1Count(int child_measles1Count) {
		this.child_measles1Count = child_measles1Count;
	}

	public void setChild_measles2Count(int child_measles2Count) {
		this.child_measles2Count = child_measles2Count;
	}

	public void setChild_opv0Count(int child_opv0Count) {
		this.child_opv0Count = child_opv0Count;
	}

	public void setChild_opv1Count(int child_opv1Count) {
		this.child_opv1Count = child_opv1Count;
	}

	public void setChild_opv2Count(int child_opv2Count) {
		this.child_opv2Count = child_opv2Count;
	}

	public void setChild_opv3Count(int child_opv3Count) {
		this.child_opv3Count = child_opv3Count;
	}

	public void setChild_pcv1Count(int child_pcv1Count) {
		this.child_pcv1Count = child_pcv1Count;
	}

	public void setChild_pcv2Count(int child_pcv2Count) {
		this.child_pcv2Count = child_pcv2Count;
	}

	public void setChild_pcv3Count(int child_pcv3Count) {
		this.child_pcv3Count = child_pcv3Count;
	}

	public void setChild_penta1Count(int child_penta1Count) {
		this.child_penta1Count = child_penta1Count;
	}

	public void setChild_penta2Count(int child_penta2Count) {
		this.child_penta2Count = child_penta2Count;
	}

	public void setChild_penta3Count(int child_penta3Count) {
		this.child_penta3Count = child_penta3Count;
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
