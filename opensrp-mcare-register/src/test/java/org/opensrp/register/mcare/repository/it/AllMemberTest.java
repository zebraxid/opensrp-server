package org.opensrp.register.mcare.repository.it;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.common.util.HttpResponse;
import org.opensrp.connector.HttpUtil;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMembers;


public class AllMemberTest {
	
	private AllMembers allMembers;
	private AllHouseHolds allHouseHolds;
	private CouchDbInstance dbInstance;
	private StdCouchDbConnector stdCouchDbConnector;
	
	@Before
    public void setUp() throws Exception {
       HttpClient httpClient = new StdHttpClient.Builder() 
        .host("localhost") 
       	//.host("192.168.19.55")
        .port(5984) 
        .username("Admin").password("mPower@1234")
        .socketTimeout(1000) 
        .build(); 
		dbInstance = new StdCouchDbInstance(httpClient); 		
		stdCouchDbConnector = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());		 
		stdCouchDbConnector.createDatabaseIfNotExists();		
		allMembers = new AllMembers(2, stdCouchDbConnector);
		allHouseHolds = new AllHouseHolds(2, stdCouchDbConnector);
		
    }
	
	@Ignore@Test
	public void shouldGetMemberByCaseId(){
		Members member = allMembers.findByCaseId("f34aa45d-6e89-445c-a894-22176b3c71ce");		
	    System.err.println(member.details().get("relationalid"));
	    System.err.println(member.Member_Fname());
        HouseHold houseHold = allHouseHolds.findByCaseId(member.details().get("relationalid"));
        System.err.println(houseHold.HoH_Mobile_No());
		
	}
	
	@Ignore@Test
	public void EncounterTest() throws JSONException{
		JSONObject enc = new JSONObject();
		enc.put("encounterDatetime", "2017-04-05");
		enc.put("provider", "3e03cbab-bb1b-427c-96c1-3ad4b1d63e9b");
		enc.put("patient", "5b3430a7-52f1-4d70-8acb-0bc59f455881");
		enc.put("encounterType", "Woman TT Follow Up");
		
		
		JSONArray obar1 = new JSONArray();
		JSONObject obs1 = new JSONObject();
		obs1.put("concept", "021e0705-953d-11e6-90c1-005056b01095");
		obs1.put("value", "TT 1");
		obar1.put(obs1);
		
		JSONArray obar2 = new JSONArray();
		JSONObject obs2 = new JSONObject();		
		JSONArray gruop1 = new JSONArray();		
		JSONObject ob1 = new JSONObject(); 
		ob1.put("concept", "021e8246-953d-11e6-90c1-005056b01095");
		ob1.put("value", "2017-03-23");
		
		JSONObject ob2 = new JSONObject(); 
		ob2.put("concept", "021efde7-953d-11e6-90c1-005056b01095");
		ob2.put("value", "1");		
		gruop1.put(ob1);
		gruop1.put(ob2);
		
		obs2.put("groupMembers", gruop1);
		obs2.put("concept", "be200e2b-0469-4cb3-b3c3-eeda1c51b336");
		
		obar1.put(obs2);
		
		
		JSONArray obar3 = new JSONArray();
		JSONObject obs3 = new JSONObject();		
		JSONArray gruop2 = new JSONArray();		
		JSONObject ob3 = new JSONObject(); 
		ob3.put("concept", "021e8246-953d-11e6-90c1-005056b01095");
		ob3.put("value", "2017-03-22");
		
		JSONObject ob4 = new JSONObject(); 
		ob4.put("concept", "021efde7-953d-11e6-90c1-005056b01095");
		ob4.put("value", "1");		
		gruop2.put(ob3);
		gruop2.put(ob4);
		
		obs3.put("groupMembers", gruop2);
		obs3.put("concept", "be200e2b-0469-4cb3-b3c3-eeda1c51b336");
		
		obar1.put(obs3);
		
	
		
		enc.put("obs", obar1);
		//enc.put("obs", obar2);
		//enc.put("obs", obar3);
		
		System.out.println("Going to create Encounter: " + enc.toString());
		HttpResponse op = HttpUtil.post("https://103.247.238.26/openmrs/ws/rest/v1/encounter", "", enc.toString(),"sohel", "Sohel@123");
		System.out.println(op.getClass());
	}
	
}
