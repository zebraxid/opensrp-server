package org.opensrp.etl.document;

import java.util.List;
import java.util.Map;

public abstract class CommonInformation {
	
	private String caseId;	
	private String instantId;	
	private String provider;	
	private String locationId;	
	private String today;
	private String start;	
	private String end;	
	private String registrationDate;	
	private String GOBHHID; 	
	private String jivitaHouseholdId;	
	private String country;	
	private String division;	
	private String district;	
	private String upazila;	
	private String union;	
	private String ward;	
	private String subunit;	
	private String mauzaPara;	
	private String gps;	
	private String formName;	
	private String userType;	
	private String externalUserId;	
	private String currentFormStatus;	
	private List<Map<String, String>> multimediaAttachments;	
	private Map<String, String> details;	
	private long submissionTime;	
	private long clientVersion;
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getInstantId() {
		return instantId;
	}
	public void setInstantId(String instantId) {
		this.instantId = instantId;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getGOBHHID() {
		return GOBHHID;
	}
	public void setGOBHHID(String gOBHHID) {
		GOBHHID = gOBHHID;
	}
	public String getJivitahouseholdId() {
		return jivitaHouseholdId;
	}
	public void setJivitahouseholdId(String jivitaHouseholdId) {
		this.jivitaHouseholdId = jivitaHouseholdId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getUpazila() {
		return upazila;
	}
	public void setUpazila(String upazila) {
		this.upazila = upazila;
	}
	public String getUnion() {
		return union;
	}
	public void setUnion(String union) {
		this.union = union;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getSubunit() {
		return subunit;
	}
	public void setSubunit(String subunit) {
		this.subunit = subunit;
	}
	public String getMauzaPara() {
		return mauzaPara;
	}
	public void setMauzaPara(String mauzaPara) {
		this.mauzaPara = mauzaPara;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getExternalUserId() {
		return externalUserId;
	}
	public void setExternalUserId(String externalUserId) {
		this.externalUserId = externalUserId;
	}
	public String getCurrentFormStatus() {
		return currentFormStatus;
	}
	public void setCurrentFormStatus(String currentFormStatus) {
		this.currentFormStatus = currentFormStatus;
	}
	public List<Map<String, String>> getMultimediaAttachments() {
		return multimediaAttachments;
	}
	public void setMultimediaAttachments(
			List<Map<String, String>> multimediaAttachments) {
		this.multimediaAttachments = multimediaAttachments;
	}
	public Map<String, String> getDetails() {
		return details;
	}
	public void setDetails(Map<String, String> details) {
		this.details = details;
	}
	public long getSubmissionTime() {
		return submissionTime;
	}
	public void setSubmissionTime(long submissionTime) {
		this.submissionTime = submissionTime;
	}
	public long getClientVersion() {
		return clientVersion;
	}
	public void setClientVersion(long clientVersion) {
		this.clientVersion = clientVersion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((GOBHHID == null) ? 0 : GOBHHID.hashCode());
		result = prime * result + ((caseId == null) ? 0 : caseId.hashCode());
		result = prime * result
				+ (int) (clientVersion ^ (clientVersion >>> 32));
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime
				* result
				+ ((currentFormStatus == null) ? 0 : currentFormStatus
						.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result
				+ ((district == null) ? 0 : district.hashCode());
		result = prime * result
				+ ((division == null) ? 0 : division.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result
				+ ((externalUserId == null) ? 0 : externalUserId.hashCode());
		result = prime * result
				+ ((formName == null) ? 0 : formName.hashCode());
		result = prime * result + ((gps == null) ? 0 : gps.hashCode());
		result = prime * result
				+ ((instantId == null) ? 0 : instantId.hashCode());
		result = prime
				* result
				+ ((jivitaHouseholdId == null) ? 0 : jivitaHouseholdId
						.hashCode());
		result = prime * result
				+ ((locationId == null) ? 0 : locationId.hashCode());
		result = prime * result
				+ ((mauzaPara == null) ? 0 : mauzaPara.hashCode());
		result = prime
				* result
				+ ((multimediaAttachments == null) ? 0 : multimediaAttachments
						.hashCode());
		result = prime * result
				+ ((provider == null) ? 0 : provider.hashCode());
		result = prime
				* result
				+ ((registrationDate == null) ? 0 : registrationDate.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result
				+ (int) (submissionTime ^ (submissionTime >>> 32));
		result = prime * result + ((subunit == null) ? 0 : subunit.hashCode());
		result = prime * result + ((today == null) ? 0 : today.hashCode());
		result = prime * result + ((union == null) ? 0 : union.hashCode());
		result = prime * result + ((upazila == null) ? 0 : upazila.hashCode());
		result = prime * result
				+ ((userType == null) ? 0 : userType.hashCode());
		result = prime * result + ((ward == null) ? 0 : ward.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommonInformation other = (CommonInformation) obj;
		if (GOBHHID == null) {
			if (other.GOBHHID != null)
				return false;
		} else if (!GOBHHID.equals(other.GOBHHID))
			return false;
		if (caseId == null) {
			if (other.caseId != null)
				return false;
		} else if (!caseId.equals(other.caseId))
			return false;
		if (clientVersion != other.clientVersion)
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (currentFormStatus == null) {
			if (other.currentFormStatus != null)
				return false;
		} else if (!currentFormStatus.equals(other.currentFormStatus))
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
			return false;
		if (division == null) {
			if (other.division != null)
				return false;
		} else if (!division.equals(other.division))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (externalUserId == null) {
			if (other.externalUserId != null)
				return false;
		} else if (!externalUserId.equals(other.externalUserId))
			return false;
		if (formName == null) {
			if (other.formName != null)
				return false;
		} else if (!formName.equals(other.formName))
			return false;
		if (gps == null) {
			if (other.gps != null)
				return false;
		} else if (!gps.equals(other.gps))
			return false;
		if (instantId == null) {
			if (other.instantId != null)
				return false;
		} else if (!instantId.equals(other.instantId))
			return false;
		if (jivitaHouseholdId == null) {
			if (other.jivitaHouseholdId != null)
				return false;
		} else if (!jivitaHouseholdId.equals(other.jivitaHouseholdId))
			return false;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		if (mauzaPara == null) {
			if (other.mauzaPara != null)
				return false;
		} else if (!mauzaPara.equals(other.mauzaPara))
			return false;
		if (multimediaAttachments == null) {
			if (other.multimediaAttachments != null)
				return false;
		} else if (!multimediaAttachments.equals(other.multimediaAttachments))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (registrationDate == null) {
			if (other.registrationDate != null)
				return false;
		} else if (!registrationDate.equals(other.registrationDate))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (submissionTime != other.submissionTime)
			return false;
		if (subunit == null) {
			if (other.subunit != null)
				return false;
		} else if (!subunit.equals(other.subunit))
			return false;
		if (today == null) {
			if (other.today != null)
				return false;
		} else if (!today.equals(other.today))
			return false;
		if (union == null) {
			if (other.union != null)
				return false;
		} else if (!union.equals(other.union))
			return false;
		if (upazila == null) {
			if (other.upazila != null)
				return false;
		} else if (!upazila.equals(other.upazila))
			return false;
		if (userType == null) {
			if (other.userType != null)
				return false;
		} else if (!userType.equals(other.userType))
			return false;
		if (ward == null) {
			if (other.ward != null)
				return false;
		} else if (!ward.equals(other.ward))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CommonInformation [caseId=" + caseId + ", instantId="
				+ instantId + ", provider=" + provider + ", locationId="
				+ locationId + ", today=" + today + ", start=" + start
				+ ", end=" + end + ", registrationDate=" + registrationDate
				+ ", GOBHHID=" + GOBHHID + ", jivitaHouseholdId="
				+ jivitaHouseholdId + ", country=" + country + ", division="
				+ division + ", district=" + district + ", upazila=" + upazila
				+ ", union=" + union + ", ward=" + ward + ", subunit="
				+ subunit + ", mauzaPara=" + mauzaPara + ", gps=" + gps
				+ ", formName=" + formName + ", userType=" + userType
				+ ", externalUserId=" + externalUserId + ", currentFormStatus="
				+ currentFormStatus + ", multimediaAttachments="
				+ multimediaAttachments + ", details=" + details
				+ ", submissionTime=" + submissionTime + ", clientVersion="
				+ clientVersion + "]";
	}
	
	

}
