package org.opensrp.web.controller;

import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.opensrp.register.mcare.service.DataExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
public class DataExportController {
	@Autowired
	private DataExportService dataExportService;
	private String multimediaDirPath;
	
	@Autowired
	DataExportController(@Value("#{opensrp['multimedia.directory.name']}") String multimediaDirName){
		this.multimediaDirPath = multimediaDirName;
	}
	
	DataExportController(){
		
	}
	@RequestMapping(value="/export")
    @ResponseBody
	public ResponseEntity<String> getFullTextHouseHolds(@RequestParam String formName,@RequestParam String start_date,@RequestParam String end_date,@RequestParam String user,HttpServletResponse response) 
	{
		System.out.println("formName: "+formName);
		dataExportService.exports(response,formName,start_date,end_date,user);		
		return  new ResponseEntity<>(new Gson().toJson(dataExportService.getExportsByUser(user)), HttpStatus.OK);
	}
	@RequestMapping(value = "/downloadCSV")
	public void downloadCSV(@RequestParam String formName,HttpServletResponse response) throws IOException {		
		System.out.println("Path:..............:"+multimediaDirPath);
		System.out.println("Path:..............:"+formName);
		response.setContentType("text/csv");
		String reportName = "Report_" + System.currentTimeMillis() + ".csv";
		response.setHeader("Content-disposition",
				"attachment; " + "filename=" + reportName);
		
		FileWriter writer = new FileWriter(multimediaDirPath +"/public/" + reportName);
		writer.append("Agent ID");
		writer.append(',');
		writer.append("Date");
		writer.append('\n');
		
		writer.append("One");
		writer.append(',');
		writer.append("Two");
		writer.append('\n');
		
		writer.flush();
		writer.close();
		
		
 
	}
 
}
