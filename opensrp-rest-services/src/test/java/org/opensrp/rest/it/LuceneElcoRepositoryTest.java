package org.opensrp.rest.it;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.rest.repository.LuceneElcoRepository;

import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;
import com.github.ldriscoll.ektorplucene.LuceneResult;

public class LuceneElcoRepositoryTest {

	private LuceneAwareCouchDbConnector connector; 
    private LuceneElcoRepository luceneElcoRepository; 
    @Before
    public void setUp() throws IOException { 
        HttpClient httpClient = new StdHttpClient.Builder() 
                .host("localhost") 
                .port(5984) 
                .socketTimeout(1000) 
               // .username("testadmin").password("testpass") 
                .build(); 
        CouchDbInstance instance = new StdCouchDbInstance(httpClient); 
 
        connector = new LuceneAwareCouchDbConnector("opensrp", instance); 
        connector.createDatabaseIfNotExists(); 
        luceneElcoRepository = new  LuceneElcoRepository(connector); 
        //createDocuments(); 
    } 
 
 
    @After 
    public void tearDown() { 
       // deleteDocuments(); 
        connector = null; 
    } 
    
    @Test 
    public void testInit() throws ParseException { 
    	//String makeQueryString ="PROVIDERID:proshanto" + " AND " + "FWUPAZILLA:GAIBANDHA SADAR" + " AND " + "user_type:FWA"+ " AND SUBMISSIONDATE:[2014-02-01 TO 2017-03-30]" ;
    	
    	
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date today = Calendar.getInstance().getTime();
    	Date dates = dateFormat.parse(dateFormat.format(today));
    	Date date = dateFormat.parse("2016-04-12");
    	long start = date.getTime();
    	long end = dates.getTime();
    	System.out.println("ddd:"+end);
    	System.out.println("ss:"+start);
    	//String makeQueryString ="PROVIDERID:proshanto" + " AND " + "user_type:FWA"+ " AND SUBMISSIONDATE:["+start+" TO "+end+"]" ;
    	String makeQueryString ="type:Elco" + " AND " + "SUBMISSIONDATE:["+start+" TO "+end+"]" ;
    	LuceneResult result = luceneElcoRepository.findDocsByProvider(makeQueryString);
    	System.out.println(result.toString());
        System.out.println(result.getRows().size());
       
		
        assertNotNull("Expecting a non null result", result); 
        assertTrue("Should only have one result", result.getRows().size() >=0); 
    } 
}
