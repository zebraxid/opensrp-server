package org.opensrp.register.mcare.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Acl;
import org.opensrp.register.mcare.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllUser  extends MotechBaseRepository<User>{
	private static Logger logger = LoggerFactory.getLogger(AllUser.class);
	
	@Autowired
	public AllUser(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(User.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}
	
	@View(name = "user_by_user_name", map = "function(doc) { if (doc.type === 'User' && doc.user_name) { emit(doc.user_name); } }")
	public User findUserByUserName(String username) {
		logger.info("inside AllUsers.findUserByUserName()");
		List<User> users = db.queryView(
				createQuery("user_by_user_name").key(username)
						.includeDocs(true), User.class);
		if (users == null || users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}
	
}
