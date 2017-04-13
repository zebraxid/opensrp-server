/**
 * @author Asifur
 */

package org.opensrp.register.mcare.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewResult;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Members;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllMembers extends MotechBaseRepository<Members> {

	private static Logger logger = LoggerFactory.getLogger(AllMembers.class);

	@Autowired
	public AllMembers(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Members.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}

	@GenerateView
	public Members findByCaseId(String caseId) {
		List<Members> members = queryView("by_caseId", caseId);
		if (members == null || members.isEmpty()) {
			return null;
		}
		return members.get(0);
	}

	public void close(String caseId) {
		Members members = findByCaseId(caseId);
		update(members.setIsClosed(true));
	}
	
	@View(name = "all_open_members_for_provider", map = "function(doc) { if (doc.type === 'Members' && doc.PROVIDERID) { emit(doc.PROVIDERID); } }")
	public List<Members> allOpenMembersForProvider(String providerId) {
		return db.queryView(
				createQuery("all_open_members_for_provider").key(providerId)
						.includeDocs(true), Members.class);
	}
	@View(name = "all_open_members_for_provider", map = "function(doc) { if (doc.type === 'Members' && doc.PROVIDERID) { emit(doc.PROVIDERID); } }")
	public List<Members> allOpenMembers() {
		return db.queryView(
				createQuery("all_open_members_for_provider")
						.includeDocs(true), Members.class);
	}

	@View(name = "created_in_last_4_months_by_provider_and_location", map = "function(doc) { if(doc.type === 'Members' && doc.clientVersion && doc.PROVIDERID && doc.Member_DISTRICT && doc.Member_UPAZILLA && doc.Member_UNION) { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.clientVersion > time){ emit([doc.PROVIDERID,doc.Member_DISTRICT,doc.Member_UPAZILLA,doc.Member_UNION], doc.clientVersion)} } }")
	public List<Members> allElcosCreatedLastFourMonthsByProviderAndLocation(String startKey, String endKey){
		List<Members> elcos =  db.queryView(
				createQuery("created_in_last_4_months_by_provider_and_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(true), Members.class);
			
		return elcos;
	}
	
	@View(name = "created_in_last_4_months_by_location", map = "function(doc) { if(doc.type === 'Members' && doc.clientVersion && doc.Member_DISTRICT && doc.Member_UPAZILLA && doc.Member_UNION) { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.clientVersion > time){ emit([doc.Member_DISTRICT,doc.Member_UPAZILLA,doc.Member_UNION], doc.clientVersion)} } }")
	public List<Members> allElcosCreatedLastFourMonthsByLocation(String startKey, String endKey){
		List<Members> elcos =  db.queryView(
				createQuery("created_in_last_4_months_by_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(true), Members.class);
		
		return elcos;
	}	
	
	@View(name = "elco_count_last_four_month", map = "function(doc) { if (doc.type === 'Members' && doc.clientVersion) { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.clientVersion > time ) {emit(doc.clientVersion, doc.clientVersion); }} }")
	public ViewResult elcoBetweenTwoDatesAsViewResult(Long startTime){
		Long endTime = System.currentTimeMillis();
		ViewResult vr = db.queryView(
				createQuery("elco_count_last_four_month")
				.startKey(startTime)
				.endKey(endTime)
				.includeDocs(false));
		return vr;
		
	}
	
	
	public ViewResult allElcosCreatedLastFourMonthsByLocationViewResult(String startKey, String endKey){
		
		ViewResult vr = db.queryView(
				createQuery("created_in_last_4_months_by_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(false));
		
		return vr;
	}
	
	public ViewResult allElcosCreatedLastFourMonthsByProviderAndLocationViewResult(String startKey, String endKey){
		
		ViewResult vr = db.queryView(
				createQuery("created_in_last_4_months_by_provider_and_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(false));
		
		return vr;
	}
	
	public int totalElco() {       
	       return db.queryView(createQuery("all")).getRows().get(0).getValueAsInt();
	    }
	    
}
