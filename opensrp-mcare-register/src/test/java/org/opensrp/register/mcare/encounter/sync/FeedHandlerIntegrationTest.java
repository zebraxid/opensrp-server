package org.opensrp.register.mcare.encounter.sync;

import java.lang.reflect.Field;
import java.util.Map;

import junit.framework.Assert;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.encounter.sync.FeedHandler;
import org.opensrp.register.encounter.sync.FormFatcory;
import org.opensrp.register.encounter.sync.forms.ChildVaccineFollowup;
import org.opensrp.register.encounter.sync.forms.WomanTTFollowUp;
import org.opensrp.register.encounter.sync.interfaces.FormsType;
import org.opensrp.register.encounter.sync.mapping.repository.AllEncounterSyncMapping;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;

import com.google.gson.Gson;

public class FeedHandlerIntegrationTest extends TestConfig {	
	@Mock
	FormSubmission formSubmission;
	@Mock
    private AllFormSubmissions formSubmissions;
	@Mock
	private AllMembers allMembers;
	@Mock
	private AllEncounterSyncMapping allEncounterSyncMapping;
	@Before
	public void setUp() throws Exception
	{
		//formSubmissions = Mockito.mock(AllFormSubmissions.class);
		formSubmissions = new AllFormSubmissions(getStdCouchDbConnectorForOpensrpForm());
		allMembers = new AllMembers(1,getStdCouchDbConnectorForOpensrp());
		allEncounterSyncMapping = new AllEncounterSyncMapping(1,getStdCouchDbConnectorForOpensrp());
	}
	
	@Ignore@Test
	public void shouldCheckWhichHasTTVisit() throws Exception{		
		Woman woman = new Woman();
		Members member = woman.getWomanMember();		
		member.setTTVisit(woman.getTTVaccine());
		//allMembers.add(member);
		ObjectMapper members = new ObjectMapper();    	
    	Gson gson = new Gson();
        gson.toJson(member);         
        //System.out.println("GOOO"+gson.toJson(member).valueOf("caseId"));           
		JSONObject encounter = new JSONObject();
		JSONObject ob1Object = new JSONObject();
		JSONArray obs = new JSONArray();
		ob1Object.put("display", 
       "Immunization Incident Template: TT 4 (Tetanus toxoid), 2016-02-28, true, 4.0");
		ob1Object.put("uuid", "08db9795-1016-4a3e-9174-cb030962b173");
		JSONObject ob2Object = new JSONObject();		
		ob2Object.put("display", 
			       "Immunization Incident Template: 2014-05-28, true, 1.0, TT 1 (Tetanus toxoid)");
		ob2Object.put("uuid", "e013eaf3-b8fc-4954-94a9-1691af8ddb49");
		obs.put(ob1Object);
		//obs.put(ob2Object);
		encounter.put("obs", obs);		
		WomanTTFollowUp womanTTForm = WomanTTFollowUp.getInstance();	
		FeedHandler feedHandler =  new FeedHandler(allMembers,formSubmissions,allEncounterSyncMapping);
		feedHandler.setFormDirectory("./../assets/form");
		feedHandler.getEvent(encounter, "e1e16f38-01d8-42ae-be55-4573b3ac349e",member);		
		Assert.assertEquals(member.TTVisit().isEmpty(), false);	
		//Mockito.doNothing().when(formSubmissions).add(Matchers.any(FormSubmission.class));		
	}
	
