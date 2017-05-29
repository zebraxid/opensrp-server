package org.opensrp.register.mcare.encounter.sync;

import static org.opensrp.common.AllConstants.CommonFormFields.ID;
import static org.opensrp.common.AllConstants.HHRegistrationFields.END_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.REFERENCE_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.START_DATE;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Father_name;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Husband_name;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Is_Reg_Today;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Marital_status;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Member_Address_line;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Member_BLOCK;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Member_COUNTRY;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Member_DISTRICT;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Member_DIVISION;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Member_Fname;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Member_GPS;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Member_HIE_facilities;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Member_Paurasava;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Member_UNION;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Member_UPAZILLA;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Member_WARD;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.address1;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.address_change;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.address_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.birth_date_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.calc_age_confirm;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.calc_dob_confirm;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.center_gps;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.contact_phone_number;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_tt1;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_tt2;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_tt3;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_tt4;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_tt5;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.edd;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.edd_calc_lmp;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.edd_calc_lmp_formatted;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.edd_calc_ultrasound;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.edd_calc_ultrasound_formatted;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.edd_lmp;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.epi_card_number;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.epi_card_number_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.existing_contact_phone_number;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.existing_location;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.father_name_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_edd;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_edd_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_ga;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_ga_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_lmp;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_lmp_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.first_name_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.ga_edd;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.ga_lmp;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.ga_ult;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.husband_name_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.landmark;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.lmp;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.lmp_calc_edd;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.lmp_calc_edd_formatted;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.lmp_calc_ultrasound;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.lmp_calc_ultrasound_formatted;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.marriage;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.pregnant;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.provider_id;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.provider_location_id;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.provider_location_name;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.provider_location_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt1;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt1_final;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt1_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt1_retro;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt2;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt2_final;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt2_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt2_retro;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt3;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt3_final;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt3_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt3_retro;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt4;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt4_final;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt4_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt4_retro;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt5;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt5_final;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt5_retro;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt_1_dose;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt_1_dose_today;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt_2_dose;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt_2_dose_today;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt_3_dose;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt_3_dose_today;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt_4_dose;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt_4_dose_today;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt_5_dose;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.tt_5_dose_today;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.ultrasound_date;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.ultrasound_weeks;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.vaccines;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.vaccines1;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.vaccines1_2;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.vaccines_2;
import static org.opensrp.common.util.EasyMap.create;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

import junit.framework.Assert;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.encounter.sync.FeedHandler;
import org.opensrp.register.encounter.sync.forms.WomanTTFollowUp;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;

import com.google.gson.Gson;

public class WomanTTFollowUpTest extends TestConfig {	
	@Mock
	FormSubmission formSubmission;
	@Mock
    private AllFormSubmissions formSubmissions;
	@Mock
	private AllMembers allMembers;
	@Before
	public void setUp() throws Exception
	{
		formSubmissions = new AllFormSubmissions(getStdCouchDbConnectorForOpensrpForm());
		allMembers = new AllMembers(1,getStdCouchDbConnectorForOpensrp());
	}
	
