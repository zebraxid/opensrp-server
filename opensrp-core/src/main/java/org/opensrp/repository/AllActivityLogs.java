
package org.opensrp.repository;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.joda.time.DateTime;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.ActivityLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @author muhammad.ahmed@ihsinformatics.com
 *  Created on May 25, 2015
 */
@Repository
public class AllActivityLogs extends MotechBaseRepository<ActivityLog> {
	
	
	@Autowired
	protected AllActivityLogs(@Value("#{opensrp['couchdb.opensrp-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.ACTIVITY_LOG_DATABASE) CouchDbConnector db) {
		super(ActivityLog.class, db);
		db.setRevisionLimit(revisionLimit);
	}

	public ActivityLog findById(String _id) {
		return  get(_id);
	}
	
	@View(name = "all_activity_logs", map = "function(doc) { if (doc.type === 'ActivityLog') { emit(doc.id); } }")
	public List<ActivityLog> findAll() {
		return db.queryView(createQuery("all_activity_logs").includeDocs(true), ActivityLog.class);
	}

	@View(name = "all_activity_logs_by_category_and_timestamp", map = "function (doc) {  if(doc.type === 'ActivityLog'){   "
			+ " var timestamp = Date.parse(doc.dateLogged);   "
			+ " emit([doc.category.toLowerCase(), timestamp]);   "
			+ " emit([doc.category.toLowerCase(), timestamp]);  "
			+ " }}")
	public List<ActivityLog> findAllByCategory(String category, DateTime from, DateTime to) {
		//couchdb does left to right match and also we want sort by timestamp
		ComplexKey skey = ComplexKey.of(category.toLowerCase(), from.getMillis());
		ComplexKey ekey = ComplexKey.of(category.toLowerCase(), to.getMillis());

		return db.queryView(createQuery("all_activity_logs_by_category_and_timestamp").startKey(skey).endKey(ekey).includeDocs(true), ActivityLog.class);
	}
	
	@View(name = "all_activity_logs_by_timestamp", map = "function (doc) {  if(doc.type === 'ActivityLog'){   "
			+ " var timestamp = Date.parse(doc.dateLogged);   "
			+ " emit(timestamp);"
			+ " }}")	
	public List<ActivityLog> findByTimestamp(DateTime from, DateTime to) {
		return db.queryView(createQuery("all_activity_logs_by_timestamp").startKey(from.getMillis()).endKey(to.getMillis()).includeDocs(true), ActivityLog.class);		
	}
}
