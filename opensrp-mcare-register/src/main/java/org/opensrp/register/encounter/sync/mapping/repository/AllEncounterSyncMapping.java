/**
 * @author proshanto
 */

package org.opensrp.register.encounter.sync.mapping.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.register.encounter.sync.mapping.domain.EncounterSyncMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllEncounterSyncMapping extends MotechBaseRepository<EncounterSyncMapping> {

	private static Logger logger = LoggerFactory.getLogger(AllEncounterSyncMapping.class);

	@Autowired
	public AllEncounterSyncMapping(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(EncounterSyncMapping.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}

	@GenerateView
	public EncounterSyncMapping findByEncounterId(String encounterId) {
		List<EncounterSyncMapping> encounterSyncMappings = queryView("by_encounterId", encounterId);
		if (encounterSyncMappings == null || encounterSyncMappings.isEmpty()) {
			return null;
		}
		return encounterSyncMappings.get(0);
	}
	
	public void add(String encounterId,String instancId,String vaccineName,int dose){
		EncounterSyncMapping encounterSyncMapping = new EncounterSyncMapping();
		encounterSyncMapping.setVaccineName(vaccineName);
		encounterSyncMapping.setEncounterId(encounterId);
		encounterSyncMapping.setInstanceId(instancId);
		encounterSyncMapping.setDose(dose);
		encounterSyncMapping.setCreated(System.currentTimeMillis());
		encounterSyncMapping.setUpdated(System.currentTimeMillis());
		this.add(encounterSyncMapping);
	}
	public void update(String encounterId,String vaccineName,int dose ){
		EncounterSyncMapping encounterSyncMapping = this.findByEncounterId(encounterId);
		encounterSyncMapping.setVaccineName(vaccineName);
		encounterSyncMapping.setDose(dose);
		encounterSyncMapping.setId(encounterSyncMapping.getId());
		encounterSyncMapping.setUpdated(System.currentTimeMillis());
		encounterSyncMapping.setRevision(encounterSyncMapping.getRevision());
		this.update(encounterSyncMapping);
	}
}
