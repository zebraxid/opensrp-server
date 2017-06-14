/**
 * @author proshanto
 * 
 * */
package org.opensrp.register.encounter.sync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncConstant {
	public static final String TT = "TT";
	public static final String TTFORMNAME = "woman_tt_form";
	public static final String CHILDACCINATIONFORMNAME = "child_vaccine_followup";	
	public static final String ISWOMAN = "1";
	public static final String ISCHILD = "1";
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
	
	/**
	 * TT Final field list Mapping in HashMap which is defined 
	 * in woman TT followup form.
	 * 
	 */	
	public final static Map<String, String> TTFinalMapping=new HashMap<String, String>();
	static{
		TTFinalMapping.put("1","tt1_final");  
		TTFinalMapping.put("2","tt2_final");
		TTFinalMapping.put("3","tt3_final"); 
		TTFinalMapping.put("4","tt4_final"); 
		TTFinalMapping.put("5","tt5_final");
	}
	
	/**
	 * TT Retro field list Mapping in HashMap which is defined 
	 * in woman TT followup form.
	 * 
	 */	
	public final static Map<String, String> TTRetroMapping=new HashMap<String, String>();
	static{
		TTRetroMapping.put("1","tt1_retro");  
		TTRetroMapping.put("2","tt2_retro");
		TTRetroMapping.put("3","tt3_retro"); 
		TTRetroMapping.put("4","tt4_retro"); 
		TTRetroMapping.put("5","tt5_retro");
	}
	/**
	 * TT Dose field list Mapping in HashMap which is defined 
	 * in woman TT followup form.
	 * 
	 */	
	public final static Map<String, String> TTDoseMapping=new HashMap<String, String>();
	static{
		TTDoseMapping.put("1","tt_1_dose");  
		TTDoseMapping.put("2","tt_2_dose");
		TTDoseMapping.put("3","tt_3_dose"); 
		TTDoseMapping.put("4","tt_4_dose"); 
		TTDoseMapping.put("5","tt_5_dose");
	}
	
	
	// setting for child vaccination 
	/**
	 * BCG Final field list Mapping in HashMap which is defined in child 
	 * vaccine followup form.
	 * 
	 */
	public final static Map<String, String> BCGFinalMapping=new HashMap<String, String>();
	static{
		BCGFinalMapping.put("0","final_bcg");
	}
	
	/**
	 * OPV Final field list Mapping in HashMap which is defined in child 
	 * vaccine followup form.
	 * 
	 */
	public final static Map<String, String> OPVFinalMapping=new HashMap<String, String>();
	static{
		OPVFinalMapping.put("0","final_opv0");
		OPVFinalMapping.put("1","final_opv1");
		OPVFinalMapping.put("2","final_opv2");
		OPVFinalMapping.put("3","final_opv3");
	}
	
	/**
	 * Pentavalente Final field list Mapping in HashMap which is defined in child 
	 * vaccine followup form.
	 * 
	 */
	public final static Map<String, String> PENTAFinalMapping=new HashMap<String, String>();
	static{		
		PENTAFinalMapping.put("1","final_penta1");
		PENTAFinalMapping.put("2","final_penta2");
		PENTAFinalMapping.put("3","final_penta3");
	}
	
	/**
	 * PCV Final field list Mapping in HashMap which is defined in child 
	 * vaccine followup form.
	 * 
	 */
	public final static Map<String, String>  PCVFinalMapping=new HashMap<String, String>();
	static{		
		PCVFinalMapping.put("1","final_pcv1");
		PCVFinalMapping.put("2","final_pcv2");
		PCVFinalMapping.put("3","final_pcv3");
	}
	
	/**
	 * IPV Final field list Mapping in HashMap which is defined in child 
	 * vaccine followup form.
	 * 
	 */
	public final static Map<String, String>  IPVFinalMapping=new HashMap<String, String>();
	static{		
		IPVFinalMapping.put("0","final_ipv");
		
	}
	
	/**
	 * BCG Retro field list Mapping in HashMap which is defined in child 
	 * vaccine followup form.
	 * 
	 */
	public final static Map<String, String> BCGRetroMapping=new HashMap<String, String>();
	static{
		BCGRetroMapping.put("0","bcg_retro");
	}
	
	/**
	 * OPV Retro field list Mapping in HashMap which is defined in child 
	 * vaccine followup form.
	 * 
	 */
	public final static Map<String, String> OPVRetroMapping=new HashMap<String, String>();
	static{
		OPVRetroMapping.put("0","opv0_retro");
		OPVRetroMapping.put("1","opv1_retro");
		OPVRetroMapping.put("2","opv2_retro");
		OPVRetroMapping.put("3","opv3_retro");		
	}
	/**
	 * Pentavalente Retro field list Mapping in HashMap which is defined in child 
	 * vaccine followup form.
	 * 
	 */
	public final static Map<String, String> PENTARetroMapping=new HashMap<String, String>();
	static{
		PENTARetroMapping.put("1","penta1_retro");
		PENTARetroMapping.put("2","penta2_retro");
		PENTARetroMapping.put("3","penta3_retro");		
	}
	
	/**
	 * PCV Retro field list Mapping in HashMap which is defined in child 
	 * vaccine followup form.
	 * 
	 */
	public final static Map<String, String> PCVRetroMapping=new HashMap<String, String>();
	static{
		PCVRetroMapping.put("1","pcv1_retro");
		PCVRetroMapping.put("2","pcv2_retro");
		PCVRetroMapping.put("3","pcv3_retro");		
	}
	/**
	 * IPV Retro field list Mapping in HashMap which is defined in child 
	 * vaccine followup form.
	 * 
	 */
	public final static Map<String, String>IPVRetroMapping=new HashMap<String, String>();
	static{
		IPVRetroMapping.put("0","ipv_retro");
			
	}
	
	/**
	 * OPV Dose field list Mapping in HashMap which is defined in child 
	 * vaccine followup form.
	 * 
	 */
	public final static Map<String, String> OPVDoseMapping=new HashMap<String, String>();
	static{
		OPVDoseMapping.put("0","opv0_dose");
		OPVDoseMapping.put("1","opv1_dose");
		OPVDoseMapping.put("2","opv2_dose");
		OPVDoseMapping.put("3","opv3_dose");		
	}
	/**
	 * Pentavalente Dose field list Mapping in HashMap which is defined in child 
	 * vaccine followup form.
	 * 
	 */
	public final static Map<String, String> PENTADoseMapping=new HashMap<String, String>();
	static{
		PENTADoseMapping.put("1","penta1_dose");
		PENTADoseMapping.put("2","penta2_dose");
		PENTADoseMapping.put("3","penta2_dose");		
	}
	
	/**
	 * PCV Dose field list Mapping in HashMap which is defined in child 
	 * vaccine followup form.
	 * 
	 */
	public final static Map<String, String> PCVDoseMapping=new HashMap<String, String>();
	static{
		PCVDoseMapping.put("1","pcv1_dose");
		PCVDoseMapping.put("2","pcv2_dose");
		PCVDoseMapping.put("3","pcv3_dose");		
	}
	
	/**
	 * Child vaccine list in short name
	 * */
	public final static List<String> getChildVaccinesName(){		
		 List<String> childVaccines = new ArrayList<>();
		 childVaccines.add("BCG");
		 childVaccines.add("Pentavalent");
		 childVaccines.add("PCV");
		 childVaccines.add("OPV");
		 childVaccines.add("IPV");
		return childVaccines;
		
	}	
	
	public final static List<String> getWomanVaccinesName(){		
		 List<String> womanVaccines = new ArrayList<>();
		 womanVaccines.add("TT");		 
		return womanVaccines;
		
	}
	
	public final static Map<String, String> scheduleMapping=new HashMap<String, String>();
	static{
		scheduleMapping.put("BCG","child_bcg");
		scheduleMapping.put("IPV","child_ipv");
		scheduleMapping.put("3","child_measles1");	
		scheduleMapping.put("3","child_measles2");
		scheduleMapping.put("OPV 0","child_opv0");
		scheduleMapping.put("OPV 1","child_opv1");
		scheduleMapping.put("OPV 2","child_opv2");
		scheduleMapping.put("OPV 3","child_opv3");
		scheduleMapping.put("PCV 1","child_pcv1");
		scheduleMapping.put("PCV 2","child_pcv2");
		scheduleMapping.put("PCV 3","child_pcv3");
		scheduleMapping.put("Pentavalent 1","child_penta1");
		scheduleMapping.put("Pentavalent 2","child_penta2");
		scheduleMapping.put("Pentavalent 3","child_penta3");
		scheduleMapping.put("TT 1","Woman_TT1");
		scheduleMapping.put("TT 2","Woman_TT2");
		scheduleMapping.put("TT 3","Woman_TT3");
		scheduleMapping.put("TT 4","Woman_TT4");
		scheduleMapping.put("TT 5","Woman_TT5");
	}

}
