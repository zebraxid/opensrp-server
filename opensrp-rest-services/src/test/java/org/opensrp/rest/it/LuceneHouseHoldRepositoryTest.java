package org.opensrp.rest.it;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.util.UUID;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.support.OpenCouchDbDocument;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.common.AllConstants;
import org.opensrp.repository.AllBaseEntities;
import org.opensrp.rest.repository.LuceneHouseHoldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;
import com.github.ldriscoll.ektorplucene.LuceneResult;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:test-applicationContext-opensrp-rest-services.xml")
public class LuceneHouseHoldRepositoryTest {
	
	private LuceneAwareCouchDbConnector connector; 
    private LuceneHouseHoldRepository repo; 
    
/*
	@Autowired
	@Qualifier(AllConstants.OPENSRP_DATABASE_LUCENE_CONNECTOR)
	LuceneAwareCouchDbConnector connector;

	@Autowired
	private LuceneHouseHoldRepository repo;*/

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
        repo = new  LuceneHouseHoldRepository(connector); 
        //createDocuments(); 
    } 
 
 
    @After 
    public void tearDown() { 
       // deleteDocuments(); 
        connector = null; 
    } 
 
    private void createDocuments() { 
        updateDocument("test1", "field1", "test"); 
        updateDocument("test2", "field1", "test"); 
        updateDocument("test2", "field2", "here"); 
    } 
 
    private void deleteDocuments() { 
        deleteDocument("test1"); 
        deleteDocument("test2"); 
    } 
 
    private void updateDocument(String name, String field, String value) { 
        final OpenCouchDbDocument doc; 
        if (connector.contains(name)) { 
            doc = connector.get(OpenCouchDbDocument.class, name); 
        } else { 
            doc = new OpenCouchDbDocument(); 
            doc.setId(name); 
            connector.create(doc); 
        } 
 
        doc.setAnonymous(field, value); 
        connector.update(doc); 
    } 
 
    private void deleteDocument(String name) { 
        if (connector.contains(name)) { 
            OpenCouchDbDocument doc = connector.get(OpenCouchDbDocument.class, name); 
            connector.delete(doc); 
        } 
    }
    
    @Test 
    public void testInit() { 
 
        LuceneResult result = repo.findDocsByProviderAndUpazilla("opensrp", "SUNDARGANJ","FWA"); 
        assertNotNull("Expecting a non null result", result); 
        assertTrue("Should only have one result", result.getRows().size() == 1); 
    } 
}
