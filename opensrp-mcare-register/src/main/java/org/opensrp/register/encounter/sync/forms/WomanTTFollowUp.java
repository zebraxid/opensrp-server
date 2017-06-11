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
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.encounter.sync.FileReader;
import org.opensrp.register.encounter.sync.SyncConstant;
import org.opensrp.register.encounter.sync.interfaces.FormsType;
import org.opensrp.register.encounter.sync.mapping.domain.EncounterSyncMapping;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;
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
	public FormSubmission getFormSubmission(String formDir,String vaccineDate,int vaccineDose,String memberEntityId,Members member,String vaccineName,EncounterSyncMapping encounterSyncMapping,AllFormSubmissions formSubmissions,AllMembers allMembers) throws IOException {
		FormSubmission  form = null ;
		if(member!=null){
			if(encounterSyncMapping!=null){
				form = getFormSubmissionWithInstanceId(encounterSyncMapping.getInstanceId().trim(),vaccineName,vaccineDate,vaccineDose,encounterSyncMapping,formSubmissions,memberEntityId,allMembers);
			}else{
			    if(member.TTVisit().isEmpty()){ // when no vaccine is given before .
			    	form =  craeteFormsubmission(formDir,vaccineDate,vaccineDose,memberEntityId,member);	    	
			    }else if(!checkingVaccineGivenOrNot(member,vaccineDose, vaccineName)){  // At least one vaccine is given before. 
			    	form =  craeteFormsubmission(formDir,vaccineDate,vaccineDose,memberEntityId,member);	    	
			    }
			}
		}
	    return form;		
	}	
	
	public FormSubmission getFormSubmissionWithInstanceId(String instanceId,String vaccineName,String currentVaccineDate, int currentDose,EncounterSyncMapping encounterSyncMapping,AllFormSubmissions formSubmissions,String memberEntityId,AllMembers allMembers) {		
		FormSubmission formSubmission = formSubmissions.findByInstanceId(instanceId);		
		String currentTTRetroField = SyncConstant.TTRetroMapping.get(Integer.toString(currentDose));
		String currentTTFinalField = SyncConstant.TTFinalMapping.get(Integer.toString(currentDose));
		String currentTTDoseField = SyncConstant.TTDoseMapping.get(Integer.toString(currentDose));
		int beforeDose = encounterSyncMapping.getDose();
		String beforeTTRetroField = SyncConstant.TTRetroMapping.get(Integer.toString(beforeDose));
		String beforeTTFinalField = SyncConstant.TTFinalMapping.get(Integer.toString(beforeDose));
		String beforeTTDoseField = SyncConstant.TTDoseMapping.get(Integer.toString(beforeDose));
		List<FormField> fields = formSubmission.getFormInstance().form().fields();		
		Map<String,String> fieldsMap = formSubmission.getFormInstance().form().getFieldsAsMap();
		System.err.println("allMembers:"+memberEntityId);
		Members member = allMembers.findByCaseId(memberEntityId);
		if(beforeDose==1){
			member.setTt1_final("");
			member.setTt1_retro("");
			member.setTt_1_dose("");
		}else if(beforeDose ==2){
			member.setTt2_final("");
			member.setTt2_retro("");
			member.setTt_2_dose("");
		}else if(beforeDose ==3){
			member.setTt3_final("");
			member.setTt3_retro("");
			member.setTt_3_dose("");
		}else if(beforeDose ==4){
			member.setTt4_final("");
			member.setTt4_retro("");
			member.setTt_4_dose("");
		}else if(beforeDose ==5){
			member.setTt5_final("");
			member.setTT5Retro("");
			member.setTT5Dose("");
		}else{
			
		}		
		
		fieldsMap.put(beforeTTRetroField, "");
		fieldsMap.put(beforeTTFinalField, "");
		fieldsMap.put(beforeTTDoseField, "");		
		fieldsMap.put(currentTTRetroField, currentVaccineDate.trim());
		fieldsMap.put(currentTTFinalField, currentVaccineDate.trim());
		fieldsMap.put(currentTTDoseField, Integer.toString(currentDose).trim());
		
		Map<String, String> ttVisit = member.TTVisit();
		for (FormField formField : fields) {			
			String name =formField.name();
			if(name.equalsIgnoreCase(currentTTRetroField.trim())){
				formField.setValue(currentVaccineDate.trim());
				ttVisit.put(currentTTRetroField, currentVaccineDate.trim());
	    	}else if(name.equalsIgnoreCase(currentTTFinalField.trim())){
	    		formField.setValue(currentVaccineDate.trim());
	    		ttVisit.put(currentTTFinalField, currentVaccineDate.trim());
	    	}else if(name.equalsIgnoreCase(currentTTDoseField.trim())){
	    		formField.setValue(Integer.toString(currentDose).trim());
	    		ttVisit.put(currentTTDoseField, Integer.toString(currentDose).trim());
	    	}else if(name.equalsIgnoreCase(beforeTTRetroField)){
	    		formField.setValue("");
	    		ttVisit.put(beforeTTRetroField, "");
	    	}else if(name.equalsIgnoreCase(beforeTTFinalField)){
	    		formField.setValue("");
	    		ttVisit.put(beforeTTFinalField, "");
	    	}else if(name.equalsIgnoreCase(beforeTTDoseField)){
	    		formField.setValue("");
	    		ttVisit.put(beforeTTDoseField, "");
	    	}else{
	    		
	    	}			
		}
		
		try{
			member.setId(member.getId());
			member.setRevision(member.getRevision());
			allMembers.update(member);
			return formSubmission;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
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
