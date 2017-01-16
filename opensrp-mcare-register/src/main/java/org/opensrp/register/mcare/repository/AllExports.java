package org.opensrp.register.mcare.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Acl;
import org.opensrp.register.mcare.domain.Exports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.google.gson.annotations.Expose;

@Repository
public class AllExports  extends MotechBaseRepository<Exports>{
	@Autowired
	public AllExports(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Exports.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}
	
	
	@View(name = "by_user", map = "function(doc) { if (doc.type === 'Exports' && doc._id) { emit(doc.user, doc); } }")
	public List<Exports> getExportsByUser(String session_id) {
		List<Exports> exports = db.queryView(createQuery("by_user").key(session_id).includeDocs(true), Exports.class);
		return exports;
	}
	
	public void save(Exports exports){
		this.save(exports);
	}


	
	
	
}
