package org.opensrp.register.mcare.report.mis1;

public class Filter {
	 public String district;
     public String subDistrict;
     public String union;
     public String worker;
     public int month;
     public int year;

     public Filter(){
    	 
     }
     public Filter(String district, String subDistrict, String union, String worker, int year, int month) {
         this.district = district;
         this.subDistrict = subDistrict;
         this.union = union;
         this.worker = worker;
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
     
}
