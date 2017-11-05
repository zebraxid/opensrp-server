package org.opensrp.web.controller;

import com.google.gson.Gson;
import org.json.JSONException;
import org.opensrp.connector.openmrs.service.OpenmrsReportingService;
import org.opensrp.register.mcare.mapper.ELCORegisterMapper;
import org.opensrp.register.mcare.mapper.HHRegisterMapper;
import org.opensrp.register.mcare.report.mis1.Filter;
import org.opensrp.register.mcare.report.mis1.MIS1Report;
import org.opensrp.register.mcare.report.mis1.MIS1ReportGenerator;
import org.opensrp.register.mcare.repository.AllMembers;
import org.opensrp.register.mcare.service.reporting.HHReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReportController {

	private OpenmrsReportingService reportService;
	private HHReportService hhReportService;
	private ELCORegisterMapper ecRegisterMapper;
	private HHRegisterMapper hhRegisterMapper;
	private AllMembers allMembers;
	@Autowired
	private MIS1ReportGenerator mis1ReportGenerator;

	@Autowired
	public ReportController(
			OpenmrsReportingService reportService,
			HHReportService hhReportService,
			ELCORegisterMapper ecRegisterMapper,
			HHRegisterMapper hhRegisterMapper, AllMembers allMembers) {
		this.reportService = reportService;
		this.hhReportService = hhReportService;
		this.ecRegisterMapper = ecRegisterMapper;
		this.hhRegisterMapper = hhRegisterMapper;
		this.allMembers = allMembers;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/report/report-definitions")
	public ResponseEntity<String> reportDefinitions() throws JSONException {
		return new ResponseEntity<>(new Gson().toJson(reportService.getReportDefinitions()), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/report/report-data")
	public ResponseEntity<String> reportDefinitions(@RequestParam("uuid") String uuid) throws JSONException {
		return new ResponseEntity<>("here", HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/report/mis1")
		public ResponseEntity<String> getMis1Report(@RequestParam(value = "district") String district, @RequestParam(value = "upazilla", required = false) String upazilla, @RequestParam(value = "union", required = false) String union, @RequestParam(value = "ward", required = false) String ward, @RequestParam(value = "unit", required = false) String unit, @RequestParam(value = "worker", required = false) String worker, @RequestParam(value = "year") int year, @RequestParam(value = "month") int month) {
		Filter filter = new Filter(district, upazilla, union, ward, unit, worker , year, month);
		if(!filter.validate()) {
			System.out.println("validation failed");
			return new ResponseEntity<>(new Gson().toJson("Invalid parameters").toString(), HttpStatus.BAD_REQUEST);
		}
		MIS1Report mis1Report = mis1ReportGenerator.getReportBasedOn(filter);
		return new ResponseEntity<>(new Gson().toJson(mis1Report).toString(), HttpStatus.OK);
	}

}
