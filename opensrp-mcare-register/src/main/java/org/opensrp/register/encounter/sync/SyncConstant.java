package org.opensrp.register.encounter.sync;

import java.util.HashMap;
import java.util.Map;

public class SyncConstant {
	

	public static final String TT = "TT";
	
	static Map<String, String> map=new HashMap<String, String>();
	static{
		map.put("IIT","Immunization Incident Template:");  
	    map.put("TT","(Tetanus toxoid)");
	    map.put("OPV","(Poliomyelitis oral, trivalent, live attenuated)"); 
	    map.put("PENTA","(Diphtheria-hemophilus influenzae B-pertussis-poliomyelitis-tetanus-hepatitis B)"); 
	    map.put("PCV","(Pneumococcus, purified polysaccharides antigen conjugated)");
	    map.put("BCG","(Tuberculosis, live attenuated)"); 
	    map.put("IPV","(IPV Vaccine)");
	    
	    
	}
	
	

}
