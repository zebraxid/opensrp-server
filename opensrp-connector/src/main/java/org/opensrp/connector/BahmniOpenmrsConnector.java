package org.opensrp.connector;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.opensrp.api.domain.Address;
import org.opensrp.api.domain.BaseEntity;
import org.opensrp.api.domain.Client;
import org.opensrp.common.util.DateUtil;
import org.opensrp.connector.openmrs.constants.OpenmrsConstants.OpenmrsEntity;
import org.opensrp.connector.openmrs.constants.OpenmrsConstants.Person;
import org.opensrp.connector.openmrs.service.OpenmrsService;
import org.opensrp.form.domain.FormField;
import org.opensrp.form.domain.FormSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;

@Service
public class BahmniOpenmrsConnector {

	private FormAttributeMapper formAttributeMapper;
	
	@Autowired
	public BahmniOpenmrsConnector(FormAttributeMapper formAttributeMapper){
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
	 * Get field name for specified openmrs entity in given form submission
	 * @param en
	 * @param fs
	 * @return
	 */
	String getFieldName(OpenmrsEntity en, FormSubmission fs) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("openmrs_entity" , en.entity());
		m.put("openmrs_entity_id" , en.entityId());
		return formAttributeMapper.getFieldName(m , fs);
	}
	
	  Map<String, Address> extractAddresses(FormSubmission fs) throws ParseException {
	        Map<String, Address> paddr = new HashMap<>();
	        for (FormField fl : fs.instance().form().fields()) {
	            fillAddressFields(fl, null, fs, paddr);
	        }
	        return paddr;
	    }
	  void fillAddressFields(FormField fl, String subform, FormSubmission fs, Map<String, Address> addresses) throws ParseException {
	        Map<String, String> att = new HashMap<>();
	        if(StringUtils.isEmptyOrWhitespaceOnly(subform)){
	            att = formAttributeMapper.getAttributesForField(fl.name(), fs);
	        }
	        else {
	            att = formAttributeMapper.getAttributesForSubform(subform, fl.name(), fs);
	        }
	        if(att.size()>0 && att.get("openmrs_entity").equalsIgnoreCase("person_address")){
	            String addressType = att.get("openmrs_entity_parent");
	            String addressField = att.get("openmrs_entity_id");
	            Address ad = addresses.get(addressType);
	            if(ad == null){
	                ad = new Address(addressType, null, null, null, null, null, null, null, null);
	            }
	 
	            if(addressField.equalsIgnoreCase("startDate")||addressField.equalsIgnoreCase("start_date")){
	                ad.setStartDate(DateUtil.parseDate(fl.value()));
	            }
	            else if(addressField.equalsIgnoreCase("endDate")||addressField.equalsIgnoreCase("end_date")){
	                ad.setEndDate(DateUtil.parseDate(fl.value()));
	            }
	            else if(addressField.equalsIgnoreCase("latitude")){
	                ad.setLatitude(fl.value());
	            }
	            else if(addressField.equalsIgnoreCase("longitute")){
	                ad.setLongitute(fl.value());
	            }
	            else if(addressField.equalsIgnoreCase("geopoint")){
	                // example geopoint 34.044494 -84.695704 4 76 = lat lon alt prec
	                String geopoint = fl.value();
	                if(!StringUtils.isEmptyOrWhitespaceOnly(geopoint)){
	                    String[] g = geopoint.split(" ");
	                    ad.setLatitude(g[0]);
	                    ad.setLongitute(g[1]);
	                    ad.addAddressField(addressField, fl.value());
	                }
	            }
	            else if(addressField.equalsIgnoreCase("postalCode")||addressField.equalsIgnoreCase("postal_code")){
	                ad.setPostalCode(fl.value());
	            }
	            else if(addressField.equalsIgnoreCase("state")||addressField.equalsIgnoreCase("state_province")||addressField.equalsIgnoreCase("stateProvince")){
	                ad.setState(fl.value());
	            }
	            else if(addressField.equalsIgnoreCase("country")){
	                ad.setCountry(fl.value());
	            }
	            else {
	                ad.addAddressField(addressField, fl.value());
	            }
	 
	            addresses.put(addressType, ad);
	        }
	    }
	  
