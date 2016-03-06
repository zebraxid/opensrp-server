package org.opensrp.scheduler.service;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.scheduler.ScheduleRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleRuleService extends MotechBaseRepository<ScheduleRules> {
	
	@Autowired
    protected ScheduleRuleService(@Qualifier(AllConstants.OPENSRP_SCHEDULE_DATABASE_CONNECTOR) CouchDbConnector db) {
        super(ScheduleRules.class, db);
    }
    
    @GenerateView
	public List<ScheduleRules> findByName(String name) {
    	return queryView("by_name", name);
	}

}
