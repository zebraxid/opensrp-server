package org.opensrp.web.rest;

import static org.opensrp.web.rest.RestUtils.getStringFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.opensrp.common.AllConstants.BaseEntity;
import org.opensrp.domain.viewconfiguration.BaseConfiguration;
import org.opensrp.domain.viewconfiguration.TestResultsConfiguration;
import org.opensrp.domain.viewconfiguration.ViewConfiguration;
import org.opensrp.repository.ViewConfigurationRepository;
import org.opensrp.service.ViewConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/rest/viewconfiguration")
public class ViewConfigurationResource {
	
	private ViewConfigurationService viewConfigurationService;
	
	@Autowired
	public void setViewConfigurationService(ViewConfigurationService viewConfigurationService) {
		this.viewConfigurationService = viewConfigurationService;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/sync")
	@ResponseBody
	public List<ViewConfiguration> findViewConfigurationsByVersion(HttpServletRequest request) {
		String serverVersion = getStringFilter(BaseEntity.SERVER_VERSIOIN, request);
		Long lastSyncedServerVersion = null;
		if (serverVersion != null) {
			lastSyncedServerVersion = Long.valueOf(serverVersion) + 1;
		}
		return viewConfigurationService.findViewConfigurationsByVersion(lastSyncedServerVersion);
	}
	
	@RequestMapping(value="/getAll", method=RequestMethod.GET)
	@ResponseBody
	public List<ViewConfiguration> findAllViewConfiguration(HttpServletRequest request){
		return viewConfigurationService.findAllViewConfigurations();		
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public List<ViewConfiguration> save(@RequestBody ViewConfiguration request){
		viewConfigurationService.update(request);
		List<ViewConfiguration> listVC = viewConfigurationService.findAllViewConfigurations();
		return listVC;
//		ResponseEntity<T>
	}
}
