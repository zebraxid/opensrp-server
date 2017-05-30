package org.opensrp.register.encounter.sync.forms;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.json.JSONObject;
import org.opensrp.form.domain.FormData;
import org.opensrp.form.domain.FormField;
import org.opensrp.form.domain.FormInstance;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.encounter.sync.SyncConstant;
import org.opensrp.register.encounter.sync.interfaces.FormsType;
import org.opensrp.register.mcare.domain.Members;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service
public class WomanTTFollowUp implements FormsType<Members> {
	private static Logger logger = LoggerFactory.getLogger(WomanTTFollowUp.class.toString());
	
	private WomanTTFollowUp(){
		
	}
	
	@Override
	public FormSubmission makeForm(String formDir,String vaccineDate,int vaccineDose,String patientIdEntityId,Members member,String vaccineName) {
		FormSubmission  form = null ;
		if(member!=null){
		    if(member.TTVisit().isEmpty()){ 
		    	form =  craeteFormsubmission(formDir,vaccineDate,vaccineDose,patientIdEntityId,member);	    	
		    }else if(!isThisVaccineGiven(member,vaccineDose, vaccineName)){ 
		    	form =  craeteFormsubmission(formDir,vaccineDate,vaccineDose,patientIdEntityId,member);	    	
		    }else{    	
		    	
		    }
		}else{
			
		}
	    return form;		
	}
	
	
	private FormSubmission craeteFormsubmission(String formDir,String vaccineDate,int vaccineDose,String patientIdEntityId,Members member){
		JsonNode enc = null;
		ObjectMapper mapper = new ObjectMapper();
		String filePath = formDir+"/"+SyncConstant.TTFORMNAME+"/form_definition.json";		
	    try {
	    	 enc = mapper.readValue(new File(filePath), JsonNode.class);
	    	 FormInstance formInstance =new FormInstance();	     
	 	    List<FormField> formFields = new ArrayList<FormField>();
	 	    JsonNode bindType=  enc.get("form").get("bind_type");
	 	    String convertMemberToString ;
	 	    JSONObject convertMemberToJsonObject = null;
	 	    Members getMemberNewObject = new Members();
	 	    if(member.TTVisit().isEmpty()){
	 		    try{
	 		    	convertMemberToString = mapper.writeValueAsString(member);		
	 		    	convertMemberToJsonObject = new JSONObject(convertMemberToString);	
	 		    }catch(Exception e){
	 		    	logger.info(""+e.getMessage());
	 		    }
	 	    }else{
	 	    	
	 	    }
	 	    ArrayNode fields = (ArrayNode) enc.get("form").get("fields");
	 	    for (JsonNode node : fields) {
	 	    	FormField form=new FormField();
	 	    	String name = node.get("name").toString();
	 	    	name = name.replace("\"", "");
	 	    	StringBuilder bType = new StringBuilder(bindType.toString());
	 	    	bType.append(".".toString());
	 	    	bType.append(name);	    	
	 	    	form.setName(name);	    	
	 	    	form.setSource(bType.toString().replace("\"", ""));
	 	    	
	 	    	if(!member.TTVisit().isEmpty()){
	 		    	try{
	 		    		if(name.equalsIgnoreCase(SyncConstant.TTFinalMapping.get(Integer.toString(vaccineDose)))){
	 		    			form.setValue(vaccineDate.trim());
	 		    		}else if(name.equalsIgnoreCase(SyncConstant.TTRetroMapping.get(Integer.toString(vaccineDose)))){
	 		    			form.setValue(vaccineDate.trim());
	 		    		}else if(name.equalsIgnoreCase(SyncConstant.TTDoseMapping.get(Integer.toString(vaccineDose)))){
	 		    			form.setValue(Integer.toString(vaccineDose).trim());
	 		    		}else{
	 		    			form.setValue(member.TTVisit().get(name).trim());
	 		    		}
	 		    	}catch(Exception e){
	 		    		
	 		    	}
	 	    	}else{	    		
	 	    		try{	    			
	 	    			Field field = getMemberNewObject.getClass().getDeclaredField(name);
	 	    			field.setAccessible(true); 	    			
	 		    		if(name.equalsIgnoreCase(SyncConstant.TTFinalMapping.get(Integer.toString(vaccineDose)))){
	 		    			form.setValue(vaccineDate.trim());
	 		    		}else if(name.equalsIgnoreCase(SyncConstant.TTRetroMapping.get(Integer.toString(vaccineDose)))){
	 		    			form.setValue(vaccineDate.trim());
	 		    		}else if(name.equalsIgnoreCase(SyncConstant.TTDoseMapping.get(Integer.toString(vaccineDose)))){
	 		    			form.setValue(Integer.toString(vaccineDose).trim());
	 		    		}else{		    			
	 		    			form.setValue(convertMemberToJsonObject.get(field.getName()).toString().trim());
	 		    		}
	 		    	}catch(Exception e){
	 		    		form.setValue("");		    		
	 		    	}
	 	    	}	    	
	 	    	if(name.equalsIgnoreCase("id")){				
	 				form.setValue(patientIdEntityId.trim());
	 			}
	 	    	formFields.add(form);
	 			
	 		}
	 	    
	 	    
	 	    FormData formData = new FormData();
	 	    formData.setBind_type("members");
	 	    formData.setDefault_bind_path("/model/instance/Woman_TT_Followup_Form");
	 	    formData.setFields(formFields);	     
	 	    formInstance.setForm_data_definition_version("1");	     
	 	    formInstance.setForm(formData);	       
	 	    FormSubmission formSubmission = new FormSubmission(member.PROVIDERID(),UUID.randomUUID().toString().trim(), SyncConstant.TTFORMNAME, patientIdEntityId, "1", System.currentTimeMillis(), formInstance);
	 	    formSubmission.setServerVersion(System.currentTimeMillis());	  
	 		return formSubmission;
	     }catch (IOException e) {
	         e.printStackTrace();
	     }	    
	    return null;
	}	
	
	public static WomanTTFollowUp getInstance(){
		return new WomanTTFollowUp();
	}
	
	@Override
	public boolean isThisVaccineGiven(Members member,int dose,String vaccineName) {		
		if(!member.TTVisit().isEmpty()){		
			Map<String, String> TTVisit = member.TTVisit();		
			String TTFinalDate = TTVisit.get(SyncConstant.TTFinalMapping.get(Integer.toString(dose)));
			if(TTFinalDate.equalsIgnoreCase("") || TTFinalDate.equalsIgnoreCase("null") || TTFinalDate.equalsIgnoreCase(null)){
				return false;
			}else{
				return true;
		}
		}else{
			return false;
		}
	}
	
	
}
