package org.opensrp.dynamicreport;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.scheduler.Schedule;
import org.opensrp.util.Utils;
import org.slf4j.Logger;

import com.google.gson.JsonArray;
import com.mysql.jdbc.StringUtils;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

public class DynamicReportConfig {
	  private static Logger logger = LoggerFactory.getLogger("org.opensrp");
	private static List<DynamicReport> reports =new ArrayList<>();
	
	   static HSSFRow row;
	  
	   @Autowired
	   public DynamicReportConfig(@Value("#{opensrp['dynamicreport.reports.path']}") String reportPath) throws IOException {
			ResourceLoader loader=new DefaultResourceLoader();
			reportPath = loader.getResource(reportPath).getURI().getPath();
			File reportsFolder=new File(reportPath);
			if(reportsFolder.isDirectory()){
				for( File report:reportsFolder.listFiles())
				{
					
					try {
						addReport(getReport(report));
						logger.info(report.getName()+" report loaded!");
					} catch (JSONException e) {
						logger.debug(e.getLocalizedMessage());
						e.printStackTrace();
					}
				}
			}
			
			
			
			
	}
	   
	   
	   public static DynamicReport getReport(String name){
		   for (Iterator iterator = reports.iterator(); iterator.hasNext();) {
			DynamicReport dynamicReport = (DynamicReport) iterator.next();
			if(dynamicReport.getSetting().getReportName().equalsIgnoreCase(name))
			{
				return dynamicReport;
				
			}
			
		}
		return null;
		   
	   }
	/**
	 * @param args
	 * @throws JSONException
	 * @throws IOException
	 */
	public DynamicReport getReport(File file) throws JSONException, IOException {
	
	   FileInputStream fis = new FileInputStream(file);
	      HSSFWorkbook workbook = new HSSFWorkbook(fis); 
	 //     XSSFWorkbook workbook = new XSSFWorkbook(fis);
		//logger.info("Found "+jarr.length()+" automated schedules");
	      
	   HSSFSheet settingSheet = workbook.getSheet("settings");
	      HSSFSheet fieldsSheet = workbook.getSheet("fields");
	      HSSFSheet reportSheet = workbook.getSheet("report");
	     org.opensrp.dynamicreport.DynamicReport dReport= new org.opensrp.dynamicreport.DynamicReport(getFieldsSheet(fieldsSheet), getSettingsSheet(settingSheet).get(0), reportSheet);
	 return dReport;
	     
	}
	
	private static List<Field> getFieldsSheet(HSSFSheet fields ) throws JSONException, IOException{
		List<Field> list=new ArrayList<>();
		JSONArray jar=getXlsToJson(fields);
		for (int i = 0; i < jar.length(); i++) {
			JSONObject jo = jar.getJSONObject(i);
			logger.debug(jo.toString());
			list.add(new Field(jo));
		}
		return list;
	}
	
	private static List<Setting> getSettingsSheet(HSSFSheet settings ) throws JSONException, IOException{
		List<Setting> list=new ArrayList<>();
		JSONArray jar=getXlsToJson(settings);
		for (int i = 0; i < jar.length(); i++) {
			JSONObject jo = jar.getJSONObject(i);
			logger.debug(jo.toString());
			list.add(new Setting(jo));
		}
		return list;
	}
	
	
	  public static JSONArray getXlsToJson(HSSFSheet sheet) throws JSONException, IOException {
	    	//FileInputStream inp = new FileInputStream( new File(path) );
	    	//Get the workbook instance for XLS file 
	    	//HSSFWorkbook workbook = new HSSFWorkbook(inp);
	    	 
	    	//Get first sheet from the workbook
	    //	HSSFSheet sheet = workbook.getSheetAt(0);
	    	
	    	int hrn = getHeaderRowNum(sheet);
	    	List<String> hr = getRowContent(sheet, hrn);
		    // Start constructing JSON.
		    JSONArray jarr = new JSONArray();

		    for (int i = hrn+1; i <= sheet.getLastRowNum(); i++) {
				List<String> rc = getRowContent(sheet, i);
				if(!isRowEmpty(rc)){
					JSONObject row = new JSONObject();
					for (int j = 0; j < hr.size(); j++) {
						row.put(hr.get(j), rc.get(j));
					}
					jarr.put(row);
				}
			}

		   
	    	return jarr;
		}

		private static int getHeaderRowNum(HSSFSheet sheet) {
			Iterator<Row> i = sheet.iterator();
			while (i.hasNext()) {
				Row r = i.next();
				for (Cell c : r) {
					if(!StringUtils.isEmptyOrWhitespaceOnly(c.getStringCellValue())){
						return r.getRowNum();
					}
				}
			}
			return -1;
		}
		private static boolean isRowEmpty(List<String> rcontent){
			for (String r : rcontent) {
				if(!StringUtils.isEmptyOrWhitespaceOnly(r)){
					return false;
				}
			}
			return true;
		}
		private static List<String> getRowContent(HSSFSheet sheet, int rowNum) {
			List<String> hc = new ArrayList<>();
			HSSFRow r = sheet.getRow(rowNum);
			if(r != null && r.getPhysicalNumberOfCells()>0){
				for (int i = 0; i < r.getLastCellNum(); i++) {
					Cell c = r.getCell(i);
					hc.add(c==null?"":c.getStringCellValue());
				}
			}
			
			/*Iterator<Cell> it = sheet.getRow(rowNum).cellIterator();
			while (it.hasNext()) {
				Cell c = it.next();
				hc.add(c.getStringCellValue());
			}*/
			return hc;
		}
	
	
	public void addReport(DynamicReport report) {
		if(reports == null){
			reports = new ArrayList<>();
		}
		reports.add(report);
	}

}
