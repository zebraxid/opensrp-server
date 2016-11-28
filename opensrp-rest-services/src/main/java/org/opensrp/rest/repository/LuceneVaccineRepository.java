package org.opensrp.rest.repository;

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
        name = "houdehold",
	    index = "function(rec) {" +
	    		" var doc=new Document();" +
	    		" doc.add(rec.health_assistant,{\"field\":\"health_assistant\", \"store\":\"yes\"});" +
	    		" doc.add(rec.clientId,{\"field\":\"clientId\", \"store\":\"yes\"});" +
	    		" doc.add(rec.actionId,{\"field\":\"actionId\", \"store\":\"yes\"});" +
	    		" doc.add(rec.beneficiaryType,{\"field\":\"beneficiaryType\", \"store\":\"yes\"});" +
	    		" doc.add(rec.vaccineName,{\"field\":\"vaccineName\", \"store\":\"yes\"});" +
	    		" doc.add(rec.scheduleDate,{\"field\":\"scheduleDate\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.expiredDate,{\"field\":\"expiredDate\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.status,{\"field\":\"status\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.missedCount,{\"field\":\"missedCount\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.createdDate,{\"field\":\"createdDate\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.executionDate,{\"field\":\"executionDate\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.timeStamp,{\"field\":\"timeStamp\", \"store\":\"yes\"});"+   		 
	    		" doc.add(rec.type,{\"field\":\"type\", \"store\":\"yes\"});" + 
	    		" return doc;" +
	    		"}")
})

@Repository
public class LuceneVaccineRepository extends CouchDbRepositorySupportWithLucene<Vaccine> {
	@Autowired
	public LuceneVaccineRepository(@Qualifier(AllConstants.OPENSRP_DATABASE_LUCENE_CONNECTOR)LuceneAwareCouchDbConnector db) {
		super(Vaccine.class, db);
		initStandardDesignDocument();
	}
	
	 public LuceneResult findDocsByProvider(String queryString) { 
        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId); 
        //assertTrue(designDoc != null); 
       // assertTrue(designDoc.containsIndex("by_provider")); 
        
       // String makeQueryString ="PROVIDERID:"+ providerId + " AND " + "FWUPAZILLA:" + upazilla + " AND " + "user_type:" + userType; //+ " AND TODAY:[\"2016-02-01\"+\"TO\"+\"2016-03-01\"]" ;
        LuceneQuery query = new LuceneQuery(designDoc.getId(), "vaccine"); 
        query.setQuery(queryString); 
        query.setStaleOk(false); 
        return db.queryLucene(query); 
    } 
}
