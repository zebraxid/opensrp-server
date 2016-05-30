package org.opensrp.repository;

import org.opensrp.common.AllConstants;
import org.opensrp.domain.DrishtiUser;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.motechproject.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AllOpenSRPUsers extends MotechBaseRepository<DrishtiUser> {
    @Autowired
    protected AllOpenSRPUsers(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
    		@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
        super(DrishtiUser.class, db);
        this.db.setRevisionLimit(revisionLimit);
    }

    @GenerateView()
    public DrishtiUser findByUsername(String username) {
        if (username == null) {
            return null;
        }
        List<DrishtiUser> users = queryView("by_username", username);
        if (users == null || users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
}
