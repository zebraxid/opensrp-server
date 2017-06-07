/***
 * @author proshanto
 * 
 * */
package org.opensrp.register.encounter.sync.forms;

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
import org.opensrp.register.encounter.sync.FileReader;
import org.opensrp.register.encounter.sync.SyncConstant;
import org.opensrp.register.encounter.sync.interfaces.FormsType;
import org.opensrp.register.mcare.domain.Members;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service
public class WomanTTFollowUp extends FileReader implements FormsType<Members> {
	private static Logger logger = LoggerFactory.getLogger(WomanTTFollowUp.class.toString());
	
	private WomanTTFollowUp(){		
	}
	
	/**
	 * @param   formDir  current directory location of the form.
	 * @param 	vaccineDate date of vaccine.
	 * @param 	vaccineDose dose number of a vaccine.
	 * @param 	memberEntityId unique id of a member.
	 * @param 	vaccineName name of a vaccine.
	 * @param 	member A member information.	 
	 * @return 	FormSubmission 
	 */
	@Override
	public FormSubmission getFormSubmission(String formDir,String vaccineDate,int vaccineDose,String memberEntityId,Members member,String vaccineName) throws IOException {
		FormSubmission  form = null ;
		if(member!=null){
		    if(member.TTVisit().isEmpty()){ // when no vaccine is given before .
		    	form =  craeteFormsubmission(formDir,vaccineDate,vaccineDose,memberEntityId,member);	    	
		    }else if(!checkingVaccineGivenOrNot(member,vaccineDose, vaccineName)){  // At least one vaccine is given before. 
		    	form =  craeteFormsubmission(formDir,vaccineDate,vaccineDose,memberEntityId,member);	    	
		    }
		}
	    return form;		
	}	
	
	/**
	 * <p>make a <code>FormSubmission</code> according to condition.</p>
	 * <h5>No vaccine is given before then reqiured member information gets from <code>Members</code> object.</h5>
	 * <h5>At least one vaccine is given before then reqiured member information gets from <code>Members.TTVisit()</code> object.</h5> 
	 * 
	 * @param  formDir  current directory location of the form.
	 * @param vaccineDate date of vaccine.
	 * @param vaccineDose dose number of a vaccine.
	 * @param memberEntityId unique id of a member.
	 * @param vaccineName name of a vaccine.
	 * @param member A member information.
	 * @throws IOException 
	 * 			if stream to a file cannot be written.
	 * @return FormSubmission 
	 * */
	private FormSubmission craeteFormsubmission(String formDir,String vaccineDate,int vaccineDose,String memberEntityId,Members member) throws IOException{
		JsonNode file = WomanTTFollowUp.getFile(formDir, SyncConstant.TTFORMNAME);
		ObjectMapper mapper = new ObjectMapper();				
	    try {	    	 
	    	FormInstance formInstance =new FormInstance();	     
	 	    List<FormField> formFields = new ArrayList<FormField>();
	 	    JsonNode bindType=  file.get("form").get("bind_type");
	 	    String convertMemberToString ;
	 	    JSONObject convertMemberToJsonObject = null;
	 	    Members getMemberNewObject = new Members();
	 	    if(member.TTVisit().isEmpty()){	 		    
	 		    convertMemberToString = mapper.writeValueAsString(member);		
	 		    convertMemberToJsonObject = new JSONObject(convertMemberToString);	 		    
	 	    }
	 	    ArrayNode fields = (ArrayNode) file.get("form").get("fields");
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
	 				form.setValue(memberEntityId.trim());
	 			}
	 	    	formFields.add(form);	 			
	 		}	 	    
	 	    FormData formData = new FormData();
	 	    formData.setBind_type("members");
	 	    formData.setDefault_bind_path("/model/instance/Woman_TT_Followup_Form");
	 	    formData.setFields(formFields);	     
	 	    formInstance.setForm_data_definition_version("1");	     
	 	    formInstance.setForm(formData);	       
	 	    FormSubmission formSubmission = new FormSubmission(member.PROVIDERID(),UUID.randomUUID().toString().trim(), SyncConstant.TTFORMNAME, memberEntityId, "1", System.currentTimeMillis(), formInstance);
	 	    formSubmission.setServerVersion(System.currentTimeMillis());	  
	 		return formSubmission;
	     }catch (Exception e) {
	         logger.info("Message:"+e.getMessage());
	     }	    
	    return null;
	}	
	
	public static WomanTTFollowUp getInstance(){
		return new WomanTTFollowUp();
	}
	
	/**
	 * This method is used to check a vaccine given or not.	
	 * 
	 * @param vaccineDose dose number of a vaccine.	 
	 * @param vaccineName A vaccine name.
	 * @param member A member information.
	 * 
	 * */
	@Override
	public boolean checkingVaccineGivenOrNot(Members member,int dose,String vaccineName) {		
		Map<String, String> TTVisit = member.TTVisit();	
		if(!TTVisit.isEmpty()){
			String TTFinalDate = TTVisit.get(SyncConstant.TTFinalMapping.get(Integer.toString(dose)));
			if(TTFinalDate.isEmpty() || TTFinalDate.equalsIgnoreCase("null") || TTFinalDate ==null){
				return false;
			}else{
				return true;	
			}
		}else{
			return false;
		}
	}	
}
