package org.opensrp.camp.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.camp.dao.Camp;
import org.opensrp.common.AllConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class CampRepository extends MotechBaseRepository<Camp> {
	
	private static Logger logger = LoggerFactory.getLogger(CampRepository.class);
	
	@Autowired
	public CampRepository(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
	    @Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Camp.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}
	
	@View(name = "by_session_name", map = "function(doc) { if (doc.type === 'Camp' && doc.session_name) { emit(doc.session_name, doc); } }")
	public Camp findBySessionName(String session_name) {
		
		List<Camp> camps = db.queryView(createQuery("by_session_name").key(session_name).includeDocs(true), Camp.class);
		if (camps == null || camps.isEmpty()) {
			
			return null;
		}
		return camps.get(0);
	}
	
	@View(name = "by_id", map = "function(doc) { if (doc.type === 'Camp' && doc._id) { emit(doc._id, doc); } }")
	public List<Camp> findById(String id) {
		List<Camp> camp = db.queryView(createQuery("by_id").key(id).includeDocs(true), Camp.class);
		if (camp == null || camp.isEmpty()) {
			return null;
		}
		return camp;
	}
	
}
