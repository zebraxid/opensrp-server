package org.opensrp.dynamicreport;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Setting {
	
	
	private String baseForm;
	private String reportName;
	private List<String> secondaryForms;

	public Setting(String json) throws JSONException  {
		
		this(new JSONObject(json));
		}
		
	public Setting(JSONObject json)throws JSONException {
			
			this(json.getString("baseform"),json.getString("reportname"),json.getString("secondaryforms"));
	}
		
	public Setting(String baseForm, String reportName,
			String secondaryForms) {

		this.baseForm = baseForm;
		this.reportName = reportName;

		setSecondaryForms(secondaryForms);
		//		this.secondaryForms = secondaryForms;
	}


	public String getBaseForm() {
		return baseForm;
	}


	public String getReportName() {
		return reportName;
	}


	public List<String> getSecondaryForms() {
		return secondaryForms;
	}


	public void setBaseForm(String baseForm) {
		this.baseForm = baseForm;
	}


	public void setReportName(String reportName) {
		this.reportName = reportName;
	}


	public void setSecondaryForms(String secondaryForms) {
		String[] array=secondaryForms.split(",");
		List<String> list=new ArrayList<String>();
		for(int i=0;i<array.length;i++)
		{
			list.add(array[i]);
			
		}
		this.secondaryForms = list;
	}
	
	
	
	
}
