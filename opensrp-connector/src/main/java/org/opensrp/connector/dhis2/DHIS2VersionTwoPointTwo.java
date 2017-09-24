package org.opensrp.connector.dhis2;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class DHIS2VersionTwoPointTwo implements DHIS2Version {
	
	@Override
	public JSONObject send() {
		System.err.println("come to 2");
		return null;
	}
	
}
