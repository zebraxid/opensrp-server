package org.opensrp.register.encounter.sync;

import java.util.HashMap;
import java.util.Map;

public class SyncConstant {
	

	public static final String TT = "TT";
	
	public static Map<String, String> vaccines=new HashMap<String, String>();
	static{
		vaccines.put("IIT","Immunization Incident Template:");  
		vaccines.put("TT","(Tetanus toxoid)");
		vaccines.put("OPV","(Poliomyelitis oral, trivalent, live attenuated)"); 
		vaccines.put("PENTA","(Diphtheria-hemophilus influenzae B-pertussis-poliomyelitis-tetanus-hepatitis B)"); 
		vaccines.put("PCV","(Pneumococcus, purified polysaccharides antigen conjugated)");
		vaccines.put("BCG","(Tuberculosis, live attenuated)"); 
		vaccines.put("IPV","(IPV Vaccine)");	    
	    
	}
	
	public static Map<String, String> TTFinalMapping=new HashMap<String, String>();
	static{
		TTFinalMapping.put("1","tt1_final");  
		TTFinalMapping.put("2","tt2_final");
		TTFinalMapping.put("3","tt3_final"); 
		TTFinalMapping.put("4","tt4_final"); 
		TTFinalMapping.put("5","tt5_final");
	}
	public static Map<String, String> TTRetroMapping=new HashMap<String, String>();
	static{
		TTRetroMapping.put("1","tt1_retro");  
		TTRetroMapping.put("2","tt2_retro");
		TTRetroMapping.put("3","tt3_retro"); 
		TTRetroMapping.put("4","tt4_retro"); 
		TTRetroMapping.put("5","tt5_retro");
	}
	
	public static Map<String, String> TTDoseMapping=new HashMap<String, String>();
	static{
		TTDoseMapping.put("1","tt_1_dose");  
		TTDoseMapping.put("2","tt_2_dose");
		TTDoseMapping.put("3","tt_3_dose"); 
		TTDoseMapping.put("4","tt_4_dose"); 
		TTDoseMapping.put("5","tt_5_dose");
	}
	

}
