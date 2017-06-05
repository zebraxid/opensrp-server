package org.opensrp.register.mcare.encounter.sync;

import java.lang.reflect.Field;

import junit.framework.Assert;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.encounter.sync.FeedHandler;
import org.opensrp.register.encounter.sync.forms.WomanTTFollowUp;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;

import com.google.gson.Gson;

public class WomanTTFollowUpIntegrationTest extends TestConfig {	
	@Mock
	FormSubmission formSubmission;
	@Mock
    private AllFormSubmissions formSubmissions;
	@Mock
	private AllMembers allMembers;
	@Before
	public void setUp() throws Exception
	{
		formSubmissions = Mockito.mock(AllFormSubmissions.class);
		//formSubmissions = new AllFormSubmissions(getStdCouchDbConnectorForOpensrpForm());
		allMembers = new AllMembers(1,getStdCouchDbConnectorForOpensrp());
	}	
	@Test
	public void shouldCheckWhichHasTTVisit() throws Exception{		
		Woman woman = new Woman();
		Members member = woman.getWomanMember();		
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
		FeedHandler feedHandler =  new FeedHandler(allMembers,formSubmissions);
		feedHandler.setFormDirectory("./../assets/form");
		feedHandler.getEvent(encounter, "1ace9084-8e71-45b7-b2b1-aeea66c203c2",member);		
		Assert.assertEquals(member.TTVisit().isEmpty(), false);	
		Mockito.doNothing().when(formSubmissions).add(Matchers.any(FormSubmission.class));		
	}	
	
	@Test
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
		FeedHandler feedHandler =  new FeedHandler(allMembers,formSubmissions);
		feedHandler.setFormDirectory("./../assets/form");
		feedHandler.getEvent(encounter, "e1e16f38-01d8-42ae-be55-4573b3ac349e",member);	
		Assert.assertEquals(member.TTVisit().isEmpty(), true);		
		Assert.assertEquals(womanTTForm.checkingVaccineGivenOrNot(member, 1,""),false);
		Mockito.doNothing().when(formSubmissions).add(Matchers.any(FormSubmission.class));
	}

}