	  Map<String, Object> extractAttributes(FormSubmission fs) {
			Map<String, Object> pattributes = new HashMap<>();
			for (FormField fl : fs.instance().form().fields()) {
				if(!StringUtils.isEmptyOrWhitespaceOnly(fl.value())){
					Map<String, String> att = new HashMap<>();
					att = formAttributeMapper.getAttributesForField(fl.name(), fs);
					if(att.size()>0 && att.get("openmrs_entity").equalsIgnoreCase("person_attribute")){
						pattributes.put(att.get("openmrs_entity_id"), fl.value());
					}
				}
			}
			return pattributes;
		}
	  
	  Map<String, String> extractIdentifiers(FormSubmission fs) {
			Map<String, String> pids = new HashMap<>();
			for (FormField fl : fs.instance().form().fields()) {
				if(!StringUtils.isEmptyOrWhitespaceOnly(fl.value())){
					Map<String, String> att = new HashMap<>();
					att = formAttributeMapper.getAttributesForField(fl.name(), fs);
					
					if(att.size()>0 && att.get("openmrs_entity").equalsIgnoreCase("person_identifier")){
						pids.put(att.get("openmrs_entity_id"), fl.value());
					}
				}
			}
			return pids;
		}
		
	/**
	 * Extract Client from given form submission
	 * @param fs
	 * @return
	 * @throws ParseException
	 */
	public Client getClientFromFormSubmission(FormSubmission fs) throws ParseException {
		String firstName = fs.getField(getFieldName(Person.first_name, fs));
		String middleName = fs.getField(getFieldName(Person.middle_name, fs));
		String lastName = fs.getField(getFieldName(Person.last_name, fs));
		String bd = fs.getField(getFieldName(Person.birthdate, fs));
		Date birthdate = (bd==null || bd.isEmpty())? OpenmrsService.OPENMRS_DATE.parse("1900-01-01"):OpenmrsService.OPENMRS_DATE.parse(bd);
		String dd = fs.getField(getFieldName(Person.deathdate, fs));
		Date deathdate = (dd==null || dd.isEmpty())?null:OpenmrsService.OPENMRS_DATE.parse(dd);
		String aproxbd = fs.getField(getFieldName(Person.birthdate_estimated, fs));
		Boolean birthdateApprox = false;
		if(!StringUtils.isEmptyOrWhitespaceOnly(aproxbd) && NumberUtils.isNumber(aproxbd)){
			int bde = 0;
			try {
				bde = Integer.parseInt(aproxbd);
			} catch (Exception e) {
				e.printStackTrace();
			}
			birthdateApprox = bde > 0 ? true:false;
		}
		String aproxdd = fs.getField(getFieldName(Person.deathdate_estimated, fs));
		Boolean deathdateApprox = false;
		if(!StringUtils.isEmptyOrWhitespaceOnly(aproxdd) && NumberUtils.isNumber(aproxdd)){
			int dde = 0;
			try {
				dde = Integer.parseInt(aproxdd);
			} catch (Exception e) {
				e.printStackTrace();
			}
			deathdateApprox = dde > 0 ? true:false;
		}
		String gender ="F" ;//fs.getField(getFieldName(Person.gender, fs));
		
		List<Address> addresses = new ArrayList<>(extractAddresses(fs).values());
		
		Client c = new Client()
			.withBaseEntity(new BaseEntity(fs.entityId(), firstName, middleName, lastName, birthdate, deathdate, 
					birthdateApprox, deathdateApprox, gender, addresses, extractAttributes(fs)))
			.withIdentifiers(extractIdentifiers(fs));
		return c;
	}
	
}
