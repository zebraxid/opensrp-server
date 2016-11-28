package org.opensrp.connector.openmrs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;

import com.mysql.jdbc.StringUtils;

public class Dhis {

	/**
	 * @param args
	 * @throws JSONException 
	 */
	
	
	public static void main(String[] args) throws JSONException {
		
		
		JSONObject personObj =	new JSONObject();
		JSONArray nameArray =	new JSONArray();
		JSONObject nameAttrObj1 = new JSONObject();
		nameAttrObj1.put("dataElement", "nbQTnNFs1I8");
		nameAttrObj1.put("period", "201610");
		nameAttrObj1.put("orgUnit", "SkiBAS3qNA6");
		nameAttrObj1.put("value", "1");
		
		JSONObject nameAttrObj2 = new JSONObject();
		nameAttrObj2.put("dataElement", "Na5rrDNtwOW");//Penta 1given (0-11m)
		nameAttrObj2.put("period", "201610");
		nameAttrObj2.put("orgUnit", "SkiBAS3qNA6");
		nameAttrObj2.put("value", "2");
		
		JSONObject nameAttrObj3 = new JSONObject();
		nameAttrObj3.put("dataElement", "zGQIRoCQIcK");//Penta 2 given (0-11m)
		nameAttrObj3.put("period", "201610");
		nameAttrObj3.put("orgUnit", "SkiBAS3qNA6");
		nameAttrObj3.put("value", "3");
		
		JSONObject nameAttrObj4 = new JSONObject();
		nameAttrObj4.put("dataElement", "cOP5mAREs38");//Penta 3 given (0-11m)
		nameAttrObj4.put("period", "201610");
		nameAttrObj4.put("orgUnit", "SkiBAS3qNA6");
		nameAttrObj4.put("value", "4");
		
		JSONObject nameAttrObj5 = new JSONObject();
		nameAttrObj5.put("dataElement", "TzbgFs3CSyp");//OPV 0 given (0-11m)
		nameAttrObj5.put("period", "201610");
		nameAttrObj5.put("orgUnit", "SkiBAS3qNA6");
		nameAttrObj5.put("value", "5");
		
		JSONObject nameAttrObj6 = new JSONObject();
		nameAttrObj6.put("dataElement", "eYJ3MgWzghH");//OPV 1 given (0-11m)
		nameAttrObj6.put("period", "201610");
		nameAttrObj6.put("orgUnit", "SkiBAS3qNA6");	
		nameAttrObj6.put("value", "6");
		
		JSONObject nameAttrObj7 = new JSONObject();
		nameAttrObj7.put("dataElement", "YkajaYobus9");//OPV 2 given (0-11m)
		nameAttrObj7.put("period", "201610");
		nameAttrObj7.put("orgUnit", "SkiBAS3qNA6");
		nameAttrObj7.put("value", "7");
		
		JSONObject nameAttrObj8 = new JSONObject();
		nameAttrObj8.put("dataElement", "AFIo5tpZjyr");//OPV 3 given (0-11m)
		nameAttrObj8.put("period", "201610");
		nameAttrObj8.put("orgUnit", "SkiBAS3qNA6");
		nameAttrObj8.put("value", "8");
		
		JSONObject nameAttrObj9 = new JSONObject();
		nameAttrObj9.put("dataElement", "AFIo5tpZjyr");//OPV 3 given (0-11m)
		nameAttrObj9.put("period", "201610");
		nameAttrObj9.put("orgUnit", "SkiBAS3qNA6");
		nameAttrObj9.put("value", "9");
		
		JSONObject nameAttrObj10 = new JSONObject();
		nameAttrObj10.put("dataElement", "muz8krEzZOY");//OPV 4 given (0-11m)
		nameAttrObj10.put("period", "201610");
		nameAttrObj10.put("orgUnit", "SkiBAS3qNA6");
		nameAttrObj10.put("value", "10");
		
		nameArray.put(nameAttrObj1);
        nameArray.put(nameAttrObj2);
		nameArray.put(nameAttrObj3);
		nameArray.put(nameAttrObj4);
		nameArray.put(nameAttrObj5);
		nameArray.put(nameAttrObj6);
		nameArray.put(nameAttrObj7);
		nameArray.put(nameAttrObj8);
		nameArray.put(nameAttrObj9);
		nameArray.put(nameAttrObj10);
		personObj.put("dataValues", nameArray);	
		
		System.out.println(personObj.toString());
		
	TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
    };
    // Install the all-trusting trust manager
    SSLContext sc = null;
	try {
		sc = SSLContext.getInstance("SSL");
	} catch (NoSuchAlgorithmException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    try {
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
	} catch (KeyManagementException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    // Create all-trusting host name verifier
    HostnameVerifier allHostsValid = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    // Install the all-trusting host verifier
    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
   
    
    try{
    	//String url = "https://192.168.22.152/dhis/api/24/dataValueSets";
    	String url = "http://103.247.238.74:8081/dhis22/api/dataValueSets";
        String payload ="";
        url = (url+(StringUtils.isEmptyOrWhitespaceOnly(payload)?"":("?"+payload))).replaceAll(" ", "%20");
    	URL urlo = new URL(url);
        HttpURLConnection urlc = (HttpURLConnection) urlo.openConnection();
        
        urlc.setRequestProperty("Content-Type", "application/json");
        String charset = "UTF-8";
        urlc.setRequestProperty("Accept-Charset", charset);
        String encoded = new String(Base64.encodeBase64(("mpower:mPower4321").getBytes()));
        urlc.setRequestProperty("Authorization", "Basic "+encoded);
        urlc.setRequestMethod(HttpMethod.POST.name());          
        urlc.setFixedLengthStreamingMode(
        	personObj.toString().getBytes().length);           
        urlc.setDoOutput(true);
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(urlc.getOutputStream(), charset ), true); // true = autoFlush, important!
		writer.print(personObj.toString());
		  if (writer != null) writer.close();
	        int statusCode = urlc.getResponseCode();
	        if (statusCode != HttpURLConnection.HTTP_OK) {
	            
	        } 
	        BufferedReader    br = new BufferedReader(new InputStreamReader((urlc.getInputStream())));
	        StringBuilder sb = new StringBuilder();
	        String output;
	        while ((output = br.readLine()) != null) {
	        sb.append(output);		        
	        }
		System.out.println(sb.toString());			
    } catch (Exception e){           
    	e.printStackTrace();
    }
    
    

	}

}
