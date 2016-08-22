package org.opensrp.camp.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.camp.dao.Camp;
import org.opensrp.camp.dao.CampDate;
import org.opensrp.common.AllConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class CampDateRepository extends MotechBaseRepository<CampDate> {
	
	private static Logger logger = LoggerFactory.getLogger(CampDateRepository.class);
	
	@Autowired
	public CampDateRepository(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
	    @Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(CampDate.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}
	
	@View(name = "by_session_id", map = "function(doc) { if (doc.type === 'CampDate' && doc._id) { emit(doc.session_id, doc); } }")
	public List<CampDate> findBySessionId(String session_id) {
		List<CampDate> campDates = db.queryView(createQuery("by_session_id").key(session_id).includeDocs(true), CampDate.class);
		return campDates;
	}
	
}
