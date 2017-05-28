package org.opensrp.register.encounter.sync.forms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ArrayNode;
import org.opensrp.form.domain.FormData;
import org.opensrp.form.domain.FormField;
import org.opensrp.form.domain.FormInstance;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.encounter.sync.SyncConstant;
import org.opensrp.register.encounter.sync.interfaces.FormsType;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
@Service
public class WomanTTForm implements FormsType<Members> {

	private static Logger logger = LoggerFactory.getLogger(WomanTTForm.class.toString());
	private WomanTTForm(){
		
	}
	private AllMembers allMembers;
	
	@Autowired
	public WomanTTForm(AllMembers allMembers){
		this.allMembers = allMembers;
	}
	@Override
	public FormSubmission makeForm(String formDir,String vaccineDate,int vaccineDose,String patientIdEntityId,Members member) {
		
		FormSubmission  form = null ;
	    if(member.TTVisit().isEmpty()){ //Always create TT1 vaccine.
	    	form =  craeteFormsubmission(formDir,vaccineDate,vaccineDose,patientIdEntityId,member);
	    	
	    }else if(!isThisVaccineGiven(member,vaccineDose)){ //create TT vaccine according to dose
	    	form =  craeteFormsubmission(formDir,vaccineDate,vaccineDose,patientIdEntityId,member);
	    	
	    }else{
	    	
	    	
	    }
	    return form;
	    
		
		
	}
	
	@SuppressWarnings("static-access")
	private FormSubmission craeteFormsubmission(String formDir,String vaccineDate,int vaccineDose,String patientIdEntityId,Members member){
		JsonNode enc = null;
		ObjectMapper mapper = new ObjectMapper();
		String filePath = formDir+"/woman_tt_form/form_definition.json";
		
	    try {
	    	 enc = mapper.readValue(new File(filePath), JsonNode.class);
	     }catch (IOException e) {
	         e.printStackTrace();
	     }
	    
		
	    String anmId =member.PROVIDERID();
	    String instanceId = UUID.randomUUID().toString();
	    String formName ="woman_tt_form";
	    String entityId=patientIdEntityId;
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
	    	
	    	Gson memberJson = new Gson();
	    	
	    	if(!member.TTVisit().isEmpty()){
		    	try{
		    		if(name.equalsIgnoreCase(SyncConstant.TTFinalMapping.get(Integer.toString(vaccineDose)))){
		    			form.setValue(vaccineDate);
		    		}else if(name.equalsIgnoreCase(SyncConstant.TTRetroMapping.get(Integer.toString(vaccineDose)))){
		    			form.setValue(vaccineDate);
		    		}else if(name.equalsIgnoreCase(SyncConstant.TTDoseMapping.get(Integer.toString(vaccineDose)))){
		    			form.setValue(Integer.toString(vaccineDose));
		    		}else{
		    			form.setValue(member.TTVisit().get(name));
		    		}
		    	}catch(Exception e){
		    		System.out.println(e.getMessage());
		    	}
	    	}else{	    		
	    		try{
	    			
		    		if(name.equalsIgnoreCase(SyncConstant.TTFinalMapping.get("1"))){
		    			form.setValue(vaccineDate);
		    		}else if(name.equalsIgnoreCase(SyncConstant.TTRetroMapping.get("1"))){
		    			form.setValue(vaccineDate);
		    		}else if(name.equalsIgnoreCase(SyncConstant.TTDoseMapping.get("1"))){
		    			form.setValue(Integer.toString(vaccineDose));
		    		}else{
		    			form.setValue(memberJson.toJson(member).valueOf(name+""));
		    			System.out.println("Name:"+name+":::"+memberJson.toJson(member).valueOf(name));
		    		}
		    	}catch(Exception e){
		    		System.out.println(e.getMessage());
		    	}
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
	    System.out.println("formInstance:"+formInstance.toString());
	    FormSubmission formSubmission = new FormSubmission(anmId, instanceId, formName, entityId, formDataDefinitionVersion, clientVersion, formInstance);
	    formSubmission.setServerVersion(serverVersion);
	    System.out.println("formSubmission:::::::::"+formSubmission.toString());
		return formSubmission;
	}
	
	
	public static WomanTTForm getInstance(){
		return new WomanTTForm();
	}
	
	@Override
	public boolean isThisVaccineGiven(Members member,int dose) {
		
		if(!member.TTVisit().isEmpty()){		
			Map<String, String> TTVisit = member.TTVisit();		
			String TTFinalDate = TTVisit.get(SyncConstant.TTFinalMapping.get(Integer.toString(dose)));
			logger.info("TTFinalDate:"+TTFinalDate);
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
