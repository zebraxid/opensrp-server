package org.opensrp.service.formSubmission;

import org.ektorp.CouchDbConnector;
import org.opensrp.repository.couch.AllAppStateTokens;


public class StubAllAppStateToken extends AllAppStateTokens {

    public StubAllAppStateToken(CouchDbConnector db) {
        super(db);
    }
}
