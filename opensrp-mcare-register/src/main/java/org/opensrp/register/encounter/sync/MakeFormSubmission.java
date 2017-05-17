package org.opensrp.register.encounter.sync;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.opensrp.domain.Event;
import org.opensrp.domain.Obs;
import org.opensrp.form.domain.FormData;
import org.opensrp.form.domain.FormField;
import org.opensrp.form.domain.FormInstance;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.encounter.sync.forms.WomanTTForm;
import org.opensrp.register.encounter.sync.interfaces.FormsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component
public class MakeFormSubmission {
	
	private String jsonFilePath;
	
	public MakeFormSubmission(){
		
	}
	
	/*@Autowired
	public MakeFormSubmission(@Value("#{opensrp['form.directory.name']}") String formDirPath) throws IOException
	{
		ResourceLoader loader=new DefaultResourceLoader();
		formDirPath = loader.getResource(formDirPath).getURI().getPath();
		this.jsonFilePath = formDirPath;
		
	}*/
	
	@SuppressWarnings("rawtypes")
	static Map map=new HashMap();
	static{
		map.put("id","d7e1d300-ce40-47f7-80b3-1dd1873e3683");
		map.put("existing_Is_Reg_Today","0");
		map.put("ultrasound_date","");
		map.put("lmp_calc_ultrasound_formatted","Invalid Date");
		map.put("epi_card_number","14725836");
		map.put("edd_calc_ultrasound","Invalid Date");
		map.put("Marital_status","2");
		map.put("tt_2_dose_today",null);
		map.put("Member_DISTRICT","Gazipur");
		map.put("lmp","");
		map.put("tt4_note","");
		map.put("Is_Reg_Today","0");
		map.put("husband_name_note","");
		map.put("contact_phone_number","");
		map.put("final_ga","");
		map.put("tt3_note","");
		map.put("existing_location",null);
		map.put("tt4_retro","2017-05-09");
		map.put("vaccines1_2",null);
		map.put("provider_id","");
		map.put("tt3_final","2016-05-09");
		map.put("Member_WARD",null);
		map.put("husband_name",null);
		map.put("tt1_retro",null);
		map.put("tt1_retro","");
		map.put("Member_HIE_facilities","Kaliganj TW (10019869)");
		map.put("vaccines1", null);
		map.put("tt2_note", null);
		map.put("tt2_note", "2012-05-09");
		map.put("tt2_retro", null);
		map.put("tt_4_dose", "4");
		map.put("edd_lmp", "");
		map.put("edd_lmp", "");
		map.put("tt_3_dose_today", null);
		map.put("edd_calc_lmp_formatted",  "Invalid Date");
		map.put("Member_Paurasava",  "Kaliganj Paurashava");
		map.put("display_pregnant",  "no");
		map.put("Member_GPS",  "");
		map.put("tt_4_dose_today",  null);
		map.put("e_tt2",  "2012-05-09");
		map.put("ultrasound_weeks",  "");
		map.put("e_tt3",  "");
		map.put("e_tt4",  "");
		map.put("e_tt5",  "");
		map.put("tt3_retro",  "2016-05-09");
		map.put("e_tt1",  "2010-05-09");
		map.put("ga_lmp",  "NaN");
		map.put("final_edd",  null);
		map.put("tt1_final",  "2010-05-09");
		map.put("tt_5_dose_today",  null);
		map.put("edd_calc_ultrasound_formatted",  "Invalid Date");
		map.put("birth_date_note",  "");
		map.put("Member_UNION",  "Urban Ward No-01");
		map.put("ga_edd",  "NaN");
		map.put("Member_UPAZILLA",  "Kaliganj");
		map.put("center_gps",  null);
		map.put("existing_contact_phone_number",  null);
		map.put("tt_1_dose",  null);
		map.put("Member_COUNTRY",  "null");
		map.put("Member_Address_line",  "gghj");
		map.put("tt4_final",  "2017-05-09");
		map.put("Member_DIVISION",  "Dhaka");
		map.put("provider_location_note",  "");
		map.put("calc_dob_confirm",  "1973-05-08");
		map.put("existing_Is_Reg_Today",  "0");
		map.put("landmark",  "");
		map.put("pregnant",  "no");
		map.put("ga_ult",  "NaN");
		map.put("tt_2_dose",  null);
		map.put("first_name_note",  "");
		map.put("tt_3_dose",  "3");
		map.put("Member_Fname",  "testw");
		map.put("edd",  "");
		map.put("final_edd_note",  "");
		map.put("tt1_note",  "");
		map.put("father_name_note",  "");
		map.put("tt1",  null);
		map.put("tt3",  null);
		map.put("tt2",  null);
		map.put("tt5",  null);
		map.put("tt4",  null);
		map.put("tt_1_dose_today",  null);
		map.put("calc_age_confirm",  44);
		map.put("vaccines_2",  null);
		map.put("address_change",  "no");
		map.put("edd_calc_lmp",  "Invalid Date");
		map.put("vaccines",  "TT4");
		map.put("final_lmp_note",  "");
		map.put("Member_BLOCK",  "3-KHA");
		map.put("Father_name",  "test");
		map.put("tt5_final",  "");
		map.put("Husband_name",  "etuj");
		map.put("marriage",  "");
		map.put("today",  "2017-05-09");
		map.put("end",  "2017-05-09");
		map.put("epi_card_number_note",  "");
		map.put("lmp_calc_edd_formatted",  "Invalid Date");
		map.put("provider_location_id",  "");
		map.put("address_note",  "");
		map.put("lmp_calc_ultrasound",  "Invalid Date");
		map.put("address1",  "");
		map.put("start",  "2017-05-09 16:52:25");
		map.put("final_lmp",  null);
		map.put("provider_location_name",  "");
		map.put("lmp_calc_edd",  "Invalid Date");
		
	}
	private static Logger logger = LoggerFactory.getLogger(MakeFormSubmission.class.toString());
	private AllFormSubmissions formSubmissions;
	@Autowired
	public MakeFormSubmission(AllFormSubmissions formSubmissions){
		this.formSubmissions = formSubmissions;
	}
	public Event getEvent(Event event){		
		List<Obs> obs = event.getObs();	
		try{
		for (Obs obs1 : obs) {			
			String[] array = ((String) obs1.getValues().get(0)).split(":"); 
			String name = array[0];
			if(!name.equalsIgnoreCase("Immunization Incident Vaccine")){
				if(name.equalsIgnoreCase("Tetanus toxoid")){
					FormsType<WomanTTForm> womanTTForm	= FormFatcory.getFormsTypeInstance("WTT");
					String filePath = this.jsonFilePath+"/woman_tt_form/form_definition.json";
					womanTTForm.makeForm(filePath);
					
				}else{
					
				}
			}
			logger.info("Event:"+array[0]);	
			
		}
		}catch(Exception e){
			
		}
		return event;
		
	}
	