	@Ignore@Test
	public void shouldCheckWhichHasGivenVisit() throws Exception{		
		Woman woman = new Woman();
		Members member = woman.getWomanMember();		
		member.setTTVisit(woman.getTTVaccine());
		//allMembers.add(member);
		ObjectMapper members = new ObjectMapper();    	
    	Gson gson = new Gson();
        gson.toJson(member);         
        //System.out.println("GOOO"+gson.toJson(member).valueOf("caseId"));           
		JSONObject encounter = new JSONObject();
		JSONObject ob1Object = new JSONObject();
		JSONArray obs = new JSONArray();
		ob1Object.put("display", 
       "Immunization Incident Template: TT 2 (Tetanus toxoid), 2016-02-28, true, 2.0");
		ob1Object.put("uuid", "08db9795-1016-4a3e-9174-cb030962b173");
		JSONObject ob2Object = new JSONObject();		
		ob2Object.put("display", 
			       "Immunization Incident Template: 2014-05-28, true, 1.0, TT 1 (Tetanus toxoid)");
		ob2Object.put("uuid", "e013eaf3-b8fc-4954-94a9-1691af8ddb49");
		obs.put(ob1Object);
		//obs.put(ob2Object);
		encounter.put("obs", obs);		
		WomanTTFollowUp womanTTForm = WomanTTFollowUp.getInstance();	
		FeedHandler feedHandler =  new FeedHandler(allMembers,formSubmissions,allEncounterSyncMapping);
		feedHandler.setFormDirectory("./../assets/form");
		feedHandler.getEvent(encounter, "e1e16f38-01d8-42ae-be55-4573b3ac349e",member);		
		Assert.assertEquals(member.TTVisit().isEmpty(), false);	
		//Mockito.doNothing().when(formSubmissions).add(Matchers.any(FormSubmission.class));		
	}
	
	
	@Ignore@Test
	public void shouldCheckWhichHasNoTTVisit() throws Exception{
		Woman woman = new Woman();
		Members member = woman.getWomanMember();		
		JSONObject encounter = new JSONObject();
		JSONObject ob1Object = new JSONObject();
		JSONArray obs = new JSONArray();
		ob1Object.put("display", 
       "Immunization Incident Template: TT 2 (Tetanus toxoid), 2010-05-28, true, 2.0");
		ob1Object.put("uuid", "08db9795-1016-4a3e-9174-cb030962b173");
		JSONObject ob2Object = new JSONObject();		
		ob2Object.put("display", 
			       "Immunization Incident Template: 2008-05-28, true, 1.0, TT 1 (Tetanus toxoid)");
		ob2Object.put("uuid", "e013eaf3-b8fc-4954-94a9-1691af8ddb49");
		//obs.put(ob1Object);
		obs.put(ob2Object);
		encounter.put("obs", obs);		
		ObjectMapper mapper = new ObjectMapper();
		Members user = new Members();
		//Object to JSON in file
		String jsonStr = mapper.writeValueAsString(member);		
		JSONObject jsonObj = new JSONObject(jsonStr);		
		Members mem = new Members();		
		Field field = mem.getClass().getDeclaredField("caseId");
		field.setAccessible(true);		
		WomanTTFollowUp womanTTForm = WomanTTFollowUp.getInstance();	
		FeedHandler feedHandler =  new FeedHandler(allMembers,formSubmissions,allEncounterSyncMapping);
		feedHandler.setFormDirectory("./../assets/form");
		feedHandler.getEvent(encounter, "e1e16f38-01d8-42ae-be55-4573b3ac349e",member);	
		Assert.assertEquals(member.TTVisit().isEmpty(), true);		
		Assert.assertEquals(womanTTForm.checkingVaccineGivenOrNot(member, 1,""),false);
		//Mockito.doNothing().when(formSubmissions).add(Matchers.any(FormSubmission.class));
	}
	
	@Ignore@Test
	public void shouldCheckWhichIsActuallyIsNotAWoman() throws Exception{
		Woman woman = new Woman();
		Members member = woman.getWomanMember();
		member.setIs_woman("0");
		member.setTTVisit(woman.getTTVaccine());		
		ObjectMapper members = new ObjectMapper();    	
    	Gson gson = new Gson();
        gson.toJson(member);         
        //System.out.println("GOOO"+gson.toJson(member).valueOf("caseId"));           
		JSONObject encounter = new JSONObject();
		JSONObject ob1Object = new JSONObject();
		JSONArray obs = new JSONArray();
		ob1Object.put("display", 
       "Immunization Incident Template: TT 2 (Tetanus toxoid), 2016-02-28, true, 2.0");
		ob1Object.put("uuid", "08db9795-1016-4a3e-9174-cb030962b173");
		JSONObject ob2Object = new JSONObject();		
		ob2Object.put("display", 
			       "Immunization Incident Template: 2014-05-28, true, 1.0, TT 1 (Tetanus toxoid)");
		ob2Object.put("uuid", "e013eaf3-b8fc-4954-94a9-1691af8ddb49");
		obs.put(ob1Object);
		//obs.put(ob2Object);
		encounter.put("obs", obs);		
		WomanTTFollowUp womanTTForm = WomanTTFollowUp.getInstance();	
		FeedHandler feedHandler =  new FeedHandler(allMembers,formSubmissions,allEncounterSyncMapping);
		feedHandler.setFormDirectory("./../assets/form");
		feedHandler.getEvent(encounter, "e1e16f38-01d8-42ae-be55-4573b3ac349e",member);		
		Assert.assertEquals(member.TTVisit().isEmpty(), false);	
		Mockito.doNothing().when(formSubmissions).add(Matchers.any(FormSubmission.class));
	}
	
