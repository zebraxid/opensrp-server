package org.opensrp.rest.repository;

import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.HouseHold;
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
        name = "elco",
	    index = "function(rec) {" +
	    		" var doc=new Document();" +
	    		" doc.add(rec.TODAY,{\"field\":\"TODAY\", \"store\":\"yes\"});" +
	    		" doc.add(rec.FWWOMFNAME,{\"field\":\"FWWOMFNAME\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.FWWOMNID,{\"field\":\"FWWOMNID\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.FWHUSNAME,{\"field\":\"FWHUSNAME\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.FWWOMBID,{\"field\":\"FWWOMBID\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.FWWOMRETYPEBID,{\"field\":\"FWWOMRETYPEBID\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.GOBHHID,{\"field\":\"GOBHHID\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.JiVitAHHID,{\"field\":\"JiVitAHHID\", \"store\":\"yes\"});" +	    		
	    		" doc.add(rec.SUBMISSIONDATE,{\"field\":\"SUBMISSIONDATE\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.type,{\"field\":\"type\", \"store\":\"yes\"});" +
	    		" return doc;" +
	    		"}")
})

@Repository
public class LuceneElcoRepository extends CouchDbRepositorySupportWithLucene<Elco>{

	@Autowired
	public LuceneElcoRepository(@Qualifier(AllConstants.OPENSRP_DATABASE_LUCENE_CONNECTOR)LuceneAwareCouchDbConnector db) {
		super(Elco.class, db);
		initStandardDesignDocument();
	}
	public LuceneResult findDocsByProvider(String queryString) { 
        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId); 
        //assertTrue(designDoc != null); 
       // assertTrue(designDoc.containsIndex("by_provider")); 
        
       // String makeQueryString ="PROVIDERID:"+ providerId + " AND " + "FWUPAZILLA:" + upazilla + " AND " + "user_type:" + userType; //+ " AND TODAY:[\"2016-02-01\"+\"TO\"+\"2016-03-01\"]" ;
        LuceneQuery query = new LuceneQuery(designDoc.getId(), "elco"); 
        query.setQuery(queryString); 
        query.setStaleOk(false); 
        return db.queryLucene(query); 
    } 

}
