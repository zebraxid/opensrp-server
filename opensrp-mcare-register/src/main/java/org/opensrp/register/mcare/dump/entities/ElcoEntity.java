package org.opensrp.register.mcare.dump.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="elcos_dump")
public class ElcoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="elco_dump_id_seq")
	@SequenceGenerator(name="elco_dump_id_seq", sequenceName="elco_dump_id_seq", allocationSize=1)
    private int id;
	@Column(name="caseId")
	private String caseId;
	@Column(name="instanceId")
	private String instanceId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	

}
