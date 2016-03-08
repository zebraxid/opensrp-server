package org.opensrp.scheduler.service;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.scheduler.ScheduleRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


@Repository
public class ScheduleRuleRepository extends MotechBaseRepository<ScheduleRules>{
	
	@Autowired
    protected ScheduleRuleRepository(@Qualifier(AllConstants.OPENSRP_SCHEDULE_DATABASE_CONNECTOR) CouchDbConnector db) {
        super(ScheduleRules.class, db);
    }

	/*@GenerateView
	public String findByName(String name) {
	    return queryView("by_name", name).get(0).toString();
	}*/
	@View(name = "all_rule", map = "function(doc) { if (doc.type === 'ScheduleRule') { emit(doc.name); } }")
    public List<ScheduleRules> allRule(){
    	return db.queryView(
				createQuery("all_rule")
						.includeDocs(true), ScheduleRules.class);
    }
}
