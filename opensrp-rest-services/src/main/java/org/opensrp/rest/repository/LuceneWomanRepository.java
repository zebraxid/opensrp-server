package org.opensrp.rest.repository;

import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Woman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.github.ldriscoll.ektorplucene.CouchDbRepositorySupportWithLucene;
import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;
import com.github.ldriscoll.ektorplucene.LuceneQuery;
import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.designdocument.LuceneDesignDocument;
import com.github.ldriscoll.ektorplucene.designdocument.annotation.FullText;
import com.github.ldriscoll.ektorplucene.designdocument.annotation.Index;

@FullText({
    @Index(
        name = "mother",
	    index = "function(rec) {" +
	    		" var doc=new Document();" +
	    		" doc.add(rec.TODAY,{\"field\":\"TODAY\", \"store\":\"yes\"});" +
	    		" doc.add(rec.isClosed,{\"field\":\"isClosed\", \"store\":\"yes\"});"+ 	    		   		
	    		" doc.add(rec.SUBMISSIONDATE,{\"field\":\"SUBMISSIONDATE\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.type,{\"field\":\"type\", \"store\":\"yes\"});" +
	    		" return doc;" +
	    		"}")
})

@Repository
public class LuceneWomanRepository extends CouchDbRepositorySupportWithLucene<Woman>{
	@Autowired
	public LuceneWomanRepository(@Qualifier(AllConstants.OPENSRP_DATABASE_LUCENE_CONNECTOR)LuceneAwareCouchDbConnector db) {
		super(Woman.class, db);
		initStandardDesignDocument();
	}
	public LuceneResult findDocsByProvider(String queryString) { 
        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);
        LuceneQuery query = new LuceneQuery(designDoc.getId(), "mother"); 
        query.setQuery(queryString); 
        query.setStaleOk(false); 
        return db.queryLucene(query); 
    } 

}
