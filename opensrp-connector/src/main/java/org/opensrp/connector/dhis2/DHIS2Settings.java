/**
 * 
 */
package org.opensrp.connector.dhis2;

import java.util.HashMap;
import java.util.Map;

/**
 * @author proshanto
 */
public class DHIS2Settings {
	
	static Map<String, String> CLIENTIDMAPPING = new HashMap<String, String>();
	static {
		CLIENTIDMAPPING.put("firstName", "pzuh7zrs9Xx");
		CLIENTIDMAPPING.put("lastName", "VDWBOoLHJ8S");
		CLIENTIDMAPPING.put("gender", "xDvyz0ezL4e");
		CLIENTIDMAPPING.put("Child_Birth_Certificate", "ZDWzVhjlgWK");
		CLIENTIDMAPPING.put("birthdate", "vzleM5DCHs0");
		CLIENTIDMAPPING.put("Mother_guardian_First_Name", "ra9rJm4IoD0");
		CLIENTIDMAPPING.put("Mother_Guardian_Last_Name", "cnbMOmsHTUe");
		CLIENTIDMAPPING.put("Mother_Guardian_DOB", "UhNq433oy7S");
	}
	
	static Map<String, String> MOTHERIDMAPPING = new HashMap<String, String>();
	static {
		MOTHERIDMAPPING.put("firstName", "pzuh7zrs9Xx");
		MOTHERIDMAPPING.put("lastName", "VDWBOoLHJ8S");
		MOTHERIDMAPPING.put("registration_Date", "bWoFS5a23fo");
		MOTHERIDMAPPING.put("birthdate", "vzleM5DCHs0");
		MOTHERIDMAPPING.put("phone_Number", "wCom53wUTKf");
		
		MOTHERIDMAPPING.put("NID_BRID", "gXDTMMEoPTO");
		MOTHERIDMAPPING.put("houband_Name", "MGeiR6Vv1ma");
		MOTHERIDMAPPING.put("Mother_Guardian_DOB", "UhNq433oy7S");
		MOTHERIDMAPPING.put("Member_Registration_No", "TqA0pnQLHKt");
		MOTHERIDMAPPING.put("EPI_Card_Number", "AcfzB53w2JK");
		MOTHERIDMAPPING.put("Maritial_Status", "NkOtPre4iTm");
		MOTHERIDMAPPING.put("Couple_No", "DftawD2qqQJ");
		MOTHERIDMAPPING.put("Pregnant", "HaFQAe7H67k");
		MOTHERIDMAPPING.put("FP_User", "ip2EZBFuVLk");
		MOTHERIDMAPPING.put("EDD", "vA1g2eVeu1V");
		MOTHERIDMAPPING.put("FP_Methods", "eg9vNyPsUlI");
		MOTHERIDMAPPING.put("EDD_LMP", "OULPvo7wDIB");
	}
	
	static Map<String, String> HOUSEHOLDIDMAPPING = new HashMap<String, String>();
	static {
		HOUSEHOLDIDMAPPING.put("firstName", "pzuh7zrs9Xx");
		HOUSEHOLDIDMAPPING.put("lastName", "VDWBOoLHJ8S");
		HOUSEHOLDIDMAPPING.put("gender", "xDvyz0ezL4e");
		HOUSEHOLDIDMAPPING.put("Household_ID", "FUr13UGc7aC");
		HOUSEHOLDIDMAPPING.put("birthdate", "vzleM5DCHs0");
		HOUSEHOLDIDMAPPING.put("registration_Date", "bWoFS5a23fo");
	}
	
	static Map<String, String> VACCINATIONMAPPING = new HashMap<String, String>();
	static {
		VACCINATIONMAPPING.put("firstName", "pzuh7zrs9Xx");
		VACCINATIONMAPPING.put("lastName", "VDWBOoLHJ8S");
		VACCINATIONMAPPING.put("Mother_guardian_First_Name", "ra9rJm4IoD0");
		VACCINATIONMAPPING.put("Mother_Guardian_DOB", "UhNq433oy7S");
		VACCINATIONMAPPING.put("NID_BRID", "gXDTMMEoPTO");
		VACCINATIONMAPPING.put("Child_Birth_Certificate", "ZDWzVhjlgWK");
		VACCINATIONMAPPING.put("Vaccina_date", "FrU0Gy7JgDj");
		VACCINATIONMAPPING.put("Vaccina_dose", "angDB9as1Mm");
		VACCINATIONMAPPING.put("Vaccina_name", "xpcgeevTaHG");
	}
	
	static Map<String, Integer> VACCINATION = new HashMap<String, Integer>();
	static {
		VACCINATION.put("opv_0", 0);
		VACCINATION.put("opv_1", 1);
		VACCINATION.put("opv_2", 2);
		VACCINATION.put("opv_3", 3);
		VACCINATION.put("opv_4", 4);
		
		VACCINATION.put("bcg", 1);
		
		VACCINATION.put("penta_1", 1);
		VACCINATION.put("penta_2", 2);
		VACCINATION.put("penta_3", 3);
		
		VACCINATION.put("pcv_1", 1);
		VACCINATION.put("pcv_2", 2);
		VACCINATION.put("pcv_3", 3);
		
		VACCINATION.put("measles_1", 1);
		VACCINATION.put("measles_2", 2);
		
		VACCINATION.put("tt_1", 1);
		VACCINATION.put("tt_2", 2);
		VACCINATION.put("tt_3", 3);
		VACCINATION.put("tt_4", 4);
		VACCINATION.put("tt_5", 5);
		
	}
}
