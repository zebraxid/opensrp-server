package org.opensrp.register.encounter.sync;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.domain.Event;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.encounter.sync.forms.WomanTTForm;
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
	public FeedHandler(){
		
	}
	
	public FeedHandler(String formDirectory) throws IOException {
		super(formDirectory);
		
	}
	
	private static Logger logger = LoggerFactory.getLogger(FeedHandler.class.toString());
	private AllFormSubmissions formSubmissions;
	@Autowired
	public FeedHandler(AllFormSubmissions formSubmissions,AllMembers allMembers){
		this.formSubmissions = formSubmissions;
		this.allMembers = allMembers;
	}
	public Event getEvent(JSONObject encounter,String patientEntityId){	
			
		//System.out.println("Event:"+encounter.toString());
		System.out.println("patientEentityId:"+patientEntityId);
		String encounterType;
		try {
			encounterType = encounter.getJSONObject("encounterType").get("display").toString();
			logger.info("eventType:"+encounterType);
			String patientInfo = encounter.getJSONObject("patient").get("display").toString();
			String[] patientStringToArray =  patientInfo.split("-");
			logger.info("patient:"+patientStringToArray[0]);			
			System.err.println("formDirectory:"+formDirectory);
			if(encounterType.equalsIgnoreCase("VAC")){
				JSONArray observations = encounter.getJSONArray("obs");
				for (int i = 0; i < observations.length(); i++) {
					JSONObject o = observations.getJSONObject(i);
					String vaccines = (String) o.get("display");
					String vaccineStringAfterFilter = this.StringFilter(vaccines);					
					boolean TT = this.parseVaccineTypeFromString(vaccineStringAfterFilter, SyncConstant.TT);
					String vaccineDate = this.parseDateFromString(vaccineStringAfterFilter);
					double vaccineDose = this.parseDoseFromString(vaccineStringAfterFilter);
					int vaccineDoseAsInt =(int) vaccineDose;
					Members member =  this.get(patientEntityId);
					if(TT){	
						FormsType<WomanTTForm> womanTTForm	= FormFatcory.getFormsTypeInstance("WTT");
						womanTTForm.makeForm(formDirectory,vaccineDate,vaccineDoseAsInt,patientEntityId, member);
					}else{
						
					}
					
				}
			}
			
			
		} catch (JSONException e) {			
			logger.info(e.getMessage());
		}catch(ArrayIndexOutOfBoundsException ee){
			logger.info(ee.getMessage());
		}
		
		return null;
	}
	
	
	
	public String StringFilter(String str){
		
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
	
	public boolean parseVaccineTypeFromString(String  str,String subString){		
		return str.toLowerCase().contains(subString.toLowerCase());
			
	}
	
	public String parseDateFromString(String str){
		SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");		
		String[] vaccineStringToArray = str.split(",");
		for (String value : vaccineStringToArray) {				
			try {
				formatter.parse(value.trim());				 
				return value;
			} catch (ParseException e) {				
				logger.info("Message: "+e.getMessage());
			}
           
		}
		return null;
		
	}
	
	public Double parseDoseFromString(String str){		
		String[] vaccineStringToArray = str.split(",");
		for (String value : vaccineStringToArray) {				
			try{				
				return (double) Float.parseFloat(value);				 
			}catch (Exception e) {				
				logger.info("Message: "+e.getMessage());
			}
		}
		return 99.0;
		
	}

	
	private Members get(String patientIdEntityId) {
		 Members members = allMembers.findByCaseId(patientIdEntityId);		 
		return members;
	}
}
