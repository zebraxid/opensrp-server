package org.opensrp.register.mcare;

import org.opensrp.dto.form.FormSubmissionDTO;

public class TestData {
	
	public static final String ANM_ID = "opensrp";
	public static final String FORM_NAME = "";
	public static final String INSTANCE_ID = "";
	public static final String CLIENT_VERSION = "";
	public static final String ENTITY_ID = "";
	public static final String FORM_DATA_DEFINITION_VERSION = "1";
	public static final String FORM_INSTANCE = "";
	
	/*public static final String HOUSE_HOLD_REGISTRATION_INPUT_JSON = "[{\"anmId\":\"opensrp\","
			+ "\"clientVersion\":\"1488196854235\",\"entityId\":\"5e545820-9d28-46ab-a910-39e2ea015f1b\","
			+ "\"formDataDefinitionVersion\":\"1\",\"formInstance\":
			\"{\"form\":{\"default_bind_path\":\"/model/instance/NewHH\",
			\"sub_forms\":[{\"default_bind_path\":\"/model/instance/NewHH/HH_Member\",
			\"instances\":[{\"Permanent_Address\":\"1\",\"ELCO_BRID_Concept\":\"12345678901234567\",
			\"Mem_F_Name\":\"uhjj\",\"Final_Dist\":\"Gaibandha\",\"Calc_Dob\":\"Invalid Date\",\"Place_Of_Birth\":
			\"Dinajpur\",\"Child\":\"0\",\"Mem_Upazilla\":\"Gaibandha Sadar\",\"Mem_District\":\"Gaibandha\",
			\"Final_Vill\":\"hjjk\",\"Wom_Hus_Alive\":\"\",\"ELCO_NID\":\"\",\"Wom_Hus_Live\":\"1\",
			\"Adolescent\":\"0\",\"Mem_Mauzapara\":\"Chapadaha-Garer Par\",\"Spouse_Name\":\"hjkk\",
			\"Birth_Date_Note\":\"\",\"add_member\":\"\",\"ELCO_ID_Type\":\"2\",\"Mem_Ward\":\"Ward-1\",\
			"Religion\":\"1\",\"Mem_Union\":\"Kuptala\",\"ELCO_Mobile_Number\":\"01761234567\",
			\"Wom_Menstruating\":\"1\",\"Final_Union\":\"Kuptala\",\"Couple_No\":\"0001\",
			\"ELCO_NID_Concept\":\"\",\"relationalid\":\"5e545820-9d28-46ab-a910-39e2ea015f1b\",
			\"Calc_Age_Confirm\":\"30\",\"Mem_Division\":\"Rangpur\",\"Note_age\":\"\",\"Mem_L_Name\":\".\",
			\"Member_Reg_Date\":\"2017-02-27\",\"Nutrition\":\"0\",\"Updated_Union\":\"${existing_Union}\",
			\"Eligible2\":\"1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\",\"ELCO_BRID\":\"12345678901234567\",
			\"Calc_Dob_Confirm\":\"1987-02-27\",\"id\":\"06b98e9c-96b2-4ef7-a706-4211f13cec28\",
			\"Updated_Dist\":\"${existing_District}\",\"Mem_BRID\":\"12345678901234567\",
			\"ELCO_Note\":\"\",\"Wom_Sterilized\":\"0\",\"Member_GoB_HHID\":\"0001\",
			\"Occupation\":\"3\",\"Mem_Village_Name\":\"hjjk\",\"Child_Mother\":\"\",
			\"Wom_Hus_Sterilized\":\"0\",\"Mem_NID\":\"\",\"Relation_HoH\":\"3\",\"Eligible\":\"1\",
			\"Mem_Subunit\":\"1-Ka\",\"Child_Father\":\"\",\"Mem_Country\":\"Bangladesh\",
			\"Member_Gender\":\"2\",\"Retype_Mem_BRID\":\"12345678901234567\",
			\"Mem_BRID_Concept\":\"12345678901234567\",\"Mem_Mobile_Number\":\"01761234567\",
			\"Calc_Age\":\"30\",\"Education\":\"10\",\"Member_Birth_Date_Known\":\"1\",
			\"BD_Citizen\":\"1\",\"Member_Birth_Date\":\"1987-02-27\",
			\"Mem_ID_Type\":\"2\",\"Mem_Marital_Status\":\"2\",\"Updated_Vill\":\"${Village_Name}\"}],
			\"fields\":[{\"source\":\"members.id\",\"name\":\"id\"},
			{\"source\":\"members.relationalid\",\"name\":\"relationalid\"},
			{\"source\":\"members.Member_GoB_HHID\",\"name\":\"Member_GoB_HHID\"},
			{\"source\":\"members.Member_Reg_Date\",\"name\":\"Member_Reg_Date\"},
			{\"source\":\"members.Mem_F_Name\",\"name\":\"Mem_F_Name\"},
			{\"source\":\"members.Mem_L_Name\",\"name\":\"Mem_L_Name\"},
			{\"source\":\"members.Member_Birth_Date_Known\",\"name\":\"Member_Birth_Date_Known\"},
			{\"source\":\"members.Member_Birth_Date\",\"name\":\"Member_Birth_Date\"},
			{\"source\":\"members.Member_Age\",\"name\":\"Member_Age\"},
			{\"source\":\"members.Calc_Age\",\"name\":\"Calc_Age\"},
			{\"source\":\"members.Calc_Dob\",\"name\":\"Calc_Dob\"},
			{\"source\":\"members.Calc_Dob_Confirm\",\"name\":\"Calc_Dob_Confirm\"},
			{\"source\":\"members.Calc_Age_Confirm\",\"name\":\"Calc_Age_Confirm\"},
			{\"source\":\"members.Birth_Date_Note\",\"name\":\"Birth_Date_Note\"},
			{\"source\":\"members.Note_age\",\"name\":\"Note_age\"},
			{\"source\":\"members.Member_Gender\",\"name\":\"Member_Gender\"},
			{\"source\":\"members.Mem_ID_Type\",\"name\":\"Mem_ID_Type\"},
			{\"source\":\"members.Mem_NID\",\"name\":\"Mem_NID\"},
			{\"source\":\"members.Retype_Mem_NID\",\"name\":\"Retype_Mem_NID\"},
			{\"source\":\"members.Mem_NID_Concept\",\"name\":\"Mem_NID_Concept\"},
			{\"source\":\"members.Mem_BRID\",\"name\":\"Mem_BRID\"},
			{\"source\":\"members.Retype_Mem_BRID\",\"name\":\"Retype_Mem_BRID\"},
			{\"source\":\"members.Mem_BRID_Concept\",\"name\":\"Mem_BRID_Concept\"},
			{\"source\":\"members.Mem_Mobile_Number\",\"name\":\"Mem_Mobile_Number\"},
			{\"source\":\"members.Mem_Marital_Status\",\"name\":\"Mem_Marital_Status\"},
			{\"source\":\"members.Couple_No\",\"name\":\"Couple_No\"},
			{\"source\":\"members.Spouse_Name\",\"name\":\"Spouse_Name\"},
			{\"source\":\"members.Child_Father\",\"name\":\"Child_Father\"},
			{\"source\":\"members.Child_Mother\",\"name\":\"Child_Mother\"},
			{\"source\":\"members.Wom_Menstruating\",\"name\":\"Wom_Menstruating\"},{\"source\":\"members.Wom_Sterilized\",\"name\":\"Wom_Sterilized\"},{\"source\":\"members.Wom_Hus_Live\",\"name\":\"Wom_Hus_Live\"},{\"source\":\"members.Wom_Hus_Alive\",\"name\":\"Wom_Hus_Alive\"},{\"source\":\"members.Wom_Hus_Sterilized\",\"name\":\"Wom_Hus_Sterilized\"},{\"source\":\"members.Eligible\",\"name\":\"Eligible\"},{\"source\":\"members.Eligible2\",\"name\":\"Eligible2\"},{\"source\":\"members.ELCO\",\"name\":\"ELCO\"},{\"source\":\"members.ELCO_Note\",\"name\":\"ELCO_Note\"},{\"source\":\"members.Mem_Country\",\"name\":\"Mem_Country\"},{\"source\":\"members.Mem_Division\",\"name\":\"Mem_Division\"},{\"source\":\"members.Mem_District\",\"name\":\"Mem_District\"},{\"source\":\"members.Mem_Upazilla\",\"name\":\"Mem_Upazilla\"},{\"source\":\"members.Mem_Union\",\"name\":\"Mem_Union\"},{\"source\":\"members.Mem_Ward\",\"name\":\"Mem_Ward\"},{\"source\":\"members.Mem_Subunit\",\"name\":\"Mem_Subunit\"},{\"source\":\"members.Mem_Mauzapara\",\"name\":\"Mem_Mauzapara\"},{\"source\":\"members.Mem_Village_Name\",\"name\":\"Mem_Village_Name\"},{\"source\":\"members.Mem_GPS\",\"name\":\"Mem_GPS\"},{\"source\":\"members.ELCO_ID_Type\",\"name\":\"ELCO_ID_Type\"},{\"source\":\"members.ELCO_NID\",\"name\":\"ELCO_NID\"},{\"source\":\"members.ELCO_NID_Concept\",\"name\":\"ELCO_NID_Concept\"},{\"source\":\"members.ELCO_BRID\",\"name\":\"ELCO_BRID\"},{\"source\":\"members.ELCO_BRID_Concept\",\"name\":\"ELCO_BRID_Concept\"},{\"source\":\"members.ELCO_Mobile_Number\",\"name\":\"ELCO_Mobile_Number\"},{\"source\":\"members.Member_Detail\",\"name\":\"Member_Detail\"},{\"source\":\"members.Permanent_Address\",\"name\":\"Permanent_Address\"},{\"source\":\"members.Updated_Dist\",\"name\":\"Updated_Dist\"},{\"source\":\"members.Updated_Union\",\"name\":\"Updated_Union\"},{\"source\":\"members.Updated_Vill\",\"name\":\"Updated_Vill\"},{\"source\":\"members.Final_Dist\",\"name\":\"Final_Dist\"},{\"source\":\"members.Final_Union\",\"name\":\"Final_Union\"},{\"source\":\"members.Final_Vill\",\"name\":\"Final_Vill\"},{\"source\":\"members.Relation_HoH\",\"name\":\"Relation_HoH\"},{\"source\":\"members.Place_Of_Birth\",\"name\":\"Place_Of_Birth\"},{\"source\":\"members.Education\",\"name\":\"Education\"},{\"source\":\"members.Religion\",\"name\":\"Religion\"},{\"source\":\"members.BD_Citizen\",\"name\":\"BD_Citizen\"},{\"source\":\"members.Occupation\",\"name\":\"Occupation\"},{\"source\":\"members.Child\",\"name\":\"Child\"},{\"source\":\"members.Adolescent\",\"name\":\"Adolescent\"},{\"source\":\"members.Nutrition\",\"name\":\"Nutrition\"},{\"source\":\"members.add_member\",\"name\":\"add_member\"}],\"bind_type\":\"members\",\"name\":\"member_registration\"}],\"fields\":[{\"value\":\"5e545820-9d28-46ab-a910-39e2ea015f1b\",\"source\":\"household.id\",\"name\":\"id\"},{\"source\":\"household.Version\",\"value\":\"\",\"bind\":\"/model/instance/NewHH/Version\",\"name\":\"Version\"},{\"source\":\"household.Changes\",\"value\":\"\",\"bind\":\"/model/instance/NewHH/Changes\",\"name\":\"Changes\"},{\"source\":\"household.existing_location\",\"value\":\"4d68ef7c-9f95-11e6-a293-000c299c7c5d\",\"bind\":\"/model/instance/NewHH/existing_location\",\"name\":\"existing_location\"},{\"source\":\"household.existing_Country\",\"value\":\"Bangladesh\",\"bind\":\"/model/instance/NewHH/existing_Country\",\"name\":\"existing_Country\"},{\"source\":\"household.existing_Division\",\"value\":\"Rangpur\",\"bind\":\"/model/instance/NewHH/existing_Division\",\"name\":\"existing_Division\"},{\"source\":\"household.existing_District\",\"value\":\"Gaibandha\",\"bind\":\"/model/instance/NewHH/existing_District\",\"name\":\"existing_District\"},{\"source\":\"household.existing_Upazilla\",\"value\":\"Gaibandha Sadar\",\"bind\":\"/model/instance/NewHH/existing_Upazilla\",\"name\":\"existing_Upazilla\"},{\"source\":\"household.existing_Union\",\"value\":\"Kuptala\",\"bind\":\"/model/instance/NewHH/existing_Union\",\"name\":\"existing_Union\"},{\"source\":\"household.existing_Ward\",\"value\":\"Ward-1\",\"bind\":\"/model/instance/NewHH/existing_Ward\",\"name\":\"existing_Ward\"},{\"source\":\"household.existing_Subunit\",\"value\":\"1-Ka\",\"bind\":\"/model/instance/NewHH/existing_Subunit\",\"name\":\"existing_Subunit\"},{\"source\":\"household.existing_Mauzapara\",\"value\":\"Chapadaha-Garer Par\",\"bind\":\"/model/instance/NewHH/existing_Mauzapara\",\"name\":\"existing_Mauzapara\"},{\"source\":\"household.Form_Name\",\"value\":\"NewHH\",\"bind\":\"/model/instance/NewHH/Form_Name\",\"name\":\"Form_Name\"},{\"source\":\"household.Today\",\"value\":\"2017-02-27\",\"bind\":\"/model/instance/NewHH/Today\",\"name\":\"Today\"},{\"source\":\"household.Start\",\"value\":\"2017-02-27 17:57:08\",\"bind\":\"/model/instance/NewHH/Start\",\"name\":\"Start\"},{\"source\":\"household.End\",\"value\":\"2017-02-27 18:00:53\",\"bind\":\"/model/instance/NewHH/End\",\"name\":\"End\"},{\"source\":\"household.Reg_Date\",\"value\":\"2017-02-27\",\"bind\":\"/model/instance/NewHH/Reg_Date\",\"name\":\"Reg_Date\"},{\"source\":\"household.Village_Name\",\"value\":\"hjjk\",\"bind\":\"/model/instance/NewHH/Village_Name\",\"name\":\"Village_Name\"},{\"source\":\"household.GoB_HHID\",\"value\":\"0001\",\"bind\":\"/model/instance/NewHH/GoB_HHID\",\"name\":\"GoB_HHID\"},{\"source\":\"household.No_Of_Couples\",\"value\":\"1\",\"bind\":\"/model/instance/NewHH/No_Of_Couples\",\"name\":\"No_Of_Couples\"},{\"source\":\"household.Country\",\"value\":\"Bangladesh\",\"bind\":\"/model/instance/NewHH/Country\",\"name\":\"Country\"},{\"source\":\"household.Division\",\"value\":\"Rangpur\",\"bind\":\"/model/instance/NewHH/Division\",\"name\":\"Division\"},{\"source\":\"household.District\",\"value\":\"Gaibandha\",\"bind\":\"/model/instance/NewHH/District\",\"name\":\"District\"},{\"source\":\"household.Upazilla\",\"value\":\"Gaibandha Sadar\",\"bind\":\"/model/instance/NewHH/Upazilla\",\"name\":\"Upazilla\"},{\"source\":\"household.Union\",\"value\":\"Kuptala\",\"bind\":\"/model/instance/NewHH/Union\",\"name\":\"Union\"},{\"source\":\"household.Ward\",\"value\":\"Ward-1\",\"bind\":\"/model/instance/NewHH/Ward\",\"name\":\"Ward\"},{\"source\":\"household.Subunit\",\"value\":\"1-Ka\",\"bind\":\"/model/instance/NewHH/Subunit\",\"name\":\"Subunit\"},{\"source\":\"household.Mauzapara\",\"value\":\"Chapadaha-Garer Par\",\"bind\":\"/model/instance/NewHH/Mauzapara\",\"name\":\"Mauzapara\"},{\"source\":\"household.GPS\",\"bind\":\"/model/instance/NewHH/GPS\",\"name\":\"GPS\"},{\"source\":\"household.HoH_F_Name\",\"value\":\"hjjk\",\"bind\":\"/model/instance/NewHH/HoH_F_Name\",\"name\":\"HoH_F_Name\"},{\"source\":\"household.HoH_L_Name\",\"value\":\".\",\"bind\":\"/model/instance/NewHH/HoH_L_Name\",\"name\":\"HoH_L_Name\"},{\"source\":\"household.HoH_Birth_Date\",\"value\":\"\",\"bind\":\"/model/instance/NewHH/HoH_Birth_Date\",\"name\":\"HoH_Birth_Date\"},{\"source\":\"household.HoH_Gender\",\"value\":\"1\",\"bind\":\"/model/instance/NewHH/HoH_Gender\",\"name\":\"HoH_Gender\"},{\"source\":\"household.HoH_Birth_Date_Known\",\"value\":\"0\",\"bind\":\"/model/instance/NewHH/HoH_Birth_Date_Known\",\"name\":\"HoH_Birth_Date_Known\"},{\"source\":\"household.HoH_Age\",\"value\":\"85\",\"bind\":\"/model/instance/NewHH/HoH_Age\",\"name\":\"HoH_Age\"},{\"source\":\"household.Calc_HoH_Age\",\"value\":\"NaN\",\"bind\":\"/model/instance/NewHH/Calc_HoH_Age\",\"name\":\"Calc_HoH_Age\"},{\"source\":\"household.Calc_HoH_Dob\",\"value\":\"1932-02-28\",\"bind\":\"/model/instance/NewHH/Calc_HoH_Dob\",\"name\":\"Calc_HoH_Dob\"},{\"source\":\"household.Calc_HoH_Dob_Confirm\",\"value\":\"1932-02-28\",\"bind\":\"/model/instance/NewHH/Calc_HoH_Dob_Confirm\",\"name\":\"Calc_HoH_Dob_Confirm\"},{\"source\":\"household.Calc_HoH_Age_Confirm\",\"value\":\"85\",\"bind\":\"/model/instance/NewHH/Calc_HoH_Age_Confirm\",\"name\":\"Calc_HoH_Age_Confirm\"},{\"source\":\"household.HoH_Birth_Date_Note\",\"value\":\"\",\"bind\":\"/model/instance/NewHH/HoH_Birth_Date_Note\",\"name\":\"HoH_Birth_Date_Note\"},{\"source\":\"household.HoH_Note_age\",\"value\":\"\",\"bind\":\"/model/instance/NewHH/HoH_Note_age\",\"name\":\"HoH_Note_age\"},{\"source\":\"household.HoH_ID_Type\",\"value\":\"1\",\"bind\":\"/model/instance/NewHH/HoH_ID_Type\",\"name\":\"HoH_ID_Type\"},{\"source\":\"household.HoH_NID\",\"value\":\"0987654321123\",\"bind\":\"/model/instance/NewHH/HoH_NID\",\"name\":\"HoH_NID\"},{\"source\":\"household.Retype_HoH_NID\",\"value\":\"0987654321123\",\"bind\":\"/model/instance/NewHH/Retype_HoH_NID\",\"name\":\"Retype_HoH_NID\"},{\"source\":\"household.HoH_NID_Concept\",\"value\":\"0987654321123\",\"bind\":\"/model/instance/NewHH/HoH_NID_Concept\",\"name\":\"HoH_NID_Concept\"},{\"source\":\"household.HoH_BRID\",\"value\":\"\",\"bind\":\"/model/instance/NewHH/HoH_BRID\",\"name\":\"HoH_BRID\"},{\"source\":\"household.Retype_HoH_BRID\",\"bind\":\"/model/instance/NewHH/Retype_HoH_BRID\",\"name\":\"Retype_HoH_BRID\"},{\"source\":\"household.HoH_BRID_Concept\",\"bind\":\"/model/instance/NewHH/HoH_BRID_Concept\",\"name\":\"HoH_BRID_Concept\"},{\"source\":\"household.HoH_Mobile_number\",\"value\":\"01781234576\",\"bind\":\"/model/instance/NewHH/HoH_Mobile_number\",\"name\":\"HoH_Mobile_number\"},{\"source\":\"household.Member_Number\",\"value\":\"30\",\"bind\":\"/model/instance/NewHH/Member_Number\",\"name\":\"Member_Number\"},{\"source\":\"household.MWRA\",\"value\":\"1\",\"bind\":\"/model/instance/NewHH/MWRA\",\"name\":\"MWRA\"},{\"source\":\"household.Final_ELCO\",\"value\":\"1\",\"bind\":\"/model/instance/NewHH/Final_ELCO\",\"name\":\"Final_ELCO\"},{\"source\":\"household.HH_Status\",\"value\":\"ACTIVE\",\"bind\":\"/model/instance/NewHH/HH_Status\",\"name\":\"HH_Status\"}],\"bind_type\":\"household\"},\"form_data_definition_version\":\"1\"}\",\"formName\":\"new_household_registration\",\"instanceId\":\"c7945a5d-2e3a-4521-82ed-e2556de395cd\"}]"
*/
	//public static final FormSubmissionDTO formSubmissionDTO = new FormSubmissionDTO()
}
