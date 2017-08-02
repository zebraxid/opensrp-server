package org.opensrp.register.mcare.dump.entities;

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
import org.joda.time.DateTime;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.dump.type.HouseholdDocumentType;
import org.opensrp.register.mcare.dump.type.HouseholdDocument;

@Entity
@Table(name="household_dump")
@TypeDefs( {@TypeDef( name= "jsonb", typeClass = HouseholdDocumentType.class)})
public class HousoholdEntity  {	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="household_dump_id_seq")
	@SequenceGenerator(name="household_dump_id_seq", sequenceName="household_dump_id_seq", allocationSize=1)
    private int id;
	@Column(name="document_id")
	private String documentId;
	@Column(name="case_id")
	private String caseId;
	@Column
	@Type(type = "jsonb")
    private HouseholdDocument doc;	
	@Column(name="created")
	private DateTime created;
	@Column(name="updated")
	private DateTime updated;
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
	public DateTime getCreated() {
		return created;
	}
	public void setCreated() {
		this.created = new DateTime();
	}
	public DateTime getUpdated() {
		return updated;
	}
	public void setUpdated() {
		this.updated = new DateTime();
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
	
	
	

}
