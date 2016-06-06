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
        luceneElcoRepository = new  LuceneElcoRepository(2, connector); 
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
    	long end = dates.getTime();  //end has today's date
    	   	
    	Date date = dateFormat.parse("2016-05-01");
    	long start = date.getTime();
    	
    	System.out.println("today:- "+end);
    	System.out.println("limit:- "+start);
    	/*String makeQueryString;// ="type:Elco" + " AND " + "SUBMISSIONDATE:["+start+" TO "+end+"]" ;
    	makeQueryString ="type:Elco" + " AND " + "SUBMISSIONDATE:["	+ end +" TO " + end +"]" ;
    	LuceneResult result = luceneElcoRepository.findDocsByProvider(makeQueryString);
        System.out.println(result.getTotalRows() + " -today Count for may31");
        
        makeQueryString = "type:Elco" + " AND " + "SUBMISSIONDATE:["	+ start +" TO " + end +"]" ;
        result = luceneElcoRepository.findDocsByProvider(makeQueryString);
        System.out.println(result.getTotalRows() + " -month Count for may");*/
        
        Date weekUpperDate = dateFormat.parse("2016-05-17");
    	long weekUpperDateTimestamp = weekUpperDate.getTime();
    	Date weekLowerDate = dateFormat.parse("2016-05-21");
    	long weekLowerDateTimestamp = weekLowerDate.getTime();
    	String makeQueryString = "type:Elco" + " AND " + "SUBMISSIONDATE:["	+ weekUpperDateTimestamp +" TO " + weekLowerDateTimestamp +"]" ;
    	LuceneResult result = luceneElcoRepository.findDocsByProvider(makeQueryString);
        System.out.println(result.getRows().size() + " -week Count for may");
        System.out.println(result.getTotalRows() + " -week Count for may");
       
		
        /*assertNotNull("Expecting a non null result", result); 
        assertTrue("Should only have one result", result.getRows().size() >=0); */
    } 
}
