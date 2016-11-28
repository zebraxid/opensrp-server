package org.opensrp.connector.openmrs.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.api.domain.Address;
import org.opensrp.api.domain.BaseEntity;
import org.opensrp.api.domain.Client;
import org.opensrp.connector.BahmniHttpUtils;
import org.opensrp.connector.Dhis2HttpUtils;
import org.opensrp.connector.HttpUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DHIS2Service extends OpenmrsService {
	
	
	private static final String DHIS2URL = "https://192.168.22.152/dhis/api/trackedEntityInstances";
	
		
	public DHIS2Service() {
	}
	
	public DHIS2Service(String openmrsUrl, String user, String password) {
		super(openmrsUrl, user, password);
	}
	
	
	
	public JSONObject trackCapture(JSONObject payloadJsonObj) throws JSONException {
		
		return new JSONObject(Dhis2HttpUtils.post( DHIS2URL, "", payloadJsonObj.toString(),
		    "dghs", "Dghs@123").body());
		
	
	}

	
	
	
}
