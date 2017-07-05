package org.opensrp.scheduler.router;


import org.ektorp.CouchDbConnector;
import org.junit.runner.RunWith;
import org.opensrp.repository.lucene.LuceneActionRepository;
import org.opensrp.scheduler.repository.AllActions;
import org.springframework.beans.factory.annotation.Autowired;


public class StubAllActions  {

    @Autowired
     public AllActions allActions;
    
    protected StubAllActions() {
    }
}
