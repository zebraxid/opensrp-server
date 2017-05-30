package org.opensrp.register.encounter.sync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncConstant {
	

	public static final String TT = "TT";
	public static final String TTFORMNAME = "woman_tt_form";
	public static final String CHILDACCINATIONFORMNAME = "child_vaccine_followup";	
	
	public final static Map<String, String> vaccines=new HashMap<String, String>();
	static{
		vaccines.put("IIT","Immunization Incident Template:");  
		vaccines.put("TT","(Tetanus toxoid)");
		vaccines.put("OPV","(Poliomyelitis oral, trivalent, live attenuated)"); 
		vaccines.put("PENTA","(Diphtheria-hemophilus influenzae B-pertussis-poliomyelitis-tetanus-hepatitis B)"); 
		vaccines.put("PCV","(Pneumococcus, purified polysaccharides antigen conjugated)");
		vaccines.put("BCG","(Tuberculosis, live attenuated)"); 
		vaccines.put("IPV","(IPV Vaccine)");	    
	    
	}
	
	public final static Map<String, String> TTFinalMapping=new HashMap<String, String>();
	static{
		TTFinalMapping.put("1","tt1_final");  
		TTFinalMapping.put("2","tt2_final");
		TTFinalMapping.put("3","tt3_final"); 
		TTFinalMapping.put("4","tt4_final"); 
		TTFinalMapping.put("5","tt5_final");
	}
	public final static Map<String, String> TTRetroMapping=new HashMap<String, String>();
	static{
		TTRetroMapping.put("1","tt1_retro");  
		TTRetroMapping.put("2","tt2_retro");
		TTRetroMapping.put("3","tt3_retro"); 
		TTRetroMapping.put("4","tt4_retro"); 
		TTRetroMapping.put("5","tt5_retro");
	}
	
	public final static Map<String, String> TTDoseMapping=new HashMap<String, String>();
	static{
		TTDoseMapping.put("1","tt_1_dose");  
		TTDoseMapping.put("2","tt_2_dose");
		TTDoseMapping.put("3","tt_3_dose"); 
		TTDoseMapping.put("4","tt_4_dose"); 
		TTDoseMapping.put("5","tt_5_dose");
	}
	
	
	// setting for child vaccination 
	public final static Map<String, String> BCGFinalMapping=new HashMap<String, String>();
	static{
		BCGFinalMapping.put("0","final_bcg");
	}
	
	public final static Map<String, String> OPVFinalMapping=new HashMap<String, String>();
	static{
		OPVFinalMapping.put("0","final_opv0");
		OPVFinalMapping.put("1","final_opv1");
		OPVFinalMapping.put("2","final_opv2");
		OPVFinalMapping.put("3","final_opv3");
	}
	
	public final static Map<String, String> PENTAFinalMapping=new HashMap<String, String>();
	static{		
		PENTAFinalMapping.put("1","final_penta1");
		PENTAFinalMapping.put("2","final_penta2");
		PENTAFinalMapping.put("3","final_penta3");
	}
	
	public final static Map<String, String>  PCVFinalMapping=new HashMap<String, String>();
	static{		
		PCVFinalMapping.put("1","final_pcv1");
		PCVFinalMapping.put("2","final_pcv2");
		PCVFinalMapping.put("3","final_pcv3");
	}
	
	public final static Map<String, String>  IPVFinalMapping=new HashMap<String, String>();
	static{		
		PCVFinalMapping.put("0","final_ipv");
		
	}
	
	
	public final static Map<String, String> BCGRetroMapping=new HashMap<String, String>();
	static{
		BCGRetroMapping.put("0","bcg_retro");
	}
	
	public final static Map<String, String> OPVRetroMapping=new HashMap<String, String>();
	static{
		OPVRetroMapping.put("0","opv0_retro");
		OPVRetroMapping.put("1","opv1_retro");
		OPVRetroMapping.put("2","opv2_retro");
		OPVRetroMapping.put("3","opv3_retro");		
	}
	
	public final static Map<String, String> PENTARetroMapping=new HashMap<String, String>();
	static{
		PENTARetroMapping.put("1","penta1_retro");
		PENTARetroMapping.put("2","penta2_retro");
		PENTARetroMapping.put("3","penta3_retro");		
	}
	
	public final static Map<String, String> PCVRetroMapping=new HashMap<String, String>();
	static{
		PCVRetroMapping.put("1","pcv1_retro");
		PCVRetroMapping.put("2","pcv2_retro");
		PCVRetroMapping.put("3","pcv3_retro");		
	}
	public final static Map<String, String>IPVRetroMapping=new HashMap<String, String>();
	static{
		PCVRetroMapping.put("0","ipv_retro");
			
	}
	
	public final static Map<String, String> OPVDoseMapping=new HashMap<String, String>();
	static{
		OPVDoseMapping.put("0","opv0_dose");
		OPVDoseMapping.put("1","opv1_dose");
		OPVDoseMapping.put("2","opv2_dose");
		OPVDoseMapping.put("3","opv3_dose");		
	}
	
	public final static Map<String, String> PENTADoseMapping=new HashMap<String, String>();
	static{
		PENTADoseMapping.put("1","penta1_dose");
		PENTADoseMapping.put("2","penta2_dose");
		PENTADoseMapping.put("3","penta2_dose");		
	}
	
	public final static Map<String, String> PCVDoseMapping=new HashMap<String, String>();
	static{
		PCVDoseMapping.put("1","pcv1_dose");
		PCVDoseMapping.put("2","pcv2_dose");
		PCVDoseMapping.put("3","pcv3_dose");		
	}
	
	
	
	/*TT 5 (Tetanus toxoid) (12470)
	TT 1 (Tetanus toxoid) (12470)
	TT 4 (Tetanus toxoid) (12470)	
	TT 2 (Tetanus toxoid) (12470)
	TT 3 (Tetanus toxoid) (12470)
	
	
	OPV (Poliomyelitis oral, trivalent, live attenuated) (12466) 4+3+3+1
	
	Pentavalent 1 (Diphtheria-hemophilus influenzae B-pertussis-poliomyelitis-tetanus-hepatitis B) (12465)
	Pentavalent 2 (Diphtheria-hemophilus influenzae B-pertussis-poliomyelitis-tetanus-hepatitis B) (12465)
	Pentavalent 3 (Diphtheria-hemophilus influenzae B-pertussis-poliomyelitis-tetanus-hepatitis B) (12465)
	PCV 1 (Pneumococcus, purified polysaccharides antigen conjugated) (12468)
	PCV 2 (Pneumococcus, purified polysaccharides antigen conjugated) (12468)
	PCV 3 (Pneumococcus, purified polysaccharides antigen conjugated) (12468)
	BCG (Tuberculosis, live attenuated) (12467)
	
	IPV (IPV Vaccine) (13913)*/
	
	public final static List<String> getChildVaccinesName(){		
		 List<String> childVaccines = new ArrayList<>();
		 childVaccines.add("OPV");
		 childVaccines.add("Pentavalent");
		 childVaccines.add("PCV");
		 childVaccines.add("BCG");
		 childVaccines.add("IPV");
		return childVaccines;
		
	}	
	
	public final static List<String> getWomanVaccinesName(){		
		 List<String> womanVaccines = new ArrayList<>();
		 womanVaccines.add("TT");		 
		return womanVaccines;
		
	}	

}
