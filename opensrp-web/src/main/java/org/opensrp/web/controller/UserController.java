package org.opensrp.web.controller;

import static org.opensrp.web.HttpHeaderFactory.allowOrigin;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.api.domain.User;
import org.opensrp.api.util.LocationTree;
import org.opensrp.common.domain.UserDetail;
import org.opensrp.connector.openmrs.service.OpenmrsLocationService;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.register.mcare.bo.DgfpClient;
import org.opensrp.rest.repository.LuceneHouseHoldRepository;
import org.opensrp.rest.repository.LuceneMemberRepository;
import org.opensrp.rest.services.LuceneHouseHoldService;
import org.opensrp.rest.services.LuceneMemberService;
import org.opensrp.web.security.DrishtiAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.LuceneResult.Row;
import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;

@Controller
public class UserController {
	private String opensrpSiteUrl;
	private DrishtiAuthenticationProvider opensrpAuthenticationProvider;
	private OpenmrsLocationService openmrsLocationService;
	private OpenmrsUserService openmrsUserService;
	private LuceneHouseHoldService luceneHouseHoldService;
	private LuceneMemberService luceneMemberService;

	@Autowired
	public UserController(OpenmrsLocationService openmrsLocationService,
			OpenmrsUserService openmrsUserService,
			DrishtiAuthenticationProvider opensrpAuthenticationProvider,
			LuceneHouseHoldService luceneHouseHoldService, LuceneMemberService luceneMemberService) {
		this.openmrsLocationService = openmrsLocationService;
		this.openmrsUserService = openmrsUserService;
		this.opensrpAuthenticationProvider = opensrpAuthenticationProvider;
		this.luceneHouseHoldService = luceneHouseHoldService;
		this.luceneMemberService = luceneMemberService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/authenticate-user")
	public ResponseEntity<HttpStatus> authenticateUser(
			HttpServletRequest request) {
		// System.out.println("Client :" + request.getRemoteAddr());
		return new ResponseEntity<>(null, allowOrigin(opensrpSiteUrl), OK);
	}

	public User currentUser() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return opensrpAuthenticationProvider.getDrishtiUser(authentication);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/user-details")
	public ResponseEntity<UserDetail> userDetail(
			@RequestParam("anm-id") String anmIdentifier) {
		User user = opensrpAuthenticationProvider.getDrishtiUser(anmIdentifier);
		return new ResponseEntity<>(new UserDetail(user.getUsername(),
				user.getRoles()), allowOrigin(opensrpSiteUrl), OK);
	}

	@RequestMapping("/security/authenticate")
	@ResponseBody
	public ResponseEntity<String> authenticate() throws JSONException {
		User u = currentUser();
		String lid = "";
		try {
			JSONObject tm = openmrsUserService.getTeamMember(u.getBaseEntity()
					.getAttribute("_PERSON_UUID").toString());
			JSONArray locs = tm.getJSONArray("location");
			for (int i = 0; i < locs.length(); i++) {
				lid += locs.getJSONObject(i).getString("uuid") + ";;";
			}
		} catch (Exception e) {
			System.out
					.println("USER Location info not mapped in team management module. Now trying Person Attribute");
			;
		}
		if (StringUtils.isEmptyOrWhitespaceOnly(lid)) {
			lid = (String) u.getBaseEntity().getAttribute("Location");
			if (StringUtils.isEmptyOrWhitespaceOnly(lid)) {
				String lids = (String) u.getBaseEntity().getAttribute(
						"Locations");

				if (lids == null) {
					throw new RuntimeException(
							"User not mapped on any location. Make sure that user have a person attribute Location or Locations with uuid(s) of valid OpenMRS Location(s) separated by ;;");
				}

				lid = lids;
			}
		}
		LocationTree l = openmrsLocationService.getLocationTreeOf(lid
				.split(";;"));
		Map<String, Object> map = new HashMap<>();
		map.put("user", u);
		map.put("locations", l);
		return new ResponseEntity<>(new Gson().toJson(map),
				allowOrigin(opensrpSiteUrl), OK);
	}

	@RequestMapping(headers = { "Accept=application/json" }, method = GET, value = "/search")
	public ResponseEntity<String> search(
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "nid", required = false) String nationalId,
			@RequestParam(value = "birthId", required = false) String birthId) throws JSONException {
		List<DgfpClient> dgfpClients = new ArrayList<DgfpClient>();
		dgfpClients.addAll(this.luceneHouseHoldService.getAllHouseHoldClientBasedOn(firstName, nationalId, birthId));
		dgfpClients.addAll(this.luceneMemberService.getAllHouseHoldClientBasedOn(firstName, nationalId, birthId));
		Map<String, List<DgfpClient>> responseResult = new HashMap<String, List<DgfpClient>>();
		responseResult.put("clients", dgfpClients);
		return new ResponseEntity<>(new Gson().toJson(responseResult), OK);
	}
}
