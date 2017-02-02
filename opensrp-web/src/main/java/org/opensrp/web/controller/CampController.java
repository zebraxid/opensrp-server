/**
 * @author proshanto
 * 
 * */
package org.opensrp.web.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;

import org.json.JSONException;
import org.opensrp.camp.dto.CampDTO;
import org.opensrp.camp.service.CampDateService;
import org.opensrp.camp.service.CampService;
import org.opensrp.rest.register.DTO.CampDateEntryDTO;
import org.opensrp.rest.register.DTO.CommonDTO;
import org.opensrp.rest.services.LuceneCampDateService;
import org.opensrp.service.ClientListForCamp;
import org.opensrp.web.listener.CampListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

@Controller
public class CampController {
	
	private CampService campService;
	
	private CampDateService campDateService;
	
	private CampListener campListener;
	private ClientListForCamp clientListForCamp;
	private static Logger logger = LoggerFactory.getLogger(CampController.class);
	
	@Autowired
	private LuceneCampDateService luceneCampDateService;
	
	@Autowired
	public void setCampDateService(CampDateService campDateService) {
		this.campDateService = campDateService;
	}
	
	@Autowired
    public void setClientListForCamp(ClientListForCamp clientListForCamp) {
    	this.clientListForCamp = clientListForCamp;
    }
	@Autowired
	public void setCampService(CampService campService) {
		this.campService = campService;
	}	
	@Autowired
    public void setCampListener(CampListener campListener) {
    	this.campListener = campListener;
    }
	/**
	 * @param id is camp name id 
	 * @return a  specific camp with camp dates 
	 * */
	@RequestMapping(method = GET, value = "/camp")
	@ResponseBody
	public ResponseEntity<String> getCamp(@RequestParam String id) {		
		String jsonString = campService.getCampById(id);
		return new ResponseEntity<>(jsonString, HttpStatus.OK);
	}
	
	/***
	 * @return all camp name which is used in opensrp dashboard 
	 * when a user wants to add a camp .
	 * */
	@RequestMapping(method = GET, value = "/camp-name")
	@ResponseBody
	public ResponseEntity<String> getCampName() {
		
		String jsonString = campService.campNameList();
		return new ResponseEntity<>(jsonString, HttpStatus.OK);
	}
	
	/**
	 * @param id is the ID of a camp date not camp
	 * @return a camp date with relates to the specified @param id
	 * */
	@RequestMapping(method = GET, value = "/camp-date")
	@ResponseBody
	public ResponseEntity<String> getCampDateById(@RequestParam String id) {		
		return new ResponseEntity<>(new Gson().toJson(campDateService.findById(id)), OK);
	}
	
	/**
	 * @return all camps
	 * 
	 * */
	@RequestMapping(method = GET, value = "/all-camp")
	@ResponseBody
	public ResponseEntity<String> getAllCamp() throws JSONException {
		return new ResponseEntity<>(new Gson().toJson(campService.getAll()), HttpStatus.OK);
	}
	
	/**
	 * @return all camp dates
	 * */
	@RequestMapping(method = GET, value = "/all-camp-date")
	@ResponseBody
	public ResponseEntity<String> getAllCampDate() throws JSONException {
		return new ResponseEntity<>(new Gson().toJson(campDateService.getAll()), HttpStatus.OK);
	}
	
	/**
	 * This API for adding camp
	 * */
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/add-camp")
	public ResponseEntity<String> addCamp(@RequestBody CampDTO campDTO) {
		String message = campService.add(campDTO);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	/**
	 * @param campDTO is a Camp Object with a specific id
	 * This API for editing camp
	 * */
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/edit-camp")
	public ResponseEntity<String> editCamp(@RequestBody CampDTO campDTO) {
		String message = campService.edit(campDTO);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	/**
	 * @param thana is a dashboard location id of a thana . 
	 * @param union is a dashboard location id of a union .
	 * @param ward is a dashboard location id of a ward .
	 * @param unit is a dashboard location id of a unit .
	 * @param healthAssistant is a user id which is created from dashboard and also needs to 
	 * exists in openMRS .
	 * 
	 * @return total count of camp dates
	 * */
	@RequestMapping(headers = { "Accept=application/json" }, method = GET, value = "/camp-count-by-search")
	@ResponseBody
	public ResponseEntity<String> getCountCampDateForSearch(@RequestParam String thana,@RequestParam String union,
		@RequestParam String ward,@RequestParam String unit,@RequestParam String healthAssistant) {
		return new ResponseEntity<>(new Gson().toJson(campDateService.getCountCampDateForSearch(thana,union,ward,unit,healthAssistant)), HttpStatus.OK);
	}
	
	/**
	 * @param status . 	 
	 * @param healthAssistant is a user id which is created from dashboard and also needs to 
	 * exists in openMRS .
	 * @return total count of camp dates
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * */
	@RequestMapping(headers = { "Accept=application/json" }, method = GET, value = "/camp-count-by-user-status")
	@ResponseBody
	public ResponseEntity<String> getCountCampByUserNameAndStatus(@RequestParam MultiValueMap<String, String> queryParameters) throws JsonParseException, JsonMappingException, IOException {
		return new ResponseEntity<>(new Gson().toJson(luceneCampDateService.getDataCount(queryParameters)), HttpStatus.OK);
	}
	
	/**
	 * @param HA is a user both of openSRP and openMRS user.
	 * Sent a message to member mobile number.
	 * */
	@RequestMapping(method = GET, value = "/camp-announcement")
	@ResponseBody
	public ResponseEntity<String> campAnnouncement(@RequestParam String HA) {
		campListener.campAnnouncementListener(HA);
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	/**
	* @param HA is a user both of openSRP and openMRS user.
	* @return all eligible clients or member for vaccination to a specific HA 
	* 
	*/	
	@RequestMapping(method = GET, value = "/todays-client")
	@ResponseBody
	public ResponseEntity<String> todaysClient(@RequestParam String HA) {
		
		return new ResponseEntity<>(new Gson().toJson(clientListForCamp.todaysClientList(HA)), HttpStatus.OK);
	}
	
	/**
	* @param HA is a health assistant both of openSRP and openMRS user.
	* @param timeStamp 
	* @return all eligible clients or member for vaccination to a specific HA and after a timeStamp.
	* 
	*/	
	@RequestMapping(method = GET, value = "/client-list")
	@ResponseBody
	public ResponseEntity<String> AllClientsWithMissedCount(@RequestParam String anmIdentifier,@RequestParam long timeStamp){

		return new ResponseEntity<>(new Gson().toJson(clientListForCamp.clientList(anmIdentifier, timeStamp)), HttpStatus.OK);
	}
	
	/**
	 * @param queryParameters is a list of key.
	 * @param p is a number of page.
	 * @param limit is a data limit.
	 * @return all camp dates match with specified key.
	 * */
	@RequestMapping(method = GET, value="/camp-date/search")
    @ResponseBody
	public ResponseEntity<CommonDTO<CampDateEntryDTO>> getCampDatesByKey(@RequestParam MultiValueMap<String, String> queryParameters,@RequestParam int p,@RequestParam int limit) throws JsonParseException, JsonMappingException, IOException
	{
		CommonDTO<CampDateEntryDTO>  campDateDate  = luceneCampDateService.getData(queryParameters,p,limit);
		 return new ResponseEntity<>(campDateDate, HttpStatus.OK);
	}
	
}
