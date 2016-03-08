package org.opensrp.dynamicreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DynamicReportController {
	
	@Autowired
	DynamicReportService service;
	
	 @RequestMapping(method = RequestMethod.GET, value = "/dynamicreport")
	 @ResponseBody
	 public void getReport(@RequestParam("reportname") String name, @RequestParam("type") String type){
		 
		
			DynamicReport report= service.findReport(name);
			
		 if(type.equalsIgnoreCase("json")){
			service.generateReport(report); 
		 }
		 else {
			 
			 
		 }
		 
		 
	 }

	
	
}
