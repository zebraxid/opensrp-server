package org.opensrp.connector.etl.service;

import org.opensrp.connector.HttpUtil;
import org.springframework.beans.factory.annotation.Value;

public abstract class ETLService {
	
	@Value("#{opensrp['etl.url']}")
	protected String ETL_BASE_URL;
	
	@Value("#{opensrp['etl.username']}")
	protected String ETL_USER;
	
	@Value("#{opensrp['etl.password']}")
	protected String ETL_PWD;
	
	public ETLService() {
	}
	
	public ETLService(String etlUrl, String user, String password) {
		ETL_BASE_URL = etlUrl;
		ETL_USER = user;
		ETL_PWD = password;
	}
	
	/**
	 * returns url after trimming ending slash
	 * 
	 * @return
	 */
	public String getURL() {
		return HttpUtil.removeEndingSlash(ETL_BASE_URL);
	}
	
}
