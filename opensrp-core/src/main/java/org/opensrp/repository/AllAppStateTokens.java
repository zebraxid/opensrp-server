package org.opensrp.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.AppStateToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllAppStateTokens extends MotechBaseRepository<AppStateToken> {
    @Autowired
    protected AllAppStateTokens(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
    		@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
        super(AppStateToken.class, db);
        this.db.setRevisionLimit(revisionLimit);
    }
    
    @GenerateView
	public List<AppStateToken> findByName(String name) {
    	return queryView("by_name", name);
	}
}
