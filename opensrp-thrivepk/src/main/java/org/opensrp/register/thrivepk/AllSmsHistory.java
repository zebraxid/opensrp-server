package org.opensrp.register.thrivepk;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.joda.time.DateTime;
import org.motechproject.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AllSmsHistory extends MotechBaseRepository<SmsHistory> {
    @Autowired
    protected AllSmsHistory(@Qualifier("thrivepkDatabaseConnector") CouchDbConnector db) {
        super(SmsHistory.class, db);
    }
    
    @GenerateView
	public SmsHistory findByBaseEntityId(String baseEntityId) {
		List<SmsHistory> entities = queryView("by_baseEntityId", baseEntityId);
		if (entities == null || entities.isEmpty()) {
			return null;
		}
		return entities.get(0);
	}
    
    @View(name = "by_entityId_cause",
            map = "function(doc) { " +
                    "if(doc.type === 'SmsHistory') {" +
                    "emit([doc.baseEntityId, doc.cause], null)} " +
                    "}")
    public List<SmsHistory> byEntityIdCause(String entityId, String cause) {
        ComplexKey key = ComplexKey.of(entityId, cause);
        return db.queryView(createQuery("by_entityId_cause").key(key).includeDocs(true), SmsHistory.class);
    }
    
    @View(name = "by_status",
            map = "function(doc) { " +
                    "if(doc.type === 'SmsHistory') {" +
                    "emit([doc.status, doc.duedate], null)} " +
                    "}")
    public List<SmsHistory> byStatus(String status, DateTime from, DateTime to) {
    	ComplexKey start = ComplexKey.of(status, from);
    	ComplexKey end = ComplexKey.of(status, to);
        return db.queryView(createQuery("by_status").startKey(start).endKey(end).includeDocs(true), SmsHistory.class);
    }
    
}
