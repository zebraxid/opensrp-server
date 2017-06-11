package org.opensrp.register.encounter.sync.mapping.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;


@JsonIgnoreProperties(ignoreUnknown = true)
@TypeDiscriminator("doc.type === 'EncounterSyncMapping'")
public class EncounterSyncMapping extends MotechBaseDataObject{
	private static final long serialVersionUID = -3557930891320863509L;	
	@JsonProperty
	private String encounterId;
	@JsonProperty
	private String instanceId;
	@JsonProperty
	private String vaccineName;
	@JsonProperty
	private int dose;
	@JsonProperty
	private long created;
	@JsonProperty
	private long updated;
	public EncounterSyncMapping(){
		
	}
	
	public EncounterSyncMapping setEncounterId(String encounterId) {
		this.encounterId = encounterId;
		return this;
	}	
	
	public String getEncounterId() {
		return encounterId;
	}
	
	public EncounterSyncMapping setInstanceId(String instanceId) {
		this.instanceId = instanceId;
		return this;
	}
	
	public String getInstanceId() {
		return instanceId;
	}
	
	public EncounterSyncMapping setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
		return this;
	}
	
	public String getVaccineName() {
		return vaccineName;
	}
	
	public int getDose() {
		return dose;
	}
	
	public EncounterSyncMapping setDose(int dose) {
		this.dose = dose;
		return this;
	}
	
	
	public EncounterSyncMapping setCreated(long created) {
		this.created = created;
		return this;
	}
	
	public long getCreated() {
		return created;
	}
	
	public EncounterSyncMapping setUpdated(long updated) {
		this.updated = updated;
		return this;
	}
	
	public long getUpdated() {
		return updated;
	}
	
	
}
