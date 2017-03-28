package org.opensrp.rest.it;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.rest.repository.LuceneHouseHoldRepository;
import org.opensrp.rest.repository.LuceneMemberRepository;

import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;
import com.github.ldriscoll.ektorplucene.LuceneResult;

public class LuceneMemberRepositoryTest {
	
	private LuceneAwareCouchDbConnector connector; 
    private LuceneMemberRepository repo;
    
   @Before
    public void setUp() throws IOException { 
        HttpClient httpClient = new StdHttpClient.Builder() 
                .host("localhost") 
                .port(5984) 
                .socketTimeout(1000)  
                .build(); 
        CouchDbInstance instance = new StdCouchDbInstance(httpClient); 
 
        connector = new LuceneAwareCouchDbConnector("opensrp", instance); 
        connector.createDatabaseIfNotExists(); 
        repo = new  LuceneMemberRepository(2, connector);
    } 
 
 
    @After 
    public void tearDown() {  
        connector = null; 
    } 

    //@Test
    public void testNameSearch() throws ParseException {
    	String makeQueryString = "type:Members AND First_Name:h*" ;
    	LuceneResult result = repo.findDocsByName(makeQueryString);
    	System.out.println(result.getTotalRows());
    	System.out.println(makeQueryString);
    }
	

}
