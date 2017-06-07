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
	
	/**
	 * @param   formDir  current directory location of the form.
	 * @param 	vaccineDate date of vaccine.
	 * @param 	vaccineDose dose number of a vaccine.
	 * @param 	memberEntityId unique id of a member.
	 * @param 	vaccineName number of vaccine dose.
	 * @param 	member A member information.	 
	 * @return 	FormSubmission 
	 */
	@Override
	public FormSubmission getFormSubmission(String formDir,String vaccineDate,int vaccineDose,String memberEntityId,Members member,String vaccineName) throws IOException {
		FormSubmission  form = null ;		
		if(member!=null){
		    if(member.child_vaccine().isEmpty()){ 
		    	form =  craeteFormsubmission(formDir,vaccineDate,vaccineDose,memberEntityId,member,vaccineName);	    	
		    }else if(!checkingVaccineGivenOrNot(member,vaccineDose,vaccineName)){		    	
		    	form =  craeteFormsubmission(formDir,vaccineDate,vaccineDose,memberEntityId,member,vaccineName);	    	
		    }else{    	
		    	return  null;
		    }
		}else{
			return  null;
		}
	    return form;		
	}
	
	/**
	 * Make a <code>FormSubmission</code> according to condition.
	 * if no vaccine is given before then reqiured member information 
	 * gets from Members object.
	 * if at least one vaccine is given before then reqiured member 
	 * information gets from Members.child_vaccine() object. 
	 * 
	 * 
	 * @param  formDir  current directory location of the form.
	 * @param vaccineDate date of vaccine.
	 * @param vaccineDose dose number of a vaccine.
	 * @param memberEntityId unique id of a member.
	 * @param vaccineName number of vaccine dose.
	 * @param member A member information.
	 * @throws IOException 
	 * 			if stream to a file cannot be written.
	 * @return FormSubmission 
	 * */
	private FormSubmission craeteFormsubmission(String formDir,String vaccineDate,int vaccineDose,String memberEntityId,Members member,String vaccineName) throws IOException{
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
	    	/** 
	    	 * At least one vaccine is given then goes here.	    		
	    	 */
	    	if(!member.child_vaccine().isEmpty()){
		    	try{
		    		if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(0))){
		    			fieldNameFinal = this.getFieldName("BCGFinalMapping", 0);
		    			fieldNameRetro = this.getFieldName("BCGRetroMapping", 0);		    			
		    			if(name.equalsIgnoreCase(fieldNameFinal.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameFinal, 0, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameRetro.trim())){
		    				formField=this.setFormFieldValue(formField,  name, fieldNameRetro, 0, vaccineDate,false);
		    			}else{
		    				formField.setValue(childVaccineFollowup.get(name).trim());
		    			}		    			
		    		}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(1))){
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
		    		}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(3))){
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
		    			fieldNameFinal = this.getFieldName("IPVFinalMapping", 0);
		    			fieldNameRetro = this.getFieldName("IPVRetroMapping", 0);
		    			if(name.equalsIgnoreCase(fieldNameFinal.trim())){
		    				formField=this.setFormFieldValue(formField, name, fieldNameFinal, 0, vaccineDate,false);
		    			}else if(name.equalsIgnoreCase(fieldNameRetro.trim())){
		    				formField=this.setFormFieldValue(formField,  name, fieldNameRetro, 0, vaccineDate,false);
		    			}else{
		    				formField.setValue(childVaccineFollowup.get(name).trim());
		    			}		    			
		    		}else{
		    			logger.info("vaccine not found");
		    		}
		    	}catch(Exception e){
		    		logger.info("FormSubmission creating error megsasge"+e.getMessage());
		    	}
	    	}else{ 
	    		/** 
		    	 * No vaccine is given then goes here.	    		
		    	 */    		
	    		try{	    			
	    			Field field = getMemberNewObject.getClass().getDeclaredField(name);// access private filed of members class
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
		    			logger.info("Vaccine not found");
		    		}	    			
		    		
		    	}catch(Exception e){
		    		formField.setValue("");		    		
		    	}
	    	}	    	
	    	if(name.equalsIgnoreCase("id")){				
	    		formField.setValue(memberEntityId.trim());
			}
	    	formFields.add(formField);			
		}
	    FormData formData = new FormData();
	    formData.setBind_type("members");
	    formData.setDefault_bind_path("/model/instance/Child_Vaccination_Followup");
	    formData.setFields(formFields);	     
	    formInstance.setForm_data_definition_version("1");	     
	    formInstance.setForm(formData);	       
	    FormSubmission formSubmission = new FormSubmission(member.PROVIDERID(),UUID.randomUUID().toString().trim(), SyncConstant.CHILDACCINATIONFORMNAME, memberEntityId, "1", System.currentTimeMillis(), formInstance);
	    formSubmission.setServerVersion(System.currentTimeMillis());	  
		return formSubmission;
	}
	
	public static ChildVaccineFollowup getInstance(){
		return new ChildVaccineFollowup();
	}
	
	/**
	 * This method is used to check a vaccine given or not.		 
	 * @param vaccineDose dose number of a vaccine.	 
	 * @param vaccineName number of vaccine dose.
	 * @param member member information.
	 * 
	 * */
	@Override
	public boolean checkingVaccineGivenOrNot(Members member,int dose,String vaccineName) {		
		String finalValue ;
		if(!member.child_vaccine().isEmpty()){
			try{
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
			}catch(Exception e){
				throw new NullPointerException();
			}
		}else{
			return false;
		}		
	}
	
	private boolean checkThisVaccineGivenOrNot(String finalValue){		
		if(finalValue.equalsIgnoreCase("") || finalValue.equalsIgnoreCase("null") || finalValue==null){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * @param vaccineMappinngFieldName static Map variable of a class as example
	 * {@link org.opensrp.register.encounter.sync.SyncConstant#BCGFinalMapping}
	 * {@link org.opensrp.register.encounter.sync.SyncConstant#PCVFinalMapping}
	 * {@link org.opensrp.register.encounter.sync.SyncConstant#OPVFinalMapping}
	 * {@link org.opensrp.register.encounter.sync.SyncConstant#PENTAFinalMapping}
	 * {@link org.opensrp.register.encounter.sync.SyncConstant#IPVFinalMapping} 
	 * @param vaccineDose number of vaccine dose
	 * 	  
	 * @throws NoSuchFieldException if a Filed does not exist.
	 * @throws IOException if stream to aFile cannot be written to or closed.
	 * @throws JSONException if json is invalid.
	 * @throws IllegalArgumentException if aFile is a directory.
	 * @throws IllegalArgumentException if a File cannot be written.
	 * @throws SecurityException if a SecurityManager exists and
	 * disallows read or write access to aFile.
	 * 
	 * @return FieldName
	 * */
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
	
	/**
	 * set field to vale of <code>FormField</code> 
	 * @param  formField A FormField object.
	 * @param  name parsed field name getting from json file.
	 * @param  fieldName A field name getting from Constant file.
	 * @param  vaccineDose number of vaccine dose.
	 * @param  vaccineDate date of vaccine.
	 * @param  isDose a indication of a condition.
	 * 
	 * @return FormField 
	 * */
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
