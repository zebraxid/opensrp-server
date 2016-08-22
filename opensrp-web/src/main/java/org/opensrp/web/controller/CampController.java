package org.opensrp.web.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.opensrp.camp.dto.CampDTO;
import org.opensrp.camp.service.CampDateService;
import org.opensrp.camp.service.CampService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CampController {
	
	private CampService campService;
	
	private CampDateService campDateService;
	
	private static Logger logger = LoggerFactory.getLogger(CampController.class);
	
	@Autowired
	public void setCampDateService(CampDateService campDateService) {
		this.campDateService = campDateService;
	}
	
	@Autowired
	public void setCampService(CampService campService) {
		this.campService = campService;
	}
	
	@RequestMapping(method = GET, value = "/camp")
	@ResponseBody
	public ResponseEntity<String> getCamp(@RequestParam String id) {
		logger.info("CAm::::" + campService);
		campService.getCampById(id);
		return new ResponseEntity<>("dddd", OK);
	}
	
	@RequestMapping(method = GET, value = "/campDate")
	@ResponseBody
	public ResponseEntity<String> getCampDate(@RequestParam String id) {
		campDateService.getCampDateBySessionId(id);
		return new ResponseEntity<>("dddd", OK);
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/add-camp")
	public ResponseEntity<String> addCamp(@RequestBody CampDTO campDTO) {
		logger.info("create request received for role - " + campDTO.getSession_name());
		String message = campService.add(campDTO);
		return new ResponseEntity<>(message, OK);
	}
}
