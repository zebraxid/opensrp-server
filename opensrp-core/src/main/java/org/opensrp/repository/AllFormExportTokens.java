package org.opensrp.repository;

import org.opensrp.common.AllConstants;
import org.opensrp.domain.FormExportToken;
import org.ektorp.CouchDbConnector;
import org.motechproject.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllFormExportTokens extends MotechBaseRepository<FormExportToken> {
    @Autowired
    protected AllFormExportTokens(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
    		@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
        super(FormExportToken.class, db);
        this.db.setRevisionLimit(revisionLimit);
    }
}
