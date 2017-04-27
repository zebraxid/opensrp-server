package org.opensrp.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;





import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
public class JsonParser {

	public String getFormDefinition(String formJson) throws JsonProcessingException, IOException {
	try{
		
		
		//read json file data to String
		//byte[] jsonData = Files.readAllBytes(Paths.get("tt.json"));
		JsonParser jsonParser=new  JsonParser();
		//create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();
	//	objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	//	objectMapper.readValue(jsonData, FormSubmission.class)
		//read JSON like DOM Parser FormSubmission
		JsonNode rootNode = objectMapper.readTree(formJson);
		FormDefinition formD=jsonParser.getForm(rootNode );
		//System.out.println(objectMapper.writeValueAsString(formD));;
	//	objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		//objectMapper.writeValue(new File("form_definition.json"), formD);
		String s=objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(formD);
		//String s=objectMapper.writeValueAsString(formD);
		//objectMapper.
		//System.out.println(s);
	
	return s;
	}catch(Exception e){
		System.err.println(e.getMessage());
		e.printStackTrace();
		
	}
	return null;
	}
	
	private List<FormField> getFields(JsonNode node,String source, List<FormField> fields){
		if(fields == null){
			fields = new ArrayList<>();
		}
		
		if(node.isArray()){
			Iterator<JsonNode> elements = node.getElements();

			while (elements.hasNext()) {
				JsonNode childNode = elements.next();
				getFields(childNode, source, fields);
			}
		}
		else {
			if(!node.get("type").asText().equalsIgnoreCase("group") && !node.get("type").asText().equalsIgnoreCase("repeat"))
			{			
				fields.add(new FormField(node.get("name").asText(), source+node.get("name").asText()));
			}
			else if(node.get("type").asText().equalsIgnoreCase("group")){
				fields.addAll(getGroupFields(node,source));
			}
		}
		
		return fields;
	}
	
	private List<FormField> getGroupFields(JsonNode node, String source){
		
		List<FormField> list =new ArrayList<FormField>();
		source=source+node.get("name").asText()+"/";
		try{
			Iterator<JsonNode> elements = node.path("children").getElements();
		
			while(elements.hasNext()){
				JsonNode jjNode=elements.next();
				getFields(jjNode, source, list);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	private FormDefinition getForm(JsonNode rootNode ){
		//StringBuilder sBuilder=new StringBuilder("{ ");
		FormDefinition formDefinition= new FormDefinition();
		//FormInstance formInstance=new FormInstance();
		//formInstance. 
		
		
	//]
		
	
		String formName=rootNode.get("name").asText();
	String source="/model/instance/"+formName+"/";
	
		//System.out.println(rootNode.get("name"));
		JsonNode phoneNosNode = rootNode.path("children");
		//getFields();
		List<FormField> fields=getFields(phoneNosNode, source, null);
		//System.out.println(fields);
		FormField field=new FormField("id",null);
		field.setShouldLoadValue(true);
		//fields.add(field);
	//	fields.set(0, field);
		fields.add(0, field);
		Form formData =new Form("", source, fields, null);
		List<SubFormDefinition> sub_forms=getSubForms(phoneNosNode, source, null);
		formData.setSub_forms(sub_forms);
		formDefinition.setForm(formData);
		return formDefinition;
	}
	
	
	private SubFormDefinition getSubForm(JsonNode rootNode, String source){
		String formName=rootNode.get("name").asText();
		source+=formName+"/";
		System.out.println(rootNode.path("children"));
		List<FormField> fields=getFields(rootNode.path("children"), source, null);
		FormField field=new FormField("id",null);
		field.setShouldLoadValue(true);
		//fields.add(field);
		fields.add(0, field);
		SubFormDefinition subForm=new SubFormDefinition(formName, fields);
		subForm.setDefault_bind_path(source);
		subForm.setBind_type("");
		//SubFormDefinition
		return subForm ;
	}
	
	public static void main(String[] args) throws JsonProcessingException, IOException {
		Scanner s = new Scanner(new File("D:\\opensrpVaccinatorWkspc\\forms\\daily_treatment_monitoring\\form.json"));
		StringBuilder sb = new StringBuilder();
		while (s.hasNextLine()) {
			sb.append(s.nextLine());
		}
		System.out.println(new JsonParser().getFormDefinition(sb.toString()));
	}
	

	
	private List<SubFormDefinition> getSubForms(JsonNode node,String source, List<SubFormDefinition> subforms){
		if(subforms == null){
			subforms = new ArrayList<>();
		}
		
		if(node.isArray()){
			Iterator<JsonNode> nodes = node.getElements();
			
			while(nodes.hasNext()){
				JsonNode n = nodes.next();
				if(n.has("type") && n.get("type").asText().equalsIgnoreCase("repeat")){
					subforms.add(getSubForm(n, source));
				}
				if(n.has("children")){
					String nodePath = source + n.get("name").asText() +"/";
					getSubForms(n.get("children"), nodePath, subforms);
				}		
			}
		}
		return subforms;
	}
}