	@Ignore@Test
	public void shouldCheckWhichHasAtLeastOneChildVisit() throws Exception{		
		Child child = new Child();
		Members member = child.getChildMember();
		member.child_vaccine().add(child.getChildVaccine());
		
		JSONObject encounter = new JSONObject();
		JSONObject ob1Object = new JSONObject();
		JSONArray obs = new JSONArray();
		ob1Object.put("display", 
       "Immunization Incident Template: BCG (Tuberculosis, live attenuated), 2016-08-25, true, 0.0");
		ob1Object.put("uuid", "0c114163-948b-45a9-9f0b-786b0f5cb5ba");
		JSONObject ob2Object = new JSONObject();		
		ob2Object.put("display", 
			       "Immunization Incident Template: 2016-07-28, true, OPV (Poliomyelitis oral, trivalent, live attenuated), 1.0");
		ob2Object.put("uuid", "ba80a737-78bc-4933-9530-65527ce28b0a");		
		JSONObject ob3Object = new JSONObject();
		ob3Object.put("display", "Immunization Incident Template: PCV 2 (Pneumococcus, purified polysaccharides antigen conjugated), 2016-07-15, 2.0, true");
		ob3Object.put("uuid", "5f8d190f-c51d-4f59-a3b0-247ca1e4e7d8");		
		
		//obs.put(ob1Object);
		obs.put(ob2Object);
		//obs.put(ob3Object);
		encounter.put("obs", obs);		
		
		//FormsType<ChildVaccineFollowup> childVaccineFollowUp= FormFatcory.getFormsTypeInstance("CVF");
		//FormSubmission formsubmissionEntity= childVaccineFollowUp.getFormSubmission("./../assets/form","2017-04-03",1, member,"OPV",null, formSubmissions,allMembers);
		FeedHandler feedHandler =  new FeedHandler(allMembers,formSubmissions,allEncounterSyncMapping);
		feedHandler.setFormDirectory("./../assets/form");		
		//ChildVaccineFollowup childVaccine = ChildVaccineFollowup.getInstance();	
		feedHandler.getEvent(encounter, "05cbaa2b-d3a6-40f6-a604-328bf725ddbf",member);
		//System.err.println("member.TTVisit():"+member.child_vaccine().toString());		
		//Mockito.doNothing().when(formSubmissions).add(Matchers.any(FormSubmission.class));		
	}
	
	
	@Ignore@Test
	public void shouldCheckWhichHasGivenAVisit() throws Exception{		
		Child child = new Child();
		Members member = child.getChildMember();
		Map<String, String> childVaccine = child.getChildVaccine();
		childVaccine.put("final_opv1", "2016-07-15");
		member.child_vaccine().add(childVaccine);
		
		JSONObject encounter = new JSONObject();
		JSONObject ob1Object = new JSONObject();
		JSONArray obs = new JSONArray();
		ob1Object.put("display", 
       "Immunization Incident Template: BCG (Tuberculosis, live attenuated), 2016-08-25, true, 0.0");
		ob1Object.put("uuid", "0c114163-948b-45a9-9f0b-786b0f5cb5ba");
		JSONObject ob2Object = new JSONObject();		
		ob2Object.put("display", 
			       "Immunization Incident Template: 2016-07-28, true, OPV (Poliomyelitis oral, trivalent, live attenuated), 1.0");
		ob2Object.put("uuid", "Pa80a737-78bc-4933-9530-65527ce28b0a");		
		JSONObject ob3Object = new JSONObject();
		ob3Object.put("display", "Immunization Incident Template: PCV 2 (Pneumococcus, purified polysaccharides antigen conjugated), 2016-07-15, 2.0, true");
		ob3Object.put("uuid", "5f8d190f-c51d-4f59-a3b0-247ca1e4e7d8");		
		
		//obs.put(ob1Object);
		obs.put(ob2Object);
		//obs.put(ob3Object);
		encounter.put("obs", obs);		
		
		//FormsType<ChildVaccineFollowup> childVaccineFollowUp= FormFatcory.getFormsTypeInstance("CVF");
		//FormSubmission formsubmissionEntity= childVaccineFollowUp.getFormSubmission("./../assets/form","2017-04-03",1, member,"OPV",null, formSubmissions,allMembers);
		FeedHandler feedHandler =  new FeedHandler(allMembers,formSubmissions,allEncounterSyncMapping);
		feedHandler.setFormDirectory("./../assets/form");		
		//ChildVaccineFollowup childVaccine = ChildVaccineFollowup.getInstance();	
		feedHandler.getEvent(encounter, "05cbaa2b-d3a6-40f6-a604-328bf725ddbf",member);
		//System.err.println("member.TTVisit():"+member.child_vaccine().toString());		
		//Mockito.doNothing().when(formSubmissions).add(Matchers.any(FormSubmission.class));		
	}
	@Ignore@Test
	public void shouldCheckChildVisitWhichIsActualllyNotAChild() throws Exception{		
		Child child = new Child();
		Members member = child.getChildMember();
		member.setIs_child("0");
		member.child_vaccine().add(child.getChildVaccine());
		
		JSONObject encounter = new JSONObject();
		JSONObject ob1Object = new JSONObject();
		JSONArray obs = new JSONArray();
		ob1Object.put("display", 
       "Immunization Incident Template: BCG (Tuberculosis, live attenuated), 2016-08-25, true, 0.0");
		ob1Object.put("uuid", "0c114163-948b-45a9-9f0b-786b0f5cb5ba");
		JSONObject ob2Object = new JSONObject();		
		ob2Object.put("display", 
			       "Immunization Incident Template: 2016-07-28, true, OPV (Poliomyelitis oral, trivalent, live attenuated), 2.0");
		ob2Object.put("uuid", "ba80a737-78bc-4933-9530-65527ce28b0a");		
		JSONObject ob3Object = new JSONObject();
		ob3Object.put("display", "Immunization Incident Template: PCV 2 (Pneumococcus, purified polysaccharides antigen conjugated), 2016-07-15, 2.0, true");
		ob3Object.put("uuid", "5f8d190f-c51d-4f59-a3b0-247ca1e4e7d8");		
		
		obs.put(ob1Object);
		//obs.put(ob2Object);
		encounter.put("obs", obs);		
		
		//FormsType<ChildVaccineFollowup> childVaccineFollowUp= FormFatcory.getFormsTypeInstance("CVF");
		//FormSubmission formsubmissionEntity= childVaccineFollowUp.getFormSubmission("./../assets/form","2017-04-03",1, member,"OPV",null,formSubmissions,allMembers);
		FeedHandler feedHandler =  new FeedHandler(allMembers,formSubmissions,allEncounterSyncMapping);
		feedHandler.setFormDirectory("./../assets/form");		
		//ChildVaccineFollowup childVaccine = ChildVaccineFollowup.getInstance();	
		feedHandler.getEvent(encounter, "05cbaa2b-d3a6-40f6-a604-328bf725ddbf",member);			
		//Mockito.doNothing().when(formSubmissions).add(Matchers.any(FormSubmission.class));		
	}
	
