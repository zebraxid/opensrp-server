package org.opensrp.web.controller;

import static org.opensrp.web.HttpHeaderFactory.allowOrigin;
import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.api.domain.User;
import org.opensrp.api.util.LocationTree;
import org.opensrp.common.domain.UserDetail;
import org.opensrp.common.util.HttpResponse;
import org.opensrp.connector.HttpUtil;
import org.opensrp.connector.openmrs.service.OpenmrsLocationService;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.web.security.DrishtiAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;

@Controller
public class UserController {
	private String opensrpSiteUrl;
	private DrishtiAuthenticationProvider opensrpAuthenticationProvider;
	private OpenmrsLocationService openmrsLocationService;
	private OpenmrsUserService openmrsUserService;
    private static Logger logger = LoggerFactory.getLogger(UserController.class.toString());

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

	public User currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return opensrpAuthenticationProvider.getDrishtiUser(authentication);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/user-details")
	public ResponseEntity<UserDetail> userDetail(@RequestParam("anm-id") String anmIdentifier) {
		User user = opensrpAuthenticationProvider.getDrishtiUser(anmIdentifier);
		return new ResponseEntity<>(new UserDetail(user.getUsername(), user.getRoles()), allowOrigin(opensrpSiteUrl), OK);
	}

	@Value("#{opensrp['openmrs.url']}")
	protected String OPENMRS_BASE_URL;
	@Value("#{opensrp['openmrs.username']}")
	protected String OPENMRS_USER;

	@Value("#{opensrp['openmrs.password']}")
	protected String OPENMRS_PWD;

	@RequestMapping("/security/authenticate")
	@ResponseBody
	public ResponseEntity<String> authenticate() throws JSONException {
		User u = currentUser();

		String lid = "";
		String lname = "";
		List<String>  locationAnmids=null;
		try {
			JSONObject tm = openmrsUserService.getTeamMember(u.getBaseEntity().getAttribute("_PERSON_UUID").toString());
			JSONArray locs = tm.getJSONArray("location");
			for (int i = 0; i < locs.length(); i++) {
				lid += locs.getJSONObject(i).getString("uuid") + ";;";
				lname += locs.getJSONObject(i).getString("name");
			}
			locationAnmids=getLocationAnmids(lname);
		} catch (Exception e) {
			System.out.println("USER Location info not mapped in team management module. Now trying Person Attribute");
			;
		}
		if (StringUtils.isEmptyOrWhitespaceOnly(lid)) {
			lid = (String) u.getBaseEntity().getAttribute("Location");
			if (StringUtils.isEmptyOrWhitespaceOnly(lid)) {
				String lids = (String) u.getBaseEntity().getAttribute("Locations");

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
		map.put("locations", l);
		map.put("locationAnmids", locationAnmids);
		return new ResponseEntity<>(new Gson().toJson(map), allowOrigin(opensrpSiteUrl), OK);
	}

	private List<String> getLocationAnmids(String location) throws JSONException {
		String url = OPENMRS_BASE_URL + "ws/rest/v1/teammodule/memberLocation";
		HttpResponse op = HttpUtil.get(HttpUtil.removeEndingSlash(url), "q=" + location + "&v=full", OPENMRS_USER, OPENMRS_PWD);
		JSONArray res = new JSONObject(op.body()).getJSONArray("results");
		logger.debug(res.toString());
		List<String> usernames=new ArrayList<String>();
		if (res != null && res.length() > 0) {
			for(int i=0;i<res.length();i++){
				JSONObject user=res.getJSONObject(i).getJSONObject("teamMember");
				String username=user.getString("identifier");
				usernames.add(username);
			}

		}
		return usernames;
	}
}
