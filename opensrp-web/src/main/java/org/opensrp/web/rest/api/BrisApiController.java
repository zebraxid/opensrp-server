package org.opensrp.web.rest.api;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.json.JSONException;
import org.opensrp.dto.PrivilegeDTO;
import org.opensrp.rest.api.service.CrvsApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BrisApiController {

	private CrvsApiService crvsApiService;
	private static Logger logger = LoggerFactory.getLogger(BrisApiController.class);
	@Autowired
    public void setCrvsApiService(CrvsApiService crvsApiService) {
    	this.crvsApiService = crvsApiService;
    }


	@RequestMapping(method = GET, value = "api/crvsuuid")
	@ResponseBody
	public String crvsUuid(@RequestParam String birsEventId, @RequestParam String birthRegistrationId) throws JSONException {
		logger.info("requeset reached with - " + birthRegistrationId );
		return crvsApiService.getEntityId(birsEventId, birthRegistrationId);
		
	}
	
}
