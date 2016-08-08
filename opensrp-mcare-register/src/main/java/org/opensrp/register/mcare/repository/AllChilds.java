package org.opensrp.register.mcare.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Child;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllChilds extends MotechBaseRepository<Child> {
	
	@Autowired
	public AllChilds(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
	    @Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Child.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}
	
	@GenerateView
	public Child findByCaseId(String caseId) {
		List<Child> childs = queryView("by_caseId", caseId);
		if (childs == null || childs.isEmpty()) {
			return null;
		}
		return childs.get(0);
	}
	
	@View(name = "all_childs", map = "function(doc) { if (doc.type === 'Child') { emit(doc.PROVIDERID, doc.caseId); } }")
	public List<Child> findAllChilds() {
		return db.queryView(createQuery("all_childs").includeDocs(true), Child.class);
	}
	
	public boolean exists(String caseId) {
		return findByCaseId(caseId) != null;
	}
}
