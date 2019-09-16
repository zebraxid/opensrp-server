package org.opensrp.web.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mysql.jdbc.StringUtils;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.api.domain.Time;
import org.opensrp.api.domain.User;
import org.opensrp.api.util.LocationTree;
import org.opensrp.common.domain.UserDetail;
import org.opensrp.connector.openmrs.service.OpenmrsLocationService;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.domain.postgres.CustomQuery;
import org.opensrp.service.ClientService;
import org.opensrp.web.security.DrishtiAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static org.opensrp.web.HttpHeaderFactory.allowOrigin;
import static org.springframework.http.HttpStatus.OK;

@Controller
public class UserController {
	
	@Value("#{opensrp['opensrp.site.url']}")
	private String opensrpSiteUrl;
	
	private DrishtiAuthenticationProvider opensrpAuthenticationProvider;
	
	private OpenmrsLocationService openmrsLocationService;
	
	private OpenmrsUserService openmrsUserService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	public UserController(OpenmrsLocationService openmrsLocationService, OpenmrsUserService openmrsUserService,
	    DrishtiAuthenticationProvider opensrpAuthenticationProvider) {
		this.openmrsLocationService = openmrsLocationService;
		this.openmrsUserService = openmrsUserService;
		this.opensrpAuthenticationProvider = opensrpAuthenticationProvider;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/authenticate-user")
	public ResponseEntity<HttpStatus> authenticateUser() {
		return new ResponseEntity<>(null, allowOrigin(opensrpSiteUrl), OK);
	}
	
	public Authentication getAuthenticationAdvisor(HttpServletRequest request) {
		final String authorization = request.getHeader("Authorization");
		if (authorization != null && authorization.startsWith("Basic")) {
			// Authorization: Basic base64credentials
			String base64Credentials = authorization.substring("Basic".length()).trim();
			String credentials = new String(Base64.decode(base64Credentials.getBytes()), Charset.forName("UTF-8"));
			// credentials = username:password
			final String[] values = credentials.split(":", 2);
			
			return new UsernamePasswordAuthenticationToken(values[0], values[1]);
		}
		return null;
	}
	
	public DrishtiAuthenticationProvider getAuthenticationProvider() {
		return opensrpAuthenticationProvider;
	}
	
	public User currentUser(HttpServletRequest request) {
		System.out.println("89:currentUser");
		Authentication a = getAuthenticationAdvisor(request);
		System.out.println("91currentUser " + a.getCredentials() + "" + a.getName());
		return getAuthenticationProvider().getUser(a, a.getName());
	}
	
	public Time getServerTime() {
		return new Time(Calendar.getInstance().getTime(), TimeZone.getDefault());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/user-details")
	public ResponseEntity<UserDetail> userDetail(@RequestParam("anm-id") String anmIdentifier, HttpServletRequest request) {
		Authentication a = getAuthenticationAdvisor(request);
		System.out.println("user details:-> " + request.getRequestURL() + " USER anmIdentifier:-> " + anmIdentifier);
		User user = opensrpAuthenticationProvider.getDrishtiUser(a, anmIdentifier);
		return new ResponseEntity<>(new UserDetail(user.getUsername(), user.getRoles()), allowOrigin(opensrpSiteUrl), OK);
	}
	
	@RequestMapping("/security/authenticate")
	@ResponseBody
	public ResponseEntity<String> authenticate(HttpServletRequest request) throws JSONException {
		User u = currentUser(request);
		System.out.println("109AUTHENTICATE:-> " + request.getRequestURL() + " User:-> " + u.getUsername());
		String lid = "";
		JSONObject tm = null;
		try {
			tm = openmrsUserService.getTeamMember(u.getAttribute("_PERSON_UUID").toString());
			JSONArray locs = tm.getJSONArray("locations");
			for (int i = 0; i < locs.length(); i++) {
				lid += locs.getJSONObject(i).getString("uuid") + ";;";
			}
		}
		catch (Exception e) {
			System.out.println("USER Location info not mapped in team management module. Now trying Person Attribute");
		}
		if (StringUtils.isEmptyOrWhitespaceOnly(lid)) {
			lid = (String) u.getAttribute("Location");
			if (StringUtils.isEmptyOrWhitespaceOnly(lid)) {
				String lids = (String) u.getAttribute("Locations");
				
				if (lids == null) {
					throw new RuntimeException(
					        "User not mapped on any location. Make sure that user have a person attribute Location or Locations with uuid(s) of valid OpenMRS Location(s) separated by ;;");
				}
				
				lid = lids;
			}
		}
		LocationTree l = openmrsLocationService.getLocationTreeOf(lid.split(";;"));
		Map<String, Object> map = new HashMap<>();
		map.put("user", u);
		try {
			System.out.println("USERNAME---------------->");
			System.out.println(u.getUsername());
			CustomQuery customQuery = clientService.findTeamInfo(u.getUsername());
			System.out.println(customQuery);
			
			tm.getJSONObject("team").put("teamName", customQuery.getName());
			tm.getJSONObject("team").put("display", customQuery.getName());
			tm.getJSONObject("team").put("uuid", customQuery.getUuid());
			
			Map<String, Object> tmap = new Gson().fromJson(tm.toString(), new TypeToken<HashMap<String, Object>>() {
				
			}.getType());
			map.put("team", tmap);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		map.put("locations", l);
		Time t = getServerTime();
		map.put("time", t);
		return new ResponseEntity<>(new Gson().toJson(map), allowOrigin(opensrpSiteUrl), OK);
	}
	
	@RequestMapping("/security/configuration")
	@ResponseBody
	public ResponseEntity<String> configuration() throws JSONException {
		Map<String, Object> map = new HashMap<>();
		map.put("serverDatetime", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		return new ResponseEntity<>(new Gson().toJson(map), allowOrigin(opensrpSiteUrl), OK);
	}
}
