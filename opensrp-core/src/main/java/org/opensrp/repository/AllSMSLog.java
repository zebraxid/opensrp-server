package org.opensrp.repository;

import org.ektorp.CouchDbConnector;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.SMSLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AllSMSLog extends MotechBaseRepository<SMSLog> {
	
	@Autowired
	public AllSMSLog(@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(SMSLog.class, db);
		
	}
	
}
