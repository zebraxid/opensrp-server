package org.opensrp.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.connector.RapidProHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	private static Logger logger = LoggerFactory.getLogger(MessageService.class
		.toString());	
	@Value("#{opensrp['rapidpro.url']}")
	protected String RAPID_PRO_URL;
	
	@Value("#{opensrp['rapidpro.authorization.token']}")
	protected String AUTHORIZATION_TOKEN;
	
	public MessageService(){
		
	}
	public MessageService(String RAPID_PRO_URL, String AUTHORIZATION_TOKEN){
		this.RAPID_PRO_URL = RAPID_PRO_URL;
		this.AUTHORIZATION_TOKEN = AUTHORIZATION_TOKEN;
	}	
	
	public String sentMessage(String message,String clientName,String mobile,String location) throws JSONException{		
		JSONObject data = new JSONObject();		
		List<String> list = new ArrayList<String>();
		
		logger.info("Message sent to: "+this.getMobileNumber(mobile));
		list.add("tel:"+this.getMobileNumber(mobile));		
		data.put("text",message);
		data.put("urns",list);	
		
		RapidProHttpUtils.post(RAPID_PRO_URL, "", data.toString(), "", "",AUTHORIZATION_TOKEN);
		return mobile;
		
	}
	
	private String getMobileNumber(String mobile){
		if (mobile.length() == 10) {
				mobile = "+880"+mobile;
			 	
			} else if (mobile.length() > 10) {
			  mobile =mobile.substring(mobile.length() - 10);
			  mobile = "+880"+mobile;
			} else {
			  // whatever is appropriate in this case
			  throw new IllegalArgumentException("word has less than 3 characters!");
			}
		return mobile;
		
	}
}
