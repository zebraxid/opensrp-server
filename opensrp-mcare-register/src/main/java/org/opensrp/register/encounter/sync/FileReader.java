package org.opensrp.register.encounter.sync;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public abstract class FileReader {
	
	public static JsonNode getFile(String formDir,String formName) throws IOException{
		JsonNode file = null;
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(formDir+":"+formName);
		String filePath = formDir+"/"+formName+"/form_definition.json";		
	    try {
	    	file = mapper.readValue(new File(filePath), JsonNode.class);
	     }catch (IOException e) {
	         throw new IOException();
	     }
		return file;	
	}

}
