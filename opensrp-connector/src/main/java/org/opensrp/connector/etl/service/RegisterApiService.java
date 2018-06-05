package org.opensrp.connector.etl.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.connector.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RegisterApiService extends ETLService {
	
	private static final String CHILD_URL = "rest/api/child";
	
	private static final String MOTHER_URL = "rest/api/mother";
	
	private static Logger logger = LoggerFactory.getLogger(RegisterApiService.class.toString());
	
	public void deleteChildAndRelatedInformation(String caseid) {
		
		try {
			new JSONObject(HttpUtil.post(getURL() + "/" + CHILD_URL, caseid, caseid, ETL_USER, ETL_PWD).body());
		}
		catch (JSONException e) {
			
			logger.info("child delete problem caseid:" + caseid + ",meassage:" + e.getMessage());
		}
	}
	
	public void deleteMotherAndRelatedInformation(String caseid) {
		
		try {
			new JSONObject(HttpUtil.post(getURL() + "/" + MOTHER_URL, caseid, caseid, ETL_USER, ETL_PWD).body());
		}
		catch (JSONException e) {
			
			logger.info("mother delete problem caseid:" + caseid + ",meassage:" + e.getMessage());
		}
		
	}
}
