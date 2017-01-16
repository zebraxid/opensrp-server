package org.opensrp.register.mcare.service;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.opensrp.register.mcare.domain.Exports;
import org.opensrp.register.mcare.repository.AllExports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DataExportService{
	
	@Autowired
	private AllExports allExports;
	private String multimediaDirPath;
	public DataExportService(){
		
	}
	@Autowired
	public DataExportService(@Value("#{opensrp['multimedia.directory.name']}") String multimediaDirName){
		this.multimediaDirPath = multimediaDirName;
	}
	
	public String exports(HttpServletResponse response,String formName,String start_date,String end_date,String user) {		
		
		String reportName = formName+"_" + System.currentTimeMillis() + ".csv";
		Exports export = new Exports();
		export.setFormName(formName);
		export.setUser(user);
		Date date = new Date();
		String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
		export.setCreated_date(modifiedDate);;
		export.setReportName(reportName);
		
		export.setTimeStamp(System.currentTimeMillis());
		allExports.add(export);
		createCSV( response,formName,start_date,end_date,reportName);
		
		return reportName;
	}
	public List<Exports> getExportsByUser(String user){
		return allExports.getExportsByUser(user);
	}
	public void createCSV(HttpServletResponse response,String formName,String start_date,String end_date,String reportName){
		System.out.println("Path:..............:"+multimediaDirPath);
		response.setContentType("text/csv");		
		response.setHeader("Content-disposition",
				"attachment; " + "filename=" + reportName);
		
		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath +"/export/" + reportName);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
