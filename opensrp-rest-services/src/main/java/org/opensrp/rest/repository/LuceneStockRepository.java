package org.opensrp.rest.repository;

import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Stock;
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
        name = "stock",
	    index = "function(rec) {" +
	    		" var doc=new Document();" +
	    		" doc.add(rec.provider,{\"field\":\"provider\", \"store\":\"yes\"});" +
	    		" doc.add(rec.date,{\"field\":\"date\", \"store\":\"yes\"});" +
	    		" doc.add(rec.caseId,{\"field\":\"caseId\", \"store\":\"yes\"});" +
	    		" doc.add(rec.report,{\"field\":\"report\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.clientVersion,{\"field\":\"clientVersion\", \"store\":\"yes\"});"+   		 
	    		" doc.add(rec.type,{\"field\":\"type\", \"store\":\"yes\"});" + 
	    		" doc.add(rec._id,{\"field\":\"id\", \"store\":\"yes\"});" + 
	    		" return doc;" +
	    		"}")
})

@Repository
public class LuceneStockRepository extends CouchDbRepositorySupportWithLucene<Stock> {
	@Autowired
	public LuceneStockRepository(@Qualifier(AllConstants.OPENSRP_DATABASE_LUCENE_CONNECTOR)LuceneAwareCouchDbConnector db) {
		super(Stock.class, db);
		initStandardDesignDocument();
	}
	
	 public LuceneResult findDocsByProvider(String queryString) { 
	        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);
	        LuceneQuery query = new LuceneQuery(designDoc.getId(), "stock"); 
	        query.setQuery(queryString); 
	        query.setStaleOk(false); 
	        return db.queryLucene(query); 
	    } 
		 public LuceneResult getData(String queryString,int skip,int limit) {		
				String sortField =  "\\" + "id"; 
		        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);        
		        LuceneQuery query = new LuceneQuery(designDoc.getId(), "stock"); 
		        query.setQuery(queryString); 
		        query.setStaleOk(true);
		        query.setSkip(skip);
		        query.setLimit(limit);
		        query.setSort(sortField);
		        return db.queryLucene(query); 
		   }

		 public int  getDataCount(String queryString) {		
		        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);        
		        LuceneQuery query = new LuceneQuery(designDoc.getId(), "stock"); 
		        query.setQuery(queryString); 
		        query.setStaleOk(true);   
		        
		        return db.queryLucene(query).getTotalRows(); 
		} 
}
