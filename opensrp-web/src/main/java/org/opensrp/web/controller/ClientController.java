package org.opensrp.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.opensrp.service.ClientAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
public class ClientController {
	
	@Autowired
	private ClientAPIService clientAPIService;	
	
	public ClientController(){
		
	}
	
	@RequestMapping(method = GET, value = "/client-healthid-list")
	@ResponseBody
	public ResponseEntity<String> clientList(@RequestParam String anmIdentifier,@RequestParam long timeStamp){
		return new ResponseEntity<>(new Gson().toJson(clientAPIService.getHealthIdForAllClient(anmIdentifier, timeStamp)), HttpStatus.OK);
	}
	
}
