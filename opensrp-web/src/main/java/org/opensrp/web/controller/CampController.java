package org.opensrp.web.controller;

import static org.springframework.http.HttpStatus.ALREADY_REPORTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import org.opensrp.dto.rapidpro.AnnouncedClient;
import org.opensrp.web.listener.RapidproMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
public class CampController {
	
	private static Logger logger = LoggerFactory.getLogger(CampController.class.toString());
	
	@Autowired
	private RapidproMessageListener rapidproMessageListener;
	
	public CampController() {
		
	}
	
	@RequestMapping(method = GET, value = "/message-announcement")
	@ResponseBody
	public ArrayList<String> campAnnouncement(@RequestParam String provider) {
		rapidproMessageListener.campAnnouncementListener(provider);
		ArrayList<String> response = new ArrayList<String>();
		return response;
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = RequestMethod.POST, value = "/send-announcement")
	@ResponseBody
	public ResponseEntity<HttpStatus> campAnnouncementnew(@RequestParam String provider,
	                                                      @RequestBody List<AnnouncedClient> announcedClients) {
		logger.info("request receive for camp announchment provider: " + provider + " ,clientSize:"
		        + announcedClients.size());
		if (announcedClients.isEmpty()) {
			return new ResponseEntity<>(BAD_REQUEST);
		}
		String json = new Gson().toJson(announcedClients);
		
		List<AnnouncedClient> announcedClient = new Gson().fromJson(json,
		    new TypeToken<List<AnnouncedClient>>() {}.getType());
		int announcementStatus = rapidproMessageListener.campAnnouncementListener(provider, announcedClient);
		if (announcementStatus == -1) {
			return new ResponseEntity<>(BAD_REQUEST);
		} else if (announcementStatus == 0) {
			new ResponseEntity<>(ALREADY_REPORTED);
		}
		return new ResponseEntity<>(CREATED);
	}
	
}
