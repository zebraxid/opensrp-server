package org.opensrp.etl.entity;

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

@Entity
@Table(name="elcos_dump")
@TypeDefs( {@TypeDef( name= "json", typeClass = Elco.class)})
public class ElcoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="elco_dump_id_seq")
	@SequenceGenerator(name="elco_dump_id_seq", sequenceName="elco_dump_id_seq", allocationSize=1)
	private int id;
	@Column(name="document_id")
	private String documentId;
	@Column(name="case_id")
	private String caseId;
	@Type(type = "json")
    private String document;	
	@Column(name="created")
	private DateTime created;
	@Column(name="updated")
	private DateTime updated;
	@Column(name="time_stamp")
	private long timeStamp;
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
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public DateTime getCreated() {
		return created;
	}
	public void setCreated(DateTime created) {
		this.created = created;
	}
	public DateTime getUpdated() {
		return updated;
	}
	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
