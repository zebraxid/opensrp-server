package org.opensrp.etl.document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class HouseholdDocument extends CommonInformation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	private String firstName;	
	private String lastName;	
	private String birthDate; 	
	private String gender;	
	private String FWNHHMBRNUM;	
	private String FWNHHMWRA;	
	private String ELCO;
	private List<Map<String, String>> Elcodetails;
	
	
	
	private HouseholdDocument() {
		// TODO Auto-generated constructor stub
	}
	
	private static final HouseholdDocument INSTANCE = new HouseholdDocument();
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getFWNHHMBRNUM() {
		return FWNHHMBRNUM;
	}
	public void setFWNHHMBRNUM(String fWNHHMBRNUM) {
		FWNHHMBRNUM = fWNHHMBRNUM;
	}
	public String getFWNHHMWRA() {
		return FWNHHMWRA;
	}
	public void setFWNHHMWRA(String fWNHHMWRA) {
		FWNHHMWRA = fWNHHMWRA;
	}
	public String getElco() {
		return ELCO;
	}
	public void setElco(String ELCO) {
		this.ELCO = ELCO;
	}
	public List<Map<String, String>> getElcodetails() {
		return Elcodetails;
	}
	public void setElcodetails(List<Map<String, String>> elcodetails) {
		Elcodetails = elcodetails;
	}
	
	public static HouseholdDocument getInstance() {
		return INSTANCE;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((ELCO == null) ? 0 : ELCO.hashCode());
		result = prime * result
				+ ((Elcodetails == null) ? 0 : Elcodetails.hashCode());
		result = prime * result
				+ ((FWNHHMBRNUM == null) ? 0 : FWNHHMBRNUM.hashCode());
		result = prime * result
				+ ((FWNHHMWRA == null) ? 0 : FWNHHMWRA.hashCode());
		result = prime * result
				+ ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		HouseholdDocument other = (HouseholdDocument) obj;
		if (ELCO == null) {
			if (other.ELCO != null)
				return false;
		} else if (!ELCO.equals(other.ELCO))
			return false;
		if (Elcodetails == null) {
			if (other.Elcodetails != null)
				return false;
		} else if (!Elcodetails.equals(other.Elcodetails))
			return false;
		if (FWNHHMBRNUM == null) {
			if (other.FWNHHMBRNUM != null)
				return false;
		} else if (!FWNHHMBRNUM.equals(other.FWNHHMBRNUM))
			return false;
		if (FWNHHMWRA == null) {
			if (other.FWNHHMWRA != null)
				return false;
		} else if (!FWNHHMWRA.equals(other.FWNHHMWRA))
			return false;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "HouseholdDocument [firstName=" + firstName + ", lastName="
				+ lastName + ", birthDate=" + birthDate + ", gender=" + gender
				+ ", FWNHHMBRNUM=" + FWNHHMBRNUM + ", FWNHHMWRA=" + FWNHHMWRA
				+ ", ELCO=" + ELCO + ", Elcodetails=" + Elcodetails + "]";
	}
	
}
