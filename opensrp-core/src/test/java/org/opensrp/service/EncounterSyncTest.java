package org.opensrp.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.form.domain.FormData;
import org.opensrp.form.domain.FormField;
import org.opensrp.form.domain.FormInstance;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.repository.AllFormSubmissions;
import org.springframework.beans.factory.annotation.Autowired;


/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
"classpath*:spring/test-applicationContext-opensrp.xml"})*/

public class EncounterSyncTest {
	
	
	@Autowired
    private AllFormSubmissions formSubmissions;
	private CouchDbInstance dbInstance;
	private StdCouchDbConnector stdCouchDbConnector;
	
	@Before
	public void setUp() throws Exception
	{
		 HttpClient httpClient = new StdHttpClient.Builder() 
	        .host("localhost") 
	       	.username("Admin").password("mPower@1234")
	        .port(5984) 
	        .socketTimeout(1000) 
	        .build(); 
			dbInstance = new StdCouchDbInstance(httpClient);
			stdCouchDbConnector = new StdCouchDbConnector("opensrp-form", dbInstance, new StdObjectMapperFactory());
			stdCouchDbConnector.createDatabaseIfNotExists();
			formSubmissions = new AllFormSubmissions(stdCouchDbConnector);
		
	}
	
	@Ignore@Test
	public void shuoldCreateEncounter(){
		

		JsonNode enc = null;
		ObjectMapper mapper = new ObjectMapper();
	     try {
	            enc = mapper.readValue(new File("./../assets/form/woman_tt_form/form_definition.json"), JsonNode.class);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	     String anmId ="sujan";
	     String instanceId = "R759afa5d-1fb6-47a1-a942-306edb9485fe";
	     String formName ="woman_tt_form";
	     String entityId="D69a8472-de13-420d-8634-5a8b0555347b";
	     long clientVersion = System.currentTimeMillis();
	     String formDataDefinitionVersion ="1";
	     FormInstance formInstance =new FormInstance();
	     
	    List<FormField> formFields = new ArrayList<FormField>();
	     
	    
	    ArrayNode fields = (ArrayNode) enc.get("form").get("fields");
	    for (JsonNode jsonNode : fields) {
	    	FormField form=new FormField();
	    	String name = jsonNode.get("name").toString();
	    	form.setName(name.replace("\"", ""));
	    	form.setSource("");
	    	form.setValue("12");
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
	     //formSubmissions.add(formSubmission);
		 System.out.println(formSubmission.toString());
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
}
