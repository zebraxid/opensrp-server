/**
 * @author Asifur
 */
package org.opensrp.rest.repository;

import org.opensrp.common.AllConstants;
import org.opensrp.scheduler.ScheduleLog;
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
    @Index(
        name = "scheduleLog",
	    index = "function(rec) {" +
	    		" var doc=new Document();" +
	    		" doc.add(rec.anmIdentifier,{\"field\":\"anmIdentifier\", \"store\":\"yes\"});" +
	    		" doc.add(rec.caseID,{\"field\":\"caseID\", \"store\":\"yes\"});"+ 	    		   		
	    		" doc.add(rec.visitCode,{\"field\":\"visitCode\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.currentWindow,{\"field\":\"currentWindow\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.scheduleName,{\"field\":\"scheduleName\", \"store\":\"yes\"});" +
	    		" doc.add(rec.isActionActive,{\"field\":\"isActionActive\", \"store\":\"yes\"});" +
	    		" doc.add(rec.timeStamp,{\"field\":\"timeStamp\", \"store\":\"yes\"});" +
	    		" doc.add(rec.type,{\"field\":\"type\", \"store\":\"yes\"});" + 
	    		" return doc;" +
	    		"}")
})

@Repository
public class LuceneScheduleRepository extends CouchDbRepositorySupportWithLucene<ScheduleLog>{
	@Autowired
	public LuceneScheduleRepository(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_LUCENE_CONNECTOR)LuceneAwareCouchDbConnector db) {
		super(ScheduleLog.class, db);
		this.db.setRevisionLimit(revisionLimit);
		initStandardDesignDocument();
	}
	public LuceneResult findDocsByProvider(String queryString) { 
        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);
        LuceneQuery query = new LuceneQuery(designDoc.getId(), "scheduleLog"); 
        query.setQuery(queryString); 
        query.setStaleOk(false); 
        return db.queryLucene(query); 
    } 
}
