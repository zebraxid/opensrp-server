/**
 * @author Asifur
 */

package org.opensrp.rest.repository;

import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.service.*;
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
/*index = "function(doc) { " + 
		"    var res = new Document(); " + 
		"    res.add(doc.TODAY,{\"field\":\"TODAY\", \"store\":\"yes\"});"+
		"    res.add(doc.PROVIDERID,{\"field\":\"PROVIDERID\", \"store\":\"yes\"});" +
		"	 res.add(doc.FWUPAZILLA,{\"field\":\"FWUPAZILLA\", \"store\":\"yes\"});" +
		"    return res; " + 
    "}")
*/
@FullText({
    @Index(
        name = "by_provider",
	    index = "function(rec) {" +
	    		" var doc=new Document();" + 
	    		" doc.add(rec.FWDIVISION,{\"field\":\"FWDIVISION\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.FWDISTRICT,{\"field\":\"FWDISTRICT\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.FWUNION,{\"field\":\"FWUNION\", \"store\":\"yes\"});" +  
	    		" doc.add(rec.PROVIDERID,{\"field\":\"PROVIDERID\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.FWUPAZILLA,{\"field\":\"FWUPAZILLA\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.SUBMISSIONDATE,{\"field\":\"SUBMISSIONDATE\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.type,{\"field\":\"type\", \"store\":\"yes\"});" + 
	    		" return doc;" +
	    		"}")
})
@Repository
public class LuceneHouseHoldRepository extends CouchDbRepositorySupportWithLucene<HouseHold> {
	private static Logger logger = LoggerFactory.getLogger(LuceneHouseHoldRepository.class);
	@Autowired
	public LuceneHouseHoldRepository(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_LUCENE_CONNECTOR)LuceneAwareCouchDbConnector db) {
		super(HouseHold.class, db);
		this.db.setRevisionLimit(revisionLimit);
		initStandardDesignDocument();
	}
	
	 public LuceneResult findDocsByProvider(String queryString) { 
        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId); 
        LuceneQuery query = new LuceneQuery(designDoc.getId(), "by_provider"); 
        query.setQuery(queryString); 
        query.setStaleOk(false); 
        logger.info("inside luceneHouseholdRepository.");
        return db.queryLucene(query); 
    } 

}
