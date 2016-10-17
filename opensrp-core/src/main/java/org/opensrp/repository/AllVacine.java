package org.opensrp.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.motechproject.dao.MotechBaseRepository;

import org.opensrp.common.AllConstants;
import org.opensrp.domain.Vaccine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AllVacine extends MotechBaseRepository<Vaccine> {
	
	private static Logger logger = LoggerFactory.getLogger(AllVacine.class.toString());
	
	@Autowired
	protected AllVacine(@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Vaccine.class, db);
	}
	
	@GenerateView
	private List<Vaccine> findByClientId(String caseId) {
		return queryView("by_clientId", caseId);
	}
	
}
