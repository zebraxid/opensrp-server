package org.opensrp.register.mcare;


import com.google.gson.Gson;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.common.util.HttpResponse;
import org.opensrp.connector.HttpUtil;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertFalse;

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
    
    @Ignore@Test
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
    @Ignore@Test
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

    

   @Ignore @Test
  public void failedTest() {
        assertFalse(false);
  }

    @Test
    public void EncounterTest() throws JSONException {
        ObjectMapper mapper = new ObjectMapper();
       /* JSONObject enc = new JSONObject();
        enc.put("encounterDatetime", "2017-04-05");
        enc.put("provider", "99f68f5f-2278-4b1b-bd64-1af3991b1148");
        enc.put("patient", "9cf5582f-dfb5-478e-9e35-a386f9c3f9d2");
        enc.put("location", "4d836403-9f95-11e6-a293-000c299c7c5d");
        enc.put("encounterType", "Eligible Couple");


        JSONArray obar1 = new JSONArray();
        JSONObject obs1 = new JSONObject();
        obs1.put("concept", "163084AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        obs1.put("value", "1234567654309");
        obar1.put(obs1);
        JSONObject obs2 = new JSONObject();
        obs2.put("concept", "163087AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        obs2.put("value", "163084AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        obar1.put(obs2);*/



        //enc.put("obs", obar1);
        //enc.put("obs", obar2);
     JsonNode enc = null;
     try {
            enc = mapper.readValue(new File("src/test/java/org/opensrp/register/mcare/validEncounterJson/ElcoFollowUp.json"), JsonNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Going to create Encounter: " + enc.toString());
        HttpResponse op = HttpUtil.post("http://192.168.19.28:8080/openmrs/ws/rest/v1/encounter", "", enc.toString(),"admin", "mPower@1234");
        //System.out.println(new Gson().toJson(op));
    }
}



