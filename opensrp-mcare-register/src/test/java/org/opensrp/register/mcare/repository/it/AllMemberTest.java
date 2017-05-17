package org.opensrp.register.mcare.repository.it;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
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
import org.joda.time.DateTime;
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
		enc.put("encounterTypeUuid", "03aecf69-953d-11e6-90c1-005056b01095");
		enc.put("encounterDateTime", new DateTime());
		enc.put("patientUuid", "d50d4174-16d4-4387-ab76-c021cf71594e");
		enc.put("locationUuid", "4f2b8e02-f9b5-47b5-afdc-fe6ca7d50f7d");
		//enc.put("encounterUuid", "02f0939b-e307-4f63-bacb-e32ac4bc7283");
		enc.put("visitType", "field");
		//enc.put("visitUuid", "72a3ee07-0e7f-4f56-a7b6-c30b0665b787");
		JSONObject providers = new JSONObject();
		JSONArray providerArray = new JSONArray();
		providers.put("uuid", "0da40551-08cb-4f13-94f9-04de483e3f6b");
		providerArray.put(providers);
		
		JSONObject observations = new JSONObject();
		JSONObject concept = new JSONObject();
		concept.put("uuid", "021cb967-953d-11e6-90c1-005056b01095");//Immunization Incident Template
		
		//inner group members
		
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
		innerGroupMember.put(innerGroupMember3);
		
		// group member property
		JSONObject groupMember = new JSONObject();
		groupMember.put("groupMembers", innerGroupMember);
		
		
		
		JSONObject conceptInner = new JSONObject();
		conceptInner.put("uuid", "021d28cd-953d-11e6-90c1-005056b01095"); //Immunization Incident Group
		conceptInner.put("name", "Immunization Incident Group");
		groupMember.put("concept", conceptInner);
		
		
		groupMember.put("isObservation", true);
		//groupMember.put("uniqueId", "observation_45");
		
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
	
	//4f2b8e02-f9b5-47b5-afdc-fe6ca7d50f7d
	@Test
	public void bahmniEncounterTest2() throws JSONException{
		JSONObject enc = new JSONObject();
		enc.put("encounterTypeUuid", "03aecf69-953d-11e6-90c1-005056b01095");
		enc.put("encounterDateTime", new DateTime());
		enc.put("patientUuid", "69fa8817-0aab-4f97-b494-2347b2d28daf");//malin
		enc.put("visitType", "field");
		enc.put("locationUuid", "4f2b8e02-f9b5-47b5-afdc-fe6ca7d50f7d");
		JSONObject providers = new JSONObject();
		JSONArray providerArray = new JSONArray();
		providers.put("uuid", "0da40551-08cb-4f13-94f9-04de483e3f6b");
		providerArray.put(providers);
		
		JSONObject observations = new JSONObject();
		JSONObject concept = new JSONObject();
		concept.put("dataType", "N/A");
		concept.put("name", "Immunization Incident Template");
		concept.put("uuid", "021cb967-953d-11e6-90c1-005056b01095");
		
		///inner group members
		
		JSONArray innerGroupMember = new JSONArray();
		
		
		/// group memener vacine name
		JSONObject innerGroupMember1 = new JSONObject();
		JSONObject innerGroupMember1Concept = new JSONObject();
		//innerGroupMember1Concept.put("dataType", "Coded");
		innerGroupMember1Concept.put("name", "Immunization Incident Vaccine");
		innerGroupMember1Concept.put("uuid", "021e0705-953d-11e6-90c1-005056b01095");
		innerGroupMember1.put("concept", innerGroupMember1Concept);
		
		
		JSONObject innerGroupMember1Value = new JSONObject();
		innerGroupMember1Value.put("name", "TT3");
		innerGroupMember1Value.put("uuid", "8197f5b8-328c-402d-8128-fd04d9de8a94");
		innerGroupMember1Value.put("value", "TT3");
		innerGroupMember1.put("value", innerGroupMember1Value);
		
		
		
		/// group memener for dose
		JSONObject innerGroupMember2 = new JSONObject();
		JSONObject innerGroupMember2Concept = new JSONObject();
		innerGroupMember2Concept.put("dataType", "Numeric");
		innerGroupMember2Concept.put("name", "Immunization Incident Vaccination Dosage");
		innerGroupMember2Concept.put("uuid", "021efde7-953d-11e6-90c1-005056b01095");
		innerGroupMember2.put("concept", innerGroupMember2Concept);
		
		innerGroupMember2.put("value", 4);
		
		
		/// group memener for reported 
		JSONObject innerGroupMember3 = new JSONObject();
		JSONObject innerGroupMember3Concept = new JSONObject();
		innerGroupMember3Concept.put("dataType", "Boolean");
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
		
	}
	@Ignore@Test
	public void EncounterTest() throws JSONException{
		JSONObject enc = new JSONObject();
		//enc.put("encounterDatetime", "2017-04-05");
		//enc.put("provider", "3e03cbab-bb1b-427c-96c1-3ad4b1d63e9b");
		//enc.put("patient", "00f88b53-0b98-4fee-b09f-bd612dd9fc47");
		//enc.put("encounterType", "Woman TT Follow Up");
		//enc.put("location", "b767bf43-3cb0-49b6-8fb3-06a0625e5dd3");
		enc.put("encounterTypeUuid", "03aecf69-953d-11e6-90c1-005056b010954");
		enc.put("encounterDateTime", new DateTime());
		enc.put("patientUuid", "ccbbe9e4-7470-419e-b5e7-c2eddaef6175");
		enc.put("locationUuid", "4f2b8e02-f9b5-47b5-afdc-fe6ca7d50f7d");
		enc.put("visitType", "field");
		//enc.put("encounterUuid", "02f0939b-e307-4f63-bacb-e32ac4bc7283");
		JSONObject providers = new JSONObject();
		JSONArray providerArray = new JSONArray();
		providers.put("uuid", "0da40551-08cb-4f13-94f9-04de483e3f6b");
		providerArray.put(providers);
		enc.put("providers", providerArray);
		
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
		ob2.put("value", "5");		
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
		
	
		
		enc.put("observations", obar1);
		
		
		System.out.println("Going to create Encounter: " + enc.toString());
		HttpResponse op = HttpUtil.post("https://103.247.238.26/openmrs/ws/rest/v1/bahmnicore/bahmniencounter", "", enc.toString(),"sohel", "Sohel@123");
		System.out.println(op.getClass());
		
		
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
