package org.opensrp.register.mcare.encounter.sync;

import static org.opensrp.common.AllConstants.CommonFormFields.ID;
import static org.opensrp.common.AllConstants.HHRegistrationFields.END_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.REFERENCE_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.START_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.received_time;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Child_dob;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Child_gender;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Child_mother_name;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Is_Reg_Today;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.MEMBER_ADDRESS_LINE;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.MEMBER_COUNTRY;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.MEMBER_DISTRICT;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.MEMBER_DIVISION;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.MEMBER_GPS;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.MEMBER_HIE_FACILITIES;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.MEMBER_PAURASAVA;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.MEMBER_UNION;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.MEMBER_UPAZILLA;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.MEMBER_WARD;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Member_BLOCK;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Member_Fname;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.Member_Reg_Date;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.address;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.address1;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.address_change;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.bcg_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.calc_dob_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.center_gps;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.child_age;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.child_age_days;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.child_vaccines;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.child_vaccines1;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.child_vaccines1_2;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.child_vaccines_2;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.child_was_suffering_from_a_disease_at_birth;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.child_was_suffering_from_a_disease_at_birth_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.contact_phone_number;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_bcg;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_ipv;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_measles1;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_measles2;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_opv0;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_opv1;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_opv2;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_opv3;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_pcv1;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_pcv2;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_pcv3;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_penta1;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_penta2;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.e_penta3;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.epi_card_number;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.epi_card_number_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.existing_client_reg_date_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.fifteen_months;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_bcg;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_ipv;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_measles1;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_measles2;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_opv0;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_opv1;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_opv2;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_opv3;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_pcv1;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_pcv2;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_pcv3;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_penta1;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_penta2;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.final_penta3;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.first_name_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.forteen_weeks;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.gender_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.ipv_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.landmark;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.measles1_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.measles2_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.mother_name_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.nine_months;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.opv0_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.opv1_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.opv2_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.opv3_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.pcv1_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.pcv2_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.pcv3_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.penta1_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.penta2_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.penta3_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.provider_id;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.provider_location_id;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.provider_location_name;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.provider_location_note;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.side_effects;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.six_weeks;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.ten_weeks;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.vaccination_date;
import static org.opensrp.common.util.EasyMap.create;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.opensrp.register.encounter.sync.forms.ChildVaccineFollowup;
import org.opensrp.register.encounter.sync.forms.WomanTTFollowUp;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;

