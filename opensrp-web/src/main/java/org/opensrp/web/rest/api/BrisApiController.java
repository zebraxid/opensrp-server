package org.opensrp.web.rest.api;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.dto.PrivilegeDTO;
import org.opensrp.rest.api.service.CrvsApiService;
import org.opensrp.rest.api.service.CrvsEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
public class BrisApiController {

	private CrvsApiService crvsApiService;
	private static Logger logger = LoggerFactory.getLogger(BrisApiController.class);
	@Autowired
    public void setCrvsApiService(CrvsApiService crvsApiService) {
    	this.crvsApiService = crvsApiService;
    }


	@RequestMapping(method = GET, value = "api/opensrp/assignbrn")
	@ResponseBody
	public  ResponseEntity<String> assignBrn(@RequestParam String birsEventId, @RequestParam String birthRegistrationId) throws JSONException {
		JSONObject per = new JSONObject();
		try{
			CrvsEventMapper crvsMapper = new CrvsEventMapper();
			crvsMapper.setEventId(crvsApiService.getEntityId(birsEventId, birthRegistrationId));
			
			per.put("entityId", crvsApiService.getEntityId(birsEventId, birthRegistrationId));
			per.put("status", "success");
			return new ResponseEntity<>(per.toString().replace("\\\\",""),HttpStatus.OK);
		}catch(Exception e){
			per.put("entityId", " ");
			per.put("status", "failed");
			per.put("message", e.getMessage());
			return new ResponseEntity<>(per.toString().replace("\\\\",""),HttpStatus.FAILED_DEPENDENCY);
		}
		
	}
	
}
