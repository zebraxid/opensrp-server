package org.opensrp.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.BahmniId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class BahmniIdRepository  extends MotechBaseRepository<BahmniId>{
	private static Logger logger = LoggerFactory.getLogger(BahmniIdRepository.class);
	
	@Autowired
	public BahmniIdRepository(
			@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(BahmniId.class, db);
	}
	@GenerateView
	public BahmniId findByentityId(String genId) {
		List<BahmniId> ids = queryView("by_entityId", genId);
		if (ids == null || ids.isEmpty()) {
			return null;
		}
		return ids.get(0);
		
	}
	
	
	
}