public class ChildVaccinationFollowUpTest extends TestConfig {	
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
	public void shouldCheckWhichHasAtLeastOneVisit() throws JSONException{
		
		Members member = new Members();		
		member.setCaseId("6a1332be-5c19-4e26-b7cb-5851d27b68bd");
		member.setPROVIDERID("sujan");
		member.setINSTANCEID("558292f1-f486-4968-8687-837b19bf431c");
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
		member.setMember_Fname("molim");
		member.setMember_Unique_ID("");
		member.setChild_dob("2015-01-07");
		member.setChild_age_days("874") ;
		member.setChild_birth_date_note("");
		member.setChild_gender("1");
		member.setChild_mother_name("dghh");
		member.setChild_guardian_id("");
		member.setChild_Mother_NID("");
		member.setChild_Mother_BRID("");
		member.setChild_Father_NID("");
		member.setChild_Father_BRID("");
		member.setChild_Other_Guardian_NID("");
		member.setChild_Other_Guardian_BRID("");
		member.setChild_was_suffering_from_a_disease_at_birth("no");
		member.setContact_phone_number("");
		member.setBcg_retro("2016-08-25");
		member.setOpv0_retro("2016-07-28");
		member.setOpv0_dose("0");
		member.setPcv1_retro("2016-07-15");
		member.setPcv1_dose("1");
		member.setFinal_bcg("2016-08-25");
		member.setFinal_opv0("2016-07-28");
		member.setFinal_pcv1("2016-07-15");
		member.setFinal_opv1("");
		member.setFinal_penta1("");
		member.setFinal_pcv2("");
		member.setFinal_opv2("");
		member.setFinal_penta2("");
		member.setFinal_pcv3("");
		member.setFinal_opv3("");
		member.setFinal_penta3("");
		member.setFinal_ipv("");
		member.setFinal_measles1("");  
		member.setFinal_measles2("");  
		member.setMember_Reg_Date("2017-05-30");  
		member.setIs_Reg_Today("0"); 
		member.setIs_child("1");
		member.setrelationalid("0ae55ea6-bf72-4fe2-8fee-b31dd6d5428f") ;
		member.setMotherRelationalId("3d413001-d117-4bb9-a337-930412986d4f");	
	
		SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();		
		Map<String, String> vaccine = create(ID, "6a1332be-5c19-4e26-b7cb-5851d27b68bd")
				.put(REFERENCE_DATE,"2017-05-30")
				.put(START_DATE,"2017-05-30 10:04:10")
				.put(END_DATE,"2017-05-30 10:05:00")
				.put(Member_Fname,"molim")
				.put(Child_mother_name,"dghh")
				.put(Child_gender,"1")
				.put(Child_dob,"2015-01-07")
				.put(Member_Reg_Date,"2017-05-30")
				.put(MEMBER_COUNTRY	, "null")
				.put(MEMBER_DIVISION,"Dhaka")
				.put(MEMBER_DISTRICT,"Gazipur")
				.put(MEMBER_UPAZILLA,"Kaliganj")
				.put(MEMBER_PAURASAVA,"Kaliganj Paurashava")
				.put(MEMBER_UNION,"Urban Ward No-01")
				.put(MEMBER_WARD,"Ward-1")
				.put(Member_BLOCK,"1-KA")
				.put(MEMBER_HIE_FACILITIES,"Kaliganj TW (10019869)")
				.put(MEMBER_ADDRESS_LINE,"")
				.put(MEMBER_GPS,"")
				.put(epi_card_number,"25874128")
				.put(child_was_suffering_from_a_disease_at_birth,"no")
				.put(contact_phone_number
					    	, "")
				.put(e_bcg
					    	, "")
				.put(e_opv0
					    	, "")
				.put(e_penta2
					    	, "")
				.put(e_penta1
					    	, "")
				.put(e_penta3
					    	,"")
				.put(e_opv1
					    	, "")
				.put(e_opv2
					    	, "")
				.put(e_opv3
					    	, "")
				.put(e_pcv1
					    	, "")
				.put(e_pcv2
					    	, "") 
				.put(e_pcv3
					    	, "")
				.put(e_ipv
					    	, "")
				.put(e_measles1
					    	, "")
				.put(e_measles2
					    	, "")
				.put(provider_id
					    	, "")
				.put(provider_location_id
					    	, "")
				.put(provider_location_name
					    	, "")
				.put(provider_location_note
					    	, "")
				.put(existing_client_reg_date_note
					    	,"")
				.put(epi_card_number_note
					    	, "")
				.put(first_name_note
					    	, "")
				.put(child_age
					    	, "28")
				.put(child_age_days
							, "874")
				.put(calc_dob_note
					    	, "")
				.put(gender_note
					    	, "")
				.put(mother_name_note
					    	, "")
				.put(address
					    	, "")
				.put(address_change
					    	, "")
				.put(address1
					    	, "")
				.put(landmark
					    	, "")
				.put(center_gps
					    	, "")
				.put(child_was_suffering_from_a_disease_at_birth_note
					    	, "")
				.put(side_effects
					    	, "")
				.put(six_weeks
					    	, null)
				.put(ten_weeks
					    	, null)
				.put(forteen_weeks
					    	, null)
				.put(nine_months
					    	, null)
				.put(fifteen_months
					    	, null)
				.put(bcg_note
					    	, "")
				.put(opv0_note
					    	, "")
				.put(opv1_note
					    	, "")
				.put(pcv1_note
					    	, "")
				.put(penta1_note
					    	, "")
				.put(opv2_note
					    	, "")
				.put(pcv2_note
					    	, "")
				.put(penta2_note
					    	, "")
				.put(opv3_note
					    	, "")
				.put(pcv3_note
					    	, "")
				.put(penta3_note
					    	, "")
				.put(ipv_note
					    	, "")
				.put(measles1_note
					    	, "")
				.put(measles2_note
					    	, "")
				.put(vaccination_date
					    	, null)
				.put(final_bcg
					    	, "2016-08-25")
				.put(final_opv0
					    	, "2016-07-28")
				.put(final_pcv1
					    	, "2016-07-15")
				.put(final_opv1
					    	, "")
				.put(final_penta1
					    	, "")
				.put(final_pcv2
					    	, "")
				.put(final_opv2
					    	, "")
				.put(final_penta2
					    	, "")
				.put(final_pcv3
					    	, "")
				.put(final_opv3
					    	, "")
				.put(final_penta3
					    	, "")
				.put(final_ipv
					    	, "")
				.put(final_measles1
					    	, "")
				.put(final_measles2
					    	, "")
				.put(Is_Reg_Today
					    	,"0")
				.put(child_vaccines
					    	, null)
				.put(child_vaccines1
					    	,  "bcg opv0 pcv1")
				.put(child_vaccines1_2
					    	, null)
				.put(child_vaccines_2
					    	, null)				
				.put(received_time, 
				dateTime.format(today).toString()).map();				
				member.child_vaccine().add(vaccine);
		
		
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
		ob3Object.put("display", "Immunization Incident Template: PCV 1 (Pneumococcus, purified polysaccharides antigen conjugated), 2016-07-15, 1.0, true");
		ob3Object.put("uuid", "5f8d190f-c51d-4f59-a3b0-247ca1e4e7d8");
		
		obs.put(ob3Object);
		obs.put(ob1Object);
		obs.put(ob2Object);
		encounter.put("obs", obs);
		System.out.println(encounter.toString());
		ChildVaccineFollowup childVaccine = ChildVaccineFollowup.getInstance();	
		FeedHandler feedHandler =  new FeedHandler(allMembers,formSubmissions);
		feedHandler.setFormDirectory("./../assets/form");
		FormSubmission formSubmission = feedHandler.getEvent(encounter, "6a1332be-5c19-4e26-b7cb-5851d27b68bd",member);
		System.err.println("member.TTVisit():"+member.child_vaccine().toString());
		
		
		/*Assert.assertEquals(childVaccine.isThisVaccineGiven(member, 1),true);		
		Assert.assertNotNull(formSubmission);
		formSubmissions.remove(formSubmission);*/
		
	}
	
	
	@Ignore@Test
	public void shouldCheckWhichHasNoVisit() throws JSONException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, JsonGenerationException, JsonMappingException, IOException{
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
	
		
		Assert.assertNotNull(formSubmission);
		formSubmissions.remove(formSubmission);
		
		
	}

}
