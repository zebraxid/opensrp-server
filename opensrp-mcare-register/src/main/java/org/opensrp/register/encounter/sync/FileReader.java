package org.opensrp.register.encounter.sync;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public abstract class FileReader {
	
	/**
	 * read a json file. 
	 * @param formDir A directory of json file.
	 * @param formName name of a form name.
	 * @return a file as type JsonNode.
	 */
	public static JsonNode getFile(String formDir,String formName) throws IOException {
		JsonNode file = null;
		ObjectMapper mapper = new ObjectMapper();		
		String filePath = formDir+"/"+formName+"/form_definition.json";		
	    try {
	    	file = mapper.readValue(new File(filePath), JsonNode.class);
	     }catch (IOException e) {
	         throw new IOException();
	     }
		return file;	
	}

}
