package org.opensrp.register.mcare.service;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.ektorp.ViewResult;
import org.opensrp.register.mcare.domain.Exports;
import org.opensrp.register.mcare.repository.AllExports;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DataExportService{
	
	@Autowired
	private AllExports allExports;
	private AllHouseHolds allHouseHolds;	
	private String multimediaDirPath;
	public DataExportService(){
		
	}
	@Autowired
	public DataExportService(@Value("#{opensrp['multimedia.directory.name']}") String multimediaDirName, AllHouseHolds allHouseHolds){
		this.multimediaDirPath = multimediaDirName;
		this.allHouseHolds = allHouseHolds;
		
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
		
		if(formName.equalsIgnoreCase("NEW_HOUSEHOLD_FORM"))
			createHHCSV( response,formName,start_date,end_date,reportName);
		else if(formName.equalsIgnoreCase("CENSUS_FORM"))
			createCensusCSV( response,formName,start_date,end_date,reportName);
		
		else
			;
		allExports.add(export);
		
		return reportName;
	}
	
	public List<Exports> getExportsByUser(String user){
		return allExports.getExportsByUser(user);
	}
	
	public void createHHCSV(HttpServletResponse response,String formName,String start_date,String end_date,String reportName){
		System.out.println("Path:..............:"+multimediaDirPath);
		response.setContentType("text/csv");		
		response.setHeader("Content-disposition",
				"attachment; " + "filename=" + reportName);
	
		ViewResult hhs=allHouseHolds.exportData("HouseHold",convertDateToTimestampMills(start_date), convertDateToTimestampMills(end_date));
		
		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath +"/export/" + reportName);			
			 writer.append("FWA WORKER ID"); 
			 writer.append(',');
		     writer.append("today_newhh_FW"); 
			 writer.append(',');
		     writer.append("start_newhh_FW"); 
			 writer.append(',');
		     writer.append("end_newhh_FW"); 
			 writer.append(',');
		     writer.append("FWNHREGDATE"); 
			 writer.append(',');
		     writer.append("FWGOBHHID"); 
			 writer.append(',');
		     writer.append("FWJIVHHID"); 
			 writer.append(',');
		     writer.append("FWUNION"); 
			 writer.append(',');
		     writer.append("FWWARD"); 
			 writer.append(',');
		     writer.append("FWSUBUNIT"); 
			 writer.append(','); //10
		     writer.append("FWMAUZA_PARA"); 
			 writer.append(',');
		     writer.append("FWHOHFNAME"); 
			 writer.append(',');
		     writer.append("FWHOHBIRTHDATE"); 
			 writer.append(',');
		     writer.append("FWHOHGENDER"); 
			 writer.append(',');
		     writer.append("FWNHHMBRNUMB"); 
			 writer.append(',');
		     writer.append("FWNHHMWRA"); 
			 writer.append(',');
		     writer.append("REGDATE"); 
			 writer.append(',');
		     writer.append("FWWOMFNAME"); 
			 writer.append(',');
		     writer.append("FWBIRTHDATE"); 
			 writer.append(',');
		     writer.append("FWWOMAGE"); 
			 writer.append(','); //20
		     writer.append("FWCWOMSTRMEN"); 
			 writer.append(',');
		     writer.append("FWCWOMHUSLIV"); 
			 writer.append(',');
		     writer.append("FWCWOMHUSALV"); 
			 writer.append(',');
		     writer.append("FWCWOMHUSSTR"); 
			 writer.append(',');
		     writer.append("FWELIGIBLE"); 
			 writer.append(',');
		     writer.append("FWWOMANYID"); 
			 writer.append(',');
		     writer.append("FWWOMNID"); 
			 writer.append(',');
		     writer.append("FWWOMBID"); 
			 writer.append(',');
		     writer.append("FWHUSNAME");  
			 writer.append(','); 
		     writer.append("Latitude"); 
			 writer.append(','); //30
		     writer.append("Longitude"); 
		     writer.append(',');
		     writer.append("ELCO");
			 writer.append(',');
		     writer.append("RECEIVED TIME AT SERVER"); 
			 writer.append(',');
		     writer.append("FD WORKER ID"); 
			 writer.append(',');       
		     writer.append("INSTANCE ID"); 
			 writer.append(',');
		     writer.append("ENTITY ID"); 
		     writer.append('\n'); //36
		     
			int count =hhs.getSize(); 
			System.out.println("count: "+count);
		     for (int i = 0; i <count; i++) {
					if(hhs.getRows().get(i).getValue().equalsIgnoreCase("")){
						System.err.println("Error.........................");
					}
					String[] s=hhs.getRows().get(i).getValue().split(",");
					int elco = 0;
					
					String temp = s[0].replace("\"", "").replace("[", "");
					//System.out.println("Loop val: "+ temp);
					
					if(temp != null && !temp.isEmpty() && !temp.equalsIgnoreCase(""))
						elco = Integer.parseInt(temp);
					//String jsonArray = vr.getRows().get(i).getValue().trim();
				    //System.out.println(hhs.getRows().get(i).getValue());
					for(int y=1;y<=16;y++){
						if(s[y] != null && !s[y].isEmpty() && !s[y].equalsIgnoreCase("")){
							writer.append(s[y]);
							writer.append(',');
						}
						else {
							writer.append("");
							writer.append(',');
						}
					}
					if(elco>0){		
						for(int y=21;y<=30;y++){
							if(s[y] != null && !s[y].isEmpty() && !s[y].equalsIgnoreCase("")){
								writer.append(s[y]);
								writer.append(',');
							}
							else {
								writer.append("");
								writer.append(',');
							}
						}
						writer.append(' '+String.valueOf(s[31]));
						writer.append(',');
						writer.append(' '+String.valueOf(s[32]));
						writer.append(',');
						writer.append(s[33]);						
						
						String gps = s[34];
						if(gps != null && !gps.isEmpty() && !gps.equalsIgnoreCase("")){
							String[] location = gps.split(" ");   							
				            if (location.length > 1 ) {
				            	System.out.println("Location: "+ location[0].replace("\"", "") + " " + location[1]);
				            	writer.append(',');
								writer.append(location[0].replace("\"", ""));								
								writer.append(',');
								writer.append(location[1].replace("\"", ""));
				            }
				            else if (location.length > 0 ) {
				            	writer.append(',');
								writer.append(location[0].replace("\"", ""));
								writer.append(',');
								writer.append("");
				            }
				            else{
				            	writer.append(',');
								writer.append("");
								writer.append(',');
								writer.append("");
				            }
						}else{
			            	writer.append(',');
							writer.append("");
							writer.append(',');
							writer.append("");
			            }            
					}
					else{		
						for(int y=1;y<=14;y++){
							writer.append(',');
							writer.append("");
						}           
					}
					writer.append(',');
					writer.append(String.valueOf(elco));
					writer.append(',');					
					writer.append(s[17]);
					writer.append(',');					
					writer.append(s[18]);
					writer.append(',');					
					writer.append(s[19]);
					writer.append(',');
					writer.append(s[20]);
					writer.append('\n');
				}
				
				writer.flush();
				writer.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{				
				System.out.println("Loop time end:"+ System.currentTimeMillis());
			}		
	}
	
	public void createCensusCSV(HttpServletResponse response,String formName,String start_date,String end_date,String reportName){
		System.out.println("Path:..............:"+multimediaDirPath);
		response.setContentType("text/csv");		
		response.setHeader("Content-disposition",
				"attachment; " + "filename=" + reportName);
		
		ViewResult hhs=allHouseHolds.allCensusCreatedBetween2Date("HouseHold",convertDateToTimestampMills(start_date), convertDateToTimestampMills(end_date));
		
		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath +"/export/" + reportName);			
			writer.append("Form Status at Submission");
			writer.append(','); //1
			writer.append("SCHEDULED_DATE_Census_FW");
			writer.append(','); //2
			writer.append("today_census_FW");
			writer.append(','); //3
			writer.append("start_census_FW");
			writer.append(','); //4
			writer.append("end_census_FW");
			writer.append(','); //5
			writer.append("FWCENDATE");
			writer.append(','); //6
			writer.append("FWCENSTAT");
			writer.append(','); //7
			writer.append("FWGOBHHID");
			writer.append(','); //8
			writer.append("FWJIVHHID");
			writer.append(','); //9
			writer.append("FWUNION");
			writer.append(','); //10
			writer.append("FWWARD");
			writer.append(','); //11
			writer.append("FWSUBUNIT");
			writer.append(','); //12
			writer.append("FWMAUZA_PARA");
			writer.append(','); //13
			writer.append("ELCO");
			writer.append(','); //14
			writer.append("existing_ELCO");
			writer.append(','); //15
			writer.append("new_ELCO");
			writer.append(','); //16
			writer.append("CENDATE");
			writer.append(','); //17
			writer.append("FWWOMFNAME");
			writer.append(','); //18
			writer.append("FWBIRTHDATE");
			writer.append(','); //19
			writer.append("FWWOMAGE");
			writer.append(','); //20
			writer.append("FWCWOMSTRMEN");
			writer.append(','); //21
			writer.append("FWCWOMHUSLIV");
			writer.append(','); //22
			writer.append("FWCWOMHUSALV");
			writer.append(','); //23
			writer.append("FWCWOMHUSSTR");
			writer.append(','); //24
			writer.append("FWELIGIBLE");
			writer.append(','); //25
			writer.append("FWWOMANYID");
			writer.append(','); //26
			writer.append("FWWOMNID");
			writer.append(','); //27
			writer.append("FWWOMBID");
			writer.append(','); //28
			writer.append("FWHUSNAME");
			writer.append(','); //29       
			writer.append("RECEIVED TIME AT SERVER");
			writer.append(','); //30
			writer.append("FD WORKER ID");
			writer.append(','); //31
			writer.append("FWA WORKER ID");
			writer.append(','); //32    
			writer.append("INSTANCE ID");
			writer.append(','); //33
			writer.append("ENTITY ID");
			writer.append('\n'); //34
			

			int count =hhs.getSize(); 
			System.out.println("count: "+count);
		     for (int i = 0; i <count; i++) {
					if(hhs.getRows().get(i).getValue().equalsIgnoreCase("")){
						System.err.println("Error.........................");
					}
					String[] s=hhs.getRows().get(i).getValue().split(",");
					for(int y=0;y<s.length;y++){
						//System.out.print(s[y]+" ");
					}
					//System.out.println();
					
					int elco = 0;
					
					String temp = s[0].replace("\"", "").replace("[", "");
					//System.out.println("Loop val: "+ temp);
					
					if(temp != null && !temp.isEmpty() && !temp.equalsIgnoreCase(""))
						elco = Integer.parseInt(temp);
					//String jsonArray = vr.getRows().get(i).getValue().trim();
				    //System.out.println(hhs.getRows().get(i).getValue());					
					writer.append(s[1]);
					writer.append(',');
					writer.append(s[4]);
					writer.append(',');
					writer.append(s[3]);
					writer.append(',');
					writer.append(s[4]);
					writer.append(',');
					writer.append(s[5]);
					writer.append(',');
					
					for(int y=10;y<=30;y++){
						if(s[y] != null && !s[y].isEmpty() && !s[y].equalsIgnoreCase("")){
							writer.append(s[y]);
							writer.append(',');
						}
						else {
							writer.append("");
							writer.append(',');
						}
					}
					
					writer.append(' '+String.valueOf(s[31]));
					writer.append(',');
					writer.append(' '+String.valueOf(s[32]));
					writer.append(',');
					writer.append(s[33]);		
					writer.append(',');
					writer.append(s[6]);
					writer.append(',');					
					writer.append(s[7]);
					writer.append(',');	
					writer.append(s[2]);
					writer.append(',');
					writer.append(s[8]);
					writer.append(',');
					writer.append(s[9]);
					writer.append('\n');
				}
			
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	
	public void deleteExport(String id) {
		Exports export = allExports.getExportsById(id);
		allExports.delete(export);		
	}
	
	private long convertDateToTimestampMills(String date){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");   	
    	Date day= null;
		try {
			day = dateFormat.parse(date);			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return day.getTime();		
	}	

}
