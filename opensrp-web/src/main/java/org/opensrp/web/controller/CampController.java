package org.opensrp.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.opensrp.web.listener.RapidproMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CampController {
	
	@Autowired
	private RapidproMessageListener rapidproMessageListener;
	
	public CampController() {
		
	}
	
	@RequestMapping(method = GET, value = "/message-announcement")
	@ResponseBody
	public ResponseEntity<String> campAnnouncement(@RequestParam String provider) {
		rapidproMessageListener.campAnnouncementListener(provider);
		return new ResponseEntity<>("", HttpStatus.OK);
	}
}
