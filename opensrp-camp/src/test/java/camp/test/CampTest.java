package camp.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.camp.dao.CampDate;
import org.opensrp.camp.repository.CampDateRepository;
import org.opensrp.camp.service.CampDateService;
import org.opensrp.common.util.DateTimeUtil;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.repository.AllActions;
import org.springframework.beans.factory.annotation.Autowired;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register-mcare.xml")*/
public class CampTest {
 
	@Autowired  
	private CampDateRepository campDateRepository;
	
	@Autowired
	private CampDateService campDateService;
	
	@Autowired
	private AllActions allActions;
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
		
		campDateRepository = new CampDateRepository(2, stdCouchDbConnector);
		campDateService = new CampDateService();
		allActions = new AllActions(stdCouchDbConnector);
		
    }
    private long getTimeStamp(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");		
		Date day = null;		
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, 1);	    
	    String today = dateFormat.format(now.getTime());	   
		try {
			day = dateFormat.parse(today);			
		} catch (ParseException e) {			
			e.printStackTrace();
		}		
		return day.getTime();
	}

    @Ignore@Test 
    public void testDashboardLocationService() throws Exception {
    	
		/*List<CampDate> ca=  campDateRepository.search("13f2e137e3e1e878713443a6e805ed7c", "13f2e137e3e1e878713443a6e8063bf0", "13f2e137e3e1e878713443a6e8064b23", "13f2e137e3e1e878713443a6e8064fe7", "sohel",6);
		//List<CampDate> ca=  campDateRepository.search("13f2e137e3e1e878713443a6e805ed7c", "");
		System.out.println(ca);
		for (CampDate campDate : ca) {
	        System.out.println(campDate.getId());
        }*/
    }
    
   @Ignore @Test
	public void shouldGeTodaysCamp(){
		
	}
}
