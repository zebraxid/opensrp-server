package org.opensrp.register.encounter.sync.forms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.opensrp.form.domain.FormData;
import org.opensrp.form.domain.FormField;
import org.opensrp.form.domain.FormInstance;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.encounter.sync.interfaces.FormsType;
import org.opensrp.register.mcare.domain.Members;

public class WomanTTForm implements FormsType<Members> {

	private WomanTTForm(){
		
	}
	@Override
	public FormSubmission makeForm(String formDir,String vaccineDate,int vaccineDose,String patientId) {
		JsonNode enc = null;
		ObjectMapper mapper = new ObjectMapper();
		String filePath = formDir+"/woman_tt_form/form_definition.json";
		
	     try {
	    	 enc = mapper.readValue(new File(filePath), JsonNode.class);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    
	     String anmId ="sujan";
	     String instanceId = UUID.randomUUID().toString();
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
	    	/*try{
	    	form.setValue(map.get(name.toString()).toString());
	    	}catch(Exception e){
	    		System.out.println(e.getMessage());
	    	}*/
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
	    System.out.println("formSubmission:::::::::"+formSubmission.toString());
		return formSubmission;
		
		
	}

	@Override
	public void submit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Members get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	public static WomanTTForm getInstance(){
		return new WomanTTForm();
	}
}
