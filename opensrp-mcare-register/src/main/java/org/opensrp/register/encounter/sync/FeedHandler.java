package org.opensrp.register.encounter.sync;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.encounter.sync.forms.ChildVaccineFollowup;
import org.opensrp.register.encounter.sync.forms.WomanTTFollowUp;
import org.opensrp.register.encounter.sync.interfaces.FormsType;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class FeedHandler extends FormSubmissionConfig{
	
	private AllMembers allMembers;
	private AllFormSubmissions formSubmissions;
	public FeedHandler(){
		
	}
	
	public FeedHandler(String formDirectory) throws IOException {
		super(formDirectory);
		setFormDirectory(formDirectory);
	}	

	public void setFormDirectory(String formDirectory) {
		this.formDirectory = formDirectory;
	}

	private static Logger logger = LoggerFactory.getLogger(FeedHandler.class.toString());	
	@Autowired
	public FeedHandler(AllMembers allMembers,AllFormSubmissions formSubmissions){		
		this.allMembers = allMembers;
		this.formSubmissions = formSubmissions;
	}
	@SuppressWarnings("unchecked")
	public void getEvent(JSONObject encounter,String patientEntityId,Members member) throws Exception{		
		try {					
			JSONArray observations = encounter.getJSONArray("obs");				
			for (int i = 0; i < observations.length(); i++) {				
				JSONObject o = observations.getJSONObject(i);
				String vaccines = (String) o.get("display");
				String vaccineStringAfterFilter = this.stringFilter(vaccines);					
				boolean TT = this.getTTFromString(vaccineStringAfterFilter, SyncConstant.TT);
				String vaccineDate = this.getDateFromString(vaccineStringAfterFilter);
				double vaccineDose = this.getDoseFromString(vaccineStringAfterFilter);
				String vaccineName = this.getVaccinationName(vaccineStringAfterFilter);
				System.err.println("vaccineName:"+vaccineName);
				int vaccineDoseAsInt =(int) vaccineDose;				
				if(TT){	
					FormsType<WomanTTFollowUp> TTFormObj	= FormFatcory.getFormsTypeInstance("WTT");
					FormSubmission formsubmissionEntity= TTFormObj.getFormSubmission(this.formDirectory,vaccineDate,vaccineDoseAsInt,patientEntityId, member,vaccineName);
					if(formsubmissionEntity !=null){
						formSubmissions.add(formsubmissionEntity);
						
					}
				}else{
					FormsType<ChildVaccineFollowup> childVaccineFormObj= FormFatcory.getFormsTypeInstance("CVF");
					FormSubmission formsubmissionEntity= childVaccineFormObj.getFormSubmission(this.formDirectory,vaccineDate,vaccineDoseAsInt,patientEntityId, member,vaccineName);
					if(formsubmissionEntity !=null){
						formSubmissions.add(formsubmissionEntity);
						
					}
				}					
			}			
			
		} catch (JSONException e) {			
			logger.info(e.getMessage());
		}catch(ArrayIndexOutOfBoundsException ee){
			logger.info(ee.getMessage());
		}
		
		
	}
	
	
	
	public String stringFilter(String str){		
		String strRemoveImmu = "";
		strRemoveImmu = str.replace(SyncConstant.vaccines.get("IIT"), "");
		String strRemoveTT = "";
		strRemoveTT = strRemoveImmu.replace(SyncConstant.vaccines.get("TT"), "");//TT
		String strRemoveOPV = "";
		strRemoveOPV = strRemoveTT.replace(SyncConstant.vaccines.get("OPV"), "");//OPV
		String strRemovePenta = "";
		strRemovePenta = strRemoveOPV.replace(SyncConstant.vaccines.get("PENTA"), "");//penta
		String strRemovePCV = "";
		strRemovePCV = strRemovePenta.replace(SyncConstant.vaccines.get("PCV"), "");//pcv1
		String strRemoveBCG = "";
		strRemoveBCG = strRemovePCV.replace(SyncConstant.vaccines.get("BCG"), "");//bcg
		String strRemoveIPV = "";
		strRemoveIPV = strRemoveBCG.replace(SyncConstant.vaccines.get("IPV"), "");//ipv		
		return strRemoveIPV;
		
	}
	
	public String getVaccinationName(String str) throws Exception{
		String[] vaccineStringToArray = str.split(",");
		for (String value : vaccineStringToArray) {	
			try{				
				String[] vaccine = value.trim().split(" ");				
				if(SyncConstant.getChildVaccinesName().contains(vaccine[0])){					
					return vaccine[0];
				}
			}catch(Exception e ){
				throw new Exception();
			}
		}
		return null;
		
	}
	public boolean getTTFromString(String  str,String subString){		
		return str.toLowerCase().contains(subString.toLowerCase());
			
	}
	
	public String getDateFromString(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");		
		String[] vaccineStringToArray = str.split(",");
		for (String value : vaccineStringToArray) {				
			try {
				formatter.parse(value.trim());				 
				return value;
			} catch (ParseException e) {				
				logger.info("Message:"+e.getMessage());
			}
           
		}
		
		return null;
		
	}
	
	public Double getDoseFromString(String str){		
		String[] vaccineStringToArray = str.split(",");
		for (String value : vaccineStringToArray) {				
			try{				
				return (double) Float.parseFloat(value);				 
			}catch (Exception e) {				
				logger.info("Message:"+e.getMessage());
			}
		}
		return 99.0;
		
	}

	
	public Members get(String patientIdEntityId) {
		 Members members = allMembers.findByCaseId(patientIdEntityId);		 
		return members;
	}
}
