/**
 * @author Asifur
 */

package org.opensrp.register.mcare.repository;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1ReportGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

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

	
	@View(name = "created_in_current_month", map = "function(doc) { " +
            "var date = new Date(); var currentYear = date.getFullYear(); var currentMonth = date.getMonth();" +
            "var firstDayOfCurrentMonth = new Date(currentYear, currentMonth, 1);" +
            "var lastDayOfCurrentMonth = new Date(currentYear, currentMonth + 1, 0);"+
            "if(doc.type === 'Members' && doc.PROVIDERID && " +
            "(doc.timestamp>=firstDayOfCurrentMonth.getTime() && doc.timestamp<=lastDayOfCurrentMonth.getTime())) { " +
            "emit( [doc.PROVIDERID], null); } }"
    )
	public List<Members> allMembersCreatedBetweenTwoDateBasedOnProviderId(String providerId) {
		ComplexKey start = ComplexKey.of(providerId);
		ComplexKey end = ComplexKey.of(providerId);
		List<Members> members = db.queryView(
				createQuery("created_in_current_month")
						.startKey(start)
						.endKey(end)
						.includeDocs(true), Members.class);
		return members;
	}

	@View(name = "create_member_with_updatedTimeStamp", map = "function(doc) { " +
            "if(doc.type === 'Members' "
            + "&& doc.caseId) "
            + "{emit([doc.updatedTimeStamp],null)}  }"
    )
	public List<Members> allMembersCreatedBetweenTwoDateBasedOnUpdatedTimeStamp(Long updatedTimeStamp) {	
		ComplexKey startKey = ComplexKey.of(updatedTimeStamp);
        ComplexKey endKey = ComplexKey.of(Long.MAX_VALUE);
		List<Members> members = db.queryView(
				createQuery("create_member_with_updatedTimeStamp")
				.startKey(startKey)
				.endKey(endKey)
				.includeDocs(true), Members.class);
		System.err.println("members:"+members.size());
		/*for( int i=0 ; i< members.size() ; i++){
			System.out.println("show updatedtimestamp of member::" + members.get(i).getUpdatedTimeStamp());
		}*/
		return members;
	}

}
