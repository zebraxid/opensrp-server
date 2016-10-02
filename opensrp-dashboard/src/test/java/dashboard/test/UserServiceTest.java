package dashboard.test;

import static org.junit.Assert.*;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.dashboard.domain.Privilege;
import org.opensrp.dashboard.domain.Role;
import org.opensrp.dashboard.domain.User;
import org.opensrp.dashboard.repository.AllPrivileges;
import org.opensrp.dashboard.repository.AllRoles;
import org.opensrp.dashboard.repository.AllUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;


/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register-mcare.xml")*/
public class UserServiceTest {
 
	@Autowired  
	private AllUser allUsers;
	private AllRoles allRoles;
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
		allUsers = new AllUser(2, stdCouchDbConnector);
		allRoles = new AllRoles(2, stdCouchDbConnector);
    	//initMocks(this);
    }   

    @Test 
    public void testUserService() throws Exception {
    	/*String pass = "Pass45";
    	byte[] encodedPass = Base64.encode(pass.getBytes());
    	//byte[] encodedByteArray =
    	String encodedString = new String(Base64.encode(pass.getBytes()));
    	System.out.println(encodedString);
    	//String x =  encodedPass.toString();
    	byte[] decodedPass = Base64.decode(encodedString.getBytes());
    	String decodedString = new String(Base64.decode(encodedString.getBytes()));
    	System.out.println(decodedString);*/
    	
    	
    	//System.out.println(); 
    	
    	User user = allUsers.findUserByUserName("sohel");
		assertNotNull(user);
		System.out.println(user.getPassword());
		/*decodedPass = Base64.decode(user.getPassword().getBytes());
		System.out.println(new String(decodedPass));*/
		/*user.withPassword(encodedString);
		allUsers.update(user);*/
		
		/*user = allUsers.findUserByUserName("demosrp");
		System.out.println(user.getPassword());
		System.out.println(Base64.decode(user.getPassword().getBytes()).equals(user.getPassword().getBytes()));*/
		//System.out.println(Base64.encode(pass.getBytes()).toString());
		
		/*if(Base64.encode(pass.getBytes()).toString().equals(user.getPassword())){
			System.out.println("ok");
		}*/
		
		
		
		/*System.out.println(user.getPassword());
		if(user.getPassword().getBytes().equals(encodedPass)){
			System.out.println("ass saved.");
		}
		System.out.println();*/
		/*Role role = allRoles.findRoleById(user.getRoles().get(0).getId());// "d32766973465b8847aad7220633da341");
		for(int i = 0 ; i < role.getPrivileges().size(); i++){
			System.out.println(role.getPrivileges().get(i).getName());
		}*/	
    }
    
}
