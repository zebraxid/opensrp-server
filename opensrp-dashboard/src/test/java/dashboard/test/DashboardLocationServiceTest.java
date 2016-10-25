package dashboard.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
import org.opensrp.dashboard.dto.DashboardLocationDTO;
import org.opensrp.dashboard.dto.DashboardLocationInfoDTO;
import org.opensrp.dashboard.dto.LocationTagDTO;
import org.opensrp.dashboard.dto.SimplifiedLocation;
import org.opensrp.dashboard.dto.UserDTO;
import org.opensrp.dashboard.repository.AllDashboardLocations;
import org.opensrp.dashboard.repository.AllLocationTags;
import org.opensrp.dashboard.repository.AllPrivileges;
import org.opensrp.dashboard.service.DashboardLocationService;
import org.opensrp.dashboard.service.LocationTagService;
import org.springframework.beans.factory.annotation.Autowired;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register-mcare.xml")*/
public class DashboardLocationServiceTest {
 
	@Autowired  
	private AllDashboardLocations allDashboardLocations;
	@Autowired  
	private AllLocationTags allLocationTags;
	@Autowired
	private DashboardLocationService dashboardLocationService;
	@Autowired
	private LocationTagService locationTagService;
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
		
		stdCouchDbConnector = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());
		 
		stdCouchDbConnector.createDatabaseIfNotExists();
		allDashboardLocations = new AllDashboardLocations(2, stdCouchDbConnector);
		allLocationTags = new AllLocationTags(2, stdCouchDbConnector);
		dashboardLocationService = new DashboardLocationService(allDashboardLocations, allLocationTags);
		locationTagService  = new LocationTagService(allLocationTags);
    }

    @Ignore@Test 
    public void testDashboardLocationService() throws Exception {
    	/*
    	 * Code for creating Country 
    	 * 
    	 * LocationTag countryTag = allLocationTags.findLocationTagByName("Country");
		DashboardLocationDTO countryDTO = new DashboardLocationDTO();
		countryDTO.withName("Bangladesh");
		countryDTO.withParentname("");
		countryDTO.withParentId("");
		countryDTO.withTagId(countryTag.getId());
		dashboardLocationService.addDashboardLocation(countryDTO);*/
		
		/*DashboardLocation countryLocation = allDashboardLocations.findDashboardLocationByName("Bangladesh");
		assertNotNull(countryLocation);
		
		LocationTagDTO divisionTag = locationTagService.getLocationTag("Division");
		assertEquals("Division", divisionTag.getName());
		
		DashboardLocationDTO divisionDTO = new DashboardLocationDTO();
		divisionDTO.withName("Chittagong");
		divisionDTO.withParentname(countryLocation.getName());
		divisionDTO.withParentId(countryLocation.getId());
		//divisionDTO.withTagId(divisionTag.getId());
		assertEquals("2", dashboardLocationService.addDashboardLocation(divisionDTO));
		if(divisionDTO.getTagId() == null)
			System.out.println(divisionDTO.getId());
		
		List<DashboardLocationDTO> children = dashboardLocationService.getChildrenLocations(countryLocation.getId());
		for(int i =0 ; i < children.size(); i++){
			System.out.println(children.get(i).getName());
		}		
		
		//dashboardLocationService.getChildrenLocations(countryLocation.getId());		
		
		assertEquals(2, children.size());*/
		
		/*assertEquals(2, allDashboardLocations.findLocationsByParentAndTag("87ab5c6407741290fe50d6bfbc00ec73", "87ab5c6407741290fe50d6bfbc00e0b9").size());
		List<DashboardLocationDTO> locations = dashboardLocationService.getChildrenLocationsOfRoot();
		for(int i=0; i< locations.size(); i++){
			System.out.println(locations.get(i).getName());
		}*/
		
		List<LocationTag> tags = allLocationTags.getAll();
		String idForUpazillaTag = "";
		for(LocationTag tag :tags){
			if(tag.getName().equals("Division")){
				idForUpazillaTag = tag.getId();
			}
		}
		System.out.println(idForUpazillaTag);
		System.out.println(allDashboardLocations.findLocationsByTag(idForUpazillaTag).size());
		
		/*DashboardLocation newDashboardLocation = new DashboardLocation();
		newDashboardLocation.withName("kola");
		newDashboardLocation.withParentName("Khulna");
		newDashboardLocation.withParentId("71fff7052d0e1c392b2b448813008e48");
		newDashboardLocation.withTagId("1e84829ef6153b9b8f05089b28051142");
		allDashboardLocations.add(newDashboardLocation);
		
		System.out.println(newDashboardLocation.getId());*/
		
		/*DashboardLocationDTO sampleWard = new DashboardLocationDTO();
		sampleWard.withName("Sample Ward");
		sampleWard.withParentId("71fff7052d0e1c392b2b4488130087fa");
		sampleWard.withParentname("Union_Upa_Gazi");
		sampleWard.withTagId("8e8fa8966b1e39997011f42f64009e97");
		
		dashboardLocationService.addDashboardLocation(sampleWard);
		
		String store = sampleWard.getId();
		*/
		//dashboardLocationService.deleteDashboardLocation("12b0f9fc6457e2f58950e582f9011260");
		
		UserDTO withNoLocation = new UserDTO();
		withNoLocation.withName("passTest");
		List<SimplifiedLocation> locations = new ArrayList<SimplifiedLocation>();
		withNoLocation.withLocation(locations);
		System.out.println(withNoLocation.getLocation());
	
		System.out.println("-- " + dashboardLocationService.getDashboardLocationsByTag("8e8fa8966b1e39997011f42f64008c41").size());
		DashboardLocationInfoDTO allInfo = dashboardLocationService.getDashboardLocationInfo("71fff7052d0e1c392b2b4488130087fa");
		if(allInfo.getParentDistrict() != null){
			System.out.println(allInfo.getParentDistrict().getName());
		}
		
		Queue<String> strings = new LinkedList<String>();
		strings.add("first");
		strings = append(strings, "second");
		System.out.println(strings.size());
		
		//type: "DashboardLocation", name: "Jhenaidah", tagId: "1e84829ef6153b9b8f05089b28051142", parentName: "Khulna", parentId: "71fff7052d0e1c392b2b448813008e48"
		
    }
    
    public Queue<String> append(Queue<String> in, String str){
    	in.add(str);
    	return in;
    }
    
    /*@Test 
    public void check() throws Exception {		
		Privilege privilege = new Privilege();
		privilege.withName("testPrivilege");
		privilege.withStatus("testStatus");
		allPrivileges.add(privilege);	
    }*/
}
