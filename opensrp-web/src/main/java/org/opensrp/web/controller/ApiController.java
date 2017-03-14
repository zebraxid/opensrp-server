package org.opensrp.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;

import org.opensrp.dto.FieldValue;
import org.opensrp.dto.register.HHRegisterDTO;
import org.opensrp.rest.services.LuceneHouseHoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
public class ApiController {

	private LuceneHouseHoldService luceneHouseHoldService;
	
	@Autowired
	public ApiController(LuceneHouseHoldService luceneHouseHoldService)
	{
		this.luceneHouseHoldService = luceneHouseHoldService;
	}
	
	@RequestMapping(method = GET, value="/full-text-households")
    @ResponseBody
	public ResponseEntity<HHRegisterDTO> getFullTextHouseHolds(@RequestParam MultiValueMap<String, String> queryParameters) throws JsonParseException, JsonMappingException, IOException
	{
		 HHRegisterDTO  hhRegisterDTO  = luceneHouseHoldService.findLuceneResult(queryParameters);
		 return new ResponseEntity<>(hhRegisterDTO, HttpStatus.OK);
	}
	
	/**
	 * 
	 * 
	 * */
	@RequestMapping(headers = { "Accept=application/json" }, method =POST, value = "/add-update-data-to-existing-field")
	public ResponseEntity<String> addUpdateDataToExistingField(@RequestBody FieldValue fieldValue) {
		
		System.out.println(fieldValue.toString());
		return new ResponseEntity<>("OKkk", HttpStatus.OK);
	}
}
