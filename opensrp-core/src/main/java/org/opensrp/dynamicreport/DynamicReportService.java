package org.opensrp.dynamicreport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.opensrp.domain.Client;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.repository.AllClients;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

public class DynamicReportService {

	private AllFormSubmissions allFormSubmissions;
	private AllClients allClients;
	private DynamicReportConfig dynamicReportConfig;

	@Autowired
	public DynamicReportService(AllFormSubmissions allFormSubmissions, AllClients allClients) {
		this.allFormSubmissions = allFormSubmissions;
		this.allClients=allClients;
		//dynamicReportConfig.
	}
	
	public DynamicReport findReport(String reportName){
		return dynamicReportConfig.getReport(reportName); //DynamicReportConfig
		
	}
	
	public void generateReport(DynamicReport report){
		HSSFWorkbook book=new HSSFWorkbook();
		HSSFSheet mainReport=book.createSheet(); 
		List<String> entityIds=getAllClientByBaseForm(report.getSetting().getBaseForm());
		List<RowData> rows= new ArrayList<>();
		for (String string : entityIds) {
			rows.add(getWrapperClient(string,report));
		}
		//#TODO add these data to report template .
	Iterator itr=	report.getReport().iterator();
	while(itr.hasNext()){
		Row r=(Row)itr.next();
	Iterator itc=r.cellIterator();
	while(itc.hasNext()){
		Cell c=(Cell)itc.next();
		if(c.getStringCellValue().matches("([$]{1}[{]{1}[A-Za-z.]+[}]{1})"))
		{
			c.getStringCellValue().split("[${}]");
			for(String s:c.getStringCellValue().split("[${}]")){
				if(!s.equalsIgnoreCase("")){
//rows.get(0).getDetails()
				}
			}
			
		}
	}
	}
	
	}
	
	
	//gets all entity Id from all base forms
	private List<String> getAllClientByBaseForm(String baseForm){
	
		List<String> clientList=new ArrayList<String>();
		List<FormSubmission> list= allFormSubmissions.findByFormName(baseForm, 0);
			for(FormSubmission f : list){
			clientList.add(f.entityId());
	 }
		
		return clientList;
			
	}
	
	private List<FormSubmission> getFormSubmissionsByEntityID(String entityId){
	return	allFormSubmissions.findByMetadata("entityId", entityId);
		
	}
	
	private RowData getWrapperClient(String entityId ,DynamicReport dynamicReport ){
		
		List<Field> fields=dynamicReport.getFields();
		Client client=allClients.get(entityId);
		List<FormSubmission> submissions=getFormSubmissionsByEntityID(entityId);
		HashMap<String,String> details=getDetails(fields,submissions);
		
		return new RowData(client,details);
	}
	
	
	
	private HashMap<String, String> getDetails(List<Field> fields, List<FormSubmission> forms){
		HashMap<String, String> details = new HashMap<>();

		for (Field f : fields) {
			if (!f.getEntityForm().equalsIgnoreCase("client")) {
				for (FormSubmission form : forms) {

					if (form.getField(f.getField()) != null && !form.getField(f.getField()).equalsIgnoreCase("")) {
						details.put(f.getVarName(), form.getField(f.getField()));
					}
				}
			}
		}
		return details;
	}
	
	
	
	
	
}
