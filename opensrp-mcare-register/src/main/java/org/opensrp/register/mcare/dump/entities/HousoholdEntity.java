package org.opensrp.register.mcare.dump.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="household_dump")
public class HousoholdEntity {	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="household_dump_id_seq")
	@SequenceGenerator(name="household_dump_id_seq", sequenceName="household_dump_id_seq", allocationSize=1)
    private int id;
	@Column(name="caseId")
	private String caseId;
	@Column(name="instanceId")
	private String instanceId;
	/*@Column(name="provider")
	private String provider;
	@Column(name="locationId")
	private String locationId;
	@Column(name="today")
	private String today;
	@Column(name="start")
	private String start;
	@Column(name="end")
	private String end;
	@Column(name="FWNHRegDate")
	private String FWNHRegDate;
	@Column(name="FWGOBHHID")
	private String FWGOBHHID; 
	@Column(name="FWJIVHHID")
	private String FWJIVHHID;
	@Column(name="FWCountry")
	private String FWCountry;
	@Column(name="FWDivision")
	private String FWDivision;
	@Column(name="FWDistrict")
	private String FWDistrict;
	@Column(name="FWUpazila")
	private String FWUpazila;
	@Column(name="FWUnion")
	private String FWUnion;
	@Column(name="FWWard")
	private String FWWard;
	@Column(name="FWSubUnit")
	private String FWSubUnit;
	@Column(name="FWMauzaPara")
	private String FWMauzaPara;
	@Column(name="FWNHHHGPS")
	private String FWNHHHGPS;
	@Column(name="formName")
	private String formName;
	@Column(name="FWHOHFName")
	private String FWHOHFName;
	@Column(name="FWHOHLName")
	private String FWHOHLName;
	@Column(name="FWHOHBirthDate")
	private String FWHOHBirthDate; 
	@Column(name="FWHOHGender")
	private String FWHOHGender;
	@Column(name="FWNHHMBRNUM")
	private String FWNHHMBRNUM;
	@Column(name="FWNHHMWRA")
	private String FWNHHMWRA;
	@Column(name="elco")
	private String elco;
	@Column(name="userType")
	private String userType;
	@Column(name="externalUserId")
	private String externalUserId;
	@Column(name="currentFormStatus")
	private String currentFormStatus;
	
	@JsonProperty
	private List<Map<String, String>> multimediaAttachments;
	@JsonProperty
	private Map<String, String> details;
	@Column(name="submissionTimestamp")
	private long submissionTimestamp;
	@Column(name="clientVersion")
	private long clientVersion;*/
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
	@Override
	public String toString() {
		return "Housohold [id=" + id + ", caseId=" + caseId + ", instanceId="
				+ instanceId + "]";
	}
	

}
