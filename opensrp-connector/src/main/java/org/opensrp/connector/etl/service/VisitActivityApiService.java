package org.opensrp.connector.etl.service;

import org.opensrp.connector.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VisitActivityApiService extends ETLService {
	
	private static final String BNFURL = "rest/api/bnf";
	
	private static final String PSRFURL = "rest/api/psrf";
	
	private static Logger logger = LoggerFactory.getLogger(VisitActivityApiService.class.toString());
	
	public void deleteChildAndRelatedInformation(String caseid, String visitCode) {
		try {
			HttpUtil.get(getURL() + "/" + BNFURL + "?caseid=" + caseid + "&visitCode=" + visitCode, "", ETL_USER, ETL_PWD)
			        .body();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void deleteMotherAndRelatedInformation(String caseid) {
		try {
			HttpUtil.get(getURL() + "/" + PSRFURL + "?caseid=" + caseid, "", ETL_USER, ETL_PWD).body();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
