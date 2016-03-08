package org.opensrp.rest.repository;

import org.opensrp.common.AllConstants;
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
	    		" doc.add(rec.TODAY,{\"field\":\"TODAY\", \"store\":\"yes\"});" +
	    		" doc.add(rec.FWNHREGDATE,{\"field\":\"FWNHREGDATE\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.FWGOBHHID,{\"field\":\"FWGOBHHID\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.FWJIVHHID,{\"field\":\"FWJIVHHID\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.FWCOUNTRY,{\"field\":\"FWCOUNTRY\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.FWDIVISION,{\"field\":\"FWDIVISION\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.FWDISTRICT,{\"field\":\"FWDISTRICT\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.FWUNION,{\"field\":\"FWUNION\", \"store\":\"yes\"});" +
	    		" doc.add(rec.FWNHHHGPS,{\"field\":\"FWNHHHGPS\", \"store\":\"yes\"});" +
	    		" doc.add(rec.form_name,{\"field\":\"form_name\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.FWHOHFNAME,{\"field\":\"FWHOHFNAME\", \"store\":\"yes\"});" +
	    		" doc.add(rec.FWHOHBIRTHDATE,{\"field\":\"FWHOHBIRTHDATE\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.PROVIDERID,{\"field\":\"PROVIDERID\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.FWHOHGENDER,{\"field\":\"FWHOHGENDER\", \"store\":\"yes\"});" +
	    		" doc.add(rec.user_type,{\"field\":\"user_type\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.FWUPAZILLA,{\"field\":\"FWUPAZILLA\", \"store\":\"yes\"});" + 
	    		" return doc;" +
	    		"}")
})
@Repository
public class LuceneHouseHoldRepository extends CouchDbRepositorySupportWithLucene<HouseHold> {

	@Autowired
	public LuceneHouseHoldRepository(@Qualifier(AllConstants.OPENSRP_DATABASE_LUCENE_CONNECTOR)LuceneAwareCouchDbConnector db) {
		super(HouseHold.class, db);
		initStandardDesignDocument();
	}
	
	 public LuceneResult findDocsByProviderAndUpazilla(String providerId, String upazilla, String userType) { 
        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId); 
        //assertTrue(designDoc != null); 
       // assertTrue(designDoc.containsIndex("by_provider")); 
        
        String makeQueryString ="PROVIDERID:"+ providerId + " AND " + "FWUPAZILLA:" + upazilla + " AND " + "user_type:" + userType; //+ " AND TODAY:[\"2016-02-01\"+\"TO\"+\"2016-03-01\"]" ;
        LuceneQuery query = new LuceneQuery(designDoc.getId(), "by_provider"); 
        query.setQuery(makeQueryString); 
        query.setStaleOk(false); 
        return db.queryLucene(query); 
    } 

}
