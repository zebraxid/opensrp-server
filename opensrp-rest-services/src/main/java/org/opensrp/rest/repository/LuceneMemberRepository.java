package org.opensrp.rest.repository;


import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Members;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.github.ldriscoll.ektorplucene.CouchDbRepositorySupportWithLucene;
import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;
import com.github.ldriscoll.ektorplucene.LuceneQuery;
import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.designdocument.LuceneDesignDocument;
import com.github.ldriscoll.ektorplucene.designdocument.annotation.FullText;
import com.github.ldriscoll.ektorplucene.designdocument.annotation.Index;

@FullText({
	@Index(index = "function(rec){"
			+ "var doc =  new Document();"
			+ " doc.add(rec.Mem_F_Name,{\"field\":\"" +
			LuceneDgfpClientRepositoryField.CLIENT_FIRST_NAME+
			"\", \"store\":\"yes\"}); "
			+ " doc.add(rec.Mem_NID,{\"field\":\"" +
			LuceneDgfpClientRepositoryField.CLIENT_NATIONAL_ID+
			"\", \"store\":\"yes\"}); "
			+ " doc.add(rec.Mem_BRID,{\"field\":\"" +
			LuceneDgfpClientRepositoryField.CLIENT_BIRTH_ID +
			"\", \"store\":\"yes\"}); "
			+ " doc.add(rec.caseId,{\"field\":\"" +
			LuceneDgfpClientRepositoryField.CLIENT_CASE_ID +
			"\", \"store\":\"yes\"});"
			+ " doc.add(rec.type,{\"field\":\"" +
			LuceneDgfpClientRepositoryField.CLIENT_TYPE +
			"\" , \"store\":\"yes\"});"
			+ "return doc;} ", 
			name = "by_name") })

@Repository
public class LuceneMemberRepository extends
CouchDbRepositorySupportWithLucene<Members> {

	private static Logger logger = LoggerFactory
			.getLogger(LuceneHouseHoldRepository.class);

	
	@Autowired
	public LuceneMemberRepository(
			@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_LUCENE_CONNECTOR) LuceneAwareCouchDbConnector db) {
		super(Members.class, db);
		this.db.setRevisionLimit(revisionLimit);
		initStandardDesignDocument();
	}

	
	public LuceneResult findDocsByName(String queryString) {
		LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class,
				stdDesignDocumentId);
		LuceneQuery query = new LuceneQuery(designDoc.getId(), "by_name");
		query.setQuery(queryString);
		query.setStaleOk(false);
		logger.info("inside luceneMembersRepository.");
		return db.queryLucene(query);
	}

}
