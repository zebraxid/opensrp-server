package org.opensrp.register.mcare.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.joda.time.LocalDate;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Child;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllChilds extends MotechBaseRepository<Child> {
	
	private static Logger logger = LoggerFactory.getLogger(AllChilds.class);
	
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
	
	public boolean exists(String caseId) {
		return findByCaseId(caseId) != null;
	}
	
	@View(name = "all_childs", map = "function(doc) { if (doc.type === 'Child') { emit(doc.PROVIDERID, doc.caseId); } }")
	public List<Child> findAllChilds() {
		return db.queryView(createQuery("all_childs").includeDocs(true), Child.class);
	}
	
	@View(name = "get_all_child", map = "function(doc) { if (doc.type === 'Child' && doc.FWNChildHGPS) { emit(doc._id, [doc.FWNChildHGPS.split(' ')[0],doc.FWNChildHGPS.split(' ')[1],doc.ELCO,doc.FWMAUZA_PARA,doc.FWDIVISION,doc.FWDISTRICT,doc.FWUPAZILLA,doc.FWUNION,doc.FWWARD]); } }")
	public List<Child> allChilds() {
		return db.queryView(createQuery("get_all_child").includeDocs(true), Child.class);
	}
	
	@View(name = "all_open_childs_for_provider", map = "function(doc) { if (doc.type === 'Child' && doc.PROVIDERID) { emit(doc.PROVIDERID,doc._id ); } }")
	public List<Child> allOpenChildsForProvider(String providerId) {
		return db.queryView(createQuery("all_open_childs_for_provider").key(providerId).includeDocs(true), Child.class);
	}
	
	@View(name = "all_childs_prev_7_days", map = "function(doc) { if (doc.type === 'Child' && doc.PROVIDERID && doc.TODAY) { emit(doc.PROVIDERID, doc.TODAY); } }")
	public List<Child> allChildsVisited7Days(String providerId) {
		
		LocalDate today = LocalDate.now();
		
		List<Child> childs = db.queryView(createQuery("all_childs_prev_7_days").startKey(today.minusDays(100)).endKey(today)
		//.key(providerId)
		        .includeDocs(true), Child.class);
		
		return childs;
	}
}
