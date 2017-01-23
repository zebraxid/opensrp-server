/**
 * @author Asifur
 */

package org.opensrp.rest.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.HouseHold;
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
	    		"}"),
	@Index(
        name = "by_all_criteria",
        index = "function (doc) { if(doc.type !== 'Mother') return null;  var docl = new Array(); var ret = new Document(); if(doc.PROVIDERID){    var led = doc.PROVIDERID;    ret.add(led, {'field' : 'PROVIDERID'}); } docl.push(ret); return docl; }")
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
	
	 public List<Integer> getByCriteria(String anmIdentifier) {
			// create a simple query against the view/search function that we've created
			
			Query qf = new Query(FilterType.AND);
			
			if(anmIdentifier!= null && !anmIdentifier.isEmpty() && !anmIdentifier.equalsIgnoreCase("")){
				qf.eq("PROVIDERID", anmIdentifier);
			}

			if(qf.query() == null || qf.query().isEmpty()){
				throw new RuntimeException("Atleast one search filter must be specified");
			}
				
			/*LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);
	        LuceneQuery query = new LuceneQuery(designDoc.getId(), "by_all_criteria");*/ 
			
			LuceneQuery query = new LuceneQuery("Mother", "by_all_criteria");
			
			System.out.println("Query: "+qf.query());
			
	        query.setQuery(qf.query()); 
	        query.setStaleOk(false); 
	        query.setIncludeDocs(true);
	       
	        LuceneResult result = db.queryLucene(query);	        
	        List<Integer> count = new ArrayList<Integer>();
	        int countNewborn = 0, countLiveBirth = 0, countStillBirth = 0;

			for (Row r : result.getRows()) {
				HashMap<String, Object> doc = r.getDoc();
				//System.out.println("Lucene HH doc: "+doc);
				Mother ro = null;
				try {
					ro = new ObjectMapper().readValue(new JSONObject(doc).toString(), type);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("Lucene BnfVisitDetails: "+ro.bnfVisitDetails().toString());
				//System.out.println("Lucene BnfVisitDetails size: "+ro.bnfVisitDetails().size());
				for (int i=0; i < ro.bnfVisitDetails().size(); i++){	
					String FWBNFSTS = "", FWBNFCHLDVITSTS = "";
					try {
						FWBNFSTS = ro.bnfVisitDetails().get(i).get("FWBNFSTS");					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						FWBNFCHLDVITSTS = ro.bnfVisitDetails().get(i).get("FWBNFCHLDVITSTS");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(FWBNFSTS.equalsIgnoreCase("3"))
						countNewborn++;
					if(FWBNFCHLDVITSTS.equalsIgnoreCase("1"))
						countLiveBirth++;
					if(FWBNFCHLDVITSTS.equalsIgnoreCase("0"))
						countStillBirth++;
				}			
			}	
			count.add(countNewborn);
			count.add(countLiveBirth);
			count.add(countStillBirth);
	        return count;
	 }
}
