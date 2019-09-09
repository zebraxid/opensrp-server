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
import org.opensrp.common.util.HttpResponse;
import org.opensrp.common.util.HttpUtil;
import org.opensrp.connector.openmrs.service.OpenmrsLocationService;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.domain.postgres.CustomQuery;
import org.opensrp.service.ClientService;
import org.opensrp.service.EventService;
import org.opensrp.service.LocationService;
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
import java.util.*;

import static org.opensrp.web.HttpHeaderFactory.allowOrigin;
import static org.springframework.http.HttpStatus.OK;

@Controller
public class UserController {
	
	@Value("#{opensrp['opensrp.site.url']}")
	private String opensrpSiteUrl;

	@Value("#{opensrp['opensrp.web.url']}")
	protected String OPENSRP_BASE_URL;

	@Value("#{opensrp['opensrp.web.username']}")
	protected String OPENSRP_USER;

	@Value("#{opensrp['opensrp.web.password']}")
	protected String OPENSRP_PWD;

	private static int childRoleId = 13;

	@Autowired
	private LocationService locationService;
	
	private DrishtiAuthenticationProvider opensrpAuthenticationProvider;
	
	private OpenmrsLocationService openmrsLocationService;
	
	private OpenmrsUserService openmrsUserService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private EventService eventService;
	
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
		Authentication a = getAuthenticationAdvisor(request);
		return getAuthenticationProvider().getDrishtiUser(a, a.getName());
	}
	
	public Time getServerTime() {
		return new Time(Calendar.getInstance().getTime(), TimeZone.getDefault());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/user-details")
	public ResponseEntity<UserDetail> userDetail(@RequestParam("anm-id") String anmIdentifier, HttpServletRequest request) {
		Authentication a = getAuthenticationAdvisor(request);
		User user = opensrpAuthenticationProvider.getDrishtiUser(a, anmIdentifier);
		return new ResponseEntity<>(new UserDetail(user.getUsername(), user.getRoles()), allowOrigin(opensrpSiteUrl), OK);
	}
	
	@RequestMapping("/security/authenticate")
	@ResponseBody
	public ResponseEntity<String> authenticate(HttpServletRequest request) throws JSONException {
		User u = currentUser(request);
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
			CustomQuery customQuery = clientService.findTeamInfo(u.getUsername());

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

	@RequestMapping(value = "/provider/location-tree", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getLocationTree(@RequestParam("username") String username) throws JSONException {

		CustomQuery user = eventService.getUser(username);
		CustomQuery teamMember = eventService.getTeamMemberId(user.getId());
		List<CustomQuery> treeDTOS = clientService.getProviderLocationTreeByChildRole(teamMember.getId(), childRoleId);
		JSONArray array = new JSONArray();
		try {
			array = locationService.convertLocationTreeToJSON(treeDTOS);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(array.toString(), OK);
	}

	@RequestMapping(value = "/household/generated-code", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getHouseholdUniqueId(@RequestParam("username") String username,
													   @RequestParam("villageId") String villageId) throws JSONException {
		HttpResponse op = HttpUtil.get(OPENSRP_BASE_URL+"/household/generated-code?username="+username+"&villageId="+villageId, "", OPENSRP_USER, OPENSRP_PWD);
		JSONArray res = new JSONArray(op.body());
		return new ResponseEntity<>(res.toString(), OK);
	}
}
