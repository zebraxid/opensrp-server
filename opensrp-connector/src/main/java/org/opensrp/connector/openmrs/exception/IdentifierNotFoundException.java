package org.opensrp.connector.openmrs.exception;

public class IdentifierNotFoundException extends Exception {

	private String errorMessage;

	public IdentifierNotFoundException(String message) {
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
