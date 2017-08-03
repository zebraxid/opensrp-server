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
import org.joda.time.DateTime;
import org.opensrp.etl.hibernate.custom.type.ElcoJsonbType;

@Entity
@Table(name="elcos_dump")
@TypeDefs( {@TypeDef( name= "json", typeClass = ElcoJsonbType.class)})
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
	private Date created;
	@Column(name="updated")
	private Date updated;
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
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
