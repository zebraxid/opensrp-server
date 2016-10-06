package org.opensrp.service;

import org.springframework.stereotype.Service;

@Service
public class MessageService {
	public String sentMessage(String message,String clientName,String mobile,String location){
		System.err.println(message);
		System.err.println(mobile);
		return mobile;
		
	}
}
