/**
 * @author Asifur
 */

package org.opensrp.rest.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.scheduler.ScheduleLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.github.ldriscoll.ektorplucene.CouchDbRepositorySupportWithLucene;
import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;
import com.github.ldriscoll.ektorplucene.LuceneQuery;
import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.LuceneResult.Row;
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
	    		" doc.add(rec.PROVIDERID,{\"field\":\"PROVIDERID\", \"store\":\"yes\"});" +
	    		" doc.add(rec.type,{\"field\":\"type\", \"store\":\"yes\"});" +
	    		" return doc;" +
	    		"}")
})

@Repository
public class LuceneMotherRepository extends CouchDbRepositorySupportWithLucene<Mother>{
	@Autowired
	public LuceneMotherRepository(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_LUCENE_CONNECTOR)LuceneAwareCouchDbConnector db) {
		super(Mother.class, db);
		this.db.setRevisionLimit(revisionLimit);
		initStandardDesignDocument();
	}
	public LuceneResult findDocsByProvider(String queryString) { 
        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);
        LuceneQuery query = new LuceneQuery(designDoc.getId(), "mother"); 
        query.setQuery(queryString); 
        query.setStaleOk(false); 
        
        LuceneResult result = db.queryLucene(query);
        
        List<Mother> ol = new ArrayList<>();
		for (Row r : result.getRows()) {
			HashMap<String, Object> doc = r.getDoc();
			Mother ro = null;
			try {
				ro = new ObjectMapper().readValue(new JSONObject(doc).toString(), type);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ol.add(ro);
		}	
		
		//System.out.println("Lucene Result: "+ol);
				
        return result;  
    } 

}
