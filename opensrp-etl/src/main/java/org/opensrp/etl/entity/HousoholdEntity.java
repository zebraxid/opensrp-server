package org.opensrp.etl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.opensrp.etl.document.HouseholdDocument;
import org.opensrp.etl.hibernate.custom.type.HouseholdJsonbType;

@Entity
@Table(name="household_document")
@TypeDefs( {@TypeDef( name= "jsonb", typeClass = HouseholdJsonbType.class)})
public class HousoholdEntity  {	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="household_document_id_seq")
	@SequenceGenerator(name="household_document_id_seq", sequenceName="household_document_id_seq", allocationSize=1)
    private int id;
	@Column(name="document_id")
	private String documentId;
	@Column(name="case_id")
	private String caseId;
	@Column
	@Type(type = "jsonb")
    private HouseholdDocument doc;	
	@Column(name="created")
	private Date created;
	@Column(name="updated")
	private Date updated;
	@Column(name="time_stamp")
	private long timeStamp;
	@Column(name="status")
	private boolean status;
	@Column(name="rev_id")
	private String revId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}	
	
	public HouseholdDocument getDoc() {
		return doc;
	}
	public void setDoc(HouseholdDocument doc) {
		this.doc = doc;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated() {
		this.created = new Date();
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated() {
		this.updated = new Date();
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp() {
		this.timeStamp = System.currentTimeMillis();
	}
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String getRevId() {
		return revId;
	}
	public void setRevId(String revId) {
		this.revId = revId;
	}
	
	@Override
	public String toString() {
		return "HousoholdEntity [id=" + id + ", documentId=" + documentId
				+ ", caseId=" + caseId + ", doc=" + doc
				+ ", created=" + created + ", updated=" + updated
				+ ", timeStamp=" + timeStamp + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caseId == null) ? 0 : caseId.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((doc == null) ? 0 : doc.hashCode());
		result = prime * result
				+ ((documentId == null) ? 0 : documentId.hashCode());
		result = prime * result + id;
		result = prime * result + ((revId == null) ? 0 : revId.hashCode());
		result = prime * result + (status ? 1231 : 1237);
		result = prime * result + (int) (timeStamp ^ (timeStamp >>> 32));
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
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
		HousoholdEntity other = (HousoholdEntity) obj;
		if (caseId == null) {
			if (other.caseId != null)
				return false;
		} else if (!caseId.equals(other.caseId))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (doc == null) {
			if (other.doc != null)
				return false;
		} else if (!doc.equals(other.doc))
			return false;
		if (documentId == null) {
			if (other.documentId != null)
				return false;
		} else if (!documentId.equals(other.documentId))
			return false;
		if (id != other.id)
			return false;
		if (revId == null) {
			if (other.revId != null)
				return false;
		} else if (!revId.equals(other.revId))
			return false;
		if (status != other.status)
			return false;
		if (timeStamp != other.timeStamp)
			return false;
		if (updated == null) {
			if (other.updated != null)
				return false;
		} else if (!updated.equals(other.updated))
			return false;
		return true;
	}
	
	
	

}
