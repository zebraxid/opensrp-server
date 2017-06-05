package org.opensrp.register.mcare.encounter.sync;

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
import org.opensrp.register.encounter.sync.interfaces.FormsType;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;

public class ChildVaccinationFollowUpIntegrationTest extends TestConfig {	
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
	public void shouldCheckWhichHasAtLeastOneVisit() throws Exception{
		
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
		obs.put(ob3Object);
		//obs.put(ob1Object);
		//obs.put(ob2Object);
		encounter.put("obs", obs);		
		
		FormsType<ChildVaccineFollowup> childVaccineFollowUp= FormFatcory.getFormsTypeInstance("CVF");
		FormSubmission formsubmissionEntity= childVaccineFollowUp.getFormSubmission("./../assets/form","2017-04-03",1,"6a1332be-5c19-4e26-b7cb-5851d27b68bd", member,"OPV");
		FeedHandler feedHandler =  new FeedHandler(allMembers,formSubmissions);
		feedHandler.setFormDirectory("./../assets/form");		
		ChildVaccineFollowup childVaccine = ChildVaccineFollowup.getInstance();	
		feedHandler.getEvent(encounter, "05cbaa2b-d3a6-40f6-a604-328bf725ddbf",member);
		//System.err.println("member.TTVisit():"+member.child_vaccine().toString());		
		Mockito.doNothing().when(formSubmissions).add(Matchers.any(FormSubmission.class));		
	}
	
	@Test
	public void shouldCheckWhichHasNoVisit() throws Exception{
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
		//obs.put(ob1Object);
		obs.put(ob2Object);
		//obs.put(ob3Object);
		encounter.put("obs", obs);		
		FormsType<ChildVaccineFollowup> childVaccineFollowUp= FormFatcory.getFormsTypeInstance("CVF");
		FormSubmission formsubmissionEntity= childVaccineFollowUp.getFormSubmission("./../assets/form","2017-04-03",1,"05cbaa2b-d3a6-40f6-a604-328bf725ddbf", member,"OPV");
		FeedHandler feedHandler =  new FeedHandler(allMembers,formSubmissions);
		feedHandler.setFormDirectory("./../assets/form");
		ChildVaccineFollowup childVaccine = ChildVaccineFollowup.getInstance();	
		feedHandler.getEvent(encounter, "05cbaa2b-d3a6-40f6-a604-328bf725ddbf",member);
		Mockito.doNothing().when(formSubmissions).add(Matchers.any(FormSubmission.class));		
	}

}