	@Test
	public void shouldCheckWhichHasTTVisit() throws JSONException{
		
		Members member = new Members();		
		member.setCaseId("1ace9084-8e71-45b7-b2b1-aeea66c203c2");
		member.setPROVIDERID("sujan");
		member.setINSTANCEID("8f3166f9-ae37-4d9c-9294-54cdb31e7546");
		member.setMember_COUNTRY("");
		member.setMember_DIVISION("Dhaka");
		member.setMember_DISTRICT("Gazipur");
		member.setMember_UPAZILLA("Kaliganj");
		member.setMember_UNION("Urban Ward No-01");
		member.setMember_WARD("Ward-1");
		member.setMember_BLOCK("1-KA");
		member.setMember_HIE_facilities("Kaliganj TW (10019869)");
		member.setMember_GPS("");
		member.setMember_Address_line(null);
		member.setMember_type("1");
		member.setMember_Fname("robff");
		member.setMember_Fname("fhj");
		member.setHusband_name("dfgjk");
		member.setMarital_status("2");
		member.setPregnant("yes");
		member.setedd("");
		member.setEdd_lmp("");
		member.setLmp("");
		member.setUltrasound_date("");
		member.setUltrasound_weeks("");
		member.setEdd_calc_lmp("Invalid Date");
		member.setEdd_calc_ultrasound("Invalid Date");
		member.setEdd_calc_lmp_formatted("Invalid Date");
		member.setEdd_calc_ultrasound_formatted("Invalid Date");
		member.setLmp_calc_edd("Invalid Date");
		member.setLmp_calc_ultrasound("Invalid Date");
		member.setLmp_calc_edd_formatted("Invalid Date");
		member.setLmp_calc_ultrasound_formatted("Invalid Date");
		member.setFinal_edd("2017-05-22");
		member.setFinal_lmp("2016-8-15");
		member.setGa_edd("NaN");
		member.setGa_lmp("NaN");
		member.setGa_ult("NaN");
		member.setFinal_edd_note("");
		member.setFinal_lmp_note("");
		member.setFinal_ga("");
		member.setFinal_ga_note("");
		member.setTt1_retro("2012-05-22");
		member.setTt_1_dose("1");
		member.setTt1_final("2012-05-22");
		member.setTt2_final("");
		member.setTt3_final("");
		member.setTt4_final("");
		member.setTt5_final("");
		
		Map<String, String> TTVisit = create(ID, "1ace9084-8e71-45b7-b2b1-aeea66c203c2")
				.put(REFERENCE_DATE, "2017-05-22")
				.put(START_DATE, "2017-05-22 17:23:59")
				.put(END_DATE, "2017-05-22 17:24:22")											
				.put(address_change	,"")
				.put(address_note	,"")
				.put(address1	,"")
				.put(birth_date_note	,"")
				.put(calc_age_confirm	,"21")
				.put(calc_dob_confirm	,"1996-05-22")
				.put(center_gps	,null)
				.put(contact_phone_number	,null)
				.put(e_tt1	,"")
				.put(e_tt2	,"")
				.put(e_tt3	,"")
				.put(e_tt4	,"")
				.put(e_tt5	,"")
				.put(edd	,"")
				.put(edd_calc_lmp	,"Invalid Date")
				.put(edd_calc_lmp_formatted	,"Invalid Date")
				.put(edd_calc_ultrasound	,"Invalid Date")
				.put(edd_calc_ultrasound_formatted	,"Invalid Date")
				.put(edd_lmp	,"")
				.put(epi_card_number	,"25874123")
				.put(epi_card_number_note	,"")
				.put(existing_contact_phone_number	,null)
				.put(existing_location	,null)
				.put(Father_name	,"dfgfhfh")
				.put(father_name_note	,"")
				.put(final_edd	,"2017-05-22")
				.put(final_edd_note	,"")
				.put(final_ga	,"")
				.put(final_ga_note	,"")
				.put(final_lmp	,"2016-8-15")
				.put(final_lmp_note	,"")
				.put(first_name_note	,"")
				.put(ga_edd	,"")
				.put(ga_lmp	,"NaN")
				.put(ga_ult	,"NaN")
				.put(Husband_name	, "dfgdfghg")				
				.put(husband_name_note	,"")
				.put(landmark	,"")
				.put(lmp	,"Invalid Date")
				.put(lmp_calc_edd	,"Invalid Date")
				.put(lmp_calc_edd_formatted	,"Invalid Date")
				.put(lmp_calc_ultrasound	,"Invalid Date")
				.put(lmp_calc_ultrasound_formatted	,"Invalid Date")
				.put(Marital_status	,"2")
				.put(marriage	,"")
				.put(Member_Address_line	,null)
				.put(Member_BLOCK	,"1-KA")
				.put(Member_COUNTRY	,"null")
				.put(Member_DISTRICT	,"Gazipur")
				.put(Member_DIVISION	,"Dhaka")
				.put(Member_Fname	,"robff")
				.put(Member_GPS	,"")
				.put(Member_HIE_facilities	,"Kaliganj TW (10019869)")
				.put(Member_Paurasava	,"Kaliganj Paurashava")
				.put(Member_UNION	,"Urban Ward No-01")
				.put(Member_UPAZILLA	,"Kaliganj")
				.put(Member_WARD	,"Ward-1")
				.put(pregnant	,"yes")				
				.put(provider_id	,"")
				.put(provider_location_id	,"")
				.put(provider_location_name	,"")
				.put(provider_location_note	,"")
				.put(tt_1_dose	,"1")
				.put(tt_1_dose_today	,null)
				.put(tt_2_dose	,null)
				.put(tt_2_dose_today	,null)
				.put(tt_3_dose	,null)
				.put(tt_3_dose_today	,null)
				.put(tt_4_dose	,null)
				.put(tt_4_dose_today	,null)
				.put(tt_5_dose_today	,null)
				.put(tt1	,null)
				.put(tt1_final	,"2012-05-22")
				.put(tt1_note	,"")
				.put(tt1_retro	,"2012-05-22")
				.put(tt2	,null)
				.put(tt2_final	,"")
				.put(tt2_note	,"")
				.put(tt2_retro	,null)
				.put(tt3	,null)
				.put(tt3_final	,"")
				.put(tt3_note	,"")
				.put(tt3_retro	,null)
				.put(tt4	,null)
				.put(tt4_final	,"")
				.put(tt4_note	,"")
				.put(tt4_retro	,null)
				.put(tt5	,null)
				.put(tt5_final	,"")
				.put(tt5_retro	,null)
				.put(tt_5_dose	,null)
				.put(ultrasound_date	,"")
				.put(ultrasound_weeks	,"")
				.put(vaccines	,"TT1")
				.put(vaccines_2	,null)
				.put(Is_Reg_Today	,"0")
				.put(vaccines1	,"TT1")
				.put(vaccines1_2	,null)
				.map();	
		
		member.setTTVisit(TTVisit);
		
		ObjectMapper members = new ObjectMapper();
    	//members.writeValueAsString(member);
    	Gson gson = new Gson();
           gson.toJson(member);
          
          System.out.println("GOOO"+gson.toJson(member).valueOf("caseId"));
           
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
		System.out.println(encounter.toString());
		WomanTTFollowUp womanTTForm = WomanTTFollowUp.getInstance();	
		FeedHandler feedHandler =  new FeedHandler(allMembers,formSubmissions);
		feedHandler.setFormDirectory("./../assets/form");
		FormSubmission formSubmission = feedHandler.getEvent(encounter, "1ace9084-8e71-45b7-b2b1-aeea66c203c2",member);
		System.err.println("member.TTVisit():"+member.TTVisit());
		Assert.assertEquals(member.TTVisit().isEmpty(), false);		
		Assert.assertEquals(womanTTForm.isThisVaccineGiven(member, 1),true);		
		Assert.assertNotNull(formSubmission);
		formSubmissions.remove(formSubmission);
		
	}
	
	
	@Test
	public void shouldCheckWhichHasNoTTVisit() throws JSONException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, JsonGenerationException, JsonMappingException, IOException{
		System.err.println(""+UUID.randomUUID().toString());
		Members member = new Members();		
		member.setCaseId("e1e16f38-01d8-42ae-be55-4573b3ac349e");
		member.setPROVIDERID("sujan");
		member.setINSTANCEID("b73ec00b-17d4-40c9-b021-a857cf57369b");
		member.setMember_COUNTRY("");
		member.setMember_DIVISION("Dhaka");
		member.setMember_DISTRICT("Gazipur");
		member.setMember_UPAZILLA("Kaliganj");
		member.setMember_UNION("Urban Ward No-01");
		member.setMember_WARD("Ward-1");
		member.setMember_BLOCK("1-KA");
		member.setMember_HIE_facilities("Kaliganj TW (10019869)");
		member.setMember_GPS("");
		member.setMember_Address_line(null);
		member.setMember_type("1");
		member.setMember_Fname("robff");
		member.setMember_Fname("fhj");
		member.setHusband_name("dfgjk");
		member.setMarital_status("2");
		member.setPregnant("yes");
		member.setedd("");
		member.setEdd_lmp("");
		member.setLmp("");
		member.setUltrasound_date("");
		member.setUltrasound_weeks("");
		member.setEdd_calc_lmp("Invalid Date");
		member.setEdd_calc_ultrasound("Invalid Date");
		member.setEdd_calc_lmp_formatted("Invalid Date");
		member.setEdd_calc_ultrasound_formatted("Invalid Date");
		member.setLmp_calc_edd("Invalid Date");
		member.setLmp_calc_ultrasound("Invalid Date");
		member.setLmp_calc_edd_formatted("Invalid Date");
		member.setLmp_calc_ultrasound_formatted("Invalid Date");
		member.setFinal_edd("2017-05-22");
		member.setFinal_lmp("2016-8-15");
		member.setGa_edd("NaN");
		member.setGa_lmp("NaN");
		member.setGa_ult("NaN");
		member.setFinal_edd_note("");
		member.setFinal_lmp_note("");
		member.setFinal_ga("");
		member.setFinal_ga_note("");
		member.setTt1_retro("2012-05-22");
		member.setTt_1_dose("1");
		member.setTt1_final("2012-05-22");
		member.setTt2_final("");
		member.setTt3_final("");
		member.setTt4_final("");
		member.setTt5_final("");	
		
		
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
		obs.put(ob1Object);
		//obs.put(ob2Object);
		encounter.put("obs", obs);
		
		ObjectMapper mapper = new ObjectMapper();
		Members user = new Members();

		//Object to JSON in file
		String jsonStr = mapper.writeValueAsString(member);		
		JSONObject jsonObj = new JSONObject(jsonStr);		
		Members mem = new Members();		
		Field field = mem.getClass().getDeclaredField("caseId");
		field.setAccessible(true);
				
		//System.err.println("value:"+jsonObj.get(field.getName()));	
		
		System.out.println(encounter.toString());
		WomanTTFollowUp womanTTForm = WomanTTFollowUp.getInstance();	
		FeedHandler feedHandler =  new FeedHandler(allMembers,formSubmissions);
		feedHandler.setFormDirectory("./../assets/form");
		FormSubmission formSubmission = feedHandler.getEvent(encounter, "e1e16f38-01d8-42ae-be55-4573b3ac349e",member);
	
		Assert.assertEquals(member.TTVisit().isEmpty(), true);		
		Assert.assertEquals(womanTTForm.isThisVaccineGiven(member, 1),false);
		Assert.assertNotNull(formSubmission);
		formSubmissions.remove(formSubmission);
		
		
	}

}
