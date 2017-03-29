package org.opensrp.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.Drug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllDrugs extends MotechBaseRepository<Drug> {
	@Autowired
	protected AllDrugs(@Value("#{opensrp['couchdb.opensrp-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Drug.class, db);
		db.setRevisionLimit(revisionLimit);
	}

	@View(name = "all_drugs_by_drugName", map = "function(doc) {if (doc.type === 'Drug') {emit(doc.drugName);}}")
	public List<Drug> findAllByName(String key) {
		return db.queryView(createQuery("all_drugs_by_drugName").key(key).includeDocs(true), Drug.class);
	}

	@View(name = "all_drugs_by_code", map = "function(doc) {if (doc.type === 'Drug') {for(var key in doc.codes) {emit(doc.codes[key]);}}}")
	public List<Drug> findAllByCode(String code) {
		return db.queryView(createQuery("all_drugs_by_code").key(code).includeDocs(true), Drug.class);
	}
}
