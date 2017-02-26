package org.opensrp.register.thrivepk;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
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
}
