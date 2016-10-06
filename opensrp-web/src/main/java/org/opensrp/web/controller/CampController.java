package org.opensrp.web.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.json.JSONException;
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

import com.google.gson.Gson;

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
		String jsonString = campService.getCampById(id);
		return new ResponseEntity<>(jsonString, OK);
	}
	
	@RequestMapping(method = GET, value = "/camp-date")
	@ResponseBody
	public ResponseEntity<String> getCampDateById(@RequestParam String id) {		
		return new ResponseEntity<>(new Gson().toJson(campDateService.findById(id)), OK);
	}
	
	@RequestMapping(method = GET, value = "/all-camp")
	@ResponseBody
	public ResponseEntity<String> getAllUserName() throws JSONException {
		return new ResponseEntity<>(new Gson().toJson(campService.getAll()), OK);
	}
	@RequestMapping(method = GET, value = "/all-camp-date")
	@ResponseBody
	public ResponseEntity<String> getAllCamp() throws JSONException {
		return new ResponseEntity<>(new Gson().toJson(campDateService.getAll()), OK);
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/add-camp")
	public ResponseEntity<String> addCamp(@RequestBody CampDTO campDTO) {
		String message = campService.add(campDTO);
		return new ResponseEntity<>(message, OK);
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/edit-camp")
	public ResponseEntity<String> editCamp(@RequestBody CampDTO campDTO) {
		String message = campService.edit(campDTO);
		return new ResponseEntity<>(message, OK);
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = GET, value = "/camp/search")
	@ResponseBody
	public ResponseEntity<String> search(@RequestParam String thana,@RequestParam String union,
		@RequestParam String ward,@RequestParam String unit,@RequestParam String healthAssistant) {		
		return new ResponseEntity<>(new Gson().toJson(campDateService.search(thana,union,ward,unit,healthAssistant)), OK);
	}
}
