package org.opensrp.register.mcare.bo;

public class DgfpClient {
	private String caseId;
	private String firstName;
	private String nationalId;
	private String birthId;
	private DgfpClientType type;
	private String imagePath;
	
	
	
	public DgfpClient(String caseId, String firstName, String nationalId, String birthId, String type) {
		this.caseId = caseId;
		this.firstName = firstName;
		this.nationalId = nationalId;
		this.birthId = birthId;
		this.type = DgfpClientType.getUserType(type);
		this.imagePath = "";
	}
	

	public String getCaseId() {
		return caseId;
	}




	public String getFirstName() {
		return firstName;
	}



	public String getNationalId() {
		return nationalId;
	}



	public String getBirthId() {
		return birthId;
	}



	public DgfpClientType getType() {
		return type;
	}



	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	

	
}
