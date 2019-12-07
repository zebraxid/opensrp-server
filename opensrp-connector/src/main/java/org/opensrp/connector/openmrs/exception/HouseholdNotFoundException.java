package org.opensrp.connector.openmrs.exception;

public class HouseholdNotFoundException extends Exception {

	private String errorMessage;

	public HouseholdNotFoundException(String message) {
		super(message);
		this.errorMessage = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
