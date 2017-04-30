package org.opensrp.service;

import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.opensrp.form.domain.FormData;
import org.opensrp.form.domain.FormField;
import org.opensrp.form.domain.FormInstance;
import org.opensrp.form.domain.FormSubmission;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.JsonArray;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
"classpath*:spring/test-applicationContext-opensrp.xml"})

public class EncounterSyncTest {
		
	
	@Before
	public void setUp() throws Exception
	{
		initMocks(this);
		
	}
	@Test
	public void shuoldCreateEncounter(){
		

		JsonNode enc = null;
		ObjectMapper mapper = new ObjectMapper();
	     try {
	            enc = mapper.readValue(new File("./../assets/form/woman_tt_form/form_definition.json"), JsonNode.class);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	     String anmId ="sujan";
	     String instanceId = "759afa5d-1fb6-47a1-a942-306edb9485fe";
	     String formName ="woman_tt_form";
	     String entityId="R69a8472-de13-420d-8634-5a8b0555347b";
	     long clientVersion = System.currentTimeMillis();
	     String formDataDefinitionVersion ="1";
	     FormInstance formInstance =new FormInstance();
	     
	    List<FormField> formFields = new ArrayList<FormField>();
	     
	    
	    ArrayNode fields = (ArrayNode) enc.get("form").get("fields");
	    for (JsonNode jsonNode : fields) {
			System.out.println(jsonNode.get("name"));
		}
	     System.out.println(fields);
	     FormData formData = new FormData();
	     formData.setBind_type("members");
	     formData.setDefault_bind_path("/model/instance/Woman_TT_Followup_Form");
	     formData.setFields(formFields);
	     
	     formInstance.setForm_data_definition_version("1");
	     
	     formInstance.setForm(formData);
	     long serverVersion = System.currentTimeMillis();
	     
	     FormSubmission formSubmission = new FormSubmission(anmId, instanceId, formName, entityId, formDataDefinitionVersion, clientVersion, formInstance);
	     
	    
	     
		 //System.out.println(formSubmission.toString());
	}
	
}