	@Ignore@Test
	public void shouldCheckWhichHasNoChildVisit() throws Exception{
		Child child = new Child();
		Members member = child.getChildMember();
		JSONObject encounter = new JSONObject();
		JSONObject ob1Object = new JSONObject();
		JSONArray obs = new JSONArray();
		ob1Object.put("display", 
       "Immunization Incident Template: BCG (Tuberculosis, live attenuated), 2016-08-25, true, 0.0");
		ob1Object.put("uuid", "0c114163-948b-45a9-9f0b-786b0f5cb5ba");
		JSONObject ob2Object = new JSONObject();		
		ob2Object.put("display", 
			       "Immunization Incident Template: 2016-07-28, true, OPV (Poliomyelitis oral, trivalent, live attenuated), 0.0");
		ob2Object.put("uuid", "ba80a737-78bc-4933-9530-65527ce28b0a");		
		JSONObject ob3Object = new JSONObject();
		ob3Object.put("display", "Immunization Incident Template: PCV 2 (Pneumococcus, purified polysaccharides antigen conjugated), 2016-07-15, 2.0, true");
		ob3Object.put("uuid", "5f8d190f-c51d-4f59-a3b0-247ca1e4e7d8");		
		obs.put(ob1Object);
		
		//obs.put(ob3Object);
		//obs.put(ob3Object);
		encounter.put("obs", obs);	
		
		//FormsType<ChildVaccineFollowup> childVaccineFollowUp= FormFatcory.getFormsTypeInstance("CVF");
		//FormSubmission formsubmissionEntity= childVaccineFollowUp.getFormSubmission("./../assets/form","2017-04-03",1, member,"OPV", null,formSubmissions,allMembers);
		FeedHandler feedHandler =  new FeedHandler(allMembers,formSubmissions,allEncounterSyncMapping);
		feedHandler.setFormDirectory("./../assets/form");
		//ChildVaccineFollowup childVaccine = ChildVaccineFollowup.getInstance();	
		feedHandler.getEvent(encounter, "05cbaa2b-d3a6-40f6-a604-328bf725ddbf",member);
		//Mockito.doNothing().when(formSubmissions).add(Matchers.any(FormSubmission.class));		
	}

}
