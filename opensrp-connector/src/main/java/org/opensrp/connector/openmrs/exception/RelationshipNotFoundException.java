package org.opensrp.connector.openmrs.exception;

public class RelationshipNotFoundException extends Exception {

	private String errorMessage;

	public RelationshipNotFoundException(String message) {
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
