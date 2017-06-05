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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.opensrp.register.mcare.domain.Members;

public class Child {
	
	public Child(){
		
	}
	public Members getChildMember(){
		Members member = new Members();		
		member.setCaseId("05cbaa2b-d3a6-40f6-a604-328bf725ddbf");
		member.setPROVIDERID("sujan");
		member.setINSTANCEID("3135f608-2206-41e8-b110-bba810c8a4f3");
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
		member.setMember_Fname("pakhiaa");
		member.setMember_Unique_ID("");
		member.setChild_dob("2017-01-07");
		member.setChild_age_days("302") ;
		member.setChild_age("11");
		member.setChild_birth_date_note("");
		member.setChild_gender("1");
		member.setChild_mother_name("gghjj");
		member.setChild_guardian_id("");
		member.setChild_Mother_NID("");
		member.setChild_Mother_BRID("");
		member.setChild_Father_NID("");
		member.setChild_Father_BRID("");
		member.setChild_Other_Guardian_NID("");
		member.setChild_Other_Guardian_BRID("");
		member.setChild_was_suffering_from_a_disease_at_birth("no");
		member.setContact_phone_number("");		
		member.setMember_Reg_Date("2017-05-30");  
		member.setIs_Reg_Today("0"); 
		member.setIs_child("1");
		member.setrelationalid("d4423a9f-a9df-4ae7-9abc-e776f077ccc8") ;
		member.setMotherRelationalId("2a817076-8030-4fef-8740-c33500cd17c9");	
		return member;
		
	}
	
	public Map<String, String> getChildVaccine(){
		SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();	
		Map<String, String> vaccine = create(ID, "05cbaa2b-d3a6-40f6-a604-328bf725ddbf")
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
		return vaccine;
		
	}

}
