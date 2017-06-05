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
import org.json.JSONException;
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

public class ChildVaccineFollowup extends FileReader implements FormsType<Members> {
	private static Logger logger = LoggerFactory.getLogger(ChildVaccineFollowup.class.toString());
	private ChildVaccineFollowup(){
		
	}
	@Override
	public FormSubmission getFormSubmission(String formDir,String vaccineDate,int vaccineDose,String patientIdEntityId,Members member,String vaccineName) throws IOException {
		FormSubmission  form = null ;		
		if(member!=null){
		    if(member.child_vaccine().isEmpty()){ 
		    	form =  craeteFormsubmission(formDir,vaccineDate,vaccineDose,patientIdEntityId,member,vaccineName);	    	
		    }else if(!checkingVaccineGivenOrNot(member,vaccineDose,vaccineName)){		    	
		    	form =  craeteFormsubmission(formDir,vaccineDate,vaccineDose,patientIdEntityId,member,vaccineName);	    	
		    }else{    	
		    	return  null;
		    }
		}else{
			return  null;
		}
	    return form;		
	}
	private FormSubmission craeteFormsubmission(String formDir,String vaccineDate,int vaccineDose,String patientIdEntityId,Members member,String vaccineName) throws IOException{
		JsonNode file = ChildVaccineFollowup.getFile(formDir, SyncConstant.CHILDACCINATIONFORMNAME);
		ObjectMapper mapper = new ObjectMapper();		    
	    FormInstance formInstance =new FormInstance();	     
	    List<FormField> formFields = new ArrayList<FormField>();
	    JsonNode bindType=  file.get("form").get("bind_type");
	    String convertMemberToString ;
	    JSONObject convertMemberToJsonObject = null;
	    Members getMemberNewObject = new Members();
	    Map<String, String> childVaccineFollowup = null;
	    if(member.child_vaccine().isEmpty()){
		    try{		    	
		    	convertMemberToString = mapper.writeValueAsString(member);		
		    	convertMemberToJsonObject = new JSONObject(convertMemberToString);	
		    	System.err.println("convertMemberToJsonObject:"+convertMemberToJsonObject);
		    }catch(Exception e){
		    	logger.info(""+e.getMessage());
		    }
	    }else{
	    	childVaccineFollowup = member.child_vaccine().get(member.child_vaccine().size()-1);
	    }	   
	    
	    ArrayNode fields = (ArrayNode) file.get("form").get("fields");
	    String fieldNameFinal="";
	    String fieldNameRetro = "";
	    String fieldNameDose = "";	    
	    for (JsonNode node : fields) {
	    	FormField formField=new FormField();
	    	String name = node.get("name").toString();
	    	name = name.replace("\"", "");
	    	StringBuilder bType = new StringBuilder(bindType.toString());
	    	bType.append(".".toString());
	    	bType.append(name);	    	
	    	formField.setName(name);	    	
	    	formField.setSource(bType.toString().replace("\"", ""));
	    	
	    	if(!member.child_vaccine().isEmpty()){
	    		
		    	try{
		    		if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(0))){//BCG
		    			fieldNameFinal = this.getFieldName("BCGFinalMapping", vaccineDose);
		    			fieldNameRetro = this.getFieldName("BCGRetroMapping", vaccineDose);		    			
		    			if(name.equalsIgnoreCase(fieldNameFinal.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameFinal, vaccineDose, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameRetro.trim())){
		    				formField=this.setFormFieldValue(formField,  name, fieldNameRetro, vaccineDose, vaccineDate,false);
		    			}else{
		    				formField.setValue(childVaccineFollowup.get(name).trim());
		    			}
		    		}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(1))){// Pentavalent
		    			fieldNameFinal = this.getFieldName("PENTAFinalMapping", vaccineDose);
		    			fieldNameRetro = this.getFieldName("PENTARetroMapping", vaccineDose);
		    			fieldNameDose = this.getFieldName("PENTADoseMapping", vaccineDose);		    			
		    			if(name.equalsIgnoreCase(fieldNameFinal.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameFinal, vaccineDose, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameRetro.trim())){
		    				formField=this.setFormFieldValue(formField,  name, fieldNameRetro, vaccineDose, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameDose.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameDose, vaccineDose, vaccineDate,true);
		    			}else{
		    				formField.setValue(childVaccineFollowup.get(name).trim());
		    			}
		    			
		    		}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(2))){ //PCV
		    			fieldNameFinal = this.getFieldName("PCVFinalMapping", vaccineDose);
		    			fieldNameRetro = this.getFieldName("PCVRetroMapping", vaccineDose);
		    			fieldNameDose = this.getFieldName("PCVDoseMapping", vaccineDose);
		    			if(name.equalsIgnoreCase(fieldNameFinal.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameFinal, vaccineDose, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameRetro.trim())){
		    				formField=this.setFormFieldValue(formField,  name, fieldNameRetro, vaccineDose, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameDose.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameDose, vaccineDose, vaccineDate,true);
		    			}else{
		    				formField.setValue(childVaccineFollowup.get(name).trim());
		    			}
		    			
		    		}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(3))){ //OPV
		    			fieldNameFinal = this.getFieldName("OPVFinalMapping", vaccineDose);
		    			fieldNameRetro = this.getFieldName("OPVRetroMapping", vaccineDose);
		    			fieldNameDose = this.getFieldName("OPVDoseMapping", vaccineDose);
		    			if(name.equalsIgnoreCase(fieldNameFinal.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameFinal, vaccineDose, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameRetro.trim())){
		    				formField=this.setFormFieldValue(formField,  name, fieldNameRetro, vaccineDose, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameDose.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameDose, vaccineDose, vaccineDate,true);
		    			}else{
		    				formField.setValue(childVaccineFollowup.get(name).trim());
		    			}
		    		}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(4))){ //IPV
		    			fieldNameFinal = this.getFieldName("IPVFinalMapping", vaccineDose);
		    			fieldNameRetro = this.getFieldName("IPVRetroMapping", vaccineDose);
		    			if(name.equalsIgnoreCase(fieldNameFinal.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameFinal, vaccineDose, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameRetro.trim())){
		    				formField=this.setFormFieldValue(formField,  name, fieldNameRetro, vaccineDose, vaccineDate,false);
		    			}else{
		    				formField.setValue(childVaccineFollowup.get(name).trim());
		    			}		    			
		    		}else{
		    			
		    		}
		    	}catch(Exception e){
		    		
		    	}
	    	}else{	    		
	    		try{	    			
	    			Field field = getMemberNewObject.getClass().getDeclaredField(name);
	    			field.setAccessible(true);	    			
	    			if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(0))){//BCG
		    			fieldNameFinal = this.getFieldName("BCGFinalMapping", vaccineDose);
		    			fieldNameRetro = this.getFieldName("BCGRetroMapping", vaccineDose);		    			
		    			if(name.equalsIgnoreCase(fieldNameFinal.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameFinal, vaccineDose, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameRetro.trim())){
		    				formField=this.setFormFieldValue(formField,  name, fieldNameRetro, vaccineDose, vaccineDate,false);
		    			}else{
		    				formField.setValue(convertMemberToJsonObject.get(field.getName()).toString().trim());
		    			}
		    		}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(1))){// Pentavalent
		    			fieldNameFinal = this.getFieldName("PENTAFinalMapping", vaccineDose);
		    			fieldNameRetro = this.getFieldName("PENTARetroMapping", vaccineDose);
		    			fieldNameDose = this.getFieldName("PENTADoseMapping", vaccineDose);		    			
		    			if(name.equalsIgnoreCase(fieldNameFinal.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameFinal, vaccineDose, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameRetro.trim())){
		    				formField=this.setFormFieldValue(formField,  name, fieldNameRetro, vaccineDose, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameDose.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameDose, vaccineDose, vaccineDate,true);
		    			}else{
		    				formField.setValue(convertMemberToJsonObject.get(field.getName()).toString().trim());
		    			}
		    			
		    		}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(2))){ //PCV
		    			fieldNameFinal = this.getFieldName("PCVFinalMapping", vaccineDose);
		    			fieldNameRetro = this.getFieldName("PCVRetroMapping", vaccineDose);
		    			fieldNameDose = this.getFieldName("PCVDoseMapping", vaccineDose);
		    			if(name.equalsIgnoreCase(fieldNameFinal.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameFinal, vaccineDose, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameRetro.trim())){
		    				formField=this.setFormFieldValue(formField,  name, fieldNameRetro, vaccineDose, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameDose.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameDose, vaccineDose, vaccineDate,true);
		    			}else{
		    				formField.setValue(convertMemberToJsonObject.get(field.getName()).toString().trim());
		    			}
		    			
		    		}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(3))){ //OPV
		    			fieldNameFinal = this.getFieldName("OPVFinalMapping", vaccineDose);
		    			fieldNameRetro = this.getFieldName("OPVRetroMapping", vaccineDose);
		    			fieldNameDose = this.getFieldName("OPVDoseMapping", vaccineDose);
		    			if(name.equalsIgnoreCase(fieldNameFinal.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameFinal, vaccineDose, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameRetro.trim())){
		    				formField=this.setFormFieldValue(formField,  name, fieldNameRetro, vaccineDose, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameDose.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameDose, vaccineDose, vaccineDate,true);
		    			}else{
		    				formField.setValue(convertMemberToJsonObject.get(field.getName()).toString().trim());
		    			}
		    		}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(4))){ //IPV
		    			fieldNameFinal = this.getFieldName("IPVFinalMapping", vaccineDose);
		    			fieldNameRetro = this.getFieldName("IPVRetroMapping", vaccineDose);
		    			if(name.equalsIgnoreCase(fieldNameFinal.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameFinal, vaccineDose, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameRetro.trim())){
		    				formField=this.setFormFieldValue(formField,  name, fieldNameRetro, vaccineDose, vaccineDate,false);
		    			}else{
		    				formField.setValue(convertMemberToJsonObject.get(field.getName()).toString().trim());
		    			}
		    			
		    		}else{
		    			
		    		}	    			
		    		
		    	}catch(Exception e){
		    		formField.setValue("");		    		
		    	}
	    	}	    	
	    	if(name.equalsIgnoreCase("id")){				
	    		formField.setValue(patientIdEntityId.trim());
			}
	    	formFields.add(formField);
			
		}	    
	    
	    FormData formData = new FormData();
	    formData.setBind_type("members");
	    formData.setDefault_bind_path("/model/instance/Child_Vaccination_Followup");
	    formData.setFields(formFields);	     
	    formInstance.setForm_data_definition_version("1");	     
	    formInstance.setForm(formData);	       
	    FormSubmission formSubmission = new FormSubmission(member.PROVIDERID(),UUID.randomUUID().toString().trim(), SyncConstant.CHILDACCINATIONFORMNAME, patientIdEntityId, "1", System.currentTimeMillis(), formInstance);
	    formSubmission.setServerVersion(System.currentTimeMillis());	  
		return formSubmission;
	}
	
	public static ChildVaccineFollowup getInstance(){
		return new ChildVaccineFollowup();
	}
	
	@Override
	public boolean checkingVaccineGivenOrNot(Members member,int dose,String vaccineName) {		
		
		String finalValue ;
		if(!member.child_vaccine().isEmpty()){	
			Map<String, String> childVaccineFollowup = member.child_vaccine().get(member.child_vaccine().size()-1);	
			if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(0))){//BCG
				finalValue = childVaccineFollowup.get(SyncConstant.BCGFinalMapping.get(Integer.toString(dose)));
				return this.checkThisVaccineGivenOrNot(finalValue);
			}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(1))){// Pentavalent
				finalValue = childVaccineFollowup.get(SyncConstant.PENTAFinalMapping.get(Integer.toString(dose)));
				return this.checkThisVaccineGivenOrNot(finalValue);
			}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(2))){ //PCV
				finalValue = childVaccineFollowup.get(SyncConstant.PCVFinalMapping.get(Integer.toString(dose)));
				return this.checkThisVaccineGivenOrNot(finalValue);
			}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(3))){ //OPV
				finalValue = childVaccineFollowup.get(SyncConstant.OPVFinalMapping.get(Integer.toString(dose)));
				return this.checkThisVaccineGivenOrNot(finalValue);
			}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(4))){ //IPV
				finalValue = childVaccineFollowup.get(SyncConstant.IPVFinalMapping.get(Integer.toString(dose)));
				return this.checkThisVaccineGivenOrNot(finalValue);
			}else{
				return false;
			}		
		
		}else{
			return false;
		}
	}
	
	private boolean checkThisVaccineGivenOrNot(String finalValue){		
		if(finalValue.equalsIgnoreCase("") || finalValue.equalsIgnoreCase("null") || finalValue.equalsIgnoreCase(null)){
			return false;
		}else{
			return true;
		}
	}
	
	public String getFieldName(String vaccineMappinngFieldName,int vaccineDose) throws NoSuchFieldException, IllegalAccessException, JSONException{
		JSONObject convertVaccineToJsonObject = null;	    
    	Field field;
    	String fieldName = null;
		try {
			field = SyncConstant.class.getDeclaredField(vaccineMappinngFieldName);
			field.setAccessible(true); 
			convertVaccineToJsonObject = new JSONObject(field.get(0).toString());			
			fieldName = (String) convertVaccineToJsonObject.get(Integer.toString(vaccineDose));			
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			throw new  NoSuchFieldException();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			throw new SecurityException();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			throw new IllegalArgumentException();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new IllegalAccessException();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			throw new JSONException(e.getMessage());
		}
		return fieldName;
		
	}
	public FormField setFormFieldValue(FormField formField,String name,String fieldName,int vaccineDose,String vaccineDate,boolean isDose){
		if(isDose){
			if(name.equalsIgnoreCase(fieldName)){
				formField.setValue(Integer.toString(vaccineDose).trim());
			}				
		}else{
			if(name.equalsIgnoreCase(fieldName.trim())){				
				formField.setValue(vaccineDate.trim());					
			}
		}		
		return formField;		
	}

}
