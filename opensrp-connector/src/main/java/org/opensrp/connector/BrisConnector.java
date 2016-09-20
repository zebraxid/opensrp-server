package org.opensrp.connector;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.api.domain.Address;
import org.opensrp.api.domain.BaseEntity;
import org.opensrp.api.domain.Client;
import org.opensrp.api.domain.Event;
import org.opensrp.api.domain.Obs;
import org.opensrp.common.util.DateUtil;
import org.opensrp.connector.openmrs.constants.OpenmrsConstants.Encounter;
import org.opensrp.connector.openmrs.constants.OpenmrsConstants.OpenmrsEntity;
import org.opensrp.connector.openmrs.constants.OpenmrsConstants.Person;
import org.opensrp.connector.openmrs.service.OpenmrsService;
import org.opensrp.form.domain.FormField;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;

@Service
public class BrisConnector {

	private FormAttributeMapper formAttributeMapper;
	private static final String BRIS_URL = " http://172.22.100.121/CrvsDemo/api/birthevent";
	@Autowired
	public BrisConnector(FormAttributeMapper formAttributeMapper) {
		this.formAttributeMapper = formAttributeMapper;
	}
	
	/**
	 * Whether form submission is an openmrs form. The xlsform made openmrs form by mapping to an encounter_type in settings in xlsform.
	 * @param fs
	 * @return
	 */
	public boolean isOpenmrsForm(FormSubmission fs) {
		List<String> a = new ArrayList<>();
		a .add("encounter_type");
		String eventType = formAttributeMapper.getUniqueAttributeValue(a , fs).get("encounter_type");
		return !StringUtils.isEmptyOrWhitespaceOnly(eventType);
	}
	
	/** 
	 * Extract Event from given form submission
	 * @param fs
	 * @return
	 * @throws ParseException
	 * @throws JSONException 
	 */
	public JSONObject getEventFromFormSubmission(FormSubmission fs,Client c) throws ParseException, JSONException {
		JSONObject personObj =	new JSONObject();
		JSONArray attributeArray =	new JSONArray();
		
		
		List<FormField> formfileds = fs.instance().form().fields();
		//System.err.println(formfileds.get(29).value());
		String NewBornsUid = formfileds.get(0).value();
		String facilityId = formfileds.get(8).value();		
		String DOB = formfileds.get(34).value();
		String FathersNID = formfileds.get(26).value();
		String FathersName = formfileds.get(27).value();
		String FathersDOB = formfileds.get(29).value();
		String MothersNID = formfileds.get(30).value();
		String MothersName = formfileds.get(31).value();
		String MothersDOB = formfileds.get(33).value();
		String urlParameters = "facilityId=" + facilityId+"&NewBornsUid=" + NewBornsUid+"&DOB=" + DOB
				+"&FathersNID=" + FathersNID+"&FathersName=" + FathersName+"&FathersDOB=" + FathersDOB
				+"&MothersNID=" + MothersNID +"&MothersName=" + MothersName+"&MothersDOB=" +MothersDOB;
		
		/*JSONArray p = new JSONObject(HttpUtil.get(BRIS_URL, urlParameters,
		    "", "").body()).getJSONArray("results");*/
		
		personObj.put("attributes", attributeArray);
		
		return personObj;
	}
	
	
    
}