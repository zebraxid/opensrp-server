package org.opensrp.register.mcare;


import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register-mcare.xml")
public class AllMembersTest {
	
	//@Autowired
    private AllHouseHolds allHouseHolds;
    @Autowired
	private AllMembers allMembers;

	
	
   // @Before
    public void setUp() throws Exception {
    
       
    }
    
    @Test
    public void simpleTest() {
    	assert(true);
    }
    
  //  @Test
    public void shouldRegister() throws Exception {
    	Members currentMembers = allMembers.findByCaseId("7e33380e-68ae-4f67-ad2e-4ca0d9600f1c");
		 String json = new Gson().toJson(currentMembers);
		// System.out.println(currentMembers.size());
        System.out.println("members::"+json);

    }
    @Test
    public void shouldRegisterEligibleCouple() throws Exception {
    	List<Members> currentMembers = allMembers.allMembersCreatedBetweenTwoDateBasedOnProviderId(
    	        "opensrp");
		 String json = new Gson().toJson(currentMembers);
		 System.out.println(currentMembers.size());
      //  System.out.println(json);
        int countBirthPill = 0;
        
        for(Members member: currentMembers) {
            if(member.details().containsKey("Birth_Control")) {
                System.out.println(member.details().get("Birth_Control"));
                if(member.details().get("Birth_Control").equalsIgnoreCase("1")) {
            	    countBirthPill++;
                }
            }
        }

        System.out.println(countBirthPill);

    }

    @Test
  public void failedTest() {
        assertFalse(false);
  }

}
