package org.opensrp.connector.dhis2;

/*import static org.opensrp.common.AllConstants.DHIS2.DHIS2_BASE_URL;
import static org.opensrp.common.AllConstants.DHIS2.DHIS2_PWD;
import static org.opensrp.common.AllConstants.DHIS2.DHIS2_USER;*/

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class Dhis2TrackCaptureConnector extends DHIS2Service {
	
	public Dhis2TrackCaptureConnector() {
		
	}
	
	public Dhis2TrackCaptureConnector(String dhis2Url, String user, String password) {
		super(dhis2Url, user, password);
	}
	
	public JSONObject trackCaptureDataSendToDHIS2(JSONObject payloadJsonObj) throws JSONException {
		JSONObject Jobj = new JSONObject(Dhis2HttpUtils.post(
		    DHIS2_BASE_URL.replaceAll("\\s+", "") + "trackedEntityInstances", "", payloadJsonObj.toString(),
		    DHIS2_USER.replaceAll("\\s+", ""), DHIS2_PWD.replaceAll("\\s+", "")).body());
		JSONObject j = (JSONObject) Jobj.get("response");
		System.err.println(j.get("reference"));
		JSONObject enroll = new JSONObject();
		enroll.put("trackedEntityInstance", j.get("reference"));
		enroll.put("program", "OprRhyWVIM6");
		enroll.put("orgUnit", "IDc0HEyjhvL");
		return new JSONObject(Dhis2HttpUtils.post(DHIS2_BASE_URL.replaceAll("\\s+", "") + "enrollments", "",
		    enroll.toString(), DHIS2_USER.replaceAll("\\s+", ""), DHIS2_PWD.replaceAll("\\s+", "")).body());
		
	}
	
}
