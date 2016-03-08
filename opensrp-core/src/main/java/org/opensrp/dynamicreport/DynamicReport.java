package org.opensrp.dynamicreport;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;

public class DynamicReport {

	private List<Field> fields;
	private Setting setting;
	private HSSFSheet report;
	
	public DynamicReport(List<Field> fields, Setting setting, HSSFSheet report) {
		this.fields = fields;
		this.setting = setting;
		this.report = report;
	}

	public List<Field> getFields() {
		return fields;
	}

	public Setting getSetting() {
		return setting;
	}

	public HSSFSheet getReport() {
		return report;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public void setReport(HSSFSheet report) {
		this.report = report;
	}
	
	
	
	
	
}
