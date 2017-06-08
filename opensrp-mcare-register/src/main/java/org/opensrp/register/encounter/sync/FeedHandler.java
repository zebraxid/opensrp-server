package org.opensrp.register.encounter.sync;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hamcrest.text.SubstringMatcher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.encounter.sync.forms.ChildVaccineFollowup;
import org.opensrp.register.encounter.sync.forms.WomanTTFollowUp;
import org.opensrp.register.encounter.sync.interfaces.FormsType;
import org.opensrp.register.encounter.sync.mapping.domain.EncounterSyncMapping;
import org.opensrp.register.encounter.sync.mapping.repository.AllEncounterSyncMapping;
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
	private AllEncounterSyncMapping allEncounterSyncMapping;
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
	public FeedHandler(AllMembers allMembers,AllFormSubmissions formSubmissions,AllEncounterSyncMapping allEncounterSyncMapping){		
		this.allMembers = allMembers;
		this.formSubmissions = formSubmissions;
		this.allEncounterSyncMapping=allEncounterSyncMapping;
	}
	
	/**
	 * <p>get Event from <code>OpenMRS</code> through <code>AtomFeed</code>.
	 *  getting <code>Formsubmission</code>  save to <code>opensrp-form</code></p>
	 * @param encounter A Encounter comes from OpenMRS.
	 * @param memberEntityId unique id of a member.
	 * @param member A member information.
	 * @throws JSONException if json is invalid.
	 * @throws Exception. 
	 * */
	@SuppressWarnings("unchecked")
	public void getEvent(JSONObject encounter,String memberEntityId,Members member) throws Exception{		
		try {					
			JSONArray observations = encounter.getJSONArray("obs");				
			for (int i = 0; i < observations.length(); i++) {				
				JSONObject o = observations.getJSONObject(i);
				String vaccines = (String) o.get("display");
				String vaccineStringAfterFilter = this.stringFilter(vaccines);					
				boolean TT = this.checkTTFVaccineFromAString(vaccineStringAfterFilter, SyncConstant.TT);
				String vaccineDate = this.getDateFromString(vaccineStringAfterFilter);
				double vaccineDose = this.getDoseFromString(vaccineStringAfterFilter);
				String vaccineName = this.getVaccinationName(vaccineStringAfterFilter);				
				int doseNumber =(int) vaccineDose;
				try{
					if(TT){	
						if(member.Is_woman().equalsIgnoreCase(SyncConstant.ISWOMAN)){
							
							FormsType<WomanTTFollowUp> TTFormObj	= FormFatcory.getFormsTypeInstance("WTT");
							FormSubmission formsubmissionEntity= TTFormObj.getFormSubmission(this.formDirectory,vaccineDate,doseNumber,memberEntityId, member,vaccineName);
							if(formsubmissionEntity !=null){
								formSubmissions.add(formsubmissionEntity);								
								logger.info("Synced TT vaccine:"+formsubmissionEntity.toString());
							}
							
						}else{							
							logger.info("Member is not a woman:"+member.toString());
						}
					}else{
						if(member.Is_child().equalsIgnoreCase(SyncConstant.ISCHILD)){
							FormsType<ChildVaccineFollowup> childVaccineFormObj= FormFatcory.getFormsTypeInstance("CVF");
							FormSubmission formsubmissionEntity= childVaccineFormObj.getFormSubmission(this.formDirectory,vaccineDate,doseNumber,memberEntityId, member,vaccineName);
							if(formsubmissionEntity !=null){
								formSubmissions.add(formsubmissionEntity);								
								logger.info("Synced child vaccine:"+formsubmissionEntity.toString());
							}
						}else{							
							logger.info("Member is not a child:"+member.toString());
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}			
			
		} catch (JSONException e) {			
			logger.info(e.getMessage());
		}catch(ArrayIndexOutOfBoundsException ee){
			logger.info(ee.getMessage());
		}		
	}
	
	/**
	 * <p>extract a string from one format to another desired format. </p>
	 * <p> input string is as like Immunization Incident Template: TT 2 (Tetanus toxoid), 2016-02-28, true, 2.0</p>
	 * <p> Desired output string is as like TT 2 , 2016-02-28, true, 2.0</p>
	 * @param str A String value.
	 * @return Desired formatted string. 
	 * */
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
		return strRemoveIPV.trim();
		
	}
	
	/**
	 * <p>get <code>Child vaccine name</code>  this is special for Child vaccine.</p>
	 * @param str A String value.
	 * @param subString A String Value.
	 * @return child vaccine.  
	 * */	
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
	
	/**
	 * <p>check <code>TT vaccine name</code>,this is special for TT vaccine.</p>
	 * <h5> Ex.("TT 2 , 2016-02-28, true, 2.0" Compare to "TT")</h5>
	 * @param str A String value.
	 * @param subString A String which is compared to input str.
	 * @return true or false.  
	 * */
	public boolean checkTTFVaccineFromAString(String  str,String subString){		
		return str.toLowerCase().contains(subString.toLowerCase());			
	}
	
	/**
	 * get vaccine <code>Date</code> from a String (e.x: <code>OPV 2 , 2016-02-28, true, 2.0</code>).
	 * @param str A String value.
	 * @return vaccine Date.
	 * */
	public String getDateFromString(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");		
		String[] vaccineStringToArray = str.split(",");
		for (String value : vaccineStringToArray) {				
			try{
				formatter.parse(value.trim());				 
				return value.trim();
			} catch (ParseException e) {				
				logger.info("Message:"+e.getMessage());
			}
           
		}
		
		return null;
		
	}
	
	/**
	 * get <code>dose number</code> from a string (<code>e.x: OPV 2 , 2016-02-28, true, 2.0</code>).
	 * @param str A String value.
	 * @return  dose number.
	 * */
	public Double getDoseFromString(String str){		
		String[] vaccineStringToArray = str.split(",");
		for (String value : vaccineStringToArray) {				
			try{				
				return (double) Float.parseFloat(value);				 
			}catch (Exception e) {				
				logger.info("Message:"+e.getMessage());
			}
		}
		return 0.0;
		
	}
	
	/**
	 * get a <code>member</code> 
	 * @param memberEntityId A member unique Id.
	 * @return member
	 */	
	public Members get(String memberEntityId) {
		 Members members = allMembers.findByCaseId(memberEntityId);		 
		return members;
	}
	
	public EncounterSyncMapping getEncounterSyncMapping(String encounterId){
		EncounterSyncMapping encounterSyncMapping = allEncounterSyncMapping.findByEncounterId(encounterId);
		return encounterSyncMapping;
		
	}
}
