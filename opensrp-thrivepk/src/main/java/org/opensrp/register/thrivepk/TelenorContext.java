package org.opensrp.register.thrivepk;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.opensrp.common.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.StringUtils;

@Component
public class TelenorContext {
	public enum Config {
		BASE_URL("telenor.api.base-url"),
		AUTH_URL("telenor.api.url.auth"),
		PING_URL("telenor.api.url.ping"),
		OUTBOUND_DISPATCH_URL("telenor.api.url.outbound.dispatch"),
		OUTBOUND_QUERY_URL("telenor.api.url.outbound.query"),
		OUTBOUND_KEEP_LOG("telenor.outbound.keep-log"),
		OUTBOUND_AUTO_RETRY("telenor.outbound.auto-retry"),
		SUBSCRIBER_CREATE_LIST_URL("telenor.api.url.subscriber.create-list"),
		SUBSCRIBER_LIST_ADD_CONTACT_URL("telenor.api.url.subscriber-list.add-contacts"),
		CAMPAIGN_CREATE_URL("telenor.api.url.campaign.create"),
		CAMPAIGN_QUERY_URL("telenor.api.url.campaign.query"),
		CALL_DISPATCH_URL("telenor.api.url.call.dispatch"),
		CALL_QUERY_URL("telenor.api.url.call.query"),
		MSISDN("telenor.msisdn"),
		PASSWORD("telenor.password"),
		;
		
		private String property;
		
		private Config(String propertyName) {
			this.property = propertyName;
		}

		public String property() { return property; }
		
		public static String fullUrl(Config serviceUrl){
			String base = HttpUtil.removeEndingSlash(getProperty(BASE_URL, null));
			String service = HttpUtil.removeEndingSlash(HttpUtil.removeTrailingSlash(getProperty(serviceUrl, null)));
			return base + "/" + service;
		}
		
		public static boolean isConfigured(Config property) {
			if(StringUtils.isEmptyOrWhitespaceOnly(getProperty(property.property, null))){
				return false;
			}
			return true;
		}

	}
	private static Logger lg = Logger.getLogger(TelenorContext.class);
	
	private static Properties properties;
	
	public static Properties getProperties() { return properties; }
	static void setProperties(Properties properties) { TelenorContext.properties = properties; }

	@Autowired
	public TelenorContext(@Value("#{telenor['telenor.api.base-url']}") String basurl,
			@Value("#{telenor['telenor.api.url.auth']}") String authurl,  
			@Value("#{telenor['telenor.api.url.outbound.dispatch']}") String outboundurl, 
			@Value("#{telenor['telenor.msisdn']}") String msisdn,
			@Value("#{telenor['telenor.password']}") String password) {
		Properties p = new Properties();
		p.put("telenor.api.base-url", basurl);
		p.put("telenor.api.url.auth", authurl);
		p.put("telenor.api.url.outbound.dispatch", outboundurl);
		p.put("telenor.msisdn", msisdn);
		p.put("telenor.password", password);
		
		setProperties(p);
	}
	
	public static String getProperty(String name, String defaultVal) {
		return properties.getProperty(name, defaultVal);
	}
	
	public static String getProperty(Config name, String defaultVal) {
		return properties.getProperty(name.property(), defaultVal);
	}
	
	public static String createReferenceNumber(Long messageId) {
		return "Telenor:"+messageId.toString();
	}
	
	public static String createReferenceNumber(Object messageId) {
		return createReferenceNumber(new Double(messageId.toString()).longValue());
	}
	
	public static String getMessageId(String referenceNumber) {
		return referenceNumber.substring(referenceNumber.lastIndexOf(":")+1);
	}

}
