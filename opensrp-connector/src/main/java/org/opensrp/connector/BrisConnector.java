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
		//System.out.println("DHIS2"+c.getBaseEntity().getGender());
		String gender = c.getBaseEntity().getGender();
		
		for (FormField fl : fs.instance().form().fields()) {
			//System.out.println("Field NAme:"+fl.toString());
			Map<String, String> att = formAttributeMapper.getAttributesForField(fl.name(), fs);			
			if(!StringUtils.isEmptyOrWhitespaceOnly(fl.value()) && att.size()>0 && att.get("openmrs_entity").equalsIgnoreCase("concept")){
            	JSONObject attrValueObj = new JSONObject(); 
    			attrValueObj.put(att.get("concept"), att.get("openmrs_entity_id"));
	    		attrValueObj.put("value",  fl.value());
	    		attributeArray.put(attrValueObj);    					
			}
		}
		
		
		personObj.put("attributes", attributeArray);
		
		return personObj;
	}
	
	
	
	
	/**
	 * Get field name for specified openmrs entity in given form submission
	 * @param en
	 * @param fs
	 * @return
	 */
	String getFieldName(OpenmrsEntity en, FormSubmission fs) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("dhis_attribute" , en.entity());
		m.put("dhis_attribute_id" , en.entityId());
		return formAttributeMapper.getFieldName(m , fs);
	}
	
	/**
	 * Get field name for specified openmrs entity in given form submission for given subform
	 * @param en
	 * @param subform
	 * @param fs
	 * @return
	 */
	String getFieldName(OpenmrsEntity en, String subform, FormSubmission fs) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("dhis_attribute" , en.entity());
		m.put("dhis_attribute_id" , en.entityId());
		return formAttributeMapper.getFieldName(m , subform, fs);
	}
	
	/**
	 * Get field name for specified openmrs attribute mappings in given form submission
	 * @param entity
	 * @param entityId
	 * @param entityParentId
	 * @param fs
	 * @return
	 */
	String getFieldName(String entity, String entityId, String entityParentId, FormSubmission fs) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("dhis_attribute" , entity);
		m.put("dhis_attribute_id" , entityId);
		m.put("openmrs_entity_parent" , entityParentId);
		return formAttributeMapper.getFieldName(m , fs);
	}
	
    
}