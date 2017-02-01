package org.opensrp.rest.repository;

import org.opensrp.camp.dao.CampDate;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.Vaccine;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.ldriscoll.ektorplucene.CouchDbRepositorySupportWithLucene;
import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;
import com.github.ldriscoll.ektorplucene.LuceneQuery;
import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.designdocument.LuceneDesignDocument;
import com.github.ldriscoll.ektorplucene.designdocument.annotation.FullText;
import com.github.ldriscoll.ektorplucene.designdocument.annotation.Index;

@FullText({
    @Index(
        name = "camp_date",
	    index = "function(rec) {" +
	    		" var doc=new Document();" +
	    		" doc.add(rec.session_date,{\"field\":\"session_date\", \"store\":\"yes\"});" +
	    		" doc.add(rec.session_id,{\"field\":\"session_id\", \"store\":\"yes\"});" +
	    		" doc.add(rec.session_name,{\"field\":\"session_name\", \"store\":\"yes\"});" +
	    		" doc.add(rec.status,{\"field\":\"status\", \"store\":\"yes\"});" +
	    		" doc.add(rec.health_assistant,{\"field\":\"health_assistant\", \"store\":\"yes\"});" +
	    		" doc.add(rec.contact,{\"field\":\"contact\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.timestamp,{\"field\":\"timestamp\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.thana,{\"field\":\"thana\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.union,{\"field\":\"union\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.ward,{\"field\":\"ward\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.unit,{\"field\":\"unit\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec._id,{\"field\":\"id\", \"store\":\"yes\"});"+	    			 
	    		" doc.add(rec.type,{\"field\":\"type\", \"store\":\"yes\"});" + 
	    		" return doc;" +
	    		"}")
})

@Repository
public class LuceneCampDateRepository extends CouchDbRepositorySupportWithLucene<CampDate> {
	@Autowired
	public LuceneCampDateRepository(@Qualifier(AllConstants.OPENSRP_DATABASE_LUCENE_CONNECTOR)LuceneAwareCouchDbConnector db) {
		super(CampDate.class, db);
		initStandardDesignDocument();
	}
	
	public LuceneResult getData(String queryString,int skip,int limit) {
		System.out.println("Query:"+queryString);
		String sortField =  "\\" + "timestamp"; 
        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);        
        LuceneQuery query = new LuceneQuery(designDoc.getId(), "camp_date"); 
        query.setQuery(queryString); 
        query.setStaleOk(true);
        query.setSkip(skip);
        query.setLimit(limit);
        query.setSort(sortField);
        return db.queryLucene(query); 
    } 
}
