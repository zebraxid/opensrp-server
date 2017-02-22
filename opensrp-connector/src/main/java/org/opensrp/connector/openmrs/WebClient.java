package org.opensrp.connector.openmrs;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.ict4h.atomfeed.client.AtomFeedProperties;
import org.ict4h.atomfeed.client.exceptions.AtomFeedClientException;
import org.ict4h.atomfeed.client.repository.datasource.ClientCookies;

public class WebClient extends org.ict4h.atomfeed.client.repository.datasource.WebClient {
    
	public String fetch(URI uri, AtomFeedProperties atomFeedProperties, Map<String, String> clientCookies) {
		
		try {
			trustHostNames("endtbtest.irdresearch.org");
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
       /*HttpsURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            connection = (HttpsURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/atom+xml");
            ClientCookies cookies = new ClientCookies(clientCookies);
            String httpRequestPropertyValue = cookies.getHttpRequestPropertyValue();
            if (httpRequestPropertyValue != null)
                connection.setRequestProperty("Cookie", httpRequestPropertyValue);
            connection.setDoOutput(true);
            connection.setConnectTimeout(atomFeedProperties.getConnectTimeout());
            connection.setReadTimeout(atomFeedProperties.getReadTimeout());
            connection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + '\n');
            }
        } catch (Exception e) {
            throw new AtomFeedClientException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return stringBuilder.toString();*/
		return super.fetch(uri, atomFeedProperties, clientCookies);
    }
	
	private void trustHostNames(final String hostName) throws KeyManagementException, NoSuchAlgorithmException {
		SSLContext sc = SSLContext.getInstance("TLS");
		Security.addProvider(new BouncyCastleProvider());
		sc.init(null, new TrustManager[] { new TrustAllX509TrustManager() }, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier(){
		    public boolean verify(String string, SSLSession ssls) {
		    	/*System.out.println("String is: "+ string);
		    	if(string.equals(hostName)) {
		    		return true;
		    	}
		    	
		        return false;*/
		    	return true;
		    }
		});
	}
	
	public class TrustAllX509TrustManager implements X509TrustManager {
	    public X509Certificate[] getAcceptedIssuers() {
	        return null;
	    }

	    public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
	            String authType) {
	    }

	    public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
	            String authType) {
	    }

	}
}
