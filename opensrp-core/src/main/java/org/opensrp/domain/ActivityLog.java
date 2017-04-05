package org.opensrp.domain;

import java.util.Map;

import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;
import org.motechproject.model.MotechBaseDataObject;

@TypeDiscriminator("doc.type == 'ActivityLog'")
public class ActivityLog extends MotechBaseDataObject{

	private String title;
	private String documentId;
	private String documentType;
	private String uniqueIdentifier;
	private String activity;
	private String category;
	private String description;
	private DateTime dateLogged;
	private Map<String, Object> details;
	
	public ActivityLog() { }
	
	public ActivityLog(String title, String documentId, String documentType, String uniqueIdentifier, String activity, String category,
			String description, DateTime dateLogged, Map<String, Object> details) {
		this.title = title;
		this.documentId = documentId;
		this.documentType = documentType;
		this.uniqueIdentifier = uniqueIdentifier;
		this.activity = activity;
		this.category = category;
		this.description = description;
		this.setDateLogged(dateLogged);
		this.details = details;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public DateTime getDateLogged() {
		return dateLogged;
	}

	public void setDateLogged(DateTime dateLogged) {
		this.dateLogged = dateLogged;
	}

	public Map<String, Object> getDetails() {
		return details;
	}
	public void setDetails(Map<String, Object> details) {
		this.details = details;
	}
}
