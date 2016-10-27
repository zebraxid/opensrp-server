package dashboard.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.dashboard.domain.DashboardLocation;
import org.opensrp.dashboard.domain.LocationTag;
import org.opensrp.dashboard.domain.Privilege;
import org.opensrp.dashboard.dto.LocationTagDTO;
import org.opensrp.dashboard.repository.AllDashboardLocations;
import org.opensrp.dashboard.repository.AllLocationTags;
import org.opensrp.dashboard.repository.AllPrivileges;
import org.opensrp.dashboard.service.LocationTagService;
import org.springframework.beans.factory.annotation.Autowired;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register-mcare.xml")*/
public class LocationTagServiceTest {
 
	@Autowired
	private LocationTagService locationTagService;
	@Autowired  
	private AllLocationTags allLocationTags;
	private CouchDbInstance dbInstance;
	private StdCouchDbConnector stdCouchDbConnector;
	
    @Before
    public void setUp() throws Exception {
       HttpClient httpClient = new StdHttpClient.Builder() 
        .host("localhost") 
       	//.host("192.168.19.55")
        .port(5984) 
        .socketTimeout(1000) 
        .build(); 
		dbInstance = new StdCouchDbInstance(httpClient); 
		
		stdCouchDbConnector = new StdCouchDbConnector("opensrp_testdb", dbInstance, new StdObjectMapperFactory());
		 
		stdCouchDbConnector.createDatabaseIfNotExists();
		allLocationTags = new AllLocationTags(2, stdCouchDbConnector);
		locationTagService = new LocationTagService(allLocationTags);
    }

    @Ignore @Test 
    public void testLocationTagService() throws Exception {
    	/*LocationTag tag = new LocationTag();
    	tag.withName("Country").withParentTagId("");
    	allLocationTags.add(tag);*/
    	
    	/*LocationTag fetchedTag = allLocationTags.findLocationTagByName("Country");
    	assertNotNull(fetchedTag);*/
    	
    	LocationTag countryTag = allLocationTags.get("87ab5c6407741290fe50d6bfbc00c7c7");
    	assertNotNull(countryTag);
    	assertEquals("Country", countryTag.getName());
    	
    	/*
    	 * delete test, first one is id for which document does not exist
    	 * assertEquals("2", locationTagService.deleteLocationTag("87ab5c6407741290fe50d6bfbc00d56v"));
    	assertEquals("1", locationTagService.deleteLocationTag("87ab5c6407741290fe50d6bfbc00d56d"));*/    	    	
    	
    	/*LocationTagDTO newTag = new LocationTagDTO();
    	newTag.withName("Division");
    	newTag.withParentTagId("87ab5c6407741290fe50d6bfbc00c7c7");
    	
    	locationTagService.addLocationTag(newTag);
    	
    	assertNotNull(allLocationTags.findLocationTagByName(newTag.getName()));*/
    	
    	/*LocationTag division = new LocationTag();
    	division.withName("Division").withParentTagId(ff.getId());*/    	
    	
    }    
}