	public FormSubmission createFormSubmission(){
		JsonNode enc = null;
		ObjectMapper mapper = new ObjectMapper();
	     try {
	            enc = mapper.readValue(new File("./../assets/form/woman_tt_form/form_definition.json"), JsonNode.class);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	     String anmId ="sujan";
	     String instanceId = "d7e1d300-ce40-47f7-80b3-1dd1873e3683";
	     String formName ="woman_tt_form";
	     String entityId="d7e1d300-ce40-47f7-80b3-1dd1873e3683";
	     long clientVersion = System.currentTimeMillis();
	     String formDataDefinitionVersion ="1";
	     FormInstance formInstance =new FormInstance();
	     
	    List<FormField> formFields = new ArrayList<FormField>();
	    JsonNode bindType=  enc.get("form").get("bind_type");
	    
	    ArrayNode fields = (ArrayNode) enc.get("form").get("fields");
	    for (JsonNode jsonNode : fields) {
	    	FormField form=new FormField();
	    	String name = jsonNode.get("name").toString();
	    	name = name.replace("\"", "");
	    	StringBuilder bType = new StringBuilder(bindType.toString());
	    	bType.append(".".toString());
	    	bType.append(name);	    	
	    	form.setName(name);	    	
	    	form.setSource(bType.toString().replace("\"", ""));
	    	try{
	    	form.setValue(map.get(name.toString()).toString());
	    	}catch(Exception e){
	    		System.out.println(e.getMessage());
	    	}
	    	formFields.add(form);
			
		}
	    
	     FormData formData = new FormData();
	     formData.setBind_type("members");
	     formData.setDefault_bind_path("/model/instance/Woman_TT_Followup_Form");
	     formData.setFields(formFields);
	     
	     formInstance.setForm_data_definition_version("1");
	     
	     formInstance.setForm(formData);
	     long serverVersion = System.currentTimeMillis();
	     
	     FormSubmission formSubmission = new FormSubmission(anmId, instanceId, formName, entityId, formDataDefinitionVersion, clientVersion, formInstance);
	     formSubmission.setServerVersion(serverVersion);
	    try{
	     formSubmissions.add(formSubmission);
		 System.out.println(formSubmission.toString());
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return formSubmission;
	}
	
	
	

}
