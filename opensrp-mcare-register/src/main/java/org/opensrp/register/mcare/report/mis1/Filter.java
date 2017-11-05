package org.opensrp.register.mcare.report.mis1;

import org.ektorp.ComplexKey;
import org.joda.time.DateTime;

import java.util.List;

import static java.util.Arrays.asList;

public class Filter {
	public String district;
	public String subDistrict;
	public String union;
	public String ward;
	public String unit;
	public String worker;
	public int month;
	public int year;

	public Filter() {

	}

	public Filter(String district, String subDistrict, String union, String ward, String unit, String worker, int year, int month) {
		this.district = district;
		this.subDistrict = subDistrict;
		this.union = union;
		this.worker = worker;
		this.ward = ward;
		this.unit = unit;
		this.month = month;
		this.year = year;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getSubDistrict() {
		return subDistrict;
	}

	public void setSubDistrict(String subDistrict) {
		this.subDistrict = subDistrict;
	}

	public String getUnion() {
		return union;
	}

	public void setUnion(String union) {
		this.union = union;
	}

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public boolean validate() {
		boolean valid = false;

		if(!validYear(year) || !validMonth(month)){
			return false;
		}

		if(!validParam(district)) {
			return false;
		}


		if(!validParam(subDistrict) && anyValidParam(asList(union, ward, unit))) {
			return  false;
		}

		if(!validParam(union) && anyValidParam(asList(ward, unit))) {
			return false;
		}

		if(!validParam(ward) && anyValidParam(asList(unit))) {
			return false;
		}

		return true;


	}

	public boolean anyValidParam(List<String> params) {
		for(String param : params) {
			if(validParam(param)) {
				return true;
			}
		}
		return false;
	}

	private boolean validParam(String param) {
		return param != null && !param.isEmpty();
	}

	private boolean validYear(int year) {
		return year>0 && year<= new DateTime().getYear();
	}

	private boolean validMonth(int month) {
		return month>=1 && month<=12;
	}

}
