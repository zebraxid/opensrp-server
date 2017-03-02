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
import org.opensrp.register.mcare.repository.AllElcos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DataExportService{
	
	@Autowired
	private AllExports allExports;
	private AllHouseHolds allHouseHolds;
	private AllElcos allElcos;
	private String multimediaDirPath;
	public DataExportService(){
		
	}
	@Autowired
	public DataExportService(@Value("#{opensrp['multimedia.directory.name']}") String multimediaDirName, AllHouseHolds allHouseHolds, AllElcos allElcos){
		this.multimediaDirPath = multimediaDirName;
		this.allHouseHolds = allHouseHolds;
		this.allElcos = allElcos;
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
		else if(formName.equalsIgnoreCase("PSRF_FORM"))
			createPsrfCSV( response,formName,start_date,end_date,reportName);
		else if(formName.equalsIgnoreCase("MIS_CENSUS_FORM"))
			createMisCensusCSV( response,formName,start_date,end_date,reportName);
		else if(formName.equalsIgnoreCase("MIS_ELCO_FORM"))
			createMisElcoCSV( response,formName,start_date,end_date,reportName);
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
	
		ViewResult hhs=allHouseHolds.allHHsCreatedBetween2Date("HouseHold",convertDateToTimestampMills(start_date), convertDateToTimestampMills(end_date),"FWA");
		
		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath +"/export/" + reportName);
			writer.append("Serial No."); 
			writer.append(',');//1 
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
			 writer.append("ELCO");
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
		     
		     writer.append("RECEIVED TIME AT SERVER");
		     writer.append('\n'); //36
		     
			int count =hhs.getSize();
		     for (int i = 0; i <count; i++) {
					if(hhs.getRows().get(i).getValue().equalsIgnoreCase("")){
						System.err.println("Error.........................");
					}
					writer.append(Integer.toString(i+1));
					writer.append(',');
					String[] ConvertRowValueStringToArray=hhs.getRows().get(i).getValue().split(",");
					int elco = 0;
					
					String ElcoCountAsString = ConvertRowValueStringToArray[0].replace("\"", "").replace("[", "");
					
					
					if(ElcoCountAsString != null && !ElcoCountAsString.isEmpty() && !ElcoCountAsString.equalsIgnoreCase(""))
						elco = Integer.parseInt(ElcoCountAsString);
					
					for(int increment=1;increment<=16;increment++){
						if(ConvertRowValueStringToArray[increment] != null && !ConvertRowValueStringToArray[increment].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[increment].equals("\"NaN\"") && !ConvertRowValueStringToArray[increment].equals("\"\"") && !ConvertRowValueStringToArray[increment].isEmpty() && !ConvertRowValueStringToArray[increment].equalsIgnoreCase("")){
							writer.append(ConvertRowValueStringToArray[increment]);
							writer.append(',');
						}
						else {
							writer.append("");
							writer.append(',');
						}
					}
					
					writer.append(Integer.toString(elco));
					writer.append(',');
					/**
					 * Eclo exists condition.
					 * */
					if(elco>0){		
						for(int increment=21;increment<=30;increment++){
							if(ConvertRowValueStringToArray[increment] != null && !ConvertRowValueStringToArray[increment].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[increment].isEmpty() && !ConvertRowValueStringToArray[increment].equalsIgnoreCase("")){
								writer.append(ConvertRowValueStringToArray[increment]);
								writer.append(',');
							}
							else {
								writer.append("");
								writer.append(',');
							}
						}
						
						/**
						 * For NID & BRID
						 * */
						if(ConvertRowValueStringToArray[31] != null && !ConvertRowValueStringToArray[31].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[31].equals("\"\"") && !ConvertRowValueStringToArray[31].isEmpty() && !ConvertRowValueStringToArray[31].equalsIgnoreCase("")){
							writer.append(' '+String.valueOf(ConvertRowValueStringToArray[31].toString()));
							writer.append(',');
						}else{
							writer.append("");
							writer.append(',');
						}
						if(ConvertRowValueStringToArray[32] != null && !ConvertRowValueStringToArray[32].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[32].equals("\"\"") && !ConvertRowValueStringToArray[32].isEmpty() && !ConvertRowValueStringToArray[32].equalsIgnoreCase("")){
							writer.append(' '+String.valueOf(ConvertRowValueStringToArray[32].toString()));
							writer.append(',');
						}else{
							writer.append("");
							writer.append(',');
						}
						/**
						 * Server time
						 * */
						writer.append(ConvertRowValueStringToArray[33]);						
						/**
						 * GPS calculation
						 * */
						String gps = ConvertRowValueStringToArray[34];
						if(gps != null && !gps.isEmpty() && !gps.equalsIgnoreCase("")){
							String[] location = gps.split(" ");   							
				            if (location.length > 1 ) {				            	
				            	writer.append(',');
								writer.append(location[0].replace("\"", ""));								
								writer.append(',');
								writer.append(location[1].replace("\"", ""));
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
					else{ /**
						 * Eclo does not exists condition.
						 * */		
						for(int increment=1;increment<=14;increment++){
							writer.append(',');
							writer.append("");
						}           
					}
					
					writer.append(',');					
					writer.append(ConvertRowValueStringToArray[17]);
					
					writer.append('\n');
				}
				
				writer.flush();
				writer.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{				
				
			}		
	}
	
	public void createCensusCSV(HttpServletResponse response,String formName,String start_date,String end_date,String reportName){
		response.setContentType("text/csv");		
		response.setHeader("Content-disposition",
				"attachment; " + "filename=" + reportName);
		
		ViewResult hhs=allHouseHolds.allCensusCreatedBetween2Date("HouseHold",convertDateToTimestampMills(start_date), convertDateToTimestampMills(end_date),"FWA");
		
		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath +"/export/" + reportName);
			writer.append("Serial No."); 
			writer.append(',');//1 
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
			
			writer.append("FWA WORKER ID");
			
			writer.append('\n'); //34
			

			int count =hhs.getSize(); 			
		     for (int i = 0; i <count; i++) {
					if(hhs.getRows().get(i).getValue().equalsIgnoreCase("")){
						System.err.println("Error.........................");
					}
					writer.append(Integer.toString(i+1));
					writer.append(',');
					String[] ConvertRowValueStringToArray=hhs.getRows().get(i).getValue().split(",");					
					int elco = 0;					
					String temp = ConvertRowValueStringToArray[0].replace("\"", "").replace("[", "");					
					if(temp != null && !temp.isEmpty() && !temp.equalsIgnoreCase(""))
						elco = Integer.parseInt(temp);
									
					writer.append(ConvertRowValueStringToArray[1]);//1
					writer.append(',');
					writer.append(ConvertRowValueStringToArray[4]);//2
					writer.append(',');
					writer.append(ConvertRowValueStringToArray[3]);//3
					writer.append(',');
					writer.append(ConvertRowValueStringToArray[4]);//4
					writer.append(',');
					writer.append(ConvertRowValueStringToArray[5]);//5
					writer.append(',');
					
					for(int increment=10;increment<=30;increment++){
						if(ConvertRowValueStringToArray[increment] != null && !ConvertRowValueStringToArray[increment].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[increment].equals("\"NaN\"") && !ConvertRowValueStringToArray[increment].equals("\"\"") && !ConvertRowValueStringToArray[increment].isEmpty() && !ConvertRowValueStringToArray[increment].equalsIgnoreCase("")){
							writer.append(ConvertRowValueStringToArray[increment]);
							writer.append(',');							
						}
						else {							
							writer.append("");
							writer.append(',');
						}
					}	
					
					/**
					 * For NID & BRID
					 * */
					
					if(ConvertRowValueStringToArray[31] != null && !ConvertRowValueStringToArray[31].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[31].equals("\"\"") && !ConvertRowValueStringToArray[31].isEmpty() && !ConvertRowValueStringToArray[31].equalsIgnoreCase("")){
						writer.append(' '+String.valueOf(ConvertRowValueStringToArray[31].toString()));
						writer.append(',');
					}else{
						writer.append("");
						writer.append(',');
					}
					if(ConvertRowValueStringToArray[32] != null && !ConvertRowValueStringToArray[32].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[32].equals("\"\"") && !ConvertRowValueStringToArray[32].isEmpty() && !ConvertRowValueStringToArray[32].equalsIgnoreCase("")){
						writer.append(' '+String.valueOf(ConvertRowValueStringToArray[32]).toString());
						writer.append(',');
					}else{
						writer.append("");
						writer.append(',');
					}
					if(ConvertRowValueStringToArray[33] != null && !ConvertRowValueStringToArray[33].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[33].equals("\"\"") && !ConvertRowValueStringToArray[33].isEmpty() && !ConvertRowValueStringToArray[33].equalsIgnoreCase("")){
						writer.append(((ConvertRowValueStringToArray[33].equalsIgnoreCase("null")) ? " " : ConvertRowValueStringToArray[33]));		
						writer.append(',');
					}else{
						writer.append("");
						writer.append(',');
					}
					writer.append(ConvertRowValueStringToArray[6]);
					writer.append(',');
					writer.append(ConvertRowValueStringToArray[2]);
					
					writer.append('\n');
				}
			
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void createPsrfCSV(HttpServletResponse response,String formName,String start_date,String end_date,String reportName){
		
		response.setContentType("text/csv");		
		response.setHeader("Content-disposition",
				"attachment; " + "filename=" + reportName);

		ViewResult ecs=allElcos.allElcosCreatedBetween2Date("Elco",convertDateToTimestampMills(start_date), convertDateToTimestampMills(end_date));
		
		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath +"/export/" + reportName);
			writer.append("Serial No."); 
			writer.append(',');//1 
			writer.append("FWGOBHHID"); 
			writer.append(',');//4
			writer.append("FWJIVHHID"); 
			writer.append(',');//5
			writer.append("FWUNION"); 
			writer.append(',');//6
			writer.append("FWWARD"); 
			writer.append(',');//7
			writer.append("FWSUBUNIT"); 
			writer.append(',');//8
			writer.append("FWMAUZA_PARA"); 
			writer.append(',');//9
			writer.append("wom_nid"); 
			writer.append(',');//10
			writer.append("wob_bid"); 
			writer.append(',');//11
			writer.append("wom_age"); 
			writer.append(',');//12
			writer.append("first_name"); 
			writer.append(',');//13
			writer.append("husname"); 			 
			writer.append(',');//15
			writer.append("FWA Worker ID"); 
			writer.append(','); //16
			writer.append("start"); 
			writer.append(',');//1
			writer.append("end"); 
			writer.append(',');//2
			writer.append("today"); 
			writer.append(',');//3
			writer.append("Form Status at Submission"); 
			writer.append(',');//17
			writer.append("SCHEDULED_DATE_PSRF_FWA"); 
			writer.append(',');//18
			writer.append("FWPSRDATE"); 
			writer.append(',');//19
			writer.append("FWPSRSTS"); 
			writer.append(',');//20
			writer.append("FWPSRLMP"); 
			writer.append(',');//21
			writer.append("FWPSRPREGSTS"); 
			writer.append(',');//22			
			writer.append("FWPSRHUSPREGWTD"); 
			writer.append(',');//24
			writer.append("FWPSREVRPREG"); 
			writer.append(',');//25
			writer.append("FWPSRTOTBIRTH"); 
			writer.append(',');//26
			writer.append("FWPSRNBDTH"); 
			writer.append(',');//27
			writer.append("FWPSRPRSB"); 
			writer.append(',');//28
			writer.append("FWPSRPRMC"); 
			writer.append(',');//29
			writer.append("FWPSRPREGTWYRS"); 
			writer.append(',');//30        
			writer.append("FWPSRPRVPREGCOMP"); 
			writer.append(',');//31
			writer.append("FWPSRPRCHECKS"); 
			writer.append(',');//32
			writer.append("FWPSRANM"); 
			writer.append(',');//33
			writer.append("FWPSRHBP"); 
			writer.append(',');//34
			writer.append("FWPSRDBT"); 
			writer.append(',');//35
			writer.append("FWPSRTHY"); 
			writer.append(',');//36
			writer.append("FWPSRVDGMEM"); 
			writer.append(',');//37
			writer.append("FWPSRWOMEDU"); 
			writer.append(',');//38
			writer.append("FWPSRHHLAT"); 
			writer.append(',');//39
			writer.append("FWPSRHHRICE"); 
			writer.append(',');//40
			writer.append("FWPSRPHONE"); 
			writer.append(',');//41
			writer.append("FWPSRPHONENUM"); 
			writer.append(',');//42
			writer.append("FWPSRMUAC"); 
			writer.append(',');//43
			writer.append("FWVG"); 
			writer.append(',');//44
			writer.append("FWHRP"); 
			writer.append(','); //45       
			writer.append("FWHR_PSR"); 
			writer.append(',');//46
			writer.append("FWFLAGVALUE"); 
			writer.append(',');//47
			writer.append("FWSORTVALUE"); 
			writer.append(',');//48
			writer.append("FWPSRPREGWTD"); 
			writer.append(',');//23
			writer.append("RECEIVED_TIME_AT_SERVER");			
			writer.append('\n'); //51
			
			int count =ecs.getSize();			
		     for (int i = 0; i <count; i++) {
					if(ecs.getRows().get(i).getValue().equalsIgnoreCase("")){
						System.err.println("Error.........................");
					}
					writer.append(Integer.toString(i+1));
					writer.append(',');
					String[] ConvertRowValueStringToArray=ecs.getRows().get(i).getValue().split(",");
					
					for(int increment=1;increment<=6;increment++){
						if(ConvertRowValueStringToArray[increment] != null && !ConvertRowValueStringToArray[increment].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[increment].equals("\"NaN\"") && !ConvertRowValueStringToArray[increment].equals("\"\"") && !ConvertRowValueStringToArray[increment].isEmpty() && !ConvertRowValueStringToArray[increment].equalsIgnoreCase("")){
							writer.append(ConvertRowValueStringToArray[increment]);
							writer.append(',');
						}
						else {
							writer.append("");
							writer.append(',');
						}
					}
					
					/**
					 * For NID & BRID
					 * */
					if(ConvertRowValueStringToArray[7] != null && !ConvertRowValueStringToArray[7].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[7].equals("\"\"") && !ConvertRowValueStringToArray[7].isEmpty() && !ConvertRowValueStringToArray[7].equalsIgnoreCase("")){
						writer.append(' '+String.valueOf(ConvertRowValueStringToArray[7].toString()));
						writer.append(',');
					}else{
						writer.append("");
						writer.append(',');
					}
					if(ConvertRowValueStringToArray[8] != null && !ConvertRowValueStringToArray[8].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[8].equals("\"\"") && !ConvertRowValueStringToArray[8].isEmpty() && !ConvertRowValueStringToArray[8].equalsIgnoreCase("")){
						writer.append(' '+String.valueOf(ConvertRowValueStringToArray[8]).toString());
						writer.append(',');
					}else{
						writer.append("");
						writer.append(',');
					}
					
					
					for(int increment=9;increment<=11;increment++){
						if(ConvertRowValueStringToArray[increment] != null && !ConvertRowValueStringToArray[increment].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[increment].equals("\"NaN\"") && !ConvertRowValueStringToArray[increment].equals("\"\"") && !ConvertRowValueStringToArray[increment].isEmpty() && !ConvertRowValueStringToArray[increment].equalsIgnoreCase("")){
							writer.append(ConvertRowValueStringToArray[increment]);
							writer.append(',');
						}
						else {
							writer.append("");
							writer.append(',');
						}
					}
					
					
					//12 is external user id which currently not showing....
					if(ConvertRowValueStringToArray[13] != null && !ConvertRowValueStringToArray[13].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[13].equals("\"NaN\"") && !ConvertRowValueStringToArray[13].equals("\"\"") && !ConvertRowValueStringToArray[13].isEmpty() && !ConvertRowValueStringToArray[13].equalsIgnoreCase("")){
						writer.append(ConvertRowValueStringToArray[13]);
						writer.append(',');
					}
					else {
						writer.append("");
						writer.append(',');
					}
					
					if(ConvertRowValueStringToArray.length>30){
						for(int increment=14;increment<=49;increment++){
							System.out.println(increment+" : "+ ConvertRowValueStringToArray[increment]);
							if(ConvertRowValueStringToArray[increment] != null && !ConvertRowValueStringToArray[increment].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[increment].equals("\"NaN\"") && !ConvertRowValueStringToArray[increment].equals("\"\"") && !ConvertRowValueStringToArray[increment].isEmpty() && !ConvertRowValueStringToArray[increment].equalsIgnoreCase("")){
								writer.append(ConvertRowValueStringToArray[increment]);
								writer.append(',');
							}
							else {
								writer.append("");
								writer.append(',');
							}
						}
						writer.append("");
					}
					else {						
						for(int increment=1;increment<=32;increment++){
								writer.append("");
								writer.append(',');
						}
						writer.append(ConvertRowValueStringToArray[14]);
						writer.append(',');
						writer.append(ConvertRowValueStringToArray[15]);
						
					}
					writer.append('\n');
		     }
				
				writer.flush();
				writer.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{				
				
			}		
	}
	
	public void createMisCensusCSV(HttpServletResponse response,String formName,String start_date,String end_date,String reportName){
		response.setContentType("text/csv");		
		response.setHeader("Content-disposition",
				"attachment; " + "filename=" + reportName);

		ViewResult ecs=allElcos.allMisCensusCreatedBetween2Date("Elco",convertDateToTimestampMills(start_date), convertDateToTimestampMills(end_date));
		
		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath +"/export/" + reportName);
			writer.append("Serial No."); 
			writer.append(',');//1 
			writer.append("FWA Worker ID"); 
			writer.append(',');//1     
			writer.append("existing_location"); 
			writer.append(',');//2
			writer.append("today"); 
			writer.append(',');//3 
			writer.append("FWGOBHHID"); 
			writer.append(',');//4
			writer.append("FWJIVHHID"); 
			writer.append(',');//5
			writer.append("FWUNION"); 
			writer.append(',');//6
			writer.append("FWWARD"); 
			writer.append(',');//7
			writer.append("FWSUBUNIT"); 
			writer.append(',');//8
			writer.append("FWMAUZA_PARA"); 
			writer.append(',');//9
			writer.append("FWWOMNID"); 
			writer.append(',');//10
			writer.append("FWWOMBID"); 
			writer.append(',');//11
			writer.append("wom_age"); 
			writer.append(',');//12
			writer.append("first_name"); 
			writer.append(',');//13
			writer.append("husname");			
			writer.append(',');//15
			writer.append("FWA Worker ID"); 
			writer.append(','); //16
			writer.append("Form Status at Submission"); 
			writer.append(',');//17
			writer.append("SCHEDULED_DATE"); 
			writer.append(',');//18
			writer.append("start"); 
			writer.append(',');//19 
			writer.append("end"); 
			writer.append(',');//20
			writer.append("FWMISCENSUSDATE"); 
			writer.append(',');//21
			writer.append("FWCOUPLENUM"); 
			writer.append(',');//22
			writer.append("FWTETSTAT"); 
			writer.append(',');//23
			writer.append("FWMARRYDATE"); 
			writer.append(',');//24
			writer.append("FWCHILDALIVEB"); 
			writer.append(',');//26
			writer.append("FWCHILDALIVEG"); 
			writer.append(',');//27
			writer.append("RECEIVED_TIME_AT_SERVER"); 
			
			writer.append('\n'); //30			
			 int count =ecs.getSize();				
			     for (int i = 0; i <count; i++) {
						if(ecs.getRows().get(i).getValue().equalsIgnoreCase("")){
							System.err.println("Error.........................");
						}
						writer.append(Integer.toString(i+1));
						writer.append(',');
						String[] ConvertRowValueStringToArray=ecs.getRows().get(i).getValue().split(",");
						for(int counter=1;counter<=9;counter++){
							if(ConvertRowValueStringToArray[counter] != null && !ConvertRowValueStringToArray[counter].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[counter].equals("\"NaN\"") && !ConvertRowValueStringToArray[counter].equals("\"\"") && !ConvertRowValueStringToArray[counter].isEmpty() && !ConvertRowValueStringToArray[counter].equalsIgnoreCase("")){
								writer.append(ConvertRowValueStringToArray[counter]);
								writer.append(',');
							}
							else {
								writer.append("");
								writer.append(',');
							}
						}
						
						/**
						 * For NID & BRID
						 * */
						if(ConvertRowValueStringToArray[10] != null && !ConvertRowValueStringToArray[10].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[10].equals("\"\"") && !ConvertRowValueStringToArray[10].isEmpty() && !ConvertRowValueStringToArray[10].equalsIgnoreCase("")){
							writer.append(' '+String.valueOf(ConvertRowValueStringToArray[10].toString()));
							writer.append(',');
						}else{
							writer.append("");
							writer.append(',');
						}
						if(ConvertRowValueStringToArray[11] != null && !ConvertRowValueStringToArray[11].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[11].equals("\"\"") && !ConvertRowValueStringToArray[11].isEmpty() && !ConvertRowValueStringToArray[11].equalsIgnoreCase("")){
							writer.append(' '+String.valueOf(ConvertRowValueStringToArray[11]).toString());
							writer.append(',');
						}else{
							writer.append("");
							writer.append(',');
						}
						
						for(int counter=12;counter<=14;counter++){
							if(ConvertRowValueStringToArray[counter] != null && !ConvertRowValueStringToArray[counter].equals("\"NaN\"") && !ConvertRowValueStringToArray[counter].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[counter].equals("\"\"") && !ConvertRowValueStringToArray[counter].isEmpty() && !ConvertRowValueStringToArray[counter].equalsIgnoreCase("")){
								writer.append(ConvertRowValueStringToArray[counter]);
								writer.append(',');
							}
							else {
								writer.append("");
								writer.append(',');
							}
						}	
						
						for(int counter=16;counter<=26;counter++){
							if(ConvertRowValueStringToArray[counter] != null && !ConvertRowValueStringToArray[counter].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[counter].equals("\"NaN\"") && !ConvertRowValueStringToArray[counter].equals("\"\"") && !ConvertRowValueStringToArray[counter].isEmpty() && !ConvertRowValueStringToArray[counter].equalsIgnoreCase("")){
								writer.append(ConvertRowValueStringToArray[counter]);
								writer.append(',');
							}
							else {
								writer.append("");
								writer.append(',');
							}
						}					
						
						writer.append(ConvertRowValueStringToArray[27]);
						writer.append('\n');
			     }					
					writer.flush();
					writer.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{				
					
				}		
		}
	
	public void createMisElcoCSV(HttpServletResponse response,String formName,String start_date,String end_date,String reportName){
		
		response.setContentType("text/csv");		
		response.setHeader("Content-disposition",
				"attachment; " + "filename=" + reportName);

		ViewResult ecs=allElcos.allMisElcoCreatedBetween2Date("Elco",convertDateToTimestampMills(start_date), convertDateToTimestampMills(end_date));
		
		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath +"/export/" + reportName);			
			writer.append("Serial No."); 
			writer.append(',');//1 
			writer.append("FWA Worker ID"); 
			writer.append(',');//1     
			writer.append("FWGOBHHID"); 
			writer.append(',');//4
			writer.append("FWJIVHHID"); 
			writer.append(',');//5
			writer.append("FWUNION"); 
			writer.append(',');//6
			writer.append("FWWARD"); 
			writer.append(',');//7
			writer.append("FWSUBUNIT"); 
			writer.append(',');//8
			writer.append("FWMAUZA_PARA"); 
			writer.append(',');//9
			writer.append("FWWOMNID"); 
			writer.append(',');//10
			writer.append("FWWOMBID"); 
			writer.append(',');//11
			writer.append("wom_age"); 
			writer.append(',');//12
			writer.append("first_name"); 
			writer.append(',');//13
			writer.append("husname");			 
			writer.append(','); //16
			writer.append("today"); 
			writer.append(',');//17
			writer.append("start"); 
			writer.append(',');//18
			writer.append("end"); 
			writer.append(',');//19
			writer.append("Form Status at Submission"); 
			writer.append(',');//2
			writer.append("SCHEDULED_DATE"); 
			writer.append(',');//3 
			writer.append("FWMISELCODATE"); 
			writer.append(',');//20
			writer.append("FWPMISBIRTHCTRL"); 
			writer.append(',');//21
			writer.append("FWMISBCSOURCE"); 
			writer.append(',');//22
			writer.append("RECEIVED_TIME_AT_SERVER");
			writer.append('\n'); //22
			
			int count =ecs.getSize();			
		     for (int i = 0; i <count; i++) {
					if(ecs.getRows().get(i).getValue().equalsIgnoreCase("")){
						System.err.println("Error.........................");
					}
					
					writer.append(Integer.toString(i+1));
					writer.append(',');
					String[] ConvertRowValueStringToArray=ecs.getRows().get(i).getValue().split(",");					
					for(int counter=1;counter<=7;counter++){
						if(ConvertRowValueStringToArray[counter] != null && !ConvertRowValueStringToArray[counter].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[counter].equals("\"NaN\"") && !ConvertRowValueStringToArray[counter].equals("\"\"") && !ConvertRowValueStringToArray[counter].isEmpty() && !ConvertRowValueStringToArray[counter].equalsIgnoreCase("")){
							writer.append(ConvertRowValueStringToArray[counter]);
							writer.append(',');
						}
						else {
							writer.append("");
							writer.append(',');
						}
					}

					/**
					 * For NID & BRID
					 * */
					if(ConvertRowValueStringToArray[8] != null && !ConvertRowValueStringToArray[8].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[8].equals("\"\"") && !ConvertRowValueStringToArray[8].isEmpty() && !ConvertRowValueStringToArray[8].equalsIgnoreCase("")){
						writer.append(' '+String.valueOf(ConvertRowValueStringToArray[8].toString()));
						writer.append(',');
					}else{
						writer.append("");
						writer.append(',');
					}
					if(ConvertRowValueStringToArray[9] != null && !ConvertRowValueStringToArray[9].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[9].equals("\"\"") && !ConvertRowValueStringToArray[9].isEmpty() && !ConvertRowValueStringToArray[9].equalsIgnoreCase("")){
						writer.append(' '+String.valueOf(ConvertRowValueStringToArray[9]).toString());
						writer.append(',');
					}else{
						writer.append("");
						writer.append(',');
					}
					
					for(int counter=10;counter<=12;counter++){
						if(ConvertRowValueStringToArray[counter] != null && !ConvertRowValueStringToArray[counter].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[counter].equals("\"NaN\"") && !ConvertRowValueStringToArray[counter].equals("\"\"") && !ConvertRowValueStringToArray[counter].isEmpty() && !ConvertRowValueStringToArray[counter].equalsIgnoreCase("")){
							writer.append(ConvertRowValueStringToArray[counter]);
							writer.append(',');
						}
						else {
							writer.append("");
							writer.append(',');
						}
					}
					for(int counter=15;counter<=22;counter++){
						if(ConvertRowValueStringToArray[counter] != null && !ConvertRowValueStringToArray[counter].equals("\"NaN\"") && !ConvertRowValueStringToArray[counter].equalsIgnoreCase("null") && !ConvertRowValueStringToArray[counter].equals("\"\"") && !ConvertRowValueStringToArray[counter].isEmpty() && !ConvertRowValueStringToArray[counter].equalsIgnoreCase("")){
							writer.append(ConvertRowValueStringToArray[counter]);
							writer.append(',');
						}
						else {
							writer.append("");
							writer.append(',');
						}
					}	
					
					writer.append(ConvertRowValueStringToArray[23]);
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
