package org.opensrp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class DHIS2ConfigService {

	@Value("#{opensrp['dhis2.url']}")
	protected String DHIS2_BASE_URL;
	
	@Value("#{opensrp['dhis2.username']}")
	protected String DHIS2_USER;
	
	@Value("#{opensrp['dhis2.password']}")
	protected String DHIS2_PWD;
	
	public DHIS2ConfigService(){
		
	}
	public DHIS2ConfigService(String dhis2Url, String user, String password) {
		DHIS2_BASE_URL = dhis2Url;
		DHIS2_USER = user;
		DHIS2_PWD = password;
	}
}
