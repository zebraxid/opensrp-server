package org.opensrp.connector.dhis2;

import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.domain.Client;

public interface DHIS2Tracker {
	
	public JSONObject getTrackCaptureData(Client client) throws JSONException;
	
}
