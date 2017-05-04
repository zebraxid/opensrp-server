package org.opensrp.register.mcare.repository.it;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
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

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
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
	public void bahmniEncounterTest() throws JSONException{
		JSONObject enc = new JSONObject();
		enc.put("encounterTypeUuid", "910d3315-0632-4c5b-9e2a-32916d0a8004");
		enc.put("encounterDateTime", "2017-04-20T10:19:18.000+0600");
		enc.put("patientUuid", "00f88b53-0b98-4fee-b09f-bd612dd9fc47");
		
		JSONObject providers = new JSONObject();
		JSONArray providerArray = new JSONArray();
		providers.put("uuid", "dd967c60-5089-4e49-82be-ef7990439619");
		providerArray.put(providers);
		
		JSONObject observations = new JSONObject();
		JSONObject concept = new JSONObject();
		concept.put("uuid", "021cb967-953d-11e6-90c1-005056b01095");//Immunization Incident Template
		
		///inner group members
		
		JSONArray innerGroupMember = new JSONArray();
		
		
		/// group memener 1
		JSONObject innerGroupMember1 = new JSONObject();
		JSONObject innerGroupMember1Concept = new JSONObject();
		innerGroupMember1Concept.put("uuid", "021e0705-953d-11e6-90c1-005056b01095");//Immunization Incident Vaccine
		innerGroupMember1.put("concept", innerGroupMember1Concept);
		
		
		JSONObject innerGroupMember1Value = new JSONObject();		
		innerGroupMember1Value.put("uuid", "9c6b6047-137c-48fe-8955-3ceea1bd1327");	//TT 3	
		innerGroupMember1.put("value", innerGroupMember1Value);
		
		
		
		/// group member 2
		JSONObject innerGroupMember2 = new JSONObject();
		JSONObject innerGroupMember2Concept = new JSONObject();
		innerGroupMember2Concept.put("uuid", "021efde7-953d-11e6-90c1-005056b01095");//Immunization Incident Vaccination Dosage
		innerGroupMember2.put("concept", innerGroupMember2Concept);		
		innerGroupMember2.put("value", 1);
		
		
		/// group member 2
		JSONObject innerGroupMember3 = new JSONObject();
		JSONObject innerGroupMember3Concept = new JSONObject();
		innerGroupMember3Concept.put("uuid", "021f7622-953d-11e6-90c1-005056b01095");
		innerGroupMember3.put("concept", innerGroupMember3Concept);		
		innerGroupMember3.put("value", true);
		
		innerGroupMember.put(innerGroupMember1);
		innerGroupMember.put(innerGroupMember2);
		
		
		// group member property
		JSONObject groupMember = new JSONObject();
		groupMember.put("groupMembers", innerGroupMember);
		
		JSONObject conceptInner = new JSONObject();
		conceptInner.put("uuid", "021d28cd-953d-11e6-90c1-005056b01095"); //Immunization Incident Group
		groupMember.put("concept", conceptInner);
		
		
		
		// group member array property
		JSONArray groupMemberArray = new JSONArray();
		groupMemberArray.put(groupMember);
		
		JSONArray observationsArray = new JSONArray();
		observations.put("conceptSetName", "Immunization Incident Group");
		observations.put("concept", concept);
		observations.put("groupMembers", groupMemberArray);
		
		observationsArray.put(observations);
		
		enc.put("providers", providerArray);
		enc.put("observations", observationsArray);
		System.out.println("Going to create Encounter: " + enc.toString());
		
		
		HttpResponse op = HttpUtil.post("https://103.247.238.26/openmrs/ws/rest/v1/bahmnicore/bahmniencounter", "", enc.toString(),"sohel", "Sohel@123");
		System.out.println(op.getClass());
		
	}
	
	
	/*@Test
	public void bahmniEncounterTest() throws JSONException{
		JSONObject enc = new JSONObject();
		enc.put("encounterTypeUuid", "910d3315-0632-4c5b-9e2a-32916d0a8004");
		enc.put("encounterDateTime", "2017-04-20T10:19:18.000+0600");
		enc.put("patientUuid", "00f88b53-0b98-4fee-b09f-bd612dd9fc47");
		
		JSONObject providers = new JSONObject();
		JSONArray providerArray = new JSONArray();
		providers.put("uuid", "dd967c60-5089-4e49-82be-ef7990439619");
		providerArray.put(providers);
		
		JSONObject observations = new JSONObject();
		JSONObject concept = new JSONObject();
		concept.put("dataType", "N/A");
		concept.put("name", "Immunization Incident Template");
		concept.put("uuid", "021cb967-953d-11e6-90c1-005056b01095");
		
		///inner group members
		
		JSONArray innerGroupMember = new JSONArray();
		
		
		/// group memener 1
		JSONObject innerGroupMember1 = new JSONObject();
		JSONObject innerGroupMember1Concept = new JSONObject();
		//innerGroupMember1Concept.put("dataType", "Coded");
		innerGroupMember1Concept.put("name", "Immunization Incident Vaccine");
		innerGroupMember1Concept.put("uuid", "021e0705-953d-11e6-90c1-005056b01095");
		innerGroupMember1.put("concept", innerGroupMember1Concept);
		
		
		JSONObject innerGroupMember1Value = new JSONObject();
		//innerGroupMember1Value.put("name", "TT 3");
		innerGroupMember1Value.put("uuid", "8197f5b8-328c-402d-8128-fd04d9de8a94");
		//innerGroupMember1Value.put("value", "TT 3");
		innerGroupMember1.put("value", innerGroupMember1Value);
		
		
		
		/// group memener 2
		JSONObject innerGroupMember2 = new JSONObject();
		JSONObject innerGroupMember2Concept = new JSONObject();
		//innerGroupMember2Concept.put("dataType", "Numeric");
		innerGroupMember2Concept.put("name", "Immunization Incident Vaccination Dosage");
		innerGroupMember2Concept.put("uuid", "021efde7-953d-11e6-90c1-005056b01095");
		innerGroupMember2.put("concept", innerGroupMember2Concept);
		
		innerGroupMember2.put("value", 1);
		
		
		/// group memener 2
		JSONObject innerGroupMember3 = new JSONObject();
		JSONObject innerGroupMember3Concept = new JSONObject();
		//innerGroupMember3Concept.put("dataType", "Boolean");
		innerGroupMember3Concept.put("name", "Immunization Incident Vaccination Reported");
		innerGroupMember3Concept.put("uuid", "021f7622-953d-11e6-90c1-005056b01095");
		innerGroupMember3.put("concept", innerGroupMember3Concept);		
		innerGroupMember3.put("value", true);
		
		
		
		
		innerGroupMember.put(innerGroupMember1);
		innerGroupMember.put(innerGroupMember2);
		innerGroupMember.put(innerGroupMember3);
		
		// group member property
		JSONObject groupMember = new JSONObject();
		groupMember.put("groupMembers", innerGroupMember);
		groupMember.put("isObservation", true);
		JSONObject conceptInner = new JSONObject();
		conceptInner.put("dataType", "N/A");
		conceptInner.put("name", "Immunization Incident Group");
		conceptInner.put("uuid", "021d28cd-953d-11e6-90c1-005056b01095");
		groupMember.put("concept", conceptInner);
		
		
		
		// group member array property
		JSONArray groupMemberArray = new JSONArray();
		groupMemberArray.put(groupMember);
		
		JSONArray observationsArray = new JSONArray();
		observations.put("conceptSetName", "Immunization Incident Group");
		observations.put("concept", concept);
		observations.put("groupMembers", groupMemberArray);
		observations.put("label", "Immunization Incident");
		observationsArray.put(observations);
		
		enc.put("providers", providerArray);
		enc.put("observations", observationsArray);
		System.out.println("Going to create Encounter: " + enc.toString());
		
		
		HttpResponse op = HttpUtil.post("https://103.247.238.26/openmrs/ws/rest/v1/bahmnicore/bahmniencounter", "", enc.toString(),"sohel", "Sohel@123");
		System.out.println(op.getClass());
		
	}*/
	@Ignore@Test
	public void EncounterTest() throws JSONException{
		JSONObject enc = new JSONObject();
		enc.put("encounterDatetime", "2017-04-05");
		enc.put("provider", "3e03cbab-bb1b-427c-96c1-3ad4b1d63e9b");
		enc.put("patient", "00f88b53-0b98-4fee-b09f-bd612dd9fc47");
		enc.put("encounterType", "Woman TT Follow Up");
		
		//021e0705-953d-11e6-90c1-005056b01095
		JSONArray obar1 = new JSONArray();
		JSONObject obs1 = new JSONObject();
		obs1.put("concept", "021e0705-953d-11e6-90c1-005056b01095");
		obs1.put("value", "c8e8ed67-d125-4c77-a8c7-2c9a5cf2c46b");
		obar1.put(obs1);		
		
		
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
		obs2.put("concept", "021e0705-953d-11e6-90c1-005056b01095");
		obs2.put("value", "021e0705-953d-11e6-90c1-005056b01095");
		
		//obar1.put(obs2);
		
		
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
		
		/*System.out.println("Going to create Encounter: " + enc.toString());
		HttpResponse op = HttpUtil.post("https://103.247.238.26/openmrs/ws/rest/v1/encounter", "", enc.toString(),"sohel", "Sohel@123");
		System.out.println(op.getClass());*/
		String c = "dd";
		StringBuilder sb = new StringBuilder();
		sb.append("DDD"+" ");
		sb.append("T dddT"+" ");
		sb.append("R dddT"+" ");
		System.out.println(sb);
		
	}
	public enum Member{
		CHILD(1),WOMAN(1);
		  
	
	  public int value;  
	  Member(int value){  
		  this.value=value;  
	  }
	}  
	@Test
	public void getResourceFile() throws FileNotFoundException{
		
		/*JsonNode enc = null;
		ObjectMapper mapper = new ObjectMapper();
	     try {
	            enc = mapper.readValue(new File("./../assets/form/woman_tt_form/form_definition.json"), JsonNode.class);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		 System.out.println(enc.toString());*/
		System.out.println(Member.CHILD.name());
		System.out.println(Member.CHILD.value);
		for(Member s : Member.values())  
		System.out.println(s+" "+s.value);  
		  
		}
	
	
}
