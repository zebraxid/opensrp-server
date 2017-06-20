package org.opensrp.register.encounter.sync.forms;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.form.domain.FormData;
import org.opensrp.form.domain.FormField;
import org.opensrp.form.domain.FormInstance;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.encounter.sync.FileReader;
import org.opensrp.register.encounter.sync.SyncConstant;
import org.opensrp.register.encounter.sync.VaccineParamsBuilder;
import org.opensrp.register.encounter.sync.interfaces.FormsType;
import org.opensrp.register.encounter.sync.mapping.domain.EncounterSyncMapping;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChildVaccineFollowup extends FileReader implements FormsType<Members> {
	private static Logger logger = LoggerFactory.getLogger(ChildVaccineFollowup.class.toString());
	private ChildVaccineFollowup(){
		
	}
	
	/**
	 * @param   params.getFormDir(); as formDir  current directory location of the form.
	 * @param 	params.getVaccineDate() as vaccineDate date of vaccine.
	 * @param 	params.getVaccineDose() as vaccineDose dose number of a vaccine.
	 * @param 	params.getMember().caseId() as memberEntityId unique id of a member.
	 * @param 	params.getVaccineName() as vaccineName A vaccine name .
	 * @param 	params.getMember() as member A member information.	 
	 * @return 	FormSubmission 
	 */
	@Override
	public FormSubmission getFormSubmission(VaccineParamsBuilder params) throws IOException {
		FormSubmission  form = null ;		
		if(params.getMember()!=null){
			if(params.getEncounterSyncMapping()!=null){
				if(!isVaccineGiven(params.getMember(),params.getVaccineDose(),params.getVaccineName())){		    	
					form = getFormSubmissionWithInstanceId(params);
				}else{					
					logger.info(params.getVaccineName()+" "+params.getVaccineDose() +" is  already given...");
				}
			}else{			
			    if(!isVaccineGiven(params.getMember(),params.getVaccineDose(),params.getVaccineName())){		    	
			    	form =  craeteFormsubmission(params);	    	
			    }else{			    	
			    	logger.info(params.getVaccineName()+" "+params.getVaccineDose() +" is  already given...");
			    	
			    }
			}
		}else{
			logger.info("No member found in opensrp....");
			return  form;
		}
	    return form;		
	}
	
	/**
	 * <code>Called when a encounter is updated.</code>
	 * @param params.getEncounterSyncMapping().getInstanceId() as instanceId A member Id.
	 * @param params.getVaccineDate() as currentVaccineDate date of vaccine.
	 * @param params.getVaccineDose() as currentDose dose number of a dose.
	 * @param params.getMember().caseId() as memberEntityId unique id of a member.
	 * @param params.getVaccineName() as vaccineName number of vaccine name.	 
	 * @param params.getEncounterSyncMapping() as encounterSyncMapping A existing encounterSyncMapping.
	 * @param params.getFormSubmissions() as formSubmissions A FormSubmission object.
	 * @param params.getAllMembers() as allMembers A Allmember object.
	 * @return FormSubmission. 
	 * 
	 * */
	private FormSubmission getFormSubmissionWithInstanceId(VaccineParamsBuilder params) {
		String instanceId = params.getEncounterSyncMapping().getInstanceId().trim();
		String vaccineName = params.getVaccineName();
		String currentVaccineDate = params.getVaccineDate();
		int currentDose = params.getVaccineDose();
		EncounterSyncMapping encounterSyncMapping = params.getEncounterSyncMapping();
		AllFormSubmissions formSubmissions = params.getFormSubmissions();
		String memberEntityId = params.getMember().caseId();
		AllMembers allMembers = params.getAllMembers();
		FormSubmission formSubmission = formSubmissions.findByInstanceId(instanceId);		
		String currentRetroField="";
		String currentFinalField = "";
		String currentDoseField  = "";
		String beforeRetroField  = "";		
		String beforeFinalField="";
		String beforeDoseField ="";
		int beforeDose = encounterSyncMapping.getDose();	
		try{			
			Map<String,String> CurrentFinalRetroDoseField= this.getFinalRetroDoseAndField(vaccineName, currentDose);
			currentFinalField = CurrentFinalRetroDoseField.get("finalFieldName");	
			currentRetroField = CurrentFinalRetroDoseField.get("retroFieldName");
			currentDoseField = CurrentFinalRetroDoseField.get("doseFieldName");		    		
    		if(CurrentFinalRetroDoseField.isEmpty()){		    			
    			logger.info("vaccine not found");
    		}			
			String beforeVaccineName = encounterSyncMapping.getVaccineName();			
			Map<String,String> BeforeFinalRetroDoseField= this.getFinalRetroDoseAndField(beforeVaccineName, beforeDose);
			beforeFinalField = BeforeFinalRetroDoseField.get("finalFieldName");	
			beforeRetroField = BeforeFinalRetroDoseField.get("retroFieldName");
			beforeDoseField = BeforeFinalRetroDoseField.get("doseFieldName");		    		
    		if(BeforeFinalRetroDoseField.isEmpty()){		    			
    			logger.info("vaccine not found");
    		}
		}catch(Exception e){
    		logger.info("FormSubmission creating error megsasge"+e.getMessage());
    	}
		
		List<FormField> fields = formSubmission.getFormInstance().form().fields();		
		Map<String,String> fieldsMap = formSubmission.getFormInstance().form().getFieldsAsMap();
		Members member = allMembers.findByCaseId(memberEntityId);				
		
		fieldsMap.put(beforeRetroField, "");
		fieldsMap.put(beforeFinalField, "");
		fieldsMap.put(beforeDoseField, "");		
		fieldsMap.put(currentRetroField, currentVaccineDate.trim());
		fieldsMap.put(currentFinalField, currentVaccineDate.trim());
		fieldsMap.put(currentDoseField, Integer.toString(currentDose).trim());
		
		List<Map<String, String>> childVaccineVisits = member.child_vaccine();
		for (Map<String, String> childVaccineVisit : childVaccineVisits) {
			childVaccineVisit.put(currentRetroField, currentVaccineDate.trim());
			childVaccineVisit.put(currentFinalField, currentVaccineDate.trim());
			childVaccineVisit.put(currentDoseField, Integer.toString(currentDose).trim());			
			childVaccineVisit.put(beforeRetroField, "");
			childVaccineVisit.put(beforeFinalField, "");
			childVaccineVisit.put(beforeDoseField, "");			
		}		
		
		for (FormField formField : fields) {			
			String name =formField.name();
			if(name.equalsIgnoreCase(currentRetroField.trim())){
				formField.setValue(currentVaccineDate.trim());				
	    	}else if(name.equalsIgnoreCase(currentFinalField.trim())){
	    		formField.setValue(currentVaccineDate.trim());	    		
	    	}else if(name.equalsIgnoreCase(currentDoseField.trim())){
	    		formField.setValue(Integer.toString(currentDose).trim());	    		
	    	}else if(name.equalsIgnoreCase(beforeRetroField)){
	    		formField.setValue("");	    		
	    	}else if(name.equalsIgnoreCase(beforeFinalField)){
	    		formField.setValue("");	    		
	    	}else if(name.equalsIgnoreCase(beforeDoseField)){
	    		formField.setValue("");	    		
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
	 * Make a <code>FormSubmission</code> according to condition.
	 * if no vaccine is given before then reqiured member information 
	 * gets from Members object.
	 * if at least one vaccine is given before then reqiured member 
	 * information gets from Members.child_vaccine() object. 
	 * 
	 * 
	 * @param params.getFormDir() as  formDir  current directory location of the form.
	 * @param params.getVaccineDate() as vaccineDate date of vaccine.
	 * @param params.getVaccineDose() as vaccineDose dose number of a vaccine.
	 * @param params.getMember().caseId() as memberEntityId unique id of a member.
	 * @param params.getVaccineName() as vaccineName number of vaccine dose.
	 * @param params.getMember() as member A member information.
	 * @throws IOException 
	 * 			if stream to a file cannot be written.
	 * @return FormSubmission 
	 * */
	
	
	private FormSubmission craeteFormsubmission(VaccineParamsBuilder params) throws IOException{
		String formDir = params.getFormDir();
		String vaccineDate = params.getVaccineDate();
		int vaccineDose = params.getVaccineDose();
		String memberEntityId = params.getMember().caseId();
		Members member = params.getMember();
		String vaccineName = params.getVaccineName();
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
	    String finalFieldName="";
	    String retroFieldName = "";
	    String doseFieldName = "";	    
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
		    		Map<String,String> finalRetroDoseField= this.getFinalRetroDoseAndField(vaccineName, vaccineDose);
		    		finalFieldName = finalRetroDoseField.get("finalFieldName");	
		    		retroFieldName = finalRetroDoseField.get("retroFieldName");
		    		doseFieldName = finalRetroDoseField.get("doseFieldName");		    		
		    		if(!finalRetroDoseField.isEmpty()){		    			
		    			formField = this.getFormFieldValue(formField, name, finalFieldName, retroFieldName, doseFieldName, vaccineDose, vaccineDate, childVaccineFollowup,true,null,convertMemberToJsonObject);
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
	    			    			
	    			Map<String,String> finalRetroDoseField= this.getFinalRetroDoseAndField(vaccineName, vaccineDose);
	    			finalFieldName = finalRetroDoseField.get("finalFieldName");	
		    		retroFieldName = finalRetroDoseField.get("retroFieldName");
		    		doseFieldName = finalRetroDoseField.get("doseFieldName");		    		
		    		if(!finalRetroDoseField.isEmpty()){
		    			formField = this.getFormFieldValue(formField, name, finalFieldName, retroFieldName, doseFieldName, vaccineDose, vaccineDate, childVaccineFollowup,false,field,convertMemberToJsonObject);
		    		}else{
		    			logger.info("vaccine not found");
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
	
	/**
	 * <p> gets FormField with value.</p>
	 * @param  formField A FormField object.
	 * @param  name parsed field name getting from json file. 
	 * @param  finalFieldName A filed name which is defined in child vaccine followup form(Ex:<code>final_penta1</code>)
	 * @param  retroFieldName A filed name which is defined in child vaccine followup form(Ex:<code>penta1_retro</code>)
	 * @param  doseFieldName A filed name which is defined in child vaccine followup form(Ex:<code>penta1_dose</code>)
	 * @param  childVaccineFollowup followup of a child.
	 * @param  type false if child has no vaccine and true if child has at least one vavccine.
	 * @param field 
	 * @param convertMemberToJsonObject converted json of a member.
	 * @return FormField
	 * */
	
	public FormField getFormFieldValue(FormField formField,String name, String finalFieldName,String retroFieldName,String doseFieldName,int vaccineDose,String vaccineDate,Map<String, String> childVaccineFollowup,boolean type,Field field,JSONObject convertMemberToJsonObject ) throws JSONException{
		if(type){
			if(name.equalsIgnoreCase(finalFieldName.trim())){
				formField=this.setFormFieldValue(formField, name, finalFieldName, vaccineDose, vaccineDate,false);
			}else if(name.equalsIgnoreCase(retroFieldName.trim())){
				formField=this.setFormFieldValue(formField,  name, retroFieldName, vaccineDose, vaccineDate,false);
			}else if(!doseFieldName.isEmpty() && name.equalsIgnoreCase(doseFieldName.trim()) ){
				formField=this.setFormFieldValue(formField, name, doseFieldName, vaccineDose, vaccineDate,true);
			}else{
				formField.setValue(childVaccineFollowup.get(name).trim());
			}
		}else{			
			field.setAccessible(true);	
			if(name.equalsIgnoreCase(finalFieldName.trim())){
				formField=this.setFormFieldValue(formField, name, finalFieldName, vaccineDose, vaccineDate,false);
			}else if(name.equalsIgnoreCase(retroFieldName.trim())){
				formField=this.setFormFieldValue(formField,  name, retroFieldName, vaccineDose, vaccineDate,false);
			}else if(!doseFieldName.isEmpty() && name.equalsIgnoreCase(doseFieldName.trim())){
				formField=this.setFormFieldValue(formField, name, doseFieldName, vaccineDose, vaccineDate,true);
			}else{
				formField.setValue(convertMemberToJsonObject.get(field.getName()).toString().trim());
			}
		}
		
		return formField;
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
	public boolean isVaccineGiven(Members member,int dose,String vaccineName) {		
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
	 * <code>Generate final , retro and dose field name</code>.
	 * @param  vaccineName  A vaccine name.
	 * @param  vaccineDose A vaccine dose.
	 * @return  FinalRetroDoseAndField name.
	 * */
	public Map<String,String> getFinalRetroDoseAndField(String vaccineName,int vaccineDose){
		String finalFieldName="";
		String retroFieldName = "";
		String doseFieldName = "";
		Map<String,String> fieldsMap= new HashMap<>();
		try{
    		if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(0))){
    			finalFieldName = this.getFieldName("BCGFinalMapping", 0);
    			retroFieldName = this.getFieldName("BCGRetroMapping", 0);		    			
    			
    		}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(1))){
    			finalFieldName = this.getFieldName("PENTAFinalMapping", vaccineDose);
    			retroFieldName = this.getFieldName("PENTARetroMapping", vaccineDose);
    			doseFieldName = this.getFieldName("PENTADoseMapping", vaccineDose);		    			
    			
    		}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(2))){ //PCV
    			finalFieldName = this.getFieldName("PCVFinalMapping", vaccineDose);
    			retroFieldName = this.getFieldName("PCVRetroMapping", vaccineDose);
    			doseFieldName = this.getFieldName("PCVDoseMapping", vaccineDose);
    			
    		}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(3))){
    			finalFieldName = this.getFieldName("OPVFinalMapping", vaccineDose);
    			retroFieldName = this.getFieldName("OPVRetroMapping", vaccineDose);
    			doseFieldName = this.getFieldName("OPVDoseMapping", vaccineDose);
    			
    			
    		}else if(vaccineName.equalsIgnoreCase(SyncConstant.getChildVaccinesName().get(4))){ //IPV
    			finalFieldName = this.getFieldName("IPVFinalMapping", 0);
    			retroFieldName = this.getFieldName("IPVRetroMapping", 0);
    			
    		}else{
    			
    			return fieldsMap;
    		}
    		fieldsMap.put("finalFieldName", finalFieldName);
    		fieldsMap.put("retroFieldName", retroFieldName);
    		fieldsMap.put("doseFieldName", doseFieldName);
    	}catch(Exception e){
    		logger.info("FormSubmission creating error megsasge"+e.getMessage());
    	}
		return fieldsMap;
		
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
