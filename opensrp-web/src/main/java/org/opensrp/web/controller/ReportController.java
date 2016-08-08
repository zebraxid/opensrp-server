package org.opensrp.web.controller;

import org.json.JSONException;
import org.opensrp.connector.openmrs.service.OpenmrsReportingService;
import org.opensrp.register.mcare.mapper.ChildRegisterMapper;
import org.opensrp.register.mcare.service.reporting.ChildReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

@Controller
public class ReportController {
	
	private OpenmrsReportingService reportService;
	
	private ChildReportService childReportService;
	
	private ChildRegisterMapper childRegisterMapper;
	
	@Autowired
	public ReportController(OpenmrsReportingService reportService, ChildReportService childReportService,
	    ChildRegisterMapper childRegisterMapper) {
		this.reportService = reportService;
		this.childReportService = childReportService;
		this.childRegisterMapper = childRegisterMapper;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/report/report-definitions")
	public ResponseEntity<String> reportDefinitions() throws JSONException {
		return new ResponseEntity<>(new Gson().toJson(reportService.getReportDefinitions()), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/report/report-data")
	public ResponseEntity<String> reportDefinitions(@RequestParam("uuid") String uuid) throws JSONException {
		return new ResponseEntity<>(new Gson().toJson(reportService.getReportData(uuid)), HttpStatus.OK);
	}
	
	/*@RequestMapping(method = RequestMethod.GET, value = "/report/report-child-7-days")
	public ResponseEntity<ChildRegisterDTO> reportChildForPrev7Days(@RequestParam("provider-id") String providerId) {
		ChildRegistration childRegister = childReportService.getChildDataVisitedPrev7Days(providerId);
		return new ResponseEntity<>(childRegisterMapper.mapToDTO(childRegister), HttpStatus.OK);
	}*/
	/*
	    @RequestMapping(method = RequestMethod.GET, value = "/report/actions")
	    @ResponseBody
	    public List<Action> getNewActionForANM(@RequestParam("anmIdentifier") String anmIdentifier, @RequestParam("timeStamp") Long timeStamp){
	        List<org.opensrp.scheduler.Action> actions = actionService.getNewAlertsForANM(anmIdentifier, timeStamp);
	        return with(actions).convert(new Converter<org.opensrp.scheduler.Action, Action>() {
	            @Override
	            public Action convert(org.opensrp.scheduler.Action action) {
	                return ActionConvertor.from(action);
	            }
	        });
	    }*/
	
}
